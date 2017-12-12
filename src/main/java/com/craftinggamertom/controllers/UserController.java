/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.craftinggamertom.constants.JSPLocation;
import com.craftinggamertom.constants.URLLocation;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;

@Controller
public class UserController {

	/**
	 * Landing page for signed in user accounts
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public String goToUserHome() {

		PageAuthority userUserAuthority = new PageAuthority("admin");
		UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against

		if (userUserAuthority.grantAccessGTE(userAuthority)) { // Only admin and higher allowed
			/**
			 * Dashboard?
			 */
			return "redirect:/feed"; // Temp until Organization Feed page is implemented
		} else { // if not authorized to be on this page

			return "redirect:" + URLLocation.unauthorized;
		}
	}

}
