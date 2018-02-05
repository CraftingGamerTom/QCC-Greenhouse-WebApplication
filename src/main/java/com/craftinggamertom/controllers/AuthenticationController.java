/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.constants.AuthorityLevels;
import com.craftinggamertom.constants.JSPLocation;
import com.craftinggamertom.constants.OrgUrl;
import com.craftinggamertom.constants.URLLocation;
import com.craftinggamertom.pageBuilders.PageBuilder;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;

@Controller
public class AuthenticationController {

	/**
	 * Goes to register page which just explains the process of registering and has
	 * a link to sign in to google
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/register")
	public ModelAndView goToRegister(Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		PageBuilder builder = new PageBuilder(org_url);

		PageAuthority anonUserAuthority = builder.getPageAuthority();
		UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against

		if (anonUserAuthority.grantAccessLTE(userAuthority, AuthorityLevels.ANONYMOUS)) {

			builder.buildPage(model);

			return new ModelAndView(JSPLocation.register);
		} else { // if signed in
			return new ModelAndView("forward:" + URLLocation.USER_PROFILE);
		}

	}

	/**
	 * Goes to user home page but has the user log in first
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String goToLogin() {

		return "redirect:/user/profile";
	}

	/**
	 * For logging out
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/"; // redirect to landing page.
	}

	/**
	 * A safe place to send a user who is unauthorized to do something
	 */
	@RequestMapping("/unauthorized")
	public ModelAndView unauthorized(Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		PageBuilder builder = new PageBuilder(org_url);
		builder.buildPage(model);

		return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
	}
}
