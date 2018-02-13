/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.Getminimalsamplemetadatabydataset;
import org.jooq.Field;
import org.jooq.Record10;
import org.jooq.Row10;
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
public class GetminimalsamplemetadatabydatasetRecord extends TableRecordImpl<GetminimalsamplemetadatabydatasetRecord> implements Record10<String, String, String, String, String, String, String, String, String, String> {

    private static final long serialVersionUID = 1008754486;

    /**
     * Setter for <code>public.getminimalsamplemetadatabydataset.dnarun_name</code>.
     */
    public void setDnarunName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.getminimalsamplemetadatabydataset.dnarun_name</code>.
     */
    public String getDnarunName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.getminimalsamplemetadatabydataset.sample_name</code>.
     */
    public void setSampleName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.getminimalsamplemetadatabydataset.sample_name</code>.
     */
    public String getSampleName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.getminimalsamplemetadatabydataset.germplasm_name</code>.
     */
    public void setGermplasmName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.getminimalsamplemetadatabydataset.germplasm_name</code>.
     */
    public String getGermplasmName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.getminimalsamplemetadatabydataset.external_code</code>.
     */
    public void setExternalCode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.getminimalsamplemetadatabydataset.external_code</code>.
     */
    public String getExternalCode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.getminimalsamplemetadatabydataset.germplasm_type</code>.
     */
    public void setGermplasmType(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.getminimalsamplemetadatabydataset.germplasm_type</code>.
     */
    public String getGermplasmType() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.getminimalsamplemetadatabydataset.species</code>.
     */
    public void setSpecies(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.getminimalsamplemetadatabydataset.species</code>.
     */
    public String getSpecies() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.getminimalsamplemetadatabydataset.platename</code>.
     */
    public void setPlatename(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.getminimalsamplemetadatabydataset.platename</code>.
     */
    public String getPlatename() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.getminimalsamplemetadatabydataset.num</code>.
     */
    public void setNum(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.getminimalsamplemetadatabydataset.num</code>.
     */
    public String getNum() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.getminimalsamplemetadatabydataset.well_row</code>.
     */
    public void setWellRow(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.getminimalsamplemetadatabydataset.well_row</code>.
     */
    public String getWellRow() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.getminimalsamplemetadatabydataset.well_col</code>.
     */
    public void setWellCol(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.getminimalsamplemetadatabydataset.well_col</code>.
     */
    public String getWellCol() {
        return (String) get(9);
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<String, String, String, String, String, String, String, String, String, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<String, String, String, String, String, String, String, String, String, String> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET.DNARUN_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET.SAMPLE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET.GERMPLASM_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET.EXTERNAL_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET.GERMPLASM_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET.SPECIES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET.PLATENAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET.NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET.WELL_ROW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET.WELL_COL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getDnarunName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getSampleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getGermplasmName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getExternalCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getGermplasmType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getSpecies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getPlatename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getWellRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getWellCol();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getDnarunName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getSampleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getGermplasmName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getExternalCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getGermplasmType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getSpecies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getPlatename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getWellRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getWellCol();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord value1(String value) {
        setDnarunName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord value2(String value) {
        setSampleName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord value3(String value) {
        setGermplasmName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord value4(String value) {
        setExternalCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord value5(String value) {
        setGermplasmType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord value6(String value) {
        setSpecies(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord value7(String value) {
        setPlatename(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord value8(String value) {
        setNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord value9(String value) {
        setWellRow(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord value10(String value) {
        setWellCol(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetminimalsamplemetadatabydatasetRecord values(String value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8, String value9, String value10) {
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
     * Create a detached GetminimalsamplemetadatabydatasetRecord
     */
    public GetminimalsamplemetadatabydatasetRecord() {
        super(Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET);
    }

    /**
     * Create a detached, initialised GetminimalsamplemetadatabydatasetRecord
     */
    public GetminimalsamplemetadatabydatasetRecord(String dnarunName, String sampleName, String germplasmName, String externalCode, String germplasmType, String species, String platename, String num, String wellRow, String wellCol) {
        super(Getminimalsamplemetadatabydataset.GETMINIMALSAMPLEMETADATABYDATASET);

        set(0, dnarunName);
        set(1, sampleName);
        set(2, germplasmName);
        set(3, externalCode);
        set(4, germplasmType);
        set(5, species);
        set(6, platename);
        set(7, num);
        set(8, wellRow);
        set(9, wellCol);
    }
}
