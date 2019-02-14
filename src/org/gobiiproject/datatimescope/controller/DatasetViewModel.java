package org.gobiiproject.datatimescope.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.gobiiproject.datatimescope.entity.DatasetEntity;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
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
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

public class DatasetViewModel {
	//UI component

	ViewModelService viewModelService;
	private boolean cbAllUsers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, isIsRoleUser=false, performedDeleteSuccesfully=false;
	
	@Wire("#datasetGrid")
	Grid datasetGrid;
	
	private List<CvRecord> datasetTypes;
	private List<ContactRecord> contactsList, piList;
	private List<VDatasetSummaryEntity> datasetList, selectedDsList;
	private List<DatasetSummaryEntity> datasetSummary;
	private DatasetSummaryEntity datasetSummaryEntity;
	private DatasetEntity datasetEntity;

	@SuppressWarnings("unchecked")
	@Init
	public void init() {
		datasetSummaryEntity= new DatasetSummaryEntity();
		selectedDsList = new ArrayList<VDatasetSummaryEntity>();
		viewModelService = new ViewModelServiceImpl();
		setDatasetEntity(new DatasetEntity());
		setDatasetList(viewModelService.getAllDatasets(datasetSummaryEntity));
		contactsList = viewModelService.getAllContacts();
		Integer [] roles = {1}; // PI only
		piList = viewModelService.getContactsByRoles(roles);
		setDatasetTypes(viewModelService.getCvTermsByGroupName("dataset_type"));

		UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
        datasetSummary = (List<DatasetSummaryEntity>) Sessions.getCurrent().getAttribute("datasetSummary");
		
        if(datasetSummary.size()>0){
        	performedDeleteSuccesfully=true;
        }
		
		if(cre.getRole() == 3){
			isIsRoleUser=true;
		}
	}

	@Command("submitQuery")
	@NotifyChange({"datasetList","selectedDsList", "allCbSelected", "cbAllUsers"})
	public void submitQuery(){

		try{
			datasetList.clear(); //clear the list first and then just add if there are any selected

			selectedDsList.clear();

		}catch(NullPointerException e){

		}
		
		setDatasetList(viewModelService.getAllDatasetsBasedOnQuery(datasetEntity,datasetSummaryEntity));

		setiDBoxDisabled(false);
		setnameListDisabled(false);
		setAllCbSelected(false);
		setCbAllUsers(false);

	}

	@Command("resetDatasetTab")
	@NotifyChange({"datasetList","selectedDsList", "allCbSelected", "cbAllUsers", "datasetEntity","iDBoxDisabled","nameListDisabled"})
	public void resetDatasetTab(){
		try{
			datasetList.clear(); //clear the list first and then just add if there are any selected
			selectedDsList.clear(); 

		}catch(NullPointerException e){

		}
		datasetEntity = new DatasetEntity();

		setDatasetList(viewModelService.getAllDatasets(datasetSummaryEntity));
		setiDBoxDisabled(false);
		setnameListDisabled(false);
		setAllCbSelected(false);
		setCbAllUsers(false);
	}

	@Command("doSelectAll")
	@NotifyChange("allCbSelected")
	public void doSelectAll(){
		List<VDatasetSummaryEntity> datasetListOnDisplay = getDatasetList();

		selectedDsList.clear(); //clear the list first and then just add if there are any selected

		setAllCbSelected(isCbAllUsers());

		if (isCbAllUsers()) {
			try{
				for(VDatasetSummaryEntity u: datasetListOnDisplay){
					selectedDsList.add(u);
				}
			}catch(NullPointerException npe){
				Messagebox.show("Submit an empty query to display all datasets", "There is nothing to select", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	@Command("changeEnabled")
	@NotifyChange({"iDBoxDisabled","nameListDisabled"})
	public void changeEnabled(){
		isIDBoxDisabled = false; // reset
		isNameListDisabled= false; 

		if(datasetEntity.getDatasetNamesAsEnterSeparatedString()!=null && !datasetEntity.getDatasetNamesAsEnterSeparatedString().isEmpty()){
			isIDBoxDisabled = true;
		}else if(datasetEntity.getDatasetIDStartRange() != null ){
			if(datasetEntity.getDatasetIDStartRange() >0 ){
				isNameListDisabled=true;
			}
		}else if(datasetEntity.getDatasetIDEndRange() !=null){
			if(datasetEntity.getDatasetIDEndRange()>0){
				isNameListDisabled=true;
			}
		}
	}


	@SuppressWarnings("unchecked")
	@Command("resetDSSummary")
	@NotifyChange({"datasetSummary","performedDeleteSuccesfully"})
	public void resetDSSummary(){
        datasetSummary = (List<DatasetSummaryEntity>) Sessions.getCurrent().getAttribute("datasetSummary");
		
        
        if(datasetSummary.size()>0){
        	performedDeleteSuccesfully=true;
        }
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command("deleteSelectedDatasets")
	@NotifyChange({"datasetSummary","performedDeleteSuccesfully"})
	public void deleteUsers(){

		if(selectedDsList.isEmpty()){ //Nothing is selected
			Messagebox.show("There are no datasets selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		else{
			StringBuilder sb = new StringBuilder();

			for(VDatasetSummaryEntity u: selectedDsList){
				sb.append("\n"+u.getDatasetName());
			}

			Messagebox.show("Are you sure you want to delete the following datasets?\n"+sb.toString(), 
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
										successful = viewModelService.deleteDataset(selectedDsList.get(0), datasetSummary ,datasetSummaryEntity);
									
									}else{
										//bulk delete
										successful = viewModelService.deleteDatasets(selectedDsList, datasetSummary ,datasetSummaryEntity);
									}

									if(successful){
										BindUtils.postGlobalCommand(null, null, "retrieveDatasetList", null);

								        Session sess = Sessions.getCurrent();
								        sess.setAttribute("datasetSummary",datasetSummary);
								        
								        if(datasetSummary.size()>0){
								        	performedDeleteSuccesfully=true;
								        }
									}

								}
							}
						});
					}
				}
			});
		}
	}

	@GlobalCommand("retrieveDatasetList")
	@NotifyChange({"datasetList", "selectedDsList", "allCbSelected", "cbAllUsers"})
	public void retrieveUserList(){
		//...

		setDatasetList(viewModelService.getAllDatasetsBasedOnQuery(datasetEntity, datasetSummaryEntity));

		selectedDsList.clear();

		setAllCbSelected(false);
		setCbAllUsers(false);
	}

	@Command("updateSelectDs")
	@NotifyChange({"cbAllUsers", "selectedDsList"})
	public void updateSelectDs(@BindingParam("dsChecked") VDatasetSummaryEntity dsList, @BindingParam("isChecked") Boolean isChecked){
		if(isChecked){
			selectedDsList.add(dsList);
		}else{
			setCbAllUsers(false);

			ListIterator<VDatasetSummaryEntity> it = selectedDsList.listIterator();
			while (it.hasNext()) {
				if (it.next().getDatasetId().equals(dsList.getDatasetId())) {
					it.remove();
					break;
				}
			}
		}
	}

	@Command("exportDatasetTable")
	public void exportDatasetTable() {
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("gridTable", datasetGrid);
		BindUtils.postGlobalCommand(null, null, "exportTableToFile", args);
		
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

	public List<VDatasetSummaryEntity> getDatasetList() {
		return datasetList;
	}

	public void setDatasetList(List<VDatasetSummaryEntity> datasetList) {
		this.datasetList = datasetList;
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

	public List<CvRecord> getDatasetTypes() {
		return datasetTypes;
	}

	public void setDatasetTypes(List<CvRecord> list) {
		this.datasetTypes = list;
	}

	public DatasetEntity getDatasetEntity() {
		return datasetEntity;
	}

	public void setDatasetEntity(DatasetEntity datasetEntity) {
		this.datasetEntity = datasetEntity;
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

	public List<DatasetSummaryEntity> getDatasetSummary() {
		return datasetSummary;
	}

	public void setDatasetSummary(List<DatasetSummaryEntity> datasetSummary) {
		this.datasetSummary = datasetSummary;
	}

}
