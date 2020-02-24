package org.gobiiproject.datatimescope.exceptions;

import java.util.List;

public class CannotDeleteDataSetListException extends TimescopeException {
	public List<String> getDataSetNames() {
		return dataSetNames;
	}

	public void setDataSetNames(List<String> dataSetNames) {
		this.dataSetNames = dataSetNames;
	}

	private List<String> dataSetNames;
	
	public CannotDeleteDataSetListException(List<String> dataSetNames) {
		super(
			"Cannot delete the data files for the following dataset(s):\n\n"+ String.join("\n", dataSetNames)+ "\n\n",
			"ERROR: Cannot delete datasets!"
		);
		setDataSetNames(dataSetNames);
		
	}

}
