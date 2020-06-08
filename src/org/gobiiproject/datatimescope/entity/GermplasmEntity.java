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
public class GermplasmEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private Integer germplasmIDStartRange;
	private Integer  germplasmIDEndRange;
	private String germplasmNamesAsEnterSeparatedString, germplasmNamesAsCommaSeparatedString;

    private ArrayList<RowColEntity> filterListAsRows;
    
	public GermplasmEntity(){
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
        
         if(germplasmIDStartRange!=null || germplasmIDEndRange!=null){
             
             sb.append("Germplasm Id: ");
             sb.append(Utils.getIdRangeAsString(germplasmIDStartRange,germplasmIDEndRange));
         }else if(germplasmNamesAsEnterSeparatedString!=null) {
        
             if(!germplasmNamesAsEnterSeparatedString.isEmpty()) {
                 sb.append("Germplasm Names:\n");
                 sb.append(germplasmNamesAsEnterSeparatedString);
             }
         }
        
        if(sb.length()>1) {
            returnVal =  sb.toString();
        }
        return returnVal;
    }
    

    public List<RowColEntity> getFilterListAsRows() {
        filterListAsRows = new ArrayList<RowColEntity>();

        RowColEntity rowColEntity = new RowColEntity();
        
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
        return filterListAsRows;
    }
    
    
    public String getGermplasmNamesAsEnterSeparatedString() {
        return germplasmNamesAsEnterSeparatedString;
    }

    public void setGermplasmNamesAsEnterSeparatedString(String germplasmNamesAsEnterSeparatedString) {
        this.germplasmNamesAsEnterSeparatedString = germplasmNamesAsEnterSeparatedString;
    }

    public Integer getGermplasmIDStartRange() {
        return germplasmIDStartRange;
    }

    public void setGermplasmIDStartRange(Integer germplasmIDStartRange) {
        this.germplasmIDStartRange = germplasmIDStartRange;
    }
    public Integer getGermplasmIDEndRange() {
        return germplasmIDEndRange;
    }

    public void setGermplasmIDEndRange(Integer germplasmIDEndRange) {
        this.germplasmIDEndRange = germplasmIDEndRange;
    }
    
    public String getGermplasmNamesAsCommaSeparatedString() {
        return germplasmNamesAsCommaSeparatedString;
    }

    public void setGermplasmNamesAsCommaSeparatedString(String germplasmNamesAsCommaSeparatedString) {
        this.germplasmNamesAsCommaSeparatedString = germplasmNamesAsCommaSeparatedString;
    }

}
