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
import org.gobiiproject.datatimescope.entity.DnasampleDeleteResultTableEntity;
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

public class DnasampleDeleteWarningModel {
	final static Logger log = Logger.getLogger(DnasampleDeleteWarningModel.class.getName());

	
	@Wire("#dnasampleDeleteWarningDialog")
	Window dnasampleDeleteWarningDialog;

	private List<DnasampleDeleteResultTableEntity> dnasampleDeleteResultTableEntityList = null;
	private int totalNumOfDnasamplesThatCantBeDeleted;
	private boolean itemsMoreThanTen = false;
	 
	@Init
	public void init(@ExecutionArgParam("dnasampleDeleteResultTableEntityList") List<DnasampleDeleteResultTableEntity> dnasampleDeleteResultTableEntityList, @ExecutionArgParam("totalNumOfDnasamplesThatCantBeDeleted") Integer totalNumOfDnasamplesThatCantBeDeleted ) {
		this.setDnasampleDeleteResultTableEntityList(dnasampleDeleteResultTableEntityList);
		this.setTotalNumOfDnasamplesThatCantBeDeleted(totalNumOfDnasamplesThatCantBeDeleted);
		
		if(totalNumOfDnasamplesThatCantBeDeleted>0) itemsMoreThanTen = true;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	public List<DnasampleDeleteResultTableEntity> getDnasampleDeleteResultTableEntityList() {
		return dnasampleDeleteResultTableEntityList;
	}

	public void setDnasampleDeleteResultTableEntityList(List<DnasampleDeleteResultTableEntity> dnasampleDeleteResultTableEntityList) {
		this.dnasampleDeleteResultTableEntityList = dnasampleDeleteResultTableEntityList;
	}

	@Command
	public void closeModalDialog(){
		
	    dnasampleDeleteWarningDialog.detach();
		
	}

	@Command("exportDnasamplesThatCantBeDeleted")
	public void exportDnasamplesThatCantBeDeleted() {

		ListIterator<DnasampleDeleteResultTableEntity> it = dnasampleDeleteResultTableEntityList.listIterator();
		StringBuffer buffMap = new StringBuffer();

		while (it.hasNext()) {

			DnasampleDeleteResultTableEntity next = it.next();

			if(it.nextIndex()==1){
				buffMap.append(next.getHeaderDelimitedBy("\t"));
			}
			buffMap.append(next.getAllDelimitedBy("\t"));

		}

		FileWriter fw;
		try {
			File file = new File("list_of_dnasamples_that_cant_be_deleted.txt");
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

	
	public int getTotalNumOfDnasamplesThatCantBeDeleted() {
		return totalNumOfDnasamplesThatCantBeDeleted;
	}

	public void setTotalNumOfDnasamplesThatCantBeDeleted(int totalNumOfDnasamplesThatCantBeDeleted) {
		this.totalNumOfDnasamplesThatCantBeDeleted = totalNumOfDnasamplesThatCantBeDeleted;
	}

	public boolean isItemsMoreThanTen() {
		return itemsMoreThanTen;
	}

	public void setItemsMoreThanTen(boolean itemsMoreThanTen) {
		this.itemsMoreThanTen = itemsMoreThanTen;
	}
}

