/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.VMarkerLinkageGeneticRecord;
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
public class VMarkerLinkageGenetic extends TableImpl<VMarkerLinkageGeneticRecord> {

    private static final long serialVersionUID = 1976895238;

    /**
     * The reference instance of <code>public.v_marker_linkage_genetic</code>
     */
    public static final VMarkerLinkageGenetic V_MARKER_LINKAGE_GENETIC = new VMarkerLinkageGenetic();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VMarkerLinkageGeneticRecord> getRecordType() {
        return VMarkerLinkageGeneticRecord.class;
    }

    /**
     * The column <code>public.v_marker_linkage_genetic.marker_id</code>.
     */
    public final TableField<VMarkerLinkageGeneticRecord, Integer> MARKER_ID = createField("marker_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.v_marker_linkage_genetic.linkage_group_name</code>.
     */
    public final TableField<VMarkerLinkageGeneticRecord, String> LINKAGE_GROUP_NAME = createField("linkage_group_name", org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.v_marker_linkage_genetic.start</code>.
     */
    public final TableField<VMarkerLinkageGeneticRecord, Integer> START = createField("start", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.v_marker_linkage_genetic.stop</code>.
     */
    public final TableField<VMarkerLinkageGeneticRecord, Integer> STOP = createField("stop", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.v_marker_linkage_genetic.mapset_name</code>.
     */
    public final TableField<VMarkerLinkageGeneticRecord, String> MAPSET_NAME = createField("mapset_name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.v_marker_linkage_genetic</code> table reference
     */
    public VMarkerLinkageGenetic() {
        this(DSL.name("v_marker_linkage_genetic"), null);
    }

    /**
     * Create an aliased <code>public.v_marker_linkage_genetic</code> table reference
     */
    public VMarkerLinkageGenetic(String alias) {
        this(DSL.name(alias), V_MARKER_LINKAGE_GENETIC);
    }

    /**
     * Create an aliased <code>public.v_marker_linkage_genetic</code> table reference
     */
    public VMarkerLinkageGenetic(Name alias) {
        this(alias, V_MARKER_LINKAGE_GENETIC);
    }

    private VMarkerLinkageGenetic(Name alias, Table<VMarkerLinkageGeneticRecord> aliased) {
        this(alias, aliased, null);
    }

    private VMarkerLinkageGenetic(Name alias, Table<VMarkerLinkageGeneticRecord> aliased, Field<?>[] parameters) {
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
    public VMarkerLinkageGenetic as(String alias) {
        return new VMarkerLinkageGenetic(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkageGenetic as(Name alias) {
        return new VMarkerLinkageGenetic(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public VMarkerLinkageGenetic rename(String name) {
        return new VMarkerLinkageGenetic(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public VMarkerLinkageGenetic rename(Name name) {
        return new VMarkerLinkageGenetic(name, null);
    }
}
