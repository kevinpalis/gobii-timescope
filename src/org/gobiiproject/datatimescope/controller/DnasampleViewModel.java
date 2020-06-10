package org.gobiiproject.datatimescope.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.entity.DnasampleEntity;
import org.gobiiproject.datatimescope.entity.DnasampleViewEntity;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.Utils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;

public class DnasampleViewModel {
	//UI component


    ViewModelService viewModelService;
    private boolean cbAllUsers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, isDsIDBoxDisabled=false, isDsNameListDisabled=false, isGermplasmIDBoxDisabled=false, isGermplasmNameListDisabled=false, isIsRoleUser=false, performedDeleteSuccesfully=false, paged=false;

    @Wire("#dnasampleGrid")
    Grid dnasampleGrid;
    
    private Integer sizeDnasampleList=0;
    
    private List<DnasampleViewEntity> dnasampleList;
    private List<DnasampleViewEntity> selectedList;
    private List<ProjectRecord> projectList;
    private DnasampleEntity dnasampleEntity;

    @SuppressWarnings("unchecked")
    @Init
    public void init() {
        selectedList = new ArrayList<DnasampleViewEntity>();
        viewModelService = new ViewModelServiceImpl();
        
        setDnasampleEntity(new DnasampleEntity());
//        setDnasampleList(viewModelService.getAllDnasamples());
        
        projectList = viewModelService.getAllProjects();
        
        ProjectRecord selectAllProject = new ProjectRecord(0, "SELECT ALL PROJECTS", "", "", 0, 0, null, 0, null, 0, null);
        projectList.add(0,selectAllProject);
           
    }


    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }


    @Command("submitQuery")
    @NotifyChange({"dnasampleList","selectedList", "allCbSelected", "cbAllUsers","paged", "sizeDnasampleList","iDBoxDisabled","nameListDisabled","dsIDBoxDisabled","dsNameListDisabled","germplasmIDBoxDisabled","germplasmNameListDisabled"})
    public void submitQuery(){
//        viewModelService.getAllDnasamplesBasedOnQuery(dnasampleEntity);
//        viewModelService.getDnasampleidsbyproject(6);
        try{
            dnasampleList.clear(); //clear the list first and then just add if there are any selected

            selectedList.clear();

        }catch(NullPointerException e){

        }
        
        dnasampleGrid.setEmptyMessage("There are no dnasamples that match your search.");
        setDnasampleList(viewModelService.getAllDnasamplesBasedOnQuery(dnasampleEntity));

        setAllCbSelected(false);
        setCbAllUsers(false);

    }

    @Command("resetDnasampleTab")
    @NotifyChange({"dnasampleList", "sizeDnasampleList", "selectedList", "allCbSelected", "cbAllUsers", "dnasampleEntity","iDBoxDisabled","nameListDisabled","dsIDBoxDisabled","dsNameListDisabled","germplasmIDBoxDisabled","germplasmNameListDisabled", "paged"})
    public void resetDnasampleTab(){
        try{
            dnasampleList.clear(); //clear the list first and then just add if there are any selected
            selectedList.clear(); 

        }catch(NullPointerException e){

        }
        dnasampleEntity = new DnasampleEntity();

        
        dnasampleGrid.setEmptyMessage("There's nothing to see here. Submit a query to search for DNAsamples.");
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
        List<DnasampleViewEntity> dnasampleListOnDisplay = getDnasampleList();

        selectedList.clear(); //clear the list first and then just add if there are any selected

        setAllCbSelected(isCbAllUsers());

        if (isCbAllUsers()) {
            try{
                for(DnasampleViewEntity u: dnasampleListOnDisplay){
                    selectedList.add(u);
                }
            }catch(NullPointerException npe){
                Messagebox.show("Click submit query to display all dnasamples", "There is nothing to select", Messagebox.OK, Messagebox.EXCLAMATION);
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
        
        //dnasample id range and names
        if(dnasampleEntity.getDnasampleNamesAsEnterSeparatedString()!=null && !dnasampleEntity.getDnasampleNamesAsEnterSeparatedString().isEmpty()){
            isIDBoxDisabled = true;
        }else if(dnasampleEntity.getDnasampleIDStartRange() != null ){
            if(dnasampleEntity.getDnasampleIDStartRange() >0 ){
                isNameListDisabled=true;
            }
        }else if(dnasampleEntity.getDnasampleIDEndRange() !=null){
            if(dnasampleEntity.getDnasampleIDEndRange()>0){
                isNameListDisabled=true;
            }
        }
        
        //dnasample id range and names
        if(dnasampleEntity.getDnasampleNamesAsEnterSeparatedString()!=null && !dnasampleEntity.getDnasampleNamesAsEnterSeparatedString().isEmpty()){
            isDsIDBoxDisabled = true;
        }else if(dnasampleEntity.getDnasampleIDStartRange() != null ){
            if(dnasampleEntity.getDnasampleIDStartRange() >0 ){
                isDsNameListDisabled=true;
            }
        }else if(dnasampleEntity.getDnasampleIDEndRange() !=null){
            if(dnasampleEntity.getDnasampleIDEndRange()>0){
                isDsNameListDisabled=true;
            }
        }
        
        //germplasm id range and names
        if(dnasampleEntity.getGermplasmNamesAsEnterSeparatedString()!=null && !dnasampleEntity.getGermplasmNamesAsEnterSeparatedString().isEmpty()){
            isGermplasmIDBoxDisabled = true;
        }else if(dnasampleEntity.getGermplasmIDStartRange() != null ){
            if(dnasampleEntity.getGermplasmIDStartRange() >0 ){
                isGermplasmNameListDisabled=true;
            }
        }else if(dnasampleEntity.getGermplasmIDStartRange() !=null){
            if(dnasampleEntity.getGermplasmIDStartRange()>0){
                isGermplasmNameListDisabled=true;
            }
        }
    }


    @SuppressWarnings("unchecked")
    @Command("resetDSSummary")
    @NotifyChange({"dnasampleSummary","performedDeleteSuccesfully"})
    public void resetDSSummary(){


//        if(dnasampleSummary.size()>0){
//            performedDeleteSuccesfully=true;
//        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Command("deleteSelectedDnasamples")
    @NotifyChange({"dnasampleSummary","performedDeleteSuccesfully"})
    public void deleteSelectedDnasamples(){

        if(selectedList.isEmpty()){ //Nothing is selected
            Messagebox.show("There are no dnasamples selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("the following dnasamples?");
            for(DnasampleViewEntity u: selectedList){
                sb.append("\n"+u.getDnasampleName());
            }
           
            if (selectedList.size()>10){

                sb =  new StringBuilder();
                sb.append(Integer.toString(selectedList.size())+" dnasamples?");

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
                                // TODO Auto-generated method stub
                                if(Messagebox.ON_OK.equals(event.getName())){
                                    //YES is clicked
                                    boolean successful;

                                    if(selectedList.size() == 1){  // 
                                        successful = viewModelService.deleteDnasample(selectedList.get(0));

                                    }else{
                                        //bulk delete
                                        successful = viewModelService.deleteDnasamples(selectedList);
                                    }


                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @GlobalCommand("retrieveDNAsampleList")
    @NotifyChange({"dnasampleList", "sizeDnasampleList", "selectedList", "allCbSelected", "cbAllUsers","paged"})
    public void retrieveDNAsampleList(){
        //...

        setDnasampleList(viewModelService.getAllDnasamplesBasedOnQuery(dnasampleEntity));

        selectedList.clear();

        setAllCbSelected(false);
        setCbAllUsers(false);
    }

    @Command("updateSelectDs")
    @NotifyChange({"cbAllUsers", "selectedList"})
    public void updateSelectDs(@BindingParam("dsChecked") DnasampleViewEntity dsList, @BindingParam("isChecked") Boolean isChecked){
        if(isChecked){
            selectedList.add(dsList);
        }else{
            setCbAllUsers(false);

            ListIterator<DnasampleViewEntity> it = selectedList.listIterator();
            while (it.hasNext()) {
                if (it.next().getDnasampleId().equals(dsList.getDnasampleId())) {
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

    public List<DnasampleViewEntity> getDnasampleList() {
        return dnasampleList;
    }

    public void setDnasampleList(List<DnasampleViewEntity> dnasampleList) {
        if(Utils.isListNotNullOrEmpty(dnasampleList)) {
            if(dnasampleList.size() > 25) setPaged(true);
            else setPaged(false);
            
            this.dnasampleList = dnasampleList;
        }
    }
    
    public DnasampleEntity getDnasampleEntity() {
        return dnasampleEntity;
    }

    public void setDnasampleEntity(DnasampleEntity dnasampleEntity) {
        this.dnasampleEntity = dnasampleEntity;
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


    public Integer getSizeDnasampleList() {
        if(Utils.isListNotNullOrEmpty(dnasampleList)) {
            sizeDnasampleList = dnasampleList.size();
        }else sizeDnasampleList = 0;
        return sizeDnasampleList;
    }


    public void setSizeDnasampleList(Integer sizeDnasampleList) {
        this.sizeDnasampleList = sizeDnasampleList;
    }


    public List<ProjectRecord> getProjectList() {
        return projectList;
    }


    public void setProjectList(List<ProjectRecord> projectList) {
        this.projectList = projectList;
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

}
