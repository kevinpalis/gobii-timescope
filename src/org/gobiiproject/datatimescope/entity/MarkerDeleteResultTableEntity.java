package org.gobiiproject.datatimescope.entity;

import java.util.List;

public class MarkerDeleteResultTableEntity {

	private Integer marker_id;
	private String marker_name;
	
	private int marker_group_id;
	private String marker_group_name;
	
	private List<Integer> dataset_id;
	private String dataset_name;
	

    private int linkage_group_id;
    private String linkage_group_name;
	
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
		String returnVal="";
		try{
			if(marker_group_name!=null) {
				returnVal = marker_group_name;
			}
		}catch(NullPointerException npe) {
		}
		
		return returnVal;
	}
	public void setMarker_group_name(String marker_group_name) {
		this.marker_group_name = marker_group_name;
	}
	public List<Integer> getDataset_id() {
		return dataset_id;
	}
	public void setDataset_id(List<Integer> dataset_id) {
		this.dataset_id = dataset_id;
	}
	public String getDataset_name() {

		String returnVal="";
		try{
			if(dataset_name!=null) {
				returnVal = dataset_name;
			}
		}catch(NullPointerException npe) {	
		}
		
		return returnVal;
	}
	public void setDataset_name(String dataset_name) {
		this.dataset_name = dataset_name;
	}
	public Object getHeaderDelimitedBy(String string) {
		// TODO Auto-generated method stub
		String header = "Marker ID, Marker Name, Dataset (ID), Markergroup (ID:Name), Linkagegroup (ID:Name) \n";
		return header;
	}

    public int getLinkage_group_id() {
        return linkage_group_id;
    }
    public void setLinkage_group_id(int linkage_group_id) {
        this.linkage_group_id = linkage_group_id;
    }
    public String getLinkage_group_name() {
        String returnVal="";
        try{
            if(linkage_group_name!=null) {
                returnVal = linkage_group_name;
            }
        }catch(NullPointerException npe) {
        }
        
        return returnVal;
    }
    public void setLinkage_group_name(String linkage_group_name) {
        this.linkage_group_name = linkage_group_name;
    }
	
	public Object getAllDelimitedBy(String string) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(getMarker_id())+",");
		sb.append("\""+getMarker_name()+"\",");
		sb.append("\""+getDataset_name()+"\",");
		sb.append("\""+getMarker_group_name()+"\",");
        sb.append("\""+getLinkage_group_name()+"\",\n");
		
		return sb.toString();
	}
	
}
