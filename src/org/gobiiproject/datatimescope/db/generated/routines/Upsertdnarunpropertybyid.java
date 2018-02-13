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
public class Upsertdnarunpropertybyid extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = 89549965;

    /**
     * The parameter <code>public.upsertdnarunpropertybyid.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.upsertdnarunpropertybyid.propertyid</code>.
     */
    public static final Parameter<Integer> PROPERTYID = createParameter("propertyid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.upsertdnarunpropertybyid.propertyvalue</code>.
     */
    public static final Parameter<String> PROPERTYVALUE = createParameter("propertyvalue", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Upsertdnarunpropertybyid() {
        super("upsertdnarunpropertybyid", Public.PUBLIC);

        addInParameter(ID);
        addInParameter(PROPERTYID);
        addInParameter(PROPERTYVALUE);
    }

    /**
     * Set the <code>id</code> parameter IN value to the routine
     */
    public void setId(Integer value) {
        setValue(ID, value);
    }

    /**
     * Set the <code>propertyid</code> parameter IN value to the routine
     */
    public void setPropertyid(Integer value) {
        setValue(PROPERTYID, value);
    }

    /**
     * Set the <code>propertyvalue</code> parameter IN value to the routine
     */
    public void setPropertyvalue(String value) {
        setValue(PROPERTYVALUE, value);
    }
}
