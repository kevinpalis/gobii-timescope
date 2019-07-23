package org.gobiiproject.datatimescope.webconfigurator;

import java.io.IOException;
import java.util.List;

import static org.zkoss.zk.ui.util.Clients.alert;

/**
 * A class for general small utility functions that are called multiple time within the webconfigurator package
 *
 */
public class UtilityFunctions {

    public static String generateAlertMessage(List<String> messages){
        StringBuilder sb = new StringBuilder();
        for (String str : messages) {
            sb.append(str);
            sb.append("\n");
        }
         return sb.toString();
    }


    public static boolean scriptExecutor(String scriptName, List<String> scriptParameters){
        boolean success;
        String scriptPath = "/usr/locat/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/" + scriptName;
        //String scriptPath = "/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/" + scriptName;
        scriptParameters.add(0, scriptPath);
        String[] fullCommand = scriptParameters.toArray(new String[0]);
        try {
            new ProcessBuilder(fullCommand).start();
            success = true;
        } catch (IOException e) {
            alert("Script " + scriptName + "execution failed with following error: \n" + e.toString());
            success = false;
        }
        return success;
    }

}
