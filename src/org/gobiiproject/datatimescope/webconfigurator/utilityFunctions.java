package org.gobiiproject.datatimescope.webconfigurator;

import java.io.IOException;
import java.util.Arrays;
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
        scriptParameters.add(0,scriptPath);
        Object[] fullCommand = scriptParameters.toArray();
        try {
            new ProcessBuilder(Arrays.toString(fullCommand)).start();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

}
