package org.gobiiproject.datatimescope.webconfigurator;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;

import static org.zkoss.zk.ui.util.Clients.alert;

/**
 * A class for general small utility functions that are called multiple time within the webconfigurator package
 *
 */
public class UtilityFunctions {


    private static final Logger log = Logger.getLogger("../logs/ConfigManager");
    private static boolean configured = false;

    /**
     * @param messages List of messages to be concatenated into one Alert error message
     * @return One String to be passed to an alert() call
     */
    public static String generateAlertMessage(List<String> messages){
        StringBuilder sb = new StringBuilder();
        for (String str : messages) {
            sb.append(str);
            sb.append("\n");
        }
         return sb.toString();
    }


    /**
     * Execute the giveen script with the given parameters
     * @param scriptName scriptname to be executed
     * @param scriptParameters parameters to be passed to the script in List format
     * @return true if script executed correctly
     */
    public static boolean scriptExecutor(String scriptName, List<String> scriptParameters){
        boolean success;
        String scriptPath = "/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/" + scriptName;
        //String scriptPath = "/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/" + scriptName;
        scriptParameters.add(0, scriptPath);
        String[] fullCommand = scriptParameters.toArray(new String[0]);
        try {
            new ProcessBuilder(fullCommand).start();
            success = true;
        } catch (IOException e) {
            alert("Script " + scriptName + " execution failed with following error: \n" + e.toString());
            success = false;
        }
        return success;
    }

    public static void writeToLog(String context, String message, String username){
        if (!configured){
            try {
                ConsoleHandler consoleHandler = new ConsoleHandler();
                SimpleFormatter simpleFormatter = new SimpleFormatter();
                FileHandler fileHandler = new FileHandler("../logs/ConfigManager.log");
                log.addHandler(fileHandler);
                fileHandler.setFormatter(simpleFormatter);
                log.addHandler(consoleHandler);
                configured = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info(username + ": The calling function is " + context  + ", with following message:\n\t" + message);
        // Somehow on shutdown need to get rid of the handlers
    }
}
