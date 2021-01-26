package org.gobiiproject.datatimescope.webconfigurator;

import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import java.io.*;
import java.util.*;

import javax.servlet.ServletContext;

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.*;

/**
 * The main class for the entire webconfigurator model. This class controls both the redirection upon interaction with website elements
 * as well as functioning as the centre point to all functionality. All user interaction triggers a response in this class
 * which then either passes on the responsibility to one of its members or works with one of its members to process the task.
 *
 */

@SuppressWarnings("serial")
public class WebConfigViewModel extends SelectorComposer<Component> {

    public ServerHandler serverHandler;
    private ServerInfo serverInfo;
    public CronHandler cronHandler;
    private XmlModifier xmlHandler;
    private BackupHandler backupHandler;
    private XmlCropHandler xmlCropHandler;
    private WarningComposer warningComposer;
    private PropertyHandler propertyHandler;
    private Crop currentCrop;

    private String newXMLPath;
    private boolean documentLocked = true;
    private boolean isSuperAdmin = false;
    private boolean locationSet = false;
    private boolean firstUpload = true;
    private boolean isKeySet = true;
    private boolean setUpisDone = false;

    private String username;
    private String newname;

    @Init
    public void init() {
        if (setUpisDone){
            writeToLog("WebConfigViewModel.init()", "SetUp already is completed.", username);
        } else {
            setUpisDone = true;
            UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
            if (cre.getRole() == 1) {
                isSuperAdmin = true;
                username = cre.getAccount();
                instantiate();
                copyCurrentSettings();
                writeToLog("WebConfigViewModel.init()", "Configuration setup performed correctly.", username);
            } else {
                writeToLog("WebConfigViewModel.init()", "User is not SuperAdmin", username);
            }
        }
    }

    private void instantiate(){
    	
        xmlHandler = new XmlModifier(username);
        backupHandler = new BackupHandler(username);
        
        
        ServletContext context = (ServletContext)Executions.getCurrent().getDesktop().getWebApp().getServletContext();
        String propsFile = context.getRealPath("/WEB-INF/classes/gobii-configurator.properties");
        Properties properties = readPropertiesFile(propsFile);
        propertyHandler = new PropertyHandler(username, properties);
        serverHandler = new ServerHandler(xmlHandler, propertyHandler);
        serverInfo = serverHandler.getServerInfoFromCookies();
        cronHandler = new CronHandler(username, serverInfo);
        xmlCropHandler = new XmlCropHandler(username);
        warningComposer = new WarningComposer(xmlHandler, username);
        
        currentCrop = new Crop(username);
    }
    
    private Properties readPropertiesFile(String fileName) {
		 
		try {
			FileReader reader=new FileReader(fileName);  
			Properties p=new Properties(); 
			p.load(reader);
			return p;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return new Properties(); //default empty

	}

    
    

    /**
     * A function to create a copy of the current gobii-web.xml to enable a button to revert changes made.
     */
    private void copyCurrentSettings(){
        try {
            Runtime.getRuntime().exec(
            	String.format("cp %s %sgobii-web-tmp.xml", EnvUtil.getGobiiWebXmlFilename(), EnvUtil.getTempDir())
            	//"cp /data/gobii_bundle/config/gobii-web.xml /usr/local/tomcat/temp/gobii-web-tmp.xml"
            );
            writeToLog("WebConfigViewModel.copyCurrentSettings()", "Successfully created temporary copy of gobii-web.xml.", username);
        } catch (IOException e) {
            e.printStackTrace();
            writeToLog("WebConfigViewModel.copyCurrentSettings()", "Creation of temporary copy of gobii-web.xml failed.", username);
        }
    }

    /**
     * Revert all gobii-web.xml changes made during this session. This excludes crop addition, deletion, crop CRON jobs and backup configuration
     */
    @Command("revertAllSessionChanges")
    public void revertAllSessionChanges(){
        ListModelList<String> oldCrops = xmlHandler.getCropList();
        try {
            Runtime.getRuntime().exec(
            	String.format("cp %sgobii-web-tmp.xml %sgobii-web-tmp.xml", EnvUtil.getTempDir(), EnvUtil.getGobiiBundleDir())
            	//"cp /usr/local/tomcat/temp/gobii-web-tmp.xml /data/gobii_bundle/config/gobii-web-tmp.xml"
            );
            writeToLog("WebConfigViewModel.copyCurrentSettings()", "Successfully copied temporary copy of gobii-web.xml.", username);
        } catch (IOException e) {
            e.printStackTrace();
            writeToLog("WebConfigViewModel.copyCurrentSettings()", "Copying of temporary copy of gobii-web.xml failed.", username);
        }
        String oldPostgresName = xmlHandler.getPostgresUserName();
        xmlHandler.setPath(String.format("%sgobii-web-tmp.xml", EnvUtil.getGobiiBundleDir()));
        ListModelList<String> newCrops = xmlHandler.getCropList();
        if (!oldCrops.equals(newCrops)){
            alert("Can't revert changes as a crop has been added or deleted during this session.");
            writeToLog("WebConfigViewModel.copyCurrentSettings()", "Can't revert changes as a crop has been added or deleted during this session.", username);
        } else {
            try {
                Runtime.getRuntime().exec(
                	String.format("mv %sgobii-web-tmp.xml %s", EnvUtil.getTempDir(), EnvUtil.getGobiiWebXmlFilename())
                	//"mv /usr/local/tomcat/temp/gobii-web-tmp.xml /data/gobii_bundle/config/gobii-web.xml"
                );
                xmlHandler.setPath(EnvUtil.getGobiiWebXmlFilename());
                serverHandler.executePostgresChange(oldPostgresName);
                alert("You have successfully reverted the changes made this session. The settings changed for the BackUp schedule and the Scheduler modifications have NOT been reverted.");
                writeToLog("WebConfigViewModel.copyCurrentSettings()", "You have successfully reverted the changes made this session. The settings changed for the BackUp schedule and the Scheduler modifications have NOT been reverted.", username);
            } catch (IOException e) {
                xmlHandler.setPath(EnvUtil.getGobiiWebXmlFilename());
                e.printStackTrace();
                writeToLog("WebConfigViewModel.copyCurrentSettings()", "Making temporary gobii-web.xml the main gobii-web.xml failed.", username);
            }
        }
    }


    /**
     * This function checks if keygen has been run for later ssh commands which are needed in the backend
     */
    private boolean keygen() {
    	
    	//check env for id rsa
    	String sshFile = "";
    	Boolean keygenSuccessful = false;
    	
    	try {
    		sshFile = System.getenv("SSH_PUBLIC_KEY_FILE");
    	} catch (SecurityException se) {
    		
    	}
    	
    	if (sshFile == "" || sshFile == null) {
			sshFile = "/home/gadm/.ssh/id_rsa.pub"; //TODO: remove this
		}
    	writeToLog("WebConfigViewModel.keygen()", String.format("Using file: %s",  sshFile), username);
 
        if (new File(sshFile).exists()) {
       
            keygenSuccessful = true;
        }
        writeToLog("WebConfigViewModel.keygen()", "The ssh Key has previously been set", username);
        
        return keygenSuccessful;
    }

    /**
     * This function controls the en/disable attribute of most textboxes and buttons within the module
     */
    @Command("enableEdit")
    @NotifyChange("documentLocked")
    public void enableEdit() {
        if (isSuperAdmin) {
            this.documentLocked = false;
            writeToLog("WebConfigViewModel.enableEdit()", "Document is unlocked.", username);
        } else {
            alert("Only Super Admins can configure these settings.");
            writeToLog("WebConfigViewModel.enableEdit()", "User is not SuperAdmin", username);
        }
    }

    /**
     * Creates a warning box for the user and reloads all Tomcat services upon acceptance, before routing the user to the homepage
     * @param binder
     */
    @Command("tomcatModification")
    public void tomcatModification(@ContextParam(ContextType.BINDER) Binder binder){
        if (
        	propertyHandler.getUsername() == null ||
        	propertyHandler.getUsername().trim().isEmpty() ||
        	propertyHandler.getPassword() == null ||
        	propertyHandler.getPassword().trim().isEmpty()
        ){
            writeToLog("ServerHandler.configureTomcatReloadRequest()", "Tomcat properties are not set.", username);
            alert("Please configure gobii-configurator.properties with correct credentials and redo the changes.");
            goToHome();
        } else {
            warningComposer.warningTomcat(binder, this, "WebConfigViewModel", null);
        }
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
            binder.sendCommand("disableEdit", null);
        } else {
            binder.sendCommand("disableEdit", null);
            alert("All CRON jobs have successfully modified and been transmitted to the server.");
            writeToLog("WebConfigViewModel.reloadCrons()", "All CRON jobs have successfully modified and been transmitted to the server.", username);
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
            writeToLog("WebConfigViewModel.saveBackup()", generateAlertMessage(backupHandler.getErrorMessages()), username);
        } else {
            if (!backupHandler.saveDataToCrons(xmlHandler.getHostForReload())){
                alert(generateAlertMessage(backupHandler.getErrorMessages()));
                writeToLog("WebConfigViewModel.saveBackup()", generateAlertMessage(backupHandler.getErrorMessages()), username);
            } else {
                alert("Backups preferences have been modified and saved.");
                writeToLog("WebConfigViewModel.saveBackup()", "Backups preferences have been modified and saved.", username);
            }
            goToHome();
        }
    }

    @Command("exportXML")
    public void exportXML(@ContextParam(ContextType.BINDER) Binder binder){
        try {
            InputStream is = new FileInputStream(EnvUtil.getGobiiWebXmlFilename());
            Filedownload.save(is, "text/xml", "gobii-web.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            alert("File not found.");
            writeToLog("WebConfigViewModel.exportXML()", "File for download not found.", username);
            binder.sendCommand("disableEdit", null);
            return;
        }
        alert("gobii-web.xml exported successfully.");
        writeToLog("WebConfigViewModel.exportXML()", "gobii-web.xml exported successfully.", username);
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
            writeToLog("WebConfigViewModel.createTmpCopyOfUpload()", "Successfully created a temporary copy of the imported file.", username);
            return xml.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            writeToLog("WebConfigViewModel.createTmpCopyOfUpload()", "File for import not found.", username);
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
            writeToLog("WebConfigViewModel.selectFileForImport()", "File for import was not found.", username);
            return;
        }
        locationSet = true;
        writeToLog("WebConfigViewModel.selectFileForImport()", "Location of import file successfully set.", username);
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
            writeToLog("WebConfigViewModel.importXML()", "Scp to server failed.", username);
            return;
        }
        writeToLog("WebConfigViewModel.importXML()", "Imported file successfully sent to server.", username);
        xmlHandler.setPath("/data/gobii_bundle/config/gobii-web-tmp.xml");
        XmlValidator validator = new XmlValidator(xmlHandler, username);
        if (validator.validateGobiiConfiguration()){
            params = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), "passed"));
            writeToLog("WebConfigViewModel.importXML()", "Imported file is valid.", username);
            if (scriptExecutor("importExportXml.sh", params)){
                String oldPGname = xmlHandler.getPostgresUserName();
                xmlHandler.setPath(EnvUtil.getGobiiWebXmlFilename());
                warningComposer.warningTomcat(binder, this, "WebConfigViewModel.importXML()", "Imported XML is now being used.");
                serverHandler.executePostgresChange(oldPGname);
            } else {
                binder.sendCommand("disableEdit",null);
                writeToLog("WebConfigViewModel.importXML()", "After successful validation script for renaming failed.", username);
            }
        } else {
            xmlHandler.setPath(EnvUtil.getGobiiWebXmlFilename());
            params = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), "failed"));
            if (!scriptExecutor("importExportXml.sh", params)){
                alert(validator.getErrorMessage() + "\nCouldn't remove the imported file from server.");
                writeToLog("WebConfigViewModel.importXML()", "After failing validation removing user imported file failed.", username);
            } else {
                alert(validator.getErrorMessage());
                writeToLog("WebConfigViewModel.importXML()", "Validation of the XML failed.", username);
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
     * If ANY step EXCEPT the CRON creation fails the entire operation will be aborted and reverted to the previous state.
     */
    @Command("addCropToDatabase")
    public void addCropToDatabase(@ContextParam(ContextType.BINDER) Binder binder){
        ArrayList<String>  contacts = new ArrayList<String>();
        List<String> currentCrops = xmlHandler.getCropList();
        if (currentCrops.contains(currentCrop.getName())){
            alert("This crop already has a database associated with it. Please choose another crop.");
            writeToLog("WebConfigViewModel.addCropToDatabase()", "This crop already has a database associated with it. Please choose another crop.", username);
            return;
        }
        if (currentCrop.getContactData() != null){
            contacts = readInContactData();
            writeToLog("WebConfigViewModel.addCropToDatabase()", "The contact data for the crop " + currentCrop.getName() + " has successfully been read in.", username);
//            alert("Please upload a contact data file, otherwise the database cannot be created.");
//            binder.sendCommand("disableEdit",null);
//            writeToLog("WebConfigViewModel.addCropToDatabase()", "Please upload a contact data file, otherwise the database cannot be created.", username);
//            return;
        }
        int seedData = serverHandler.postgresAddCrop(currentCrop, contacts, firstUpload);
        if (seedData == 1){ //Liquibase failed => Delete Crop, to not leave in an inconsistent state
            serverHandler.postgresRemoveCrop(currentCrop.getName(), currentCrop.getDatabaseName());
            binder.sendCommand("disableEdit", null);
            writeToLog("WebConfigViewModel.addCropToDatabase()", "Liquibase failed and database of the new crop was removed.", username);
            return;
        } else if (seedData == -1){
            //Remember if upload was tried already
            firstUpload = false;
            writeToLog("WebConfigViewModel.addCropToDatabase()", "Contact Data was malformed.", username);
            return;
        }
        writeToLog("WebConfigViewModel.addCropToDatabase()", "The database for the crop " + currentCrop.getName() + " has successfully been created and populated.", username);
        firstUpload = true;
        xmlCropHandler.appendCrop(currentCrop);
        writeToLog("WebConfigViewModel.addCropToDatabase()", "The crop " + currentCrop.getName() + " has successfully been added to the XML.", username);
        List<String> createFiles = new ArrayList<>(Arrays.asList(serverInfo.getHost(), currentCrop.getName(), "1" , String.valueOf(xmlHandler.getCropList().get(0))));
        if (!scriptExecutor("cropFileManagement.sh", createFiles)){
            xmlCropHandler.removeCrop(currentCrop);
            serverHandler.postgresRemoveCrop(currentCrop.getName(), currentCrop.getDatabaseName());
            binder.sendCommand("disableEdit",null);
            writeToLog("WebConfigViewModel.addCropToDatabase()", "Creating the file structure for the new crop failed, database creation has been reverted.", username);
            return;
        }
        writeToLog("WebConfigViewModel.addCropToDatabase()", "The files for the crop " + currentCrop.getName() + " have successfully been created.", username);
        String oldWar = xmlHandler.getContextPathNodes().item(0).getTextContent();
        List<String> duplicateWAR = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), "1" , currentCrop.getWARName(), oldWar));
        if (!scriptExecutor("WARHandler.sh", duplicateWAR)){
            xmlCropHandler.removeCrop(currentCrop);
            List<String> removeBundle = new ArrayList<>(Arrays.asList(serverInfo.getHost(), currentCrop.getName(), "0", String.valueOf(xmlHandler.getCropList().get(0))));
            scriptExecutor("cropFileManagement.sh", removeBundle);
            serverHandler.postgresRemoveCrop(currentCrop.getName(), currentCrop.getDatabaseName());
            binder.sendCommand("disableEdit",null);
            writeToLog("WebConfigViewModel.addCropToDatabase()", "Copying the WAR file failed, all changes have been reverted.", username);
            return;
        }
        writeToLog("WebConfigViewModel.addCropToDatabase()", "The crop " + currentCrop.getName() + "war file was successfully copied and deployed.", username);
        cronHandler.modifyCron("create", xmlHandler.getHostForReload(), currentCrop);
        currentCrop.setHideContactData(true);
        
        //Add crop to xml file
        List<String> updatePortal = new ArrayList<>(Arrays.asList(currentCrop.getName(), serverInfo.getHost()));
        if (!scriptExecutor("updateGobiiPortal.sh", updatePortal)){
            writeToLog("WebConfigViewModel.addCropToDatabase()", "Updating the PORTAL xml file failed.", username);
            return;
        }
        
        alert("You have successfully created a new crop " + currentCrop.getName());
        writeToLog("WebConfigViewModel.addCropToDatabase()", "You have successfully created a new crop " + currentCrop.getName(), username);
        binder.sendCommand("disableEdit", null);
        goToHome();
    }


    /**
     * Reads in the contact data from a user provided file path and adds each line to an ArrayList as a String for later processing
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
            writeToLog("WebConfigViewModel.readInContactData()", "The file with the contact data could not be found.", username);
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
    @Command("modifyCropByRename")
    public void modifyCropByRename (@ContextParam(ContextType.BINDER) Binder binder){

        List<String> currentCrops = xmlHandler.getCropList();
        if (currentCrops.contains(currentCrop.getRename())){
            alert("The name "+ currentCrop.getRename() +" already has a database associated with it. Please choose a different crop name.");
            writeToLog("WebConfigViewModel.modifyCropByRename()", "This crop already has a database associated with it. Please choose another crop.", username);
            return;
        }

        warningComposer.warningRename(binder, this);
       
    }

    /**
     * Removes the chosen crop from the database and all the associated locations (XML, Tomcat, CRON)
     * The Crop is chosen by dropdown menu
     */
    @NotifyChange("cropList")
    @Command("removeCropFromDatabase")
    public void removeCropFromDatabase (@ContextParam(ContextType.BINDER) Binder binder){
        if (!currentCrop.getName().equals(currentCrop.getTypedName())){
            alert("The typed name does not match the selection made. The database was not removed. Please try again.");
            writeToLog("WebConfigViewModel.removeCropFromDatabase()", "The crop name for removal was not typed correctly.", username);
            return;
        }
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
        writeToLog("xmlHandler.getWARName(currentCrop.getName())", "xmlHandler.getWARName(currentCrop.getName()) " + xmlHandler.getWARName(currentCrop.getName()) + ".", username);
        
        if (!serverHandler.undeployFromTomcat(currentCrop)){
            alert("Couldn't undeploy " + xmlHandler.getWebContextPath(currentCrop.getName()) + ", undeploying forcibly instead.");
            writeToLog("WebConfigViewModel.executeRemoval()", "Undeployment of the war file failed.", username);
        } else {
            List<String> removeWAR = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), "0", xmlHandler.getWARName(currentCrop.getName())));
            if (!scriptExecutor("WARHandler.sh", removeWAR)) {
                binder.sendCommand("disableEdit", null);
                writeToLog("WebConfigViewModel.executeRemoval()", "Removal of the .war file failed.", username);
                return;
            }
        }
        writeToLog("WebConfigViewModel.executeRemoval()", "The crop " + currentCrop.getName() + " .war file has been removed.", username);
        if (!serverHandler.postgresRemoveCrop(currentCrop.getName(), currentCrop.getDatabaseName())) {
            writeToLog("WebConfigViewModel.executeRemoval()", "Removal of the database was unsuccessful.", username);
            return;
        }
        writeToLog("WebConfigViewModel.executeRemoval()", "The database for the crop " + currentCrop.getName() + " has been removed.", username);
        
        xmlCropHandler.removeCrop(currentCrop);
        writeToLog("WebConfigViewModel.executeRemoval()", "The crop " + currentCrop.getName() + " has been removed from the XML.", username);
        List<String> removeBundle = new ArrayList<>(Arrays.asList(serverInfo.getHost(), currentCrop.getName(), "0", String.valueOf(xmlHandler.getCropList().get(0))));
        if (!scriptExecutor("cropFileManagement.sh", removeBundle)) {
            binder.sendCommand("disableEdit", null);
            writeToLog("WebConfigViewModel.executeRemoval()", "Removing the crop files failed.", username);
            return;
        }
        
        cronHandler.modifyCron("delete", xmlHandler.getHostForReload(), currentCrop);
        binder.sendCommand("disableEdit", null);
        
        //Remove crop from xml file
        List<String> updatePortal = new ArrayList<>(Arrays.asList(currentCrop.getName(), serverInfo.getHost()));
        if (!scriptExecutor("removeCropFromGobiiPortal.sh", updatePortal)){
            writeToLog("WebConfigViewModel.removeCropFromDatabase()", "Updating the PORTAL xml file failed.", username);
            return;
        }
        writeToLog("WebConfigViewModel.executeRemoval()", "The crop " + currentCrop.getName() + " has been removed.", username);
        goToHome();
    }

    /**
     * Performs the actual rename of database.
     * This function should never be called by hand and instead always be called by the warning form WarningHandler
     * The function drops the database, renames the .war, renmaes the gobii_bundle directory and subdirectory,
     * renames the CRON tasks and renames the crop from the XML.
     * @param binder
     */
    public void executeRename(@ContextParam(ContextType.BINDER) Binder binder){
        if (!serverHandler.undeployFromTomcat(currentCrop)){
            alert("Couldn't undeploy " + xmlHandler.getWebContextPath(currentCrop.getName()) + ".\nPlease make sure that there are no active sessions before trying again.");
            writeToLog("WebConfigViewModel.executeRename()", "Undeployment of the war file failed.", username);
            return;
        }else {
            List<String> renameWAR = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), "2", xmlHandler.getWARName(currentCrop.getName()), "gobii-" + currentCrop.getRename()));
            if (!scriptExecutor("WARHandler.sh", renameWAR)) {
                binder.sendCommand("disableEdit", null);
                writeToLog("WebConfigViewModel.executeRename()", "Renaming of the .war file failed.", username);
                return;
            }
            writeToLog("WebConfigViewModel.executeRename()", "The crop " + currentCrop.getName() + " .war file has been renamed.", username);
        }
        
        if (!serverHandler.postgresRenameCropDb(currentCrop.getName(), currentCrop.getDatabaseName(), currentCrop.getRename())) {
            writeToLog("WebConfigViewModel.executeRename()", "Renaming of the database was unsuccessful.", username);
            return;
        }
        writeToLog("WebConfigViewModel.executeRename()", "The database for the crop " + currentCrop.getName() + " has been renamed.", username);

        if (!xmlCropHandler.renameCrop(currentCrop, currentCrop.getRename())){
            writeToLog("WebConfigViewModel.executeRename()", "Renaming of the crop in the GOBII-WEB XML was unsuccessful.", username);
            return;
        }
        writeToLog("WebConfigViewModel.executeRename()", "The crop " + currentCrop.getName() + " has been renamed in the GOBII-WEB XML.", username);
   
      
        List<String> renameBundle = new ArrayList<>(Arrays.asList(serverInfo.getHost(), currentCrop.getName(), "2", currentCrop.getRename()));
        if (!scriptExecutor("cropFileManagement.sh", renameBundle)) {
            binder.sendCommand("disableEdit", null);
            writeToLog("WebConfigViewModel.executeRename()", "Renaming the crop files failed.", username);
            return;
        }
        cronHandler.modifyCron("rename", xmlHandler.getHostForReload(), currentCrop);
        binder.sendCommand("disableEdit", null);
        
        //Remove old crop name from xml file
        List<String> editPortal = new ArrayList<>(Arrays.asList(currentCrop.getName(), serverInfo.getHost()));
        if (!scriptExecutor("removeCropFromGobiiPortal.sh", editPortal)){
            writeToLog("WebConfigViewModel.executeRename()", "Updating the PORTAL xml file failed.", username);
            return;
        }
        
        //Add new crop name to xml file
        List<String> updatePortal = new ArrayList<>(Arrays.asList(currentCrop.getRename(), serverInfo.getHost()));
        if (!scriptExecutor("updateGobiiPortal.sh", updatePortal)){
            writeToLog("WebConfigViewModel.executeRename()", "Updating the PORTAL xml file failed.", username);
            return;
        }
        writeToLog("WebConfigViewModel.executeRename()", "The crop " + currentCrop.getName() + " has been renamed in the portal.", username);
        goToHome();
    }
    

    @Command("disableEdit")
    @NotifyChange("documentLocked")
    public void disableEdit () {
        this.documentLocked = true;
        writeToLog("WebConfigViewModel.disableEdit()", "The current settings page is now locked.", username);
    }

    @Command("cancelChanges")
    @NotifyChange("documentLocked")
    public void cancelChanges () {
        this.documentLocked = true;
        writeToLog("WebConfigViewModel.cancelChanges()", "The current settings page is now locked.", username);
    }

    @Command("saveConfigCreds")
    public void saveConfigCreds(@BindingParam("windowConfig") Window x){
        x.detach();
    }

    @Command("cancelConfigCreds")
    public void cancelConfigCreds(@BindingParam("windowConfig") Window x){
        x.detach();
    }

    @Command("setLocationForSeeddata")
    public void setLocationForSeeddata (@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event, @ContextParam(ContextType.BINDER) Binder binder) {
        currentCrop.setContactData(createTmpCopyOfUpload(event));
    }

    protected void goToHome () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/mainContent.zul");
        getPage().getDesktop().setBookmark("p_" + "home");
        writeToLog("WebConfigViewModel.goToHome()", "Navigated to home.", username);
    }

    //Switch src for tag with id = mainContent from current page to X, in this call X = postgreSystemUser.zul

    @Command("postgresSystemUser")
    public void postgresSystemUser () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/postgresSystemUser.zul");
        getPage().getDesktop().setBookmark("p_" + "postgresSystemUser");
        writeToLog("WebConfigViewModel.postgresSystemUser()", "Navigated to postgresSystemUser.", username);
    }

    @Command("ldapSystemUser")
    public void ldapSystemUser () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ldapSystemUser.zul");
        getPage().getDesktop().setBookmark("p_" + "ldapUserSystem");
        writeToLog("WebConfigViewModel.ldapSystemUser()", "Navigated to ldapSystemUser.", username);
    }

    @Command("ldapUnitUser")
    public void ldapUnitUser () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ldapUnitUser.zul");
        getPage().getDesktop().setBookmark("p_" + "ldapUnitUser");
        writeToLog("WebConfigViewModel.ldapUnitUser()", "Navigated to ldapUnitUser.", username);
    }

    @Command("emailNotifications")
    public void emailNotifications () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/emailNotifications.zul");
        getPage().getDesktop().setBookmark("p_" + "emailNotifications");
        writeToLog("WebConfigViewModel.emailNotifications()", "Navigated to emailNotifications.", username);
    }

    @Command("pushNotifications")
    public void pushNotifications () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/pushNotifications.zul");
        getPage().getDesktop().setBookmark("p_" + "pushNotifications");
        writeToLog("WebConfigViewModel.pushNotifications()", "Navigated to pushNotifications.", username);
    }

    @Command("addCrop")
    public void addCrop () {
        if(!keygen()) {
            
            //show error guide
            Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                    .iterator().next();
            include.setSrc("/generate_key_guide.zul");
           
            isKeySet = false;
            writeToLog("WebConfigViewModel.keygen()", "The ssh Key hasn't been set", username);
        }else {
            Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                    .iterator().next();
            include.setSrc("/addCrop.zul");
            getPage().getDesktop().setBookmark("p_" + "addCrop");
            writeToLog("WebConfigViewModel.addCrop()", "Navigated to addCrop.", username);
        }
    }

    @Command("deleteCrop")
    public void deleteCrop () {
        if(!keygen()) {
            //show error guide
            Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                    .iterator().next();
            include.setSrc("/generate_key_guide.zul");
           
            isKeySet = false;
            writeToLog("WebConfigViewModel.keygen()", "The ssh Key hasn't been set", username);
        }else {
            Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                    .iterator().next();   
            include.setSrc("/deleteCrop.zul");
            getPage().getDesktop().setBookmark("p_" + "deleteCrop");
            writeToLog("WebConfigViewModel.deleteCrop()", "Navigated to deleteCrop.", username);
        }
    }

    @Command("modifyCrop")
    public void manageCrop () {
        if(!keygen()) {
            
            //show error guide
            Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                    .iterator().next();
            include.setSrc("/generate_key_guide.zul");
           
            isKeySet = false;
            writeToLog("WebConfigViewModel.keygen()", "The ssh Key hasn't been set", username);
        }else {
            Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                    .iterator().next();
            include.setSrc("/editCrop.zul");
            getPage().getDesktop().setBookmark("p_" + "modifyCrop");
            writeToLog("WebConfigViewModel.manageCrop()", "Navigated to manageCrop.", username);
        }
    }

    @Command("logSettings")
    public void logSettings () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/logSettings.zul");
        getPage().getDesktop().setBookmark("p_" + "logSettings");
        writeToLog("WebConfigViewModel.logSettings()", "Navigated to logSettings.", username);
    }

    @Command("linkageGroups")
    public void linkeageGroups () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/linkageGroups.zul");
        getPage().getDesktop().setBookmark("p_" + "linkageGroups");
        writeToLog("WebConfigViewModel.linkeageGroups()", "Navigated to linkeageGroups.", username);
    }

    @Command("markerGroups")
    public void markerGroups () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/markerGroups.zul");
        getPage().getDesktop().setBookmark("p_" + "markerGroups");
        writeToLog("WebConfigViewModel.markerGroups()", "Navigated to markerGroups.", username);
    }

    @Command("kdCompute")
    public void kdCompute () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/kdCompute.zul");
        getPage().getDesktop().setBookmark("p_" + "KDComputeIntegration");
        writeToLog("WebConfigViewModel.kdCompute()", "Navigated to kdCompute.", username);
    }

    @Command("ownCloud")
    public void ownCloud () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ownCloud.zul");
        getPage().getDesktop().setBookmark("p_" + "OwnCloud");
        writeToLog("WebConfigViewModel.ownCloud()", "Navigated to ownCloud.", username);
    }

    @Command("galaxy")
    public void galaxy () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/galaxy.zul");
        getPage().getDesktop().setBookmark("p_" + "Galaxy");
        writeToLog("WebConfigViewModel.galaxy()", "Navigated to galaxy.", username);
    }

    @Command("scheduler")
    public void scheduler () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/scheduler.zul");
        getPage().getDesktop().setBookmark("p_" + "Scheduler");
        writeToLog("WebConfigViewModel.scheduler()", "Navigated to scheduler.", username);
    }

    @Command("portConfig")
    public void portConfig () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/portConfig.zul");
        getPage().getDesktop().setBookmark("p_" + "PortConfiguration");
        writeToLog("WebConfigViewModel.portConfig()", "Navigated to portConfig.", username);
    }

    @Command("backup")
    public void backup () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/backup.zul");
        getPage().getDesktop().setBookmark("p_" + "backup");
        writeToLog("WebConfigViewModel.backup()", "Navigated to backup.", username);
    }

    @Command("import")
    public void import_settings () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/import.zul");
        getPage().getDesktop().setBookmark("p_" + "import");
        writeToLog("WebConfigViewModel.import_settings()", "Navigated to import_settings.", username);
    }

    @Command("export")
    public void export_settings () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/export.zul");
        getPage().getDesktop().setBookmark("p_" + "export");
        writeToLog("WebConfigViewModel.export_settings()", "Navigated to export_settings.", username);
    }

    @Command("revert")
    public void revert () {
        Include include = (Include) Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/revert.zul");
        getPage().getDesktop().setBookmark("p_" + "revert");
        writeToLog("WebConfigViewModel.revert()", "Navigated to revert.", username);
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

    public WarningComposer getWarningComposer(){
        return warningComposer;
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

