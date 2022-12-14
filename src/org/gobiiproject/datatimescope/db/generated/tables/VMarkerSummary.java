/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.VMarkerSummaryRecord;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class VMarkerSummary extends TableImpl<VMarkerSummaryRecord> {

    private static final long serialVersionUID = -736743807;

    /**
     * The reference instance of <code>public.v_marker_summary</code>
     */
    public static final VMarkerSummary V_MARKER_SUMMARY = new VMarkerSummary();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VMarkerSummaryRecord> getRecordType() {
        return VMarkerSummaryRecord.class;
    }

    /**
     * The column <code>public.v_marker_summary.marker_id</code>.
     */
    public final TableField<VMarkerSummaryRecord, Integer> MARKER_ID = createField("marker_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.v_marker_summary.marker_name</code>.
     */
    public final TableField<VMarkerSummaryRecord, String> MARKER_NAME = createField("marker_name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.v_marker_summary.platform_id</code>.
     */
    public final TableField<VMarkerSummaryRecord, Integer> PLATFORM_ID = createField("platform_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.v_marker_summary.platform_name</code>.
     */
    public final TableField<VMarkerSummaryRecord, String> PLATFORM_NAME = createField("platform_name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.v_marker_summary.ref</code>.
     */
    public final TableField<VMarkerSummaryRecord, String> REF = createField("ref", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.v_marker_summary.alts</code>.
     */
    public final TableField<VMarkerSummaryRecord, String[]> ALTS = createField("alts", org.jooq.impl.SQLDataType.CLOB.getArrayDataType(), this, "");

    /**
     * The column <code>public.v_marker_summary.reference_id</code>.
     */
    public final TableField<VMarkerSummaryRecord, Integer> REFERENCE_ID = createField("reference_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.v_marker_summary.reference_name</code>.
     */
    public final TableField<VMarkerSummaryRecord, String> REFERENCE_NAME = createField("reference_name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.v_marker_summary.variant_id</code>.
     */
    public final TableField<VMarkerSummaryRecord, Integer> VARIANT_ID = createField("variant_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.v_marker_summary.code</code>.
     */
    public final TableField<VMarkerSummaryRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.v_marker_summary.sequence</code>.
     */
    public final TableField<VMarkerSummaryRecord, String> SEQUENCE = createField("sequence", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<VMarkerSummaryRecord, Object> PRIMERS = createField("primers", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * The column <code>public.v_marker_summary.strand_id</code>.
     */
    public final TableField<VMarkerSummaryRecord, Integer> STRAND_ID = createField("strand_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.v_marker_summary.strand_name</code>.
     */
    public final TableField<VMarkerSummaryRecord, String> STRAND_NAME = createField("strand_name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.v_marker_summary.status</code>.
     */
    public final TableField<VMarkerSummaryRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<VMarkerSummaryRecord, Object> PROBSETS = createField("probsets", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<VMarkerSummaryRecord, Object> DATASET_MARKER_IDX = createField("dataset_marker_idx", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<VMarkerSummaryRecord, Object> PROPS = createField("props", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<VMarkerSummaryRecord, Object> DATASET_VENDOR_PROTOCOL = createField("dataset_vendor_protocol", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * Create a <code>public.v_marker_summary</code> table reference
     */
    public VMarkerSummary() {
        this(DSL.name("v_marker_summary"), null);
    }

    /**
     * Create an aliased <code>public.v_marker_summary</code> table reference
     */
    public VMarkerSummary(String alias) {
        this(DSL.name(alias), V_MARKER_SUMMARY);
    }

    /**
     * Create an aliased <code>public.v_marker_summary</code> table reference
     */
    public VMarkerSummary(Name alias) {
        this(alias, V_MARKER_SUMMARY);
    }

    private VMarkerSummary(Name alias, Table<VMarkerSummaryRecord> aliased) {
        this(alias, aliased, null);
    }

    private VMarkerSummary(Name alias, Table<VMarkerSummaryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummary as(String alias) {
        return new VMarkerSummary(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummary as(Name alias) {
        return new VMarkerSummary(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public VMarkerSummary rename(String name) {
        return new VMarkerSummary(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public VMarkerSummary rename(Name name) {
        return new VMarkerSummary(name, null);
    }
}
