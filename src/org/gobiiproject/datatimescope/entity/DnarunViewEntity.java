
package org.gobiiproject.datatimescope.entity;

public class DnarunViewEntity {
  
    private Integer dnarunId;
    private String dnarunName;
    private Integer dnasampleId;
    private String dnasampleName;
    private Integer experimentId;
    private String experimentName;
    private Integer projectId;
    private String projectName;
    
    public DnarunViewEntity(Integer dnarunId, String dnarunName, Integer dnasampleId, String dnasampleName, Integer experimentId, String experimentName, Integer projectId, String projectName) {

        setDnarunId(dnarunId);
        setDnasampleId(dnasampleId);
        setExperimentId(experimentId);
        setProjectId(projectId);
        setDnarunName(dnarunName);
        setDnasampleName(dnasampleName);
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

    public Integer getDnasampleId() {
        return dnasampleId;
    }

    public void setDnasampleId(Integer dnasampleId) {
        this.dnasampleId = dnasampleId;
    }

    public String getDnasampleName() {
        return dnasampleName;
    }

    public void setDnasampleName(String dnasampleName) {
        this.dnasampleName = dnasampleName;
    }
    
}