package org.gobiiproject.datatimescope.webconfigurator;

import org.apache.catalina.ant.ReloadTask;
import org.apache.catalina.ant.UndeployTask;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.jooq.DSLContext;
import org.w3c.dom.NodeList;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.scriptExecutor;
import static org.zkoss.zk.ui.util.Clients.alert;
import org.gobiiproject.datatimescope.db.generated.Routines;
import org.zkoss.util.Pair;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

/**
 * A class designed to execute the operations on both Postgres and Tomcat
 * These functions are generally either called directly by WebConfigViewModel or after a warning box was accepted for a critical operation
 * Generally this class funtions as a layer of abstraction between the functions and the server operations in the background
 */

public class ServerHandler {

    private XmlModifier xmlHandler;
    private ReloadTask reloadRequest = new ReloadTask();
    private PropertyHandler prop = new PropertyHandler();

    public ServerHandler (XmlModifier xmlHandler){
        this.xmlHandler = xmlHandler;
        configureTomcatReloadRequest();
    }


    /**
     * Modify Postgres User Credentials
     * @param oldUserName
     */
    public void executePostgresChange(String oldUserName){
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
        reloadRequest.setUsername(prop.getUsername());
        reloadRequest.setPassword(prop.getPassword());
        while (prop.getUsername() == null || prop.getPassword() == null){
            alert("Please configure gobii-configurator.properties with correct credentials for the changes to be able to take place.");
            Window window = (Window)Executions.createComponents("/setConfigCreds.zul", null, null);
            window.doModal();
        }
    }


    /**
     * Sends a single requests to reload the web application under the found context path for the crop in the gobii-web.xml file
     */
    public void executeSingleTomcatReloadRequest(String cropname) {
        String host = xmlHandler.getHostForReload();
        String port = xmlHandler.getPortForReload();
        reloadRequest.setPath(xmlHandler.getWARName(cropname));
        reloadRequest.setUrl("http://" + host + ":" + port + "/manager/text");
        reloadRequest.execute();
    }

    /**
     * Sends the requests to reload the web application under the found context paths in the gobii-web.xml file
     */
    public void executeAllTomcatReloadRequest() {
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

    /**
     * Sends the request to undeploy the web application under the found context path in the gobii-web.xml file
     */
    public boolean undeployFromTomcat(Crop currCrop) {
        boolean success = true;
        try {
            UndeployTask undeploy = new UndeployTask();
            undeploy.setUsername(prop.getUsername());
            undeploy.setPassword(prop.getPassword());
            String host = xmlHandler.getHostForReload();
            String port = xmlHandler.getPortForReload();
            undeploy.setPath(xmlHandler.getWebContextPath(currCrop.getName()));
            undeploy.setUrl("http://" + host + ":" + port + "/manager/text");
            undeploy.execute();
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    /**
     * Removes the database of the given crop
     * @param cropName
     * @return true upon success
     */
    public boolean postgresRemoveCrop(String cropName){
        boolean success = true;
        ViewModelServiceImpl tmpService = new ViewModelServiceImpl();
        DSLContext context = tmpService.getDSLContext();
        try {
            context.fetch("DROP DATABASE " + xmlHandler.getDatabaseName(cropName) + ";");
        } catch (Exception e) {
            alert("The database could not be remove. Stacktrace of the error: \n" + e.toString());
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    /**
     * Create a database for the current Crop and populate it with basic seed data and contact data
     * The ViewModel operations enable relative smooth switching of databases for the queries
     * @param currentCrop
     * @param contactData
     * @param firstUpload A flag that allows users to retry filling the contact data if it was invalid on the first try
     * @return
     *  1, if the liquibase population of basic seed data failed
     * -1, if the provided contact data had an error
     *  0, upon complete success
     */
    public int postgresAddCrop(Crop currentCrop, ArrayList<String> contactData, boolean firstUpload){
        int success;
        if (firstUpload) {
            ViewModelServiceImpl tmpService = new ViewModelServiceImpl();
            ServerInfo tmpInfo = new ServerInfo(xmlHandler.getPostgresHost(), xmlHandler.getPostgresPort(), "gobii_dev",
                    xmlHandler.getPostgresUserName(), xmlHandler.getPostgresPassword());
            tmpService.connectToDB(tmpInfo.getUserName(), tmpInfo.getPassword(), tmpInfo);
            DSLContext context = tmpService.getDSLContext();
            if (!populateSeedData(context, currentCrop)) {
                success = 1;
                return success;
            }
            //Connecting to the database created a race condition, through trial and error I found 7 seconds to be the lower
            //bound by seconds so that the program runs correctly.
            try {
                TimeUnit.SECONDS.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ServerInfo info = new ServerInfo(xmlHandler.getPostgresHost(), xmlHandler.getPostgresPort(), currentCrop.getDatabaseName(),
                xmlHandler.getPostgresUserName(), xmlHandler.getPostgresPassword());
        ViewModelServiceImpl newService = new ViewModelServiceImpl();
        newService.connectToDB(info.getUserName(), info.getPassword(), info);
        DSLContext context = newService.getDSLContext();
        Pair<Integer, java.sql.Date> timescopeData = populateTimescopeArray(context);
        success = populateContactData(context, contactData, timescopeData);
        info = new ServerInfo(xmlHandler.getPostgresHost(), xmlHandler.getPostgresPort(), "gobii_dev",
                xmlHandler.getPostgresUserName(), xmlHandler.getPostgresPassword());
        newService.connectToDB(info.getUserName(), info.getPassword(), info);
        return success;
    }

    /**
     * get the gadm user ID as a query from the current database paired with the current date
     * @param context
     * @return
     */
    private Pair<Integer, java.sql.Date> populateTimescopeArray(DSLContext context){
        java.util.Date utilDat = new java.util.Date();
        Integer gdmSuperID = (Integer) context.fetch("select contact_id from contact where username = 'gadm';").get(0).getValue(0);
        return new Pair<>(gdmSuperID, new java.sql.Date(utilDat.getTime()));
    }

    /**
     * Create the new database and populate it with basic seed data as described here:
     * http://cbsugobii05.biohpc.cornell.edu:6084/pages/viewpage.action?pageId=7833278
     * @param context
     * @param currentCrop
     * @return
     */
    private boolean populateSeedData(DSLContext context, Crop currentCrop){
        boolean success = true;
        try {
            context.fetch("CREATE DATABASE " + currentCrop.getDatabaseName() + " WITH OWNER '" + xmlHandler.getPostgresUserName() + "';");
        } catch (Exception e){
            alert("The database couldn't be created due to following error: \n" + e.toString());
            success = false;
        }
        List<String> populate = new ArrayList<>(Arrays.asList(xmlHandler.getHostForReload(), xmlHandler.getPostgresUserName(), xmlHandler.getPostgresPassword(), xmlHandler.getPostgresHost(), xmlHandler.getPostgresPort(),  currentCrop.getDatabaseName()));
        if (!scriptExecutor("liquibase.sh", populate)){
            success = false;
        }
        return success;
    }

    /**
     * Fill the contact table as specified:
     * https://gobiiproject.atlassian.net/browse/I19-53
     * Validation is done by querying the information directly from the database
     * @param context
     * @param contactData
     * @param timescopeData
     * @return
     * -1 upon failure
     *  0 upon success
     */
    private int populateContactData(DSLContext context, ArrayList<String> contactData, Pair<Integer, Date> timescopeData){
        int success = -1;
        for (String rawData : contactData) {
            String[] splitData = rawData.split("\t");
            String stripped = splitData[4].replace(" ","");
            String[] splitRoles = stripped.split(",");
            ArrayList<Integer> tmpRoleID = new ArrayList<>();
            String lastRole = null;
            try {
                for (String role : splitRoles){
                    lastRole = role;
                    tmpRoleID.add((Integer) context.fetch("select role_id from role where role_name = '" + role + "';").getValue(0,0));
                }
            } catch (IndexOutOfBoundsException e){
                alert("The role '" + lastRole + "' is not a valid role. Please verify the spelling and make sure that the uploaded file follows the expected tab-delimited format.");
                return success;
            }
            Integer[] roleID = tmpRoleID.toArray(Integer[]::new);
            Integer organizationID;
            try {
                organizationID = (Integer) context.fetch("select organization_id from organization where name = '" + splitData[5] + "';").getValue(0, 0);
            } catch (Exception e){
                alert("The organization '" + splitData[5] + "' is not a valid organization. Please verify the spelling and make sure that the uploaded file follows the expected tab-delimited format.");
                return success;
            }
            try {
                Routines.createcontact(context.configuration(), splitData[0], splitData[1], splitData[2], splitData[3], roleID,
                        timescopeData.getX(), timescopeData.getY(), timescopeData.getX(), timescopeData.getY(), organizationID, splitData[6]
                );
            } catch (Exception e) {
                alert("Contact data could not be filled in correctly, with following error: \n" + e.toString());
                return success;
            }
        }
        success = 0;
        return success;
    }
}
