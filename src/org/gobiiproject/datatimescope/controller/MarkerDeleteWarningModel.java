package org.gobiiproject.datatimescope.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.entity.MarkerDeleteResultTableEntity;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class MarkerDeleteWarningModel {
	final static Logger log = Logger.getLogger(MarkerDeleteWarningModel.class.getName());

	
	@Wire("#modalDialog")
	Window modalDialog;

	private List<MarkerDeleteResultTableEntity> markerDeleteResultTableEntityList = null;

	 
    @Listen("onClick = #closeBtn")
    public void showModal(Event e) {
        modalDialog.detach();
    }

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

}

