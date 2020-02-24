package org.gobiiproject.datatimescope.controller;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.gobiiproject.datatimescope.services.AuthenticationService;
import org.gobiiproject.datatimescope.services.AuthenticationServiceImpl;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.Utils;
import org.gobiiproject.datatimescope.utils.WebappUtil;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.apache.log4j.Logger;

public class UserViewModel {
	final static Logger log = Logger.getLogger(UserViewModel.class.getName());
	//UI component
	ViewModelService viewModelService;

	private boolean cbAllUsers, isAllCbSelected=false, superUser=false;

	private TimescoperEntity userAccount;

	private ListModelList<String> roleList;

	private ListModelList<TimescoperEntity> userlist, selectedUsersList;

	@AfterCompose
	public void afterCompose() {
		try {
			viewModelService = new ViewModelServiceImpl();

			setCbAllUsers(false);
			UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");

			String accountUsername = cre.getAccount();

			userAccount = viewModelService.getUserInfo(accountUsername);

			//log.debug(String.format("User account is role: %s", userAccount.getRolename()) );

			roleList= new ListModelList<String>(Utils.getRoleList());

			selectedUsersList = new ListModelList<TimescoperEntity>();


			userlist = new ListModelList<TimescoperEntity>(viewModelService.getAllOtherUsers(accountUsername), true);


			userlist.setMultiple(true);

			if(userAccount.getRolename().contains("Super")) superUser=true;
			//log.debug(String.format("User account is superUser: %b", superUser));

		} catch (TimescopeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
			return;
		}
	}


	@NotifyChange("userAccount")
	@Command("userProfile")
	public void userProfile(){


	}


	@Command("signout")
	public void signout() {

		AuthenticationService authService =new AuthenticationServiceImpl();
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
				sb.append("\n"+u.getLastname() +", "+ u.getFirstname()+"  ("+u.getUsername()+") ");
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
						String term = "user";
						try {
							if(selectedUsersList.getSize() == 1){  // just one user is selected
								successful = viewModelService.deleteUser(selectedUsersList.get(0));

							}else{
								//bulk delete
								successful = viewModelService.deleteUsers(selectedUsersList);
								term = "users";
							}

							if(successful) {
								Messagebox.show(
										String.format("Successfully deleted %s!", term),
										"",
										Messagebox.OK,
										Messagebox.INFORMATION
										);
								BindUtils.postGlobalCommand(null, null, "retrieveUserList", null); 
							}
						} catch (TimescopeException e) {
							WebappUtil.showErrorDialog(e);
						}

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
		emptyUser.attach(userAccount.configuration());
		emptyUser.setUsername("");
		emptyUser.setRole(0);
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
		try {
			UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");

			String accountUsername = cre.getAccount();

			userAccount = viewModelService.getUserInfo(accountUsername);

			setUsers(new ListModelList<TimescoperEntity>(viewModelService.getAllOtherUsers(accountUsername), true));

			userlist.setMultiple(true);

			selectedUsersList.clear();

			setAllCbSelected(false);
			setCbAllUsers(false);
		} catch (TimescopeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
			return;
		}
	}

	@GlobalCommand("refreshUserWindow")
	@NotifyChange({"userlist", "users", "selectedUsersList", "allCbSelected", "cbAllUsers", "superUser"})
	public void refreshUserWindow(){
		setCbAllUsers(false);
		UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");

		String accountUsername = cre.getAccount();

		try {
			userAccount = viewModelService.getUserInfo(accountUsername);
		} catch (TimescopeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
			return;
		}

		roleList= new ListModelList<String>(Utils.getRoleList());

		selectedUsersList = new ListModelList<TimescoperEntity>();

		try {
			userlist = new ListModelList<TimescoperEntity>(viewModelService.getAllOtherUsers(accountUsername), true);
		} catch (TimescopeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WebappUtil.showErrorDialog(e);
			return;
		}

		userlist.setMultiple(true);

		if(userAccount.getRolename().contains("Super")) superUser=true;
		else  superUser=false;


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
