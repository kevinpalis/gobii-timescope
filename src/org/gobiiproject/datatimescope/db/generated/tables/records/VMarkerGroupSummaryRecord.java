/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.VMarkerGroupSummary;
import org.jooq.Field;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class VMarkerGroupSummaryRecord extends TableRecordImpl<VMarkerGroupSummaryRecord> implements Record6<Integer, String, String, String, String, String> {

    private static final long serialVersionUID = 813815401;

    /**
     * Setter for <code>public.v_marker_group_summary.marker_group_id</code>.
     */
    public void setMarkerGroupId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.v_marker_group_summary.marker_group_id</code>.
     */
    public Integer getMarkerGroupId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.v_marker_group_summary.marker_group_name</code>.
     */
    public void setMarkerGroupName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.v_marker_group_summary.marker_group_name</code>.
     */
    public String getMarkerGroupName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.v_marker_group_summary.germplasm_group</code>.
     */
    public void setGermplasmGroup(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.v_marker_group_summary.germplasm_group</code>.
     */
    public String getGermplasmGroup() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.v_marker_group_summary.marker_name</code>.
     */
    public void setMarkerName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.v_marker_group_summary.marker_name</code>.
     */
    public String getMarkerName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.v_marker_group_summary.platform</code>.
     */
    public void setPlatform(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.v_marker_group_summary.platform</code>.
     */
    public String getPlatform() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.v_marker_group_summary.favorable_alleles</code>.
     */
    public void setFavorableAlleles(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.v_marker_group_summary.favorable_alleles</code>.
     */
    public String getFavorableAlleles() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return VMarkerGroupSummary.V_MARKER_GROUP_SUMMARY.MARKER_GROUP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return VMarkerGroupSummary.V_MARKER_GROUP_SUMMARY.MARKER_GROUP_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return VMarkerGroupSummary.V_MARKER_GROUP_SUMMARY.GERMPLASM_GROUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return VMarkerGroupSummary.V_MARKER_GROUP_SUMMARY.MARKER_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return VMarkerGroupSummary.V_MARKER_GROUP_SUMMARY.PLATFORM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return VMarkerGroupSummary.V_MARKER_GROUP_SUMMARY.FAVORABLE_ALLELES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getMarkerGroupId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getMarkerGroupName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getGermplasmGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getMarkerName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getPlatform();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getFavorableAlleles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getMarkerGroupId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getMarkerGroupName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getGermplasmGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getMarkerName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getPlatform();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getFavorableAlleles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerGroupSummaryRecord value1(Integer value) {
        setMarkerGroupId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerGroupSummaryRecord value2(String value) {
        setMarkerGroupName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerGroupSummaryRecord value3(String value) {
        setGermplasmGroup(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerGroupSummaryRecord value4(String value) {
        setMarkerName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerGroupSummaryRecord value5(String value) {
        setPlatform(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerGroupSummaryRecord value6(String value) {
        setFavorableAlleles(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerGroupSummaryRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VMarkerGroupSummaryRecord
     */
    public VMarkerGroupSummaryRecord() {
        super(VMarkerGroupSummary.V_MARKER_GROUP_SUMMARY);
    }

    /**
     * Create a detached, initialised VMarkerGroupSummaryRecord
     */
    public VMarkerGroupSummaryRecord(Integer markerGroupId, String markerGroupName, String germplasmGroup, String markerName, String platform, String favorableAlleles) {
        super(VMarkerGroupSummary.V_MARKER_GROUP_SUMMARY);

        set(0, markerGroupId);
        set(1, markerGroupName);
        set(2, germplasmGroup);
        set(3, markerName);
        set(4, platform);
        set(5, favorableAlleles);
    }
}
