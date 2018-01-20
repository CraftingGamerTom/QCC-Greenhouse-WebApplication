package com.craftinggamertom.entity;

import org.bson.Document;

public class Sensor extends Entity{

	private String sensorID;
	private String type;
	private String date;
	private String friendlyName;
	private String description;
	private boolean isVisible;
	private boolean isDefault;

	/**
	 * SensorInfo object with all blank fields (great for when there is not sensor
	 * data in the database)
	 */
	public Sensor() {
		super(null); //TODO Build document
		this.sensorID = "Blank";
		this.type = "Blank";
		this.date = null;
		this.friendlyName = "Blank";
		this.description = "Blank";
		this.isVisible = true;
	}

	public Sensor(String sensorID, String type, String date, String friendlyName, String description,
			boolean isVisible, boolean isDefault) {
		super(null); // TODO Build document
		this.sensorID = sensorID;
		this.type = type;
		this.date = date;
		this.friendlyName = friendlyName;
		this.description = description;
		this.isVisible = isVisible;
		this.isVisible = isDefault;
	}

	public Sensor(Document doc) {
		super(doc);
		this.sensorID = doc.getString("sensorId");
		this.type = doc.getString("type");
		this.date = doc.getString("date");
		this.friendlyName = doc.getString("friendlyName");
		this.description = doc.getString("description");
		this.isVisible = doc.getBoolean("isVisible");
		this.isDefault = doc.getBoolean("isDefault");
//		this.isDefault = doc.getBoolean("range_min");
//		this.isDefault = doc.getBoolean("range_max");
	}

	public String getSensorId() {
		return sensorID;
	}

	public String getType() {
		return type;
	}

	public String getDate() {
		return date;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public String getDescription() {
		return description;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public boolean isDefault() {
		return isDefault;
	}

	/**
	 * Apparently Broken?
	 */
	@Override
	public String toString() {
		return String.format(
				"SensorInfo[SensorID='%s', Type='%s', Date=%s, FriendlyName='%s', Description='%s', isVisible=%s, isDefault=%s]",
				sensorID, type, date, friendlyName, description, isVisible, isDefault);
	}
}
