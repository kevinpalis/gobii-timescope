/* 
	
*/
package org.gobiiproject.datatimescope.services;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserCredential implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String account;
	Integer role;
	
	Set<String> roles = new HashSet<String>();

	public UserCredential(String account, Integer integer) {
		this.account = account;
		this.role = integer;
	}

	public UserCredential() {
		this.account = "anonymous";
		this.role = 0;
	}
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}


}
