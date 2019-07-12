package org.gobiiproject.datatimescope.webconfigurator;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class WarningComposer extends WebConfigViewModel{

    private boolean acceptedWarning;
    private ArrayList<String> errorMessages = new ArrayList<>();
    private boolean errorFlag = false;
    private XmlModifier xmlHandler;

    public WarningComposer(XmlModifier xmlHandler){
        this.xmlHandler = xmlHandler;
    }

    /**
     * Opens a warning box and if acknowledged performs the restart of the the application process.
     * Otherwise stages it for later and upon next restart the effects will take place
     * */
    public void warningTomcat() {
        Messagebox.show("Clicking OK will restart the web application and all unsaved data will be lost.", "WarningComposer", Messagebox.OK | Messagebox.CANCEL, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
            public void onEvent(Event evt) {
                if (evt.getName().equals("onOK")) {
                    acceptedWarning = true;
                    //TODO Make sure this behaviour is maintained
                } else {
                    acceptedWarning = false;
                }
            }
        });
    }


    public void warningRemoval(){
        Messagebox.Button[] buttons = new Messagebox.Button[]{Messagebox.Button.OK, Messagebox.Button.CANCEL};
        Map<String, String> params = new HashMap<>();
        params.put("width", "500");
        Messagebox.show("This operation will permanently remove this database and all its associated information." +
                "\nPlease make sure that there are no active sessions prior to removing this database. \nAre you sure" +
                " you want to remove the database now?", "WarningComposer", buttons, null, Messagebox.EXCLAMATION, null, new org.zkoss.zk.ui.event.EventListener() {
            public void onEvent(Event evt) {
                if (evt.getName().equals("onOK")) {
                    acceptedWarning = true;
                    if (xmlHandler.getCropList().size() > 1) {
                        //TODO Perform the actions
                        /*
                        binder.sendCommand("removeCropFromDatabase", null);
                        binder.sendCommand("disableEdit", null);*/
                    } else {
                        errorFlag = true;
                        errorMessages.add("This the only database. Please add another crop before deleting this database.");
                    }
                } else {
                    acceptedWarning = false;
                }
            }
        }, params);
    }

    public boolean isAcceptedWarning() {
        return acceptedWarning;
    }

    public boolean isErrorFlag() {
        return errorFlag;
    }

    public ArrayList<String> getErrorMessages (){
        return errorMessages;
    }
}
