package com.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * @author Likhitha NR
 */

public class ConfigManager {

	private static String ENVIORNMENT = "gsEnvironment";
	private static String REPORTNAME = "gsReportName";
	private static String PROJECTNAME = "gsProjectName";
	private static String DOCTITLE = "gsDocumentTitle";
	private static String AUTO_DOCPATH = "gsAutomationAutoSupportDocs";
	private static String AUTO_RESULTPATH = "gsAutomationAutoResultPath";
	private static String JSONFILEPATH = "gsJSONFilePath";
	private static String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/Config.properties";

	// global accessing property variables
	public static String gsEnvironment;
	public static String gsReportName;
	public static String gsProjectName;
	public static String gsDocumentTitle;
	public static String gsAutomationAutoSupportDocs;
	public static String gsAutomationAutoResultPath;
	public static String gsJSONFilePath;
	public static FileReader file = null;
	public static Properties prop = new Properties();
	public static ConfigManager readPropFile = null;
	public static Logger logger = Logger.getLogger(ConfigManager.class);

	private ConfigManager() {
	}

	/** 
	 * @return ConfigManager
	 */
	public static ConfigManager getInstance() {
		if (readPropFile == null) {
			readPropFile = new ConfigManager();
		}
		return readPropFile;
	}

	/** 
	 * @throws IOException
	 */
	// Description: method to load and and get all values form property file
	public static void loadProperties() throws IOException {
		try {

			file = new FileReader(FILE_PATH);
			prop.load(file);
			gsEnvironment = prop.getProperty(ENVIORNMENT);
			gsReportName = prop.getProperty(REPORTNAME);
			gsDocumentTitle = prop.getProperty(DOCTITLE);
			gsProjectName = prop.getProperty(PROJECTNAME);
			gsAutomationAutoSupportDocs = prop.getProperty(AUTO_DOCPATH);
			gsAutomationAutoResultPath = prop.getProperty(AUTO_RESULTPATH);
			gsJSONFilePath = prop.getProperty(JSONFILEPATH);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			file.close();
		}
	}

	/** 
	 * @param fileName
	 * @return URL
	 */
	// Get the com.plete path of file by passing properties file name
	public static URL getpropertyFilePath(String fileName) {
		readPropFile = new ConfigManager();
		readPropFile = ConfigManager.getInstance();
		Class cls = readPropFile.getClass();
		System.out.println(cls);
		URL filePathUrl = cls.getResource(fileName);
		System.out.println(filePathUrl);
		return filePathUrl;
	}

	/** 
	 * @param filePath
	 * @return Properties
	 */
	// Get the prop, reference variable of properties file
	public static Properties getPropertyRef(URL filePath) {
		try {
			file = new FileReader(filePath.getFile());
			prop.load(file);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e);
		}
		return prop;
	}
	
	/** 
	 * @return String
	 */
	// Description: method to get the required formatted date and time
	public static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_YYYY_HH_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
