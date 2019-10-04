/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.routines;


import java.math.BigDecimal;
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
public class Createlinkagegroup2 extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -254527331;

    /**
     * The parameter <code>public.createlinkagegroup.linkagegroupname</code>.
     */
    public static final Parameter<String> LINKAGEGROUPNAME = createParameter("linkagegroupname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createlinkagegroup.linkagegroupstart</code>.
     */
    public static final Parameter<BigDecimal> LINKAGEGROUPSTART = createParameter("linkagegroupstart", org.jooq.impl.SQLDataType.NUMERIC, false, false);

    /**
     * The parameter <code>public.createlinkagegroup.linkagegroupstop</code>.
     */
    public static final Parameter<BigDecimal> LINKAGEGROUPSTOP = createParameter("linkagegroupstop", org.jooq.impl.SQLDataType.NUMERIC, false, false);

    /**
     * The parameter <code>public.createlinkagegroup.mapid</code>.
     */
    public static final Parameter<Integer> MAPID = createParameter("mapid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createlinkagegroup.createdby</code>.
     */
    public static final Parameter<Integer> CREATEDBY = createParameter("createdby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createlinkagegroup.createddate</code>.
     */
    public static final Parameter<Date> CREATEDDATE = createParameter("createddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.createlinkagegroup.modifiedby</code>.
     */
    public static final Parameter<Integer> MODIFIEDBY = createParameter("modifiedby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createlinkagegroup.modifieddate</code>.
     */
    public static final Parameter<Date> MODIFIEDDATE = createParameter("modifieddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.createlinkagegroup.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Createlinkagegroup2() {
        super("createlinkagegroup", Public.PUBLIC);

        addInParameter(LINKAGEGROUPNAME);
        addInParameter(LINKAGEGROUPSTART);
        addInParameter(LINKAGEGROUPSTOP);
        addInParameter(MAPID);
        addInParameter(CREATEDBY);
        addInParameter(CREATEDDATE);
        addInParameter(MODIFIEDBY);
        addInParameter(MODIFIEDDATE);
        addOutParameter(ID);
        setOverloaded(true);
    }

    /**
     * Set the <code>linkagegroupname</code> parameter IN value to the routine
     */
    public void setLinkagegroupname(String value) {
        setValue(LINKAGEGROUPNAME, value);
    }

    /**
     * Set the <code>linkagegroupstart</code> parameter IN value to the routine
     */
    public void setLinkagegroupstart(BigDecimal value) {
        setValue(LINKAGEGROUPSTART, value);
    }

    /**
     * Set the <code>linkagegroupstop</code> parameter IN value to the routine
     */
    public void setLinkagegroupstop(BigDecimal value) {
        setValue(LINKAGEGROUPSTOP, value);
    }

    /**
     * Set the <code>mapid</code> parameter IN value to the routine
     */
    public void setMapid(Integer value) {
        setValue(MAPID, value);
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
     * Get the <code>id</code> parameter OUT value from the routine
     */
    public Integer getId() {
        return get(ID);
    }
}
