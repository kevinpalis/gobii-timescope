/* 

 */
package org.gobiiproject.datatimescope.services;

import static org.gobiiproject.datatimescope.db.generated.Tables.CV;
import static org.gobiiproject.datatimescope.db.generated.Tables.TIMESCOPER;
import static org.gobiiproject.datatimescope.db.generated.Tables.DATASET;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.gobiiproject.datatimescope.db.generated.routines.Createtimescoper;
import org.gobiiproject.datatimescope.db.generated.routines.Crypt;
import org.gobiiproject.datatimescope.db.generated.routines.GenSalt2;
import org.gobiiproject.datatimescope.db.generated.routines.Gettimescoper;
import org.gobiiproject.datatimescope.db.generated.tables.VDatasetSummary;
import org.gobiiproject.datatimescope.db.generated.tables.records.DatasetRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.db.generated.tables.records.VDatasetSummaryRecord;
import org.gobiiproject.datatimescope.entity.ServerInfo;
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
	public boolean createNewUser(TimescoperRecord userAccount) {
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


	private Createtimescoper createTimescoperFromRecord(TimescoperRecord userAccount) {
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
	public boolean deleteUser(TimescoperRecord userAccount) {

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
	public boolean deleteUsers(ListModelList<TimescoperRecord> selectedUsersList) {
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
	

	public synchronized TimescoperRecord getUserInfo(String username){

		TimescoperRecord user = new TimescoperRecord();

		try{

			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");


			user = (TimescoperRecord) context.select().from(TIMESCOPER).where(TIMESCOPER.USERNAME.equal(username)).limit(1).fetchOne();


		}catch(Exception e ){

			Messagebox.show("Invalid username", "ERROR", Messagebox.OK, Messagebox.ERROR);

		}

		return user;
	}

	@Override
	public List<TimescoperRecord> getAllOtherUsers(String username) {
		// TODO Auto-generated method stub

		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");
		List<TimescoperRecord> userList = null;
		try{
			
			userList = context.select().from(TIMESCOPER).where(TIMESCOPER.USERNAME.notEqual(username)).fetchInto(TimescoperRecord.class);

		}catch(Exception e ){

			Messagebox.show("There was an error whil trying to retrieve users", "ERROR", Messagebox.OK, Messagebox.ERROR);

		}

		return userList;
	}

	@Override
	public List<VDatasetSummaryRecord> getAllDatasets() {
		// TODO Auto-generated method stub
		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

		List<VDatasetSummaryRecord> datasetList = null ;
		try{
			datasetList = context.fetch("select d.dataset_id, d.name as dataset_name, d.experiment_id, e.name as experiment_name, d.callinganalysis_id, a.name as callingnalysis_name, d.analyses, d.data_table, d.data_file, d.quality_table, d.quality_file, d.scores, c1.username created_by_username, d.created_date, c2.username as modified_by_username, d.modified_date, cv1.term as status_name, cv2.term as type_name, j.name as job_name from dataset d left join experiment e on d.experiment_id=e.experiment_id left join analysis a on a.analysis_id=d.callinganalysis_id left join contact c1 on c1.contact_id=d.created_by left join contact c2 on c2.contact_id=d.modified_by left join cv cv1 on cv1.cv_id=d.status left join cv cv2 on cv2.cv_id=d.type_id left join job j on j.job_id=d.job_id;").into(VDatasetSummaryRecord.class);

		}catch(Exception e ){

			Messagebox.show("There was an error whil trying to retrieve datasets", "ERROR", Messagebox.OK, Messagebox.ERROR);

		}
		return datasetList;
	}

	@Override
	public boolean updateUser(TimescoperRecord userAccount) {
		// TODO Auto-generated method stub

		boolean successful = false;
		try{

			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");
			
			if(userAccount.changed(4)){ // check if pswrd was changed, it's the 4th field in TimescoperRecord
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
	public boolean deleteDataset(VDatasetSummaryRecord vDatasetSummaryRecord) {
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
	public boolean deleteDatasets(List<VDatasetSummaryRecord> selectedDsList) {
		// TODO Auto-generated method stub
		
		boolean successful = false;
		try{

			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");
			
			context.deleteFrom(DATASET).where(DATASET.DATASET_ID.in(selectedDsList
			        .stream()
			        .map(VDatasetSummaryRecord::getDatasetId)
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

}
