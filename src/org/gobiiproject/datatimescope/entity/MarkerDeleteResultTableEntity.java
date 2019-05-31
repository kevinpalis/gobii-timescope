package org.gobiiproject.datatimescope.entity;

public class MarkerDeleteResultTableEntity {

	private int marker_id;
	private String marker_name;
	
	private int marker_group_id;
	private String marker_group_name;
	
	private int dataset_id;
	private String dataset_name;
	
	
	public String getMarker_name() {
		return marker_name;
	}
	public void setMarker_name(String marker_name) {
		this.marker_name = marker_name;
	}
	public int getMarker_id() {
		return marker_id;
	}
	public void setMarker_id(int marker_id) {
		this.marker_id = marker_id;
	}
	public int getMarker_group_id() {
		return marker_group_id;
	}
	public void setMarker_group_id(int marker_group_id) {
		this.marker_group_id = marker_group_id;
	}
	public String getMarker_group_name() {
		return marker_group_name;
	}
	public void setMarker_group_name(String marker_group_name) {
		this.marker_group_name = marker_group_name;
	}
	public int getDataset_id() {
		return dataset_id;
	}
	public void setDataset_id(int dataset_id) {
		this.dataset_id = dataset_id;
	}
	public String getDataset_name() {
		return dataset_name;
	}
	public void setDataset_name(String dataset_name) {
		this.dataset_name = dataset_name;
	}
	
}
