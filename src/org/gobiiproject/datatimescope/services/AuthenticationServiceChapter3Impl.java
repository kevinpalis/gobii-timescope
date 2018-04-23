/* 
	
*/
package org.gobiiproject.datatimescope.services;

import java.io.Serializable;

import org.gobiiproject.datatimescope.entity.User;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

public class AuthenticationServiceChapter3Impl implements AuthenticationService,Serializable{
	private static final long serialVersionUID = 1L;


    ViewModelService userInfoService = new ViewModelServiceImpl();
	
	public UserCredential getUserCredential(){
		Session sess = Sessions.getCurrent();
		UserCredential cre = (UserCredential)sess.getAttribute("userCredential");
		
		return cre;
	}
     
    @Override
    public boolean login(String nm, String pd) {
    	
    	//retrieve userInfo from database
        User user = userInfoService.findUser(nm);

        //check if not empty and if password matches
		if(user.getUserName()==null) return false;
		else if (!user.getPassword().equals(pd)){
			Messagebox.show("Invalid password!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			return false;
		}
         
		//if still here, then checks passed. Update Sessions
        Session sess = Sessions.getCurrent();
        UserCredential cre = new UserCredential(user.getUserName(),user.getRoleId());
        user.setPassword("removePasswordInfo");
        
        sess.setAttribute("userCredential",cre);
        sess.setAttribute("userInfo",user);
         
 
        return true;
    }
 
    @Override
    public void logout() {
        Session sess = Sessions.getCurrent();
        sess.removeAttribute("userCredential");
    }
}
