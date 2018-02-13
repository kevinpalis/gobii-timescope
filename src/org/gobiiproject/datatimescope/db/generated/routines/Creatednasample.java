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
public class Creatednasample extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -1758284883;

    /**
     * The parameter <code>public.creatednasample.dnasamplename</code>.
     */
    public static final Parameter<String> DNASAMPLENAME = createParameter("dnasamplename", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.creatednasample.dnasamplecode</code>.
     */
    public static final Parameter<String> DNASAMPLECODE = createParameter("dnasamplecode", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.creatednasample.dnasampleplatename</code>.
     */
    public static final Parameter<String> DNASAMPLEPLATENAME = createParameter("dnasampleplatename", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.creatednasample.dnasamplenum</code>.
     */
    public static final Parameter<String> DNASAMPLENUM = createParameter("dnasamplenum", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.creatednasample.wellrow</code>.
     */
    public static final Parameter<String> WELLROW = createParameter("wellrow", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.creatednasample.wellcol</code>.
     */
    public static final Parameter<String> WELLCOL = createParameter("wellcol", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.creatednasample.projectid</code>.
     */
    public static final Parameter<Integer> PROJECTID = createParameter("projectid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.creatednasample.germplasmid</code>.
     */
    public static final Parameter<Integer> GERMPLASMID = createParameter("germplasmid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.creatednasample.createdby</code>.
     */
    public static final Parameter<Integer> CREATEDBY = createParameter("createdby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.creatednasample.createddate</code>.
     */
    public static final Parameter<Date> CREATEDDATE = createParameter("createddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.creatednasample.modifiedby</code>.
     */
    public static final Parameter<Integer> MODIFIEDBY = createParameter("modifiedby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.creatednasample.modifieddate</code>.
     */
    public static final Parameter<Date> MODIFIEDDATE = createParameter("modifieddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.creatednasample.dnasamplestatus</code>.
     */
    public static final Parameter<Integer> DNASAMPLESTATUS = createParameter("dnasamplestatus", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.creatednasample.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Creatednasample() {
        super("creatednasample", Public.PUBLIC);

        addInParameter(DNASAMPLENAME);
        addInParameter(DNASAMPLECODE);
        addInParameter(DNASAMPLEPLATENAME);
        addInParameter(DNASAMPLENUM);
        addInParameter(WELLROW);
        addInParameter(WELLCOL);
        addInParameter(PROJECTID);
        addInParameter(GERMPLASMID);
        addInParameter(CREATEDBY);
        addInParameter(CREATEDDATE);
        addInParameter(MODIFIEDBY);
        addInParameter(MODIFIEDDATE);
        addInParameter(DNASAMPLESTATUS);
        addOutParameter(ID);
    }

    /**
     * Set the <code>dnasamplename</code> parameter IN value to the routine
     */
    public void setDnasamplename(String value) {
        setValue(DNASAMPLENAME, value);
    }

    /**
     * Set the <code>dnasamplecode</code> parameter IN value to the routine
     */
    public void setDnasamplecode(String value) {
        setValue(DNASAMPLECODE, value);
    }

    /**
     * Set the <code>dnasampleplatename</code> parameter IN value to the routine
     */
    public void setDnasampleplatename(String value) {
        setValue(DNASAMPLEPLATENAME, value);
    }

    /**
     * Set the <code>dnasamplenum</code> parameter IN value to the routine
     */
    public void setDnasamplenum(String value) {
        setValue(DNASAMPLENUM, value);
    }

    /**
     * Set the <code>wellrow</code> parameter IN value to the routine
     */
    public void setWellrow(String value) {
        setValue(WELLROW, value);
    }

    /**
     * Set the <code>wellcol</code> parameter IN value to the routine
     */
    public void setWellcol(String value) {
        setValue(WELLCOL, value);
    }

    /**
     * Set the <code>projectid</code> parameter IN value to the routine
     */
    public void setProjectid(Integer value) {
        setValue(PROJECTID, value);
    }

    /**
     * Set the <code>germplasmid</code> parameter IN value to the routine
     */
    public void setGermplasmid(Integer value) {
        setValue(GERMPLASMID, value);
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
     * Set the <code>dnasamplestatus</code> parameter IN value to the routine
     */
    public void setDnasamplestatus(Integer value) {
        setValue(DNASAMPLESTATUS, value);
    }

    /**
     * Get the <code>id</code> parameter OUT value from the routine
     */
    public Integer getId() {
        return get(ID);
    }
}
