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
public class Getmapsetpropertybyid extends AbstractRoutine<String> {

    private static final long serialVersionUID = 1099686560;

    /**
     * The parameter <code>public.getmapsetpropertybyid.RETURN_VALUE</code>.
     */
    public static final Parameter<String> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmapsetpropertybyid.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getmapsetpropertybyid.propertyid</code>.
     */
    public static final Parameter<Integer> PROPERTYID = createParameter("propertyid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Getmapsetpropertybyid() {
        super("getmapsetpropertybyid", Public.PUBLIC, org.jooq.impl.SQLDataType.CLOB);

        setReturnParameter(RETURN_VALUE);
        addInParameter(ID);
        addInParameter(PROPERTYID);
    }

    /**
     * Set the <code>id</code> parameter IN value to the routine
     */
    public void setId(Integer value) {
        setValue(ID, value);
    }

    /**
     * Set the <code>id</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setId(Field<Integer> field) {
        setField(ID, field);
    }

    /**
     * Set the <code>propertyid</code> parameter IN value to the routine
     */
    public void setPropertyid(Integer value) {
        setValue(PROPERTYID, value);
    }

    /**
     * Set the <code>propertyid</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPropertyid(Field<Integer> field) {
        setField(PROPERTYID, field);
    }
}
