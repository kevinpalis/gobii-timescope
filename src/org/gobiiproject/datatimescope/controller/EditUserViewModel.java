package org.gobiiproject.datatimescope.controller;

import java.util.List;
import java.util.ListIterator;

import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.services.CommonInfoService;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
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

public class EditUserViewModel {
	//UI component

	@Wire("#editUserWindows")
	Window editUserWindow;
	
	
	boolean isCreateNew = false;
	
	private String pageCaption, userName;
	
	private TimescoperRecord userAccount;

	private ListModelList<String> roleList;
	
	ViewModelService userInfoService;
	
	@Init
	public void init(@ExecutionArgParam("editedUser") TimescoperRecord user) {
		userAccount = user;
		setRoleList(new ListModelList<String>(CommonInfoService.getRoleList()));
		userInfoService = new ViewModelServiceImpl();
		
		//Figure out if this window was called to edit a user or to create one
		if(user.getUsername()!=null){
			userName = userAccount.getUsername();
			setPageCaption("Edit User Information \""+ userName + "\"");
		}
		else{
			setPageCaption("Create New User");
			isCreateNew = true;
		}
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@Command("saveUserInfo")
	public void saveUser(){
		boolean successful = false;
		if(isCreateNew){
			System.out.println("creating new user...");
			successful = userInfoService.createNewUser(userAccount);
		}else{

			successful = userInfoService.updateUser(userAccount);
		}
		
		if(successful) editUserWindow.detach();
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

	public String getPageCaption() {
		return pageCaption;
	}

	public void setPageCaption(String pageCaption) {
		this.pageCaption = pageCaption;
	}
}
