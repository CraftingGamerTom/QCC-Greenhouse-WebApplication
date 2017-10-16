package com.craftinggamertom.database;

import org.bson.Document;

/**
 * A SensorSet object can be thought of a DataSet object and a SensorInfo object
 * merging. Here we will have all the essential data values for displaying data
 * (ie. live data)
 * 
 * @author Thomas Rokicki
 *
 */
public class SensorSet {

	private String sensorID;
	private String type;
	private String date;
	private String friendlyName;
	private String description;
	private boolean isVisible;
	private boolean isDefault;
	private double value;

	public SensorSet() {
		this.sensorID = "Blank";
		this.type = "Blank";
		this.date = null;
		this.friendlyName = "Blank";
		this.description = "Blank";
		this.isVisible = true;
		this.value = -500; // Hopefully the real value is never this
	}

	public SensorSet(String sensorID, String type, String date, String friendlyName, String description,
			boolean isVisible, boolean isDefault, double value) {
		this.sensorID = sensorID;
		this.type = type;
		this.date = date; // Should be time and date of the update not the day the sensor was added.
		this.friendlyName = friendlyName;
		this.description = description;
		this.isVisible = isVisible;
		this.isVisible = isDefault;
		this.value = value;
	}

	/**
	 * Creates a SensorSet that contains all the information needed to display live
	 * or raw data charts. It is required to have a matching sensorId in each
	 * document from the different collections for this to work correctly
	 * 
	 * @param sensorInfoData
	 * @param dataSetData
	 */
	public SensorSet(Document sensorInfoData, Document dataSetData) {
		// Sensor Info
		this.sensorID = sensorInfoData.getString("sensorId");
		this.type = sensorInfoData.getString("type");
		this.friendlyName = sensorInfoData.getString("friendlyName");
		this.description = sensorInfoData.getString("description");
		this.isVisible = sensorInfoData.getBoolean("isVisible");
		this.isDefault = sensorInfoData.getBoolean("isDefault");

		// DataSet
		this.value = dataSetData.getDouble("value");
		this.date = dataSetData.getString("date");

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

	public double getValue() {
		return value;
	}
}
