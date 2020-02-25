package org.gobiiproject.datatimescope.services;

import java.util.Map;

public class DeleteMarkersResult {
	private boolean success;
	private Map<String, Object> meta;
	public DeleteMarkersResult(boolean success, Map<String, Object> meta) {
		super();
		this.success = success;
		this.meta = meta;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
	
	

}
