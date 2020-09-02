package org.gobiiproject.datatimescope.controller;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class NeedHelpModel extends SelectorComposer<Component>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final static Logger log = Logger.getLogger(NeedHelpModel.class.getName());
	//UI component
	  
    @Wire
    Window modalDialog;
     
    @Listen("onClick = #okBtn")
    public void showModal(Event e) {
        modalDialog.detach();
    }
    
    @Listen("onClick = #timescopeLogo")
	public void bringBackToHome(){
		Executions.sendRedirect("/index.zul");
	}
}
