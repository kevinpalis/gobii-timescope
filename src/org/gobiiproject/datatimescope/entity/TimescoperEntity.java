package org.gobiiproject.datatimescope.entity;

import org.gobiiproject.datatimescope.db.generated.tables.Timescoper;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.utils.Utils;

@SuppressWarnings("serial")
public class TimescoperEntity extends TimescoperRecord {
	
	 public TimescoperEntity() {
		 new TimescoperRecord();
	    }

	    /**
	     * Create a detached, initialised TimescoperRecord
	     */
	    public TimescoperEntity(Integer timescoperId, String firstname, String lastname, String username, String password, String email, Integer role) {
	    	new TimescoperRecord();
	    	
	        set(0, timescoperId);
	        set(1, firstname);
	        set(2, lastname);
	        set(3, username);
	        set(4, password);
	        set(5, email);
	        set(6, role);
	    }
	/**
     * Getter for <code>public.timescoper.role</code>.
     */
    public String getRolename() {
    	
    	int role = this.getRole();
    	String rolename = Utils.getRoleList().get(role);
    			
        return rolename; 
  
    }
}
