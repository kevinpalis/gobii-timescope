/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.Getallpropertiesofmarker;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class GetallpropertiesofmarkerRecord extends TableRecordImpl<GetallpropertiesofmarkerRecord> implements Record3<Integer, String, String> {

    private static final long serialVersionUID = 504987539;

    /**
     * Setter for <code>public.getallpropertiesofmarker.property_id</code>.
     */
    public void setPropertyId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.getallpropertiesofmarker.property_id</code>.
     */
    public Integer getPropertyId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.getallpropertiesofmarker.property_name</code>.
     */
    public void setPropertyName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.getallpropertiesofmarker.property_name</code>.
     */
    public String getPropertyName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.getallpropertiesofmarker.property_value</code>.
     */
    public void setPropertyValue(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.getallpropertiesofmarker.property_value</code>.
     */
    public String getPropertyValue() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Getallpropertiesofmarker.GETALLPROPERTIESOFMARKER.PROPERTY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Getallpropertiesofmarker.GETALLPROPERTIESOFMARKER.PROPERTY_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Getallpropertiesofmarker.GETALLPROPERTIESOFMARKER.PROPERTY_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getPropertyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getPropertyName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getPropertyValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getPropertyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getPropertyName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getPropertyValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetallpropertiesofmarkerRecord value1(Integer value) {
        setPropertyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetallpropertiesofmarkerRecord value2(String value) {
        setPropertyName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetallpropertiesofmarkerRecord value3(String value) {
        setPropertyValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetallpropertiesofmarkerRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GetallpropertiesofmarkerRecord
     */
    public GetallpropertiesofmarkerRecord() {
        super(Getallpropertiesofmarker.GETALLPROPERTIESOFMARKER);
    }

    /**
     * Create a detached, initialised GetallpropertiesofmarkerRecord
     */
    public GetallpropertiesofmarkerRecord(Integer propertyId, String propertyName, String propertyValue) {
        super(Getallpropertiesofmarker.GETALLPROPERTIESOFMARKER);

        set(0, propertyId);
        set(1, propertyName);
        set(2, propertyValue);
    }
}
