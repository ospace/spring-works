package com.tistory.ospace.simplesecurity1.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.springframework.context.annotation.Bean;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username, password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select username, authority from authorities where username=?")
			.groupAuthoritiesByUsername("select g.id, g.group_name, ga.authority, gm.username from groups g, group_authorities ga, group_members gm where g.id = ga.group_id and gm.group_id = ga.group_id and gm.username = ?")
			.passwordEncoder(passwordEncoder())
		;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/lib/**", "/image/**",  "/h2/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable()
		    .and().csrf().disable()
		    .authorizeRequests()
		    	.antMatchers("/login**", "/thankyou**", "/register**").permitAll()
			    .antMatchers("/admin/**").hasRole("ADMIN")		
			    .antMatchers("/about/**").hasAnyRole("ADMIN", "USER")
			    .antMatchers("/user/**").hasAnyRole("USER")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")                // 인증페이지 경로
				//.defaultSuccessUrl("/main", true) //로그인 성공시 호출경로. successHandler사용시 불필요. 
				//.loginProcessingUrl("/loginProc") //사용자 정의 인증 처리 호출 URL(POST)
				//.failureUrl("/login?status=1")    //인증실패시 호출 경로. failureHandler사용시 불필요.
				//.usernameParameter("username")    //사용자ID 기본 파라미터 변경
				//.passwordParameter("password")    //패스춰드 기본 파라미터 변경
				.successHandler(successHandler())
				.failureHandler(failureHandler())
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/thankyou.html")
				.deleteCookies("JSESSIONID")
				.deleteCookies("REMEMBER_ME_COOKIE")
				.invalidateHttpSession(true)
				.and()
			.rememberMe()
				.key("REMEMBER_ME_KEY")
				.rememberMeServices(tokenBasedRememberMeServices())
				.and()
			.exceptionHandling()
				.accessDeniedPage("/denied.html")
			;
	}
	
	@Bean
	public RememberMeServices tokenBasedRememberMeServices() {
		TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices("REMEBMER_ME_KEY", userDetailsService());
		//tokenBasedRememberMeServices.setAlwaysRemember(true);								// 체크박스 클릭안해도 무조건 유지
		tokenBasedRememberMeServices.setTokenValiditySeconds( 60*60*24*30 ); //30일(60초*60분*24시*30일)
		tokenBasedRememberMeServices.setCookieName("REMEMBER_ME_COOKIE");
		return tokenBasedRememberMeServices;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
					Authentication auth) throws IOException, ServletException {
				LOGGER.debug("onAuthenticationSuccess begin: auth[{}]", auth);

				String redirectUrl = "/";
				HttpSession session= req.getSession();
				if(null != session) {
					redirectUrl = (String) session.getAttribute("prevPage");
					if (!StringUtils.isEmpty(redirectUrl)) {
						session.removeAttribute("prevPage");
					}
				}
				
				res.sendRedirect(redirectUrl);
				
				LOGGER.debug("onAuthenticationSuccess end: redirectUrl[{}]", redirectUrl);
			}
		};
	}
	
	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new AuthenticationFailureHandler() {
			
			@Override
			public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res,
					AuthenticationException ex) throws IOException, ServletException {
				String username = req.getParameter("username");
				LOGGER.debug("onAuthenticationFailure begin: username[{}] exception[{}]", username, ex.getMessage());
				
				//내부로 포워드하고 userId속성으로 username을 넘김 
				//req.setAttribute("userId", username);
				//req.getRequestDispatcher("/login?status=2").forward(req, res);
				
				//인증실패 상태 반환시
				//res.setStatus(HttpStatus.UNAUTHORIZED.value());
				res.sendRedirect("/login?status=2");
				
				LOGGER.debug("onAuthenticationFailure end:");
			}
		};
	}
}
