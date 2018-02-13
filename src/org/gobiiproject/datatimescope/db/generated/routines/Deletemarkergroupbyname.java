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
public class Deletemarkergroupbyname extends AbstractRoutine<Integer> {

    private static final long serialVersionUID = 77494212;

    /**
     * The parameter <code>public.deletemarkergroupbyname.RETURN_VALUE</code>.
     */
    public static final Parameter<Integer> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.deletemarkergroupbyname._name</code>.
     */
    public static final Parameter<String> _NAME = createParameter("_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Deletemarkergroupbyname() {
        super("deletemarkergroupbyname", Public.PUBLIC, org.jooq.impl.SQLDataType.INTEGER);

        setReturnParameter(RETURN_VALUE);
        addInParameter(_NAME);
    }

    /**
     * Set the <code>_name</code> parameter IN value to the routine
     */
    public void set_Name(String value) {
        setValue(_NAME, value);
    }

    /**
     * Set the <code>_name</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void set_Name(Field<String> field) {
        setField(_NAME, field);
    }
}
