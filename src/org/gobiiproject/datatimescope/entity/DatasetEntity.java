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

/**
 * User entity
 */
public class DatasetEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private List<Integer> datasetIDRange;
	private List<String> datasetNames;
	private Integer createdByContactId;
	private List<Date> creationDateRange;
	private Integer datasetTypeId;
	private Integer piID;
	
	public DatasetEntity(){
		
	}
	
	public List<Integer> getDatasetIDRange() {
		return datasetIDRange;
	}
	public void setDatasetIDRange(List<Integer> datasetIDRange) {
		this.datasetIDRange = datasetIDRange;
	}
	public List<String> getDatasetNames() {
		return datasetNames;
	}
	public void setDatasetNames(List<String> datasetNames) {
		this.datasetNames = datasetNames;
	}
	public Integer getCreatedByContactId() {
		return createdByContactId;
	}
	public void setCreatedByContactId(Integer createdByContactId) {
		this.createdByContactId = createdByContactId;
	}
	public List<Date> getCreationDateRange() {
		return creationDateRange;
	}
	public void setCreationDateRange(List<Date> creationDateRange) {
		this.creationDateRange = creationDateRange;
	}
	public Integer getDatasetTypeId() {
		return datasetTypeId;
	}
	public void setDatasetTypeId(Integer datasetTypeId) {
		this.datasetTypeId = datasetTypeId;
	}
	public Integer getPiID() {
		return piID;
	}
	public void setPiID(Integer piID) {
		this.piID = piID;
	}
}
