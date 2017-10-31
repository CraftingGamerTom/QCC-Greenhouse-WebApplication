package com.craftinggamertom.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.security.authentication.UserInfo;
import com.craftinggamertom.security.authorization.AppAuthorizer;

@RestController
public class SampleSecuredController {
	@RequestMapping("/test")
	public String test() {
		try {
			UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return "Welcome, " + userInfo.getName();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "nope";

	}

	@RequestMapping("/test2")
	public String test2() {
		
		UserInfo userInfo = AppAuthorizer.authorizeUser();
		
		return "Welcome, " + userInfo.getName();
	}
	
	@RequestMapping("/test-logout")
	public ModelAndView logout() {
		
		UserInfo userInfo = AppAuthorizer.authorizeUser();
		
		// return "logged out ... hopefully ... username: " + userInfo.getGivenName() + " <logout/> ";
		
		return new ModelAndView("logout");
	}
}
