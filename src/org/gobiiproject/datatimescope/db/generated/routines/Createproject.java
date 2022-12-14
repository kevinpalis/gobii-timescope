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
public class Createproject extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -188116054;

    /**
     * The parameter <code>public.createproject.projectname</code>.
     */
    public static final Parameter<String> PROJECTNAME = createParameter("projectname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createproject.projectcode</code>.
     */
    public static final Parameter<String> PROJECTCODE = createParameter("projectcode", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createproject.projectdescription</code>.
     */
    public static final Parameter<String> PROJECTDESCRIPTION = createParameter("projectdescription", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createproject.picontact</code>.
     */
    public static final Parameter<Integer> PICONTACT = createParameter("picontact", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createproject.createdby</code>.
     */
    public static final Parameter<Integer> CREATEDBY = createParameter("createdby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createproject.createddate</code>.
     */
    public static final Parameter<Date> CREATEDDATE = createParameter("createddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.createproject.modifiedby</code>.
     */
    public static final Parameter<Integer> MODIFIEDBY = createParameter("modifiedby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createproject.modifieddate</code>.
     */
    public static final Parameter<Date> MODIFIEDDATE = createParameter("modifieddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.createproject.projectstatus</code>.
     */
    public static final Parameter<Integer> PROJECTSTATUS = createParameter("projectstatus", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createproject.projectid</code>.
     */
    public static final Parameter<Integer> PROJECTID = createParameter("projectid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Createproject() {
        super("createproject", Public.PUBLIC);

        addInParameter(PROJECTNAME);
        addInParameter(PROJECTCODE);
        addInParameter(PROJECTDESCRIPTION);
        addInParameter(PICONTACT);
        addInParameter(CREATEDBY);
        addInParameter(CREATEDDATE);
        addInParameter(MODIFIEDBY);
        addInParameter(MODIFIEDDATE);
        addInParameter(PROJECTSTATUS);
        addOutParameter(PROJECTID);
    }

    /**
     * Set the <code>projectname</code> parameter IN value to the routine
     */
    public void setProjectname(String value) {
        setValue(PROJECTNAME, value);
    }

    /**
     * Set the <code>projectcode</code> parameter IN value to the routine
     */
    public void setProjectcode(String value) {
        setValue(PROJECTCODE, value);
    }

    /**
     * Set the <code>projectdescription</code> parameter IN value to the routine
     */
    public void setProjectdescription(String value) {
        setValue(PROJECTDESCRIPTION, value);
    }

    /**
     * Set the <code>picontact</code> parameter IN value to the routine
     */
    public void setPicontact(Integer value) {
        setValue(PICONTACT, value);
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
     * Set the <code>projectstatus</code> parameter IN value to the routine
     */
    public void setProjectstatus(Integer value) {
        setValue(PROJECTSTATUS, value);
    }

    /**
     * Get the <code>projectid</code> parameter OUT value from the routine
     */
    public Integer getProjectid() {
        return get(PROJECTID);
    }
}
