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

import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;

/**
 * User entity
 */
public class DatasetEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private Integer datasetIDStartRange;
	private Integer datasetIDEndRange;
	private String datasetNamesAsCommaSeparatedString;
	private List<String> datasetNames;
	private Date creationDateStart;
	private Date creationDateEnd;
	private CvRecord datasetTypeRecord;
	private ContactRecord piRecord;
	private ContactRecord createdByContactRecord;
	
	public DatasetEntity(){
		
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
		this.piRecord = piRecord;
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
		this.datasetTypeRecord = datasetTypeRecord;
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

	public String getDatasetNamesAsCommaSeparatedString() {
		return datasetNamesAsCommaSeparatedString;
	}

	public void setDatasetNamesAsCommaSeparatedString(String datasetNamesAsCommaSeparatedString) {
		this.datasetNamesAsCommaSeparatedString = datasetNamesAsCommaSeparatedString;
	}

	public void setDatasetNames(String[] split) {
		// TODO Auto-generated method stub
		
	}

	public String getSQLReadyDatasetNames() {
		// TODO Auto-generated method stub
		int ctr = 0;
		StringBuilder sb = new StringBuilder();
		
		for(String s: datasetNamesAsCommaSeparatedString.replaceAll(", ",",").split(",")){
			if(ctr>0)sb.append(",");
		sb.append(" '"+s.toLowerCase()+"' ");
		ctr++;
		}
		return sb.toString();
	}
}
