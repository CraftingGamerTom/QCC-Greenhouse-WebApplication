/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	@RequestMapping(value = "/user/postObservation", method = RequestMethod.GET)
	public String goToPostObservation() {

		return "user/postObservation";
	}

	/**
	 * Landing page for signed in user accounts
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public String goToUserHome() {

		return "redirect:/feed"; // Temp until Organization Feed page is implemented
	}

}
