package org.gobiiproject.datatimescope.webconfigurator;

import org.gobiiproject.datatimescope.services.UserCredential;
import org.jooq.DSLContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.Pair;
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

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.generateAlertMessage;
import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.scriptExecutor;

public class WebConfigViewModel extends SelectorComposer<Component> {

    private XmlModifier xmlHandler = new XmlModifier();
    private BackupHandler backupHandler = new BackupHandler();
    protected ServerHandler serverHandler = new ServerHandler(xmlHandler);
    public CronHandler cronHandler = new CronHandler();
    private XmlCropHandler xmlCropHandler = new XmlCropHandler();
    private WarningComposer warningComposer = new WarningComposer(xmlHandler);
    private Crop currentCrop = new Crop();
    private boolean documentLocked = true;
    private boolean isSuperAdmin = false;
    private String newXMLPath;
    private boolean showNewXml = false;
    private boolean locationSet = false;
    private String exportLocation = "";
    private boolean firstUpload = true;

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

    @Command("tomcatModification")
    public void tomcatModification(@ContextParam(ContextType.BINDER) Binder binder){
        warningComposer.warningTomcat(binder, this);
    }

    @Command("postgresModification")
    public void postgresModification(@ContextParam(ContextType.BINDER) Binder binder){
        warningComposer.warningPostgres(binder, this);
    }


    /**
     * Validation of the User modifications made
     */
    @Command("reloadCrons")
    public void reloadCrons(@ContextParam(ContextType.BINDER) Binder binder){
        if (!cronHandler.reloadCrons(xmlHandler.getHostForReload(), currentCrop)){
            generateAlertMessage(cronHandler.getErrorMessages());
            binder.sendCommand("disableEdit", null);
        } else {
            binder.sendCommand("disableEdit", null);
            goToHome();
        }
    }

    @Command("saveBackup")
    public void saveBackup(@ContextParam(ContextType.BINDER) Binder binder) {
        binder.sendCommand("disableEdit",null);
        if (backupHandler.getErrorMessages().size() > 0){
            //Reading in from user had an error
            alert(generateAlertMessage(backupHandler.getErrorMessages()));
            //Reset back to old Data
            backupHandler.readDataFromCrons();
        } else {
            if (!backupHandler.saveDataToCrons(xmlHandler.getHostForReload())){
                alert(generateAlertMessage(backupHandler.getErrorMessages()));
            }

        }
    }

    @Command("exportXML")
    public void exportXML(@ContextParam(ContextType.BINDER) Binder binder){
        List<String> sec_copy_back = new ArrayList<>(Arrays.asList("scpfrom" , exportLocation));
        if (!scriptExecutor("importExportXml.sh", sec_copy_back)){
            alert("Couldn't import the file from the server.");
            binder.sendCommand("disableEdit",null);
            return;
        }
        exportLocation = "";
        locationSet = false;
        binder.sendCommand("disableEdit", null);
        goToHome();
    }

    @Command("setLocationForExport")
    @NotifyChange({"locationSet", "exportLocation"})
    public void setLocationForExport(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            exportLocation = selectedFile.getAbsolutePath();
            locationSet = true;
        }
    }

    private boolean setNewXmlPath(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event){
        boolean success = false;
        FileOutputStream fos;
        try {
            File xml = new File(event.getMedia().getName());
            fos = new FileOutputStream(xml);
            fos.write(event.getMedia().getStringData().getBytes());
            fos.close();
            newXMLPath = xml.getAbsolutePath();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }


    @Command("importXML")
    public void importXML(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event, @ContextParam(ContextType.BINDER) Binder binder){
        showNewXml = true;
        if (!setNewXmlPath(event)){
            alert("File not found.");
            binder.sendCommand("disableEdit",null);
            return;
        }
        List<String> params = new ArrayList<>(Arrays.asList("scpto", newXMLPath));
        if (!scriptExecutor("importExportXml.sh", params)){
            alert("Couldn't send the file to server for validation.");
            binder.sendCommand("disableEdit",null);
            return;
        }
        xmlHandler.setPath("/data/gobii_bundle/config/gobii-web-tmp.xml");
        XmlValidator validator = new XmlValidator(xmlHandler);
        if (validator.validateGobiiConfiguration()){
            ArrayList<String> param = new ArrayList<>();
            param.add("failed");
            if (scriptExecutor("importExportXml.sh", param)){
                warningComposer.warningTomcat(binder, this);
            } else {
                alert("Couldn't set the imported file as the new reference file.");
                binder.sendCommand("disableEdit",null);
            }
        } else {
            xmlHandler.setPath("/data/gobii_bundle/config/gobii-web.xml");
            ArrayList<String> param = new ArrayList<>();
            param.add("failed");
            if (!scriptExecutor("importExportXml.sh", param)){
                alert(validator.getErrorMessage() + "\nCouldn't remove the imported file from server.");
            } else {
                alert(validator.getErrorMessage());
            }
            binder.sendCommand("disableEdit",null);
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
        ArrayList<String> contacts = readInContactData();
        int seedData = serverHandler.postgresAddCrop(currentCrop, contacts, firstUpload);
        if (seedData == 1){ //Liquibase failed
            binder.sendCommand("disableEdit", null);
            return;
        } else if (seedData == -1){
            //Remember if upload was tried already
            firstUpload = false;
            return;
        }
        firstUpload = true;
        String oldWar = xmlHandler.getContextPathNodes().item(0).getTextContent();
        List<String> duplicateWAR = new ArrayList<>(Arrays.asList("1" , currentCrop.getWARName(), oldWar));
        if (!scriptExecutor("WARHandler.sh", duplicateWAR)){
            alert("Couldn't duplicate the WAR file for deployment.");
            binder.sendCommand("disableEdit",null);
            return;
        }
        xmlCropHandler.appendCrop(currentCrop);
        List<String> createFiles = new ArrayList<>(Arrays.asList( currentCrop.getName(), "1" , String.valueOf(xmlHandler.getCropList().get(0))));
        if (!scriptExecutor("cropFileManagement.sh", createFiles)){
            alert("Couldn't create the files necessary within gobii_bundle.");
            binder.sendCommand("disableEdit",null);
            return;
        }
        cronHandler.modifyCron("create", xmlHandler.getHostForReload(), currentCrop);
        currentCrop.setHideContactData(true);
        binder.sendCommand("disableEdit", null);
        goToHome();
    }

    private ArrayList<String> readInContactData() {
         BufferedReader buffer;
        ArrayList<String> lines = new ArrayList<>();
        try {
            File fileName = new File(currentCrop.getContactData());
            buffer = new BufferedReader(new FileReader(fileName));
            String lineJustFetched;
            while((lineJustFetched = buffer.readLine()) != null) {
                lines.add(lineJustFetched);
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
            alert("The uploaded File could not be found.");
        }
        return lines;
    }

    /**
     * Toggles the field isActive in the XML of the respective crop chosen by a dropdown menu
     */
    @Command("modifyCropActive")
    public void modifyCropActive (@ContextParam(ContextType.BINDER) Binder binder){
        currentCrop.setWARName(xmlHandler.getWARName(currentCrop.getName()));
        warningComposer.warningActivityTomcat(binder, this, currentCrop);
    }

    /**
     * Removes the chosen crop from the database and all the associated locations (XML, Tomcat, CRON)
     * The Crop is chosen by dropdown menu
     */
    @NotifyChange("cropList")
    @Command("removeCropFromDatabase")
    public void removeCropFromDatabase (@ContextParam(ContextType.BINDER) Binder binder){
        warningComposer.warningRemoval(binder, this);
    }

    public void executeRemoval (@ContextParam(ContextType.BINDER) Binder binder){
        ViewModelServiceImpl tmpService = new ViewModelServiceImpl();
        DSLContext context = tmpService.getDSLContext();
        try {
            context.fetch("DROP DATABASE " + xmlHandler.getDatabaseName(currentCrop.getName()) + ";");
        } catch (Exception e) {
            alert("The database could not be removed.");
            return;
        }
        List<String> removeWAR = new ArrayList<>(Arrays.asList("0", xmlHandler.getWARName(currentCrop.getName())));
        if (!scriptExecutor("WARHandler.sh", removeWAR)) {
            alert("Couldn't remove the WAR file from deployment.");
            binder.sendCommand("disableEdit", null);
            return;
        }
        xmlCropHandler.removeCrop(currentCrop);
        List<String> removeBundle = new ArrayList<>(Arrays.asList(currentCrop.getName(), "0", String.valueOf(xmlHandler.getCropList().get(0))));
        if (!scriptExecutor("cropFileManagement.sh", removeBundle)) {
            alert("Couldn't remove the files within gobii_bundle.");
            binder.sendCommand("disableEdit", null);
            return;
        }
        cronHandler.modifyCron("delete", xmlHandler.getHostForReload(), currentCrop);
        binder.sendCommand("disableEdit", null);
        goToHome();
    }


    @Command("disableEdit")
    @NotifyChange("documentLocked")
    public void disableEdit () {
        this.documentLocked = true;
    }

    @Command("cancelChanges")
    @NotifyChange("documentLocked")
    public void cancelChanges () {
        this.documentLocked = true;
    }

    @Command("setLocationForSeeddata")
    public void setLocationForSeeddata () {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            currentCrop.setContactData(selectedFile.getAbsolutePath());
        }
    }

    protected void goToHome () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/mainContent.zul");
        getPage().getDesktop().setBookmark("p_" + "home");
    }

    //Switch src for tag with id = mainContent from current page to X, in this call X = mainContent.zul

    @Command("postgresSystemUser")
    public void postgresSystemUser () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/postgresSystemUser.zul");
        getPage().getDesktop().setBookmark("p_" + "postgresSystemUser");
    }

    @Command("ldapSystemUser")
    public void ldapSystemUser () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ldapSystemUser.zul");
        getPage().getDesktop().setBookmark("p_" + "ldapUserSystem");
    }

    @Command("ldapUnitUser")
    public void ldapUnitUser () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ldapUnitUser.zul");
        getPage().getDesktop().setBookmark("p_" + "ldapUnitUser");
    }

    @Command("emailNotifications")
    public void emailNotifications () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/emailNotifications.zul");
        getPage().getDesktop().setBookmark("p_" + "emailNotifications");
    }

    @Command("pushNotifications")
    public void pushNotifications () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/pushNotifications.zul");
        getPage().getDesktop().setBookmark("p_" + "pushNotifications");
    }

    @Command("addCrop")
    public void addCrop () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/addCrop.zul");
        getPage().getDesktop().setBookmark("p_" + "addCrop");
    }

    @Command("deleteCrop")
    public void deleteCrop () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/deleteCrop.zul");
        getPage().getDesktop().setBookmark("p_" + "deleteCrop");
    }

    @Command("modifyCrop")
    public void manageCrop () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/modifyCrop.zul");
        getPage().getDesktop().setBookmark("p_" + "modifyCrop");
    }

    @Command("logSettings")
    public void logSettings () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/logSettings.zul");
        getPage().getDesktop().setBookmark("p_" + "logSettings");
    }

    @Command("linkageGroups")
    public void linkeageGroups () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/linkageGroups.zul");
        getPage().getDesktop().setBookmark("p_" + "linkageGroups");
    }

    @Command("markerGroups")
    public void markerGroups () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/markerGroups.zul");
        getPage().getDesktop().setBookmark("p_" + "markerGroups");
    }

    @Command("kdCompute")
    public void kdCompute () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/kdCompute.zul");
        getPage().getDesktop().setBookmark("p_" + "KDComputeIntegration");
    }

    @Command("ownCloud")
    public void ownCloud () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ownCloud.zul");
        getPage().getDesktop().setBookmark("p_" + "OwnCloud");
    }

    @Command("galaxy")
    public void galaxy () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/galaxy.zul");
        getPage().getDesktop().setBookmark("p_" + "Galaxy");
    }

    @Command("scheduler")
    public void scheduler () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/scheduler.zul");
        getPage().getDesktop().setBookmark("p_" + "Scheduler");
    }

    @Command("portConfig")
    public void portConfig () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/portConfig.zul");
        getPage().getDesktop().setBookmark("p_" + "PortConfiguration");
    }

    @Command("backup")
    public void backup () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/backup.zul");
        getPage().getDesktop().setBookmark("p_" + "backup");
    }

    @Command("import")
    public void import_settings () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/import.zul");
        getPage().getDesktop().setBookmark("p_" + "import");
    }

    @Command("export")
    public void export_settings () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/export.zul");
        getPage().getDesktop().setBookmark("p_" + "export");
    }

    public void doAfterCompose (Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public XmlModifier getXmlHandler () {
        return xmlHandler;
    }

    public boolean getdocumentLocked () {
        return documentLocked;
    }

    public boolean getisSuperAdmin () {
        return isSuperAdmin;
    }

    public Crop getCurrentCrop () {
        return currentCrop;
    }

    public String getNewXMLPath () {
        return newXMLPath;
    }

    public boolean getShowNewXml () {
        return showNewXml;
    }

    public boolean getLocationSet () {
        return locationSet;
    }

    public String getExportLocation () {
        return exportLocation;
    }

    public BackupHandler getBackupHandler () {
        return backupHandler;
    }
}

