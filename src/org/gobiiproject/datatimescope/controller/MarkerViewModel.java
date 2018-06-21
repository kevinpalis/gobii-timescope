package org.gobiiproject.datatimescope.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.VMarkerSummaryEntity;
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
	private boolean cbAllMarkers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false;

	private List<VMarkerSummaryEntity> markerList, selectedMarkerList;
	private List<PlatformRecord> platformList;
	private MarkerRecordEntity markerEntity;

	@Init
	public void init() {
		markerList = new ArrayList<VMarkerSummaryEntity>();
		selectedMarkerList = new ArrayList<VMarkerSummaryEntity>();
		viewModelService = new ViewModelServiceImpl();
		setMarkerEntity(new MarkerRecordEntity());
//		setMarkerList(viewModelService.getAllMarkers());
		setPlatformList(viewModelService.getAllPlatforms());
	}

	@Command("submitQuery")
	@NotifyChange({"markerList","selectedMarkerList", "allCbSelected", "cbAllMarkers"})
	public void submitQuery(){

		try{
			markerList.clear(); //clear the list first and then just add if there are any selected
		}catch(NullPointerException e){

		}

		setMarkerList(viewModelService.getAllMarkersBasedOnQuery(markerEntity));

		setAllCbSelected(false);
		setCbAllMarkers(false);

	}

	@Command("resetMarkerTab")
	@NotifyChange({"markerList","selectedMarkerList", "allCbSelected", "cbAllMarkers", "markerEntity","iDBoxDisabled","nameListDisabled"})
	public void resetMarkerTab(){
		try{
			markerList.clear(); //clear the list first and then just add if there are any selected
			selectedMarkerList.clear(); 
		}catch(NullPointerException e){

		}
		markerEntity = new MarkerRecordEntity();


		setiDBoxDisabled(false);
		setnameListDisabled(false);
		setAllCbSelected(false);
		setCbAllMarkers(false);
	}
	@Command("doSelectAll")
	@NotifyChange("allCbSelected")
	public void doSelectAll(){
		List<VMarkerSummaryEntity> markerListOnDisplay = getMarkerList();

		selectedMarkerList.clear(); //clear the list first and then just add if there are any selected

		setAllCbSelected(isCbAllMarkers());

		if (isCbAllMarkers()) {
			try{
				for(VMarkerSummaryEntity u: markerListOnDisplay){
					selectedMarkerList.add(u);
				}
			}catch(NullPointerException npe){
				Messagebox.show("Submit an empty query to display all markers", "There is nothing to select", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	@Command("changeEnabled")
	@NotifyChange({"iDBoxDisabled","nameListDisabled"})
	public void changeEnabled(){
		isIDBoxDisabled = false; // reseet
		isNameListDisabled= false; 

		if(markerEntity.getMarkerNamesAsCommaSeparatedString()!=null && !markerEntity.getMarkerNamesAsCommaSeparatedString().isEmpty()){
			isIDBoxDisabled = true;
		}else if(markerEntity.getMarkerIDStartRange() != null ){
			if(markerEntity.getMarkerIDStartRange() >0 ){
				isNameListDisabled=true;
			}
		}else if(markerEntity.getMarkerIDEndRange() != null ){
			if(markerEntity.getMarkerIDEndRange() >0 ){
				isNameListDisabled=true;
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command("deleteSelectedMarkers")
	public void deleteMarkers(){

		if(selectedMarkerList.isEmpty()){ //Nothing is selected
			Messagebox.show("There are no markers selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		else{
			StringBuilder sb = new StringBuilder();

			for(VMarkerSummaryEntity u: selectedMarkerList){
				sb.append("\n"+u.getMarkerName());
			}


			Messagebox.show("Are you sure you want to delete the following markers?"+sb.toString(), 
					"Confirm Delete", Messagebox.YES | Messagebox.CANCEL,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener(){
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if(Messagebox.ON_YES.equals(event.getName())){
						//YES is clicked
						boolean successful;

						if(selectedMarkerList.size() == 1){  // just one marker is selected
							successful = viewModelService.deleteMarkers(selectedMarkerList.get(0));
						}else{
							//bulk delete
							successful = viewModelService.deleteMarkers(selectedMarkerList);
						}

						if(successful) BindUtils.postGlobalCommand(null, null, "retrieveMarkerList", null);

					}
				}
			});
		}
	}

	@GlobalCommand("retrieveMarkerList")
	@NotifyChange({"markerList", "selectedMarkerList", "allCbSelected", "cbAllMarkers"})
	public void retrieveMarkerList(){
		//...

		setMarkerList(viewModelService.getAllMarkersBasedOnQuery(markerEntity));

		selectedMarkerList.clear();

		setAllCbSelected(false);
		setCbAllMarkers(false);
	}

	@Command("updateSelectMarker")
	@NotifyChange({"cbAllMarkers", "selectedMarkerList"})
	public void updateSelectMarker(@BindingParam("markerChecked") VMarkerSummaryEntity markerList, @BindingParam("isChecked") Boolean isChecked){
		if(isChecked){
			selectedMarkerList.add(markerList);
		}else{
			setCbAllMarkers(false);

			ListIterator<VMarkerSummaryEntity> it = selectedMarkerList.listIterator();
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
	public boolean isCbAllMarkers() {
		return cbAllMarkers;
	}
	public void setCbAllMarkers(boolean cbAllMarkers) {
		this.cbAllMarkers = cbAllMarkers;
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

	public List<VMarkerSummaryEntity> getMarkerList() {
		return markerList;
	}

	public void setMarkerList(List<VMarkerSummaryEntity> list) {
		this.markerList = list;
	}

	public List<PlatformRecord> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(List<PlatformRecord> platformList) {
		this.platformList = platformList;
	}

}
