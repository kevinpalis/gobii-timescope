/* 
timescoper_id integer NOT NULL DEFAULT nextval('timescoper_timescoper_id_seq'::regclass),
    firstname text COLLATE pg_catalog."default" NOT NULL,
    lastname text COLLATE pg_catalog."default" NOT NULL,
    username text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default",
    role integer DEFAULT 3,
    CONSTRAINT pk_timescoper PRIMARY KEY (timescoper_id),
    CONSTRAINT username_key UNIQUE (username)
*/
package org.gobiiproject.datatimescope.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.AnalysisRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ExperimentRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.utils.Utils;

/**
 * User entity
 */
public class DnasampleEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private Integer  dnasampleIDStartRange, germplasmIDStartRange;
	private Integer  dnasampleIDEndRange, germplasmIDEndRange;
	private String dnasampleNamesAsEnterSeparatedString, germplasmNamesAsEnterSeparatedString, dnasampleNamesAsCommaSeparatedString, germplasmNamesAsCommaSeparatedString;
	private Date creationDateStart;
	private Date creationDateEnd;
	private ProjectRecord projectRecord;
	private ExperimentRecord experimentRecord;
	private DatasetRecord datasetRecord;

    private List<ProjectRecord> projectList;
    private List<ExperimentRecord> experimentList;
    private List<DatasetRecord> datasetList;

    private ArrayList<RowColEntity> filterListAsRows;
    
	public DnasampleEntity(){
        setProjectList(new ArrayList<ProjectRecord>());
        setExperimentList(new ArrayList<ExperimentRecord>());
        setDatasetList(new ArrayList<DatasetRecord>());
	}


    public String getSQLReadyDnasampleNames() {
        // TODO Auto-generated method stub
        int ctr = 0;
        StringBuilder sb = new StringBuilder();
        String names = dnasampleNamesAsEnterSeparatedString;
        
        for(String s: names.replaceAll(" \n","\n").split("\n")){
            if(ctr>0)sb.append(",");
        sb.append(" '"+s.toLowerCase()+"' ");
        ctr++;
        }
        return sb.toString();
    }
    
    public String getSQLReadyGermplasmNames() {
        // TODO Auto-generated method stub
        int ctr = 0;
        StringBuilder sb = new StringBuilder();
        String names = germplasmNamesAsEnterSeparatedString;
        
        for(String s: names.replaceAll(" \n","\n").split("\n")){
            if(ctr>0)sb.append(",");
        sb.append(" '"+s.toLowerCase()+"' ");
        ctr++;
        }
        return sb.toString();
    }
    
    public String getCompleteFiltersAsText() {
        String returnVal = " (not filtered)";
        StringBuilder sb = new StringBuilder();
        
         if(dnasampleIDStartRange!=null || dnasampleIDEndRange!=null){
                    
                    sb.append("DNAsample Id: ");
                    sb.append(Utils.getIdRangeAsString(dnasampleIDStartRange,dnasampleIDEndRange));
                }else if(dnasampleNamesAsEnterSeparatedString!=null) {
        
                    if(!dnasampleNamesAsEnterSeparatedString.isEmpty()) {
                        sb.append("DNAsample Names:\n");
                        sb.append(dnasampleNamesAsEnterSeparatedString);
                    }
           }
 
         if(germplasmIDStartRange!=null || germplasmIDEndRange!=null){
             
             sb.append("Germplasm Id: ");
             sb.append(Utils.getIdRangeAsString(germplasmIDStartRange,germplasmIDEndRange));
         }else if(germplasmNamesAsEnterSeparatedString!=null) {
        
             if(!germplasmNamesAsEnterSeparatedString.isEmpty()) {
                 sb.append("Germplasm Names:\n");
                 sb.append(germplasmNamesAsEnterSeparatedString);
             }
         }
        
        String filters = getFiltersAsTextWithDelimiter(", ");
        sb.append(filters);
        
        if(sb.length()>1) {
            returnVal =  sb.toString();
        }
        return returnVal;
    }
    

    public List<RowColEntity> getFilterListAsRows() {
        filterListAsRows = new ArrayList<RowColEntity>();

        RowColEntity rowColEntity = new RowColEntity();
        
        //dna samples
        if(dnasampleIDStartRange!=null || dnasampleIDEndRange!=null){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("DNAsample Id(s)");
            rowColEntity.setSecondRow(Utils.getIdRangeAsString(dnasampleIDStartRange,dnasampleIDEndRange));
            filterListAsRows.add(rowColEntity);
            
        }else if(dnasampleNamesAsCommaSeparatedString!=null) {

            if(!dnasampleNamesAsCommaSeparatedString.isEmpty()) {
                rowColEntity = new RowColEntity();
                rowColEntity.setFirstRow("DNAsample Name(s)");
                rowColEntity.setSecondRow(dnasampleNamesAsCommaSeparatedString);
                filterListAsRows.add(rowColEntity);
            }
        }
        
        //germplasm
        if(germplasmIDStartRange!=null || germplasmIDEndRange!=null){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Germplasm Id(s)");
            rowColEntity.setSecondRow(Utils.getIdRangeAsString(germplasmIDStartRange,germplasmIDEndRange));
            filterListAsRows.add(rowColEntity);
            
        }else if(germplasmNamesAsCommaSeparatedString!=null) {

            if(!germplasmNamesAsCommaSeparatedString.isEmpty()) {
                rowColEntity = new RowColEntity();
                rowColEntity.setFirstRow("Germplasm Name(s)");
                rowColEntity.setSecondRow(germplasmNamesAsCommaSeparatedString);
                filterListAsRows.add(rowColEntity);
            }
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
        
        
       if(Utils.isListNotNullOrEmpty(getDatasetList())){
            rowColEntity = new RowColEntity();
            rowColEntity.setFirstRow("Dataset(s)");
            rowColEntity.setSecondRow(Utils.getListNamesToStringWithDelimiter(getDatasetList(), 15,  ", "));
            filterListAsRows.add(rowColEntity);
        }
        
        return filterListAsRows;
    }
    
    public String getFiltersAsTextWithDelimiter(String delim) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();

        if(Utils.isListNotNullOrEmpty(getProjectList())){
            sb.append("Project:");
            sb.append(Utils.getListNamesToStringWithDelimiter(getProjectList(), 1, delim));
        }

        if(Utils.isListNotNullOrEmpty(getExperimentList())){
            sb.append("Experiment:");
            sb.append(Utils.getListNamesToStringWithDelimiter(getExperimentList(), 1, delim));
        }

        else if(Utils.isListNotNullOrEmpty(getDatasetList())){
            sb.append("Dataset(s):");
            sb.append(Utils.getListNamesToStringWithDelimiter(getDatasetList(), 15, delim));
        }
      
        return sb.toString();
    }
    
	public Date getCreationDateStart() {
		return creationDateStart;
	}

	public void setCreationDateStart(Date creationDateStart) {
		this.creationDateStart = creationDateStart;
	}

	public Date getCreationDateEnd() {
		return creationDateEnd;
	}

	public void setCreationDateEnd(Date creationDateEnd) {
		this.creationDateEnd = creationDateEnd;
	}

    public ProjectRecord getProjectRecord() {
        return projectRecord;
    }

    public void setProjectRecord(ProjectRecord projectRecord) {
        this.projectRecord = projectRecord;
    }

    public ExperimentRecord getExperimentRecord() {
        return experimentRecord;
    }

    public void setExperimentRecord(ExperimentRecord experimentRecord) {
        this.experimentRecord = experimentRecord;
    }

    public String getDnasampleNamesAsEnterSeparatedString() {
        return dnasampleNamesAsEnterSeparatedString;
    }

    public void setDnasampleNamesAsEnterSeparatedString(String dnasampleNamesAsEnterSeparatedString) {
        this.dnasampleNamesAsEnterSeparatedString = dnasampleNamesAsEnterSeparatedString;
    }

    public String getGermplasmNamesAsEnterSeparatedString() {
        return germplasmNamesAsEnterSeparatedString;
    }

    public void setGermplasmNamesAsEnterSeparatedString(String germplasmNamesAsEnterSeparatedString) {
        this.germplasmNamesAsEnterSeparatedString = germplasmNamesAsEnterSeparatedString;
    }

    public Integer getDnasampleIDStartRange() {
        return dnasampleIDStartRange;
    }

    public void setDnasampleIDStartRange(Integer dnasampleIDStartRange) {
        this.dnasampleIDStartRange = dnasampleIDStartRange;
    }

    public Integer getGermplasmIDStartRange() {
        return germplasmIDStartRange;
    }

    public void setGermplasmIDStartRange(Integer germplasmIDStartRange) {
        this.germplasmIDStartRange = germplasmIDStartRange;
    }

    public Integer getDnasampleIDEndRange() {
        return dnasampleIDEndRange;
    }

    public void setDnasampleIDEndRange(Integer dnasampleIDEndRange) {
        this.dnasampleIDEndRange = dnasampleIDEndRange;
    }

    public Integer getGermplasmIDEndRange() {
        return germplasmIDEndRange;
    }

    public void setGermplasmIDEndRange(Integer germplasmIDEndRange) {
        this.germplasmIDEndRange = germplasmIDEndRange;
    }

    public DatasetRecord getDatasetRecord() {
        return datasetRecord;
    }

    public void setDatasetRecord(DatasetRecord datasetRecord) {
        this.datasetRecord = datasetRecord;
    }

    public String getDnasampleNamesAsCommaSeparatedString() {
        return dnasampleNamesAsCommaSeparatedString;
    }

    public void setDnasampleNamesAsCommaSeparatedString(String dnasampleNamesAsCommaSeparatedString) {
        this.dnasampleNamesAsCommaSeparatedString = dnasampleNamesAsCommaSeparatedString;
    }

    public String getGermplasmNamesAsCommaSeparatedString() {
        return germplasmNamesAsCommaSeparatedString;
    }

    public void setGermplasmNamesAsCommaSeparatedString(String germplasmNamesAsCommaSeparatedString) {
        this.germplasmNamesAsCommaSeparatedString = germplasmNamesAsCommaSeparatedString;
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
}
