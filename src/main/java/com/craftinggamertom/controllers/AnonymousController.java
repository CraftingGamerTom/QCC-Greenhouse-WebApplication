/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.constants.JSPLocation;
import com.craftinggamertom.pageBuilders.DashboardBuilder;
import com.craftinggamertom.pageBuilders.DashboardGalleryBuilder;
import com.craftinggamertom.pageBuilders.DashboardInfoBuilder;
import com.craftinggamertom.pageBuilders.DashboardMembersBuilder;
import com.craftinggamertom.pageBuilders.PageBuilder;

@Controller
public class AnonymousController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage() {
		String redirectUrl = "dashboard";
		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboardPage(Model model) {

		try {

			DashboardInfoBuilder response = new DashboardInfoBuilder();
			model = response.buildPage(model, "qcc-greenhouse"); // Organization URL

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.organizationDashboard);
	}
	
	// TODO use the information below to set up organizations
	@RequestMapping(value = "/{organization-url}/dashboard", method = RequestMethod.GET)
	public String dashboardTest(Model model, @PathVariable("organization-url") String organization_url) {

		System.out.println(organization_url);
		
		return organization_url;
	}
	
	@RequestMapping(value = "/dashboard/gallery", method = RequestMethod.GET)
	public ModelAndView dashboardGalleryPage(Model model) {

		try {

			DashboardGalleryBuilder response = new DashboardGalleryBuilder();
			model = response.buildPage(model, "qcc-greenhouse");

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.organizationDashboardGallery);
	}
	
	@RequestMapping(value = "/dashboard/members", method = RequestMethod.GET)
	public ModelAndView dashboardMembersPage(Model model) {

		try {

			DashboardMembersBuilder response = new DashboardMembersBuilder();
			model = response.buildPage(model, "qcc-greenhouse");

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.organizationDashboardMembers);
	}

	@RequestMapping(value = "/policy", method = RequestMethod.GET)
	public ModelAndView policyPage(Model model) {

		try {

			PageBuilder response = new PageBuilder();
			model = response.buildPage(model);

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.policy);
	}

	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public ModelAndView termsPage(Model model) {

		try {

			PageBuilder response = new PageBuilder();
			model = response.buildPage(model);

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.terms);
	}

}