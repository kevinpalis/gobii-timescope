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
public class Createtimescoper extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -1630141484;

    /**
     * The parameter <code>public.createtimescoper._firstname</code>.
     */
    public static final Parameter<String> _FIRSTNAME = createParameter("_firstname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createtimescoper._lastname</code>.
     */
    public static final Parameter<String> _LASTNAME = createParameter("_lastname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createtimescoper._username</code>.
     */
    public static final Parameter<String> _USERNAME = createParameter("_username", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createtimescoper._password</code>.
     */
    public static final Parameter<String> _PASSWORD = createParameter("_password", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createtimescoper._email</code>.
     */
    public static final Parameter<String> _EMAIL = createParameter("_email", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.createtimescoper._role</code>.
     */
    public static final Parameter<Integer> _ROLE = createParameter("_role", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.createtimescoper.id</code>.
     */
    public static final Parameter<Integer> ID = createParameter("id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Createtimescoper() {
        super("createtimescoper", Public.PUBLIC);

        addInParameter(_FIRSTNAME);
        addInParameter(_LASTNAME);
        addInParameter(_USERNAME);
        addInParameter(_PASSWORD);
        addInParameter(_EMAIL);
        addInParameter(_ROLE);
        addOutParameter(ID);
    }

    /**
     * Set the <code>_firstname</code> parameter IN value to the routine
     */
    public void set_Firstname(String value) {
        setValue(_FIRSTNAME, value);
    }

    /**
     * Set the <code>_lastname</code> parameter IN value to the routine
     */
    public void set_Lastname(String value) {
        setValue(_LASTNAME, value);
    }

    /**
     * Set the <code>_username</code> parameter IN value to the routine
     */
    public void set_Username(String value) {
        setValue(_USERNAME, value);
    }

    /**
     * Set the <code>_password</code> parameter IN value to the routine
     */
    public void set_Password(String value) {
        setValue(_PASSWORD, value);
    }

    /**
     * Set the <code>_email</code> parameter IN value to the routine
     */
    public void set_Email(String value) {
        setValue(_EMAIL, value);
    }

    /**
     * Set the <code>_role</code> parameter IN value to the routine
     */
    public void set_Role(Integer value) {
        setValue(_ROLE, value);
    }

    /**
     * Get the <code>id</code> parameter OUT value from the routine
     */
    public Integer getId() {
        return get(ID);
    }
}