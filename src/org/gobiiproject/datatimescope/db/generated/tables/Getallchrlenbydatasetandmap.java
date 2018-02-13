/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.GetallchrlenbydatasetandmapRecord;
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
public class Getallchrlenbydatasetandmap extends TableImpl<GetallchrlenbydatasetandmapRecord> {

    private static final long serialVersionUID = 1212585874;

    /**
     * The reference instance of <code>public.getallchrlenbydatasetandmap</code>
     */
    public static final Getallchrlenbydatasetandmap GETALLCHRLENBYDATASETANDMAP = new Getallchrlenbydatasetandmap();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GetallchrlenbydatasetandmapRecord> getRecordType() {
        return GetallchrlenbydatasetandmapRecord.class;
    }

    /**
     * The column <code>public.getallchrlenbydatasetandmap.linkage_group_name</code>.
     */
    public final TableField<GetallchrlenbydatasetandmapRecord, String> LINKAGE_GROUP_NAME = createField("linkage_group_name", org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.getallchrlenbydatasetandmap.linkage_group_length</code>.
     */
    public final TableField<GetallchrlenbydatasetandmapRecord, Integer> LINKAGE_GROUP_LENGTH = createField("linkage_group_length", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>public.getallchrlenbydatasetandmap</code> table reference
     */
    public Getallchrlenbydatasetandmap() {
        this(DSL.name("getallchrlenbydatasetandmap"), null);
    }

    /**
     * Create an aliased <code>public.getallchrlenbydatasetandmap</code> table reference
     */
    public Getallchrlenbydatasetandmap(String alias) {
        this(DSL.name(alias), GETALLCHRLENBYDATASETANDMAP);
    }

    /**
     * Create an aliased <code>public.getallchrlenbydatasetandmap</code> table reference
     */
    public Getallchrlenbydatasetandmap(Name alias) {
        this(alias, GETALLCHRLENBYDATASETANDMAP);
    }

    private Getallchrlenbydatasetandmap(Name alias, Table<GetallchrlenbydatasetandmapRecord> aliased) {
        this(alias, aliased, new Field[2]);
    }

    private Getallchrlenbydatasetandmap(Name alias, Table<GetallchrlenbydatasetandmapRecord> aliased, Field<?>[] parameters) {
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
    public Getallchrlenbydatasetandmap as(String alias) {
        return new Getallchrlenbydatasetandmap(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Getallchrlenbydatasetandmap as(Name alias) {
        return new Getallchrlenbydatasetandmap(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getallchrlenbydatasetandmap rename(String name) {
        return new Getallchrlenbydatasetandmap(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Getallchrlenbydatasetandmap rename(Name name) {
        return new Getallchrlenbydatasetandmap(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public Getallchrlenbydatasetandmap call(Integer datasetid, Integer mapid) {
        return new Getallchrlenbydatasetandmap(DSL.name(getName()), null, new Field[] { 
              DSL.val(datasetid, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(mapid, org.jooq.impl.SQLDataType.INTEGER)
        });
    }

    /**
     * Call this table-valued function
     */
    public Getallchrlenbydatasetandmap call(Field<Integer> datasetid, Field<Integer> mapid) {
        return new Getallchrlenbydatasetandmap(DSL.name(getName()), null, new Field[] { 
              datasetid
            , mapid
        });
    }
}
