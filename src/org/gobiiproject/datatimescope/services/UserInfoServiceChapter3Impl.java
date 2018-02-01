/* 
	
*/
package org.gobiiproject.datatimescope.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gobiiproject.datatimescope.entity.User;

public class UserInfoServiceChapter3Impl implements UserInfoService,Serializable{
	private static final long serialVersionUID = 1L;
	
	static protected List<User> userList = new ArrayList<User>();  
	static{
		userList.add(new User("anonymous","lastname", "anonymous","password","a@your.com", 1));
		userList.add(new User("anonymous2","lastname", "anonymous2","password","b@your.com", 1));
		userList.add(new User("anonymous3","lastname", "anonymous3","password","c@your.com", 1));
	}
	
	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	public synchronized User findUser(String username){
		int s = userList.size();
		for(int i=0;i<s;i++){
			User u = userList.get(i);
			if(username.equals(u.getUserName())){
				return User.clone(u);
			}
		}
		return null;
	}
	
	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	public synchronized User updateUser(User user){
		int s = userList.size();
		for(int i=0;i<s;i++){
			User u = userList.get(i);
			if(user.getUserName().equals(u.getUserName())){
				userList.set(i,u = User.clone(user));
				return u;
			}
		}
		throw new RuntimeException("user not found "+user.getUserName());
	}
}
