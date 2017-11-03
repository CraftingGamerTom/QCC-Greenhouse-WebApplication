package com.craftinggamertom.controllers;

import java.net.URISyntaxException;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.pageBuilders.ManageFriendlyNamesBuilder;
import com.craftinggamertom.updater.FriendlyNamesUpdater;

@Controller
@RequestMapping("admin")
public class AdminController {

	/**
	 * Landing page for admin users
	 * 
	 * @return Dashboard for admins
	 */
	@RequestMapping(value = "")
	public String handleAdminRequest() {
		return "admin/test";
	}

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
	@RequestMapping(value = "manage/sensors/friendly-names", method = RequestMethod.GET)
	public ModelAndView handleManageFriendlyNamesRequest(
			@RequestParam(value = "chosen-type", defaultValue = "all") String sensorType, Model model) {

		try {
			// make a ManageFriendlyNamesBuilder object and build page
			ManageFriendlyNamesBuilder response = new ManageFriendlyNamesBuilder();
			model = response.buildPage(sensorType, model); // IF NO DATABASE PRESENT THERE WILL BE ERRORS

			// EXAMPLE: DataGraphBuilder response = new DataGraphBuilder();
			// EXAMPLE: model = response.buildPage(cSensor, cTiming, cDate, model);
		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView("admin/manage/sensors/friendly-names");
	}

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
	 * @throws URISyntaxException
	 */
	@RequestMapping(value = "manage/sensors/friendly-names/update", method = RequestMethod.GET)
	public String handleUpdateFriendlyNamesRequest(@RequestParam(value = "sensor-id", required = true) String sensorID,
			@RequestParam(value = "description", required = true) String sensorDescription,
			@RequestParam(value = "friendly-name", required = true) String friendlyName,
			@RequestParam(value = "is-visible", defaultValue = "0") String isVisible,
			@RequestParam(value = "is-default", defaultValue = "0") String isDefaultSensor, Model model) {

		FriendlyNamesUpdater nameUpdater = new FriendlyNamesUpdater();
		nameUpdater.updateWith(sensorID, sensorDescription, friendlyName, isVisible, isDefaultSensor);

		System.out.println("Using correct controller");
		System.out.println(sensorID);
		System.out.println(sensorDescription);
		System.out.println(friendlyName);
		System.out.println(isVisible);
		System.out.println(isDefaultSensor);

		String redirectUrl = "";
		return "redirect:" + redirectUrl;
	}

	/**
	 * Handles the request to view the sensor data graph UI
	 * 
	 * @param model
	 *            for the variables
	 * @return the page containing loaded data
	 */
	@RequestMapping(value = "manage/modules/meeting")
	public ModelAndView handleManageMeetingModuleRequest(Model model) {

		try {
			// make a ManageMeetingModuleBuilder object and build page

			// EXAMPLE: DataGraphBuilder response = new DataGraphBuilder();
			// EXAMPLE: model = response.buildPage(cSensor, cTiming, cDate, model);

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
		}

		return new ModelAndView("admin/test");
	}

	/**
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
	@RequestMapping(value = "test")
	public ModelAndView handleTestRequest(@RequestParam(value = "sensorID", defaultValue = "rp1-01") String sensorID,
			@RequestParam(value = "timing", defaultValue = "hourly") String timing,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "startDate", defaultValue = "2000-10-31") String startDate,
			Model model) {

		model.addAttribute(
				"sensor report test with ID, timing, and start-time : " + sensorID + ", " + timing + ", " + startDate);
		return new ModelAndView("admin/test", "sensorID", sensorID);
	}

}
