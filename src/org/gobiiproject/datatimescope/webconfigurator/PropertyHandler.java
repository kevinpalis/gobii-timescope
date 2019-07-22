package org.gobiiproject.datatimescope.webconfigurator;

import java.io.*;
import java.util.Properties;

import static org.zkoss.zk.ui.util.Clients.alert;

/**
 * A class that handles the gobii-configurator.properties file
 */

public class PropertyHandler {

    private Properties prop = new Properties();

    public String getUsername(){
        try {
            //InputStream input = new FileInputStream("/usr/local/tomcat/webapps/timescope/WEB-INF/classes/gobii-configurator.properties");
            InputStream input = new FileInputStream("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/gobii-configurator.properties");
            prop.load(input);
            return prop.getProperty("username");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setUsername(String username){
        try {
            //OutputStream output = new FileOutputStream("/usr/local/tomcat/webapps/timescope/WEB-INF/classes/gobii-configurator.properties");
            OutputStream output = new FileOutputStream("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/gobii-configurator.properties");
            prop.setProperty("username" , username);
            prop.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO What happens for empty file
    public String getPassword(){
        try {
            //InputStream input = new FileInputStream("/usr/local/tomcat/webapps/timescope/WEB-INF/classes/gobii-configurator.properties");
            InputStream input = new FileInputStream("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/gobii-configurator.properties");
            prop.load(input);
            return prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPassword(String password){
        try {
            //OutputStream output = new FileOutputStream("/usr/local/tomcat/webapps/timescope/WEB-INF/classes/gobii-configurator.properties");
            OutputStream output = new FileOutputStream("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/gobii-configurator.properties");
            prop.setProperty("password" , password);
            prop.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
