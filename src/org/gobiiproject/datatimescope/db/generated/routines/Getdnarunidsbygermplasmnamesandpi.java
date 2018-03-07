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
public class Getdnarunidsbygermplasmnamesandpi extends AbstractRoutine<Integer> {

    private static final long serialVersionUID = 786071943;

    /**
     * The parameter <code>public.getdnarunidsbygermplasmnamesandpi.RETURN_VALUE</code>.
     */
    public static final Parameter<Integer> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getdnarunidsbygermplasmnamesandpi.germplasmnames</code>.
     */
    public static final Parameter<String> GERMPLASMNAMES = createParameter("germplasmnames", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getdnarunidsbygermplasmnamesandpi.piid</code>.
     */
    public static final Parameter<Integer> PIID = createParameter("piid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getdnarunidsbygermplasmnamesandpi.dnarun_id</code>.
     */
    public static final Parameter<Integer> DNARUN_ID = createParameter("dnarun_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Getdnarunidsbygermplasmnamesandpi() {
        super("getdnarunidsbygermplasmnamesandpi", Public.PUBLIC, org.jooq.impl.SQLDataType.INTEGER);

        setReturnParameter(RETURN_VALUE);
        addInParameter(GERMPLASMNAMES);
        addInParameter(PIID);
        addOutParameter(DNARUN_ID);
    }

    /**
     * Set the <code>germplasmnames</code> parameter IN value to the routine
     */
    public void setGermplasmnames(String value) {
        setValue(GERMPLASMNAMES, value);
    }

    /**
     * Set the <code>piid</code> parameter IN value to the routine
     */
    public void setPiid(Integer value) {
        setValue(PIID, value);
    }

    /**
     * Get the <code>dnarun_id</code> parameter OUT value from the routine
     */
    public Integer getDnarunId() {
        return get(DNARUN_ID);
    }
}
