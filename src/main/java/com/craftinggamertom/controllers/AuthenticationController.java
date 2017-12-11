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

import com.craftinggamertom.constants.JSPLocation;
import com.craftinggamertom.pageBuilders.PageBuilder;

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

		PageBuilder builder = new PageBuilder();
		builder.buildPage(model);

		return new ModelAndView(JSPLocation.register);

	}

	/**
	 * Goes to user home page but has the user log in first
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String goToLogin() {

		return "redirect:/user/home";
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

		PageBuilder response = new PageBuilder();
		response.buildPage(model);

		return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
	}
}
