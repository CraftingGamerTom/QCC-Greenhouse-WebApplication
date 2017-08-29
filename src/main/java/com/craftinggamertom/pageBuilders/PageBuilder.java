package com.craftinggamertom.pageBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.craftinggamertom.database.ConfigurationReader;
import com.craftinggamertom.database.MongoClientConnection;
import com.mongodb.MongoClient;

import com.mongodb.client.MongoDatabase;

public class PageBuilder {

	protected MongoClient client;
	protected MongoDatabase database;
	protected String currentTime = "";

	
	public PageBuilder() {
		
		// Collects and sets the current Time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		currentTime = dtf.format(now); //2016/11/16 12:08:43

		MongoClient client = MongoClientConnection.getInstance(); // Creates connection once (Singleton Object)
		database = client.getDatabase(ConfigurationReader.databaseName);
	}
	
	

	/**
	 * If a new sensor is added it MUST be added here and on the front-end of the UI.
	 * This method converts the value on the front end to appropriate sensorID.
	 * @param cSensor
	 * @return
	 */
	protected ArrayList<String> convertSensor(String cSensor) {
		ArrayList<String> sensorInfo = new ArrayList<String>();
		if(cSensor.equals("indoor-temperature")) {
			sensorInfo.add("rp1-01");
			sensorInfo.add("indoor");
			sensorInfo.add("temperature");
			
		}
		else if(cSensor.equals("indoor-humidity")) {
			sensorInfo.add("rp1-02");
			sensorInfo.add("indoor");
			sensorInfo.add("humidity");
		}
		else if(cSensor.equals("outdoor-temperature")) {
			sensorInfo.add("a01-02");
			sensorInfo.add("outdoor");
			sensorInfo.add("temperature");
		}
		else if(cSensor.equals("outdoor-humidity")) {
			sensorInfo.add("a01-02");
			sensorInfo.add("outdoor");
			sensorInfo.add("humidity");
		}
		else {
			sensorInfo.add("error");
			System.out.println("**** ERROR WHEN CONVERTING SENSOR NAME ****");
		}
		return sensorInfo;
	}
	
}
