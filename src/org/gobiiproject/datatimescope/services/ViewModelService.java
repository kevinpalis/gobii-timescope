/* 
	
*/
package org.gobiiproject.datatimescope.services;

import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.entity.ServerInfo;

public interface ViewModelService {


	/** create new user **/
	public void createNewUser(TimescoperRecord userAccount);
	
	/** find user by username **/
	public TimescoperRecord findUser(String account);
	

	/** find user by username **/
	public List<TimescoperRecord> getAllUsers();

	public boolean connectToDB(String userName, String password, ServerInfo serverInfo);

}
