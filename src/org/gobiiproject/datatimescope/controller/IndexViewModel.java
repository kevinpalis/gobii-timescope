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
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

public class IndexViewModel {
	final static Logger log = Logger.getLogger(IndexViewModel.class.getName());

	private ViewModelServiceImpl viewModelService;
	private String datawarehouseVersion;
	private ServerInfo serverInfo;

	private String version;

	@Init
	public void init() {
		
		viewModelService = new ViewModelServiceImpl();
		
		setDatawarehouseVersion(viewModelService.getDatawarehouseVersion());
		
		setServerInfo(new ServerInfo());

		setServerInfo((ServerInfo) Sessions.getCurrent().getAttribute("serverInfo"));


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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}

