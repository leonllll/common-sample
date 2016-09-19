package com.leonlu.code.util.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
	private static Properties configProperties;
	private static final String PROPERTIES_FILE = "config.properties";
	private static ConfigUtil instance = null;
	
	public static ConfigUtil getInstance(String propertiesFile) {
		if (instance == null) {
			instance = new ConfigUtil(propertiesFile);
		}
		return instance;
	}

	public static ConfigUtil getInstance() {
		if (instance == null) {
			instance = new ConfigUtil(PROPERTIES_FILE);
		}
		return instance;
	}
	
	private ConfigUtil(String propertiesFile) {
		init(propertiesFile);
	}

	public void init(String propertiesFile) {
		configProperties = new Properties();
		try {
			InputStream is = ConfigUtil.class.getResourceAsStream(propertiesFile);
			configProperties.load(is);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return configProperties.getProperty(key);
	}
}
