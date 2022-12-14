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
public class Getdnarunidsbyproject extends AbstractRoutine<Integer> {

    private static final long serialVersionUID = -1177334134;

    /**
     * The parameter <code>public.getdnarunidsbyproject.RETURN_VALUE</code>.
     */
    public static final Parameter<Integer> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getdnarunidsbyproject.projectid</code>.
     */
    public static final Parameter<Integer> PROJECTID = createParameter("projectid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getdnarunidsbyproject.dnarun_id</code>.
     */
    public static final Parameter<Integer> DNARUN_ID = createParameter("dnarun_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Getdnarunidsbyproject() {
        super("getdnarunidsbyproject", Public.PUBLIC, org.jooq.impl.SQLDataType.INTEGER);

        setReturnParameter(RETURN_VALUE);
        addInParameter(PROJECTID);
        addOutParameter(DNARUN_ID);
    }

    /**
     * Set the <code>projectid</code> parameter IN value to the routine
     */
    public void setProjectid(Integer value) {
        setValue(PROJECTID, value);
    }

    /**
     * Get the <code>dnarun_id</code> parameter OUT value from the routine
     */
    public Integer getDnarunId() {
        return get(DNARUN_ID);
    }
}
