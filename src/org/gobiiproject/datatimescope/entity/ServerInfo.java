/* 
timescoper_id integer NOT NULL DEFAULT nextval('timescoper_timescoper_id_seq'::regclass),
    firstname text COLLATE pg_catalog."default" NOT NULL,
    lastname text COLLATE pg_catalog."default" NOT NULL,
    username text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default",
    role integer DEFAULT 3,
    CONSTRAINT pk_timescoper PRIMARY KEY (timescoper_id),
    CONSTRAINT username_key UNIQUE (username)
*/
package org.gobiiproject.datatimescope.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.controller.LoginViewModel;

/**
 * User entity
 */
public class ServerInfo {
	final static Logger log = Logger.getLogger(LoginViewModel.class.getName());
	
	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String password;
	
	public ServerInfo() {

		File configFile = new File( System.getProperty("user.dir")+"/db.properties");
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream reader = classLoader.getResourceAsStream("db.properties");
			
//		    FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		 
		    String dbHost = props.getProperty("db.host");
		    String dbPort = props.getProperty("db.port");
		    String dbName = props.getProperty("db.name");
		    

		    if(dbHost != null)setHost(dbHost);

		    if(dbPort != null)setPort(dbPort);

		    if(dbName != null)setDbName(dbName);
		    	
		    reader.close();
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
	
	public ServerInfo(String defaults) {
		//defaultValues
		
	      setHost("localhost");
	      setPort("5432");
	      setDbName("timescope_db2");
	      setUserName("");
	      setPassword("");
	}
	
	public ServerInfo(String host, String port, String dbName, String userName, String password) {
		this.setHost(host);
		this.setPort(port);
		this.setDbName(dbName);
		this.password = password;
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}
