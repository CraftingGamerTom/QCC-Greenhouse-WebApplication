package com.craftinggamertom.database;

import org.bson.Document;

public class SensorInfo {
	
	private String sensorID;
	private String type;
	private String date;
	private String friendlyName;
	private String description;
	private boolean isVisible;
	private boolean isDefault;
	
	public SensorInfo() {
		this.sensorID = "Blank";
		this.type = "Blank";
		this.date = null;
		this.friendlyName = "Blank";
		this.description = "Blank";
		this.isVisible = true;
	}
	
	public SensorInfo(String sensorID, String type, String date, String friendlyName, String description, boolean isVisible, boolean isDefault) {
		this.sensorID = sensorID;
		this.type = type;
		this.date = date;
		this.friendlyName = friendlyName;
		this.description = description;
		this.isVisible = isVisible;
		this.isVisible = isDefault;
	}
	
	public SensorInfo(Document searchResult) {
		this.sensorID = searchResult.getString("sensorId");
		this.type = searchResult.getString("type");
		this.date = searchResult.getString("date");
		this.friendlyName = searchResult.getString("friendlyName");
		this.description = searchResult.getString("description");
		this.isVisible = searchResult.getBoolean("isVisible");
		this.isDefault = searchResult.getBoolean("isDefault");
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
		return String.format("SensorInfo[id=%s, SensorID='%s', Type='%s', Date=%s, FriendlyName='%s', Description='%s', isVisible=%s, isDefault=%s]", sensorID, type, date, friendlyName, description, isVisible, isDefault);
	}
}
