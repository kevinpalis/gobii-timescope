/* 
	
*/
package org.gobiiproject.datatimescope.services;

public interface AuthenticationService {

	/**login with account and password**/
	public boolean login(String account, String password);
	
	/**logout current user**/
	public void logout();
	
	/**get current user credential**/
	public UserCredential getUserCredential();
	
}
