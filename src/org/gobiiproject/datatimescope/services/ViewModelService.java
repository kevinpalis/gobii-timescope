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
import org.gobiiproject.datatimescope.db.generated.tables.records.ReferenceRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VendorProtocolRecord;
import org.gobiiproject.datatimescope.entity.DatasetEntity;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.LinkageGroupEntity;
import org.gobiiproject.datatimescope.entity.LinkageGroupSummaryEntity;
import org.gobiiproject.datatimescope.entity.MarkerDetailDatasetEntity;
import org.gobiiproject.datatimescope.entity.MarkerDetailLinkageGroupEntity;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.VLinkageGroupSummaryEntity;
import org.gobiiproject.datatimescope.entity.VMarkerSummaryEntity;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;


//TODO:  This is a God service -- refactor!!!
public interface ViewModelService {


	/** create new user 
	 * @return 
	 * @throws TimescopeException **/
	public boolean createNewUser(TimescoperEntity userAccount) throws TimescopeException;
	
	public List<TimescoperEntity> getAllOtherUsers(String username) throws TimescopeException;

	public boolean connectToDB(String userName, String password, ServerInfo serverInfo) throws TimescopeException;

	public boolean updateUser(TimescoperEntity userAccount) throws TimescopeException;

	public boolean deleteUser(TimescoperEntity userAccount) throws TimescopeException;

	public boolean deleteUsers(List<TimescoperEntity> selectedUsersList) throws TimescopeException;

	public List<VDatasetSummaryEntity> getAllDatasets(DatasetSummaryEntity datasetSummaryEntity) throws TimescopeException;

	public DataSetDeleteInfo deleteDataset(VDatasetSummaryEntity vDatasetSummaryRecord, List<DatasetSummaryEntity> datasetSummary, DatasetSummaryEntity datasetSummaryEntity) throws TimescopeException;

	public DataSetDeleteInfo deleteDatasets(List<VDatasetSummaryEntity> selectedDsList, List<DatasetSummaryEntity> datasetSummary, DatasetSummaryEntity datasetSummaryEntity, boolean removeCannotDelete, List<String> deleteList) throws TimescopeException;

	public TimescoperRecord loginTimescoper(String username, String password) throws TimescopeException;
	
	public TimescoperEntity getUserInfo(String username) throws TimescopeException;

	public List<ContactRecord> getAllContacts() throws TimescopeException;

	public List<ContactRecord> getContactsByRoles(Integer[] role) throws TimescopeException;

	List<CvRecord> getCvTermsByGroupName(String groupName) throws TimescopeException;

	public List<VDatasetSummaryEntity> getAllDatasetsBasedOnQuery(DatasetEntity datasetEntity, DatasetSummaryEntity datasetSummaryEntity) throws TimescopeException;

	public List<VMarkerSummaryEntity> getAllMarkersBasedOnQuery(MarkerRecordEntity markerEntity, DatasetSummaryEntity markerSummaryEntity) throws TimescopeException;
	
    public List<VMarkerSummaryEntity> getAllMarkersBasedOnQueryViaView(MarkerRecordEntity markerEntity, DatasetSummaryEntity markerSummaryEntity) throws TimescopeException;

	public List<VMarkerSummaryEntity> getAllMarkers(List<DatasetSummaryEntity> markerSummary) throws TimescopeException;

	public boolean deleteMarker(VMarkerSummaryEntity vMarkerSummaryEntity, List<DatasetSummaryEntity> markerSummary) throws TimescopeException;

	public DeleteMarkersResult deleteMarkers(List<VMarkerSummaryEntity> selectedMarkerList, List<DatasetSummaryEntity> markerSummary) throws TimescopeException;

	public MarkersFilterResult filterUnusedMarkersInGroupOrDataset(List<VMarkerSummaryEntity> selectedMarkerList) throws TimescopeException;
	
	public List<PlatformRecord> getAllPlatforms() throws TimescopeException;

	public String getDatawarehouseVersion() throws TimescopeException;

	public List<VLinkageGroupSummaryEntity> getAllLinkageGroups(LinkageGroupSummaryEntity linkageGroupSummaryEntity);

	public List<VLinkageGroupSummaryEntity> getAllLinkageGroupsBasedOnQuery(LinkageGroupEntity linkageGroupEntity,
			LinkageGroupSummaryEntity linkageGroupSummaryEntity);

	public boolean deleteLinkageGroup(VLinkageGroupSummaryEntity vLinkageGroupSummaryEntity,
			List<LinkageGroupSummaryEntity> linkageGroupSummary, LinkageGroupSummaryEntity linkageGroupSummaryEntity);

	public boolean deleteLinkageGroups(List<VLinkageGroupSummaryEntity> selectedDsList,
			List<LinkageGroupSummaryEntity> linkageGroupSummary, LinkageGroupSummaryEntity linkageGroupSummaryEntity);

	public List<OrganizationRecord> getAllVendors() throws TimescopeException;

	public List<VendorProtocolRecord> getAllVendorProtocols() throws TimescopeException;

	public List<AnalysisRecord> getAllAnalyses() throws TimescopeException;

	public List<AnalysisRecord> getAllCallingAnalysis() throws TimescopeException;

	public List<ProjectRecord> getAllProjects() throws TimescopeException;

	public List<ExperimentRecord> getAllExperiments() throws TimescopeException;

	public List<MapsetRecord> getAllMapsets() throws TimescopeException;

	public List<LinkageGroupRecord> getAllLinkageGroups() throws TimescopeException;

	public List<DatasetRecord> getAllDatasets() throws TimescopeException;

	public List<LinkageGroupRecord> getLinkageGroupsAssociatedToMarkerId(Integer markerId) throws TimescopeException;

	public List<DatasetRecord> getDatasetAssociatedToMarkerId(Integer markerId) throws TimescopeException;

	public List<MarkerGroupRecord> getMarkerGroupsAssociatedToMarkerId(Integer markerId) throws TimescopeException;

	public List<VendorProtocolRecord> getVendorProtocolByPlatformId(List<PlatformRecord> list) throws TimescopeException;

//	public List<MapsetRecord> getMapsetsByPlatformTypeId(List<PlatformRecord> platformList);

	public List<LinkageGroupRecord> getLinkageGroupByMapsetId(List<MapsetRecord> mapsetList) throws TimescopeException;

	public List<ProjectRecord> getProjectsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList) throws TimescopeException;

	public List<ProjectRecord> getProjectsByPlatformID(List<PlatformRecord> platformList) throws TimescopeException;

	public List<ExperimentRecord> getExperimentsByProjectID(List<ProjectRecord> projectList) throws TimescopeException;

	public List<ExperimentRecord> getExperimentsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList) throws TimescopeException;

	public List<ExperimentRecord> getExperimentsByPlatformID(List<PlatformRecord> platformList) throws TimescopeException;

	public List<DatasetRecord> getDatasetsByProjectID(List<ProjectRecord> projectList) throws TimescopeException;

	public List<DatasetRecord> getDatasetsByExperimentIDandAnalysisId(List<ExperimentRecord> experimentList, List<AnalysisRecord> list) throws TimescopeException;

	public List<DatasetRecord> getDatasetsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList) throws TimescopeException;

	public List<DatasetRecord> getDatasetsByPlatformID(List<PlatformRecord> platformList) throws TimescopeException;

    public List<ReferenceRecord> getAllReferences() throws TimescopeException;

    public List<MapsetRecord> getAllMapsetsByReferenceId(List<ReferenceRecord> referenceList) throws TimescopeException;

    public List<LinkageGroupRecord> getAllLinkageGroupsByReferenceId(List<ReferenceRecord> referenceList) throws TimescopeException;

    public List<DatasetRecord> getDatasetsByExperimentID(List<ExperimentRecord> experimentList) throws TimescopeException;

    public List<DatasetRecord> getDatasetsByPlatformIDandAnalysisID(List<PlatformRecord> platformList,
            List<AnalysisRecord> analysesList) throws TimescopeException;

    public List<DatasetRecord> getAllDatasetsByAnalysisID(List<AnalysisRecord> analysesList) throws TimescopeException;

    public List<DatasetRecord> getDatasetsByVendorProtocolIDandAnalysisID(List<VendorProtocolRecord> vendorProtocolList,
            List<AnalysisRecord> analysesList) throws TimescopeException;

    public List<DatasetRecord> getDatasetsByProjectIDandAnalysisID(List<ProjectRecord> projectList,
            List<AnalysisRecord> analysesList) throws TimescopeException;

    public List<MarkerDetailDatasetEntity> getMarkerAssociatedDetailsForEachDataset(
            List<DatasetRecord> markerDetailDatasetList) throws TimescopeException;

    public List<MarkerDetailLinkageGroupEntity> getAssociatedDetailsForEachLinkageGroup(
            List<LinkageGroupRecord> markerDetailLinkageGroupList) throws TimescopeException;


}
