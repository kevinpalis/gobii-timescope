/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.routines;


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
public class Appendanalysistodataset extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -1579848155;

    /**
     * The parameter <code>public.appendanalysistodataset.datasetid</code>.
     */
    public static final Parameter<Integer> DATASETID = createParameter("datasetid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.appendanalysistodataset.analysisid</code>.
     */
    public static final Parameter<Integer> ANALYSISID = createParameter("analysisid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Appendanalysistodataset() {
        super("appendanalysistodataset", Public.PUBLIC);

        addInParameter(DATASETID);
        addInParameter(ANALYSISID);
    }

    /**
     * Set the <code>datasetid</code> parameter IN value to the routine
     */
    public void setDatasetid(Integer value) {
        setValue(DATASETID, value);
    }

    /**
     * Set the <code>analysisid</code> parameter IN value to the routine
     */
    public void setAnalysisid(Integer value) {
        setValue(ANALYSISID, value);
    }
}
