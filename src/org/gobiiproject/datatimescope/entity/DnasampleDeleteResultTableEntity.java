package org.gobiiproject.datatimescope.entity;

import java.util.List;

public class DnasampleDeleteResultTableEntity {

	private Integer dnasample_id;
	private String dnasample_name;
	
	private List<Integer> dnarun_id;
	private String dnarun_name;
	
	
	public String getDnasample_name() {
		return dnasample_name;
	}
	public void setDnasample_name(String dnasample_name) {
		this.dnasample_name = dnasample_name;
	}
	public int getDnasample_id() {
		return dnasample_id;
	}
	public void setDnasample_id(int dnasample_id) {
		this.dnasample_id = dnasample_id;
	}
	
	public List<Integer> getDnarun_id() {
		return dnarun_id;
	}
	public void setDnarun_id(List<Integer> dnarun_id) {
		this.dnarun_id = dnarun_id;
	}
	public String getDnarun_name() {

		String returnVal="";
		try{
			if(dnarun_name!=null) {
				returnVal = dnarun_name;
			}
		}catch(NullPointerException npe) {	
		}
		
		return returnVal;
	}
	public void setDnarun_name(String dnarun_name) {
		this.dnarun_name = dnarun_name;
	}
	public Object getHeaderDelimitedBy(String string) {
		// TODO Auto-generated method stub
		String header = "Dnasample ID"+string+" Dnasample Name"+string+" Dnarun name (Id)\n";
		return header;
	}
	public Object getAllDelimitedBy(String string) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(getDnasample_id())+string);
		sb.append(getDnasample_name()+string);
		sb.append(getDnarun_name()+string);
		sb.append("\n");
		
		return sb.toString();
	}
	
}
