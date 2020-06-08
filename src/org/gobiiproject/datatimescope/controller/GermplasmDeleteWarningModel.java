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
import org.gobiiproject.datatimescope.entity.GermplasmDeleteResultTableEntity;
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

public class GermplasmDeleteWarningModel {
	final static Logger log = Logger.getLogger(GermplasmDeleteWarningModel.class.getName());

	
	@Wire("#germplasmDeleteWarningDialog")
	Window germplasmDeleteWarningDialog;

	private List<GermplasmDeleteResultTableEntity> germplasmDeleteResultTableEntityList = null;
	private int totalNumOfGermplasmsThatCantBeDeleted;
	private boolean itemsMoreThanTen = false;
	 
	@Init
	public void init(@ExecutionArgParam("germplasmDeleteResultTableEntityList") List<GermplasmDeleteResultTableEntity> germplasmDeleteResultTableEntityList, @ExecutionArgParam("totalNumOfGermplasmsThatCantBeDeleted") Integer totalNumOfGermplasmsThatCantBeDeleted ) {
		this.setGermplasmDeleteResultTableEntityList(germplasmDeleteResultTableEntityList);
		this.setTotalNumOfGermplasmsThatCantBeDeleted(totalNumOfGermplasmsThatCantBeDeleted);
		
		if(totalNumOfGermplasmsThatCantBeDeleted>0) itemsMoreThanTen = true;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	public List<GermplasmDeleteResultTableEntity> getGermplasmDeleteResultTableEntityList() {
		return germplasmDeleteResultTableEntityList;
	}

	public void setGermplasmDeleteResultTableEntityList(List<GermplasmDeleteResultTableEntity> germplasmDeleteResultTableEntityList) {
		this.germplasmDeleteResultTableEntityList = germplasmDeleteResultTableEntityList;
	}

	@Command
	public void closeModalDialog(){
		
	    germplasmDeleteWarningDialog.detach();
		
	}

	@Command("exportGermplasmsThatCantBeDeleted")
	public void exportGermplasmsThatCantBeDeleted() {

		ListIterator<GermplasmDeleteResultTableEntity> it = germplasmDeleteResultTableEntityList.listIterator();
		StringBuffer buffMap = new StringBuffer();

		while (it.hasNext()) {

			GermplasmDeleteResultTableEntity next = it.next();

			if(it.nextIndex()==1){
				buffMap.append(next.getHeaderDelimitedBy("\t"));
			}
			buffMap.append(next.getAllDelimitedBy("\t"));

		}

		FileWriter fw;
		try {
			File file = new File("list_of_germplasms_that_cant_be_deleted.txt");
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

	
	public int getTotalNumOfGermplasmsThatCantBeDeleted() {
		return totalNumOfGermplasmsThatCantBeDeleted;
	}

	public void setTotalNumOfGermplasmsThatCantBeDeleted(int totalNumOfGermplasmsThatCantBeDeleted) {
		this.totalNumOfGermplasmsThatCantBeDeleted = totalNumOfGermplasmsThatCantBeDeleted;
	}

	public boolean isItemsMoreThanTen() {
		return itemsMoreThanTen;
	}

	public void setItemsMoreThanTen(boolean itemsMoreThanTen) {
		this.itemsMoreThanTen = itemsMoreThanTen;
	}
}

