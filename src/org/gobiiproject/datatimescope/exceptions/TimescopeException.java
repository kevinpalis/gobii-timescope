package org.gobiiproject.datatimescope.exceptions;

public class TimescopeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorTitle;

	public TimescopeException(String message, String title, Throwable cause) {
		super(message, cause);
		this.setErrorTitle(title);
	}
	
	public TimescopeException(String message, String title) {
		this(message, title, null);
	}
	

	public TimescopeException(String message) {
		this(message, "Error");
		// TODO Auto-generated constructor stub
	}

	public TimescopeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public TimescopeException(String message, Throwable cause) {
		this(message, "Error", cause);
		// TODO Auto-generated constructor stub
	}

	public TimescopeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}


	public String getErrorTitle() {
		return errorTitle;
	}


	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}

}
