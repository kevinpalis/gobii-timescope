package org.gobiiproject.datatimescope.webconfigurator;

import java.io.*;
import java.util.Properties;

public class propertyHandler {

    public propertyHandler(){

    }
    private Properties prop = new Properties();

    public String getUsername() throws IOException {
        InputStream input = propertyHandler.class.getClassLoader().getResourceAsStream("web-configurator.properties");
        if (input == null){
            System.out.println("No web-configurator file found.");
            return null;
        }
        prop.load(input);
        return prop.getProperty("username");
    }

    public String getPassword() throws IOException {
        InputStream input = propertyHandler.class.getClassLoader().getResourceAsStream("web-configurator.properties");
        if (input == null){
            System.out.println("No web-configurator file found.");
            return null;
        }
        prop.load(input);
        return prop.getProperty("password");
    }

}
