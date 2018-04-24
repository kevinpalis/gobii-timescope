package org.gobiiproject.datatimescope.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.services.CommonInfoService;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
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
	
	private boolean cbAllUsers;

	private TimescoperRecord userAccount;

	private ListModelList<String> roleList;
	private ListModelList<TimescoperRecord> userlist, selectedUsersList;

	@AfterCompose
	public void afterCompose() {
		viewModelService = new ViewModelServiceImpl();
		
		setCbAllUsers(false);

		userAccount = (TimescoperRecord) Sessions.getCurrent().getAttribute("userInfo");
		roleList= new ListModelList<String>(CommonInfoService.getRoleList());

		selectedUsersList = new ListModelList<TimescoperRecord>();
		
		userlist = new ListModelList<TimescoperRecord>(viewModelService.getAllUsers(), true);
		
		userlist.setMultiple(true);

	}

	private ListModelList<TimescoperRecord> getDummyUsers() {
		// TODO Auto-generated method stub

		ListModelList<TimescoperRecord> users = new ListModelList<TimescoperRecord>();

		int numOfUsers = 20;
		int i = 0;
		int randomNum;
		Random r = new Random();

		TimescoperRecord newUser = new TimescoperRecord();
		while (i<numOfUsers){
			randomNum = r.nextInt(3);
			newUser = new TimescoperRecord(randomNum, "Dummy", "data", "User"+ Integer.toString(i), "Password1!", "em@il.com", randomNum);
			users.add(newUser);
			i++;
		}

		return users;
	}

	@Command
	public void editProfile(){

		//create a window programmatically and use it as a modal dialog.

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("editedUser", userAccount);

		Window window = (Window)Executions.createComponents(
				"/editUser.zul", null, args);
		window.doModal();
	}


	@Command("deleteUsers")
	public void deleteUsers(){

		if(selectedUsersList.isEmpty()){ //Nothing is selected
			Messagebox.show("There are no users selected", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		else{
			StringBuilder sb = new StringBuilder();

			for(TimescoperRecord u: selectedUsersList){
				sb.append("\n"+u.getUsername()+"\" "+u.getLastname() +", "+ u.getFirstname());
			}
			Messagebox.show("Are you sure you want to delete the following users?"+sb.toString(), "Confirm Delete", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
		}

	}

	@Command("doSelectAll")
	@NotifyChange("users")
	public void doSelectAll(){
		ListModelList<TimescoperRecord> users = getUsers();

		selectedUsersList.clear(); //clear the list first and then just add if there are any selected

		for(TimescoperRecord u: users){
			if (isCbAllUsers()) selectedUsersList.add(u);
		}
	}


	@Command("modifyUser")
	@NotifyChange({"users"})
	public void modifyUser(@BindingParam("editedUser") TimescoperRecord user){

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("editedUser", user);

		Window window = (Window)Executions.createComponents(
				"/editUser.zul", null, args);
		window.doModal();

	}


	@Command("createUser")
	@NotifyChange({"users"})
	public void createUser(){
		TimescoperRecord emptyUser = new TimescoperRecord();

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("editedUser", emptyUser);

		Window window = (Window)Executions.createComponents(
				"/editUser.zul", null, args);
		window.doModal();

	}


	@Command("updateSelectUser")
	@NotifyChange({"cbAllUsers", "selectedUsersList"})
	public void updateSelectUser(@BindingParam("userChecked") TimescoperRecord user, @BindingParam("isChecked") Boolean isChecked){
		if(isChecked){
			selectedUsersList.add(user);
		}else{
			setCbAllUsers(false);

			ListIterator<TimescoperRecord> it = selectedUsersList.listIterator();
			while (it.hasNext()) {
				if (it.next().getUsername().matches(user.getUsername())) {
					it.remove();
					break;
				}
			}
		}
	}

	public TimescoperRecord getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(TimescoperRecord userAccount) {
		this.userAccount = userAccount;
	}

	public ListModelList<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(ListModelList<String> roleList) {
		this.roleList = roleList;
	}

	public ListModelList<TimescoperRecord> getUsers() {
		return userlist;
	}

	public void setUsers(ListModelList<TimescoperRecord> users) {
		this.userlist = users;
	}

	public boolean isCbAllUsers() {
		return cbAllUsers;
	}

	public void setCbAllUsers(boolean selectedAllUsers) {
		this.cbAllUsers = selectedAllUsers;
	}

	public ListModelList<TimescoperRecord> getSelectedUsersList() {
		return selectedUsersList;
	}

	public void setSelectedUsersList(ListModelList<TimescoperRecord> selectedUsersList) {
		this.selectedUsersList = selectedUsersList;
	}
}
