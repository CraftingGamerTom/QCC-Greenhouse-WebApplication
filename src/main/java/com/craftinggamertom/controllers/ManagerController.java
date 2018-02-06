/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

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
import com.craftinggamertom.pageBuilders.ManageUsersBuilder;
import com.craftinggamertom.pageBuilders.PageBuilder;
import com.craftinggamertom.security.authentication.AppUser;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;
import com.craftinggamertom.updater.AppUserUpdater;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Controller
@RequestMapping("manager")
public class ManagerController {

	/**
	 * Handles the manager users request
	 * 
	 * @param databaseId
	 *            the _id of the user in the database
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "manage/users", method = RequestMethod.GET)
	public ModelAndView handleManageUsersRequest(@RequestParam(value = "dbid", defaultValue = "me") String databaseId,
			Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		ManageUsersBuilder builder = new ManageUsersBuilder(org_url);

		PageAuthority adminUserAuthority = builder.getPageAuthority();
		UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against

		if (adminUserAuthority.grantAccessGTE(userAuthority, AuthorityLevels.MANAGER)) { 
			try {

				model = builder.buildPage(model);

			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
			}
			return new ModelAndView(JSPLocation.manageUsers);
		} else { // if not authorized to be on this page

			builder.buildDefaultPage(model);

			return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
		}
	}

	/**
	 * The page allows the manager or higher to make changes to the users metadata
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

		if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.MANAGER)) {
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

		if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.MANAGER)) { // Only admin and higher allowed
			// Makes sure the _id is valid
			if (dbid.equals("me")) {
				dbid = userAuthority.getUser().getDatabaseId();
			}

			// Validate that user is not setting credentials higher than their own
			if (pageAuthority.grantAccessGTE(userAuthority, authLevel)) {

				AppUserUpdater updater = new AppUserUpdater();
				updater.updateWith(dbid, authLevel, email, phoneNum, nickname);

				redirectUrl = "/manager/manage/users/user?dbid=" + dbid; // Sends user back to user view
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
