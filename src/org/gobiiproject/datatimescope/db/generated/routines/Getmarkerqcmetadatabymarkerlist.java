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
public class Getmarkerqcmetadatabymarkerlist extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -839808632;

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.markerlist</code>.
     */
    public static final Parameter<String> MARKERLIST = createParameter("markerlist", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_name</code>.
     */
    public static final Parameter<String> MARKER_NAME = createParameter("marker_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.platform_name</code>.
     */
    public static final Parameter<String> PLATFORM_NAME = createParameter("platform_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.variant_id</code>.
     */
    public static final Parameter<Integer> VARIANT_ID = createParameter("variant_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.variant_code</code>.
     */
    public static final Parameter<String> VARIANT_CODE = createParameter("variant_code", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_ref</code>.
     */
    public static final Parameter<String> MARKER_REF = createParameter("marker_ref", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_alts</code>.
     */
    public static final Parameter<String> MARKER_ALTS = createParameter("marker_alts", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_sequence</code>.
     */
    public static final Parameter<String> MARKER_SEQUENCE = createParameter("marker_sequence", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_strand</code>.
     */
    public static final Parameter<String> MARKER_STRAND = createParameter("marker_strand", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_primer_forw1</code>.
     */
    public static final Parameter<String> MARKER_PRIMER_FORW1 = createParameter("marker_primer_forw1", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_primer_forw2</code>.
     */
    public static final Parameter<String> MARKER_PRIMER_FORW2 = createParameter("marker_primer_forw2", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_primer_rev1</code>.
     */
    public static final Parameter<String> MARKER_PRIMER_REV1 = createParameter("marker_primer_rev1", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_primer_rev2</code>.
     */
    public static final Parameter<String> MARKER_PRIMER_REV2 = createParameter("marker_primer_rev2", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_probe1</code>.
     */
    public static final Parameter<String> MARKER_PROBE1 = createParameter("marker_probe1", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_probe2</code>.
     */
    public static final Parameter<String> MARKER_PROBE2 = createParameter("marker_probe2", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_polymorphism_type</code>.
     */
    public static final Parameter<String> MARKER_POLYMORPHISM_TYPE = createParameter("marker_polymorphism_type", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_synonym</code>.
     */
    public static final Parameter<String> MARKER_SYNONYM = createParameter("marker_synonym", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_source</code>.
     */
    public static final Parameter<String> MARKER_SOURCE = createParameter("marker_source", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_gene_id</code>.
     */
    public static final Parameter<String> MARKER_GENE_ID = createParameter("marker_gene_id", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_gene_annotation</code>.
     */
    public static final Parameter<String> MARKER_GENE_ANNOTATION = createParameter("marker_gene_annotation", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_polymorphism_annotation</code>.
     */
    public static final Parameter<String> MARKER_POLYMORPHISM_ANNOTATION = createParameter("marker_polymorphism_annotation", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_marker_dom</code>.
     */
    public static final Parameter<String> MARKER_MARKER_DOM = createParameter("marker_marker_dom", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_clone_id_pos</code>.
     */
    public static final Parameter<String> MARKER_CLONE_ID_POS = createParameter("marker_clone_id_pos", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_genome_build</code>.
     */
    public static final Parameter<String> MARKER_GENOME_BUILD = createParameter("marker_genome_build", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_typeofrefallele_alleleorder</code>.
     */
    public static final Parameter<String> MARKER_TYPEOFREFALLELE_ALLELEORDER = createParameter("marker_typeofrefallele_alleleorder", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getmarkerqcmetadatabymarkerlist.marker_strand_data_read</code>.
     */
    public static final Parameter<String> MARKER_STRAND_DATA_READ = createParameter("marker_strand_data_read", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Getmarkerqcmetadatabymarkerlist() {
        super("getmarkerqcmetadatabymarkerlist", Public.PUBLIC);

        addInParameter(MARKERLIST);
        addOutParameter(MARKER_NAME);
        addOutParameter(PLATFORM_NAME);
        addOutParameter(VARIANT_ID);
        addOutParameter(VARIANT_CODE);
        addOutParameter(MARKER_REF);
        addOutParameter(MARKER_ALTS);
        addOutParameter(MARKER_SEQUENCE);
        addOutParameter(MARKER_STRAND);
        addOutParameter(MARKER_PRIMER_FORW1);
        addOutParameter(MARKER_PRIMER_FORW2);
        addOutParameter(MARKER_PRIMER_REV1);
        addOutParameter(MARKER_PRIMER_REV2);
        addOutParameter(MARKER_PROBE1);
        addOutParameter(MARKER_PROBE2);
        addOutParameter(MARKER_POLYMORPHISM_TYPE);
        addOutParameter(MARKER_SYNONYM);
        addOutParameter(MARKER_SOURCE);
        addOutParameter(MARKER_GENE_ID);
        addOutParameter(MARKER_GENE_ANNOTATION);
        addOutParameter(MARKER_POLYMORPHISM_ANNOTATION);
        addOutParameter(MARKER_MARKER_DOM);
        addOutParameter(MARKER_CLONE_ID_POS);
        addOutParameter(MARKER_GENOME_BUILD);
        addOutParameter(MARKER_TYPEOFREFALLELE_ALLELEORDER);
        addOutParameter(MARKER_STRAND_DATA_READ);
    }

    /**
     * Set the <code>markerlist</code> parameter IN value to the routine
     */
    public void setMarkerlist(String value) {
        setValue(MARKERLIST, value);
    }

    /**
     * Get the <code>marker_name</code> parameter OUT value from the routine
     */
    public String getMarkerName() {
        return get(MARKER_NAME);
    }

    /**
     * Get the <code>platform_name</code> parameter OUT value from the routine
     */
    public String getPlatformName() {
        return get(PLATFORM_NAME);
    }

    /**
     * Get the <code>variant_id</code> parameter OUT value from the routine
     */
    public Integer getVariantId() {
        return get(VARIANT_ID);
    }

    /**
     * Get the <code>variant_code</code> parameter OUT value from the routine
     */
    public String getVariantCode() {
        return get(VARIANT_CODE);
    }

    /**
     * Get the <code>marker_ref</code> parameter OUT value from the routine
     */
    public String getMarkerRef() {
        return get(MARKER_REF);
    }

    /**
     * Get the <code>marker_alts</code> parameter OUT value from the routine
     */
    public String getMarkerAlts() {
        return get(MARKER_ALTS);
    }

    /**
     * Get the <code>marker_sequence</code> parameter OUT value from the routine
     */
    public String getMarkerSequence() {
        return get(MARKER_SEQUENCE);
    }

    /**
     * Get the <code>marker_strand</code> parameter OUT value from the routine
     */
    public String getMarkerStrand() {
        return get(MARKER_STRAND);
    }

    /**
     * Get the <code>marker_primer_forw1</code> parameter OUT value from the routine
     */
    public String getMarkerPrimerForw1() {
        return get(MARKER_PRIMER_FORW1);
    }

    /**
     * Get the <code>marker_primer_forw2</code> parameter OUT value from the routine
     */
    public String getMarkerPrimerForw2() {
        return get(MARKER_PRIMER_FORW2);
    }

    /**
     * Get the <code>marker_primer_rev1</code> parameter OUT value from the routine
     */
    public String getMarkerPrimerRev1() {
        return get(MARKER_PRIMER_REV1);
    }

    /**
     * Get the <code>marker_primer_rev2</code> parameter OUT value from the routine
     */
    public String getMarkerPrimerRev2() {
        return get(MARKER_PRIMER_REV2);
    }

    /**
     * Get the <code>marker_probe1</code> parameter OUT value from the routine
     */
    public String getMarkerProbe1() {
        return get(MARKER_PROBE1);
    }

    /**
     * Get the <code>marker_probe2</code> parameter OUT value from the routine
     */
    public String getMarkerProbe2() {
        return get(MARKER_PROBE2);
    }

    /**
     * Get the <code>marker_polymorphism_type</code> parameter OUT value from the routine
     */
    public String getMarkerPolymorphismType() {
        return get(MARKER_POLYMORPHISM_TYPE);
    }

    /**
     * Get the <code>marker_synonym</code> parameter OUT value from the routine
     */
    public String getMarkerSynonym() {
        return get(MARKER_SYNONYM);
    }

    /**
     * Get the <code>marker_source</code> parameter OUT value from the routine
     */
    public String getMarkerSource() {
        return get(MARKER_SOURCE);
    }

    /**
     * Get the <code>marker_gene_id</code> parameter OUT value from the routine
     */
    public String getMarkerGeneId() {
        return get(MARKER_GENE_ID);
    }

    /**
     * Get the <code>marker_gene_annotation</code> parameter OUT value from the routine
     */
    public String getMarkerGeneAnnotation() {
        return get(MARKER_GENE_ANNOTATION);
    }

    /**
     * Get the <code>marker_polymorphism_annotation</code> parameter OUT value from the routine
     */
    public String getMarkerPolymorphismAnnotation() {
        return get(MARKER_POLYMORPHISM_ANNOTATION);
    }

    /**
     * Get the <code>marker_marker_dom</code> parameter OUT value from the routine
     */
    public String getMarkerMarkerDom() {
        return get(MARKER_MARKER_DOM);
    }

    /**
     * Get the <code>marker_clone_id_pos</code> parameter OUT value from the routine
     */
    public String getMarkerCloneIdPos() {
        return get(MARKER_CLONE_ID_POS);
    }

    /**
     * Get the <code>marker_genome_build</code> parameter OUT value from the routine
     */
    public String getMarkerGenomeBuild() {
        return get(MARKER_GENOME_BUILD);
    }

    /**
     * Get the <code>marker_typeofrefallele_alleleorder</code> parameter OUT value from the routine
     */
    public String getMarkerTypeofrefalleleAlleleorder() {
        return get(MARKER_TYPEOFREFALLELE_ALLELEORDER);
    }

    /**
     * Get the <code>marker_strand_data_read</code> parameter OUT value from the routine
     */
    public String getMarkerStrandDataRead() {
        return get(MARKER_STRAND_DATA_READ);
    }
}
