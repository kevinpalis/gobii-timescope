package org.gobiiproject.datatimescope.services;

import java.util.Map;
import java.util.List;

import org.gobiiproject.datatimescope.entity.VMarkerSummaryEntity;

public class MarkersFilterResult {
	
	private List<VMarkerSummaryEntity> results;
	private Map<String, Object> meta;
	public MarkersFilterResult(List<VMarkerSummaryEntity> results, Map<String, Object> meta) {
		super();
		this.results = results;
		this.meta = meta;
	}
	public List<VMarkerSummaryEntity> getResults() {
		return results;
	}
	public void setResults(List<VMarkerSummaryEntity> results) {
		this.results = results;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
	
	

}
