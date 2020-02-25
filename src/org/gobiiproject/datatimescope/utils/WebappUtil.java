package org.gobiiproject.datatimescope.utils;

import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.zkoss.zul.Messagebox;

public class WebappUtil {

	public WebappUtil() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void showErrorDialog(TimescopeException exc) {
		try {
			Messagebox.show(exc.getMessage(), exc.getErrorTitle(), Messagebox.OK, Messagebox.ERROR);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

}
