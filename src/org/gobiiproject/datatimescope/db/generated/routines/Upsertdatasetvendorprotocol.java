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
public class Upsertdatasetvendorprotocol extends AbstractRoutine<Integer> {

    private static final long serialVersionUID = -197512550;

    /**
     * The parameter <code>public.upsertdatasetvendorprotocol.RETURN_VALUE</code>.
     */
    public static final Parameter<Integer> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.upsertdatasetvendorprotocol.pid</code>.
     */
    public static final Parameter<Integer> PID = createParameter("pid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.upsertdatasetvendorprotocol.pdatasetid</code>.
     */
    public static final Parameter<Integer> PDATASETID = createParameter("pdatasetid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.upsertdatasetvendorprotocol.pvendorprotocolid</code>.
     */
    public static final Parameter<Integer> PVENDORPROTOCOLID = createParameter("pvendorprotocolid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Upsertdatasetvendorprotocol() {
        super("upsertdatasetvendorprotocol", Public.PUBLIC, org.jooq.impl.SQLDataType.INTEGER);

        setReturnParameter(RETURN_VALUE);
        addInParameter(PID);
        addInParameter(PDATASETID);
        addInParameter(PVENDORPROTOCOLID);
    }

    /**
     * Set the <code>pid</code> parameter IN value to the routine
     */
    public void setPid(Integer value) {
        setValue(PID, value);
    }

    /**
     * Set the <code>pid</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPid(Field<Integer> field) {
        setField(PID, field);
    }

    /**
     * Set the <code>pdatasetid</code> parameter IN value to the routine
     */
    public void setPdatasetid(Integer value) {
        setValue(PDATASETID, value);
    }

    /**
     * Set the <code>pdatasetid</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPdatasetid(Field<Integer> field) {
        setField(PDATASETID, field);
    }

    /**
     * Set the <code>pvendorprotocolid</code> parameter IN value to the routine
     */
    public void setPvendorprotocolid(Integer value) {
        setValue(PVENDORPROTOCOLID, value);
    }

    /**
     * Set the <code>pvendorprotocolid</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPvendorprotocolid(Field<Integer> field) {
        setField(PVENDORPROTOCOLID, field);
    }
}
