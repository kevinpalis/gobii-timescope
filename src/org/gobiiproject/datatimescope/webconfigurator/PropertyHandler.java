package org.gobiiproject.datatimescope.webconfigurator;

import java.io.*;
import java.util.Properties;

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.writeToLog;

/**
 * A class that handles the gobii-configurator.properties file
 */

public class PropertyHandler{

    private Properties prop = new Properties();
    private String username;

    public PropertyHandler(String name, Properties properties){
        username = name;
    }

    public String getUsername(){
        return prop.getProperty("username");
    }

    public void setUsername(String username){
        try {
            OutputStream output = new FileOutputStream("/usr/local/tomcat/webapps/timescope/WEB-INF/classes/gobii-configurator.properties");
            //OutputStream output = new FileOutputStream("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/gobii-configurator.properties");
            prop.setProperty("username" , username);
            prop.store(output, null);
            writeToLog("PropertyHandler.setPassword()", "Tomcat username credential set.", username);

        } catch (IOException e) {
            e.printStackTrace();
            writeToLog("PropertyHandler.setUsername()", "Property file not found.", username);
        }
    }

    public String getPassword(){

        return prop.getProperty("password");

    }

    public void setPassword(String password){
        try {
            OutputStream output = new FileOutputStream("/usr/local/tomcat/webapps/timescope/WEB-INF/classes/gobii-configurator.properties");
            //OutputStream output = new FileOutputStream("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/gobii-configurator.properties");
            prop.setProperty("password" , password);
            prop.store(output, null);
            writeToLog("PropertyHandler.setPassword()", "Tomcat password credential set.", username);
        } catch (IOException e) {
            e.printStackTrace();
            writeToLog("PropertyHandler.setPassword()", "Property file not found.", username);
        }
    }


}
