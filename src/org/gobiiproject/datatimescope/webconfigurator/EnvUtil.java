/**
 * EnvUtil.java
 * 
 * Put all runtime environment settings under one utility class
 */
package org.gobiiproject.datatimescope.webconfigurator;

import java.io.File;
import java.util.logging.Logger;

public class EnvUtil {
	private static final Logger log = Logger.getLogger(EnvUtil.class.getName());
	private static String gobiiBundleDir;
	private static String gobiiWebXmlFile;
	private static String tmpDir;
	
	static {
		//initialize gobiiWebXmlFile
		gobiiWebXmlFile = "";
		
		try {
			gobiiWebXmlFile = System.getenv("GOBII_WEB_XML_FILE");
			gobiiBundleDir = System.getenv("GOBII_DATA_BUNDLE_DIR");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (gobiiBundleDir == "" || gobiiBundleDir == null) {
			gobiiBundleDir = "/data/gobii_bundle/config/"; //hardcoded dir
		}
		
		if (!(new File(gobiiBundleDir)).exists()) {
			log.warning(String.format("Gobii bundle dir %s does not exist.", gobiiBundleDir));
		}
		
		if (!gobiiBundleDir.endsWith(File.separator)) gobiiBundleDir += File.separator;
		
		if (gobiiWebXmlFile == "" || gobiiWebXmlFile == null) {
			gobiiWebXmlFile = gobiiBundleDir + "gobii-web.xml"; //hardcoded
		}
		
		if (!(new File(gobiiWebXmlFile)).exists()) {
			log.warning(String.format("Gobii web xml file: %s does not exist.", gobiiWebXmlFile));
		}
		
		tmpDir = System.getProperty("java.io.tmpdir");
		if (!tmpDir.endsWith(File.separator)) tmpDir += File.separator;
	}

	
	public static String getGobiiWebXmlFilename() {
		return gobiiWebXmlFile;
	}
	
	public static String getTempDir() {
		return tmpDir;
	}
	
	public static String getGobiiBundleDir() {
		return gobiiBundleDir;
	}

}
