package org.gobiiproject.datatimescope.webconfigurator;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.ListModelList;

import java.io.*;
import java.lang.Object;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;


/**
 * A Class that handles the gobii-web.xml file by querying data from it using XPATH and modifying it according to user input
 */

public class XmlModifier extends SelectorComposer<Component> {

    //If any of the below XPATH's return more than one Node only the first one will be modified

    private static String ldapUserForUnitTestXPath = "//ldapUserForUnitTest";
    private static String ldapPasswordForUnitTestXPath = "//ldapPasswordForUnitTest";
    private static String gobiiAuthenticationTypeXPath = "//gobiiAuthenticationType";
    private static String ldapUserDnPatternXPath = "//ldapUserDnPattern";
    private static String ldapUrlXPath = "//ldapUrl";
    private static String ldapBindUserXPath = "//ldapBindUser";
    private static String ldapBindPasswordXPath = "//ldapBindPassword";
    private static String ldapUserForBackendProcsXPath = "//ldapUserForBackendProcs";
    private static String ldapPasswordForBackendProcsXPath = "//ldapPasswordForBackendProcs";
    private static String emailSvrTypeXPath = "//emailSvrType";
    private static String emailSvrDomainXPath = "//emailSvrDomain";
    private static String emailSvrUserXPath = "//emailSvrUser";
    private static String emailSvrHashTypeXPath = "//emailSvrHashType";
    private static String emailSvrPasswordXPath = "//emailSvrPassword";
    private static String emailServerPortXPath = "//emailServerPort";
    private static String postgresUserNameXPath = "//serverType[text() = 'GOBII_PGSQL']/preceding-sibling::userName";
    private static String postgresPasswordXPath = "//serverType[text() = 'GOBII_PGSQL']/preceding-sibling::password";
    private static String postgresHostXPath = "//serverType[text() = 'GOBII_PGSQL']/following-sibling::host";
    private static String postgresPortXPath = "//serverType[text() = 'GOBII_PGSQL']/following-sibling::port";
    private static String cropListXPath = "//gobiiCropType";
    private static String fileSystemRootXPath = "//fileSystemRoot";
    private static String fileSystemLogXPath = "//fileSystemLog";
    private static String fileSysCropsParentXPath = "//fileSysCropsParent";
    private static String testExecConfigXPath = "//testExecConfig";
    private static String testCropXPath = "//testCrop";
    private static String testInitialConfigUrlXPath = "//initialConfigUrl";
    private static String testConfigFileTestDirectoryXPath = "//configFileTestDirectory";
    private static String testConfigUtilCommandLineStemXpath = "//configUtilCommandlineStem";
    private static String kdComputeDecryptXPath = "//serverType[text() = 'KDC']/preceding-sibling::decrypt";
    private static String kdComputeHostXPath = "//serverType[text() = 'KDC']/following-sibling::host";
    private static String kdComputeContextPathXPath = "//serverType[text() = 'KDC']/following-sibling::contextPath";
    private static String kdComputePortXPath = "//serverType[text() = 'KDC']/following-sibling::port";
    private static String kdComputeIsActiveXPath = "//serverType[text() = 'KDC']/following-sibling::isActive";
    private static String kdComputeStatusCheckIntervalSecsXPath = "//serverType[text() = 'KDC']/following-sibling::statusCheckIntervalSecs";
    private static String kdComputeMaxStatusCheckMinsXPath = "//serverType[text() = 'KDC']/following-sibling::maxStatusCheckMins";

    private static String currentCrop;

    protected static String path = "/data/gobii_bundle/config/gobii-web.xml";

    //Finds the GOBII_WEB configs
    //Only the contextPath can handle different settings the rest needs to stay the same
    private static String hostForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::host";
    private static String contextPathForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::contextPath";
    private static String portForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::port";

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public void setPath (String newPath){
        path = newPath;
    }

    public String getPath(){
        return path;
    }

    public void setCurrentCrop(String currCrop){
        currentCrop = currCrop;
    }

    public void setPostgresUserName(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        NodeList postgresCropNodes = evaluateXPathExpression(postgresUserNameXPath, doc);
        for (int n = 0; n < postgresCropNodes.getLength(); n++){
            postgresCropNodes.item(n).setTextContent(newContent);
        }
        XmlModifier.modifyDocument(doc, path);
    }
    public void setPostgresPassword(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        NodeList postgresCropNodes = evaluateXPathExpression(postgresPasswordXPath, doc);
        for (int n = 0; n < postgresCropNodes.getLength(); n++){
            postgresCropNodes.item(n).setTextContent(newContent);
        }
        XmlModifier.modifyDocument(doc, path);
    }
    public void setActivity(Crop modCrop, boolean activeness) {
        Document doc = XmlModifier.retrieveFile(path);
        String expression = "//gobiiCropType[text() = '" + modCrop.getName() + "']/following-sibling::isActive";
        evaluateXPathExpression(expression, doc).item(0).setTextContent(String.valueOf(activeness));
        XmlModifier.modifyDocument(doc, path);
    }
    public void setLdapUserForUnitTest(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapUserForUnitTestXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setLdapPasswordForUnitTest(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapPasswordForUnitTestXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setGobiiAuthenticationType(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(gobiiAuthenticationTypeXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setLdapUserDnPattern(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapUserDnPatternXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setLdapUrl(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapUrlXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setLdapBindUser(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapBindUserXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setLdapBindPassword(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapBindPasswordXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setLdapUserForBackendProcs(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapUserForBackendProcsXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setLdapPasswordForBackendProcs(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapPasswordForBackendProcsXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setEmailSvrType(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailSvrTypeXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setEmailSvrDomain(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailSvrDomainXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setEmailSvrUser(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailSvrUserXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setEmailSvrHashType(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailSvrHashTypeXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setEmailSvrPassword(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailSvrPasswordXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setEmailServerPort(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailServerPortXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setFileSystemRoot(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(fileSystemRootXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setFileSystemLog(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(fileSystemLogXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setFileSysCropParents(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(fileSysCropsParentXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setTestExecConfig(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(testExecConfigXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setTestCrop(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(testCropXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setTestInitialConfigUrl(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(testInitialConfigUrlXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setTestConfigFileTestDirectory(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(testConfigFileTestDirectoryXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setTestConfigUtilCommandLineStem(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(testConfigUtilCommandLineStemXpath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setKdComputeDecrypt(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(kdComputeDecryptXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setKdComputeHost(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(kdComputeHostXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setKdComputeContextPath(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(kdComputeContextPathXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setKdComputePort(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(kdComputePortXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setKdComputeIsActive(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(kdComputeIsActiveXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setKdComputeStatusCheckIntervalSecs(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(kdComputeStatusCheckIntervalSecsXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }
    public void setKdComputeMaxStatusCheckMins(String newContent){
        Document doc = XmlModifier.retrieveFile(path);
        evaluateXPathExpression(kdComputeMaxStatusCheckMinsXPath, doc).item(0).setTextContent(newContent);
        XmlModifier.modifyDocument(doc, path);
    }



    public ListModelList getCropList(){
        Document doc = XmlModifier.retrieveFile(path);
        NodeList nl = evaluateXPathExpression(cropListXPath, doc);
        ListModelList<String> cropList = new ListModelList<>();
        for (int i = 0; i < nl.getLength(); i++){
            cropList.add(nl.item(i).getTextContent());
        }
        return cropList;
    }

    //Crop specific getters
    public String getPostgres(String Cropname){
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_PGSQL']";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }
    public String getWeb(String Cropname){
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_WEB']";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }
    public String getDatabaseName(String Cropname){
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_PGSQL']/following-sibling::contextPath";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }
    public String getDatabaseUser(String Cropname){
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_PGSQL']/preceding-sibling::userName";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }
    public String getDatabasePassword(String Cropname){
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_PGSQL']/preceding-sibling::password";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }
    public String getDatabaseHost(String Cropname){
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_PGSQL']/following-sibling::host";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }
    public String getDatabasePort(String Cropname){
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_PGSQL']/following-sibling::port";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }
    public Boolean getActivity(String Cropname) {
        Document doc = XmlModifier.retrieveFile(path);
        String expression = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::isActive";
        return Boolean.valueOf(evaluateXPathExpression(expression, doc).item(0).getTextContent());
    }
    public String getWARName(String Cropname) {
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::contextPath";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }
    public String getWebHost(String Cropname) {
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::host";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }
    public String getWebContextPath(String Cropname) {
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::contextPath";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }
    public String getWebPort(String Cropname) {
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::port";
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }


    public String getKdComputeDecrypt(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(kdComputeDecryptXPath, doc).item(0).getTextContent();
    }
    public String getKdComputeHost(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(kdComputeHostXPath, doc).item(0).getTextContent();
    }
    public String getKdComputeContextPath(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(kdComputeContextPathXPath, doc).item(0).getTextContent();
    }
    public String getKdComputePort(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(kdComputePortXPath, doc).item(0).getTextContent();
    }
    public String getKdComputeIsActive(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(kdComputeIsActiveXPath, doc).item(0).getTextContent();
    }
    public String getKdComputeStatusCheckIntervalSecs(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(kdComputeStatusCheckIntervalSecsXPath, doc).item(0).getTextContent();
    }
    public String getKdComputeMaxStatusCheckMins(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(kdComputeMaxStatusCheckMinsXPath, doc).item(0).getTextContent();
    }
    public String getFileSystemRoot(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(fileSystemRootXPath, doc).item(0).getTextContent();
    }
    public String getFileSystemLog(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(fileSystemLogXPath, doc).item(0).getTextContent();
    }
    public String getFileSysCropsParent(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(fileSysCropsParentXPath, doc).item(0).getTextContent();
    }
    public String getTestExecConfig(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(testExecConfigXPath, doc).item(0).getTextContent();
    }
    public String getTestCrop(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(testCropXPath, doc).item(0).getTextContent();
    }
    public String getTestInitialConfigUrl(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(testInitialConfigUrlXPath, doc).item(0).getTextContent();
    }
    public String getTestConfigFileTestDirectory(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(testConfigFileTestDirectoryXPath, doc).item(0).getTextContent();
    }
    public String getTestConfigUtilCommandLineStem(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(testConfigUtilCommandLineStemXpath, doc).item(0).getTextContent();
    }
    public String getCurrentCrop(){
        return currentCrop;
    }
    public String getPostgresUserName(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresUserNameXPath, doc).item(0).getTextContent();
    }
    public int getPostgresPasswordExtrenal(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresPasswordXPath, doc).item(0).getTextContent().hashCode();
    }
    public String getPostgresPassword(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresPasswordXPath, doc).item(0).getTextContent();
    }
    public String getLdapUserForUnitTest(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapUserForUnitTestXPath, doc).item(0).getTextContent();
    }
    public int getLdapPasswordForUnitTest(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapPasswordForUnitTestXPath, doc).item(0).getTextContent().hashCode();
    }
    public String getGobiiAuthenticationType(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(gobiiAuthenticationTypeXPath, doc).item(0).getTextContent();
    }
    public String getLdapUserDnPattern(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapUserDnPatternXPath, doc).item(0).getTextContent();
    }
    public String getLdapUrl(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapUrlXPath, doc).item(0).getTextContent();
    }
    public String getLdapBindUser(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapBindUserXPath, doc).item(0).getTextContent();
    }
    public int getLdapBindPassword(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapBindPasswordXPath, doc).item(0).getTextContent().hashCode();
    }
    public String getLdapUserForBackendProcs(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapUserForBackendProcsXPath, doc).item(0).getTextContent();
    }
    public int getLdapPasswordForBackendProcs(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapPasswordForBackendProcsXPath, doc).item(0).getTextContent().hashCode();
    }
    public String getEmailSvrType(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrTypeXPath, doc).item(0).getTextContent();
    }
    public String getEmailSvrDomain(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrDomainXPath, doc).item(0).getTextContent();
    }
    public String getEmailSvrUser(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrUserXPath, doc).item(0).getTextContent();
    }
    public String getEmailSvrHashType(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrHashTypeXPath, doc).item(0).getTextContent();
    }
    public int getEmailSvrPassword(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrPasswordXPath, doc).item(0).getTextContent().hashCode();
    }
    public String getEmailServerPort(){
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailServerPortXPath, doc).item(0).getTextContent();
    }
    public String getPostgresPort() {
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresPortXPath, doc).item(0).getTextContent();
    }
    public String getPostgresHost() {
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresHostXPath, doc).item(0).getTextContent();
    }
    public String getHostForReload() {
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(hostForReloadXPath, doc).item(0).getTextContent();
    }
    public NodeList getContextPathNodes() {
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(contextPathForReloadXPath, doc);
    }
    public String getPortForReload() {
        Document doc = XmlModifier.retrieveFile(path);
        return evaluateXPathExpression(portForReloadXPath, doc).item(0).getTextContent();
    }

    protected static NodeList evaluateXPathExpression(String expression, Document doc){
        //Create XPath
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        Object result = null;
        try {
            XPathExpression expr = xpath.compile(expression);
            result = expr.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            System.out.println("Malformed XPath Request.");
        }
        NodeList nodes = (NodeList) result;
        if (result == null) {
            System.out.println("No results found for the given tag.");
            return null;
        }
        return nodes;
    }

    protected static Document retrieveFile(String path){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(path);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.out.println("Retrieval of gobii-web.xml failed.");
        }
        return doc;
    }

    //Creation requires tab indenture setting
    protected static void modifyDocument(Document doc, String path, Boolean creation){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
            System.out.println("Modification of gobii-web.xml failed.");
        }
    }

    protected static void modifyDocument(Document doc, String path){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
            System.out.println("Modification of gobii-web.xml failed.");
        }
    }

    //All the functions below this are simple XML Crop Creation/Deletion



}


