/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.routines;


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
public class Getprotocolpropertybyname extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -760665862;

    /**
     * The parameter <code>public.getprotocolpropertybyname.protocolid</code>.
     */
    public static final Parameter<Integer> PROTOCOLID = createParameter("protocolid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getprotocolpropertybyname.propertyname</code>.
     */
    public static final Parameter<String> PROPERTYNAME = createParameter("propertyname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getprotocolpropertybyname.property_id</code>.
     */
    public static final Parameter<Integer> PROPERTY_ID = createParameter("property_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getprotocolpropertybyname.property_value</code>.
     */
    public static final Parameter<String> PROPERTY_VALUE = createParameter("property_value", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Getprotocolpropertybyname() {
        super("getprotocolpropertybyname", Public.PUBLIC);

        addInParameter(PROTOCOLID);
        addInParameter(PROPERTYNAME);
        addOutParameter(PROPERTY_ID);
        addOutParameter(PROPERTY_VALUE);
    }

    /**
     * Set the <code>protocolid</code> parameter IN value to the routine
     */
    public void setProtocolid(Integer value) {
        setValue(PROTOCOLID, value);
    }

    /**
     * Set the <code>propertyname</code> parameter IN value to the routine
     */
    public void setPropertyname(String value) {
        setValue(PROPERTYNAME, value);
    }

    /**
     * Get the <code>property_id</code> parameter OUT value from the routine
     */
    public Integer getPropertyId() {
        return get(PROPERTY_ID);
    }

    /**
     * Get the <code>property_value</code> parameter OUT value from the routine
     */
    public String getPropertyValue() {
        return get(PROPERTY_VALUE);
    }
}
