/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

import java.time.ZonedDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.constants.JSPLocation;
import com.craftinggamertom.constants.URLLocation;
import com.craftinggamertom.pageBuilders.DataGraphBuilder;
import com.craftinggamertom.pageBuilders.LiveDataBuilder;
import com.craftinggamertom.pageBuilders.PageBuilder;
import com.craftinggamertom.pageBuilders.RawDataBuilder;
import com.craftinggamertom.pageBuilders.UserProfileBuilder;
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
			DataGraphBuilder response = new DataGraphBuilder();
			model = response.buildPage(cSensor, cTiming, cDate, model);

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

		try {
			LiveDataBuilder response = new LiveDataBuilder();
			model = response.buildPage(cType, model);

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

		try {
			RawDataBuilder response = new RawDataBuilder();
			model = response.buildPage(cSensor, cStart, cEnd, model);

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView(JSPLocation.rawData);
	}

	/**
	 * Handles request to view the observations There is the ability to POST
	 * observations here too using the REST client
	 * 
	 * @return
	 */
	@RequestMapping(value = "observations", method = RequestMethod.GET)
	public String goToObservation() {

		return URLLocation.observations;
	}

}
