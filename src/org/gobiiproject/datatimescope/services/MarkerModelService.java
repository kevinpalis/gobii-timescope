package org.gobiiproject.datatimescope.services;

import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerGroupRecord;
import org.gobiiproject.datatimescope.entity.DatasetEntity;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.VMarkerSummaryEntity;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;

public interface MarkerModelService {
	
	public List<VMarkerSummaryEntity> getAllMarkersBasedOnQuery(MarkerRecordEntity markerEntity, DatasetSummaryEntity markerSummaryEntity) throws TimescopeException;
	
    public List<VMarkerSummaryEntity> getAllMarkersBasedOnQueryViaView(MarkerRecordEntity markerEntity, DatasetSummaryEntity markerSummaryEntity) throws TimescopeException;

	public List<VMarkerSummaryEntity> getAllMarkers(List<DatasetSummaryEntity> markerSummary) throws TimescopeException;

	public boolean deleteMarker(VMarkerSummaryEntity vMarkerSummaryEntity, List<DatasetSummaryEntity> markerSummary) throws TimescopeException;

	public DeleteMarkersResult deleteMarkers(List<VMarkerSummaryEntity> selectedMarkerList, List<DatasetSummaryEntity> markerSummary) throws TimescopeException;

	public MarkersFilterResult filterUnusedMarkersInGroupOrDataset(List<VMarkerSummaryEntity> selectedMarkerList) throws TimescopeException;

	List<LinkageGroupRecord> getLinkageGroupsAssociatedToMarkerId(Integer markerId) throws TimescopeException;

	List<DatasetRecord> getDatasetAssociatedToMarkerId(Integer markerId) throws TimescopeException;

	List<MarkerGroupRecord> getMarkerGroupsAssociatedToMarkerId(Integer markerId) throws TimescopeException;
	
}
