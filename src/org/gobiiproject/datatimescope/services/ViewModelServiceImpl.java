/* 

 */
package org.gobiiproject.datatimescope.services;


import static org.gobiiproject.datatimescope.db.generated.Tables.ANALYSIS;
import static org.gobiiproject.datatimescope.db.generated.Tables.CV;
import static org.gobiiproject.datatimescope.db.generated.Tables.CVGROUP;
import static org.gobiiproject.datatimescope.db.generated.Tables.EXPERIMENT;
import static org.gobiiproject.datatimescope.db.generated.Tables.TIMESCOPER;
import static org.gobiiproject.datatimescope.db.generated.Tables.CONTACT;
import static org.gobiiproject.datatimescope.db.generated.Tables.DNARUN;
import static org.gobiiproject.datatimescope.db.generated.Tables.DNASAMPLE;
import static org.gobiiproject.datatimescope.db.generated.Tables.GERMPLASM;
import static org.gobiiproject.datatimescope.db.generated.Tables.PLATFORM;
import static org.gobiiproject.datatimescope.db.generated.Tables.PROJECT;
import static org.gobiiproject.datatimescope.db.generated.Tables.REFERENCE;
import static org.gobiiproject.datatimescope.db.generated.Tables.ORGANIZATION;
import static org.gobiiproject.datatimescope.db.generated.Tables.MAPSET;
import static org.gobiiproject.datatimescope.db.generated.Tables.MARKER;
import static org.gobiiproject.datatimescope.db.generated.Tables.MARKER_GROUP;
import static org.gobiiproject.datatimescope.db.generated.Tables.DATASET;
import static org.gobiiproject.datatimescope.db.generated.Tables.VENDOR_PROTOCOL;
import static org.gobiiproject.datatimescope.db.generated.Tables.LINKAGE_GROUP;
import static org.gobiiproject.datatimescope.db.generated.Tables.MARKER_LINKAGE_GROUP;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.db.generated.routines.Createtimescoper;
import org.gobiiproject.datatimescope.db.generated.routines.Crypt;
import org.gobiiproject.datatimescope.db.generated.routines.Deletedatasetdnarunindices;
import org.gobiiproject.datatimescope.db.generated.routines.Deletedatasetmarkerindices;
import org.gobiiproject.datatimescope.db.generated.routines.GenSalt2;
import org.gobiiproject.datatimescope.db.generated.routines.Getcvtermsbycvgroupname;
import org.gobiiproject.datatimescope.db.generated.routines.Getdnarunidsbyproject;
import org.gobiiproject.datatimescope.db.generated.routines.Gettimescoper;
import org.gobiiproject.datatimescope.db.generated.tables.Display;
import org.gobiiproject.datatimescope.db.generated.tables.VDatasetSummary;
import org.gobiiproject.datatimescope.db.generated.tables.records.AnalysisRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DnarunRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DnasampleRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ExperimentRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MapsetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerLinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MarkerRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.OrganizationRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ReferenceRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VDatasetSummaryRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VendorProtocolRecord;
import org.gobiiproject.datatimescope.entity.DatasetEntity;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.DnarunDeleteResultTableEntity;
import org.gobiiproject.datatimescope.entity.DnarunEntity;
import org.gobiiproject.datatimescope.entity.DnarunViewEntity;
import org.gobiiproject.datatimescope.entity.DnasampleDeleteResultTableEntity;
import org.gobiiproject.datatimescope.entity.DnasampleEntity;
import org.gobiiproject.datatimescope.entity.DnasampleViewEntity;
import org.gobiiproject.datatimescope.entity.GermplasmDeleteResultTableEntity;
import org.gobiiproject.datatimescope.entity.GermplasmEntity;
import org.gobiiproject.datatimescope.entity.GermplasmViewEntity;
import org.gobiiproject.datatimescope.entity.LinkageGroupEntity;
import org.gobiiproject.datatimescope.entity.LinkageGroupSummaryEntity;
import org.gobiiproject.datatimescope.entity.MarkerDeleteResultTableEntity;
import org.gobiiproject.datatimescope.entity.MarkerDetailDatasetEntity;
import org.gobiiproject.datatimescope.entity.MarkerDetailLinkageGroupEntity;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.VLinkageGroupSummaryEntity;
import org.gobiiproject.datatimescope.entity.VMarkerSummaryEntity;
import org.gobiiproject.datatimescope.utils.Utils;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.impl.DSL;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class ViewModelServiceImpl implements ViewModelService,Serializable{
    private static final long serialVersionUID = 1L;
    final static Logger log = Logger.getLogger(ViewModelServiceImpl.class.getName());
    private MarkerRecordEntity lastQueriedMarkerEntity;
    private String lastDSQuery;
    private DnarunEntity lastQueriedDNArunEntity;
    private DnasampleEntity lastQueriedDNAsampleEntity;
    private GermplasmEntity lastQueriedGermplasmEntity;
    
    @Override
    public boolean connectToDB(String userName, String password, ServerInfo serverInfo) {
        // TODO Auto-generated method stub
        boolean isConnected = false;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e1) {
            //TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try  {

	public ViewModelServiceImpl(){};

	@Override
	public boolean connectToDB(String userName, String password, ServerInfo serverInfo) {
		// TODO Auto-generated method stub
		boolean isConnected = false;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			//TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try  {

			String url = "jdbc:postgresql://"+serverInfo.getHost()+":"+serverInfo.getPort()+"/"+serverInfo.getDbName();
			Connection conn = DriverManager.getConnection(url, userName, password);        
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            Sessions.getCurrent().setAttribute("contextConfiguration", context.configuration());
            Sessions.getCurrent().setAttribute("serverInfo", serverInfo);

            isConnected = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            if(e.getLocalizedMessage().contains("dummy")){
                Messagebox.show("Invalid DB username/password.\n\nPlease contact your system admin.", "Dummy values found!", Messagebox.OK, Messagebox.ERROR);
            }
            else if(e.getLocalizedMessage().contains("FATAL: password authentication failed")){
                Messagebox.show("Invalid username or password.", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            else if(e.getMessage().contains("connect(Unknown Source)")){
                Messagebox.show("Host name not found.", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            else{
                Messagebox.show(e.getLocalizedMessage(), "Connect To Database Error", Messagebox.OK, Messagebox.ERROR);
            }
        }

        return isConnected;
    }

    @Override
    public boolean createNewUser(TimescoperEntity userAccount) {
        // TODO Auto-generated method stub

        boolean successful = false;
        try{


            DSLContext context = getDSLContext();

            Createtimescoper createTimescoper = createTimescoperFromRecord(userAccount);
            createTimescoper.execute(context.configuration());


            Messagebox.show("Successfully created new user!", "", Messagebox.OK, Messagebox.INFORMATION);
            successful = true;

        }
        catch(Exception e ){
            if(e.getMessage().contains("violates unique constraint")){
                Messagebox.show(userAccount.getUsername()+" already exists!", "ERROR", Messagebox.OK, Messagebox.ERROR);
            }
            else Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();
        }
        return successful;
    }



	public DSLContext getDSLContext() {
		// TODO Auto-generated method stub

        Configuration contextConfiguration = (Configuration) Sessions.getCurrent().getAttribute("contextConfiguration");
        DSLContext context = DSL.using(contextConfiguration);

        return context;
    }

    private Createtimescoper createTimescoperFromRecord(TimescoperEntity userAccount) {
        // TODO Auto-generated method stub

        Createtimescoper createTimescoper = new Createtimescoper();
        createTimescoper.set_Email(userAccount.getEmail());
        createTimescoper.set_Firstname(userAccount.getFirstname());
        createTimescoper.set_Lastname(userAccount.getLastname());
        createTimescoper.set_Password(userAccount.getPassword());
        createTimescoper.set_Role(userAccount.getRole());
        createTimescoper.set_Username(userAccount.getUsername());

        return createTimescoper;
    }

    @Override
    public boolean deleteUser(TimescoperEntity userAccount) {

        boolean successful = false;

        try{

            userAccount.delete();

            successful = true;

            Messagebox.show("Successfully deleted user!", "", Messagebox.OK, Messagebox.INFORMATION);

        }
        catch(Exception e ){

            e.printStackTrace();
        }
        return successful;
    }

    @Override
    public boolean deleteUsers(ListModelList<TimescoperEntity> selectedUsersList) {
        // TODO Auto-generated method stub

        boolean successful = false;
        try{

            DSLContext context = getDSLContext();

            context.batchDelete(selectedUsersList).execute();

            successful = true;

            Messagebox.show("Successfully deleted users!", "", Messagebox.OK, Messagebox.INFORMATION);

        }
        catch(Exception e ){

            e.printStackTrace();
        }
        return successful;
    }


    public TimescoperRecord loginTimescoper(String username, String password){
        boolean successful= false;
        TimescoperRecord user = new TimescoperRecord();
        Gettimescoper gettimescoper = new Gettimescoper();
        try{

            DSLContext context = getDSLContext();

            gettimescoper.set_Password(password);
            gettimescoper.set_Username(username);

            int result = gettimescoper.execute(context.configuration());

            if(result==0) successful=true;

        }catch(Exception e ){
            successful=false;
        }

        if(successful){
            user.setFirstname(gettimescoper.getFirstname());
            user.setLastname(gettimescoper.getLastname());
            user.setEmail(gettimescoper.getEmail());
            user.setRole(gettimescoper.getRole());
            user.setUsername(gettimescoper.getUsername());


        }else Messagebox.show("Invalid username or password!", "ERROR", Messagebox.OK, Messagebox.ERROR);

        return user;
    }

    @Override
    public List<CvRecord> getCvTermsByGroupName(String groupName) {
        // TODO Auto-generated method stub
        List<CvRecord> cvRecordList = null;
        try{
            DSLContext context = getDSLContext();

            //			Getcvtermsbycvgroupname getcvtermsbycvgroupname = new Getcvtermsbycvgroupname();
            //			getcvtermsbycvgroupname.setCvgroupname(groupName);
            //			getcvtermsbycvgroupname.execute(context.configuration());
            //
            //			cvRecordList  = getcvtermsbycvgroupname.getResults();

            //			select cv.term from cv, cvgroup
            //			where cv.cvgroup_id = cvgroup.cvgroup_id and cvgroup.name = cvgroupName;

            cvRecordList = context.select().from(CV, CVGROUP).where(CVGROUP.CVGROUP_ID.eq(CV.CVGROUP_ID)).and(CVGROUP.NAME.equal(groupName)).fetchInto(CvRecord.class);

        }
        catch(Exception e ){
            Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();
        }
        return cvRecordList;
    }

    public synchronized TimescoperEntity getUserInfo(String username){

        TimescoperEntity user = new TimescoperEntity();

        try{

            DSLContext context = getDSLContext();


            user =  context.fetchOne("select * from timescoper where username = '"+username+"';").into(TimescoperEntity.class);


        }catch(Exception e ){

            Messagebox.show("Invalid username", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return user;
    }

    @Override
    public List<TimescoperEntity> getAllOtherUsers(String username) {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<TimescoperEntity> userList = null;
        try{

            userList = context.fetch("select * from timescoper where username != '"+username+"';").into(TimescoperEntity.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve users", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return userList;
    }

    @Override
    public List<ContactRecord> getAllContacts() {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<ContactRecord> contactList = null;
        try{

            contactList = context.select().from(CONTACT).orderBy(CONTACT.LASTNAME).fetchInto(ContactRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve users", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return contactList;
    }

    @Override
    public List<VDatasetSummaryEntity> getAllDatasets(DatasetSummaryEntity datasetSummaryEntity) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();

        List<VDatasetSummaryEntity> datasetList = null;
        try{
            String query = "select d.dataset_id, d.name as dataset_name, d.experiment_id, e.name as experiment_name, d.callinganalysis_id, a.name as callingnalysis_name, d.analyses, d.data_table, d.data_file, d.quality_table, d.quality_file, d.scores, c1.username created_by_username, d.created_date, c2.username as modified_by_username, d.modified_date, cv1.term as status_name, cv2.term as type_name, j.name as job_name, pi.contact_id as pi_id, pi.firstname as pi_firstname, pi.lastname as pi_lastname, p.project_id, p.name as project_name from dataset d left join experiment e on d.experiment_id=e.experiment_id left join project p on e.project_id=p.project_id join contact pi on p.pi_contact=pi.contact_id  left join analysis a on a.analysis_id=d.callinganalysis_id left join contact c1 on c1.contact_id=d.created_by left join contact c2 on c2.contact_id=d.modified_by left join cv cv1 on cv1.cv_id=d.status left join cv cv2 on cv2.cv_id=d.type_id left join job j on j.job_id=d.job_id;";
            datasetList = context.fetch(query).into(VDatasetSummaryEntity.class);

            datasetSummaryEntity.setFilter("");
            setLastDSQuery(" (not filtered)");
        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve datasets", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return datasetList;
    }

    @Override
    public List<ContactRecord> getContactsByRoles(Integer[] role) {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<ContactRecord> contactList = null;
        try{

            contactList = context.select().from(CONTACT).where(CONTACT.ROLES.contains(role)).orderBy(CONTACT.LASTNAME).fetchInto(ContactRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve contacts", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return contactList;
    }

    @Override
    public boolean updateUser(TimescoperEntity userAccount) {
        // TODO Auto-generated method stub

        boolean successful = false;
        try{

            DSLContext context = getDSLContext();

            if(userAccount.changed(4)){ // check if pswrd was changed, it's the 4th field in TimescoperEntity
                GenSalt2 genSalt = new GenSalt2();
                genSalt.set__1("bf");
                genSalt.set__2(11);
                genSalt.execute(context.configuration());

                Crypt crypt = new Crypt();
                crypt.set__1(userAccount.getPassword()); 
                crypt.set__2(genSalt.getReturnValue());
                crypt.execute(context.configuration());

                userAccount.setPassword(crypt.getReturnValue());
            }

            userAccount.store();
            userAccount.refresh();

            successful = true;

            Messagebox.show("Successfully updated user!", "", Messagebox.OK, Messagebox.INFORMATION);

        }catch(Exception e ){
            if(e.getMessage().contains("violates unique constraint")){
                Messagebox.show(userAccount.getUsername()+" already exists!", "ERROR", Messagebox.OK, Messagebox.ERROR);
            }
            else Messagebox.show("Invalid username", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return successful;

    }

    @Override
    public boolean deleteDataset(VDatasetSummaryEntity vDatasetSummaryRecord, List<DatasetSummaryEntity> datasetSummary,
            DatasetSummaryEntity datasetSummaryEntity) {
        // TODO Auto-generated method stub

        boolean successful = false;

        if(vDatasetSummaryRecord.getDataFile()!=null){
            String deletedErrorMessage = deleteFilePath(vDatasetSummaryRecord.getDataFile());

            if(deletedErrorMessage!=null){
                Messagebox.show(deletedErrorMessage, "ERROR: Cannot delete data file!", Messagebox.OK, Messagebox.ERROR);
                return false;
            }
        }

        try{

            // DB deletion starts here
            Integer dataset_id = vDatasetSummaryRecord.getDatasetId();
            Configuration configuration = vDatasetSummaryRecord.configuration();

            int totalDeletedDatasetMarkerIndices = 0, totalDeletedDatasetDnarunIndices = 0;

            //delete Marker.dataset_marker_idx
            double startTime = System.currentTimeMillis();
            totalDeletedDatasetMarkerIndices = totalDeletedDatasetMarkerIndices + deleteDatasetMarkerIndices(dataset_id, configuration);
            double endTime = System.currentTimeMillis();
            double Markerseconds = (endTime - startTime) / 1000;


            //delete Dnarun.dataset_dnarun idx entries for that particular dataset
            startTime = System.currentTimeMillis();
            totalDeletedDatasetDnarunIndices = totalDeletedDatasetDnarunIndices + deleteDatasetDnarunIndices(dataset_id, configuration);
            endTime = System.currentTimeMillis();
            double DNARunSeconds = (endTime - startTime) / 1000;

            startTime = System.currentTimeMillis();
            DatasetRecord dr = new DatasetRecord();
            dr.setDatasetId(vDatasetSummaryRecord.getDatasetId());
            dr.attach(configuration);
            dr.delete();
            endTime = System.currentTimeMillis();
            double rowDeleteSeconds = (endTime - startTime) / 1000;

            successful = true;
            String message = "1 dataset deleted. ("+Double.toString(rowDeleteSeconds)+" sec) \n"+Integer.toString(totalDeletedDatasetMarkerIndices)+" markers updated. ("+Double.toString(Markerseconds)+" sec) \n"+Integer.toString(totalDeletedDatasetDnarunIndices)+" DNAruns updated. ("+Double.toString(DNARunSeconds)+" sec) \n";
            Messagebox.show(message, "Successfully deleted dataset!",Messagebox.OK, Messagebox.INFORMATION);

            //set Summary

            //dataset
            String recentFilter = datasetSummaryEntity.getFilter();
            datasetSummaryEntity = new DatasetSummaryEntity();
            datasetSummaryEntity.setFilter(recentFilter);
            datasetSummaryEntity.setEntityName("Dataset rows");
            datasetSummaryEntity.setRowCount("1");
            datasetSummaryEntity.setDuration(Double.toString(rowDeleteSeconds)+" sec");

            datasetSummary.add(datasetSummaryEntity);

            //dataset DNA Run
            datasetSummaryEntity = new DatasetSummaryEntity();
            datasetSummaryEntity.setEntityName("DNA Run Indices");
            datasetSummaryEntity.setRowCount(Integer.toString(totalDeletedDatasetDnarunIndices));
            datasetSummaryEntity.setDuration(Double.toString(DNARunSeconds)+" sec");
            datasetSummaryEntity.setFilter("");

            datasetSummary.add(datasetSummaryEntity);


            //dataset Marker Run
            datasetSummaryEntity = new DatasetSummaryEntity();
            datasetSummaryEntity.setEntityName("Marker Indices");
            datasetSummaryEntity.setRowCount(Integer.toString(totalDeletedDatasetMarkerIndices));
            datasetSummaryEntity.setDuration(Double.toString(Markerseconds)+" sec");
            datasetSummaryEntity.setFilter("");

            datasetSummary.add(datasetSummaryEntity);
            //border
            datasetSummaryEntity = new DatasetSummaryEntity();
            datasetSummaryEntity.setEntityName(" ");
            datasetSummaryEntity.setRowCount(" ");
            datasetSummaryEntity.setDuration(" ");
            datasetSummaryEntity.setFilter(" ");

            datasetSummary.add(datasetSummaryEntity);
            
            String user = getUser();
            
            StringBuilder logSB = new StringBuilder();
            logSB.append("["+user+ "] DELETED A DATASET\n\n");
            logSB.append("["+user+"] Filtering criteria for dataset delete:\n "+getLastDSQuery());
            logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n"
                    + "Deletedatasetmarkerindices deleteDatasetMarkerIndices = new Deletedatasetmarkerindices();\r\n" + 
                    "            deleteDatasetMarkerIndices.setDatasetid("+dataset_id.toString()+");\r\n" + 
                    "            deleteDatasetMarkerIndices.attach(configuration);\r\n" + 
                    "            deleteDatasetMarkerIndices.execute();\n\n"+
                    "Deletedatasetdnarunindices deleteDatasetDnarunIndices = new Deletedatasetdnarunindices();\r\n" + 
                    "            deleteDatasetDnarunIndices.setDatasetid("+dataset_id.toString()+");\r\n" + 
                    "            deleteDatasetDnarunIndices.attach(configuration);\r\n" + 
                    "            deleteDatasetDnarunIndices.execute();\n\n" + 
                    "DatasetRecord dr = new DatasetRecord();\r\n" + 
                    "            dr.setDatasetId("+dataset_id.toString()+");\r\n" + 
                    "            dr.attach(configuration);\r\n" + 
                    "            dr.delete();");
            logSB.append("\n\n["+user+"] Dataset delete result:\n"+message);
            logSB.append("\n--------------------------------------------------");
            log.info(logSB.toString());
            
        }
        catch(Exception e ){
            Messagebox.show(e.getLocalizedMessage(), "ERROR: Cannot delete dataset!", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();
        }
        return successful;
    }

    private int deleteDatasetDnarunIndices(Integer dataset_id, Configuration configuration) {
        // TODO Auto-generated method stub
        int deletedDatasetDnarunIndices = 0;
        try{
            Deletedatasetdnarunindices deleteDatasetDnarunIndices = new Deletedatasetdnarunindices();
            deleteDatasetDnarunIndices.setDatasetid(dataset_id);
            deleteDatasetDnarunIndices.attach(configuration);
            deleteDatasetDnarunIndices.execute();
            deletedDatasetDnarunIndices = deleteDatasetDnarunIndices.getReturnValue();
        }
        catch (Exception e){
            Messagebox.show(e.getLocalizedMessage(), "ERROR: Cannot delete dnarun indices!", Messagebox.OK, Messagebox.ERROR);
            log.error("Cannot delete marker dnarun for dataset id"+ Integer.toString(dataset_id) +"\n"+ e.getStackTrace().toString());
        }

        return deletedDatasetDnarunIndices;

    }

    private int deleteDatasetMarkerIndices(Integer dataset_id, Configuration configuration) {
        // TODO Auto-generated method stub

        int deletedDatasetMarkerIndices = 0;
        try{

            Deletedatasetmarkerindices deleteDatasetMarkerIndices = new Deletedatasetmarkerindices();
            deleteDatasetMarkerIndices.setDatasetid(dataset_id);
            deleteDatasetMarkerIndices.attach(configuration);
            deleteDatasetMarkerIndices.execute();
            deletedDatasetMarkerIndices = deleteDatasetMarkerIndices.getReturnValue();

        }
        catch (Exception e){
            Messagebox.show(e.getLocalizedMessage(), "ERROR: Cannot delete dataset marker indices!", Messagebox.OK, Messagebox.ERROR);
            log.error("Cannot delete marker indices for dataset id"+ Integer.toString(dataset_id) +"\n"+ e.getStackTrace().toString());
        }

        return deletedDatasetMarkerIndices;

    }

    private String deleteFilePath(String string) {
        // TODO Auto-generated method stub
        String deletedSuccessfully = null;
        Path path = Paths.get(string);

        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            // We will now ignore empty datasets
            //			deletedSuccessfully = path+": no such" + " file or directory.";
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            deletedSuccessfully = path+": is a directory that is not empty";
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            deletedSuccessfully = x.getLocalizedMessage();
            System.err.println(x);
        }

        return deletedSuccessfully;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean deleteDatasets(List<VDatasetSummaryEntity> selectedDsList, List<DatasetSummaryEntity> datasetSummary,
            DatasetSummaryEntity datasetSummaryEntity)  {
        // TODO Auto-generated method stub

        int dsCount = selectedDsList.size();
        boolean successful = false;


        DSLContext context = getDSLContext();

        //Try to deleteDataFiles
        List<VDatasetSummaryEntity> cannotDeleteFileDSList = new ArrayList<VDatasetSummaryEntity>();

        StringBuilder errorMessages = new StringBuilder();
        StringBuilder errorDSNames = new StringBuilder();

        for(VDatasetSummaryEntity ds : selectedDsList){

            if(ds.getDataFile()!=null){	
                String errorMessage =  deleteFilePath(ds.getDataFile());

                if(errorMessage!=null){
                    errorMessages.append(errorMessage+"\n");
                    errorDSNames.append(ds.getDatasetName()+"\n");
                    cannotDeleteFileDSList.add(ds);
                }
            }	

        }

        //check if there are datasets that can't be deleted
        if(!errorMessages.toString().isEmpty()){
            Messagebox.show("Cannot delete the data files for the following dataset(s):\n\n"+errorDSNames.toString()+"\n\n Do you still want to continue?", 
                    "Some dataset can't be deleted", Messagebox.YES | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener(){
                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        //YES is clicked

                        if(selectedDsList.size() == cannotDeleteFileDSList.size()){

                            Messagebox.show("Cannot delete the datafiles for all of the datasets selected ", "ERROR: Cannot delete datasets!", Messagebox.OK, Messagebox.ERROR);

                        }else{
                            //remove error datasets from the list of dataset to be deleted.
                            selectedDsList.removeAll(cannotDeleteFileDSList);

                        }
                    }
                }
            });


        }

        //move on to deletion
        StringBuilder dsLeft = new StringBuilder();
        StringBuilder dsIDLeft = new StringBuilder();
        int totalDeletedDatasetMarkerIndices = 0, totalDeletedDatasetDnarunIndices = 0;
        double markerseconds=0, dnaRunSeconds=0, rowDeleteSeconds = 0;
        for(VDatasetSummaryEntity ds : selectedDsList){

            //check which datasets are left just to be sure and display it later for the user to see 
            dsLeft.append(ds.getDatasetName()+"\n");
            dsIDLeft.append(" "+Integer.toString(ds.getDatasetId())+",");


            Integer dataset_id = ds.getDatasetId();
            Configuration configuration = ds.configuration();


            double startTime = System.currentTimeMillis();
            //delete Marker.dataset_marker_idx
            totalDeletedDatasetMarkerIndices = totalDeletedDatasetMarkerIndices + deleteDatasetMarkerIndices(dataset_id, configuration);
            double endTime = System.currentTimeMillis();
            double newMarkerseconds = (endTime - startTime) / 1000;
            markerseconds += newMarkerseconds;

            //delete Dnarun.dataset_dnarun idx entries for that particular dataset
            startTime = System.currentTimeMillis();
            totalDeletedDatasetDnarunIndices = totalDeletedDatasetDnarunIndices + deleteDatasetDnarunIndices(dataset_id, configuration);
            endTime = System.currentTimeMillis();
            double newdnaRunSeconds = (endTime - startTime) / 1000;
            dnaRunSeconds += newdnaRunSeconds;
        }

        try{

            double startTime = System.currentTimeMillis();

            context.deleteFrom(DATASET).where(DATASET.DATASET_ID.in(selectedDsList
                    .stream()
                    .map(VDatasetSummaryEntity::getDatasetId)
                    .collect(Collectors.toList())))
            .execute();

            double endTime = System.currentTimeMillis();
            rowDeleteSeconds = (endTime - startTime) / 1000;


            //set Summary

            //dataset
            String recentFilter = datasetSummaryEntity.getFilter();
            datasetSummaryEntity = new DatasetSummaryEntity();
            datasetSummaryEntity.setFilter(recentFilter);
            datasetSummaryEntity.setEntityName("Dataset rows");
            datasetSummaryEntity.setRowCount(Integer.toString(selectedDsList.size()));
            datasetSummaryEntity.setDuration(Double.toString(rowDeleteSeconds)+" sec");

            datasetSummary.add(datasetSummaryEntity);

            //dataset DNA Run
            datasetSummaryEntity = new DatasetSummaryEntity();
            datasetSummaryEntity.setEntityName("DNA Run Indices");
            datasetSummaryEntity.setRowCount(Integer.toString(totalDeletedDatasetDnarunIndices));
            datasetSummaryEntity.setDuration(Double.toString(dnaRunSeconds)+" sec");
            datasetSummaryEntity.setFilter("");

            datasetSummary.add(datasetSummaryEntity);


            //dataset Marker Run
            datasetSummaryEntity = new DatasetSummaryEntity();
            datasetSummaryEntity.setEntityName("Marker Indices");
            datasetSummaryEntity.setRowCount(Integer.toString(totalDeletedDatasetMarkerIndices));
            datasetSummaryEntity.setDuration(Double.toString(markerseconds)+" sec");
            datasetSummaryEntity.setFilter("");

            datasetSummary.add(datasetSummaryEntity);

            //border
            datasetSummaryEntity = new DatasetSummaryEntity();
            datasetSummaryEntity.setEntityName("");
            datasetSummaryEntity.setRowCount("");
            datasetSummaryEntity.setDuration("");
            datasetSummaryEntity.setFilter("");
            datasetSummary.add(datasetSummaryEntity);

        }
        catch(Exception e ){

            e.printStackTrace();
        }
        String message = Integer.toString(selectedDsList.size())+" datasets deleted. ("+Double.toString(rowDeleteSeconds)+" sec) \n"+Integer.toString(totalDeletedDatasetMarkerIndices)+" markers updated. ("+Double.toString(markerseconds)+" sec) \n"+Integer.toString(totalDeletedDatasetDnarunIndices)+" DNAruns updated. ("+Double.toString(dnaRunSeconds)+" sec) \n";
        
        String user = getUser();
        
        StringBuilder logSB = new StringBuilder();
        logSB.append("["+user+ "] DELETED DATASETS\n\n");
        logSB.append("["+user+"] Filtering criteria for dataset delete:\n "+getLastDSQuery());
        
        logSB.append("\n\n["+user+"] Background JOOQ commands that ran for each dataset_id in selectedDsList (delete marker and dnarun indices):\n"+
                "Deletedatasetmarkerindices deleteDatasetMarkerIndices = new Deletedatasetmarkerindices();\r\n" + 
                "            deleteDatasetMarkerIndices.setDatasetid(dataset_id);\r\n" + 
                "            deleteDatasetMarkerIndices.attach(configuration);\r\n" + 
                "            deleteDatasetMarkerIndices.execute();\n\n"+
                "Deletedatasetdnarunindices deleteDatasetDnarunIndices = new Deletedatasetdnarunindices();\r\n" + 
                "            deleteDatasetDnarunIndices.setDatasetid(dataset_id);\r\n" + 
                "            deleteDatasetDnarunIndices.attach(configuration);\r\n" + 
                "            deleteDatasetDnarunIndices.execute();\n");

        logSB.append("\n["+user+"] JOOQ command to delete dataset in bulk:\n"
                + "context.deleteFrom(DATASET).where(DATASET.DATASET_ID.in(selectedDsList\r\n" + 
                "            .stream()\r\n" + 
                "            .map(VDatasetSummaryEntity::getDatasetId)\r\n" + 
                "            .collect(Collectors.toList())))\r\n" + 
                "            .execute();");
        logSB.append("\n\n["+user+"] Dataset delete result:\n"+ message);
        logSB.append("\n--------------------------------------------------");
        log.info(logSB.toString());
       
        Messagebox.show(message, "Successfully deleted datasets!",Messagebox.OK, Messagebox.INFORMATION);
        if (selectedDsList.size()>0) successful=true;
        return successful;
    }

    @Override
    public List<VDatasetSummaryEntity> getAllDatasetsBasedOnQuery(DatasetEntity datasetEntity, DatasetSummaryEntity datasetSummaryEntity) {
        // TODO Auto-generated method stub
        int queryCount =0;
        int dsNameCount = 0;
        DSLContext context = getDSLContext();

        List<VDatasetSummaryEntity> datasetList = null;
        try{ //c3.lastname as pi_contact,
            StringBuilder sb = new StringBuilder();
            StringBuilder sbFilteringCriteria = new StringBuilder();

            sb.append("select d.dataset_id, d.name as dataset_name, d.experiment_id, e.name as experiment_name, d.callinganalysis_id, a.name as callingnalysis_name, d.analyses, d.data_table, d.data_file, d.quality_table, d.quality_file, d.scores, c1.username created_by_username, d.created_date, c2.username as modified_by_username, d.modified_date, cv1.term as status_name, cv2.term as type_name, j.name as job_name, pi.contact_id as pi_id, pi.firstname as pi_firstname, pi.lastname as pi_lastname, p.project_id, p.name as project_name from dataset d left join experiment e on d.experiment_id=e.experiment_id left join project p on e.project_id=p.project_id join contact pi on p.pi_contact=pi.contact_id  left join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) left join contact c1 on c1.contact_id=d.created_by left join contact c2 on c2.contact_id=d.modified_by left join cv cv1 on cv1.cv_id=d.status left join cv cv2 on cv2.cv_id=d.type_id left join job j on j.job_id=d.job_id ");

            if (datasetEntity.getDatasetNamesAsEnterSeparatedString()!=null && !datasetEntity.getDatasetNamesAsEnterSeparatedString().isEmpty()){
                String names = datasetEntity.getSQLReadyDatasetNames();
                sbFilteringCriteria.append("\n Dataset name is/are: "+names);

                sb.append(" where LOWER(d.name) in ("+names+")");
                dsNameCount++;	
            }

            if (datasetEntity.getCreatedByContactRecord()!=null){

                if(datasetEntity.getCreatedByContactRecord().getContactId()!=0){
                    checkPreviousAppends(dsNameCount, queryCount, sb);
                    String id = Integer.toString(datasetEntity.getCreatedByContactRecord().getContactId());
                    sbFilteringCriteria.append("\n Contact ID: "+id);
                    sb.append(" c1.contact_id="+id);
                    queryCount++;
                }
            }
            if (datasetEntity.getDatasetTypeRecord()!=null){

                if(datasetEntity.getDatasetTypeRecord().getCvId()!=0){
                    checkPreviousAppends(dsNameCount, queryCount, sb);

                    String id = Integer.toString(datasetEntity.getDatasetTypeRecord().getCvId());
                    sbFilteringCriteria.append("\n Cv ID: "+id);
                    sb.append(" cv2.cv_id="+id);
                    queryCount++;
                }
            }
            if (datasetEntity.getPiRecord()!=null){

                if(datasetEntity.getPiRecord().getContactId()!=0){
                    checkPreviousAppends(dsNameCount, queryCount, sb);
                    String id = Integer.toString(datasetEntity.getPiRecord().getContactId());
                    sbFilteringCriteria.append("\n PI Contact ID: "+id);
                    sb.append(" p.pi_contact="+id);
                    queryCount++;
                }
            }
            if(datasetEntity.getProjectRecord()!=null) {
                if(datasetEntity.getProjectRecord().getProjectId()!=0) {
                    checkPreviousAppends(dsNameCount, queryCount, sb);
                    String id = Integer.toString(datasetEntity.getProjectRecord().getProjectId());
                    sbFilteringCriteria.append("\n Project ID: "+id);
                    sb.append(" p.project_id="+id);
                    queryCount++;
                }
            }
            if(datasetEntity.getExperimentRecord()!=null) {
                if(datasetEntity.getExperimentRecord().getExperimentId()!=0) {
                    checkPreviousAppends(dsNameCount, queryCount, sb);
                    String id = Integer.toString(datasetEntity.getExperimentRecord().getExperimentId());
                    sbFilteringCriteria.append("\n Experiment ID: "+id);
                    sb.append(" e.experiment_id="+id);
                    queryCount++;
                }
            }
            if(datasetEntity.getAnalysisRecord()!=null) {
                if(datasetEntity.getAnalysisRecord().getAnalysisId()!=0) {
                    checkPreviousAppends(dsNameCount, queryCount, sb);
                    String id = Integer.toString(datasetEntity.getAnalysisRecord().getAnalysisId());
                    sbFilteringCriteria.append("\n Analysis ID: "+id);
                    sb.append(" a.analysis_id="+id);
                    queryCount++;
                }
            }
            if (datasetEntity.getDatasetIDStartRange()!=null || datasetEntity.getDatasetIDEndRange()!=null){

                //check which is not null
                checkPreviousAppends(dsNameCount, queryCount, sb);

                if(datasetEntity.getDatasetIDStartRange()!=null && datasetEntity.getDatasetIDEndRange()!=null){ //if both is not null
                    Integer lowerID = datasetEntity.getDatasetIDStartRange();
                    Integer higherID = datasetEntity.getDatasetIDEndRange();

                    if(lowerID.compareTo(higherID)>0){
                        lowerID = datasetEntity.getDatasetIDEndRange();
                        higherID = datasetEntity.getDatasetIDStartRange();
                    }

                    sbFilteringCriteria.append("\n Dataset ID between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
                    sb.append(" d.dataset_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
                }else{
                    Integer ID = null;
                    if(datasetEntity.getDatasetIDStartRange()!=null) ID = datasetEntity.getDatasetIDStartRange();
                    else ID = datasetEntity.getDatasetIDEndRange();

                    sbFilteringCriteria.append("\n Dataset ID : "+Integer.toString(ID));
                    sb.append(" d.dataset_id = "+Integer.toString(ID));
                }

                queryCount++;
            }

            if (datasetEntity.getCreationDateStart()!=null || datasetEntity.getCreationDateEnd()!=null){

                checkPreviousAppends(dsNameCount, queryCount, sb);

                if(datasetEntity.getCreationDateStart()!=null && datasetEntity.getCreationDateEnd()!=null){ //if both is not null

                    java.sql.Date sqlDateStart = null;
                    java.sql.Date sqlDateEnd= null;

                    //check order of query. This is to filter out dummy queries where the range is not in the proper order.

                    if(datasetEntity.getCreationDateStart().after(datasetEntity.getCreationDateEnd())){

                        sqlDateStart = new java.sql.Date(datasetEntity.getCreationDateEnd().getTime());
                        sqlDateEnd = new java.sql.Date(datasetEntity.getCreationDateStart().getTime());

                    }else{

                        sqlDateStart = new java.sql.Date(datasetEntity.getCreationDateStart().getTime());
                        sqlDateEnd = new java.sql.Date(datasetEntity.getCreationDateEnd().getTime());
                    }

                    sbFilteringCriteria.append("\n Created date between "+sqlDateStart+" and "+sqlDateEnd);
                    sb.append(" d.created_date between '"+sqlDateStart+"' and '"+sqlDateEnd+"' order by d.created_date");
                }
                else{ //check which is not null

                    java.sql.Date sqlDate = null;

                    if(datasetEntity.getCreationDateStart()!=null){
                        sqlDate = new java.sql.Date(datasetEntity.getCreationDateStart().getTime());
                    }else{
                        sqlDate  = new java.sql.Date(datasetEntity.getCreationDateEnd().getTime());
                    }

                    sbFilteringCriteria.append("\n Created on: "+sqlDate);
                    sb.append(" d.created_date = '"+sqlDate+"' ");
                }

                queryCount++;
            }

            sb.append(";");
            String query = sb.toString();
            datasetList = context.fetch(query).into(VDatasetSummaryEntity.class);
            datasetSummaryEntity.setFilter(sbFilteringCriteria.toString());
            setLastDSQuery(sbFilteringCriteria.toString());
        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve datasets", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();
        }
        return datasetList;
    }

    private void checkPreviousAppends(int dsNameCount, int queryCount, StringBuilder sb){
        // TODO Auto-generated method stub

        if(dsNameCount==0 && queryCount==0) sb.append(" where ");
        else sb.append(" and ");
    }

    @Override
    public List<VMarkerSummaryEntity> getAllMarkersBasedOnQuery(MarkerRecordEntity markerEntity, DatasetSummaryEntity markerSummaryEntity) {
        // TODO Auto-generated method stub

        lastQueriedMarkerEntity = new MarkerRecordEntity();
        int queryCount =0;
        int dsNameCount = 0;
        DSLContext context = getDSLContext();

        List<VMarkerSummaryEntity> markerList = null;

        try{ /* START building THE QUERY via StringBuilder */
            StringBuilder sb = new StringBuilder();
            StringBuilder sbWhere = new StringBuilder(); 

            sb.append("SELECT distinct on (m.marker_id) m.marker_id, m.platform_id, pl.name AS platform_name, m.variant_id, m.name AS marker_name, m.code, m.ref, m.alts, m.sequence, m.reference_id,r.name AS reference_name, m.primers, m.strand_id, cv.term AS strand_name, m.status, m.probsets, m.dataset_marker_idx, m.props, m.dataset_vendor_protocol FROM marker m ");
            sb.append(" LEFT JOIN platform pl ON m.platform_id = pl.platform_id LEFT JOIN cv ON m.strand_id = cv.cv_id  left join marker_linkage_group mlg on m.marker_id = mlg.marker_id left join linkage_group lg on mlg.linkage_group_id = lg.linkage_group_id left join mapset map on lg.map_id = map.mapset_id left join reference r on map.reference_id = r.reference_id ");

            /* ADD THE "WHERE" CONDITIONS */

            // build query for MARKER NAMES filter
            if (markerEntity.getMarkerNamesAsCommaSeparatedString()!=null && !markerEntity.getMarkerNamesAsCommaSeparatedString().isEmpty()){
                lastQueriedMarkerEntity.setMarkerNamesAsCommaSeparatedString(markerEntity.getMarkerNamesAsCommaSeparatedString());
                sbWhere.append(" where LOWER(m.name) in ("+markerEntity.getSQLReadyMarkerNames()+")");
                dsNameCount++;	
            }


            //build query for 'none' selected on dataset filter
            if(markerEntity.isMarkerNotInDatasets()) {
                lastQueriedMarkerEntity.setMarkerNotInDatasets(true);
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" m.dataset_marker_idx = '{}' ");
                queryCount++;

            }

            // build query for MAPSET filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getMapsetList())){ 
                lastQueriedMarkerEntity.getMapsetList().addAll(markerEntity.getMapsetList());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" map.mapset_id "+ getIDsToString(markerEntity.getMapsetList()));
                queryCount++;
            }

            // build query for LINKAGE GROUP filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getLinkageGroupList())){ 
                lastQueriedMarkerEntity.getLinkageGroupList().addAll(markerEntity.getLinkageGroupList());

                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" lg.linkage_group_id "+ getIDsToString(markerEntity.getLinkageGroupList()));
                queryCount++;
            }

            // build query for PLATFORM filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getPlatformList())){
                lastQueriedMarkerEntity.getPlatformList().addAll(markerEntity.getPlatformList());

                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" pl.platform_id "+ getIDsToString(markerEntity.getPlatformList()));
                queryCount++;
            }

            // build query for VENDOR-PROTOCOL filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getVendorProtocolList())){
                lastQueriedMarkerEntity.getVendorProtocolList().addAll(markerEntity.getVendorProtocolList());
                sb.append(buildLeftJoin(sb,"vendorprotocol"));
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" vp.vendor_protocol_id "+ getIDsToString(markerEntity.getVendorProtocolList()));
                queryCount++;
            }

            // build query for PROJECTS filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getProjectList())){
                lastQueriedMarkerEntity.getProjectList().addAll(markerEntity.getProjectList());
                sb.append(buildLeftJoin(sb,"project"));
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" prj.project_id "+ getIDsToString(markerEntity.getProjectList()));
                queryCount++;
            }

            // build query for EXPERIMENTS filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getExperimentList())){
                lastQueriedMarkerEntity.getExperimentList().addAll(markerEntity.getExperimentList());
                sb.append(buildLeftJoin(sb,"experiment"));
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" e.experiment_id "+ getIDsToString(markerEntity.getExperimentList()));
                queryCount++;
            }

            // build query for DATASETS filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getDatasetList()) && !markerEntity.isMarkerNotInDatasets()){
                lastQueriedMarkerEntity.getDatasetList().addAll(markerEntity.getDatasetList());
                sb.append(buildLeftJoin(sb,"dataset"));
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" d.dataset_id "+ getIDsToString(markerEntity.getDatasetList()));
                queryCount++;
            }

            // build query for ANALYSES filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getAnalysesList()) && !markerEntity.isMarkerNotInDatasets()){
                lastQueriedMarkerEntity.getAnalysesList().addAll(markerEntity.getAnalysesList());
                sb.append(buildLeftJoin(sb,"analysis"));
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" a.analysis_id "+ getIDsToString(markerEntity.getAnalysesList()));
                queryCount++;
            }

            // build query for given marker IDs
            if (markerEntity.getMarkerIDStartRange()!=null || markerEntity.getMarkerIDEndRange()!=null){

                //check which is not null
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);

                if(markerEntity.getMarkerIDStartRange()!=null && markerEntity.getMarkerIDEndRange()!=null){ //if both is not null
                    Integer lowerID = markerEntity.getMarkerIDStartRange();
                    Integer higherID = markerEntity.getMarkerIDEndRange();

                    if(lowerID.compareTo(higherID)>0){
                        lowerID = markerEntity.getMarkerIDEndRange();
                        higherID = markerEntity.getMarkerIDStartRange();
                    }
                    lastQueriedMarkerEntity.setMarkerIDEndRange(markerEntity.getMarkerIDEndRange());
                    lastQueriedMarkerEntity.setMarkerIDStartRange(lastQueriedMarkerEntity.getMarkerIDStartRange());
                    sbWhere.append(" m.marker_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
                }else{
                    Integer ID = null;
                    if(markerEntity.getMarkerIDStartRange()!=null) {
                        ID = markerEntity.getMarkerIDStartRange();
                        lastQueriedMarkerEntity.setMarkerIDStartRange(lastQueriedMarkerEntity.getMarkerIDStartRange());
                    }
                    else {
                        lastQueriedMarkerEntity.setMarkerIDEndRange(markerEntity.getMarkerIDEndRange());
                        ID = markerEntity.getMarkerIDEndRange();
                    }

                    sbWhere.append(" m.marker_id = "+Integer.toString(ID));
                }

                queryCount++;
            }

            if(sbWhere.length()>0) {
                    sbWhere.append(";");
                    sb.append(sbWhere.toString());
                    String query = sb.toString();
                    System.out.println(query);
                    markerList = context.fetch(query).into(VMarkerSummaryEntity.class);
            }
            else {
                Messagebox.show("Please specify filters", "There is nothing selected", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }catch(Exception e ){
            e.printStackTrace();
            Messagebox.show("There was an error while trying to retrieve markers", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return markerList;

    }

    private String buildLeftJoin(StringBuilder sb, String category) {
        // TODO Auto-generated method stub

        StringBuilder returnValBuilder = new StringBuilder();

        switch(category){
        case "analysis":
            if(!sb.toString().contains("dataset d")) {
                returnValBuilder.append("LEFT JOIN dataset d ON jsonb_exists(m.dataset_marker_idx, d.dataset_id::text) ");
            }
            returnValBuilder.append(" left join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) ");
            break;
        default:

            //dataset
            if(!sb.toString().contains("dataset d")) {
                returnValBuilder.append("LEFT JOIN dataset d ON jsonb_exists(m.dataset_marker_idx, d.dataset_id::text) ");
            }
            if (category.equalsIgnoreCase("dataset")) break;

            //experiment
            if(!sb.toString().contains("experiment e")) {
                returnValBuilder.append("LEFT JOIN experiment e ON d.experiment_id = e.experiment_id ");
            }
            if(category.equals("experiment")) break;

            //vendor-protocol
            if(!sb.toString().contains("vendor_protocol vp")) {
                returnValBuilder.append("LEFT JOIN vendor_protocol vp ON e.vendor_protocol_id = vp.vendor_protocol_id ");
            }
            if(category.equals("vendorprotocol")) break;

            //project
            if(!sb.toString().contains("project prj")) {
                returnValBuilder.append("LEFT JOIN project prj ON e.project_id = prj.project_id ");
            }
            break;
        }

        return returnValBuilder.toString();
    }

    @Override
    public List<VMarkerSummaryEntity> getAllMarkers(List<DatasetSummaryEntity> markerSummary) {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();

        List<VMarkerSummaryEntity> markerList = null;
        try{
            String query = "SELECT m.marker_id, m.platform_id, p.name AS platform_name, m.variant_id, m.name AS marker_name, m.code, m.ref, m.alts, m.sequence, r.reference_id, r.name AS reference_name, m.primers, m.strand_id, cv.term AS strand_name, m.status, m.probsets, m.dataset_marker_idx, m.props, m.dataset_vendor_protocol FROM marker m LEFT JOIN platform pl ON m.platform_id = p.platform_id LEFT JOIN cv ON m.strand_id = cv.cv_id  left join marker_linkage_group mlg on m.marker_id = mlg.marker_id left join linkage_group lg on mlg.linkage_group_id = lg.linkage_group_id left join mapset map on lg.map_id = map.mapset_id left join reference r on map.reference_id = r.reference_id;";
            markerList = context.fetch(query).into(VMarkerSummaryEntity.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve markers", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return markerList;

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean deleteMarker(VMarkerSummaryEntity vMarkerSummaryEntity, 
            List<DatasetSummaryEntity> markerSummary) {
        boolean successful = false;
        List<VMarkerSummaryEntity> selectedMarkerList = new ArrayList<VMarkerSummaryEntity>();
        selectedMarkerList.add(vMarkerSummaryEntity);
        //check if Marker is not being used in a Marker Group or a Dataset
        List<VMarkerSummaryEntity> unusedInMarkersGroupsOrDataset = null;

        unusedInMarkersGroupsOrDataset = checkWhichMarkersAreUsedInAMarkerGroupOrDataset(selectedMarkerList);

        if(unusedInMarkersGroupsOrDataset.size()>0){ // If there are markers that can be deleted 

            Messagebox.show("THIS ACTION IS NOT REVERSIBLE.\n\n Do you want to continue?\n", 
                    "WARNING", Messagebox.YES | Messagebox.CANCEL,
                    Messagebox.EXCLAMATION,
                    new org.zkoss.zk.ui.event.EventListener(){
                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        DSLContext context = getDSLContext();

                        double startTime = 0, endTime=0, startTimeMLG = 0, endTimeMLG=0;
                        startTimeMLG = System.currentTimeMillis();
                        Integer markerId = selectedMarkerList.get(0).getMarkerId();

                        int result = context.delete(MARKER_LINKAGE_GROUP)
                                .where(MARKER_LINKAGE_GROUP.MARKER_ID.eq(markerId))
                                .execute();

                        endTimeMLG = System.currentTimeMillis();

                        startTime = System.currentTimeMillis();
                        context.delete(MARKER)
                        .where(MARKER.MARKER_ID.eq(markerId))
                        .execute();

                        endTime = System.currentTimeMillis();
                        double rowDeleteSeconds = (endTime - startTime) / 1000;
                        double rowDeleteSecondsMLG = (endTimeMLG - startTimeMLG) / 1000;

                        List<String> successMessagesAsList = new ArrayList<String>();
                        successMessagesAsList.add("1 marker deleted. ("+Double.toString(rowDeleteSeconds)+" sec)" );
                        successMessagesAsList.add(Integer.toString(result) +" marker_linkage_group row(s) deleted. ("+Double.toString(rowDeleteSecondsMLG)+" sec)");
                       
                        String user = getUser();
                        StringBuilder logSB = new StringBuilder();
                        logSB.append("["+user+ "] DELETED A MARKER\n\n"); 
                        logSB.append("["+user+ "] Filtering criteria for marker delete:\n"+lastQueriedMarkerEntity.getCompleteFiltersAsText());
                        logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n\n"+
                                "context.delete(MARKER_LINKAGE_GROUP).where(\n"
                                + "            MARKER_LINKAGE_GROUP.MARKER_ID.eq("+markerId.toString()+"))\n"
                                        + "            .execute();\n"+
                                "context.delete(MARKER).where(\n"
                                + "            MARKER.MARKER_ID.eq("+markerId.toString()+"))\n"
                                        + "            .execute();");
          
                        logSB.append("\n\n["+user+"] Marker delete result:\n"+ "1 marker deleted. ("+Double.toString(rowDeleteSeconds)+" sec)\n"+Integer.toString(result) +" marker_linkage_group row(s) deleted. ("+Double.toString(rowDeleteSecondsMLG)+" sec)");

                        logSB.append("\n--------------------------------------------------");
                        log.info(logSB.toString());
                        Map<String, Object> args = new HashMap<String, Object>();
                        args.put("successMessagesAsList", successMessagesAsList);
                        args.put("filterEntity", lastQueriedMarkerEntity.getFilterListAsRows());

                        Window window = (Window)Executions.createComponents(
                                "/marker_delete_successful.zul", null, args);
                        window.setPosition("center");
                        window.setClosable(true);
                        window.doModal();
                        
                        BindUtils.postGlobalCommand(null, null, "retrieveMarkerList", null);
                        
                    }

                }
            });
        }

        return successful;

    }

    private List<VMarkerSummaryEntity> checkWhichMarkersAreUsedInAMarkerGroupOrDataset(List<VMarkerSummaryEntity> selectedMarkerList) {

        int totalNumOfMarkersThatCantBeDeleted = 0;

        List<VMarkerSummaryEntity> markerIDsThatCanFreelyBeDeleted =  new ArrayList<VMarkerSummaryEntity>();
        List<MarkerDeleteResultTableEntity> markerDeleteResultTableEntityList =  new ArrayList<MarkerDeleteResultTableEntity>();

        boolean inMarkerGroup = false, inDataset = false;

        for(VMarkerSummaryEntity marker: selectedMarkerList){
            try{

                MarkerDeleteResultTableEntity markerDeleteResultTableEntity = new MarkerDeleteResultTableEntity();
                //set initial values

                inMarkerGroup = false;
                inDataset = false;
                markerDeleteResultTableEntity.setMarker_id(marker.getMarkerId());
                markerDeleteResultTableEntity.setMarker_name(marker.getMarkerName());


                //check if the marker id is being used in a dataset

                List<DatasetRecord> inDatasetList = getDatasetsThatContainThisMarkerId(marker.getMarkerId());
                if(inDatasetList.size()>0) {
                    inDataset = true;
                    markerDeleteResultTableEntity.setDataset_name(setDatasetIdDetails(inDatasetList));
                }


                //check if the marker id is being used in a marker_group
                List<MarkerGroupRecord> inMarkerGroupList = getMarkerGroupsThatContainsThisMarkerId(marker.getMarkerId());


                if(inMarkerGroupList.size()>0){
                    inMarkerGroup = true;
                    markerDeleteResultTableEntity.setMarker_group_name(setMarkerGroupDetails(inMarkerGroupList));
                }

                if(!inMarkerGroup && !inDataset){
                    markerIDsThatCanFreelyBeDeleted.add(marker);
                }else{
                    if(totalNumOfMarkersThatCantBeDeleted<10) markerDeleteResultTableEntityList.add(markerDeleteResultTableEntity);
                    totalNumOfMarkersThatCantBeDeleted++;

                }

                totalNumOfMarkersThatCantBeDeleted = totalNumOfMarkersThatCantBeDeleted-10;
            }catch(Exception e ){

                Messagebox.show("There was an error while trying to retrieve MarkerGroups", "ERROR", Messagebox.OK, Messagebox.ERROR);

            }

        }
        if(markerDeleteResultTableEntityList.size()>0){

            Map<String, Object> args = new HashMap<String, Object>();
            args.put("markerDeleteResultTableEntityList", markerDeleteResultTableEntityList);
            args.put("totalNumOfMarkersThatCantBeDeleted", totalNumOfMarkersThatCantBeDeleted);
            Window window = (Window)Executions.createComponents(
                    "/markerDeleteWarning.zul", null, args);
            window.doModal();
        }
        return markerIDsThatCanFreelyBeDeleted;
    }

    private String setDatasetIdDetails(List<DatasetRecord> inDatasetList) {
        // TODO Auto-generated method stub

        StringBuilder sb = new StringBuilder();
        for(DatasetRecord mgr : inDatasetList){

            if(sb.length()>0) sb.append(", ");
            sb.append(" "+mgr.getName()+" ("+mgr.getDatasetId()+")");

        }

        return(sb.toString());
    }

    private List<DatasetRecord> getDatasetsThatContainThisMarkerId(Integer markerId) {
        // TODO Auto-generated method stub

        List<DatasetRecord> datasetList = null;
        DSLContext context = getDSLContext();
        String query = "select dataset_id, name from dataset where dataset_id in (select key::integer from jsonb_each_text((select dataset_marker_idx from marker where marker_id="+markerId+")));";
        datasetList = context.fetch(query).into(DatasetRecord.class);

        return datasetList;
    }

    private List<MarkerGroupRecord> getMarkerGroupsThatContainsThisMarkerId(Integer markerId) {
        // TODO Auto-generated method stub

        List<MarkerGroupRecord> markerGroupList = null;
        DSLContext context = getDSLContext();
        String query = "SELECT a.marker_group_id, a.name FROM (SELECT (jsonb_each_text(markers)).*, marker_group_id, name FROM marker_group) a  where a.key='"+Integer.toString(markerId)+"';";
        markerGroupList = context.fetch(query).into(MarkerGroupRecord.class);

        return markerGroupList;
    }

    private String setMarkerGroupDetails(List<MarkerGroupRecord> markerGroupList) {

        StringBuilder sb = new StringBuilder();
        for(MarkerGroupRecord mgr : markerGroupList){

            if(sb.length()>0) sb.append(", ");

            sb.append(" "+mgr.getName()+" ("+mgr.getMarkerGroupId()+")");
        }

        return(sb.toString());

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean deleteMarkers(List<VMarkerSummaryEntity> selectedMarkerList, 
            List<DatasetSummaryEntity> markerSummary) {

        //check if Marker is not being used in a Marker Group or a Dataset
        List<VMarkerSummaryEntity> unusedInMarkersGroupsOrDataset = null;

        unusedInMarkersGroupsOrDataset = checkWhichMarkersAreUsedInAMarkerGroupOrDataset(selectedMarkerList);
        StringBuilder sb = new StringBuilder();
        for(VMarkerSummaryEntity marker : unusedInMarkersGroupsOrDataset){
            sb.append(marker.getMarkerId().toString() + "\n");
        }

        final int noOfMarkers = unusedInMarkersGroupsOrDataset.size();
        
        final List<VMarkerSummaryEntity> finalListofMarkersThatcanBeDeleted = unusedInMarkersGroupsOrDataset;
        if(noOfMarkers>0){
            // If there are markers that can be deleted 

            Messagebox.show("THIS ACTION IS NOT REVERSIBLE.\n\n"+ Integer.toString(unusedInMarkersGroupsOrDataset.size())
            + " markers can still be deleted. Do you want to continue?\n", 
            "WARNING", Messagebox.YES | Messagebox.CANCEL,
            Messagebox.EXCLAMATION,
            new org.zkoss.zk.ui.event.EventListener(){

                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        DSLContext context = getDSLContext();
                        int result = 0, markersDeleted=0;
                        double startTime = 0, endTime=0, startTimeMLG = 0, endTimeMLG=0;

                        startTimeMLG = System.currentTimeMillis();

                        result = context.deleteFrom(MARKER_LINKAGE_GROUP).where(MARKER_LINKAGE_GROUP.MARKER_ID.in(finalListofMarkersThatcanBeDeleted
                                .stream()
                                .map(VMarkerSummaryEntity::getMarkerId)
                                .collect(Collectors.toList())))
                        .execute();

                        endTimeMLG = System.currentTimeMillis();

                        startTime = System.currentTimeMillis();
                        
                        markersDeleted = context.deleteFrom(MARKER).where(MARKER.MARKER_ID.in(finalListofMarkersThatcanBeDeleted
                                .stream()
                                .map(VMarkerSummaryEntity::getMarkerId)
                                .collect(Collectors.toList())))
                        .execute();

                        endTime = System.currentTimeMillis();
                        double rowDeleteSeconds = (endTime - startTime) / 1000;
                        double rowDeleteSecondsMLG = (endTimeMLG - startTimeMLG) / 1000;

                        List<String> successMessagesAsList = new ArrayList<String>();
                        successMessagesAsList.add(Integer.toString(markersDeleted)+" markers deleted. ("+Double.toString(rowDeleteSeconds)+" sec)" );
                        successMessagesAsList.add(Integer.toString(result) +" marker_linkage_group row(s) deleted. ("+Double.toString(rowDeleteSecondsMLG)+" sec)");
                       

                        String user = getUser();

                        StringBuilder logSB = new StringBuilder();
                        logSB.append("["+user+ "] DELETED MARKERS\n\n");
                        logSB.append("["+user+"] Filtering criteria for marker delete:\n"+lastQueriedMarkerEntity.getCompleteFiltersAsText());
                        
                        logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n\n"
                                + "context.deleteFrom(MARKER_LINKAGE_GROUP).where(\n"
                                + "            MARKER_LINKAGE_GROUP.MARKER_ID.in(finalListofMarkersThatcanBeDeleted\r\n" + 
                                "            .stream()\r\n" + 
                                "            .map(VMarkerSummaryEntity::getMarkerId)\r\n" + 
                                "            .collect(Collectors.toList())))\r\n" + 
                                "            .execute();\n"+
                                "context.deleteFrom(MARKER).where(\n"
                                + "            MARKER.MARKER_ID.in(finalListofMarkersThatcanBeDeleted\r\n" + 
                                "            .stream()\r\n" + 
                                "            .map(VMarkerSummaryEntity::getMarkerId)\r\n" + 
                                "            .collect(Collectors.toList())))\r\n" + 
                                "            .execute();");
                        
                        
                        logSB.append("\n\n["+user+"] Marker delete result:\n"+ Integer.toString(markersDeleted)+" markers deleted. ("+Double.toString(rowDeleteSeconds)+" sec)\n"+Integer.toString(result) +" marker_linkage_group row(s) deleted. ("+Double.toString(rowDeleteSecondsMLG)+" sec)");

                        logSB.append("\n--------------------------------------------------");
                        log.info(logSB.toString());
                        Map<String, Object> args = new HashMap<String, Object>();
                        args.put("successMessagesAsList", successMessagesAsList);
                        args.put("filterEntity", lastQueriedMarkerEntity.getFilterListAsRows());

                        Window window = (Window)Executions.createComponents(
                                "/marker_delete_successful.zul", null, args);
                        window.setPosition("center");
                        window.setClosable(true);
                        window.doModal();
                        
//                        Messagebox.show(sb.toString(), "Successfully deleted marker!",Messagebox.OK, Messagebox.INFORMATION);

                        BindUtils.postGlobalCommand(null, null, "retrieveMarkerList", null);
                    }
                }

            });

        }

        return false;
    }

    @Override
    public List<PlatformRecord> getAllPlatforms() {

        DSLContext context = getDSLContext();
        List<PlatformRecord> platformList = null;
        try{

            platformList = context.select().from(PLATFORM).orderBy(PLATFORM.NAME).fetchInto(PlatformRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve platforms", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return platformList;
    }

    @Override
    public String getDatawarehouseVersion() {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();

        String version = "";
        try{
            version = context.fetchOne("select value from gobiiprop where type_id in (select cvid from getCvId('version','gobii_datawarehouse', 1));").into(String.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve datasets", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return version;
    }

    @Override
    public List<VLinkageGroupSummaryEntity> getAllLinkageGroups(LinkageGroupSummaryEntity linkageGroupSummaryEntity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<VLinkageGroupSummaryEntity> getAllLinkageGroupsBasedOnQuery(LinkageGroupEntity linkageGroupEntity,
            LinkageGroupSummaryEntity linkageGroupSummaryEntity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteLinkageGroup(VLinkageGroupSummaryEntity vLinkageGroupSummaryEntity,
            List<LinkageGroupSummaryEntity> linkageGroupSummary, LinkageGroupSummaryEntity linkageGroupSummaryEntity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteLinkageGroups(List<VLinkageGroupSummaryEntity> selectedDsList,
            List<LinkageGroupSummaryEntity> linkageGroupSummary, LinkageGroupSummaryEntity linkageGroupSummaryEntity) {
        // TODO Auto-generated method stub
        return false;
    }	@Override
    public List<OrganizationRecord> getAllVendors() {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<OrganizationRecord> vendorList = null;
        try{

            vendorList = context.select().from(ORGANIZATION).orderBy(ORGANIZATION.NAME).fetchInto(OrganizationRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve ORGANIZATIONS", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return vendorList;

    }

    @Override
    public List<VendorProtocolRecord> getAllVendorProtocols() {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<VendorProtocolRecord> vendorProtocolList = null;
        try{

            vendorProtocolList = context.select().from(VENDOR_PROTOCOL).orderBy(VENDOR_PROTOCOL.NAME).fetchInto(VendorProtocolRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve VENDOR-PROTOCOLS", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return vendorProtocolList;
    }

    @Override
    public List<AnalysisRecord> getAllAnalyses() {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<AnalysisRecord> analysisList = new ArrayList<AnalysisRecord>();
        try{

            analysisList = context.select().from(ANALYSIS).orderBy(ANALYSIS.NAME).fetchInto(AnalysisRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve CALLING ANALYSIS", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return analysisList;
    }

    @Override
    public List<AnalysisRecord> getAllCallingAnalysis() {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<AnalysisRecord> analysisList = null;
        try{

            analysisList = context.select().from(ANALYSIS).where(ANALYSIS.ANALYSIS_ID.in(context.selectDistinct(DATASET.CALLINGANALYSIS_ID).from(DATASET))).fetchInto(AnalysisRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve ANALYSIS", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return analysisList;
    }

    @Override
    public List<ProjectRecord> getAllProjects() {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<ProjectRecord> projectList = null;
        try{

            projectList = context.select().from(PROJECT).orderBy(PROJECT.NAME).fetchInto(ProjectRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve PROJECT", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return projectList;
    }

    @Override
    public List<ExperimentRecord> getAllExperiments() {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<ExperimentRecord> experimentList = null;
        try{

            experimentList = context.select().from(EXPERIMENT).orderBy(EXPERIMENT.NAME).fetchInto(ExperimentRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve EXPERIMENT", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return experimentList;
    }

    @Override
    public List<MapsetRecord> getAllMapsets() {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<MapsetRecord> mapsetList = null;
        try{

            mapsetList = context.select().from(MAPSET).orderBy(MAPSET.NAME).fetchInto(MapsetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve MAPSET", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return mapsetList;
    }

    @Override
    public List<LinkageGroupRecord> getAllLinkageGroups() {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<LinkageGroupRecord> linkageGroupList = null;
        try{

            linkageGroupList = context.select().from(LINKAGE_GROUP).orderBy(LINKAGE_GROUP.NAME).fetchInto(LinkageGroupRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve LINKAGE_GROUP", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return linkageGroupList;
    }

    @Override
    public List<DatasetRecord> getAllDatasets() {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<DatasetRecord> datasetList = null;
        try{

            datasetList = context.select().from(DATASET).orderBy(DATASET.NAME).fetchInto(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve DATASET", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return datasetList;
    }

    @Override
    public List<LinkageGroupRecord> getLinkageGroupsAssociatedToMarkerId(Integer markerId) {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<LinkageGroupRecord> list = null;
        try{

            String query = "select * from linkage_group where linkage_group_id in (select lg.linkage_group_id from getlinkagegroupsbymarker("+markerId.toString()+") lg)";
            list = context.fetch(query).into(LinkageGroupRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve linkage groups associated to the selected marker.", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<DatasetRecord> getDatasetAssociatedToMarkerId(Integer markerId) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<DatasetRecord> list = null;
        try{

            String query = "select * from dataset where dataset_id in (select d.dataset_id from getalldatasetsbymarker("+markerId.toString()+") d)";
            list = context.fetch(query).into(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve datasets associated to the selected marker.", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<MarkerGroupRecord> getMarkerGroupsAssociatedToMarkerId(Integer markerId) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<MarkerGroupRecord> list = null;
        try{

            String query = "select * from marker_group where marker_group_id in (select mg.marker_group_id from getmarkergroupsbymarker("+markerId.toString()+") mg)";
            list = context.fetch(query).into(MarkerGroupRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve marker groups associated to the selected marker.", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();
        }

        return list;
    }


    public <T> String getIDsToString( List<T> list) {
        if(list.isEmpty()) return null;
        StringBuilder sb = new StringBuilder();

        int ctr=0;
        for(T item : list) {
            if(ctr>0)sb.append(",");
            sb.append(((Record) item).get(0).toString());
            ctr++;
        }

        if(list.size()>1) {
            sb.append(") ");
            sb.insert(0, " in (");
        }else sb.insert(0, " = ");

        return sb.toString();
    }

    @Override
    public List<VendorProtocolRecord> getVendorProtocolByPlatformId(List<PlatformRecord> iDlist) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<VendorProtocolRecord> list = null;
        try{


            String query = "select * from vendor_protocol vp left join protocol pr on vp.protocol_id = pr.protocol_id left join platform p on pr.platform_id = p.platform_id where p.platform_id "+ getIDsToString(iDlist)+";";
            list = context.fetch(query).into(VendorProtocolRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getVendorProtocolByPlatformId", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return list;
    }

    //	@Override
    //	public List<MapsetRecord> getMapsetsByPlatformTypeId(List<PlatformRecord> platformList) {
    //		// TODO Auto-generated method stub
    //		
    //		DSLContext context = getDSLContext();
    //		List<MapsetRecord> list = null;
    //		try{
    //
    //
    //			String query = "Select * from mapset map left join platform p on map.type_id = p.type_id where p.platform_id "+ getIDsToString(platformList)+";";
    //			list = context.fetch(query).into(MapsetRecord.class);
    //
    //		}catch(Exception e ){
    //
    //			Messagebox.show("There was an error while trying to retrieve MAPSETS", "ERROR", Messagebox.OK, Messagebox.ERROR);
    //
    //		}
    //
    //		return list;
    //	}

    @Override
    public List<LinkageGroupRecord> getLinkageGroupByMapsetId(List<MapsetRecord> mapsetList) {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<LinkageGroupRecord> list = null;
        try{


            String query = "Select * from linkage_group lg left join mapset map on lg.map_id = map.mapset_id where map.mapset_id "+ getIDsToString(mapsetList)+";";
            list = context.fetch(query).into(LinkageGroupRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getLinkageGroupByMapsetId", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<ProjectRecord> getProjectsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<ProjectRecord> list = null;
        try{

            String query = "Select distinct on (prj.project_id) * from project prj left join experiment e on prj.project_id = e.project_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id where vp.vendor_protocol_id "+ getIDsToString(vendorProtocolList)+";";
            list = context.fetch(query).into(ProjectRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getProjectsByVendorProtocolID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<ProjectRecord> getProjectsByPlatformID(List<PlatformRecord> platformList) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<ProjectRecord> list = null;
        try{

            String query = "Select distinct on (prj.project_id) * from project prj left join experiment e on prj.project_id = e.project_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id left join protocol pr on vp.protocol_id = pr.protocol_id left join platform pl on pr.platform_id = pl.platform_id where pl.platform_id "+ getIDsToString(platformList)+";";
            list = context.fetch(query).into(ProjectRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getProjectsByPlatformID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }
    //
    @Override
    public List<ExperimentRecord> getExperimentsByProjectID(List<ProjectRecord> projectList) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<ExperimentRecord> list = null;
        try{

            String query = "Select * from experiment e left join project prj on e.project_id = prj.project_id where prj.project_id "+ getIDsToString(projectList)+";";
            list = context.fetch(query).into(ExperimentRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getExperimentsByProjectID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<ExperimentRecord> getExperimentsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<ExperimentRecord> list = null;
        try{

            String query = "Select distinct on (e.experiment_id) * from experiment e left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id where vp.vendor_protocol_id "+ getIDsToString(vendorProtocolList)+";";
            list = context.fetch(query).into(ExperimentRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getExperimentsByVendorProtocolID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<ExperimentRecord> getExperimentsByPlatformID(List<PlatformRecord> platformList) {
        // TODO Auto-generated method stub


        DSLContext context = getDSLContext();
        List<ExperimentRecord> list = null;
        try{

            String query = "Select distinct on (e.experiment_id) * from experiment e left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id left join protocol pr on vp.protocol_id = pr.protocol_id left join platform pl on pr.platform_id = pl.platform_id where pl.platform_id "+ getIDsToString(platformList)+";";
            list = context.fetch(query).into(ExperimentRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getExperimentsByPlatformID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<DatasetRecord> getDatasetsByProjectID(List<ProjectRecord> projectList) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<DatasetRecord> list = null;
        try{

            String query = "Select * from dataset d left join experiment e on d.experiment_id = e.experiment_id left join project prj on e.project_id = prj.project_id where prj.project_id "+ getIDsToString(projectList)+";";
            list = context.fetch(query).into(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getDatasetsByProjectID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<DatasetRecord> getDatasetsByExperimentID(List<ExperimentRecord> experimentList) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<DatasetRecord> list = null;
        try{

            String query = "Select * from dataset d left join experiment e on d.experiment_id = e.experiment_id left join project prj on e.project_id = prj.project_id where e.experiment_id "+ getIDsToString(experimentList)+";";
            list = context.fetch(query).into(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getDatasetsByExperimentID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<DatasetRecord> getDatasetsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<DatasetRecord> list = null;
        try{

            String query = "Select distinct on (d.dataset_id) * from dataset d left join experiment e on d.experiment_id = e.experiment_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id where vp.vendor_protocol_id "+ getIDsToString(vendorProtocolList)+";";
            list = context.fetch(query).into(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getDatasetsByVendorProtocolID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<DatasetRecord> getDatasetsByPlatformID(List<PlatformRecord> platformList) {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<DatasetRecord> list = null;
        try{

            String query = "Select distinct on (d.dataset_id) * from dataset d left join experiment e on d.experiment_id = e.experiment_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id left join protocol pr on vp.protocol_id = pr.protocol_id left join platform pl on pr.platform_id = pl.platform_id where pl.platform_id "+ getIDsToString(platformList)+";";
            list = context.fetch(query).into(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getDatasetsByPlatformID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<ReferenceRecord> getAllReferences() {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<ReferenceRecord> list = null;
        try{

            list = context.select().from(REFERENCE).orderBy(REFERENCE.NAME).fetchInto(ReferenceRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve references", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return list;
    }

    @Override
    public List<MapsetRecord> getAllMapsetsByReferenceId(List<ReferenceRecord> referenceList) {
        // TODO Auto-generated method stub

        DSLContext context = getDSLContext();
        List<MapsetRecord> list = null;
        try{

            String query = "Select distinct on (m.mapset_id) * from mapset m left join reference r on m.reference_id = r.reference_id where r.reference_id "+ getIDsToString(referenceList)+";";
            list = context.fetch(query).into(MapsetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getDatasetsByPlatformID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<LinkageGroupRecord> getAllLinkageGroupsByReferenceId(List<ReferenceRecord> referenceList) {

        DSLContext context = getDSLContext();
        List<LinkageGroupRecord> list = null;
        try{

            String query = "Select distinct on (lg.linkage_group_id) * from linkage_group lg left join mapset m on lg.map_id = m.mapset_id left join reference r on m.reference_id = r.reference_id where r.reference_id "+ getIDsToString(referenceList)+";";
            list = context.fetch(query).into(LinkageGroupRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getDatasetsByPlatformID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<DatasetRecord> getDatasetsByExperimentIDandAnalysisId(List<ExperimentRecord> experimentList,
            List<AnalysisRecord> analysisList) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<DatasetRecord> list = null;
        try{

            String query = "Select * from dataset d left join experiment e on d.experiment_id = e.experiment_id left join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) where e.experiment_id "+ getIDsToString(experimentList)+" and a.analysis_id "+ getIDsToString(analysisList)+";";
            list = context.fetch(query).into(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getDatasetsByExperimentIDandAnalysisId", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<DatasetRecord> getDatasetsByPlatformIDandAnalysisID(List<PlatformRecord> platformList,
            List<AnalysisRecord> analysisList) {
        DSLContext context = getDSLContext();
        List<DatasetRecord> list = null;
        try{

            String query = "Select distinct on (d.dataset_id) * from dataset d left join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) left join experiment e on d.experiment_id = e.experiment_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id left join protocol pr on vp.protocol_id = pr.protocol_id left join platform pl on pr.platform_id = pl.platform_id where pl.platform_id "+ getIDsToString(platformList)+" and a.analysis_id "+ getIDsToString(analysisList)+";";
            list = context.fetch(query).into(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getDatasetsByPlatformIDandAnalysisID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<DatasetRecord> getAllDatasetsByAnalysisID(List<AnalysisRecord> analysesList) {
        DSLContext context = getDSLContext();

        List<DatasetRecord> list = null;
        try{
            String query = "Select distinct on (d.dataset_id) * from dataset d left join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) where a.analysis_id "+ getIDsToString(analysesList)+";";
            list = context.fetch(query).into(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve datasets by analysis Id", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return list;
    }

    @Override
    public List<DatasetRecord> getDatasetsByVendorProtocolIDandAnalysisID(List<VendorProtocolRecord> vendorProtocolList,
            List<AnalysisRecord> analysesList) {

        DSLContext context = getDSLContext();
        List<DatasetRecord> list = null;
        try{

            String query = "Select distinct on (d.dataset_id) * from dataset d join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) left join experiment e on d.experiment_id = e.experiment_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id where vp.vendor_protocol_id "+ getIDsToString(vendorProtocolList)+" and a.analysis_id "+ getIDsToString(analysesList)+";";
            list = context.fetch(query).into(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getDatasetsByVendorProtocolIDandAnalysisID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<DatasetRecord> getDatasetsByProjectIDandAnalysisID(List<ProjectRecord> projectList,
            List<AnalysisRecord> analysesList) {
        DSLContext context = getDSLContext();
        List<DatasetRecord> list = null;
        try{

            String query = "Select * from dataset d join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) left join experiment e on d.experiment_id = e.experiment_id left join project prj on e.project_id = prj.project_id where prj.project_id "+ getIDsToString(projectList)+" and a.analysis_id "+ getIDsToString(analysesList)+";";
            list = context.fetch(query).into(DatasetRecord.class);

        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve getDatasetsByProjectIDandAnalysisID", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<MarkerDetailDatasetEntity> getMarkerAssociatedDetailsForEachDataset(
            List<DatasetRecord> datasetList) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<MarkerDetailDatasetEntity> list = new ArrayList<MarkerDetailDatasetEntity>();
        
        try{
         if(datasetList.size()>0) {
        StringBuilder sb = new StringBuilder();
        sb.append("select d.dataset_id as dataset_id, d.name as dataset_name, d.callinganalysis_id as calling_analysis, d.analyses as analyses, e.experiment_id as experiment_id, e.name as experiment_name, p.project_id as project_id, p.name as project_name, vp.vendor_protocol_id as vp_id, vp.name as vp_name from dataset d ");
        sb.append("left join experiment e on d.experiment_id = e.experiment_id left join project p on e.project_id = p.project_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id where d.dataset_id "+ getIDsToString(datasetList));
        
        String query = sb.toString();
        list = context.fetch(query).into(MarkerDetailDatasetEntity.class);
         }
        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve marker details by dataset", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<MarkerDetailLinkageGroupEntity> getAssociatedDetailsForEachLinkageGroup(
            List<LinkageGroupRecord> markerDetailLinkageGroupList) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<MarkerDetailLinkageGroupEntity> list = new ArrayList<MarkerDetailLinkageGroupEntity>();
        
        try{
         if(markerDetailLinkageGroupList.size()>0) {
             
        StringBuilder sb = new StringBuilder();
        sb.append("select lg.linkage_group_id as lg_id, lg.name as lg_name, m.mapset_id as map_id, m.name as map_name from linkage_group lg ");
        sb.append("left join mapset m on lg.map_id = m.mapset_id where lg.linkage_group_id "+ getIDsToString(markerDetailLinkageGroupList));
        
        String query = sb.toString();
        list = context.fetch(query).into(MarkerDetailLinkageGroupEntity.class);
         }
        }catch(Exception e ){

            Messagebox.show("There was an error while trying to retrieve marker details by linkage group", "ERROR", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<VMarkerSummaryEntity> getAllMarkersBasedOnQueryViaView(MarkerRecordEntity markerEntity,
            DatasetSummaryEntity markerSummaryEntity) {
        // TODO Auto-generated method stub

        lastQueriedMarkerEntity = new MarkerRecordEntity();
        int queryCount =0;
        int dsNameCount = 0;
        DSLContext context = getDSLContext();

        List<VMarkerSummaryEntity> markerList = null;

        try{ /* START building THE QUERY via StringBuilder */
            StringBuilder sb = new StringBuilder();
            StringBuilder sbWhere = new StringBuilder(); 


               sb.append("select * from v_marker_summary vms "); 
            
            /* ADD THE "WHERE" CONDITIONS */

            // build query for MARKER NAMES filter
            if (markerEntity.getMarkerNamesAsCommaSeparatedString()!=null && !markerEntity.getMarkerNamesAsCommaSeparatedString().isEmpty()){
                lastQueriedMarkerEntity.setMarkerNamesAsCommaSeparatedString(markerEntity.getMarkerNamesAsCommaSeparatedString());
                sbWhere.append(" where LOWER(vms.marker_name) in ("+markerEntity.getSQLReadyMarkerNames()+")");
                dsNameCount++;  
            }


            //build query for 'none' selected on dataset filter
            if(markerEntity.isMarkerNotInDatasets()) {
                lastQueriedMarkerEntity.setMarkerNotInDatasets(true);
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" vms.dataset_marker_idx = '{}' ");
                queryCount++;
            }

            // build query for MAPSET filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getMapsetList())){ 
                lastQueriedMarkerEntity.getMapsetList().addAll(markerEntity.getMapsetList());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(buildQueryForGetMarkersInMapsetList(markerEntity.getMapsetList()));
                queryCount++;
            }

            // build query for LINKAGE GROUP filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getLinkageGroupList())){ 
                lastQueriedMarkerEntity.getLinkageGroupList().addAll(markerEntity.getLinkageGroupList());

                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(buildQueryForGetMarkersInLinkageGroupList(markerEntity.getLinkageGroupList()));
                queryCount++;
            }

            // build query for PLATFORM filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getPlatformList())){
                lastQueriedMarkerEntity.getPlatformList().addAll(markerEntity.getPlatformList());

                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" vms.platform_id "+ getIDsToString(markerEntity.getPlatformList()));
                queryCount++;
            }

            // build query for VENDOR-PROTOCOL filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getVendorProtocolList())){
                lastQueriedMarkerEntity.getVendorProtocolList().addAll(markerEntity.getVendorProtocolList());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(buildQueryForGetMarkersInVendorProtocol(markerEntity.getVendorProtocolList()));
                queryCount++;
            }

            // build query for PROJECTS filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getProjectList())){
                lastQueriedMarkerEntity.getProjectList().addAll(markerEntity.getProjectList());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(buildQueryForGetMarkersInProject(markerEntity.getProjectList()));
                queryCount++;
            }

            // build query for EXPERIMENTS filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getExperimentList())){
                lastQueriedMarkerEntity.getExperimentList().addAll(markerEntity.getExperimentList());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(buildQueryForGetMarkersInExperiment(markerEntity.getExperimentList()));
                queryCount++;
            }

            // build query for DATASETS filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getDatasetList()) && !markerEntity.isMarkerNotInDatasets()){
                lastQueriedMarkerEntity.getDatasetList().addAll(markerEntity.getDatasetList());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(getDatasetJsonbQueryFor(markerEntity.getDatasetList())+" ");
                queryCount++;
            }

            // build query for ANALYSES filter
            if (Utils.isListNotNullOrEmpty(markerEntity.getAnalysesList()) && !markerEntity.isMarkerNotInDatasets()){
                lastQueriedMarkerEntity.getAnalysesList().addAll(markerEntity.getAnalysesList());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append("("+buildQueryForGetMarkersInAnalysis(markerEntity.getAnalysesList()));
                sbWhere.append(" or "+buildQueryForGetMarkersInCallingAnalysis(markerEntity.getAnalysesList())+")");
                queryCount++;
            }

            // build query for given marker IDs
            if (markerEntity.getMarkerIDStartRange()!=null || markerEntity.getMarkerIDEndRange()!=null){

                //check which is not null
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);

                if(markerEntity.getMarkerIDStartRange()!=null && markerEntity.getMarkerIDEndRange()!=null){ //if both is not null
                    Integer lowerID = markerEntity.getMarkerIDStartRange();
                    Integer higherID = markerEntity.getMarkerIDEndRange();

                    if(lowerID.compareTo(higherID)>0){
                        lowerID = markerEntity.getMarkerIDEndRange();
                        higherID = markerEntity.getMarkerIDStartRange();
                    }
                    lastQueriedMarkerEntity.setMarkerIDEndRange(markerEntity.getMarkerIDEndRange());
                    lastQueriedMarkerEntity.setMarkerIDStartRange(lastQueriedMarkerEntity.getMarkerIDStartRange());
                    sbWhere.append(" vms.marker_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
                }else{
                    Integer ID = null;
                    if(markerEntity.getMarkerIDStartRange()!=null) {
                        ID = markerEntity.getMarkerIDStartRange();
                        lastQueriedMarkerEntity.setMarkerIDStartRange(lastQueriedMarkerEntity.getMarkerIDStartRange());
                    }
                    else {
                        lastQueriedMarkerEntity.setMarkerIDEndRange(markerEntity.getMarkerIDEndRange());
                        ID = markerEntity.getMarkerIDEndRange();
                    }

                    sbWhere.append(" vms.marker_id = "+Integer.toString(ID));
                }

                queryCount++;
            }
            
            
            if(sbWhere.length()>0) {
                    sbWhere.append(";");
                    sb.append(sbWhere.toString());
                    String query = sb.toString();
                    markerList = context.fetch(query).into(VMarkerSummaryEntity.class);
            }
            else {
                Messagebox.show("Please specify filters", "There is nothing selected", Messagebox.OK, Messagebox.EXCLAMATION);
            }
           

        }catch(Exception e ){
            e.printStackTrace();
            Messagebox.show("There was an error while trying to retrieve markers", "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return markerList;
    }
    
    private Object buildQueryForGetMarkersInCallingAnalysis(List<AnalysisRecord> analysesList) {
        StringBuilder sb = new StringBuilder();
        for(AnalysisRecord record : analysesList) {
            if(sb.length()>0)sb.append(" or ");
            sb.append("vms.marker_id in (select marker_id from getMarkersInCallingAnalysis("+record.getAnalysisId()+"))");
        }
        
        sb.append(") ");
        sb.insert(0, "(");
        
        return sb.toString();
    }

    private Object buildQueryForGetMarkersInAnalysis(List<AnalysisRecord> analysesList) {
        StringBuilder sb = new StringBuilder();
        for(AnalysisRecord record : analysesList) {
            if(sb.length()>0)sb.append(" or ");
            sb.append("vms.marker_id in (select marker_id from getMarkersInAnalysis("+record.getAnalysisId()+"))");
        }
        
        sb.append(") ");
        sb.insert(0, "(");
        
        return sb.toString();
    }

    private Object buildQueryForGetMarkersInExperiment(List<ExperimentRecord> experimentList) {
        StringBuilder sb = new StringBuilder();
        for(ExperimentRecord record : experimentList) {
            if(sb.length()>0)sb.append(" or ");
            sb.append("vms.marker_id in (select marker_id from getMarkersInExperiment("+record.getExperimentId()+"))");
        }
        
        sb.append(") ");
        sb.insert(0, "(");
        
        return sb.toString();
    }

    private Object buildQueryForGetMarkersInProject(List<ProjectRecord> projectList) {
        StringBuilder sb = new StringBuilder();
        for(ProjectRecord record : projectList) {
            if(sb.length()>0)sb.append(" or ");
            sb.append("vms.marker_id in (select marker_id from getMarkersInProject("+record.getProjectId()+"))");
        }
        
        sb.append(") ");
        sb.insert(0, "(");
        
        return sb.toString();
    }

    private Object buildQueryForGetMarkersInVendorProtocol(List<VendorProtocolRecord> vendorProtocolList) {
        StringBuilder sb = new StringBuilder();
        for(VendorProtocolRecord record : vendorProtocolList) {
            if(sb.length()>0)sb.append(" or ");
            sb.append("vms.marker_id in (select marker_id from getMarkersInVendorProtocol("+record.getVendorProtocolId()+"))");
        }
        
        sb.append(") ");
        sb.insert(0, "(");
        
        return sb.toString();
    }

    private Object buildQueryForGetMarkersInLinkageGroupList(List<LinkageGroupRecord> linkageGroupList) {
        StringBuilder sb = new StringBuilder();
        for(LinkageGroupRecord lg : linkageGroupList) {
            if(sb.length()>0)sb.append(" or ");
            sb.append("vms.marker_id in (select marker_id from getMarkersInLinkageGroup("+lg.getLinkageGroupId()+"))");
        }
        
        sb.append(") ");
        sb.insert(0, "(");
        
        return sb.toString();
    } 

    private String buildQueryForGetMarkersInMapsetList(List<MapsetRecord> mapsetList) {
       StringBuilder sb = new StringBuilder();
        for(MapsetRecord mr : mapsetList) {
            if(sb.length()>0)sb.append(" or ");
            sb.append("vms.marker_id in (select marker_id from getMarkersInMapset("+mr.getMapsetId()+"))");
        }
        
        sb.append(") ");
        sb.insert(0, "(");
        
        return sb.toString();
    }

    public <T> String getDatasetJsonbQueryFor(List<T> list) {
        if(list.isEmpty()) return null;
        StringBuilder sb = new StringBuilder();

        int ctr=0;
        for(T item : list) {
            if(ctr>0)sb.append(",");
            sb.append("'"+((Record) item).get(0).toString()+"'");
            ctr++;
        }

        if(list.size()>1) {
            sb.append("])");
            sb.insert(0, " jsonb_exists_any(dataset_marker_idx, array[");
        }else{
            sb.insert(0, " jsonb_exists(dataset_marker_idx, ");
            sb.append(")");
        }

        return sb.toString();
    }

    public String getLastDSQuery() {
        return lastDSQuery;
    }

    public void setLastDSQuery(String lastDSQuery) {
        if(lastDSQuery.isEmpty()) {
            this.lastDSQuery = " (not Filtered)";
        }
        else this.lastDSQuery = lastDSQuery;
    }

    private String getUser() {

        UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
        return cre.getAccount();
    }

    @Override
    public void getDnarunidsbyproject(int projectId) {
        // TODO Auto-generated method stub

        List<Integer> dnarunindicesList = null;
        
            DSLContext context = getDSLContext();

            String query = "select * from getdnarunidsbyproject("+ Integer.toString(projectId) +");";
            dnarunindicesList = context.fetch(query).into(Integer.class);    
       
        
        System.out.println(Integer.toString(dnarunindicesList.size()));
    }

    @Override
    public List<DnarunViewEntity> getAllDnaruns() {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<DnarunViewEntity> list = null;
        try{

            list = context.fetch("SELECT distinct on (dr.dnarun_id) dr.dnarun_id AS dnarunId, dr.name AS dnarunName, ds.dnasample_id AS dnasampleId, ds.name AS dnasampleName, ds.uuid as uuid, g.germplasm_id as germplasmId, g.name as germplasmName, e.experiment_id AS experimentId, e.name AS experimentName, p.project_id as projectId, p.name AS projectName FROM dnarun dr LEFT JOIN dnasample ds ON dr.dnasample_id = ds.dnasample_id LEFT JOIN experiment e ON dr.experiment_id = e.experiment_id LEFT JOIN project p ON e.project_id = p.project_id  LEFT JOIN germplasm g ON ds.germplasm_id=g.germplasm_id ").into(DnarunViewEntity.class);

        }catch(Exception e ){
            e.printStackTrace();

            Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return list;
    }

    @Override
    public List<DnarunViewEntity> getAllDnarunsBasedOnQuery(DnarunEntity dnarunEntity) {
        // TODO Auto-generated method stub
        List<DnarunViewEntity> list = null;
        
        lastQueriedDNArunEntity = new DnarunEntity();
        
        int queryCount =0;
        int dsNameCount = 0;
        DSLContext context = getDSLContext();

        try{ /* START building THE QUERY via StringBuilder */
            StringBuilder sb = new StringBuilder();
            StringBuilder sbWhere = new StringBuilder(); 

            sb.append("SELECT distinct on (dr.dnarun_id) dr.dnarun_id AS dnarunId, dr.name AS dnarunName, ds.dnasample_id AS dnasampleId, ds.name AS dnasampleName, ds.uuid as uuid, g.germplasm_id as germplasmId, g.name as germplasmName, e.experiment_id AS experimentId, e.name AS experimentName, p.project_id as projectId, p.name AS projectName FROM dnarun dr LEFT JOIN dnasample ds ON dr.dnasample_id = ds.dnasample_id LEFT JOIN experiment e ON dr.experiment_id = e.experiment_id LEFT JOIN project p ON e.project_id = p.project_id  LEFT JOIN germplasm g ON ds.germplasm_id=g.germplasm_id ");

          

            // build query for DNA RUN NAMES filter
            if (dnarunEntity.getDnarunNamesAsEnterSeparatedString()!=null && !dnarunEntity.getDnarunNamesAsEnterSeparatedString().isEmpty()){
                lastQueriedDNArunEntity.setDnarunNamesAsCommaSeparatedString(dnarunEntity.getSQLReadyDnarunNames());

                sbWhere.append(" where LOWER(dr.name) in ("+dnarunEntity.getSQLReadyDnarunNames()+")");
                dsNameCount++;  
            } else if (dnarunEntity.getDnarunIDStartRange()!=null || dnarunEntity.getDnarunIDEndRange()!=null){ // build query for given DNArun IDs

                //check which is not null
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);

                if(dnarunEntity.getDnarunIDStartRange()!=null && dnarunEntity.getDnarunIDEndRange()!=null){ //if both is not null
                    Integer lowerID = dnarunEntity.getDnarunIDStartRange();
                    Integer higherID = dnarunEntity.getDnarunIDEndRange();

                    if(lowerID.compareTo(higherID)>0){
                        lowerID = dnarunEntity.getDnarunIDEndRange();
                        higherID = dnarunEntity.getDnarunIDStartRange();
                    }
                    lastQueriedDNArunEntity.setDnarunIDEndRange(dnarunEntity.getDnarunIDEndRange());
                    lastQueriedDNArunEntity.setDnarunIDStartRange(dnarunEntity.getDnarunIDStartRange());
                    sbWhere.append(" dr.dnarun_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
                }else{
                    Integer ID = null;
                    if(dnarunEntity.getDnarunIDStartRange()!=null) {
                        ID = dnarunEntity.getDnarunIDStartRange();
                        lastQueriedDNArunEntity.setDnarunIDStartRange(dnarunEntity.getDnarunIDStartRange());
                    }
                    else {
                        lastQueriedDNArunEntity.setDnarunIDEndRange(dnarunEntity.getDnarunIDEndRange());
                        ID = dnarunEntity.getDnarunIDEndRange();
                    }

                    sbWhere.append(" dr.dnarun_id = "+Integer.toString(ID));
                }

                queryCount++;
            } // END build query for given DNArun IDs

            // build query for DNA Sample NAMES filter
            if (dnarunEntity.getDnasampleNamesAsEnterSeparatedString()!=null && !dnarunEntity.getDnasampleNamesAsEnterSeparatedString().isEmpty()){
                lastQueriedDNArunEntity.setDnasampleNamesAsCommaSeparatedString(dnarunEntity.getSQLReadyDnasampleNames());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                
                  sbWhere.append(" LOWER(ds.name) in ("+dnarunEntity.getSQLReadyDnasampleNames()+")");
                  dsNameCount++;  
            } else if (dnarunEntity.getDnasampleIDStartRange()!=null || dnarunEntity.getDnasampleIDEndRange()!=null){ // build query for given DNAsample IDs

                    //check which is not null
                    checkPreviousAppends(dsNameCount, queryCount, sbWhere);

                    if(dnarunEntity.getDnasampleIDStartRange()!=null && dnarunEntity.getDnasampleIDEndRange()!=null){ //if both is not null
                        Integer lowerID = dnarunEntity.getDnasampleIDStartRange();
                        Integer higherID = dnarunEntity.getDnasampleIDEndRange();

                        if(lowerID.compareTo(higherID)>0){
                            lowerID = dnarunEntity.getDnasampleIDEndRange();
                            higherID = dnarunEntity.getDnasampleIDStartRange();
                        }
                        lastQueriedDNArunEntity.setDnasampleIDEndRange(dnarunEntity.getDnasampleIDEndRange());
                        lastQueriedDNArunEntity.setDnasampleIDStartRange(dnarunEntity.getDnasampleIDStartRange());
                        sbWhere.append(" dr.dnasample_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
                    }else{
                        Integer ID = null;
                        if(dnarunEntity.getDnasampleIDStartRange()!=null) {
                            ID = dnarunEntity.getDnasampleIDStartRange();
                            lastQueriedDNArunEntity.setDnasampleIDStartRange(dnarunEntity.getDnasampleIDStartRange());
                        }
                        else {
                            lastQueriedDNArunEntity.setDnasampleIDEndRange(dnarunEntity.getDnasampleIDEndRange());
                            ID = dnarunEntity.getDnasampleIDEndRange();
                        }

                        sbWhere.append(" dr.dnasample_id = "+Integer.toString(ID));
                    }

                    queryCount++;
                } // END build query for given DNAsample IDs

//             build query for germplasm NAMES filter
            if (dnarunEntity.getGermplasmNamesAsEnterSeparatedString()!=null && !dnarunEntity.getGermplasmNamesAsEnterSeparatedString().isEmpty()){
                lastQueriedDNArunEntity.setGermplasmNamesAsCommaSeparatedString(dnarunEntity.getSQLReadyGermplasmNames());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                
                sbWhere.append(" LOWER(g.name) in ("+dnarunEntity.getSQLReadyGermplasmNames()+")");
                dsNameCount++;  
            } else if (dnarunEntity.getGermplasmIDStartRange()!=null || dnarunEntity.getGermplasmIDEndRange()!=null){ // 
                //check which is not null
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);

                if(dnarunEntity.getGermplasmIDStartRange()!=null && dnarunEntity.getGermplasmIDEndRange()!=null){ //if both is not null
                    Integer lowerID = dnarunEntity.getGermplasmIDStartRange();
                    Integer higherID = dnarunEntity.getGermplasmIDEndRange();

                    if(lowerID.compareTo(higherID)>0){
                        lowerID = dnarunEntity.getGermplasmIDEndRange();
                        higherID = dnarunEntity.getGermplasmIDStartRange();
                    }

                    lastQueriedDNArunEntity.setGermplasmIDEndRange(dnarunEntity.getGermplasmIDEndRange());
                    lastQueriedDNArunEntity.setGermplasmIDStartRange(dnarunEntity.getGermplasmIDStartRange());
                    sbWhere.append(" g.germplasm_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
                }else{
                    Integer ID = null;
                    if(dnarunEntity.getGermplasmIDStartRange()!=null) {
                        ID = dnarunEntity.getGermplasmIDStartRange();
                        lastQueriedDNArunEntity.setGermplasmIDStartRange(dnarunEntity.getGermplasmIDStartRange());
                    }
                    else {
                        ID = dnarunEntity.getGermplasmIDEndRange();
                        lastQueriedDNArunEntity.setGermplasmIDEndRange(dnarunEntity.getGermplasmIDEndRange());
                    }

                    sbWhere.append(" g.germplasm_id = "+Integer.toString(ID));
                }

                queryCount++;
            } // END build query for given id names / ranges

            
            //build query for UUID
            if (dnarunEntity.getDnasampleUuidAsEnterSeparatedString()!=null && !dnarunEntity.getDnasampleUuidAsEnterSeparatedString().isEmpty()){
                lastQueriedDNArunEntity.setDnasampleUuidAsCommaSeparatedString(dnarunEntity.getSQLReadyDnasampleUuidNames());

                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" LOWER(ds.uuid) in ("+dnarunEntity.getSQLReadyDnasampleUuidNames()+")");
                dsNameCount++;  
            } 
            
            /* ADD THE "WHERE" CONDITIONS */
            
            
            // build query for PROJECTS filter
            if (Utils.isListNotNullOrEmpty(dnarunEntity.getProjectList())){
                lastQueriedDNArunEntity.getProjectList().addAll(dnarunEntity.getProjectList());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" p.project_id "+ getIDsToString(dnarunEntity.getProjectList()));
                queryCount++;
            }

//            // build query for EXPERIMENTS filter
            if (Utils.isListNotNullOrEmpty(dnarunEntity.getExperimentList())){
                lastQueriedDNArunEntity.getExperimentList().addAll(dnarunEntity.getExperimentList());
                  checkPreviousAppends(dsNameCount, queryCount, sbWhere);

                  sbWhere.append(" e.experiment_id "+ getIDsToString(dnarunEntity.getExperimentList()));
                  queryCount++;
                
            }

//            // build query for DATASETS filter

            //build query for 'none' selected on dataset filter
            if(dnarunEntity.isDnarunNotInDatasets()) {
                sb.append(" LEFT JOIN dataset d ON jsonb_exists(dr.dataset_dnarun_idx, d.dataset_id::text) ");
                lastQueriedDNArunEntity.setDnarunNotInDatasets(true);
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" dr.dataset_dnarun_idx = '{}' ");
                queryCount++;
            }
            else if (Utils.isListNotNullOrEmpty(dnarunEntity.getDatasetList())){
                lastQueriedDNArunEntity.getDatasetList().addAll(dnarunEntity.getDatasetList());
                  sb.append(" LEFT JOIN dataset d ON jsonb_exists(dr.dataset_dnarun_idx, d.dataset_id::text) ");
                  checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                  sbWhere.append(" d.dataset_id "+ getIDsToString(dnarunEntity.getDatasetList()));
                  queryCount++;
                }
            
            if(sbWhere.length()>0) {
                sbWhere.append(";");
                sb.append(sbWhere.toString());
                
                String query = sb.toString();
                System.out.println(query);
                list = context.fetch(query).into(DnarunViewEntity.class);
            } else {
                Messagebox.show("Please specify filters", "There is nothing selected", Messagebox.OK, Messagebox.EXCLAMATION);
            }
            
            
        }catch(Exception e ){
            e.printStackTrace();
            Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean deleteDnarun(DnarunViewEntity dnarunRecord) {
        boolean successful = false;
        List<DnarunViewEntity> selectedList = new ArrayList<DnarunViewEntity>();
        selectedList.add(dnarunRecord);
        
        //check if Marker is not being used in a Marker Group or a Dataset
        List<DnarunViewEntity> unusedInDataset = null;

        unusedInDataset = checkWhichDnarunsAreUsedInADataset(selectedList);
        if(unusedInDataset.size()>0){

            Messagebox.show("THIS ACTION IS NOT REVERSIBLE.\n\n Do you want to continue?\n", 
                    "WARNING", Messagebox.YES | Messagebox.CANCEL,
                    Messagebox.EXCLAMATION,
                    new org.zkoss.zk.ui.event.EventListener(){
                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        DSLContext context = getDSLContext();
                        
                        Integer id = selectedList.get(0).getDnarunId();
                        double startTime = 0, endTime=0;

                        startTime = System.currentTimeMillis();
                        
                        context.delete(DNARUN)
                        .where(DNARUN.DNARUN_ID.eq(id))
                        .execute();

                        endTime = System.currentTimeMillis();
                        double rowDeleteSeconds = (endTime - startTime) / 1000;

                        List<String> successMessagesAsList = new ArrayList<String>();
                        successMessagesAsList.add("1 DNArun deleted. ("+Double.toString(rowDeleteSeconds)+" sec)" );
                       
                        String user = getUser();
                        StringBuilder logSB = new StringBuilder();
                        logSB.append("["+user+ "] DELETED A DNARUN\n\n"); 
                        logSB.append("["+user+ "] Filtering criteria for DNArun delete:\n"+lastQueriedDNArunEntity.getCompleteFiltersAsText());
                        logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n\n"+
                                "context.delete(DNARUN).where(\n"
                                + "            DNARUN.DNARUN_ID.eq("+id.toString()+"))\n"
                                        + "            .execute();");
          
                        logSB.append("\n\n["+user+"] DNArun delete result:\n"+ "1 DNArun deleted. ("+Double.toString(rowDeleteSeconds)+" sec)");

                        logSB.append("\n--------------------------------------------------");
                        log.info(logSB.toString());
                        Map<String, Object> args = new HashMap<String, Object>();
                        args.put("successMessagesAsList", successMessagesAsList);
                        args.put("filterEntity", lastQueriedDNArunEntity.getFilterListAsRows());

                        Window window = (Window)Executions.createComponents(
                                "/marker_delete_successful.zul", null, args);
                        window.setPosition("center");
                        window.setClosable(true);
                        window.doModal();
                        
                        BindUtils.postGlobalCommand(null, null, "retrieveDNArunList", null);
                        
                    }

                }
            });
        }

        return successful;

    }

    private List<DnarunViewEntity> checkWhichDnarunsAreUsedInADataset(List<DnarunViewEntity> selectedList) {
        // TODO Auto-generated method stub

        int totalNoOfCantBeDeleted = 0;

        List<DnarunViewEntity> listOfCanBeDeleted =  new ArrayList<DnarunViewEntity>();
        List<DnarunDeleteResultTableEntity> dnarunDeleteResultTableEntitList =  new ArrayList<DnarunDeleteResultTableEntity>();

        boolean inDataset = false;

        for(DnarunViewEntity dnarun: selectedList){
            try{

                DnarunDeleteResultTableEntity dnarunDeleteResultTableEntity= new DnarunDeleteResultTableEntity();
                //set initial values

                inDataset = false;
                dnarunDeleteResultTableEntity.setDnarun_id(dnarun.getDnarunId());
                dnarunDeleteResultTableEntity.setDnarun_name(dnarun.getDnarunName());


                //check if the marker id is being used in a dataset

                List<DatasetRecord> inDatasetList = getDatasetAssociatedToDnarunId(dnarun.getDnarunId());
                if(inDatasetList.size()>0) {
                    inDataset = true;
                    dnarunDeleteResultTableEntity.setDataset_name(setDatasetIdDetails(inDatasetList));
                }

                if(!inDataset){
                    listOfCanBeDeleted.add(dnarun);
                }else{
                    if(totalNoOfCantBeDeleted<10) dnarunDeleteResultTableEntitList.add(dnarunDeleteResultTableEntity);
                    totalNoOfCantBeDeleted++;

                }

                totalNoOfCantBeDeleted = totalNoOfCantBeDeleted-10;
            }catch(Exception e ){

                Messagebox.show("There was an error while trying to retrieve Dnaruns", "ERROR", Messagebox.OK, Messagebox.ERROR);

            }

        }
        if(dnarunDeleteResultTableEntitList.size()>0){

            Map<String, Object> args = new HashMap<String, Object>();
            args.put("dnarunDeleteResultTableEntityList", dnarunDeleteResultTableEntitList);
            args.put("totalNumOfDnarunsThatCantBeDeleted", totalNoOfCantBeDeleted);
            Window window = (Window)Executions.createComponents(
                    "/dnarunDeleteWarning.zul", null, args);
            window.doModal();
        }
        return listOfCanBeDeleted;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean deleteDnaruns(List<DnarunViewEntity> selectedList) {
        
        boolean successful = false;

        //check if Marker is not being used in a Marker Group or a Dataset
        List<DnarunViewEntity> unusedInDataset = null;

        unusedInDataset = checkWhichDnarunsAreUsedInADataset(selectedList);
        String warningMessage = Integer.toString(unusedInDataset.size()) + 
                " dnarun(s) can still be deleted. Do you want to continue?\n";
        
        if(unusedInDataset.size() == selectedList.size()) {warningMessage ="Do you want to continue?\n";
        }  

        final List<DnarunViewEntity> finalListThatcanBeDeleted = unusedInDataset;
            
        if(unusedInDataset.size()>0){

            Messagebox.show("THIS ACTION IS NOT REVERSIBLE.\n\n "+ warningMessage, 
                    "WARNING", Messagebox.YES | Messagebox.CANCEL,
                    Messagebox.EXCLAMATION,
                    new org.zkoss.zk.ui.event.EventListener(){
                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        DSLContext context = getDSLContext();
                        
                        int dnarunsDeleted = 0;
                        double startTime = 0, endTime=0;

                        startTime = System.currentTimeMillis();
                        
                        dnarunsDeleted = context.deleteFrom(DNARUN)
                        .where(DNARUN.DNARUN_ID.in(finalListThatcanBeDeleted
                                .stream()
                                .map(DnarunViewEntity::getDnarunId)
                                .collect(Collectors.toList())))
                        .execute();

                        endTime = System.currentTimeMillis();
                        double rowDeleteSeconds = (endTime - startTime) / 1000;

                        List<String> successMessagesAsList = new ArrayList<String>();
                        successMessagesAsList.add(Integer.toString(dnarunsDeleted)+"  DNArun(s) deleted. ("+Double.toString(rowDeleteSeconds)+" sec)" );
                       
                        String user = getUser();
                        StringBuilder logSB = new StringBuilder();
                        logSB.append("["+user+ "] DELETED DNARUN(S)\n\n"); 
                        logSB.append("["+user+ "] Filtering criteria for DNArun delete:\n"+lastQueriedDNArunEntity.getCompleteFiltersAsText());
                        logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n\n"+
                                "context.deleteFrom(DNARUN).where(\n"
                                + "            DNARUN.DNARUN_ID.in(finalListThatcanBeDeleted.stream().map(DnarunViewEntity::getDnarunId).collect(Collectors.toList()))))\n"
                                        + "            .execute();");
          
                        logSB.append("\n\n["+user+"] DNArun delete result:\n"+ Integer.toString(dnarunsDeleted)+" DNArun(s) deleted. ("+Double.toString(rowDeleteSeconds)+" sec)");

                        logSB.append("\n--------------------------------------------------");
                        log.info(logSB.toString());
                        Map<String, Object> args = new HashMap<String, Object>();
                        args.put("successMessagesAsList", successMessagesAsList);
                        args.put("filterEntity", lastQueriedDNArunEntity.getFilterListAsRows());

                        Window window = (Window)Executions.createComponents(
                                "/marker_delete_successful.zul", null, args);
                        window.setPosition("center");
                        window.setClosable(true);
                        window.doModal();
                        
                        BindUtils.postGlobalCommand(null, null, "retrieveDNArunList", null);
                        
                    }

                }
            });
        }

        return successful;
    }

    @Override
    public List<DatasetRecord> getDatasetAssociatedToDnarunId(Integer dnarunId) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<DatasetRecord> list = null;
        try{
            String query = "SELECT distinct on (d.dataset_id) d.dataset_id, d.experiment_id, d.callinganalysis_id, d.analyses, d.data_table, d.data_file, d.quality_table, d.quality_file, d.scores, d.created_by, d.created_date,d.modified_by, d.modified_date, d.status, d.type_id, d.name, d.job_id FROM dnarun dr LEFT JOIN dataset d ON jsonb_exists(dr.dataset_dnarun_idx, d.dataset_id::text) where dr.dnarun_id="+dnarunId.toString();
            System.out.println(query);
            list = context.fetch( query).into(DatasetRecord.class);


            if(list.get(0).getDatasetId()==null){
                list = new ArrayList<DatasetRecord>();
            }
        }catch(Exception e ){
            list = new ArrayList<DatasetRecord>();
            e.printStackTrace();

            Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return list;
    }

    @Override
    public List<DnasampleViewEntity> getAllDnasamples() {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<DnasampleViewEntity> list = null;
        try{

            list = context.fetch("SELECT distinct on (ds.dnasample_id) ds.dnasample_id AS dnasampleId, ds.name AS dnasampleName, ds.code as code, ds.platename as plateName, ds.well_row as wellRow, ds.well_col as wellCol, ds.uuid as uuid, g.germplasm_id as germplasmId, g.name as germplasmName, p.project_id as projectId, p.name AS projectName FROM dnasample ds LEFT JOIN project p ON ds.project_id = p.project_id  LEFT JOIN germplasm g ON ds.germplasm_id=g.germplasm_id").into(DnasampleViewEntity.class);

        }catch(Exception e ){
            e.printStackTrace();

            Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return list;
    }

    @Override
    public List<DnasampleViewEntity> getAllDnasamplesBasedOnQuery(DnasampleEntity dnasampleEntity) {
        List<DnasampleViewEntity> list = null;
        
        lastQueriedDNAsampleEntity = new DnasampleEntity();
        
        int queryCount =0;
        int dsNameCount = 0;
        DSLContext context = getDSLContext();

        try{ /* START building THE QUERY via StringBuilder */
            StringBuilder sb = new StringBuilder();
            StringBuilder sbWhere = new StringBuilder(); 

            sb.append("SELECT distinct on (ds.dnasample_id) ds.dnasample_id AS dnasampleId, ds.name AS dnasampleName, ds.code as code, ds.platename as plateName, ds.well_row as wellRow, ds.well_col as wellCol, ds.uuid as uuid, g.germplasm_id as germplasmId, g.name as germplasmName, p.project_id as projectId, p.name AS projectName FROM dnasample ds LEFT JOIN project p ON ds.project_id = p.project_id  LEFT JOIN germplasm g ON ds.germplasm_id=g.germplasm_id");

            // build query for DNA Sample NAMES filter
            if (dnasampleEntity.getDnasampleNamesAsEnterSeparatedString()!=null && !dnasampleEntity.getDnasampleNamesAsEnterSeparatedString().isEmpty()){
                lastQueriedDNAsampleEntity.setDnasampleNamesAsCommaSeparatedString(dnasampleEntity.getSQLReadyDnasampleNames());
                
                  sbWhere.append(" where LOWER(ds.name) in ("+dnasampleEntity.getSQLReadyDnasampleNames()+")");
                  dsNameCount++;  
            } else if (dnasampleEntity.getDnasampleIDStartRange()!=null || dnasampleEntity.getDnasampleIDEndRange()!=null){ // build query for given DNAsample IDs

                    //check which is not null
                    checkPreviousAppends(dsNameCount, queryCount, sbWhere);

                    if(dnasampleEntity.getDnasampleIDStartRange()!=null && dnasampleEntity.getDnasampleIDEndRange()!=null){ //if both is not null
                        Integer lowerID = dnasampleEntity.getDnasampleIDStartRange();
                        Integer higherID = dnasampleEntity.getDnasampleIDEndRange();

                        if(lowerID.compareTo(higherID)>0){
                            lowerID = dnasampleEntity.getDnasampleIDEndRange();
                            higherID = dnasampleEntity.getDnasampleIDStartRange();
                        }
                        lastQueriedDNAsampleEntity.setDnasampleIDEndRange(dnasampleEntity.getDnasampleIDEndRange());
                        lastQueriedDNAsampleEntity.setDnasampleIDStartRange(dnasampleEntity.getDnasampleIDStartRange());
                        sbWhere.append(" ds.dnasample_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
                    }else{
                        Integer ID = null;
                        if(dnasampleEntity.getDnasampleIDStartRange()!=null) {
                            ID = dnasampleEntity.getDnasampleIDStartRange();
                            lastQueriedDNAsampleEntity.setDnasampleIDStartRange(dnasampleEntity.getDnasampleIDStartRange());
                        }
                        else {
                            lastQueriedDNAsampleEntity.setDnasampleIDEndRange(dnasampleEntity.getDnasampleIDEndRange());
                            ID = dnasampleEntity.getDnasampleIDEndRange();
                        }

                        sbWhere.append(" ds.dnasample_id = "+Integer.toString(ID));
                    }

                    queryCount++;
                } // END build query for given DNAsample IDs

//             build query for germplasm NAMES filter
            if (dnasampleEntity.getGermplasmNamesAsEnterSeparatedString()!=null && !dnasampleEntity.getGermplasmNamesAsEnterSeparatedString().isEmpty()){
                lastQueriedDNAsampleEntity.setGermplasmNamesAsCommaSeparatedString(dnasampleEntity.getSQLReadyGermplasmNames());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                
                sbWhere.append(" LOWER(g.name) in ("+dnasampleEntity.getSQLReadyGermplasmNames()+")");
                dsNameCount++;  
            } else if (dnasampleEntity.getGermplasmIDStartRange()!=null || dnasampleEntity.getGermplasmIDEndRange()!=null){ // 
                //check which is not null
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);

                if(dnasampleEntity.getGermplasmIDStartRange()!=null && dnasampleEntity.getGermplasmIDEndRange()!=null){ //if both is not null
                    Integer lowerID = dnasampleEntity.getGermplasmIDStartRange();
                    Integer higherID = dnasampleEntity.getGermplasmIDEndRange();

                    if(lowerID.compareTo(higherID)>0){
                        lowerID = dnasampleEntity.getGermplasmIDEndRange();
                        higherID = dnasampleEntity.getGermplasmIDStartRange();
                    }

                    lastQueriedDNAsampleEntity.setGermplasmIDEndRange(dnasampleEntity.getGermplasmIDEndRange());
                    lastQueriedDNAsampleEntity.setGermplasmIDStartRange(dnasampleEntity.getGermplasmIDStartRange());
                    sbWhere.append(" g.germplasm_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
                }else{
                    Integer ID = null;
                    if(dnasampleEntity.getGermplasmIDStartRange()!=null) {
                        ID = dnasampleEntity.getGermplasmIDStartRange();
                        lastQueriedDNAsampleEntity.setGermplasmIDStartRange(dnasampleEntity.getGermplasmIDStartRange());
                    }
                    else {
                        ID = dnasampleEntity.getGermplasmIDEndRange();
                        lastQueriedDNAsampleEntity.setGermplasmIDEndRange(dnasampleEntity.getGermplasmIDEndRange());
                    }

                    sbWhere.append(" g.germplasm_id = "+Integer.toString(ID));
                }

                queryCount++;
            } // END build query for given id names / ranges

            /* ADD THE "WHERE" CONDITIONS */

            //build query for UUID
            if (dnasampleEntity.getDnasampleUuidAsEnterSeparatedString()!=null && !dnasampleEntity.getDnasampleUuidAsEnterSeparatedString().isEmpty()){
                lastQueriedDNAsampleEntity.setDnasampleUuidAsCommaSeparatedString(dnasampleEntity.getSQLReadyDnasampleUuidNames());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                
                sbWhere.append(" LOWER(ds.uuid) in ("+dnasampleEntity.getSQLReadyDnasampleUuidNames()+")");
                dsNameCount++;  
            } 
            
            
            // build query for PROJECTS filter
            if (Utils.isRecordNotNullOrEmpty(dnasampleEntity.getProjectRecord())){

                if(dnasampleEntity.getProjectRecord().getProjectId()!=0) {
                    lastQueriedDNAsampleEntity.getProjectList().add(dnasampleEntity.getProjectRecord());
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);
                sbWhere.append(" p.project_id = "+ dnasampleEntity.getProjectRecord().getProjectId().toString());
                queryCount++;
                }
            }
            if(sbWhere.length()>0) {
                sbWhere.append(";");
                sb.append(sbWhere.toString());
                
                String query = sb.toString();
                System.out.println(query);
                list = context.fetch(query).into(DnasampleViewEntity.class);
            } else if (Utils.isRecordNotNullOrEmpty(dnasampleEntity.getProjectRecord())){
                      Messagebox.show("Please add more filters","Too broad", Messagebox.OK, Messagebox.EXCLAMATION);
            }
            else Messagebox.show("Please specify filters", "There is nothing selected", Messagebox.OK, Messagebox.EXCLAMATION);
            
        }catch(Exception e ){
            e.printStackTrace();
            Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean deleteDnasample(DnasampleViewEntity dnasampleViewEntity) {
        boolean successful = false;
        List<DnasampleViewEntity> selectedList = new ArrayList<DnasampleViewEntity>();
        selectedList.add(dnasampleViewEntity);
        
        //check if Marker is not being used in a Marker Group or a Dataset
        List<DnasampleViewEntity> unusedInDnarun = null;

        unusedInDnarun = checkWhichDnasamplessAreUsedInADnarun(selectedList);
        if(unusedInDnarun.size()>0){

            Messagebox.show("THIS ACTION IS NOT REVERSIBLE.\n\n Do you want to continue?\n", 
                    "WARNING", Messagebox.YES | Messagebox.CANCEL,
                    Messagebox.EXCLAMATION,
                    new org.zkoss.zk.ui.event.EventListener(){
                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        DSLContext context = getDSLContext();
                        
                        Integer id = selectedList.get(0).getDnasampleId();
                        double startTime = 0, endTime=0;

                        startTime = System.currentTimeMillis();
                        
                        context.delete(DNASAMPLE)
                        .where(DNASAMPLE.DNASAMPLE_ID.eq(id))
                        .execute();

                        endTime = System.currentTimeMillis();
                        double rowDeleteSeconds = (endTime - startTime) / 1000;

                        List<String> successMessagesAsList = new ArrayList<String>();
                        successMessagesAsList.add("1 DNAsample deleted. ("+Double.toString(rowDeleteSeconds)+" sec)" );
                       
                        String user = getUser();
                        StringBuilder logSB = new StringBuilder();
                        logSB.append("["+user+ "] DELETED A DNASAMPLE\n\n"); 
                        logSB.append("["+user+ "] Filtering criteria for DNAsample delete:\n"+lastQueriedDNAsampleEntity.getCompleteFiltersAsText());
                        logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n\n"+
                                "context.delete(DNASAMPLE).where(\n"
                                + "            DNASAMPLE.DNASAMPLE_ID.eq("+id.toString()+"))\n"
                                        + "            .execute();");
          
                        logSB.append("\n\n["+user+"] DNAsample delete result:\n"+ "1 DNAsample deleted. ("+Double.toString(rowDeleteSeconds)+" sec)");

                        logSB.append("\n--------------------------------------------------");
                        log.info(logSB.toString());
                        Map<String, Object> args = new HashMap<String, Object>();
                        args.put("successMessagesAsList", successMessagesAsList);
                        args.put("filterEntity", lastQueriedDNAsampleEntity.getFilterListAsRows());

                        Window window = (Window)Executions.createComponents(
                                "/marker_delete_successful.zul", null, args);
                        window.setPosition("center");
                        window.setClosable(true);
                        window.doModal();
                        
                        BindUtils.postGlobalCommand(null, null, "retrieveDNAsampleList", null);
                        
                    }

                }
            });
        }

        return successful;
    }

    private List<DnasampleViewEntity> checkWhichDnasamplessAreUsedInADnarun(List<DnasampleViewEntity> selectedList) {
        // TODO Auto-generated method stub

        int totalNoOfCantBeDeleted = 0;

        List<DnasampleViewEntity> listOfCanBeDeleted =  new ArrayList<DnasampleViewEntity>();
        List<DnasampleDeleteResultTableEntity> dnasampleDeleteResultTableEntityList =  new ArrayList<DnasampleDeleteResultTableEntity>();

        boolean inDnarun = false;

        for(DnasampleViewEntity dnasample: selectedList){
            try{

                DnasampleDeleteResultTableEntity dnasampleDeleteResultTableEntity= new DnasampleDeleteResultTableEntity();
                //set initial values

                inDnarun = false;
                
                dnasampleDeleteResultTableEntity.setDnasample_id(dnasample.getDnasampleId());
                dnasampleDeleteResultTableEntity.setDnasample_name(dnasample.getDnasampleName());

                List<DnarunRecord> inDnarunList = getDnarunsbyDnasampleId(dnasample.getDnasampleId());
                if(inDnarunList.size()>0) {
                    inDnarun = true;
                    dnasampleDeleteResultTableEntity.setDnarun_name(setDnarunIdDetails(inDnarunList));
                }
                
                if(!inDnarun){
                    listOfCanBeDeleted.add(dnasample);
                }else{
                    if(totalNoOfCantBeDeleted<10) dnasampleDeleteResultTableEntityList.add(dnasampleDeleteResultTableEntity);
                    totalNoOfCantBeDeleted++;

                }

                totalNoOfCantBeDeleted = totalNoOfCantBeDeleted-10;
            }catch(Exception e ){

                Messagebox.show("There was an error while trying to retrieve Dnasamples", "ERROR", Messagebox.OK, Messagebox.ERROR);

            }

        }
        if(dnasampleDeleteResultTableEntityList.size()>0){

            Map<String, Object> args = new HashMap<String, Object>();
            args.put("dnasampleDeleteResultTableEntityList", dnasampleDeleteResultTableEntityList);
            args.put("totalNumOfDnasamplesThatCantBeDeleted", totalNoOfCantBeDeleted);
            Window window = (Window)Executions.createComponents(
                    "/dnasampleDeleteWarning.zul", null, args);
            window.doModal();
        }
        return listOfCanBeDeleted;
    }

    private List<DnarunRecord> getDnarunsbyDnasampleId(Integer dnasampleId) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<DnarunRecord> list = null;
        try{
            String query = "select * FROM dnarun dr LEFT JOIN dnasample ds ON dr.dnasample_id = ds.dnasample_id where ds.dnasample_id="+dnasampleId.toString()+"";
            list = context.fetch( query).into(DnarunRecord.class);


            if(list.get(0).getDnarunId()==null){
                list = new ArrayList<DnarunRecord>();
            }
        }catch(Exception e ){
            list = new ArrayList<DnarunRecord>();
            e.printStackTrace();


        }
        return list;
    }

    private String setDnarunIdDetails(List<DnarunRecord> inDnarunList) {
        // TODO Auto-generated method stub


            StringBuilder sb = new StringBuilder();
            for(DnarunRecord mgr : inDnarunList){

                if(sb.length()>0) sb.append(", ");
                sb.append(" "+mgr.getName()+" ("+mgr.getDnarunId()+")");

            }

            return(sb.toString());

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean deleteDnasamples(List<DnasampleViewEntity> selectedList) {
        // TODO Auto-generated method stub
        
        boolean successful = false;

        //check if Marker is not being used in a Marker Group or a Dataset
        List<DnasampleViewEntity> unusedInDnarun = null;

        unusedInDnarun = checkWhichDnasamplessAreUsedInADnarun(selectedList);
        String warningMessage = Integer.toString(unusedInDnarun.size()) + 
                " dnasample(s) can still be deleted. Do you want to continue?\n";
        
        if(unusedInDnarun.size() == selectedList.size()) {warningMessage ="Do you want to continue?\n";
        }  

        final List<DnasampleViewEntity> finalListThatcanBeDeleted = unusedInDnarun;
            
        if(unusedInDnarun.size()>0){

            Messagebox.show("THIS ACTION IS NOT REVERSIBLE.\n\n "+ warningMessage, 
                    "WARNING", Messagebox.YES | Messagebox.CANCEL,
                    Messagebox.EXCLAMATION,
                    new org.zkoss.zk.ui.event.EventListener(){
                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        DSLContext context = getDSLContext();
                        
                        int dnasamplesDeleted = 0;
                        double startTime = 0, endTime=0;

                        startTime = System.currentTimeMillis();
                        
                        dnasamplesDeleted = context.deleteFrom(DNASAMPLE)
                        .where(DNASAMPLE.DNASAMPLE_ID.in(finalListThatcanBeDeleted
                                .stream()
                                .map(DnasampleViewEntity::getDnasampleId)
                                .collect(Collectors.toList())))
                        .execute();

                        endTime = System.currentTimeMillis();
                        double rowDeleteSeconds = (endTime - startTime) / 1000;

                        List<String> successMessagesAsList = new ArrayList<String>();
                        successMessagesAsList.add(Integer.toString(dnasamplesDeleted)+"  DNAsample(s) deleted. ("+Double.toString(rowDeleteSeconds)+" sec)" );
                       
                        String user = getUser();
                        StringBuilder logSB = new StringBuilder();
                        logSB.append("["+user+ "] DELETED DNASAMPLES(S)\n\n"); 
                        logSB.append("["+user+ "] Filtering criteria for DNAsample delete:\n"+lastQueriedDNAsampleEntity.getCompleteFiltersAsText());
                        logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n\n"+
                                "context.deleteFrom(DNASAMPLE).where(\n"
                                + "            DNASAMPLE.DNASAMPLE_ID.in(finalListThatcanBeDeleted.stream().map(DnasampleViewEntity::getDnasampleId).collect(Collectors.toList()))))\n"
                                        + "            .execute();");
          
                        logSB.append("\n\n["+user+"] DNAsample delete result:\n"+ Integer.toString(dnasamplesDeleted)+" DNAsample(s) deleted. ("+Double.toString(rowDeleteSeconds)+" sec)");

                        logSB.append("\n--------------------------------------------------");
                        log.info(logSB.toString());
                        Map<String, Object> args = new HashMap<String, Object>();
                        args.put("successMessagesAsList", successMessagesAsList);
                        args.put("filterEntity", lastQueriedDNAsampleEntity.getFilterListAsRows());

                        Window window = (Window)Executions.createComponents(
                                "/marker_delete_successful.zul", null, args);
                        window.setPosition("center");
                        window.setClosable(true);
                        window.doModal();
                        
                        BindUtils.postGlobalCommand(null, null, "retrieveDNAsampleList", null);
                        
                    }

                }
            });
        }

        return successful;
    }

    @Override
    public List<GermplasmViewEntity> getAllGermplasms() {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<GermplasmViewEntity> list = null;
        try{

            list = context.fetch("SELECT distinct on (g.germplasm_id) g.germplasm_id as germplasmId, g.name as germplasmName, g.external_code as externalCode, ds.dnasample_id AS dnasampleId, ds.name AS dnasampleName, dr.dnarun_id AS dnarunId, dr.name AS dnarunName FROM germplasm g LEFT JOIN dnasample ds ON ds.germplasm_id=g.germplasm_id LEFT JOIN dnarun dr ON dr.dnasample_id = ds.dnasample_id").into(GermplasmViewEntity.class);

        }catch(Exception e ){
            e.printStackTrace();

            Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);

        }

        return list;
    }

    @Override
    public List<GermplasmViewEntity> getAllGermplasmsBasedOnQuery(GermplasmEntity germplasmEntity) {
        // TODO Auto-generated method stub
        List<GermplasmViewEntity> list = null;
        
        lastQueriedGermplasmEntity = new GermplasmEntity();
        
        int queryCount =0;
        int dsNameCount = 0;
        DSLContext context = getDSLContext();

        try{ /* START building THE QUERY via StringBuilder */
            StringBuilder sb = new StringBuilder();
            StringBuilder sbWhere = new StringBuilder(); 

            sb.append("SELECT distinct on (g.germplasm_id) g.germplasm_id as germplasmId, g.name as germplasmName, g.external_code as externalCode, ds.dnasample_id AS dnasampleId, ds.name AS dnasampleName, dr.dnarun_id AS dnarunId, dr.name AS dnarunName FROM germplasm g LEFT JOIN dnasample ds ON ds.germplasm_id=g.germplasm_id LEFT JOIN dnarun dr ON dr.dnasample_id = ds.dnasample_id");

//             build query for germplasm NAMES filter
            if (germplasmEntity.getGermplasmNamesAsEnterSeparatedString()!=null && !germplasmEntity.getGermplasmNamesAsEnterSeparatedString().isEmpty()){
                lastQueriedGermplasmEntity.setGermplasmNamesAsCommaSeparatedString(germplasmEntity.getSQLReadyGermplasmNames());
                
                sbWhere.append(" where LOWER(g.name) in ("+germplasmEntity.getSQLReadyGermplasmNames()+")");
                dsNameCount++;  
            } else if (germplasmEntity.getGermplasmIDStartRange()!=null || germplasmEntity.getGermplasmIDEndRange()!=null){ // 
                //check which is not null
                checkPreviousAppends(dsNameCount, queryCount, sbWhere);

                if(germplasmEntity.getGermplasmIDStartRange()!=null && germplasmEntity.getGermplasmIDEndRange()!=null){ //if both is not null
                    Integer lowerID = germplasmEntity.getGermplasmIDStartRange();
                    Integer higherID = germplasmEntity.getGermplasmIDEndRange();

                    if(lowerID.compareTo(higherID)>0){
                        lowerID = germplasmEntity.getGermplasmIDEndRange();
                        higherID = germplasmEntity.getGermplasmIDStartRange();
                    }

                    lastQueriedGermplasmEntity.setGermplasmIDEndRange(germplasmEntity.getGermplasmIDEndRange());
                    lastQueriedGermplasmEntity.setGermplasmIDStartRange(germplasmEntity.getGermplasmIDStartRange());
                    sbWhere.append(" g.germplasm_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
                }else{
                    Integer ID = null;
                    if(germplasmEntity.getGermplasmIDStartRange()!=null) {
                        ID = germplasmEntity.getGermplasmIDStartRange();
                        lastQueriedGermplasmEntity.setGermplasmIDStartRange(germplasmEntity.getGermplasmIDStartRange());
                    }
                    else {
                        ID = germplasmEntity.getGermplasmIDEndRange();
                        lastQueriedGermplasmEntity.setGermplasmIDEndRange(germplasmEntity.getGermplasmIDEndRange());
                    }

                    sbWhere.append(" g.germplasm_id = "+Integer.toString(ID));
                }

                queryCount++;
            } // END build query for given id names / ranges

            if(sbWhere.length()>0) {
                sbWhere.append(";");
                sb.append(sbWhere.toString());
                
                String query = sb.toString();
                System.out.println(query);
                list = context.fetch(query).into(GermplasmViewEntity.class);
            } else {
                Messagebox.show("Please specify filters", "There is nothing selected", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }catch(Exception e ){
            e.printStackTrace();
            Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);

        }
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean deleteGermplasm(GermplasmViewEntity germplasmViewEntity) {
        // TODO Auto-generated method stub
        boolean successful = false;
        List<GermplasmViewEntity> selectedList = new ArrayList<GermplasmViewEntity>();
        selectedList.add(germplasmViewEntity);
        
        //check if Germplasm is not being used in a Dnasample
        List<GermplasmViewEntity> unusedInGermplasm = null;

        unusedInGermplasm = checkWhichGermplasmsAreUsedInADnasample(selectedList);
        if(unusedInGermplasm.size()>0){

            Messagebox.show("THIS ACTION IS NOT REVERSIBLE.\n\n Do you want to continue?\n", 
                    "WARNING", Messagebox.YES | Messagebox.CANCEL,
                    Messagebox.EXCLAMATION,
                    new org.zkoss.zk.ui.event.EventListener(){
                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        DSLContext context = getDSLContext();
                        
                        Integer id = selectedList.get(0).getGermplasmId();
                        double startTime = 0, endTime=0;

                        startTime = System.currentTimeMillis();
                        
                        context.delete(GERMPLASM)
                        .where(GERMPLASM.GERMPLASM_ID.eq(id))
                        .execute();

                        endTime = System.currentTimeMillis();
                        double rowDeleteSeconds = (endTime - startTime) / 1000;

                        List<String> successMessagesAsList = new ArrayList<String>();
                        successMessagesAsList.add("1 Germplasm deleted. ("+Double.toString(rowDeleteSeconds)+" sec)" );
                       
                        String user = getUser();
                        StringBuilder logSB = new StringBuilder();
                        logSB.append("["+user+ "] DELETED A GERMPLASM\n\n"); 
                        logSB.append("["+user+ "] Filtering criteria for germplasm delete:\n"+lastQueriedGermplasmEntity.getCompleteFiltersAsText());
                        logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n\n"+
                                "context.delete(GERMPLASM).where(\n"
                                + "            GERMPLASM.GERMPLASM_ID.eq("+id.toString()+"))\n"
                                        + "            .execute();");
          
                        logSB.append("\n\n["+user+"] Germplasm delete result:\n"+ "1 Germplasm deleted. ("+Double.toString(rowDeleteSeconds)+" sec)");

                        logSB.append("\n--------------------------------------------------");
                        log.info(logSB.toString());
                        Map<String, Object> args = new HashMap<String, Object>();
                        args.put("successMessagesAsList", successMessagesAsList);
                        args.put("filterEntity", lastQueriedGermplasmEntity.getFilterListAsRows());

                        Window window = (Window)Executions.createComponents(
                                "/marker_delete_successful.zul", null, args);
                        window.setPosition("center");
                        window.setClosable(true);
                        window.doModal();
                        
                        BindUtils.postGlobalCommand(null, null, "retrieveGermplasmList", null);
                        
                    }

                }
            });
        }

        return successful;
    }

    private List<GermplasmViewEntity> checkWhichGermplasmsAreUsedInADnasample(List<GermplasmViewEntity> selectedList) {

        int totalNoOfCantBeDeleted = 0;

        List<GermplasmViewEntity> listOfCanBeDeleted =  new ArrayList<GermplasmViewEntity>();
        List<GermplasmDeleteResultTableEntity> germplasmDeleteResultTableEntityList =  new ArrayList<GermplasmDeleteResultTableEntity>();

        boolean inDnasample = false;

        for(GermplasmViewEntity germplasm: selectedList){
            try{

                GermplasmDeleteResultTableEntity germplasmDeleteResultTableEntity= new GermplasmDeleteResultTableEntity();
                //set initial values

                inDnasample = false;
                
                germplasmDeleteResultTableEntity.setGermplasm_id(germplasm.getGermplasmId());
                germplasmDeleteResultTableEntity.setGermplasm_name(germplasm.getGermplasmName());

                List<DnasampleRecord> inDnasampleList = getDnasamplesbyGermplasmId(germplasm.getGermplasmId());
                if(inDnasampleList.size()>0) {
                    inDnasample = true;
                    
                    //setDnasampleIdDetails
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sbDnarun = new StringBuilder();
                    for(DnasampleRecord mgr : inDnasampleList){

                        if(sb.length()>0) sb.append(", ");
                        sb.append(" "+mgr.getName()+" ("+mgr.getDnasampleId()+")");

                        //checkIfDnasampleisinaDataset
                      List<DnarunRecord> inDnarunList = getDnarunsbyDnasampleId(mgr.getDnasampleId());
                      if(inDnarunList.size()>0) {
                          sbDnarun.append(setDnarunIdDetails(inDnarunList));
                      }
                    }
                    
                    germplasmDeleteResultTableEntity.setDnasample_name(sb.toString());
                    germplasmDeleteResultTableEntity.setDnarun_name(sbDnarun.toString());
                    
                }
                
                if(!inDnasample){
                    listOfCanBeDeleted.add(germplasm);
                }else{
                    if(totalNoOfCantBeDeleted<10) germplasmDeleteResultTableEntityList.add(germplasmDeleteResultTableEntity);
                    totalNoOfCantBeDeleted++;

                }

                totalNoOfCantBeDeleted = totalNoOfCantBeDeleted-10;
            }catch(Exception e ){

                Messagebox.show("There was an error while trying to retrieve Germplasms", "ERROR", Messagebox.OK, Messagebox.ERROR);

            }

        }
        if(germplasmDeleteResultTableEntityList.size()>0){

            Map<String, Object> args = new HashMap<String, Object>();
            args.put("germplasmDeleteResultTableEntityList", germplasmDeleteResultTableEntityList);
            args.put("totalNumOfGermplasmsThatCantBeDeleted", totalNoOfCantBeDeleted);
            Window window = (Window)Executions.createComponents(
                    "/germplasmDeleteWarning.zul", null, args);
            window.doModal();
        }
        return listOfCanBeDeleted;
    }

    private List<DnasampleRecord> getDnasamplesbyGermplasmId(Integer germplasmId) {
        // TODO Auto-generated method stub
        DSLContext context = getDSLContext();
        List<DnasampleRecord> list = null;
        try{
            String query = "select * FROM dnasample ds LEFT JOIN germplasm g ON ds.germplasm_id = g.germplasm_id where g.germplasm_id ="+germplasmId.toString()+"";
            list = context.fetch( query).into(DnasampleRecord.class);


            if(list.get(0).getDnasampleId()==null){
                list = new ArrayList<DnasampleRecord>();
            }
        }catch(Exception e ){
            list = new ArrayList<DnasampleRecord>();
            e.printStackTrace();


        }
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean deleteGermplasms(List<GermplasmViewEntity> selectedList) {
        // TODO Auto-generated method stub

        boolean successful = false;
        
        //check if Germplasm is not being used in a Dnasample
        List<GermplasmViewEntity> unusedInGermplasm = null;

        unusedInGermplasm = checkWhichGermplasmsAreUsedInADnasample(selectedList);
        String warningMessage = Integer.toString(unusedInGermplasm.size()) + 
                " germplasm(s) can still be deleted. Do you want to continue?\n";
        
        if(unusedInGermplasm.size() == selectedList.size()) {warningMessage ="Do you want to continue?\n";
        }  

        final List<GermplasmViewEntity> finalListThatcanBeDeleted = unusedInGermplasm;
            
        if(unusedInGermplasm.size()>0){

            Messagebox.show("THIS ACTION IS NOT REVERSIBLE.\n\n "+ warningMessage, 
                    "WARNING", Messagebox.YES | Messagebox.CANCEL,
                    Messagebox.EXCLAMATION,
                    new org.zkoss.zk.ui.event.EventListener(){
                @Override
                public void onEvent(Event event) throws Exception {
                    // TODO Auto-generated method stub
                    if(Messagebox.ON_YES.equals(event.getName())){
                        DSLContext context = getDSLContext();
                        
                        int germplasmsDeleted = 0;
                        double startTime = 0, endTime=0;

                        startTime = System.currentTimeMillis();
                        
                        germplasmsDeleted = context.deleteFrom(GERMPLASM)
                        .where(GERMPLASM.GERMPLASM_ID.in(finalListThatcanBeDeleted
                                .stream()
                                .map(GermplasmViewEntity::getGermplasmId)
                                .collect(Collectors.toList())))
                        .execute();

                        endTime = System.currentTimeMillis();
                        double rowDeleteSeconds = (endTime - startTime) / 1000;

                        List<String> successMessagesAsList = new ArrayList<String>();
                        successMessagesAsList.add(Integer.toString(germplasmsDeleted)+"  Germplasm(s) deleted. ("+Double.toString(rowDeleteSeconds)+" sec)" );
                       
                        String user = getUser();
                        StringBuilder logSB = new StringBuilder();
                        logSB.append("["+user+ "] DELETED GERMPLASM(S)\n\n"); 
                        logSB.append("["+user+ "] Filtering criteria for germplasms delete:\n"+lastQueriedGermplasmEntity.getCompleteFiltersAsText());
                        logSB.append("\n\n["+user+"] Background JOOQ commands that ran:\n\n"+
                                "context.deleteFrom(GERMPLASM).where(\n"
                                + "            GERMPLASM.GERMPLASM_ID.in(finalListThatcanBeDeleted.stream().map(GermplasmViewEntity::getGermplasmId).collect(Collectors.toList()))))\n"
                                        + "            .execute();");
          
                        logSB.append("\n\n["+user+"] Germplasm delete result:\n"+ Integer.toString(germplasmsDeleted)+" Germplasm(s) deleted. ("+Double.toString(rowDeleteSeconds)+" sec)");

                        logSB.append("\n--------------------------------------------------");
                        log.info(logSB.toString());
                        Map<String, Object> args = new HashMap<String, Object>();
                        args.put("successMessagesAsList", successMessagesAsList);
                        args.put("filterEntity", lastQueriedGermplasmEntity.getFilterListAsRows());

                        Window window = (Window)Executions.createComponents(
                                "/marker_delete_successful.zul", null, args);
                        window.setPosition("center");
                        window.setClosable(true);
                        window.doModal();
                        
                        BindUtils.postGlobalCommand(null, null, "retrieveGermplasmList", null);
                        
                    }

                }
            });
        }

        return successful;
    }
}
