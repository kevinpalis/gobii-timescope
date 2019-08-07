package org.gobiiproject.datatimescope.webconfigurator;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.writeToLog;
import static org.zkoss.zk.ui.util.Clients.alert;

/**
 * A class responsible for any changes made to CRON schedules for individual Crops
 * The options being modifying existing CRONs, creating new CRONs for new or reactivated Crops or deleting CRONs for deleted or deactivated Crops
 */

public class CronHandler {

    private String username;

    private ArrayList<String> errorMessages = new ArrayList<>();

    public CronHandler(String name){
        username = name;
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
            if (modifyCron("update", hostFromXml, currentCrop)) {
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
        String[] read = {
                "ssh",
                "gadm@cbsugobiixvm14.biohpc.cornell.edu",
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
                case ("update"): {
                    updateCron(stdInput, currentCrop);
                    break;
                }
                case ("delete"): {
                    deleteCron(stdInput, currentCrop);
                    break;
                }
            }
            //Send the new CRONs back to the server
            Runtime.getRuntime().exec("/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/dockerCopyCron.sh " + hostFromXml);
            writeToLog("CronHandler.modifyCron()", "Sending the CRONS for the crop " + currentCrop.getName() + " back to the server succeeded.", username);
            return true;
            //Runtime.getRuntime().exec("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/dockerCopyCron.sh " + hostFromXml);
        } catch (IOException e){
            e.printStackTrace();
            alert("Sending the CRONS for the crop " + currentCrop.getName() + " back to the server failed.");
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
}
