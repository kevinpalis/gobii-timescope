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
import java.util.ArrayList;
import java.util.List;


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
    private static String postgresHostXPath = "//serverType[text() = 'GOBII_PGSQL']/following-sibling::host";
    private static String postgresPortXPath = "//serverType[text() = 'GOBII_PGSQL']/following-sibling::port";
    private static String cropListXPath = "//gobiiCropType";
    private static String currentCrop;

    private static String path = "/data/gobii_bundle/config/gobii-web.xml";
    //private static String path = "/home/fvgoldman/gobiidatatimescope/src/org/gobiiproject/datatimescope/webconfigurator/gobii-web.xml";

    //Finds the GOBII_WEB configs
    //Only the contextPath can handle different settings the rest needs to stay the same
    private static String hostForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::host";
    private static String contextPathForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::contextPath";
    private static String portForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::port";


    public String getHostForReload() {
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(hostForReloadXPath, doc).item(0).getTextContent();
    }

    public NodeList getContextPathNodes() {
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(contextPathForReloadXPath, doc);
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
        NodeList postgresCropNodes = evaluateXPathExpression(postgresUserNameXPath, doc);
        for (int n = 0; n < postgresCropNodes.getLength(); n++){
            postgresCropNodes.item(n).setTextContent(newContent);
        }
        xmlModifier.modifyDocument(doc, path);
    }
    public void setPostgresPassword(String newContent){
        Document doc = xmlModifier.retrieveFile(path);
        NodeList postgresCropNodes = evaluateXPathExpression(postgresPasswordXPath, doc);
        for (int n = 0; n < postgresCropNodes.getLength(); n++){
            postgresCropNodes.item(n).setTextContent(newContent);
        }
        xmlModifier.modifyDocument(doc, path);
    }
    public void setActivity(Crop modCrop, boolean activeness) {
        Document doc = xmlModifier.retrieveFile(path);
        String expression = "//gobiiCropType[text() = '" + modCrop.getName() + "']/following-sibling::isActive";
        evaluateXPathExpression(expression, doc).item(0).setTextContent(String.valueOf(activeness));
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

    public String getDatabaseName(String Cropname){
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_PGSQL']/following-sibling::contextPath";
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }

    public ListModelList getCropList(){
        Document doc = xmlModifier.retrieveFile(path);
        NodeList nl = evaluateXPathExpression(cropListXPath, doc);
        ListModelList<String> cropList = new ListModelList<>();
        for (int i = 0; i < nl.getLength(); i++){
            cropList.add(nl.item(i).getTextContent());
        }
        return cropList;
    }

    public Boolean getActivity(Crop modCrop) {
        Document doc = xmlModifier.retrieveFile(path);
        String expression = "//gobiiCropType[text() = '" + modCrop.getName() + "']/following-sibling::isActive";
        return Boolean.valueOf(evaluateXPathExpression(expression, doc).item(0).getTextContent());
    }

    public String getWARName(String Cropname) {
        String postgresContextPathXPath = "//gobiiCropType[text() = '" + Cropname + "']/following-sibling::serversByServerType/entry/serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::contextPath";
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresContextPathXPath, doc).item(0).getTextContent();
    }


    public String getCurrentCrop(){
        return currentCrop;
    }
    public String getPostgresUserName(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresUserNameXPath, doc).item(0).getTextContent();
    }
    public int getPostgresPasswordExtrenal(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresPasswordXPath, doc).item(0).getTextContent().hashCode();
    }
    public String getPostgresPassword(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresPasswordXPath, doc).item(0).getTextContent();
    }
    public String getLdapUserForUnitTest(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapUserForUnitTestXPath, doc).item(0).getTextContent();
    }
    public int getLdapPasswordForUnitTest(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapPasswordForUnitTestXPath, doc).item(0).getTextContent().hashCode();
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
    public int getLdapBindPassword(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapBindPasswordXPath, doc).item(0).getTextContent().hashCode();
    }
    public String getLdapUserForBackendProcs(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapUserForBackendProcsXPath, doc).item(0).getTextContent();
    }
    public int getLdapPasswordForBackendProcs(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(ldapPasswordForBackendProcsXPath, doc).item(0).getTextContent().hashCode();
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
    public int getEmailSvrPassword(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(emailSvrPasswordXPath, doc).item(0).getTextContent().hashCode();
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

    private static void modifyDocument(Document doc, String path, Boolean creation){
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

    public String getPostgresPort() {
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresPortXPath, doc).item(0).getTextContent();
    }

    public String getPostgresHost() {
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateXPathExpression(postgresHostXPath, doc).item(0).getTextContent();
    }

    @NotifyChange("cropList")
    public void removeCrop(Crop oldCrop){
        Document doc = xmlModifier.retrieveFile(path);
        Node cropRoot = evaluateXPathExpression("//string[text() = '" + oldCrop.getName() + "']/..", doc).item(0);
        removeChildren(cropRoot);
        cropRoot.getParentNode().removeChild(cropRoot);
        removeEmptyLines();
        modifyDocument(doc, path);
    }

    private void removeChildren(Node node){
        while (node.hasChildNodes())
            node.removeChild(node.getFirstChild());
    }

    public void appendCrop(Crop newCrop){
        Document doc = xmlModifier.retrieveFile(path);
        Node cropConfigRoot = evaluateXPathExpression("//cropConfigs", doc).item(0);
        cropConfigRoot.appendChild(entry(doc, newCrop));
        modifyDocument(doc, path, true);
        removeEmptyLines();
    }

    private void removeEmptyLines() {
        String oldFileName = path;
        String tmpFileName = path.replace("xml", "dat");

        try (BufferedReader br = new BufferedReader(new FileReader(oldFileName)); BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()){
                    bw.write(line + "\n");
                }
            }
        } catch (Exception e) {
            return;
        }
        File oldFile = new File(oldFileName);
        oldFile.delete();
        File newFile = new File(tmpFileName);
        newFile.renameTo(oldFile);

    }

    private Node entry(Document doc, Crop newCrop){
        Element entry = doc.createElement("entry");
        Element outer = doc.createElement("string");
        outer.appendChild(doc.createTextNode(newCrop.getName()));
        entry.appendChild(outer);
        entry.appendChild(gobiiCropConfig(doc, newCrop));
        return entry;
    }

    private Node gobiiCropConfig(Document doc, Crop crop){
        Element gobiiCropConfig = doc.createElement("gobiiCropConfig");
        Element gobiiCropType = doc.createElement("gobiiCropType");
        gobiiCropType.appendChild(doc.createTextNode(crop.getName()));
        Element isActive = doc.createElement("isActive");
        isActive.appendChild(doc.createTextNode("true"));
        gobiiCropConfig.appendChild(gobiiCropType);
        gobiiCropConfig.appendChild(isActive);
        gobiiCropConfig.appendChild(serversByServerType(doc, crop));
        return gobiiCropConfig;
    }

    private Node serversByServerType(Document doc, Crop crop){
        Element serversByServerType = doc.createElement("serversByServerType");
        serversByServerType.setAttribute("class", "java.util.HashMap");
        serversByServerType.appendChild(innerPostgres(doc, crop));
        serversByServerType.appendChild(innerWeb(doc, crop));
        return serversByServerType;
    }

    private Node innerPostgres(Document doc, Crop crop){
        Element entry = doc.createElement("entry");
        Element serverType = doc.createElement("serverType");
        serverType.appendChild(doc.createTextNode("GOBII_PGSQL"));
        entry.appendChild(serverType);
        entry.appendChild(postgresConfig(doc, crop));
        return entry;
    }

    private Node postgresConfig(Document doc, Crop crop){
        Element serverConfig = doc.createElement("serverConfig");
        Element decrypt = doc.createElement("decrypt");
        decrypt.appendChild(doc.createTextNode("false"));
        serverConfig.appendChild(decrypt);
        Element userName = doc.createElement("userName");
        userName.appendChild(doc.createTextNode(getPostgresUserName()));
        serverConfig.appendChild(userName);
        Element password = doc.createElement("password");
        password.appendChild(doc.createTextNode(getPostgresPassword()));
        serverConfig.appendChild(password);
        Element serverType = doc.createElement("serverType");
        serverType.appendChild(doc.createTextNode("GOBII_PGSQL"));
        serverConfig.appendChild(serverType);
        Element host = doc.createElement("host");
        host.appendChild(doc.createTextNode(getPostgresHost()));
        serverConfig.appendChild(host);
        Element contextPath = doc.createElement("contextPath");
        contextPath.appendChild(doc.createTextNode(crop.getDatabaseName()));
        serverConfig.appendChild(contextPath);
        Element port = doc.createElement("port");
        port.appendChild(doc.createTextNode(getPostgresPort()));
        serverConfig.appendChild(port);
        Element isActive = doc.createElement("isActive");
        isActive.appendChild(doc.createTextNode("true"));
        serverConfig.appendChild(isActive);
        Element resourceProfilesByRestRequestId = doc.createElement("resourceProfilesByRestRequestId");
        serverConfig.appendChild(resourceProfilesByRestRequestId);
        Element statusCheckIntervalSecs = doc.createElement("statusCheckIntervalSecs");
        statusCheckIntervalSecs.appendChild(doc.createTextNode("0"));
        serverConfig.appendChild(statusCheckIntervalSecs);
        Element maxStatusCheckMins = doc.createElement("maxStatusCheckMins");
        maxStatusCheckMins.appendChild(doc.createTextNode("0"));
        serverConfig.appendChild(maxStatusCheckMins);
        return serverConfig;
    }

    private Node innerWeb(Document doc, Crop crop){
        Element entry = doc.createElement("entry");
        Element serverType = doc.createElement("serverType");
        serverType.appendChild(doc.createTextNode("GOBII_WEB"));
        entry.appendChild(serverType);
        entry.appendChild(webConfig(doc, crop));
        return entry;
    }

    private Node webConfig(Document doc, Crop crop){
        Element serverConfig = doc.createElement("serverConfig");
        Element decrypt = doc.createElement("decrypt");
        decrypt.appendChild(doc.createTextNode("false"));
        serverConfig.appendChild(decrypt);
        Element serverType = doc.createElement("serverType");
        serverType.appendChild(doc.createTextNode("GOBII_WEB"));
        serverConfig.appendChild(serverType);
        Element host = doc.createElement("host");
        host.appendChild(doc.createTextNode(getHostForReload()));
        serverConfig.appendChild(host);
        Element contextPath = doc.createElement("contextPath");
        contextPath.appendChild(doc.createTextNode("/" + crop.getWARName()));
        serverConfig.appendChild(contextPath);
        Element port = doc.createElement("port");
        port.appendChild(doc.createTextNode(getPortForReload()));
        serverConfig.appendChild(port);
        Element isActive = doc.createElement("isActive");
        isActive.appendChild(doc.createTextNode("true"));
        serverConfig.appendChild(isActive);
        serverConfig.appendChild(resourceProfilesByRestRequestId(doc));
        Element statusCheckIntervalSecs = doc.createElement("statusCheckIntervalSecs");
        statusCheckIntervalSecs.appendChild(doc.createTextNode("0"));
        serverConfig.appendChild(statusCheckIntervalSecs);
        Element maxStatusCheckMins = doc.createElement("maxStatusCheckMins");
        maxStatusCheckMins.appendChild(doc.createTextNode("0"));
        serverConfig.appendChild(maxStatusCheckMins);
        return serverConfig;

    }

    private Node resourceProfilesByRestRequestId(Document doc){
        Element entry = doc.createElement("entry");
        Element restResourceId = doc.createElement("restResourceId");
        restResourceId.appendChild(doc.createTextNode("GOBII_NAMES"));
        entry.appendChild(restResourceId);
        entry.appendChild(restResourceProfile(doc));
        return entry;
    }

    private Node restResourceProfile(Document doc){
        Element restResourceProfile = doc.createElement("restResourceProfile");
        restResourceProfile.appendChild(resourceMethodCollsByTemplateParam(doc));
        Element restResourceId = doc.createElement("restResourceId");
        restResourceId.appendChild(doc.createTextNode("GOBII_NAMES"));
        Element hasTemplateParameters = doc.createElement("hasTemplateParameters");
        hasTemplateParameters.appendChild(doc.createTextNode("true"));
        restResourceProfile.appendChild(restResourceId);
        restResourceProfile.appendChild(hasTemplateParameters);
        return restResourceProfile;
    }

    private Node resourceMethodCollsByTemplateParam(Document doc) {
        Element resourceMethodCollsByTemplateParam = doc.createElement("resourceMethodCollsByTemplateParam");
        resourceMethodCollsByTemplateParam.setAttribute("class", "java.util.HashMap");
        resourceMethodCollsByTemplateParam.appendChild(innerTemplateEntries(doc, "MARKER"));
        resourceMethodCollsByTemplateParam.appendChild(innerTemplateEntries(doc, "DNASAMPLE"));
        resourceMethodCollsByTemplateParam.appendChild(innerTemplateEntries(doc, "ANALYSIS"));
        return resourceMethodCollsByTemplateParam;
    }

    private Node innerTemplateEntries(Document doc, String type){
        Element entry = doc.createElement("entry");
        Element string = doc.createElement("string");
        string.appendChild(doc.createTextNode(type));
        entry.appendChild(string);
        if (type.equals("ANALYSIS")){
            boolean addPutAsMethod = true;
            entry.appendChild(restResourceMethodLimitColl(doc, addPutAsMethod));
        } else {
            boolean addPutAsMethod = false;
            entry.appendChild(restResourceMethodLimitColl(doc, addPutAsMethod));
        }
        return entry;
    }

    private Node restResourceMethodLimitColl(Document doc, boolean flag) {
        Element restResourceMethodLimitColl = doc.createElement("restResourceMethodLimitColl");
        restResourceMethodLimitColl.appendChild(limitsByMethodType(doc, flag));
        return restResourceMethodLimitColl;
    }


    private Node limitsByMethodType(Document doc, boolean flag){
        Element limitsByMethodType = doc.createElement("limitsByMethodType");
        limitsByMethodType.appendChild(innerMethodLimit(doc, "GET"));
        limitsByMethodType.appendChild(innerMethodLimit(doc, "POST"));
        if (flag){
            limitsByMethodType.appendChild(innerMethodLimit(doc, "PUT"));
        }
        return limitsByMethodType;

    }

    private Node innerMethodLimit(Document doc, String method){
        Element entry = doc.createElement("entry");
        Element restMethodType = doc.createElement("restMethodType");
        restMethodType.appendChild(doc.createTextNode(method));
        Element restResourceMethodLimit = doc.createElement("restResourceMethodLimit");
        Element innerRestMethodType = doc.createElement("restMethodType");
        innerRestMethodType.appendChild(doc.createTextNode(method));
        Element innerResourceMax = doc.createElement("resourceMax");
        innerResourceMax.appendChild(doc.createTextNode("2000"));
        restResourceMethodLimit.appendChild(innerRestMethodType);
        restResourceMethodLimit.appendChild(innerResourceMax);

        entry.appendChild(restMethodType);
        entry.appendChild(restResourceMethodLimit);
        return entry;
    }

}


