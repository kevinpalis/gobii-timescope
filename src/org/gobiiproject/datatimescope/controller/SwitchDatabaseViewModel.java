package org.gobiiproject.datatimescope.controller;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

public class SwitchDatabaseViewModel {

	@Command
    public void openDatabaseInfoDialog() {
		  Window window = (Window)Executions.createComponents(
	                "/switch_database.zul", null, null);
	      window.doModal();
    }
}
