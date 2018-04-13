/* 
	@author Kevin Palis
	
	timescoper_id integer NOT NULL DEFAULT nextval('timescoper_timescoper_id_seq'::regclass),
    firstname text COLLATE pg_catalog."default" NOT NULL,
    lastname text COLLATE pg_catalog."default" NOT NULL,
    username text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default",
    role integer DEFAULT 3,
    CONSTRAINT pk_timescoper PRIMARY KEY (timescoper_id),
    CONSTRAINT username_key UNIQUE (username)
    
    Roles: 1=Super Admin, 2=Admin, 3=User (reserved for foreseen functionality requests)

*/
package org.gobiiproject.datatimescope.controller;

import java.util.Set;

import org.gobiiproject.datatimescope.entity.User;
import org.gobiiproject.datatimescope.services.AuthenticationService;
import org.gobiiproject.datatimescope.services.AuthenticationServiceChapter3Impl;
import org.gobiiproject.datatimescope.services.CommonInfoService;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.UserInfoService;
import org.gobiiproject.datatimescope.services.UserInfoServiceChapter3Impl;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

//For convenience, always static import your generated tables and jOOQ functions to decrease verbosity:
import static org.gobiiproject.datatimescope.db.generated.Tables.*;
import org.jooq.impl.DSL;
import org.jooq.*;

import java.sql.*;


public class UserController extends SelectorComposer<Component>{
	private static final long serialVersionUID = 1L;

	//wire components
	@Wire
	Label firstName;
	@Wire
	Label lastName;
	@Wire
	Label userName;
	@Wire
	Label email;
	@Wire
	Label role;
	
	//services
	AuthenticationService authService = new AuthenticationServiceChapter3Impl();
	UserInfoService userInfoService = new UserInfoServiceChapter3Impl();
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		
		ListModelList<String> roleModel = new ListModelList<String>(CommonInfoService.getRoleList());
		role.setValue(roleModel.get(1));
	}
	
	@Listen("onClick = #editProfile")
	public void doEditProfile(){
		Clients.showNotification("@SaveProfile.");
		UserCredential cre = authService.getUserCredential();
		User user = userInfoService.findUser(cre.getAccount());
		
		userInfoService.updateUser(user);
		
		Clients.showNotification("Your profile was updated.");
	}
	
	@Listen("onClick = #reloadProfile")
	public void doReloadProfile(){

		String userName = "timescoper";
        String password = "helloworld";
        String url = "jdbc:postgresql://localhost:5432/timescope_db2";
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
        }
	}

}
