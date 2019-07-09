package org.gobiiproject.datatimescope.webconfigurator;

import org.apache.catalina.ant.ReloadTask;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.jooq.DSLContext;
import org.w3c.dom.NodeList;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;

import javax.swing.*;
import javax.swing.plaf.FileChooserUI;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebConfigViewModel extends SelectorComposer<Component> {

    private xmlModifier xmlHandler = new xmlModifier();
    private boolean documentLocked = true;
    private propertyHandler prop = new propertyHandler();
    private ReloadTask request = new ReloadTask();
    private boolean isSuperAdmin = false;
    private Crop currentCrop = new Crop();
    private String newXMLPath;
    private boolean showNewXml = false;
    private boolean locationSet = false;
    private String exportLocation = "";


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

    @Command("exportXML")
    public void exportXML( @ContextParam(ContextType.BINDER) Binder binder){
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
        String[] sec_copy = {"/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/import_export_xml.sh", "scpto" , newXMLPath};
        try {
            new ProcessBuilder(sec_copy).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xmlHandler.setPath("/data/gobii_bundle/config/gobii-web-tmp.xml");
        if (validateGobiiConfiguration()){
            String[] overwrite = {"/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/import_export_xml.sh", "passed"};
            try {
                new ProcessBuilder(overwrite).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            warningTomcat(binder);
        } else {
            xmlHandler.setPath("/data/gobii_bundle/config/gobii-web.xml");
            String[] delete = {"/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/import_export_xml.sh", "failed"};
            try {
                new ProcessBuilder(delete).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            binder.sendCommand("disableEdit", null);
        }
    }

    private boolean validateGeneralSettings(List<String> messages){
        boolean returnVal = true;
        if (isNullOrEmpty(xmlHandler.getEmailSvrDomain())) {
            messages.add("An email server host is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getEmailServerPort())) {
            messages.add("An email port is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getEmailSvrUser())) {
            messages.add("An email server user id is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getEmailSvrPassword())) {
            messages.add("An email server password is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getFileSystemRoot())) {
            messages.add("A file system root is not defined");
            returnVal = false;
        } else {
            File directoryToTest = new File(xmlHandler.getFileSystemRoot());
            if (!directoryToTest.exists() || !directoryToTest.isDirectory()) {
                messages.add("The specified file system root does not exist or is not a directory: " + xmlHandler.getFileSystemRoot());
                returnVal = false;
            }
        }
        if (isNullOrEmpty(xmlHandler.getGobiiAuthenticationType())) {
            messages.add("An authentication type is not specified");
            returnVal = false;
        }
        if (!xmlHandler.getGobiiAuthenticationType().equals("TEST")) {
            if (isNullOrEmpty(xmlHandler.getLdapUserDnPattern())) {
                messages.add("The authentication type is "
                        + xmlHandler.getGobiiAuthenticationType()
                        + " but a user dn pattern is not specified");
                returnVal = false;
            }
            if (isNullOrEmpty(xmlHandler.getLdapUrl())) {
                messages.add("The authentication type is "
                        + xmlHandler.getGobiiAuthenticationType()
                        + " but an ldap url is not specified");
                returnVal = false;
            }
            if (xmlHandler.getGobiiAuthenticationType().equals("LDAP_CONNECT_WITH_MANAGER") ||
                    xmlHandler.getGobiiAuthenticationType().equals("ACTIVE_DIRECTORY_CONNECT_WITH_MANAGER")) {
                if (isNullOrEmpty(xmlHandler.getLdapBindUser())) {
                    messages.add("The authentication type is "
                            + xmlHandler.getGobiiAuthenticationType()
                            + " but an ldap bind user is not specified");
                    returnVal = false;
                }
                if (isNullOrEmpty(xmlHandler.getLdapBindPassword())) {
                    messages.add("The authentication type is "
                            + xmlHandler.getGobiiAuthenticationType()
                            + " but an ldap bind password is not specified");
                    returnVal = false;
                }
            } // if the authentication type requires connection credentails
        } // if the authentication type requires url and user dn pattern
        if (isNullOrEmpty(xmlHandler.getFileSystemLog())) {
            messages.add("A file system log directory is not defined");
            returnVal = false;
        } else {
            File directoryToTest = new File(xmlHandler.getFileSystemLog());
            if (!directoryToTest.exists() || !directoryToTest.isDirectory()) {
                messages.add("The specified file system log does not exist or is not a directory: " + xmlHandler.getFileSystemLog());
                returnVal = false;
            }
        }
        if (isNullOrEmpty(xmlHandler.getFileSysCropsParent())) {
            messages.add("A file system crop parent directory is not defined");
            returnVal = false;
        } else {
            File directoryToTest = new File(xmlHandler.getFileSysCropsParent());
            if (!directoryToTest.exists() || !directoryToTest.isDirectory()) {
                messages.add("The specified file crop parent directory does not exist or is not a directory: " + xmlHandler.getFileSysCropsParent());
                returnVal = false;
            }
        }
        return returnVal;
    }

    private boolean validateTestCrop(List<String> messages){
        boolean returnVal = true;
        if (isNullOrEmpty(xmlHandler.getTestExecConfig())){
            messages.add("No test exec configuration is defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getTestCrop())) {
            messages.add("A test crop id is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getTestInitialConfigUrl())) {
            messages.add("An initial configuration url for testing is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getTestConfigFileTestDirectory())) {
            messages.add("A a directory for test files is not defined");
            returnVal = false;
        } else {
            String testDirectoryPath = xmlHandler.getTestConfigFileTestDirectory();
            File testFilePath = new File(xmlHandler.getTestConfigFileTestDirectory());
            if (!testFilePath.exists()) {
                messages.add("The specified test file path does not exist: "
                        + testDirectoryPath);
                returnVal = false;
            }
        }
        if (isNullOrEmpty(xmlHandler.getTestConfigUtilCommandLineStem())) {
            messages.add("The commandline stem of this utility for testing purposes is not defined");
            returnVal = false;
        }
        return returnVal;
    }

    private boolean validateWebServer(List<String> messages, List<String> contextPathList, String Cropname) {
        boolean returnVal = true;
        if (isNullOrEmpty(Cropname)) {
            messages.add("The crop type for the crop is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getWebHost(Cropname))) {
            messages.add("The web server host for the crop (" + Cropname + ") is not defined");
            returnVal = false;

        }
        if (isNullOrEmpty(xmlHandler.getWebContextPath(Cropname))) {
            messages.add("The web server context path for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        } else {
            if (!contextPathList.contains(xmlHandler.getWebContextPath(Cropname))) {
                contextPathList.add(xmlHandler.getWebContextPath(Cropname));
            } else {
                messages.add("The context path for the crop occurs more than once -- context paths must be unique:" + xmlHandler.getWebContextPath(Cropname));
                returnVal = false;
            }
        }
        if (isNullOrEmpty(xmlHandler.getWebPort(Cropname))) {
            messages.add("The web server port for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        }
        return returnVal;
    }

    private boolean validatePostgresServer(List<String> messages, List<String> databasesList, String Cropname){
        boolean returnVal = true;
        if (isNullOrEmpty(xmlHandler.getDatabaseHost(Cropname))) {
            messages.add("The postgres server host for the crop (" + Cropname + ") is not defined");
            returnVal = false;

        }
        if (isNullOrEmpty(xmlHandler.getDatabaseName(Cropname))) {
            messages.add("The postgres server context path for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        } else {
            if (!databasesList.contains(xmlHandler.getDatabaseName(Cropname))) {
                databasesList.add(xmlHandler.getDatabaseName(Cropname));
            } else {
                messages.add("The context path for the crop occurs more than once -- context paths must be unique:" + xmlHandler.getDatabaseName(Cropname));
                returnVal = false;
            }
        }
        if (xmlHandler.getDatabasePort(Cropname) == null) {
            messages.add("The postgres server port for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getDatabaseUser(Cropname))){
            messages.add("The postgres username for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getDatabasePassword(Cropname))){
            messages.add("The postgres password for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        }
        return returnVal;
    }

    private boolean validateServers(List<String> messages){
        boolean returnVal = true;
        List<String> contextPathList = new ArrayList<>();
        List<String> databasesList = new ArrayList<>();
        for (int i = 0; i < xmlHandler.getCropList().size(); i++) {
            String Cropname = String.valueOf(xmlHandler.getCropList().get(i));
            if (xmlHandler.getPostgres(Cropname) == null) {
                messages.add("The postgresdb for the crop (" + Cropname + ") is not defined");
                returnVal = false;
            } else if (xmlHandler.getWeb(Cropname) == null) {
                messages.add("The webConfig for the crop (" + Cropname + ") is not defined");
                returnVal = false;
            } else {
                returnVal = returnVal && validateWebServer(messages, contextPathList, Cropname) &&
                        validatePostgresServer(messages, databasesList, Cropname);
            }
        }
        return returnVal;
    }

    private boolean isNullOrEmpty(String value){
        return (null == value || value.isEmpty());
    }
    private boolean isNullOrEmpty(int value){
        return (value == 0);
    }

    private boolean validateGobiiConfiguration() {
        boolean returnVal;
        try {
            List<String> messages = new ArrayList<>();
            returnVal = validateGeneralSettings(messages) && validateTestCrop(messages) && validateServers(messages);
            if (xmlHandler.getCropList().size() < 1) {
                messages.add("No active crops are defined");
                returnVal = false;
            }
            if (!returnVal && messages.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (String s : messages)
                {
                    sb.append(s);
                    sb.append("\n");
                }
                alert("The provided xml was invalid.\n" + sb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            alert("A mandatory XML tag is missing.");
            returnVal = false;
        }
        return returnVal;

    }

    /**
     * Opens a warning box and if acknowledged performs the restart of the the application process.
     * Otherwise stages it for later and upon next restart the effects will take place
     * */
    @Command("warningTomcat")
    public void warningTomcat(@ContextParam(ContextType.BINDER) Binder binder) {
        if (configureTomcatReloadRequest()) {
            Messagebox.show("Clicking OK will restart the web application and all unsaved data will be lost.", "Warning", Messagebox.OK | Messagebox.CANCEL, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(Event evt) {
                    if (evt.getName().equals("onOK")) {
                        binder.sendCommand("disableEdit", null);
                        executeTomcatReloadRequest(true);
                    }
                }
            });
        } else {
            binder.sendCommand("disableEdit", null);
        }
    }

    @Command("warningPostgres")
    public void warningPostgres(@ContextParam(ContextType.BINDER) Binder binder){
        if (configureTomcatReloadRequest()) {
            Messagebox.Button[] buttons = new Messagebox.Button[]{Messagebox.Button.OK, Messagebox.Button.CANCEL};
            Map<String, String> params = new HashMap<>();
            params.put("width", "500");
            Messagebox.show("This operation requires a restart of your Postgres database. This has the potential to fail if there are " +
                    "active sessions, or worse, corrupt data being loaded. \nPlease make sure that there are no active session prior to changing these settings. \nAre you sure" +
                    " you want to restart Postgres now?", "Warning", buttons, null, Messagebox.EXCLAMATION, null, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(Event evt) {
                    if (evt.getName().equals("onOK")) {
                        String oldUserName = xmlHandler.getPostgresUserName();
                        binder.sendCommand("disableEdit", null);
                        executePostgresReload(oldUserName);
                        executeTomcatReloadRequest(true);
                    }
                }
            }, params);
        } else {
            binder.sendCommand("disableEdit", null);
        }
    }

    private void executePostgresReload(String oldUserName){
        ViewModelServiceImpl tmpService = new ViewModelServiceImpl();
        DSLContext context = tmpService.getDSLContext();
        if (!oldUserName.equals(xmlHandler.getPostgresUserName())){
            context.fetch("ALTER USER " + oldUserName + " RENAME to " + xmlHandler.getPostgresUserName() + ";");
        }
        context.fetch("ALTER USER " + xmlHandler.getPostgresUserName() + " PASSWORD '" + xmlHandler.getPostgresPassword() + "';");
        context.fetch("ALTER USER " + xmlHandler.getPostgresUserName() + " VALID UNTIL 'infinity';");
    }

    /**
     * Sets username and password needed for the reload request for the web application specified under context path in the gobii-web.xml
     * @return true if username and password are filled
     */
    private boolean configureTomcatReloadRequest(){
        try {
            request.setUsername(prop.getUsername());
            request.setPassword(prop.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (request.getUsername() == null || request.getPassword() == null) {
            alert("Please configure gobii-configurator.properties with correct credentials for the changes to be able to take place." +
                    "The modifications are staged and will take effect when the web application is restarted.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Sends the requests to reload the web application under the found context paths in the gobii-web.xml file
     */
    private void executeTomcatReloadRequest(boolean all){
        String host = xmlHandler.getHostForReload();
        String port = xmlHandler.getPortForReload();
        if (all) {
            NodeList contextPathNodes = xmlHandler.getContextPathNodes();
            //Reload each context
            for (int i = 0; i < contextPathNodes.getLength(); i++) {
                request.setPath(contextPathNodes.item(i).getTextContent());
                request.setUrl("http://" + host + ":" + port + "/manager/text");
                request.execute();
            }
        } else {
            request.setPath(xmlHandler.getWARName(currentCrop.getName()));
            request.setUrl("http://" + host + ":" + port + "/manager/text");
            request.execute();
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
        if (configureTomcatReloadRequest()){
            currentCrop.setWARName(xmlHandler.getWARName(currentCrop.getName()));
            binder.sendCommand("disableEdit", null);
            if (currentCrop.isActivityChanged()){
                currentCrop.setActivityChanged(false);
            } else {
                alert("No changes have been made, please change a setting.");
                return;
            }
            executeTomcatReloadRequest(false);
            if (currentCrop.getIsActive()) {
                modifyCron("create");
            } else {
                modifyCron("delete");
            }
        } else {
            binder.sendCommand("disableEdit", null);
        }

    }

    @Command("warningRemoval")
    public void warningRemoval(@ContextParam(ContextType.BINDER) Binder binder){
        Messagebox.Button[] buttons = new Messagebox.Button[]{Messagebox.Button.OK, Messagebox.Button.CANCEL};
        Map<String, String> params = new HashMap<>();
        params.put("width", "500");
        Messagebox.show("This operation will permanently remove this database and all its associated information." +
                "\nPlease make sure that there are no active sessions prior to removing this database. \nAre you sure" +
                " you want to remove the database now?", "Warning", buttons, null, Messagebox.EXCLAMATION, null, new org.zkoss.zk.ui.event.EventListener() {
            public void onEvent(Event evt) {
                if (evt.getName().equals("onOK")) {
                    if (xmlHandler.getCropList().size() > 1) {
                        binder.sendCommand("removeCropFromDatabase", null);
                        binder.sendCommand("disableEdit", null);
                    } else {
                        alert("This the only database. Please add another crop before deleting this database.");
                    }
                }
            }
        }, params);
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

    private void createCron(BufferedReader stdInput) throws IOException {
        ArrayList<String> newJobs = new ArrayList<>();
        String line = null;
        while ((line = stdInput.readLine()) != null) {
            if (line.equals("")){
                break;
            }
            newJobs.add(line);
        }
        newJobs.add("*/2 * * * * /data/gobii_bundle/loaders/cronjob.sh " + currentCrop.getName() + " +2");
        newJobs.add("*/2 * * * * /data/gobii_bundle/extractors/cronjob.sh " + currentCrop.getName() + " +2");
        FileWriter writer = new FileWriter("newCrons.txt");
        for(String str: newJobs) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }

    private void updateCron(BufferedReader stdInput) throws IOException {
        ArrayList<String> newJobs = new ArrayList<>();
        String line = null;
        while ((line = stdInput.readLine()) != null) {
            if (line.equals("")){
                break;
            }
            String [] input = line.split(" ");
            if (input[6].equals(currentCrop.getName())){
                input[7] = "+" + currentCrop.getFileAge();
                input[0] = "*/" + currentCrop.getCron();
                newJobs.add(String.join(" ", input));
            } else {
                newJobs.add(line);
            }
        }
        FileWriter writer = new FileWriter("newCrons.txt");
        for(String str: newJobs) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }

    private void deleteCron(BufferedReader stdInput) throws IOException {
        ArrayList<String> newJobs = new ArrayList<>();
        String line = null;
        while ((line = stdInput.readLine()) != null) {
            if (line.equals("")){
                break;
            }
            String [] input = line.split(" ");
            if (!input[6].equals(currentCrop.getName())) {
                newJobs.add(line);
            }
        }
        FileWriter writer = new FileWriter("newCrons.txt");
        for(String str: newJobs) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Wrapper that handles the different types the User can interact with CRON Jobs
     * Creating a new CRON with default values
     * Updating a CRON changing the values for a crops existing job
     * Deleting both CRONS for an existing crop
     * @param modification
     */
    private void modifyCron(String modification){
        String[] read = {
                "ssh",
                "gadm@cbsugobiixvm14.biohpc.cornell.edu",
                "docker exec gobii-compute-node bash -c 'crontab -u gadm -l'"
        };
        try {
            Process proc = new ProcessBuilder(read).start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            switch (modification) {
                case ("create"): {
                    createCron(stdInput);
                    break;
                }
                case ("update"): {
                    updateCron(stdInput);
                    break;
                }
                case ("delete"): {
                    deleteCron(stdInput);
                    break;
                }
            }
            Runtime.getRuntime().exec("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/dockerCopyCron.sh " + xmlHandler.getHostForReload());
        } catch (IOException e){
            e.printStackTrace();
        }
        /*
        TODO on deploy
        String dockerCopyCron = "/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/dockerCopyCron.sh " + xmlHandler.getHostForReload;

        new ProcessBuilder(dockerCopyCron).start();
        */
    }

    /**
     * Validation of the modifications made
     */
    @Command ("reloadCrons")
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

    public xmlModifier getXmlHandler(){
        return xmlHandler;
    }

    public boolean getdocumentLocked() {
        return documentLocked;
    }

    public propertyHandler getProp(){
        return prop;
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
}
