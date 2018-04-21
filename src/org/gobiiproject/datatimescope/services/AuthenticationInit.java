package org.gobiiproject.datatimescope.services;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

public class AuthenticationInit implements Initiator {
	 
    //services
    AuthenticationService authService = new AuthenticationServiceChapter3Impl();
  
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		// TODO Auto-generated method stub
		   
        UserCredential cre = authService.getUserCredential();
        if(cre==null){
        	  Executions.sendRedirect("/login.zul");
            return;
        }
	}
}