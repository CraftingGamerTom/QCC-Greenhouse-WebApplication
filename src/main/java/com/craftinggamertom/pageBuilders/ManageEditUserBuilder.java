/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.pageBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.security.authentication.AppUser;
import com.craftinggamertom.security.authorization.Authority;
import com.craftinggamertom.security.authorization.UserAuthority;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

public class ManageEditUserBuilder extends PageBuilder {

	public ManageEditUserBuilder() {
		super();
	}

	public Model buildPage(String databaseId, Model model) {
		super.buildPage(model);

		this.model.addAllAttributes(getUserMetaData(databaseId));

		return model;
	}

	/**
	 * IMPORTANT The user's current authority_key is on the .jsp in javascript to
	 * set the placemarker. NOT on the .jspf
	 * 
	 * @param databaseId
	 * @return
	 */
	private HashMap<String, String> getUserMetaData(String databaseId) {
		HashMap<String, String> map = new HashMap<String, String>();
		AppUser theUser;

		try {
			if (databaseId.equals("me")) {
				UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against
				theUser = userAuthority.getUser();
			} else { // Gets the user by id

				MongoCollection<Document> userCollection = null;
				userCollection = database.getCollection(ConfigurationReaderSingleton.getAppUserCollection());

				BasicDBObject query = new BasicDBObject();
				query.put("_id", new ObjectId(databaseId));

				Document databaseResult = userCollection.find(query).first();

				theUser = new AppUser(databaseResult);
			}
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
		for (Map.Entry<Integer, String> m : numberSortLevels.entrySet()) {
			html += "<option value=\"" + m.getValue() + "\">" + m.getValue() + "</option>\r\n";
		}
		return html;
	}

}
