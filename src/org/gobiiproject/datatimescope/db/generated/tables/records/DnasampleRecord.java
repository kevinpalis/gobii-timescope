/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import java.sql.Date;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.Dnasample;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
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
public class DnasampleRecord extends UpdatableRecordImpl<DnasampleRecord> implements Record15<Integer, String, String, String, String, String, String, Integer, Integer, Integer, Date, Integer, Date, Integer, Object> {

    private static final long serialVersionUID = -2054871305;

    /**
     * Setter for <code>public.dnasample.dnasample_id</code>.
     */
    public void setDnasampleId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.dnasample.dnasample_id</code>.
     */
    public Integer getDnasampleId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.dnasample.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.dnasample.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.dnasample.code</code>.
     */
    public void setCode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.dnasample.code</code>.
     */
    public String getCode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.dnasample.platename</code>.
     */
    public void setPlatename(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.dnasample.platename</code>.
     */
    public String getPlatename() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.dnasample.num</code>.
     */
    public void setNum(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.dnasample.num</code>.
     */
    public String getNum() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.dnasample.well_row</code>.
     */
    public void setWellRow(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.dnasample.well_row</code>.
     */
    public String getWellRow() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.dnasample.well_col</code>.
     */
    public void setWellCol(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.dnasample.well_col</code>.
     */
    public String getWellCol() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.dnasample.project_id</code>.
     */
    public void setProjectId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.dnasample.project_id</code>.
     */
    public Integer getProjectId() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>public.dnasample.germplasm_id</code>.
     */
    public void setGermplasmId(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.dnasample.germplasm_id</code>.
     */
    public Integer getGermplasmId() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>public.dnasample.created_by</code>.
     */
    public void setCreatedBy(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.dnasample.created_by</code>.
     */
    public Integer getCreatedBy() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>public.dnasample.created_date</code>.
     */
    public void setCreatedDate(Date value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.dnasample.created_date</code>.
     */
    public Date getCreatedDate() {
        return (Date) get(10);
    }

    /**
     * Setter for <code>public.dnasample.modified_by</code>.
     */
    public void setModifiedBy(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.dnasample.modified_by</code>.
     */
    public Integer getModifiedBy() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>public.dnasample.modified_date</code>.
     */
    public void setModifiedDate(Date value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.dnasample.modified_date</code>.
     */
    public Date getModifiedDate() {
        return (Date) get(12);
    }

    /**
     * Setter for <code>public.dnasample.status</code>.
     */
    public void setStatus(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>public.dnasample.status</code>.
     */
    public Integer getStatus() {
        return (Integer) get(13);
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
    // Record15 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, String, String, String, String, String, String, Integer, Integer, Integer, Date, Integer, Date, Integer, Object> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, String, String, String, String, String, String, Integer, Integer, Integer, Date, Integer, Date, Integer, Object> valuesRow() {
        return (Row15) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Dnasample.DNASAMPLE.DNASAMPLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Dnasample.DNASAMPLE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Dnasample.DNASAMPLE.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Dnasample.DNASAMPLE.PLATENAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Dnasample.DNASAMPLE.NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Dnasample.DNASAMPLE.WELL_ROW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Dnasample.DNASAMPLE.WELL_COL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return Dnasample.DNASAMPLE.PROJECT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return Dnasample.DNASAMPLE.GERMPLASM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return Dnasample.DNASAMPLE.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field11() {
        return Dnasample.DNASAMPLE.CREATED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return Dnasample.DNASAMPLE.MODIFIED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field13() {
        return Dnasample.DNASAMPLE.MODIFIED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return Dnasample.DNASAMPLE.STATUS;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field15() {
        return Dnasample.DNASAMPLE.PROPS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getDnasampleId();
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
        return getPlatename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getWellRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getWellCol();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getProjectId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getGermplasmId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component11() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getModifiedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component13() {
        return getModifiedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component14() {
        return getStatus();
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
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getDnasampleId();
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
        return getPlatename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getWellRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getWellCol();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getProjectId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getGermplasmId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value11() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getModifiedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value13() {
        return getModifiedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value14() {
        return getStatus();
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
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value1(Integer value) {
        setDnasampleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value3(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value4(String value) {
        setPlatename(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value5(String value) {
        setNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value6(String value) {
        setWellRow(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value7(String value) {
        setWellCol(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value8(Integer value) {
        setProjectId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value9(Integer value) {
        setGermplasmId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value10(Integer value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value11(Date value) {
        setCreatedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value12(Integer value) {
        setModifiedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value13(Date value) {
        setModifiedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord value14(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public DnasampleRecord value15(Object value) {
        setProps(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DnasampleRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, String value7, Integer value8, Integer value9, Integer value10, Date value11, Integer value12, Date value13, Integer value14, Object value15) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DnasampleRecord
     */
    public DnasampleRecord() {
        super(Dnasample.DNASAMPLE);
    }

    /**
     * Create a detached, initialised DnasampleRecord
     */
    public DnasampleRecord(Integer dnasampleId, String name, String code, String platename, String num, String wellRow, String wellCol, Integer projectId, Integer germplasmId, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate, Integer status, Object props) {
        super(Dnasample.DNASAMPLE);

        set(0, dnasampleId);
        set(1, name);
        set(2, code);
        set(3, platename);
        set(4, num);
        set(5, wellRow);
        set(6, wellCol);
        set(7, projectId);
        set(8, germplasmId);
        set(9, createdBy);
        set(10, createdDate);
        set(11, modifiedBy);
        set(12, modifiedDate);
        set(13, status);
        set(14, props);
    }
}
