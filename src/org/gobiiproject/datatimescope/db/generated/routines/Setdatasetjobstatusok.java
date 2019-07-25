/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.routines;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.jooq.Field;
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
public class Setdatasetjobstatusok extends AbstractRoutine<Integer> {

    private static final long serialVersionUID = -74896292;

    /**
     * The parameter <code>public.setdatasetjobstatusok.RETURN_VALUE</code>.
     */
    public static final Parameter<Integer> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.setdatasetjobstatusok.datasetid</code>.
     */
    public static final Parameter<Integer> DATASETID = createParameter("datasetid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Setdatasetjobstatusok() {
        super("setdatasetjobstatusok", Public.PUBLIC, org.jooq.impl.SQLDataType.INTEGER);

        setReturnParameter(RETURN_VALUE);
        addInParameter(DATASETID);
    }

    /**
     * Set the <code>datasetid</code> parameter IN value to the routine
     */
    public void setDatasetid(Integer value) {
        setValue(DATASETID, value);
    }

    /**
     * Set the <code>datasetid</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setDatasetid(Field<Integer> field) {
        setField(DATASETID, field);
    }
}
