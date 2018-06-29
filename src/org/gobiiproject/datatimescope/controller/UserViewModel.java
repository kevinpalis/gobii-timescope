package org.gobiiproject.datatimescope.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.services.AuthenticationService;
import org.gobiiproject.datatimescope.services.AuthenticationServiceChapter3Impl;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.Utils;
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

public class UserViewModel {
	//UI component
	ViewModelService viewModelService;

	private boolean cbAllUsers, isAllCbSelected=false, superUser=false;

	private TimescoperEntity userAccount;

	private ListModelList<String> roleList;
	private ListModelList<TimescoperEntity> userlist, selectedUsersList;

	@AfterCompose
	public void afterCompose() {
		viewModelService = new ViewModelServiceImpl();

		setCbAllUsers(false);
		UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");

		String accountUsername = cre.getAccount();

		userAccount = viewModelService.getUserInfo(accountUsername);

		roleList= new ListModelList<String>(Utils.getRoleList());

		selectedUsersList = new ListModelList<TimescoperEntity>();

		userlist = new ListModelList<TimescoperEntity>(viewModelService.getAllOtherUsers(accountUsername), true);

		userlist.setMultiple(true);
		
		if(userAccount.getRolename().contains("Super")) superUser=true;

	}


	@NotifyChange("userAccount")
	@Command("userProfile")
	public void userProfile(){


	}

	
	@Command("signout")
	public void signout() {
		
		AuthenticationService authService =new AuthenticationServiceChapter3Impl();
		authService.logout();

		Executions.sendRedirect("/index.zul");
	}
	
	@Command
	public void editProfile(){

		//create a window programmatically and use it as a modal dialog.

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("editedUser", userAccount);

		Window window = (Window)Executions.createComponents(
				"/editUser.zul", null, args);
		window.doModal();
		window.setVisible(true);


	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command("deleteUsers")
	public void deleteUsers(){

		if(selectedUsersList.isEmpty()){ //Nothing is selected
			Messagebox.show("There are no users selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		else{
			StringBuilder sb = new StringBuilder();

			for(TimescoperEntity u: selectedUsersList){
				sb.append("\n"+u.getUsername()+"\" "+u.getLastname() +", "+ u.getFirstname());
			}


			Messagebox.show("Are you sure you want to delete the following users?"+sb.toString(), 
					"Confirm Delete", Messagebox.YES | Messagebox.CANCEL,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener(){
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if(Messagebox.ON_YES.equals(event.getName())){
						//YES is clicked
						boolean successful;

						if(selectedUsersList.getSize() == 1){  // just one user is selected
							successful = viewModelService.deleteUser(selectedUsersList.get(0));
						}else{
							//bulk delete
							successful = viewModelService.deleteUsers(selectedUsersList);
						}

						if(successful) BindUtils.postGlobalCommand(null, null, "retrieveUserList", null);

					}
				}
			});

		}

	}

	@Command("doSelectAll")
	@NotifyChange({"userlist", "allCbSelected"})
	public void doSelectAll(){
		ListModelList<TimescoperEntity> users = getUsers();

		selectedUsersList.clear(); //clear the list first and then just add if there are any selected

		setAllCbSelected(isCbAllUsers());

		if (isCbAllUsers()) {
			for(TimescoperEntity u: users){
				selectedUsersList.add(u);
			}
		}
	}


	@Command("modifyUser")
	@NotifyChange({"userlist"})
	public void modifyUser(@BindingParam("editedUser") TimescoperEntity user){

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("editedUser", user);

		Window window = (Window)Executions.createComponents(
				"/editUser.zul", null, args);
		window.doModal();
		
			
	}


	@Command("createUser")
	public void createUser(){
		TimescoperEntity emptyUser = new TimescoperEntity();
		emptyUser.setRole(0);
		emptyUser.attach(userAccount.configuration());
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("editedUser", emptyUser);

		Window window = (Window)Executions.createComponents(
				"/editUser.zul", null, args);
		window.doModal();

	}


	@GlobalCommand("refreshTimescoperRecord")
	@NotifyChange({"userAccount", "userlist", "users", "userRole" })
	public void refreshTimescoperRecord(@BindingParam("timescoperRecordEntity")TimescoperEntity record){
		//...
		record.refresh();

	}


	@GlobalCommand("retrieveUserList")
	@NotifyChange({"userlist", "users", "selectedUsersList", "allCbSelected", "cbAllUsers"})
	public void retrieveUserList(){
		//...

		UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");

		String accountUsername = cre.getAccount();

		userAccount = viewModelService.getUserInfo(accountUsername);
		setUsers(new ListModelList<TimescoperEntity>(viewModelService.getAllOtherUsers(accountUsername), true));


		userlist.setMultiple(true);

		selectedUsersList.clear();

		setAllCbSelected(false);
		setCbAllUsers(false);

	}

	@Command("updateSelectUser")
	@NotifyChange({"cbAllUsers", "selectedUsersList"})
	public void updateSelectUser(@BindingParam("userChecked") TimescoperEntity user, @BindingParam("isChecked") Boolean isChecked){
		if(isChecked){
			selectedUsersList.add(user);
		}else{
			setCbAllUsers(false);

			ListIterator<TimescoperEntity> it = selectedUsersList.listIterator();
			while (it.hasNext()) {
				if (it.next().getUsername().matches(user.getUsername())) {
					it.remove();
					break;
				}
			}
		}
	}

	public TimescoperEntity getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(TimescoperEntity userAccount) {
		this.userAccount = userAccount;
	}

	public ListModelList<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(ListModelList<String> roleList) {
		this.roleList = roleList;
	}

	public ListModelList<TimescoperEntity> getUsers() {
		return userlist;
	}

	public void setUsers(ListModelList<TimescoperEntity> users) {
		this.userlist = users;
	}

	public boolean isCbAllUsers() {
		return cbAllUsers;
	}

	public void setCbAllUsers(boolean selectedAllUsers) {
		this.cbAllUsers = selectedAllUsers;
	}

	public ListModelList<TimescoperEntity> getSelectedUsersList() {
		return selectedUsersList;
	}

	public void setSelectedUsersList(ListModelList<TimescoperEntity> selectedUsersList) {
		this.selectedUsersList = selectedUsersList;
	}

	public boolean isAllCbSelected() {
		return isAllCbSelected;
	}

	public void setAllCbSelected(boolean isAllCbSelected) {
		this.isAllCbSelected = isAllCbSelected;
	}


	public boolean isSuperUser() {
		return superUser;
	}


	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}
	
}
