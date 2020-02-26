package org.gobiiproject.datatimescope.controller;

import java.util.List;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerGroupRecord;
import org.gobiiproject.datatimescope.entity.MarkerDetailDatasetEntity;
import org.gobiiproject.datatimescope.entity.MarkerDetailLinkageGroupEntity;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.gobiiproject.datatimescope.services.ServiceFactory;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.utils.WebappUtil;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class MarkerDetailViewModel {
	//UI component

    @Wire("#markerDetailWindow")
    Window markerDetailWindow;
    
	private String markerAssociated;
	private List<MarkerDetailDatasetEntity> markerDetailDatasetList;
	private List<MarkerDetailLinkageGroupEntity> markerDetailLinkageGroupList;
	private List<MarkerGroupRecord> markerDetailsMarkerGroupList;
	
	ViewModelService viewModelService;
	@Init
	public void init(@ExecutionArgParam("markerDetailsMarkerGroupList") List<MarkerGroupRecord> markerDetailsMarkerGroupList,
			@ExecutionArgParam("markerDetailDatasetList") List<DatasetRecord> markerDetailDatasetList,
			@ExecutionArgParam("markerDetailLinkageGroupList")  List<LinkageGroupRecord> markerDetailLinkageGroupList,
			@ExecutionArgParam("markerAssociated") Boolean markerAssociated) {

        viewModelService = ServiceFactory.getViewModelService();
		try {
			this.setMarkerDetailDatasetList(markerDetailDatasetList);
		} catch (TimescopeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
		}
		try {
			this.setMarkerDetailLinkageGroupList(markerDetailLinkageGroupList);
		} catch (TimescopeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
		}
		this.setMarkerDetailsMarkerGroupList(markerDetailsMarkerGroupList);
		this.setMarkerAssociated(isAssociated(markerAssociated));
		
	}

	
	@NotifyChange("markerAssociated")
	private String isAssociated(Boolean input) {
        // TODO Auto-generated method stub

	    if(input) return "This marker is associated with the following:";
	    
        return "This marker is not associated with any linkage group, dataset, and marker groups.";
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
    
	public List<MarkerDetailDatasetEntity> getMarkerDetailDatasetList() {
		return markerDetailDatasetList;
	}

	public void setMarkerDetailDatasetList(List<DatasetRecord> markerDetailDatasetList) throws TimescopeException {
	    this.markerDetailDatasetList = viewModelService.getMarkerAssociatedDetailsForEachDataset(markerDetailDatasetList);
	}

	public List<MarkerDetailLinkageGroupEntity> getMarkerDetailLinkageGroupList() {
		return markerDetailLinkageGroupList;
	}

	public void setMarkerDetailLinkageGroupList(List<LinkageGroupRecord> markerDetailLinkageGroupList) throws TimescopeException {
		this.markerDetailLinkageGroupList = viewModelService.getAssociatedDetailsForEachLinkageGroup(markerDetailLinkageGroupList);
	}

	public List<MarkerGroupRecord> getMarkerDetailsMarkerGroupList() {
		return markerDetailsMarkerGroupList;
	}

	public void setMarkerDetailsMarkerGroupList(List<MarkerGroupRecord> markerDetailsMarkerGroupList) {
		this.markerDetailsMarkerGroupList = markerDetailsMarkerGroupList;
	}

    public String getMarkerAssociated() {
        return markerAssociated;
    }

    public void setMarkerAssociated(String markerAssociated) {
        this.markerAssociated = markerAssociated;
    }

}
