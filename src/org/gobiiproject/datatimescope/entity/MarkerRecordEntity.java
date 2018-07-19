package org.gobiiproject.datatimescope.entity;

import java.io.Serializable;

import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;

public class MarkerRecordEntity  implements Serializable,Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Integer markerIDStartRange;
	private Integer  markerIDEndRange;
	private PlatformRecord platform;
	private String markerNamesAsCommaSeparatedString;
	
	public MarkerRecordEntity() {
	}

	public String getMarkerNamesAsCommaSeparatedString() {
		return markerNamesAsCommaSeparatedString;
	}

	public void setMarkerNamesAsCommaSeparatedString(String markerNamesAsCommaSeparatedString) {
		this.markerNamesAsCommaSeparatedString = markerNamesAsCommaSeparatedString;
	}

	public PlatformRecord getPlatform() {
		return platform;
	}

	public void setPlatform(PlatformRecord platform) {
		this.platform = platform;
	}

	public String getSQLReadyMarkerNames() {
		// TODO Auto-generated method stub

		int ctr = 0;
		StringBuilder sb = new StringBuilder();
		
		for(String s: markerNamesAsCommaSeparatedString.replaceAll(", ",",").split(",")){
			if(ctr>0)sb.append(",");
		sb.append(" '"+s.toLowerCase()+"' ");
		ctr++;
		}
		return sb.toString();
	}

	public Integer getMarkerIDStartRange() {
		return markerIDStartRange;
	}

	public void setMarkerIDStartRange(Integer markerIDStartRange) {
		this.markerIDStartRange = markerIDStartRange;
	}

	public Integer getMarkerIDEndRange() {
		return markerIDEndRange;
	}

	public void setMarkerIDEndRange(Integer markerIDEndRange) {
		this.markerIDEndRange = markerIDEndRange;
	}

}
