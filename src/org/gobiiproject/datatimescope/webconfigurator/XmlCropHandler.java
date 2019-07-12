package org.gobiiproject.datatimescope.webconfigurator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.zkoss.bind.annotation.NotifyChange;

import java.io.*;

//All the functions below this are simple XML Crop Creation/Deletion

public class XmlCropHandler extends XmlModifier {
    @NotifyChange("cropList")
    public void removeCrop(Crop oldCrop){
        Document doc = XmlModifier.retrieveFile(XmlModifier.path);
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
        Document doc = XmlModifier.retrieveFile(path);
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
