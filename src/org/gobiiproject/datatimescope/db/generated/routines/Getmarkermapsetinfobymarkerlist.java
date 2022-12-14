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
public class Getmarkermapsetinfobymarkerlist extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -219549145;

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.markerlist</code>.
     */
    public static final Parameter<String> MARKERLIST = createParameter("markerlist", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.marker_name</code>.
     */
    public static final Parameter<String> MARKER_NAME = createParameter("marker_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.platform_name</code>.
     */
    public static final Parameter<String> PLATFORM_NAME = createParameter("platform_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.mapset_id</code>.
     */
    public static final Parameter<Integer> MAPSET_ID = createParameter("mapset_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.mapset_name</code>.
     */
    public static final Parameter<String> MAPSET_NAME = createParameter("mapset_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.mapset_type</code>.
     */
    public static final Parameter<String> MAPSET_TYPE = createParameter("mapset_type", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.linkage_group_name</code>.
     */
    public static final Parameter<String> LINKAGE_GROUP_NAME = createParameter("linkage_group_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.linkage_group_start</code>.
     */
    public static final Parameter<String> LINKAGE_GROUP_START = createParameter("linkage_group_start", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.linkage_group_stop</code>.
     */
    public static final Parameter<String> LINKAGE_GROUP_STOP = createParameter("linkage_group_stop", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.marker_linkage_group_start</code>.
     */
    public static final Parameter<String> MARKER_LINKAGE_GROUP_START = createParameter("marker_linkage_group_start", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.marker_linkage_group_stop</code>.
     */
    public static final Parameter<String> MARKER_LINKAGE_GROUP_STOP = createParameter("marker_linkage_group_stop", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.reference_name</code>.
     */
    public static final Parameter<String> REFERENCE_NAME = createParameter("reference_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkermapsetinfobymarkerlist.reference_version</code>.
     */
    public static final Parameter<String> REFERENCE_VERSION = createParameter("reference_version", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Getmarkermapsetinfobymarkerlist() {
        super("getmarkermapsetinfobymarkerlist", Public.PUBLIC);

        addInParameter(MARKERLIST);
        addOutParameter(MARKER_NAME);
        addOutParameter(PLATFORM_NAME);
        addOutParameter(MAPSET_ID);
        addOutParameter(MAPSET_NAME);
        addOutParameter(MAPSET_TYPE);
        addOutParameter(LINKAGE_GROUP_NAME);
        addOutParameter(LINKAGE_GROUP_START);
        addOutParameter(LINKAGE_GROUP_STOP);
        addOutParameter(MARKER_LINKAGE_GROUP_START);
        addOutParameter(MARKER_LINKAGE_GROUP_STOP);
        addOutParameter(REFERENCE_NAME);
        addOutParameter(REFERENCE_VERSION);
    }

    /**
     * Set the <code>markerlist</code> parameter IN value to the routine
     */
    public void setMarkerlist(String value) {
        setValue(MARKERLIST, value);
    }

    /**
     * Get the <code>marker_name</code> parameter OUT value from the routine
     */
    public String getMarkerName() {
        return get(MARKER_NAME);
    }

    /**
     * Get the <code>platform_name</code> parameter OUT value from the routine
     */
    public String getPlatformName() {
        return get(PLATFORM_NAME);
    }

    /**
     * Get the <code>mapset_id</code> parameter OUT value from the routine
     */
    public Integer getMapsetId() {
        return get(MAPSET_ID);
    }

    /**
     * Get the <code>mapset_name</code> parameter OUT value from the routine
     */
    public String getMapsetName() {
        return get(MAPSET_NAME);
    }

    /**
     * Get the <code>mapset_type</code> parameter OUT value from the routine
     */
    public String getMapsetType() {
        return get(MAPSET_TYPE);
    }

    /**
     * Get the <code>linkage_group_name</code> parameter OUT value from the routine
     */
    public String getLinkageGroupName() {
        return get(LINKAGE_GROUP_NAME);
    }

    /**
     * Get the <code>linkage_group_start</code> parameter OUT value from the routine
     */
    public String getLinkageGroupStart() {
        return get(LINKAGE_GROUP_START);
    }

    /**
     * Get the <code>linkage_group_stop</code> parameter OUT value from the routine
     */
    public String getLinkageGroupStop() {
        return get(LINKAGE_GROUP_STOP);
    }

    /**
     * Get the <code>marker_linkage_group_start</code> parameter OUT value from the routine
     */
    public String getMarkerLinkageGroupStart() {
        return get(MARKER_LINKAGE_GROUP_START);
    }

    /**
     * Get the <code>marker_linkage_group_stop</code> parameter OUT value from the routine
     */
    public String getMarkerLinkageGroupStop() {
        return get(MARKER_LINKAGE_GROUP_STOP);
    }

    /**
     * Get the <code>reference_name</code> parameter OUT value from the routine
     */
    public String getReferenceName() {
        return get(REFERENCE_NAME);
    }

    /**
     * Get the <code>reference_version</code> parameter OUT value from the routine
     */
    public String getReferenceVersion() {
        return get(REFERENCE_VERSION);
    }
}
