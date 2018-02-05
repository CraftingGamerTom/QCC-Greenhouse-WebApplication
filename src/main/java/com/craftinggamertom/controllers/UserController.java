/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

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
import com.craftinggamertom.pageBuilders.EditUserProfileBuilder;
import com.craftinggamertom.pageBuilders.PageBuilder;
import com.craftinggamertom.pageBuilders.UserProfileBuilder;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;
import com.craftinggamertom.updater.AppUserUpdater;

@Controller
@RequestMapping("user")
public class UserController {

	/**
	 * Handles the request to view a users profile
	 * 
	 * @param model
	 *            for the variables
	 * @return the page containing loaded data
	 */
	@RequestMapping(value = "profile")
	public ModelAndView handleProfileRequest(@RequestParam(value = "dbid", defaultValue = "me") String userDatabaseId,
			Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		UserProfileBuilder builder = new UserProfileBuilder(org_url);

		PageAuthority userUnverifiedAuthority = builder.getPageAuthority();
		UserAuthority userAuthority = builder.getUserAuthority();

		// If the user is accessing their own profile
		if (userDatabaseId.equals("me")) {
			userDatabaseId = userAuthority.getUser().getDatabaseId();
		}
		// If signed in
		if (userUnverifiedAuthority.grantAccessGTE(userAuthority, AuthorityLevels.UNVERIFIED)) {
			try {
				model = builder.buildPage(userDatabaseId, model);
			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
			}

			return new ModelAndView(JSPLocation.userProfile);
		} else {
			builder.buildPage(model); // Calls build page of PageBuilder to load navbar
			return new ModelAndView(JSPLocation.unauthorized);
		}
	}

	/**
	 * Page for the user (or admin) to edit their profile
	 * 
	 * @param databaseId
	 *            the dbid of the user whos profile will be edited
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "profile/edit", method = RequestMethod.GET)
	public ModelAndView goToEditUserProfile(@RequestParam(value = "dbid", defaultValue = "me") String databaseId,
			Model model) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		EditUserProfileBuilder builder = new EditUserProfileBuilder(org_url);

		UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against
		PageAuthority userUserAuthority = builder.getPageAuthority();

		// Sets the dbid to the user who made the call
		if (databaseId.equals("me")) {
			databaseId = userAuthority.getUser().getDatabaseId();
		}

		// Admin or the the user can access
		if (userUserAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ADMIN)
				|| userAuthority.getUser().getDatabaseId() == databaseId) {
			try {
				model = builder.buildPage(databaseId, model);
			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
			}
			return new ModelAndView(JSPLocation.editUserProfile);

		} else { // if not authorized to be on this page

			builder.buildDefaultPage(model);

			return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
		}

	}

	/**
	 * The endpoint to update the users profile
	 * 
	 * @param dbid
	 * @param authLevel
	 * @param email
	 * @param phoneNum
	 * @param nickname
	 * @return
	 */
	@RequestMapping(value = "profile/update", method = RequestMethod.GET)
	public String handleUpdateUserRequest(@RequestParam(value = "dbid", defaultValue = "me") String dbid,
			@RequestParam(value = "email", defaultValue = "default") String email,
			@RequestParam(value = "phoneNum", defaultValue = "default") String phoneNum,
			@RequestParam(value = "nickname", defaultValue = "default") String nickname) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		String redirectUrl = ""; // initialize

		PageBuilder builder = new PageBuilder(org_url);

		PageAuthority adminUserAuthority = builder.getPageAuthority();
		UserAuthority userAuthority = builder.getUserAuthority(); // Gets the user to check against

		// Admin or the user
		if (adminUserAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ADMIN)
				|| userAuthority.getUser().getDatabaseId().equals(dbid)) {
			// Makes sure the _id is valid
			if (dbid.equals("me")) {
				dbid = userAuthority.getUser().getDatabaseId();
			}

			AppUserUpdater updater = new AppUserUpdater();
			updater.updateWith(dbid, email, phoneNum, nickname);

			redirectUrl = "/user/profile/edit?dbid=" + dbid; // Sends user back to user view
			return "redirect:" + redirectUrl;
		} else { // if not authorized to be on this page

			redirectUrl = URLLocation.unauthorized;
			return "redirect:" + redirectUrl;
		}
	}

}
