package org.gobiiproject.datatimescope.webconfigurator;

import java.io.IOException;
import java.util.List;

public class utilityFunctions {

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
        //TODO on deploy
        String scriptPath = "/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/" + scriptName;
        scriptParameters.add(0, scriptPath);
        String[] fullCommand = scriptParameters.toArray(new String[0]);
        try {
            new ProcessBuilder(fullCommand).start();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

}
