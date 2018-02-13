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
public class Updatemanifest extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = 666947245;

    /**
     * The parameter <code>public.updatemanifest.manifestid</code>.
     */
    public static final Parameter<Integer> MANIFESTID = createParameter("manifestid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatemanifest.manifestname</code>.
     */
    public static final Parameter<String> MANIFESTNAME = createParameter("manifestname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatemanifest.manifestcode</code>.
     */
    public static final Parameter<String> MANIFESTCODE = createParameter("manifestcode", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatemanifest.filepath</code>.
     */
    public static final Parameter<String> FILEPATH = createParameter("filepath", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatemanifest.createdby</code>.
     */
    public static final Parameter<Integer> CREATEDBY = createParameter("createdby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatemanifest.createddate</code>.
     */
    public static final Parameter<Date> CREATEDDATE = createParameter("createddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.updatemanifest.modifiedby</code>.
     */
    public static final Parameter<Integer> MODIFIEDBY = createParameter("modifiedby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatemanifest.modifieddate</code>.
     */
    public static final Parameter<Date> MODIFIEDDATE = createParameter("modifieddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * Create a new routine call instance
     */
    public Updatemanifest() {
        super("updatemanifest", Public.PUBLIC);

        addInParameter(MANIFESTID);
        addInParameter(MANIFESTNAME);
        addInParameter(MANIFESTCODE);
        addInParameter(FILEPATH);
        addInParameter(CREATEDBY);
        addInParameter(CREATEDDATE);
        addInParameter(MODIFIEDBY);
        addInParameter(MODIFIEDDATE);
    }

    /**
     * Set the <code>manifestid</code> parameter IN value to the routine
     */
    public void setManifestid(Integer value) {
        setValue(MANIFESTID, value);
    }

    /**
     * Set the <code>manifestname</code> parameter IN value to the routine
     */
    public void setManifestname(String value) {
        setValue(MANIFESTNAME, value);
    }

    /**
     * Set the <code>manifestcode</code> parameter IN value to the routine
     */
    public void setManifestcode(String value) {
        setValue(MANIFESTCODE, value);
    }

    /**
     * Set the <code>filepath</code> parameter IN value to the routine
     */
    public void setFilepath(String value) {
        setValue(FILEPATH, value);
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
}
