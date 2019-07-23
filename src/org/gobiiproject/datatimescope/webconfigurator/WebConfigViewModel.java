package org.gobiiproject.datatimescope.webconfigurator;

import org.gobiiproject.datatimescope.services.UserCredential;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.generateAlertMessage;
import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.scriptExecutor;

/**
 * The main class for the entire webconfigurator model. This class controls both the redirection upon interaction with website elements
 * as well as functioning as the centre point to all functionality. All user interaction triggers a response in this class
 * which then either passes on the responsibility to one of its members or works with one of its members to process the task.
 *
 */

public class WebConfigViewModel extends SelectorComposer<Component> {

    private XmlModifier xmlHandler = new XmlModifier();
    private BackupHandler backupHandler = new BackupHandler();
    protected ServerHandler serverHandler = new ServerHandler(xmlHandler);
    public CronHandler cronHandler = new CronHandler();
    private XmlCropHandler xmlCropHandler = new XmlCropHandler();
    private WarningComposer warningComposer = new WarningComposer(xmlHandler);
    public PropertyHandler propertyHandler = new PropertyHandler();
    private Crop currentCrop = new Crop();
    private boolean documentLocked = true;
    private boolean isSuperAdmin = false;
    private String newXMLPath;
    private boolean locationSet = false;
    private boolean firstUpload = true;
    private boolean isKeySet = true;

    @Init
    public void init() {
        UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
        if (cre.getRole() == 1) {
            isSuperAdmin = true;
            keygen();
        }
    }

    private void keygen() {
        if (!new File("/home/gadm/.ssh/id_rsa.pub").exists()) {
            alert("Please do keygen for the docker instance to the host by hand.");
            isKeySet = false;
        }
    }

    /**
     * This function controls the en/disable attribute of most textboxes and buttons within the module
     */
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
     * Creates a warning box for the user and reloads all Tomcat services upon acceptance, before routing the user to the homepage
     * @param binder
     */
    @Command("tomcatModification")
    public void tomcatModification(@ContextParam(ContextType.BINDER) Binder binder){
        warningComposer.warningTomcat(binder, this);
    }

    @Command("postgresModification")
    public void postgresModification(@ContextParam(ContextType.BINDER) Binder binder){
        warningComposer.warningPostgres(binder, this);
    }


    /**
     * Validates and if accepted modifies the current Crops cron jobs
     */
    @Command("reloadCrons")
    public void reloadCrons(@ContextParam(ContextType.BINDER) Binder binder){
        if (!cronHandler.reloadCrons(xmlHandler.getHostForReload(), currentCrop)){
            alert(generateAlertMessage(cronHandler.getErrorMessages()));
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
        try {
            InputStream is = new FileInputStream("/data/gobii_bundle/config/gobii-web.xml");
            Filedownload.save(is, "text/xml", "gobii-web.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            alert("File not found.");
        }
        binder.sendCommand("disableEdit", null);
        goToHome();
    }

    /**
     * Creates a temp file in the current execution location from which the file will be sent to the server from
     * (and deleted from) and then validated on server side.
     * @param event
     * @return true if creation was successful
     */
    private String createTmpCopyOfUpload(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event){
        FileOutputStream fos;
        try {
            File xml = new File(event.getMedia().getName());
            fos = new FileOutputStream(xml);
            fos.write(event.getMedia().getStringData().getBytes());
            fos.close();
            return xml.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Command("selectFileForImport")
    @NotifyChange({"locationSet" , "newXMLShort"})
    public void selectFileForImport(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event, @ContextParam(ContextType.BINDER) Binder binder){
        newXMLPath = createTmpCopyOfUpload(event);
        if (newXMLPath == null){
            alert("File not found.");
            binder.sendCommand("disableEdit",null);
            return;
        }
        locationSet = true;
    }

    /**
     * Imports a user provided XML file and sends it to the server via scp where it is processed and validated.
     * If it passes validation it is assigned as the new gobii-web.xml and will reload all the context-paths, if accepted by the user
     * @param binder
     */
    @Command("importXML")
    public void importXML(@ContextParam(ContextType.BINDER) Binder binder){
        List<String> params = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), "scpto", newXMLPath));
        if (!scriptExecutor("importExportXml.sh", params)){
            binder.sendCommand("disableEdit",null);
            return;
        }
        xmlHandler.setPath("/data/gobii_bundle/config/gobii-web-tmp.xml");
        XmlValidator validator = new XmlValidator(xmlHandler);
        if (validator.validateGobiiConfiguration()){
            params = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), "passed"));
            if (scriptExecutor("importExportXml.sh", params)){
                warningComposer.warningTomcat(binder, this);
            } else {
                binder.sendCommand("disableEdit",null);
            }
        } else {
            xmlHandler.setPath("/data/gobii_bundle/config/gobii-web.xml");
            params = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), "failed"));
            if (!scriptExecutor("importExportXml.sh", params)){
                alert(validator.getErrorMessage() + "\nCouldn't remove the imported file from server.");
            } else {
                alert(validator.getErrorMessage());
            }
            binder.sendCommand("disableEdit",null);
        }
        locationSet = false;
    }


    /**
     * Adds a new Crop to an existing database, calling the scripts in rawbase and liquibase to populate it according to
     * http://cbsugobii05.biohpc.cornell.edu:6084/pages/viewpage.action?pageId=7833278
     * As specified here https://gobiiproject.atlassian.net/browse/I19-53 validation is performed before adding contact data to the new crop
     * !Upon contact data failure it allows the user to reupload a file, without reloading the page!
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
        if (currentCrop.getContactData() == null){
            alert("Please upload a contact data file, otherwise the database cannot be created.");
            binder.sendCommand("disableEdit",null);
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
        xmlCropHandler.appendCrop(currentCrop);
        List<String> createFiles = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), currentCrop.getName(), "1" , String.valueOf(xmlHandler.getCropList().get(0))));
        if (!scriptExecutor("cropFileManagement.sh", createFiles)){
            binder.sendCommand("disableEdit",null);
            return;
        }
        String oldWar = xmlHandler.getContextPathNodes().item(0).getTextContent();
        List<String> duplicateWAR = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), "1" , currentCrop.getWARName(), oldWar));
        if (!scriptExecutor("WARHandler.sh", duplicateWAR)){
            binder.sendCommand("disableEdit",null);
            return;
        }
        cronHandler.modifyCron("create", xmlHandler.getHostForReload(), currentCrop);
        currentCrop.setHideContactData(true);
        binder.sendCommand("disableEdit", null);
        goToHome();
    }


    /**
     * Reads in the contact data from a user provided file path and adds each line to an Arraylist as a String for later processing
     */
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

    /**
     * Performs the actual deletion, after being warned in SeverHandler about the risks of deletion.
     * This function should never be called by hand and instead always be called by the warning form WarningHandler
     * The function drops the database, removes the .war, deletes the gobii_bundle directory and subdirectory,
     * removes the CRON tasks and removes the crop from the XML.
     * @param binder
     */
    public void executeRemoval (@ContextParam(ContextType.BINDER) Binder binder){
        if (!serverHandler.undeployFromTomcat(currentCrop)){
            alert("Couldn't undeploy " + xmlHandler.getWebContextPath(currentCrop.getName()) + ", undeploying forcibly instead.");
        } else {
            List<String> removeWAR = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), "0", xmlHandler.getWARName(currentCrop.getName())));
            if (!scriptExecutor("WARHandler.sh", removeWAR)) {
                binder.sendCommand("disableEdit", null);
                return;
            }
        }
        if (!serverHandler.postgresRemoveCrop(currentCrop.getName())) {
            return;
        }
        xmlCropHandler.removeCrop(currentCrop);
        List<String> removeBundle = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), currentCrop.getName(), "0", String.valueOf(xmlHandler.getCropList().get(0))));
        if (!scriptExecutor("cropFileManagement.sh", removeBundle)) {
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

    @Command("saveConfigCreds")
    public void saveConfigCreds(@BindingParam("windowConfig") Window x){
        x.detach();
    }

    @Command("cancelConfigCreds")
    public void cancelConfigCreds(@BindingParam("windowConfig") Window x){
        x.detach();
    }

    //TODO rename this to more accurate name
    @Command("setLocationForSeeddata")
    public void setLocationForSeeddata (@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event, @ContextParam(ContextType.BINDER) Binder binder) {
        currentCrop.setContactData(createTmpCopyOfUpload(event));
    }

    protected void goToHome () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/mainContent.zul");
        getPage().getDesktop().setBookmark("p_" + "home");
    }

    //Switch src for tag with id = mainContent from current page to X, in this call X = postgreSystemUser.zul

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
        return isSuperAdmin && isKeySet;
    }

    public Crop getCurrentCrop () {
        return currentCrop;
    }

    public String getNewXMLPath () {
        return newXMLPath;
    }

    public String getNewXMLShort() {
        if (newXMLPath == null){
            return null;
        } else {
            String[] split = newXMLPath.split("/");
            return split[split.length - 1];
        }
    }

    public boolean getLocationSet () {
        return locationSet;
    }

    public BackupHandler getBackupHandler () {
        return backupHandler;
    }

    public PropertyHandler getPropertyHandler() {
        return propertyHandler;
    }
}

