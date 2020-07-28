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

/**
 * User entity
 */
public class DatasetSummaryEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private String entityName;
	private String rowCount;
	private String filter;
	private String duration;
	
	public DatasetSummaryEntity(){
		
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getRowCount() {
		return rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
