/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.routines;


import java.sql.Date;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Createdisplay extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -107387064;

    /**
     * The parameter <code>public.createdisplay.tablename</code>.
     */
    public static final Parameter<String> TABLENAME = createParameter("tablename", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createdisplay.columnname</code>.
     */
    public static final Parameter<String> COLUMNNAME = createParameter("columnname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createdisplay.displayname</code>.
     */
    public static final Parameter<String> DISPLAYNAME = createParameter("displayname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createdisplay.createdby</code>.
     */
    public static final Parameter<Integer> CREATEDBY = createParameter("createdby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createdisplay.createddate</code>.
     */
    public static final Parameter<Date> CREATEDDATE = createParameter("createddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.createdisplay.modifiedby</code>.
     */
    public static final Parameter<Integer> MODIFIEDBY = createParameter("modifiedby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createdisplay.modifieddate</code>.
     */
    public static final Parameter<Date> MODIFIEDDATE = createParameter("modifieddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.createdisplay.displayrank</code>.
     */
    public static final Parameter<Integer> DISPLAYRANK = createParameter("displayrank", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createdisplay.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Createdisplay() {
        super("createdisplay", Public.PUBLIC);

        addInParameter(TABLENAME);
        addInParameter(COLUMNNAME);
        addInParameter(DISPLAYNAME);
        addInParameter(CREATEDBY);
        addInParameter(CREATEDDATE);
        addInParameter(MODIFIEDBY);
        addInParameter(MODIFIEDDATE);
        addInParameter(DISPLAYRANK);
        addOutParameter(ID);
    }

    /**
     * Set the <code>tablename</code> parameter IN value to the routine
     */
    public void setTablename(String value) {
        setValue(TABLENAME, value);
    }

    /**
     * Set the <code>columnname</code> parameter IN value to the routine
     */
    public void setColumnname(String value) {
        setValue(COLUMNNAME, value);
    }

    /**
     * Set the <code>displayname</code> parameter IN value to the routine
     */
    public void setDisplayname(String value) {
        setValue(DISPLAYNAME, value);
    }

    /**
     * Set the <code>createdby</code> parameter IN value to the routine
     */
    public void setCreatedby(Integer value) {
        setValue(CREATEDBY, value);
    }

    /**
     * Set the <code>createddate</code> parameter IN value to the routine
     */
    public void setCreateddate(Date value) {
        setValue(CREATEDDATE, value);
    }

    /**
     * Set the <code>modifiedby</code> parameter IN value to the routine
     */
    public void setModifiedby(Integer value) {
        setValue(MODIFIEDBY, value);
    }

    /**
     * Set the <code>modifieddate</code> parameter IN value to the routine
     */
    public void setModifieddate(Date value) {
        setValue(MODIFIEDDATE, value);
    }

    /**
     * Set the <code>displayrank</code> parameter IN value to the routine
     */
    public void setDisplayrank(Integer value) {
        setValue(DISPLAYRANK, value);
    }

    /**
     * Get the <code>id</code> parameter OUT value from the routine
     */
    public Integer getId() {
        return get(ID);
    }
}
