package org.gobiiproject.datatimescope.webconfigurator;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gobiiproject.datatimescope.entity.ServerInfo;

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.scriptExecutor;
import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.writeToLog;
import static org.zkoss.zk.ui.util.Clients.alert;

/**
 * A class responsible for any changes made to CRON schedules for individual Crops
 * The options being modifying existing CRONs, creating new CRONs for new or reactivated Crops or deleting CRONs for deleted or deactivated Crops
 */

public class CronHandler {

    private String username;

    private ArrayList<String> errorMessages = new ArrayList<>();

    private ServerInfo serverinfo;

    public CronHandler(String name, ServerInfo serverinfo){
        username = name;
        this.setServerinfo(serverinfo);
    }

    /**
     * Performs a rudimentary validation to make sure the format works in a sensical way and changes the CRON of the given Crop
     * to the user provided values
     * @param hostFromXml Currently web-node host pulled from the XML file, should be changed to compute host once known
     * @param currentCrop The crop we are modifying the CRON jobs for
     * @return true, if successful change was made.
     */
    public boolean reloadCrons(String hostFromXml, Crop currentCrop){
        boolean success = false;
        errorMessages = new ArrayList<>();
        if (currentCrop.getName() == null){
            errorMessages.add("Please specify a crop.");
            writeToLog("CronHandler.reloadCrons()", "No crop was chosen.", username);
        } else if (currentCrop.getFileAge() > 59 || currentCrop.getFileAge() < 1 || currentCrop.getCron() > 59 || currentCrop.getCron() < 1){
            errorMessages.add("Please choose a valid value between 1 and 59. The default setting is 2.");
            writeToLog("CronHandler.reloadCrons()", "Invalid minute selection for the crop " + currentCrop.getName() + ".", username);
        } else {
            if (modifyCron("update", getServerinfo().getHost(), currentCrop)) {
                writeToLog("CronHandler.reloadCrons()", "CRON jobs for the crop " + currentCrop.getName() + " have been adjusted.", username);
                success = true;
            } else {
                writeToLog("CronHandler.reloadCrons()", "CRON jobs for the crop " + currentCrop.getName() + " have failed to be adjusted.", username);
                success = false;
            }
        }
        return success;
    }

    /**
     * Wrapper that handles the different types the User can interact with CRON Jobs
     * Creating a new CRON with default values
     * Updating a CRON changing the values for a crops existing job with user values
     * Deleting both CRONS for an existing crop
     * @param modification switch for what to do, options are create, delete or update
     * @param hostFromXml Currently web-node host pulled from the XML file, should be changed to compute host once known
     * @param currentCrop The crop we are applying the CRON job change to
     */
    public boolean modifyCron(String modification, String hostFromXml, Crop currentCrop){
        String sshHost = "gadm@"+ getServerinfo().getHost();
        String scriptPath = UtilityFunctions.getScriptPath("dockerCopyCron.sh");
        
        String[] read = {
                "ssh",
                sshHost,
                "docker exec gobii-compute-node bash -c 'crontab -u gadm -l'"
        };
        try {
            //Read the current crontab into a Buffer
            
            
            Process proc = new ProcessBuilder(read).start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            switch (modification) {
                case ("create"): {
                    createCron(stdInput, currentCrop);
                    break;
                }
                case ("rename"): {
                    renameCron(stdInput, currentCrop);
                    break;
                }
                case ("update"): {
                    updateCron(stdInput, currentCrop);
                    break;
                }
                case ("delete"): {
                    deleteCron(stdInput, currentCrop);
                    break;
                }
            }

            String makeExecutable = "chmod +x "+ scriptPath;
            Process p = Runtime.getRuntime().exec(makeExecutable);
            //Send the new CRONs back to the server
            Runtime.getRuntime().exec(
                	String.format("%s %s", scriptPath,  getServerinfo().getHost())
            );
            //Runtime.getRuntime().exec("/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/dockerCopyCron.sh " + getServerinfo().getHost());
            writeToLog("CronHandler.modifyCron()", "Sending the CRONS for the crop " + currentCrop.getName() + " back to the server succeeded.", username);
            
            if(!modification.equalsIgnoreCase("delete")) {
                String scriptPath2 = UtilityFunctions.getScriptPath("runCronJob.sh");
                String makeExecutable2 = "chmod +x "+ scriptPath2;
                Runtime.getRuntime().exec(makeExecutable2);
                String cropname=currentCrop.getName();
                
                if(modification.equalsIgnoreCase("rename")) {
                    cropname=currentCrop.getRename();
                }
                
                List<String> runCrobJobSH = new ArrayList<>(Arrays.asList(getServerinfo().getHost(),cropname,"+2"));
                if (!scriptExecutor("runCronJob.sh", runCrobJobSH)){
                    writeToLog("CronHandler.modifyCron()", "Runnung the runCronJob sh file failed.", username);
                }
            }
            return true;
            //Runtime.getRuntime().exec("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/dockerCopyCron.sh " + getServerinfo().getHost());
        } catch (IOException e){
            e.printStackTrace();
            alert("Sending the CRONS for the crop " + currentCrop.getName() + " back to the server failed. "+e.getLocalizedMessage()+"\n"+e.getMessage()+"\n");
            writeToLog("CronHandler.modifyCron()", "Sending the CRONS for the crop " + currentCrop.getName() + " back to the server failed.", username);
            return false;
        }
    }

    /**
     * Appends the new CRON jobs for the new Crop to the Crontab using the default values of 2
     * @param stdInput A buffer containing all the current CRONs
     * @param currentCrop The new Crop for which we are adding the jobs
     */
    private void createCron(BufferedReader stdInput, Crop currentCrop) throws IOException {
        ArrayList<String> newJobs = new ArrayList<>();
        String line;
        while ((line = stdInput.readLine()) != null) {
            if (line.equals("")){
                break;
            }
            newJobs.add(line);
        }
        newJobs.add("*/2 * * * * /data/gobii_bundle/loaders/cronjob.sh " + currentCrop.getName() + " +2");
        newJobs.add("*/2 * * * * /data/gobii_bundle/extractors/cronjob.sh " + currentCrop.getName() + " +2");
        FileWriter writer = new FileWriter("newCrons.txt");
        for(String str: newJobs) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Modifies the current CRON jobs for the provided Crop to the values from the user
     * @param stdInput A buffer containing all the current CRONs
     * @param currentCrop The Crop for which we are changing the jobs
     */
    private void updateCron(BufferedReader stdInput, Crop currentCrop) throws IOException {

        ArrayList<String> newJobs = new ArrayList<>();
        String line;
        while ((line = stdInput.readLine()) != null) {
            if (line.equals("")){
                break;
            }
            String [] input = line.split(" ");
            if (input[6].equals(currentCrop.getName())){
                input[7] = "+" + currentCrop.getFileAge();
                input[0] = "*/" + currentCrop.getCron();
                newJobs.add(String.join(" ", input));
            } else {
                newJobs.add(line);
            }
        }
        FileWriter writer = new FileWriter("newCrons.txt");
        for(String str: newJobs) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }
    
    /**
     * Renames the current CRON jobs for the provided Crop to the new cropname
     * @param stdInput A buffer containing all the current CRONs
     * @param currentCrop The Crop for which we are changing the jobs
     */
    private void renameCron(BufferedReader stdInput, Crop currentCrop) throws IOException {
       
        ArrayList<String> newJobs = new ArrayList<>();
        String line;
        while ((line = stdInput.readLine()) != null) {
            if (line.equals("")){
                break;
            }
            newJobs.add(line);
        }
        newJobs.add("*/2 * * * * /data/gobii_bundle/loaders/cronjob.sh " + currentCrop.getRename() + " +2");
        newJobs.add("*/2 * * * * /data/gobii_bundle/extractors/cronjob.sh " + currentCrop.getRename() + " +2");
        FileWriter writer = new FileWriter("newCrons.txt");
        for(String str: newJobs) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
//        ArrayList<String> newJobs = new ArrayList<>();
//        String line;
//        while ((line = stdInput.readLine()) != null) {
//            if (line.equals("")){
//                break;
//            }
//            String [] input = line.split(" ");
//            if (input[6].equals(currentCrop.getName())){
//                input[6]=newCropName;
//                newJobs.add(line);
//            } else {
//                newJobs.add(line);
//            }
//        }
//        FileWriter writer = new FileWriter("newCrons.txt");
//        for(String str: newJobs) {
//            writer.write(str + System.lineSeparator());
//        }
//        writer.close();
    }

    /**
     * Removes the CRON jobs for the provided Crop
     * @param stdInput A buffer containing all the current CRONs
     * @param currentCrop The Crop for which we are deleting the jobs
     */
    private void deleteCron(BufferedReader stdInput, Crop currentCrop) throws IOException {
        ArrayList<String> newJobs = new ArrayList<>();
        String line;
        while ((line = stdInput.readLine()) != null) {
            if (line.equals("")){
                break;
            }
            String [] input = line.split(" ");
            if (!input[6].equals(currentCrop.getName())) {
                newJobs.add(line);
            }
        }
        FileWriter writer = new FileWriter("newCrons.txt");
        for(String str: newJobs) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    public ServerInfo getServerinfo() {
        return serverinfo;
    }

    public void setServerinfo(ServerInfo serverinfo) {
        this.serverinfo = serverinfo;
    }
}
