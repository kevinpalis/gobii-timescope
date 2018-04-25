/* 

 */
package org.gobiiproject.datatimescope.services;

import static org.gobiiproject.datatimescope.db.generated.Tables.CV;
import static org.gobiiproject.datatimescope.db.generated.Tables.TIMESCOPER;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
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

			Result<Record> result = context.select().from(CV).where(CV.CV_ID.lessThan(11)).fetch();

			for (Record r : result) {
				Integer id = r.getValue(CV.CV_ID);
				String term = r.getValue(CV.TERM);

				System.out.println("CV_id: " + id + " Term: " + term );
			}

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

			userAccount.store();
			
			context.executeInsert(userAccount);
			
//			context.insertInto(TIMESCOPER)
//			.set(TIMESCOPER.EMAIL, userAccount.getEmail())
//			.set(TIMESCOPER.FIRSTNAME, userAccount.getFirstname())
//			.set(TIMESCOPER.LASTNAME, userAccount.getLastname())
//			.set(TIMESCOPER.PASSWORD, userAccount.getPassword())
//			.set(TIMESCOPER.ROLE, userAccount.getRole())
//			.set(TIMESCOPER.USERNAME, userAccount.getUsername())
//			.execute();

			successful = true;
			Messagebox.show("Successfully created new user!");

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

	public synchronized TimescoperRecord findUser(String username){

		TimescoperRecord user = new TimescoperRecord();

		try{

			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");


			user = (TimescoperRecord) context.select().from(TIMESCOPER).where(TIMESCOPER.USERNAME.equal(username)).limit(1).fetchOne();

//			System.out.println("retrieved user.");
//			user.setEmail(r.getValue(TIMESCOPER.EMAIL));
//			user.setFirstName(r.getValue(TIMESCOPER.FIRSTNAME));
//			user.setLastName(r.getValue(TIMESCOPER.LASTNAME));
//			user.setPassword(r.getValue(TIMESCOPER.PASSWORD));
//			user.setRoleId(r.getValue(TIMESCOPER.ROLE));
//			user.setUserName(r.getValue(TIMESCOPER.USERNAME));

		}catch(Exception e ){

			Messagebox.show("Invalid username", "ERROR", Messagebox.OK, Messagebox.ERROR);
			
		}

		return user;
	}

	@Override
	public List<TimescoperRecord> getAllOtherUsers(String username) {
		// TODO Auto-generated method stub
		

		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");
		List<TimescoperRecord> userList = context.select().from(TIMESCOPER).where(TIMESCOPER.USERNAME.notEqual(username)).fetchInto(TimescoperRecord.class);

		
		return userList;
	}

	@Override
	public boolean updateUser(TimescoperRecord userAccount) {
		// TODO Auto-generated method stub

		boolean successful = false;
		try{

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



}
