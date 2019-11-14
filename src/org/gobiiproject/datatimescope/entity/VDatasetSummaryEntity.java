package org.gobiiproject.datatimescope.entity;

import java.sql.Date;

import org.gobiiproject.datatimescope.db.generated.tables.Timescoper;
import org.gobiiproject.datatimescope.db.generated.tables.VDatasetSummary;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VDatasetSummaryRecord;
import org.gobiiproject.datatimescope.utils.Utils;

@SuppressWarnings("serial")
public class VDatasetSummaryEntity extends VDatasetSummaryRecord {
	private String piName;
	
	public VDatasetSummaryEntity() {
		new VDatasetSummaryRecord();
	}

	/**
	 * Create a detached, initialised VDatasetSummaryRecord
	 */
	public VDatasetSummaryEntity(Integer datasetId, String datasetName, Integer experimentId, String experimentName, Integer callinganalysisId, String callingnalysisName, Integer[] analyses, String dataTable, String dataFile, String qualityTable, String qualityFile, Object scores, String createdByUsername, Date createdDate, String modifiedByUsername, Date modifiedDate, String statusName, String typeName, String jobName, Integer projectId, String projectName, Integer piId, String piFirstname, String piLastname) {
		new VDatasetSummaryRecord();

		set(0, datasetId);
		set(1, datasetName);
		set(2, experimentId);
		set(3, experimentName);
		set(4, callinganalysisId);
		set(5, callingnalysisName);
		set(6, analyses);
		set(7, dataTable);
		set(8, dataFile);
		set(9, qualityTable);
		set(10, qualityFile);
		set(11, scores);
		set(12, createdByUsername);
		set(13, createdDate);
		set(14, modifiedByUsername);
		set(15, modifiedDate);
		set(16, statusName);
		set(17, typeName);
		set(18, jobName);
		set(19, projectId);
		set(20, projectName);
		set(21, piId);
		set(22, piFirstname);
		set(23, piLastname);
	}
	
	 /**
     * Not Generated. to get Analyses values as String
     */
	
	 public String getAllDelimitedBy(String delim){
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append(Utils.checkInteger((Integer) get(0))+delim); //getDatasetId()
	    	sb.append((Utils.checkString((String)get(1))).toLowerCase()+delim); //getDatasetName()
	    	
	    	sb.append(Utils.checkInteger((Integer) get(2))+delim);//getExperimentId()
	    	sb.append((Utils.checkString((String)get(3))).toLowerCase()+delim);//getExperimentName()
	    	
	    	sb.append(Utils.checkInteger((Integer) get(21))+delim);//getPiId()
	    	sb.append(getPiName()+delim);
	    	
	    	sb.append(Utils.checkInteger((Integer) get(4))+delim);//getCallinganalysisId()
	    	sb.append((Utils.checkString((String)get(5)))+delim);//getCallingnalysisName()
	    	sb.append(getAnalysesAsString()+delim);

	    	sb.append(Utils.checkString((String) get(7))+delim);//getDataTable()
	    	sb.append(Utils.checkString((String) get(8))+delim);//getDataFile()
	    	
	    	sb.append(Utils.checkString((String) get(9))+delim);//getQualityTable()
	    	sb.append(Utils.checkString((String) get(10))+delim);//getQualityFile()
	    	
	    	sb.append(Utils.checkString((String) get(12))+delim);//getCreatedByUsername()
	    	sb.append(Utils.checkDate((Date) get(13))+delim);// getCreatedDate()
	    	
	    	sb.append(Utils.checkString((String) get(14))+delim);//getModifiedByUsername()
	    	sb.append(Utils.checkDate((Date) get(15))+delim);// getModifiedDate()
			

	    	sb.append(Utils.checkString((String) get(16))+delim);//getStatusName()
	    	sb.append(Utils.checkString((String) get(17))+delim);//getTypeName()
	    	sb.append(Utils.checkString((String) get(18))+"\n");//getJobName()

	    	return sb.toString();
	    }

	 public String getHeaderDelimitedBy(String delim){
		 StringBuilder sb = new StringBuilder();
		 sb.append("Dataset Id" +delim);
		 sb.append("Dataset Name" +delim);
		 sb.append("Experiment Id" +delim);
		 sb.append("Experiment Name" +delim);
		 sb.append("PI Id" +delim);
		 sb.append("PI Name" +delim);
		 sb.append("Calling Analysis Id" +delim);
		 sb.append("Calling Analysis Name" +delim);
		 sb.append("Analyses" +delim);
		 sb.append("Data Table" +delim);
		 sb.append("Data File" +delim);
		 sb.append("Quality Table" +delim);
		 sb.append("Quality File" +delim);
		 sb.append("Created By" +delim);
		 sb.append("Created Date" +delim);
		 sb.append("Modified By" +delim);
		 sb.append("Modified Date" +delim);
		 sb.append("Status" +delim);
		 sb.append("Type" +delim);
		 sb.append("Job" +"\n");
		 
		return sb.toString();
	 }
	public String getAnalysesAsString(){
    	StringBuilder sb = new StringBuilder();
    	
    	Integer[] analysesList =  (Integer[]) get(6);
    	
    	if(analysesList.length>1)sb.append("\"");
    	for(Integer i: analysesList){
    		sb.append(i.toString()+",");
    	}
    	if(analysesList.length>1)sb.append("\"");
		
    	return sb.toString();
    }

	public String getPiName() {

    	String piLastName=  (String) get(23);
    	String piFirstName= (String) get(22);
    	
    	piName = piLastName +", "+ piFirstName;
		return piName;
	}

}
