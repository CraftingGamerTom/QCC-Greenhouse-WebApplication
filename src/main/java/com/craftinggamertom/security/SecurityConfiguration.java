package com.craftinggamertom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("dummy")
			.roles("USER", "ADMIN");
		auth.inMemoryAuthentication().withUser("admin2").password("dummy2")
			.roles("USER", "ADMIN");
		auth.inMemoryAuthentication().withUser("user").password("dummy2")
			.roles("USER");
	}

	// Need to set the security to allow user sign ins on welcome page
	// Fix the welcome page for when someone is logged in
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll()
				.antMatchers("/*todo*/**").access("hasRole('ADMIN')").and()
				.formLogin();
		http.authorizeRequests().antMatchers("/login").permitAll()
				.antMatchers("/*admin*/**", "/*todo*/**").access("hasRole('ADMIN')").and()
				.formLogin();
		http.authorizeRequests().antMatchers("/login").permitAll()
				.antMatchers("/*user*/**").access("hasRole('USER')").and()
				.formLogin();
	}
}
