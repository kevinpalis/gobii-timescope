package org.gobiiproject.datatimescope.controller;

import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zul.Html;
import org.zkoss.zul.Popup;

public class HelpContext extends Popup {
    
    public void onOpen(OpenEvent event) {
    	if (event.getReference() == null) {
    		return; // popup close - noaction
    	}
	
    	Html helptext = (Html) this.getFellow("helptext");
    	helptext.setContent(getHelpText(event.getReference()));
    }
}