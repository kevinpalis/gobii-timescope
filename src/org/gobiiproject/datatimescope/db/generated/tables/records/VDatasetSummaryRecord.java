/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.tables.records;


import java.sql.Date;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.VDatasetSummary;
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
public class VDatasetSummaryRecord extends TableRecordImpl<VDatasetSummaryRecord> implements Record19<Integer, String, Integer, String, Integer, String, Integer[], String, String, String, String, Object, String, Date, String, Date, String, String, String> {

    private static final long serialVersionUID = 1274458995;

    /**
     * Setter for <code>public.v_dataset_summary.dataset_id</code>.
     */
    public void setDatasetId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.dataset_id</code>.
     */
    public Integer getDatasetId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.v_dataset_summary.dataset_name</code>.
     */
    public void setDatasetName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.dataset_name</code>.
     */
    public String getDatasetName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.v_dataset_summary.experiment_id</code>.
     */
    public void setExperimentId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.experiment_id</code>.
     */
    public Integer getExperimentId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.v_dataset_summary.experiment_name</code>.
     */
    public void setExperimentName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.experiment_name</code>.
     */
    public String getExperimentName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.v_dataset_summary.callinganalysis_id</code>.
     */
    public void setCallinganalysisId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.callinganalysis_id</code>.
     */
    public Integer getCallinganalysisId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.v_dataset_summary.callingnalysis_name</code>.
     */
    public void setCallingnalysisName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.callingnalysis_name</code>.
     */
    public String getCallingnalysisName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.v_dataset_summary.analyses</code>.
     */
    public void setAnalyses(Integer... value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.analyses</code>.
     */
    public Integer[] getAnalyses() {
        return (Integer[]) get(6);
    }

    /**
     * Not Generated. to get Analyses values as String
     */
    public String getAnalysesAsString(){
    	StringBuilder sb = new StringBuilder();
    	
    	Integer[] analysesList =  (Integer[]) get(6);

		sb.append("{");
    	for(Integer i: analysesList){
    		sb.append(i.toString()+", ");
    	}
		sb.append("}");
		
    	return sb.toString();
    }
    /**
     * Setter for <code>public.v_dataset_summary.data_table</code>.
     */
    public void setDataTable(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.data_table</code>.
     */
    public String getDataTable() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.v_dataset_summary.data_file</code>.
     */
    public void setDataFile(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.data_file</code>.
     */
    public String getDataFile() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.v_dataset_summary.quality_table</code>.
     */
    public void setQualityTable(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.quality_table</code>.
     */
    public String getQualityTable() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.v_dataset_summary.quality_file</code>.
     */
    public void setQualityFile(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.quality_file</code>.
     */
    public String getQualityFile() {
        return (String) get(10);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public void setScores(Object value) {
        set(11, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getScores() {
        return get(11);
    }

    /**
     * Setter for <code>public.v_dataset_summary.created_by_username</code>.
     */
    public void setCreatedByUsername(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.created_by_username</code>.
     */
    public String getCreatedByUsername() {
        return (String) get(12);
    }

    /**
     * Setter for <code>public.v_dataset_summary.created_date</code>.
     */
    public void setCreatedDate(Date value) {
        set(13, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.created_date</code>.
     */
    public Date getCreatedDate() {
        return (Date) get(13);
    }

    /**
     * Setter for <code>public.v_dataset_summary.modified_by_username</code>.
     */
    public void setModifiedByUsername(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.modified_by_username</code>.
     */
    public String getModifiedByUsername() {
        return (String) get(14);
    }

    /**
     * Setter for <code>public.v_dataset_summary.modified_date</code>.
     */
    public void setModifiedDate(Date value) {
        set(15, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.modified_date</code>.
     */
    public Date getModifiedDate() {
        return (Date) get(15);
    }

    /**
     * Setter for <code>public.v_dataset_summary.status_name</code>.
     */
    public void setStatusName(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.status_name</code>.
     */
    public String getStatusName() {
        return (String) get(16);
    }

    /**
     * Setter for <code>public.v_dataset_summary.type_name</code>.
     */
    public void setTypeName(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.type_name</code>.
     */
    public String getTypeName() {
        return (String) get(17);
    }

    /**
     * Setter for <code>public.v_dataset_summary.job_name</code>.
     */
    public void setJobName(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>public.v_dataset_summary.job_name</code>.
     */
    public String getJobName() {
        return (String) get(18);
    }

    // -------------------------------------------------------------------------
    // Record19 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, String, Integer, String, Integer, String, Integer[], String, String, String, String, Object, String, Date, String, Date, String, String, String> fieldsRow() {
        return (Row19) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, String, Integer, String, Integer, String, Integer[], String, String, String, String, Object, String, Date, String, Date, String, String, String> valuesRow() {
        return (Row19) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return VDatasetSummary.V_DATASET_SUMMARY.DATASET_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return VDatasetSummary.V_DATASET_SUMMARY.DATASET_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return VDatasetSummary.V_DATASET_SUMMARY.EXPERIMENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return VDatasetSummary.V_DATASET_SUMMARY.EXPERIMENT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return VDatasetSummary.V_DATASET_SUMMARY.CALLINGANALYSIS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return VDatasetSummary.V_DATASET_SUMMARY.CALLINGNALYSIS_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer[]> field7() {
        return VDatasetSummary.V_DATASET_SUMMARY.ANALYSES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return VDatasetSummary.V_DATASET_SUMMARY.DATA_TABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return VDatasetSummary.V_DATASET_SUMMARY.DATA_FILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return VDatasetSummary.V_DATASET_SUMMARY.QUALITY_TABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return VDatasetSummary.V_DATASET_SUMMARY.QUALITY_FILE;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Field<Object> field12() {
        return VDatasetSummary.V_DATASET_SUMMARY.SCORES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return VDatasetSummary.V_DATASET_SUMMARY.CREATED_BY_USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field14() {
        return VDatasetSummary.V_DATASET_SUMMARY.CREATED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return VDatasetSummary.V_DATASET_SUMMARY.MODIFIED_BY_USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field16() {
        return VDatasetSummary.V_DATASET_SUMMARY.MODIFIED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field17() {
        return VDatasetSummary.V_DATASET_SUMMARY.STATUS_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field18() {
        return VDatasetSummary.V_DATASET_SUMMARY.TYPE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field19() {
        return VDatasetSummary.V_DATASET_SUMMARY.JOB_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getDatasetId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getDatasetName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getExperimentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getExperimentName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getCallinganalysisId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getCallingnalysisName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer[] component7() {
        return getAnalyses();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getDataTable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getDataFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getQualityTable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getQualityFile();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object component12() {
        return getScores();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getCreatedByUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component14() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component15() {
        return getModifiedByUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component16() {
        return getModifiedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component17() {
        return getStatusName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component18() {
        return getTypeName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component19() {
        return getJobName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getDatasetId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getDatasetName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getExperimentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getExperimentName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCallinganalysisId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getCallingnalysisName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer[] value7() {
        return getAnalyses();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getDataTable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getDataFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getQualityTable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getQualityFile();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public Object value12() {
        return getScores();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getCreatedByUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value14() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getModifiedByUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value16() {
        return getModifiedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value17() {
        return getStatusName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value18() {
        return getTypeName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value19() {
        return getJobName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value1(Integer value) {
        setDatasetId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value2(String value) {
        setDatasetName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value3(Integer value) {
        setExperimentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value4(String value) {
        setExperimentName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value5(Integer value) {
        setCallinganalysisId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value6(String value) {
        setCallingnalysisName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value7(Integer... value) {
        setAnalyses(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value8(String value) {
        setDataTable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value9(String value) {
        setDataFile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value10(String value) {
        setQualityTable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value11(String value) {
        setQualityFile(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    @Override
    public VDatasetSummaryRecord value12(Object value) {
        setScores(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value13(String value) {
        setCreatedByUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value14(Date value) {
        setCreatedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value15(String value) {
        setModifiedByUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value16(Date value) {
        setModifiedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value17(String value) {
        setStatusName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value18(String value) {
        setTypeName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord value19(String value) {
        setJobName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VDatasetSummaryRecord values(Integer value1, String value2, Integer value3, String value4, Integer value5, String value6, Integer[] value7, String value8, String value9, String value10, String value11, Object value12, String value13, Date value14, String value15, Date value16, String value17, String value18, String value19) {
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
     * Create a detached VDatasetSummaryRecord
     */
    public VDatasetSummaryRecord() {
        super(VDatasetSummary.V_DATASET_SUMMARY);
    }

    /**
     * Create a detached, initialised VDatasetSummaryRecord
     */
    public VDatasetSummaryRecord(Integer datasetId, String datasetName, Integer experimentId, String experimentName, Integer callinganalysisId, String callingnalysisName, Integer[] analyses, String dataTable, String dataFile, String qualityTable, String qualityFile, Object scores, String createdByUsername, Date createdDate, String modifiedByUsername, Date modifiedDate, String statusName, String typeName, String jobName) {
        super(VDatasetSummary.V_DATASET_SUMMARY);

        set(0, datasetId);
        set(1, datasetName);
        set(2, experimentId);
        set(3, experimentName);
        set(4, callinganalysisId);
        set(5, callingnalysisName);
        set(6, analyses);
        set(7, dataTable);
        set(8, dataFile);
        set(9, qualityTable);
        set(10, qualityFile);
        set(11, scores);
        set(12, createdByUsername);
        set(13, createdDate);
        set(14, modifiedByUsername);
        set(15, modifiedDate);
        set(16, statusName);
        set(17, typeName);
        set(18, jobName);
    }
}
