/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.routines;


import java.sql.Date;
import java.sql.Timestamp;

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
public class Createanalysis1 extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = 2125327162;

    /**
     * The parameter <code>public.createanalysis.analysisname</code>.
     */
    public static final Parameter<String> ANALYSISNAME = createParameter("analysisname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createanalysis.analysisdescription</code>.
     */
    public static final Parameter<String> ANALYSISDESCRIPTION = createParameter("analysisdescription", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createanalysis.typeid</code>.
     */
    public static final Parameter<Integer> TYPEID = createParameter("typeid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createanalysis.analysisprogram</code>.
     */
    public static final Parameter<String> ANALYSISPROGRAM = createParameter("analysisprogram", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createanalysis.analysisprogramversion</code>.
     */
    public static final Parameter<String> ANALYSISPROGRAMVERSION = createParameter("analysisprogramversion", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createanalysis.aanalysisalgorithm</code>.
     */
    public static final Parameter<String> AANALYSISALGORITHM = createParameter("aanalysisalgorithm", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createanalysis.analysissourcename</code>.
     */
    public static final Parameter<String> ANALYSISSOURCENAME = createParameter("analysissourcename", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createanalysis.analysissourceversion</code>.
     */
    public static final Parameter<String> ANALYSISSOURCEVERSION = createParameter("analysissourceversion", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createanalysis.analysissourceuri</code>.
     */
    public static final Parameter<String> ANALYSISSOURCEURI = createParameter("analysissourceuri", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createanalysis.referenceid</code>.
     */
    public static final Parameter<Integer> REFERENCEID = createParameter("referenceid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createanalysis.analysistimeexecuted</code>.
     */
    public static final Parameter<Timestamp> ANALYSISTIMEEXECUTED = createParameter("analysistimeexecuted", org.jooq.impl.SQLDataType.TIMESTAMP, false, false);

    /**
     * The parameter <code>public.createanalysis.analysisstatus</code>.
     */
    public static final Parameter<Integer> ANALYSISSTATUS = createParameter("analysisstatus", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createanalysis.createdby</code>.
     */
    public static final Parameter<Integer> CREATEDBY = createParameter("createdby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createanalysis.createddate</code>.
     */
    public static final Parameter<Date> CREATEDDATE = createParameter("createddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.createanalysis.modifiedby</code>.
     */
    public static final Parameter<Integer> MODIFIEDBY = createParameter("modifiedby", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createanalysis.modifieddate</code>.
     */
    public static final Parameter<Date> MODIFIEDDATE = createParameter("modifieddate", org.jooq.impl.SQLDataType.DATE, false, false);

    /**
     * The parameter <code>public.createanalysis.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Createanalysis1() {
        super("createanalysis", Public.PUBLIC);

        addInParameter(ANALYSISNAME);
        addInParameter(ANALYSISDESCRIPTION);
        addInParameter(TYPEID);
        addInParameter(ANALYSISPROGRAM);
        addInParameter(ANALYSISPROGRAMVERSION);
        addInParameter(AANALYSISALGORITHM);
        addInParameter(ANALYSISSOURCENAME);
        addInParameter(ANALYSISSOURCEVERSION);
        addInParameter(ANALYSISSOURCEURI);
        addInParameter(REFERENCEID);
        addInParameter(ANALYSISTIMEEXECUTED);
        addInParameter(ANALYSISSTATUS);
        addInParameter(CREATEDBY);
        addInParameter(CREATEDDATE);
        addInParameter(MODIFIEDBY);
        addInParameter(MODIFIEDDATE);
        addOutParameter(ID);
        setOverloaded(true);
    }

    /**
     * Set the <code>analysisname</code> parameter IN value to the routine
     */
    public void setAnalysisname(String value) {
        setValue(ANALYSISNAME, value);
    }

    /**
     * Set the <code>analysisdescription</code> parameter IN value to the routine
     */
    public void setAnalysisdescription(String value) {
        setValue(ANALYSISDESCRIPTION, value);
    }

    /**
     * Set the <code>typeid</code> parameter IN value to the routine
     */
    public void setTypeid(Integer value) {
        setValue(TYPEID, value);
    }

    /**
     * Set the <code>analysisprogram</code> parameter IN value to the routine
     */
    public void setAnalysisprogram(String value) {
        setValue(ANALYSISPROGRAM, value);
    }

    /**
     * Set the <code>analysisprogramversion</code> parameter IN value to the routine
     */
    public void setAnalysisprogramversion(String value) {
        setValue(ANALYSISPROGRAMVERSION, value);
    }

    /**
     * Set the <code>aanalysisalgorithm</code> parameter IN value to the routine
     */
    public void setAanalysisalgorithm(String value) {
        setValue(AANALYSISALGORITHM, value);
    }

    /**
     * Set the <code>analysissourcename</code> parameter IN value to the routine
     */
    public void setAnalysissourcename(String value) {
        setValue(ANALYSISSOURCENAME, value);
    }

    /**
     * Set the <code>analysissourceversion</code> parameter IN value to the routine
     */
    public void setAnalysissourceversion(String value) {
        setValue(ANALYSISSOURCEVERSION, value);
    }

    /**
     * Set the <code>analysissourceuri</code> parameter IN value to the routine
     */
    public void setAnalysissourceuri(String value) {
        setValue(ANALYSISSOURCEURI, value);
    }

    /**
     * Set the <code>referenceid</code> parameter IN value to the routine
     */
    public void setReferenceid(Integer value) {
        setValue(REFERENCEID, value);
    }

    /**
     * Set the <code>analysistimeexecuted</code> parameter IN value to the routine
     */
    public void setAnalysistimeexecuted(Timestamp value) {
        setValue(ANALYSISTIMEEXECUTED, value);
    }

    /**
     * Set the <code>analysisstatus</code> parameter IN value to the routine
     */
    public void setAnalysisstatus(Integer value) {
        setValue(ANALYSISSTATUS, value);
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
     * Get the <code>id</code> parameter OUT value from the routine
     */
    public Integer getId() {
        return get(ID);
    }
}
