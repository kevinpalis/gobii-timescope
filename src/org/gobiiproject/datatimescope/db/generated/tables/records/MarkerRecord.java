/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.Marker;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record16;
import org.jooq.Row16;
import org.jooq.impl.UpdatableRecordImpl;


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
public class MarkerRecord extends UpdatableRecordImpl<MarkerRecord> implements Record16<Integer, Integer, Integer, String, String, String, String[], String, Integer, Object, Integer, Integer, Object, Object, Object, Object> {

    private static final long serialVersionUID = -23498507;

    /**
     * Setter for <code>public.marker.marker_id</code>.
     */
    public void setMarkerId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.marker.marker_id</code>.
     */
    public Integer getMarkerId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.marker.platform_id</code>.
     */
    public void setPlatformId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.marker.platform_id</code>.
     */
    public Integer getPlatformId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.marker.variant_id</code>.
     */
    public void setVariantId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.marker.variant_id</code>.
     */
    public Integer getVariantId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.marker.name</code>.
     */
    public void setName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.marker.name</code>.
     */
    public String getName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.marker.code</code>.
     */
    public void setCode(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.marker.code</code>.
     */
    public String getCode() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.marker.ref</code>.
     */
    public void setRef(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.marker.ref</code>.
     */
    public String getRef() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.marker.alts</code>.
     */
    public void setAlts(String... value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.marker.alts</code>.
     */
    public String[] getAlts() {
        return (String[]) get(6);
    }

    /**
     * Setter for <code>public.marker.sequence</code>.
     */
    public void setSequence(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.marker.sequence</code>.
     */
    public String getSequence() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.marker.reference_id</code>.
     */
    public void setReferenceId(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.marker.reference_id</code>.
     */
    public Integer getReferenceId() {
        return (Integer) get(8);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setPrimers(Object value) {
        set(9, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getPrimers() {
        return get(9);
    }

    /**
     * Setter for <code>public.marker.strand_id</code>.
     */
    public void setStrandId(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.marker.strand_id</code>.
     */
    public Integer getStrandId() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>public.marker.status</code>.
     */
    public void setStatus(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.marker.status</code>.
     */
    public Integer getStatus() {
        return (Integer) get(11);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setProbsets(Object value) {
        set(12, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getProbsets() {
        return get(12);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setDatasetMarkerIdx(Object value) {
        set(13, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getDatasetMarkerIdx() {
        return get(13);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setProps(Object value) {
        set(14, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getProps() {
        return get(14);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setDatasetVendorProtocol(Object value) {
        set(15, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getDatasetVendorProtocol() {
        return get(15);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record16 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row16<Integer, Integer, Integer, String, String, String, String[], String, Integer, Object, Integer, Integer, Object, Object, Object, Object> fieldsRow() {
        return (Row16) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row16<Integer, Integer, Integer, String, String, String, String[], String, Integer, Object, Integer, Integer, Object, Object, Object, Object> valuesRow() {
        return (Row16) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Marker.MARKER.MARKER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Marker.MARKER.PLATFORM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return Marker.MARKER.VARIANT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Marker.MARKER.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Marker.MARKER.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Marker.MARKER.REF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String[]> field7() {
        return Marker.MARKER.ALTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Marker.MARKER.SEQUENCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return Marker.MARKER.REFERENCE_ID;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field10() {
        return Marker.MARKER.PRIMERS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return Marker.MARKER.STRAND_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return Marker.MARKER.STATUS;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field13() {
        return Marker.MARKER.PROBSETS;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field14() {
        return Marker.MARKER.DATASET_MARKER_IDX;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field15() {
        return Marker.MARKER.PROPS;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field16() {
        return Marker.MARKER.DATASET_VENDOR_PROTOCOL;
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
        return getPlatformId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getVariantId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getRef();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] component7() {
        return getAlts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getSequence();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getReferenceId();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component10() {
        return getPrimers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component11() {
        return getStrandId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getStatus();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component13() {
        return getProbsets();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component14() {
        return getDatasetMarkerIdx();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component15() {
        return getProps();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component16() {
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
    public Integer value2() {
        return getPlatformId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getVariantId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getRef();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] value7() {
        return getAlts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getSequence();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getReferenceId();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value10() {
        return getPrimers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getStrandId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getStatus();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value13() {
        return getProbsets();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value14() {
        return getDatasetMarkerIdx();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value15() {
        return getProps();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value16() {
        return getDatasetVendorProtocol();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value1(Integer value) {
        setMarkerId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value2(Integer value) {
        setPlatformId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value3(Integer value) {
        setVariantId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value4(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value5(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value6(String value) {
        setRef(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value7(String... value) {
        setAlts(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value8(String value) {
        setSequence(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value9(Integer value) {
        setReferenceId(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public MarkerRecord value10(Object value) {
        setPrimers(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value11(Integer value) {
        setStrandId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord value12(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public MarkerRecord value13(Object value) {
        setProbsets(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public MarkerRecord value14(Object value) {
        setDatasetMarkerIdx(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public MarkerRecord value15(Object value) {
        setProps(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public MarkerRecord value16(Object value) {
        setDatasetVendorProtocol(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerRecord values(Integer value1, Integer value2, Integer value3, String value4, String value5, String value6, String[] value7, String value8, Integer value9, Object value10, Integer value11, Integer value12, Object value13, Object value14, Object value15, Object value16) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MarkerRecord
     */
    public MarkerRecord() {
        super(Marker.MARKER);
    }

    /**
     * Create a detached, initialised MarkerRecord
     */
    public MarkerRecord(Integer markerId, Integer platformId, Integer variantId, String name, String code, String ref, String[] alts, String sequence, Integer referenceId, Object primers, Integer strandId, Integer status, Object probsets, Object datasetMarkerIdx, Object props, Object datasetVendorProtocol) {
        super(Marker.MARKER);

        set(0, markerId);
        set(1, platformId);
        set(2, variantId);
        set(3, name);
        set(4, code);
        set(5, ref);
        set(6, alts);
        set(7, sequence);
        set(8, referenceId);
        set(9, primers);
        set(10, strandId);
        set(11, status);
        set(12, probsets);
        set(13, datasetMarkerIdx);
        set(14, props);
        set(15, datasetVendorProtocol);
    }
}
