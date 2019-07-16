package org.gobiiproject.datatimescope.webconfigurator;

import org.apache.catalina.ant.ReloadTask;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.scriptExecutor;
import static org.zkoss.zk.ui.util.Clients.alert;
import org.gobiiproject.datatimescope.db.generated.Routines;
import org.zkoss.util.Pair;

public class ServerHandler {

    private XmlModifier xmlHandler;
    private ReloadTask reloadRequest = new ReloadTask();
    private PropertyHandler prop = new PropertyHandler();

    public ServerHandler (XmlModifier xmlHandler){
        this.xmlHandler = xmlHandler;
        configureTomcatReloadRequest();
    }


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
            //Connecting to the database created a race condition, through trial and error I found 6 seconds to be the lower
            //bound by seconds so that the program runs correctly.
            try {
                TimeUnit.SECONDS.sleep(6);
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

    private Pair<Integer, java.sql.Date> populateTimescopeArray(DSLContext context){
        java.util.Date utilDat = new java.util.Date();
        Integer gdmSuperID = (Integer) context.fetch("select contact_id from contact where username = 'gadm';").get(0).getValue(0);
        return new Pair<>(gdmSuperID, new java.sql.Date(utilDat.getTime()));
    }

    private boolean populateSeedData(DSLContext context, Crop currentCrop){
        boolean success = true;
        try {
            context.fetch("CREATE DATABASE " + currentCrop.getDatabaseName() + " WITH OWNER '" + xmlHandler.getPostgresUserName() + "';");
        } catch (Exception e){
            alert("This database name is already in use or the name is using invalid characters. Please choose another name.");
            success = false;
        }
        List<String> populate = new ArrayList<>(Arrays.asList(xmlHandler.getPostgresUserName(), xmlHandler.getPostgresPassword(), xmlHandler.getPostgresHost(), xmlHandler.getPostgresPort(),  currentCrop.getDatabaseName()));
        if (!scriptExecutor("liquibase.sh", populate)){
            alert("Couldn't populate the database with seed data.");
            success = false;
        }
        return success;
    }

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
                e.printStackTrace();
                alert("Something went wrong when filling in the contact data.");
                return success;
            }
        }
        success = 0;
        return success;
    }
}
