package org.gobiiproject.datatimescope.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.gobiiproject.datatimescope.entity.Dataset;
import org.gobiiproject.datatimescope.entity.User;
import org.gobiiproject.datatimescope.services.CommonInfoService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

public class DatasetViewModel {
	//UI component

	private ListModelList<Dataset> datasetList;
	@Init
	public void init() {
		setDatasetList(getDummyDatasets());
	}
	private ListModelList<Dataset> getDummyDatasets() {
		// TODO Auto-generated method stub
		

		ListModelList<Dataset> datasets = new ListModelList<Dataset>();

		int total = 30;
		int i = 0;

		Dataset newData = new Dataset();
		while (i<total){
			newData  = new Dataset();
			
			newData.setId(i);
			newData.setCreatedDate(Integer.toString(i)+"-April-2018");
			newData.setDataFile("randomFileName");
			newData.setDataTable("Dummy Data Table");
			newData.setExpId(i);
			newData.setName("Dataset"+Integer.toString(i) );
			newData.setCreatedBy("Dummy, Name"+Integer.toString(i) );
			datasets.add(newData);
			i++;
		}

		return datasets;
		
	}
	public ListModelList<Dataset> getDatasetList() {
		return datasetList;
	}
	public void setDatasetList(ListModelList<Dataset> datasetList) {
		this.datasetList = datasetList;
	}

}
