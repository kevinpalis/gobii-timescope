/* 

 */
package org.gobiiproject.datatimescope.services;


import static org.gobiiproject.datatimescope.db.generated.Tables.CV;
import static org.gobiiproject.datatimescope.db.generated.Tables.CVGROUP;
import static org.gobiiproject.datatimescope.db.generated.Tables.TIMESCOPER;
import static org.gobiiproject.datatimescope.db.generated.Tables.CONTACT;
import static org.gobiiproject.datatimescope.db.generated.Tables.PLATFORM;
import static org.gobiiproject.datatimescope.db.generated.Tables.DATASET;

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
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.db.generated.routines.Createtimescoper;
import org.gobiiproject.datatimescope.db.generated.routines.Crypt;
import org.gobiiproject.datatimescope.db.generated.routines.Deletedatasetdnarunindices;
import org.gobiiproject.datatimescope.db.generated.routines.Deletedatasetmarkerindices;
import org.gobiiproject.datatimescope.db.generated.routines.GenSalt2;
import org.gobiiproject.datatimescope.db.generated.routines.Getcvtermsbycvgroupname;
import org.gobiiproject.datatimescope.db.generated.routines.Gettimescoper;
import org.gobiiproject.datatimescope.db.generated.tables.Display;
import org.gobiiproject.datatimescope.db.generated.tables.VDatasetSummary;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VDatasetSummaryRecord;
import org.gobiiproject.datatimescope.entity.DatasetEntity;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.entity.VMarkerSummaryEntity;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

public class ViewModelServiceImpl implements ViewModelService,Serializable{
	private static final long serialVersionUID = 1L;
	final static Logger log = Logger.getLogger(ViewModelServiceImpl.class.getName());

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
			Sessions.getCurrent().setAttribute("dbContext", context);
			Sessions.getCurrent().setAttribute("serverInfo", serverInfo);

			isConnected = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			if(e.getLocalizedMessage().contains("FATAL: password authentication failed")){
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


			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

			Createtimescoper createTimescoper = createTimescoperFromRecord(userAccount);
			createTimescoper.execute(context.configuration());

			Messagebox.show("Successfully created new user!");
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
			Messagebox.show("Successfully deleted user!");

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

			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

			context.batchDelete(selectedUsersList).execute();

			successful = true;
			Messagebox.show("Successfully deleted users!");

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

			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

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
			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

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

			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");


			user =  context.fetchOne("select * from timescoper where username = '"+username+"';").into(TimescoperEntity.class);


		}catch(Exception e ){

			Messagebox.show("Invalid username", "ERROR", Messagebox.OK, Messagebox.ERROR);

		}

		return user;
	}

	@Override
	public List<TimescoperEntity> getAllOtherUsers(String username) {
		// TODO Auto-generated method stub

		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");
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

		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");
		List<ContactRecord> contactList = null;
		try{

			contactList = context.select().from(CONTACT).orderBy(CONTACT.LASTNAME).fetchInto(ContactRecord.class);

		}catch(Exception e ){

			Messagebox.show("There was an error while trying to retrieve users", "ERROR", Messagebox.OK, Messagebox.ERROR);

		}

		return contactList;
	}

	@Override
	public List<VDatasetSummaryEntity> getAllDatasets() {
		// TODO Auto-generated method stub
		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

		List<VDatasetSummaryEntity> datasetList = null;
		try{
			String query = "select d.dataset_id, d.name as dataset_name, d.experiment_id, e.name as experiment_name, d.callinganalysis_id, a.name as callingnalysis_name, d.analyses, d.data_table, d.data_file, d.quality_table, d.quality_file, d.scores, c1.username created_by_username, d.created_date, c2.username as modified_by_username, d.modified_date, cv1.term as status_name, cv2.term as type_name, j.name as job_name, pi.contact_id as pi_id, pi.firstname as pi_firstname, pi.lastname as pi_lastname from dataset d left join experiment e on d.experiment_id=e.experiment_id left join project p on e.project_id=p.project_id join contact pi on p.pi_contact=pi.contact_id  left join analysis a on a.analysis_id=d.callinganalysis_id left join contact c1 on c1.contact_id=d.created_by left join contact c2 on c2.contact_id=d.modified_by left join cv cv1 on cv1.cv_id=d.status left join cv cv2 on cv2.cv_id=d.type_id left join job j on j.job_id=d.job_id;";
			datasetList = context.fetch(query).into(VDatasetSummaryEntity.class);

			log.info("Submitted Query: "+query);
		}catch(Exception e ){

			Messagebox.show("There was an error while trying to retrieve datasets", "ERROR", Messagebox.OK, Messagebox.ERROR);

		}
		return datasetList;
	}

	@Override
	public List<ContactRecord> getContactsByRoles(Integer[] role) {
		// TODO Auto-generated method stub

		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");
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

			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

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
			Messagebox.show("Successfully updated user!");

		}catch(Exception e ){
			if(e.getMessage().contains("violates unique constraint")){
				Messagebox.show(userAccount.getUsername()+" already exists!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			}
			else Messagebox.show("Invalid username", "ERROR", Messagebox.OK, Messagebox.ERROR);

		}
		return successful;

	}

	@Override
	public boolean deleteDataset(VDatasetSummaryEntity vDatasetSummaryRecord) {
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
			Integer dataset_id = vDatasetSummaryRecord.getDatasetId();
			Configuration configuration = vDatasetSummaryRecord.configuration();


			//delete Marker.dataset_marker_idx
			deleteDatasetMarkerIndices(dataset_id, configuration);

			//delete Dnarun.dataset_dnarun idx entries for that particular dataset
			deleteDatasetDnarunIndices(dataset_id, configuration);


			DatasetRecord dr = new DatasetRecord();
			dr.setDatasetId(vDatasetSummaryRecord.getDatasetId());
			dr.attach(configuration);
			dr.delete();

			successful = true;
			Messagebox.show("Successfully deleted dataset!");

		}
		catch(Exception e ){
			Messagebox.show(e.getLocalizedMessage(), "ERROR: Cannot delete dataset!", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
		return successful;
	}

	private void deleteDatasetDnarunIndices(Integer dataset_id, Configuration configuration) {
		// TODO Auto-generated method stub
		try{
			Deletedatasetdnarunindices deleteDatasetDnarunIndices = new Deletedatasetdnarunindices();
			deleteDatasetDnarunIndices.setDatasetid(dataset_id);
			deleteDatasetDnarunIndices.attach(configuration);
			deleteDatasetDnarunIndices.execute();

			log.info("Deleted dataset dnarun indices for dataset id :"+ Integer.toString(dataset_id));
		}
		catch (Exception e){
			Messagebox.show(e.getLocalizedMessage(), "ERROR: Cannot delete dnarun indices!", Messagebox.OK, Messagebox.ERROR);
			log.error("Cannot delete marker dnarun for dataset id"+ Integer.toString(dataset_id) +"\n"+ e.getStackTrace().toString());
		}


	}

	private void deleteDatasetMarkerIndices(Integer dataset_id, Configuration configuration) {
		// TODO Auto-generated method stub

		try{

			Deletedatasetmarkerindices deleteDatasetMarkerIndices = new Deletedatasetmarkerindices();
			deleteDatasetMarkerIndices.setDatasetid(dataset_id);
			deleteDatasetMarkerIndices.attach(configuration);
			deleteDatasetMarkerIndices.execute();

			log.info("Deleted dataset marker indices for dataset id :"+ Integer.toString(dataset_id));
		}
		catch (Exception e){
			Messagebox.show(e.getLocalizedMessage(), "ERROR: Cannot delete dataset marker indices!", Messagebox.OK, Messagebox.ERROR);
			log.error("Cannot delete marker indices for dataset id"+ Integer.toString(dataset_id) +"\n"+ e.getStackTrace().toString());
		}


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
	public boolean deleteDatasets(List<VDatasetSummaryEntity> selectedDsList) {
		// TODO Auto-generated method stub

		int dsCount = selectedDsList.size();
		boolean successful = false;


		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

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
		for(VDatasetSummaryEntity ds : selectedDsList){

			//check which datasets are left just to be sure and display it later for the user to see 
			dsLeft.append(ds.getDatasetName()+"\n");


			Integer dataset_id = ds.getDatasetId();
			Configuration configuration = ds.configuration();


			//delete Marker.dataset_marker_idx
			deleteDatasetMarkerIndices(dataset_id, configuration);

			//delete Dnarun.dataset_dnarun idx entries for that particular dataset
			deleteDatasetDnarunIndices(dataset_id, configuration);
		}

		try{
			context.deleteFrom(DATASET).where(DATASET.DATASET_ID.in(selectedDsList
					.stream()
					.map(VDatasetSummaryEntity::getDatasetId)
					.collect(Collectors.toList())))
			.execute();

		}
		catch(Exception e ){

			e.printStackTrace();
		}

		Messagebox.show("Successfully deleted the following dataset(s):\n\n"+dsLeft.toString());

		if (selectedDsList.size()>0) successful=true;
		return successful;
	}

	@Override
	public List<VDatasetSummaryEntity> getAllDatasetsBasedOnQuery(DatasetEntity datasetEntity) {
		// TODO Auto-generated method stub
		int queryCount =0;
		int dsNameCount = 0;
		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

		List<VDatasetSummaryEntity> datasetList = null;
		try{ //c3.lastname as pi_contact,
			StringBuilder sb = new StringBuilder();


			sb.append("select d.dataset_id, d.name as dataset_name, d.experiment_id, e.name as experiment_name, d.callinganalysis_id, a.name as callingnalysis_name, d.analyses, d.data_table, d.data_file, d.quality_table, d.quality_file, d.scores, c1.username created_by_username, d.created_date, c2.username as modified_by_username, d.modified_date, cv1.term as status_name, cv2.term as type_name, j.name as job_name, pi.contact_id as pi_id, pi.firstname as pi_firstname, pi.lastname as pi_lastname from dataset d left join experiment e on d.experiment_id=e.experiment_id left join project p on e.project_id=p.project_id join contact pi on p.pi_contact=pi.contact_id  left join analysis a on a.analysis_id=d.callinganalysis_id left join contact c1 on c1.contact_id=d.created_by left join contact c2 on c2.contact_id=d.modified_by left join cv cv1 on cv1.cv_id=d.status left join cv cv2 on cv2.cv_id=d.type_id left join job j on j.job_id=d.job_id ");

			if (datasetEntity.getDatasetNamesAsCommaSeparatedString()!=null && !datasetEntity.getDatasetNamesAsCommaSeparatedString().isEmpty()){

				sb.append(" where LOWER(d.name) in ("+datasetEntity.getSQLReadyDatasetNames()+")");
				dsNameCount++;	
			}

			if (datasetEntity.getCreatedByContactRecord()!=null){

				checkPreviousAppends(dsNameCount, queryCount, sb);

				sb.append(" c1.contact_id="+Integer.toString(datasetEntity.getCreatedByContactRecord().getContactId()));
				queryCount++;
			}
			if (datasetEntity.getDatasetTypeRecord()!=null){

				checkPreviousAppends(dsNameCount, queryCount, sb);

				sb.append(" cv2.cv_id="+Integer.toString(datasetEntity.getDatasetTypeRecord().getCvId()));
				queryCount++;
			}
			if (datasetEntity.getPiRecord()!=null){

				checkPreviousAppends(dsNameCount, queryCount, sb);
				sb.append(" p.pi_contact="+Integer.toString(datasetEntity.getPiRecord().getContactId()));
				queryCount++;
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

					sb.append(" d.dataset_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
				}else{
					Integer ID = null;
					if(datasetEntity.getDatasetIDStartRange()!=null) ID = datasetEntity.getDatasetIDStartRange();
					else ID = datasetEntity.getDatasetIDEndRange();

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

					sb.append(" d.created_date between '"+sqlDateStart+"' and '"+sqlDateEnd+"' order by d.created_date");
				}
				else{ //check which is not null

					java.sql.Date sqlDate = null;

					if(datasetEntity.getCreationDateStart()!=null){
						sqlDate = new java.sql.Date(datasetEntity.getCreationDateStart().getTime());
					}else{
						sqlDate  = new java.sql.Date(datasetEntity.getCreationDateEnd().getTime());
					}

					sb.append(" d.created_date = '"+sqlDate+"' ");
				}

				queryCount++;
			}

			sb.append(";");
			String query = sb.toString();
			datasetList = context.fetch(query).into(VDatasetSummaryEntity.class);

			log.info("Submitted Query: "+query);
		}catch(Exception e ){

			Messagebox.show("There was an error while trying to retrieve datasets", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
		return datasetList;
	}

	private void checkPreviousAppends(int dsNameCount, int queryCount, StringBuilder sb) {
		// TODO Auto-generated method stub

		if(dsNameCount==0 && queryCount==0) sb.append(" where ");
		else sb.append(" and ");
	}

	@Override
	public List<VMarkerSummaryEntity> getAllMarkersBasedOnQuery(MarkerRecordEntity markerEntity) {
		// TODO Auto-generated method stub

		int queryCount =0;
		int dsNameCount = 0;
		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

		List<VMarkerSummaryEntity> markerList = null;
		try{ //c3.lastname as pi_contact,
			StringBuilder sb = new StringBuilder();


			sb.append("SELECT m.marker_id, m.platform_id, p.name AS platform_name, m.variant_id, m.name AS marker_name, m.code, m.ref, m.alts, m.sequence, m.reference_id, r.name AS reference_name, m.primers, m.strand_id, cv.term AS strand_name, m.status, m.probsets, m.dataset_marker_idx, m.props, m.dataset_vendor_protocol FROM marker m LEFT JOIN platform p ON m.platform_id = p.platform_id LEFT JOIN reference r ON m.reference_id = r.reference_id LEFT JOIN cv ON m.strand_id = cv.cv_id ");

			if (markerEntity.getMarkerNamesAsCommaSeparatedString()!=null && !markerEntity.getMarkerNamesAsCommaSeparatedString().isEmpty()){

				sb.append(" where LOWER(m.name) in ("+markerEntity.getSQLReadyMarkerNames()+")");
				dsNameCount++;	
			}
			if (markerEntity.getPlatform()!=null){

				checkPreviousAppends(dsNameCount, queryCount, sb);
				sb.append(" p.platform_id="+Integer.toString(markerEntity.getPlatform().getPlatformId()));
				queryCount++;
			}
			if (markerEntity.getMarkerIDStartRange()!=null || markerEntity.getMarkerIDEndRange()!=null){

				//check which is not null
				checkPreviousAppends(dsNameCount, queryCount, sb);

				if(markerEntity.getMarkerIDStartRange()!=null && markerEntity.getMarkerIDEndRange()!=null){ //if both is not null
					Integer lowerID = markerEntity.getMarkerIDStartRange();
					Integer higherID = markerEntity.getMarkerIDEndRange();

					if(lowerID.compareTo(higherID)>0){
						lowerID = markerEntity.getMarkerIDEndRange();
						higherID = markerEntity.getMarkerIDStartRange();
					}

					sb.append(" m.marker_id between "+Integer.toString(lowerID)+" and "+Integer.toString(higherID));
				}else{
					Integer ID = null;
					if(markerEntity.getMarkerIDStartRange()!=null) ID = markerEntity.getMarkerIDStartRange();
					else ID = markerEntity.getMarkerIDEndRange();

					sb.append(" m.marker_id = "+Integer.toString(ID));
				}

				queryCount++;
			}

			sb.append(";");
			String query = sb.toString();
			markerList = context.fetch(query).into(VMarkerSummaryEntity.class);

			log.info("Submitted Query: "+query);
		}catch(Exception e ){

			Messagebox.show("There was an error while trying to retrieve markers", "ERROR", Messagebox.OK, Messagebox.ERROR);

		}
		return markerList;

	}

	@Override
	public List<VMarkerSummaryEntity> getAllMarkers() {
		// TODO Auto-generated method stub

		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

		List<VMarkerSummaryEntity> markerList = null;
		try{
			String query = "SELECT m.marker_id, m.platform_id, p.name AS platform_name, m.variant_id, m.name AS marker_name, m.code, m.ref, m.alts, m.sequence, m.reference_id, r.name AS reference_name, m.primers, m.strand_id, cv.term AS strand_name, m.status, m.probsets, m.dataset_marker_idx, m.props, m.dataset_vendor_protocol FROM marker m LEFT JOIN platform p ON m.platform_id = p.platform_id LEFT JOIN reference r ON m.reference_id = r.reference_id LEFT JOIN cv ON m.strand_id = cv.cv_id;";
			markerList = context.fetch(query).into(VMarkerSummaryEntity.class);

			log.info("Submitted Query: "+query);
		}catch(Exception e ){

			Messagebox.show("There was an error while trying to retrieve markers", "ERROR", Messagebox.OK, Messagebox.ERROR);

		}
		return markerList;

	}

	@Override
	public boolean deleteMarkers(VMarkerSummaryEntity vMarkerSummaryEntity) {
		// TODO Auto-generated method stub
		return false;

	}

	@Override
	public boolean deleteMarkers(List<VMarkerSummaryEntity> selectedMarkerList) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public List<PlatformRecord> getAllPlatforms() {
		// TODO Auto-generated method stub

		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");
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
		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

		String version = "";
		try{
			version = context.fetchOne("select value from gobiiprop where type_id in (select cvid from getCvId('version','gobii_datawarehouse', 1));").into(String.class);

		}catch(Exception e ){

			Messagebox.show("There was an error while trying to retrieve datasets", "ERROR", Messagebox.OK, Messagebox.ERROR);

		}
		return version;
	}

}
