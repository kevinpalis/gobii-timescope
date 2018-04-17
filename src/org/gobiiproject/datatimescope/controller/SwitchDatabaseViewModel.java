package org.gobiiproject.datatimescope.controller;

import static org.gobiiproject.datatimescope.db.generated.Tables.CV;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class SwitchDatabaseViewModel {
	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String password;
	
	@AfterCompose
	public void afterCompose() {
		  
	      setHost("localhost");
	      setPort("5432");
	      setDbName("timescope_db2");
	      setUserName("timescoper");
	      setPassword("helloworld");
	}
	
	@Command
    public void openDatabaseInfoDialog() {
		  Window window = (Window)Executions.createComponents(
	                "/switch_database.zul", null, null);
	      window.doModal();
    }
	
	@Command
	public void connectToDatabase(){

		String userName = getUserName();
        String password = getPassword();
        String url = "jdbc:postgresql://"+getHost()+":"+getPort()+"/"+getDbName();
        try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			 //TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try  {
            Connection conn = DriverManager.getConnection(url, userName, password);        
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = context.select().from(CV).where(CV.CV_ID.lessThan(11)).fetch();
            
            for (Record r : result) {
                Integer id = r.getValue(CV.CV_ID);
                String term = r.getValue(CV.TERM);

                System.out.println("CV_id: " + id + " Term: " + term );
            }
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
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pw) {
		this.password = pw;
	}
}
