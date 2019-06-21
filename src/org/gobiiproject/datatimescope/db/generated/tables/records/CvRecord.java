/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.Cv;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * A term, class, universal or type within an
 * ontology or controlled vocabulary.  This table is also used for
 * relations and properties. cvterms constitute nodes in the graph
 * defined by the collection of cvterms and cvterm_relationships.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CvRecord extends UpdatableRecordImpl<CvRecord> implements Record9<Integer, String, String, Integer, Integer, String, Integer, Integer, Object> {

    private static final long serialVersionUID = -263639929;

    /**
     * Setter for <code>public.cv.cv_id</code>.
     */
    public void setCvId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.cv.cv_id</code>.
     */
    public Integer getCvId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.cv.term</code>. A concise human-readable name or
label for the cvterm. Uniquely identifies a cvterm within a cv.
     */
    public void setTerm(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.cv.term</code>. A concise human-readable name or
label for the cvterm. Uniquely identifies a cvterm within a cv.
     */
    public String getTerm() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.cv.definition</code>. A human-readable text
definition.
     */
    public void setDefinition(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.cv.definition</code>. A human-readable text
definition.
     */
    public String getDefinition() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.cv.rank</code>.
     */
    public void setRank(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.cv.rank</code>.
     */
    public Integer getRank() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.cv.cvgroup_id</code>. The cv or ontology or namespace to which
this cvterm belongs.
     */
    public void setCvgroupId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.cv.cvgroup_id</code>. The cv or ontology or namespace to which
this cvterm belongs.
     */
    public Integer getCvgroupId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.cv.abbreviation</code>.
     */
    public void setAbbreviation(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.cv.abbreviation</code>.
     */
    public String getAbbreviation() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.cv.dbxref_id</code>. Primary identifier dbxref - The
unique global OBO identifier for this cvterm.  
     */
    public void setDbxrefId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.cv.dbxref_id</code>. Primary identifier dbxref - The
unique global OBO identifier for this cvterm.  
     */
    public Integer getDbxrefId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>public.cv.status</code>.
     */
    public void setStatus(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.cv.status</code>.
     */
    public Integer getStatus() {
        return (Integer) get(7);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setProps(Object value) {
        set(8, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getProps() {
        return get(8);
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
    public Row9<Integer, String, String, Integer, Integer, String, Integer, Integer, Object> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, String, Integer, Integer, String, Integer, Integer, Object> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Cv.CV.CV_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Cv.CV.TERM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Cv.CV.DEFINITION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return Cv.CV.RANK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Cv.CV.CVGROUP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Cv.CV.ABBREVIATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return Cv.CV.DBXREF_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return Cv.CV.STATUS;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field9() {
        return Cv.CV.PROPS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getCvId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getTerm();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getDefinition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getRank();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getCvgroupId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getAbbreviation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getDbxrefId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getStatus();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component9() {
        return getProps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getCvId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getTerm();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDefinition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getRank();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCvgroupId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getAbbreviation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getDbxrefId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getStatus();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value9() {
        return getProps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CvRecord value1(Integer value) {
        setCvId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CvRecord value2(String value) {
        setTerm(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CvRecord value3(String value) {
        setDefinition(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CvRecord value4(Integer value) {
        setRank(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CvRecord value5(Integer value) {
        setCvgroupId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CvRecord value6(String value) {
        setAbbreviation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CvRecord value7(Integer value) {
        setDbxrefId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CvRecord value8(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public CvRecord value9(Object value) {
        setProps(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CvRecord values(Integer value1, String value2, String value3, Integer value4, Integer value5, String value6, Integer value7, Integer value8, Object value9) {
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
     * Create a detached CvRecord
     */
    public CvRecord() {
        super(Cv.CV);
    }

    /**
     * Create a detached, initialised CvRecord
     */
    public CvRecord(Integer cvId, String term, String definition, Integer rank, Integer cvgroupId, String abbreviation, Integer dbxrefId, Integer status, Object props) {
        super(Cv.CV);

        set(0, cvId);
        set(1, term);
        set(2, definition);
        set(3, rank);
        set(4, cvgroupId);
        set(5, abbreviation);
        set(6, dbxrefId);
        set(7, status);
        set(8, props);
    }
}
