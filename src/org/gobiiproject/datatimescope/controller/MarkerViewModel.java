package org.gobiiproject.datatimescope.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
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
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.OrganizationRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VendorProtocolRecord;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.VMarkerSummaryEntity;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.Utils;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class MarkerViewModel {
    //UI component


    @Wire("#markerGrid")
    Grid markerGrid;
    @Wire("#markerDetailPopup")
    Popup markerDetailPopup;
    @Wire("#lblDatasetFilter")
    Textbox lblDatasetFilter;

    ViewModelService viewModelService;

    private Integer sizeMarkerList=0;
    private boolean cbAllMarkers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, performedDeleteSuccesfully=false, paged=false, gridGroupVisible=true, shouldNextChangeResetOtherFilterValues=false;
    private boolean markerAssociated=true, dbPlatforms=true, dbVendors=true, dbVendorProtocols=true, dbAnalyses=true, dbProjects=true, dbLinkageGroup=true, dbExperiment=true, dbDataset=true, dbMapset=true, dbCallingAnalysis=true, dbFilterProject=true;
    private List<VMarkerSummaryEntity> markerList, selectedMarkerList;
    private List<PlatformRecord> platformList;
    private List<OrganizationRecord> vendorList;
    private List<VendorProtocolRecord> vendorProtocolList, backupVendorProtocolList;
    private List<AnalysisRecord> callingAnalysisList, analysesList;
    private List<ProjectRecord> projectList, backupProjectList;
    private List<ExperimentRecord> experimentList, backupExperimentList;
    private List<DatasetRecord> datasetList, markerDetailDatasetList, backupDatasetList;
    private List<MapsetRecord> mapsetList;
    private List<LinkageGroupRecord> linkageGroupList, markerDetailLinkageGroupList, backupLinkageGroupList;
    private List<MarkerGroupRecord> markerDetailsMarkerGroupList;
    private List<DatasetSummaryEntity> markerSummary;

    private MarkerRecordEntity markerEntity;
    private DatasetSummaryEntity markerSummaryEntity;

    private String filterPlatform, platformTabLabel;
    private String filterVendor;
    private String filterVendorProtocol, vendorProtocolTabLabel;
    private String filterAnalyses, analysisTabLabel;
    private String filterProject, projectsTabLabel;
    private String filterExperiment, experimentTabLabel;
    private String filterDataset, datasetTabLabel;
    private String filterMapset, mapsetTabLabel;
    private String filterCallingAnalysis;
    private String filterLinkageGroup, linkageGroupTabLabel;
    private String currentFiltersAsText;

    @SuppressWarnings("unchecked")
    @Init
    public void init() {
        markerEntity = new MarkerRecordEntity();
        markerSummaryEntity= new DatasetSummaryEntity();
        markerList = new ArrayList<VMarkerSummaryEntity>();
        selectedMarkerList = new ArrayList<VMarkerSummaryEntity>();
        markerDetailLinkageGroupList = new ArrayList<LinkageGroupRecord>();
        markerDetailDatasetList = new ArrayList<DatasetRecord>();
        markerDetailsMarkerGroupList = new ArrayList<MarkerGroupRecord>();
        viewModelService = new ViewModelServiceImpl();

        backupVendorProtocolList =  new ArrayList<VendorProtocolRecord>();
        backupProjectList =  new ArrayList<ProjectRecord>();
        backupExperimentList =  new ArrayList<ExperimentRecord>();
        backupDatasetList  =  new ArrayList<DatasetRecord>();
        backupLinkageGroupList = new ArrayList<LinkageGroupRecord>();

        populateFilterLists();


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

    @Command("showMarkerDetail")
    @NotifyChange({"markerDetailDatasetList","markerDetailsMarkerGroupList", "markerDetailLinkageGroupList"})
    public void showMarkerDetail(@BindingParam("markerId") Integer markerId, @BindingParam("markerName") String markerName){

        setMarkerDetailLinkageGroupList(viewModelService.getLinkageGroupsAssociatedToMarkerId(markerId));
        setMarkerDetailsMarkerGroupList(viewModelService.getMarkerGroupsAssociatedToMarkerId(markerId));
        setMarkerDetailDatasetList(viewModelService.getDatasetAssociatedToMarkerId(markerId));

        if(markerDetailsMarkerGroupList.size()<1 && markerDetailLinkageGroupList.size()<1 && markerDetailDatasetList.size()<1) {
            setMarkerAssociated(false);
        }

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("markerName", markerName);
        args.put("markerDetailsMarkerGroupList", markerDetailsMarkerGroupList);
        args.put("markerDetailDatasetList", markerDetailDatasetList);
        args.put("markerDetailLinkageGroupList", markerDetailLinkageGroupList);
        args.put("markerAssociated", markerAssociated);

        Window window = (Window)Executions.createComponents(
                "/marker_detail.zul", null, args);
        window.setPosition("center");
        window.setClosable(true);
        window.doModal();
    }

    @Command("noDatasetsSelectedAsFilter")
    @NotifyChange({"dbDataset","lblDatasetFilter","currentFiltersAsText"})
    public void noDatasetsSelectedAsFilter(@BindingParam("isChecked") Boolean isChecked){
        if(isChecked){
            markerEntity.setMarkerNotInDatasets(true);
            dbDataset=false;
            lblDatasetFilter.setValue("Will search for markers that are not associated with any dataset.");
        }else{
            if(!Utils.isListNotNullOrEmpty(datasetList)) { // if dataset is empty just change the label
                dbDataset=false;
                lblDatasetFilter.setValue("There's nothing to see here");
            }else { //not empty then display search bar
                dbDataset=true;
            }
            markerEntity.setMarkerNotInDatasets(false);
        }

        currentFiltersAsText = markerEntity.getFiltersAsTextWithDelimiter(System.getProperty("line.separator"));
    }

    @Command("displayFilterDetails")
    public void displayFilterDetails(@BindingParam("category") String category){
        String title="This list is being filtered by: ";
        StringBuilder sb = new StringBuilder();
        String filterInfo="";

        switch(category){
        case "experiment":
        case "project":
        case "vendorprotocol":
        case "datasets":
            if(!markerEntity.getPlatformList().isEmpty()) {
                sb.append("\n Platform(s): \n"+ Utils.getListNamesToString(markerEntity.getPlatformList())+"\n");
            }else {
                filterInfo="This list is not yet filtered. You can filter it by selecting a platform.";
            }

            if(category.equalsIgnoreCase("vendorprotocol")) break;

            if(!markerEntity.getVendorProtocolList().isEmpty()) {
                sb.append("\n Vendor-Protocol(s): \n"+ Utils.getListNamesToString(markerEntity.getVendorProtocolList())+"\n");
            } else {

                filterInfo="This list is not yet filtered. You can filter it by selecting a platform and/or a vendor-protocol.";
            }

            if(category.equalsIgnoreCase("project")) break;

            if(!markerEntity.getProjectList().isEmpty()) {
                sb.append("\n Project(s): \n "+ Utils.getListNamesToString(markerEntity.getProjectList())+"\n");
            }  else {

                filterInfo="This list is not yet filtered. You can filter it by selecting a platform, vendor-protocol, and/or a project.";
            }
            if(category.equalsIgnoreCase("experiment")) break;

            if(!markerEntity.getExperimentList().isEmpty()) {
                sb.append("\n Experiment(s): \n "+ Utils.getListNamesToString(markerEntity.getExperimentList())+"\n");
            } else {

                filterInfo="This list is not yet filtered. You can filter it by selecting a platform, vendor-protocol, project, and/or an experiment.";
            }

            if(!markerEntity.getAnalysesList().isEmpty()) {
                sb.append("\n Anaylsis: \n"+ Utils.getListNamesToString(markerEntity.getAnalysesList())+"\n");
            }  else {

                filterInfo="This list is not yet filtered. You can filter it by selecting a platform, vendor-protocol, project, experiment, and/or an analysis.";
            }
            break;
        case "linkagegroup": 
            if(!markerEntity.getMapsetList().isEmpty()) {
                sb.append("\n Mapset(s): \n"+ Utils.getListNamesToString(markerEntity.getMapsetList())+"\n");
            }else  filterInfo="This list is not yet filtered. You can filter it by selecting a mapset.";
            break;
        default: 
            title=" All items are displayed.";
            sb.append("This list is not affected by filters.");
        }

        if(sb.toString().isEmpty()) {

            Messagebox.show(filterInfo, "All items are displayed.", Messagebox.OK, Messagebox.NONE);
        }
        else Messagebox.show(sb.toString(), title, Messagebox.OK, Messagebox.NONE);

    }

    @Command("validateForReset")
    @NotifyChange({"markerEntity", "currentFiltersAsText"})
    public void validateForReset(@BindingParam("category") String category){

        //if reset required then: if(shouldNextChangeResetOtherFilterValues)  

        //whether reset or not, update no. of lists being displayed beside the label by calling the postGlobalCommand
        switch(category){

        case "platform": 
            if(Utils.isListNotNullOrEmpty(markerEntity.getVendorProtocolList())) {

                if(shouldNextChangeResetOtherFilterValues) markerEntity.getVendorProtocolList().clear();
            }

            BindUtils.postGlobalCommand(null, null, "updateVendorProtocolTab", null);
        case "vendorprotocol": 
            if(Utils.isListNotNullOrEmpty(markerEntity.getProjectList())) {

                if(shouldNextChangeResetOtherFilterValues) markerEntity.getProjectList().clear();
            } 

            BindUtils.postGlobalCommand(null, null, "updateProjectsTab", null);
        case "project":
            if(Utils.isListNotNullOrEmpty(markerEntity.getExperimentList())) {

                if(shouldNextChangeResetOtherFilterValues) markerEntity.getExperimentList().clear();
            }

            BindUtils.postGlobalCommand(null, null, "updateExperimentsTab", null);
        case "experiment":
        case "analyses":
            if(Utils.isListNotNullOrEmpty(markerEntity.getDatasetList())) {
                if(shouldNextChangeResetOtherFilterValues) markerEntity.getDatasetList().clear();
            }

            BindUtils.postGlobalCommand(null, null, "updateDatasetsTab", null);
            break;
        case "mapset": 
            if(Utils.isListNotNullOrEmpty(markerEntity.getLinkageGroupList())) {
                if(shouldNextChangeResetOtherFilterValues) markerEntity.getLinkageGroupList().clear();
            }

            BindUtils.postGlobalCommand(null, null, "updateLinkageGroupsTab", null);
            break; 
        default: 
            break; 
        }
        //once resets are done, update boolean
        shouldNextChangeResetOtherFilterValues=false;
        currentFiltersAsText = markerEntity.getFiltersAsTextWithDelimiter(System.getProperty("line.separator"));
    }

    @Command("submitQuery")
    @NotifyChange({"markerList","sizeMarkerList", "selectedMarkerList", "allCbSelected", "cbAllMarkers","paged"})
    public void submitQuery(){

        try{
            markerList.clear(); //clear the list first and then just add if there are any selected
        }catch(NullPointerException e){

        }
        //		setMarkerList(viewModelService.getAllMarkers(markerSummary));
        setMarkerList(viewModelService.getAllMarkersBasedOnQuery(markerEntity,markerSummaryEntity));

        setAllCbSelected(false);
        setCbAllMarkers(false);
    }

    @Command("resetMarkerTab")
    @NotifyChange({"markerList","sizeMarkerList", "selectedMarkerList", "allCbSelected", "cbAllMarkers", "markerEntity", "currentFiltersAsText", "iDBoxDisabled","nameListDisabled","paged","platformList","vendorProtocolList", "analysesList", "projectList", "experimentList", "datasetList", "mapsetList", "linakageGroupList",
        "dbAnalyses", "dbProjects", "dbExperiment", "dbMapset", "dbLinkageGroup", "dbDataset", "dbVendorProtocols",
        "linkageGroupTabLabel","vendorProtocolTabLabel","projectsTabLabel","experimentTabLabel","datasetTabLabel"})
    public void resetMarkerTab(){
        try{
            markerList.clear(); //clear the list first and then just add if there are any selected
            selectedMarkerList.clear(); 
        }catch(NullPointerException e){

        }
        markerEntity = new MarkerRecordEntity();
        currentFiltersAsText = "";

        populateFilterLists();
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
        isIDBoxDisabled = false; // reset
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

                        if(selectedMarkerList.size() == 1){  // just one marker is selected
                            viewModelService.deleteMarker(selectedMarkerList.get(0), markerSummary);
                        }else{
                            //bulk delete
                            viewModelService.deleteMarkers(selectedMarkerList, markerSummary);
                        }

                    }
                }
            });
        }
    }

    @GlobalCommand("retrieveMarkerList")
    @NotifyChange({"markerList", "sizeMarkerList", "selectedMarkerList", "allCbSelected", "cbAllMarkers","paged"})
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
                buffMap.append(next.getHeaderDelimitedBy("\t"));
            }
            buffMap.append(next.getAllDelimitedBy("\t"));

        }

        FileWriter fw;
        try {
            File file = new File("timescope_marker.txt");
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(buffMap.toString());
            bw.flush();
            bw.close();

            InputStream is = new FileInputStream(file);
            Filedownload.save(is, "text/plain", file.getName());
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
                buffMap.append(next.getHeaderDelimitedBy("\t"));
            }

            if(indices.contains(nextIndex)){
                buffMap.append(next.getAllDelimitedBy("\t"));
            }

        }

        FileWriter fw;
        try {
            File file = new File("timescope_marker_currentpage.txt");
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(buffMap.toString());
            bw.flush();
            bw.close();

            InputStream is = new FileInputStream(file);
            Filedownload.save(is, "text/plain", file.getName());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void populateFilterLists() {
        // TODO Auto-generated method stub
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


        // If List is noll or empty, make invisible.
        setDbAnalyses(Utils.isListNotNullOrEmpty(analysesList));
        setDbMapset(Utils.isListNotNullOrEmpty(mapsetList));
        setDbPlatforms(Utils.isListNotNullOrEmpty(platformList));
    }



    /**************************************************************** Methods related to tab selection *************************************************************/ 

    @Command
    public void selectPlatformTab() {

        showInfoPopUp("platform");
    }


    @GlobalCommand
    public void selectVendorProtocolTab() {

        showInfoPopUp("vendorprotocol");
    }

    @NotifyChange({"vendorProtocolList","vendorProtocolTabLabel","dbVendorProtocols"})
    @GlobalCommand
    public void updateVendorProtocolTab() {

        if(Utils.isListNotNullOrEmpty(markerEntity.getPlatformList())) {
            //          Messagebox.show("There are platforms selected");
            setVendorProtocolList(viewModelService.getVendorProtocolByPlatformId(markerEntity.getPlatformList()));

        }else{

            setVendorProtocolList(viewModelService.getAllVendorProtocols());
        }

        setVendorProtocolTabLabel(vendorProtocolList.size());
    }

    @Command
    public void selectMapsetsTab() {
        //Displaying all mapsets instead

        showInfoPopUp("mapset");
    }

    @NotifyChange({"linkageGroupList","linkageGroupTabLabel", "dbLinkageGroup"}) 
    @GlobalCommand
    public void updateLinkageGroupsTab() {
        if(Utils.isListNotNullOrEmpty(markerEntity.getMapsetList())) {

            setLinkageGroupList(viewModelService.getLinkageGroupByMapsetId(markerEntity.getMapsetList()));
        }else{

            setLinkageGroupList(viewModelService.getAllLinkageGroups());
        }
    }

    @GlobalCommand
    public void selectProjectsTab() { // Projects are connected to a platform and a vendor-protocol (via experiment)

        showInfoPopUp("project");
    }

    @NotifyChange({"projectList", "projectsTabLabel", "dbProjects"}) 
    @GlobalCommand
    public void updateProjectsTab() { // Projects are connected to a platform and a vendor-protocol (via experiment)

        if(Utils.isListNotNullOrEmpty(markerEntity.getVendorProtocolList())) { // if not empty, use vendor-protocol as filter

            setProjectList(viewModelService.getProjectsByVendorProtocolID(markerEntity.getVendorProtocolList()));


        }else if(Utils.isListNotNullOrEmpty(markerEntity.getPlatformList())) { // check if platform filter is not empty and filter by that instead

            setProjectList(viewModelService.getProjectsByPlatformID(markerEntity.getPlatformList()));

        }else{

            setProjectList(viewModelService.getAllProjects());
        }
    }


    @GlobalCommand
    public void selectExperimentsTab() { // Experiments are connected to a specific project then a vendor-protocol and a platform

        showInfoPopUp("experiment");
    }

    @NotifyChange({"experimentList","experimentTabLabel", "dbExperiment"})
    @GlobalCommand
    public void updateExperimentsTab() { // Experiments are connected to a specific project then a vendor-protocol and a platform

        if(Utils.isListNotNullOrEmpty(markerEntity.getProjectList())) { // if not empty, use project as filter

            setExperimentList(viewModelService.getExperimentsByProjectID(markerEntity.getProjectList()));

        }
        else if(Utils.isListNotNullOrEmpty(markerEntity.getVendorProtocolList())) { // if not empty, use vendor-protocol as filter

            setExperimentList(viewModelService.getExperimentsByVendorProtocolID(markerEntity.getVendorProtocolList()));


        }else if(Utils.isListNotNullOrEmpty(markerEntity.getPlatformList())) { // check if platform filter is not empty and filter by that instead

            setExperimentList(viewModelService.getExperimentsByPlatformID(markerEntity.getPlatformList()));

        }else{

            setExperimentList(viewModelService.getAllExperiments());
        }
    }

    @Command
    public void selectAnalysesTab() { // Displaying all analysis
        showInfoPopUp("analyses");
    }

    @NotifyChange({"datasetList","datasetTabLabel", "dbDataset","lblDatasetFilter","markerEntity","currentFiltersAsText"}) 
    @GlobalCommand
    public void updateDatasetsTab() {

        if(Utils.isListNotNullOrEmpty(markerEntity.getExperimentList())) { // if not empty, use experiment as filter

            if(Utils.isListNotNullOrEmpty(markerEntity.getAnalysesList())) setDatasetList(viewModelService.getDatasetsByExperimentIDandAnalysisId(markerEntity.getExperimentList(),markerEntity.getAnalysesList()));
            else setDatasetList(viewModelService.getDatasetsByExperimentID(markerEntity.getExperimentList()));

        }
        else if(Utils.isListNotNullOrEmpty(markerEntity.getProjectList())) { // if not empty, use project as filter

            if(Utils.isListNotNullOrEmpty(markerEntity.getAnalysesList()))setDatasetList(viewModelService.getDatasetsByProjectIDandAnalysisID(markerEntity.getProjectList(),markerEntity.getAnalysesList()));
            else setDatasetList(viewModelService.getDatasetsByProjectID(markerEntity.getProjectList()));
        }
        else if(Utils.isListNotNullOrEmpty(markerEntity.getVendorProtocolList())) { // if not empty, use vendor-protocol as filter

            if(Utils.isListNotNullOrEmpty(markerEntity.getAnalysesList()))setDatasetList(viewModelService.getDatasetsByVendorProtocolIDandAnalysisID(markerEntity.getVendorProtocolList(),markerEntity.getAnalysesList()));
            else setDatasetList(viewModelService.getDatasetsByVendorProtocolID(markerEntity.getVendorProtocolList()));


        }else if(Utils.isListNotNullOrEmpty(markerEntity.getPlatformList())) { // check if platform filter is not empty and filter by that instead

            if(Utils.isListNotNullOrEmpty(markerEntity.getAnalysesList())) setDatasetList(viewModelService.getDatasetsByPlatformIDandAnalysisID(markerEntity.getPlatformList(),markerEntity.getAnalysesList()));
            else setDatasetList(viewModelService.getDatasetsByPlatformID(markerEntity.getPlatformList()));

        }else{
            if(Utils.isListNotNullOrEmpty(markerEntity.getAnalysesList())) setDatasetList(viewModelService.getAllDatasetsByAnalysisID(markerEntity.getAnalysesList()));
            else setDatasetList(viewModelService.getAllDatasets());
        }

        if(!Utils.isListNotNullOrEmpty(getDatasetList())) { // If it's empty update textbox message
            lblDatasetFilter.setValue("There's nothing to see here.");
            markerEntity.setMarkerNotInDatasets(false);
        }else {
            if(markerEntity.isMarkerNotInDatasets()){
                dbDataset=false;
                lblDatasetFilter.setValue("Will search for markers that are not associated with any dataset.");
            }else{
                dbDataset=true;
            }
        }

        currentFiltersAsText = markerEntity.getFiltersAsTextWithDelimiter(System.getProperty("line.separator"));
    }

    public void showInfoPopUp(String category) {
        StringBuilder sb = new StringBuilder();

        switch(category){


        case "platform": 
            if(Utils.isListNotNullOrEmpty(markerEntity.getVendorProtocolList())) {
                sb.append("\n Vendor-Protocol");
            }
        case "vendorprotocol": 
            if(Utils.isListNotNullOrEmpty(markerEntity.getProjectList())) {
                sb.append(checkIfCommaNeeded(sb,"\n Projects"));
            } 
        case "project":
            if(Utils.isListNotNullOrEmpty(markerEntity.getExperimentList())) {
                sb.append(checkIfCommaNeeded(sb,"\n Experiments"));
            }
        case "experiment":
        case "analyses":
            if(Utils.isListNotNullOrEmpty(markerEntity.getDatasetList())) {
                sb.append(checkIfCommaNeeded(sb, "\n Datasets"));
            }
            break;
        case "mapset": 
            if(Utils.isListNotNullOrEmpty(markerEntity.getLinkageGroupList())) {
                sb.append("\n Linkage Groups");
            }
            break; 
        default: 
            break; 
        } 

        if(!sb.toString().isEmpty()) {
            shouldNextChangeResetOtherFilterValues = true;
            sb.insert(0, "Changing the selected values here will reset the selections you already made on the  tab(s): \n");
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("message", sb.toString());

            Window window = (Window)Executions.createComponents(
                    "/info_popup.zul", null, args);
            window.setPosition("center");
            window.setClosable(true);
            window.doModal();
        }
        else shouldNextChangeResetOtherFilterValues = false;
    }

    private String checkIfCommaNeeded(StringBuilder sb, String string) {
        // TODO Auto-generated method stub
        String returnVal = string;
        if(!sb.toString().isEmpty()) {
            returnVal = ", " + string;
        }
        return returnVal;
    }


    /**************************************************************** Filter Search *************************************************************/


    @NotifyChange("platformList")
    @Command
    public void doSearchPlatform() {
        List<PlatformRecord> allItems = viewModelService.getAllPlatforms();

        Utils.filterItems(platformList, allItems, filterPlatform);
    }

    @NotifyChange("vendorList")
    @Command
    public void doSearchVendor() {
        List<OrganizationRecord> allItems = new ArrayList<OrganizationRecord>();
        allItems.addAll(vendorList);
        Utils.filterItems(vendorList, allItems, filterVendor);
    }

    @NotifyChange("vendorProtocolList")
    @Command
    public void doSearchVendorProtocol() {

        Utils.filterItems(vendorProtocolList, backupVendorProtocolList, filterVendorProtocol);
    }

    @NotifyChange("callingAnalysisList")
    @Command
    public void doSearchCallingAnalysis() {
        List<AnalysisRecord> allItems = viewModelService.getAllCallingAnalysis();

        Utils.filterItems(callingAnalysisList, allItems, filterCallingAnalysis);
    }

    @NotifyChange("analysesList")
    @Command
    public void doSearchAnalyses() {
        List<AnalysisRecord> allItems = viewModelService.getAllAnalyses();

        Utils.filterItems(analysesList, allItems, filterAnalyses);
    }

    @NotifyChange("projectList")
    @Command
    public void doSearchProject() {

        Utils.filterItems(projectList, backupProjectList, filterProject);
    }

    @NotifyChange("experimentList")
    @Command
    public void doSearchExperiment() {

        Utils.filterItems(experimentList, backupExperimentList, filterExperiment);
    }		

    @NotifyChange("datasetList")
    @Command
    public void doSearchDataset() {

        Utils.filterItems(datasetList, backupDatasetList, filterDataset,15);
    }		

    @NotifyChange("mapasetList")
    @Command
    public void doSearchMapset() {
        List<MapsetRecord> allItems = viewModelService.getAllMapsets();

        Utils.filterItems(mapsetList, allItems, filterMapset);
    }

    @NotifyChange("linkageGroupList")
    @Command
    public void doSearchLinkageGroup() {

        Utils.filterItems(linkageGroupList, backupLinkageGroupList, filterLinkageGroup);
    }

    /**************************************************************** Getters and Setters *************************************************************/ 
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

        setDbPlatforms(Utils.isListNotNullOrEmpty(platformList));
        setPlatformTabLabel(platformList.size());
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

        backupVendorProtocolList.clear();
        backupVendorProtocolList.addAll(vendorProtocolList);

        setVendorProtocolTabLabel(vendorProtocolList.size());
        setDbVendorProtocols(Utils.isListNotNullOrEmpty(vendorProtocolList));
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
        setAnalysisTabLabel(analysesList.size());

        this.analysesList = analysesList;
    }

    public List<ProjectRecord> getProjectList() {
        return projectList;
    }


    public void setProjectList(List<ProjectRecord> projectList) {

        backupProjectList.clear();
        backupProjectList.addAll(projectList);
        setProjectsTabLabel(projectList.size());
        setDbProjects(Utils.isListNotNullOrEmpty(projectList));
        this.projectList = projectList;
    }


    public List<ExperimentRecord> getExperimentList() {
        return experimentList;
    }


    public void setExperimentList(List<ExperimentRecord> experimentList) {

        backupExperimentList.clear();
        backupExperimentList.addAll(experimentList);
        setExperimentTabLabel(experimentList.size());
        setDbExperiment(Utils.isListNotNullOrEmpty(experimentList));
        this.experimentList = experimentList;
    }


    public List<MapsetRecord> getMapsetList() {
        return mapsetList;
    }


    public void setMapsetList(List<MapsetRecord> mapsetList) {
        setMapsetTabLabel(mapsetList.size());
        this.mapsetList = mapsetList;
    }


    public List<LinkageGroupRecord> getLinkageGroupList() {
        return linkageGroupList;
    }


    public void setLinkageGroupList(List<LinkageGroupRecord> linkageGroupList) {

        backupLinkageGroupList.clear();
        backupLinkageGroupList.addAll(linkageGroupList);
        setDbLinkageGroup(Utils.isListNotNullOrEmpty(linkageGroupList));
        setLinkageGroupTabLabel(linkageGroupList.size());
        this.linkageGroupList = linkageGroupList;
    }


    public List<DatasetRecord> getDatasetList() {
        return datasetList;
    }


    public void setDatasetList(List<DatasetRecord> datasetList) {

        backupDatasetList.clear();
        backupDatasetList.addAll(datasetList);
        setDatasetTabLabel(datasetList.size());
        setDbDataset(Utils.isListNotNullOrEmpty(datasetList));
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


    public List<LinkageGroupRecord> getMarkerDetailLinkageGroupList() {
        return markerDetailLinkageGroupList;
    }


    public void setMarkerDetailLinkageGroupList(List<LinkageGroupRecord> markerDetailLinkageGroupList) {
        this.markerDetailLinkageGroupList = markerDetailLinkageGroupList;
    }


    public boolean isMarkerAssociated() {
        return markerAssociated;
    }


    public void setMarkerAssociated(boolean markerAssociated) {
        this.markerAssociated = markerAssociated;
    }


    public List<DatasetRecord> getMarkerDetailDatasetList() {
        return markerDetailDatasetList;
    }


    public void setMarkerDetailDatasetList(List<DatasetRecord> markerDetailDatasetList) {
        this.markerDetailDatasetList = markerDetailDatasetList;
    }


    public List<MarkerGroupRecord> getMarkerDetailsMarkerGroupList() {
        return markerDetailsMarkerGroupList;
    }


    public void setMarkerDetailsMarkerGroupList(List<MarkerGroupRecord> markerDetailsMarkerGroupList) {
        this.markerDetailsMarkerGroupList = markerDetailsMarkerGroupList;
    }


    public Integer getSizeMarkerList() {

        sizeMarkerList = markerList.size();

        if(sizeMarkerList<1) sizeMarkerList = 0;

        return sizeMarkerList;
    }


    public void setSizeMarkerList(Integer sizeMarkerList) {
        this.sizeMarkerList = sizeMarkerList;
    }

    public String getCurrentFiltersAsText() {
        return currentFiltersAsText;
    }


    public void setCurrentFiltersAsText(String currentFiltersAsText) {
        this.currentFiltersAsText = currentFiltersAsText;
    }


    public String getPlatformTabLabel() {
        return platformTabLabel;
    }


    public void setPlatformTabLabel(Integer i) {
        this.platformTabLabel =  Utils.combineLabelWithNum("Platforms", i );
    }


    public String getVendorProtocolTabLabel() {
        return vendorProtocolTabLabel;
    }


    public void setVendorProtocolTabLabel(Integer i) {

        this.vendorProtocolTabLabel = Utils.combineLabelWithNum("Vendor-Protocols", i );
    }


    public String getAnalysisTabLabel() {
        return analysisTabLabel;
    }


    public void setAnalysisTabLabel(Integer i) {
        this.analysisTabLabel = Utils.combineLabelWithNum("Analyses", i );
    }


    public String getProjectsTabLabel() {
        return projectsTabLabel;
    }


    public void setProjectsTabLabel(Integer i) {
        this.projectsTabLabel = Utils.combineLabelWithNum("Projects", i );
    }


    public String getExperimentTabLabel() {
        return experimentTabLabel;
    }


    public void setExperimentTabLabel(Integer i) {
        this.experimentTabLabel = Utils.combineLabelWithNum("Experiments", i );
    }


    public String getDatasetTabLabel() {
        return datasetTabLabel;
    }


    public void setDatasetTabLabel(Integer i) {
        this.datasetTabLabel = Utils.combineLabelWithNum("Datasets", i );
    }


    public String getMapsetTabLabel() {
        return mapsetTabLabel;
    }


    public void setMapsetTabLabel(Integer i) {
        this.mapsetTabLabel =  Utils.combineLabelWithNum("Mapsets", i );
    }


    public String getLinkageGroupTabLabel() {
        return linkageGroupTabLabel;
    }


    public void setLinkageGroupTabLabel(Integer i) {
        this.linkageGroupTabLabel =  Utils.combineLabelWithNum("Linkage Groups", i );
    }

}
