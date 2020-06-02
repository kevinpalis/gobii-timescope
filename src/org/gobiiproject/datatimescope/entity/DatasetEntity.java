/* 
timescoper_id integer NOT NULL DEFAULT nextval('timescoper_timescoper_id_seq'::regclass),
    firstname text COLLATE pg_catalog."default" NOT NULL,
    lastname text COLLATE pg_catalog."default" NOT NULL,
    username text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default",
    role integer DEFAULT 3,
    CONSTRAINT pk_timescoper PRIMARY KEY (timescoper_id),
    CONSTRAINT username_key UNIQUE (username)
*/
package org.gobiiproject.datatimescope.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.AnalysisRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ExperimentRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;

/**
 * User entity
 */
public class DatasetEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private Integer datasetIDStartRange;
	private Integer datasetIDEndRange;
	private String datasetNamesAsEnterSeparatedString;
	private List<String> datasetNames;
	private Date creationDateStart;
	private Date creationDateEnd;
	private CvRecord datasetTypeRecord;
	private ContactRecord piRecord;
	private ProjectRecord projectRecord;
	private ExperimentRecord experimentRecord;
	private AnalysisRecord analysisRecord;
	private ContactRecord createdByContactRecord;
	
	public DatasetEntity(){
		
	}

    public String getSQLReadyDatasetNames() {
        // TODO Auto-generated method stub
        int ctr = 0;
        StringBuilder sb = new StringBuilder();
        String names = datasetNamesAsEnterSeparatedString;
//      String removeSpaces = datasetNamesAsEnterSeparatedString.replaceAll(", ",",");
//      String removeEnters = removeSpaces.replaceAll(",\n",",");
        for(String s: names.split("\n")){
            if(ctr>0)sb.append(",");
        sb.append(" '"+s.toLowerCase()+"' ");
        ctr++;
        }
        return sb.toString();
    }
    
	public List<String> getDatasetNames() {
		return datasetNames;
	}
	public void setDatasetNames(List<String> datasetNames) {
		this.datasetNames = datasetNames;
	}
	public ContactRecord getPiRecord() {
		return piRecord;
	}

	public void setPiRecord(ContactRecord piRecord) {
		if(piRecord.get(2)!=null) this.piRecord = piRecord;
		else this.piRecord = null;
	}

	public ContactRecord getCreatedByContactRecord() {
		return createdByContactRecord;
	}

	public void setCreatedByContactRecord(ContactRecord createdByContactRecord) {
		this.createdByContactRecord = createdByContactRecord;
	}

	public CvRecord getDatasetTypeRecord() {
		return datasetTypeRecord;
	}

	public void setDatasetTypeRecord(CvRecord datasetTypeRecord) {
		if(datasetTypeRecord.get(0)!=null) this.datasetTypeRecord = datasetTypeRecord;
		else this.datasetTypeRecord = null;
	}

	public Integer getDatasetIDStartRange() {
		return datasetIDStartRange;
	}

	public void setDatasetIDStartRange(Integer datasetIDStartRange) {
		this.datasetIDStartRange = datasetIDStartRange;
	}

	public Integer getDatasetIDEndRange() {
		return datasetIDEndRange;
	}

	public void setDatasetIDEndRange(Integer datasetIDEndRange) {
		this.datasetIDEndRange = datasetIDEndRange;
	}

	public Date getCreationDateStart() {
		return creationDateStart;
	}

	public void setCreationDateStart(Date creationDateStart) {
		this.creationDateStart = creationDateStart;
	}

	public Date getCreationDateEnd() {
		return creationDateEnd;
	}

	public void setCreationDateEnd(Date creationDateEnd) {
		this.creationDateEnd = creationDateEnd;
	}

	public String getDatasetNamesAsEnterSeparatedString() {
		return datasetNamesAsEnterSeparatedString;
	}

	public void setDatasetNamesAsEnterSeparatedString(String datasetNamesAsEnterSeparatedString) {
		this.datasetNamesAsEnterSeparatedString = datasetNamesAsEnterSeparatedString;
	}

	public void setDatasetNames(String[] split) {
		// TODO Auto-generated method stub
		
	}

    public ProjectRecord getProjectRecord() {
        return projectRecord;
    }

    public void setProjectRecord(ProjectRecord projectRecord) {
        this.projectRecord = projectRecord;
    }

    public ExperimentRecord getExperimentRecord() {
        return experimentRecord;
    }

    public void setExperimentRecord(ExperimentRecord experimentRecord) {
        this.experimentRecord = experimentRecord;
    }

    public AnalysisRecord getAnalysisRecord() {
        return analysisRecord;
    }

    public void setAnalysisRecord(AnalysisRecord analysisRecord) {
        this.analysisRecord = analysisRecord;
    }
}
