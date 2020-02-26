package org.gobiiproject.datatimescope.services.impls;

import static org.gobiiproject.datatimescope.db.generated.Tables.MARKER;
import static org.gobiiproject.datatimescope.db.generated.Tables.MARKER_LINKAGE_GROUP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.db.generated.tables.records.AnalysisRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ExperimentRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MapsetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VendorProtocolRecord;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.MarkerDeleteResultTableEntity;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.VMarkerSummaryEntity;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.gobiiproject.datatimescope.services.DeleteMarkersResult;
import org.gobiiproject.datatimescope.services.MarkerModelService;
import org.gobiiproject.datatimescope.services.MarkersFilterResult;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.utils.Utils;
import org.gobiiproject.datatimescope.utils.WebappUtil;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.zkoss.zk.ui.Sessions;

public class MarkerModelServiceImpl implements MarkerModelService, Serializable {
	final static Logger log = Logger.getLogger(MarkerModelServiceImpl.class.getName());
	private MarkerRecordEntity lastQueriedMarkerEntity;
	
	
	@Override
	public List<VMarkerSummaryEntity> getAllMarkers(List<DatasetSummaryEntity> markerSummary) throws TimescopeException {

		DSLContext context = WebappUtil.getDSLContext();
		List<VMarkerSummaryEntity> markerList = null;
		try{
			String query = "SELECT m.marker_id, m.platform_id, p.name AS platform_name, m.variant_id, m.name AS marker_name, m.code, m.ref, m.alts, m.sequence, r.reference_id, r.name AS reference_name, m.primers, m.strand_id, cv.term AS strand_name, m.status, m.probsets, m.dataset_marker_idx, m.props, m.dataset_vendor_protocol FROM marker m LEFT JOIN platform pl ON m.platform_id = p.platform_id LEFT JOIN cv ON m.strand_id = cv.cv_id  left join marker_linkage_group mlg on m.marker_id = mlg.marker_id left join linkage_group lg on mlg.linkage_group_id = lg.linkage_group_id left join mapset map on lg.map_id = map.mapset_id left join reference r on map.reference_id = r.reference_id;";
			markerList = context.fetch(query).into(VMarkerSummaryEntity.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve markers", e);
		}
		return markerList;

	}


	@Override
	@Deprecated // - use filterUnusuedMarkersInGroupOrDataset  + deleteMarkers
	public boolean deleteMarker(VMarkerSummaryEntity vMarkerSummaryEntity, 
			List<DatasetSummaryEntity> markerSummary) throws TimescopeException {
		boolean successful = false;
//		List<VMarkerSummaryEntity> selectedMarkerList = new ArrayList<VMarkerSummaryEntity>();
//		selectedMarkerList.add(vMarkerSummaryEntity);
//		//check if Marker is not being used in a Marker Group or a Dataset
//		List<VMarkerSummaryEntity> unusedInMarkersGroupsOrDataset = null;
//
//		unusedInMarkersGroupsOrDataset = filterUnusedMarkersInGroupOrDataset(selectedMarkerList);
//
//		if(unusedInMarkersGroupsOrDataset.size()>0){ // If there are markers that can be deleted 
//
//			Messagebox.show("THIS ACTION IS NOT REVERSIBLE.\n\n Do you want to continue?\n", 
//					"WARNING", Messagebox.YES | Messagebox.CANCEL,
//					Messagebox.EXCLAMATION,
//					new org.zkoss.zk.ui.event.EventListener(){
//				@Override
//				public void onEvent(Event event) throws Exception {
//					// TODO Auto-generated method stub
//					if(Messagebox.ON_YES.equals(event.getName())){
//						DSLContext context = getDSLContext();
//
//						double startTime = 0, endTime=0, startTimeMLG = 0, endTimeMLG=0;
//						startTimeMLG = System.currentTimeMillis();
//						Integer markerId = selectedMarkerList.get(0).getMarkerId();
//
//						int result = context.delete(MARKER_LINKAGE_GROUP)
//								.where(MARKER_LINKAGE_GROUP.MARKER_ID.eq(markerId))
//								.execute();
//
//						endTimeMLG = System.currentTimeMillis();
//
//						startTime = System.currentTimeMillis();
//						context.delete(MARKER)
//						.where(MARKER.MARKER_ID.eq(markerId))
//						.execute();
//
//						endTime = System.currentTimeMillis();
//						double rowDeleteSeconds = (endTime - startTime) / 1000;
//						double rowDeleteSecondsMLG = (endTimeMLG - startTimeMLG) / 1000;
//
//						List<String> successMessagesAsList = new ArrayList<String>();
//						successMessagesAsList.add("1 marker deleted. ("+Double.toString(rowDeleteSeconds)+" sec)" );
//						successMessagesAsList.add(Integer.toString(result) +" marker_linkage_group row(s) deleted. ("+Double.toString(rowDeleteSecondsMLG)+" sec)");
//
//						String user = getUser();
//						StringBuilder logSB = new StringBuilder();
//						logSB.append("["+user+ "] DELETED A MARKER\n\n"); 
//						logSB.append("["+user+ "] Filtering criteria for marker delete:\n"+lastQueriedMarkerEntity.getCompleteFiltersAsText());
//						logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n\n"+
//								"context.delete(MARKER_LINKAGE_GROUP).where(\n"
//								+ "            MARKER_LINKAGE_GROUP.MARKER_ID.eq("+markerId.toString()+"))\n"
//								+ "            .execute();\n"+
//								"context.delete(MARKER).where(\n"
//								+ "            MARKER.MARKER_ID.eq("+markerId.toString()+"))\n"
//								+ "            .execute();");
//
//						logSB.append("\n\n["+user+"] Marker delete result:\n"+ "1 marker deleted. ("+Double.toString(rowDeleteSeconds)+" sec)\n"+Integer.toString(result) +" marker_linkage_group row(s) deleted. ("+Double.toString(rowDeleteSecondsMLG)+" sec)");
//
//						logSB.append("\n--------------------------------------------------");
//						log.info(logSB.toString());
//						Map<String, Object> args = new HashMap<String, Object>();
//						args.put("successMessagesAsList", successMessagesAsList);
//						args.put("filterEntity", lastQueriedMarkerEntity.getFilterListAsRows());
//
//						Window window = (Window)Executions.createComponents(
//								"/marker_delete_successful.zul", null, args);
//						window.setPosition("center");
//						window.setClosable(true);
//						window.doModal();
//
//						BindUtils.postGlobalCommand(null, null, "retrieveMarkerList", null);
//
//					}
//
//				}
//			});
//		}

		return successful;

	}

	@Override
	public MarkersFilterResult filterUnusedMarkersInGroupOrDataset(List<VMarkerSummaryEntity> selectedMarkerList) throws TimescopeException {

		int totalNumOfMarkersThatCantBeDeleted = 0;
		MarkersFilterResult result = new MarkersFilterResult(null, null);
		List<VMarkerSummaryEntity> markerIDsThatCanFreelyBeDeleted =  new ArrayList<VMarkerSummaryEntity>();
		List<MarkerDeleteResultTableEntity> markerDeleteResultTableEntityList =  new ArrayList<MarkerDeleteResultTableEntity>();

		boolean inMarkerGroup = false, inDataset = false;

		for(VMarkerSummaryEntity marker: selectedMarkerList){
			try{

				MarkerDeleteResultTableEntity markerDeleteResultTableEntity = new MarkerDeleteResultTableEntity();
				//set initial values

				inMarkerGroup = false;
				inDataset = false;
				markerDeleteResultTableEntity.setMarker_id(marker.getMarkerId());
				markerDeleteResultTableEntity.setMarker_name(marker.getMarkerName());


				//check if the marker id is being used in a dataset

				List<DatasetRecord> inDatasetList = getDatasetsThatContainThisMarkerId(marker.getMarkerId());
				if(inDatasetList.size()>0) {
					inDataset = true;
					markerDeleteResultTableEntity.setDataset_name(setDatasetIdDetails(inDatasetList));
				}


				//check if the marker id is being used in a marker_group
				List<MarkerGroupRecord> inMarkerGroupList = getMarkerGroupsThatContainsThisMarkerId(marker.getMarkerId());


				if(inMarkerGroupList.size()>0){
					inMarkerGroup = true;
					markerDeleteResultTableEntity.setMarker_group_name(setMarkerGroupDetails(inMarkerGroupList));
				}

				if(!inMarkerGroup && !inDataset){
					markerIDsThatCanFreelyBeDeleted.add(marker);
				}else{
					if(totalNumOfMarkersThatCantBeDeleted<10) markerDeleteResultTableEntityList.add(markerDeleteResultTableEntity);
					totalNumOfMarkersThatCantBeDeleted++;

				}

				totalNumOfMarkersThatCantBeDeleted = totalNumOfMarkersThatCantBeDeleted-10;
			}catch(Exception e ){
				throw new TimescopeException("There was an error while trying to retrieve MarkerGroups", e);
			}

		}
		
		result.setResults(markerIDsThatCanFreelyBeDeleted);
		if(markerDeleteResultTableEntityList.size()>0){

			Map<String, Object> args = new HashMap<String, Object>();
			args.put("markerDeleteResultTableEntityList", markerDeleteResultTableEntityList);
			args.put("totalNumOfMarkersThatCantBeDeleted", totalNumOfMarkersThatCantBeDeleted);
			result.setMeta(args);
			//Window window = (Window)Executions.createComponents(
			//		"/markerDeleteWarning.zul", null, args);
			//window.doModal();
		}
		return result;
	}
	
	@SuppressWarnings({ })
	@Override
	public DeleteMarkersResult deleteMarkers(List<VMarkerSummaryEntity> filteredMarkerList, 
			List<DatasetSummaryEntity> markerSummary) throws TimescopeException {

		//check if Marker is not being used in a Marker Group or a Dataset
		//List<VMarkerSummaryEntity> unusedInMarkersGroupsOrDataset = null; --  we assume the deletemarkers have already been filtered

		//unusedInMarkersGroupsOrDataset = filterUnusedMarkersInGroupOrDataset(selectedMarkerList);
		StringBuilder sb = new StringBuilder();
		for(VMarkerSummaryEntity marker : filteredMarkerList){
			sb.append(marker.getMarkerId().toString() + "\n");
		}
		
		DeleteMarkersResult deleteMarkersResult = new DeleteMarkersResult(false, null);

		final int noOfMarkers = filteredMarkerList.size();

		//final List<VMarkerSummaryEntity> finalListofMarkersThatcanBeDeleted = unusedInMarkersGroupsOrDataset;
		if(noOfMarkers>0){

			DSLContext context = WebappUtil.getDSLContext();
			int result = 0, markersDeleted=0;
			double startTime = 0, endTime=0, startTimeMLG = 0, endTimeMLG=0;

			startTimeMLG = System.currentTimeMillis();

			result = context.deleteFrom(MARKER_LINKAGE_GROUP).where(MARKER_LINKAGE_GROUP.MARKER_ID.in(filteredMarkerList
					.stream()
					.map(VMarkerSummaryEntity::getMarkerId)
					.collect(Collectors.toList())))
					.execute();

			endTimeMLG = System.currentTimeMillis();

			startTime = System.currentTimeMillis();

			markersDeleted = context.deleteFrom(MARKER).where(MARKER.MARKER_ID.in(filteredMarkerList
					.stream()
					.map(VMarkerSummaryEntity::getMarkerId)
					.collect(Collectors.toList())))
					.execute();

			endTime = System.currentTimeMillis();
			double rowDeleteSeconds = (endTime - startTime) / 1000;
			double rowDeleteSecondsMLG = (endTimeMLG - startTimeMLG) / 1000;

			List<String> successMessagesAsList = new ArrayList<String>();
			successMessagesAsList.add(Integer.toString(markersDeleted)+" markers deleted. ("+Double.toString(rowDeleteSeconds)+" sec)" );
			successMessagesAsList.add(Integer.toString(result) +" marker_linkage_group row(s) deleted. ("+Double.toString(rowDeleteSecondsMLG)+" sec)");


			String user = getUser();

			StringBuilder logSB = new StringBuilder();
			logSB.append("["+user+ "] DELETED MARKERS\n\n");
			logSB.append("["+user+"] Filtering criteria for marker delete:\n"+lastQueriedMarkerEntity.getCompleteFiltersAsText());

			logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n\n"
					+ "context.deleteFrom(MARKER_LINKAGE_GROUP).where(\n"
					+ "            MARKER_LINKAGE_GROUP.MARKER_ID.in(finalListofMarkersThatcanBeDeleted\r\n" + 
					"            .stream()\r\n" + 
					"            .map(VMarkerSummaryEntity::getMarkerId)\r\n" + 
					"            .collect(Collectors.toList())))\r\n" + 
					"            .execute();\n"+
					"context.deleteFrom(MARKER).where(\n"
					+ "            MARKER.MARKER_ID.in(finalListofMarkersThatcanBeDeleted\r\n" + 
					"            .stream()\r\n" + 
					"            .map(VMarkerSummaryEntity::getMarkerId)\r\n" + 
					"            .collect(Collectors.toList())))\r\n" + 
					"            .execute();");


			logSB.append("\n\n["+user+"] Marker delete result:\n"+ Integer.toString(markersDeleted)+" markers deleted. ("+Double.toString(rowDeleteSeconds)+" sec)\n"+Integer.toString(result) +" marker_linkage_group row(s) deleted. ("+Double.toString(rowDeleteSecondsMLG)+" sec)");

			logSB.append("\n--------------------------------------------------");
			log.info(logSB.toString());
			
			deleteMarkersResult.setSuccess(true);
		
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("successMessagesAsList", successMessagesAsList);
			args.put("filterEntity", lastQueriedMarkerEntity.getFilterListAsRows());
			
			deleteMarkersResult.setMeta(args);
			//Window window = (Window)Executions.createComponents(
			//		"/marker_delete_successful.zul", null, args);
			//window.setPosition("center");
			//window.setClosable(true);
			//window.doModal();

			//BindUtils.postGlobalCommand(null, null, "retrieveMarkerList", null);
			
		}

		return deleteMarkersResult;
	}
	
	
	@Override
	public List<DatasetRecord> getDatasetAssociatedToMarkerId(Integer markerId) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<DatasetRecord> list = null;
		try{
			String query = "select * from dataset where dataset_id in (select d.dataset_id from getalldatasetsbymarker("+markerId.toString()+") d)";
			list = context.fetch(query).into(DatasetRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve datasets associated to the selected marker.");
		}

		return list;
	}
	
	@Override
	public List<MarkerGroupRecord> getMarkerGroupsAssociatedToMarkerId(Integer markerId) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();;
		List<MarkerGroupRecord> list = null;
		try{
			String query = "select * from marker_group where marker_group_id in (select mg.marker_group_id from getmarkergroupsbymarker("+markerId.toString()+") mg)";
			list = context.fetch(query).into(MarkerGroupRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve marker groups associated to the selected marker.", e);
		}
		return list;
	}
	
	@Override
	public List<LinkageGroupRecord> getLinkageGroupsAssociatedToMarkerId(Integer markerId) throws TimescopeException {

		DSLContext context = WebappUtil.getDSLContext();
		List<LinkageGroupRecord> list = null;
		try{

			String query = "select * from linkage_group where linkage_group_id in (select lg.linkage_group_id from getlinkagegroupsbymarker("+markerId.toString()+") lg)";
			list = context.fetch(query).into(LinkageGroupRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve linkage groups associated to the selected marker.", e);
		}

		return list;
	}
	
	@Override
	public List<VMarkerSummaryEntity> getAllMarkersBasedOnQuery(MarkerRecordEntity markerEntity, DatasetSummaryEntity markerSummaryEntity) throws TimescopeException {

		lastQueriedMarkerEntity = new MarkerRecordEntity();
		int queryCount =0;
		int dsNameCount = 0;
		DSLContext context = WebappUtil.getDSLContext();

		List<VMarkerSummaryEntity> markerList = null;

		try{ /* START building THE QUERY via StringBuilder */
			StringBuilder sb = new StringBuilder();
			StringBuilder sbWhere = new StringBuilder(); 

			sb.append("SELECT distinct on (m.marker_id) m.marker_id, m.platform_id, pl.name AS platform_name, m.variant_id, m.name AS marker_name, m.code, m.ref, m.alts, m.sequence, m.reference_id,r.name AS reference_name, m.primers, m.strand_id, cv.term AS strand_name, m.status, m.probsets, m.dataset_marker_idx, m.props, m.dataset_vendor_protocol FROM marker m ");
			sb.append(" LEFT JOIN platform pl ON m.platform_id = pl.platform_id LEFT JOIN cv ON m.strand_id = cv.cv_id  left join marker_linkage_group mlg on m.marker_id = mlg.marker_id left join linkage_group lg on mlg.linkage_group_id = lg.linkage_group_id left join mapset map on lg.map_id = map.mapset_id left join reference r on map.reference_id = r.reference_id ");

			/* ADD THE "WHERE" CONDITIONS */

			// build query for MARKER NAMES filter
			if (markerEntity.getMarkerNamesAsCommaSeparatedString()!=null && !markerEntity.getMarkerNamesAsCommaSeparatedString().isEmpty()){
				lastQueriedMarkerEntity.setMarkerNamesAsCommaSeparatedString(markerEntity.getMarkerNamesAsCommaSeparatedString());
				sbWhere.append(" where LOWER(m.name) in ("+markerEntity.getSQLReadyMarkerNames()+")");
				dsNameCount++;	
			}


			//build query for 'none' selected on dataset filter
			if(markerEntity.isMarkerNotInDatasets()) {
				lastQueriedMarkerEntity.setMarkerNotInDatasets(true);
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" m.dataset_marker_idx = '{}' ");
				queryCount++;

			}

			// build query for MAPSET filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getMapsetList())){ 
				lastQueriedMarkerEntity.getMapsetList().addAll(markerEntity.getMapsetList());
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" map.mapset_id "+ getIDsToString(markerEntity.getMapsetList()));
				queryCount++;
			}

			// build query for LINKAGE GROUP filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getLinkageGroupList())){ 
				lastQueriedMarkerEntity.getLinkageGroupList().addAll(markerEntity.getLinkageGroupList());

				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" lg.linkage_group_id "+ getIDsToString(markerEntity.getLinkageGroupList()));
				queryCount++;
			}

			// build query for PLATFORM filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getPlatformList())){
				lastQueriedMarkerEntity.getPlatformList().addAll(markerEntity.getPlatformList());

				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" pl.platform_id "+ getIDsToString(markerEntity.getPlatformList()));
				queryCount++;
			}

			// build query for VENDOR-PROTOCOL filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getVendorProtocolList())){
				lastQueriedMarkerEntity.getVendorProtocolList().addAll(markerEntity.getVendorProtocolList());
				sb.append(buildLeftJoin(sb,"vendorprotocol"));
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" vp.vendor_protocol_id "+ getIDsToString(markerEntity.getVendorProtocolList()));
				queryCount++;
			}

			// build query for PROJECTS filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getProjectList())){
				lastQueriedMarkerEntity.getProjectList().addAll(markerEntity.getProjectList());
				sb.append(buildLeftJoin(sb,"project"));
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" prj.project_id "+ getIDsToString(markerEntity.getProjectList()));
				queryCount++;
			}

			// build query for EXPERIMENTS filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getExperimentList())){
				lastQueriedMarkerEntity.getExperimentList().addAll(markerEntity.getExperimentList());
				sb.append(buildLeftJoin(sb,"experiment"));
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" e.experiment_id "+ getIDsToString(markerEntity.getExperimentList()));
				queryCount++;
			}

			// build query for DATASETS filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getDatasetList()) && !markerEntity.isMarkerNotInDatasets()){
				lastQueriedMarkerEntity.getDatasetList().addAll(markerEntity.getDatasetList());
				sb.append(buildLeftJoin(sb,"dataset"));
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" d.dataset_id "+ getIDsToString(markerEntity.getDatasetList()));
				queryCount++;
			}

			// build query for ANALYSES filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getAnalysesList()) && !markerEntity.isMarkerNotInDatasets()){
				lastQueriedMarkerEntity.getAnalysesList().addAll(markerEntity.getAnalysesList());
				sb.append(buildLeftJoin(sb,"analysis"));
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" a.analysis_id "+ getIDsToString(markerEntity.getAnalysesList()));
				queryCount++;
			}

			// build query for given marker IDs
			if (markerEntity.getMarkerIDStartRange()!=null || markerEntity.getMarkerIDEndRange()!=null){

				//check which is not null
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);

				if(markerEntity.getMarkerIDStartRange()!=null && markerEntity.getMarkerIDEndRange()!=null){ //if both is not null
					Integer lowerID = markerEntity.getMarkerIDStartRange();
					Integer higherID = markerEntity.getMarkerIDEndRange();

					if(lowerID.compareTo(higherID)>0){
						lowerID = markerEntity.getMarkerIDEndRange();
						higherID = markerEntity.getMarkerIDStartRange();
					}
					lastQueriedMarkerEntity.setMarkerIDEndRange(markerEntity.getMarkerIDEndRange());
					lastQueriedMarkerEntity.setMarkerIDStartRange(lastQueriedMarkerEntity.getMarkerIDStartRange());
					sbWhere.append(" m.marker_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
				}else{
					Integer ID = null;
					if(markerEntity.getMarkerIDStartRange()!=null) {
						ID = markerEntity.getMarkerIDStartRange();
						lastQueriedMarkerEntity.setMarkerIDStartRange(lastQueriedMarkerEntity.getMarkerIDStartRange());
					}
					else {
						lastQueriedMarkerEntity.setMarkerIDEndRange(markerEntity.getMarkerIDEndRange());
						ID = markerEntity.getMarkerIDEndRange();
					}

					sbWhere.append(" m.marker_id = "+Integer.toString(ID));
				}

				queryCount++;
			}

			sbWhere.append(";");
			sb.append(sbWhere.toString());
			String query = sb.toString();
			System.out.println(query);
			markerList = context.fetch(query).into(VMarkerSummaryEntity.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve markers");
		}
		return markerList;

	}
	
	@Override
	public List<VMarkerSummaryEntity> getAllMarkersBasedOnQueryViaView(MarkerRecordEntity markerEntity,
			DatasetSummaryEntity markerSummaryEntity) throws TimescopeException {
		lastQueriedMarkerEntity = new MarkerRecordEntity();
		int queryCount =0;
		int dsNameCount = 0;
		DSLContext context = WebappUtil.getDSLContext();

		List<VMarkerSummaryEntity> markerList = null;

		try{ /* START building THE QUERY via StringBuilder */
			StringBuilder sb = new StringBuilder();
			StringBuilder sbWhere = new StringBuilder(); 


			sb.append("select * from v_marker_summary vms "); 

			/* ADD THE "WHERE" CONDITIONS */

			// build query for MARKER NAMES filter
			if (markerEntity.getMarkerNamesAsCommaSeparatedString()!=null && !markerEntity.getMarkerNamesAsCommaSeparatedString().isEmpty()){
				lastQueriedMarkerEntity.setMarkerNamesAsCommaSeparatedString(markerEntity.getMarkerNamesAsCommaSeparatedString());
				sbWhere.append(" where LOWER(vms.marker_name) in ("+markerEntity.getSQLReadyMarkerNames()+")");
				dsNameCount++;  
			}


			//build query for 'none' selected on dataset filter
			if(markerEntity.isMarkerNotInDatasets()) {
				lastQueriedMarkerEntity.setMarkerNotInDatasets(true);
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" vms.dataset_marker_idx = '{}' ");
				queryCount++;
			}

			// build query for MAPSET filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getMapsetList())){ 
				lastQueriedMarkerEntity.getMapsetList().addAll(markerEntity.getMapsetList());
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(buildQueryForGetMarkersInMapsetList(markerEntity.getMapsetList()));
				queryCount++;
			}

			// build query for LINKAGE GROUP filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getLinkageGroupList())){ 
				lastQueriedMarkerEntity.getLinkageGroupList().addAll(markerEntity.getLinkageGroupList());

				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(buildQueryForGetMarkersInLinkageGroupList(markerEntity.getLinkageGroupList()));
				queryCount++;
			}

			// build query for PLATFORM filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getPlatformList())){
				lastQueriedMarkerEntity.getPlatformList().addAll(markerEntity.getPlatformList());

				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(" vms.platform_id "+ getIDsToString(markerEntity.getPlatformList()));
				queryCount++;
			}

			// build query for VENDOR-PROTOCOL filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getVendorProtocolList())){
				lastQueriedMarkerEntity.getVendorProtocolList().addAll(markerEntity.getVendorProtocolList());
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(buildQueryForGetMarkersInVendorProtocol(markerEntity.getVendorProtocolList()));
				queryCount++;
			}

			// build query for PROJECTS filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getProjectList())){
				lastQueriedMarkerEntity.getProjectList().addAll(markerEntity.getProjectList());
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(buildQueryForGetMarkersInProject(markerEntity.getProjectList()));
				queryCount++;
			}

			// build query for EXPERIMENTS filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getExperimentList())){
				lastQueriedMarkerEntity.getExperimentList().addAll(markerEntity.getExperimentList());
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(buildQueryForGetMarkersInExperiment(markerEntity.getExperimentList()));
				queryCount++;
			}

			// build query for DATASETS filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getDatasetList()) && !markerEntity.isMarkerNotInDatasets()){
				lastQueriedMarkerEntity.getDatasetList().addAll(markerEntity.getDatasetList());
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append(getDatasetJsonbQueryFor(markerEntity.getDatasetList())+" ");
				queryCount++;
			}

			// build query for ANALYSES filter
			if (Utils.isListNotNullOrEmpty(markerEntity.getAnalysesList()) && !markerEntity.isMarkerNotInDatasets()){
				lastQueriedMarkerEntity.getAnalysesList().addAll(markerEntity.getAnalysesList());
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);
				sbWhere.append("("+buildQueryForGetMarkersInAnalysis(markerEntity.getAnalysesList()));
				sbWhere.append(" or "+buildQueryForGetMarkersInCallingAnalysis(markerEntity.getAnalysesList())+")");
				queryCount++;
			}

			// build query for given marker IDs
			if (markerEntity.getMarkerIDStartRange()!=null || markerEntity.getMarkerIDEndRange()!=null){

				//check which is not null
				checkPreviousAppends(dsNameCount, queryCount, sbWhere);

				if(markerEntity.getMarkerIDStartRange()!=null && markerEntity.getMarkerIDEndRange()!=null){ //if both is not null
					Integer lowerID = markerEntity.getMarkerIDStartRange();
					Integer higherID = markerEntity.getMarkerIDEndRange();

					if(lowerID.compareTo(higherID)>0){
						lowerID = markerEntity.getMarkerIDEndRange();
						higherID = markerEntity.getMarkerIDStartRange();
					}
					lastQueriedMarkerEntity.setMarkerIDEndRange(markerEntity.getMarkerIDEndRange());
					lastQueriedMarkerEntity.setMarkerIDStartRange(lastQueriedMarkerEntity.getMarkerIDStartRange());
					sbWhere.append(" vms.marker_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
				}else{
					Integer ID = null;
					if(markerEntity.getMarkerIDStartRange()!=null) {
						ID = markerEntity.getMarkerIDStartRange();
						lastQueriedMarkerEntity.setMarkerIDStartRange(lastQueriedMarkerEntity.getMarkerIDStartRange());
					}
					else {
						lastQueriedMarkerEntity.setMarkerIDEndRange(markerEntity.getMarkerIDEndRange());
						ID = markerEntity.getMarkerIDEndRange();
					}

					sbWhere.append(" vms.marker_id = "+Integer.toString(ID));
				}

				queryCount++;
			}

			sbWhere.append(";");
			sb.append(sbWhere.toString());
			String query = sb.toString();
			markerList = context.fetch(query).into(VMarkerSummaryEntity.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve markers");
		}
		return markerList;
	}

	private Object buildQueryForGetMarkersInCallingAnalysis(List<AnalysisRecord> analysesList) {
		StringBuilder sb = new StringBuilder();
		for(AnalysisRecord record : analysesList) {
			if(sb.length()>0)sb.append(" or ");
			sb.append("vms.marker_id in (select marker_id from getMarkersInCallingAnalysis("+record.getAnalysisId()+"))");
		}

		sb.append(") ");
		sb.insert(0, "(");

		return sb.toString();
	}

	private Object buildQueryForGetMarkersInAnalysis(List<AnalysisRecord> analysesList) {
		StringBuilder sb = new StringBuilder();
		for(AnalysisRecord record : analysesList) {
			if(sb.length()>0)sb.append(" or ");
			sb.append("vms.marker_id in (select marker_id from getMarkersInAnalysis("+record.getAnalysisId()+"))");
		}

		sb.append(") ");
		sb.insert(0, "(");

		return sb.toString();
	}

	private Object buildQueryForGetMarkersInExperiment(List<ExperimentRecord> experimentList) {
		StringBuilder sb = new StringBuilder();
		for(ExperimentRecord record : experimentList) {
			if(sb.length()>0)sb.append(" or ");
			sb.append("vms.marker_id in (select marker_id from getMarkersInExperiment("+record.getExperimentId()+"))");
		}

		sb.append(") ");
		sb.insert(0, "(");

		return sb.toString();
	}

	private Object buildQueryForGetMarkersInProject(List<ProjectRecord> projectList) {
		StringBuilder sb = new StringBuilder();
		for(ProjectRecord record : projectList) {
			if(sb.length()>0)sb.append(" or ");
			sb.append("vms.marker_id in (select marker_id from getMarkersInProject("+record.getProjectId()+"))");
		}

		sb.append(") ");
		sb.insert(0, "(");

		return sb.toString();
	}

	private Object buildQueryForGetMarkersInVendorProtocol(List<VendorProtocolRecord> vendorProtocolList) {
		StringBuilder sb = new StringBuilder();
		for(VendorProtocolRecord record : vendorProtocolList) {
			if(sb.length()>0)sb.append(" or ");
			sb.append("vms.marker_id in (select marker_id from getMarkersInVendorProtocol("+record.getVendorProtocolId()+"))");
		}

		sb.append(") ");
		sb.insert(0, "(");

		return sb.toString();
	}

	private Object buildQueryForGetMarkersInLinkageGroupList(List<LinkageGroupRecord> linkageGroupList) {
		StringBuilder sb = new StringBuilder();
		for(LinkageGroupRecord lg : linkageGroupList) {
			if(sb.length()>0)sb.append(" or ");
			sb.append("vms.marker_id in (select marker_id from getMarkersInLinkageGroup("+lg.getLinkageGroupId()+"))");
		}

		sb.append(") ");
		sb.insert(0, "(");

		return sb.toString();
	}

	private String buildQueryForGetMarkersInMapsetList(List<MapsetRecord> mapsetList) {
		StringBuilder sb = new StringBuilder();
		for(MapsetRecord mr : mapsetList) {
			if(sb.length()>0)sb.append(" or ");
			sb.append("vms.marker_id in (select marker_id from getMarkersInMapset("+mr.getMapsetId()+"))");
		}

		sb.append(") ");
		sb.insert(0, "(");

		return sb.toString();
	}
	
	private List<DatasetRecord> getDatasetsThatContainThisMarkerId(Integer markerId) {

		List<DatasetRecord> datasetList = null;
		DSLContext context = WebappUtil.getDSLContext();
		String query = "select dataset_id, name from dataset where dataset_id in (select key::integer from jsonb_each_text((select dataset_marker_idx from marker where marker_id="+markerId+")));";
		datasetList = context.fetch(query).into(DatasetRecord.class);

		return datasetList;
	}
	
	private String setMarkerGroupDetails(List<MarkerGroupRecord> markerGroupList) {

		StringBuilder sb = new StringBuilder();
		for (MarkerGroupRecord mgr : markerGroupList){
			if(sb.length()>0) sb.append(", ");
			sb.append(" "+mgr.getName()+" ("+mgr.getMarkerGroupId()+")");
		}

		return(sb.toString());

	}
	
	private String setDatasetIdDetails(List<DatasetRecord> inDatasetList) {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		for(DatasetRecord mgr : inDatasetList){
			if (sb.length()>0) sb.append(", ");
			sb.append(" "+mgr.getName()+" ("+mgr.getDatasetId()+")");
		}

		return(sb.toString());
	}
	

	private List<MarkerGroupRecord> getMarkerGroupsThatContainsThisMarkerId(Integer markerId) {

		List<MarkerGroupRecord> markerGroupList = null;
		DSLContext context = WebappUtil.getDSLContext();
		String query = "SELECT a.marker_group_id, a.name FROM (SELECT (jsonb_each_text(markers)).*, marker_group_id, name FROM marker_group) a  where a.key='"+Integer.toString(markerId)+"';";
		markerGroupList = context.fetch(query).into(MarkerGroupRecord.class);

		return markerGroupList;
	}
	
	private String getUser() {
		//TODO:  transfer this to SessionUtil
		UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
		return cre.getAccount();
	}
	
	private void checkPreviousAppends(int dsNameCount, int queryCount, StringBuilder sb){
		// TODO Auto-generated method stub

		if(dsNameCount==0 && queryCount==0) sb.append(" where ");
		else sb.append(" and ");
	}
	
	

	private<T> String getDatasetJsonbQueryFor(List<T> list) {
		if(list.isEmpty()) return null;
		StringBuilder sb = new StringBuilder();

		int ctr=0;
		for(T item : list) {
			if(ctr>0)sb.append(",");
			sb.append("'"+((Record) item).get(0).toString()+"'");
			ctr++;
		}

		if(list.size()>1) {
			sb.append("])");
			sb.insert(0, " jsonb_exists_any(dataset_marker_idx, array[");
		}else{
			sb.insert(0, " jsonb_exists(dataset_marker_idx, ");
			sb.append(")");
		}

		return sb.toString();
	}
	
	private String buildLeftJoin(StringBuilder sb, String category) {

		StringBuilder returnValBuilder = new StringBuilder();

		switch(category){
		case "analysis":
			if(!sb.toString().contains("dataset d")) {
				returnValBuilder.append("LEFT JOIN dataset d ON jsonb_exists(m.dataset_marker_idx, d.dataset_id::text) ");
			}
			returnValBuilder.append(" left join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) ");
			break;
		default:

			//dataset
			if(!sb.toString().contains("dataset d")) {
				returnValBuilder.append("LEFT JOIN dataset d ON jsonb_exists(m.dataset_marker_idx, d.dataset_id::text) ");
			}
			if (category.equalsIgnoreCase("dataset")) break;

			//experiment
			if(!sb.toString().contains("experiment e")) {
				returnValBuilder.append("LEFT JOIN experiment e ON d.experiment_id = e.experiment_id ");
			}
			if(category.equals("experiment")) break;

			//vendor-protocol
			if(!sb.toString().contains("vendor_protocol vp")) {
				returnValBuilder.append("LEFT JOIN vendor_protocol vp ON e.vendor_protocol_id = vp.vendor_protocol_id ");
			}
			if(category.equals("vendorprotocol")) break;

			//project
			if(!sb.toString().contains("project prj")) {
				returnValBuilder.append("LEFT JOIN project prj ON e.project_id = prj.project_id ");
			}
			break;
		}

		return returnValBuilder.toString();
	}
	
	public <T> String getIDsToString( List<T> list) { //TODO: move to utility class
		if(list.isEmpty()) return null;
		StringBuilder sb = new StringBuilder();

		int ctr=0;
		for(T item : list) {
			if(ctr>0)sb.append(",");
			sb.append(((Record) item).get(0).toString());
			ctr++;
		}

		if(list.size()>1) {
			sb.append(") ");
			sb.insert(0, " in (");
		}else sb.insert(0, " = ");

		return sb.toString();
	}


}
