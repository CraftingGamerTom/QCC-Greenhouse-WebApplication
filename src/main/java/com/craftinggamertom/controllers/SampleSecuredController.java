package com.craftinggamertom.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.security.authentication.AppUser;
import com.craftinggamertom.security.authentication.UserInfo;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;

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

		PageAuthority pageAuthority = new PageAuthority("anonymous"); // Sets the credentials needed
		UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against
		AppUser appUser = userAuthority.getUser(); // Gets the user for referencing

		return "Welcome, " + appUser.getName();
	}

	@RequestMapping("/test-logout")
	public ModelAndView logout() {

		PageAuthority pageAuthority = new PageAuthority("anonymous"); // Sets the credentials needed
		UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against
		AppUser appUser = userAuthority.getUser(); // Gets the user for referencing

		// return "logged out ... hopefully ... username: " + appUser.getGivenName() +
		// " <logout/> ";

		return new ModelAndView("logout");
	}

	@RequestMapping("/test-userinfo")
	public String userInfo() {

		PageAuthority pageAuthority = new PageAuthority("anonymous"); // Sets the credentials needed
		UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against
		AppUser appUser = userAuthority.getUser(); // Gets the user for referencing

		String name = appUser.getName();
		String family = appUser.getFamilyName();
		String gender = appUser.getGender();
		String givenName = appUser.getGivenName();
		String id = appUser.getId();
		String link = appUser.getLink();
		String picture = appUser.getPicture();
		return "name: " + name + " family: " + family + " gender: " + gender + " givenName: " + givenName + " id: " + id
				+ " link: " + link + " picture: " + picture;
	}

}
