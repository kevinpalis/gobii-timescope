/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.tables.records.FtLinkageGroup_2bsid4wzRecord;
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
public class FtLinkageGroup_2bsid4wz extends TableImpl<FtLinkageGroup_2bsid4wzRecord> {

    private static final long serialVersionUID = 1236344443;

    /**
     * The reference instance of <code>public.ft_linkage_group_2bsid4wz</code>
     */
    public static final FtLinkageGroup_2bsid4wz FT_LINKAGE_GROUP_2BSID4WZ = new FtLinkageGroup_2bsid4wz();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FtLinkageGroup_2bsid4wzRecord> getRecordType() {
        return FtLinkageGroup_2bsid4wzRecord.class;
    }

    /**
     * The column <code>public.ft_linkage_group_2bsid4wz.map_id</code>.
     */
    public final TableField<FtLinkageGroup_2bsid4wzRecord, String> MAP_ID = createField("map_id", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.ft_linkage_group_2bsid4wz.stop</code>.
     */
    public final TableField<FtLinkageGroup_2bsid4wzRecord, String> STOP = createField("stop", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.ft_linkage_group_2bsid4wz.name</code>.
     */
    public final TableField<FtLinkageGroup_2bsid4wzRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.ft_linkage_group_2bsid4wz.start</code>.
     */
    public final TableField<FtLinkageGroup_2bsid4wzRecord, String> START = createField("start", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.ft_linkage_group_2bsid4wz</code> table reference
     */
    public FtLinkageGroup_2bsid4wz() {
        this(DSL.name("ft_linkage_group_2bsid4wz"), null);
    }

    /**
     * Create an aliased <code>public.ft_linkage_group_2bsid4wz</code> table reference
     */
    public FtLinkageGroup_2bsid4wz(String alias) {
        this(DSL.name(alias), FT_LINKAGE_GROUP_2BSID4WZ);
    }

    /**
     * Create an aliased <code>public.ft_linkage_group_2bsid4wz</code> table reference
     */
    public FtLinkageGroup_2bsid4wz(Name alias) {
        this(alias, FT_LINKAGE_GROUP_2BSID4WZ);
    }

    private FtLinkageGroup_2bsid4wz(Name alias, Table<FtLinkageGroup_2bsid4wzRecord> aliased) {
        this(alias, aliased, null);
    }

    private FtLinkageGroup_2bsid4wz(Name alias, Table<FtLinkageGroup_2bsid4wzRecord> aliased, Field<?>[] parameters) {
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
    public FtLinkageGroup_2bsid4wz as(String alias) {
        return new FtLinkageGroup_2bsid4wz(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FtLinkageGroup_2bsid4wz as(Name alias) {
        return new FtLinkageGroup_2bsid4wz(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FtLinkageGroup_2bsid4wz rename(String name) {
        return new FtLinkageGroup_2bsid4wz(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FtLinkageGroup_2bsid4wz rename(Name name) {
        return new FtLinkageGroup_2bsid4wz(name, null);
    }
}