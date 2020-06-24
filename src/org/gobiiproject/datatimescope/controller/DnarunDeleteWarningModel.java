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
import org.gobiiproject.datatimescope.entity.DnarunDeleteResultTableEntity;
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

public class DnarunDeleteWarningModel {
	final static Logger log = Logger.getLogger(DnarunDeleteWarningModel.class.getName());

	
	@Wire("#dnarunDeleteWarningDialog")
	Window dnarunDeleteWarningDialog;

	private List<DnarunDeleteResultTableEntity> dnarunDeleteResultTableEntityList = null;
	private int totalNumOfDnarunsThatCantBeDeleted;
	private boolean itemsMoreThanTen = false;
	 
	@Init
	public void init(@ExecutionArgParam("dnarunDeleteResultTableEntityList") List<DnarunDeleteResultTableEntity> dnarunDeleteResultTableEntityList, @ExecutionArgParam("totalNumOfDnarunsThatCantBeDeleted") Integer totalNumOfDnarunsThatCantBeDeleted ) {
		this.setDnarunDeleteResultTableEntityList(dnarunDeleteResultTableEntityList);
		this.setTotalNumOfDnarunsThatCantBeDeleted(totalNumOfDnarunsThatCantBeDeleted);
		
		if(totalNumOfDnarunsThatCantBeDeleted>0) itemsMoreThanTen = true;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	public List<DnarunDeleteResultTableEntity> getDnarunDeleteResultTableEntityList() {
		return dnarunDeleteResultTableEntityList;
	}

	public void setDnarunDeleteResultTableEntityList(List<DnarunDeleteResultTableEntity> dnarunDeleteResultTableEntityList) {
		this.dnarunDeleteResultTableEntityList = dnarunDeleteResultTableEntityList;
	}

	@Command
	public void closeModalDialog(){
		
	    dnarunDeleteWarningDialog.detach();
		
	}

	@Command("exportDnarunsThatCantBeDeleted")
	public void exportDnarunsThatCantBeDeleted() {

		ListIterator<DnarunDeleteResultTableEntity> it = dnarunDeleteResultTableEntityList.listIterator();
		StringBuffer buffMap = new StringBuffer();

		while (it.hasNext()) {

			DnarunDeleteResultTableEntity next = it.next();

			if(it.nextIndex()==1){
				buffMap.append(next.getHeaderDelimitedBy("\t"));
			}
			buffMap.append(next.getAllDelimitedBy("\t"));

		}

		FileWriter fw;
		try {
			File file = new File("list_of_dnaruns_that_cant_be_deleted.txt");
			fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(buffMap.toString());
			bw.flush();
			bw.close();

			InputStream is = new FileInputStream(file);
			Filedownload.save(is, "text/plain", file.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public int getTotalNumOfDnarunsThatCantBeDeleted() {
		return totalNumOfDnarunsThatCantBeDeleted;
	}

	public void setTotalNumOfDnarunsThatCantBeDeleted(int totalNumOfDnarunsThatCantBeDeleted) {
		this.totalNumOfDnarunsThatCantBeDeleted = totalNumOfDnarunsThatCantBeDeleted;
	}

	public boolean isItemsMoreThanTen() {
		return itemsMoreThanTen;
	}

	public void setItemsMoreThanTen(boolean itemsMoreThanTen) {
		this.itemsMoreThanTen = itemsMoreThanTen;
	}
}

