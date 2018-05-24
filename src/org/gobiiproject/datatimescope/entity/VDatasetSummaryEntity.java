package org.gobiiproject.datatimescope.entity;

import java.sql.Date;

import org.gobiiproject.datatimescope.db.generated.tables.Timescoper;
import org.gobiiproject.datatimescope.db.generated.tables.VDatasetSummary;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VDatasetSummaryRecord;
import org.gobiiproject.datatimescope.utils.Utils;

@SuppressWarnings("serial")
public class VDatasetSummaryEntity extends VDatasetSummaryRecord {

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
    public String getAnalysesAsString(){
    	StringBuilder sb = new StringBuilder();
    	
    	Integer[] analysesList =  (Integer[]) get(6);

		sb.append("{");
    	for(Integer i: analysesList){
    		sb.append(i.toString()+", ");
    	}
		sb.append("}");
		
    	return sb.toString();
    }
}
