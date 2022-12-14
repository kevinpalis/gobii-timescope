/* 
	
*/
package org.gobiiproject.datatimescope.services;

import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.AnalysisRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ExperimentRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MapsetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.OrganizationRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VDatasetSummaryRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VendorProtocolRecord;
import org.gobiiproject.datatimescope.entity.DatasetEntity;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.LinkageGroupEntity;
import org.gobiiproject.datatimescope.entity.LinkageGroupSummaryEntity;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.VLinkageGroupSummaryEntity;
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

	public List<VLinkageGroupSummaryEntity> getAllLinkageGroups(LinkageGroupSummaryEntity linkageGroupSummaryEntity);

	public List<VLinkageGroupSummaryEntity> getAllLinkageGroupsBasedOnQuery(LinkageGroupEntity linkageGroupEntity,
			LinkageGroupSummaryEntity linkageGroupSummaryEntity);

	public boolean deleteLinkageGroup(VLinkageGroupSummaryEntity vLinkageGroupSummaryEntity,
			List<LinkageGroupSummaryEntity> linkageGroupSummary, LinkageGroupSummaryEntity linkageGroupSummaryEntity);

	public boolean deleteLinkageGroups(List<VLinkageGroupSummaryEntity> selectedDsList,
			List<LinkageGroupSummaryEntity> linkageGroupSummary, LinkageGroupSummaryEntity linkageGroupSummaryEntity);

	public List<OrganizationRecord> getAllVendors();

	public List<VendorProtocolRecord> getAllVendorProtocols();

	public List<AnalysisRecord> getAllAnalyses();

	public List<AnalysisRecord> getAllCallingAnalysis();

	public List<ProjectRecord> getAllProjects();

	public List<ExperimentRecord> getAllExperiments();

	public List<MapsetRecord> getAllMapsets();

	public List<LinkageGroupRecord> getAllLinkageGroups();

	public List<DatasetRecord> getAllDatasets();

	public List<LinkageGroupRecord> getLinkageGroupsAssociatedToMarkerId(Integer markerId);

	public List<DatasetRecord> getDatasetAssociatedToMarkerId(Integer markerId);

	public List<MarkerGroupRecord> getMarkerGroupsAssociatedToMarkerId(Integer markerId);

	public List<VendorProtocolRecord> getVendorProtocolByPlatformId(List<PlatformRecord> list);

//	public List<MapsetRecord> getMapsetsByPlatformTypeId(List<PlatformRecord> platformList);

	public List<LinkageGroupRecord> getLinkageGroupByMapsetId(List<MapsetRecord> mapsetList);

	public List<ProjectRecord> getProjectsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList);

	public List<ProjectRecord> getProjectsByPlatformID(List<PlatformRecord> platformList);

	public List<ExperimentRecord> getExperimentsByProjectID(List<ProjectRecord> projectList);

	public List<ExperimentRecord> getExperimentsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList);

	public List<ExperimentRecord> getExperimentsByPlatformID(List<PlatformRecord> platformList);

	public List<DatasetRecord> getDatasetsByProjectID(List<ProjectRecord> projectList);

	public List<DatasetRecord> getDatasetsByExperimentID(List<ExperimentRecord> experimentList);

	public List<DatasetRecord> getDatasetsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList);

	public List<DatasetRecord> getDatasetsByPlatformID(List<PlatformRecord> platformList);


}
