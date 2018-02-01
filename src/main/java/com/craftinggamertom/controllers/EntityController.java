/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.constants.JSPLocation;
import com.craftinggamertom.pageBuilders.ObservationsBuilder;
import com.craftinggamertom.pageBuilders.PageBuilder;

/**
 * The EntityController class contains the controllers for all entities used on
 * the app. We did not want a separate controller for each entity.
 * 
 * @author Thomas Rokicki
 *
 */
@Controller
// @RequestMapping("{organization-url}")
public class EntityController {

	/**
	 * End point for the observations list for an organization
	 * 
	 * Parameters are not listed
	 * 
	 * @return ModelAndView of the page
	 */
	@RequestMapping(/* @PathVariable("organization-url") String organization_url, */ value = "observations", method = RequestMethod.GET)
	public ModelAndView goToObservations(Model model,
			@RequestParam(value = "s-date", defaultValue = "default") String startDate,
			@RequestParam(value = "e-date", defaultValue = "default") String endDate) {

		// TODO Remove with organization implementation
		String org_url = "qcc";

		try {
			ObservationsBuilder builder = new ObservationsBuilder();
			boolean isAuthorized = builder.grantAccess(org_url);
			if (isAuthorized) {
				builder.buildPage(model, startDate, endDate);
			} else { // if not authorized to be on this page
				builder.buildDefaultPage(model);

				return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
			}

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.observations);
	}
}
