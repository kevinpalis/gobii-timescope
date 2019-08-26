package org.gobiiproject.datatimescope.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.AnalysisRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ExperimentRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MapsetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.OrganizationRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VendorProtocolRecord;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.Utils;
import org.jooq.Record;

public class MarkerRecordEntity  implements Serializable,Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean markerNotInDatasets;
	private Integer markerIDStartRange;
	private Integer  markerIDEndRange;
	private String markerNamesAsCommaSeparatedString;
    private List<RowColEntity> filterListAsRows;
	private List<PlatformRecord> platformList;
	private List<OrganizationRecord> vendorList;
	private List<VendorProtocolRecord> vendorProtocolList;
	private List<AnalysisRecord> callingAnalysisList, analysesList;
	private List<ProjectRecord> projectList;
	private List<ExperimentRecord> experimentList;
	private List<DatasetRecord> datasetList;
	private List<MapsetRecord> mapsetList;
	private List<LinkageGroupRecord> linkageGroupList;
	
	public MarkerRecordEntity() {
		setMarkerNotInDatasets(false);
		setPlatformList(new ArrayList<PlatformRecord>());
		setVendorProtocolList(new ArrayList<VendorProtocolRecord>());
		setAnalysesList(new ArrayList<AnalysisRecord>());
		setProjectList(new ArrayList<ProjectRecord>());
		setExperimentList(new ArrayList<ExperimentRecord>());
		setDatasetList(new ArrayList<DatasetRecord>());
		setMapsetList(new ArrayList<MapsetRecord>());
		setLinkageGroupList(new ArrayList<LinkageGroupRecord>());
	}

	public String getMarkerNamesAsCommaSeparatedString() {
		return markerNamesAsCommaSeparatedString;
	}

	public void setMarkerNamesAsCommaSeparatedString(String markerNamesAsCommaSeparatedString) {
		this.markerNamesAsCommaSeparatedString = markerNamesAsCommaSeparatedString;
	}

	public String getSQLReadyMarkerNames() {
		// TODO Auto-generated method stub

		int ctr = 0;
		StringBuilder sb = new StringBuilder();
		
		for(String s: markerNamesAsCommaSeparatedString.replaceAll(" \n","\n").split("\n")){
			if(ctr>0)sb.append(",");
		sb.append(" '"+s.toLowerCase()+"' ");
		ctr++;
		}
		return sb.toString();
	}

	public Integer getMarkerIDStartRange() {
		return markerIDStartRange;
	}

	public void setMarkerIDStartRange(Integer markerIDStartRange) {
		this.markerIDStartRange = markerIDStartRange;
	}

	public Integer getMarkerIDEndRange() {
		return markerIDEndRange;
	}

	public void setMarkerIDEndRange(Integer markerIDEndRange) {
		this.markerIDEndRange = markerIDEndRange;
	}

	public List<PlatformRecord> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(List<PlatformRecord> platform) {
		this.platformList = platform;
	}

	public List<OrganizationRecord> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<OrganizationRecord> vendorList) {
		this.vendorList = vendorList;
	}

	public List<VendorProtocolRecord> getVendorProtocolList() {
		return vendorProtocolList;
	}

	public void setVendorProtocolList(List<VendorProtocolRecord> vendorProtocolList) {
		this.vendorProtocolList = vendorProtocolList;
	}

	public List<AnalysisRecord> getCallingAnalysisList() {
		return callingAnalysisList;
	}

	public void setCallingAnalysisList(List<AnalysisRecord> callingAnalysisList) {
		this.callingAnalysisList = callingAnalysisList;
	}

	public List<AnalysisRecord> getAnalysesList() {
		return analysesList;
	}

	public void setAnalysesList(List<AnalysisRecord> analysesList) {
		this.analysesList = analysesList;
	}

	public List<ProjectRecord> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<ProjectRecord> projectList) {
		this.projectList = projectList;
	}

	public List<ExperimentRecord> getExperimentList() {
		return experimentList;
	}

	public void setExperimentList(List<ExperimentRecord> experimentList) {
		this.experimentList = experimentList;
	}

	public List<DatasetRecord> getDatasetList() {
		return datasetList;
	}

	public void setDatasetList(List<DatasetRecord> datasetList) {
		this.datasetList = datasetList;
	}

	public List<MapsetRecord> getMapsetList() {
		return mapsetList;
	}

	public void setMapsetList(List<MapsetRecord> mapsetList) {
		this.mapsetList = mapsetList;
	}

	public List<LinkageGroupRecord> getLinkageGroupList() {
		return linkageGroupList;
	}

	public void setLinkageGroupList(List<LinkageGroupRecord> linkageGroupList) {
		this.linkageGroupList = linkageGroupList;
	}

	public boolean isMarkerNotInDatasets() {
		return markerNotInDatasets;
	}

	public void setMarkerNotInDatasets(boolean markerNotInDatasets) {
		this.markerNotInDatasets = markerNotInDatasets;
	}

	public String getFiltersAsTextWithDelimiter(String delim) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		if(Utils.isListNotNullOrEmpty(getPlatformList())){
			sb.append("Platform(s):");
			sb.append(Utils.getListNamesToStringWithDelimiter(getPlatformList(), 1, delim));
		}
		
		if(Utils.isListNotNullOrEmpty(getVendorProtocolList())){
			sb.append("Vendor-protocol(s):");
			sb.append(Utils.getListNamesToStringWithDelimiter(getVendorProtocolList(), 1, delim));
		}
		
		if(Utils.isListNotNullOrEmpty(getMapsetList())){
			sb.append("Mapset(s):");
			sb.append(Utils.getListNamesToStringWithDelimiter(getMapsetList(), 1, delim));
		}

		if(Utils.isListNotNullOrEmpty(getLinkageGroupList())){
			sb.append("Linkage group(s):");
			sb.append(Utils.getListNamesToStringWithDelimiter(getLinkageGroupList(), 1, delim));
		}

		if(Utils.isListNotNullOrEmpty(getProjectList())){
			sb.append("Project(s):");
			sb.append(Utils.getListNamesToStringWithDelimiter(getProjectList(), 1, delim));
		}

		if(Utils.isListNotNullOrEmpty(getExperimentList())){
			sb.append("Experiment(s):");
			sb.append(Utils.getListNamesToStringWithDelimiter(getExperimentList(), 1, delim));
		}
		
		if(Utils.isListNotNullOrEmpty(getAnalysesList())){
			sb.append("Analyses:");
			sb.append(Utils.getListNamesToStringWithDelimiter(getAnalysesList(), 1, delim));
		}
		
		if(markerNotInDatasets){
		    sb.append("Dataset(s):\n\t*Should not be in a dataset");
		}
		else if(Utils.isListNotNullOrEmpty(getDatasetList())){
			sb.append("Dataset(s):");
			sb.append(Utils.getListNamesToStringWithDelimiter(getDatasetList(), 15, delim));
		}
		
//		private Integer markerIDStartRange;
//		private Integer  markerIDEndRange;
//		private String markerNamesAsCommaSeparatedString;
		
		return sb.toString();
	}

    public String getCompleteFiltersAsText() {
        StringBuilder sb = new StringBuilder();
        
        if(markerIDStartRange!=null || markerIDEndRange!=null){
            
            sb.append("Marker Id: ");
            sb.append(Utils.getIdRangeAsString(markerIDStartRange,markerIDEndRange));
        }else if(markerNamesAsCommaSeparatedString!=null) {

            if(!markerNamesAsCommaSeparatedString.isEmpty()) {
                sb.append("Marker Names:\n");
                sb.append(markerNamesAsCommaSeparatedString);
            }
        }
        String filters = getFiltersAsTextWithDelimiter(", ");
        sb.append(filters);
        return sb.toString();
    }

    public List<RowColEntity> getFilterListAsRows() {
        filterListAsRows = new ArrayList<RowColEntity>();

        RowColEntity rowColEntity = new RowColEntity();
        
        if(markerIDStartRange!=null || markerIDEndRange!=null){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Marker Id(s)");
            rowColEntity.setSecondRow(Utils.getIdRangeAsString(markerIDStartRange,markerIDEndRange));
            filterListAsRows.add(rowColEntity);
            
        }else if(markerNamesAsCommaSeparatedString!=null) {

            if(!markerNamesAsCommaSeparatedString.isEmpty()) {
                rowColEntity = new RowColEntity();
                rowColEntity.setFirstRow("Marker Name(s)");
                rowColEntity.setSecondRow(markerNamesAsCommaSeparatedString);
                filterListAsRows.add(rowColEntity);
            }
        }
        
        if(Utils.isListNotNullOrEmpty(getPlatformList())){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Platform(s)");
            rowColEntity.setSecondRow(Utils.getListNamesToStringWithDelimiter(getPlatformList(), 1, ", "));
            filterListAsRows.add(rowColEntity);
        }
        
        if(Utils.isListNotNullOrEmpty(getVendorProtocolList())){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Vendor-protocol(s)");
            rowColEntity.setSecondRow(Utils.getListNamesToStringWithDelimiter(getVendorProtocolList(), 1,  ", "));
            filterListAsRows.add(rowColEntity);
        }
        
        if(Utils.isListNotNullOrEmpty(getMapsetList())){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Mapset(s)");
            rowColEntity.setSecondRow(Utils.getListNamesToStringWithDelimiter(getMapsetList(), 1,  ", "));
            filterListAsRows.add(rowColEntity);
        }

        if(Utils.isListNotNullOrEmpty(getLinkageGroupList())){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Linkage group(s)");
            rowColEntity.setSecondRow(Utils.getListNamesToStringWithDelimiter(getLinkageGroupList(), 1,  ", "));
            filterListAsRows.add(rowColEntity);
        }

        if(Utils.isListNotNullOrEmpty(getProjectList())){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Project(s)");
            rowColEntity.setSecondRow(Utils.getListNamesToStringWithDelimiter(getProjectList(), 1,  ", "));
            filterListAsRows.add(rowColEntity);
        }

        if(Utils.isListNotNullOrEmpty(getExperimentList())){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Experiment(s)");
            rowColEntity.setSecondRow(Utils.getListNamesToStringWithDelimiter(getExperimentList(), 1,  ", "));
            filterListAsRows.add(rowColEntity);
        }
        
        if(Utils.isListNotNullOrEmpty(getAnalysesList())){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Analyses");
            rowColEntity.setSecondRow(Utils.getListNamesToStringWithDelimiter(getAnalysesList(), 1,  ", "));
            filterListAsRows.add(rowColEntity);
        }
        
        if(markerNotInDatasets){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Dataset(s)");
            rowColEntity.setSecondRow("*Should not be in a dataset");
            filterListAsRows.add(rowColEntity);
        }
        else if(Utils.isListNotNullOrEmpty(getDatasetList())){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Dataset(s)");
            rowColEntity.setSecondRow(Utils.getListNamesToStringWithDelimiter(getDatasetList(), 15,  ", "));
            filterListAsRows.add(rowColEntity);
        }
        
        return filterListAsRows;
    }

    public void setFilterListAsRows(List<RowColEntity> filterListAsRows) {
        this.filterListAsRows = filterListAsRows;
    }

}
