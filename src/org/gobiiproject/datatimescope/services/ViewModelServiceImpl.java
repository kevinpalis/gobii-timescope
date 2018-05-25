/* 

 */
package org.gobiiproject.datatimescope.services;

import static org.gobiiproject.datatimescope.db.generated.Tables.CV;
import static org.gobiiproject.datatimescope.db.generated.Tables.CVGROUP;
import static org.gobiiproject.datatimescope.db.generated.Tables.TIMESCOPER;
import static org.gobiiproject.datatimescope.db.generated.Tables.CONTACT;
import static org.gobiiproject.datatimescope.db.generated.Tables.DATASET;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.gobiiproject.datatimescope.db.generated.routines.Createtimescoper;
import org.gobiiproject.datatimescope.db.generated.routines.Crypt;
import org.gobiiproject.datatimescope.db.generated.routines.GenSalt2;
import org.gobiiproject.datatimescope.db.generated.routines.Getcvtermsbycvgroupname;
import org.gobiiproject.datatimescope.db.generated.routines.Gettimescoper;
import org.gobiiproject.datatimescope.db.generated.tables.VDatasetSummary;
import org.gobiiproject.datatimescope.db.generated.tables.records.ContactRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.CvRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VDatasetSummaryRecord;
import org.gobiiproject.datatimescope.entity.DatasetEntity;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.entity.TimescoperEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

public class ViewModelServiceImpl implements ViewModelService,Serializable{
	private static final long serialVersionUID = 1L;

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
				Messagebox.show(e.getLocalizedMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
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
			datasetList = context.fetch("select d.dataset_id, d.name as dataset_name, d.experiment_id, e.name as experiment_name, d.callinganalysis_id, a.name as callingnalysis_name, d.analyses, d.data_table, d.data_file, d.quality_table, d.quality_file, d.scores, c1.username created_by_username, d.created_date, c2.username as modified_by_username, d.modified_date, cv1.term as status_name, cv2.term as type_name, j.name as job_name from dataset d left join experiment e on d.experiment_id=e.experiment_id left join analysis a on a.analysis_id=d.callinganalysis_id left join contact c1 on c1.contact_id=d.created_by left join contact c2 on c2.contact_id=d.modified_by left join cv cv1 on cv1.cv_id=d.status left join cv cv2 on cv2.cv_id=d.type_id left join job j on j.job_id=d.job_id;").into(VDatasetSummaryEntity.class);

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
		try{
			DatasetRecord dr = new DatasetRecord();
			dr.setDatasetId(vDatasetSummaryRecord.getDatasetId());
			dr.attach(vDatasetSummaryRecord.configuration());

			dr.delete();
			
			successful = true;
			Messagebox.show("Successfully deleted dataset!");

		}
		catch(Exception e ){

			e.printStackTrace();
		}
		return successful;
	}

	@Override
	public boolean deleteDatasets(List<VDatasetSummaryEntity> selectedDsList) {
		// TODO Auto-generated method stub
		
		boolean successful = false;
		try{

			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");
			
			context.deleteFrom(DATASET).where(DATASET.DATASET_ID.in(selectedDsList
			        .stream()
			        .map(VDatasetSummaryEntity::getDatasetId)
			        .collect(Collectors.toList())))
			    .execute();
			
			successful = true;
			Messagebox.show("Successfully deleted users!");

		}
		catch(Exception e ){

			e.printStackTrace();
		}
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
			sb.append("select d.dataset_id, d.name as dataset_name, d.experiment_id, e.name as experiment_name, d.callinganalysis_id, a.name as callingnalysis_name, d.analyses, d.data_table, d.data_file, d.quality_table, d.quality_file, d.scores, c1.username created_by_username, d.created_date, c2.username as modified_by_username, d.modified_date, cv1.term as status_name, cv2.term as type_name, j.name as job_name from dataset d left join experiment e on d.experiment_id=e.experiment_id left join project p on e.project_id=p.project_id join contact c3 on p.pi_contact=c3.contact_id  left join analysis a on a.analysis_id=d.callinganalysis_id left join contact c1 on c1.contact_id=d.created_by left join contact c2 on c2.contact_id=d.modified_by left join cv cv1 on cv1.cv_id=d.status left join cv cv2 on cv2.cv_id=d.type_id left join job j on j.job_id=d.job_id ");

			if (datasetEntity.getDatasetNamesAsCommaSeparatedString()!=null && !datasetEntity.getDatasetNamesAsCommaSeparatedString().isEmpty()){
				
				sb.append(" where d.name in ("+datasetEntity.getSQLReadyDatasetNames()+")");
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
			if (datasetEntity.getDatasetIDStartRange()!=null && datasetEntity.getDatasetIDEndRange()!=null){

				checkPreviousAppends(dsNameCount, queryCount, sb);
				sb.append(" d.dataset_id between "+Integer.toString(datasetEntity.getDatasetIDStartRange())+" and "+Integer.toString(datasetEntity.getDatasetIDEndRange()));
				queryCount++;
			}

			if (datasetEntity.getCreationDateStart()!=null && datasetEntity.getCreationDateEnd()!=null){

				checkPreviousAppends(dsNameCount, queryCount, sb);
				
				java.sql.Date sqlDateStart = new java.sql.Date(datasetEntity.getCreationDateStart().getTime());
				java.sql.Date sqlDateEnd = new java.sql.Date(datasetEntity.getCreationDateEnd().getTime());
				
				sb.append(" d.created_date between '"+sqlDateStart+"' and '"+sqlDateEnd+"' order by d.created_date");
				queryCount++;
			}
//			if(queryCount>0 && dsNameCount>0) sb.append(") ");
			sb.append(";");
			String query = sb.toString();
			datasetList = context.fetch(query).into(VDatasetSummaryEntity.class);

		}catch(Exception e ){

			Messagebox.show("There was an error while trying to retrieve datasets", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
		return datasetList;
	}

	private void checkPreviousAppends(int dsNameCount, int queryCount, StringBuilder sb) {
		// TODO Auto-generated method stub

//		if(dsNameCount>0 && queryCount==0) sb.append(" OR ( where"); 

		if(dsNameCount>0 && queryCount==0) sb.append(" and "); 
		else if(dsNameCount==0 && queryCount==0) sb.append(" where ");
		else sb.append(" and ");
	}

}
