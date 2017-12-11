/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.controllers;

import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.craftinggamertom.constants.JSPLocation;
import com.craftinggamertom.pageBuilders.ManageEditUserBuilder;
import com.craftinggamertom.pageBuilders.ManageFriendlyNamesBuilder;
import com.craftinggamertom.pageBuilders.PageBuilder;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;
import com.craftinggamertom.updater.AppUserUpdater;
import com.craftinggamertom.updater.FriendlyNamesUpdater;

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

		return new ModelAndView(JSPLocation.manageSensorsFriendlyNames);
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
	 * The page allows the admin or higher to make changes to the users metadata
	 * 
	 * @param dbid
	 *            is the id for the user _id object
	 * @param model
	 * @return the page containing loaded data
	 */
	@RequestMapping(value = "manage/users/user", method = RequestMethod.GET)
	public ModelAndView handleEditUserRequest(@RequestParam(value = "dbid", defaultValue = "me") String dbid,
			Model model) {

		PageAuthority adminUserAuthority = new PageAuthority("admin");
		UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against

		if (adminUserAuthority.grantAccessGTE(userAuthority)) { // Only admin and higher allowed
			try {
				ManageEditUserBuilder response = new ManageEditUserBuilder();
				model = response.buildPage(dbid, model);

			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
			}
			return new ModelAndView(JSPLocation.manageUsersEditUser);
		} else { // if not authorized to be on this page

			PageBuilder response = new PageBuilder();
			response.buildPage(model);

			return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
		}
	}

	/**
	 * This is the endpoint for updating a user's meta data
	 * 
	 * @param dbid
	 *            : _id of the user to be updated
	 * @param authLevel
	 *            to replace old
	 * @param email
	 *            to replace old
	 * @param phoneNum
	 *            to replace old
	 * @param nickname
	 *            to replace old
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "manage/users/user/update", method = RequestMethod.GET)
	public String handleUpdateUserRequest(@RequestParam(value = "dbid", defaultValue = "me") String dbid,
			@RequestParam(value = "authLevel", defaultValue = "default") String authLevel,
			@RequestParam(value = "email", defaultValue = "default") String email,
			@RequestParam(value = "phoneNum", defaultValue = "default") String phoneNum,
			@RequestParam(value = "nickname", defaultValue = "default") String nickname) {

		String redirectUrl = ""; // initialize

		PageAuthority adminUserAuthority = new PageAuthority("admin");
		UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against

		// Makes sure the _id is valid
		if (dbid.equals("me")) {
			dbid = userAuthority.getUser().getDatabaseId();
		}

		if (adminUserAuthority.grantAccessGTE(userAuthority)) { // Only admin and higher allowed

			AppUserUpdater updater = new AppUserUpdater();
			updater.updateWith(dbid, authLevel, email, phoneNum, nickname);

			redirectUrl = "/admin/manage/users/user?dbid=" + dbid; // Sends user back to user view
		} else { // if not authorized to be on this page

			redirectUrl = "/unauthorized";
		}
		return "redirect:" + redirectUrl;
	}

}
