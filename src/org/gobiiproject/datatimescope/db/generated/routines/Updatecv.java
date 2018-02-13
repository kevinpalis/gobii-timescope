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
public class Updatecv extends AbstractRoutine<Integer> {

    private static final long serialVersionUID = -325151293;

    /**
     * The parameter <code>public.updatecv.RETURN_VALUE</code>.
     */
    public static final Parameter<Integer> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatecv.pid</code>.
     */
    public static final Parameter<Integer> PID = createParameter("pid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatecv.pcvgroupid</code>.
     */
    public static final Parameter<Integer> PCVGROUPID = createParameter("pcvgroupid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatecv.pcvterm</code>.
     */
    public static final Parameter<String> PCVTERM = createParameter("pcvterm", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatecv.pcvdefinition</code>.
     */
    public static final Parameter<String> PCVDEFINITION = createParameter("pcvdefinition", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatecv.pcvrank</code>.
     */
    public static final Parameter<Integer> PCVRANK = createParameter("pcvrank", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatecv.pabbreviation</code>.
     */
    public static final Parameter<String> PABBREVIATION = createParameter("pabbreviation", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.updatecv.pdbxrefid</code>.
     */
    public static final Parameter<Integer> PDBXREFID = createParameter("pdbxrefid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.updatecv.pstatus</code>.
     */
    public static final Parameter<Integer> PSTATUS = createParameter("pstatus", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Updatecv() {
        super("updatecv", Public.PUBLIC, org.jooq.impl.SQLDataType.INTEGER);

        setReturnParameter(RETURN_VALUE);
        addInParameter(PID);
        addInParameter(PCVGROUPID);
        addInParameter(PCVTERM);
        addInParameter(PCVDEFINITION);
        addInParameter(PCVRANK);
        addInParameter(PABBREVIATION);
        addInParameter(PDBXREFID);
        addInParameter(PSTATUS);
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
     * Set the <code>pcvgroupid</code> parameter IN value to the routine
     */
    public void setPcvgroupid(Integer value) {
        setValue(PCVGROUPID, value);
    }

    /**
     * Set the <code>pcvgroupid</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPcvgroupid(Field<Integer> field) {
        setField(PCVGROUPID, field);
    }

    /**
     * Set the <code>pcvterm</code> parameter IN value to the routine
     */
    public void setPcvterm(String value) {
        setValue(PCVTERM, value);
    }

    /**
     * Set the <code>pcvterm</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPcvterm(Field<String> field) {
        setField(PCVTERM, field);
    }

    /**
     * Set the <code>pcvdefinition</code> parameter IN value to the routine
     */
    public void setPcvdefinition(String value) {
        setValue(PCVDEFINITION, value);
    }

    /**
     * Set the <code>pcvdefinition</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPcvdefinition(Field<String> field) {
        setField(PCVDEFINITION, field);
    }

    /**
     * Set the <code>pcvrank</code> parameter IN value to the routine
     */
    public void setPcvrank(Integer value) {
        setValue(PCVRANK, value);
    }

    /**
     * Set the <code>pcvrank</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPcvrank(Field<Integer> field) {
        setField(PCVRANK, field);
    }

    /**
     * Set the <code>pabbreviation</code> parameter IN value to the routine
     */
    public void setPabbreviation(String value) {
        setValue(PABBREVIATION, value);
    }

    /**
     * Set the <code>pabbreviation</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPabbreviation(Field<String> field) {
        setField(PABBREVIATION, field);
    }

    /**
     * Set the <code>pdbxrefid</code> parameter IN value to the routine
     */
    public void setPdbxrefid(Integer value) {
        setValue(PDBXREFID, value);
    }

    /**
     * Set the <code>pdbxrefid</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPdbxrefid(Field<Integer> field) {
        setField(PDBXREFID, field);
    }

    /**
     * Set the <code>pstatus</code> parameter IN value to the routine
     */
    public void setPstatus(Integer value) {
        setValue(PSTATUS, value);
    }

    /**
     * Set the <code>pstatus</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void setPstatus(Field<Integer> field) {
        setField(PSTATUS, field);
    }
}
