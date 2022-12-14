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
public class Upsertprotocolpropertybyname extends AbstractRoutine<Integer> {

    private static final long serialVersionUID = -1053299163;

    /**
     * The parameter <code>public.upsertprotocolpropertybyname.RETURN_VALUE</code>.
     */
    public static final Parameter<Integer> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.upsertprotocolpropertybyname.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.upsertprotocolpropertybyname.propertyname</code>.
     */
    public static final Parameter<String> PROPERTYNAME = createParameter("propertyname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.upsertprotocolpropertybyname.propertyvalue</code>.
     */
    public static final Parameter<String> PROPERTYVALUE = createParameter("propertyvalue", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Upsertprotocolpropertybyname() {
        super("upsertprotocolpropertybyname", Public.PUBLIC, org.jooq.impl.SQLDataType.INTEGER);

        setReturnParameter(RETURN_VALUE);
        addInParameter(ID);
        addInParameter(PROPERTYNAME);
        addInParameter(PROPERTYVALUE);
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

    /**
     * Set the <code>propertyvalue</code> parameter IN value to the routine
     */
    public void setPropertyvalue(String value) {
        setValue(PROPERTYVALUE, value);
    }

    /**
     * Set the <code>propertyvalue</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPropertyvalue(Field<String> field) {
        setField(PROPERTYVALUE, field);
    }
}
