package org.gobiiproject.datatimescope.webconfigurator;

import org.zkoss.bind.annotation.NotifyChange;

public class Crop {

    private int cron;
    private String name;
    private int fileAge;
    private String databaseName;
    private String WARName;
    private boolean makeActive;
    private xmlModifier xmlHandler = new xmlModifier();

    public int getCron() {
        return cron;
    }

    public void setCron(int cron) {
        this.cron = cron;
    }

    public String getName() {
        return name;
    }

    @NotifyChange("makeActive")
    public void setName(String name) {
        this.name = name;
        this.makeActive = xmlHandler.getActivity(this);
    }

    public int getFileAge() {
        return fileAge;
    }

    public void setFileAge(int fileAge) {
        this.fileAge = fileAge;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getWARName() {
        return WARName;
    }

    public void setWARName(String WARName) {
        this.WARName = WARName;
    }

    public boolean isMakeActive() {
        return makeActive;
    }

    public void setMakeActive(boolean makeActive) {
        this.makeActive = makeActive;
    }
}
