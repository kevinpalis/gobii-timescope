/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import java.sql.Date;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.Display;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
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
public class DisplayRecord extends UpdatableRecordImpl<DisplayRecord> implements Record9<Integer, String, String, String, Integer, Date, Integer, Date, Integer> {

    private static final long serialVersionUID = 496377332;

    /**
     * Setter for <code>public.display.display_id</code>.
     */
    public void setDisplayId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.display.display_id</code>.
     */
    public Integer getDisplayId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.display.table_name</code>.
     */
    public void setTableName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.display.table_name</code>.
     */
    public String getTableName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.display.column_name</code>.
     */
    public void setColumnName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.display.column_name</code>.
     */
    public String getColumnName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.display.display_name</code>.
     */
    public void setDisplayName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.display.display_name</code>.
     */
    public String getDisplayName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.display.created_by</code>.
     */
    public void setCreatedBy(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.display.created_by</code>.
     */
    public Integer getCreatedBy() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.display.created_date</code>.
     */
    public void setCreatedDate(Date value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.display.created_date</code>.
     */
    public Date getCreatedDate() {
        return (Date) get(5);
    }

    /**
     * Setter for <code>public.display.modified_by</code>.
     */
    public void setModifiedBy(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.display.modified_by</code>.
     */
    public Integer getModifiedBy() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>public.display.modified_date</code>.
     */
    public void setModifiedDate(Date value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.display.modified_date</code>.
     */
    public Date getModifiedDate() {
        return (Date) get(7);
    }

    /**
     * Setter for <code>public.display.rank</code>.
     */
    public void setRank(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.display.rank</code>.
     */
    public Integer getRank() {
        return (Integer) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, String, String, Integer, Date, Integer, Date, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, String, String, Integer, Date, Integer, Date, Integer> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Display.DISPLAY.DISPLAY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Display.DISPLAY.TABLE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Display.DISPLAY.COLUMN_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Display.DISPLAY.DISPLAY_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Display.DISPLAY.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field6() {
        return Display.DISPLAY.CREATED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return Display.DISPLAY.MODIFIED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field8() {
        return Display.DISPLAY.MODIFIED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return Display.DISPLAY.RANK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getDisplayId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getTableName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getColumnName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getDisplayName();
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
        return getRank();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getDisplayId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getTableName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getColumnName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDisplayName();
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
        return getRank();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayRecord value1(Integer value) {
        setDisplayId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayRecord value2(String value) {
        setTableName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayRecord value3(String value) {
        setColumnName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayRecord value4(String value) {
        setDisplayName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayRecord value5(Integer value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayRecord value6(Date value) {
        setCreatedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayRecord value7(Integer value) {
        setModifiedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayRecord value8(Date value) {
        setModifiedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayRecord value9(Integer value) {
        setRank(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayRecord values(Integer value1, String value2, String value3, String value4, Integer value5, Date value6, Integer value7, Date value8, Integer value9) {
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
     * Create a detached DisplayRecord
     */
    public DisplayRecord() {
        super(Display.DISPLAY);
    }

    /**
     * Create a detached, initialised DisplayRecord
     */
    public DisplayRecord(Integer displayId, String tableName, String columnName, String displayName, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate, Integer rank) {
        super(Display.DISPLAY);

        set(0, displayId);
        set(1, tableName);
        set(2, columnName);
        set(3, displayName);
        set(4, createdBy);
        set(5, createdDate);
        set(6, modifiedBy);
        set(7, modifiedDate);
        set(8, rank);
    }
}
