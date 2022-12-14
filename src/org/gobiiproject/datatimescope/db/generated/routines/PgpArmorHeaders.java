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
public class PgpArmorHeaders extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -840334230;

    /**
     * The parameter <code>public.pgp_armor_headers._1</code>.
     */
    public static final Parameter<String> _1 = createParameter("_1", org.jooq.impl.SQLDataType.CLOB, false, true);

    /**
     * The parameter <code>public.pgp_armor_headers.key</code>.
     */
    public static final Parameter<String> KEY = createParameter("key", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.pgp_armor_headers.value</code>.
     */
    public static final Parameter<String> VALUE = createParameter("value", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public PgpArmorHeaders() {
        super("pgp_armor_headers", Public.PUBLIC);

        addInParameter(_1);
        addOutParameter(KEY);
        addOutParameter(VALUE);
    }

    /**
     * Set the <code>_1</code> parameter IN value to the routine
     */
    public void set__1(String value) {
        setValue(_1, value);
    }

    /**
     * Get the <code>key</code> parameter OUT value from the routine
     */
    public String getKey() {
        return get(KEY);
    }

    /**
     * Get the <code>value</code> parameter OUT value from the routine
     */
    public String getValue() {
        return get(VALUE);
    }
}
