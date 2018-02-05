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
import com.craftinggamertom.pageBuilders.ManageUsersBuilder;
import com.craftinggamertom.pageBuilders.PageBuilder;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;

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

		if (adminUserAuthority.grantAccessGTE(userAuthority, AuthorityLevels.MANAGER)) { // Only admin and higher
																							// allowed
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

}
