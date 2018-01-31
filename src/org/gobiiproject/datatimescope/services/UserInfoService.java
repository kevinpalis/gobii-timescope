/* 
	
*/
package org.gobiiproject.datatimescope.services;

import org.gobiiproject.datatimescope.entity.User;

public interface UserInfoService {

	/** find user by account **/
	public User findUser(String account);
	
	/** update user **/
	public User updateUser(User user);
}
