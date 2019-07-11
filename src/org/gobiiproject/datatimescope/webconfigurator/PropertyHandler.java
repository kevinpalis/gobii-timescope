package org.gobiiproject.datatimescope.webconfigurator;

import java.io.*;
import java.util.Properties;

/**
 * A class that handles the gobii-configurator.properties file
 */

public class PropertyHandler {

    private Properties prop = new Properties();

    public String getUsername() throws IOException {
        InputStream input = PropertyHandler.class.getClassLoader().getResourceAsStream("gobii-configurator.properties");
        if (input == null){
            System.out.println("No web-configurator file found.");
            return null;
        }
        prop.load(input);
        return prop.getProperty("username");
    }

    public String getPassword() throws IOException {
        InputStream input = PropertyHandler.class.getClassLoader().getResourceAsStream("gobii-configurator.properties");
        if (input == null){
            System.out.println("No web-configurator file found.");
            return null;
        }
        prop.load(input);
        return prop.getProperty("password");
    }

}
