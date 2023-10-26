package com.example.demo3;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Demo3Configuration2 {
	private static Logger logger = LoggerFactory.getLogger(Demo3Configuration2.class);
	private static Demo3Configuration2 instance = null;
	
	private String propertiesFile = "properties/default.properties";	
	private Properties properties = new Properties();
	
	@Value("${spring.profiles.active:NA}")
	private String profile;
	
	public Demo3Configuration2() {
//		init();
	}
	
	@PostConstruct
	private void init() {
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFile);
		try {
			properties.load(inStream);
		} catch (IOException e) {
			logger.error("failed properties[{}]", propertiesFile, e);
		}
		
		Demo3Configuration2.instance = this;
		logger.info("inited - profile[{}] ENV:profile[{}]", profile, System.getProperty("spring.profiles.active"));
	}
	
	public String getPropertiesFile() {
		return "["+profile+"]"+propertiesFile;
	}
	
	public static Demo3Configuration2 instance() {
		return Demo3Configuration2.instance;
	}
	
	public String getPropertyString(String key) {
		return properties.getProperty(key);
	}
}
