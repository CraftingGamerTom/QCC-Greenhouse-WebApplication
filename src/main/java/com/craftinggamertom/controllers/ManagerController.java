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

import com.craftinggamertom.constants.JSPLocation;
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

		PageAuthority adminUserAuthority = new PageAuthority("manager");
		UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against

		if (adminUserAuthority.grantAccessGTE(userAuthority)) { // Only admin and higher allowed
			try {
				ManageUsersBuilder response = new ManageUsersBuilder();
				model = response.buildPage(model);

			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
			}
			return new ModelAndView(JSPLocation.manageUsers);
		} else { // if not authorized to be on this page

			PageBuilder response = new PageBuilder();
			response.buildPage(model);

			return new ModelAndView(JSPLocation.unauthorized); // unauthorized page
		}
	}

}
