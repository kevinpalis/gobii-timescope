package org.gobiiproject.datatimescope.webconfigurator;

import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;

public class Crop {

    private int cron;
    private String name;
    private int fileAge;
    private String databaseName;
    private String WARName;
    private boolean isActive;
    private boolean changeActivity;
    private boolean activityChanged;
    private XmlModifier xmlHandler = new XmlModifier();
    private Media contactData;
    private boolean hideContactData = true;

    public int getCron() {
        return cron;
    }

    public void setCron(int cron) {
        this.cron = cron;
    }

    public String getName() {
        return name;
    }

    @NotifyChange("isActive")
    public void setName(String name) {
        this.name = name;
        if (xmlHandler.getCropList().contains(this.name)) {
            this.isActive = xmlHandler.getActivity(this.name);
        }
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

    public boolean getIsActive() {
        return isActive;
    }

    @NotifyChange("isActive")
    public void setIsActive(boolean makeActive) {
        this.isActive = makeActive;
    }

    public String getContactData() {
        return contactData.getName();
    }

    @NotifyChange("hideContactData")
    public void setContactData(Media contactData) {
        this.contactData = contactData;
        this.hideContactData = false;
    }

    public boolean getHideContactData() {
        return hideContactData;
    }

    public void setHideContactData(boolean hideData) {
        this.hideContactData = hideData;
    }


    public boolean getChangeActivity() {
        return changeActivity;
    }

    @NotifyChange({"isActive", "changeActivity"})
    public void setChangeActivity(boolean changeActivity){
        if (changeActivity){
            if (isActive) {
                xmlHandler.setActivity(this, false);
                isActive = false;
            } else {
                xmlHandler.setActivity(this, true);
                isActive = true;
            }
            activityChanged = true;
        }
    }

    public boolean isActivityChanged() {
        return activityChanged;
    }

    public void setActivityChanged(boolean activityChanged) {
        this.activityChanged = activityChanged;
    }
}
