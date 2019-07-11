package org.gobiiproject.datatimescope.webconfigurator;

import org.gobiiproject.datatimescope.services.UserCredential;
import org.jooq.DSLContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;

import javax.swing.*;
import java.io.*;
import java.util.*;

import static org.gobiiproject.datatimescope.webconfigurator.utilityFunctions.scriptExecutor;

public class WebConfigViewModel extends SelectorComposer<Component> {

    private XmlModifier xmlHandler = new XmlModifier();
    private boolean documentLocked = true;
    private boolean isSuperAdmin = false;
    private Crop currentCrop = new Crop();
    private String newXMLPath;
    private boolean showNewXml = false;
    private boolean locationSet = false;
    private String exportLocation = "";
    private BackupHandler bh = new BackupHandler();
    private ServerHandler serverHandler = new ServerHandler(xmlHandler);


    @Init
    public void init() {
        UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
        if (cre.getRole() == 1) {
            isSuperAdmin = true;
        }
    }


    @Command("enableEdit")
    @NotifyChange("documentLocked")
    public void enableEdit() {
        if (isSuperAdmin) {
            this.documentLocked = false;
        } else {
            alert("Only Super Admins can configure these settings.");
        }
    }

    /**
     * Validation of the User modifications made
     */
    @Command("reloadCrons")
    public void reloadCrons(@ContextParam(ContextType.BINDER) Binder binder){
        if (currentCrop.getName() == null){
            alert("Please specify a crop.");
            binder.sendCommand("disableEdit", null);
            return;
        } else if (currentCrop.getFileAge() > 59 || currentCrop.getFileAge() < 1 || currentCrop.getCron() > 59 || currentCrop.getCron() < 1){
            alert("Please choose a valid value between 1 and 59. The default setting is 2.");
            binder.sendCommand("disableEdit", null);
            return;
        }
        modifyCron("update");
        binder.sendCommand("disableEdit", null);
    }

    @Command("saveBackup")
    public void saveBackup(@ContextParam(ContextType.BINDER) Binder binder) {
        binder.sendCommand("disableEdit", null);
        if (bh.getErrors().size() > 0){
            StringBuilder sb = new StringBuilder();
            for (String str : bh.getErrors()){
                sb.append(str);
                sb.append("\n");
            }
            alert(sb.toString());
            bh.readDataFromCrons();
        } else {
            ArrayList<String> newJobs = bh.saveDataToCrons();
            FileWriter writer = null;
            try {
                writer = new FileWriter("newCrons.txt");
                for (String str : newJobs) {
                    writer.write(str + System.lineSeparator());
                }
                writer.close();
                Runtime.getRuntime().exec("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/dockerCopyCron.sh " + xmlHandler.getHostForReload());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //make modifications to file
        //call dockerCronCopy.sh for next 2
            //send crons back
            //rm temp cron file
    }

    @Command("exportXML")
    public void exportXML(@ContextParam(ContextType.BINDER) Binder binder){
        String[] sec_copy_back = {"/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/import_export_xml.sh", "scpfrom" , exportLocation};
        try {
            new ProcessBuilder(sec_copy_back).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        exportLocation = "";
        locationSet = false;
        binder.sendCommand("cancelChanges", null);
    }

    @Command("setLocation")
    @NotifyChange({"locationSet", "exportLocation"})
    public void setLocation(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            exportLocation = selectedFile.getAbsolutePath();
            locationSet = true;
        }
    }

    @Command("importXML")
    //TODO on deploy
    public void importXML(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event, @ContextParam(ContextType.BINDER) Binder binder){
        showNewXml = true;
        FileOutputStream fos = null;
        try {
            File xml = new File(event.getMedia().getName());
            fos = new FileOutputStream(xml);
            fos.write(event.getMedia().getStringData().getBytes());
            fos.close();
            newXMLPath = xml.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> params = new ArrayList<>(Arrays.asList("scpto", newXMLPath));
        if (!scriptExecutor("importExportXml.sh", params)){
            //TODO Handle failure of script
        }
        xmlHandler.setPath("/data/gobii_bundle/config/gobii-web-tmp.xml");
        XmlValidator validator = new XmlValidator(xmlHandler);
        if (validator.validateGobiiConfiguration()){
            if (scriptExecutor("importExportXml.sh", Collections.singletonList("passed"))){
                if (!serverHandler.reloadTomcatAllCrops()){
                    //TODO Handle failure
                }
            } else {
                //TODO Handle failure of script
            }
        } else {
            alert(validator.getErrorMessage());
            xmlHandler.setPath("/data/gobii_bundle/config/gobii-web.xml");
            if (!scriptExecutor("importExportXml.sh", Collections.singletonList("failed"))){
                //TODO Handle failure of script
            }
            binder.sendCommand("disableEdit", null);
        }
    }


    /**
     * Adds a new Crop to an existing database, calling the scripts in rawbase and liquibase to populate it according to
     * http://cbsugobii05.biohpc.cornell.edu:6084/pages/viewpage.action?pageId=7833278
     * It also duplicates the gobii-dev.war file within tomcat webapps for tomcat deployment (Happens automatically upon creation)
     * Furthermore it modifies the XML to reflect the new Crop and configures the respective CRON jobs for the new crop
     * using the default values of 2 and 2
     */
    @Command("addCropToDatabase")
    public void addCropToDatabase(@ContextParam(ContextType.BINDER) Binder binder){
        List currentCrops = xmlHandler.getCropList();
        if (currentCrops.contains(currentCrop.getName())){
            alert("This crop already has a database associated with it. Please choose another crop.");
            return;
        }
        ViewModelServiceImpl tmpService = new ViewModelServiceImpl();
        DSLContext context = tmpService.getDSLContext();
        try {
            context.fetch("CREATE DATABASE " + currentCrop.getDatabaseName() + " WITH OWNER '" + xmlHandler.getPostgresUserName() + "';");
        } catch (Exception e){
            alert("This database name is already in use or the name is using invalid characters. Please choose another name.");
            return;
        }
        //Purely for debugging, can be removed once validated
        String command = "docker exec -ti gobii-web-node bash -c 'cd liquibase; " +
                "java -jar bin/liquibase.jar" +
                " --username=" + xmlHandler.getPostgresUserName() +
                " --password=" + xmlHandler.getPostgresPassword() +
                " --url=jdbc:postgresql://" + xmlHandler.getPostgresHost() + ":" + xmlHandler.getPostgresPort() +
                "/" + currentCrop.getName() +
                " --driver=org.postgresql.Driver" +
                " --classpath=drivers/postgresql-9.4.1209.jar --changeLogFile=changelogs/db.changelog-master.xml" +
                " --contexts=general,seed_general update'";
        String[] populateDatabase = {"/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/liquibase.sh"
                , xmlHandler.getPostgresUserName(), xmlHandler.getPostgresPassword(), xmlHandler.getPostgresHost(), xmlHandler.getPostgresPort(),  currentCrop.getDatabaseName()};
        /*TODO on Deploy
        String[] populateDatabase = {"/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/liquibase.sh"
                , xmlHandler.getPostgresUserName(), xmlHandler.getPostgresPassword(), xmlHandler.getHostForReload()
                , xmlHandler.getPostgresHost(), xmlHandler.getPostgresPort(),  currentCrop.getName()};
        */
        try {
            new ProcessBuilder(populateDatabase).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String oldWar = xmlHandler.getContextPathNodes().item(0).getTextContent();
        String[] dupli = {"/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/WARHandler.sh", "1" , currentCrop.getWARName(), oldWar};
        //TODO on Deploy
        //String[] dupli = {"/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/WARHandler.sh", "1" , currentCrop.getWARName()};
        try {
            new ProcessBuilder(dupli).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xmlHandler.appendCrop(currentCrop);
        String[] addBundle = {"/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/cropFileManagement.sh", currentCrop.getName(), "1" , String.valueOf(xmlHandler.getCropList().get(0))};
        //TODO on deploy
        //String[] addBundle = {"/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/cropFileManagement.sh", currentCrop.getName(), "1" , String.valueOf(xmlHandler.getCropList().get(0))};
        try {
            new ProcessBuilder(addBundle).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        modifyCron("create");
        currentCrop.setHideContactData(true);
        binder.sendCommand("disableEdit", null);
    }

    /**
     * Toggles the field isActive in the XML of the respective crop chosen by a dropdown menu
     */
    @Command("modifyCropActive")
    public void modifyCropActive(@ContextParam(ContextType.BINDER) Binder binder){
        currentCrop.setWARName(xmlHandler.getWARName(currentCrop.getName()));
        binder.sendCommand("disableEdit", null);
        if (currentCrop.isActivityChanged()){
            currentCrop.setActivityChanged(false);
        } else {
            alert("No changes have been made, please change a setting.");
            return;
        }
        serverHandler.reloadTomcatSingleCrop(currentCrop.getName());
        if (currentCrop.getIsActive()) {
            modifyCron("create");
        } else {
            modifyCron("delete");
        }
    }

    /**
     * Removes the chosen crop from the database and all the associated locations (XML, Tomcat, CRON)
     * The Crop is chosen by dropdown menu
     */
    @NotifyChange("cropList")
    @Command("removeCropFromDatabase")
    public void removeCropFromDatabase(@ContextParam(ContextType.BINDER) Binder binder){
        ViewModelServiceImpl tmpService = new ViewModelServiceImpl();
        DSLContext context = tmpService.getDSLContext();
        try {
            context.fetch("DROP DATABASE " + xmlHandler.getDatabaseName(currentCrop.getName()) + ";");
        } catch (Exception e){
            alert("The database could not be removed.");
            return;
        }
        String[] removeWAR = {"/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/WARHandler.sh", "0" , xmlHandler.getWARName(currentCrop.getName())};
        //TODO on deploy
        //String[] removeWAR = {"/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/WARHandler.sh", "0" , currentCrop.getWARName()};
        try {
            new ProcessBuilder(removeWAR).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xmlHandler.removeCrop(currentCrop);
        String[] removeBundle = {"/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/cropFileManagement.sh", currentCrop.getName(), "0" , String.valueOf(xmlHandler.getCropList().get(0))};
        //TODO on deploy
        //String[] removeBundle = {"/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/cropFileManagement.sh", currentCrop.getName(), "0" , String.valueOf(xmlHandler.getCropList().get(0))};
        try {
            new ProcessBuilder(removeBundle).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        modifyCron("delete");
        xmlHandler.getCropList();
        binder.sendCommand("disableEdit", null);
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/mainContent.zul");
        getPage().getDesktop().setBookmark("p_"+"home");
    }



    @Command("disableEdit")
    @NotifyChange("documentLocked")
    public void disableEdit() {
        this.documentLocked = true;
    }

    @Command("upload")
    public void uploaded(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event){
        currentCrop.setContactData(event.getMedia());
    }

    //Switch src for tag with id = mainContent from current page to X, in this call X = mainContent.zul
    @Command("cancelChanges")
    public void cancelChanges(){
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/mainContent.zul");
        getPage().getDesktop().setBookmark("p_"+"home");
    }

    @Command("postgresSystemUser")
    public void postgresSystemUser(){
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/postgresSystemUser.zul");
        getPage().getDesktop().setBookmark("p_"+"postgresSystemUser");
    }

    @Command("ldapSystemUser")
    public void ldapSystemUser() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ldapSystemUser.zul");
        getPage().getDesktop().setBookmark("p_"+"ldapUserSystem");
    }

    @Command("ldapUnitUser")
    public void ldapUnitUser() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ldapUnitUser.zul");
        getPage().getDesktop().setBookmark("p_"+"ldapUnitUser");
    }

    @Command("emailNotifications")
    public void emailNotifications() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/emailNotifications.zul");
        getPage().getDesktop().setBookmark("p_"+"emailNotifications");
    }

    @Command("pushNotifications")
    public void pushNotifications() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/pushNotifications.zul");
        getPage().getDesktop().setBookmark("p_"+"pushNotifications");
    }

    @Command("addCrop")
    public void addCrop() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/addCrop.zul");
        getPage().getDesktop().setBookmark("p_"+"addCrop");
    }

    @Command("deleteCrop")
    public void deleteCrop() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/deleteCrop.zul");
        getPage().getDesktop().setBookmark("p_"+"deleteCrop");
    }

    @Command("modifyCrop")
    public void manageCrop() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/modifyCrop.zul");
        getPage().getDesktop().setBookmark("p_"+"modifyCrop");
    }

    @Command("logSettings")
    public void logSettings() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/logSettings.zul");
        getPage().getDesktop().setBookmark("p_"+"logSettings");
    }

    @Command("linkageGroups")
    public void linkeageGroups() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/linkageGroups.zul");
        getPage().getDesktop().setBookmark("p_"+"linkageGroups");
    }

    @Command("markerGroups")
    public void markerGroups() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/markerGroups.zul");
        getPage().getDesktop().setBookmark("p_"+"markerGroups");
    }

    @Command("kdCompute")
    public void kdCompute() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/kdCompute.zul");
        getPage().getDesktop().setBookmark("p_"+"KDComputeIntegration");
    }

    @Command("ownCloud")
    public void ownCloud() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ownCloud.zul");
        getPage().getDesktop().setBookmark("p_"+"OwnCloud");
    }

    @Command("galaxy")
    public void galaxy() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/galaxy.zul");
        getPage().getDesktop().setBookmark("p_"+"Galaxy");
    }

    @Command("scheduler")
    public void scheduler() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/scheduler.zul");
        getPage().getDesktop().setBookmark("p_"+"Scheduler");
    }

    @Command("portConfig")
    public void portConfig() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/portConfig.zul");
        getPage().getDesktop().setBookmark("p_"+"PortConfiguration");
    }

    @Command("backup")
    public void backup() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/backup.zul");
        getPage().getDesktop().setBookmark("p_"+"backup");
    }

    @Command("import")
    public void import_settings() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/import.zul");
        getPage().getDesktop().setBookmark("p_"+"import");
    }

    @Command("export")
    public void export_settings() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/export.zul");
        getPage().getDesktop().setBookmark("p_"+"export");
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public XmlModifier getXmlHandler(){
        return xmlHandler;
    }

    public boolean getdocumentLocked() {
        return documentLocked;
    }

    public boolean getisSuperAdmin() {
        return isSuperAdmin;
    }

    public Crop getCurrentCrop(){
        return currentCrop;
    }

    public String getNewXMLPath() {
        return newXMLPath;
    }

    public void setNewXMLPath(String newXMLPath) {
        this.newXMLPath = newXMLPath;
    }

    public boolean getShowNewXml() {
        return showNewXml;
    }

    public boolean getLocationSet() {
        return locationSet;
    }

    public String getExportLocation() {
        return exportLocation;
    }

    public BackupHandler getBh() {
        return bh;
    }
}

