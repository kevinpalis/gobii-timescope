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
public class Getmatrixposofsamples extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -1625948327;

    /**
     * The parameter <code>public.getmatrixposofsamples.samplelist</code>.
     */
    public static final Parameter<String> SAMPLELIST = createParameter("samplelist", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmatrixposofsamples.datasettypeid</code>.
     */
    public static final Parameter<Integer> DATASETTYPEID = createParameter("datasettypeid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getmatrixposofsamples.dataset_id</code>.
     */
    public static final Parameter<Integer> DATASET_ID = createParameter("dataset_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getmatrixposofsamples.positions</code>.
     */
    public static final Parameter<String> POSITIONS = createParameter("positions", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Getmatrixposofsamples() {
        super("getmatrixposofsamples", Public.PUBLIC);

        addInParameter(SAMPLELIST);
        addInParameter(DATASETTYPEID);
        addOutParameter(DATASET_ID);
        addOutParameter(POSITIONS);
    }

    /**
     * Set the <code>samplelist</code> parameter IN value to the routine
     */
    public void setSamplelist(String value) {
        setValue(SAMPLELIST, value);
    }

    /**
     * Set the <code>datasettypeid</code> parameter IN value to the routine
     */
    public void setDatasettypeid(Integer value) {
        setValue(DATASETTYPEID, value);
    }

    /**
     * Get the <code>dataset_id</code> parameter OUT value from the routine
     */
    public Integer getDatasetId() {
        return get(DATASET_ID);
    }

    /**
     * Get the <code>positions</code> parameter OUT value from the routine
     */
    public String getPositions() {
        return get(POSITIONS);
    }
}
