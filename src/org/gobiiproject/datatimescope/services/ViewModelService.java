/* 
	
*/
package org.gobiiproject.datatimescope.services;

import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.zkoss.zul.ListModelList;

public interface ViewModelService {


	/** create new user 
	 * @return **/
	public boolean createNewUser(TimescoperRecord userAccount);
	
	/** find user by username **/
	public TimescoperRecord findUser(String account);
	

	/** find user by username **/
	public List<TimescoperRecord> getAllOtherUsers(String username);

	public boolean connectToDB(String userName, String password, ServerInfo serverInfo);

	public boolean updateUser(TimescoperRecord userAccount);

	public boolean deleteUser(TimescoperRecord userAccount);

	public boolean deleteUsers(ListModelList<TimescoperRecord> selectedUsersList);

}
