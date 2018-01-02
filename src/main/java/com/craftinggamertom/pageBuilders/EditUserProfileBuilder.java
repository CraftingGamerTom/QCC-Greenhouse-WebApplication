/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.pageBuilders;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.security.authentication.AppUser;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

public class EditUserProfileBuilder extends PageBuilder {

	private String userDatabaseId;

	public EditUserProfileBuilder() {
		super();
	}

	public Model buildPage(String userDatabaseId, Model theModel) {
		super.buildPage(theModel);
		this.userDatabaseId = userDatabaseId;

		model.addAllAttributes(getUserInformation());

		return model;
	}

	private Map<String, String> getUserInformation() {
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
