package org.gobiiproject.datatimescope.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.entity.MarkerDeleteResultTableEntity;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class MarkerDeleteWarningModel {
	final static Logger log = Logger.getLogger(MarkerDeleteWarningModel.class.getName());

	
	@Wire("#modalDialog")
	Window modalDialog;

	private List<MarkerDeleteResultTableEntity> markerDeleteResultTableEntityList = null;

	 
	@Init
	public void init(@ExecutionArgParam("markerDeleteResultTableEntityList") List<MarkerDeleteResultTableEntity> markerDeleteResultTableEntityList) {
		this.setMarkerDeleteResultTableEntityList(markerDeleteResultTableEntityList);
		
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	public List<MarkerDeleteResultTableEntity> getMarkerDeleteResultTableEntityList() {
		return markerDeleteResultTableEntityList;
	}

	public void setMarkerDeleteResultTableEntityList(List<MarkerDeleteResultTableEntity> markerDeleteResultTableEntityList) {
		this.markerDeleteResultTableEntityList = markerDeleteResultTableEntityList;
	}

	@Command
	public void closeModalDialog(){
		
		modalDialog.detach();
		
	}
}

