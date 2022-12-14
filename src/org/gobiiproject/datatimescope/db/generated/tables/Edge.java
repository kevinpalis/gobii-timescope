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
import org.gobiiproject.datatimescope.db.generated.tables.records.EdgeRecord;
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
public class Edge extends TableImpl<EdgeRecord> {

    private static final long serialVersionUID = 1303955388;

    /**
     * The reference instance of <code>public.edge</code>
     */
    public static final Edge EDGE = new Edge();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EdgeRecord> getRecordType() {
        return EdgeRecord.class;
    }

    /**
     * The column <code>public.edge.edge_id</code>.
     */
    public final TableField<EdgeRecord, Integer> EDGE_ID = createField("edge_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('edge_edge_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.edge.start_vertex</code>.
     */
    public final TableField<EdgeRecord, Integer> START_VERTEX = createField("start_vertex", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.edge.end_vertex</code>.
     */
    public final TableField<EdgeRecord, Integer> END_VERTEX = createField("end_vertex", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.edge.type_id</code>.
     */
    public final TableField<EdgeRecord, Integer> TYPE_ID = createField("type_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.edge.criterion</code>.
     */
    public final TableField<EdgeRecord, String> CRITERION = createField("criterion", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.edge</code> table reference
     */
    public Edge() {
        this(DSL.name("edge"), null);
    }

    /**
     * Create an aliased <code>public.edge</code> table reference
     */
    public Edge(String alias) {
        this(DSL.name(alias), EDGE);
    }

    /**
     * Create an aliased <code>public.edge</code> table reference
     */
    public Edge(Name alias) {
        this(alias, EDGE);
    }

    private Edge(Name alias, Table<EdgeRecord> aliased) {
        this(alias, aliased, null);
    }

    private Edge(Name alias, Table<EdgeRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.EDGE_PKEY, Indexes.EDGE_START_VERTEX_END_VERTEX_KEY, Indexes.END_VERTEX_IDX, Indexes.START_VERTEX_IDX, Indexes.TYPEOF_EDGE_IDX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<EdgeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_EDGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<EdgeRecord> getPrimaryKey() {
        return Keys.EDGE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<EdgeRecord>> getKeys() {
        return Arrays.<UniqueKey<EdgeRecord>>asList(Keys.EDGE_PKEY, Keys.EDGE_START_VERTEX_END_VERTEX_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<EdgeRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<EdgeRecord, ?>>asList(Keys.EDGE__EDGE_START_VERTEX_FKEY, Keys.EDGE__EDGE_END_VERTEX_FKEY, Keys.EDGE__EDGE_TYPE_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Edge as(String alias) {
        return new Edge(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Edge as(Name alias) {
        return new Edge(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Edge rename(String name) {
        return new Edge(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Edge rename(Name name) {
        return new Edge(name, null);
    }
}
