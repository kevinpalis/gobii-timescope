package org.gobiiproject.datatimescope.utils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gobiiproject.datatimescope.db.generated.tables.records.PlatformRecord;
import org.jooq.Record;

public class Utils {

	static List<String> roleList= new ArrayList<String>();
	static{
		roleList.add(""); // empty for index 0
		roleList.add("Super Administrator"); // index 1
		roleList.add("Administrator"); // index 2
		roleList.add("User"); // index 3
		roleList = Collections.unmodifiableList(roleList);
	}
	
	public static List<String> getRoleList() {
		return roleList;
	}

	public static String checkString(String value) {
		// TODO Auto-generated method stub
		String returnVal;
		
		try{
			if(value==null){
				returnVal = "";
			}else returnVal = value;
		}catch(NullPointerException npe){
			returnVal = "";
		}
		
		return returnVal;
	}


	public static String checkInteger(Integer value) {
		// TODO Auto-generated method stub
		String returnVal;
		
		try{
			returnVal = Integer.toString(value);
		}catch(NullPointerException npe){
			returnVal = "";
		}
		
		return returnVal;
	}

	public static String checkDate(Date value) {
		// TODO Auto-generated method stub
		String returnVal;
		
		try{
			returnVal = value.toString();
		}catch(NullPointerException npe){
			returnVal = "";
		}
		
		return returnVal;
	}

	public static String checkIfCommaNeeded(StringBuilder sb, String string) {
        // TODO Auto-generated method stub
        String returnVal = string;
        if(!sb.toString().isEmpty()) {
            returnVal = ", " + string;
        }
        return returnVal;
	}

    public static <T> void filterItems( List<T> list, List<T> allItems, String filter) {
        list.clear();

        if(filter == null || "".equals(filter)) {
            list.addAll(allItems);
        } else {
            for(T item : allItems) {
                if(((String) ((Record) item).get(1)).toLowerCase().indexOf(filter.toLowerCase()) >= 0) {
                    list.add(item);
                }
            }
        }
    }

    public static <T> void filterItems(List<T> list, List<T> allItems,
            String filter, int indexOfName) {
        
        list.clear();

        if(filter == null || "".equals(filter)) {
            list.addAll(allItems);
        } else {
            for(T item : allItems) {
                if(((String) ((Record) item).get(indexOfName)).toLowerCase().indexOf(filter.toLowerCase()) >= 0) {
                    list.add(item);
                }
            }
        }
        
    }
    
    
    public static <T> String getListNamesToString( List<T> list) {

        StringBuilder sb = new StringBuilder();

        int ctr=0;

        for(T item : list) {
            if(ctr>0) sb.append(", "); 
            sb.append((String) ((Record) item).get(1));
            ctr++;
        }
        
        return sb.toString();
    }
    

    public static <T> String getListNamesToString( List<T> list, int index) {

        StringBuilder sb = new StringBuilder();

        sb.append(System.getProperty("line.separator"));
        for(T item : list) {
            sb.append("\t");
            sb.append((String) ((Record) item).get(index));
            sb.append(System.getProperty("line.separator"));
        }
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }
    
    public static <T> Boolean isListNotNullOrEmpty( List<T> list) {
        Boolean returnValue = false;

        if( list!=null && !list.isEmpty()) returnValue = true;

        return returnValue;
    }

    public static String combineLabelWithNum(String string, Integer i) {
        // TODO Auto-generated method stub
        
        String label;
        try {
             label = string+" ("+ i.toString()+")";
        } catch(NullPointerException npe) {
             label = string+" (0)";
        }
        
        return label;
    }

    public static String getIdRangeAsString(Integer markerIDStartRange, Integer markerIDEndRange) {
        // TODO Auto-generated method stub
        
        StringBuilder sb = new StringBuilder();
        try {
            
            if(markerIDStartRange!=null) {
                sb.append(Integer.toString(markerIDStartRange));
            }
            
            if(markerIDEndRange!=null) {
                if(sb.length()>0) sb.append(" - ");
                sb.append(Integer.toString(markerIDEndRange));
            }
            sb.append("\n");
        } catch(NullPointerException npe) {
            
        }
        
        return sb.toString();
    }
}
