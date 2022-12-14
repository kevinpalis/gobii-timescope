/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import java.math.BigDecimal;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.VMarkerLinkagePhysical;
import org.jooq.Field;
import org.jooq.Record9;
import org.jooq.Row9;
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
public class VMarkerLinkagePhysicalRecord extends TableRecordImpl<VMarkerLinkagePhysicalRecord> implements Record9<Integer, Integer, String, Integer, Integer, BigDecimal, BigDecimal, String, Integer> {

    private static final long serialVersionUID = -1104099542;

    /**
     * Setter for <code>public.v_marker_linkage_physical.marker_id</code>.
     */
    public void setMarkerId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.v_marker_linkage_physical.marker_id</code>.
     */
    public Integer getMarkerId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.v_marker_linkage_physical.linkage_group_id</code>.
     */
    public void setLinkageGroupId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.v_marker_linkage_physical.linkage_group_id</code>.
     */
    public Integer getLinkageGroupId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.v_marker_linkage_physical.linkage_group_name</code>.
     */
    public void setLinkageGroupName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.v_marker_linkage_physical.linkage_group_name</code>.
     */
    public String getLinkageGroupName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.v_marker_linkage_physical.linkage_group_start</code>.
     */
    public void setLinkageGroupStart(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.v_marker_linkage_physical.linkage_group_start</code>.
     */
    public Integer getLinkageGroupStart() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.v_marker_linkage_physical.linkage_group_stop</code>.
     */
    public void setLinkageGroupStop(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.v_marker_linkage_physical.linkage_group_stop</code>.
     */
    public Integer getLinkageGroupStop() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.v_marker_linkage_physical.start</code>.
     */
    public void setStart(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.v_marker_linkage_physical.start</code>.
     */
    public BigDecimal getStart() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>public.v_marker_linkage_physical.stop</code>.
     */
    public void setStop(BigDecimal value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.v_marker_linkage_physical.stop</code>.
     */
    public BigDecimal getStop() {
        return (BigDecimal) get(6);
    }

    /**
     * Setter for <code>public.v_marker_linkage_physical.mapset_name</code>.
     */
    public void setMapsetName(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.v_marker_linkage_physical.mapset_name</code>.
     */
    public String getMapsetName() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.v_marker_linkage_physical.map_id</code>.
     */
    public void setMapId(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.v_marker_linkage_physical.map_id</code>.
     */
    public Integer getMapId() {
        return (Integer) get(8);
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, String, Integer, Integer, BigDecimal, BigDecimal, String, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, String, Integer, Integer, BigDecimal, BigDecimal, String, Integer> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL.MARKER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL.LINKAGE_GROUP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL.LINKAGE_GROUP_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL.LINKAGE_GROUP_START;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL.LINKAGE_GROUP_STOP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL.START;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field7() {
        return VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL.STOP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL.MAPSET_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL.MAP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getMarkerId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getLinkageGroupId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getLinkageGroupName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getLinkageGroupStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getLinkageGroupStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component6() {
        return getStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component7() {
        return getStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getMapsetName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getMapId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getMarkerId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getLinkageGroupId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getLinkageGroupName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getLinkageGroupStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getLinkageGroupStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value6() {
        return getStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value7() {
        return getStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getMapsetName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getMapId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkagePhysicalRecord value1(Integer value) {
        setMarkerId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkagePhysicalRecord value2(Integer value) {
        setLinkageGroupId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkagePhysicalRecord value3(String value) {
        setLinkageGroupName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkagePhysicalRecord value4(Integer value) {
        setLinkageGroupStart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkagePhysicalRecord value5(Integer value) {
        setLinkageGroupStop(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkagePhysicalRecord value6(BigDecimal value) {
        setStart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkagePhysicalRecord value7(BigDecimal value) {
        setStop(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkagePhysicalRecord value8(String value) {
        setMapsetName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkagePhysicalRecord value9(Integer value) {
        setMapId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerLinkagePhysicalRecord values(Integer value1, Integer value2, String value3, Integer value4, Integer value5, BigDecimal value6, BigDecimal value7, String value8, Integer value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VMarkerLinkagePhysicalRecord
     */
    public VMarkerLinkagePhysicalRecord() {
        super(VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL);
    }

    /**
     * Create a detached, initialised VMarkerLinkagePhysicalRecord
     */
    public VMarkerLinkagePhysicalRecord(Integer markerId, Integer linkageGroupId, String linkageGroupName, Integer linkageGroupStart, Integer linkageGroupStop, BigDecimal start, BigDecimal stop, String mapsetName, Integer mapId) {
        super(VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL);

        set(0, markerId);
        set(1, linkageGroupId);
        set(2, linkageGroupName);
        set(3, linkageGroupStart);
        set(4, linkageGroupStop);
        set(5, start);
        set(6, stop);
        set(7, mapsetName);
        set(8, mapId);
    }
}
