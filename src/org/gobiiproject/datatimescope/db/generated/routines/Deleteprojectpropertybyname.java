/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.routines;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.jooq.Field;
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
public class Deleteprojectpropertybyname extends AbstractRoutine<String> {

    private static final long serialVersionUID = 1538953507;

    /**
     * The parameter <code>public.deleteprojectpropertybyname.RETURN_VALUE</code>.
     */
    public static final Parameter<String> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.deleteprojectpropertybyname.projectid</code>.
     */
    public static final Parameter<Integer> PROJECTID = createParameter("projectid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.deleteprojectpropertybyname.propertyname</code>.
     */
    public static final Parameter<String> PROPERTYNAME = createParameter("propertyname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Deleteprojectpropertybyname() {
        super("deleteprojectpropertybyname", Public.PUBLIC, org.jooq.impl.SQLDataType.CLOB);

        setReturnParameter(RETURN_VALUE);
        addInParameter(PROJECTID);
        addInParameter(PROPERTYNAME);
    }

    /**
     * Set the <code>projectid</code> parameter IN value to the routine
     */
    public void setProjectid(Integer value) {
        setValue(PROJECTID, value);
    }

    /**
     * Set the <code>projectid</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setProjectid(Field<Integer> field) {
        setField(PROJECTID, field);
    }

    /**
     * Set the <code>propertyname</code> parameter IN value to the routine
     */
    public void setPropertyname(String value) {
        setValue(PROPERTYNAME, value);
    }

    /**
     * Set the <code>propertyname</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPropertyname(Field<String> field) {
        setField(PROPERTYNAME, field);
    }
}
