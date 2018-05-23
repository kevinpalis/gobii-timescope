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
import org.gobiiproject.datatimescope.db.generated.tables.records.VDatasetSummaryRecord;
import org.gobiiproject.datatimescope.entity.DatasetEntity;
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

public class DatasetViewModel {
	//UI component

	ViewModelService viewModelService;
	private boolean cbAllUsers, isAllCbSelected=false;

	private List<CvRecord> datasetTypes;
	private List<ContactRecord> contactsList, piList;
	private List<VDatasetSummaryRecord> datasetList, selectedDsList;
	private DatasetEntity datasetEntity;

	@Init
	public void init() {
		selectedDsList = new ArrayList<VDatasetSummaryRecord>();
		viewModelService = new ViewModelServiceImpl();
		setDatasetEntity(new DatasetEntity());
		setDatasetList(viewModelService.getAllDatasets());
		contactsList = viewModelService.getAllContacts();
		Integer [] roles = {1}; // PI only
		piList = viewModelService.getContactsByRoles(roles);
		setDatasetTypes(viewModelService.getCvTermsByGroupName("dataset_type"));
	}

	@Command("submitQuery")
	@NotifyChange({"datasetList","selectedDsList", "allCbSelected", "cbAllUsers"})
	public void submitQuery(){
		try{
		datasetList.clear(); //clear the list first and then just add if there are any selected
		}catch(NullPointerException e){
			
		}
		setDatasetList(viewModelService.getAllDatasetsBasedOnQuery(datasetEntity));
		

		setAllCbSelected(false);
		setCbAllUsers(false);
	}
	
	@Command("resetDatasetTab")
	@NotifyChange({"datasetList","selectedDsList", "allCbSelected", "cbAllUsers", "datasetEntity"})
	public void resetDatasetTab(){
		try{
		datasetList.clear(); //clear the list first and then just add if there are any selected
		selectedDsList.clear(); 
		}catch(NullPointerException e){
			
		}
		datasetEntity = new DatasetEntity();

		setAllCbSelected(false);
		setCbAllUsers(false);
	}
	@Command("doSelectAll")
	@NotifyChange("allCbSelected")
	public void doSelectAll(){
		List<VDatasetSummaryRecord> datasetListOnDisplay = getDatasetList();

		selectedDsList.clear(); //clear the list first and then just add if there are any selected

		setAllCbSelected(isCbAllUsers());

		if (isCbAllUsers()) {
			for(VDatasetSummaryRecord u: datasetListOnDisplay){
				selectedDsList.add(u);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command("deleteSelectedDatasets")
	public void deleteUsers(){

		if(selectedDsList.isEmpty()){ //Nothing is selected
			Messagebox.show("There are no users selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		else{
			StringBuilder sb = new StringBuilder();

			for(VDatasetSummaryRecord u: selectedDsList){
				sb.append("\n"+u.getDatasetName());
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

						if(selectedDsList.size() == 1){  // just one user is selected
							successful = viewModelService.deleteDataset(selectedDsList.get(0));
						}else{
							//bulk delete
							successful = viewModelService.deleteDatasets(selectedDsList);
						}

						if(successful) BindUtils.postGlobalCommand(null, null, "retrieveDatasetList", null);

					}
				}
			});
		}
	}

	@GlobalCommand("retrieveDatasetList")
	@NotifyChange({"datasetList", "selectedDsList", "allCbSelected", "cbAllUsers"})
	public void retrieveUserList(){
		//...

		setDatasetList(viewModelService.getAllDatasetsBasedOnQuery(datasetEntity));

		selectedDsList.clear();

		setAllCbSelected(false);
		setCbAllUsers(false);
	}
	
	@Command("updateSelectDs")
	@NotifyChange({"cbAllUsers", "selectedDsList"})
	public void updateSelectDs(@BindingParam("dsChecked") VDatasetSummaryRecord dsList, @BindingParam("isChecked") Boolean isChecked){
		if(isChecked){
			selectedDsList.add(dsList);
		}else{
			setCbAllUsers(false);

			ListIterator<VDatasetSummaryRecord> it = selectedDsList.listIterator();
			while (it.hasNext()) {
				if (it.next().getDatasetId().equals(dsList.getDatasetId())) {
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

	public List<VDatasetSummaryRecord> getDatasetList() {
		return datasetList;
	}

	public void setDatasetList(List<VDatasetSummaryRecord> datasetList) {
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

}
