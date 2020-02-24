package org.gobiiproject.datatimescope.controller;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
//import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.WebappUtil;
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
		log.debug("Initializing Index page");
		viewModelService = new ViewModelServiceImpl();
		
		try {
			setDatawarehouseVersion(viewModelService.getDatawarehouseVersion());
		} catch (TimescopeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
		}
		ServerInfo current = (ServerInfo) Sessions.getCurrent().getAttribute("serverInfo");
		if (current == null || current.getHost() == null || current.getHost() == "" ) {
			log.info("Loading default DB values from cookie");
			ServerInfo serverInfo = new ServerInfo();
			//Load cookies --
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			Cookie[] cookies = request.getCookies();

			for (Cookie cookie: cookies) {
				if (cookie.getName().equals("DB_HOST")) {
					serverInfo.setHost(cookie.getValue());
				}
				else if (cookie.getName().equals("DB_PORT")) {
					serverInfo.setPort(cookie.getValue());
				}
				else if (cookie.getName().equals("DB_NAME")) {
					serverInfo.setDbName(cookie.getValue());
				}
			}

			setServerInfo(serverInfo);
		} else {
			setServerInfo(current);
		}
		//setServerInfo((ServerInfo) Sessions.getCurrent().getAttribute("serverInfo"));
		//getting the version
		ServletContext context = Executions.getCurrent().getDesktop().getWebApp().getServletContext();
		String version = (String) context.getAttribute("VERSION");
		if (version != null) {
			setVersion(version);
		}

//
//		//File configFile = new File( System.getProperty("user.dir")+"/config.properties");
//		try {
//			//TODO: Move this to startup servlet and store the contents of config.properties to application scope.
//			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//			InputStream reader = classLoader.getResourceAsStream("config.properties");
//			
//		    //FileReader reader = new FileReader(configFile);
//		    Properties props = new Properties();
//		    props.load(reader);
//		 
//		    String tsversion = props.getProperty("version");
//
//		    if(tsversion != null){
//		    	setVersion(tsversion);
//		    }
//		    reader.close();
//		} catch (FileNotFoundException ex) {
//		    // file does not exist
//			 log.error("cannot find config properties file: "+ configFile.getAbsolutePath());
//		} catch (IOException ex) {
//		    // I/O error
//			 log.error("i/o exception"+ ex.getMessage());
//		} catch (NullPointerException ex) {
//		    // file does not exist
//			 log.error("Null values were retrieved from config properties file: "+ configFile.getAbsolutePath());
//		}
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
		log.debug(String.format("Set serverInfo called: %s %s %s", serverInfo.getHost(), serverInfo.getPort(), serverInfo.getDbName()));
		this.serverInfo = serverInfo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}

