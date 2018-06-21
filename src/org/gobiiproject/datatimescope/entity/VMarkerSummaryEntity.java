package org.gobiiproject.datatimescope.entity;

import org.gobiiproject.datatimescope.db.generated.tables.records.VMarkerSummaryRecord;

@SuppressWarnings("serial")
public class VMarkerSummaryEntity extends VMarkerSummaryRecord {

	public VMarkerSummaryEntity() {
		new VMarkerSummaryRecord();
	}

	/**
	 * Create a detached, initialised VMarkerSummaryRecord
	 */
	public VMarkerSummaryEntity(Integer markerId, Integer platformId, String platformName, Integer variantId, String markerName, String code, String ref, String[] alts, String sequence, Integer referenceId, String referenceName, Object primers, Integer strandId, String strandName, Integer status, Object probsets, Object datasetMarkerIdx, Object props, Object datasetVendorProtocol) {
		new VMarkerSummaryRecord();

		set(0, markerId);
		set(1, platformId);
		set(2, platformName);
		set(3, variantId);
		set(4, markerName);
		set(5, code);
		set(6, ref);
		set(7, alts);
		set(8, sequence);
		set(9, referenceId);
		set(10, referenceName);
		set(11, primers);
		set(12, strandId);
		set(13, strandName);
		set(14, status);
		set(15, probsets);
		set(16, datasetMarkerIdx);
		set(17, props);
		set(18, datasetVendorProtocol);
	}

	public String getAltsToString(){
		StringBuilder sb = new StringBuilder();

		String[] altsList =  (String[]) get(7);
		try{
			sb.append("{");
			for(String i: altsList){
				sb.append(i.toString()+", ");
			}
			sb.append("}");

		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}



	public String getPrimersToString(){
		StringBuilder sb = new StringBuilder();

		Object[] list =  (Object[]) get(11);
		try{
			sb.append("{");
			for(Object i: list){
				sb.append(i.toString()+", ");
			}
			sb.append("}");

		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}



	public String getProbsetsToString(){
		StringBuilder sb = new StringBuilder();

		Object[] list =  (Object[]) get(15);
		try{
			sb.append("{");
			for(Object i: list){
				sb.append(i.toString()+", ");
			}
			sb.append("}");

		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}


	public String getDatasetMarkerIdxToString(){
		StringBuilder sb = new StringBuilder();

		Object[] list =  (Object[]) get(15);
		try{
			sb.append("{");
			for(Object i: list){
				sb.append(i.toString()+", ");
			}
			sb.append("}");

		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}
	public String getPropsToString(){
		StringBuilder sb = new StringBuilder();

		Object[] list =  (Object[]) get(15);
		try{
			sb.append("{");
			for(Object i: list){
				sb.append(i.toString()+", ");
			}
			sb.append("}");

		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}
	public String getDatasetVendorProtocolToString(){
		StringBuilder sb = new StringBuilder();

		Object[] list =  (Object[]) get(15);

		try{
			sb.append("{");
			for(Object i: list){
				sb.append(i.toString()+", ");
			}
			sb.append("}");
		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}
}
