/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.pageBuilders;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.security.authentication.AppUser;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

public class UserProfileBuilder extends PageBuilder {

	private String userDatabaseId;

	public UserProfileBuilder() {
		super();
	}

	public Model buildPage(String userDatabaseId, Model theModel) {
		this.userDatabaseId = userDatabaseId;
		super.buildPage(theModel); // Adds the standard model attributes

		model.addAllAttributes(addUserInformation());
		model.addAllAttributes(addConfigOptions());

		return model;
	}

	/**
	 * Adds the buttons that the user or admins can use to edit the users profile
	 * 
	 * @return Map of the HTML for the buttons
	 */
	private Map<String, String> addConfigOptions() {
		HashMap<String, String> map = new HashMap<String, String>();

		PageAuthority adminAuthority = new PageAuthority("admin");

		// if the current user is the user, and admin, or higher than an admin
		if (userAuthority.getUser().getDatabaseId() == userDatabaseId || adminAuthority.grantAccessGTE(userAuthority)) {
			map.put("edit-profile-btn", "				<div class=\"user-button\">\r\n"
					+ "					<div class=\"row text-center\">\r\n"
					+ "                                            	    <button onclick=\"window.location.href='/user/profile/edit?dbid="
					+ userAuthority.getUser().getDatabaseId()
					+ "'\" class=\"btn-success btn btn-xs\"><i class=\"fa fa-gear\"></i> Edit Profile</button>\r\n"
					+ "					</div>\r\n" + "				</div>\r\n");
		} else {
			map.put("edit-profile-btn", "");
		}

		return map;
	}

	/**
	 * Adds the users information to be displayed
	 * 
	 * @return Map<String, String> A map of the attributes
	 */
	private Map<String, String> addUserInformation() {
		HashMap<String, String> map = new HashMap<String, String>();

		MongoCollection<Document> collection = null;
		collection = database.getCollection(ConfigurationReaderSingleton.getAppUserCollection());

		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(userDatabaseId));

		Document databaseResult = collection.find(query).first();

		AppUser theUser = new AppUser(databaseResult);

		map.putAll(theUser.getAllInformation()); // Returns all the attributes
		return map;
	}

}
