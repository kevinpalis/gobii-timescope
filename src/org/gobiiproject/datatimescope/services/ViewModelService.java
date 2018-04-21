/* 
	
*/
package org.gobiiproject.datatimescope.services;

import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.entity.User;

public interface ViewModelService {


	/** create new user **/
	public void createNewUser(User userAccount);
	
	/** find user by username **/
	public User findUser(String account);
	
	/** update user **/
	public User updateUser(User user);

	public void connectToDB(String userName, String password, ServerInfo serverInfo);

}
