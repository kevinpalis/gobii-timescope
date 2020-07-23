package org.gobiiproject.datatimescope.webconfigurator;

import org.zkoss.bind.annotation.NotifyChange;


/**
 * A small class that acts as a temporary way to modify aspects of an individual Crop.
 * Usages include creation and deletion of a crop, as well as any modifications of a Crop.
 * These currently are CRON schedules and activity of the Crop
 * Mostly an instance of this class will be partially populated by a .zul file
 * The exact members that are populated at any point are dependant on the usage
 */

public class Crop {

    private int cron;
    private String name;
    private int fileAge;
    private String databaseName;
    private String WARName;
    private boolean isActive;
    private boolean changeActivity;
    private boolean activityChanged;
    private XmlModifier xmlHandler;
    private String contactData;
    private String contactDataShort;
    private String typedName;
    private boolean hideContactData = true;
    private String username;

    public Crop(String name){
        username = name;
        xmlHandler = new XmlModifier(username);
    }

    public int getCron() {
        return cron;
    }

    public void setCron(int cron) {
        this.cron = cron;
    }

    public String getName() {
        return name;
    }

    @NotifyChange({"isActive", "databaseName", "WARName"})
    public void setName(String name) {
        this.name = name;
        this.WARName = ("gobii-" + name);
        this.databaseName = ("gobii_" + name);
        if (xmlHandler.getCropList().contains(this.name)) {
            this.isActive = xmlHandler.getActivity(this.name);
        }
    }

    public String getContextPath(){
        return WARName;
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
        return contactData;
    }

    @NotifyChange("hideContactData")
    public void setContactData(String contactData) {
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

    public String getContactDataShort() {
        String[] split = contactData.split("/");
        return split[split.length - 1];
    }

    public void setTypedName(String typedName) {
        this.typedName = typedName;
    }

    public String getTypedName() {
        return typedName;
    }
}
