/* 
	Description:
		
*/
package org.gobiiproject.datatimescope.configurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.gobiiproject.datatimescope.services.SidebarPage;
import org.gobiiproject.datatimescope.services.SidebarPageConfig;

public class SidebarPageConfigImpl implements SidebarPageConfig{

	HashMap<String,SidebarPage> pageMap = new LinkedHashMap<String,SidebarPage>();
	public SidebarPageConfigImpl(){		
		pageMap.put("fn1",new SidebarPage("markers","Markers","/imgs/site.png","http://www.zkoss.org/"));
		pageMap.put("fn2",new SidebarPage("dnasample","DNA Samples","/imgs/demo.png","http://www.zkoss.org/zkdemo"));
		pageMap.put("fn3",new SidebarPage("dnarun","DNA Runs","/imgs/doc.png","http://books.zkoss.org/wiki/ZK_Developer's_Reference"));
		pageMap.put("fn4",new SidebarPage("dataset","Datasets","/imgs/doc.png","http://books.zkoss.org/wiki/ZK_Developer's_Reference"));
		pageMap.put("fn5",new SidebarPage("user","Users","/imgs/demo.png","http://www.zkoss.org/zkdemo"));
	}
	
	public List<SidebarPage> getPages(){
		return new ArrayList<SidebarPage>(pageMap.values());
	}
	
	public SidebarPage getPage(String name){
		return pageMap.get(name);
	}
}
