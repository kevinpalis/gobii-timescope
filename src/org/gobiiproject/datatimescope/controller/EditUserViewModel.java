package org.gobiiproject.datatimescope.controller;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.Utils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class EditUserViewModel {
	//UI component

	@Wire("#editUserWindows")
	Window editUserWindow;


	boolean isCreateNew = false, isSuperAdmin=false, isEditingSelf=false, isSuperAdminEditingOthers=false;

	private String pageCaption, userName, password;

	private TimescoperEntity userAccount;

	private ListModelList<String> roleList;
	
	private ListModelList<TimescoperEntity> allUser;

	ViewModelService userInfoService;

	@Init
	public void init(@ExecutionArgParam("editedUser") TimescoperEntity user) {
		userAccount = user;
		userAccount.changed(false);
		setRoleList(new ListModelList<String>(Utils.getRoleList()));
		userInfoService = new ViewModelServiceImpl();
		UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");

		allUser = new ListModelList<TimescoperEntity>(userInfoService.getAllOtherUsers(userAccount.getUsername()), true);
		
		//Figure out if this window was called to edit a user or to create one
		if(user.getUsername()!=null && !user.getUsername().isEmpty() ){
			userName = userAccount.getUsername();
			setPageCaption("Edit User Information \""+ userName + "\"");
			password = "dummypassword";
			
			if(cre.getAccount().equalsIgnoreCase(user.getUsername())){
				isEditingSelf=true;
				
			}
		}
		else {
			setPageCaption("Create New User");
			isCreateNew = true;
			password = "";
		}

		if(cre.getRole() == 1){
			if(!isEditingSelf) isSuperAdminEditingOthers = true;
			isSuperAdmin=true;
		}
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command("refreshUserBeforeClose")
	public void refreshUserBeforeClose(){
		if(userAccount.changed()){

			Messagebox.show("Are you sure you want to exit without saving your changes?", 
					"Question", Messagebox.YES | Messagebox.CANCEL,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener(){
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if(Messagebox.ON_YES.equals(event.getName())){
						//YES is clicked

						if(!isCreateNew){

							Map<String,Object> args = new HashMap<String,Object>();
							args.put("timescoperRecordEntity", userAccount);
							BindUtils.postGlobalCommand(null, null, "refreshTimescoperRecord", args);

						}
						editUserWindow.detach();
					}
				}
			});

		} else editUserWindow.detach();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command("saveUserInfo")
	public void saveUser(){
		boolean successful = false;

		if(validate(userAccount)){ // check if a role is selected


			if(isCreateNew){
				successful = userInfoService.createNewUser(userAccount);

				BindUtils.postGlobalCommand(null, null, "retrieveUserList", null);
			}else{
				if(userAccount.changed()){
					

					if( userAccount.getRole()>1 && isSuperAdmin && isEditingSelf){ // if user is trying to downgrade role, warn against that
					    
						Messagebox.show("You will no longer be able to view and edit other user profiles.\n\n Are you sure you want to remove your Super Administrator privileges?", 
								"Question", Messagebox.YES | Messagebox.CANCEL,
								Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener(){
							@Override
							public void onEvent(Event event) throws Exception {
								// TODO Auto-generated method stub
								if(Messagebox.ON_YES.equals(event.getName())){
									//YES is clicked
									
									if(updateUser()){
										UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
										cre.setRole(userAccount.getRole());
										
										editUserWindow.detach();

										BindUtils.postGlobalCommand(null, null, "refreshUserWindow", null);
									}
								}
							}
						});
						
					}else successful = updateUser(); //go straight to update
					
				} else Messagebox.show("There are no changes yet.", "There's nothing to save", Messagebox.OK, Messagebox.INFORMATION);

			}

			if(successful){
				editUserWindow.detach();
			}
		}
	}

	private boolean updateUser() {
		// TODO Auto-generated method stub
		boolean successful = false;
		//update Original User Values
		successful = userInfoService.updateUser(userAccount);

		Map<String,Object> args = new HashMap<String,Object>();
		args.put("timescoperRecordEntity", userAccount);
		BindUtils.postGlobalCommand(null, null, "refreshTimescoperRecord", args);
		
		return successful;
	}

	private boolean validate(TimescoperEntity userAccount2) {
		// TODO Auto-generated method stub
		boolean didItPass = false;
		
		if( userAccount.getRole()==null || userAccount.getRole()==0){
			Messagebox.show("Please specify the user's role.", "There's no role selected", Messagebox.OK, Messagebox.INFORMATION);
		}
		else if( userAccount.getRole()==1 && !isSuperAdmin){
			Messagebox.show("You cannot upgrade yourself into a Super Admin.\n\nPlease ask a Super Administrator to do that.", "Action not allowed.", Messagebox.OK, Messagebox.ERROR);
			
		}
		else if(userAccount.getUsername() == null || userAccount.getUsername().isEmpty()){
			Messagebox.show("Please specify a username.", "Please do not ignore warnings.", Messagebox.OK, Messagebox.INFORMATION);
		}
		else if(userAccount.getEmail() == null || userAccount.getEmail().isEmpty()){
			Messagebox.show("Please specify an email.", "Email cannot be empty.", Messagebox.OK, Messagebox.INFORMATION);
		}
		else if(userAccount.getFirstname() == null || userAccount.getFirstname().isEmpty()){
			Messagebox.show("Please specify the user's first name.", "Please do not ignore warnings.", Messagebox.OK, Messagebox.INFORMATION);
		}
		else if(userAccount.getLastname() == null || userAccount.getLastname().isEmpty()){
			Messagebox.show("Please specify the user's last name.", "Please do not ignore warnings.", Messagebox.OK, Messagebox.INFORMATION);
		}
		else if(userAccount.getPassword() == null || userAccount.getPassword().isEmpty()){
			Messagebox.show("Please specify a password.", "Password cannot be empty.", Messagebox.OK, Messagebox.INFORMATION);
		}
		else if(checkForDuplicateUsername(userAccount.getUsername())){
			Messagebox.show("That username is already taken.", "Invalid username.", Messagebox.OK, Messagebox.INFORMATION);
		}
		else if(checkForDuplicateEmail(userAccount.getEmail())){
			Messagebox.show("That email is already taken.", "Invalid email.", Messagebox.OK, Messagebox.INFORMATION);
		}
		else{
		
			didItPass = true;
		}

		return didItPass;
	}

	private boolean checkForDuplicateEmail(String email) {
		// TODO Auto-generated method stub
		boolean foundDuplicate = false;

		ListIterator<TimescoperEntity> it = allUser.listIterator();
		while (it.hasNext()) {
			if (it.next().getEmail().matches(email)) {
				foundDuplicate = true;
				break;
			}
		}
		
		return foundDuplicate;
	}

	private boolean checkForDuplicateUsername(String username2) {
		// TODO Auto-generated method stub

		boolean foundDuplicate = false;

		ListIterator<TimescoperEntity> it = allUser.listIterator();
		while (it.hasNext()) {
			if (it.next().getUsername().matches(username2)) {
				foundDuplicate = true;
				break;
			}
		}
		
		return foundDuplicate;
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

	public String getPageCaption() {
		return pageCaption;
	}

	public void setPageCaption(String pageCaption) {
		this.pageCaption = pageCaption;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public boolean isSuperAdminEditingOthers() {
		return isSuperAdminEditingOthers;
	}

	public void setSuperAdminEditingOthers(boolean isSuperAdminEditingOthers) {
		this.isSuperAdminEditingOthers = isSuperAdminEditingOthers;
	}

	public boolean isCreateNew() {
		return isCreateNew;
	}

	public void setCreateNew(boolean isCreateNew) {
		this.isCreateNew = isCreateNew;
	}
}
