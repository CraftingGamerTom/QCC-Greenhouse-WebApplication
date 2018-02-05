/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers.rest;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.craftinggamertom.constants.AuthorityLevels;
import com.craftinggamertom.constants.OrgUrl;
import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.database.MongoDatabaseConnection;
import com.craftinggamertom.entity.Observation;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Rest API controllers for entity objects
 * 
 * @author Thomas Rokicki
 *
 */
@RestController
// @RequestMapping("{organization-url}/api")
@RequestMapping("api")
public class EntityAPI {

	/**
	 * Create a new Observation
	 * 
	 * @param observation_id
	 * @return
	 */
	@RequestMapping(value = "/observation", method = RequestMethod.POST)
	public ResponseEntity<String> newObservation(
			/* @PathVariable("organization-url") String organization_url, */ @RequestBody String requestBody) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		final ObjectMapper mapper = new ObjectMapper();

		PageAuthority pageAuthority = new PageAuthority(org_url);
		UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against

		if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.USER)) {
			try {

				// Convert Contents of the pay load to node objects
				JsonNode json = mapper.readTree(requestBody);

				// Create the Observation ZonedDateTime & convert it to UTC time zone
				String observation_calander_date = json.get("date").textValue();
				String observation_time = json.get("time").textValue();
				ZonedDateTime dateTime = ZonedDateTime.parse(observation_calander_date + "T" + observation_time
						+ ":00.000" + userAuthority.getUser().getTime_zone()).withZoneSameInstant(ZoneOffset.UTC);

				// Set Strings
				String title = json.get("title").textValue();
				String priority = json.get("priority").textValue();
				String body = json.get("body").textValue();
				String observation_date = dateTime.toString();
				String post_date = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toString();
				String posted_by_id = userAuthority.getUser().getDatabaseId();
				String posted_by = userAuthority.getUser().getName();

				// System.out.println(observation_calander_date);
				// System.out.println(observation_time);
				// System.out.println(observation_date);
				// System.out.println(title);
				// System.out.println(priority);
				// System.out.println(body);

				// Establish Connection to Database
				MongoDatabase database = MongoDatabaseConnection.getInstance();
				MongoCollection<Document> collection = database
						.getCollection(ConfigurationReaderSingleton.getObservationCollection());

				// Create Map of Observation attributes
				Map<String, String> data = new HashMap<String, String>();
				data.put("organization_url", org_url);
				data.put("posted_by_id", posted_by_id);
				data.put("posted_by", posted_by);
				data.put("observation_date", observation_date);
				data.put("post_date", post_date);
				data.put("title", title);
				data.put("priority", priority);
				data.put("body", body);

				// Create Observation object from Map
				Observation newObservation = new Observation(data);

				// Put new Observation into the Database
				Document document = new Document();
				document.putAll(newObservation.getInformationToPutInDatabase());
				collection.insertOne(document);

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

	@RequestMapping(value = "/observation/{observation-id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable("organization-url") String organization_url,
			@PathVariable("observation-id") String observation_id) {

		// TODO Remove with organization implementation
		String org_url = OrgUrl.QCC;

		PageAuthority pageAuthority = new PageAuthority(org_url);
		UserAuthority userAuthority = new UserAuthority(); // Gets the user to check against

		if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.ADMIN)) { // Only admin and higher allowed
			try {

				MongoDatabase database = MongoDatabaseConnection.getInstance(); // Singleton

				// Creates the object to search for that represents the user based on their _id
				BasicDBObject query = new BasicDBObject();
				query.put("_id", new ObjectId(observation_id));

				MongoCollection<Document> userCollection = database
						.getCollection(ConfigurationReaderSingleton.getObservationCollection());
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
