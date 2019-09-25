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

import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MapsetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ReferenceRecord;
import org.gobiiproject.datatimescope.services.ViewModelServiceImpl;
import org.gobiiproject.datatimescope.utils.Utils;

/**
 * User entity
 */
public class LinkageGroupEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private Integer linkageGroupIDStartRange;
	private Integer linkageGroupIDEndRange;
	private Date creationDateStart;
	private Date creationDateEnd;
	
    private List<ReferenceRecord> referenceList;
    private List<MapsetRecord> mapsetList;
    private List<LinkageGroupRecord> linkageGroupList;
    
	public LinkageGroupEntity(){
	    referenceList = new ArrayList<ReferenceRecord>();
	    mapsetList = new ArrayList<MapsetRecord>();
	    linkageGroupList = new ArrayList<LinkageGroupRecord>();
	}

    public Integer getLinkageGroupIDStartRange() {
        // TODO Auto-generated method stub
        return linkageGroupIDStartRange;
    }
	
	public void setLinkageGroupIDStartRange(Integer linkageGroupIDStartRange) {
		this.linkageGroupIDStartRange = linkageGroupIDStartRange;
	}

	public Integer getLinkageGroupIDEndRange() {
		return linkageGroupIDEndRange;
	}

	public void setLinkageGroupIDEndRange(Integer linkageGroupIDEndRange) {
		this.linkageGroupIDEndRange = linkageGroupIDEndRange;
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

	public void setLinkageGroupNames(String[] split) {
		// TODO Auto-generated method stub
		
	}

    public List<ReferenceRecord> getReferenceList() {
        return referenceList;
    }

    public void setReferenceList(List<ReferenceRecord> referenceList) {
        this.referenceList = referenceList;
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

    public String getFiltersAsText() {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        
        if(Utils.isListNotNullOrEmpty(getReferenceList())){
            sb.append("Reference(s):");
            sb.append(Utils.getListNamesToString(getReferenceList(), 1));
        }
        
        if(Utils.isListNotNullOrEmpty(getMapsetList())){
            sb.append("Mapset(s):");
            sb.append(Utils.getListNamesToString(getMapsetList(), 1));
        }

        if(Utils.isListNotNullOrEmpty(getLinkageGroupList())){
            sb.append("Linkage group(s):");
            sb.append(Utils.getListNamesToString(getLinkageGroupList(), 1));
        }

//      private Integer markerIDStartRange;
//      private Integer  markerIDEndRange;
//      private String markerNamesAsCommaSeparatedString;
        
        return sb.toString();
    }

//	public String getSQLReadyLinkageGroupNames() {
//		// TODO Auto-generated method stub
//		int ctr = 0;
//		StringBuilder sb = new StringBuilder();
//		String names = linkageGroupNamesAsEnterSeparatedString;
////		String removeSpaces = linkageGroupNamesAsEnterSeparatedString.replaceAll(", ",",");
////		String removeEnters = removeSpaces.replaceAll(",\n",",");
//		for(String s: names.split("\n")){
//			if(ctr>0)sb.append(",");
//		sb.append(" '"+s.toLowerCase()+"' ");
//		ctr++;
//		}
//		return sb.toString();
//	}


}
