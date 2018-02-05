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

		String redirectUrl = "";

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

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		ManageEditUserBuilder builder = new ManageEditUserBuilder(org_url);

		PageAuthority pageAuthority = builder.getPageAuthority();
		UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against

		if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ADMIN)) { // Only admin and higher allowed
			try {

				AppUser theUser;
				if (dbid.equals("me")) {
					theUser = userAuthority.getUser();
				} else { // Gets the user by id

					MongoDatabase database = MongoDatabaseConnection.getInstance(); // Singleton

					MongoCollection<Document> userCollection = null;
					userCollection = database.getCollection(ConfigurationReaderSingleton.getAppUserCollection());

					BasicDBObject query = new BasicDBObject();
					query.put("_id", new ObjectId(dbid));

					Document databaseResult = userCollection.find(query).first();

					theUser = new AppUser(databaseResult);
				}
				// Check if user is trying to edit a user with higher authority
				if (pageAuthority.grantAccessGTE(userAuthority, theUser.getAuthority_key())) {
					model = builder.buildPage(theUser, model);
				} else { // if not authorized to edit user with higher authority

					builder.buildDefaultPage(model);
					return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
				}
			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
			}
			return new ModelAndView(JSPLocation.manageUsersEditUser);
		} else { // if not authorized to be on this page

			builder.buildDefaultPage(model);

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

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		String redirectUrl = ""; // initialize

		PageBuilder builder = new PageBuilder(org_url);

		PageAuthority pageAuthority = builder.getPageAuthority();
		UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against

		if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ADMIN)) { // Only admin and higher allowed
			// Makes sure the _id is valid
			if (dbid.equals("me")) {
				dbid = userAuthority.getUser().getDatabaseId();
			}

			// Validate that user is not setting credentials higher than their own
			if (pageAuthority.grantAccessGTE(userAuthority, authLevel)) {

				AppUserUpdater updater = new AppUserUpdater();
				updater.updateWith(dbid, authLevel, email, phoneNum, nickname);

				redirectUrl = "/admin/manage/users/user?dbid=" + dbid; // Sends user back to user view
				return "redirect:" + redirectUrl;
			} else { // if user tried to set authLevel higher than their own

				redirectUrl = URLLocation.unauthorized;
				return "redirect:" + redirectUrl;
			}
		} else { // if not authorized to be on this page

			redirectUrl = URLLocation.unauthorized;
			return "redirect:" + redirectUrl;
		}
	}

}
