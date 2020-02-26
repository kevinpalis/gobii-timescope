package org.gobiiproject.datatimescope.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.services.ServiceFactory;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.zkoss.bind.annotation.AfterCompose;

public class TimescopeVersionModel {
	final static Logger log = Logger.getLogger(TimescopeVersionModel.class.getName());
	//UI component
	ViewModelService viewModelService;

	private String version;

	@AfterCompose
	public void afterCompose() {
		viewModelService = ServiceFactory.getViewModelService();

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
