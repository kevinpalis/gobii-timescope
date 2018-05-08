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

import org.gobiiproject.datatimescope.services.CommonInfoService;
import org.zkoss.zul.ListModelList;
/**
 * User entity
 */
public class DatasetEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer datasetId;
	public Integer getDatasetId() {
		return datasetId;
	}
	public void setDatasetId(Integer datasetId) {
		this.datasetId = datasetId;
	}
	public String getDatasetName() {
		return datasetName;
	}
	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
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
	public Integer getCallingAnalysisId() {
		return callingAnalysisId;
	}
	public void setCallingAnalysisId(Integer callingAnalysisId) {
		this.callingAnalysisId = callingAnalysisId;
	}
	public String getCallingAnalysisName() {
		return callingAnalysisName;
	}
	public void setCallingAnalysisName(String callingAnalysisName) {
		this.callingAnalysisName = callingAnalysisName;
	}
	public Integer[] getAnalyses() {
		return analyses;
	}
	public void setAnalyses(Integer[] analyses) {
		this.analyses = analyses;
	}
	public String getDataTable() {
		return dataTable;
	}
	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}
	public String getDataFile() {
		return dataFile;
	}
	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}
	public String getQualityTable() {
		return qualityTable;
	}
	public void setQualityTable(String qualityTable) {
		this.qualityTable = qualityTable;
	}
	public String getQualityFile() {
		return qualityFile;
	}
	public void setQualityFile(String qualityFile) {
		this.qualityFile = qualityFile;
	}
	public String getCreatedByUsername() {
		return createdByUsername;
	}
	public void setCreatedByUsername(String createdByUsername) {
		this.createdByUsername = createdByUsername;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedByUsername() {
		return modifiedByUsername;
	}
	public void setModifiedByUsername(String modifiedByUsername) {
		this.modifiedByUsername = modifiedByUsername;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	private String datasetName;
	private Integer experimentId;
	private String experimentName;
	private Integer callingAnalysisId;
	private String callingAnalysisName;
	private Integer[] analyses;
	private String dataTable;
	private String dataFile;
	private String qualityTable;
	private String qualityFile;
	private String createdByUsername;
	private String createdDate;
	private String modifiedByUsername;
	private String modifiedDate;
	private String statusName;
	private String typeName;
	private String jobName;

}
