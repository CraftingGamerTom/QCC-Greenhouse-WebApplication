/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.entity;

import java.util.Map;

import org.bson.Document;

public class PestType extends Entity {

	private String id;
	private String name;
	private String latin_name;
	private String type;
	private String location;
	private String habitat;
	private String growth_states;
	private String active_time; // (seasons when active?)
	private String diet; // (what does it eat or require for growth)
	private String threatens; // (list what it threatens)
	private String benefits; // (List what it helps)
	private String symptoms; // (Symptoms of pest)
	private String predators;
	private String deterrents;
	private String remedies;

	/**
	 * New PestType from a document
	 * 
	 * @param doc
	 *            The PestType Document
	 */
	public PestType(Document doc) {
		super(doc);

		setId(doc.getString("id"));
		setName(doc.getString("name"));
		setLatin_name(doc.getString("latin_name"));
		setType(doc.getString("type"));
		setLocation(doc.getString("location"));
		setHabitat(doc.getString("habitat"));
		setGrowth_states(doc.getString("growth_states"));
		setActive_time(doc.getString("active_time"));
		setDiet(doc.getString("diet"));
		setThreatens(doc.getString("threatens"));
		setBenefits(doc.getString("benefits"));
		setSymptoms(doc.getString("symptoms"));
		setPredators(doc.getString("predators"));
		setDeterrents(doc.getString("deterrents"));
		setRemedies(doc.getString("remedies"));
	}

	/**
	 * Create a TestType from a Map
	 * 
	 * @param attributes
	 *            the Map with the attributes
	 */
	public PestType(Map<String, String> attributes) {
		super(null);

		Document doc = new Document();
		doc.append(attributes.get("id"), "NONE");
		doc.append(attributes.get("name"), "NONE");
		doc.append(attributes.get("latin_name"), "NONE");
		doc.append(attributes.get("type"), "NONE");
		doc.append(attributes.get("location"), "NONE");
		doc.append(attributes.get("habitat"), "NONE");
		doc.append(attributes.get("growth_states"), "[]");
		doc.append(attributes.get("active_time"), "[]"); // (seasons when active?)
		doc.append(attributes.get("diet"), "[]"); // (what does it eat or require for growth)
		doc.append(attributes.get("threatens"), "[]"); // (list what it threatens)
		doc.append(attributes.get("benefits"), "[]"); // (List what it helps)
		doc.append(attributes.get("symptoms"), "[]"); // (Symptoms of pest)
		doc.append(attributes.get("predators"), "[]");
		doc.append(attributes.get("deterrents"), "[]");
		doc.append(attributes.get("remedies"), "[]");

		setId(doc.getString("id"));
		setName(doc.getString("name"));
		setLatin_name(doc.getString("latin_name"));
		setType(doc.getString("type"));
		setLocation(doc.getString("location"));
		setHabitat(doc.getString("habitat"));
		setGrowth_states(doc.getString("growth_states"));
		setActive_time(doc.getString("active_time"));
		setDiet(doc.getString("diet"));
		setThreatens(doc.getString("threatens"));
		setBenefits(doc.getString("benefits"));
		setSymptoms(doc.getString("symptoms"));
		setPredators(doc.getString("predators"));
		setDeterrents(doc.getString("deterrents"));
		setRemedies(doc.getString("remedies"));
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLatin_name() {
		return latin_name;
	}

	public String getType() {
		return type;
	}

	public String getLocation() {
		return location;
	}

	public String getHabitat() {
		return habitat;
	}

	public String getGrowth_states() {
		return growth_states;
	}

	public String getActive_time() {
		return active_time;
	}

	public String getDiet() {
		return diet;
	}

	public String getThreatens() {
		return threatens;
	}

	public String getBenefits() {
		return benefits;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public String getPredators() {
		return predators;
	}

	public String getDeterrents() {
		return deterrents;
	}

	public String getRemedies() {
		return remedies;
	}

	private void setId(String id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setLatin_name(String latin_name) {
		this.latin_name = latin_name;
	}

	private void setType(String type) {
		this.type = type;
	}

	private void setLocation(String location) {
		this.location = location;
	}

	private void setHabitat(String habitat) {
		this.habitat = habitat;
	}

	private void setGrowth_states(String growth_states) {
		this.growth_states = growth_states;
	}

	private void setActive_time(String active_time) {
		this.active_time = active_time;
	}

	private void setDiet(String diet) {
		this.diet = diet;
	}

	private void setThreatens(String threatens) {
		this.threatens = threatens;
	}

	private void setBenefits(String benefits) {
		this.benefits = benefits;
	}

	private void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	private void setPredators(String predators) {
		this.predators = predators;
	}

	private void setDeterrents(String deterrents) {
		this.deterrents = deterrents;
	}

	private void setRemedies(String remedies) {
		this.remedies = remedies;
	}

}
