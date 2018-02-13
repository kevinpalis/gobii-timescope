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
public class Removewritetablefromrole extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = 1297598777;

    /**
     * The parameter <code>public.removewritetablefromrole.roleid</code>.
     */
    public static final Parameter<Integer> ROLEID = createParameter("roleid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.removewritetablefromrole.tableid</code>.
     */
    public static final Parameter<Integer> TABLEID = createParameter("tableid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Removewritetablefromrole() {
        super("removewritetablefromrole", Public.PUBLIC);

        addInParameter(ROLEID);
        addInParameter(TABLEID);
    }

    /**
     * Set the <code>roleid</code> parameter IN value to the routine
     */
    public void setRoleid(Integer value) {
        setValue(ROLEID, value);
    }

    /**
     * Set the <code>tableid</code> parameter IN value to the routine
     */
    public void setTableid(Integer value) {
        setValue(TABLEID, value);
    }
}
