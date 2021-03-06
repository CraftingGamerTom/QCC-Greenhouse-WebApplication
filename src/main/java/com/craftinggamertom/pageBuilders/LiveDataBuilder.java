package com.craftinggamertom.pageBuilders;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.database.SensorSet;
import com.craftinggamertom.entity.Sensor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

/**
 * Copyright (c) 2017 Thomas Rokicki
 * 
 * @author Thomas Rokicki
 *
 */
public class LiveDataBuilder extends PageBuilder {

	private String chosenType;
	private String type = "BLANK";

	public LiveDataBuilder(String organization_url) {
		super(organization_url);
	}

	/**
	 * Handles everything that is required to return all the needed components of
	 * the live data module
	 * 
	 * @return Model containing all the variables
	 */
	public Model buildPage(String chosenType, Model model) {

		this.chosenType = chosenType;
		this.model = model;

		super.buildPage(model); // Adds the standard model attributes

		return addPageAttributes();

	}

	/**
	 * Models all variables to be given to the Front end
	 * 
	 * @return Model to be returned back to the front end
	 */
	private Model addPageAttributes() {
		model.addAttribute("type-options", getTypeOptions());
		model.addAttribute("displayed-sensors", getSensorChart());
		model.addAttribute("shown-type", type); // Must be last because the type is set in the methods called above.

		return model;
	}

	/**
	 * Searches for the types of sensors and displays the types without duplicates
	 * and gets all the sensors with the according type.
	 * 
	 * @return HTML string to be put in a drop down
	 */
	private String getTypeOptions() {
		String message = "";
		ArrayList<Sensor> allSensors = new ArrayList<Sensor>();
		ArrayList<String> allTypes = new ArrayList<String>();

		MongoCollection<Document> collection = null;
		collection = database.getCollection(ConfigurationReaderSingleton.getSensorNameCollection());

		Bson isVisibleFilter = Filters.eq("isVisible", true);
		FindIterable<Document> searchResult = collection.find(isVisibleFilter);

		Iterator<Document> iter = searchResult.iterator();
		while (iter.hasNext()) {
			allSensors.add(new Sensor(iter.next()));
		}

		// Gets all the types into an arraylist
		for (int i = 0; i < allSensors.size(); i++) {

			String type = allSensors.get(i).getType();

			if (isNotDuplicate(allTypes, type)) {
				allTypes.add(type);
			}
		}
		// Adds all types as options on the UI
		for (int i = 0; i < allTypes.size(); i++) {
			message += "\n";
			message += "<option value=\"";
			message += allTypes.get(i);
			message += "\">";
			message += allTypes.get(i);
			message += "</option>";
		}

		return message;
	}

	/**
	 * Checks if the type has already been added to the array since we do not want
	 * duplicate type options for the query on the UI.
	 * 
	 * The currently method is really inefficient for a large number of sensors
	 * 
	 * @param allSensors
	 *            The array of all the sensors in the database
	 * @param type
	 *            the type to check for duplicates
	 * @return true if it is a new type
	 */
	private boolean isNotDuplicate(ArrayList<String> allSensors, String type) {
		for (int i = 0; i < allSensors.size(); i++) {
			if (allSensors.get(i).equals(type)) {
				return false;
			}

		}
		return true;
	}

	/**
	 * Makes a connection to mongo db and get the info of a all sensors to display
	 * 
	 * @return A string of html to represent the options
	 */
	private String getSensorChart() {
		String message = "";
		ArrayList<SensorSet> allSensors = new ArrayList<SensorSet>();

		MongoCollection<Document> liveDataCollection = null;
		MongoCollection<Document> namesCollection = null;

		namesCollection = database.getCollection(ConfigurationReaderSingleton.getSensorNameCollection());
		liveDataCollection = database.getCollection(ConfigurationReaderSingleton.getLiveDataCollection());

		try {

			FindIterable<Document> searchResult;
			FindIterable<Document> valuesResult;
			if (!chosenType.equals("default")) {
				Bson typeFilter = Filters.eq("type", chosenType);

				Bson isVisibleFilter = Filters.eq("isVisible", true);
				searchResult = namesCollection.find(Filters.and(isVisibleFilter, typeFilter));
				valuesResult = liveDataCollection.find(typeFilter);

				// Sets the type for display
				this.type = chosenType;

			} else { // If the sensor was not specified it will get the default and all with the same
						// type

				FindIterable<Document> searchDefault;
				Bson defaultFilter = Filters.eq("isDefault", true);

				searchDefault = namesCollection.find(defaultFilter);
				String type = searchDefault.first().getString("type");

				Bson typeFilter = Filters.eq("type", type);
				searchResult = namesCollection.find(typeFilter);
				valuesResult = liveDataCollection.find(typeFilter);

				// Set the type to the default type for display
				this.type = type;

			}
			Iterator<Document> namesIter = searchResult.iterator();
			while (namesIter.hasNext()) {
				Document namesDoc = namesIter.next();
				Iterator<Document> valuesIter = valuesResult.iterator(); // must be local to make new iterator each
																			// time.

				while (valuesIter.hasNext()) {
					Document valuesDoc = valuesIter.next();
					// If the ID's match -> do work
					if (namesDoc.getString("sensorId").equals(valuesDoc.getString("sensorId"))) {
						SensorSet set = new SensorSet(namesDoc, valuesDoc);
						allSensors.add(set);
					}

				}
			}
			for (int i = 0; i < allSensors.size(); i++) {

				// String sensorID = allSensors.get(i).getSensorId();
				String type = allSensors.get(i).getType();
				String description = allSensors.get(i).getDescription();
				String friendlyName = allSensors.get(i).getFriendlyName();
				Double value = allSensors.get(i).getValue();
				String time = allSensors.get(i).getDate();

				message += "                                <tr>\r\n" + "                                    <td>"
						+ friendlyName + "</td>\r\n" + "                                    <td>" + description
						+ "</td>\r\n" + "                                    <td>" + type + "</td>\r\n"
						+ "                                    <td class=\"center\">" + time + "</td>\r\n"
						+ "                                    <td class=\"center\">" + value + "</td>\r\n"
						+ "                                </tr>";
			}

		} catch (NullPointerException nullE) {
			System.out
					.println("NullPointer(LiveDataBuilder): Adding a dumby sensor with placemarkers and warning user");
			this.type = "no available";

			// To warn user of no sensor data
			String warning = "<div class=\"row wrapper page-heading\">\r\n" + "	<div class=\"col-lg-12\">\r\n"
					+ "		<div class=\"alert alert-warning\">\r\n"
					+ "			<p class=\"alert-link text-center\">Hey!</p>\r\n"
					+ "			<p class=\"text-center\">You don't seem to have any data to show. You should add some.</p>\r\n"
					+ "			<p class=\"text-center\">Perhaps there is no default sensor set. An admin can fix that.</p>\r\n"
					+ "		</div>\r\n" + "	</div>\r\n" + "</div>";

			model.addAttribute("no-sensor-data-found-warning", warning);
			return ""; // Returns nothing since there is no data
		}
		model.addAttribute("no-sensor-data-found-warning", ""); // only called if data was found in database - to
																// make sure the value is not null

		return message;
	}
}