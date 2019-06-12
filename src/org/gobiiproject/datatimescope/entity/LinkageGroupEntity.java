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

import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;

/**
 * User entity
 */
public class LinkageGroupEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private Integer linkageGroupIDStartRange;
	private Integer linkageGroupIDEndRange;
	private String linkageGroupNamesAsEnterSeparatedString;
	private List<String> linkageGroupNames;
	private Date creationDateStart;
	private Date creationDateEnd;
	private CvRecord linkageGroupTypeRecord;
	private ContactRecord piRecord;
	private ContactRecord createdByContactRecord;
	
	public LinkageGroupEntity(){
		
	}
	
	public List<String> getLinkageGroupNames() {
		return linkageGroupNames;
	}
	public void setLinkageGroupNames(List<String> linkageGroupNames) {
		this.linkageGroupNames = linkageGroupNames;
	}
	public ContactRecord getPiRecord() {
		return piRecord;
	}

	public void setPiRecord(ContactRecord piRecord) {
		if(piRecord.get(2)!=null) this.piRecord = piRecord;
		else this.piRecord = null;
	}

	public ContactRecord getCreatedByContactRecord() {
		return createdByContactRecord;
	}

	public void setCreatedByContactRecord(ContactRecord createdByContactRecord) {
		this.createdByContactRecord = createdByContactRecord;
	}

	public CvRecord getLinkageGroupTypeRecord() {
		return linkageGroupTypeRecord;
	}

	public void setLinkageGroupTypeRecord(CvRecord linkageGroupTypeRecord) {
		if(linkageGroupTypeRecord.get(0)!=null) this.linkageGroupTypeRecord = linkageGroupTypeRecord;
		else this.linkageGroupTypeRecord = null;
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

	public String getLinkageGroupNamesAsEnterSeparatedString() {
		return linkageGroupNamesAsEnterSeparatedString;
	}

	public void setLinkageGroupNamesAsEnterSeparatedString(String linkageGroupNamesAsEnterSeparatedString) {
		this.linkageGroupNamesAsEnterSeparatedString = linkageGroupNamesAsEnterSeparatedString;
	}

	public void setLinkageGroupNames(String[] split) {
		// TODO Auto-generated method stub
		
	}

	public String getSQLReadyLinkageGroupNames() {
		// TODO Auto-generated method stub
		int ctr = 0;
		StringBuilder sb = new StringBuilder();
		String names = linkageGroupNamesAsEnterSeparatedString;
//		String removeSpaces = linkageGroupNamesAsEnterSeparatedString.replaceAll(", ",",");
//		String removeEnters = removeSpaces.replaceAll(",\n",",");
		for(String s: names.split("\n")){
			if(ctr>0)sb.append(",");
		sb.append(" '"+s.toLowerCase()+"' ");
		ctr++;
		}
		return sb.toString();
	}

	public Integer getLinkageGroupIDStartRange() {
		// TODO Auto-generated method stub
		return null;
	}

}
