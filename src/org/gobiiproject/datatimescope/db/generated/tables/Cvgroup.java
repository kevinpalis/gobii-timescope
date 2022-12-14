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
import org.gobiiproject.datatimescope.db.generated.tables.records.CvgroupRecord;
import org.jooq.Field;
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
 * A controlled vocabulary or ontology. A cv is
 * composed of cvterms (AKA terms, classes, types, universals - relations
 * and properties are also stored in cvterm) and the relationships
 * between them.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Cvgroup extends TableImpl<CvgroupRecord> {

    private static final long serialVersionUID = 1522859474;

    /**
     * The reference instance of <code>public.cvgroup</code>
     */
    public static final Cvgroup CVGROUP = new Cvgroup();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CvgroupRecord> getRecordType() {
        return CvgroupRecord.class;
    }

    /**
     * The column <code>public.cvgroup.cvgroup_id</code>.
     */
    public final TableField<CvgroupRecord, Integer> CVGROUP_ID = createField("cvgroup_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('cvgroup_cvgroup_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.cvgroup.name</code>. The name of the group.
     */
    public final TableField<CvgroupRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "The name of the group.");

    /**
     * The column <code>public.cvgroup.definition</code>. A text description of the criteria for membership of this group.
     */
    public final TableField<CvgroupRecord, String> DEFINITION = createField("definition", org.jooq.impl.SQLDataType.CLOB, this, "A text description of the criteria for membership of this group.");

    /**
     * The column <code>public.cvgroup.type</code>. Determines if CV group is of type "System CV" (1) or "Custom CV" (2). More types can be added as needed
     */
    public final TableField<CvgroupRecord, Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "Determines if CV group is of type \"System CV\" (1) or \"Custom CV\" (2). More types can be added as needed");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<CvgroupRecord, Object> PROPS = createField("props", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * Create a <code>public.cvgroup</code> table reference
     */
    public Cvgroup() {
        this(DSL.name("cvgroup"), null);
    }

    /**
     * Create an aliased <code>public.cvgroup</code> table reference
     */
    public Cvgroup(String alias) {
        this(DSL.name(alias), CVGROUP);
    }

    /**
     * Create an aliased <code>public.cvgroup</code> table reference
     */
    public Cvgroup(Name alias) {
        this(alias, CVGROUP);
    }

    private Cvgroup(Name alias, Table<CvgroupRecord> aliased) {
        this(alias, aliased, null);
    }

    private Cvgroup(Name alias, Table<CvgroupRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "A controlled vocabulary or ontology. A cv is\ncomposed of cvterms (AKA terms, classes, types, universals - relations\nand properties are also stored in cvterm) and the relationships\nbetween them.");
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
        return Arrays.<Index>asList(Indexes.CV_PKEY, Indexes.IDX_CVGROUP, Indexes.UNIQUE_CVGROUP_NAME_TYPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CvgroupRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CVGROUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CvgroupRecord> getPrimaryKey() {
        return Keys.CV_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CvgroupRecord>> getKeys() {
        return Arrays.<UniqueKey<CvgroupRecord>>asList(Keys.CV_PKEY, Keys.UNIQUE_CVGROUP_NAME_TYPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cvgroup as(String alias) {
        return new Cvgroup(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cvgroup as(Name alias) {
        return new Cvgroup(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Cvgroup rename(String name) {
        return new Cvgroup(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Cvgroup rename(Name name) {
        return new Cvgroup(name, null);
    }
}
