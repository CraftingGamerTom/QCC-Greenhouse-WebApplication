/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.entity;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

public class Observation extends Entity {

	private String database_id;
	private String organization_url;
	private String posted_by_id;
	private String posted_by;
	private String observation_date;
	private String post_date;
	private String title;
	private String priority;
	private String body;
	// TODO implement edit tracker
	// private Map<String, String> edits; // <user, ISODate>

	/**
	 * Create an Observation document from a Mongo Document
	 * 
	 * @param doc
	 *            from the observationData collection
	 */
	public Observation(Document doc) {
		super(doc);
		database_id = doc.getObjectId("_id").toString();
		organization_url = doc.getString("organization_url");
		posted_by_id = doc.getString("posted_by_id");
		posted_by = doc.getString("posted_by");
		observation_date = doc.getString("observation_date");
		post_date = doc.getString("post_date");
		title = doc.getString("title");
		priority = doc.getString("priority");
		body = doc.getString("body");

	}

	/**
	 * Creates a new Observation from scratch. This will then be used to be put into
	 * the database.
	 * 
	 * @param Map
	 *            of the key value pairs for the object except the _id variable that
	 *            will be created by MongoDB
	 */
	public Observation(Map<String, String> info) {
		super(null); // set to null - update later

		Document doc = new Document();
		doc.append("database_id", info.get("database_id"));
		doc.append("organization_url", info.get("organization_url"));
		doc.append("posted_by_id", info.get("posted_by_id"));
		doc.append("posted_by", info.get("posted_by"));
		doc.append("observation_date", info.get("observation_date"));
		doc.append("post_date", info.get("post_date"));
		doc.append("title", info.get("title"));
		doc.append("priority", info.get("priority"));
		doc.append("body", info.get("body"));

		document = doc; // Set the document

		database_id = doc.getString("database_id");
		organization_url = doc.getString("organization_url");
		posted_by_id = doc.getString("posted_by_id");
		posted_by = doc.getString("posted_by");
		observation_date = doc.getString("observation_date");
		post_date = doc.getString("post_date");
		title = doc.getString("title");
		priority = doc.getString("priority");
		body = doc.getString("body");
	}

	/**
	 * Gets the information in the form of a map to be put into the database
	 * 
	 * @return Map<String, String> Key value pair map for the object not including
	 *         the _id variable
	 */
	public Map<String, String> getInformationToPutInDatabase() {
		Map<String, String> map = new HashMap<String, String>();

		// Purposely does not include the database_id for MongoDB to create it
		map.put("organization_url", document.get("organization_url").toString());
		map.put("posted_by_id", document.get("posted_by_id").toString());
		map.put("posted_by", document.get("posted_by").toString());
		map.put("observation_date", document.get("observation_date").toString());
		map.put("post_date", document.get("post_date").toString());
		map.put("title", document.get("title").toString());
		map.put("priority", document.get("priority").toString());
		map.put("body", document.get("body").toString());

		return map;
	}

	public String getDatabase_id() {
		return database_id;
	}

	public String getOrganization_url() {
		return organization_url;
	}

	public String getPosted_by_id() {
		return posted_by_id;
	}

	public String getPosted_by() {
		return posted_by;
	}

	public String getObservation_date() {
		return observation_date;
	}

	public String getPost_date() {
		return post_date;
	}

	public String getTitle() {
		return title;
	}

	public String getPriority() {
		return priority;
	}

	public String getBody() {
		return body;
	}

}
