package org.gobiiproject.datatimescope.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.entity.MarkerDeleteResultTableEntity;
import org.gobiiproject.datatimescope.entity.VDatasetSummaryEntity;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class MarkerDeleteWarningModel {
	final static Logger log = Logger.getLogger(MarkerDeleteWarningModel.class.getName());

	
	@Wire("#modalDialog")
	Window modalDialog;

	private List<MarkerDeleteResultTableEntity> markerDeleteResultTableEntityList = null;
	private int totalNumOfMarkersThatCantBeDeleted;
	private boolean itemsMoreThanTen = false;
	 
	@Init
	public void init(@ExecutionArgParam("markerDeleteResultTableEntityList") List<MarkerDeleteResultTableEntity> markerDeleteResultTableEntityList, @ExecutionArgParam("totalNumOfMarkersThatCantBeDeleted") Integer totalNumOfMarkersThatCantBeDeleted ) {
		this.setMarkerDeleteResultTableEntityList(markerDeleteResultTableEntityList);
		this.setTotalNumOfMarkersThatCantBeDeleted(totalNumOfMarkersThatCantBeDeleted);
		
		if(totalNumOfMarkersThatCantBeDeleted>0) itemsMoreThanTen = true;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	public List<MarkerDeleteResultTableEntity> getMarkerDeleteResultTableEntityList() {
		return markerDeleteResultTableEntityList;
	}

	public void setMarkerDeleteResultTableEntityList(List<MarkerDeleteResultTableEntity> markerDeleteResultTableEntityList) {
		this.markerDeleteResultTableEntityList = markerDeleteResultTableEntityList;
	}

	@Command
	public void closeModalDialog(){
		
		modalDialog.detach();
		
	}

	@Command("exportMarkersThatCantBeDeleted")
	public void exportMarkersThatCantBeDeleted() {

		ListIterator<MarkerDeleteResultTableEntity> it = markerDeleteResultTableEntityList.listIterator();
		StringBuffer buffMap = new StringBuffer();

		while (it.hasNext()) {

			MarkerDeleteResultTableEntity next = it.next();

			if(it.nextIndex()==1){
				buffMap.append(next.getHeaderDelimitedBy(","));
			}
			buffMap.append(next.getAllDelimitedBy(","));

		}

		FileWriter fw;
		try {
			File file = new File("list_of_markers_that_cant_be_deleted.csv");
			fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(buffMap.toString());
			bw.flush();
			bw.close();

			InputStream is = new FileInputStream(file);
			Filedownload.save(is, "text/csv", file.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public int getTotalNumOfMarkersThatCantBeDeleted() {
		return totalNumOfMarkersThatCantBeDeleted;
	}

	public void setTotalNumOfMarkersThatCantBeDeleted(int totalNumOfMarkersThatCantBeDeleted) {
		this.totalNumOfMarkersThatCantBeDeleted = totalNumOfMarkersThatCantBeDeleted;
	}

	public boolean isItemsMoreThanTen() {
		return itemsMoreThanTen;
	}

	public void setItemsMoreThanTen(boolean itemsMoreThanTen) {
		this.itemsMoreThanTen = itemsMoreThanTen;
	}
}

