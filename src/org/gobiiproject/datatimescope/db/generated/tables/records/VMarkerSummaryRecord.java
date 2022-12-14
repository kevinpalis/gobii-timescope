/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.VMarkerSummary;
import org.jooq.Field;
import org.jooq.Record19;
import org.jooq.Row19;
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
public class VMarkerSummaryRecord extends TableRecordImpl<VMarkerSummaryRecord> implements Record19<Integer, String, Integer, String, String, String[], Integer, String, Integer, String, String, Object, Integer, String, Integer, Object, Object, Object, Object> {

    private static final long serialVersionUID = 1673107557;

    /**
     * Setter for <code>public.v_marker_summary.marker_id</code>.
     */
    public void setMarkerId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.marker_id</code>.
     */
    public Integer getMarkerId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.v_marker_summary.marker_name</code>.
     */
    public void setMarkerName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.marker_name</code>.
     */
    public String getMarkerName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.v_marker_summary.platform_id</code>.
     */
    public void setPlatformId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.platform_id</code>.
     */
    public Integer getPlatformId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.v_marker_summary.platform_name</code>.
     */
    public void setPlatformName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.platform_name</code>.
     */
    public String getPlatformName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.v_marker_summary.ref</code>.
     */
    public void setRef(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.ref</code>.
     */
    public String getRef() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.v_marker_summary.alts</code>.
     */
    public void setAlts(String... value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.alts</code>.
     */
    public String[] getAlts() {
        return (String[]) get(5);
    }

    /**
     * Setter for <code>public.v_marker_summary.reference_id</code>.
     */
    public void setReferenceId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.reference_id</code>.
     */
    public Integer getReferenceId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>public.v_marker_summary.reference_name</code>.
     */
    public void setReferenceName(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.reference_name</code>.
     */
    public String getReferenceName() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.v_marker_summary.variant_id</code>.
     */
    public void setVariantId(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.variant_id</code>.
     */
    public Integer getVariantId() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>public.v_marker_summary.code</code>.
     */
    public void setCode(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.code</code>.
     */
    public String getCode() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.v_marker_summary.sequence</code>.
     */
    public void setSequence(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.sequence</code>.
     */
    public String getSequence() {
        return (String) get(10);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setPrimers(Object value) {
        set(11, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getPrimers() {
        return get(11);
    }

    /**
     * Setter for <code>public.v_marker_summary.strand_id</code>.
     */
    public void setStrandId(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.strand_id</code>.
     */
    public Integer getStrandId() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>public.v_marker_summary.strand_name</code>.
     */
    public void setStrandName(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.strand_name</code>.
     */
    public String getStrandName() {
        return (String) get(13);
    }

    /**
     * Setter for <code>public.v_marker_summary.status</code>.
     */
    public void setStatus(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>public.v_marker_summary.status</code>.
     */
    public Integer getStatus() {
        return (Integer) get(14);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setProbsets(Object value) {
        set(15, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getProbsets() {
        return get(15);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setDatasetMarkerIdx(Object value) {
        set(16, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getDatasetMarkerIdx() {
        return get(16);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setProps(Object value) {
        set(17, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getProps() {
        return get(17);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setDatasetVendorProtocol(Object value) {
        set(18, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getDatasetVendorProtocol() {
        return get(18);
    }

    // -------------------------------------------------------------------------
    // Record19 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, String, Integer, String, String, String[], Integer, String, Integer, String, String, Object, Integer, String, Integer, Object, Object, Object, Object> fieldsRow() {
        return (Row19) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, String, Integer, String, String, String[], Integer, String, Integer, String, String, Object, Integer, String, Integer, Object, Object, Object, Object> valuesRow() {
        return (Row19) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return VMarkerSummary.V_MARKER_SUMMARY.MARKER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return VMarkerSummary.V_MARKER_SUMMARY.MARKER_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return VMarkerSummary.V_MARKER_SUMMARY.PLATFORM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return VMarkerSummary.V_MARKER_SUMMARY.PLATFORM_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return VMarkerSummary.V_MARKER_SUMMARY.REF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String[]> field6() {
        return VMarkerSummary.V_MARKER_SUMMARY.ALTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return VMarkerSummary.V_MARKER_SUMMARY.REFERENCE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return VMarkerSummary.V_MARKER_SUMMARY.REFERENCE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return VMarkerSummary.V_MARKER_SUMMARY.VARIANT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return VMarkerSummary.V_MARKER_SUMMARY.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return VMarkerSummary.V_MARKER_SUMMARY.SEQUENCE;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field12() {
        return VMarkerSummary.V_MARKER_SUMMARY.PRIMERS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return VMarkerSummary.V_MARKER_SUMMARY.STRAND_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return VMarkerSummary.V_MARKER_SUMMARY.STRAND_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field15() {
        return VMarkerSummary.V_MARKER_SUMMARY.STATUS;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field16() {
        return VMarkerSummary.V_MARKER_SUMMARY.PROBSETS;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field17() {
        return VMarkerSummary.V_MARKER_SUMMARY.DATASET_MARKER_IDX;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field18() {
        return VMarkerSummary.V_MARKER_SUMMARY.PROPS;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field19() {
        return VMarkerSummary.V_MARKER_SUMMARY.DATASET_VENDOR_PROTOCOL;
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
    public String component2() {
        return getMarkerName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getPlatformId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getPlatformName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getRef();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] component6() {
        return getAlts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getReferenceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getReferenceName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getVariantId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getSequence();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component12() {
        return getPrimers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component13() {
        return getStrandId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getStrandName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component15() {
        return getStatus();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component16() {
        return getProbsets();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component17() {
        return getDatasetMarkerIdx();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component18() {
        return getProps();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component19() {
        return getDatasetVendorProtocol();
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
    public String value2() {
        return getMarkerName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getPlatformId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getPlatformName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getRef();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] value6() {
        return getAlts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getReferenceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getReferenceName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getVariantId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getSequence();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value12() {
        return getPrimers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getStrandId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getStrandName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value15() {
        return getStatus();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value16() {
        return getProbsets();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value17() {
        return getDatasetMarkerIdx();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value18() {
        return getProps();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value19() {
        return getDatasetVendorProtocol();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value1(Integer value) {
        setMarkerId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value2(String value) {
        setMarkerName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value3(Integer value) {
        setPlatformId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value4(String value) {
        setPlatformName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value5(String value) {
        setRef(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value6(String... value) {
        setAlts(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value7(Integer value) {
        setReferenceId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value8(String value) {
        setReferenceName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value9(Integer value) {
        setVariantId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value10(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value11(String value) {
        setSequence(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public VMarkerSummaryRecord value12(Object value) {
        setPrimers(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value13(Integer value) {
        setStrandId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value14(String value) {
        setStrandName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord value15(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public VMarkerSummaryRecord value16(Object value) {
        setProbsets(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public VMarkerSummaryRecord value17(Object value) {
        setDatasetMarkerIdx(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public VMarkerSummaryRecord value18(Object value) {
        setProps(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public VMarkerSummaryRecord value19(Object value) {
        setDatasetVendorProtocol(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VMarkerSummaryRecord values(Integer value1, String value2, Integer value3, String value4, String value5, String[] value6, Integer value7, String value8, Integer value9, String value10, String value11, Object value12, Integer value13, String value14, Integer value15, Object value16, Object value17, Object value18, Object value19) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VMarkerSummaryRecord
     */
    public VMarkerSummaryRecord() {
        super(VMarkerSummary.V_MARKER_SUMMARY);
    }

    /**
     * Create a detached, initialised VMarkerSummaryRecord
     */
    public VMarkerSummaryRecord(Integer markerId, String markerName, Integer platformId, String platformName, String ref, String[] alts, Integer referenceId, String referenceName, Integer variantId, String code, String sequence, Object primers, Integer strandId, String strandName, Integer status, Object probsets, Object datasetMarkerIdx, Object props, Object datasetVendorProtocol) {
        super(VMarkerSummary.V_MARKER_SUMMARY);

        set(0, markerId);
        set(1, markerName);
        set(2, platformId);
        set(3, platformName);
        set(4, ref);
        set(5, alts);
        set(6, referenceId);
        set(7, referenceName);
        set(8, variantId);
        set(9, code);
        set(10, sequence);
        set(11, primers);
        set(12, strandId);
        set(13, strandName);
        set(14, status);
        set(15, probsets);
        set(16, datasetMarkerIdx);
        set(17, props);
        set(18, datasetVendorProtocol);
    }
}
