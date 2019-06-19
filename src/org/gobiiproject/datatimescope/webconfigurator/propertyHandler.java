package org.gobiiproject.datatimescope.webconfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class propertyHandler {


    private Properties prop = new Properties();
    private String path = "/home/fvgoldman/gobiidatatimescope/src/org/gobiiproject/datatimescope/webconfigurator/web-configurator.properties";

    public String getUsername() throws IOException {
        InputStream input = new FileInputStream(path);
        prop.load(input);
        return prop.getProperty("username");
    }

    public String getPassword() throws IOException {
        InputStream input = new FileInputStream(path);
        prop.load(input);
        return prop.getProperty("password");
    }

}
