package org.gobiiproject.datatimescope.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
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
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

public class MarkerViewModel {
	//UI component


	@Wire("#markerGrid")
	Grid markerGrid;
	
	ViewModelService viewModelService;
	private boolean cbAllMarkers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, performedDeleteSuccesfully=false, paged=false;

	private List<VMarkerSummaryEntity> markerList, selectedMarkerList;
	private List<PlatformRecord> platformList;
	private MarkerRecordEntity markerEntity;
	private List<DatasetSummaryEntity> markerSummary;
	private DatasetSummaryEntity markerSummaryEntity;

	@SuppressWarnings("unchecked")
	@Init
	public void init() {

		markerSummaryEntity= new DatasetSummaryEntity();
		markerList = new ArrayList<VMarkerSummaryEntity>();
		selectedMarkerList = new ArrayList<VMarkerSummaryEntity>();
		viewModelService = new ViewModelServiceImpl();
		setMarkerEntity(new MarkerRecordEntity());
		setMarkerList(viewModelService.getAllMarkers(markerSummary));
		setPlatformList(viewModelService.getAllPlatforms());
		
		UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
        markerSummary = (List<DatasetSummaryEntity>) Sessions.getCurrent().getAttribute("markerSummary");
		
		  if(markerSummary.size()>0){
	        	performedDeleteSuccesfully=true;
	        }
			
	}
	

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command("submitQuery")
	@NotifyChange({"markerList","selectedMarkerList", "allCbSelected", "cbAllMarkers","paged"})
	public void submitQuery(){

		try{
			markerList.clear(); //clear the list first and then just add if there are any selected
		}catch(NullPointerException e){

		}

		setMarkerList(viewModelService.getAllMarkersBasedOnQuery(markerEntity,markerSummaryEntity));

		setAllCbSelected(false);
		setCbAllMarkers(false);

	}

	@Command("resetMarkerTab")
	@NotifyChange({"markerList","selectedMarkerList", "allCbSelected", "cbAllMarkers", "markerEntity","iDBoxDisabled","nameListDisabled","paged"})
	public void resetMarkerTab(){
		try{
			markerList.clear(); //clear the list first and then just add if there are any selected
			selectedMarkerList.clear(); 
		}catch(NullPointerException e){

		}
		markerEntity = new MarkerRecordEntity();


		setMarkerList(viewModelService.getAllMarkers(markerSummary));
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
	@NotifyChange({"markerSummary","performedDeleteSuccesfully"})
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
							successful = viewModelService.deleteMarkers(selectedMarkerList.get(0), markerSummary, markerSummaryEntity);
						}else{
							//bulk delete
							successful = viewModelService.deleteMarkers(selectedMarkerList, markerSummary, markerSummaryEntity);
						}

						if(successful) BindUtils.postGlobalCommand(null, null, "retrieveMarkerList", null);

					}
				}
			});
		}
	}

	@GlobalCommand("retrieveMarkerList")
	@NotifyChange({"markerList", "selectedMarkerList", "allCbSelected", "cbAllMarkers","paged"})
	public void retrieveMarkerList(){
		//...

		setMarkerList(viewModelService.getAllMarkersBasedOnQuery(markerEntity, markerSummaryEntity));

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

	@SuppressWarnings("unchecked")
	@Command("resetMarkerSummary")
	@NotifyChange({"markerSummary","performedDeleteSuccesfully"})
	public void resetDSSummary(){
		markerSummary = (List<DatasetSummaryEntity>) Sessions.getCurrent().getAttribute("markerSummary");
		
        
        if(markerSummary.size()>0){
        	performedDeleteSuccesfully=true;
        }
	}
	

	@Command("exportMarkerTable")
	public void exportMarkerTable() {

		ListIterator<VMarkerSummaryEntity> it = markerList.listIterator();
		StringBuffer buffMap = new StringBuffer();

		while (it.hasNext()) {

			VMarkerSummaryEntity next = it.next();

			if(it.nextIndex()==1){
				buffMap.append(next.getHeaderDelimitedBy(","));
			}
			buffMap.append(next.getAllDelimitedBy(","));

		}

		FileWriter fw;
		try {
			File file = new File("timescope_marker.csv");
			fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(buffMap.toString());
			bw.flush();
			bw.close();

			InputStream is = new FileInputStream(file);
			Filedownload.save(is, "text/csv", file.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Command("exportCurrentMarkerTablePage")
	public void exportCurrentMarkerTablePage() {


		int ActivePage = markerGrid.getActivePage();
		int initial, last;
		if(ActivePage==0){
			initial=1;
		}else{
			initial=(ActivePage*markerGrid.getPageSize())+1;
		}

		StringBuffer buffMap = new StringBuffer();


		List<Integer> indices = new ArrayList<Integer>();

		last = initial+markerGrid.getPageSize();
		//get Indices
		for( int i = initial; i<last; i++){

			indices.add(i);

		}

		ListIterator<VMarkerSummaryEntity> it = markerList.listIterator();

		while (it.hasNext()) {

			VMarkerSummaryEntity next = it.next();
			int nextIndex = it.nextIndex();

			if(nextIndex==1){
				buffMap.append(next.getHeaderDelimitedBy(","));
			}

			if(indices.contains(nextIndex)){
				buffMap.append(next.getAllDelimitedBy(","));
			}

		}

		FileWriter fw;
		try {
			File file = new File("timescope_marker_currentpage.csv");
			fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(buffMap.toString());
			bw.flush();
			bw.close();

			InputStream is = new FileInputStream(file);
			Filedownload.save(is, "text/csv", file.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		if(list.size() > 25) setPaged(true);
		else setPaged(false);
		
		this.markerList = list;
	}

	public List<PlatformRecord> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(List<PlatformRecord> platformList) {
		this.platformList = platformList;
	}

	public boolean isPerformedDeleteSuccesfully() {
		return performedDeleteSuccesfully;
	}

	public void setPerformedDeleteSuccesfully(boolean performedDeleteSuccesfully) {
		this.performedDeleteSuccesfully = performedDeleteSuccesfully;
	}

	public DatasetSummaryEntity getMarkerSummaryEntity() {
		return markerSummaryEntity;
	}

	public void setMarkerSummaryEntity(DatasetSummaryEntity markerSummaryEntity) {
		this.markerSummaryEntity = markerSummaryEntity;
	}
	
	public List<DatasetSummaryEntity> getMarkerSummary() {
		return markerSummary;
	}

	public void setMarkerSummary(List<DatasetSummaryEntity> markerSummary) {
		this.markerSummary = markerSummary;
	}

	public boolean isPaged() {
		return paged;
	}

	public void setPaged(boolean paged) {
		this.paged = paged;
	}
}
