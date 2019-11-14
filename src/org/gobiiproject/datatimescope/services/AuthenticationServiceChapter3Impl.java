/* 
	
*/
package org.gobiiproject.datatimescope.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.entity.DatasetSummaryEntity;
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
    	TimescoperRecord user = userInfoService.loginTimescoper(nm, pd);

        //check if not empty and if password matches
		if(user.getUsername()==null){
			return false;
		}
		
		//if still here, then checks passed. Update Sessions
        Session sess = Sessions.getCurrent();
        UserCredential cre = new UserCredential(user.getUsername(), user.getRole());
       
        List<DatasetSummaryEntity> datasetSummary = new ArrayList<DatasetSummaryEntity>(); 
        sess.setAttribute("userCredential",cre);
        sess.setAttribute("datasetSummary",datasetSummary);
        sess.setAttribute("markerSummary",datasetSummary);
 
        return true;
    }
 
    @Override
    public void logout() {
        Session sess = Sessions.getCurrent();
        sess.removeAttribute("userCredential");
    }
}
