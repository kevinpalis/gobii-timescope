package org.gobiiproject.datatimescope.entity;

import java.util.List;

public class GermplasmDeleteResultTableEntity {

	private Integer germplasm_id;
	private String germplasm_name;
	
	private List<Integer> dnasample_id;
	private String dnasample_name;
	
	
	public String getGermplasm_name() {
		return germplasm_name;
	}
	public void setGermplasm_name(String germplasm_name) {
		this.germplasm_name = germplasm_name;
	}
	public int getGermplasm_id() {
		return germplasm_id;
	}
	public void setGermplasm_id(int germplasm_id) {
		this.germplasm_id = germplasm_id;
	}
	
	public List<Integer> getDnasample_id() {
		return dnasample_id;
	}
	public void setDnasample_id(List<Integer> dnasample_id) {
		this.dnasample_id = dnasample_id;
	}
	public String getDnasample_name() {

		String returnVal="";
		try{
			if(dnasample_name!=null) {
				returnVal = dnasample_name;
			}
		}catch(NullPointerException npe) {	
		}
		
		return returnVal;
	}
	public void setDnasample_name(String dnasample_name) {
		this.dnasample_name = dnasample_name;
	}
	public Object getHeaderDelimitedBy(String string) {
		// TODO Auto-generated method stub
		String header = "Germplasm ID"+string+" Germplasm Name"+string+" Dnasample name (Id)\n";
		return header;
	}
	public Object getAllDelimitedBy(String string) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(getGermplasm_id())+string);
		sb.append(getGermplasm_name()+string);
		sb.append(getDnasample_name()+string);
		sb.append("\n");
		
		return sb.toString();
	}
	
}
