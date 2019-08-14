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
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MapsetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.OrganizationRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ReferenceRecord;
import org.gobiiproject.datatimescope.entity.LinkageGroupEntity;
import org.gobiiproject.datatimescope.entity.LinkageGroupSummaryEntity;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.entity.VLinkageGroupSummaryEntity;
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

public class LinkageGroupViewModel {
	//UI component

	ViewModelService viewModelService;
	private boolean cbAllUsers, isAllCbSelected=false, isIDBoxDisabled=false, isNameListDisabled=false, isIsRoleUser=false, performedDeleteSuccesfully=false, paged=false, shouldNextChangeResetOtherFilterValues=false;
	private boolean dbReferences=true, dbMapsets=true, dbLinkageGroup=true;
	
	@Wire("#linkageGroupGrid")
	Grid linkageGroupGrid;

    private Integer sizeLGList=0;
	private List<CvRecord> linkageGroupTypes;
	private List<ContactRecord> contactsList, piList;
    private List<ReferenceRecord> referenceList;
    private List<MapsetRecord> mapsetList, backupMapsetList;
    private List<LinkageGroupRecord> linkageGroupList, backupLinkageGroupList;
	private List<VLinkageGroupSummaryEntity> vlinkageGroupSummaryEntityList, selectedDsList;
	private List<LinkageGroupSummaryEntity> linkageGroupSummary;
	private LinkageGroupSummaryEntity linkageGroupSummaryEntity;
	private LinkageGroupEntity linkageGroupEntity;

    private String filterMapset;
    private String filterLinkageGroup;
    private String filterReference, currentFiltersAsText;
	
	@SuppressWarnings("unchecked")
	@Init
	public void init() {
        Integer [] roles = {1}; // PI only
        ContactRecord selectAllPI = new ContactRecord(0, null, null, null, null, roles, null, null, null, null, null, null);
        
		linkageGroupSummaryEntity= new LinkageGroupSummaryEntity();
		selectedDsList = new ArrayList<VLinkageGroupSummaryEntity>();
		viewModelService = new ViewModelServiceImpl();
        contactsList = viewModelService.getAllContacts();
        piList = viewModelService.getContactsByRoles(roles);
        piList.add(0, selectAllPI);

        backupMapsetList = new ArrayList<MapsetRecord>();
        backupLinkageGroupList = new ArrayList<LinkageGroupRecord>();
		setLinkageGroupEntity(new LinkageGroupEntity());
		setvlinkageGroupSummaryEntityList(viewModelService.getAllLinkageGroups(linkageGroupSummaryEntity));
        setLinkageGroupTypes(viewModelService.getCvTermsByGroupName("linkageGroup_type"));
        setReferenceList(viewModelService.getAllReferences());
        setMapsetList(viewModelService.getAllMapsets());
        setLinkageGroupList(viewModelService.getAllLinkageGroups());

        if(referenceList.isEmpty()) dbReferences = false;
        if(mapsetList.isEmpty()) dbMapsets = false;
        if(linkageGroupList.isEmpty()) dbLinkageGroup = false;
		
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
	@NotifyChange({"vlinkageGroupSummaryEntityList","sizeLGList", "sizeLGList", "selectedDsList", "allCbSelected", "cbAllUsers","paged"})
	public void submitQuery(){

		try{
			vlinkageGroupSummaryEntityList.clear(); //clear the list first and then just add if there are any selected

			selectedDsList.clear();

		}catch(NullPointerException e){

		}

		setvlinkageGroupSummaryEntityList(viewModelService.getAllLinkageGroupsBasedOnQuery(linkageGroupEntity,linkageGroupSummaryEntity));

		setiDBoxDisabled(false);
		setnameListDisabled(false);
		setAllCbSelected(false);
		setCbAllUsers(false);

	}

	@Command("resetLinkageGroupTab")
	@NotifyChange({"vlinkageGroupSummaryEntityList","sizeLGList","selectedDsList", "allCbSelected", "cbAllUsers", "linkageGroupEntity","iDBoxDisabled","nameListDisabled", "paged"})
	public void resetLinkageGroupTab(){
		try{
			vlinkageGroupSummaryEntityList.clear(); //clear the list first and then just add if there are any selected
			selectedDsList.clear(); 

		}catch(NullPointerException e){

		}
		linkageGroupEntity = new LinkageGroupEntity();

		setvlinkageGroupSummaryEntityList(viewModelService.getAllLinkageGroups(linkageGroupSummaryEntity));
		setiDBoxDisabled(false);
		setnameListDisabled(false);
		setAllCbSelected(false);
		setCbAllUsers(false);
	}

	@Command("doSelectAll")
	@NotifyChange("allCbSelected")
	public void doSelectAll(){
		List<VLinkageGroupSummaryEntity> linkageGroupListOnDisplay = getvlinkageGroupSummaryEntityList();

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

//	@Command("changeEnabled")
//	@NotifyChange({"iDBoxDisabled","nameListDisabled"})
//	public void changeEnabled(){
//		isIDBoxDisabled = false; // reset
//		isNameListDisabled= false; 
//
//		if(linkageGroupEntity.getLinkageGroupNamesAsEnterSeparatedString()!=null && !linkageGroupEntity.getLinkageGroupNamesAsEnterSeparatedString().isEmpty()){
//			isIDBoxDisabled = true;
//		}else if(linkageGroupEntity.getLinkageGroupIDStartRange() != null ){
//			if(linkageGroupEntity.getLinkageGroupIDStartRange() >0 ){
//				isNameListDisabled=true;
//			}
//		}else if(linkageGroupEntity.getLinkageGroupIDEndRange() !=null){
//			if(linkageGroupEntity.getLinkageGroupIDEndRange()>0){
//				isNameListDisabled=true;
//			}
//		}
//	}


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

	@Command
    public void selectReferenceTab() {

        showTabInfoPopUp("reference");
    }
	

    @NotifyChange("mapsetList")
    @Command
    public void selectMapsetsTab() {
        //Displaying all mapsets instead

        showTabInfoPopUp("mapset");
        if(((ViewModelServiceImpl) viewModelService).isListNotNullOrEmpty(linkageGroupEntity.getReferenceList())) {

            setMapsetList(viewModelService.getAllMapsetsByReferenceId(linkageGroupEntity.getReferenceList()));
        }else{

            setMapsetList(viewModelService.getAllMapsets());
        }
    }


    @NotifyChange("linkageGroupList")
    @Command
    public void selectLinkageGroupTab() {
        if(((ViewModelServiceImpl) viewModelService).isListNotNullOrEmpty(linkageGroupEntity.getMapsetList())) {

            setLinkageGroupList(viewModelService.getLinkageGroupByMapsetId(linkageGroupEntity.getMapsetList()));
        }else if(((ViewModelServiceImpl) viewModelService).isListNotNullOrEmpty(linkageGroupEntity.getReferenceList())) {

            setLinkageGroupList(viewModelService.getAllLinkageGroupsByReferenceId(linkageGroupEntity.getReferenceList()));
        }else{

            setLinkageGroupList(viewModelService.getAllLinkageGroups());
        }
    }
	
	private void showTabInfoPopUp(String string) {
        // TODO Auto-generated method stub
	    StringBuilder sb = new StringBuilder();

        switch(string){

        case "reference": 
            if(((ViewModelServiceImpl) viewModelService).isListNotNullOrEmpty(linkageGroupEntity.getMapsetList())) {
                sb.append("\n Mapset");
            }
        case "mapset": 
            if(((ViewModelServiceImpl) viewModelService).isListNotNullOrEmpty(linkageGroupEntity.getLinkageGroupList())) {
                sb.append(Utils.checkIfCommaNeeded(sb,"\n Linkage Group"));
            } 
        default: 
            break; 
        } 

        if(!sb.toString().isEmpty()) {
            setShouldNextChangeResetOtherFilterValues(true);
            sb.insert(0, "Changing the selected values here will reset the selections you already made on the  tab(s): \n");
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("message", sb.toString());

            Window window = (Window)Executions.createComponents(
                    "/info_popup.zul", null, args);
            window.setPosition("center");
            window.setClosable(true);
            window.doModal();
        }
        else setShouldNextChangeResetOtherFilterValues(false);
    }

    @Command("displayFilterDetails")
    public void displayFilterDetails(@BindingParam("category") String category){
        int ctr = 0;
        String title="This list is being filtered by: ";
        StringBuilder sb = new StringBuilder();
        StringBuilder filterInfo = new StringBuilder();

        switch(category){
        case "mapset" :
            if(!linkageGroupEntity.getReferenceList().isEmpty()) {
                sb.append("\n Reference(s): \n"+ Utils.getListNamesToString(linkageGroupEntity.getReferenceList())+"\n");
            } else {
                ctr++;
                filterInfo.append(Utils.checkIfCommaNeeded(filterInfo,"references"));
            }
            
            if(category.equalsIgnoreCase("mapset")) break;
        case "linkagegroup": 
            if(!linkageGroupEntity.getMapsetList().isEmpty()) {
                ctr++;
                sb.append("\n Mapset(s): \n"+ Utils.getListNamesToString(linkageGroupEntity.getMapsetList())+"\n");
            }else filterInfo.append("mapset");
            break;
        default: 
            title=" All items are displayed.";
            sb.append("This list is not affected by filters.");
        }
        
        if(sb.toString().isEmpty()) {
            if(ctr>1) filterInfo.insert(0, "List is not filtered. There are no filters selected in the following tabs: ");
            else {
                filterInfo.insert(0, "List is not filtered. There are no filters selected in the ");
                filterInfo.append(" tab.");
            }
            Messagebox.show(filterInfo.toString(), "All items are displayed.", Messagebox.OK, Messagebox.NONE);
        }
        else Messagebox.show(sb.toString(), title, Messagebox.OK, Messagebox.NONE);
        
    }

    @Command("validateForReset")
    @NotifyChange({"markerEntity", "currentFiltersAsText"})
    public void validateForReset(@BindingParam("category") String category){

        //if reset required then: if(shouldNextChangeResetOtherFilterValues)  

            switch(category){

            case "reference": 
                if(((ViewModelServiceImpl) viewModelService).isListNotNullOrEmpty(linkageGroupEntity.getReferenceList())) {

                    if(shouldNextChangeResetOtherFilterValues) linkageGroupEntity.getReferenceList().clear();
                }
            case "mapset": 
                    if(((ViewModelServiceImpl) viewModelService).isListNotNullOrEmpty(linkageGroupEntity.getMapsetList())) {
                        if(shouldNextChangeResetOtherFilterValues) linkageGroupEntity.getMapsetList().clear();
                }
            case "linkageGroup": 
                if(((ViewModelServiceImpl) viewModelService).isListNotNullOrEmpty(linkageGroupEntity.getLinkageGroupList())) {
                    if(shouldNextChangeResetOtherFilterValues) linkageGroupEntity.getLinkageGroupList().clear();
                }
                break; 
            default: 
                break; 
            }
            //once resets are done, update boolean
            shouldNextChangeResetOtherFilterValues=false;
            setCurrentFiltersAsText(linkageGroupEntity.getFiltersAsText());
    }
    
    @GlobalCommand("retrieveLinkageGroupList")
	@NotifyChange({"vlinkageGroupSummaryEntityList","sizeLGList", "selectedDsList", "allCbSelected", "cbAllUsers","paged"})
	public void retrieveUserList(){
		//...

		setvlinkageGroupSummaryEntityList(viewModelService.getAllLinkageGroupsBasedOnQuery(linkageGroupEntity, linkageGroupSummaryEntity));

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

		ListIterator<VLinkageGroupSummaryEntity> it = vlinkageGroupSummaryEntityList.listIterator();
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

		ListIterator<VLinkageGroupSummaryEntity> it = vlinkageGroupSummaryEntityList.listIterator();

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
	
	@NotifyChange("referenceList")
    @Command
    public void doSearchReferences() {
        List<ReferenceRecord> allItems = viewModelService.getAllReferences();
        Utils.filterItems(referenceList, allItems, filterReference);
    }

    @NotifyChange("mapasetList")
    @Command
    public void doSearchMapset() {

        Utils.filterItems(mapsetList, backupMapsetList, filterMapset);
    }


    @NotifyChange("linkageGroupList")
    @Command
    public void doSearchLinkageGroup() {

        Utils.filterItems(linkageGroupList, backupLinkageGroupList, filterLinkageGroup);
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

	public List<VLinkageGroupSummaryEntity> getvlinkageGroupSummaryEntityList() {
		return vlinkageGroupSummaryEntityList;
	}

	public void setvlinkageGroupSummaryEntityList(List<VLinkageGroupSummaryEntity> vlinkageGroupSummaryEntityList) {
		
		try {
		if(vlinkageGroupSummaryEntityList.size() > 25) setPaged(true);
		else setPaged(false);
		}catch(NullPointerException npe) {
			
		}
		this.vlinkageGroupSummaryEntityList = vlinkageGroupSummaryEntityList;
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
		CvRecord newRecord = new CvRecord(0, null, null, null, null, null, null, null, null);
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


    public boolean isDbReferences() {
        return dbReferences;
    }


    public void setDbReferences(boolean dbReferences) {
        this.dbReferences = dbReferences;
    }


    public List<ReferenceRecord> getReferenceList() {
        return referenceList;
    }


    public void setReferenceList(List<ReferenceRecord> referenceList) {
        this.referenceList = referenceList;
    }


    public boolean isShouldNextChangeResetOtherFilterValues() {
        return shouldNextChangeResetOtherFilterValues;
    }


    public void setShouldNextChangeResetOtherFilterValues(boolean shouldNextChangeResetOtherFilterValues) {
        this.shouldNextChangeResetOtherFilterValues = shouldNextChangeResetOtherFilterValues;
    }


    public String getFilterMapset() {
        return filterMapset;
    }


    public void setFilterMapset(String filterMapset) {
        this.filterMapset = filterMapset;
    }


    public String getFilterLinkageGroup() {
        return filterLinkageGroup;
    }


    public void setFilterLinkageGroup(String filterLinkageGroup) {
        this.filterLinkageGroup = filterLinkageGroup;
    }


    public String getFilterReference() {
        return filterReference;
    }


    public void setFilterReference(String filterReference) {
        this.filterReference = filterReference;
    }


    public List<MapsetRecord> getMapsetList() {
        return mapsetList;
    }


    public void setMapsetList(List<MapsetRecord> mapsetList) {

        backupMapsetList.clear();
        backupMapsetList.addAll(mapsetList);
        
        
        this.mapsetList = mapsetList;
    }


    public List<VLinkageGroupSummaryEntity> getVlinkageGroupSummaryEntityList() {
        return vlinkageGroupSummaryEntityList;
    }


    public boolean isDbMapsets() {
        return dbMapsets;
    }


    public void setDbMapsets(boolean dbMapsets) {
        this.dbMapsets = dbMapsets;
    }


    public boolean isDbLinkageGroup() {
        return dbLinkageGroup;
    }


    public void setDbLinkageGroup(boolean dbLinkageGroup) {
        this.dbLinkageGroup = dbLinkageGroup;
    }


    public List<LinkageGroupRecord> getLinkageGroupList() {
        return linkageGroupList;
    }


    public void setLinkageGroupList(List<LinkageGroupRecord> linkageGroupList) {

        backupLinkageGroupList.clear();
        backupLinkageGroupList.addAll(linkageGroupList);
        
        this.linkageGroupList = linkageGroupList;
    }


    public String getCurrentFiltersAsText() {
        return currentFiltersAsText;
    }


    public void setCurrentFiltersAsText(String currentFiltersAsText) {
        this.currentFiltersAsText = currentFiltersAsText;
    }


    public Integer getSizeLGList() {
        
        try {
        sizeLGList = vlinkageGroupSummaryEntityList.size();
        
        if(sizeLGList<1) sizeLGList = 0;
        
        }catch(NullPointerException npe) {
        sizeLGList = 0;
        }
        
        return sizeLGList;
    }


    public void setSizeLGList(Integer sizeLGList) {
        this.sizeLGList = sizeLGList;
    }

}
