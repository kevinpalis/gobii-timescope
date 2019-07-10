package org.gobiiproject.datatimescope.webconfigurator;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

public class backupHandler {

    private String backupDayTime;
    private String backupWeekday;
    private String backupWeekTime;
    private String backupMonthDay;
    private String backupMonthTime;
    private ArrayList<String> currentCrons;
    private boolean daily = false;
    private boolean weekly = false;
    private boolean monthly = false;
    private ArrayList<String> errors = new ArrayList<>();

    public String getBackupDayTime() {
        return backupDayTime;
    }

    public void setBackupDayTime(String backupDayTime) {
        String[] tm = backupDayTime.split("");

        this.backupDayTime = backupDayTime;
    }

    public String getBackupWeekday() {
        return backupWeekday;
    }

    public void setBackupWeekday(String backupWeekday) {
        this.backupWeekday = backupWeekday;
    }

    public String getBackupWeekTime() {
        return backupWeekTime;
    }

    public void setBackupWeekTime(String backupWeekTime) {
        this.backupWeekTime = backupWeekTime;
        if (this.backupWeekTime.equals(backupDayTime)){
            errors.add("Daily and weekly backups cannot overlap. Please change one of the times.");
        }
    }

    public ArrayList<String> getErrors(){
        return errors;
    }

    public String getBackupMonthDay() {
        return backupMonthDay;
    }

    public void setBackupMonthDay(String backupMonthDay) {
        this.backupMonthDay = backupMonthDay;
    }

    public String getBackupMonthTime() {
        return backupMonthTime;
    }

    public void setBackupMonthTime(String backupMonthTime) {
        this.backupMonthTime = backupMonthTime;
    }

    public ArrayList<String> getCurrentCrons() {
        return currentCrons;
    }

    public void setCurrentCrons(ArrayList<String> currentCron) {
        this.currentCrons = currentCron;
    }

    public void readDataFromCrons() {
        for (String line : currentCrons) {
            String[] input = line.split(" ");
            if (input.length > 8 && input[8].matches("^.*/daily")) {
                backupDayTime = input[1] + ":" + input[0];
                daily = true;
            } else if (input.length > 9 && input[9].matches("^.*/weekly")) {
                backupWeekday = DayOfWeek.of(Integer.parseInt(input[4])).name();
                backupWeekTime = input[1] + ":" + input[0];
                weekly = true;
            } else if (input.length > 7 && input[7].matches("^.*/monthly/.*")) {
                backupMonthDay = input[2];
                backupMonthTime = input[1] + ":" + input[0];
                monthly = true;
            }
        }
    }

    public ArrayList<String> saveDataToCrons() {
        ArrayList<String> retJobs = new ArrayList<>();
        if (daily && weekly && monthly) {
            for (String line : currentCrons) {
                String[] input = line.split(" ");
                if (input.length <= 7) {
                    retJobs.add(line);
                } else if (input.length > 8 && input[8].matches("^.*/daily")) {
                    String[] tm = backupDayTime.split(":");
                    input[1] = tm[0];
                    input[0] = tm[1];
                    retJobs.add(String.join(" ", input));
                } else if (input.length > 9 && input[9].matches("^.*/weekly")) {
                    input[4] = String.valueOf(DayOfWeek.valueOf(backupWeekday).getValue());
                    String[] tm = backupWeekTime.split(":");
                    input[1] = tm[0];
                    input[0] = tm[1];
                    retJobs.add(String.join(" ", input));
                } else if (input[7].matches("^.*/monthly/.*")) {
                    input[2] = backupMonthDay;
                    String[] tm = backupMonthTime.split(":");
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
                String[] tm = backupDayTime.split(":");
                retJobs.add(tm[1] + " " + tm[0] + " * * * bash /shared_data/bamboo_gobii_backups/gobiideployment/backup_data_bundle.sh /shared_data/dev_test/gobii_bundle /shared_data/bamboo_gobii_backups/qa_test_incremental/daily 2");
            }
            if (!weekly){
                String day= String.valueOf(DayOfWeek.valueOf(backupWeekday).getValue());
                String[] tm = backupWeekTime.split(":");
                retJobs.add(tm[1] + " " + tm[0] + " * * " + day + " rsync -ah --delete /shared_data/bamboo_gobii_backups/qa_test_incremental/daily /shared_data/bamboo_gobii_backups/qa_test_incremental/weekly");
            }
            if (!monthly){
                String[] tm = backupMonthTime.split(":");
                retJobs.add(tm[1] + " " + tm[0] + " " + backupMonthDay + "* * tar -cvjf /shared_data/bamboo_gobii_backups/qa_test_incremental/monthly/monthly_$(date +%Y%m%d).tar.bz2 /shared_data/bamboo_gobii_backups/qa_test_incremental/daily/");
            }
        }
        daily = false;
        weekly = false;
        monthly = false;
        return retJobs;
    }
}
