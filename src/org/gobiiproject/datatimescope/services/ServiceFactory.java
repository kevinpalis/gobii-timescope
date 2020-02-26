package org.gobiiproject.datatimescope.services;

import org.gobiiproject.datatimescope.services.impls.*;

public class ServiceFactory {
	
	public static ViewModelService getViewModelService() {
		return new ViewModelServiceImpl();
	}
	
	public static MarkerModelService getMarkerModelService() {
		return new MarkerModelServiceImpl();
	}

	public static AuthenticationService getAuthenticationService() {
		// TODO Auto-generated method stub
		return new AuthenticationServiceImpl();
	}


}
