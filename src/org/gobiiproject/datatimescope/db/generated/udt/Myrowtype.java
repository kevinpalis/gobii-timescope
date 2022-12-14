/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated.udt;


import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.Public;
import org.gobiiproject.datatimescope.db.generated.udt.records.MyrowtypeRecord;
import org.jooq.Schema;
import org.jooq.UDTField;
import org.jooq.impl.UDTImpl;


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
public class Myrowtype extends UDTImpl<MyrowtypeRecord> {

    private static final long serialVersionUID = -714698494;

    /**
     * The reference instance of <code>public.myrowtype</code>
     */
    public static final Myrowtype MYROWTYPE = new Myrowtype();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MyrowtypeRecord> getRecordType() {
        return MyrowtypeRecord.class;
    }

    /**
     * The attribute <code>public.myrowtype.23</code>.
     */
    public static final UDTField<MyrowtypeRecord, String> _23 = createField("23", org.jooq.impl.SQLDataType.CLOB, MYROWTYPE, "");

    /**
     * The attribute <code>public.myrowtype.24</code>.
     */
    public static final UDTField<MyrowtypeRecord, String> _24 = createField("24", org.jooq.impl.SQLDataType.CLOB, MYROWTYPE, "");

    /**
     * The attribute <code>public.myrowtype.25</code>.
     */
    public static final UDTField<MyrowtypeRecord, String> _25 = createField("25", org.jooq.impl.SQLDataType.CLOB, MYROWTYPE, "");

    /**
     * The attribute <code>public.myrowtype.26</code>.
     */
    public static final UDTField<MyrowtypeRecord, String> _26 = createField("26", org.jooq.impl.SQLDataType.CLOB, MYROWTYPE, "");

    /**
     * The attribute <code>public.myrowtype.27</code>.
     */
    public static final UDTField<MyrowtypeRecord, String> _27 = createField("27", org.jooq.impl.SQLDataType.CLOB, MYROWTYPE, "");

    /**
     * No further instances allowed
     */
    private Myrowtype() {
        super("myrowtype", null, null, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }
}
