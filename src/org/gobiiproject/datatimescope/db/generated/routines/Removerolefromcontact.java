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
public class Removerolefromcontact extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -1406979880;

    /**
     * The parameter <code>public.removerolefromcontact.contactid</code>.
     */
    public static final Parameter<Integer> CONTACTID = createParameter("contactid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.removerolefromcontact.roleid</code>.
     */
    public static final Parameter<Integer> ROLEID = createParameter("roleid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Removerolefromcontact() {
        super("removerolefromcontact", Public.PUBLIC);

        addInParameter(CONTACTID);
        addInParameter(ROLEID);
    }

    /**
     * Set the <code>contactid</code> parameter IN value to the routine
     */
    public void setContactid(Integer value) {
        setValue(CONTACTID, value);
    }

    /**
     * Set the <code>roleid</code> parameter IN value to the routine
     */
    public void setRoleid(Integer value) {
        setValue(ROLEID, value);
    }
}
