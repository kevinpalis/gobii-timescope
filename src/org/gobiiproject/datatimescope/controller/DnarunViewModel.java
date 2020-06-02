package org.gobiiproject.datatimescope.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.gobiiproject.datatimescope.db.generated.tables.Dnarun;
import org.gobiiproject.datatimescope.db.generated.tables.records.AnalysisRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ExperimentRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.entity.DnarunEntity;
import org.gobiiproject.datatimescope.entity.DnarunViewEntity;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.Utils;
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
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

public class DnarunViewModel {
	//UI component


    ViewModelService viewModelService;
    private boolean cbAllUsers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, isDsIDBoxDisabled=false, isDsNameListDisabled=false, isGermplasmIDBoxDisabled=false, isGermplasmNameListDisabled=false, isIsRoleUser=false, performedDeleteSuccesfully=false, paged=false;

    @Wire("#dnarunGrid")
    Grid dnarunGrid;
    
    private Integer sizeDnarunList=0;
    private String filterProject, projectsTabLabel;

    private String filterExperiment, experimentTabLabel;
    private String filterDataset, datasetTabLabel;
    private String currentFiltersAsText;
    
    private boolean dbProjects=true, dbExperiment=true, dbDataset=true,  shouldNextChangeResetOtherFilterValues=false;
    private List<ContactRecord> contactsList, piList;
    private List<DnarunViewEntity> dnarunList;
    private List<DnarunViewEntity> selectedList;
    private List<DatasetRecord> datasetList, backupDatasetList;
    private List<ProjectRecord> projectList, backupProjectList;
    private List<ExperimentRecord> experimentList, backupExperimentList;
    private List<AnalysisRecord> analysisList;
    private DnarunEntity dnarunEntity;

    @Init
    public void init() {
        selectedList = new ArrayList<DnarunViewEntity>();
        backupProjectList =  new ArrayList<ProjectRecord>();
        backupExperimentList =  new ArrayList<ExperimentRecord>();
        backupDatasetList  =  new ArrayList<DatasetRecord>();
        
        viewModelService = new ViewModelServiceImpl();
        
        setDnarunEntity(new DnarunEntity());
        setDatasetList(viewModelService.getAllDatasets());
        setExperimentList(viewModelService.getAllExperiments());
        setProjectList(viewModelService.getAllProjects());
        
    }


    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }


    @Command("submitQuery")
    @NotifyChange({"dnarunList","selectedList", "allCbSelected", "cbAllUsers","paged", "sizeDnarunList"})
    public void submitQuery(){
//        viewModelService.getAllDnarunsBasedOnQuery(dnarunEntity);
//        viewModelService.getDnarunidsbyproject(6);
        try{
            dnarunList.clear(); //clear the list first and then just add if there are any selected

            selectedList.clear();

        }catch(NullPointerException e){

        }
        
        dnarunGrid.setEmptyMessage("There are no dnaruns that match your search.");
        setDnarunList(viewModelService.getAllDnarunsBasedOnQuery(dnarunEntity));

        setiDBoxDisabled(false);
        setnameListDisabled(false);
        setAllCbSelected(false);
        setCbAllUsers(false);
        setDsIDBoxDisabled(false);
        setDsNameListDisabled(false);
        setGermplasmIDBoxDisabled(false);
        setGermplasmNameListDisabled(false);

    }

    @Command("resetDnarunTab")
    @NotifyChange({"dnarunList", "sizeDnarunList", "selectedList", "allCbSelected", "cbAllUsers", "dnarunEntity","iDBoxDisabled","nameListDisabled", "paged"})
    public void resetDnarunTab(){
        try{
            dnarunList.clear(); //clear the list first and then just add if there are any selected
            selectedList.clear(); 

        }catch(NullPointerException e){

        }
        dnarunEntity = new DnarunEntity();
        currentFiltersAsText = "";


        dnarunGrid.setEmptyMessage("There's nothing to see here. Submit a query to search for DNAruns");
        setiDBoxDisabled(false);
        setnameListDisabled(false);
        setAllCbSelected(false);
        setCbAllUsers(false);
        setDsIDBoxDisabled(false);
        setDsNameListDisabled(false);
        setGermplasmIDBoxDisabled(false);
        setGermplasmNameListDisabled(false);
    }

    @Command("doSelectAll")
    @NotifyChange("allCbSelected")
    public void doSelectAll(){
        List<DnarunViewEntity> dnarunListOnDisplay = getDnarunList();

        selectedList.clear(); //clear the list first and then just add if there are any selected

        setAllCbSelected(isCbAllUsers());

        if (isCbAllUsers()) {
            try{
                for(DnarunViewEntity u: dnarunListOnDisplay){
                    selectedList.add(u);
                }
            }catch(NullPointerException npe){
                Messagebox.show("Click submit query to display all dnaruns", "There is nothing to select", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }
    }

    @Command("changeEnabled")
    @NotifyChange({"iDBoxDisabled","nameListDisabled", "dsIDBoxDisabled","dsNameListDisabled", "germplasmIDBoxDisabled","germplasmNameListDisabled"})
    public void changeEnabled(){
        isIDBoxDisabled = false; // reset
        isNameListDisabled= false; 
        isDsIDBoxDisabled=false;
        isDsNameListDisabled=false;
        isGermplasmIDBoxDisabled=false;
        isGermplasmNameListDisabled=false;
        
        //dnarun id range and names
        if(dnarunEntity.getDnarunNamesAsEnterSeparatedString()!=null && !dnarunEntity.getDnarunNamesAsEnterSeparatedString().isEmpty()){
            isIDBoxDisabled = true;
        }else if(dnarunEntity.getDnarunIDStartRange() != null ){
            if(dnarunEntity.getDnarunIDStartRange() >0 ){
                isNameListDisabled=true;
            }
        }else if(dnarunEntity.getDnarunIDEndRange() !=null){
            if(dnarunEntity.getDnarunIDEndRange()>0){
                isNameListDisabled=true;
            }
        }
        
        //dnasample id range and names
        if(dnarunEntity.getDnasampleNamesAsEnterSeparatedString()!=null && !dnarunEntity.getDnasampleNamesAsEnterSeparatedString().isEmpty()){
            isDsIDBoxDisabled = true;
        }else if(dnarunEntity.getDnasampleIDStartRange() != null ){
            if(dnarunEntity.getDnasampleIDStartRange() >0 ){
                isDsNameListDisabled=true;
            }
        }else if(dnarunEntity.getDnasampleIDEndRange() !=null){
            if(dnarunEntity.getDnasampleIDEndRange()>0){
                isDsNameListDisabled=true;
            }
        }
        
        //germplasm id range and names
        if(dnarunEntity.getGermplasmNamesAsEnterSeparatedString()!=null && !dnarunEntity.getGermplasmNamesAsEnterSeparatedString().isEmpty()){
            isGermplasmIDBoxDisabled = true;
        }else if(dnarunEntity.getGermplasmIDStartRange() != null ){
            if(dnarunEntity.getGermplasmIDStartRange() >0 ){
                isGermplasmNameListDisabled=true;
            }
        }else if(dnarunEntity.getGermplasmIDStartRange() !=null){
            if(dnarunEntity.getGermplasmIDStartRange()>0){
                isGermplasmNameListDisabled=true;
            }
        }
    }


    @SuppressWarnings("unchecked")
    @Command("resetDSSummary")
    @NotifyChange({"dnarunSummary","performedDeleteSuccesfully"})
    public void resetDSSummary(){


//        if(dnarunSummary.size()>0){
//            performedDeleteSuccesfully=true;
//        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Command("deleteSelectedDnaruns")
    @NotifyChange({"dnarunSummary","performedDeleteSuccesfully"})
    public void deleteSelectedDnaruns(){

        if(selectedList.isEmpty()){ //Nothing is selected
            Messagebox.show("There are no dnaruns selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("the following dnaruns?");
            for(DnarunViewEntity u: selectedList){
                sb.append("\n"+u.getDnarunName());
            }
           
            if (selectedList.size()>10){

                sb =  new StringBuilder();
                sb.append(Integer.toString(selectedList.size())+" dnaruns?");

            }
            Messagebox.show("Are you sure you want to delete "+sb.toString(), 
                    "Confirm Delete", Messagebox.YES | Messagebox.NO,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener(){
                
                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        //YES is clicked

                        Messagebox.show("Please have at least one backup of your data before proceeding to allow recoverability in case of user mistakes.", 
                                "WARNING", Messagebox.OK | Messagebox.CANCEL,
                                Messagebox.EXCLAMATION,
                                new org.zkoss.zk.ui.event.EventListener(){
                            @Override
                            public void onEvent(Event event) throws Exception {
                                
                                if(Messagebox.ON_OK.equals(event.getName())){
                                    //YES is clicked
                                    if(selectedList.size() == 1){  // 
                                       viewModelService.deleteDnarun(selectedList.get(0));

                                    }else{
                                        //bulk delete
                                         viewModelService.deleteDnaruns(selectedList);
                                    }


                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @GlobalCommand("retrieveDNArunList")
    @NotifyChange({"dnarunList", "sizeDnarunList", "selectedList", "allCbSelected", "cbAllUsers","paged"})
    public void retrieveDNArunList(){
        //...

        setDnarunList(viewModelService.getAllDnarunsBasedOnQuery(dnarunEntity));

        selectedList.clear();

        setAllCbSelected(false);
        setCbAllUsers(false);
    }

    @Command("updateSelectDs")
    @NotifyChange({"cbAllUsers", "selectedList"})
    public void updateSelectDs(@BindingParam("dsChecked") DnarunViewEntity dsList, @BindingParam("isChecked") Boolean isChecked){
        if(isChecked){
            selectedList.add(dsList);
        }else{
            setCbAllUsers(false);

            ListIterator<DnarunViewEntity> it = selectedList.listIterator();
            while (it.hasNext()) {
                if (it.next().getDnarunId().equals(dsList.getDnarunId())) {
                    it.remove();
                    break;
                }
            }
        }
    }

    @Command("showDnarunDetail")
    @NotifyChange({"dnarunDetailDatasetList"})
    public void showDnarunDetail(@BindingParam("dnarunId") Integer dnarunId, @BindingParam("dnarunName") String dnarunName){
        Boolean associated = false;
        List<DatasetRecord> dnarunDetailDatasetList = new ArrayList<DatasetRecord>();
        dnarunDetailDatasetList = viewModelService.getDatasetAssociatedToDnarunId(dnarunId);
        
        if(Utils.isListNotNullOrEmpty(dnarunDetailDatasetList)) {
            associated = true;
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("dnarunDetailDatasetList", dnarunDetailDatasetList);
            args.put("associated", associated); 

            Window window = (Window)Executions.createComponents(
                    "/dnarun_detail.zul", null, args);
            window.setPosition("center");
            window.setClosable(true);
            window.doModal();
        }
        else {
            Messagebox.show("The DNArun '" + dnarunName + "' is not associated to any dataset", "There's nothing to display", Messagebox.OK, null);
        }
    }

    @Command("validateForReset")
    @NotifyChange({"dnarunEntity", "currentFiltersAsText"})
    public void validateForReset(@BindingParam("category") String category){

        //if reset required then: if(shouldNextChangeResetOtherFilterValues)  

        //whether reset or not, update no. of lists being displayed beside the label by calling the postGlobalCommand
        switch(category){

     
        case "project":
            if(Utils.isListNotNullOrEmpty(dnarunEntity.getExperimentList())) {
                if(shouldNextChangeResetOtherFilterValues) dnarunEntity.getExperimentList().clear();
            }

            BindUtils.postGlobalCommand(null, null, "updateDnaRunsExperimentsTab", null);
        case "experiment":
            if(Utils.isListNotNullOrEmpty(dnarunEntity.getDatasetList())) {
                if(shouldNextChangeResetOtherFilterValues) dnarunEntity.getDatasetList().clear();
            }

            BindUtils.postGlobalCommand(null, null, "updateDnaRunsDatasetsTab", null);
            break;
        default: 
            break; 
        }
        //once resets are done, update boolean
        shouldNextChangeResetOtherFilterValues=false;
        setCurrentFiltersAsText(dnarunEntity.getFiltersAsTextWithDelimiter(System.getProperty("line.separator")));
    }
    
    @NotifyChange({"experimentList","experimentTabLabel", "dbExperiment"})
    @GlobalCommand
    public void updateDnaRunsExperimentsTab() { // Experiments are connected to a specific project then a vendor-protocol and a platform

        if(Utils.isListNotNullOrEmpty(dnarunEntity.getProjectList())) { // if not empty, use project as filter

            setExperimentList(viewModelService.getExperimentsByProjectID(dnarunEntity.getProjectList()));

        }else{

            setExperimentList(viewModelService.getAllExperiments());
        }
    }
    
    @NotifyChange({"datasetList","datasetTabLabel", "dbDataset"}) 
    @GlobalCommand
    public void updateDnaRunsDatasetsTab() {

        if(Utils.isListNotNullOrEmpty(dnarunEntity.getExperimentList())) { // if not empty, use experiment as filter
            setDatasetList(viewModelService.getDatasetsByExperimentID(dnarunEntity.getExperimentList()));

        }
        else if(Utils.isListNotNullOrEmpty(dnarunEntity.getProjectList())) { // if not empty, use project as filter
            setDatasetList(viewModelService.getDatasetsByProjectID(dnarunEntity.getProjectList()));
        }
        else{
            setDatasetList(viewModelService.getAllDatasets());
        }

    }
    
    @Command
    public void selectProjectsTab() { // Projects are connected to a platform and a vendor-protocol (via experiment)

        showInfoPopUp("project");
    }

    @Command
    public void selectExperimentsTab() { // Experiments are connected to a specific project then a vendor-protocol and a platform

        showInfoPopUp("experiment");
    }
    public void showInfoPopUp(String category) {
        StringBuilder sb = new StringBuilder();

        switch(category){

        case "project":
            if(Utils.isListNotNullOrEmpty(dnarunEntity.getExperimentList())) {
                sb.append(Utils.checkIfCommaNeeded(sb,"\n Experiments"));
            }
        case "experiment":
            if(Utils.isListNotNullOrEmpty(dnarunEntity.getDatasetList())) {
                sb.append(Utils.checkIfCommaNeeded(sb, "\n Datasets"));
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

    public List<DnarunViewEntity> getDnarunList() {
        return dnarunList;
    }

    public void setDnarunList(List<DnarunViewEntity> dnarunList) {
        if(Utils.isListNotNullOrEmpty(dnarunList)) {
            if(dnarunList.size() > 25) setPaged(true);
            else setPaged(false);
            
            this.dnarunList = dnarunList;
        }
    }

    public List<ContactRecord> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<ContactRecord> contactsList) {
        this.contactsList = contactsList;
    }

    public List<ContactRecord> getPiList() {
        return piList;
    }

    public void setPiList(List<ContactRecord> piList) {
        this.piList = piList;
    }

    public DnarunEntity getDnarunEntity() {
        return dnarunEntity;
    }

    public void setDnarunEntity(DnarunEntity dnarunEntity) {
        this.dnarunEntity = dnarunEntity;
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

    public boolean isIsRoleUser() {
        return isIsRoleUser;
    }

    public void setIsRoleUser(boolean isIsRoleUser) {
        this.isIsRoleUser = isIsRoleUser;
    }

    public boolean isPerformedDeleteSuccesfully() {
        return performedDeleteSuccesfully;
    }

    public void setPerformedDeleteSuccesfully(boolean performedDeleteSuccesfully) {
        this.performedDeleteSuccesfully = performedDeleteSuccesfully;
    }


    public boolean isPaged() {
        return paged;
    }


    public void setPaged(boolean paged) {
        this.paged = paged;
    }


    public Integer getSizeDnarunList() {
        if(Utils.isListNotNullOrEmpty(dnarunList)) {
            sizeDnarunList = dnarunList.size();
        }else sizeDnarunList = 0;
        return sizeDnarunList;
    }


    public void setSizeDnarunList(Integer sizeDnarunList) {
        this.sizeDnarunList = sizeDnarunList;
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


    public List<AnalysisRecord> getAnalysisList() {
        return analysisList;
    }


    public void setAnalysisList(List<AnalysisRecord> analysisList) {
        this.analysisList = analysisList;
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


    public boolean isDsIDBoxDisabled() {
        return isDsIDBoxDisabled;
    }


    public void setDsIDBoxDisabled(boolean isDsIDBoxDisabled) {
        this.isDsIDBoxDisabled = isDsIDBoxDisabled;
    }


    public boolean isDsNameListDisabled() {
        return isDsNameListDisabled;
    }


    public void setDsNameListDisabled(boolean isDsNameListDisabled) {
        this.isDsNameListDisabled = isDsNameListDisabled;
    }


    public boolean isGermplasmIDBoxDisabled() {
        return isGermplasmIDBoxDisabled;
    }


    public void setGermplasmIDBoxDisabled(boolean isGermplasmIDBoxDisabled) {
        this.isGermplasmIDBoxDisabled = isGermplasmIDBoxDisabled;
    }


    public boolean isGermplasmNameListDisabled() {
        return isGermplasmNameListDisabled;
    }


    public void setGermplasmNameListDisabled(boolean isGermplasmNameListDisabled) {
        this.isGermplasmNameListDisabled = isGermplasmNameListDisabled;
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


    public String getCurrentFiltersAsText() {
        return currentFiltersAsText;
    }


    public void setCurrentFiltersAsText(String currentFiltersAsText) {
        this.currentFiltersAsText = currentFiltersAsText;
    }
    public String getFilterProject() {
        return filterProject;
    }


    public void setFilterProject(String filterProject) {
        this.filterProject = filterProject;
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


    public void setProjectsTabLabel(String projectsTabLabel) {
        this.projectsTabLabel = projectsTabLabel;
    }


    public void setExperimentTabLabel(String experimentTabLabel) {
        this.experimentTabLabel = experimentTabLabel;
    }


    public void setDatasetTabLabel(String datasetTabLabel) {
        this.datasetTabLabel = datasetTabLabel;
    }

}
