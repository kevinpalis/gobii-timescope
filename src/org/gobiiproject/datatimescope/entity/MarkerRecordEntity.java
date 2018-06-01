package org.gobiiproject.datatimescope.entity;

import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerRecord;

@SuppressWarnings("serial")
public class MarkerRecordEntity extends MarkerRecord {
	
	
	public MarkerRecordEntity() {
		new MarkerRecord();
	}

	/**
	 * Create a detached, initialised MarkerRecord
	 */
	 public MarkerRecordEntity(Integer markerId, Integer platformId, Integer variantId, String name, String code, String ref, String[] alts, String sequence, Integer referenceId, Object primers, Integer strandId, Integer status, Object probsets, Object datasetMarkerIdx, Object props, Object datasetVendorProtocol) {
		 new MarkerRecord();

	        set(0, markerId);
	        set(1, platformId);
	        set(2, variantId);
	        set(3, name);
	        set(4, code);
	        set(5, ref);
	        set(6, alts);
	        set(7, sequence);
	        set(8, referenceId);
	        set(9, primers);
	        set(10, strandId);
	        set(11, status);
	        set(12, probsets);
	        set(13, datasetMarkerIdx);
	        set(14, props);
	        set(15, datasetVendorProtocol);
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
