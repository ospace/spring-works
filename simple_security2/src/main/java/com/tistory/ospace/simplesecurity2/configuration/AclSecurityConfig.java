package com.tistory.ospace.simplesecurity2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

//https://www.baeldung.com/spring-security-acl
//https://github.com/eugenp/tutorials/tree/master/spring-security-modules/spring-security-acl
//https://grails-plugins.github.io/grails-spring-security-acl/v2/guide/single.pdf
//https://medium.com/sfl-newsroom/spring-security-expression-based-access-control-56411a60ab3b
//https://github.com/lordlothar99/strategy-spring-security-acl
/*
 * prePostEnabled로 SpEL을 사용하게함.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AclSecurityConfig extends GlobalMethodSecurityConfiguration {
	@Autowired
	private DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler;
	
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		return defaultMethodSecurityExpressionHandler;
	}
}
