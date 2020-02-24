package org.gobiiproject.datatimescope.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.gobiiproject.datatimescope.services.AuthenticationService;
import org.gobiiproject.datatimescope.services.AuthenticationServiceImpl;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class SwitchDatabaseViewModel {
	final static Logger log = Logger.getLogger(SwitchDatabaseViewModel.class.getName());

	@Wire("#switchDatabaseConnectionWindow")
	Window databaseConnectionWindow;

	private boolean isLoggedIn = false;

	private ServerInfo serverInfo;

	ViewModelService viewModelService;

	AuthenticationService authService;

	private String pageCaption, username, password;

	private ServerInfo prevserverInfo;

	private UserCredential prevUsercredential;


	@Init
	public void init(@ExecutionArgParam("isLoggedIn") Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
		prevserverInfo = new ServerInfo();

		if(isLoggedIn) serverInfo = (ServerInfo) Sessions.getCurrent().getAttribute("serverInfo");

		authService =new AuthenticationServiceImpl();
		viewModelService = new ViewModelServiceImpl();

		//Figure out if this window was called to edit a user or to create one
		if(isLoggedIn) setPageCaption("Modify Database Connection");
		else{
			setPageCaption("Specify Database");
		}

		prevserverInfo.setHost(serverInfo.getHost());
		prevserverInfo.setPort(serverInfo.getPort());
		prevserverInfo.setDbName(serverInfo.getDbName());

		prevUsercredential = authService.getUserCredential();
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}


	@Command
	public void connectToDatabase(){
		if(validate()){
			//read file to get db username and pw from config file because upon logging in, that has been set back to dummy values.
			File configFile = new File( System.getProperty("user.dir")+"/config.properties");
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				InputStream reader = classLoader.getResourceAsStream("config.properties");

				//		    FileReader reader = new FileReader(configFile);
				Properties props = new Properties();
				props.load(reader);

				String dbusername = props.getProperty("db.username");
				String dbpassword = props.getProperty("db.pw");

				if(dbusername != null && dbpassword != null){
					reader.close();
					serverInfo.setUserName(dbusername);
					serverInfo.setPassword(dbpassword);

					Map<String, Object> args = new HashMap<String, Object>();
					args.put("isLoggedIn", false);

				}
			} catch (FileNotFoundException ex) {
				// file does not exist

				Messagebox.show("Cannot find config properties file for default db username and password, please contact your administrator", "Cannot switch to any database", Messagebox.OK, Messagebox.ERROR);

				log.error("cannot find config properties file: "+ configFile.getAbsolutePath());
				databaseConnectionWindow.detach();

			} catch (IOException ex) {
				// I/O error

				Messagebox.show("Cannot find config properties file for default db username and password, please contact your administrator", "Cannot switch to any database", Messagebox.OK, Messagebox.ERROR);

				log.error("i/o exception"+ ex.getMessage());
				databaseConnectionWindow.detach();
			} catch (NullPointerException ex) {
				// file does not exist

				Messagebox.show("Cannot find config properties file for default db username and password, please contact your administrator", "Cannot switch to any database", Messagebox.OK, Messagebox.ERROR);

				log.error("Null values were retrieved from config properties file: "+ configFile.getAbsolutePath());
				databaseConnectionWindow.detach();
			}

			try {
				if(viewModelService.connectToDB(serverInfo.getUserName(), serverInfo.getPassword(), serverInfo)){


					//try logging in using timescope credentials provided
					if (authService.login(username, password)){
						Messagebox.show("Login successful!");

						databaseConnectionWindow.detach();
						Executions.sendRedirect("/index.zul");
						return;

					} else{ // re-connect to previous db and server

						if(viewModelService.connectToDB(serverInfo.getUserName(), serverInfo.getPassword(), prevserverInfo)){ //if can't log back in, logout altogether to be safe XD

							Session sess = Sessions.getCurrent();
							UserCredential cre = new UserCredential(prevUsercredential.getAccount(), prevUsercredential.getRole());

							sess.setAttribute("userCredential",cre);

						}else{
							autologout();
						}
					}
				}
			} catch (TimescopeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Messagebox.show(e.getMessage(), e.getErrorTitle(), Messagebox.OK, Messagebox.ERROR);
			}

		}
	}

	private boolean validate() {
		// TODO Auto-generated method stub
		boolean validated = true;
		if( prevserverInfo.getHost().equals(serverInfo.getHost()) &&
				prevserverInfo.getPort().equals(serverInfo.getPort()) &&
				prevserverInfo.getDbName().equals(serverInfo.getDbName())
				){
			Messagebox.show("You are already connected to that database.");
			validated = false;
		}


		return validated;
	}

	private void autologout() {
		// TODO Auto-generated method stub
		authService.logout();

		Messagebox.show("There were troubles while logging in to server. Please log in again.", "Automatic Logout", Messagebox.OK, Messagebox.ERROR);

		Executions.sendRedirect("/index.zul");
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}


	public String getPageCaption() {
		return pageCaption;
	}

	public void setPageCaption(String pageCaption) {
		this.pageCaption = pageCaption;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

