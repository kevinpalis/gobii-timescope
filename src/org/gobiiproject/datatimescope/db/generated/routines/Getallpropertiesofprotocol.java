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
public class Getallpropertiesofprotocol extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -1909667972;

    /**
     * The parameter <code>public.getallpropertiesofprotocol.protocolid</code>.
     */
    public static final Parameter<Integer> PROTOCOLID = createParameter("protocolid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getallpropertiesofprotocol.property_id</code>.
     */
    public static final Parameter<Integer> PROPERTY_ID = createParameter("property_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getallpropertiesofprotocol.property_name</code>.
     */
    public static final Parameter<String> PROPERTY_NAME = createParameter("property_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getallpropertiesofprotocol.property_value</code>.
     */
    public static final Parameter<String> PROPERTY_VALUE = createParameter("property_value", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Getallpropertiesofprotocol() {
        super("getallpropertiesofprotocol", Public.PUBLIC);

        addInParameter(PROTOCOLID);
        addOutParameter(PROPERTY_ID);
        addOutParameter(PROPERTY_NAME);
        addOutParameter(PROPERTY_VALUE);
    }

    /**
     * Set the <code>protocolid</code> parameter IN value to the routine
     */
    public void setProtocolid(Integer value) {
        setValue(PROTOCOLID, value);
    }

    /**
     * Get the <code>property_id</code> parameter OUT value from the routine
     */
    public Integer getPropertyId() {
        return get(PROPERTY_ID);
    }

    /**
     * Get the <code>property_name</code> parameter OUT value from the routine
     */
    public String getPropertyName() {
        return get(PROPERTY_NAME);
    }

    /**
     * Get the <code>property_value</code> parameter OUT value from the routine
     */
    public String getPropertyValue() {
        return get(PROPERTY_VALUE);
    }
}
