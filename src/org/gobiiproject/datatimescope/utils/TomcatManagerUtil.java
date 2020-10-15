package org.gobiiproject.datatimescope.utils;

import java.net.URI;
import static org.zkoss.zk.ui.util.Clients.alert;
//import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
//import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Replacement utility class for Tomcat Manager operations
 * @author rnduldulaojr
 *
 */
public class TomcatManagerUtil {
	
	private static URIBuilder getBuilderBase(String host, int port) {
		return new URIBuilder().setScheme("http").setHost(host).setPort(port);
	}
	
	private static void commandWebapp(String command, String host, String portStr, String webAppPath, String username, String password) throws Exception {
		
	    //alert("TomcatManagerUtil.undeployWebapp \n Command+ "+command+"\n host"+host+"\n portStr "+portStr+"\n "+webAppPath+"\n "+webAppPath+"\n username"+username+"\n password"+password);
	    
	    
	    if (!webAppPath.startsWith("/")) {
			webAppPath = "/" + webAppPath;
		}
	    
		int port = 80;
		if (portStr != "") {

			try {

				port = Integer.parseInt(portStr);
			} catch (NumberFormatException nfe) {

				port = 80;
			}
		}

		CloseableHttpClient client = HttpClients.createDefault();
		URI uri = getBuilderBase(host, port)
				.setPath(String.format("/manager/text/%s", command))
				.addParameter("path", webAppPath)	
				.build();
		
		//alert("uri "+uri.toString());
		
		HttpGet getRequest = new HttpGet(uri);
	    UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
	    getRequest.addHeader(new BasicScheme().authenticate(creds, getRequest, null));
	    
	    CloseableHttpResponse response = client.execute(getRequest);
	    HttpEntity entity = response.getEntity();
	    String responseBody = EntityUtils.toString(entity).trim();

       // alert("responseBody"+responseBody);
	    response.close();
	    if (!responseBody.startsWith("OK")) {
	//        alert(responseBody);
//	    	throw new Exception(responseBody);
	    }
	//    alert("END OF COMMAND WEB APP");
	}
	
	public static void reloadWebapp(String host, String port, String webAppPath, String username, String password) throws Exception {
		commandWebapp("reload", host, port, webAppPath, username, password);
	}
	
	public static void undeployWebapp(String host, String port, String webAppPath, String username, String password) throws Exception {
		commandWebapp("undeploy", host, port, webAppPath, username, password);
	}

}
