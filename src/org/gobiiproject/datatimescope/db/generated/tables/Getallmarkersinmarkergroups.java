/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.GetallmarkersinmarkergroupsRecord;
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
public class Getallmarkersinmarkergroups extends TableImpl<GetallmarkersinmarkergroupsRecord> {

    private static final long serialVersionUID = 540556939;

    /**
     * The reference instance of <code>public.getallmarkersinmarkergroups</code>
     */
    public static final Getallmarkersinmarkergroups GETALLMARKERSINMARKERGROUPS = new Getallmarkersinmarkergroups();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GetallmarkersinmarkergroupsRecord> getRecordType() {
        return GetallmarkersinmarkergroupsRecord.class;
    }

    /**
     * The column <code>public.getallmarkersinmarkergroups.marker_group_id</code>.
     */
    public final TableField<GetallmarkersinmarkergroupsRecord, Integer> MARKER_GROUP_ID = createField("marker_group_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.getallmarkersinmarkergroups.marker_group_id</code>.
     */
    public final TableField<GetallmarkersinmarkergroupsRecord, Integer> MARKER_GROUP_ID = createField("marker_group_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.getallmarkersinmarkergroups.marker_group_name</code>.
     */
    public final TableField<GetallmarkersinmarkergroupsRecord, String> MARKER_GROUP_NAME = createField("marker_group_name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.getallmarkersinmarkergroups.marker_group_name</code>.
     */
    public final TableField<GetallmarkersinmarkergroupsRecord, String> MARKER_GROUP_NAME = createField("marker_group_name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.getallmarkersinmarkergroups.marker_id</code>.
     */
    public final TableField<GetallmarkersinmarkergroupsRecord, String> MARKER_ID = createField("marker_id", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.getallmarkersinmarkergroups.marker_id</code>.
     */
    public final TableField<GetallmarkersinmarkergroupsRecord, String> MARKER_ID = createField("marker_id", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.getallmarkersinmarkergroups.favorable_alleles</code>.
     */
    public final TableField<GetallmarkersinmarkergroupsRecord, String> FAVORABLE_ALLELES = createField("favorable_alleles", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.getallmarkersinmarkergroups.favorable_alleles</code>.
     */
    public final TableField<GetallmarkersinmarkergroupsRecord, String> FAVORABLE_ALLELES = createField("favorable_alleles", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.getallmarkersinmarkergroups</code> table reference
     */
    public Getallmarkersinmarkergroups() {
        this(DSL.name("getallmarkersinmarkergroups"), null);
    }

    /**
     * Create an aliased <code>public.getallmarkersinmarkergroups</code> table reference
     */
    public Getallmarkersinmarkergroups(String alias) {
        this(DSL.name(alias), GETALLMARKERSINMARKERGROUPS);
    }

    /**
     * Create an aliased <code>public.getallmarkersinmarkergroups</code> table reference
     */
    public Getallmarkersinmarkergroups(Name alias) {
        this(alias, GETALLMARKERSINMARKERGROUPS);
    }

    private Getallmarkersinmarkergroups(Name alias, Table<GetallmarkersinmarkergroupsRecord> aliased) {
        this(alias, aliased, new Field[2]);
    }

    private Getallmarkersinmarkergroups(Name alias, Table<GetallmarkersinmarkergroupsRecord> aliased, Field<?>[] parameters) {
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
    public Getallmarkersinmarkergroups as(String alias) {
        return new Getallmarkersinmarkergroups(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Getallmarkersinmarkergroups as(Name alias) {
        return new Getallmarkersinmarkergroups(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getallmarkersinmarkergroups rename(String name) {
        return new Getallmarkersinmarkergroups(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getallmarkersinmarkergroups rename(Name name) {
        return new Getallmarkersinmarkergroups(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public Getallmarkersinmarkergroups call(String _Idlist, String _Platformlist) {
        return new Getallmarkersinmarkergroups(DSL.name(getName()), null, new Field[] { 
              DSL.val(_Idlist, org.jooq.impl.SQLDataType.CLOB)
            , DSL.val(_Platformlist, org.jooq.impl.SQLDataType.CLOB)
        });
    }

    /**
     * Call this table-valued function
     */
    public Getallmarkersinmarkergroups call(Field<String> _Idlist, Field<String> _Platformlist) {
        return new Getallmarkersinmarkergroups(DSL.name(getName()), null, new Field[] { 
              _Idlist
            , _Platformlist
        });
    }
}
