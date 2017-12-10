/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.updater;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.database.MongoDatabaseConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

public class AppUserUpdater {

	protected MongoDatabase database;

	public AppUserUpdater() {
		database = MongoDatabaseConnection.getInstance(); // Singleton
	}

	public boolean updateWith(String dbid, String authLevel, String email, String phoneNum, String nickname) {

		// To be returned
		boolean successful = true;

		// Creates the object to search for that represents the user based on their _id
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(dbid));

		// Update-able filters
		Bson updateAuthLevel = Updates.set("authority_key", authLevel);
		Bson updateEmail = Updates.set("email_address", email);
		Bson updatePhone = Updates.set("cell_phone", phoneNum);
		Bson updateNickname = Updates.set("nickname", nickname);

		try {
			MongoCollection<Document> userCollection = database
					.getCollection(ConfigurationReaderSingleton.getAppUserCollection());

			if (!authLevel.equals("default")) {

				userCollection.findOneAndUpdate(query, updateAuthLevel);
			}
			if (!email.equals("default")) {

				userCollection.findOneAndUpdate(query, updateEmail);
			}
			if (!phoneNum.equals("default")) {

				// Format the phone number
				phoneNum = phoneNum.replaceAll("\\D+", "");
				phoneNum = String.valueOf(phoneNum).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");

				userCollection.findOneAndUpdate(query, updatePhone);
			}
			if (!nickname.equals("default")) {

				userCollection.findOneAndUpdate(query, updateNickname);
			}

		} catch (Exception e) {
			System.out.println("Error while updating user meta data: ");
			e.printStackTrace();
			successful = false;
		}

		return successful;

	}

}
