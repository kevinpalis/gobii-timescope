package org.gobiiproject.datatimescope.webconfigurator;

import org.apache.catalina.ant.ReloadTask;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.jooq.DSLContext;
import org.w3c.dom.NodeList;

import java.io.IOException;

import static org.gobiiproject.datatimescope.webconfigurator.utilityFunctions.generateAlertMessage;
import static org.zkoss.zk.ui.util.Clients.alert;

public class ServerHandler {

    private XmlModifier xmlHandler;
    private ReloadTask reloadRequest = new ReloadTask();
    private PropertyHandler prop = new PropertyHandler();
    private WarningComposer warning;

    public ServerHandler (XmlModifier xmlHandler){
        this.xmlHandler = xmlHandler;
        this.warning = new WarningComposer(this.xmlHandler);
        configureTomcatReloadRequest();
    }

    public boolean changePostgresCredentials(String oldUsername){
        boolean success;
        if (warning.isAcceptedWarning()){
            executePostgresChange(oldUsername);
            executeAllTomcatReloadRequest();
            success = true;
        } else {
            success = false;
        }
        return success;
    }

    public boolean reloadTomcatSingleCrop(String cropname){
        boolean success;
        warning.warningTomcat();
        if (warning.isErrorFlag()){
            alert(generateAlertMessage(warning.getErrorMessages()));
            success = false;
        } else if (warning.isAcceptedWarning()){
            executeSingleTomcatReloadRequest(cropname);
            success = true;
        } else {
            success = false;
        }
        return success;
    }


    public boolean reloadTomcatAllCrops(){
        boolean success;
        warning.warningTomcat();
        if (warning.isErrorFlag()){
            alert(generateAlertMessage(warning.getErrorMessages()));
            success = false;
        } else if (warning.isAcceptedWarning()){
            executeAllTomcatReloadRequest();
            success = true;
        } else {
            success = false;
        }
        return success;
    }


    private void executePostgresChange(String oldUserName){
        ViewModelServiceImpl tmpService = new ViewModelServiceImpl();
        DSLContext context = tmpService.getDSLContext();
        if (!oldUserName.equals(xmlHandler.getPostgresUserName())){
            context.fetch("ALTER USER " + oldUserName + " RENAME to " + xmlHandler.getPostgresUserName() + ";");
        }
        context.fetch("ALTER USER " + xmlHandler.getPostgresUserName() + " PASSWORD '" + xmlHandler.getPostgresPassword() + "';");
        context.fetch("ALTER USER " + xmlHandler.getPostgresUserName() + " VALID UNTIL 'infinity';");
    }

    /**
     * Sets username and password needed for the reload reloadRequest for the web application specified under context path in the gobii-web.xml
     * @return true if username and password are filled
     */
    private void configureTomcatReloadRequest(){
        try {
            reloadRequest.setUsername(prop.getUsername());
            reloadRequest.setPassword(prop.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (reloadRequest.getUsername() == null || reloadRequest.getPassword() == null) {
            alert("Please configure gobii-configurator.properties with correct credentials for the changes to be able to take place." +
                    "The modifications are staged and will take effect when the web application is restarted.");
            //TODO Basically block this module until anything is filled in
        }
    }


    /**
     * Sends a single requests to reload the web application under the found context path for the crop in the gobii-web.xml file
     */
    private void executeSingleTomcatReloadRequest(String cropname) {
        String host = xmlHandler.getHostForReload();
        String port = xmlHandler.getPortForReload();
        reloadRequest.setPath(xmlHandler.getWARName(cropname));
        reloadRequest.setUrl("http://" + host + ":" + port + "/manager/text");
        reloadRequest.execute();
    }

    /**
     * Sends the requests to reload the web application under the found context paths in the gobii-web.xml file
     */
    private void executeAllTomcatReloadRequest() {
        String host = xmlHandler.getHostForReload();
        String port = xmlHandler.getPortForReload();
        NodeList contextPathNodes = xmlHandler.getContextPathNodes();
        //Reload each context
        for (int i = 0; i < contextPathNodes.getLength(); i++) {
            reloadRequest.setPath(contextPathNodes.item(i).getTextContent());
            reloadRequest.setUrl("http://" + host + ":" + port + "/manager/text");
            reloadRequest.execute();
        }
    }



}
