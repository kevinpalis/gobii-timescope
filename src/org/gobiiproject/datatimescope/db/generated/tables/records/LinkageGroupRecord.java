/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import java.sql.Date;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.LinkageGroup;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This table will contain different linkage groups, ie. Chromosome 1, Chromosome 
 * 2, etc. along with their respective start and stop boundaries.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LinkageGroupRecord extends UpdatableRecordImpl<LinkageGroupRecord> implements Record9<Integer, String, Integer, Integer, Integer, Integer, Date, Integer, Date> {

    private static final long serialVersionUID = -560774686;

    /**
     * Setter for <code>public.linkage_group.linkage_group_id</code>.
     */
    public void setLinkageGroupId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.linkage_group.linkage_group_id</code>.
     */
    public Integer getLinkageGroupId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.linkage_group.name</code>. ex. Chromosome 1, Chromosome 2, ..., ChromosomeN, LG01, LG02, etc.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.linkage_group.name</code>. ex. Chromosome 1, Chromosome 2, ..., ChromosomeN, LG01, LG02, etc.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.linkage_group.start</code>. Start of the linkage group. 0-based, interbased coordinates.
     */
    public void setStart(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.linkage_group.start</code>. Start of the linkage group. 0-based, interbased coordinates.
     */
    public Integer getStart() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.linkage_group.stop</code>. The maximum position in the linkage group, ex. 200, 200000000

     */
    public void setStop(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.linkage_group.stop</code>. The maximum position in the linkage group, ex. 200, 200000000

     */
    public Integer getStop() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.linkage_group.map_id</code>. Foreign key to the Map table. This defines which map the linkage group belongs to.
     */
    public void setMapId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.linkage_group.map_id</code>. Foreign key to the Map table. This defines which map the linkage group belongs to.
     */
    public Integer getMapId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.linkage_group.created_by</code>.
     */
    public void setCreatedBy(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.linkage_group.created_by</code>.
     */
    public Integer getCreatedBy() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>public.linkage_group.created_date</code>.
     */
    public void setCreatedDate(Date value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.linkage_group.created_date</code>.
     */
    public Date getCreatedDate() {
        return (Date) get(6);
    }

    /**
     * Setter for <code>public.linkage_group.modified_by</code>.
     */
    public void setModifiedBy(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.linkage_group.modified_by</code>.
     */
    public Integer getModifiedBy() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>public.linkage_group.modified_date</code>.
     */
    public void setModifiedDate(Date value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.linkage_group.modified_date</code>.
     */
    public Date getModifiedDate() {
        return (Date) get(8);
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
    public Row9<Integer, String, Integer, Integer, Integer, Integer, Date, Integer, Date> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, Integer, Integer, Integer, Integer, Date, Integer, Date> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return LinkageGroup.LINKAGE_GROUP.LINKAGE_GROUP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return LinkageGroup.LINKAGE_GROUP.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return LinkageGroup.LINKAGE_GROUP.START;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return LinkageGroup.LINKAGE_GROUP.STOP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return LinkageGroup.LINKAGE_GROUP.MAP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return LinkageGroup.LINKAGE_GROUP.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field7() {
        return LinkageGroup.LINKAGE_GROUP.CREATED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return LinkageGroup.LINKAGE_GROUP.MODIFIED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field9() {
        return LinkageGroup.LINKAGE_GROUP.MODIFIED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getLinkageGroupId();
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
    public Integer component3() {
        return getStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getMapId();
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
    public Integer value1() {
        return getLinkageGroupId();
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
    public Integer value3() {
        return getStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getMapId();
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
    public LinkageGroupRecord value1(Integer value) {
        setLinkageGroupId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkageGroupRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkageGroupRecord value3(Integer value) {
        setStart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkageGroupRecord value4(Integer value) {
        setStop(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkageGroupRecord value5(Integer value) {
        setMapId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkageGroupRecord value6(Integer value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkageGroupRecord value7(Date value) {
        setCreatedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkageGroupRecord value8(Integer value) {
        setModifiedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkageGroupRecord value9(Date value) {
        setModifiedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkageGroupRecord values(Integer value1, String value2, Integer value3, Integer value4, Integer value5, Integer value6, Date value7, Integer value8, Date value9) {
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
     * Create a detached LinkageGroupRecord
     */
    public LinkageGroupRecord() {
        super(LinkageGroup.LINKAGE_GROUP);
    }

    /**
     * Create a detached, initialised LinkageGroupRecord
     */
    public LinkageGroupRecord(Integer linkageGroupId, String name, Integer start, Integer stop, Integer mapId, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
        super(LinkageGroup.LINKAGE_GROUP);

        set(0, linkageGroupId);
        set(1, name);
        set(2, start);
        set(3, stop);
        set(4, mapId);
        set(5, createdBy);
        set(6, createdDate);
        set(7, modifiedBy);
        set(8, modifiedDate);
    }
}
