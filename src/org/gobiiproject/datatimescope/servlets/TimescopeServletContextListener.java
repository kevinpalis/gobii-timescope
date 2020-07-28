package org.gobiiproject.datatimescope.servlets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

/**
 * Application Lifecycle Listener implementation class TimescopeServletContextListener
 *
 */
@WebListener
public class TimescopeServletContextListener implements ServletContextListener {
	private final static String DB_USERNAME = "DB_USERNAME";
	private final static String DB_PASSWORD = "DB_PASSWORD";
	private final static String VERSION = "VERSION";
	
	final static Logger log = Logger.getLogger(TimescopeServletContextListener.class.getName());


    /**
     * Default constructor. 
     */
    public TimescopeServletContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	log.info("Timescope application is shutting down.");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	log.info("Timescope application is starting up.");
    	
    	//start up activities
    	//load database connection properties to application scope.
    	try {
    		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    		InputStream reader = classLoader.getResourceAsStream("config.properties");
    		
    		Properties props = new Properties();
		    props.load(reader);
		   
		    String username = (String) props.get("db.username");
		    String password = (String) props.get("db.pw");
		    String version = (String) props.get("version");
		    
		    if (username == null || username == ""  || password == null || password == "") {
		    	log.warn("No database credentials are found config.");
		    
		    }
		    
		    sce.getServletContext().setAttribute(DB_USERNAME, username);
		    sce.getServletContext().setAttribute(DB_PASSWORD, password);
		    sce.getServletContext().setAttribute(VERSION, version);
		    
		    log.info("Global database credentials set.");
		    
		    //TODO:  get username/password combo from env vars
		    reader.close();
    	} catch (FileNotFoundException fnfe) {
    		log.error("Database config file not found.");
    		fnfe.printStackTrace();
    	} catch (IOException e) {
			log.error("Error loading db properties -- check config file");
			e.printStackTrace();
		}
    	
    }
	
}
