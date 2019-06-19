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

import org.gobiiproject.datatimescope.db.generated.tables.records.AnalysisRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ExperimentRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MapsetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.OrganizationRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VendorProtocolRecord;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.DbRecord;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.VMarkerSummaryEntity;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.UpdatableRecordImpl;
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
	private boolean cbAllMarkers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, performedDeleteSuccesfully=false, paged=false, gridGroupVisible=true;
	private boolean dbPlatforms=true, dbVendors=true, dbVendorProtocols=true, dbAnalyses=true, dbProjects=true, dbLinkageGroup=true, dbExperiment=true, dbDataset=true, dbMapset=true, dbCallingAnalysis=true, dbFilterProject=true;
	private List<VMarkerSummaryEntity> markerList, selectedMarkerList;
	private List<PlatformRecord> platformList;
	private List<OrganizationRecord> vendorList;
	private List<VendorProtocolRecord> vendorProtocolList;
	private List<AnalysisRecord> callingAnalysisList, analysesList;
	private List<ProjectRecord> projectList;
	private List<ExperimentRecord> experimentList;
	private List<DatasetRecord> datasetList;
	private List<MapsetRecord> mapsetList;
	private List<LinkageGroupRecord> linkageGroupList;
	
	private MarkerRecordEntity markerEntity;
	private List<DatasetSummaryEntity> markerSummary;
	private DatasetSummaryEntity markerSummaryEntity;
	
	private String filterPlatform;
	private String filterVendor;
	private String filterVendorProtocol;
	private String filterAnalyses;
	private String filterProjects;
	private String filterExperiment;
	private String filterDataset;
	private String filterMapset;
	private String filterCallingAnalysis;
	private String filterProject;
	private String filterLinkageGroup;

	@SuppressWarnings("unchecked")
	@Init
	public void init() {

		markerSummaryEntity= new DatasetSummaryEntity();
		markerList = new ArrayList<VMarkerSummaryEntity>();
		selectedMarkerList = new ArrayList<VMarkerSummaryEntity>();
		viewModelService = new ViewModelServiceImpl();
		
		setMarkerEntity(new MarkerRecordEntity());
		setPlatformList(viewModelService.getAllPlatforms());
		setVendorList(viewModelService.getAllVendors());
		setVendorProtocolList(viewModelService.getAllVendorProtocols());
		setCallingAnalysisList(viewModelService.getAllCallingAnalysis());
		setAnalysesList(viewModelService.getAllAnalyses());
		setProjectList(viewModelService.getAllProjects());
		setExperimentList(viewModelService.getAllExperiments());
		setDatasetList(viewModelService.getAllDatasets());
		setMapsetList(viewModelService.getAllMapsets());
		setLinkageGroupList(viewModelService.getAllLinkageGroups());
		if(platformList.isEmpty()) dbPlatforms = false;
		if(vendorList.isEmpty()) dbVendors = false;
		if(vendorProtocolList.isEmpty()) dbVendorProtocols = false;		
		if(callingAnalysisList.isEmpty()) dbCallingAnalysis = false;
		if(analysesList.isEmpty()) dbAnalyses = false;
		if(projectList.isEmpty()) dbProjects = false;		
		if(experimentList.isEmpty()) dbExperiment = false;
		if(datasetList.isEmpty()) dbDataset = false;		
		if(mapsetList.isEmpty()) dbMapset = false;
		if(linkageGroupList.isEmpty()) dbLinkageGroup = false;
		
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

			sb.append("the following markers?");
			for(VMarkerSummaryEntity u: selectedMarkerList){
				sb.append("\n"+u.getMarkerName());
			}

			if (selectedMarkerList.size()>10){
				
				sb =  new StringBuilder();
				sb.append(Integer.toString(selectedMarkerList.size())+" markers?");
				
			}

			Messagebox.show("Are you sure you want to delete "+sb.toString(), 
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
							successful = viewModelService.deleteMarker(selectedMarkerList.get(0), markerSummary, markerSummaryEntity);
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
	

	@NotifyChange("platformList")
	@Command
	public void doSearchPlatform() {
		 List<PlatformRecord> allItems = viewModelService.getAllPlatforms();
		 filterItems(platformList, allItems, filterPlatform);
	}
	
	@NotifyChange("vendorList")
	@Command
	public void doSearchVendor() {
		List<OrganizationRecord> allItems = viewModelService.getAllVendors();
		
		filterItems(vendorList, allItems, filterVendor);
	}
	
	@NotifyChange("vendorProtocolList")
	@Command
	public void doSearchVendorProtocol() {
		List<VendorProtocolRecord> allItems = viewModelService.getAllVendorProtocols();
		
		filterItems(vendorProtocolList, allItems, filterVendorProtocol);
	}
	
	@NotifyChange("callingAnalysisList")
	@Command
	public void doSearchCallingAnalysis() {
		List<AnalysisRecord> allItems = viewModelService.getAllCallingAnalysis();
		
		filterItems(callingAnalysisList, allItems, filterCallingAnalysis);
	}
	
	@NotifyChange("analysesList")
	@Command
	public void doSearchAnalyses() {
		List<AnalysisRecord> allItems = viewModelService.getAllAnalyses();
		
		filterItems(analysesList, allItems, filterAnalyses);
	}
	
	@NotifyChange("projectList")
	@Command
	public void doSearchProjects() {
		List<ProjectRecord> allItems = viewModelService.getAllProjects();
		
		filterItems(projectList, allItems, filterProject);
	}

	@NotifyChange("experimentList")
	@Command
	public void doSearchExperiment() {
		List<ExperimentRecord> allItems = viewModelService.getAllExperiments();
		
		filterItems(experimentList, allItems, filterExperiment);
	}		
	
	@NotifyChange("datasetList")
	@Command
	public void doSearchDataset() {
		List<DatasetRecord> allItems = viewModelService.getAllDatasets();
		
		filterItems(datasetList, allItems, filterDataset);
	}		
	
	@NotifyChange("mapasetList")
	@Command
	public void doSearchMapset() {
		List<MapsetRecord> allItems = viewModelService.getAllMapsets();
		
		filterItems(mapsetList, allItems, filterMapset);
	}
	
	@NotifyChange("linkageGroupList")
	@Command
	public void doSearchLinkageGroup() {
		List<LinkageGroupRecord> allItems = viewModelService.getAllLinkageGroups();
		
		filterItems(linkageGroupList, allItems, filterLinkageGroup);
	}
	
	public <T> void filterItems( List<T> list, List<T> allItems, String filter) {
		list.clear();
		
		if(filter == null || "".equals(filter)) {
			list.addAll(allItems);
		} else {
			for(T item : allItems) {
				if(((String) ((Record) item).get(1)).toLowerCase().indexOf(filter.toLowerCase()) >= 0) {
					list.add(item);
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
		if(list.size()<1) setGridGroupVisible(true);
		else setGridGroupVisible(false);
			
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


	public List<OrganizationRecord> getVendorList() {
		return vendorList;
	}


	public void setVendorList(List<OrganizationRecord> vendorList) {
		this.vendorList = vendorList;
	}


	public List<VendorProtocolRecord> getVendorProtocolList() {
		return vendorProtocolList;
	}


	public void setVendorProtocolList(List<VendorProtocolRecord> vendorProtocolList) {
		this.vendorProtocolList = vendorProtocolList;
	}


	public List<AnalysisRecord> getCallingAnalysisList() {
		return callingAnalysisList;
	}


	public void setCallingAnalysisList(List<AnalysisRecord> callingAnalysisList) {
		this.callingAnalysisList = callingAnalysisList;
	}


	public List<AnalysisRecord> getAnalysesList() {
		return analysesList;
	}


	public void setAnalysesList(List<AnalysisRecord> analysesList) {
		this.analysesList = analysesList;
	}


	public List<ProjectRecord> getProjectList() {
		return projectList;
	}


	public void setProjectList(List<ProjectRecord> projectList) {
		this.projectList = projectList;
	}


	public List<ExperimentRecord> getExperimentList() {
		return experimentList;
	}


	public void setExperimentList(List<ExperimentRecord> experimentList) {
		this.experimentList = experimentList;
	}


	public List<MapsetRecord> getMapsetList() {
		return mapsetList;
	}


	public void setMapsetList(List<MapsetRecord> mapsetList) {
		this.mapsetList = mapsetList;
	}


	public List<LinkageGroupRecord> getLinkageGroupList() {
		return linkageGroupList;
	}


	public void setLinkageGroupList(List<LinkageGroupRecord> linkageGroupList) {
		this.linkageGroupList = linkageGroupList;
	}


	public List<DatasetRecord> getDatasetList() {
		return datasetList;
	}


	public void setDatasetList(List<DatasetRecord> datasetList) {
		this.datasetList = datasetList;
	}


	public String getFilterPlatform() {
		return filterPlatform;
	}


	public void setFilterPlatform(String filterPlatform) {
		this.filterPlatform = filterPlatform;
	}


	public String getFilterVendor() {
		return filterVendor;
	}


	public void setFilterVendor(String filterVendor) {
		this.filterVendor = filterVendor;
	}


	public String getFilterProjects() {
		return filterProjects;
	}


	public void setFilterProjects(String filterProjects) {
		this.filterProjects = filterProjects;
	}


	public String getFilterVendorProtocol() {
		return filterVendorProtocol;
	}


	public void setFilterVendorProtocol(String filterVendorProtocol) {
		this.filterVendorProtocol = filterVendorProtocol;
	}


	public String getFilterAnalyses() {
		return filterAnalyses;
	}


	public void setFilterAnalyses(String filterAnalyses) {
		this.filterAnalyses = filterAnalyses;
	}


	public String getFilterExperiment() {
		return filterExperiment;
	}


	public void setFilterExperiment(String filterExperiment) {
		this.filterExperiment = filterExperiment;
	}


	public String getFilterDataset() {
		return filterDataset;
	}


	public void setFilterDataset(String filterDataset) {
		this.filterDataset = filterDataset;
	}


	public String getFilterMapset() {
		return filterMapset;
	}


	public void setFilterMapset(String filterMapset) {
		this.filterMapset = filterMapset;
	}


	public String getFilterCallingAnalysis() {
		return filterCallingAnalysis;
	}


	public void setFilterCallingAnalysis(String filterCallingAnalysis) {
		this.filterCallingAnalysis = filterCallingAnalysis;
	}


	public String getFilterProject() {
		return filterProject;
	}


	public void setFilterProject(String filterProject) {
		this.filterProject = filterProject;
	}


	public String getFilterLinkageGroup() {
		return filterLinkageGroup;
	}


	public void setFilterLinkageGroup(String filterLinkageGroup) {
		this.filterLinkageGroup = filterLinkageGroup;
	}


	public boolean isDbPlatforms() {
		return dbPlatforms;
	}


	public void setDbPlatforms(boolean dbPlatforms) {
		this.dbPlatforms = dbPlatforms;
	}


	public boolean isDbVendors() {
		return dbVendors;
	}


	public void setDbVendors(boolean dbVendors) {
		this.dbVendors = dbVendors;
	}


	public boolean isDbVendorProtocols() {
		return dbVendorProtocols;
	}


	public void setDbVendorProtocols(boolean dbVendorProtocols) {
		this.dbVendorProtocols = dbVendorProtocols;
	}


	public boolean isDbAnalyses() {
		return dbAnalyses;
	}


	public void setDbAnalyses(boolean dbAnalyses) {
		this.dbAnalyses = dbAnalyses;
	}


	public boolean isDbProjects() {
		return dbProjects;
	}


	public void setDbProjects(boolean dbProjects) {
		this.dbProjects = dbProjects;
	}


	public boolean isDbExperiment() {
		return dbExperiment;
	}


	public void setDbExperiment(boolean dbExperiment) {
		this.dbExperiment = dbExperiment;
	}


	public boolean isDbDataset() {
		return dbDataset;
	}


	public void setDbDataset(boolean dbDataset) {
		this.dbDataset = dbDataset;
	}


	public boolean isDbMapset() {
		return dbMapset;
	}


	public void setDbMapset(boolean dbMapset) {
		this.dbMapset = dbMapset;
	}


	public boolean isDbCallingAnalysis() {
		return dbCallingAnalysis;
	}


	public void setDbCallingAnalysis(boolean dbCallingAnalysis) {
		this.dbCallingAnalysis = dbCallingAnalysis;
	}


	public boolean isDbFilterProject() {
		return dbFilterProject;
	}


	public void setDbFilterProject(boolean dbFilterProject) {
		this.dbFilterProject = dbFilterProject;
	}


	public boolean isDbLinkageGroup() {
		return dbLinkageGroup;
	}


	public void setDbLinkageGroup(boolean dbLinkageGroup) {
		this.dbLinkageGroup = dbLinkageGroup;
	}


	public boolean isGridGroupVisible() {
		return gridGroupVisible;
	}


	public void setGridGroupVisible(boolean gridGroupVisible) {
		this.gridGroupVisible = gridGroupVisible;
	}

}
