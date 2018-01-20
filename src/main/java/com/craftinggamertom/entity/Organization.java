/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.database.MongoDatabaseConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Organization extends Entity {

	private String database_id;
	private String id;
	private String name;
	private String profile_picture;
	private String plan_type;
	private boolean is_public; // TODO Implement
	private Map<String, String> users;
	private String date_created;
	private String last_activity;
	private String header;
	private String address;
	private String phone_number;
	private String website;
	private List<String> sensors;
	private List<String> zones;
	private String sensor_count;
	private String member_count;
	private String plant_count;
	private List<String> gallery_photos; // TODO implement

	/**
	 * Set the document based on the organization's unique url (id)
	 * 
	 * @param String
	 *            the organization's url
	 */
	public Organization(String orgURL) {
		super(null); // Not known yet

		// TODO Implement Organizations
		/*
		 * MongoDatabase database = MongoDatabaseConnection.getInstance(); // Singleton
		 * 
		 * // Creates the object to search for that represents the user based on their
		 * _id
		 * BasicDBObject query = new BasicDBObject();
		 * query.put("id", new ObjectId(orgURL));
		 * 
		 * MongoCollection<Document> orgCollection = database
		 * .getCollection(ConfigurationReaderSingleton.getOrganizationCollection());
		 * Document doc = orgCollection.find(query).first(); // Only ever one (url is
		 * unique)
		 * 
		 * document = doc; // Sets the document so it is no longer null
		 * 
		 * set_database_id(doc.getObjectId("_id").toString());
		 * setId(doc.getString("id"));
		 * setName(doc.getString("name"));
		 * setProfilePicture(doc.getString("profile_picture"));
		 * setPlan_type(doc.getString("plan_type"));
		 * setGallery_photos(doc.getString("gallery_photos"));
		 * setUsers(doc.getString("users"));
		 * setDate_created(doc.getString("date_created"));
		 * setLast_activity(doc.getString("last_activity"));
		 * setHeader(doc.getString("header"));
		 * setAddress(doc.getString("address"));
		 * setPhone_number(doc.getString("phone_number"));
		 * setWebsite(doc.getString("website"));
		 * setZones(doc.getString("zones"));
		 * setSensors(doc.getString("sensors"));
		 * setSensor_count(doc.getString("sensor_count"));
		 * setMember_count(doc.getString("member_count"));
		 * setPlant_count(doc.getString("plant_count"));
		 */
		// TODO REMOVE BELOW - once organization is implemented

		// Start building gallery list
		List<String> photos = new ArrayList<String>();
		photos.add("http://drive.google.com/uc?export=view&id=1dFTvpCTGfg680QPfDe4JotRVWEz08Het");
		photos.add("http://drive.google.com/uc?export=view&id=1xzI34k-NyFz53BWfSDoaXuSo4fYGj3iG");
		photos.add("http://drive.google.com/uc?export=view&id=1RjdEsubr6_w1yPYI5jQhgajac7rDYAnN");
		photos.add("http://drive.google.com/uc?export=view&id=1fiVeDHo_HpwV8miuZOGjW2LdRPvksU2m");
		photos.add("http://drive.google.com/uc?export=view&id=1dTrHs7uwhelHUJcsYd5kg7AjP7tPmG8d");
		// End build gallery list

		set_database_id("none");
		setId("qcc-greenhouse");
		setName("Quinsigamond Community College");
		setProfilePicture("http://drive.google.com/uc?export=view&id=1Kq4UrPTzNdFC1nI9uxWJiEs4Irq3dMDU");
		setPlan_type("school");
		setGallery_photos(photos.toString());
		setUsers("[]");
		setDate_created("none");
		setLast_activity("none");
		setHeader("PTK Live and Learn Greenhouse");
		setAddress("670 W Boylston St, Worcester, MA 01606");
		setPhone_number("(508) 853-2300");
		setWebsite("http://www.qcc.edu/");
		setZones("[]");
		setSensors("[]");
		setSensor_count("0");
		setMember_count("0");
		setPlant_count("0");

	}

	/**
	 * Pass in a organization document for it to be converted into an organization
	 * object
	 * 
	 * @param doc
	 */
	public Organization(Document doc) {
		super(doc);

		set_database_id(doc.getObjectId("_id").toString());
		setId(doc.getString("id"));
		setName(doc.getString("name"));
		setProfilePicture(doc.getString("profile_picture"));
		setPlan_type(doc.getString("plan_type"));
		setGallery_photos(doc.getString("gallery_photos"));
		setUsers(doc.getString("users"));
		setDate_created(doc.getString("date_created"));
		setLast_activity(doc.getString("last_activity"));
		setHeader(doc.getString("header"));
		setAddress(doc.getString("address"));
		setPhone_number(doc.getString("phone_number"));
		setWebsite(doc.getString("website"));
		setZones(doc.getString("zones"));
		setSensors(doc.getString("sensors"));
		setSensor_count(doc.getString("sensor_count"));
		setMember_count(doc.getString("member_count"));
		setPlant_count(doc.getString("plant_count"));

	}

	public String get_database_id() {
		return database_id;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getProfilePicture() {
		return profile_picture;
	}

	public String getPlan_type() {
		return plan_type;
	}

	public List<String> getGallery_photos() {
		return gallery_photos;
	}

	public Map<String, String> getUsers() {
		return users;
	}

	public String getDate_created() {
		return date_created;
	}

	public String getLast_activity() {
		return last_activity;
	}

	public String getHeader() {
		return header;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public String getWebsite() {
		return website;
	}

	public List<String> getSensors() {
		return sensors;
	}

	public List<String> getZones() {
		return zones;
	}

	public String getSensor_count() {
		return sensor_count;
	}

	public String getMember_count() {
		return member_count;
	}

	public String getPlant_count() {
		return plant_count;
	}

	private void set_database_id(String database_id) {
		this.database_id = database_id;
	}

	private void setId(String id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setProfilePicture(String picture_url) {
		this.profile_picture = picture_url;
	}

	private void setPlan_type(String plan_type) {
		this.plan_type = plan_type;
	}

	private void setGallery_photos(String users) {
		users = users.substring(1, users.length() - 1);

		List<String> photoList = new ArrayList<String>(Arrays.asList(users.split(",")));

		this.gallery_photos = photoList;
	}

	private void setUsers(String users) {
		Map<String, String> userMap = new HashMap<String, String>();

		// Read the string and convert it into the hashmap

		this.users = userMap;
	}

	private void setDate_created(String date_created) {
		this.date_created = date_created;
	}

	private void setLast_activity(String last_activity) {
		this.last_activity = last_activity;
	}

	private void setHeader(String header) {
		this.header = header;
	}

	private void setAddress(String address) {
		this.address = address;
	}

	private void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	private void setWebsite(String website) {
		this.website = website;
	}

	private void setSensors(String sensors) {
		List<String> sensorList = new ArrayList<String>(Arrays.asList(sensors));
		// TODO Make sure this asList call functions properly
		this.sensors = sensorList;
	}

	private void setZones(String zones) {
		List<String> zoneList = new ArrayList<String>(Arrays.asList(zones));
		// TODO Make sure this asList call functions properly
		this.zones = zoneList;
	}

	private void setSensor_count(String sensor_count) {
		this.sensor_count = sensor_count;
	}

	private void setMember_count(String member_count) {
		this.member_count = member_count;
	}

	private void setPlant_count(String plant_count) {
		this.plant_count = plant_count;
	}

}
