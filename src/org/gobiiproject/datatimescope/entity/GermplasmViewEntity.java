
package org.gobiiproject.datatimescope.entity;

public class GermplasmViewEntity {
  
    private Integer germplasmId;
    private String germplasmName;
    private String externalCode;
    private Integer dnasampleId;
    private String dnasampleName;
    private Integer dnarunId;
    private String dnarunName;
    
    
    public GermplasmViewEntity(Integer germplasmId, String germplasmName, String externalCode, Integer dnasampleId, String dnasampleName, Integer dnarunId, String dnarunName) {

        setDnasampleId(dnasampleId);
        setDnasampleName(dnasampleName);
        setExternalCode(externalCode);
        setGermplasmId(germplasmId);
        setGermplasmName(germplasmName);
        setDnarunId(dnarunId);
        setDnarunName(dnarunName);
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
    public String getExternalCode() {
        return externalCode;
    }
    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
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
}