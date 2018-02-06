/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

import java.net.URISyntaxException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.constants.AuthorityLevels;
import com.craftinggamertom.constants.JSPLocation;
import com.craftinggamertom.constants.OrgUrl;
import com.craftinggamertom.constants.URLLocation;
import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.database.MongoDatabaseConnection;
import com.craftinggamertom.pageBuilders.ManageEditUserBuilder;
import com.craftinggamertom.pageBuilders.ManageFriendlyNamesBuilder;
import com.craftinggamertom.pageBuilders.PageBuilder;
import com.craftinggamertom.security.authentication.AppUser;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;
import com.craftinggamertom.updater.AppUserUpdater;
import com.craftinggamertom.updater.FriendlyNamesUpdater;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Controller
@RequestMapping("admin")
public class AdminController {

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

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		// make a ManageFriendlyNamesBuilder object and build page
		ManageFriendlyNamesBuilder builder = new ManageFriendlyNamesBuilder(org_url);

		PageAuthority pageAuthority = builder.getPageAuthority();
		UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against

		if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ADMIN)) { // Only admin and higher allowed

			try {
				model = builder.buildPage(sensorType, model); // IF NO DATABASE PRESENT THERE WILL BE ERRORS

				// EXAMPLE: DataGraphBuilder response = new DataGraphBuilder();
				// EXAMPLE: model = response.buildPage(cSensor, cTiming, cDate, model);
			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
			}

			return new ModelAndView(JSPLocation.manageSensorsFriendlyNames);
		} else

		{ // if not authorized to be on this page

			builder.buildDefaultPage(model);

			return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
		}
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
			@RequestParam(value = "is-default", defaultValue = "0") String isDefaultSensor) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		String redirectUrl = "/admin/manage/sensors/friendly-names";

		PageBuilder builder = new PageBuilder(org_url);

		PageAuthority pageAuthority = builder.getPageAuthority();
		UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against

		if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ADMIN)) { // Only admin and higher allowed

			FriendlyNamesUpdater nameUpdater = new FriendlyNamesUpdater();
			nameUpdater.updateWith(sensorID, sensorDescription, friendlyName, isVisible, isDefaultSensor);

			System.out.println("Using correct controller");
			System.out.println(sensorID);
			System.out.println(sensorDescription);
			System.out.println(friendlyName);
			System.out.println(isVisible);
			System.out.println(isDefaultSensor);

			return "redirect:" + redirectUrl;

		} else { // if not authorized to be on this page

			redirectUrl = URLLocation.unauthorized;
			return "redirect:" + redirectUrl;
		}
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

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		PageBuilder builder = new PageBuilder(org_url);

		PageAuthority adminUserAuthority = builder.getPageAuthority();
		UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against

		if (adminUserAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ADMIN)) { // Only admin and higher allowed

			try {
				// make a ManageMeetingModuleBuilder object and build page

				// EXAMPLE: DataGraphBuilder response = new DataGraphBuilder();
				// EXAMPLE: model = response.buildPage(cSensor, cTiming, cDate, model);

			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
			}

			return new ModelAndView(JSPLocation.errorPage);
		} else

		{ // if not authorized to be on this page

			builder.buildDefaultPage(model);

			return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
		}
	}

}
