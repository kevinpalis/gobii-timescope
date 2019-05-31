/* 
	
*/
package org.gobiiproject.datatimescope.services;

import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VDatasetSummaryRecord;
import org.gobiiproject.datatimescope.entity.DatasetEntity;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.VMarkerSummaryEntity;
import org.jooq.Record;
import org.jooq.Result;
import org.zkoss.zul.ListModelList;

public interface ViewModelService {


	/** create new user 
	 * @return **/
	public boolean createNewUser(TimescoperEntity userAccount);
	
	public List<TimescoperEntity> getAllOtherUsers(String username);

	public boolean connectToDB(String userName, String password, ServerInfo serverInfo);

	public boolean updateUser(TimescoperEntity userAccount);

	public boolean deleteUser(TimescoperEntity userAccount);

	public boolean deleteUsers(ListModelList<TimescoperEntity> selectedUsersList);

	public List<VDatasetSummaryEntity> getAllDatasets(DatasetSummaryEntity datasetSummaryEntity);

	public boolean deleteDataset(VDatasetSummaryEntity vDatasetSummaryRecord, List<DatasetSummaryEntity> datasetSummary, DatasetSummaryEntity datasetSummaryEntity);

	public boolean deleteDatasets(List<VDatasetSummaryEntity> selectedDsList, List<DatasetSummaryEntity> datasetSummary, DatasetSummaryEntity datasetSummaryEntity);

	public TimescoperRecord loginTimescoper(String username, String password);
	
	public TimescoperEntity getUserInfo(String username);

	public List<ContactRecord> getAllContacts();

	public List<ContactRecord> getContactsByRoles(Integer[] role);

	List<CvRecord> getCvTermsByGroupName(String groupName);

	public List<VDatasetSummaryEntity> getAllDatasetsBasedOnQuery(DatasetEntity datasetEntity, DatasetSummaryEntity datasetSummaryEntity);

	public List<VMarkerSummaryEntity> getAllMarkersBasedOnQuery(MarkerRecordEntity markerEntity, DatasetSummaryEntity markerSummaryEntity);

	public List<VMarkerSummaryEntity> getAllMarkers(List<DatasetSummaryEntity> markerSummary);

	public boolean deleteMarker(VMarkerSummaryEntity vMarkerSummaryEntity, List<DatasetSummaryEntity> markerSummary, DatasetSummaryEntity markerSummaryEntity);

	public boolean deleteMarkers(List<VMarkerSummaryEntity> selectedMarkerList, List<DatasetSummaryEntity> markerSummary, DatasetSummaryEntity markerSummaryEntity);

	public List<PlatformRecord> getAllPlatforms();

	public String getDatawarehouseVersion();

}
