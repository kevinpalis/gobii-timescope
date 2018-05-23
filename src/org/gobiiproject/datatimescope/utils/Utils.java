package org.gobiiproject.datatimescope.utils;

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

}
