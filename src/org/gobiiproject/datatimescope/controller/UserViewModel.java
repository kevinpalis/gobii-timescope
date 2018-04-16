package org.gobiiproject.datatimescope.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.gobiiproject.datatimescope.entity.User;
import org.gobiiproject.datatimescope.services.CommonInfoService;
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

	private boolean cbAllUsers;

	private User userAccount;

	private ListModelList<String> roleList;
	private ListModelList<User> userlist, selectedUsersList;

	@AfterCompose
	public void afterCompose() {

		setCbAllUsers(false);

		userAccount = new User("Angel Manica", "Raquel", "araquel", "Password1!", "angelmanica@gmail.com", 2);
		roleList= new ListModelList<String>(CommonInfoService.getRoleList());

		selectedUsersList = new ListModelList<User>();
		userlist = getDummyUsers();
		userlist.setMultiple(true);

	}

	private ListModelList<User> getDummyUsers() {
		// TODO Auto-generated method stub

		ListModelList<User> users = new ListModelList<User>();

		int numOfUsers = 20;
		int i = 0;
		int randomNum;
		Random r = new Random();
		
		User newUser = new User();
		while (i<numOfUsers){
			randomNum = r.nextInt(3);
			newUser = new User("User"+ Integer.toString(i), "Dummy", "data", "Password1!", "em@il.com", randomNum);
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

			for(User u: selectedUsersList){
				sb.append("\n"+u.getUserName()+"\" "+u.getLastName() +", "+ u.getFirstName());
			}
			Messagebox.show("Are you sure you want to delete the following users?"+sb.toString(), "Confirm Delete", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
		}

	}

	@Command("doSelectAll")
	@NotifyChange("users")
	public void doSelectAll(){
		ListModelList<User> users = getUsers();

		selectedUsersList.clear(); //clear the list first and then just add if there are any selected

		for(User u: users){
			u.setSelected(isCbAllUsers());
			if (isCbAllUsers()) selectedUsersList.add(u);
		}
	}


	@Command("modifyUser")
	@NotifyChange({"users"})
	public void modifyUser(@BindingParam("editedUser") User user){

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("editedUser", user);

		Window window = (Window)Executions.createComponents(
				"/editUser.zul", null, args);
		window.doModal();

	}
	

	@Command("createUser")
	@NotifyChange({"users"})
	public void createUser(){
		User emptyUser = new User();
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("editedUser", emptyUser);

		Window window = (Window)Executions.createComponents(
				"/editUser.zul", null, args);
		window.doModal();

	}


	@Command("updateSelectUser")
	@NotifyChange({"cbAllUsers", "selectedUsersList"})
	public void updateSelectUser(@BindingParam("userChecked") User user, @BindingParam("isChecked") Boolean isChecked){

		user.setSelected(isChecked);

		if(isChecked){
			selectedUsersList.add(user);
		}else{
			setCbAllUsers(false);

			ListIterator<User> it = selectedUsersList.listIterator();
			while (it.hasNext()) {
				if (it.next().getUserName().matches(user.getUserName())) {
					it.remove();
					break;
				}
			}
		}
	}

	public User getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(User userAccount) {
		this.userAccount = userAccount;
	}

	public ListModelList<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(ListModelList<String> roleList) {
		this.roleList = roleList;
	}

	public ListModelList<User> getUsers() {
		return userlist;
	}

	public void setUsers(ListModelList<User> users) {
		this.userlist = users;
	}

	public boolean isCbAllUsers() {
		return cbAllUsers;
	}

	public void setCbAllUsers(boolean selectedAllUsers) {
		this.cbAllUsers = selectedAllUsers;
	}

	public ListModelList<User> getSelectedUsersList() {
		return selectedUsersList;
	}

	public void setSelectedUsersList(ListModelList<User> selectedUsersList) {
		this.selectedUsersList = selectedUsersList;
	}
}
