package org.gobiiproject.datatimescope.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.entity.GermplasmEntity;
import org.gobiiproject.datatimescope.entity.GermplasmViewEntity;
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

public class GermplasmViewModel {
	//UI component


    ViewModelService viewModelService;
    private boolean cbAllUsers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, isIsRoleUser=false, performedDeleteSuccesfully=false, paged=false;

    @Wire("#germplasmGrid")
    Grid germplasmGrid;
    
    private Integer sizeGermplasmList=0;
    
    private List<GermplasmViewEntity> germplasmList;
    private List<GermplasmViewEntity> selectedList;
    private GermplasmEntity germplasmEntity;

    @SuppressWarnings("unchecked")
    @Init
    public void init() {
        selectedList = new ArrayList<GermplasmViewEntity>();
        viewModelService = new ViewModelServiceImpl();
        
        setGermplasmEntity(new GermplasmEntity());
        setGermplasmList(viewModelService.getAllGermplasms());
        
    }


    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }


    @Command("submitQuery")
    @NotifyChange({"germplasmList","selectedList", "allCbSelected", "cbAllUsers","paged", "sizeGermplasmList"})
    public void submitQuery(){
//        viewModelService.getAllGermplasmsBasedOnQuery(germplasmEntity);
        try{
            germplasmList.clear(); //clear the list first and then just add if there are any selected

            selectedList.clear();

        }catch(NullPointerException e){

        }
        
        germplasmGrid.setEmptyMessage("There are no germplasms that match your search.");
        setGermplasmList(viewModelService.getAllGermplasmsBasedOnQuery(germplasmEntity));

        setiDBoxDisabled(false);
        setnameListDisabled(false);
        setAllCbSelected(false);
        setCbAllUsers(false);

    }

    @Command("resetGermplasmTab")
    @NotifyChange({"germplasmList", "sizeGermplasmList", "selectedList", "allCbSelected", "cbAllUsers", "germplasmEntity","iDBoxDisabled","nameListDisabled", "paged"})
    public void resetGermplasmTab(){
        try{
            germplasmList.clear(); //clear the list first and then just add if there are any selected
            selectedList.clear(); 

        }catch(NullPointerException e){

        }
        germplasmEntity = new GermplasmEntity();

        
        germplasmGrid.setEmptyMessage("There's nothing to see here. Submit a query to search for Germplasms.");
        setiDBoxDisabled(false);
        setnameListDisabled(false);
        setAllCbSelected(false);
        setCbAllUsers(false);
    }

    @Command("doSelectAll")
    @NotifyChange("allCbSelected")
    public void doSelectAll(){
        List<GermplasmViewEntity> germplasmListOnDisplay = getGermplasmList();

        selectedList.clear(); //clear the list first and then just add if there are any selected

        setAllCbSelected(isCbAllUsers());

        if (isCbAllUsers()) {
            try{
                for(GermplasmViewEntity u: germplasmListOnDisplay){
                    selectedList.add(u);
                }
            }catch(NullPointerException npe){
                Messagebox.show("Click submit query to display all germplasms", "There is nothing to select", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }
    }

    @Command("changeEnabled")
    @NotifyChange({"iDBoxDisabled","nameListDisabled"})
    public void changeEnabled(){
        isIDBoxDisabled = false; // reset
        isNameListDisabled= false; 
        
        //id range and names
        if(germplasmEntity.getGermplasmNamesAsEnterSeparatedString()!=null && !germplasmEntity.getGermplasmNamesAsEnterSeparatedString().isEmpty()){
            isIDBoxDisabled = true;
        }else if(germplasmEntity.getGermplasmIDStartRange() != null ){
            if(germplasmEntity.getGermplasmIDStartRange() >0 ){
                isNameListDisabled=true;
            }
        }else if(germplasmEntity.getGermplasmIDEndRange() !=null){
            if(germplasmEntity.getGermplasmIDEndRange()>0){
                isNameListDisabled=true;
            }
        }
        
    }


    @SuppressWarnings("unchecked")
    @Command("resetDSSummary")
    @NotifyChange({"germplasmSummary","performedDeleteSuccesfully"})
    public void resetDSSummary(){


//        if(germplasmSummary.size()>0){
//            performedDeleteSuccesfully=true;
//        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Command("deleteSelectedGermplasms")
    @NotifyChange({"germplasmSummary","performedDeleteSuccesfully"})
    public void deleteSelectedGermplasms(){

        if(selectedList.isEmpty()){ //Nothing is selected
            Messagebox.show("There are no germplasms selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("the following germplasms?");
            for(GermplasmViewEntity u: selectedList){
                sb.append("\n"+u.getGermplasmName());
            }
           
            if (selectedList.size()>10){

                sb =  new StringBuilder();
                sb.append(Integer.toString(selectedList.size())+" germplasms?");

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
                                        successful = viewModelService.deleteGermplasm(selectedList.get(0));

                                    }else{
                                        //bulk delete
                                        successful = viewModelService.deleteGermplasms(selectedList);
                                    }


                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @GlobalCommand("retrieveGermplasmList")
    @NotifyChange({"germplasmList", "sizeGermplasmList", "selectedList", "allCbSelected", "cbAllUsers","paged"})
    public void retrieveGermplasmList(){
        //...

        setGermplasmList(viewModelService.getAllGermplasmsBasedOnQuery(germplasmEntity));

        selectedList.clear();

        setAllCbSelected(false);
        setCbAllUsers(false);
    }

    @Command("updateSelectDs")
    @NotifyChange({"cbAllUsers", "selectedList"})
    public void updateSelectDs(@BindingParam("dsChecked") GermplasmViewEntity dsList, @BindingParam("isChecked") Boolean isChecked){
        if(isChecked){
            selectedList.add(dsList);
        }else{
            setCbAllUsers(false);

            ListIterator<GermplasmViewEntity> it = selectedList.listIterator();
            while (it.hasNext()) {
                if (it.next().getGermplasmId().equals(dsList.getGermplasmId())) {
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

    public List<GermplasmViewEntity> getGermplasmList() {
        return germplasmList;
    }

    public void setGermplasmList(List<GermplasmViewEntity> germplasmList) {
        if(Utils.isListNotNullOrEmpty(germplasmList)) {
            if(germplasmList.size() > 25) setPaged(true);
            else setPaged(false);
            
            this.germplasmList = germplasmList;
        }
    }
    
    public GermplasmEntity getGermplasmEntity() {
        return germplasmEntity;
    }

    public void setGermplasmEntity(GermplasmEntity germplasmEntity) {
        this.germplasmEntity = germplasmEntity;
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


    public Integer getSizeGermplasmList() {
        if(Utils.isListNotNullOrEmpty(germplasmList)) {
            sizeGermplasmList = germplasmList.size();
        }else sizeGermplasmList = 0;
        return sizeGermplasmList;
    }


    public void setSizeGermplasmList(Integer sizeGermplasmList) {
        this.sizeGermplasmList = sizeGermplasmList;
    }

}
