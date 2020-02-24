package org.gobiiproject.datatimescope.exceptions;

public class DatabaseException extends TimescopeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public DatabaseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public DatabaseException(String message, String title) {
		super(message, title);
		// TODO Auto-generated constructor stub
	}
	
	public DatabaseException(String message, String title, Throwable cause) {
		super(message, title, cause);
		// TODO Auto-generated constructor stub
	}


	public DatabaseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
