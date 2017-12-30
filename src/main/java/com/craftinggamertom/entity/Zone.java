/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.entity;

import java.util.Map;

import org.bson.Document;

/**
 * Object to group plants and sensors together
 * 
 * @author Thomas Rokicki
 *
 */
public class Zone extends Entity {

	private String id;
	private String organization;
	private String owner;
	private String name;
	private String nickname; // (friendly name for queries)
	private String plants_list; // Array of plant Ids
	private String sensors_list; // Array of Sensor Ids

	/**
	 * Creates the Zone object from a MongoDB Document
	 * 
	 * @param doc
	 */
	public Zone(Document doc) {
		super(doc);

		setId(doc.getString("id"));
		setOrganization(doc.getString("organization"));
		setOwner(doc.getString("owner"));
		setName(doc.getString("name"));
		setNickname(doc.getString("nickname"));
		setPlants_list(doc.getString("plants"));
		setSensors_list(doc.getString("sensors"));

	}

	/**
	 * Create a Zone object from scratch
	 * 
	 * This zone is not put in the database by itself. Make sure an accessor puts a
	 * new zone into the database.
	 * 
	 * @param A
	 *            unique Id for the zone
	 * @param the
	 *            Id for the organization the zone belongs to
	 * @param The
	 *            person who created the zone
	 * @param the
	 *            name of the zone
	 * @param friendly
	 *            name of the zone for display
	 * @param list
	 *            of plants that are in the zone
	 * @param list
	 *            of sensors that are in the zone
	 */
	public Zone(Map<String, String> attributes) {
		super(null); // doc must be created

		Document doc = new Document();
		doc.append(attributes.get("id"), "BAD_ID");
		doc.append(attributes.get("organization"), "NONE");
		doc.append(attributes.get("owner"), "NONE");
		doc.append(attributes.get("name"), "NO_NAME");
		doc.append(attributes.get("nickname"), getNickname());
		doc.append(attributes.get("plants"), "[]");
		doc.append(attributes.get("sensors"), "[]");

		document = doc; // Overwrite the document

		setId(doc.getString("id"));
		setOrganization(doc.getString("organization"));
		setOwner(doc.getString("owner"));
		setName(doc.getString("name"));
		setNickname(doc.getString("nickname"));
		setPlants_list(doc.getString("plants"));
		setSensors_list(doc.getString("sensors"));
		
	}

	public String getId() {
		return id;
	}

	public String getOrganization() {
		return organization;
	}

	public String getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPlants_list() {
		return plants_list;
	}

	public String getSensors_list() {
		return sensors_list;
	}

	private void setId(String id) {
		this.id = id;
	}

	private void setOrganization(String organization) {
		this.organization = organization;
	}

	private void setOwner(String owner) {
		this.owner = owner;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setNickname(String nickname) {
		this.nickname = nickname;
	}

	private void setPlants_list(String plants_list) {
		this.plants_list = plants_list;
	}

	private void setSensors_list(String sensors_list) {
		this.sensors_list = sensors_list;
	}

}
