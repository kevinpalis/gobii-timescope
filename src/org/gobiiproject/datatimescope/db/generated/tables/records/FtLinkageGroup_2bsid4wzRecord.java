/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.FtLinkageGroup_2bsid4wz;
import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.TableRecordImpl;


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
public class FtLinkageGroup_2bsid4wzRecord extends TableRecordImpl<FtLinkageGroup_2bsid4wzRecord> implements Record4<String, String, String, String> {

    private static final long serialVersionUID = -587263937;

    /**
     * Setter for <code>public.ft_linkage_group_2bsid4wz.map_id</code>.
     */
    public void setMapId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.ft_linkage_group_2bsid4wz.map_id</code>.
     */
    public String getMapId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.ft_linkage_group_2bsid4wz.stop</code>.
     */
    public void setStop(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.ft_linkage_group_2bsid4wz.stop</code>.
     */
    public String getStop() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.ft_linkage_group_2bsid4wz.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.ft_linkage_group_2bsid4wz.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.ft_linkage_group_2bsid4wz.start</code>.
     */
    public void setStart(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.ft_linkage_group_2bsid4wz.start</code>.
     */
    public String getStart() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return FtLinkageGroup_2bsid4wz.FT_LINKAGE_GROUP_2BSID4WZ.MAP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return FtLinkageGroup_2bsid4wz.FT_LINKAGE_GROUP_2BSID4WZ.STOP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return FtLinkageGroup_2bsid4wz.FT_LINKAGE_GROUP_2BSID4WZ.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return FtLinkageGroup_2bsid4wz.FT_LINKAGE_GROUP_2BSID4WZ.START;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getMapId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getMapId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FtLinkageGroup_2bsid4wzRecord value1(String value) {
        setMapId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FtLinkageGroup_2bsid4wzRecord value2(String value) {
        setStop(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FtLinkageGroup_2bsid4wzRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FtLinkageGroup_2bsid4wzRecord value4(String value) {
        setStart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FtLinkageGroup_2bsid4wzRecord values(String value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FtLinkageGroup_2bsid4wzRecord
     */
    public FtLinkageGroup_2bsid4wzRecord() {
        super(FtLinkageGroup_2bsid4wz.FT_LINKAGE_GROUP_2BSID4WZ);
    }

    /**
     * Create a detached, initialised FtLinkageGroup_2bsid4wzRecord
     */
    public FtLinkageGroup_2bsid4wzRecord(String mapId, String stop, String name, String start) {
        super(FtLinkageGroup_2bsid4wz.FT_LINKAGE_GROUP_2BSID4WZ);

        set(0, mapId);
        set(1, stop);
        set(2, name);
        set(3, start);
    }
}