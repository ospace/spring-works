package com.example.demo3;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//https://www.freeism.co.kr/wp/archives/1667

@PropertySources({
	@PropertySource("classpath:properties/default.properties"),
	@PropertySource("classpath:properties/messages_${spring.profiles.active:na}.properties")
})
@Component
public class Demo3Configuration {
	private static Logger logger = LoggerFactory.getLogger(Demo3Configuration.class);
	
	@Value("${name:NA}")
	private String name;
	
	@Value("${spring.profiles.active:NA}")
	private String profile;
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	private void init() {
		logger.info("inited - profile[{}]", profile);
	}
	
	public String getName() {
		return name;
	}
	
	public String getName2() {
		return env.getProperty("name");
	}
}
