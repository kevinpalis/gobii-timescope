/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.GetallpropertiesofdnasampleRecord;
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
public class Getallpropertiesofdnasample extends TableImpl<GetallpropertiesofdnasampleRecord> {

    private static final long serialVersionUID = -374553640;

    /**
     * The reference instance of <code>public.getallpropertiesofdnasample</code>
     */
    public static final Getallpropertiesofdnasample GETALLPROPERTIESOFDNASAMPLE = new Getallpropertiesofdnasample();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GetallpropertiesofdnasampleRecord> getRecordType() {
        return GetallpropertiesofdnasampleRecord.class;
    }

    /**
     * The column <code>public.getallpropertiesofdnasample.property_id</code>.
     */
    public final TableField<GetallpropertiesofdnasampleRecord, Integer> PROPERTY_ID = createField("property_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.getallpropertiesofdnasample.property_name</code>.
     */
    public final TableField<GetallpropertiesofdnasampleRecord, String> PROPERTY_NAME = createField("property_name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.getallpropertiesofdnasample.property_value</code>.
     */
    public final TableField<GetallpropertiesofdnasampleRecord, String> PROPERTY_VALUE = createField("property_value", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.getallpropertiesofdnasample</code> table reference
     */
    public Getallpropertiesofdnasample() {
        this(DSL.name("getallpropertiesofdnasample"), null);
    }

    /**
     * Create an aliased <code>public.getallpropertiesofdnasample</code> table reference
     */
    public Getallpropertiesofdnasample(String alias) {
        this(DSL.name(alias), GETALLPROPERTIESOFDNASAMPLE);
    }

    /**
     * Create an aliased <code>public.getallpropertiesofdnasample</code> table reference
     */
    public Getallpropertiesofdnasample(Name alias) {
        this(alias, GETALLPROPERTIESOFDNASAMPLE);
    }

    private Getallpropertiesofdnasample(Name alias, Table<GetallpropertiesofdnasampleRecord> aliased) {
        this(alias, aliased, new Field[1]);
    }

    private Getallpropertiesofdnasample(Name alias, Table<GetallpropertiesofdnasampleRecord> aliased, Field<?>[] parameters) {
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
    public Getallpropertiesofdnasample as(String alias) {
        return new Getallpropertiesofdnasample(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Getallpropertiesofdnasample as(Name alias) {
        return new Getallpropertiesofdnasample(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getallpropertiesofdnasample rename(String name) {
        return new Getallpropertiesofdnasample(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getallpropertiesofdnasample rename(Name name) {
        return new Getallpropertiesofdnasample(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public Getallpropertiesofdnasample call(Integer id) {
        return new Getallpropertiesofdnasample(DSL.name(getName()), null, new Field[] { 
              DSL.val(id, org.jooq.impl.SQLDataType.INTEGER)
        });
    }

    /**
     * Call this table-valued function
     */
    public Getallpropertiesofdnasample call(Field<Integer> id) {
        return new Getallpropertiesofdnasample(DSL.name(getName()), null, new Field[] { 
              id
        });
    }
}
