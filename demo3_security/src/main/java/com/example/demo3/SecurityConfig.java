package com.example.demo3;

import java.util.Arrays;

//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
//import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.filter.CompositeFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo3.entity.Group;
import com.example.demo3.entity.GroupRole;
import com.example.demo3.entity.User;
import com.example.demo3.entity.UserRole;
import com.example.demo3.repository.GroupRepositoryJPA;
import com.example.demo3.repository.UserRepositoryJPA;

//https://docs.gigaspaces.com/xap/10.0/security/authenticating-against-a-database.html

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
//	@Autowired
//	private UserService userService;
	
//	@Autowired
//	OAuth2ClientContext oauth2ClientContext;
	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserRepositoryJPA userRepo;
	
	@Autowired
	private GroupRepositoryJPA groupRepo;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		init();
		
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		
//		auth.userDetailsService(userService).passwordEncod`er(new BCryptPasswordEncoder());
		auth.jdbcAuthentication().dataSource(dataSource)
			//SELECT username,password,enabled FROM users WHERE username = ?
			.usersByUsernameQuery("select id as username, pwd as password, 1 as enabled from users where id=?")
			.authoritiesByUsernameQuery("select id as username, name as authority from user_roles where id=?")
			.groupAuthoritiesByUsername("select g.id, g.group_name, gr.authority from groups g, users u, group_roles gr where u.id = ? and g.id = gr.id and g.id = u.group_id")
			.passwordEncoder(pwdEncoder)
//		.and().inMemoryAuthentication()
//			.withUser("a").password(pwdEncoder.encode("a")).roles("ADMIN")
//		.and().passwordEncoder(new BCryptPasswordEncoder())
		;
	}
	
	private void init() {
		logger.info("- initializing -----------------------------------------------------");
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		
		Group g1 = Group.of("ADMIN", Arrays.asList(GroupRole.of("ROLE_ADMIN"), GroupRole.of("ROLE_USER")));
		groupRepo.save(g1);
		Group g2 = Group.of("BASIC", Arrays.asList(GroupRole.of("ROLE_USER")));
		groupRepo.save(g2);
		
		logger.info("group1 : {}", g1);
		logger.info("group2 : {}", g2);
		
//		userRepo.save(User.of("z", pwdEncoder.encode("z"), Arrays.asList(UserRole.of("SpacePrivilege READ ClassFilter eg.cinema.Movie, SpacePrivilege READ ClassFilter eg.cinema.Seat"))));
		User u1 = User.of("z", pwdEncoder.encode("z"), g2);
		userRepo.save(u1);
		
		User u2 = User.of("a", pwdEncoder.encode("a"), g1);
		userRepo.save(u2);
		
		User u3 = User.of("u", pwdEncoder.encode("u"), g1);
		userRepo.save(u3);
		
		User u4 = User.of("o", pwdEncoder.encode("o"), Arrays.asList(UserRole.of("ROLE_USER")));
		userRepo.save(u4);
		
		logger.info("- initialized  -----------------------------------------------------");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/lib/**", "/image/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.csrf().disable();
		http.authorizeRequests()
//			.antMatchers("/admin/**").access("hasRole('ADMIN') and hasRole('SUPER')")
		    .antMatchers("/admin/**").hasRole("ADMIN")		
//		    .antMatchers("/admin/**").hasAuthority("READ_PRIVILEGE")
//			.antMatchers("/user/**").authenticated()
//			.antMatchers("/user/**").hasAuthority("READ")
			.antMatchers("/user/**").hasRole("USER")
			.antMatchers("/**").permitAll()
		.and().formLogin()
			.loginPage("/login.html") // 인증페이지 위치(GET)
//			.loginProcessingUrl("/login") //인증 처리(POST)
//			.defaultSuccessUrl("/")
//			.failureUrl("/login")
//			.successHandler(new LonginSuccessHandler("/"))
		.and().exceptionHandling()
			.accessDeniedPage("/denied.html")
		.and().logout()
			.logoutSuccessUrl("/thankyou.html");
//		.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
		
//		class Matcher {
//		   public String name;
//		   public String roleInfo;
//		}
		
//		for (Matcher matcher : matchers) {
//	        http.authorizeRequests().antMatchers(matcher.name).access(matcher.roleInfo);
//	    }
		//"hasRole('ADMIN') and hasIpAddress('123.123.123.123')"
	}
	
//	private Filter ssoFilter() {
//		CompositeFilter filter = new CompositeFilter();
//		List<Filter> filters = new ArrayList<>();
//		
//		filters.add(new Filter() {
//			@Override
//			public void init(FilterConfig filterConfig) throws ServletException {
//				logger.info("Filter : init");
//			}
//			@Override
//			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//					throws IOException, ServletException {
//				logger.info("Filter : doFilter[{}]", ((HttpServletRequest)request).getRequestURI());
//				chain.doFilter(request, response);
//			}
//			@Override
//			public void destroy() {
//				logger.info("Filter : destroy");
//			}
//		});
//		
//	
//		filters.add(oauth2Filter("/login/facebook"));
//		
//		filter.setFilters(filters);
//		return filter;
//	}
	
//	private Filter oauth2Filter(String path) {
//		OAuth2ClientAuthenticationProcessingFilter oauth2Filter = new OAuth2ClientAuthenticationProcessingFilter(path);
//		AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
//		OAuth2RestTemplate template = new OAuth2RestTemplate(client);
//		oauth2Filter.setRestTemplate(template);
//		ResourceServerProperties resource = new ResourceServerProperties(); 
//		UserInfoTokenServices tokenServices = new UserInfoTokenServices(resource.getUserInfoUri(), resource.getClientId()); 
//		tokenServices.setRestTemplate(template);
//		oauth2Filter.setTokenServices(tokenServices);
//		return oauth2Filter;
//	}
}
