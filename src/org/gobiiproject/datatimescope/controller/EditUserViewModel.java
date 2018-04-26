package org.gobiiproject.datatimescope.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.services.CommonInfoService;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.zkoss.bind.BindUtils;
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
import org.zkoss.zk.ui.event.Event;
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
		userAccount.changed(false);
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
						args.put("timescoperRecord", userAccount);
						BindUtils.postGlobalCommand(null, null, "refreshTimescoperRecord", args);

						}
						editUserWindow.detach();
					}
				}
			});

		} else editUserWindow.detach();
	}

	@Command("saveUserInfo")
	public void saveUser(){
		boolean successful = false;
		if(isCreateNew){
			System.out.println("creating new user...");
			successful = userInfoService.createNewUser(userAccount);
			
			BindUtils.postGlobalCommand(null, null, "retrieveUserList", null);
		}else{
			if(userAccount.changed()){

				//update Original User Values
				successful = userInfoService.updateUser(userAccount);

				Map<String,Object> args = new HashMap<String,Object>();
				args.put("timescoperRecord", userAccount);
				BindUtils.postGlobalCommand(null, null, "refreshTimescoperRecord", args);
			} else Messagebox.show("There are no changes yet.", "There's nothing to save", Messagebox.OK, Messagebox.INFORMATION);

		}

		if(successful){
			editUserWindow.detach();
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

	public String getPageCaption() {
		return pageCaption;
	}

	public void setPageCaption(String pageCaption) {
		this.pageCaption = pageCaption;
	}
}
