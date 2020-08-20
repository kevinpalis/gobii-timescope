package org.gobiiproject.datatimescope.webconfigurator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.*;

import org.zkoss.zul.Messagebox;

import static org.zkoss.zk.ui.util.Clients.alert;

/**
 * A class for general small utility functions that are called multiple time within the webconfigurator package
 *
 */

public class UtilityFunctions {


    private static final Logger log = Logger.getLogger("../logs/ConfigManager");
    @SuppressWarnings("unused")
	private static boolean configured = false;
    
    private static String SCRIPTS_DIR = "";
    
    static {
    	//check if the script path is in env
    	String envPath = System.getenv("WEBCONFIGURATOR_SCRIPTS_DIR");
    	if (envPath != null && envPath != "") {
    		SCRIPTS_DIR = envPath;
    	} else {
    		SCRIPTS_DIR = UtilityFunctions.class.getResource("./scripts/").getFile();
    	}
    	
    	SCRIPTS_DIR = SCRIPTS_DIR.trim();
    	if (!SCRIPTS_DIR.endsWith(File.separator)) SCRIPTS_DIR += File.separator;
		//if (!SCRIPTS_DIR.endsWith("/")) SCRIPTS_DIR += "/";
    	log.info(String.format("WebConfigurator Scripts Dir : %s", SCRIPTS_DIR));
    }
    	

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
        boolean success = false;
        //String scriptPath = "/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/" + scriptName;
        //String scriptPath = "/home/fvgoldman/gobiidatatimescope/src/org/gobiiproject/datatimescope/webconfigurator/scripts/" + scriptName;
        String scriptPath = getScriptPath(scriptName);
        
        scriptParameters.add(0, scriptPath);
        String[] fullCommand = scriptParameters.toArray(new String[0]);
//        Messagebox.show(generateAlertMessage(scriptParameters), "Script that ran", Messagebox.OK, Messagebox.EXCLAMATION);
        try {
            //make sure the script is runnable
            String makeExecutable = "chmod +x "+ scriptPath;
            Process p = Runtime.getRuntime().exec(makeExecutable);
            
            ProcessBuilder pb = new ProcessBuilder(fullCommand);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            //uncomment for debug mode
//            //Read output and show as a messageBox
//            StringBuilder out = new StringBuilder();
//            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line = null, previous = null;
//            while ((line = br.readLine()) != null) {
//                if (!line.equals(previous)) {
//                    previous = line;
//                    out.append(line).append('\n');
//
//                }
//            }
//            Messagebox.show(out.toString(), "Script output of "+ scriptName, Messagebox.OK, Messagebox.EXCLAMATION);

          success = true;

            
            
        } catch (IOException e) {
            alert("Script " + scriptName + " execution failed with following error: \n" + e.toString());
            success = false;
        }
        return success;
    }
    
    public static String getScriptPath(String scriptName) {
    	String scriptPath =  SCRIPTS_DIR + scriptName;
    	if (!(new File(scriptPath)).exists()) {
    		log.warning(String.format("Script file for %s does not exist", scriptName));
    	}
    	return scriptPath;
    }


    public static void writeToLog(String context, String message, String username){
//        if (!configured){
//            try {
//                SimpleFormatter simpleFormatter = new SimpleFormatter();
//                FileHandler fileHandler = new FileHandler("../logs/ConfigManager.log");
//                log.addHandler(fileHandler);
//                fileHandler.setFormatter(simpleFormatter);
//                configured = true;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        String msg = username + ": The calling function is " + context  + ", with following message:\n\t" + message;
//        Messagebox.show(msg, "writeToLog", Messagebox.OK, Messagebox.EXCLAMATION);
        log.info(msg);
        // Somehow on shutdown need to get rid of the handlers
    }
}
