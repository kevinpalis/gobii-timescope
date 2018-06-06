/*
 * This file is generated by jOOQ.
*/
package org.gobiiproject.datatimescope.db.generated;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.gobiiproject.datatimescope.db.generated.tables.Analysis;
import org.gobiiproject.datatimescope.db.generated.tables.Contact;
import org.gobiiproject.datatimescope.db.generated.tables.Cv;
import org.gobiiproject.datatimescope.db.generated.tables.Cvgroup;
import org.gobiiproject.datatimescope.db.generated.tables.Databasechangelog;
import org.gobiiproject.datatimescope.db.generated.tables.Databasechangeloglock;
import org.gobiiproject.datatimescope.db.generated.tables.Dataset;
import org.gobiiproject.datatimescope.db.generated.tables.Dbxref;
import org.gobiiproject.datatimescope.db.generated.tables.Display;
import org.gobiiproject.datatimescope.db.generated.tables.Dnarun;
import org.gobiiproject.datatimescope.db.generated.tables.Dnasample;
import org.gobiiproject.datatimescope.db.generated.tables.Experiment;
import org.gobiiproject.datatimescope.db.generated.tables.FtLinkageGroup_2bsid4wz;
import org.gobiiproject.datatimescope.db.generated.tables.Germplasm;
import org.gobiiproject.datatimescope.db.generated.tables.Gobiiprop;
import org.gobiiproject.datatimescope.db.generated.tables.Job;
import org.gobiiproject.datatimescope.db.generated.tables.LinkageGroup;
import org.gobiiproject.datatimescope.db.generated.tables.Manifest;
import org.gobiiproject.datatimescope.db.generated.tables.Mapset;
import org.gobiiproject.datatimescope.db.generated.tables.Marker;
import org.gobiiproject.datatimescope.db.generated.tables.MarkerGroup;
import org.gobiiproject.datatimescope.db.generated.tables.MarkerLinkageGroup;
import org.gobiiproject.datatimescope.db.generated.tables.Organization;
import org.gobiiproject.datatimescope.db.generated.tables.Platform;
import org.gobiiproject.datatimescope.db.generated.tables.Project;
import org.gobiiproject.datatimescope.db.generated.tables.Protocol;
import org.gobiiproject.datatimescope.db.generated.tables.Reference;
import org.gobiiproject.datatimescope.db.generated.tables.Role;
import org.gobiiproject.datatimescope.db.generated.tables.Timescoper;
import org.gobiiproject.datatimescope.db.generated.tables.VDatasetSummary;
import org.gobiiproject.datatimescope.db.generated.tables.VJobsSummary;
import org.gobiiproject.datatimescope.db.generated.tables.VMarkerGroupSummary;
import org.gobiiproject.datatimescope.db.generated.tables.VMarkerLinkageGenetic;
import org.gobiiproject.datatimescope.db.generated.tables.VMarkerLinkagePhysical;
import org.gobiiproject.datatimescope.db.generated.tables.VMarkerSummary;
import org.gobiiproject.datatimescope.db.generated.tables.Variant;
import org.gobiiproject.datatimescope.db.generated.tables.VendorProtocol;
import org.gobiiproject.datatimescope.db.generated.udt.DistinctSourceKeys;
import org.gobiiproject.datatimescope.db.generated.udt.KeyvaluepairType;
import org.gobiiproject.datatimescope.db.generated.udt.Myrowtype;
import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.UDT;
import org.jooq.impl.SchemaImpl;


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
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 967802927;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.analysis</code>.
     */
    public final Analysis ANALYSIS = org.gobiiproject.datatimescope.db.generated.tables.Analysis.ANALYSIS;

    /**
     * The table <code>public.contact</code>.
     */
    public final Contact CONTACT = org.gobiiproject.datatimescope.db.generated.tables.Contact.CONTACT;

    /**
     * A term, class, universal or type within an
ontology or controlled vocabulary.  This table is also used for
relations and properties. cvterms constitute nodes in the graph
defined by the collection of cvterms and cvterm_relationships.
     */
    public final Cv CV = org.gobiiproject.datatimescope.db.generated.tables.Cv.CV;

    /**
     * A controlled vocabulary or ontology. A cv is
composed of cvterms (AKA terms, classes, types, universals - relations
and properties are also stored in cvterm) and the relationships
between them.
     */
    public final Cvgroup CVGROUP = org.gobiiproject.datatimescope.db.generated.tables.Cvgroup.CVGROUP;

    /**
     * The table <code>public.databasechangelog</code>.
     */
    public final Databasechangelog DATABASECHANGELOG = org.gobiiproject.datatimescope.db.generated.tables.Databasechangelog.DATABASECHANGELOG;

    /**
     * The table <code>public.databasechangeloglock</code>.
     */
    public final Databasechangeloglock DATABASECHANGELOGLOCK = org.gobiiproject.datatimescope.db.generated.tables.Databasechangeloglock.DATABASECHANGELOGLOCK;

    /**
     * The table <code>public.dataset</code>.
     */
    public final Dataset DATASET = org.gobiiproject.datatimescope.db.generated.tables.Dataset.DATASET;

    /**
     * A unique, global, public, stable identifier. Not necessarily an external reference - can reference data items inside the particular instance being used. Typically a row in a table can be uniquely identified with a primary identifier (called dbxref_id); a table may also have secondary identifiers (in a linking table &lt;T&gt;_dbxref). A dbxref is generally written as &lt;DB&gt;:&lt;ACCESSION&gt; or as &lt;DB&gt;:&lt;ACCESSION&gt;:&lt;VERSION&gt;.
     */
    public final Dbxref DBXREF = org.gobiiproject.datatimescope.db.generated.tables.Dbxref.DBXREF;

    /**
     * The table <code>public.display</code>.
     */
    public final Display DISPLAY = org.gobiiproject.datatimescope.db.generated.tables.Display.DISPLAY;

    /**
     * The table <code>public.dnarun</code>.
     */
    public final Dnarun DNARUN = org.gobiiproject.datatimescope.db.generated.tables.Dnarun.DNARUN;

    /**
     * The table <code>public.dnasample</code>.
     */
    public final Dnasample DNASAMPLE = org.gobiiproject.datatimescope.db.generated.tables.Dnasample.DNASAMPLE;

    /**
     * The table <code>public.experiment</code>.
     */
    public final Experiment EXPERIMENT = org.gobiiproject.datatimescope.db.generated.tables.Experiment.EXPERIMENT;

    /**
     * The table <code>public.ft_linkage_group_2bsid4wz</code>.
     */
    public final FtLinkageGroup_2bsid4wz FT_LINKAGE_GROUP_2BSID4WZ = org.gobiiproject.datatimescope.db.generated.tables.FtLinkageGroup_2bsid4wz.FT_LINKAGE_GROUP_2BSID4WZ;

    /**
     * The table <code>public.germplasm</code>.
     */
    public final Germplasm GERMPLASM = org.gobiiproject.datatimescope.db.generated.tables.Germplasm.GERMPLASM;

    /**
     * This table is different from other prop tables/columns in the database, as it is for storing information about the database itself, like schema version
     */
    public final Gobiiprop GOBIIPROP = org.gobiiproject.datatimescope.db.generated.tables.Gobiiprop.GOBIIPROP;

    /**
     * This table keeps track of all the data loading and extraction jobs.
     */
    public final Job JOB = org.gobiiproject.datatimescope.db.generated.tables.Job.JOB;

    /**
     * This table will contain different linkage groups, ie. Chromosome 1, Chromosome 2, etc. along with their respective start and stop boundaries.
     */
    public final LinkageGroup LINKAGE_GROUP = org.gobiiproject.datatimescope.db.generated.tables.LinkageGroup.LINKAGE_GROUP;

    /**
     * The table <code>public.manifest</code>.
     */
    public final Manifest MANIFEST = org.gobiiproject.datatimescope.db.generated.tables.Manifest.MANIFEST;

    /**
     * The table <code>public.mapset</code>.
     */
    public final Mapset MAPSET = org.gobiiproject.datatimescope.db.generated.tables.Mapset.MAPSET;

    /**
     * The table <code>public.marker</code>.
     */
    public final Marker MARKER = org.gobiiproject.datatimescope.db.generated.tables.Marker.MARKER;

    /**
     * The table <code>public.marker_group</code>.
     */
    public final MarkerGroup MARKER_GROUP = org.gobiiproject.datatimescope.db.generated.tables.MarkerGroup.MARKER_GROUP;

    /**
     * The table <code>public.marker_linkage_group</code>.
     */
    public final MarkerLinkageGroup MARKER_LINKAGE_GROUP = org.gobiiproject.datatimescope.db.generated.tables.MarkerLinkageGroup.MARKER_LINKAGE_GROUP;

    /**
     * The table <code>public.organization</code>.
     */
    public final Organization ORGANIZATION = org.gobiiproject.datatimescope.db.generated.tables.Organization.ORGANIZATION;

    /**
     * The table <code>public.platform</code>.
     */
    public final Platform PLATFORM = org.gobiiproject.datatimescope.db.generated.tables.Platform.PLATFORM;

    /**
     * The table <code>public.project</code>.
     */
    public final Project PROJECT = org.gobiiproject.datatimescope.db.generated.tables.Project.PROJECT;

    /**
     * A Platform can have multiple protocols and more than one protocol can be run by more than one vendor. 
     */
    public final Protocol PROTOCOL = org.gobiiproject.datatimescope.db.generated.tables.Protocol.PROTOCOL;

    /**
     * The table <code>public.reference</code>.
     */
    public final Reference REFERENCE = org.gobiiproject.datatimescope.db.generated.tables.Reference.REFERENCE;

    /**
     * The table <code>public.role</code>.
     */
    public final Role ROLE = org.gobiiproject.datatimescope.db.generated.tables.Role.ROLE;

    /**
     * The table <code>public.timescoper</code>.
     */
    public final Timescoper TIMESCOPER = org.gobiiproject.datatimescope.db.generated.tables.Timescoper.TIMESCOPER;

    /**
     * The table <code>public.v_dataset_summary</code>.
     */
    public final VDatasetSummary V_DATASET_SUMMARY = org.gobiiproject.datatimescope.db.generated.tables.VDatasetSummary.V_DATASET_SUMMARY;

    /**
     * The table <code>public.v_jobs_summary</code>.
     */
    public final VJobsSummary V_JOBS_SUMMARY = org.gobiiproject.datatimescope.db.generated.tables.VJobsSummary.V_JOBS_SUMMARY;

    /**
     * The table <code>public.v_marker_group_summary</code>.
     */
    public final VMarkerGroupSummary V_MARKER_GROUP_SUMMARY = org.gobiiproject.datatimescope.db.generated.tables.VMarkerGroupSummary.V_MARKER_GROUP_SUMMARY;

    /**
     * The table <code>public.v_marker_linkage_genetic</code>.
     */
    public final VMarkerLinkageGenetic V_MARKER_LINKAGE_GENETIC = org.gobiiproject.datatimescope.db.generated.tables.VMarkerLinkageGenetic.V_MARKER_LINKAGE_GENETIC;

    /**
     * The table <code>public.v_marker_linkage_physical</code>.
     */
    public final VMarkerLinkagePhysical V_MARKER_LINKAGE_PHYSICAL = org.gobiiproject.datatimescope.db.generated.tables.VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL;

    /**
     * The table <code>public.v_marker_summary</code>.
     */
    public final VMarkerSummary V_MARKER_SUMMARY = org.gobiiproject.datatimescope.db.generated.tables.VMarkerSummary.V_MARKER_SUMMARY;

    /**
     * The table <code>public.variant</code>.
     */
    public final Variant VARIANT = org.gobiiproject.datatimescope.db.generated.tables.Variant.VARIANT;

    /**
     * Vendors reside in the Organization table. A vendor can provide multiple protocols, and a particular protocol can be offered by multiple vendors, hence the N:M relationship table.
     */
    public final VendorProtocol VENDOR_PROTOCOL = org.gobiiproject.datatimescope.db.generated.tables.VendorProtocol.VENDOR_PROTOCOL;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.ANALYSIS_ANALYSIS_ID_SEQ,
            Sequences.CONTACT_CONTACT_ID_SEQ,
            Sequences.CV_CV_ID_SEQ,
            Sequences.CVGROUP_CVGROUP_ID_SEQ,
            Sequences.DATASET_DATASET_ID_SEQ,
            Sequences.DBXREF_DBXREF_ID_SEQ,
            Sequences.DISPLAY_DISPLAY_ID_SEQ,
            Sequences.DNARUN_DNARUN_ID_SEQ,
            Sequences.DNASAMPLE_DNASAMPLE_ID_SEQ,
            Sequences.EXPERIMENT_EXPERIMENT_ID_SEQ,
            Sequences.GERMPLASM_GERMPLASM_ID_SEQ,
            Sequences.GOBIIPROP_GOBIIPROP_ID_SEQ,
            Sequences.JOB_JOB_ID_SEQ,
            Sequences.LINKAGE_GROUP_LINKAGE_GROUP_ID_SEQ,
            Sequences.MANIFEST_MANIFEST_ID_SEQ,
            Sequences.MAP_MAP_ID_SEQ,
            Sequences.MARKER_GROUP_MARKER_GROUP_ID_SEQ,
            Sequences.MARKER_MAP_MARKER_MAP_ID_SEQ,
            Sequences.MARKER_MARKER_ID_SEQ,
            Sequences.ORGANIZATION_ORGANIZATION_ID_SEQ,
            Sequences.PLATFORM_PLATFORM_ID_SEQ,
            Sequences.PROJECT_PROJECT_ID_SEQ,
            Sequences.PROTOCOL_PROTOCOL_ID_SEQ,
            Sequences.REFERENCE_REFERENCE_ID_SEQ,
            Sequences.ROLE_ROLE_ID_SEQ,
            Sequences.TIMESCOPER_TIMESCOPER_ID_SEQ,
            Sequences.VARIANT_VARIANT_ID_SEQ,
            Sequences.VENDOR_PROTOCOL_VENDOR_PROTOCOL_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Analysis.ANALYSIS,
            Contact.CONTACT,
            Cv.CV,
            Cvgroup.CVGROUP,
            Databasechangelog.DATABASECHANGELOG,
            Databasechangeloglock.DATABASECHANGELOGLOCK,
            Dataset.DATASET,
            Dbxref.DBXREF,
            Display.DISPLAY,
            Dnarun.DNARUN,
            Dnasample.DNASAMPLE,
            Experiment.EXPERIMENT,
            FtLinkageGroup_2bsid4wz.FT_LINKAGE_GROUP_2BSID4WZ,
            Germplasm.GERMPLASM,
            Gobiiprop.GOBIIPROP,
            Job.JOB,
            LinkageGroup.LINKAGE_GROUP,
            Manifest.MANIFEST,
            Mapset.MAPSET,
            Marker.MARKER,
            MarkerGroup.MARKER_GROUP,
            MarkerLinkageGroup.MARKER_LINKAGE_GROUP,
            Organization.ORGANIZATION,
            Platform.PLATFORM,
            Project.PROJECT,
            Protocol.PROTOCOL,
            Reference.REFERENCE,
            Role.ROLE,
            Timescoper.TIMESCOPER,
            VDatasetSummary.V_DATASET_SUMMARY,
            VJobsSummary.V_JOBS_SUMMARY,
            VMarkerGroupSummary.V_MARKER_GROUP_SUMMARY,
            VMarkerLinkageGenetic.V_MARKER_LINKAGE_GENETIC,
            VMarkerLinkagePhysical.V_MARKER_LINKAGE_PHYSICAL,
            VMarkerSummary.V_MARKER_SUMMARY,
            Variant.VARIANT,
            VendorProtocol.VENDOR_PROTOCOL);
    }

    @Override
    public final List<UDT<?>> getUDTs() {
        List result = new ArrayList();
        result.addAll(getUDTs0());
        return result;
    }

    private final List<UDT<?>> getUDTs0() {
        return Arrays.<UDT<?>>asList(
            DistinctSourceKeys.DISTINCT_SOURCE_KEYS,
            KeyvaluepairType.KEYVALUEPAIR_TYPE,
            Myrowtype.MYROWTYPE);
    }
}
