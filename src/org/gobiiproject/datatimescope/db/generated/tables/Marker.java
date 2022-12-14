/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Indexes;
import org.gobiiproject.datatimescope.db.generated.Keys;
import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
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
public class Marker extends TableImpl<MarkerRecord> {

    private static final long serialVersionUID = -1992347589;

    /**
     * The reference instance of <code>public.marker</code>
     */
    public static final Marker MARKER = new Marker();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MarkerRecord> getRecordType() {
        return MarkerRecord.class;
    }

    /**
     * The column <code>public.marker.marker_id</code>.
     */
    public final TableField<MarkerRecord, Integer> MARKER_ID = createField("marker_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('marker_marker_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.marker.platform_id</code>.
     */
    public final TableField<MarkerRecord, Integer> PLATFORM_ID = createField("platform_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.marker.variant_id</code>.
     */
    public final TableField<MarkerRecord, Integer> VARIANT_ID = createField("variant_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.marker.name</code>.
     */
    public final TableField<MarkerRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.marker.code</code>.
     */
    public final TableField<MarkerRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.marker.ref</code>.
     */
    public final TableField<MarkerRecord, String> REF = createField("ref", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.marker.alts</code>.
     */
    public final TableField<MarkerRecord, String[]> ALTS = createField("alts", org.jooq.impl.SQLDataType.CLOB.getArrayDataType(), this, "");

    /**
     * The column <code>public.marker.sequence</code>.
     */
    public final TableField<MarkerRecord, String> SEQUENCE = createField("sequence", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.marker.reference_id</code>.
     */
    public final TableField<MarkerRecord, Integer> REFERENCE_ID = createField("reference_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<MarkerRecord, Object> PRIMERS = createField("primers", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * The column <code>public.marker.strand_id</code>.
     */
    public final TableField<MarkerRecord, Integer> STRAND_ID = createField("strand_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.marker.status</code>.
     */
    public final TableField<MarkerRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<MarkerRecord, Object> PROBSETS = createField("probsets", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<MarkerRecord, Object> DATASET_MARKER_IDX = createField("dataset_marker_idx", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<MarkerRecord, Object> PROPS = createField("props", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<MarkerRecord, Object> DATASET_VENDOR_PROTOCOL = createField("dataset_vendor_protocol", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "Key-value-pair JSONB that stores the vendor_protocol ID for each marker-dataset combination.");

    /**
     * Create a <code>public.marker</code> table reference
     */
    public Marker() {
        this(DSL.name("marker"), null);
    }

    /**
     * Create an aliased <code>public.marker</code> table reference
     */
    public Marker(String alias) {
        this(DSL.name(alias), MARKER);
    }

    /**
     * Create an aliased <code>public.marker</code> table reference
     */
    public Marker(Name alias) {
        this(alias, MARKER);
    }

    private Marker(Name alias, Table<MarkerRecord> aliased) {
        this(alias, aliased, null);
    }

    private Marker(Name alias, Table<MarkerRecord> aliased, Field<?>[] parameters) {
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
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.IDX_MARKER_DATASETMARKERIDX, Indexes.IDX_MARKER_DATASETVENDORPROTOCOL, Indexes.IDX_MARKER_NAME, Indexes.IDX_MARKER_PROPS, Indexes.IDX_MARKER_REFERENCE_ID, Indexes.IDX_MARKER_STRAND_ID, Indexes.PK_MARKER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MarkerRecord, Integer> getIdentity() {
        return Keys.IDENTITY_MARKER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MarkerRecord> getPrimaryKey() {
        return Keys.PK_MARKER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MarkerRecord>> getKeys() {
        return Arrays.<UniqueKey<MarkerRecord>>asList(Keys.PK_MARKER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<MarkerRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<MarkerRecord, ?>>asList(Keys.MARKER__MARKER_PLATFORM_ID_FKEY, Keys.MARKER__MARKER_VARIANT_ID_FKEY, Keys.MARKER__MARKER_REFERENCE_ID_FKEY, Keys.MARKER__MARKER_STRAND_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Marker as(String alias) {
        return new Marker(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Marker as(Name alias) {
        return new Marker(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Marker rename(String name) {
        return new Marker(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Marker rename(Name name) {
        return new Marker(name, null);
    }
}
