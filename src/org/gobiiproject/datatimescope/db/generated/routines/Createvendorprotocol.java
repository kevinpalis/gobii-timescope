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
public class Createvendorprotocol extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -1570007264;

    /**
     * The parameter <code>public.createvendorprotocol.pname</code>.
     */
    public static final Parameter<String> PNAME = createParameter("pname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createvendorprotocol.pvendorid</code>.
     */
    public static final Parameter<Integer> PVENDORID = createParameter("pvendorid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createvendorprotocol.pprotocolid</code>.
     */
    public static final Parameter<Integer> PPROTOCOLID = createParameter("pprotocolid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createvendorprotocol.pstatus</code>.
     */
    public static final Parameter<Integer> PSTATUS = createParameter("pstatus", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createvendorprotocol.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Createvendorprotocol() {
        super("createvendorprotocol", Public.PUBLIC);

        addInParameter(PNAME);
        addInParameter(PVENDORID);
        addInParameter(PPROTOCOLID);
        addInParameter(PSTATUS);
        addOutParameter(ID);
    }

    /**
     * Set the <code>pname</code> parameter IN value to the routine
     */
    public void setPname(String value) {
        setValue(PNAME, value);
    }

    /**
     * Set the <code>pvendorid</code> parameter IN value to the routine
     */
    public void setPvendorid(Integer value) {
        setValue(PVENDORID, value);
    }

    /**
     * Set the <code>pprotocolid</code> parameter IN value to the routine
     */
    public void setPprotocolid(Integer value) {
        setValue(PPROTOCOLID, value);
    }

    /**
     * Set the <code>pstatus</code> parameter IN value to the routine
     */
    public void setPstatus(Integer value) {
        setValue(PSTATUS, value);
    }

    /**
     * Get the <code>id</code> parameter OUT value from the routine
     */
    public Integer getId() {
        return get(ID);
    }
}
