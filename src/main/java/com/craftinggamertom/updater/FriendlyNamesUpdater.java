package com.craftinggamertom.updater;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.craftinggamertom.database.ConfigurationReader;
import com.craftinggamertom.database.MongoClientConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

/**
 * Updates the data in the database that maintains the name and some information
 * regarding the sensor
 * 
 * @author Thomas Rokicki
 *
 */
public class FriendlyNamesUpdater {

	protected MongoClient client;
	protected MongoDatabase database;

	/**
	 * Default Constructor
	 */
	public FriendlyNamesUpdater() {
		client = MongoClientConnection.getInstance(); // Creates connection once (Singleton Object)
		database = client.getDatabase(ConfigurationReader.databaseName);
	}

	/**
	 * Updates all the fields for a sensor based on user input
	 * 
	 * @param sensorID
	 * @param sensorDescription
	 * @param friendlyName
	 * @param isVisible
	 * @param isDefaultSensor
	 * @param isDefaultSensor2
	 */
	public void updateWith(String sensorID, String sensorDescription, String friendlyName, String isVisible,
			String isDefaultSensor) {
		// Filter Types
		// Sensor ID
		Bson sensorFilter = Filters.eq("sensorId", sensorID);

		Bson updateDescription = Updates.set("description", sensorDescription);
		Bson updateFriendlyName = Updates.set("friendlyName", friendlyName);
		Bson updateIsVisible = Updates.set("isVisible", makeBoolean(isVisible));
		Bson updateIsDefault = Updates.set("isDefault", makeBoolean(isDefaultSensor));

		Bson oldDefault = Filters.eq("isDefault", true);
		Bson updateOld = Updates.set("isDefault", false);

		MongoCollection<Document> collection = null;

		try {
			// Iterates through the list of persistent collections
			collection = database.getCollection(ConfigurationReader.sensorNamesCollection);

			// If statement will remove the old default if a new one is set.
			if (makeBoolean(isDefaultSensor) == true) {

				FindIterable<Document> results = collection.find(oldDefault); // Should only ever be one but MongoDB
																				// doesnt make it easy.
				if (results.first() != null) {
					if (!(results.first().get("sensorId").equals(sensorID))) {
						collection.findOneAndUpdate(oldDefault, updateOld);
					}
				}
			}

			collection.findOneAndUpdate(sensorFilter,
					Filters.and(updateDescription, updateFriendlyName, updateIsVisible, updateIsDefault));

		} catch (Exception e) {
			System.out.println("Error while updating friendly sensor data: ");
			e.printStackTrace();
		}

	}

	private boolean makeBoolean(String isTrue) {
		if (isTrue.equals("1")) {
			return true;
		}
		return false;
	}

}
