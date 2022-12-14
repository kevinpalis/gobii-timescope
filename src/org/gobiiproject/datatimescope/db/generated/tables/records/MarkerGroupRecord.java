/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import java.sql.Date;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.MarkerGroup;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
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
public class MarkerGroupRecord extends UpdatableRecordImpl<MarkerGroupRecord> implements Record10<Integer, String, String, Object, String, Integer, Date, Integer, Date, Integer> {

    private static final long serialVersionUID = 154465361;

    /**
     * Setter for <code>public.marker_group.marker_group_id</code>.
     */
    public void setMarkerGroupId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.marker_group.marker_group_id</code>.
     */
    public Integer getMarkerGroupId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.marker_group.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.marker_group.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.marker_group.code</code>.
     */
    public void setCode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.marker_group.code</code>.
     */
    public String getCode() {
        return (String) get(2);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setMarkers(Object value) {
        set(3, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getMarkers() {
        return get(3);
    }

    /**
     * Setter for <code>public.marker_group.germplasm_group</code>.
     */
    public void setGermplasmGroup(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.marker_group.germplasm_group</code>.
     */
    public String getGermplasmGroup() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.marker_group.created_by</code>.
     */
    public void setCreatedBy(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.marker_group.created_by</code>.
     */
    public Integer getCreatedBy() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>public.marker_group.created_date</code>.
     */
    public void setCreatedDate(Date value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.marker_group.created_date</code>.
     */
    public Date getCreatedDate() {
        return (Date) get(6);
    }

    /**
     * Setter for <code>public.marker_group.modified_by</code>.
     */
    public void setModifiedBy(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.marker_group.modified_by</code>.
     */
    public Integer getModifiedBy() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>public.marker_group.modified_date</code>.
     */
    public void setModifiedDate(Date value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.marker_group.modified_date</code>.
     */
    public Date getModifiedDate() {
        return (Date) get(8);
    }

    /**
     * Setter for <code>public.marker_group.status</code>.
     */
    public void setStatus(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.marker_group.status</code>.
     */
    public Integer getStatus() {
        return (Integer) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, String, Object, String, Integer, Date, Integer, Date, Integer> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, String, Object, String, Integer, Date, Integer, Date, Integer> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return MarkerGroup.MARKER_GROUP.MARKER_GROUP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return MarkerGroup.MARKER_GROUP.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return MarkerGroup.MARKER_GROUP.CODE;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field4() {
        return MarkerGroup.MARKER_GROUP.MARKERS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return MarkerGroup.MARKER_GROUP.GERMPLASM_GROUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return MarkerGroup.MARKER_GROUP.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field7() {
        return MarkerGroup.MARKER_GROUP.CREATED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return MarkerGroup.MARKER_GROUP.MODIFIED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field9() {
        return MarkerGroup.MARKER_GROUP.MODIFIED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return MarkerGroup.MARKER_GROUP.STATUS;
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
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getCode();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component4() {
        return getMarkers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getGermplasmGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component7() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getModifiedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component9() {
        return getModifiedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getStatus();
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
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCode();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value4() {
        return getMarkers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getGermplasmGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value7() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getModifiedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value9() {
        return getModifiedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerGroupRecord value1(Integer value) {
        setMarkerGroupId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerGroupRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerGroupRecord value3(String value) {
        setCode(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public MarkerGroupRecord value4(Object value) {
        setMarkers(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerGroupRecord value5(String value) {
        setGermplasmGroup(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerGroupRecord value6(Integer value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerGroupRecord value7(Date value) {
        setCreatedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerGroupRecord value8(Integer value) {
        setModifiedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerGroupRecord value9(Date value) {
        setModifiedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerGroupRecord value10(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerGroupRecord values(Integer value1, String value2, String value3, Object value4, String value5, Integer value6, Date value7, Integer value8, Date value9, Integer value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MarkerGroupRecord
     */
    public MarkerGroupRecord() {
        super(MarkerGroup.MARKER_GROUP);
    }

    /**
     * Create a detached, initialised MarkerGroupRecord
     */
    public MarkerGroupRecord(Integer markerGroupId, String name, String code, Object markers, String germplasmGroup, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate, Integer status) {
        super(MarkerGroup.MARKER_GROUP);

        set(0, markerGroupId);
        set(1, name);
        set(2, code);
        set(3, markers);
        set(4, germplasmGroup);
        set(5, createdBy);
        set(6, createdDate);
        set(7, modifiedBy);
        set(8, modifiedDate);
        set(9, status);
    }
}
