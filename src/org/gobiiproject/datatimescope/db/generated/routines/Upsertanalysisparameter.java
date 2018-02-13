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
public class Upsertanalysisparameter extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -502533187;

    /**
     * The parameter <code>public.upsertanalysisparameter.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.upsertanalysisparameter.parametername</code>.
     */
    public static final Parameter<String> PARAMETERNAME = createParameter("parametername", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.upsertanalysisparameter.parametervalue</code>.
     */
    public static final Parameter<String> PARAMETERVALUE = createParameter("parametervalue", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Upsertanalysisparameter() {
        super("upsertanalysisparameter", Public.PUBLIC);

        addInParameter(ID);
        addInParameter(PARAMETERNAME);
        addInParameter(PARAMETERVALUE);
    }

    /**
     * Set the <code>id</code> parameter IN value to the routine
     */
    public void setId(Integer value) {
        setValue(ID, value);
    }

    /**
     * Set the <code>parametername</code> parameter IN value to the routine
     */
    public void setParametername(String value) {
        setValue(PARAMETERNAME, value);
    }

    /**
     * Set the <code>parametervalue</code> parameter IN value to the routine
     */
    public void setParametervalue(String value) {
        setValue(PARAMETERVALUE, value);
    }
}
