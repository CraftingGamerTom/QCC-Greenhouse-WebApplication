/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.pageBuilders;

import java.util.Collection;
import java.util.HashMap;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class ManageEditUserBuilder extends PageBuilder {

	public ManageEditUserBuilder() {
		super();
	}

	public Model buildPage(String databaseId, Model model) {
		super.buildPage(model);

		this.model.addAllAttributes(getUserMetaData(databaseId));

		return model;
	}

	private HashMap<String, String> getUserMetaData(String databaseId) {
		HashMap<String, String> map = new HashMap<String, String>();
		Bson idFilter = Filters.eq("_id", databaseId);
		
		try {
			
			MongoCollection<Document> userCollection = null;
			userCollection = database.getCollection(ConfigurationReaderSingleton.getAppUserCollection());
			
			Document defaultResult = userCollection.find(idFilter).first();
			String name = defaultResult.getString("name");
			
		}catch(Exception e) {
			
		}
		
		return map;
	}

}
