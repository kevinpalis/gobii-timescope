/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated;


import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.analysis_analysis_id_seq</code>
     */
    public static final Sequence<Long> ANALYSIS_ANALYSIS_ID_SEQ = new SequenceImpl<Long>("analysis_analysis_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.contact_contact_id_seq</code>
     */
    public static final Sequence<Long> CONTACT_CONTACT_ID_SEQ = new SequenceImpl<Long>("contact_contact_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.cv_cv_id_seq</code>
     */
    public static final Sequence<Long> CV_CV_ID_SEQ = new SequenceImpl<Long>("cv_cv_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.cvgroup_cvgroup_id_seq</code>
     */
    public static final Sequence<Long> CVGROUP_CVGROUP_ID_SEQ = new SequenceImpl<Long>("cvgroup_cvgroup_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.dataset_dataset_id_seq</code>
     */
    public static final Sequence<Long> DATASET_DATASET_ID_SEQ = new SequenceImpl<Long>("dataset_dataset_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.dbxref_dbxref_id_seq</code>
     */
    public static final Sequence<Long> DBXREF_DBXREF_ID_SEQ = new SequenceImpl<Long>("dbxref_dbxref_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.display_display_id_seq</code>
     */
    public static final Sequence<Long> DISPLAY_DISPLAY_ID_SEQ = new SequenceImpl<Long>("display_display_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.dnarun_dnarun_id_seq</code>
     */
    public static final Sequence<Long> DNARUN_DNARUN_ID_SEQ = new SequenceImpl<Long>("dnarun_dnarun_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.dnasample_dnasample_id_seq</code>
     */
    public static final Sequence<Long> DNASAMPLE_DNASAMPLE_ID_SEQ = new SequenceImpl<Long>("dnasample_dnasample_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.edge_edge_id_seq</code>
     */
    public static final Sequence<Long> EDGE_EDGE_ID_SEQ = new SequenceImpl<Long>("edge_edge_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.experiment_experiment_id_seq</code>
     */
    public static final Sequence<Long> EXPERIMENT_EXPERIMENT_ID_SEQ = new SequenceImpl<Long>("experiment_experiment_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.germplasm_germplasm_id_seq</code>
     */
    public static final Sequence<Long> GERMPLASM_GERMPLASM_ID_SEQ = new SequenceImpl<Long>("germplasm_germplasm_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.gobiiprop_gobiiprop_id_seq</code>
     */
    public static final Sequence<Long> GOBIIPROP_GOBIIPROP_ID_SEQ = new SequenceImpl<Long>("gobiiprop_gobiiprop_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.job_job_id_seq</code>
     */
    public static final Sequence<Long> JOB_JOB_ID_SEQ = new SequenceImpl<Long>("job_job_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.linkage_group_linkage_group_id_seq</code>
     */
    public static final Sequence<Long> LINKAGE_GROUP_LINKAGE_GROUP_ID_SEQ = new SequenceImpl<Long>("linkage_group_linkage_group_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.manifest_manifest_id_seq</code>
     */
    public static final Sequence<Long> MANIFEST_MANIFEST_ID_SEQ = new SequenceImpl<Long>("manifest_manifest_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.map_map_id_seq</code>
     */
    public static final Sequence<Long> MAP_MAP_ID_SEQ = new SequenceImpl<Long>("map_map_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.marker_group_marker_group_id_seq</code>
     */
    public static final Sequence<Long> MARKER_GROUP_MARKER_GROUP_ID_SEQ = new SequenceImpl<Long>("marker_group_marker_group_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.marker_map_marker_map_id_seq</code>
     */
    public static final Sequence<Long> MARKER_MAP_MARKER_MAP_ID_SEQ = new SequenceImpl<Long>("marker_map_marker_map_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.marker_marker_id_seq</code>
     */
    public static final Sequence<Long> MARKER_MARKER_ID_SEQ = new SequenceImpl<Long>("marker_marker_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.organization_organization_id_seq</code>
     */
    public static final Sequence<Long> ORGANIZATION_ORGANIZATION_ID_SEQ = new SequenceImpl<Long>("organization_organization_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.platform_platform_id_seq</code>
     */
    public static final Sequence<Long> PLATFORM_PLATFORM_ID_SEQ = new SequenceImpl<Long>("platform_platform_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.project_project_id_seq</code>
     */
    public static final Sequence<Long> PROJECT_PROJECT_ID_SEQ = new SequenceImpl<Long>("project_project_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.protocol_protocol_id_seq</code>
     */
    public static final Sequence<Long> PROTOCOL_PROTOCOL_ID_SEQ = new SequenceImpl<Long>("protocol_protocol_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.reference_reference_id_seq</code>
     */
    public static final Sequence<Long> REFERENCE_REFERENCE_ID_SEQ = new SequenceImpl<Long>("reference_reference_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.role_role_id_seq</code>
     */
    public static final Sequence<Long> ROLE_ROLE_ID_SEQ = new SequenceImpl<Long>("role_role_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.timescoper_timescoper_id_seq</code>
     */
    public static final Sequence<Long> TIMESCOPER_TIMESCOPER_ID_SEQ = new SequenceImpl<Long>("timescoper_timescoper_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.variant_variant_id_seq</code>
     */
    public static final Sequence<Long> VARIANT_VARIANT_ID_SEQ = new SequenceImpl<Long>("variant_variant_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.vendor_protocol_vendor_protocol_id_seq</code>
     */
    public static final Sequence<Long> VENDOR_PROTOCOL_VENDOR_PROTOCOL_ID_SEQ = new SequenceImpl<Long>("vendor_protocol_vendor_protocol_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.vertex_vertex_id_seq</code>
     */
    public static final Sequence<Long> VERTEX_VERTEX_ID_SEQ = new SequenceImpl<Long>("vertex_vertex_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}
