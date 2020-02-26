/* 

 */
package org.gobiiproject.datatimescope.services.impls;


import static org.gobiiproject.datatimescope.db.generated.Tables.ANALYSIS;
import static org.gobiiproject.datatimescope.db.generated.Tables.CV;
import static org.gobiiproject.datatimescope.db.generated.Tables.CVGROUP;
import static org.gobiiproject.datatimescope.db.generated.Tables.EXPERIMENT;
import static org.gobiiproject.datatimescope.db.generated.Tables.CONTACT;
import static org.gobiiproject.datatimescope.db.generated.Tables.PLATFORM;
import static org.gobiiproject.datatimescope.db.generated.Tables.PROJECT;
import static org.gobiiproject.datatimescope.db.generated.Tables.REFERENCE;
import static org.gobiiproject.datatimescope.db.generated.Tables.ORGANIZATION;
import static org.gobiiproject.datatimescope.db.generated.Tables.MAPSET;
import static org.gobiiproject.datatimescope.db.generated.Tables.DATASET;
import static org.gobiiproject.datatimescope.db.generated.Tables.VENDOR_PROTOCOL;
import static org.gobiiproject.datatimescope.db.generated.Tables.LINKAGE_GROUP;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.db.generated.routines.Createtimescoper;
import org.gobiiproject.datatimescope.db.generated.routines.Crypt;
import org.gobiiproject.datatimescope.db.generated.routines.Deletedatasetdnarunindices;
import org.gobiiproject.datatimescope.db.generated.routines.Deletedatasetmarkerindices;
import org.gobiiproject.datatimescope.db.generated.routines.GenSalt2;
import org.gobiiproject.datatimescope.db.generated.routines.Gettimescoper;
import org.gobiiproject.datatimescope.db.generated.tables.records.AnalysisRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ExperimentRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.LinkageGroupRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.MapsetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.OrganizationRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ProjectRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.ReferenceRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VendorProtocolRecord;
import org.gobiiproject.datatimescope.entity.DatasetEntity;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.LinkageGroupEntity;
import org.gobiiproject.datatimescope.entity.LinkageGroupSummaryEntity;
import org.gobiiproject.datatimescope.entity.MarkerDetailDatasetEntity;
import org.gobiiproject.datatimescope.entity.MarkerDetailLinkageGroupEntity;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.VLinkageGroupSummaryEntity;
import org.gobiiproject.datatimescope.exceptions.CannotDeleteDataSetListException;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.gobiiproject.datatimescope.services.DataSetDeleteInfo;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.exceptions.DatabaseException;
import org.gobiiproject.datatimescope.utils.WebappUtil;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;

public class ViewModelServiceImpl implements ViewModelService,Serializable{
	private static final long serialVersionUID = 1L;
	final static Logger log = Logger.getLogger(ViewModelServiceImpl.class.getName());
	private String lastDSQuery;

	@Override
	public boolean connectToDB(String userName, String password, ServerInfo serverInfo) throws TimescopeException {
		return WebappUtil.connectToDB(userName, password, serverInfo);
	}

	@Override
	public boolean createNewUser(TimescoperEntity userAccount) throws TimescopeException {
		boolean successful = false;
		try{
			DSLContext context = WebappUtil.getDSLContext();

			Createtimescoper createTimescoper = createTimescoperFromRecord(userAccount);
			createTimescoper.execute(context.configuration());
			successful = true;
		}
		catch(Exception e ){
			e.printStackTrace();
			if(e.getMessage().contains("violates unique constraint")){
				throw new DatabaseException(userAccount.getUsername()+" already exists!");
			}
			else throw new TimescopeException(e.getMessage());
		}
		return successful;
	}


	private Createtimescoper createTimescoperFromRecord(TimescoperEntity userAccount) {
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
	public boolean deleteUser(TimescoperEntity userAccount) throws TimescopeException {
		boolean successful = false;
		try{
			userAccount.delete();
			successful = true;
		}
		catch(Exception e ){
			throw new TimescopeException(e.getMessage());
		}
		return successful;
	}

	@Override
	public boolean deleteUsers(List<TimescoperEntity> selectedUsersList) throws TimescopeException {
		boolean successful = false;
		try{
			DSLContext context = WebappUtil.getDSLContext();
			context.batchDelete(selectedUsersList).execute();
			successful = true;
		} catch(Exception e ){
			throw new TimescopeException(e.getMessage());
		}
		return successful;
	}


	@Override
	public TimescoperRecord loginTimescoper(String username, String password) throws TimescopeException{
		boolean successful= false;
		TimescoperRecord user = new TimescoperRecord();
		Gettimescoper gettimescoper = new Gettimescoper();
		try{

			DSLContext context = WebappUtil.getDSLContext();

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


		}else throw new DatabaseException("Invalid username or password!");
		return user;
	}

	@Override
	public List<CvRecord> getCvTermsByGroupName(String groupName) throws TimescopeException {
		List<CvRecord> cvRecordList = null;
		try{
			DSLContext context = WebappUtil.getDSLContext();
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
			throw new TimescopeException(e.getMessage());
		}
		return cvRecordList;
	}

	@Override
	public synchronized TimescoperEntity getUserInfo(String username) throws TimescopeException {

		TimescoperEntity user = new TimescoperEntity();

		try{
			DSLContext context = WebappUtil.getDSLContext();
			user =  context.fetchOne("select * from timescoper where username = '"+username+"';").into(TimescoperEntity.class);
		}catch(Exception e ){
			throw new TimescopeException("Invalid username");
		}

		return user;
	}

	@Override
	public List<TimescoperEntity> getAllOtherUsers(String username) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<TimescoperEntity> userList = null;
		try{
			userList = context.fetch("select * from timescoper where username != '"+username+"';").into(TimescoperEntity.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve users");
		}
		return userList;
	}

	@Override
	public List<ContactRecord> getAllContacts() throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<ContactRecord> contactList = null;
		try{
			contactList = context.select().from(CONTACT).orderBy(CONTACT.LASTNAME).fetchInto(ContactRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve users");
		}

		return contactList;
	}

	@Override
	public List<VDatasetSummaryEntity> getAllDatasets(DatasetSummaryEntity datasetSummaryEntity) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();

		List<VDatasetSummaryEntity> datasetList = null;
		try{
			String query = "select d.dataset_id, d.name as dataset_name, d.experiment_id, e.name as experiment_name, d.callinganalysis_id, a.name as callingnalysis_name, d.analyses, d.data_table, d.data_file, d.quality_table, d.quality_file, d.scores, c1.username created_by_username, d.created_date, c2.username as modified_by_username, d.modified_date, cv1.term as status_name, cv2.term as type_name, j.name as job_name, pi.contact_id as pi_id, pi.firstname as pi_firstname, pi.lastname as pi_lastname from dataset d left join experiment e on d.experiment_id=e.experiment_id left join project p on e.project_id=p.project_id join contact pi on p.pi_contact=pi.contact_id  left join analysis a on a.analysis_id=d.callinganalysis_id left join contact c1 on c1.contact_id=d.created_by left join contact c2 on c2.contact_id=d.modified_by left join cv cv1 on cv1.cv_id=d.status left join cv cv2 on cv2.cv_id=d.type_id left join job j on j.job_id=d.job_id;";
			datasetList = context.fetch(query).into(VDatasetSummaryEntity.class);

			datasetSummaryEntity.setFilter("");
			setLastDSQuery(" (not filtered)");
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve datasets");
		}
		return datasetList;
	}

	@Override
	public List<ContactRecord> getContactsByRoles(Integer[] role) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<ContactRecord> contactList = null;
		try{
			contactList = context.select().from(CONTACT).where(CONTACT.ROLES.contains(role)).orderBy(CONTACT.LASTNAME).fetchInto(ContactRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve contacts");
		}
		return contactList;
	}

	@Override
	public boolean updateUser(TimescoperEntity userAccount) throws TimescopeException {
		boolean successful = false;
		try{

			DSLContext context = WebappUtil.getDSLContext();

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

		}catch(Exception e ){
			if(e.getMessage().contains("violates unique constraint")){
				throw new DatabaseException(userAccount.getUsername()+" already exists!");
			}
			else throw new  TimescopeException("Invalid username");
		}
		return successful;
	}

	@Override
	public DataSetDeleteInfo deleteDataset(VDatasetSummaryEntity vDatasetSummaryRecord, List<DatasetSummaryEntity> datasetSummary,
			DatasetSummaryEntity datasetSummaryEntity) throws TimescopeException {
		// TODO:  Call deleteDatasets 
		//boolean successful = false;

		if(vDatasetSummaryRecord.getDataFile()!=null){
			String deletedErrorMessage = deleteFilePath(vDatasetSummaryRecord.getDataFile());

			if(deletedErrorMessage!=null){
				throw new TimescopeException(deletedErrorMessage, "ERROR: Cannot delete data file!");
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

			//successful = true;
			DataSetDeleteInfo info = new DataSetDeleteInfo(
					1,
					rowDeleteSeconds, 
					totalDeletedDatasetMarkerIndices,
					Markerseconds,
					totalDeletedDatasetDnarunIndices,
					DNARunSeconds
					);

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

			String user = WebappUtil.getUser();

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
			logSB.append("\n\n["+user+"] Dataset delete result:\n"+info.toString());
			logSB.append("\n--------------------------------------------------");
			log.info(logSB.toString());
			return info;

		}
		catch(Exception e ){
			throw new TimescopeException(e.getLocalizedMessage(), "ERROR: Cannot delete dataset!" );
		}

	}

	private int deleteDatasetDnarunIndices(Integer dataset_id, Configuration configuration) throws TimescopeException {
		int deletedDatasetDnarunIndices = 0;
		try{
			Deletedatasetdnarunindices deleteDatasetDnarunIndices = new Deletedatasetdnarunindices();
			deleteDatasetDnarunIndices.setDatasetid(dataset_id);
			deleteDatasetDnarunIndices.attach(configuration);
			deleteDatasetDnarunIndices.execute();
			deletedDatasetDnarunIndices = deleteDatasetDnarunIndices.getReturnValue();
		}
		catch (Exception e){
			log.error("Cannot delete marker dnarun for dataset id"+ Integer.toString(dataset_id) +"\n"+ e.getStackTrace().toString());
			throw new TimescopeException(e.getLocalizedMessage(), "ERROR: Cannot delete dnarun indices!", e);
		}

		return deletedDatasetDnarunIndices;

	}

	private int deleteDatasetMarkerIndices(Integer dataset_id, Configuration configuration) throws TimescopeException {

		int deletedDatasetMarkerIndices = 0;
		try{

			Deletedatasetmarkerindices deleteDatasetMarkerIndices = new Deletedatasetmarkerindices();
			deleteDatasetMarkerIndices.setDatasetid(dataset_id);
			deleteDatasetMarkerIndices.attach(configuration);
			deleteDatasetMarkerIndices.execute();
			deletedDatasetMarkerIndices = deleteDatasetMarkerIndices.getReturnValue();

		}
		catch (Exception e){ 
			log.error("Cannot delete marker indices for dataset id"+ Integer.toString(dataset_id) +"\n"+ e.getStackTrace().toString());
			throw new TimescopeException(e.getLocalizedMessage(), "ERROR: Cannot delete dataset marker indices!", e);
		}

		return deletedDatasetMarkerIndices;

	}

	private String deleteFilePath(String string) {
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

	@Override
	public DataSetDeleteInfo deleteDatasets(List<VDatasetSummaryEntity> selectedDsList, List<DatasetSummaryEntity> datasetSummary,
			DatasetSummaryEntity datasetSummaryEntity, boolean removeCannotDelete, List<String> deleteList) throws TimescopeException  {

		//int dsCount = selectedDsList.size();
		//boolean successful = false;

		DSLContext context = WebappUtil.getDSLContext();

		//Try to deleteDataFiles
		List<VDatasetSummaryEntity> cannotDeleteFileDSList = new ArrayList<VDatasetSummaryEntity>();

		StringBuilder errorMessages = new StringBuilder();
		//StringBuilder errorDSNames = new StringBuilder();
		List<String> cannotDeleteList = new ArrayList<String>();

		if (removeCannotDelete && deleteList != null && deleteList.size() > 0 ) { //this is a repeat call
			for(VDatasetSummaryEntity ds : selectedDsList){
				if (deleteList.contains(ds.getDatasetName())) {
					cannotDeleteFileDSList.add(ds);
				}

			}

		} else {
			for(VDatasetSummaryEntity ds : selectedDsList){

				if(ds.getDataFile()!=null){	
					String errorMessage =  deleteFilePath(ds.getDataFile());

					if(errorMessage!=null){
						errorMessages.append(errorMessage+"\n");
						//errorDSNames.append(ds.getDatasetName()+"\n");
						cannotDeleteList.add(ds.getDatasetName()); 
						cannotDeleteFileDSList.add(ds);
					}
				}	

			}
		}

		if (removeCannotDelete) {
			selectedDsList.removeAll(cannotDeleteFileDSList);
		} else if (!removeCannotDelete && cannotDeleteList.size() > 0) {
			throw new CannotDeleteDataSetListException(cannotDeleteList);
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

		DataSetDeleteInfo info = new DataSetDeleteInfo(
				selectedDsList.size(), 
				rowDeleteSeconds, 
				totalDeletedDatasetMarkerIndices,
				markerseconds,
				totalDeletedDatasetDnarunIndices,
				dnaRunSeconds
				);
		String user = WebappUtil.getUser();

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
		logSB.append("\n\n["+user+"] Dataset delete result:\n"+ info.toString());
		logSB.append("\n--------------------------------------------------");
		log.info(logSB.toString());

		return info;
	}

	@Override
	public List<VDatasetSummaryEntity> getAllDatasetsBasedOnQuery(DatasetEntity datasetEntity, DatasetSummaryEntity datasetSummaryEntity) throws TimescopeException {
		int queryCount =0;
		int dsNameCount = 0;
		DSLContext context = WebappUtil.getDSLContext();

		List<VDatasetSummaryEntity> datasetList = null;
		try{ //c3.lastname as pi_contact,
			StringBuilder sb = new StringBuilder();
			StringBuilder sbFilteringCriteria = new StringBuilder();

			sb.append("select d.dataset_id, d.name as dataset_name, d.experiment_id, e.name as experiment_name, d.callinganalysis_id, a.name as callingnalysis_name, d.analyses, d.data_table, d.data_file, d.quality_table, d.quality_file, d.scores, c1.username created_by_username, d.created_date, c2.username as modified_by_username, d.modified_date, cv1.term as status_name, cv2.term as type_name, j.name as job_name, pi.contact_id as pi_id, pi.firstname as pi_firstname, pi.lastname as pi_lastname from dataset d left join experiment e on d.experiment_id=e.experiment_id left join project p on e.project_id=p.project_id join contact pi on p.pi_contact=pi.contact_id  left join analysis a on a.analysis_id=d.callinganalysis_id left join contact c1 on c1.contact_id=d.created_by left join contact c2 on c2.contact_id=d.modified_by left join cv cv1 on cv1.cv_id=d.status left join cv cv2 on cv2.cv_id=d.type_id left join job j on j.job_id=d.job_id ");

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
			throw new TimescopeException("There was an error while trying to retrieve datasets");
		}
		return datasetList;
	}

	private void checkPreviousAppends(int dsNameCount, int queryCount, StringBuilder sb){
		if(dsNameCount==0 && queryCount==0) sb.append(" where ");
		else sb.append(" and ");
	}


	@Override
	public String getDatawarehouseVersion() throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();

		String version = "";
		try{
			version = context.fetchOne("select value from gobiiprop where type_id in (select cvid from getCvId('version','gobii_datawarehouse', 1));").into(String.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve datasets", e);
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
	public List<VendorProtocolRecord> getVendorProtocolByPlatformId(List<PlatformRecord> iDlist) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<VendorProtocolRecord> list = null;
		try{
			String query = "select * from vendor_protocol vp left join protocol pr on vp.protocol_id = pr.protocol_id left join platform p on pr.platform_id = p.platform_id where p.platform_id "+ getIDsToString(iDlist)+";";
			list = context.fetch(query).into(VendorProtocolRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getVendorProtocolByPlatformId", e);
		}

		return list;
	}

	//	@Override
	//	public List<MapsetRecord> getMapsetsByPlatformTypeId(List<PlatformRecord> platformList) {
	//		// TODO Auto-generated method stub
	//		
	//		DSLContext context = WebappUtil.getDSLContext();
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
	public List<LinkageGroupRecord> getLinkageGroupByMapsetId(List<MapsetRecord> mapsetList) throws TimescopeException {

		DSLContext context = WebappUtil.getDSLContext();
		List<LinkageGroupRecord> list = null;
		try{
			String query = "Select * from linkage_group lg left join mapset map on lg.map_id = map.mapset_id where map.mapset_id "+ getIDsToString(mapsetList)+";";
			list = context.fetch(query).into(LinkageGroupRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getLinkageGroupByMapsetId", e);
		}
		return list;
	}

	@Override
	public List<ProjectRecord> getProjectsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<ProjectRecord> list = null;
		try{
			String query = "Select distinct on (prj.project_id) * from project prj left join experiment e on prj.project_id = e.project_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id where vp.vendor_protocol_id "+ getIDsToString(vendorProtocolList)+";";
			list = context.fetch(query).into(ProjectRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getProjectsByVendorProtocolID", e);
		}

		return list;
	}

	@Override
	public List<ProjectRecord> getProjectsByPlatformID(List<PlatformRecord> platformList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<ProjectRecord> list = null;
		try{

			String query = "Select distinct on (prj.project_id) * from project prj left join experiment e on prj.project_id = e.project_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id left join protocol pr on vp.protocol_id = pr.protocol_id left join platform pl on pr.platform_id = pl.platform_id where pl.platform_id "+ getIDsToString(platformList)+";";
			list = context.fetch(query).into(ProjectRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getProjectsByPlatformID", e);
		}

		return list;
	}
	//
	@Override
	public List<ExperimentRecord> getExperimentsByProjectID(List<ProjectRecord> projectList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<ExperimentRecord> list = null;
		try{
			String query = "Select * from experiment e left join project prj on e.project_id = prj.project_id where prj.project_id "+ getIDsToString(projectList)+";";
			list = context.fetch(query).into(ExperimentRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getExperimentsByProjectID", e);
		}
		return list;
	}

	@Override
	public List<ExperimentRecord> getExperimentsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<ExperimentRecord> list = null;
		try{

			String query = "Select distinct on (e.experiment_id) * from experiment e left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id where vp.vendor_protocol_id "+ getIDsToString(vendorProtocolList)+";";
			list = context.fetch(query).into(ExperimentRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getExperimentsByVendorProtocolID", e);

		}
		return list;
	}

	@Override
	public List<ExperimentRecord> getExperimentsByPlatformID(List<PlatformRecord> platformList) throws TimescopeException {

		DSLContext context = WebappUtil.getDSLContext();
		List<ExperimentRecord> list = null;
		try{

			String query = "Select distinct on (e.experiment_id) * from experiment e left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id left join protocol pr on vp.protocol_id = pr.protocol_id left join platform pl on pr.platform_id = pl.platform_id where pl.platform_id "+ getIDsToString(platformList)+";";
			list = context.fetch(query).into(ExperimentRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getExperimentsByPlatformID", e);
		}

		return list;
	}


	@Override
	public List<DatasetRecord> getDatasetsByVendorProtocolID(List<VendorProtocolRecord> vendorProtocolList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<DatasetRecord> list = null;
		try{
			String query = "Select distinct on (d.dataset_id) * from dataset d left join experiment e on d.experiment_id = e.experiment_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id where vp.vendor_protocol_id "+ getIDsToString(vendorProtocolList)+";";
			list = context.fetch(query).into(DatasetRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getDatasetsByVendorProtocolID", e);
		}

		return list;
	}

	@Override
	public List<DatasetRecord> getDatasetsByPlatformID(List<PlatformRecord> platformList) throws TimescopeException {

		DSLContext context = WebappUtil.getDSLContext();
		List<DatasetRecord> list = null;
		try{
			String query = "Select distinct on (d.dataset_id) * from dataset d left join experiment e on d.experiment_id = e.experiment_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id left join protocol pr on vp.protocol_id = pr.protocol_id left join platform pl on pr.platform_id = pl.platform_id where pl.platform_id "+ getIDsToString(platformList)+";";
			list = context.fetch(query).into(DatasetRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getDatasetsByPlatformID", e);

		}

		return list;
	}

	@Override
	public List<ReferenceRecord> getAllReferences() throws TimescopeException {

		DSLContext context = WebappUtil.getDSLContext();
		List<ReferenceRecord> list = null;
		try{
			list = context.select().from(REFERENCE).orderBy(REFERENCE.NAME).fetchInto(ReferenceRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve references", e);
		}

		return list;
	}

	@Override
	public List<MapsetRecord> getAllMapsetsByReferenceId(List<ReferenceRecord> referenceList) throws TimescopeException {

		DSLContext context = WebappUtil.getDSLContext();
		List<MapsetRecord> list = null;
		try{

			String query = "Select distinct on (m.mapset_id) * from mapset m left join reference r on m.reference_id = r.reference_id where r.reference_id "+ getIDsToString(referenceList)+";";
			list = context.fetch(query).into(MapsetRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getDatasetsByPlatformID", e);
		}

		return list;
	}

	@Override
	public List<LinkageGroupRecord> getAllLinkageGroupsByReferenceId(List<ReferenceRecord> referenceList) throws TimescopeException {

		DSLContext context = WebappUtil.getDSLContext();
		List<LinkageGroupRecord> list = null;
		try{

			String query = "Select distinct on (lg.linkage_group_id) * from linkage_group lg left join mapset m on lg.map_id = m.mapset_id left join reference r on m.reference_id = r.reference_id where r.reference_id "+ getIDsToString(referenceList)+";";
			list = context.fetch(query).into(LinkageGroupRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getDatasetsByPlatformID", e);

		}

		return list;
	}

	@Override
	public List<DatasetRecord> getDatasetsByExperimentIDandAnalysisId(List<ExperimentRecord> experimentList,
			List<AnalysisRecord> analysisList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<DatasetRecord> list = null;
		try{

			String query = "Select * from dataset d left join experiment e on d.experiment_id = e.experiment_id left join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) where e.experiment_id "+ getIDsToString(experimentList)+" and a.analysis_id "+ getIDsToString(analysisList)+";";
			list = context.fetch(query).into(DatasetRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getDatasetsByExperimentIDandAnalysisId", e);

		}

		return list;
	}

	@Override
	public List<DatasetRecord> getDatasetsByPlatformIDandAnalysisID(List<PlatformRecord> platformList,
			List<AnalysisRecord> analysisList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<DatasetRecord> list = null;
		try{

			String query = "Select distinct on (d.dataset_id) * from dataset d left join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) left join experiment e on d.experiment_id = e.experiment_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id left join protocol pr on vp.protocol_id = pr.protocol_id left join platform pl on pr.platform_id = pl.platform_id where pl.platform_id "+ getIDsToString(platformList)+" and a.analysis_id "+ getIDsToString(analysisList)+";";
			list = context.fetch(query).into(DatasetRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getDatasetsByPlatformIDandAnalysisID", e);
		}

		return list;
	}

	@Override
	public List<DatasetRecord> getAllDatasetsByAnalysisID(List<AnalysisRecord> analysesList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();

		List<DatasetRecord> list = null;
		try{
			String query = "Select distinct on (d.dataset_id) * from dataset d left join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) where a.analysis_id "+ getIDsToString(analysesList)+";";
			list = context.fetch(query).into(DatasetRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve datasets by analysis Id", e);
		}
		return list;
	}

	@Override
	public List<DatasetRecord> getDatasetsByVendorProtocolIDandAnalysisID(List<VendorProtocolRecord> vendorProtocolList,
			List<AnalysisRecord> analysesList) throws TimescopeException {

		DSLContext context = WebappUtil.getDSLContext();
		List<DatasetRecord> list = null;
		try{

			String query = "Select distinct on (d.dataset_id) * from dataset d join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) left join experiment e on d.experiment_id = e.experiment_id left join vendor_protocol vp on e.vendor_protocol_id = vp.vendor_protocol_id where vp.vendor_protocol_id "+ getIDsToString(vendorProtocolList)+" and a.analysis_id "+ getIDsToString(analysesList)+";";
			list = context.fetch(query).into(DatasetRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getDatasetsByVendorProtocolIDandAnalysisID", e);

		}

		return list;
	}

	@Override
	public List<DatasetRecord> getDatasetsByProjectIDandAnalysisID(List<ProjectRecord> projectList,
			List<AnalysisRecord> analysesList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<DatasetRecord> list = null;
		try{

			String query = "Select * from dataset d join analysis a on (a.analysis_id = ANY (d.analyses) OR a.analysis_id = d.callinganalysis_id) left join experiment e on d.experiment_id = e.experiment_id left join project prj on e.project_id = prj.project_id where prj.project_id "+ getIDsToString(projectList)+" and a.analysis_id "+ getIDsToString(analysesList)+";";
			list = context.fetch(query).into(DatasetRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getDatasetsByProjectIDandAnalysisID", e);
		}

		return list;
	}

	@Override
	public List<MarkerDetailDatasetEntity> getMarkerAssociatedDetailsForEachDataset(
			List<DatasetRecord> datasetList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
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
			throw new TimescopeException("There was an error while trying to retrieve marker details by dataset", e);
		}

		return list;
	}

	@Override
	public List<MarkerDetailLinkageGroupEntity> getAssociatedDetailsForEachLinkageGroup(
			List<LinkageGroupRecord> markerDetailLinkageGroupList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
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
			throw new TimescopeException("There was an error while trying to retrieve marker details by linkage group", e);

		}

		return list;
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

	
	
	@Override
	public List<PlatformRecord> getAllPlatforms() throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<PlatformRecord> platformList = null;
		try{
			platformList = context.select().from(PLATFORM).orderBy(PLATFORM.NAME).fetchInto(PlatformRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve platforms", e);
		}

		return platformList;
	}
	@Override
	public List<OrganizationRecord> getAllVendors() throws TimescopeException {
		DSLContext context =  WebappUtil.getDSLContext();
		List<OrganizationRecord> vendorList = null;
		try{
			vendorList = context.select().from(ORGANIZATION).orderBy(ORGANIZATION.NAME).fetchInto(OrganizationRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve ORGANIZATIONS", e);
		}

		return vendorList;

	}

	@Override
	public List<VendorProtocolRecord> getAllVendorProtocols() throws TimescopeException {
		DSLContext context =  WebappUtil.getDSLContext();
		List<VendorProtocolRecord> vendorProtocolList = null;
		try{
			vendorProtocolList = context.select().from(VENDOR_PROTOCOL).orderBy(VENDOR_PROTOCOL.NAME).fetchInto(VendorProtocolRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve VENDOR-PROTOCOLS", e);
		}

		return vendorProtocolList;
	}

	@Override
	public List<AnalysisRecord> getAllAnalyses() throws TimescopeException {

		DSLContext context =  WebappUtil.getDSLContext();
		List<AnalysisRecord> analysisList = new ArrayList<AnalysisRecord>();
		try{
			analysisList = context.select().from(ANALYSIS).orderBy(ANALYSIS.NAME).fetchInto(AnalysisRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve CALLING ANALYSIS", e);
		}

		return analysisList;
	}

	@Override
	public List<AnalysisRecord> getAllCallingAnalysis() throws TimescopeException {

		DSLContext context =  WebappUtil.getDSLContext();
		List<AnalysisRecord> analysisList = null;
		try{
			analysisList = context.select().from(ANALYSIS).where(ANALYSIS.ANALYSIS_ID.in(context.selectDistinct(DATASET.CALLINGANALYSIS_ID).from(DATASET))).fetchInto(AnalysisRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve ANALYSIS", e);
		}

		return analysisList;
	}

	@Override
	public List<ProjectRecord> getAllProjects() throws TimescopeException {

		DSLContext context =  WebappUtil.getDSLContext();
		List<ProjectRecord> projectList = null;
		try{
			projectList = context.select().from(PROJECT).orderBy(PROJECT.NAME).fetchInto(ProjectRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve PROJECT", e);
		}

		return projectList;
	}

	@Override
	public List<ExperimentRecord> getAllExperiments() throws TimescopeException {

		DSLContext context =  WebappUtil.getDSLContext();
		List<ExperimentRecord> experimentList = null;
		try{
			experimentList = context.select().from(EXPERIMENT).orderBy(EXPERIMENT.NAME).fetchInto(ExperimentRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve EXPERIMENT", e);
		}

		return experimentList;
	}

	@Override
	public List<MapsetRecord> getAllMapsets() throws TimescopeException {
		DSLContext context =  WebappUtil.getDSLContext();
		List<MapsetRecord> mapsetList = null;
		try{
			mapsetList = context.select().from(MAPSET).orderBy(MAPSET.NAME).fetchInto(MapsetRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve MAPSET", e);
		}

		return mapsetList;
	}

	@Override
	public List<LinkageGroupRecord> getAllLinkageGroups() throws TimescopeException {
		DSLContext context =  WebappUtil.getDSLContext();
		List<LinkageGroupRecord> linkageGroupList = null;
		try{

			linkageGroupList = context.select().from(LINKAGE_GROUP).orderBy(LINKAGE_GROUP.NAME).fetchInto(LinkageGroupRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve LINKAGE_GROUP", "ERROR", e);

		}
		return linkageGroupList;
	}

	@Override
	public List<DatasetRecord> getAllDatasets() throws TimescopeException {
		DSLContext context =  WebappUtil.getDSLContext();
		List<DatasetRecord> datasetList = null;
		try{
			datasetList = context.select().from(DATASET).orderBy(DATASET.NAME).fetchInto(DatasetRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve DATASET", e);
		}

		return datasetList;
	}
	
	
	@Override
	public List<DatasetRecord> getDatasetsByProjectID(List<ProjectRecord> projectList) throws TimescopeException {

		DSLContext context = WebappUtil.getDSLContext();
		List<DatasetRecord> list = null;
		try{

			String query = "Select * from dataset d left join experiment e on d.experiment_id = e.experiment_id left join project prj on e.project_id = prj.project_id where prj.project_id "+ getIDsToString(projectList)+";";
			list = context.fetch(query).into(DatasetRecord.class);

		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getDatasetsByProjectID", e);
		}

		return list;
	}

	@Override
	public List<DatasetRecord> getDatasetsByExperimentID(List<ExperimentRecord> experimentList) throws TimescopeException {
		DSLContext context = WebappUtil.getDSLContext();
		List<DatasetRecord> list = null;
		try{
			String query = "Select * from dataset d left join experiment e on d.experiment_id = e.experiment_id left join project prj on e.project_id = prj.project_id where e.experiment_id "+ getIDsToString(experimentList)+";";
			list = context.fetch(query).into(DatasetRecord.class);
		}catch(Exception e ){
			throw new TimescopeException("There was an error while trying to retrieve getDatasetsByExperimentID", e);
		}

		return list;
	}

}
