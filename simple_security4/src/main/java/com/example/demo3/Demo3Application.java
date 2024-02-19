package com.example.demo3;

//import org.springframework.beans.factory.annotation.Value;

//import java.util.HashMap;
//import java.util.Map;
//
//import javax.sql.DataSource;

//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo3.annotation.EnableTimeLog;

@EnableTimeLog
@SpringBootApplication//(scanBasePackages={"com.example.demo3"})
//@EnableJpaRepositories(basePackages = "com.example.demo3")
//@EnableJpaRepositories(basePackageClasses = UserRepositoryJPA.class)
//@EnableJpaRepositories
//@MapperScan("com.example.demo3")
//scoped-proxy="interfaces"
//@ComponentScan(scopedProxy=ScopedProxyMode.INTERFACES)
@EnableAutoConfiguration(exclude = {
		MongoAutoConfiguration.class	
})
@EnableTransactionManagement
public class Demo3Application {
//	private static final String DEFAULT_NAMING_STRATEGY = "org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy";
//	
//	@Autowired
//	private DataSource dataSource;
	
	public static void main(String[] args) {
//		System.setProperty("spring.profiles.active", "DEV");
		
		SpringApplication springApplication = new SpringApplication(Demo3Application.class);
		springApplication.addListeners(new ApplicationPidFileWriter("app.pid"));
		springApplication.run(args);
	}
	
//	public EntityManagerFactory entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//		emf.setDataSource(dataSource);
//		emf.setJpaVendorAdapter(jpaVendorAdapter);
//		emf.setPackagesToScan("com.mysource.model");
//		emf.setPersistenceUnitName("default");
//		emf.afterPropertiesSet();
//		return emf.getObject();
//	}
	
//	@Bean
//	@ConfigurationProperties(prefix="datasource")
//	public DataSource articleDataSource() {
//		return DataSourceBuilder<DataSource>.create().build();
//	}
	
//	@Primary
//	@Bean(name = "entityManagerFactory")
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
//	    Map<String, String> propertiesHashMap = new HashMap<>();
//	    propertiesHashMap.put("hibernate.ejb.naming_strategy", DEFAULT_NAMING_STRATEGY);
//	
//	    return builder.dataSource(dataSource)
//	    	.packages("com.millky.dev.database.multi.domain.article")
//	        .properties(propertiesHashMap)
//	        .build();
//	}
}
