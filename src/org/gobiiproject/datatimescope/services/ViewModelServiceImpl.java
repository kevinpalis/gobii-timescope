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

import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.entity.User;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

public class ViewModelServiceImpl implements ViewModelService,Serializable{
	private static final long serialVersionUID = 1L;
	static protected List<User> userList = new ArrayList<User>();  
	static{
		userList.add(new User("anonymous","lastname", "anonymous","password","a@your.com", 1));
		userList.add(new User("anonymous2","lastname", "anonymous2","password","b@your.com", 1));
		userList.add(new User("anonymous3","lastname", "anonymous3","password","c@your.com", 1));
	}

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
			Sessions.getCurrent().setAttribute("serverInfo", context);

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
			else if(e.getCause().getStackTrace()[0].toString().contains("connect(Unknown Source)")){
				Messagebox.show("Host not found.", "Error", Messagebox.OK, Messagebox.ERROR);
			}
			else{
				Messagebox.show(e.getLocalizedMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
			}
		}
		
		return isConnected;
	}

	@Override
	public void createNewUser(User userAccount) {
		// TODO Auto-generated method stub

		try{

			DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");
			context.insertInto(TIMESCOPER)
			.set(TIMESCOPER.EMAIL, userAccount.getEmail())
			.set(TIMESCOPER.FIRSTNAME, userAccount.getFirstName())
			.set(TIMESCOPER.LASTNAME, userAccount.getLastName())
			.set(TIMESCOPER.PASSWORD, userAccount.getPassword())
			.set(TIMESCOPER.ROLE, userAccount.getRoleId())
			.set(TIMESCOPER.USERNAME, userAccount.getUserName())
			.execute();

			Messagebox.show("Successfully created new user!");

		}
		catch(Exception e ){
			if(e.getMessage().contains("violates unique constraint")){
				Messagebox.show(userAccount.getUserName()+" already exists!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			}
			else Messagebox.show(e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}

	}

	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	@SuppressWarnings("deprecation")
	public synchronized User findUser(String username){
		User user = new User();
		DSLContext context = (DSLContext) Sessions.getCurrent().getAttribute("dbContext");

		Result<Record> result = context.select().from(TIMESCOPER).where(TIMESCOPER.USERNAME.equals(username)).fetch();
		//.from(CV).where(CV.CV_ID.lessThan(11)).fetch();

		for (Record r : result) {
			user.setEmail(r.getValue(TIMESCOPER.EMAIL));
			user.setFirstName(r.getValue(TIMESCOPER.FIRSTNAME));
			user.setLastName(r.getValue(TIMESCOPER.LASTNAME));
			user.setPassword(r.getValue(TIMESCOPER.PASSWORD));
			user.setRoleId(r.getValue(TIMESCOPER.ROLE));
			user.setUserName(r.getValue(TIMESCOPER.USERNAME));
		}

		return user;
	}

	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	public synchronized User updateUser(User user){
		int s = userList.size();
		for(int i=0;i<s;i++){
			User u = userList.get(i);
			if(user.getUserName().equals(u.getUserName())){
				userList.set(i,u = User.clone(user));
				return u;
			}
		}
		throw new RuntimeException("user not found "+user.getUserName());
	}


}
