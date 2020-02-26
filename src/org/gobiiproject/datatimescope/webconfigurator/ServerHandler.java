package org.gobiiproject.datatimescope.webconfigurator;

//import org.apache.catalina.ant.ReloadTask;
//import org.apache.catalina.ant.UndeployTask;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.gobiiproject.datatimescope.utils.TomcatManagerUtil;
import org.gobiiproject.datatimescope.utils.WebappUtil;
import org.jooq.DSLContext;
import org.w3c.dom.NodeList;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.scriptExecutor;
import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.writeToLog;
import static org.zkoss.zk.ui.util.Clients.alert;
import org.gobiiproject.datatimescope.db.generated.Routines;
import org.zkoss.util.Pair;
import org.zkoss.zul.Messagebox;


/**
 * A class designed to execute the operations on both Postgres and Tomcat
 * These functions are generally either called directly by WebConfigViewModel or after a warning box was accepted for a critical operation
 * Generally this class functions as a layer of abstraction between the functions and the server operations in the background
 */

public class ServerHandler {

	private XmlModifier xmlHandler;
	//private ReloadTask reloadRequest = new ReloadTask();
	private PropertyHandler prop;
	private String username;

	public ServerHandler (XmlModifier xmlHandler, PropertyHandler prop){
		this.xmlHandler = xmlHandler;
		username = prop.getUsername();
		this.prop = prop;
		//configureTomcatReloadRequest();
	}

	

	/**
	 * Modify Postgres User Credentials
	 * @param oldUserName the old username which is still needed to transition to the new name
	 */
	public void executePostgresChange(String oldUserName){
		DSLContext context = WebappUtil.getDSLContext();
		if (!oldUserName.equals(xmlHandler.getPostgresUserName())){
			context.fetch("ALTER USER " + oldUserName + " RENAME to " + xmlHandler.getPostgresUserName() + ";");
			context.fetch("ALTER USER " + xmlHandler.getPostgresUserName() + " PASSWORD '" + xmlHandler.getPostgresPassword() + "';");
			context.fetch("ALTER USER " + xmlHandler.getPostgresUserName() + " VALID UNTIL 'infinity';");
			writeToLog("ServerHandler.executePostgresChange()", "You have successfully changed the username from " + oldUserName + " to " + xmlHandler.getPostgresUserName() + " and updated the password.", username);
			alert("You have successfully changed the username from " + oldUserName + " to " + xmlHandler.getPostgresUserName() + " and updated the password.");
		} else {
			context.fetch("ALTER USER " + xmlHandler.getPostgresUserName() + " PASSWORD '" + xmlHandler.getPostgresPassword() + "';");
			context.fetch("ALTER USER " + xmlHandler.getPostgresUserName() + " VALID UNTIL 'infinity';");
			alert("You have successfully changed the password for the account " + xmlHandler.getPostgresUserName() + ".");
			writeToLog("ServerHandler.executePostgresChange()", "You have successfully changed the password for the account " + xmlHandler.getPostgresUserName() + ".", username);
		}
	}

	/**
	 * Sets username and password needed for the reload reloadRequest for the web application specified under context path in the gobii-web.xml
	 * @return true if username and password are filled
	 */
	//private void configureTomcatReloadRequest(){
		//reloadRequest.setUsername(prop.getUsername());
		//reloadRequest.setPassword(prop.getPassword());
		//writeToLog("ServerHandler.configureTomcatReloadRequest()", "Tomcat properties are set.", username);
	//}


	/**
	 * Sends a single requests to reload the web application under the found context path for the crop in the gobii-web.xml file
	 */
	public void executeSingleTomcatReloadRequest(String cropname) {
		String host = xmlHandler.getHostForReload();
		String port = xmlHandler.getPortForReload();
		String webAppPath = xmlHandler.getWARName(cropname);
		String username = prop.getUsername();
		String password = prop.getPassword();
		
		try {
			TomcatManagerUtil.reloadWebapp(host, port, webAppPath, username, password);
			alert("You have successfully reloaded the web-application for the crop " + cropname + ".");
			writeToLog("ServerHandler.executeSingleTomcatReloadRequest()", "You have successfully reloaded the web-application for the crop " + cropname + ".", username);
		} catch (Exception e) {
			alert("Failed reloading webapp for " + cropname + ".");
			writeToLog("ServerHandler.executeSingleTomcatReloadRequest()", "Failed reloading webapp for " + cropname + ", " + e.getMessage() + ".", username);
		}
		//reloadRequest.setPath(xmlHandler.getWARName(cropname));
		//reloadRequest.setUrl("http://" + host + ":" + port + "/manager/text");
		//reloadRequest.execute();
		
	}

	/**
	 * Sends the requests to reload the web application under the found context paths in the gobii-web.xml file
	 */
	public void executeAllTomcatReloadRequest() {
		String host = xmlHandler.getHostForReload();
		String port = xmlHandler.getPortForReload();
		String username = prop.getUsername();
		String password = prop.getPassword();
		NodeList contextPathNodes = xmlHandler.getContextPathNodes();
		//Reload each context
		for (int i = 0; i < contextPathNodes.getLength(); i++) {
			String webAppPath = contextPathNodes.item(i).getTextContent();
			try {
				TomcatManagerUtil.reloadWebapp(host, port, webAppPath, username, password);
				//alert("You have successfully reloaded the web-application for at path " + webAppPath + ".");
				writeToLog("ServerHandler.executeSingleTomcatReloadRequest()", "You have successfully reloaded the web-application at path " + webAppPath + ".", username);
			} catch (Exception e) {
				alert("Failed reloading webapp for " + webAppPath + ".");
				writeToLog("ServerHandler.executeSingleTomcatReloadRequest()", "Failed reloading webapp for " + webAppPath + ", " + e.getMessage() + ".", username);
			}
			//reloadRequest.setPath(contextPathNodes.item(i).getTextContent());
			//reloadRequest.setUrl("http://" + host + ":" + port + "/manager/text");
			//reloadRequest.execute();
		}
		writeToLog("ServerHandler.executeAllTomcatReloadRequest()", "You have successfully reloaded all the web-applications.", username);
	}

	/**
	 * Sends the request to undeploy the web application under the found context path in the gobii-web.xml file
	 */
	public boolean undeployFromTomcat(Crop currCrop) {
		boolean success = true;
		//try {
			/*
			 * UndeployTask undeploy = new UndeployTask();
			 * undeploy.setUsername(prop.getUsername());
			 * undeploy.setPassword(prop.getPassword()); String host =
			 * xmlHandler.getHostForReload(); String port = xmlHandler.getPortForReload();
			 * undeploy.setPath(xmlHandler.getWebContextPath(currCrop.getName()));
			 * undeploy.setUrl("http://" + host + ":" + port + "/manager/text");
			 * undeploy.execute();
			 */
		String host = xmlHandler.getHostForReload();
		String port = xmlHandler.getPortForReload();
		String webAppPath = xmlHandler.getWebContextPath(currCrop.getName());
		String username = prop.getUsername();
		String password = prop.getPassword();

		try {
			TomcatManagerUtil.undeployWebapp(host, port, webAppPath, username, password);
			Messagebox.show(("You have successfully undeployed the crop " + currCrop.getName() + "."));
			writeToLog("ServerHandler.undeployFromTomcat()", "You have successfully undeployed the crop " + currCrop.getName() + ".", username);
		} catch (Exception e) {
			alert("Failed undeploying webapp for crop " + currCrop.getName() + ".");
			writeToLog("ServerHandler.undeployFromTomcat()", "Undeployment for the crop " + currCrop.getName() + " " + e.getMessage() + ".", username);
			success = false;
		}
			
		/*
		 * } catch (Exception e) { e.printStackTrace();
		 * writeToLog("ServerHandler.undeployFromTomcat()", "Undeployment for the crop "
		 * + currCrop.getName() + " has failed.", username); success = false; }
		 */
		return success;
	}

	/**
	 * Removes the database of the given crop
	 * @param cropName
	 * @return true upon success
	 */
	public boolean postgresRemoveCrop(String cropName, String cropDatabase){
		boolean success = true;
		DSLContext context = WebappUtil.getDSLContext();
		try {
			context.fetch("DROP DATABASE " + cropDatabase + ";");
			writeToLog("ServerHandler.postgresRemoveCrop()", "You have successfully removed database for the crop " + cropName + ".", username);
		} catch (Exception e) {
			alert("The database could not be removed. Stacktrace of the error: \n" + e.toString());
			writeToLog("ServerHandler.postgresRemoveCrop()", "The database for the crop " + cropName + " failed to be removed.", username);
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	/**
	 * Create a database for the current Crop and populate it with basic seed data and contact data
	 * The ViewModel operations enable relative smooth switching of databases for the queries
	 * @param currentCrop
	 * @param contactData each cell is a tab delimited string containing the contact data
	 * @param firstUpload A flag that allows users to retry filling the contact data if it was invalid on the first try
	 * @return
	 *  1, if the liquibase population of basic seed data failed
	 * -1, if the provided contact data had an error
	 *  0, upon complete success
	 * @throws TimescopeException 
	 */
	public int postgresAddCrop(Crop currentCrop, ArrayList<String> contactData, boolean firstUpload) throws TimescopeException{
		int success;
		if (firstUpload) {
			ServerInfo tmpInfo = new ServerInfo(xmlHandler.getPostgresHost(), xmlHandler.getPostgresPort(), "gobii_dev",
					xmlHandler.getPostgresUserName(), xmlHandler.getPostgresPassword());
			WebappUtil.connectToDB(tmpInfo.getUserName(), tmpInfo.getPassword(), tmpInfo);
			DSLContext context = WebappUtil.getDSLContext();
			if (!populateSeedData(context, currentCrop)) {
				success = 1;
				writeToLog("ServerHandler.postgresAddCrop()", "Populating the the liquibase data failed.", username);
				return success;
			}
			writeToLog("ServerHandler.postgresAddCrop()", "The liquibase data was successfully added to the database of crop " + currentCrop.getName() + ".", username);
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
		
		WebappUtil.connectToDB(info.getUserName(), info.getPassword(), info);
		DSLContext context = WebappUtil.getDSLContext();
		Pair<Integer, java.sql.Date> timescopeData = populateTimescopeArray(context);
		success = populateContactData(context, contactData, timescopeData);
		info = new ServerInfo(xmlHandler.getPostgresHost(), xmlHandler.getPostgresPort(), "gobii_dev",
				xmlHandler.getPostgresUserName(), xmlHandler.getPostgresPassword());
		WebappUtil.connectToDB(info.getUserName(), info.getPassword(), info);
		writeToLog("ServerHandler.postgresAddCrop()", "The contact data was successfully added to the database of crop " + currentCrop.getName() + ".", username);
		return success;
	}

	/**
	 * get the gadm user ID as a query from the current database paired with the current date
	 * @param context
	 * @return a Pair of ID of the gadm super user and the current Date in SQL format
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
	 * @return true upon successful seeddata population
	 */
	private boolean populateSeedData(DSLContext context, Crop currentCrop){
		boolean success = true;
		try {
			context.fetch("CREATE DATABASE " + currentCrop.getDatabaseName() + " WITH OWNER '" + xmlHandler.getPostgresUserName() + "';");
		} catch (Exception e){
			writeToLog("ServerHandler.populateSeedData()", "The database for the crop " + currentCrop.getName() + " couldn't be created.", username);
			alert("The database for the crop " + currentCrop.getName() + "couldn't be created due to following error: \n" + e.toString());
			return false;
		}
		writeToLog("ServerHandler.populateSeedData()", "The database for the crop " + currentCrop.getName() + " was successfully created.", username);
		List<String> populate = new ArrayList<>(Arrays.asList(xmlHandler.getPostgresHost(), xmlHandler.getPostgresUserName(), xmlHandler.getPostgresPassword(), xmlHandler.getPostgresHost(), xmlHandler.getPostgresPort(),  currentCrop.getDatabaseName()));
		if (!scriptExecutor("liquibase.sh", populate)){
			writeToLog("ServerHandler.populateSeedData()", "The database couldn't be populated with liquibase data.", username);
			success = false;
		}
		return success;
	}

	/**
	 * Fill the contact table as specified:
	 * https://gobiiproject.atlassian.net/browse/I19-53
	 * Validation is done by querying the information directly from the database
	 * @param context
	 * @param contactData each cell is a tab delimited string containing the contact data
	 * @param timescopeData a Pair of gadm Super user ID in the postgres database and the current Date in SQL Format
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
				writeToLog("ServerHandler.populateContactData()", "The role " + lastRole + " is not a valid role.", username);
				alert("The role '" + lastRole + "' is not a valid role. Please verify the spelling and make sure that the uploaded file follows the expected tab-delimited format.");
				return success;
			}
			writeToLog("ServerHandler.populateContactData()", "The roles have been correctly been read in.", username);
			Integer[] roleID = new Integer[tmpRoleID.size()];
			for (int i =0; i < tmpRoleID.size(); i++)
				roleID[i] = tmpRoleID.get(i);
			Integer organizationID;
			try {
				organizationID = (Integer) context.fetch("select organization_id from organization where name = '" + splitData[5] + "';").getValue(0, 0);
			} catch (Exception e){
				writeToLog("ServerHandler.populateContactData()", "The organization " + splitData[5] + " is not a valid organization.", username);
				alert("The organization '" + splitData[5] + "' is not a valid organization. Please verify the spelling and make sure that the uploaded file follows the expected tab-delimited format.");
				return success;
			}
			writeToLog("ServerHandler.populateContactData()", "The organization have been correctly read in.", username);
			try {
				Routines.createcontact(
					context.configuration(),
					splitData[0],
					splitData[1],
					splitData[2],
					splitData[3],
					roleID,
					timescopeData.getX(),
					timescopeData.getY(),
					timescopeData.getX(),
					timescopeData.getY(),
					organizationID,
					splitData[6]
				);
			} catch (Exception e) {
				writeToLog("ServerHandler.populateContactData()", "Contact data could not be filled in.", username);
				alert("Contact data could not be filled in correctly, with following error: \n" + e.toString());
				return success;
			}
		}
		success = 0;
		return success;
	}
}
