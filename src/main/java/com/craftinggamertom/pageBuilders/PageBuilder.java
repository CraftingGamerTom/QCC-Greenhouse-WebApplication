package com.craftinggamertom.pageBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.craftinggamertom.database.ConfigurationReader;
import com.craftinggamertom.database.MongoClientConnection;
import com.craftinggamertom.database.SensorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class PageBuilder {

	protected MongoClient client;
	protected MongoDatabase database;
	protected String currentTime = "";

	public PageBuilder() {

		// Collects and sets the current Time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		currentTime = dtf.format(now); // 2016/11/16 12:08:43

		client = MongoClientConnection.getInstance(); // Creates connection once (Singleton Object)
		database = client.getDatabase(ConfigurationReader.databaseName);
	}

	/**
	 * If a new sensor is added it MUST be added here and on the front-end of the
	 * UI. This method converts the value on the front end to appropriate sensorID.
	 * 
	 * @param cSensor
	 * @return
	 */
	protected SensorInfo convertSensor(String cSensor) {

		Bson sensorFilter = Filters.eq("isDefault", true);

		// if not default
		if (!cSensor.equals("default")) {
			sensorFilter = Filters.eq("sensorId", cSensor);
		}

		MongoCollection<Document> collection = null;
		collection = database.getCollection(ConfigurationReader.sensorNamesCollection);

		Document searchResult = collection.find(sensorFilter).first();

		SensorInfo sensor = new SensorInfo(searchResult);
		return sensor;
	}
}
