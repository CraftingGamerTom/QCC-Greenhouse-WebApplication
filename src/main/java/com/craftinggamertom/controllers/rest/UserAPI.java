/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers.rest;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.craftinggamertom.constants.AuthorityLevels;
import com.craftinggamertom.constants.OrgUrl;
import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.database.MongoDatabaseConnection;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * This API is for controlling everything about a user.
 * 
 * @author Thomas Rokicki
 *
 */
@RestController
@RequestMapping("api/user")
public class UserAPI {

	/**
	 * Use this API to delete users from the database.
	 * 
	 * @param dbid
	 *            the datebase _id of the user to be deleted (dbid)
	 * @return OK if the user is deleted, InternalServerError if failed,
	 *         Unauthorized if unauthorized
	 */
	@RequestMapping(value = "/{dbid}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable String dbid) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		PageAuthority pageAuthority = new PageAuthority(org_url);
		UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against

		if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ADMIN)) { // Only admin and higher allowed
			try {

				MongoDatabase database = MongoDatabaseConnection.getInstance(); // Singleton

				// Creates the object to search for that represents the user based on their _id
				BasicDBObject query = new BasicDBObject();
				query.put("_id", new ObjectId(dbid));

				MongoCollection<Document> userCollection = database
						.getCollection(ConfigurationReaderSingleton.getAppUserCollection());
				Document deletedDoc = userCollection.findOneAndDelete(query);
				if (deletedDoc == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"NOT FOUND\"}");
				}
				return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"SUCCESS\"}");

			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("{\"message\": \"INTERNAL SERVER ERROR\"}");
			}

		} else { // if not authorized to make request
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"UNAUTHORIZED\"}");
		}

	}

}
