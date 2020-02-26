package org.gobiiproject.datatimescope.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.exceptions.DatabaseException;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

public class WebappUtil {

	public WebappUtil() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void showErrorDialog(TimescopeException exc) {
		try {
			Messagebox.show(exc.getMessage(), exc.getErrorTitle(), Messagebox.OK, Messagebox.ERROR);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DSLContext getDSLContext() {
		Configuration contextConfiguration = (Configuration) Sessions.getCurrent().getAttribute("contextConfiguration");
		DSLContext context = DSL.using(contextConfiguration);
		return context;
	}

	public static boolean connectToDB(String userName, String password, ServerInfo serverInfo) throws TimescopeException {
		boolean isConnected = false;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace(); //this is actually a show stopper:
			throw new DatabaseException("Missing database driver, please contact system administrator.");
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
				throw new DatabaseException("Invalid DB username/password.\n\nPlease contact your system admin.", e);
			}
			else if(e.getLocalizedMessage().contains("FATAL: password authentication failed")){
				throw new DatabaseException("Invalid username or password", e);
			}
			else if(e.getMessage().contains("connect(Unknown Source)")){
				throw new TimescopeException("Host name not found", e); 
			}
			else{
				throw new DatabaseException(e.getLocalizedMessage(), "Connect To Database Error", e);
			}
		}

		return isConnected;
	}
	
	public static String getUser() {
		UserCredential cre = (UserCredential) Sessions.getCurrent().getAttribute("userCredential");
		return cre.getAccount();
	}
}
