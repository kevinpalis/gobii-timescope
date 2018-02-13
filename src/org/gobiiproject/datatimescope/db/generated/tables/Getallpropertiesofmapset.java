/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.GetallpropertiesofmapsetRecord;
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
public class Getallpropertiesofmapset extends TableImpl<GetallpropertiesofmapsetRecord> {

    private static final long serialVersionUID = 469101536;

    /**
     * The reference instance of <code>public.getallpropertiesofmapset</code>
     */
    public static final Getallpropertiesofmapset GETALLPROPERTIESOFMAPSET = new Getallpropertiesofmapset();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GetallpropertiesofmapsetRecord> getRecordType() {
        return GetallpropertiesofmapsetRecord.class;
    }

    /**
     * The column <code>public.getallpropertiesofmapset.property_id</code>.
     */
    public final TableField<GetallpropertiesofmapsetRecord, Integer> PROPERTY_ID = createField("property_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.getallpropertiesofmapset.property_name</code>.
     */
    public final TableField<GetallpropertiesofmapsetRecord, String> PROPERTY_NAME = createField("property_name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.getallpropertiesofmapset.property_value</code>.
     */
    public final TableField<GetallpropertiesofmapsetRecord, String> PROPERTY_VALUE = createField("property_value", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.getallpropertiesofmapset</code> table reference
     */
    public Getallpropertiesofmapset() {
        this(DSL.name("getallpropertiesofmapset"), null);
    }

    /**
     * Create an aliased <code>public.getallpropertiesofmapset</code> table reference
     */
    public Getallpropertiesofmapset(String alias) {
        this(DSL.name(alias), GETALLPROPERTIESOFMAPSET);
    }

    /**
     * Create an aliased <code>public.getallpropertiesofmapset</code> table reference
     */
    public Getallpropertiesofmapset(Name alias) {
        this(alias, GETALLPROPERTIESOFMAPSET);
    }

    private Getallpropertiesofmapset(Name alias, Table<GetallpropertiesofmapsetRecord> aliased) {
        this(alias, aliased, new Field[1]);
    }

    private Getallpropertiesofmapset(Name alias, Table<GetallpropertiesofmapsetRecord> aliased, Field<?>[] parameters) {
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
    public Getallpropertiesofmapset as(String alias) {
        return new Getallpropertiesofmapset(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Getallpropertiesofmapset as(Name alias) {
        return new Getallpropertiesofmapset(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getallpropertiesofmapset rename(String name) {
        return new Getallpropertiesofmapset(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getallpropertiesofmapset rename(Name name) {
        return new Getallpropertiesofmapset(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public Getallpropertiesofmapset call(Integer id) {
        return new Getallpropertiesofmapset(DSL.name(getName()), null, new Field[] { 
              DSL.val(id, org.jooq.impl.SQLDataType.INTEGER)
        });
    }

    /**
     * Call this table-valued function
     */
    public Getallpropertiesofmapset call(Field<Integer> id) {
        return new Getallpropertiesofmapset(DSL.name(getName()), null, new Field[] { 
              id
        });
    }
}
