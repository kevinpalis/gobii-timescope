package org.gobiiproject.datatimescope.webconfigurator;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class xmlModifier extends SelectorComposer<Component> {

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

    private static String path = "/data/gobii_bundle/config/gobii-web.xml";

    private String includeSrc = "/home.zul";


    //Finds the GOBII_WEB configs
    private static String hostForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::host";
    private static String contextPathlForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::contextPath]";
    private static String portForReloadXPath = "//serverConfig/serverType[text() = 'GOBII_WEB']/following-sibling::port";

    private String pathForReload;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    @Command("ldapSystemUser")
    public void ldapSystemUser() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ldapSystemUser.zul");
        getPage().getDesktop().setBookmark("p_"+"ldapUSerSystem");
    }

    @Command("ldapTestUser")
    public void ldapTestUser() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ldapTestUser.zul");
        getPage().getDesktop().setBookmark("p_"+"ldapTestUser");
    }

    @Command("emailNotifications")
    public void emailNotifications() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/emailNotifications.zul");
        getPage().getDesktop().setBookmark("p_"+"emailNotifications");
    }

    @Command("pushNotifications")
    public void pushNotifications() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/pushNotifications.zul");
        getPage().getDesktop().setBookmark("p_"+"pushNotifications");
    }

    @Command("addCrop")
    public void addCrop() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/addCrop.zul");
        getPage().getDesktop().setBookmark("p_"+"addCrop");
    }

    @Command("deleteCrop")
    public void deleteCrop() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/deleteCrop.zul");
        getPage().getDesktop().setBookmark("p_"+"deleteCrop");
    }

    @Command("manageCrop")
    public void manageCrop() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/manageCrop.zul");
        getPage().getDesktop().setBookmark("p_"+"manageCrop");
    }

    @Command("logSettings")
    public void logSettings() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/logSettings.zul");
        getPage().getDesktop().setBookmark("p_"+"logSettings");
    }

    @Command("linkageGroups")
    public void linkeageGroups() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/linkageGroups.zul");
        getPage().getDesktop().setBookmark("p_"+"linkageGroups");
    }

    @Command("markerGroups")
    public void markerGroups() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/markerGroups.zul");
        getPage().getDesktop().setBookmark("p_"+"markerGroups");
    }

    @Command("kdCompute")
    public void kdCompute() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/kdCompute.zul");
        getPage().getDesktop().setBookmark("p_"+"KDComputeIntegration");
    }

    @Command("ownCloud")
    public void ownCloud() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/ownCloud.zul");
        getPage().getDesktop().setBookmark("p_"+"OwnCloud");
    }

    @Command("galaxy")
    public void galaxy() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/galaxy.zul");
        getPage().getDesktop().setBookmark("p_"+"Galaxy");
    }

    @Command("scheduler")
    public void scheduler() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/scheduler.zul");
        getPage().getDesktop().setBookmark("p_"+"Scheduler");
    }

    @Command("portConfig")
    public void portConfig() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/portConfig.zul");
        getPage().getDesktop().setBookmark("p_"+"PortConfiguration");
    }

    @Command("backup")
    public void backup() {
        Include include = (Include)Selectors.iterable(getPage(), "#mainContent")
                .iterator().next();
        include.setSrc("/backup.zul");
        getPage().getDesktop().setBookmark("p_"+"backup");
    }

    public String getldapUserForUnitTest(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//ldapUserForUnitTest", doc).item(0).getTextContent();
    }
    public String getldapPasswordForUnitTest(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//ldapPasswordForUnitTest", doc).item(0).getTextContent();
    }
    public String getgobiiAuthenticationType(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//gobiiAuthenticationType", doc).item(0).getTextContent();
    }
    public String getldapUserDnPattern(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//ldapUserDnPattern", doc).item(0).getTextContent();
    }
    public String getldapUrl(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//ldapUrl", doc).item(0).getTextContent();
    }
    public String getldapBindUser(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//ldapBindUser", doc).item(0).getTextContent();
    }
    public String getldapBindPassword(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//ldapBindPassword", doc).item(0).getTextContent();
    }
    public String getldapUserForBackendProcs(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//ldapUserForBackendProcs", doc).item(0).getTextContent();
    }
    public String getldapPasswordForBackendProcs(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//ldapPasswordForBackendProcs", doc).item(0).getTextContent();
    }
    public String getemailSvrType(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//emailSvrType", doc).item(0).getTextContent();
    }
    public String getemailSvrDomain(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//emailSvrDomain", doc).item(0).getTextContent();
    }
    public String getemailSvrUser(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//emailSvrUser", doc).item(0).getTextContent();
    }
    public String getemailSvrHashType(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//emailSvrHashType", doc).item(0).getTextContent();
    }
    public String getemailSvrPassword(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//emailSvrPassword", doc).item(0).getTextContent();
    }
    public String getemailServerPort(){
        Document doc = xmlModifier.retrieveFile(path);
        return evaluateExpression("//emailServerPort", doc).item(0).getTextContent();
    }



    private static List<String> retriever (String identifier, ArrayList<String> XPathQueries) {
        //String path = "/home/fvgoldman/gobiidatatimescope/src/org/gobiiproject/datatimescope/webconfigurator/gobii-web.xml";
        //String path = "/data/gobii_bundle/config/gobii-web.xml";


        //Build DOM
        Document doc = xmlModifier.retrieveFile(path);
        List<String> results = new ArrayList<>();

        for (String xPathQuery : XPathQueries) {

            NodeList nodes = evaluateExpression(xPathQuery, doc);
            if (nodes == null || nodes.getLength() == 0) {
                System.out.println("There seems to be no result for the expression: " + xPathQuery);

            } else if (nodes.getLength() > 1) {
                //We need to use another form of verification
                if (identifier.equals("")) {
                    System.out.println("There are more than one entries that fit the given parameters, please specify more precisely which one to modify.");
                }
                for (int j = 0; j < nodes.getLength(); j++) {
                    if (nodes.item(j).getTextContent().equals(identifier)) {

                    }
                }
            } else {
                results.add(nodes.item(0).getTextContent());
            }
        }

        return results;
    }

    /**
     * @param identifier For the use cases in which tag will not return a single node, currently not in use
     * @param XPathQueries ArrayList of tags that will be modified
     * @param newContents Array of the new Content, to be filled in by the User/UI
     * @return 0 upon at least one successful modification, -1 upon failure to make any changes
     * This function performs the modifications to the xml file according to the provided parameters
     * It will print messages, if anything didn't fully execute specifying what went wrong
     */
    private static int modifier(String identifier, ArrayList<String> XPathQueries, String[] newContents) {
        //String path = "/home/fvgoldman/gobiidatatimescope/src/org/gobiiproject/datatimescope/webconfigurator/gobii-web.xml";
        String path = "/data/gobii_bundle/config/gobii-web.xml";

        if (XPathQueries.size() != newContents.length) {
            //Something didn't line up and will definitely break the XML
            System.out.println("The amount of tags does not line up with the amount of contents.");
            return -1;
        }

        //Build DOM
        Document doc = xmlModifier.retrieveFile(path);

        for (int i = 0; i < XPathQueries.size(); i++) {
            String newContent = newContents[i];

            NodeList nodes = evaluateExpression(XPathQueries.get(i), doc);
            if (nodes == null || nodes.getLength() == 0) {
                System.out.println("There seems to be no result for the expression: " + XPathQueries.get(i));

            } else if (nodes.getLength() > 1) {
                //We need to use another form of verification
                if (identifier.equals("")) {
                    System.out.println("There are more than one entries that fit the given parameters, please specify more precisely which one to modify.");
                }
                for (int j = 0; j < nodes.getLength(); j++) {
                    if (nodes.item(j).getTextContent().equals(identifier)) {
                        nodes.item(j).setTextContent(newContent);
                    }
                }
            } else {
                nodes.item(0).setTextContent(newContent);
            }
        }

        xmlModifier.modifyDocument(doc, path);
        /*try {
            String host = evaluateExpression(hostForReloadXPath, doc).item(0).getTextContent();
            String contextPath = evaluateExpression(contextPathlForReloadXPath, doc).item(0).getTextContent();
            String port = evaluateExpression(portForReloadXPath, doc).item(0).getTextContent();
            ReloadTask command = new ReloadTask();
            command.setUsername("gadm");
            command.setPassword("g0b11admin");
            command.setUrl(host + contextPath + port);
            //Ask about this
            command.setPath("/gobiidatatimescope/WebContent/WEB-INF/web.xml");
            command.execute();
        } catch (Exception e){
            e.printStackTrace();
        }*/
        return 0;
    }

    private static NodeList evaluateExpression(String expression, Document doc){
        //Create XPath
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        Object result = null;
        try {
            XPathExpression expr = xpath.compile(expression);
            result = expr.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
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
        }
    }

    public static List<String> xmlRetrieverWrapper(String operation, String[] contents){
        ArrayList<String> tagsToBeRetrieved;
        switch (operation) {
            case "LDAP Unit":
                tagsToBeRetrieved = new ArrayList<>(Arrays.asList(ldapUserForUnitTestXPath, ldapPasswordForUnitTestXPath));
                break;
            case "LDAP Gobii":
                tagsToBeRetrieved = new ArrayList<>(Arrays.asList(
                        gobiiAuthenticationTypeXPath, ldapUserDnPatternXPath, ldapUrlXPath, ldapBindUserXPath,
                        ldapBindPasswordXPath, ldapUserForBackendProcsXPath, ldapPasswordForBackendProcsXPath));
                break;
            case "Email notification":
                tagsToBeRetrieved = new ArrayList<>(Arrays.asList(emailSvrTypeXPath, emailSvrDomainXPath, emailSvrUserXPath,
                        emailSvrHashTypeXPath, emailSvrPasswordXPath, emailServerPortXPath));
                break;
            default:
                //Not implemented yet
                System.out.println("Queried a not yet implemented modifier.");
                return null;
        }
        return retriever("", tagsToBeRetrieved);
    }

    public static void xmlModifierWrapper(String operation, String[] contents){
        ArrayList<String> tagsToBeModified;
        switch (operation){
            case "LDAP Unit":
                tagsToBeModified = new ArrayList<>(Arrays.asList(ldapUserForUnitTestXPath, ldapPasswordForUnitTestXPath));
                break;
            case "LDAP Gobii":
                tagsToBeModified = new ArrayList<>(Arrays.asList(
                        gobiiAuthenticationTypeXPath, ldapUserDnPatternXPath, ldapUrlXPath, ldapBindUserXPath,
                        ldapBindPasswordXPath, ldapUserForBackendProcsXPath, ldapPasswordForBackendProcsXPath));
                break;
            case "Email notification":
                tagsToBeModified = new ArrayList<>(Arrays.asList(emailSvrTypeXPath, emailSvrDomainXPath, emailSvrUserXPath,
                        emailSvrHashTypeXPath, emailSvrPasswordXPath, emailServerPortXPath));
                break;
            default:
                //Not implemented yet
                System.out.println("Queried a not yet implemented modifier.");
                return;
        }
        if (modifier("", tagsToBeModified, contents) == -1){
            //Something went wrong, handle
            System.out.println("Something went wrong.");
        } else {
            System.out.println("All went well.");
        }

    }

    public static void main(String[] args){
        //content will be filled by UI upon calling

        System.out.println("Test 1");
        String[] testContent_1 = {"user2", "dummypass"};
        xmlModifierWrapper("LDAP Unit",  testContent_1);

        System.out.println("Test 2");
        String[] testContent_2 = {"user2"};
        xmlModifierWrapper("LDAP Unit",  testContent_2);

        System.out.println("Test 3");
        String[] testContent_3 = {};
        xmlModifierWrapper("Dummy",  testContent_3);

        System.out.println("Test 4");
        String[] testContent_4 = {"NO_AUTH", "uid={1}", "localhost:8080", "test", "test", "admin", "123456"};
        xmlModifierWrapper("LDAP Gobii",  testContent_4);

        System.out.println("Test 5");
        String[] testContent_5 = {"SMTP", "smtp.gmail.com", "my.test@gmail.com", "MD5", "test", "666"};
        xmlModifierWrapper("Email notification",  testContent_5);

        System.out.println("Test 6");
        String[] testContent_6 = {"SMTP", "smtp.gmail.com", "my.test@gmail.com", "MD5", "test", "666"};
        xmlModifierWrapper("LDAP Unit",  testContent_6);

        System.out.println("Test 7");
        String[] testContent_7 = {"", ""};
        xmlModifierWrapper("LDAP Unit",  testContent_7);

        System.out.println("Test 8");
        String[] testContent_8 = {"user2", "dummypass"};
        xmlModifierWrapper("LDAP Unit",  testContent_8);
    }

    public String getIncludeSrc() {
        return includeSrc;
    }
}


