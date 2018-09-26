package org.gobiiproject.datatimescope.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.services.AuthenticationService;
import org.gobiiproject.datatimescope.services.AuthenticationServiceChapter3Impl;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.Utils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

public class TimescopeVersionModel {
	final static Logger log = Logger.getLogger(TimescopeVersionModel.class.getName());
	//UI component
	ViewModelService viewModelService;

	private String version;

	@AfterCompose
	public void afterCompose() {
		viewModelService = new ViewModelServiceImpl();

		File configFile = new File( System.getProperty("user.dir")+"/config.properties");
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream reader = classLoader.getResourceAsStream("config.properties");
			
//		    FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		 
		    String tsversion = props.getProperty("version");

		    if(tsversion != null){
		    reader.close();
			setVersion(tsversion);
			
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
		
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = "Timescope v. "+version;
	}

}
