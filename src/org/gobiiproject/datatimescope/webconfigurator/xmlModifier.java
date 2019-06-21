package org.gobiiproject.datatimescope.webconfigurator;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import java.lang.Object;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;


/**
 * A Class that handles the gobii-web.xml file by querying data from it using XPATH and modifying it according to user input
 */

public class xmlModifier extends SelectorComposer<Component> {

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
    private static String cropListXPath = "//";
    private static String currentCrop;

    private static String path = "/data/gobii_bundle/config/gobii-web.xml";

    //Finds the GOBII_WEB configs
    //Only the contextPath can handle different settings the rest needs to stay the same
    private static String hostForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::host";
    private static String contextPathlForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::contextPath";
    private static String portForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::port";

    public String getHostForReload() {
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(hostForReloadXPath, doc).item(0).getTextContent();
    }

    public NodeList getContextPathNodes() {
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(contextPathlForReloadXPath, doc);
    }

    public String getPortForReload() {
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(portForReloadXPath, doc).item(0).getTextContent();
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public void setCurrentCrop(String currCrop){
        currentCrop = currCrop;
    }
    public void setPostgresUserName(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(postgresUserNameXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setPostgresPassword(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(postgresPasswordXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setLdapUserForUnitTest(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapUserForUnitTestXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setLdapPasswordForUnitTest(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapPasswordForUnitTestXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setGobiiAuthenticationType(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(gobiiAuthenticationTypeXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setLdapUserDnPattern(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapUserDnPatternXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setLdapUrl(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapUrlXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setLdapBindUser(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapBindUserXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setLdapBindPassword(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapBindPasswordXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setLdapUserForBackendProcs(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapUserForBackendProcsXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setLdapPasswordForBackendProcs(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(ldapPasswordForBackendProcsXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setEmailSvrType(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailSvrTypeXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setEmailSvrDomain(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailSvrDomainXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setEmailSvrUser(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailSvrUserXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setEmailSvrHashType(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailSvrHashTypeXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setEmailSvrPassword(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailSvrPasswordXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }
    public void setEmailServerPort(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        evaluateXPathExpression(emailServerPortXPath, doc).item(0).setTextContent(newContent);
        xmlModifier.modifyDocument(doc, path);
    }

    public NodeList getCropList(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(cropListXPath, doc);
    }
    public String getCurrentCrop(){
        return currentCrop;
    }
    public String getPostgresUserName(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresUserNameXPath, doc).item(0).getTextContent();
    }
    public String getPostgresPassword(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresPasswordXPath, doc).item(0).getTextContent();
    }
    public String getLdapUserForUnitTest(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapUserForUnitTestXPath, doc).item(0).getTextContent();
    }
    public String getLdapPasswordForUnitTest(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapPasswordForUnitTestXPath, doc).item(0).getTextContent();
    }
    public String getGobiiAuthenticationType(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(gobiiAuthenticationTypeXPath, doc).item(0).getTextContent();
    }
    public String getLdapUserDnPattern(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapUserDnPatternXPath, doc).item(0).getTextContent();
    }
    public String getLdapUrl(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapUrlXPath, doc).item(0).getTextContent();
    }
    public String getLdapBindUser(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapBindUserXPath, doc).item(0).getTextContent();
    }
    public String getLdapBindPassword(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapBindPasswordXPath, doc).item(0).getTextContent();
    }
    public String getLdapUserForBackendProcs(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapUserForBackendProcsXPath, doc).item(0).getTextContent();
    }
    public String getLdapPasswordForBackendProcs(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapPasswordForBackendProcsXPath, doc).item(0).getTextContent();
    }
    public String getEmailSvrType(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrTypeXPath, doc).item(0).getTextContent();
    }
    public String getEmailSvrDomain(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrDomainXPath, doc).item(0).getTextContent();
    }
    public String getEmailSvrUser(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrUserXPath, doc).item(0).getTextContent();
    }
    public String getEmailSvrHashType(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrHashTypeXPath, doc).item(0).getTextContent();
    }
    public String getEmailSvrPassword(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrPasswordXPath, doc).item(0).getTextContent();
    }
    public String getEmailServerPort(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailServerPortXPath, doc).item(0).getTextContent();
    }

    private static NodeList evaluateXPathExpression(String expression, Document doc){
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

    private static Document retrieveFile(String path){
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

    private static void modifyDocument(Document doc, String path){
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

}


