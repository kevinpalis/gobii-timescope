package org.gobiiproject.datatimescope.controller;

import static org.gobiiproject.datatimescope.db.generated.Tables.CV;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;

import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class SwitchDatabaseViewModel {
	private ServerInfo serverInfo;
	
	ViewModelService viewModelService;
	
	@AfterCompose
	public void afterCompose() {
		  
		serverInfo = (ServerInfo) Sessions.getCurrent().getAttribute("serverInfo");
	      
	      viewModelService = new ViewModelServiceImpl();
	}
	
	@Command
    public void openDatabaseInfoDialog() {
		  Window window = (Window)Executions.createComponents(
	                "/switch_database.zul", null, null);
	      window.doModal();
    }
	 
	@Command
	public void connectToDatabase(){

		String userName = "timescoper";
        String password = "helloworld";
        
        viewModelService.connectToDB(userName, password, serverInfo);
       
        
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}
}
