package org.gobiiproject.datatimescope.webconfigurator;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

import java.util.HashMap;
import java.util.Map;

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.writeToLog;
import static org.zkoss.zk.ui.util.Clients.alert;

/**
 * A class which basically functions as a wrapper for any critical operations on postgres or Tomcat
 * This is done by prompting the user to click OK on a generated message box outlining possible side effects of the chosen action
 * If the user accepts the prompt the function calls the corresponding execution function
 */

public class WarningComposer{

	private XmlModifier xmlHandler;
	private String username;

	public WarningComposer(XmlModifier xmlHandler, String name){
		username = name;
		this.xmlHandler = xmlHandler;
	}

	/**
	 * Opens a warning box and if acknowledged performs the restart of all web-applications.
	 * Otherwise stages it for later and upon next restart the effects will take place
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void warningTomcat(@ContextParam(ContextType.BINDER) Binder binder, WebConfigViewModel model, String callingContext, String logMessage) {
		Messagebox.show(
				"Clicking OK will restart all web applications and all unsaved data will be lost.",
				"Warning",
				Messagebox.OK | Messagebox.CANCEL,
				Messagebox.EXCLAMATION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) {
						if (evt.getName().equals("onOK")) {
							model.serverHandler.executeAllTomcatReloadRequest();
							if (logMessage != null){
								alert(logMessage);
								writeToLog(callingContext, logMessage, username);
							}
							binder.sendCommand("disableEdit", null);
							model.goToHome();
						} else {
							writeToLog("WarningComposer.warningTomcat()", "Didn't accept warning box.", username);
							model.cancelChanges();
						}
					}
				});
	}

	/**
	 * Opens a warning box and if acknowledged performs the restart of all the web-applications and modifies the postgres credentials as input by the user.
	 * Otherwise stages it for later and upon next restart the effects will take place
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void warningPostgres(@ContextParam(ContextType.BINDER) Binder binder, WebConfigViewModel model){
		Messagebox.Button[] buttons = new Messagebox.Button[]{Messagebox.Button.OK, Messagebox.Button.CANCEL};
		Map<String, String> params = new HashMap<>();
		params.put("width", "500");
		Messagebox.show(
			"This operation requires a restart of your Postgres database. This has the potential to fail if there are " +
			"active sessions, or worse, corrupt data being loaded. \nPlease make sure that there are no active session prior to changing these settings. \nAre you sure" +
			" you want to restart Postgres now?",
			"Warning",
			buttons,
			null,
			Messagebox.EXCLAMATION,
			null,
			new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event evt) {
					if (evt.getName().equals("onOK")) {
						String oldUsername = xmlHandler.getPostgresUserName();
						binder.sendCommand("disableEdit", null); //Now XML contains new name
						model.serverHandler.executePostgresChange(oldUsername);
						model.serverHandler.executeAllTomcatReloadRequest();
						alert("You have successfully reloaded all web-applications.");
						writeToLog("WarningComposer.warningPostgres()", "You have successfully reloaded all web-applications.", username);
						model.goToHome();
					} else {
						writeToLog("WarningComposer.warningPostgres()", "Didn't accept warning box.", username);
						model.cancelChanges();
					}
				}
			}, params
		);

	}

	/**
	 * Opens a warning box and if acknowledged performs the deletion of the database of the chosen crop
	 * If this is the only database left this operation will fail as that would leave the system in a weird state
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void warningRemoval(@ContextParam(ContextType.BINDER) Binder binder, WebConfigViewModel model){
		Messagebox.Button[] buttons = new Messagebox.Button[]{Messagebox.Button.OK, Messagebox.Button.CANCEL};
		Map<String, String> params = new HashMap<>();
		params.put("width", "500");
		Messagebox.show(
			"This operation will permanently remove this database and all its associated information." +
			"\nPlease make sure that there are no active sessions prior to removing this database. \nAre you sure" +
			" you want to remove the database now?",
			"Warning",
			buttons,
			null,
			Messagebox.EXCLAMATION,
			null,
			new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event evt) {
					if (evt.getName().equals("onOK")) {
						if (xmlHandler.getCropList().size() > 1) {
			//			    writeToLog("WarningComposer.warningRemoval()", "Will now execute model.executeRemoval", username);
							model.executeRemoval(binder);
						} else {
							alert("This is the only database. Please add another crop before deleting this database.");
							writeToLog("WarningComposer.warningRemoval()", "Tried to remove only database.", username);
							model.cancelChanges();
						}
					} else {
						writeToLog("WarningComposer.warningRemoval()", "Didn't accept warning box.", username);
						model.cancelChanges();
					}
				}
			}, params
		);
	}


	/**
	 * Opens a warning box and if acknowledged performs the restart of only the web-application associated with the crop
	 * Otherwise stages it for later and upon next restart the effects will take place
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void warningActivityTomcat(Binder binder, WebConfigViewModel model, Crop currentCrop) {
		Messagebox.show(
			"Clicking OK will restart the web application of the crop " + currentCrop.getName() + " and all unsaved data will be lost.",
			"Warning",
			Messagebox.OK | Messagebox.CANCEL,
			Messagebox.EXCLAMATION,
			new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event evt) {
					if (evt.getName().equals("onOK")) {
						binder.sendCommand("disableEdit",null);
						if (currentCrop.isActivityChanged()){
							currentCrop.setActivityChanged(false);
							alert("Activity for the crop" + currentCrop.getName() + " has been changed.");
							writeToLog("WarningComposer.warningActivityTomcat()", "Activity for the crop" + currentCrop.getName() + " has been changed.", username);
						} else {
							alert("No changes have been made, please change a setting.");
							writeToLog("WarningComposer.warningActivityTomcat()", "No changes made.", username);
							return;
						}
						if (currentCrop.getIsActive()) {
							model.cronHandler.modifyCron("create", xmlHandler.getHostForReload(), currentCrop);
							writeToLog("WarningComposer.warningActivityTomcat()", "CRONs for the crop" + currentCrop.getName() + " have been activated.", username);
						} else {
							model.cronHandler.modifyCron("delete", xmlHandler.getHostForReload(), currentCrop);
							writeToLog("WarningComposer.warningActivityTomcat()", "CRONs for the crop" + currentCrop.getName() + " have been deactivated.", username);
						}
						model.serverHandler.executeSingleTomcatReloadRequest(currentCrop.getName());
						model.goToHome();
					} else {
						writeToLog("WarningComposer.warningActivityTomcat()", "Didn't accept warning box.", username);
						model.cancelChanges();
					}
				}
			});
	}
}
