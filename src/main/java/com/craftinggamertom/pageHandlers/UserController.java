package com.craftinggamertom.pageHandlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	@RequestMapping(value = "/user/postObservation", method = RequestMethod.GET)
	public String goToPostObservation() {

		return "user/postObservation";
	}
	
	@RequestMapping(value = "/testing", method = RequestMethod.GET)
	public String goToTesting() {

		return "default/welcome";
	}

}
