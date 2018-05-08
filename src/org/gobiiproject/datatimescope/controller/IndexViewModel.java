package org.gobiiproject.datatimescope.controller;

import static org.gobiiproject.datatimescope.db.generated.Tables.CV;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.services.CommonInfoService;
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

	@Command
	public void openDatabaseInfoDialog() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("isLoggedIn", true);

		Window window = (Window)Executions.createComponents(
				"/switch_database.zul", null, args);
		window.doModal();
	}

}

