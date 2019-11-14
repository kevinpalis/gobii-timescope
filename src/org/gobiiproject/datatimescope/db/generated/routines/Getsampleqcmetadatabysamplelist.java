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
public class Getsampleqcmetadatabysamplelist extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = 169910215;

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.samplelist</code>.
     */
    public static final Parameter<String> SAMPLELIST = createParameter("samplelist", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.datasettypeid</code>.
     */
    public static final Parameter<Integer> DATASETTYPEID = createParameter("datasettypeid", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnarun_name</code>.
     */
    public static final Parameter<String> DNARUN_NAME = createParameter("dnarun_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_name</code>.
     */
    public static final Parameter<String> GERMPLASM_NAME = createParameter("germplasm_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_pedigree</code>.
     */
    public static final Parameter<String> GERMPLASM_PEDIGREE = createParameter("germplasm_pedigree", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_type</code>.
     */
    public static final Parameter<String> GERMPLASM_TYPE = createParameter("germplasm_type", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnarun_barcode</code>.
     */
    public static final Parameter<String> DNARUN_BARCODE = createParameter("dnarun_barcode", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.project_name</code>.
     */
    public static final Parameter<String> PROJECT_NAME = createParameter("project_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.project_pi_contact</code>.
     */
    public static final Parameter<String> PROJECT_PI_CONTACT = createParameter("project_pi_contact", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.project_genotyping_purpose</code>.
     */
    public static final Parameter<String> PROJECT_GENOTYPING_PURPOSE = createParameter("project_genotyping_purpose", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.project_date_sampled</code>.
     */
    public static final Parameter<String> PROJECT_DATE_SAMPLED = createParameter("project_date_sampled", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.project_division</code>.
     */
    public static final Parameter<String> PROJECT_DIVISION = createParameter("project_division", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.project_study_name</code>.
     */
    public static final Parameter<String> PROJECT_STUDY_NAME = createParameter("project_study_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.experiment_name</code>.
     */
    public static final Parameter<String> EXPERIMENT_NAME = createParameter("experiment_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.vendor_protocol_name</code>.
     */
    public static final Parameter<String> VENDOR_PROTOCOL_NAME = createParameter("vendor_protocol_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.vendor_name</code>.
     */
    public static final Parameter<String> VENDOR_NAME = createParameter("vendor_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.protocol_name</code>.
     */
    public static final Parameter<String> PROTOCOL_NAME = createParameter("protocol_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dataset_name</code>.
     */
    public static final Parameter<String> DATASET_NAME = createParameter("dataset_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_external_code</code>.
     */
    public static final Parameter<String> GERMPLASM_EXTERNAL_CODE = createParameter("germplasm_external_code", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_species</code>.
     */
    public static final Parameter<String> GERMPLASM_SPECIES = createParameter("germplasm_species", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_id</code>.
     */
    public static final Parameter<String> GERMPLASM_ID = createParameter("germplasm_id", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_seed_source_id</code>.
     */
    public static final Parameter<String> GERMPLASM_SEED_SOURCE_ID = createParameter("germplasm_seed_source_id", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_subsp</code>.
     */
    public static final Parameter<String> GERMPLASM_SUBSP = createParameter("germplasm_subsp", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_heterotic_group</code>.
     */
    public static final Parameter<String> GERMPLASM_HETEROTIC_GROUP = createParameter("germplasm_heterotic_group", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_par1</code>.
     */
    public static final Parameter<String> GERMPLASM_PAR1 = createParameter("germplasm_par1", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_par1_type</code>.
     */
    public static final Parameter<String> GERMPLASM_PAR1_TYPE = createParameter("germplasm_par1_type", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_par2</code>.
     */
    public static final Parameter<String> GERMPLASM_PAR2 = createParameter("germplasm_par2", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_par2_type</code>.
     */
    public static final Parameter<String> GERMPLASM_PAR2_TYPE = createParameter("germplasm_par2_type", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_par3</code>.
     */
    public static final Parameter<String> GERMPLASM_PAR3 = createParameter("germplasm_par3", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_par3_type</code>.
     */
    public static final Parameter<String> GERMPLASM_PAR3_TYPE = createParameter("germplasm_par3_type", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_par4</code>.
     */
    public static final Parameter<String> GERMPLASM_PAR4 = createParameter("germplasm_par4", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.germplasm_par4_type</code>.
     */
    public static final Parameter<String> GERMPLASM_PAR4_TYPE = createParameter("germplasm_par4_type", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_name</code>.
     */
    public static final Parameter<String> DNASAMPLE_NAME = createParameter("dnasample_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_platename</code>.
     */
    public static final Parameter<String> DNASAMPLE_PLATENAME = createParameter("dnasample_platename", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_num</code>.
     */
    public static final Parameter<String> DNASAMPLE_NUM = createParameter("dnasample_num", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_well_row</code>.
     */
    public static final Parameter<String> DNASAMPLE_WELL_ROW = createParameter("dnasample_well_row", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_well_col</code>.
     */
    public static final Parameter<String> DNASAMPLE_WELL_COL = createParameter("dnasample_well_col", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_trial_name</code>.
     */
    public static final Parameter<String> DNASAMPLE_TRIAL_NAME = createParameter("dnasample_trial_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_sample_group</code>.
     */
    public static final Parameter<String> DNASAMPLE_SAMPLE_GROUP = createParameter("dnasample_sample_group", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_sample_group_cycle</code>.
     */
    public static final Parameter<String> DNASAMPLE_SAMPLE_GROUP_CYCLE = createParameter("dnasample_sample_group_cycle", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_sample_type</code>.
     */
    public static final Parameter<String> DNASAMPLE_SAMPLE_TYPE = createParameter("dnasample_sample_type", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_sample_parent</code>.
     */
    public static final Parameter<String> DNASAMPLE_SAMPLE_PARENT = createParameter("dnasample_sample_parent", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_ref_sample</code>.
     */
    public static final Parameter<String> DNASAMPLE_REF_SAMPLE = createParameter("dnasample_ref_sample", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.getsampleqcmetadatabysamplelist.dnasample_uuid</code>.
     */
    public static final Parameter<String> DNASAMPLE_UUID = createParameter("dnasample_uuid", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public Getsampleqcmetadatabysamplelist() {
        super("getsampleqcmetadatabysamplelist", Public.PUBLIC);

        addInParameter(SAMPLELIST);
        addInParameter(DATASETTYPEID);
        addOutParameter(DNARUN_NAME);
        addOutParameter(GERMPLASM_NAME);
        addOutParameter(GERMPLASM_PEDIGREE);
        addOutParameter(GERMPLASM_TYPE);
        addOutParameter(DNARUN_BARCODE);
        addOutParameter(PROJECT_NAME);
        addOutParameter(PROJECT_PI_CONTACT);
        addOutParameter(PROJECT_GENOTYPING_PURPOSE);
        addOutParameter(PROJECT_DATE_SAMPLED);
        addOutParameter(PROJECT_DIVISION);
        addOutParameter(PROJECT_STUDY_NAME);
        addOutParameter(EXPERIMENT_NAME);
        addOutParameter(VENDOR_PROTOCOL_NAME);
        addOutParameter(VENDOR_NAME);
        addOutParameter(PROTOCOL_NAME);
        addOutParameter(DATASET_NAME);
        addOutParameter(GERMPLASM_EXTERNAL_CODE);
        addOutParameter(GERMPLASM_SPECIES);
        addOutParameter(GERMPLASM_ID);
        addOutParameter(GERMPLASM_SEED_SOURCE_ID);
        addOutParameter(GERMPLASM_SUBSP);
        addOutParameter(GERMPLASM_HETEROTIC_GROUP);
        addOutParameter(GERMPLASM_PAR1);
        addOutParameter(GERMPLASM_PAR1_TYPE);
        addOutParameter(GERMPLASM_PAR2);
        addOutParameter(GERMPLASM_PAR2_TYPE);
        addOutParameter(GERMPLASM_PAR3);
        addOutParameter(GERMPLASM_PAR3_TYPE);
        addOutParameter(GERMPLASM_PAR4);
        addOutParameter(GERMPLASM_PAR4_TYPE);
        addOutParameter(DNASAMPLE_NAME);
        addOutParameter(DNASAMPLE_PLATENAME);
        addOutParameter(DNASAMPLE_NUM);
        addOutParameter(DNASAMPLE_WELL_ROW);
        addOutParameter(DNASAMPLE_WELL_COL);
        addOutParameter(DNASAMPLE_TRIAL_NAME);
        addOutParameter(DNASAMPLE_SAMPLE_GROUP);
        addOutParameter(DNASAMPLE_SAMPLE_GROUP_CYCLE);
        addOutParameter(DNASAMPLE_SAMPLE_TYPE);
        addOutParameter(DNASAMPLE_SAMPLE_PARENT);
        addOutParameter(DNASAMPLE_REF_SAMPLE);
        addOutParameter(DNASAMPLE_UUID);
    }

    /**
     * Set the <code>samplelist</code> parameter IN value to the routine
     */
    public void setSamplelist(String value) {
        setValue(SAMPLELIST, value);
    }

    /**
     * Set the <code>datasettypeid</code> parameter IN value to the routine
     */
    public void setDatasettypeid(Integer value) {
        setValue(DATASETTYPEID, value);
    }

    /**
     * Get the <code>dnarun_name</code> parameter OUT value from the routine
     */
    public String getDnarunName() {
        return get(DNARUN_NAME);
    }

    /**
     * Get the <code>germplasm_name</code> parameter OUT value from the routine
     */
    public String getGermplasmName() {
        return get(GERMPLASM_NAME);
    }

    /**
     * Get the <code>germplasm_pedigree</code> parameter OUT value from the routine
     */
    public String getGermplasmPedigree() {
        return get(GERMPLASM_PEDIGREE);
    }

    /**
     * Get the <code>germplasm_type</code> parameter OUT value from the routine
     */
    public String getGermplasmType() {
        return get(GERMPLASM_TYPE);
    }

    /**
     * Get the <code>dnarun_barcode</code> parameter OUT value from the routine
     */
    public String getDnarunBarcode() {
        return get(DNARUN_BARCODE);
    }

    /**
     * Get the <code>project_name</code> parameter OUT value from the routine
     */
    public String getProjectName() {
        return get(PROJECT_NAME);
    }

    /**
     * Get the <code>project_pi_contact</code> parameter OUT value from the routine
     */
    public String getProjectPiContact() {
        return get(PROJECT_PI_CONTACT);
    }

    /**
     * Get the <code>project_genotyping_purpose</code> parameter OUT value from the routine
     */
    public String getProjectGenotypingPurpose() {
        return get(PROJECT_GENOTYPING_PURPOSE);
    }

    /**
     * Get the <code>project_date_sampled</code> parameter OUT value from the routine
     */
    public String getProjectDateSampled() {
        return get(PROJECT_DATE_SAMPLED);
    }

    /**
     * Get the <code>project_division</code> parameter OUT value from the routine
     */
    public String getProjectDivision() {
        return get(PROJECT_DIVISION);
    }

    /**
     * Get the <code>project_study_name</code> parameter OUT value from the routine
     */
    public String getProjectStudyName() {
        return get(PROJECT_STUDY_NAME);
    }

    /**
     * Get the <code>experiment_name</code> parameter OUT value from the routine
     */
    public String getExperimentName() {
        return get(EXPERIMENT_NAME);
    }

    /**
     * Get the <code>vendor_protocol_name</code> parameter OUT value from the routine
     */
    public String getVendorProtocolName() {
        return get(VENDOR_PROTOCOL_NAME);
    }

    /**
     * Get the <code>vendor_name</code> parameter OUT value from the routine
     */
    public String getVendorName() {
        return get(VENDOR_NAME);
    }

    /**
     * Get the <code>protocol_name</code> parameter OUT value from the routine
     */
    public String getProtocolName() {
        return get(PROTOCOL_NAME);
    }

    /**
     * Get the <code>dataset_name</code> parameter OUT value from the routine
     */
    public String getDatasetName() {
        return get(DATASET_NAME);
    }

    /**
     * Get the <code>germplasm_external_code</code> parameter OUT value from the routine
     */
    public String getGermplasmExternalCode() {
        return get(GERMPLASM_EXTERNAL_CODE);
    }

    /**
     * Get the <code>germplasm_species</code> parameter OUT value from the routine
     */
    public String getGermplasmSpecies() {
        return get(GERMPLASM_SPECIES);
    }

    /**
     * Get the <code>germplasm_id</code> parameter OUT value from the routine
     */
    public String getGermplasmId() {
        return get(GERMPLASM_ID);
    }

    /**
     * Get the <code>germplasm_seed_source_id</code> parameter OUT value from the routine
     */
    public String getGermplasmSeedSourceId() {
        return get(GERMPLASM_SEED_SOURCE_ID);
    }

    /**
     * Get the <code>germplasm_subsp</code> parameter OUT value from the routine
     */
    public String getGermplasmSubsp() {
        return get(GERMPLASM_SUBSP);
    }

    /**
     * Get the <code>germplasm_heterotic_group</code> parameter OUT value from the routine
     */
    public String getGermplasmHeteroticGroup() {
        return get(GERMPLASM_HETEROTIC_GROUP);
    }

    /**
     * Get the <code>germplasm_par1</code> parameter OUT value from the routine
     */
    public String getGermplasmPar1() {
        return get(GERMPLASM_PAR1);
    }

    /**
     * Get the <code>germplasm_par1_type</code> parameter OUT value from the routine
     */
    public String getGermplasmPar1Type() {
        return get(GERMPLASM_PAR1_TYPE);
    }

    /**
     * Get the <code>germplasm_par2</code> parameter OUT value from the routine
     */
    public String getGermplasmPar2() {
        return get(GERMPLASM_PAR2);
    }

    /**
     * Get the <code>germplasm_par2_type</code> parameter OUT value from the routine
     */
    public String getGermplasmPar2Type() {
        return get(GERMPLASM_PAR2_TYPE);
    }

    /**
     * Get the <code>germplasm_par3</code> parameter OUT value from the routine
     */
    public String getGermplasmPar3() {
        return get(GERMPLASM_PAR3);
    }

    /**
     * Get the <code>germplasm_par3_type</code> parameter OUT value from the routine
     */
    public String getGermplasmPar3Type() {
        return get(GERMPLASM_PAR3_TYPE);
    }

    /**
     * Get the <code>germplasm_par4</code> parameter OUT value from the routine
     */
    public String getGermplasmPar4() {
        return get(GERMPLASM_PAR4);
    }

    /**
     * Get the <code>germplasm_par4_type</code> parameter OUT value from the routine
     */
    public String getGermplasmPar4Type() {
        return get(GERMPLASM_PAR4_TYPE);
    }

    /**
     * Get the <code>dnasample_name</code> parameter OUT value from the routine
     */
    public String getDnasampleName() {
        return get(DNASAMPLE_NAME);
    }

    /**
     * Get the <code>dnasample_platename</code> parameter OUT value from the routine
     */
    public String getDnasamplePlatename() {
        return get(DNASAMPLE_PLATENAME);
    }

    /**
     * Get the <code>dnasample_num</code> parameter OUT value from the routine
     */
    public String getDnasampleNum() {
        return get(DNASAMPLE_NUM);
    }

    /**
     * Get the <code>dnasample_well_row</code> parameter OUT value from the routine
     */
    public String getDnasampleWellRow() {
        return get(DNASAMPLE_WELL_ROW);
    }

    /**
     * Get the <code>dnasample_well_col</code> parameter OUT value from the routine
     */
    public String getDnasampleWellCol() {
        return get(DNASAMPLE_WELL_COL);
    }

    /**
     * Get the <code>dnasample_trial_name</code> parameter OUT value from the routine
     */
    public String getDnasampleTrialName() {
        return get(DNASAMPLE_TRIAL_NAME);
    }

    /**
     * Get the <code>dnasample_sample_group</code> parameter OUT value from the routine
     */
    public String getDnasampleSampleGroup() {
        return get(DNASAMPLE_SAMPLE_GROUP);
    }

    /**
     * Get the <code>dnasample_sample_group_cycle</code> parameter OUT value from the routine
     */
    public String getDnasampleSampleGroupCycle() {
        return get(DNASAMPLE_SAMPLE_GROUP_CYCLE);
    }

    /**
     * Get the <code>dnasample_sample_type</code> parameter OUT value from the routine
     */
    public String getDnasampleSampleType() {
        return get(DNASAMPLE_SAMPLE_TYPE);
    }

    /**
     * Get the <code>dnasample_sample_parent</code> parameter OUT value from the routine
     */
    public String getDnasampleSampleParent() {
        return get(DNASAMPLE_SAMPLE_PARENT);
    }

    /**
     * Get the <code>dnasample_ref_sample</code> parameter OUT value from the routine
     */
    public String getDnasampleRefSample() {
        return get(DNASAMPLE_REF_SAMPLE);
    }

    /**
     * Get the <code>dnasample_uuid</code> parameter OUT value from the routine
     */
    public String getDnasampleUuid() {
        return get(DNASAMPLE_UUID);
    }
}
