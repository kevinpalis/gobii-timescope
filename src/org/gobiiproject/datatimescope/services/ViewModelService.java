/* 
	
*/
package org.gobiiproject.datatimescope.services;

import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VDatasetSummaryRecord;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.jooq.Record;
import org.jooq.Result;
import org.zkoss.zul.ListModelList;

public interface ViewModelService {


	/** create new user 
	 * @return **/
	public boolean createNewUser(TimescoperRecord userAccount);
	
	public List<TimescoperRecord> getAllOtherUsers(String username);

	public boolean connectToDB(String userName, String password, ServerInfo serverInfo);

	public boolean updateUser(TimescoperRecord userAccount);

	public boolean deleteUser(TimescoperRecord userAccount);

	public boolean deleteUsers(ListModelList<TimescoperRecord> selectedUsersList);

	public List<VDatasetSummaryRecord> getAllDatasets();

	public boolean deleteDataset(VDatasetSummaryRecord vDatasetSummaryRecord);

	public boolean deleteDatasets(List<VDatasetSummaryRecord> selectedDsList);

	public TimescoperRecord loginTimescoper(String username, String password);
	
	public TimescoperRecord getUserInfo(String username);

	public List<ContactRecord> getAllContacts();

	public List<ContactRecord> getContactsByRoles(Integer[] role);

	List<CvRecord> getCvTermsByGroupName(String groupName);
}
