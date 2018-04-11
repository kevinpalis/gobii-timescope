package org.gobiiproject.datatimescope.controller;

import org.gobiiproject.datatimescope.entity.User;
import org.gobiiproject.datatimescope.services.CommonInfoService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

public class UserViewModel {
	
	private User userAccount;
	
	private ListModelList<String> roleList;
	private ListModelList<User> userlist;
	
	@Init
	    public void init() {
	        //initialization code
		  userAccount = new User("Angel Manica", "Raquel", "araquel", "Password1!", "angelmanica@gmail.com", 1);
		  roleList= new ListModelList<String>(CommonInfoService.getRoleList());
		  userlist = getDummyUsers();
		  userlist.setMultiple(true);
	    }
	 
	 private ListModelList<User> getDummyUsers() {
		// TODO Auto-generated method stub
		 
		 ListModelList<User> users = new ListModelList<User>();
		 
		 int numOfUsers = 20;
		 int i = 0;
		 
		 User newUser = new User();
		 while (i<numOfUsers){
			  newUser = new User("User"+ Integer.toString(i), "Dummy", "data", "Password1!", "em@il.com", 1);
			  users.add(newUser);
			  i++;
		 }
		 
		return users;
	}

	@Command
	 public void editProfile(){

	        //create a window programmatically and use it as a modal dialog.
	        Window window = (Window)Executions.createComponents(
	                "/editUser.zul", null, null);
	        window.doModal();
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
}
