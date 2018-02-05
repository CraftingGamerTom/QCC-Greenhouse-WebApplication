/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.pageBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.ui.Model;

import com.craftinggamertom.security.authentication.AppUser;
import com.craftinggamertom.security.authorization.Authority;

public class ManageEditUserBuilder extends PageBuilder {

	public ManageEditUserBuilder(String organization_url) {
		super(organization_url);
	}

	public Model buildPage(AppUser user, Model model) {
		super.buildPage(model);

		this.model.addAllAttributes(getUserMetaData(user));

		return model;
	}

	/**
	 * IMPORTANT The user's current authority_key is on the .jsp in javascript to
	 * set the placemarker. NOT on the .jspf
	 * 
	 * @param databaseId
	 * @return
	 */
	private HashMap<String, String> getUserMetaData(AppUser user) {
		HashMap<String, String> map = new HashMap<String, String>();
		AppUser theUser = user;

		try {

			map.putAll(theUser.getAllInformation()); // Returns all the attributes
			map.put("auth-options", getAuthLevelOptions());
			return map;

		} catch (Exception e) {

		}

		return map;
	}

	/**
	 * Puts all options for the authority_key for the user. The current set
	 * authority_key level will be in a place marker. This drop down is in order of
	 * hierarchy
	 * 
	 * @return html containing all the authority_key options
	 */
	private String getAuthLevelOptions() {
		String html = "";

		Map<String, Integer> alphebetSortLevels = new Authority().getLevelsList();
		Map<Integer, String> numberSortLevels = new TreeMap<Integer, String>();

		// Puts the levels in authority level order (they are originally alphabetical)
		// Does not add "anonymous" since this will break the user if it is chosen.
		for (Map.Entry<String, Integer> m : alphebetSortLevels.entrySet()) {
			if (!m.getKey().equals("anonymous")) {
				numberSortLevels.put(m.getValue(), m.getKey());
			}
		}
		// Creates the drop down from the list above
		for (Map.Entry<Integer, String> m : numberSortLevels.entrySet()) {
			html += "<option value=\"" + m.getValue() + "\">" + m.getValue() + "</option>\r\n";
			// Does not put the option in for the user to choose something higher than their
			// own level
			if (m.getKey().equals(userAuthority.getUser().getAuthority_key())) {
				break;
			}
		}
		return html;
	}

}
