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
import org.gobiiproject.datatimescope.db.generated.tables.records.DnarunRecord;
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
public class Dnarun extends TableImpl<DnarunRecord> {

    private static final long serialVersionUID = 228085464;

    /**
     * The reference instance of <code>public.dnarun</code>
     */
    public static final Dnarun DNARUN = new Dnarun();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DnarunRecord> getRecordType() {
        return DnarunRecord.class;
    }

    /**
     * The column <code>public.dnarun.dnarun_id</code>.
     */
    public final TableField<DnarunRecord, Integer> DNARUN_ID = createField("dnarun_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('dnarun_dnarun_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.dnarun.experiment_id</code>.
     */
    public final TableField<DnarunRecord, Integer> EXPERIMENT_ID = createField("experiment_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.dnarun.dnasample_id</code>.
     */
    public final TableField<DnarunRecord, Integer> DNASAMPLE_ID = createField("dnasample_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.dnarun.name</code>.
     */
    public final TableField<DnarunRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.dnarun.code</code>.
     */
    public final TableField<DnarunRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<DnarunRecord, Object> DATASET_DNARUN_IDX = createField("dataset_dnarun_idx", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<DnarunRecord, Object> PROPS = createField("props", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * Create a <code>public.dnarun</code> table reference
     */
    public Dnarun() {
        this(DSL.name("dnarun"), null);
    }

    /**
     * Create an aliased <code>public.dnarun</code> table reference
     */
    public Dnarun(String alias) {
        this(DSL.name(alias), DNARUN);
    }

    /**
     * Create an aliased <code>public.dnarun</code> table reference
     */
    public Dnarun(Name alias) {
        this(alias, DNARUN);
    }

    private Dnarun(Name alias, Table<DnarunRecord> aliased) {
        this(alias, aliased, null);
    }

    private Dnarun(Name alias, Table<DnarunRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.IDX_DNARUN_DATASETDNARUNIDX, Indexes.IDX_DNARUN_NAME, Indexes.IDX_DNARUN_PROPS, Indexes.PK_DNARUN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DnarunRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DNARUN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DnarunRecord> getPrimaryKey() {
        return Keys.PK_DNARUN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DnarunRecord>> getKeys() {
        return Arrays.<UniqueKey<DnarunRecord>>asList(Keys.PK_DNARUN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<DnarunRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<DnarunRecord, ?>>asList(Keys.DNARUN__DNARUN_EXPERIMENT_ID_FKEY, Keys.DNARUN__DNARUN_DNASAMPLE_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dnarun as(String alias) {
        return new Dnarun(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dnarun as(Name alias) {
        return new Dnarun(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Dnarun rename(String name) {
        return new Dnarun(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Dnarun rename(Name name) {
        return new Dnarun(name, null);
    }
}
