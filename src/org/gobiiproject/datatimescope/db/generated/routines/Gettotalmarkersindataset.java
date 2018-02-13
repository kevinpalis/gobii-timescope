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
public class Gettotalmarkersindataset extends AbstractRoutine<Integer> {

    private static final long serialVersionUID = -1820492918;

    /**
     * The parameter <code>public.gettotalmarkersindataset.RETURN_VALUE</code>.
     */
    public static final Parameter<Integer> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.gettotalmarkersindataset._dataset_id</code>.
     */
    public static final Parameter<String> _DATASET_ID = createParameter("_dataset_id", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Gettotalmarkersindataset() {
        super("gettotalmarkersindataset", Public.PUBLIC, org.jooq.impl.SQLDataType.INTEGER);

        setReturnParameter(RETURN_VALUE);
        addInParameter(_DATASET_ID);
    }

    /**
     * Set the <code>_dataset_id</code> parameter IN value to the routine
     */
    public void set_DatasetId(String value) {
        setValue(_DATASET_ID, value);
    }

    /**
     * Set the <code>_dataset_id</code> parameter to the function to be used with a {@link org.jooq.Select} statement
     */
    public void set_DatasetId(Field<String> field) {
        setField(_DATASET_ID, field);
    }
}
