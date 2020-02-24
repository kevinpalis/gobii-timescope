package org.gobiiproject.datatimescope.services;

public class DataSetDeleteInfo {
	private int dsListSize;
	public int getDsListSize() {
		return dsListSize;
	}

	public void setDsListSize(int dsListSize) {
		this.dsListSize = dsListSize;
	}

	private double rowDeleteSeconds;
	private int totalDeletedDatasetMarkerIndices;
	private double markerDeleteSeconds;
	private int totalDeletedDatasetDnarunIndices;
	private double dnaRunSeconds;
	public DataSetDeleteInfo(int dsListSize, double rowDeleteSeconds, int totalDeletedDatasetMarkerIndices,
			double markerDeleteSeconds, int totalDeletedDatasetDnarunIndices, double dnaRunSeconds) {
		super();
		this.dsListSize = dsListSize;
		this.rowDeleteSeconds = rowDeleteSeconds;
		this.totalDeletedDatasetMarkerIndices = totalDeletedDatasetMarkerIndices;
		this.markerDeleteSeconds = markerDeleteSeconds;
		this.totalDeletedDatasetDnarunIndices = totalDeletedDatasetDnarunIndices;
		this.dnaRunSeconds = dnaRunSeconds;
	}
	
	public String toString() {
		return Integer.toString(dsListSize)+" datasets deleted. ("+Double.toString(rowDeleteSeconds)+" sec) \n"+Integer.toString(totalDeletedDatasetMarkerIndices)+" markers updated. ("+Double.toString(markerDeleteSeconds)+" sec) \n"+Integer.toString(totalDeletedDatasetDnarunIndices)+" DNAruns updated. ("+Double.toString(dnaRunSeconds)+" sec) \n";
	}
}


//Integer.toString(selectedDsList.size())+" datasets deleted. ("+Double.toString(rowDeleteSeconds)+" sec) \n"+Integer.toString(totalDeletedDatasetMarkerIndices)+" markers updated. ("+Double.toString(markerseconds)+" sec) \n"+Integer.toString(totalDeletedDatasetDnarunIndices)+" DNAruns updated. ("+Double.toString(dnaRunSeconds)+" sec) \n";
