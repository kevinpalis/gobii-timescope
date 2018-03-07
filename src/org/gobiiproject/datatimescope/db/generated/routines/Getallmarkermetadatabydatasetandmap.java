/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.routines;


import java.math.BigDecimal;

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
public class Getallmarkermetadatabydatasetandmap extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = 1257704337;

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.datasetid</code>.
     */
    public static final Parameter<Integer> DATASETID = createParameter("datasetid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.mapid</code>.
     */
    public static final Parameter<Integer> MAPID = createParameter("mapid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.marker_name</code>.
     */
    public static final Parameter<String> MARKER_NAME = createParameter("marker_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.linkage_group_name</code>.
     */
    public static final Parameter<String> LINKAGE_GROUP_NAME = createParameter("linkage_group_name", org.jooq.impl.SQLDataType.VARCHAR, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.start</code>.
     */
    public static final Parameter<BigDecimal> START = createParameter("start", org.jooq.impl.SQLDataType.NUMERIC, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.stop</code>.
     */
    public static final Parameter<BigDecimal> STOP = createParameter("stop", org.jooq.impl.SQLDataType.NUMERIC, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.mapset_name</code>.
     */
    public static final Parameter<String> MAPSET_NAME = createParameter("mapset_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.platform_name</code>.
     */
    public static final Parameter<String> PLATFORM_NAME = createParameter("platform_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.variant_id</code>.
     */
    public static final Parameter<Integer> VARIANT_ID = createParameter("variant_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.code</code>.
     */
    public static final Parameter<String> CODE = createParameter("code", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.ref</code>.
     */
    public static final Parameter<String> REF = createParameter("ref", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.alts</code>.
     */
    public static final Parameter<String> ALTS = createParameter("alts", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.sequence</code>.
     */
    public static final Parameter<String> SEQUENCE = createParameter("sequence", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.reference_name</code>.
     */
    public static final Parameter<String> REFERENCE_NAME = createParameter("reference_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public static final Parameter<Object> PRIMERS = createParameter("primers", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), false, false);

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public static final Parameter<Object> PROBSETS = createParameter("probsets", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), false, false);

    /**
     * The parameter <code>public.getallmarkermetadatabydatasetandmap.strand_name</code>.
     */
    public static final Parameter<String> STRAND_NAME = createParameter("strand_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Getallmarkermetadatabydatasetandmap() {
        super("getallmarkermetadatabydatasetandmap", Public.PUBLIC);

        addInParameter(DATASETID);
        addInParameter(MAPID);
        addOutParameter(MARKER_NAME);
        addOutParameter(LINKAGE_GROUP_NAME);
        addOutParameter(START);
        addOutParameter(STOP);
        addOutParameter(MAPSET_NAME);
        addOutParameter(PLATFORM_NAME);
        addOutParameter(VARIANT_ID);
        addOutParameter(CODE);
        addOutParameter(REF);
        addOutParameter(ALTS);
        addOutParameter(SEQUENCE);
        addOutParameter(REFERENCE_NAME);
        addOutParameter(PRIMERS);
        addOutParameter(PROBSETS);
        addOutParameter(STRAND_NAME);
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
     * Get the <code>marker_name</code> parameter OUT value from the routine
     */
    public String getMarkerName() {
        return get(MARKER_NAME);
    }

    /**
     * Get the <code>linkage_group_name</code> parameter OUT value from the routine
     */
    public String getLinkageGroupName() {
        return get(LINKAGE_GROUP_NAME);
    }

    /**
     * Get the <code>start</code> parameter OUT value from the routine
     */
    public BigDecimal getStart() {
        return get(START);
    }

    /**
     * Get the <code>stop</code> parameter OUT value from the routine
     */
    public BigDecimal getStop() {
        return get(STOP);
    }

    /**
     * Get the <code>mapset_name</code> parameter OUT value from the routine
     */
    public String getMapsetName() {
        return get(MAPSET_NAME);
    }

    /**
     * Get the <code>platform_name</code> parameter OUT value from the routine
     */
    public String getPlatformName() {
        return get(PLATFORM_NAME);
    }

    /**
     * Get the <code>variant_id</code> parameter OUT value from the routine
     */
    public Integer getVariantId() {
        return get(VARIANT_ID);
    }

    /**
     * Get the <code>code</code> parameter OUT value from the routine
     */
    public String getCode() {
        return get(CODE);
    }

    /**
     * Get the <code>ref</code> parameter OUT value from the routine
     */
    public String getRef() {
        return get(REF);
    }

    /**
     * Get the <code>alts</code> parameter OUT value from the routine
     */
    public String getAlts() {
        return get(ALTS);
    }

    /**
     * Get the <code>sequence</code> parameter OUT value from the routine
     */
    public String getSequence() {
        return get(SEQUENCE);
    }

    /**
     * Get the <code>reference_name</code> parameter OUT value from the routine
     */
    public String getReferenceName() {
        return get(REFERENCE_NAME);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getPrimers() {
        return get(PRIMERS);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getProbsets() {
        return get(PROBSETS);
    }

    /**
     * Get the <code>strand_name</code> parameter OUT value from the routine
     */
    public String getStrandName() {
        return get(STRAND_NAME);
    }
}
