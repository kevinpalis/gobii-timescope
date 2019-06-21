package org.gobiiproject.datatimescope.controller;

import org.apache.catalina.ant.ReloadTask;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.webconfigurator.propertyHandler;
import org.gobiiproject.datatimescope.webconfigurator.xmlModifier;
import org.jooq.DSLContext;
import org.w3c.dom.NodeList;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebConfigViewModel extends SelectorComposer<Component> {

    private xmlModifier xmlHandler = new xmlModifier();
    private boolean documentLocked = true;
    private propertyHandler prop = new propertyHandler();
    private ReloadTask request = new ReloadTask();
    private boolean isSuperAdmin = false;

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
                public void onEvent(Event evt) throws InterruptedException {
                    if (evt.getName().equals("onOK")) {
                        binder.sendCommand("disableEdit", null);
                        executeTomcatReloadRequest();
                    }
                }
            });
        } else {
            binder.sendCommand("disableEdit", null);
        }
    }

    @Command("warningPostgres")
    public void warningPostgres(@ContextParam(ContextType.BINDER) Binder binder){
        Messagebox.Button[] buttons = new Messagebox.Button[]{Messagebox.Button.OK, Messagebox.Button.CANCEL};
        Map<String, String> params = new HashMap<>();
        params.put("width", "500");
        Messagebox.show("This operation requires a restart of your Postgres database. This has the potential to fail if there " +
                "active sessions, or worse, corrupt your data. \nPlease make sure that there are no active session prior to changing these settings. \nAre you sure" +
                " you want to restart Postgres now?", "Warning", buttons, null, Messagebox.EXCLAMATION, null, new org.zkoss.zk.ui.event.EventListener() {
            public void onEvent(Event evt) throws InterruptedException {
                if (evt.getName().equals("onOK")) {
                    String oldUserName = xmlHandler.getPostgresUserName();
                    binder.sendCommand("disableEdit", null);
                    executePostgresReload(oldUserName);
                }
            }
        }, params);
    }

    private void executePostgresReload(String oldUserName){
        ViewModelServiceImpl tmpService = new ViewModelServiceImpl();
        DSLContext context = tmpService.getDSLContext();
        context.fetch("ALTER USER " + oldUserName + " RENAME to " + xmlHandler.getPostgresUserName() + ";");
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
    private void executeTomcatReloadRequest(){
        String host = xmlHandler.getHostForReload();
        String port = xmlHandler.getPortForReload();
        NodeList contextPathNodes = xmlHandler.getContextPathNodes();
        //Reload each context
        for (int i = 0; i < contextPathNodes.getLength(); i++) {
            request.setPath(contextPathNodes.item(i).getTextContent());
            request.setUrl("http://" + host + ":" + port + "/manager/text");
            request.execute();
        }
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

    @Command("manageCrop")
    public void manageCrop() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/manageCrop.zul");
        getPage().getDesktop().setBookmark("p_"+"manageCrop");
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
}
