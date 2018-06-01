package org.gobiiproject.datatimescope.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.gobiiproject.datatimescope.db.generated.tables.Dataset;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.jooq.Record;
import org.jooq.Result;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
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

public class MarkerViewModel {
	//UI component

	ViewModelService viewModelService;
	private boolean cbAllUsers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false;

	private List<MarkerRecordEntity> markerList, selectedMarkerList;
	private MarkerRecordEntity markerEntity;

	@Init
	public void init() {
		selectedMarkerList = new ArrayList<MarkerRecordEntity>();
		viewModelService = new ViewModelServiceImpl();
		setMarkerEntity(new MarkerRecordEntity());
		setMarkerList(viewModelService.getAllMarkers());
	}

	@Command("submitQuery")
	@NotifyChange({"datasetList","selectedMarkerList", "allCbSelected", "cbAllUsers"})
	public void submitQuery(){
		
		try{
		markerList.clear(); //clear the list first and then just add if there are any selected
		}catch(NullPointerException e){
			
		}
		
		setMarkerList(viewModelService.getAllMarkersBasedOnQuery(markerEntity));
		

		setAllCbSelected(false);
		setCbAllUsers(false);
		
	}

	@Command("resetDatasetTab")
	@NotifyChange({"datasetList","selectedMarkerList", "allCbSelected", "cbAllUsers", "markerEntity","iDBoxDisabled","nameListDisabled"})
	public void resetDatasetTab(){
		try{
		markerList.clear(); //clear the list first and then just add if there are any selected
		selectedMarkerList.clear(); 
		}catch(NullPointerException e){
			
		}
		markerEntity = new MarkerRecordEntity();


		setiDBoxDisabled(false);
		setnameListDisabled(false);
		setAllCbSelected(false);
		setCbAllUsers(false);
	}
	@Command("doSelectAll")
	@NotifyChange("allCbSelected")
	public void doSelectAll(){
		List<MarkerRecordEntity> datasetListOnDisplay = getMarkerList();

		selectedMarkerList.clear(); //clear the list first and then just add if there are any selected

		setAllCbSelected(isCbAllUsers());

		if (isCbAllUsers()) {
			for(MarkerRecordEntity u: datasetListOnDisplay){
				selectedMarkerList.add(u);
			}
		}
	}

	@Command("changeEnabled")
	@NotifyChange({"iDBoxDisabled","nameListDisabled"})
	public void changeEnabled(){
		isIDBoxDisabled = false; // reseet
		isNameListDisabled= false; 
		
		if(markerEntity.getName()!=null && !markerEntity.getName().isEmpty()){
			isIDBoxDisabled = true;
		}else if(markerEntity.getMarkerId() != null ){
			if(markerEntity.getMarkerId() >0 ){
			isNameListDisabled=true;
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command("deleteSelectedDatasets")
	public void deleteUsers(){

		if(selectedMarkerList.isEmpty()){ //Nothing is selected
			Messagebox.show("There are no users selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		else{
			StringBuilder sb = new StringBuilder();

			for(MarkerRecordEntity u: selectedMarkerList){
				sb.append("\n"+u.getName());
			}


			Messagebox.show("Are you sure you want to delete the following datasets?"+sb.toString(), 
					"Confirm Delete", Messagebox.YES | Messagebox.CANCEL,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener(){
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if(Messagebox.ON_YES.equals(event.getName())){
						//YES is clicked
						boolean successful;

						if(selectedMarkerList.size() == 1){  // just one user is selected
							successful = viewModelService.deleteMarkers(selectedMarkerList.get(0));
						}else{
							//bulk delete
							successful = viewModelService.deleteMarkers(selectedMarkerList);
						}

						if(successful) BindUtils.postGlobalCommand(null, null, "retrieveDatasetList", null);

					}
				}
			});
		}
	}

	@GlobalCommand("retrieveDatasetList")
	@NotifyChange({"datasetList", "selectedMarkerList", "allCbSelected", "cbAllUsers"})
	public void retrieveUserList(){
		//...

		setMarkerList(viewModelService.getAllMarkersBasedOnQuery(markerEntity));

		selectedMarkerList.clear();
		
		setAllCbSelected(false);
		setCbAllUsers(false);
	}
	
	@Command("updateSelectMarker")
	@NotifyChange({"cbAllUsers", "selectedMarkerList"})
	public void updateSelectMarker(@BindingParam("markerChecked") MarkerRecordEntity markerList, @BindingParam("isChecked") Boolean isChecked){
		if(isChecked){
			selectedMarkerList.add(markerList);
		}else{
			setCbAllUsers(false);

			ListIterator<MarkerRecordEntity> it = selectedMarkerList.listIterator();
			while (it.hasNext()) {
				if (it.next().getMarkerId().equals(markerList.getMarkerId())) {
					it.remove();
					break;
				}
			}
		}
	}
	
	public boolean isAllCbSelected() {
		return isAllCbSelected;
	}
	public void setAllCbSelected(boolean isAllCbSelected) {
		this.isAllCbSelected = isAllCbSelected;
	}
	public boolean isCbAllUsers() {
		return cbAllUsers;
	}
	public void setCbAllUsers(boolean cbAllUsers) {
		this.cbAllUsers = cbAllUsers;
	}

	public boolean isiDBoxDisabled() {
		return isIDBoxDisabled;
	}

	public void setiDBoxDisabled(boolean isIDBoxDisabled) {
		this.isIDBoxDisabled = isIDBoxDisabled;
	}

	public boolean isnameListDisabled() {
		return isNameListDisabled;
	}

	public void setnameListDisabled(boolean isNameListDisabled) {
		this.isNameListDisabled = isNameListDisabled;
	}

	public MarkerRecordEntity getMarkerEntity() {
		return markerEntity;
	}

	public void setMarkerEntity(MarkerRecordEntity markerEntity) {
		this.markerEntity = markerEntity;
	}

	public List<MarkerRecordEntity> getMarkerList() {
		return markerList;
	}

	public void setMarkerList(List<MarkerRecordEntity> markerList) {
		this.markerList = markerList;
	}

}
