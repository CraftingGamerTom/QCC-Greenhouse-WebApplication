package com.craftinggamertom.database;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class DataSet {

	@Id
	private String id;
	
	private String sensorID;
	private String type;
	private Date date;
	private double value;
	
	public DataSet() {}
	
	public DataSet(String sensorID, String type, Date date, double value) {
		this.sensorID = sensorID;
		this.type = type;
		this.date = date;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.format("DataSet[id=%s, SensorID='%s', Type='%s', Date=%s, Value=%s]", id, sensorID, type, date, value);
	}
}
