package org.gobiiproject.datatimescope.controller;

import java.util.List;
import java.util.ListIterator;

import org.gobiiproject.datatimescope.entity.User;
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
	boolean isCreateNew = false;
	
	private String pageCaption;
	
	private User userAccount;

	private ListModelList<String> roleList;
	
	ViewModelService userInfoService;
	
	@Init
	public void init(@ExecutionArgParam("editedUser") User user) {
		userAccount = user;
		setRoleList(new ListModelList<String>(CommonInfoService.getRoleList()));
		userInfoService = new ViewModelServiceImpl();
		
		//Figure out if this window was called to edit a user or to create one
		if(user.getUserName()!=null) setPageCaption("Edit User Information \""+ userAccount.getUserName() + "\"");
		else{
			setPageCaption("Create New User");
			isCreateNew = true;
		}
	}

	@Command("saveUserInfo")
	public void saveUser(){
		if(isCreateNew){
			System.out.println("creating new user...");
			userInfoService.createNewUser(userAccount);
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

	public String getPageCaption() {
		return pageCaption;
	}

	public void setPageCaption(String pageCaption) {
		this.pageCaption = pageCaption;
	}
}
