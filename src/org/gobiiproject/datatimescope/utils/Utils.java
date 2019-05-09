package org.gobiiproject.datatimescope.utils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

	static List<String> roleList= new ArrayList<String>();
	static{
		roleList.add(""); // empty for index 0
		roleList.add("Super Administrator"); // index 1
		roleList.add("Administrator"); // index 2
		roleList.add("User"); // index 3
		roleList = Collections.unmodifiableList(roleList);
	}
	
	public static List<String> getRoleList() {
		return roleList;
	}

	public static String checkString(String value) {
		// TODO Auto-generated method stub
		String returnVal;
		
		try{
			if(value==null){
				returnVal = "";
			}else returnVal = value;
		}catch(NullPointerException npe){
			returnVal = "";
		}
		
		return returnVal;
	}


	public static String checkInteger(Integer value) {
		// TODO Auto-generated method stub
		String returnVal;
		
		try{
			returnVal = Integer.toString(value);
		}catch(NullPointerException npe){
			returnVal = "";
		}
		
		return returnVal;
	}

	public static String checkDate(Date value) {
		// TODO Auto-generated method stub
		String returnVal;
		
		try{
			returnVal = value.toString();
		}catch(NullPointerException npe){
			returnVal = "";
		}
		
		return returnVal;
	}

}
