package com.example.demo3;

import javax.annotation.PostConstruct;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//
////@Configuration
//public class MongodbConfig extends AbstractMongoConfiguration {
//	private static Logger logger = LoggerFactory.getLogger(MongodbConfig.class);
//	
//	@Value("${spring.data.mongodb.uri}")
//	private String uri;
//	
//	@Value("${spring.data.mongodb.database}")
//	private String database;
//
//	@Value("${spring.data.mongodb.username}")
//	private String username;
//	
//	@Value("${spring.data.mongodb.password}")
//	private String password;
//	
//	private MongoCredential cridential;
//	private MongoClientOptions.Builder options;
//	
//	@PostConstruct
//	private void init() {
//		logger.info("mongodb config : url[{}] database[{}] uername[{}] password[{}]", uri, database, username, password);
//		
//		cridential = MongoCredential.createCredential(username, database, password.toCharArray());
//		options = new MongoClientOptions.Builder()
//			.connectTimeout(1000) //msec
//			.maxConnectionIdleTime(3000) //msec
//			.socketTimeout(3000) //msec
//			.maxWaitTime(3000); //msec
//	}
//	
//	@Override
//	public MongoClient mongoClient() {
//		return new MongoClient(new ServerAddress(uri, 27017), cridential, options.build());
//	}
//
//	@Override
//	protected String getDatabaseName() {
//		return database;
//	}
//	
//	@Bean
//	public MongoTemplate mongoTemplate() {
//		return new MongoTemplate(mongoClient(), database);
//	}
//	
//
//}
