package org.gobiiproject.datatimescope.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MapsetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.Utils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

public class MarkerDetailViewModel {
	//UI component

    @Wire("#markerDetailWindow")
    Window markerDetailWindow;
    
	private Boolean markerAssociated;
	private List<DatasetRecord> markerDetailDatasetList;
	private List<LinkageGroupRecord> markerDetailLinkageGroupList;
	private List<MarkerGroupRecord> markerDetailsMarkerGroupList;
	
	@Init
	public void init(@ExecutionArgParam("markerDetailsMarkerGroupList") List<MarkerGroupRecord> markerDetailsMarkerGroupList,
			@ExecutionArgParam("markerDetailDatasetList") List<DatasetRecord> markerDetailDatasetList,
			@ExecutionArgParam("markerDetailLinkageGroupList")  List<LinkageGroupRecord> markerDetailLinkageGroupList,
			@ExecutionArgParam("markerAssociated") Boolean markerAssociated) {

		this.setMarkerDetailDatasetList(markerDetailDatasetList);
		this.setMarkerDetailLinkageGroupList(markerDetailLinkageGroupList);
		this.setMarkerDetailsMarkerGroupList(markerDetailsMarkerGroupList);
		this.setMarkerAssociated(markerAssociated);
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

    @Command("displayTooltip")
    public void displayTooltip() {
        Messagebox.show("Hover/point to a row to see its ID.", "Quick Tip", Messagebox.OK,  Messagebox.INFORMATION);
    }
	
    @Command("exit")
    public void exit(){
        markerDetailWindow.detach();
    }
    
	public Boolean getMarkerAssociated() {
		return markerAssociated;
	}

	public void setMarkerAssociated(Boolean markerAssociated) {
		this.markerAssociated = markerAssociated;
	}

	public List<DatasetRecord> getMarkerDetailDatasetList() {
		return markerDetailDatasetList;
	}

	public void setMarkerDetailDatasetList(List<DatasetRecord> markerDetailDatasetList) {
		this.markerDetailDatasetList = markerDetailDatasetList;
	}

	public List<LinkageGroupRecord> getMarkerDetailLinkageGroupList() {
		return markerDetailLinkageGroupList;
	}

	public void setMarkerDetailLinkageGroupList(List<LinkageGroupRecord> markerDetailLinkageGroupList) {
		this.markerDetailLinkageGroupList = markerDetailLinkageGroupList;
	}

	public List<MarkerGroupRecord> getMarkerDetailsMarkerGroupList() {
		return markerDetailsMarkerGroupList;
	}

	public void setMarkerDetailsMarkerGroupList(List<MarkerGroupRecord> markerDetailsMarkerGroupList) {
		this.markerDetailsMarkerGroupList = markerDetailsMarkerGroupList;
	}

}
