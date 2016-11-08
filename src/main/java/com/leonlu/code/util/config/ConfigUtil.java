package com.leonlu.code.util.config;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {
	private static Properties configProperties;
	private static final String PROPERTIES_FILE = "config.properties";
	private static ConfigUtil instance = null;
	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class.getName());
	
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
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile);
			configProperties.load(is);
		} catch (Exception e) {
			logger.error("can't load config file " + propertiesFile + ", " + e.getMessage());
		}
	}
	
	public Object get(String key) {
		if(configProperties != null) {
			return configProperties.get(key);
		}
		return null;
	}
	public String getProperty(String key) {
		if(configProperties != null) {
			return configProperties.getProperty(key); 
		}
		return null;
	}
	
	public String getString(String key, String defaultValue) {
		if(configProperties != null && configProperties.contains(key)) {
			return configProperties.getProperty(key);
		}
		return defaultValue;
	}
	
	public Integer getInt(String key, Integer defaultValue) {
		if(configProperties != null && configProperties.contains(key)) {
			try {
				return (Integer) configProperties.get(key);
			}catch(Exception e) {
				logger.error(e.getMessage());
			}
		}
		return defaultValue;
	}
	
	public Double getDouble(String key, Double defaultValue) {
		if(configProperties != null && configProperties.contains(key)) {
			try {
				return (Double)configProperties.get(key);
			}catch(Exception e) {
				logger.error(e.getMessage());
			}
		}
		return defaultValue;
	}
	
	public Long getLong(String key, Long defaultValue) {
		if(configProperties != null && configProperties.contains(key)) {
			try {
				return (Long) configProperties.get(key);
			}catch(Exception e) {
				logger.error(e.getMessage());
			}
		}
		return defaultValue;
	}
}