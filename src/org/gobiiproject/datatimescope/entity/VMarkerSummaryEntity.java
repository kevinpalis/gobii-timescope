package org.gobiiproject.datatimescope.entity;

import java.sql.Date;

import org.gobiiproject.datatimescope.db.generated.tables.records.VMarkerSummaryRecord;
import org.gobiiproject.datatimescope.utils.Utils;

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

	 public String getAllDelimitedBy(String delim){
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append(Utils.checkInteger((Integer) get(0))+delim); //getMarkerId()
	    	sb.append((Utils.checkString((String)get(4))).toLowerCase()+delim); //getMarkerName()
	    	
	    	sb.append(Utils.checkInteger((Integer) get(1))+delim);//PlatformId()
	    	sb.append((Utils.checkString((String)get(2))).toLowerCase()+delim);//PlatformName()
	    	

	    	sb.append((Utils.checkString((String) get(4))).toLowerCase()+delim);//Ref
	    	sb.append(Utils.checkString(getAltsToString())+delim);//Alts
	    	

	    	sb.append(Utils.checkInteger((Integer) get(6))+delim);//ReferenceId()
	    	sb.append(Utils.checkString((String) get(7))+delim);//ReferenceName()
	    	
	    	sb.append(Utils.checkInteger((Integer) get(8))+delim);//VariantId()
	    	sb.append((Utils.checkString((String)get(9))).toLowerCase()+delim);//Code
	    	

	    	sb.append(Utils.checkString((String) get(10))+delim);//Sequence
	    	sb.append(Utils.checkString(getPrimersToString())+delim);//Primers()

	    	sb.append(Utils.checkInteger((Integer) get(12))+delim);//StrandId()
	    	sb.append(Utils.checkString((String) get(13))+delim);// StrandName()

	    	sb.append(Utils.checkInteger((Integer) get(14))+delim);//Status()
	    	sb.append(Utils.checkString(getProbsetsToString())+delim);// Probsets()
			
	    	sb.append(Utils.checkString(getDatasetMarkerIdxToString())+delim);//DatasetMarkerIdx()
	    	sb.append(Utils.checkString(getPropsToString())+delim);//Props()
	    	sb.append(Utils.checkString(getDatasetVendorProtocolToString())+"\n");//Dataset Vendor Protocol()

	    	return sb.toString();
	    }

	 public String getHeaderDelimitedBy(String delim){
		 StringBuilder sb = new StringBuilder();
		 sb.append("Marker Id" +delim);
		 sb.append("Marker Name" +delim);
		 sb.append("Platform Id" +delim);
		 sb.append("Platform Name" +delim);
		 sb.append("Ref" +delim);
		 sb.append("Alts" +delim);
		 sb.append("Reference Id" +delim);
		 sb.append("Reference Name" +delim);
		 sb.append("Variant Id" +delim);
		 sb.append("Code" +delim);
		 sb.append("Sequence" +delim);
		 sb.append("Primers" +delim);
		 sb.append("Strand Id" +delim);
		 sb.append("Strand Name" +delim);
		 sb.append("Status"+delim);
		 sb.append("Probsets" +delim);
		 sb.append("Dataset Marker Idx" +delim);
		 sb.append("Props" +delim);
		 sb.append("Dataset Vendor Protocol" +"\n");
		 
		return sb.toString();
	 }

	public String getAltsToString(){
		StringBuilder sb = new StringBuilder();

		try{
			String[] altsList =  (String[]) get(5);
			sb.append("\"");
			for(String i: altsList){
				sb.append(i.toString()+",");
			}
			sb.append("\"");
			
		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}



	public String getPrimersToString(){
		StringBuilder sb = new StringBuilder();

		try{
			Object[] list =  (Object[]) get(11);
			sb.append("\"");
			for(Object i: list){
				sb.append(i.toString()+",");
			}
			sb.append("\"");

		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}



	public String getProbsetsToString(){
		StringBuilder sb = new StringBuilder();

		try{
			Object[] list =  (Object[]) get(15);
			sb.append("\"");
			for(Object i: list){
				sb.append(i.toString()+",");
			}
			sb.append("\"");

		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}


	public String getDatasetMarkerIdxToString(){
		StringBuilder sb = new StringBuilder();

		try{
			Object[] list =  (Object[]) get(15);
			sb.append("\"");
			for(Object i: list){
				sb.append(i.toString()+",");
			}
			sb.append("\"");

		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}
	
	public String getPropsToString(){
		StringBuilder sb = new StringBuilder();

		try{
			Object[] list =  (Object[]) get(15);
			sb.append("\"");
			for(Object i: list){
				sb.append(i.toString()+",");
			}
			sb.append("\"");

		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}
	
	public String getDatasetVendorProtocolToString(){
		StringBuilder sb = new StringBuilder();


		try{
			Object[] list =  (Object[]) get(15);
			sb.append("\"");
			for(Object i: list){
				sb.append(i.toString()+",");
			}
			sb.append("\"");
		}catch(NullPointerException npe){
			return ("");
		}
		return sb.toString();
	}
}
