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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.gobiiproject.datatimescope.db.generated.tables.LinkageGroup;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.entity.LinkageGroupEntity;
import org.gobiiproject.datatimescope.entity.LinkageGroupSummaryEntity;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.entity.VLinkageGroupSummaryEntity;
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

public class LinkageGroupViewModel {
	//UI component

	ViewModelService viewModelService;
	private boolean cbAllUsers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, isIsRoleUser=false, performedDeleteSuccesfully=false, paged=false;

	@Wire("#linkageGroupGrid")
	Grid linkageGroupGrid;

	private List<CvRecord> linkageGroupTypes;
	private List<ContactRecord> contactsList, piList;
	private List<VLinkageGroupSummaryEntity> linkageGroupList, selectedDsList;
	private List<LinkageGroupSummaryEntity> linkageGroupSummary;
	private LinkageGroupSummaryEntity linkageGroupSummaryEntity;
	private LinkageGroupEntity linkageGroupEntity;

	@SuppressWarnings("unchecked")
	@Init
	public void init() {
		linkageGroupSummaryEntity= new LinkageGroupSummaryEntity();
		selectedDsList = new ArrayList<VLinkageGroupSummaryEntity>();
		viewModelService = new ViewModelServiceImpl();
		setLinkageGroupEntity(new LinkageGroupEntity());
		setLinkageGroupList(viewModelService.getAllLinkageGroups(linkageGroupSummaryEntity));
		contactsList = viewModelService.getAllContacts();
		Integer [] roles = {1}; // PI only
		piList = viewModelService.getContactsByRoles(roles);
		ContactRecord selectAllPI = new ContactRecord(0);
		piList.add(0, selectAllPI);
		setLinkageGroupTypes(viewModelService.getCvTermsByGroupName("linkageGroup_type"));

		UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
		linkageGroupSummary = (List<LinkageGroupSummaryEntity>) Sessions.getCurrent().getAttribute("linkageGroupSummary");

		try {
		if(linkageGroupSummary.size()>0){
			performedDeleteSuccesfully=true;
		}
		}catch(NullPointerException npe) {
			
		}
		if(cre.getRole() == 3){
			isIsRoleUser=true;
		}
	}


	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}


	@Command("submitQuery")
	@NotifyChange({"linkageGroupList","selectedDsList", "allCbSelected", "cbAllUsers","paged"})
	public void submitQuery(){

		try{
			linkageGroupList.clear(); //clear the list first and then just add if there are any selected

			selectedDsList.clear();

		}catch(NullPointerException e){

		}

		setLinkageGroupList(viewModelService.getAllLinkageGroupsBasedOnQuery(linkageGroupEntity,linkageGroupSummaryEntity));

		setiDBoxDisabled(false);
		setnameListDisabled(false);
		setAllCbSelected(false);
		setCbAllUsers(false);

	}

	@Command("resetLinkageGroupTab")
	@NotifyChange({"linkageGroupList","selectedDsList", "allCbSelected", "cbAllUsers", "linkageGroupEntity","iDBoxDisabled","nameListDisabled", "paged"})
	public void resetLinkageGroupTab(){
		try{
			linkageGroupList.clear(); //clear the list first and then just add if there are any selected
			selectedDsList.clear(); 

		}catch(NullPointerException e){

		}
		linkageGroupEntity = new LinkageGroupEntity();

		setLinkageGroupList(viewModelService.getAllLinkageGroups(linkageGroupSummaryEntity));
		setiDBoxDisabled(false);
		setnameListDisabled(false);
		setAllCbSelected(false);
		setCbAllUsers(false);
	}

	@Command("doSelectAll")
	@NotifyChange("allCbSelected")
	public void doSelectAll(){
		List<VLinkageGroupSummaryEntity> linkageGroupListOnDisplay = getLinkageGroupList();

		selectedDsList.clear(); //clear the list first and then just add if there are any selected

		setAllCbSelected(isCbAllUsers());

		if (isCbAllUsers()) {
			try{
				for(VLinkageGroupSummaryEntity u: linkageGroupListOnDisplay){
					selectedDsList.add(u);
				}
			}catch(NullPointerException npe){
				Messagebox.show("Submit an empty query to display all linkageGroups", "There is nothing to select", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	@Command("changeEnabled")
	@NotifyChange({"iDBoxDisabled","nameListDisabled"})
	public void changeEnabled(){
		isIDBoxDisabled = false; // reset
		isNameListDisabled= false; 

		if(linkageGroupEntity.getLinkageGroupNamesAsEnterSeparatedString()!=null && !linkageGroupEntity.getLinkageGroupNamesAsEnterSeparatedString().isEmpty()){
			isIDBoxDisabled = true;
		}else if(linkageGroupEntity.getLinkageGroupIDStartRange() != null ){
			if(linkageGroupEntity.getLinkageGroupIDStartRange() >0 ){
				isNameListDisabled=true;
			}
		}else if(linkageGroupEntity.getLinkageGroupIDEndRange() !=null){
			if(linkageGroupEntity.getLinkageGroupIDEndRange()>0){
				isNameListDisabled=true;
			}
		}
	}


	@SuppressWarnings("unchecked")
	@Command("resetDSSummary")
	@NotifyChange({"linkageGroupSummary","performedDeleteSuccesfully"})
	public void resetDSSummary(){
		linkageGroupSummary = (List<LinkageGroupSummaryEntity>) Sessions.getCurrent().getAttribute("linkageGroupSummary");


		if(linkageGroupSummary.size()>0){
			performedDeleteSuccesfully=true;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command("deleteSelectedLinkageGroups")
	@NotifyChange({"linkageGroupSummary","performedDeleteSuccesfully"})
	public void deleteSelectedLinkageGroups(){

		if(selectedDsList.isEmpty()){ //Nothing is selected
			Messagebox.show("There are no linkageGroups selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		else{
			StringBuilder sb = new StringBuilder();

			for(VLinkageGroupSummaryEntity u: selectedDsList){
				sb.append("\n"+u.getLinkageGroupName());
			}

			Messagebox.show("Are you sure you want to delete the following linkageGroups?\n"+sb.toString(), 
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
										successful = viewModelService.deleteLinkageGroup(selectedDsList.get(0), linkageGroupSummary ,linkageGroupSummaryEntity);

									}else{
										//bulk delete
										successful = viewModelService.deleteLinkageGroups(selectedDsList, linkageGroupSummary ,linkageGroupSummaryEntity);
									}

									if(successful){
										BindUtils.postGlobalCommand(null, null, "retrieveLinkageGroupList", null);

										Session sess = Sessions.getCurrent();
										sess.setAttribute("linkageGroupSummary",linkageGroupSummary);

										if(linkageGroupSummary.size()>0){
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

	@GlobalCommand("retrieveLinkageGroupList")
	@NotifyChange({"linkageGroupList", "selectedDsList", "allCbSelected", "cbAllUsers","paged"})
	public void retrieveUserList(){
		//...

		setLinkageGroupList(viewModelService.getAllLinkageGroupsBasedOnQuery(linkageGroupEntity, linkageGroupSummaryEntity));

		selectedDsList.clear();

		setAllCbSelected(false);
		setCbAllUsers(false);
	}

	@Command("updateSelectDs")
	@NotifyChange({"cbAllUsers", "selectedDsList"})
	public void updateSelectDs(@BindingParam("dsChecked") VLinkageGroupSummaryEntity dsList, @BindingParam("isChecked") Boolean isChecked){
		if(isChecked){
			selectedDsList.add(dsList);
		}else{
			setCbAllUsers(false);

			ListIterator<VLinkageGroupSummaryEntity> it = selectedDsList.listIterator();
			while (it.hasNext()) {
				if (it.next().getLinkageGroupId().equals(dsList.getLinkageGroupId())) {
					it.remove();
					break;
				}
			}
		}
	}

	@Command("exportLinkageGroupTable")
	public void exportLinkageGroupTable() {

		ListIterator<VLinkageGroupSummaryEntity> it = linkageGroupList.listIterator();
		StringBuffer buffMap = new StringBuffer();

		while (it.hasNext()) {

			VLinkageGroupSummaryEntity next = it.next();

			if(it.nextIndex()==1){
				buffMap.append(next.getHeaderDelimitedBy(","));
			}
			buffMap.append(next.getAllDelimitedBy(","));

		}

		FileWriter fw;
		try {
			File file = new File("timescope_linkageGroup.csv");
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

	@Command("exportCurrentLinkageGroupTablePage")
	public void exportCurrentLinkageGroupTablePage() {


		int ActivePage = linkageGroupGrid.getActivePage();
		int initial, last;
		if(ActivePage==0){
			initial=1;
		}else{
			initial=(ActivePage*linkageGroupGrid.getPageSize())+1;
		}

		StringBuffer buffMap = new StringBuffer();


		List<Integer> indices = new ArrayList<Integer>();

		last = initial+linkageGroupGrid.getPageSize();
		//get Indices
		for( int i = initial; i<last; i++){

			indices.add(i);

		}

		ListIterator<VLinkageGroupSummaryEntity> it = linkageGroupList.listIterator();

		while (it.hasNext()) {

			VLinkageGroupSummaryEntity next = it.next();
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
			File file = new File("timescope_linkageGroup_currentpage.csv");
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

	public List<VLinkageGroupSummaryEntity> getLinkageGroupList() {
		return linkageGroupList;
	}

	public void setLinkageGroupList(List<VLinkageGroupSummaryEntity> linkageGroupList) {
		
		try {
		if(linkageGroupList.size() > 25) setPaged(true);
		else setPaged(false);
		}catch(NullPointerException npe) {
			
		}
		this.linkageGroupList = linkageGroupList;
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

	public List<CvRecord> getLinkageGroupTypes() {
		return linkageGroupTypes;
	}

	public void setLinkageGroupTypes(List<CvRecord> list) {
		this.linkageGroupTypes = list;
		CvRecord newRecord = new CvRecord(0);
		linkageGroupTypes.add(0, newRecord);
	}

	public LinkageGroupEntity getLinkageGroupEntity() {
		return linkageGroupEntity;
	}

	public void setLinkageGroupEntity(LinkageGroupEntity linkageGroupEntity) {
		this.linkageGroupEntity = linkageGroupEntity;
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

	public List<LinkageGroupSummaryEntity> getLinkageGroupSummary() {
		return linkageGroupSummary;
	}

	public void setLinkageGroupSummary(List<LinkageGroupSummaryEntity> linkageGroupSummary) {
		this.linkageGroupSummary = linkageGroupSummary;
	}


	public boolean isPaged() {
		return paged;
	}


	public void setPaged(boolean paged) {
		this.paged = paged;
	}

}
