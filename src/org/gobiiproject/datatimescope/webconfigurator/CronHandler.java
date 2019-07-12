package org.gobiiproject.datatimescope.webconfigurator;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CronHandler {

    private Crop currentCrop;
    private ArrayList<String> errorMessages = new ArrayList<>();


    public boolean reloadCrons(String hostFromXml){
        boolean success = false;
        errorMessages = new ArrayList<>();
        if (currentCrop.getName() == null){
            errorMessages.add("Please specify a crop.");
        } else if (currentCrop.getFileAge() > 59 || currentCrop.getFileAge() < 1 || currentCrop.getCron() > 59 || currentCrop.getCron() < 1){
            errorMessages.add("Please choose a valid value between 1 and 59. The default setting is 2.");
        } else {
            modifyCron("update", hostFromXml);
            success = true;
        }
        return success;
    }

    /**
     * Wrapper that handles the different types the User can interact with CRON Jobs
     * Creating a new CRON with default values
     * Updating a CRON changing the values for a crops existing job
     * Deleting both CRONS for an existing crop
     * @param modification
     */
    public void modifyCron(String modification, String hostFromXml){
        String[] read = {
                "ssh",
                "gadm@cbsugobiixvm14.biohpc.cornell.edu",
                "docker exec gobii-compute-node bash -c 'crontab -u gadm -l'"
        };
        try {
            Process proc = new ProcessBuilder(read).start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            switch (modification) {
                case ("create"): {
                    createCron(stdInput);
                    break;
                }
                case ("update"): {
                    updateCron(stdInput);
                    break;
                }
                case ("delete"): {
                    deleteCron(stdInput);
                    break;
                }
            }
            Runtime.getRuntime().exec("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/dockerCopyCron.sh " + hostFromXml);
        } catch (IOException e){
            e.printStackTrace();
        }
        /*
        TODO on deploy
        String dockerCopyCron = "/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/dockerCopyCron.sh " + xmlHandler.getHostForReload;

        new ProcessBuilder(dockerCopyCron).start();
        */
    }

    private void createCron(BufferedReader stdInput) throws IOException {
        ArrayList<String> newJobs = new ArrayList<>();
        String line = null;
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

    private void updateCron(BufferedReader stdInput) throws IOException {
        ArrayList<String> newJobs = new ArrayList<>();
        String line = null;
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

    private void deleteCron(BufferedReader stdInput) throws IOException {
        ArrayList<String> newJobs = new ArrayList<>();
        String line = null;
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

    public void setCurrentCrop(Crop currentCrop) {
        this.currentCrop = currentCrop;
    }

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }
}
