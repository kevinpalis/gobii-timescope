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
public class DnarunEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private Integer dnarunIDStartRange, dnasampleIDStartRange, germplasmIDStartRange;
	private Integer dnarunIDEndRange, dnasampleIDEndRange, germplasmIDEndRange;
	private String dnarunNamesAsEnterSeparatedString, dnasampleNamesAsEnterSeparatedString, germplasmNamesAsEnterSeparatedString;
	private Date creationDateStart;
	private Date creationDateEnd;
	private ProjectRecord projectRecord;
	private ExperimentRecord experimentRecord;
	private DatasetRecord datasetRecord;
	
	public DnarunEntity(){
		
	}

    public String getSQLReadyDnarunNames() {
        // TODO Auto-generated method stub
        int ctr = 0;
        StringBuilder sb = new StringBuilder();
        String names = dnarunNamesAsEnterSeparatedString;
        
        for(String s: names.split("\n")){
            if(ctr>0)sb.append(",");
        sb.append(" '"+s.toLowerCase()+"' ");
        ctr++;
        }
        return sb.toString();
    }

    public String getSQLReadyDnasampleNames() {
        // TODO Auto-generated method stub
        int ctr = 0;
        StringBuilder sb = new StringBuilder();
        String names = dnasampleNamesAsEnterSeparatedString;
        
        for(String s: names.split("\n")){
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
        
        for(String s: names.split("\n")){
            if(ctr>0)sb.append(",");
        sb.append(" '"+s.toLowerCase()+"' ");
        ctr++;
        }
        return sb.toString();
    }
    
    public String getCompleteFiltersAsText() {
        String returnVal = " (not filtered)";
        StringBuilder sb = new StringBuilder();
        
        if(dnarunIDStartRange!=null || dnarunIDEndRange!=null){
            
            sb.append("DNArun Id: ");
            sb.append(Utils.getIdRangeAsString(dnarunIDStartRange,dnarunIDEndRange));
        }else if(dnarunNamesAsEnterSeparatedString!=null) {

            if(!dnarunNamesAsEnterSeparatedString.isEmpty()) {
                sb.append("DNArun Names:\n");
                sb.append(dnarunNamesAsEnterSeparatedString);
            }
        }
        
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
    
    public String getFiltersAsTextWithDelimiter(String delim) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        
        if(Utils.isRecordNotNullOrEmpty(projectRecord)){
            sb.append("\nProject(s):\n");
            sb.append(projectRecord.getName());
        }

        if(Utils.isRecordNotNullOrEmpty(experimentRecord)){
            sb.append("\nExperiment(s):\n");
            sb.append(experimentRecord.getName());
        }
        
        if(Utils.isRecordNotNullOrEmpty(datasetRecord)){
            sb.append("\nDataset(s):\n");
            sb.append(datasetRecord.getName());
        }
      
        return sb.toString();
    }
    
	public Integer getDnarunIDStartRange() {
		return dnarunIDStartRange;
	}

	public void setDnarunIDStartRange(Integer dnarunIDStartRange) {
		this.dnarunIDStartRange = dnarunIDStartRange;
	}

	public Integer getDnarunIDEndRange() {
		return dnarunIDEndRange;
	}

	public void setDnarunIDEndRange(Integer dnarunIDEndRange) {
		this.dnarunIDEndRange = dnarunIDEndRange;
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

	public String getDnarunNamesAsEnterSeparatedString() {
		return dnarunNamesAsEnterSeparatedString;
	}

	public void setDnarunNamesAsEnterSeparatedString(String dnarunNamesAsEnterSeparatedString) {
		this.dnarunNamesAsEnterSeparatedString = dnarunNamesAsEnterSeparatedString;
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
}
