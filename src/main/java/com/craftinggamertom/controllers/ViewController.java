/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

import java.time.ZonedDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.constants.AuthorityLevels;
import com.craftinggamertom.constants.JSPLocation;
import com.craftinggamertom.constants.OrgUrl;
import com.craftinggamertom.pageBuilders.DataGraphBuilder;
import com.craftinggamertom.pageBuilders.LiveDataBuilder;
import com.craftinggamertom.pageBuilders.RawDataBuilder;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;

@Controller
@RequestMapping("view")
public class ViewController {

	/**
	 * Handles the request to view the sensor data graph UI
	 * 
	 * @param sensorID
	 *            The ID as defined by the raspberry pi and held in the database
	 * @param timing
	 *            The timing to gather data from the appropriate table
	 * @param startDate
	 *            The first date for the data to be shown
	 * @param model
	 * @return the page containing loaded data
	 */
	@RequestMapping(value = "sensor-data")
	public ModelAndView handleSensorDataRequest(
			@RequestParam(value = "c-sensor", defaultValue = "default") String cSensor,
			@RequestParam(value = "c-timing", defaultValue = "h") String cTiming,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "start-date", defaultValue = "default") String cDate,
			Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		/**
		 * handle the default value so that the current day is what is shown
		 */
		if (cDate.equals("default")) {
			ZonedDateTime now = ZonedDateTime.now();
			cDate = now.toString().substring(0, 10); // Dont use full ZonedDateTime because it can't be parsed
		}

		model.addAttribute("c-timing", "\"" + cTiming + "\""); // Must use these quote insertions
		model.addAttribute("c-date", "\"" + cDate + "\""); // Must use these quote insertions

		try {
			DataGraphBuilder builder = new DataGraphBuilder(org_url);
			UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against
			PageAuthority pageAuthority = builder.getPageAuthority();

			if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ANONYMOUS)) {
				model = builder.buildPage(cSensor, cTiming, cDate, model);
			} else { // if not authorized to be on this page
				builder.buildDefaultPage(model);

				return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
			}

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.sensorData);
	}

	/**
	 * Handles the request to view the sensor data graph UI
	 * 
	 * @param model
	 *            for the variables
	 * @return the page containing loaded data
	 */
	@RequestMapping(value = "live-data")
	public ModelAndView handleLiveDataRequest(
			@RequestParam(value = "chosen-type", defaultValue = "default") String cType, Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		try {
			LiveDataBuilder builder = new LiveDataBuilder(org_url);
			UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against
			PageAuthority pageAuthority = builder.getPageAuthority();

			if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ANONYMOUS)) {
				model = builder.buildPage(cType, model);
			} else { // if not authorized to be on this page
				builder.buildDefaultPage(model);

				return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
			}

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.liveData);
	}

	/**
	 * Handles the request to view the sensor data graph UI
	 * 
	 * @param model
	 *            for the variables
	 * @return the page containing loaded data
	 */
	@RequestMapping(value = "raw-data")
	public ModelAndView handleRawDataRequest(@RequestParam(value = "c-sensor", defaultValue = "default") String cSensor,
			@RequestParam(value = "start-date", defaultValue = "default") String cStart,
			@RequestParam(value = "end-date", defaultValue = "default") String cEnd, Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		try {
			RawDataBuilder builder = new RawDataBuilder(org_url);
			UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against
			PageAuthority pageAuthority = builder.getPageAuthority();

			if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ANONYMOUS)) {
				model = builder.buildPage(cSensor, cStart, cEnd, model);
			} else { // if not authorized to be on this page
				builder.buildDefaultPage(model);

				return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
			}

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.rawData);
	}

}
