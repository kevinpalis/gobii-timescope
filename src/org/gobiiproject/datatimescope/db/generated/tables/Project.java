/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Indexes;
import org.gobiiproject.datatimescope.db.generated.Keys;
import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
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
public class Project extends TableImpl<ProjectRecord> {

    private static final long serialVersionUID = 754078795;

    /**
     * The reference instance of <code>public.project</code>
     */
    public static final Project PROJECT = new Project();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectRecord> getRecordType() {
        return ProjectRecord.class;
    }

    /**
     * The column <code>public.project.project_id</code>.
     */
    public final TableField<ProjectRecord, Integer> PROJECT_ID = createField("project_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('project_project_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.project.name</code>.
     */
    public final TableField<ProjectRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.project.code</code>.
     */
    public final TableField<ProjectRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.project.description</code>.
     */
    public final TableField<ProjectRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.project.pi_contact</code>.
     */
    public final TableField<ProjectRecord, Integer> PI_CONTACT = createField("pi_contact", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.project.created_by</code>.
     */
    public final TableField<ProjectRecord, Integer> CREATED_BY = createField("created_by", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.project.created_date</code>.
     */
    public final TableField<ProjectRecord, Date> CREATED_DATE = createField("created_date", org.jooq.impl.SQLDataType.DATE.defaultValue(org.jooq.impl.DSL.field("('now'::text)::date", org.jooq.impl.SQLDataType.DATE)), this, "");

    /**
     * The column <code>public.project.modified_by</code>.
     */
    public final TableField<ProjectRecord, Integer> MODIFIED_BY = createField("modified_by", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.project.modified_date</code>.
     */
    public final TableField<ProjectRecord, Date> MODIFIED_DATE = createField("modified_date", org.jooq.impl.SQLDataType.DATE.defaultValue(org.jooq.impl.DSL.field("('now'::text)::date", org.jooq.impl.SQLDataType.DATE)), this, "");

    /**
     * The column <code>public.project.status</code>.
     */
    public final TableField<ProjectRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<ProjectRecord, Object> PROPS = createField("props", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "");

    /**
     * Create a <code>public.project</code> table reference
     */
    public Project() {
        this(DSL.name("project"), null);
    }

    /**
     * Create an aliased <code>public.project</code> table reference
     */
    public Project(String alias) {
        this(DSL.name(alias), PROJECT);
    }

    /**
     * Create an aliased <code>public.project</code> table reference
     */
    public Project(Name alias) {
        this(alias, PROJECT);
    }

    private Project(Name alias, Table<ProjectRecord> aliased) {
        this(alias, aliased, null);
    }

    private Project(Name alias, Table<ProjectRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.IDX_PROJECT_NAME, Indexes.IDX_PROJECT_PI_CONTACT, Indexes.IDX_PROJECT_PROPS, Indexes.PI_PROJECT_NAME_KEY, Indexes.PK_PROJECT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ProjectRecord, Integer> getIdentity() {
        return Keys.IDENTITY_PROJECT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProjectRecord> getPrimaryKey() {
        return Keys.PK_PROJECT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectRecord>>asList(Keys.PK_PROJECT, Keys.PI_PROJECT_NAME_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjectRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjectRecord, ?>>asList(Keys.PROJECT__PROJECT_PI_CONTACT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project as(String alias) {
        return new Project(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project as(Name alias) {
        return new Project(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Project rename(String name) {
        return new Project(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Project rename(Name name) {
        return new Project(name, null);
    }
}
