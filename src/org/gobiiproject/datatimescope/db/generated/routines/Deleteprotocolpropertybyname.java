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
public class Deleteprotocolpropertybyname extends AbstractRoutine<String> {

    private static final long serialVersionUID = 94913825;

    /**
     * The parameter <code>public.deleteprotocolpropertybyname.RETURN_VALUE</code>.
     */
    public static final Parameter<String> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.deleteprotocolpropertybyname.protocolid</code>.
     */
    public static final Parameter<Integer> PROTOCOLID = createParameter("protocolid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.deleteprotocolpropertybyname.propertyname</code>.
     */
    public static final Parameter<String> PROPERTYNAME = createParameter("propertyname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Deleteprotocolpropertybyname() {
        super("deleteprotocolpropertybyname", Public.PUBLIC, org.jooq.impl.SQLDataType.CLOB);

        setReturnParameter(RETURN_VALUE);
        addInParameter(PROTOCOLID);
        addInParameter(PROPERTYNAME);
    }

    /**
     * Set the <code>protocolid</code> parameter IN value to the routine
     */
    public void setProtocolid(Integer value) {
        setValue(PROTOCOLID, value);
    }

    /**
     * Set the <code>protocolid</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setProtocolid(Field<Integer> field) {
        setField(PROTOCOLID, field);
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
