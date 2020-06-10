
package org.gobiiproject.datatimescope.configurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.gobiiproject.datatimescope.services.SidebarPage;
import org.gobiiproject.datatimescope.services.SidebarPageConfig;

public class SidebarPageConfigAjaxBasedImpl implements SidebarPageConfig{
	
	HashMap<String,SidebarPage> pageMap = new LinkedHashMap<String,SidebarPage>();
	public SidebarPageConfigAjaxBasedImpl(){		

		pageMap.put("fn1",new SidebarPage("users","Users","/imgs/youngscientist.png","/users.zul"));
		pageMap.put("fn2",new SidebarPage("datasets","Datasets","/imgs/document.png","/datasets.zul"));
        pageMap.put("fn3",new SidebarPage("markers","Markers","/imgs/science.png","/markers.zul"));
		pageMap.put("fn4",new SidebarPage("dnaruns","DNA Runs","/imgs/testtube.png","/dnaruns.zul"));
		pageMap.put("fn5",new SidebarPage("dnasamples","DNA Samples","/imgs/testdrop.png","/dnasample.zul"));
        pageMap.put("fn6",new SidebarPage("germplasms","Germplasms","/imgs/plantexperiment.png","/germplasm.zul"));
//		pageMap.put("fn3",new SidebarPage("linkagegroup","Linkage Group","/imgs/dnasample.png","/linkageGroup.zul"));
	}
	
	public List<SidebarPage> getPages(){
		return new ArrayList<SidebarPage>(pageMap.values());
	}
	
	public SidebarPage getPage(String name){
		return pageMap.get(name);
	}
	
}