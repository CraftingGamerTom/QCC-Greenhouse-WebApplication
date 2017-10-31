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
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and().authorizeRequests()
                .antMatchers(GET, "/feed/**").permitAll()
                .antMatchers(GET, "/").permitAll()
                .antMatchers(GET, "/test").authenticated()
                .antMatchers(GET, "/test2").permitAll()
                .antMatchers(GET, "/user/**").authenticated() // user panels
                .antMatchers(GET, "/admin/**").authenticated()
                .antMatchers(GET, "/test-login").authenticated().and().formLogin().loginPage("/login").permitAll().and().logout().logoutSuccessUrl("/login?logout").permitAll();
    }
}
