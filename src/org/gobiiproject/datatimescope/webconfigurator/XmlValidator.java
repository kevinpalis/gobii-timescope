package org.gobiiproject.datatimescope.webconfigurator;

import org.jooq.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.gobiiproject.datatimescope.webconfigurator.UtilityFunctions.writeToLog;

public class XmlValidator {

    /***
     * A class exclusively for the validation of the user imported XML file.
     * The functions check only for the existence of the specified attributes and not content!
     * The validation is split into General Settings:
     *  - Email Host, Email Port, Email Server User ID, Email Server Password, File System Root, Gobii Authentication Type
     *      LDAP User Pattern, LDAP URL, LDAP Bind User, LDAP Bind Password, File System Log and File System Crop Parent
     * Test Crop:
     *  - Test Exec Configuration, Test Crop, Test Initial Configuration, Test Config File Directory and Test Configuration Command Line Stem
     * Web Server: (For every Crop in the XML)
     *  - Crop Name, Host, Context Path and Port
     * Postgres:
     *   - Host, Context Path (Database Name), Port, User and Password
     * If all checked fields are not empty or null, the function validateGobiiConfiguration() will return true
     */

    private XmlModifier xmlHandler;
    private String errorMessage;
    private String username;

    public XmlValidator(XmlModifier xmlHandler, String name){
        username  = name;
        this.xmlHandler = xmlHandler;
    }

    private boolean validateGeneralSettings(List<String> messages){
        boolean returnVal = true;
        if (isNullOrEmpty(xmlHandler.getEmailSvrDomain())) {
            messages.add("An email server host is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getEmailServerPort())) {
            messages.add("An email port is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getEmailSvrUser())) {
            messages.add("An email server user id is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getEmailSvrPassword())) {
            messages.add("An email server password is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getFileSystemRoot())) {
            messages.add("A file system root is not defined");
            returnVal = false;
        } else {
            File directoryToTest = new File(xmlHandler.getFileSystemRoot());
            if (!directoryToTest.exists() || !directoryToTest.isDirectory()) {
                messages.add("The specified file system root does not exist or is not a directory: " + xmlHandler.getFileSystemRoot());
                returnVal = false;
            }
        }
        if (isNullOrEmpty(xmlHandler.getGobiiAuthenticationType())) {
            messages.add("An authentication type is not specified");
            returnVal = false;
        }
        if (!xmlHandler.getGobiiAuthenticationType().equals("TEST")) {
            if (isNullOrEmpty(xmlHandler.getLdapUserDnPattern())) {
                messages.add("The authentication type is "
                        + xmlHandler.getGobiiAuthenticationType()
                        + " but a user dn pattern is not specified");
                returnVal = false;
            }
            if (isNullOrEmpty(xmlHandler.getLdapUrl())) {
                messages.add("The authentication type is "
                        + xmlHandler.getGobiiAuthenticationType()
                        + " but an ldap url is not specified");
                returnVal = false;
            }
            if (xmlHandler.getGobiiAuthenticationType().equals("LDAP_CONNECT_WITH_MANAGER") ||
                    xmlHandler.getGobiiAuthenticationType().equals("ACTIVE_DIRECTORY_CONNECT_WITH_MANAGER")) {
                if (isNullOrEmpty(xmlHandler.getLdapBindUser())) {
                    messages.add("The authentication type is "
                            + xmlHandler.getGobiiAuthenticationType()
                            + " but an ldap bind user is not specified");
                    returnVal = false;
                }
                if (isNullOrEmpty(xmlHandler.getLdapBindPassword())) {
                    messages.add("The authentication type is "
                            + xmlHandler.getGobiiAuthenticationType()
                            + " but an ldap bind password is not specified");
                    returnVal = false;
                }
            } // if the authentication type requires connection credentails
        } // if the authentication type requires url and user dn pattern
        if (isNullOrEmpty(xmlHandler.getFileSystemLog())) {
            messages.add("A file system log directory is not defined");
            returnVal = false;
        } else {
            File directoryToTest = new File(xmlHandler.getFileSystemLog());
            if (!directoryToTest.exists() || !directoryToTest.isDirectory()) {
                messages.add("The specified file system log does not exist or is not a directory: " + xmlHandler.getFileSystemLog());
                returnVal = false;
            }
        }
        if (isNullOrEmpty(xmlHandler.getFileSysCropsParent())) {
            messages.add("A file system crop parent directory is not defined");
            returnVal = false;
        }
        return returnVal;
    }

    private boolean validateTestCrop(List<String> messages){
        boolean returnVal = true;
        if (isNullOrEmpty(xmlHandler.getTestExecConfig())){
            messages.add("No test exec configuration is defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getTestCrop())) {
            messages.add("A test crop id is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getTestInitialConfigUrl())) {
            messages.add("An initial configuration url for testing is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getTestConfigFileTestDirectory())) {
            messages.add("A a directory for test files is not defined");
            returnVal = false;
        } else {
            String testDirectoryPath = xmlHandler.getTestConfigFileTestDirectory();
            File testFilePath = new File(xmlHandler.getTestConfigFileTestDirectory());
            if (!testFilePath.exists()) {
                messages.add("The specified test file path does not exist: "
                        + testDirectoryPath);
                returnVal = false;
            }
        }
        if (isNullOrEmpty(xmlHandler.getTestConfigUtilCommandLineStem())) {
            messages.add("The commandline stem of this utility for testing purposes is not defined");
            returnVal = false;
        }
        return returnVal;
    }

    private boolean validateWebServer(List<String> messages, List<String> contextPathList, String Cropname) {
        boolean returnVal = true;
        if (isNullOrEmpty(Cropname)) {
            messages.add("The crop type for the crop is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getWebHost(Cropname))) {
            messages.add("The web server host for the crop (" + Cropname + ") is not defined");
            returnVal = false;

        }
        if (isNullOrEmpty(xmlHandler.getWebContextPath(Cropname))) {
            messages.add("The web server context path for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        } else {
            if (!contextPathList.contains(xmlHandler.getWebContextPath(Cropname))) {
                contextPathList.add(xmlHandler.getWebContextPath(Cropname));
            } else {
                messages.add("The context path for the crop occurs more than once -- context paths must be unique:" + xmlHandler.getWebContextPath(Cropname));
                returnVal = false;
            }
        }
        if (isNullOrEmpty(xmlHandler.getWebPort(Cropname))) {
            messages.add("The web server port for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        }
        return returnVal;
    }

    private boolean validatePostgresServer(List<String> messages, List<String> databasesList, String Cropname){
        boolean returnVal = true;
        if (isNullOrEmpty(xmlHandler.getDatabaseHost(Cropname))) {
            messages.add("The postgres server host for the crop (" + Cropname + ") is not defined");
            returnVal = false;

        }
        if (isNullOrEmpty(xmlHandler.getDatabaseName(Cropname))) {
            messages.add("The postgres server context path for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        } else {
            if (!databasesList.contains(xmlHandler.getDatabaseName(Cropname))) {
                databasesList.add(xmlHandler.getDatabaseName(Cropname));
            } else {
                messages.add("The context path for the crop occurs more than once -- context paths must be unique:" + xmlHandler.getDatabaseName(Cropname));
                returnVal = false;
            }
        }
        if (xmlHandler.getDatabasePort(Cropname) == null) {
            messages.add("The postgres server port for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getDatabaseUser(Cropname))){
            messages.add("The postgres username for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        }
        if (isNullOrEmpty(xmlHandler.getDatabasePassword(Cropname))){
            messages.add("The postgres password for the crop (" + Cropname + ") is not defined");
            returnVal = false;
        }
        return returnVal;
    }

    private boolean validateServers(List<String> messages){
        boolean returnVal = true;
        List<String> contextPathList = new ArrayList<>();
        List<String> databasesList = new ArrayList<>();
        for (int i = 0; i < xmlHandler.getCropList().size(); i++) {
            String Cropname = String.valueOf(xmlHandler.getCropList().get(i));
            if (xmlHandler.getPostgres(Cropname) == null) {
                messages.add("The postgresdb for the crop (" + Cropname + ") is not defined");
                returnVal = false;
            } else if (xmlHandler.getWeb(Cropname) == null) {
                messages.add("The webConfig for the crop (" + Cropname + ") is not defined");
                returnVal = false;
            } else {
                returnVal = returnVal && validateWebServer(messages, contextPathList, Cropname) &&
                        validatePostgresServer(messages, databasesList, Cropname);
            }
        }
        return returnVal;
    }

    private boolean isNullOrEmpty(String value){
        return (null == value || value.isEmpty());
    }
    private boolean isNullOrEmpty(int value){
        return (value == 0);
    }

    public boolean validateGobiiConfiguration() {
        boolean returnVal;
        try {
            List<String> messages = new ArrayList<>();
            returnVal = validateGeneralSettings(messages) && validateTestCrop(messages) && validateServers(messages);
            if (xmlHandler.getCropList().size() < 1) {
                messages.add("No active crops are defined");
                returnVal = false;
            }
            if (!returnVal && messages.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (String s : messages)
                {
                    sb.append(s);
                    sb.append("\n");
                }
                errorMessage = "The provided xml was invalid.\n" + sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "A mandatory XML tag is missing.";
            returnVal = false;
        }
        return returnVal;

    }

    public String getErrorMessage() {
        writeToLog("XmlValidator.getErrorMessage()", errorMessage, username);
        return errorMessage;
    }
}
