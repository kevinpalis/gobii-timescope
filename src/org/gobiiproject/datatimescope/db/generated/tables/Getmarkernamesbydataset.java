/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.GetmarkernamesbydatasetRecord;
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
public class Getmarkernamesbydataset extends TableImpl<GetmarkernamesbydatasetRecord> {

    private static final long serialVersionUID = 1286389997;

    /**
     * The reference instance of <code>public.getmarkernamesbydataset</code>
     */
    public static final Getmarkernamesbydataset GETMARKERNAMESBYDATASET = new Getmarkernamesbydataset();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GetmarkernamesbydatasetRecord> getRecordType() {
        return GetmarkernamesbydatasetRecord.class;
    }

    /**
     * The column <code>public.getmarkernamesbydataset.marker_id</code>.
     */
    public final TableField<GetmarkernamesbydatasetRecord, Integer> MARKER_ID = createField("marker_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.getmarkernamesbydataset.marker_name</code>.
     */
    public final TableField<GetmarkernamesbydatasetRecord, String> MARKER_NAME = createField("marker_name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.getmarkernamesbydataset</code> table reference
     */
    public Getmarkernamesbydataset() {
        this(DSL.name("getmarkernamesbydataset"), null);
    }

    /**
     * Create an aliased <code>public.getmarkernamesbydataset</code> table reference
     */
    public Getmarkernamesbydataset(String alias) {
        this(DSL.name(alias), GETMARKERNAMESBYDATASET);
    }

    /**
     * Create an aliased <code>public.getmarkernamesbydataset</code> table reference
     */
    public Getmarkernamesbydataset(Name alias) {
        this(alias, GETMARKERNAMESBYDATASET);
    }

    private Getmarkernamesbydataset(Name alias, Table<GetmarkernamesbydatasetRecord> aliased) {
        this(alias, aliased, new Field[1]);
    }

    private Getmarkernamesbydataset(Name alias, Table<GetmarkernamesbydatasetRecord> aliased, Field<?>[] parameters) {
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
    public Getmarkernamesbydataset as(String alias) {
        return new Getmarkernamesbydataset(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Getmarkernamesbydataset as(Name alias) {
        return new Getmarkernamesbydataset(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getmarkernamesbydataset rename(String name) {
        return new Getmarkernamesbydataset(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getmarkernamesbydataset rename(Name name) {
        return new Getmarkernamesbydataset(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public Getmarkernamesbydataset call(Integer datasetid) {
        return new Getmarkernamesbydataset(DSL.name(getName()), null, new Field[] { 
              DSL.val(datasetid, org.jooq.impl.SQLDataType.INTEGER)
        });
    }

    /**
     * Call this table-valued function
     */
    public Getmarkernamesbydataset call(Field<Integer> datasetid) {
        return new Getmarkernamesbydataset(DSL.name(getName()), null, new Field[] { 
              datasetid
        });
    }
}
