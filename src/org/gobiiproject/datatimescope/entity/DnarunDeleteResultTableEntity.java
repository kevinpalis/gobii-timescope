package org.gobiiproject.datatimescope.entity;

import java.util.List;

public class DnarunDeleteResultTableEntity {

	private Integer dnarun_id;
	private String dnarun_name;
	
	private List<Integer> dataset_id;
	private String dataset_name;
	
	
	public String getDnarun_name() {
		return dnarun_name;
	}
	public void setDnarun_name(String dnarun_name) {
		this.dnarun_name = dnarun_name;
	}
	public int getDnarun_id() {
		return dnarun_id;
	}
	public void setDnarun_id(int dnarun_id) {
		this.dnarun_id = dnarun_id;
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
		String header = "Dnarun ID"+string+" Dnarun Name"+string+" Dataset name (Id)\n";
		return header;
	}
	public Object getAllDelimitedBy(String string) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(getDnarun_id())+string);
		sb.append(getDnarun_name()+string);
		sb.append(getDataset_name()+string);
		sb.append("\n");
		
		return sb.toString();
	}
	
}
