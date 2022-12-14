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
public class Createorganization extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -468493701;

    /**
     * The parameter <code>public.createorganization.orgname</code>.
     */
    public static final Parameter<String> ORGNAME = createParameter("orgname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createorganization.orgaddress</code>.
     */
    public static final Parameter<String> ORGADDRESS = createParameter("orgaddress", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createorganization.orgwebsite</code>.
     */
    public static final Parameter<String> ORGWEBSITE = createParameter("orgwebsite", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createorganization.createdby</code>.
     */
    public static final Parameter<Integer> CREATEDBY = createParameter("createdby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createorganization.createddate</code>.
     */
    public static final Parameter<Date> CREATEDDATE = createParameter("createddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.createorganization.modifiedby</code>.
     */
    public static final Parameter<Integer> MODIFIEDBY = createParameter("modifiedby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createorganization.modifieddate</code>.
     */
    public static final Parameter<Date> MODIFIEDDATE = createParameter("modifieddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.createorganization.orgstatus</code>.
     */
    public static final Parameter<Integer> ORGSTATUS = createParameter("orgstatus", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createorganization.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Createorganization() {
        super("createorganization", Public.PUBLIC);

        addInParameter(ORGNAME);
        addInParameter(ORGADDRESS);
        addInParameter(ORGWEBSITE);
        addInParameter(CREATEDBY);
        addInParameter(CREATEDDATE);
        addInParameter(MODIFIEDBY);
        addInParameter(MODIFIEDDATE);
        addInParameter(ORGSTATUS);
        addOutParameter(ID);
    }

    /**
     * Set the <code>orgname</code> parameter IN value to the routine
     */
    public void setOrgname(String value) {
        setValue(ORGNAME, value);
    }

    /**
     * Set the <code>orgaddress</code> parameter IN value to the routine
     */
    public void setOrgaddress(String value) {
        setValue(ORGADDRESS, value);
    }

    /**
     * Set the <code>orgwebsite</code> parameter IN value to the routine
     */
    public void setOrgwebsite(String value) {
        setValue(ORGWEBSITE, value);
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
     * Set the <code>orgstatus</code> parameter IN value to the routine
     */
    public void setOrgstatus(Integer value) {
        setValue(ORGSTATUS, value);
    }

    /**
     * Get the <code>id</code> parameter OUT value from the routine
     */
    public Integer getId() {
        return get(ID);
    }
}
