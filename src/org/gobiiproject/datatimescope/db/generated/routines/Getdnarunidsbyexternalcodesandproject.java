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
public class Getdnarunidsbyexternalcodesandproject extends AbstractRoutine<Integer> {

    private static final long serialVersionUID = -1908116231;

    /**
     * The parameter <code>public.getdnarunidsbyexternalcodesandproject.RETURN_VALUE</code>.
     */
    public static final Parameter<Integer> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getdnarunidsbyexternalcodesandproject.externalcodes</code>.
     */
    public static final Parameter<String> EXTERNALCODES = createParameter("externalcodes", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getdnarunidsbyexternalcodesandproject.projectid</code>.
     */
    public static final Parameter<Integer> PROJECTID = createParameter("projectid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getdnarunidsbyexternalcodesandproject.dnarun_id</code>.
     */
    public static final Parameter<Integer> DNARUN_ID = createParameter("dnarun_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Getdnarunidsbyexternalcodesandproject() {
        super("getdnarunidsbyexternalcodesandproject", Public.PUBLIC, org.jooq.impl.SQLDataType.INTEGER);

        setReturnParameter(RETURN_VALUE);
        addInParameter(EXTERNALCODES);
        addInParameter(PROJECTID);
        addOutParameter(DNARUN_ID);
    }

    /**
     * Set the <code>externalcodes</code> parameter IN value to the routine
     */
    public void setExternalcodes(String value) {
        setValue(EXTERNALCODES, value);
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
