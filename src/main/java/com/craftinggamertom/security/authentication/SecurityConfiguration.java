/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.security.authentication;

import static org.springframework.http.HttpMethod.GET;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final String LOGIN_URL = "/login";

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new LoginUrlAuthenticationEntryPoint(LOGIN_URL);
	}

	@Bean
	public OpenIDConnectAuthenticationFilter openIdConnectAuthenticationFilter() {
		return new OpenIDConnectAuthenticationFilter(LOGIN_URL);
	}

	@Bean
	public OAuth2ClientContextFilter oAuth2ClientContextFilter() {
		return new OAuth2ClientContextFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterAfter(oAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
				.addFilterAfter(openIdConnectAuthenticationFilter(), OAuth2ClientContextFilter.class)
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).and().authorizeRequests()
				.antMatchers(GET, "/").permitAll() // actual home page (redirects to home page)
				.antMatchers(GET, "/view/**").permitAll() // view pages
				.antMatchers(GET, "/feed/**").permitAll() // home page
				.antMatchers(GET, "/test").authenticated() // remove
				.antMatchers(GET, "/test2").permitAll() // remove
				.antMatchers(GET, "/user/**").authenticated() // user panels
				.antMatchers(GET, "/manager/**").authenticated() // Manager pages
				.antMatchers(GET, "/admin/**").authenticated() // Admin Pages
				.antMatchers(GET, "/test-userinfo").authenticated() // remove
				.antMatchers(GET, "/dev/**").authenticated() // - dev ops
				.antMatchers(GET, "/dev/version").permitAll() // - app version
				.antMatchers(GET, "/test-login").authenticated().and().formLogin().loginPage("/login").permitAll().and()
				.logout().logoutSuccessUrl("/login?logout").permitAll();
	}
}
