package org.gobiiproject.datatimescope.webconfigurator;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A class designated to handle any modifications made to the backup schedule of the database
 * Furthermore it also creates the backup CRON jobs if they do not exist yet
 */

public class BackupHandler {

    /**
     * Sets up the weekday array for the dropdown menu of weekday selection
     * Reads in the current Crontabs, puts them into a temporary file, and reads that file to display the current information back to the user
     */
    public BackupHandler(){
        weekdays.add("Monday");
        weekdays.add("Tuesday");
        weekdays.add("Wednesday");
        weekdays.add("Thursday");
        weekdays.add("Friday");
        weekdays.add("Saturday");
        weekdays.add("Sunday");
        String[] read = {
                "ssh",
                "gadm@cbsugobiixvm14.biohpc.cornell.edu",
                "docker exec gobii-compute-node bash -c 'crontab -u gadm -l'"
        };
        try {
            Process proc = new ProcessBuilder(read).start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            ArrayList<String> oldJobs = new ArrayList<>();
            String line;
            while ((line = stdInput.readLine()) != null) {
                if (line.equals("")){
                    break;
                }
                oldJobs.add(line);
            }
            setCurrentCrons(oldJobs);
            readDataFromCrons();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> errorMessages = new ArrayList<>();
    private Date backupDayTime;
    private String backupWeekday;
    private Date backupWeekTime;
    private String backupMonthDay;
    private Date backupMonthTime;
    private ArrayList<String> currentCrons;
    private boolean daily = false;
    private boolean weekly = false;
    private boolean monthly = false;
    private List<String> weekdays = new ArrayList<>();

    public List<String> getWeekdays() {
        return weekdays;
    }

    public Date getBackupDayTime() {
        return backupDayTime;
    }

    public void setBackupDayTime(Date backupDayTime) {
        this.backupDayTime = backupDayTime;
    }

    public String getBackupWeekday() {
        return backupWeekday;
    }

    public void setBackupWeekday(String backupWeekday) {
        this.backupWeekday = backupWeekday;
    }

    public Date getBackupWeekTime() {
        return backupWeekTime;
    }

    public void setBackupWeekTime(Date backupWeekTime) {
        errorMessages = new ArrayList<>();
        this.backupWeekTime = backupWeekTime;
        if (this.backupWeekTime.equals(backupDayTime)){
            errorMessages.add("Daily and weekly backups cannot overlap. Please change one of the times.");
        }
    }

    public ArrayList<String> getErrorMessages(){
        return errorMessages;
    }

    public String getBackupMonthDay() {
        return backupMonthDay;
    }

    public void setBackupMonthDay(String backupMonthDay) {
        this.backupMonthDay = backupMonthDay;
    }

    public Date getBackupMonthTime() {
        return backupMonthTime;
    }

    public void setBackupMonthTime(Date backupMonthTime) {
        this.backupMonthTime = backupMonthTime;
    }

    public ArrayList<String> getCurrentCrons() {
        return currentCrons;
    }

    public void setCurrentCrons(ArrayList<String> currentCron) {
        this.currentCrons = currentCron;
    }

    /**
     * Read in all current CRON jobs, pattern match for the backup calls and save them to local members for display and modification
     */

    public void readDataFromCrons() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            for (String line : currentCrons) {
                String[] input = line.split(" ");
                if (input.length > 9 && input[9].matches("^.*/weekly")) {
                    backupWeekday = DayOfWeek.of(Integer.parseInt(input[4])).name();
                    String timeInString = input[1] + ":" + input[0];
                    backupWeekTime = formatter.parse(timeInString);
                    weekly = true;
                } else if (input.length > 8 && input[8].matches("^.*/daily")) {
                    String timeInString = input[1] + ":" + input[0];
                    backupDayTime = formatter.parse(timeInString);
                    daily = true;
                } else if (input.length > 7 && input[7].matches("^.*/monthly/.*")) {
                    backupMonthDay = input[2];
                    String timeInString = input[1] + ":" + input[0];
                    backupMonthTime = formatter.parse(timeInString);
                    monthly = true;
                }
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    /**
     * Create the CRON jobs if it doesn't exist yet, otherwise modify the values according to the user input
     * This also calls the script to send the jobs back to the server to make them active
     * @param hostFromXml web-node host read in from the gobii-web.xml, should be changed to compute-node host once known
     * @return
     */
    public boolean saveDataToCrons(String hostFromXml) {
        boolean success = false;
        errorMessages = new ArrayList<>();
        ArrayList<String> retJobs = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        if (daily && weekly && monthly) {
            for (String line : currentCrons) {
                String[] input = line.split(" ");
                if (input.length <= 7) {
                    retJobs.add(line);
                } else if (input.length > 8 && input[8].matches("^.*/daily")) {
                    String[] tm = formatter.format(backupDayTime).split(":");
                    input[1] = tm[0];
                    input[0] = tm[1];
                    retJobs.add(String.join(" ", input));
                } else if (input.length > 9 && input[9].matches("^.*/weekly")) {
                    input[4] = String.valueOf(DayOfWeek.valueOf(backupWeekday).getValue());
                    String[] tm = formatter.format(backupWeekTime).split(":");
                    input[1] = tm[0];
                    input[0] = tm[1];
                    retJobs.add(String.join(" ", input));
                } else if (input[7].matches("^.*/monthly/.*")) {
                    input[2] = backupMonthDay;
                    String[] tm = formatter.format(backupMonthTime).split(":");
                    input[1] = tm[0];
                    input[0] = tm[1];
                    retJobs.add(String.join(" ", input));
                } else {
                    retJobs.add(line);
                }
            }
        } else {
            retJobs = currentCrons;
            if (!daily){
                String[] tm = formatter.format(backupDayTime).split(":");
                retJobs.add(tm[1] + " " + tm[0] + " * * * bash /shared_data/bamboo_gobii_backups/gobiideployment/backup_data_bundle.sh /shared_data/dev_test/gobii_bundle /shared_data/bamboo_gobii_backups/qa_test_incremental/daily 2");
            }
            if (!weekly){
                String day = String.valueOf(DayOfWeek.valueOf(backupWeekday.toUpperCase()).getValue());
                String[] tm = formatter.format(backupWeekTime).split(":");
                retJobs.add(tm[1] + " " + tm[0] + " * * " + day + " rsync -ah --delete /shared_data/bamboo_gobii_backups/qa_test_incremental/daily /shared_data/bamboo_gobii_backups/qa_test_incremental/weekly");
            }
            if (!monthly){
                String[] tm = formatter.format(backupMonthTime).split(":");
                retJobs.add(tm[1] + " " + tm[0] + " " + backupMonthDay + " * * tar -cvjf /shared_data/bamboo_gobii_backups/qa_test_incremental/monthly/monthly_$(date +%Y%m%d).tar.bz2 /shared_data/bamboo_gobii_backups/qa_test_incremental/daily/");
            }
        }
        daily = false;
        weekly = false;
        monthly = false;
        FileWriter writer;
        try {
            writer = new FileWriter("newCrons.txt");
            for (String str : retJobs) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            Runtime.getRuntime().exec("/usr/local/tomcat/webapps/timescope/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/dockerCopyCron.sh " + hostFromXml);
            //Runtime.getRuntime().exec("/home/fvgoldman/gobiidatatimescope/out/artifacts/gobiidatatimescope_war_exploded/WEB-INF/classes/org/gobiiproject/datatimescope/webconfigurator/scripts/dockerCopyCron.sh " + hostFromXml);
            success = true;
        } catch (IOException e) {
            errorMessages.add("Something went wrong upon creating the file for the Cron Jobs.");
        }
        return success;
    }
}
