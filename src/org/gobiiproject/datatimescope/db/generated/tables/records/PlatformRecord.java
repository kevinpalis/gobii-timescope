/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import java.sql.Date;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.Platform;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
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
public class PlatformRecord extends UpdatableRecordImpl<PlatformRecord> implements Record11<Integer, String, String, String, Integer, Date, Integer, Date, Integer, Integer, Object> {

    private static final long serialVersionUID = 1638212478;

    /**
     * Setter for <code>public.platform.platform_id</code>.
     */
    public void setPlatformId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.platform.platform_id</code>.
     */
    public Integer getPlatformId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.platform.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.platform.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.platform.code</code>.
     */
    public void setCode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.platform.code</code>.
     */
    public String getCode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.platform.description</code>.
     */
    public void setDescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.platform.description</code>.
     */
    public String getDescription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.platform.created_by</code>.
     */
    public void setCreatedBy(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.platform.created_by</code>.
     */
    public Integer getCreatedBy() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.platform.created_date</code>.
     */
    public void setCreatedDate(Date value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.platform.created_date</code>.
     */
    public Date getCreatedDate() {
        return (Date) get(5);
    }

    /**
     * Setter for <code>public.platform.modified_by</code>.
     */
    public void setModifiedBy(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.platform.modified_by</code>.
     */
    public Integer getModifiedBy() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>public.platform.modified_date</code>.
     */
    public void setModifiedDate(Date value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.platform.modified_date</code>.
     */
    public Date getModifiedDate() {
        return (Date) get(7);
    }

    /**
     * Setter for <code>public.platform.status</code>.
     */
    public void setStatus(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.platform.status</code>.
     */
    public Integer getStatus() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>public.platform.type_id</code>.
     */
    public void setTypeId(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.platform.type_id</code>.
     */
    public Integer getTypeId() {
        return (Integer) get(9);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setProps(Object value) {
        set(10, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getProps() {
        return get(10);
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
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, String, String, String, Integer, Date, Integer, Date, Integer, Integer, Object> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, String, String, String, Integer, Date, Integer, Date, Integer, Integer, Object> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Platform.PLATFORM.PLATFORM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Platform.PLATFORM.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Platform.PLATFORM.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Platform.PLATFORM.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Platform.PLATFORM.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field6() {
        return Platform.PLATFORM.CREATED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return Platform.PLATFORM.MODIFIED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field8() {
        return Platform.PLATFORM.MODIFIED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return Platform.PLATFORM.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return Platform.PLATFORM.TYPE_ID;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field11() {
        return Platform.PLATFORM.PROPS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getPlatformId();
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
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component6() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getModifiedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component8() {
        return getModifiedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getTypeId();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component11() {
        return getProps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getPlatformId();
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
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value6() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getModifiedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value8() {
        return getModifiedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getTypeId();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value11() {
        return getProps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord value1(Integer value) {
        setPlatformId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord value3(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord value4(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord value5(Integer value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord value6(Date value) {
        setCreatedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord value7(Integer value) {
        setModifiedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord value8(Date value) {
        setModifiedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord value9(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord value10(Integer value) {
        setTypeId(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public PlatformRecord value11(Object value) {
        setProps(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformRecord values(Integer value1, String value2, String value3, String value4, Integer value5, Date value6, Integer value7, Date value8, Integer value9, Integer value10, Object value11) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PlatformRecord
     */
    public PlatformRecord() {
        super(Platform.PLATFORM);
    }

    /**
     * Create a detached, initialised PlatformRecord
     */
    public PlatformRecord(Integer platformId, String name, String code, String description, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate, Integer status, Integer typeId, Object props) {
        super(Platform.PLATFORM);

        set(0, platformId);
        set(1, name);
        set(2, code);
        set(3, description);
        set(4, createdBy);
        set(5, createdDate);
        set(6, modifiedBy);
        set(7, modifiedDate);
        set(8, status);
        set(9, typeId);
        set(10, props);
    }
}
