package org.gobiiproject.datatimescope.entity;

import java.io.Serializable;

public class RowColEntity  implements Serializable,Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String  firstRow;
	private String secondRow;
	
	public RowColEntity() {
	}

    public String getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(String firstRow) {
        this.firstRow = firstRow;
    }

    public String getSecondRow() {
        return secondRow;
    }

    public void setSecondRow(String secondRow) {
        this.secondRow = secondRow;
    }

}
