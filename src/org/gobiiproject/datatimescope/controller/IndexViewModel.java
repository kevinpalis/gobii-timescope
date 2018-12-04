package org.gobiiproject.datatimescope.controller;

import static org.gobiiproject.datatimescope.db.generated.Tables.CV;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.services.AuthenticationServiceChapter3Impl;
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

public class IndexViewModel {


	private ViewModelServiceImpl viewModelService;
	private String datawarehouseVersion;
	private ServerInfo serverInfo;

	@Init
	public void init() {
		
		viewModelService = new ViewModelServiceImpl();
		
		setDatawarehouseVersion(viewModelService.getDatawarehouseVersion());
		
		

		setServerInfo(new ServerInfo());

		setServerInfo((ServerInfo) Sessions.getCurrent().getAttribute("serverInfo"));

	}

	@Command
	public void openDatabaseInfoDialog() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("isLoggedIn", true);

		Window window = (Window)Executions.createComponents(
				"/switch_database.zul", null, args);
		window.doModal();
	}

	public String getDatawarehouseVersion() {
		return datawarehouseVersion;
	}

	public void setDatawarehouseVersion(String datawarehouseVersion) {
		this.datawarehouseVersion = datawarehouseVersion;
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

}

