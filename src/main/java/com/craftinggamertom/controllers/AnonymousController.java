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

import com.craftinggamertom.constants.AuthorityLevels;
import com.craftinggamertom.constants.JSPLocation;
import com.craftinggamertom.constants.OrgUrl;
import com.craftinggamertom.constants.URLLocation;
import com.craftinggamertom.pageBuilders.DashboardBuilder;
import com.craftinggamertom.pageBuilders.DashboardGalleryBuilder;
import com.craftinggamertom.pageBuilders.DashboardInfoBuilder;
import com.craftinggamertom.pageBuilders.DashboardMembersBuilder;
import com.craftinggamertom.pageBuilders.PageBuilder;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;

@Controller
public class AnonymousController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage() {
		return "redirect:" + URLLocation.DASHBOARD;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboardPage(Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		try {

			DashboardInfoBuilder builder = new DashboardInfoBuilder(org_url);

			UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against
			PageAuthority pageAuthority = builder.getPageAuthority();

			if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ANONYMOUS)) {
				model = builder.buildPage(model);
			} else { // if not authorized to be on this page
				builder.buildDefaultPage(model);
				return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
			}

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

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		try {

			DashboardGalleryBuilder builder = new DashboardGalleryBuilder(org_url);

			UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against
			PageAuthority pageAuthority = builder.getPageAuthority();

			if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ANONYMOUS)) {
				model = builder.buildPage(model);
			} else { // if not authorized to be on this page
				builder.buildDefaultPage(model);
				return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
			}

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.organizationDashboardGallery);
	}

	@RequestMapping(value = "/dashboard/members", method = RequestMethod.GET)
	public ModelAndView dashboardMembersPage(Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		try {

			DashboardMembersBuilder builder = new DashboardMembersBuilder(org_url);
			UserAuthority userAuthority = builder.getUserAuthority();
			PageAuthority pageAuthority = builder.getPageAuthority();

			if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ANONYMOUS)) {
				model = builder.buildPage(model);
			} else { // if not authorized to be on this page
				builder.buildDefaultPage(model);
				return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
			}

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.organizationDashboardMembers);
	}

	@RequestMapping(value = "/policy", method = RequestMethod.GET)
	public ModelAndView policyPage(Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		try {

			PageBuilder builder = new PageBuilder(org_url);
			model = builder.buildPage(model);

			UserAuthority userAuthority = builder.getUserAuthority();
			PageAuthority pageAuthority = builder.getPageAuthority();

			if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ANONYMOUS)) {
				model = builder.buildPage(model);
			} else { // if not authorized to be on this page
				builder.buildDefaultPage(model);
				return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
			}

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.policy);
	}

	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public ModelAndView termsPage(Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		try {

			PageBuilder builder = new PageBuilder(org_url);

			UserAuthority userAuthority = builder.getUserAuthority();
			PageAuthority pageAuthority = builder.getPageAuthority();

			if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ANONYMOUS)) {
				model = builder.buildPage(model);
			} else { // if not authorized to be on this page
				builder.buildDefaultPage(model);
				return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
			}

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.terms);
	}

}