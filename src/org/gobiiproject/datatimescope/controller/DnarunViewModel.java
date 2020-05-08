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
import org.gobiiproject.datatimescope.db.generated.tables.records.DnarunRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ExperimentRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.entity.DnarunEntity;
import org.gobiiproject.datatimescope.entity.DnarunViewEntity;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
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
    private boolean cbAllUsers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, isIsRoleUser=false, performedDeleteSuccesfully=false, paged=false;

    @Wire("#dnarunGrid")
    Grid dnarunGrid;
    
    private Integer sizeDnarunList=0;
    
    private List<ContactRecord> contactsList, piList;
    private List<DnarunViewEntity> dnarunList;
    private List<DnarunRecord> selectedDsList;
    private List<DatasetRecord> datasetList;
    private List<ProjectRecord> projectList;
    private List<ExperimentRecord> experimentList;
    private List<AnalysisRecord> analysisList;
    private DnarunEntity dnarunEntity;

    @SuppressWarnings("unchecked")
    @Init
    public void init() {
        selectedDsList = new ArrayList<DnarunRecord>();
        viewModelService = new ViewModelServiceImpl();
        
        setDnarunEntity(new DnarunEntity());
        setDnarunList(viewModelService.getAllDnaruns());
        setDatasetList(viewModelService.getAllDatasets());
        
        contactsList = viewModelService.getAllContacts();
        Integer [] roles = {1}; // PI only
        piList = viewModelService.getContactsByRoles(roles);
        projectList = viewModelService.getAllProjects();
        experimentList = viewModelService.getAllExperiments();
        analysisList = viewModelService.getAllAnalyses();
        
        ProjectRecord selectAllProject = new ProjectRecord(0, "SELECT ALL PROJECTS", "", "", 0, 0, null, 0, null, 0, null);
        projectList.add(0,selectAllProject);
           
        ExperimentRecord selectAllExp = new ExperimentRecord(0, "SELECT ALL EXPERIMENT", "", 0, 0, "", 0, null, 0, null, 0, 0);
        experimentList.add(0,selectAllExp);
        
    }


    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }


    @Command("submitQuery")
    @NotifyChange({"dnarunList","selectedDsList", "allCbSelected", "cbAllUsers","paged", "sizeDnarunList"})
    public void submitQuery(){
//        viewModelService.getAllDnarunsBasedOnQuery(dnarunEntity);
//        viewModelService.getDnarunidsbyproject(6);
        try{
            dnarunList.clear(); //clear the list first and then just add if there are any selected

            selectedDsList.clear();

        }catch(NullPointerException e){

        }
        
        dnarunGrid.setEmptyMessage("There are no dnaruns that match your search.");
        setDnarunList(viewModelService.getAllDnarunsBasedOnQuery(dnarunEntity));

        setiDBoxDisabled(false);
        setnameListDisabled(false);
        setAllCbSelected(false);
        setCbAllUsers(false);

    }

    @Command("resetDnarunTab")
    @NotifyChange({"dnarunList", "sizeDnarunList", "selectedDsList", "allCbSelected", "cbAllUsers", "dnarunEntity","iDBoxDisabled","nameListDisabled", "paged"})
    public void resetDnarunTab(){
        try{
            dnarunList.clear(); //clear the list first and then just add if there are any selected
            selectedDsList.clear(); 

        }catch(NullPointerException e){

        }
        dnarunEntity = new DnarunEntity();

        
        dnarunGrid.setEmptyMessage("Looks like there are no dnaruns in the database yet.");
        setDnarunList(viewModelService.getAllDnaruns());
        setiDBoxDisabled(false);
        setnameListDisabled(false);
        setAllCbSelected(false);
        setCbAllUsers(false);
    }

    @Command("doSelectAll")
    @NotifyChange("allCbSelected")
    public void doSelectAll(){
//        List<DnarunViewEntity> dnarunListOnDisplay = getDnarunList();
//
//        selectedDsList.clear(); //clear the list first and then just add if there are any selected
//
//        setAllCbSelected(isCbAllUsers());
//
//        if (isCbAllUsers()) {
//            try{
//                for(DnarunRecord u: dnarunListOnDisplay){
//                    selectedDsList.add(u);
//                }
//            }catch(NullPointerException npe){
//                Messagebox.show("Submit an empty query to display all dnaruns", "There is nothing to select", Messagebox.OK, Messagebox.EXCLAMATION);
//            }
//        }
    }

    @Command("changeEnabled")
    @NotifyChange({"iDBoxDisabled","nameListDisabled"})
    public void changeEnabled(){
        isIDBoxDisabled = false; // reset
        isNameListDisabled= false; 

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

        if(selectedDsList.isEmpty()){ //Nothing is selected
            Messagebox.show("There are no dnaruns selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        else{
            StringBuilder sb = new StringBuilder();

            for(DnarunRecord u: selectedDsList){
                sb.append("\n"+u.getName());
            }
           
            Messagebox.show("Are you sure you want to delete the following dnaruns?\n"+sb.toString(), 
                    "Confirm Delete", Messagebox.YES | Messagebox.NO,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener(){
                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        //YES is clicked

                        Messagebox.show("THIS ACTION IS NOT REVERSIBLE.\n\nDo you want to continue?\n", 
                                "WARNING", Messagebox.YES | Messagebox.CANCEL,
                                Messagebox.EXCLAMATION,
                                new org.zkoss.zk.ui.event.EventListener(){
                            @Override
                            public void onEvent(Event event) throws Exception {
                                // TODO Auto-generated method stub
                                if(Messagebox.ON_YES.equals(event.getName())){
                                    //YES is clicked
                                    boolean successful;

                                    if(selectedDsList.size() == 1){  // just one user is selected
                                        successful = viewModelService.deleteDnarun(selectedDsList.get(0));

                                    }else{
                                        //bulk delete
                                        successful = viewModelService.deleteDnaruns(selectedDsList);
                                    }

                                    if(successful){
                                        BindUtils.postGlobalCommand(null, null, "retrieveDnarunList", null);

//                                        if(dnarunSummary.size()>0){
//                                            performedDeleteSuccesfully=true;
//                                        }
                                    }

                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @GlobalCommand("retrieveDnarunList")
    @NotifyChange({"dnarunList", "sizeDnarunList", "selectedDsList", "allCbSelected", "cbAllUsers","paged"})
    public void retrieveUserList(){
        //...

        setDnarunList(viewModelService.getAllDnarunsBasedOnQuery(dnarunEntity));

        selectedDsList.clear();

        setAllCbSelected(false);
        setCbAllUsers(false);
    }

    @Command("updateSelectDs")
    @NotifyChange({"cbAllUsers", "selectedDsList"})
    public void updateSelectDs(@BindingParam("dsChecked") DnarunRecord dsList, @BindingParam("isChecked") Boolean isChecked){
        if(isChecked){
            selectedDsList.add(dsList);
        }else{
            setCbAllUsers(false);

            ListIterator<DnarunRecord> it = selectedDsList.listIterator();
            while (it.hasNext()) {
                if (it.next().getDnarunId().equals(dsList.getDnarunId())) {
                    it.remove();
                    break;
                }
            }
        }
    }

    @Command("exportDnarunTable")
    public void exportDnarunTable() {

//        ListIterator<DnarunRecord> it = dnarunList.listIterator();
//        StringBuffer buffMap = new StringBuffer();
//
//        while (it.hasNext()) {
//
//            DnarunRecord next = it.next();
//
//            if(it.nextIndex()==1){
//                buffMap.append(next.getHeaderDelimitedBy(","));
//            }
//            buffMap.append(next.getAllDelimitedBy(","));
//
//        }
//
//        FileWriter fw;
//        try {
//            File file = new File("timescope_dnarun.csv");
//            fw = new FileWriter(file);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(buffMap.toString());
//            bw.flush();
//            bw.close();
//
//            InputStream is = new FileInputStream(file);
//            Filedownload.save(is, "text/csv", file.getName());
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    @Command("exportCurrentDnarunTablePage")
    public void exportCurrentDnarunTablePage() {
//
//
//        int ActivePage = dnarunGrid.getActivePage();
//        int initial, last;
//        if(ActivePage==0){
//            initial=1;
//        }else{
//            initial=(ActivePage*dnarunGrid.getPageSize())+1;
//        }
//
//        StringBuffer buffMap = new StringBuffer();
//
//
//        List<Integer> indices = new ArrayList<Integer>();
//
//        last = initial+dnarunGrid.getPageSize();
//        //get Indices
//        for( int i = initial; i<last; i++){
//
//            indices.add(i);
//
//        }
//
//        ListIterator<DnarunRecord> it = dnarunList.listIterator();
//
//        while (it.hasNext()) {
//
//            DnarunRecord next = it.next();
//            int nextIndex = it.nextIndex();
//
//            if(nextIndex==1){
//                buffMap.append(next.getHeaderDelimitedBy(","));
//            }
//
//            if(indices.contains(nextIndex)){
//                buffMap.append(next.getAllDelimitedBy(","));
//            }
//
//        }
//
//        FileWriter fw;
//        try {
//            File file = new File("timescope_dnarun_currentpage.csv");
//            fw = new FileWriter(file);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(buffMap.toString());
//            bw.flush();
//            bw.close();
//
//            InputStream is = new FileInputStream(file);
//            Filedownload.save(is, "text/csv", file.getName());
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
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
        
//        if(dnarunList.size() > 25) setPaged(true);
//        else setPaged(false);
        
        this.dnarunList = dnarunList;
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

//        sizeDnarunList = dnarunList.size();
//        
//        if(dnarunList.size()<1) sizeDnarunList = 0;
        
        return sizeDnarunList;
    }


    public void setSizeDnarunList(Integer sizeDnarunList) {
        this.sizeDnarunList = sizeDnarunList;
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
        this.datasetList = datasetList;
    }

}
