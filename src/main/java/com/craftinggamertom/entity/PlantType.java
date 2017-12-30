/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.entity;

import java.util.Map;

import org.bson.Document;

/**
 * PlantType Object to define basic needs for a specific plant
 * 
 * @author Thomas Rokicki
 *
 */
public class PlantType extends Entity {

	private String id; // id for the plant type
	private String name; // A friendly name for lookup
	private String family; // what plant family the plant belongs to
	private String scientific_name; // the scientific name of the plant
	private String origin; // Where it came from: Supermarket, johnny select, etc
	private String external_link; // (JohnnySelectSeed Link)
	private String harvest_method; // The way to harvest the plant
	private String trimming_method; // The way to trim the plant
	private String expected_height; // The average range height of the plant ***ARRAY?***
	private String companion_bugs; // list of companion bugs (by id)
	private String aversive_bugs; // list of pests (by id)
	private String companion_plants; // list of companion plants (by id)
	private String aversive_plants; // list of aversive plants (by id)
	private String preferred_soil; // list of preferred soils (by id)
	private String leaf_color; // healthy leaf color
	private String picture; // link to the picture of the plant
	private String deficiencies; // list of known deficiencies
	private String nutrients; // Preferred feeding and nutrients
	private String repels; // (list of animals, bugs, insects)
	private String attracts; // (list of animals, bugs, insects)

	private String space_needed_min; // ISODuration
	private String space_needed_max; // ISODuration
	private String time_to_germinate_min; // ISODuration
	private String time_to_germinate_max; // ISODuration
	private String time_to_mature_min; // ISODuration
	private String time_to_mature_max; // ISODuration
	private String tempature_min; // ISODuration
	private String tempature_max; // ISODuration
	private String tolerances_min; // ISODuration tolerances [heat, cold, frost]
	private String tolerances_max; // ISODuration tolerances [heat, cold, frost]
	private String water_required_min; // ISODuration how often and how much
	private String water_required_max; // ISODuration how often and how much
	private String light_required_min; // ISODuration (how much (partial, full, time length?), how long)
	private String light_required_max; // ISODuration (how much (partial, full, time length?), how long)

	/**
	 * Creates a PlantType Object from a Document
	 * 
	 * @param doc
	 */
	public PlantType(Document doc) {
		super(doc);

		setId(doc.getString("id"));
		setName(doc.getString("name"));
		setFamily(doc.getString("family"));
		setScientific_name(doc.getString("scientific_name"));
		setOrigin(doc.getString("origin"));
		setExternal_link(doc.getString("external_link"));
		setHarvest_method(doc.getString("harvest_method"));
		setTrimming_method(doc.getString("trimming_method"));
		setExpected_height(doc.getString("expected_height"));
		setCompanion_bugs(doc.getString("companion_bugs"));
		setAversive_bugs(doc.getString("aversive_bugs"));
		setCompanion_plants(doc.getString("companion_plants"));
		setAversive_plants(doc.getString("aversive_plants"));
		setPreferred_soil(doc.getString("preferred_soil"));
		setLeaf_color(doc.getString("leaf_color"));
		setPicture(doc.getString("picture"));
		setDeficiencies(doc.getString("deficiencies"));
		setNutrients(doc.getString("nutrients"));
		setRepels(doc.getString("repels"));
		setAttracts(doc.getString("attracts"));

		setSpace_needed_min(doc.getString("space_needed_min"));
		setSpace_needed_max(doc.getString("space_needed_max"));
		setTime_to_germinate_min(doc.getString("time_to_germinate_min"));
		setTime_to_germinate_max(doc.getString("time_to_germinate_max"));
		setTime_to_mature_min(doc.getString("time_to_mature_min"));
		setTime_to_mature_max(doc.getString("time_to_mature_max"));
		setTempature_min(doc.getString("tempature_min"));
		setTempature_max(doc.getString("tempature_max"));
		setTolerances_min(doc.getString("tolerances_min"));
		setTolerances_max(doc.getString("tolerances_max"));
		setWater_required_min(doc.getString("water_required_min"));
		setWater_required_max(doc.getString("water_required_max"));
		setLight_required_min(doc.getString("light_required_min"));
		setLight_required_max(doc.getString("light_required_max"));

	}

	public PlantType(Map<String, String> attributes) {
		super(null);

		Document doc = new Document();

		doc.append(attributes.get("id"), "NONE");
		doc.append(attributes.get("name"), "NONE");
		doc.append(attributes.get("family"), "NONE");
		doc.append(attributes.get("scientific_name"), "NONE");
		doc.append(attributes.get("origin"), "NONE");
		doc.append(attributes.get("external_link"), "NONE");
		doc.append(attributes.get("harvest_method"), "NONE");
		doc.append(attributes.get("trimming_method"), "NONE");
		doc.append(attributes.get("expected_height_min"), "0inches");
		doc.append(attributes.get("expected_height_max"), "0inches");
		doc.append(attributes.get("companion_bugs"), "[]");
		doc.append(attributes.get("aversive_bugs"), "[]");
		doc.append(attributes.get("companion_plants"), "[]");
		doc.append(attributes.get("aversive_plants"), "[]");
		doc.append(attributes.get("preferred_soil"), "NONE");
		doc.append(attributes.get("leaf_color"), "NONE");
		doc.append(attributes.get("picture"), "NONE");
		doc.append(attributes.get("deficiencies"), "[]");
		doc.append(attributes.get("nutrients"), "[]");
		doc.append(attributes.get("repels"), "[]");
		doc.append(attributes.get("attracts"), "[]");

		doc.append(attributes.get("space_needed_min"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("space_needed_max"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("time_to_germinate_min"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("time_to_germinate_max"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("time_to_mature_min"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("time_to_mature_max"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("tempature_min"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("tempature_max"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("tolerances_min"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("tolerances_max"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("water_required_min"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("water_required_max"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("light_required_min"), "P1Y1M1DT1H1M1S");
		doc.append(attributes.get("light_required_max"), "P1Y1M1DT1H1M1S");

		document = doc; // Overwrite the document

		setId(doc.getString("id"));
		setName(doc.getString("name"));
		setFamily(doc.getString("family"));
		setScientific_name(doc.getString("scientific_name"));
		setOrigin(doc.getString("origin"));
		setExternal_link(doc.getString("external_link"));
		setHarvest_method(doc.getString("harvest_method"));
		setTrimming_method(doc.getString("trimming_method"));
		setExpected_height(doc.getString("expected_height"));
		setCompanion_bugs(doc.getString("companion_bugs"));
		setAversive_bugs(doc.getString("aversive_bugs"));
		setCompanion_plants(doc.getString("companion_plants"));
		setAversive_plants(doc.getString("aversive_plants"));
		setPreferred_soil(doc.getString("preferred_soil"));
		setLeaf_color(doc.getString("leaf_color"));
		setPicture(doc.getString("picture"));
		setDeficiencies(doc.getString("deficiencies"));
		setNutrients(doc.getString("nutrients"));
		setRepels(doc.getString("repels"));
		setAttracts(doc.getString("attracts"));

		setSpace_needed_min(doc.getString("space_needed_min"));
		setSpace_needed_max(doc.getString("space_needed_max"));
		setTime_to_germinate_min(doc.getString("time_to_germinate_min"));
		setTime_to_germinate_max(doc.getString("time_to_germinate_max"));
		setTime_to_mature_min(doc.getString("time_to_mature_min"));
		setTime_to_mature_max(doc.getString("time_to_mature_max"));
		setTempature_min(doc.getString("tempature_min"));
		setTempature_max(doc.getString("tempature_max"));
		setTolerances_min(doc.getString("tolerances_min"));
		setTolerances_max(doc.getString("tolerances_max"));
		setWater_required_min(doc.getString("water_required_min"));
		setWater_required_max(doc.getString("water_required_max"));
		setLight_required_min(doc.getString("light_required_min"));
		setLight_required_max(doc.getString("light_required_max"));

	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFamily() {
		return family;
	}

	public String getScientific_name() {
		return scientific_name;
	}

	public String getOrigin() {
		return origin;
	}

	public String getExternal_link() {
		return external_link;
	}

	public String getHarvest_method() {
		return harvest_method;
	}

	public String getTrimming_method() {
		return trimming_method;
	}

	public String getExpected_height() {
		return expected_height;
	}

	public String getCompanion_bugs() {
		return companion_bugs;
	}

	public String getAversive_bugs() {
		return aversive_bugs;
	}

	public String getCompanion_plants() {
		return companion_plants;
	}

	public String getAversive_plants() {
		return aversive_plants;
	}

	public String getPreferred_soil() {
		return preferred_soil;
	}

	public String getLeaf_color() {
		return leaf_color;
	}

	public String getPicture() {
		return picture;
	}

	public String getDeficiencies() {
		return deficiencies;
	}

	public String getNutrients() {
		return nutrients;
	}

	public String getRepels() {
		return repels;
	}

	public String getAttracts() {
		return attracts;
	}

	public String getSpace_needed_min() {
		return space_needed_min;
	}

	public String getSpace_needed_max() {
		return space_needed_max;
	}

	public String getTime_to_germinate_min() {
		return time_to_germinate_min;
	}

	public String getTime_to_germinate_max() {
		return time_to_germinate_max;
	}

	public String getTime_to_mature_min() {
		return time_to_mature_min;
	}

	public String getTime_to_mature_max() {
		return time_to_mature_max;
	}

	public String getTempature_min() {
		return tempature_min;
	}

	public String getTempature_max() {
		return tempature_max;
	}

	public String getTolerances_min() {
		return tolerances_min;
	}

	public String getTolerances_max() {
		return tolerances_max;
	}

	public String getWater_required_min() {
		return water_required_min;
	}

	public String getWater_required_max() {
		return water_required_max;
	}

	public String getLight_required_min() {
		return light_required_min;
	}

	public String getLight_required_max() {
		return light_required_max;
	}

	private void setId(String id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setFamily(String family) {
		this.family = family;
	}

	private void setScientific_name(String scientific_name) {
		this.scientific_name = scientific_name;
	}

	private void setOrigin(String origin) {
		this.origin = origin;
	}

	private void setExternal_link(String external_link) {
		this.external_link = external_link;
	}

	private void setHarvest_method(String harvest_method) {
		this.harvest_method = harvest_method;
	}

	private void setTrimming_method(String trimming_method) {
		this.trimming_method = trimming_method;
	}

	private void setExpected_height(String expected_height) {
		this.expected_height = expected_height;
	}

	private void setCompanion_bugs(String companion_bugs) {
		this.companion_bugs = companion_bugs;
	}

	private void setAversive_bugs(String aversive_bugs) {
		this.aversive_bugs = aversive_bugs;
	}

	private void setCompanion_plants(String companion_plants) {
		this.companion_plants = companion_plants;
	}

	private void setAversive_plants(String aversive_plants) {
		this.aversive_plants = aversive_plants;
	}

	private void setPreferred_soil(String preferred_soil) {
		this.preferred_soil = preferred_soil;
	}

	private void setLeaf_color(String leaf_color) {
		this.leaf_color = leaf_color;
	}

	private void setPicture(String picture) {
		this.picture = picture;
	}

	private void setDeficiencies(String deficiencies) {
		this.deficiencies = deficiencies;
	}

	private void setNutrients(String nutrients) {
		this.nutrients = nutrients;
	}

	private void setRepels(String repels) {
		this.repels = repels;
	}

	private void setAttracts(String attracts) {
		this.attracts = attracts;
	}

	private void setSpace_needed_min(String space_needed_min) {
		this.space_needed_min = space_needed_min;
	}

	private void setSpace_needed_max(String space_needed_max) {
		this.space_needed_max = space_needed_max;
	}

	private void setTime_to_germinate_min(String time_to_germinate_min) {
		this.time_to_germinate_min = time_to_germinate_min;
	}

	private void setTime_to_germinate_max(String time_to_germinate_max) {
		this.time_to_germinate_max = time_to_germinate_max;
	}

	private void setTime_to_mature_min(String time_to_mature_min) {
		this.time_to_mature_min = time_to_mature_min;
	}

	private void setTime_to_mature_max(String time_to_mature_max) {
		this.time_to_mature_max = time_to_mature_max;
	}

	private void setTempature_min(String tempature_min) {
		this.tempature_min = tempature_min;
	}

	private void setTempature_max(String tempature_max) {
		this.tempature_max = tempature_max;
	}

	private void setTolerances_min(String tolerances_min) {
		this.tolerances_min = tolerances_min;
	}

	private void setTolerances_max(String tolerances_max) {
		this.tolerances_max = tolerances_max;
	}

	private void setWater_required_min(String water_required_min) {
		this.water_required_min = water_required_min;
	}

	private void setWater_required_max(String water_required_max) {
		this.water_required_max = water_required_max;
	}

	private void setLight_required_min(String light_required_min) {
		this.light_required_min = light_required_min;
	}

	private void setLight_required_max(String light_required_max) {
		this.light_required_max = light_required_max;
	}

}