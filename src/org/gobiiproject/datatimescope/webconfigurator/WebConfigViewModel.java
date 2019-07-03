package org.gobiiproject.datatimescope.webconfigurator;

import org.apache.catalina.ant.ReloadTask;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.jooq.DSLContext;
import org.w3c.dom.NodeList;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;

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

    @Init
    public void init() {
        UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
        if (cre.getRole() == 1){
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
            request.setPath("/" + currentCrop.getWARName());
            request.setUrl("http://" + host + ":" + port + "/manager/text");
            request.execute();
        }
    }

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
        //TODO Validate Liquibase command
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
        String[] dupli = {"/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/WARHandler.sh", "1" , currentCrop.getWARName()};
        //TODO on Deploy
        //String[] dupli = {"/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/WARHandler.sh", "1" , currentCrop.getWARName()};
        try {
            new ProcessBuilder(dupli).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xmlHandler.appendCrop(currentCrop);
        /*
        Keeping this created a race condition
        if (configureTomcatReloadRequest()) {

            executeTomcatReloadRequest(false);
        } */
        currentCrop.setHideData(true);
        binder.sendCommand("disableEdit", null);
    }

    @Command("modifyCropActive")
    public void modifyCropActive(@ContextParam(ContextType.BINDER) Binder binder){
        if (configureTomcatReloadRequest()){
            xmlHandler.setActivity(currentCrop);
            executeTomcatReloadRequest(false);
            binder.sendCommand("disableEdit", null);
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
                    binder.sendCommand("removeCropFromDatabase", null);
                    binder.sendCommand("disableEdit", null);
                }
            }
        }, params);
    }


    @Command("removeCropFromDatabase")
    public void removeCropFromDatabase(){
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
    }

    @Command ("reloadCrons")
    public void reloadCrons(@ContextParam(ContextType.BINDER) Binder binder) throws IOException {
        if (currentCrop.getName() == null){
            alert("Please specify a crop.");
            binder.sendCommand("disableEdit", null);
            return;
        } else if (currentCrop.getFileAge() > 59 || currentCrop.getFileAge() < 1 || currentCrop.getCron() > 59 || currentCrop.getCron() < 1){
            alert("Please choose a valid value between 1 and 59. The default setting is 2.");
            binder.sendCommand("disableEdit", null);
            return;
        }
        String[] read = {
                "ssh",
                "gadm@cbsugobiixvm14.biohpc.cornell.edu",
                "docker exec gobii-compute-node bash -c 'crontab -u gadm -l'"
        };
        Process proc = new ProcessBuilder(read).start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
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
        /*TODO on deploy
        String dockerCopyCron = "/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/dockerCopyCron.sh";
        try {
            new ProcessBuilder(dockerCopyCron).start();
        } catch (Exception e){
            e.printStackTrace();
        }
        */
        Runtime.getRuntime().exec("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/dockerCopyCron.sh");
        binder.sendCommand("disableEdit", null);
    }


    @Command("disableEdit")
    @NotifyChange("documentLocked")
    public void disableEdit() {
        this.documentLocked = true;
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

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public Crop getCurrentCrop(){
        return currentCrop;
    }

    @Command("upload")
    public void uploaded(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event){
        currentCrop.setContactData(event.getMedia());
    }

}
