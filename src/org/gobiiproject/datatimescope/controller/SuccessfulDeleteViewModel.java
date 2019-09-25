package org.gobiiproject.datatimescope.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.entity.MarkerRecordEntity;
import org.gobiiproject.datatimescope.entity.RowColEntity;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class SuccessfulDeleteViewModel {
	final static Logger log = Logger.getLogger(SuccessfulDeleteViewModel.class.getName());

	@Wire("#deleteSuccessfulWindow")
	Window deleteSuccessfulWindow;

	private List<String> successMessagesAsList;
	private List<RowColEntity> filterEntity;
	 
	@Init
	public void init(@ExecutionArgParam("successMessagesAsList")  List<String> successMessagesAsList, @ExecutionArgParam("filterEntity") List<RowColEntity> filterEntity ) {
		this.successMessagesAsList = successMessagesAsList;
		this.setFilterEntity(filterEntity);
		
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	public void exit(){
		
	    deleteSuccessfulWindow.detach();
		
	}

    public List<RowColEntity> getFilterEntity() {
        return filterEntity;
    }

    public void setFilterEntity(List<RowColEntity> filterEntity) {
        this.filterEntity = filterEntity;
    }

    public List<String> getSuccessMessagesAsList() {
        return successMessagesAsList;
    }

    public void setSuccessMessagesAsList(List<String> successMessagesAsList) {
        this.successMessagesAsList = successMessagesAsList;
    }

}

