package org.gobiiproject.datatimescope.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.entity.DatasetEntity;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.exceptions.CannotDeleteDataSetListException;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.gobiiproject.datatimescope.services.DataSetDeleteInfo;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.WebappUtil;
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
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;

public class DatasetViewModel {
	//UI component

	ViewModelService viewModelService;
	private boolean cbAllUsers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, isIsRoleUser=false, performedDeleteSuccesfully=false, paged=false;

	@Wire("#datasetGrid")
	Grid datasetGrid;

	private Integer sizeDatasetList=0;

	private List<CvRecord> datasetTypes;
	private List<ContactRecord> contactsList, piList;
	private List<VDatasetSummaryEntity> datasetList, selectedDsList;
	private List<DatasetSummaryEntity> datasetSummary;
	private DatasetSummaryEntity datasetSummaryEntity;
	private DatasetEntity datasetEntity;

	@SuppressWarnings("unchecked")
	@Init
	public void init() {
		try {
			datasetSummaryEntity= new DatasetSummaryEntity();
			selectedDsList = new ArrayList<VDatasetSummaryEntity>();
			viewModelService = new ViewModelServiceImpl();
			setDatasetEntity(new DatasetEntity());
			setDatasetList(viewModelService.getAllDatasets(datasetSummaryEntity));
			contactsList = viewModelService.getAllContacts();
			Integer [] roles = {1}; // PI only
			piList = viewModelService.getContactsByRoles(roles);
			ContactRecord selectAllPI = new ContactRecord(0, "SELECT ALL PI", "All", "selectAll",  "c.record@gmail.com", roles, 1, null, 1, null, 1, "AllPI");
			piList.add(0, selectAllPI);
			setDatasetTypes(viewModelService.getCvTermsByGroupName("dataset_type"));

			UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
			datasetSummary = (List<DatasetSummaryEntity>) Sessions.getCurrent().getAttribute("datasetSummary");

			if(datasetSummary.size()>0){
				performedDeleteSuccesfully=true;
			}

			if(cre.getRole() == 3){
				isIsRoleUser=true;
			}
		} catch (TimescopeException e) {
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
		}
	}


	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}


	@Command("submitQuery")
	@NotifyChange({"datasetList","selectedDsList", "allCbSelected", "cbAllUsers","paged", "sizeDatasetList"})
	public void submitQuery(){
		try {
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
		} catch (TimescopeException e) {
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
		}

	}

	@Command("resetDatasetTab")
	@NotifyChange({"datasetList", "sizeDatasetList", "selectedDsList", "allCbSelected", "cbAllUsers", "datasetEntity","iDBoxDisabled","nameListDisabled", "paged"})
	public void resetDatasetTab(){
		try {
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
		} catch (TimescopeException e) {
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
		}
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
	public void deleteSelectedDatasets(){

		if(selectedDsList.isEmpty()){ //Nothing is selected
			Messagebox.show("There are no datasets selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		else{
			StringBuilder sb = new StringBuilder();

			for(VDatasetSummaryEntity u: selectedDsList){
				sb.append("\n"+u.getDatasetName());
			}


			Messagebox.show(
					"Are you sure you want to delete the following datasets?\n"+sb.toString(), 
					"Confirm Delete",
					Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener(){
						public void onEvent(Event e){
							if(Messagebox.ON_YES.equals(e.getName())){
								//OK is clicked
								deleteSelectedDsListSecondCheckpoint();
							}else if(Messagebox.ON_NO.equals(e.getName())){
								//Cancel is clicked
								return;
							}
						}
					}
					);

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void deleteSelectedDsListSecondCheckpoint() {
		Messagebox.show(
			"THIS ACTION IS NOT REVERSIBLE.\n\nDo you want to continue?\n", 
			"WARNING",
			Messagebox.YES | Messagebox.CANCEL,
			Messagebox.EXCLAMATION,
			new org.zkoss.zk.ui.event.EventListener(){
				public void onEvent(Event e){
					if(Messagebox.ON_YES.equals(e.getName())){
						//OK is clicked
						deleteSelectedDsListFinalCheckpoint();
					}else if(Messagebox.ON_CANCEL.equals(e.getName())){
						//Cancel is clicked
						return;
					}
				}
			}
		);

	}

	@SuppressWarnings("unchecked")
	private void deleteSelectedDsListFinalCheckpoint() {
		boolean successful = false;
		DataSetDeleteInfo info = null;
		try {
			if(selectedDsList.size() == 1){  // just one user is selected
				info = viewModelService.deleteDataset(selectedDsList.get(0), datasetSummary ,datasetSummaryEntity);
				if (info.getDsListSize() == 1) {
					Messagebox.show("Data set deleted", "Successfully deleted dataset!",Messagebox.OK, Messagebox.INFORMATION);
				}
			}else{
				//bulk delete
				int itemsDeleted = 0;

				try {
					info = viewModelService.deleteDatasets(selectedDsList, datasetSummary ,datasetSummaryEntity, false, null);
					successful = info != null && info.getDsListSize() > 0;
					if (successful) {
						Messagebox.show(info.toString(), "Successfully deleted datasets!",Messagebox.OK, Messagebox.INFORMATION);
					}
					
				} catch (CannotDeleteDataSetListException cde) {
					Messagebox.show(
						cde.getMessage() + "\n\n Do you still want to continue?", 
						"Some dataset can't be deleted",
						Messagebox.YES | Messagebox.CANCEL,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener(){
							public void onEvent(Event e){
								if(Messagebox.ON_YES.equals(e.getName())){
									//OK is clicked
									if(selectedDsList.size() == cde.getDataSetNames().size()){
										Messagebox.show("Cannot delete the datafiles for all of the datasets selected ", "ERROR: Cannot delete datasets!", Messagebox.OK, Messagebox.ERROR);
										return;
									}else{
										//retry the call with true remove option
										try {
											DataSetDeleteInfo info2 = viewModelService.deleteDatasets(selectedDsList, datasetSummary ,datasetSummaryEntity, true, cde.getDataSetNames());
											if (info2 != null && info2.getDsListSize() > 0) {
												Messagebox.show(info2.toString(), "Successfully deleted datasets!",Messagebox.OK, Messagebox.INFORMATION);
											}
										} catch (TimescopeException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}

								}else if(Messagebox.ON_CANCEL.equals(e.getName())){
									//Cancel is clicked
									return;
								}
							}
						}
					);

				}
			}
			
		

		} catch (TimescopeException e) {
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
		} finally {
			BindUtils.postGlobalCommand(null, null, "retrieveDatasetList", null);
			Session sess = Sessions.getCurrent();
			sess.setAttribute("datasetSummary",datasetSummary);
		}
	}

	@GlobalCommand("retrieveDatasetList")
	@NotifyChange({"datasetList", "sizeDatasetList", "selectedDsList", "allCbSelected", "cbAllUsers","paged"})
	public void retrieveUserList(){
		//...
		try {
			setDatasetList(viewModelService.getAllDatasetsBasedOnQuery(datasetEntity, datasetSummaryEntity));

			selectedDsList.clear();

			setAllCbSelected(false);
			setCbAllUsers(false);
		} catch (TimescopeException e) {
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
		}
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

		ListIterator<VDatasetSummaryEntity> it = datasetList.listIterator();
		StringBuffer buffMap = new StringBuffer();

		while (it.hasNext()) {

			VDatasetSummaryEntity next = it.next();

			if(it.nextIndex()==1){
				buffMap.append(next.getHeaderDelimitedBy(","));
			}
			buffMap.append(next.getAllDelimitedBy(","));

		}

		FileWriter fw;
		try {
			File file = new File("timescope_dataset.csv");
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

	@Command("exportCurrentDatasetTablePage")
	public void exportCurrentDatasetTablePage() {


		int ActivePage = datasetGrid.getActivePage();
		int initial, last;
		if(ActivePage==0){
			initial=1;
		}else{
			initial=(ActivePage*datasetGrid.getPageSize())+1;
		}

		StringBuffer buffMap = new StringBuffer();


		List<Integer> indices = new ArrayList<Integer>();

		last = initial+datasetGrid.getPageSize();
		//get Indices
		for( int i = initial; i<last; i++){

			indices.add(i);

		}

		ListIterator<VDatasetSummaryEntity> it = datasetList.listIterator();

		while (it.hasNext()) {

			VDatasetSummaryEntity next = it.next();
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
			File file = new File("timescope_dataset_currentpage.csv");
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

		if(datasetList.size() > 25) setPaged(true);
		else setPaged(false);

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
		CvRecord newRecord = new CvRecord(0, "SELECT ALL DATASET TYPE", "select all", 1, 1, "abbreviation", 1, 1, null);
		datasetTypes.add(0, newRecord);
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


	public boolean isPaged() {
		return paged;
	}


	public void setPaged(boolean paged) {
		this.paged = paged;
	}


	public Integer getSizeDatasetList() {

		sizeDatasetList = datasetList.size();

		if(datasetList.size()<1) sizeDatasetList = 0;

		return sizeDatasetList;
	}


	public void setSizeDatasetList(Integer sizeDatasetList) {
		this.sizeDatasetList = sizeDatasetList;
	}

}
