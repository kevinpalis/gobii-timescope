/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.GetmatrixposofmarkersRecord;
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
public class Getmatrixposofmarkers extends TableImpl<GetmatrixposofmarkersRecord> {

    private static final long serialVersionUID = -982118879;

    /**
     * The reference instance of <code>public.getmatrixposofmarkers</code>
     */
    public static final Getmatrixposofmarkers GETMATRIXPOSOFMARKERS = new Getmatrixposofmarkers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GetmatrixposofmarkersRecord> getRecordType() {
        return GetmatrixposofmarkersRecord.class;
    }

    /**
     * The column <code>public.getmatrixposofmarkers.dataset_id</code>.
     */
    public final TableField<GetmatrixposofmarkersRecord, Integer> DATASET_ID = createField("dataset_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.getmatrixposofmarkers.dataset_id</code>.
     */
    public final TableField<GetmatrixposofmarkersRecord, Integer> DATASET_ID = createField("dataset_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.getmatrixposofmarkers.positions</code>.
     */
    public final TableField<GetmatrixposofmarkersRecord, String> POSITIONS = createField("positions", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.getmatrixposofmarkers.positions</code>.
     */
    public final TableField<GetmatrixposofmarkersRecord, String> POSITIONS = createField("positions", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.getmatrixposofmarkers</code> table reference
     */
    public Getmatrixposofmarkers() {
        this(DSL.name("getmatrixposofmarkers"), null);
    }

    /**
     * Create an aliased <code>public.getmatrixposofmarkers</code> table reference
     */
    public Getmatrixposofmarkers(String alias) {
        this(DSL.name(alias), GETMATRIXPOSOFMARKERS);
    }

    /**
     * Create an aliased <code>public.getmatrixposofmarkers</code> table reference
     */
    public Getmatrixposofmarkers(Name alias) {
        this(alias, GETMATRIXPOSOFMARKERS);
    }

    private Getmatrixposofmarkers(Name alias, Table<GetmatrixposofmarkersRecord> aliased) {
        this(alias, aliased, new Field[2]);
    }

    private Getmatrixposofmarkers(Name alias, Table<GetmatrixposofmarkersRecord> aliased, Field<?>[] parameters) {
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
    public Getmatrixposofmarkers as(String alias) {
        return new Getmatrixposofmarkers(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Getmatrixposofmarkers as(Name alias) {
        return new Getmatrixposofmarkers(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getmatrixposofmarkers rename(String name) {
        return new Getmatrixposofmarkers(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getmatrixposofmarkers rename(Name name) {
        return new Getmatrixposofmarkers(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public Getmatrixposofmarkers call(String markerlist, Integer datasettypeid) {
        return new Getmatrixposofmarkers(DSL.name(getName()), null, new Field[] { 
              DSL.val(markerlist, org.jooq.impl.SQLDataType.CLOB)
            , DSL.val(datasettypeid, org.jooq.impl.SQLDataType.INTEGER)
        });
    }

    /**
     * Call this table-valued function
     */
    public Getmatrixposofmarkers call(Field<String> markerlist, Field<Integer> datasettypeid) {
        return new Getmatrixposofmarkers(DSL.name(getName()), null, new Field[] { 
              markerlist
            , datasettypeid
        });
    }
}
