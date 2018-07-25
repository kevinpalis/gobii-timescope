/* 
	
*/
package org.gobiiproject.datatimescope.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonInfoService {

	static List<String> roleList= new ArrayList<String>();
	static{
		roleList.add("User");
		roleList.add("Administrator");
		roleList.add("Super Administrator");
		roleList = Collections.unmodifiableList(roleList);
	}
	
	public static List<String> getRoleList() {
		return roleList;
	}
}