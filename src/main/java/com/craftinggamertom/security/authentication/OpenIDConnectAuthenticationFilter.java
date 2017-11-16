package com.craftinggamertom.security.authentication;

import static java.util.Optional.empty;
import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


public class OpenIDConnectAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	@Resource
	private OAuth2RestOperations restTemplate;

	protected OpenIDConnectAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		setAuthenticationManager(authentication -> authentication); // AbstractAuthenticationProcessingFilter requires
																	// an authentication manager.
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		/*
		 * This is where there was an attempt to get all of the users information from
		 * google so that their email could automatically be added to the database.
		 * Google is not being easy about it.
		 */
		/*
		try {
			OAuth2AccessToken accessToken;
			accessToken = restTemplate.getAccessToken(); // Gets the access Token


			// see if I can read the raw info
			
			JsonNode json = restTemplate.getForObject("https://www.googleapis.com/plus/v1/people/me", JsonNode.class);
			System.out.println("(OpenIDConnectAuthenticationFilter.java)REMOVE: json=" + json.toString());
			UserInfo user = restTemplate.getForObject("https://www.googleapis.com/oauth2/v2/userinfo", UserInfo.class);
			
			
			// Puts the access token inside the userInfo object in case someone figures out
			// a way to user that token for the email - or something else cool
			
			String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
			user.setToken(idToken);
			final ResponseEntity<UserInfo> userInfoResponseEntity = new ResponseEntity<UserInfo>(user, HttpStatus.OK);
			return new PreAuthenticatedAuthenticationToken(userInfoResponseEntity.getBody(), empty(), NO_AUTHORITIES);
		} catch (InvalidTokenException e) {
			throw new BadCredentialsException("Could not obtain user details from token", e);
		} catch (OAuth2Exception e) {
			throw new BadCredentialsException("Could not obtain access token", e);
		}
		*/
		// END TALKING TO GOOGLE IN ATTEMPT TO GET EMAIL
		
		/*
		 * If the above is implemented some day ... the two lines below are not needed.
		 */
        final ResponseEntity<UserInfo> userInfoResponseEntity = restTemplate.getForEntity("https://www.googleapis.com/oauth2/v2/userinfo", UserInfo.class);
        return new PreAuthenticatedAuthenticationToken(userInfoResponseEntity.getBody(), empty(), NO_AUTHORITIES);
		
	}
}
