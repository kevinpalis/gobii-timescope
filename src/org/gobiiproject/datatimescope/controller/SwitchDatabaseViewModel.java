package org.gobiiproject.datatimescope.controller;

import static org.gobiiproject.datatimescope.db.generated.Tables.CV;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

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
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class SwitchDatabaseViewModel {

	@Wire("#databaseConnectionWindow")
	Window databaseConnectionWindow;

	private boolean isLoggedIn = false;

	private ServerInfo serverInfo;

	ViewModelService viewModelService;

	private String pageCaption;


	@Init
	public void init(@ExecutionArgParam("isLoggedIn") Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;


		if(isLoggedIn) serverInfo = (ServerInfo) Sessions.getCurrent().getAttribute("serverInfo");
		else{
			serverInfo = new ServerInfo();
			serverInfo.setUserName("timescoper");
			serverInfo.setPassword("helloworld");
		}

		viewModelService = new ViewModelServiceImpl();
		//Figure out if this window was called to edit a user or to create one
		if(isLoggedIn) setPageCaption("Modify Database Connection");
		else{
			setPageCaption("Specify Database");
		}
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}


	@Command
	public void connectToDatabase(){

		if(viewModelService.connectToDB(serverInfo.getUserName(), serverInfo.getPassword(), serverInfo)){
			Messagebox.show("Successfully connected!");
			databaseConnectionWindow.detach();
		}


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
}

