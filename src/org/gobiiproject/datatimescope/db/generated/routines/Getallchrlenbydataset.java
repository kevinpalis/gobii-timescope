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
public class Getallchrlenbydataset extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = 1609848665;

    /**
     * The parameter <code>public.getallchrlenbydataset.datasetid</code>.
     */
    public static final Parameter<Integer> DATASETID = createParameter("datasetid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getallchrlenbydataset.linkage_group_name</code>.
     */
    public static final Parameter<String> LINKAGE_GROUP_NAME = createParameter("linkage_group_name", org.jooq.impl.SQLDataType.VARCHAR, false, false);

    /**
     * The parameter <code>public.getallchrlenbydataset.linkage_group_length</code>.
     */
    public static final Parameter<Integer> LINKAGE_GROUP_LENGTH = createParameter("linkage_group_length", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public Getallchrlenbydataset() {
        super("getallchrlenbydataset", Public.PUBLIC);

        addInParameter(DATASETID);
        addOutParameter(LINKAGE_GROUP_NAME);
        addOutParameter(LINKAGE_GROUP_LENGTH);
    }

    /**
     * Set the <code>datasetid</code> parameter IN value to the routine
     */
    public void setDatasetid(Integer value) {
        setValue(DATASETID, value);
    }

    /**
     * Get the <code>linkage_group_name</code> parameter OUT value from the routine
     */
    public String getLinkageGroupName() {
        return get(LINKAGE_GROUP_NAME);
    }

    /**
     * Get the <code>linkage_group_length</code> parameter OUT value from the routine
     */
    public Integer getLinkageGroupLength() {
        return get(LINKAGE_GROUP_LENGTH);
    }
}
