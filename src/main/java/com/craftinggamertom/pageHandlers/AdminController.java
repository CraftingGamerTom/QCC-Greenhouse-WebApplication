package com.craftinggamertom.pageHandlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

	@RequestMapping(value = "/admin/manage", method = RequestMethod.GET)
	public String goToManage() {

		return "admin/manage";
	}
	@RequestMapping(value = "/admin/overrides", method = RequestMethod.GET)
	public String goToOverrides() {

		return "admin/overrides";
	}

}
