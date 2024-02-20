package com.tistory.ospace.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    public void configure(AuthenticationManagerBuilder builder, BaseLdapPathContextSource contextSource) throws Exception {
        builder
                .ldapAuthentication()
                .userDnPatterns("uid={0}")
                .contextSource(contextSource);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(req->req
                        .anyRequest().fullyAuthenticated()
                )
                .formLogin(Customizer.withDefaults())
        ;

        return http.build();
    }
}
