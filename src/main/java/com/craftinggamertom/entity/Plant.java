/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.entity;

import java.util.Map;

import org.bson.Document;

public class Plant extends Entity {

	private String _id; // database id
	private String id;
	private String name; // (for easy query and lookup)
	private String plant_type; // String id of the PlantType Object
	private String date_created;
	private String date_planted;
	private String date_sprouted;
	private String date_matured;
	private String parent_plant_ids; // (if applicable - up to two parents array)
	private boolean is_alive; // (boolean - plant is archived if it is deceased)

	/**
	 * Create a Plant object from a document
	 * 
	 * @param doc
	 *            The Mongo Document containing Plant Object data
	 */
	public Plant(Document doc) {
		super(doc);

		setDatabaseId(doc.getObjectId("_id").toString());
		setId(doc.getString("id"));
		setName(doc.getString("name"));
		setPlant_type(doc.getString("plant_type_id"));
		setDate_created(doc.getString("date_created"));
		setDate_planted(doc.getString("date_planted"));
		setDate_sprouted(doc.getString("date_sprouted"));
		setDate_matured(doc.getString("date_matured"));
		setParent_plant_ids(doc.getString("parent_plant_ids"));
		setIs_alive(doc.getBoolean("is_alive"));

	}

	/**
	 * Create new Plant Object from scratch
	 * This object is not automatically put in the database. Make sure to use the
	 * accessor.
	 * 
	 * DOES NOT INCLUDE DATABASE ID - MUST BE GENERATED BY DATABASE
	 * 
	 * @param attributes
	 *            The Map with all the data for a new Plant object
	 */
	public Plant(Map<String, String> attributes) {
		super(null);

		Document doc = new Document();
		doc.append(attributes.get("id"), "BAD_ID");
		doc.append(attributes.get("name"), "NONE");
		doc.append(attributes.get("plant_type"), "NONE");
		doc.append(attributes.get("date_created"), "NONE");
		doc.append(attributes.get("date_planted"), "NONE");
		doc.append(attributes.get("date_sprouted"), "NONE");
		doc.append(attributes.get("date_matured"), "NONE");
		doc.append(attributes.get("parent_plant_ids"), "[]");
		doc.append(attributes.get("is_alive"), "true");

		document = doc; // Set the document

		setId(doc.getString("id"));
		setName(doc.getString("name"));
		setPlant_type(doc.getString("plant_type_id"));
		setDate_created(doc.getString("date_created"));
		setDate_planted(doc.getString("date_planted"));
		setDate_sprouted(doc.getString("date_sprouted"));
		setDate_matured(doc.getString("date_matured"));
		setParent_plant_ids(doc.getString("parent_plant_ids"));
		setIs_alive(doc.getBoolean("is_alive"));

	}

	public String getDatabaseId() {
		return _id;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPlant_type() {
		return plant_type;
	}

	public String getDate_created() {
		return date_created;
	}

	public String getDate_planted() {
		return date_planted;
	}

	public String getDate_sprouted() {
		return date_sprouted;
	}

	public String getDate_matured() {
		return date_matured;
	}

	public String getParent_plant_ids() {
		return parent_plant_ids;
	}

	public boolean getIs_alive() {
		return is_alive;
	}

	private void setDatabaseId(String _id) {
		this._id = _id;
	}

	private void setId(String id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setPlant_type(String plant_type) {
		this.plant_type = plant_type;
	}

	private void setDate_created(String date_created) {
		this.date_created = date_created;
	}

	private void setDate_planted(String date_planted) {
		this.date_planted = date_planted;
	}

	private void setDate_sprouted(String date_sprouted) {
		this.date_sprouted = date_sprouted;
	}

	private void setDate_matured(String date_matured) {
		this.date_matured = date_matured;
	}

	private void setParent_plant_ids(String parent_plant_ids) {
		this.parent_plant_ids = parent_plant_ids;
	}

	private void setIs_alive(boolean is_alive) {
		this.is_alive = is_alive;
	}

}
