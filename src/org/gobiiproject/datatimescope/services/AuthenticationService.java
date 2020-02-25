/* 
	
*/
package org.gobiiproject.datatimescope.services;

import org.gobiiproject.datatimescope.exceptions.TimescopeException;

public interface AuthenticationService {

	/**login with account and password
	 * @throws TimescopeException **/
	public boolean login(String account, String password) throws TimescopeException;
	
	/**logout current user**/
	public void logout();
	
	/**get current user credential**/
	public UserCredential getUserCredential();
	
}
