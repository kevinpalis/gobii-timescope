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
public class Gettimescoper extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -337079041;

    /**
     * The parameter <code>public.gettimescoper._username</code>.
     */
    public static final Parameter<String> _USERNAME = createParameter("_username", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.gettimescoper._password</code>.
     */
    public static final Parameter<String> _PASSWORD = createParameter("_password", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.gettimescoper.firstname</code>.
     */
    public static final Parameter<String> FIRSTNAME = createParameter("firstname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.gettimescoper.lastname</code>.
     */
    public static final Parameter<String> LASTNAME = createParameter("lastname", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.gettimescoper.username</code>.
     */
    public static final Parameter<String> USERNAME = createParameter("username", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.gettimescoper.email</code>.
     */
    public static final Parameter<String> EMAIL = createParameter("email", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.gettimescoper.role</code>.
     */
    public static final Parameter<Integer> ROLE = createParameter("role", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Gettimescoper() {
        super("gettimescoper", Public.PUBLIC);

        addInParameter(_USERNAME);
        addInParameter(_PASSWORD);
        addOutParameter(FIRSTNAME);
        addOutParameter(LASTNAME);
        addOutParameter(USERNAME);
        addOutParameter(EMAIL);
        addOutParameter(ROLE);
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
     * Get the <code>firstname</code> parameter OUT value from the routine
     */
    public String getFirstname() {
        return get(FIRSTNAME);
    }

    /**
     * Get the <code>lastname</code> parameter OUT value from the routine
     */
    public String getLastname() {
        return get(LASTNAME);
    }

    /**
     * Get the <code>username</code> parameter OUT value from the routine
     */
    public String getUsername() {
        return get(USERNAME);
    }

    /**
     * Get the <code>email</code> parameter OUT value from the routine
     */
    public String getEmail() {
        return get(EMAIL);
    }

    /**
     * Get the <code>role</code> parameter OUT value from the routine
     */
    public Integer getRole() {
        return get(ROLE);
    }
}