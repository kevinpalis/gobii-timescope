package org.gobiiproject.datatimescope.webconfigurator;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

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

public class xmlModifier {

    /**
     * @param identifier For the use cases in which tag will not return a single node, currently not in use
     * @param tags ArrayList of tags that will be modified
     * @param newContents Array of the new Content, to be filled in by the User/UI
     * @return 0 upon at least one successful modification, -1 upon failure to make any changes
     * This function performs the modifications to the xml file according to the provided parameters
     * It will print messages, if anything didn't fully execute specifying what went wrong
     */
    private static int modifier(String identifier, ArrayList<String> tags, String[] newContents){
        //path?
        //Do we want server side data validation? Or will this be covered in the UI, probably better
        String path = "/home/fvgoldman/gobiidatatimescope/src/org/gobiiproject/datatimescope/webconfigurator/gobii-web.xml";
        //String path = "/home/fvgoldman/data/gobii_bundle/config/gobii-web.xml";
        if (tags.size() != newContents.length){
            //Something didn't line up and will definitely break the XML
            System.out.println("The amount of tags does not line up with the amount of contents.");
            return -1;
        }

        //Build DOM
        Document doc = xmlModifier.retrieveFile(path);

        for (int i = 0; i < tags.size(); i++) {
            String tag = tags.get(i);
            String newContent = newContents[i];
            String expression = "//" + tag;
            NodeList nodes = evaluateExpression(expression, doc);
            if (nodes == null||nodes.getLength() == 0){
                System.out.println("There seems to be no result for the tag: " + tag);

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

    public static void xmlModifierWrapper(String operation, String[] contents){
        ArrayList<String> tagsToBeModified;
        switch (operation){
            case "LDAP Unit":
                //content will be filled by UI upon calling
                tagsToBeModified = new ArrayList<>(Arrays.asList("ldapUserForUnitTest", "ldapPasswordForUnitTest"));
                break;
            case "LDAP Gobii":
                tagsToBeModified = new ArrayList<>(Arrays.asList(
                        "gobiiAuthenticationType", "ldapUserDnPattern", "ldapUrl",
                        "ldapBindUser", "ldapBindPassword", "ldapUserForBackendProcs", "ldapPasswordForBackendProcs"));
                break;
            case "Email notification":
                tagsToBeModified = new ArrayList<>(Arrays.asList("emailSvrType", "emailSvrDomain", "emailSvrUser",
                        "emailSvrHashType", "emailSvrPassword", "emailServerPort"));
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
            /*String command = "/home/fvgoldman/Documents/apache-tomcat-7.0.94/bin/startup.sh";
            try {
                Process child = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

    }

    public static void main(String[] args){
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
}


