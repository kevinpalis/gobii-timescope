
package org.gobiiproject.datatimescope.entity;

import org.gobiiproject.datatimescope.db.generated.tables.VMarkerSummary;

public class DnarunViewEntity {
  
    private Integer dnarunId;
    private String dnarunName;
    private Integer experimentId;
    private String experimentName;
    private Integer projectId;
    private String projectName;
    
    public DnarunViewEntity(Integer dnarunId, String dnarunName, Integer experimentId, String experimentName, Integer projectId, String projectName) {

        setDnarunId(dnarunId);
        setExperimentId(experimentId);
        setProjectId(projectId);
        setDnarunName(dnarunName);
        setExperimentName(experimentName);
        setProjectName(projectName);
    }
    
    public Integer getDnarunId() {
        return dnarunId;
    }
    public void setDnarunId(Integer dnarunId) {
        this.dnarunId = dnarunId;
    }
    public String getDnarunName() {
        return dnarunName;
    }
    public void setDnarunName(String dnarunName) {
        this.dnarunName = dnarunName;
    }
    public Integer getExperimentId() {
        return experimentId;
    }
    public void setExperimentId(Integer experimentId) {
        this.experimentId = experimentId;
    }
    public String getExperimentName() {
        return experimentName;
    }
    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }
    public Integer getProjectId() {
        return projectId;
    }
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
}