
package org.gobiiproject.datatimescope.entity;

public class DnarunViewEntity {
  
    private Integer dnarunId;
    private String dnarunName;
    private Integer dnasampleId;
    private String dnasampleName;
    private Integer germplasmId;
    private String germplasmName;
    private Integer experimentId;
    private String experimentName;
    private Integer projectId;
    private String projectName;
    private String uuid;
    
    public DnarunViewEntity(Integer dnarunId, String dnarunName, Integer dnasampleId, String dnasampleName, String uuid, Integer germplasmId, String germplasmName, Integer experimentId, String experimentName, Integer projectId, String projectName) {

        setDnarunId(dnarunId);
        setDnasampleId(dnasampleId);
        setExperimentId(experimentId);
        setProjectId(projectId);
        setDnarunName(dnarunName);
        setDnasampleName(dnasampleName);
        setGermplasmId(germplasmId);
        setGermplasmName(germplasmName);
        setExperimentName(experimentName);
        setProjectName(projectName);
        setUuid(uuid);
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

    public Integer getGermplasmId() {
        return germplasmId;
    }

    public void setGermplasmId(Integer germplasmId) {
        this.germplasmId = germplasmId;
    }

    public String getGermplasmName() {
        return germplasmName;
    }

    public void setGermplasmName(String germplasmName) {
        this.germplasmName = germplasmName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
}