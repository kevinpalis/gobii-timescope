package org.gobiiproject.datatimescope.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.services.AuthenticationService;
import org.gobiiproject.datatimescope.services.AuthenticationServiceChapter3Impl;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

public class LoginViewModel {
	final static Logger log = Logger.getLogger(LoginViewModel.class.getName());
	//UI component
	boolean isCreateNew = false;

	private ServerInfo serverInfo;

	private String pageCaption;
	
	private TimescoperRecord userAccount;

	private ListModelList<String> roleList;

	ViewModelService viewModelService;

	@Init
	public void init() {
	    AuthenticationService authService = new AuthenticationServiceChapter3Impl();
	  
        UserCredential cre = authService.getUserCredential();
        if(cre==null){

    		userAccount = new TimescoperRecord();

    		serverInfo = new ServerInfo();

    		Map<String, Object> args = new HashMap<String, Object>();
    		args.put("isLoggedIn", false);

    		viewModelService = new ViewModelServiceImpl();
        } else{
        	Executions.sendRedirect("/index.zul");
            return;
        }
	}


	@Command("needhelp")
	public void needhelp() {
		  Window window = (Window)Executions.createComponents(
	                "needHelp.zul", null, null);
	        window.doModal();
	}
	
	@Command("login")
	public void openDatabaseInfoDialog() {

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

			 log.error("cannot find config properties file: "+ configFile.getAbsolutePath());
		} catch (IOException ex) {
		    // I/O error
			 log.error("i/o exception"+ ex.getMessage());
		} catch (NullPointerException ex) {
		    // file does not exist

			 log.error("Null values were retrieved from config properties file: "+ configFile.getAbsolutePath());
		}
		
		if(viewModelService.connectToDB(serverInfo.getUserName(), serverInfo.getPassword(), serverInfo)){

			AuthenticationService authService =new AuthenticationServiceChapter3Impl();

			if (authService.login(userAccount.getUsername(), userAccount.getPassword())){

				serverInfo.setUserName("dummyusername");
				serverInfo.setPassword("dummypassword");
				
				Messagebox.show("Login successful!", "", Messagebox.OK, Messagebox.INFORMATION);
				Executions.sendRedirect("/index.zul");
				return;

			}

		}

	}

	public TimescoperRecord getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(TimescoperRecord userAccount) {
		this.userAccount = userAccount;
	}

	public ListModelList<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(ListModelList<String> roleList) {
		this.roleList = roleList;
	}

	public String getPageCaption() {
		return pageCaption;
	}

	public void setPageCaption(String pageCaption) {
		this.pageCaption = pageCaption;
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

}
