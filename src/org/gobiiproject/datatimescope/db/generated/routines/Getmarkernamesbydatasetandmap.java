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
public class Getmarkernamesbydatasetandmap extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -229282868;

    /**
     * The parameter <code>public.getmarkernamesbydatasetandmap.datasetid</code>.
     */
    public static final Parameter<Integer> DATASETID = createParameter("datasetid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getmarkernamesbydatasetandmap.mapid</code>.
     */
    public static final Parameter<Integer> MAPID = createParameter("mapid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getmarkernamesbydatasetandmap.marker_id</code>.
     */
    public static final Parameter<Integer> MARKER_ID = createParameter("marker_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getmarkernamesbydatasetandmap.marker_name</code>.
     */
    public static final Parameter<String> MARKER_NAME = createParameter("marker_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Getmarkernamesbydatasetandmap() {
        super("getmarkernamesbydatasetandmap", Public.PUBLIC);

        addInParameter(DATASETID);
        addInParameter(MAPID);
        addOutParameter(MARKER_ID);
        addOutParameter(MARKER_NAME);
    }

    /**
     * Set the <code>datasetid</code> parameter IN value to the routine
     */
    public void setDatasetid(Integer value) {
        setValue(DATASETID, value);
    }

    /**
     * Set the <code>mapid</code> parameter IN value to the routine
     */
    public void setMapid(Integer value) {
        setValue(MAPID, value);
    }

    /**
     * Get the <code>marker_id</code> parameter OUT value from the routine
     */
    public Integer getMarkerId() {
        return get(MARKER_ID);
    }

    /**
     * Get the <code>marker_name</code> parameter OUT value from the routine
     */
    public String getMarkerName() {
        return get(MARKER_NAME);
    }
}
