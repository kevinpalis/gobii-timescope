/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.routines;


import java.sql.Date;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;


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
public class Updatedataset extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = 1423000905;

    /**
     * The parameter <code>public.updatedataset.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatedataset.datasetname</code>.
     */
    public static final Parameter<String> DATASETNAME = createParameter("datasetname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatedataset.experimentid</code>.
     */
    public static final Parameter<Integer> EXPERIMENTID = createParameter("experimentid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatedataset.callinganalysisid</code>.
     */
    public static final Parameter<Integer> CALLINGANALYSISID = createParameter("callinganalysisid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatedataset.datasetanalyses</code>.
     */
    public static final Parameter<Integer[]> DATASETANALYSES = createParameter("datasetanalyses", org.jooq.impl.SQLDataType.INTEGER.getArrayDataType(), false, false);

    /**
     * The parameter <code>public.updatedataset.datatable</code>.
     */
    public static final Parameter<String> DATATABLE = createParameter("datatable", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatedataset.datafile</code>.
     */
    public static final Parameter<String> DATAFILE = createParameter("datafile", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatedataset.qualitytable</code>.
     */
    public static final Parameter<String> QUALITYTABLE = createParameter("qualitytable", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatedataset.qualityfile</code>.
     */
    public static final Parameter<String> QUALITYFILE = createParameter("qualityfile", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatedataset.createdby</code>.
     */
    public static final Parameter<Integer> CREATEDBY = createParameter("createdby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatedataset.createddate</code>.
     */
    public static final Parameter<Date> CREATEDDATE = createParameter("createddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.updatedataset.modifiedby</code>.
     */
    public static final Parameter<Integer> MODIFIEDBY = createParameter("modifiedby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatedataset.modifieddate</code>.
     */
    public static final Parameter<Date> MODIFIEDDATE = createParameter("modifieddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.updatedataset.datasetstatus</code>.
     */
    public static final Parameter<Integer> DATASETSTATUS = createParameter("datasetstatus", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatedataset.typeid</code>.
     */
    public static final Parameter<Integer> TYPEID = createParameter("typeid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatedataset.jobid</code>.
     */
    public static final Parameter<Integer> JOBID = createParameter("jobid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Updatedataset() {
        super("updatedataset", Public.PUBLIC);

        addInParameter(ID);
        addInParameter(DATASETNAME);
        addInParameter(EXPERIMENTID);
        addInParameter(CALLINGANALYSISID);
        addInParameter(DATASETANALYSES);
        addInParameter(DATATABLE);
        addInParameter(DATAFILE);
        addInParameter(QUALITYTABLE);
        addInParameter(QUALITYFILE);
        addInParameter(CREATEDBY);
        addInParameter(CREATEDDATE);
        addInParameter(MODIFIEDBY);
        addInParameter(MODIFIEDDATE);
        addInParameter(DATASETSTATUS);
        addInParameter(TYPEID);
        addInParameter(JOBID);
    }

    /**
     * Set the <code>id</code> parameter IN value to the routine
     */
    public void setId(Integer value) {
        setValue(ID, value);
    }

    /**
     * Set the <code>datasetname</code> parameter IN value to the routine
     */
    public void setDatasetname(String value) {
        setValue(DATASETNAME, value);
    }

    /**
     * Set the <code>experimentid</code> parameter IN value to the routine
     */
    public void setExperimentid(Integer value) {
        setValue(EXPERIMENTID, value);
    }

    /**
     * Set the <code>callinganalysisid</code> parameter IN value to the routine
     */
    public void setCallinganalysisid(Integer value) {
        setValue(CALLINGANALYSISID, value);
    }

    /**
     * Set the <code>datasetanalyses</code> parameter IN value to the routine
     */
    public void setDatasetanalyses(Integer... value) {
        setValue(DATASETANALYSES, value);
    }

    /**
     * Set the <code>datatable</code> parameter IN value to the routine
     */
    public void setDatatable(String value) {
        setValue(DATATABLE, value);
    }

    /**
     * Set the <code>datafile</code> parameter IN value to the routine
     */
    public void setDatafile(String value) {
        setValue(DATAFILE, value);
    }

    /**
     * Set the <code>qualitytable</code> parameter IN value to the routine
     */
    public void setQualitytable(String value) {
        setValue(QUALITYTABLE, value);
    }

    /**
     * Set the <code>qualityfile</code> parameter IN value to the routine
     */
    public void setQualityfile(String value) {
        setValue(QUALITYFILE, value);
    }

    /**
     * Set the <code>createdby</code> parameter IN value to the routine
     */
    public void setCreatedby(Integer value) {
        setValue(CREATEDBY, value);
    }

    /**
     * Set the <code>createddate</code> parameter IN value to the routine
     */
    public void setCreateddate(Date value) {
        setValue(CREATEDDATE, value);
    }

    /**
     * Set the <code>modifiedby</code> parameter IN value to the routine
     */
    public void setModifiedby(Integer value) {
        setValue(MODIFIEDBY, value);
    }

    /**
     * Set the <code>modifieddate</code> parameter IN value to the routine
     */
    public void setModifieddate(Date value) {
        setValue(MODIFIEDDATE, value);
    }

    /**
     * Set the <code>datasetstatus</code> parameter IN value to the routine
     */
    public void setDatasetstatus(Integer value) {
        setValue(DATASETSTATUS, value);
    }

    /**
     * Set the <code>typeid</code> parameter IN value to the routine
     */
    public void setTypeid(Integer value) {
        setValue(TYPEID, value);
    }

    /**
     * Set the <code>jobid</code> parameter IN value to the routine
     */
    public void setJobid(Integer value) {
        setValue(JOBID, value);
    }
}
