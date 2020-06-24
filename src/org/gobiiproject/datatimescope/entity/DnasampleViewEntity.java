
package org.gobiiproject.datatimescope.entity;

public class DnasampleViewEntity {
  
    private Integer dnasampleId;
    private String dnasampleName;
    private Integer germplasmId;
    private String germplasmName;
    private Integer projectId;
    private String projectName;
    private String code;
    private String plateName;
    private String wellRow;
    private String wellCol;
    private String uuid;
    
    
    public DnasampleViewEntity(Integer dnasampleId, String dnasampleName, String code, String plateName, String wellRow, String wellCol, String uuid, Integer germplasmId, String germplasmName, Integer projectId, String projectName) {

        setDnasampleId(dnasampleId);
        setDnasampleName(dnasampleName);
        setCode(code);
        setPlateName(plateName);
        setWellRow(wellRow);
        setWellCol(wellCol);
        setUuid(uuid);
        setGermplasmId(germplasmId);
        setGermplasmName(germplasmName);
        setProjectId(projectId);
        setProjectName(projectName);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getWellRow() {
        return wellRow;
    }

    public void setWellRow(String wellRow) {
        this.wellRow = wellRow;
    }

    public String getWellCol() {
        return wellCol;
    }

    public void setWellCol(String wellCol) {
        this.wellCol = wellCol;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}