package com.craftinggamertom.pageBuilders;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.database.SensorInfo;
import com.craftinggamertom.database.SensorSet;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

/**
 * Copyright (c) 2017 Thomas Rokicki
 * 
 * @author Thomas Rokicki
 *
 */
public class RawDataBuilder extends PageBuilder {

	private String sensorId;
	private String sensorName = "BLANK";
	private String cStart;
	private String cEnd;

	private String displayedStartDate;
	private String displayedEndDate;
	private int numItems;

	public RawDataBuilder() {
		super();
	}

	/**
	 * Handles everything that is required to return all the needed components of
	 * the live data module
	 * 
	 * @return Model containing all the variables
	 */
	public Model buildPage(String sensorId, String cStart, String cEnd, Model model) {

		this.sensorId = sensorId;
		this.model = model;
		this.cStart = parseDate(cStart);
		this.cStart += "T00:00:00-04:00"; // add time to start of day (needed for DB query)
		this.cEnd = parseDate(cEnd);
		this.cEnd += "T23:59:59-04:00"; // add time to end of day (needed for DB query)

		this.displayedStartDate = parseDisplayedDate(cStart);
		this.displayedEndDate = parseDisplayedDate(cEnd);

		super.buildPage(model); // Adds the standard model attributes

		return addPageAttributes();

	}

	/**
	 * Models all variables to be given to the Front end
	 * 
	 * @return Model to be returned back to the front end
	 */
	private Model addPageAttributes() {
		model.addAttribute("sensor-options", getSensorOptions());
		model.addAttribute("raw-data", getSensorChart());
		model.addAttribute("friendly-name", sensorName);
		model.addAttribute("shown-sensor", sensorId); // Must be last because the type is set in the methods called
														// above.
		model.addAttribute("start-date", displayedStartDate);
		model.addAttribute("end-date", displayedEndDate);
		model.addAttribute("num-items", numItems);

		return model;
	}

	/**
	 * Parses the date that comes in by default
	 * 
	 * @param string
	 *            containing the date formatted like "06%2F02%2F2017" or "default"
	 */
	private String parseDate(String date) {
		String parsedDate = "";

		if (date.equals("default")) {
			// Use current date
			ZonedDateTime now = ZonedDateTime.now();
			parsedDate = now.toString().substring(0, 10); // Dont use full ZonedDateTime because it can't be parsed

			return parsedDate;
		} else {
			parsedDate = date.replaceAll("/", "-");
			String month = parsedDate.substring(0, 2);
			String day = parsedDate.substring(3, 5);
			String year = parsedDate.substring(6, 10);

			parsedDate = year + "-" + month + "-" + day;

			return parsedDate;
		}

	}

	/**
	 * Parses the date for being displayed in the range chooser
	 * 
	 * @param string
	 *            to be used on the front end
	 */
	private String parseDisplayedDate(String date) {
		String parsedDate = date;

		if (date.equals("default")) {
			parsedDate = ZonedDateTime.now().toString().substring(0, 10).replaceAll("-", "/"); // current date
			String year = parsedDate.substring(0, 4);
			String month = parsedDate.substring(5, 7);
			String day = parsedDate.substring(8, 10);
			parsedDate = month + "/" + day + "/" + year;
			return parsedDate;
		}
		return parsedDate;

	}

	/**
	 * Makes a connection to mongo db and get the name and friendly name of a all
	 * visible sensors to display as an option.
	 * 
	 * @return A string of html to represent the options
	 */
	private String getSensorOptions() {
		try {
			String message = "";
			ArrayList<SensorInfo> allVisibleSensors = new ArrayList<SensorInfo>();

			Bson isVisibleFilter = Filters.eq("isVisible", true);
			Bson isDefaultFilter = Filters.eq("isDefault", true);
			Bson isNotDefaultFilter = Filters.eq("isDefault", false);

			MongoCollection<Document> collection = null;
			collection = database.getCollection(ConfigurationReaderSingleton.getSensorNamesCollection());

			FindIterable<Document> searchResult = collection.find(Filters.and(isVisibleFilter, isNotDefaultFilter));
			FindIterable<Document> defaultResult = collection.find(isDefaultFilter);

			Document defaultSensor = defaultResult.first();
			allVisibleSensors.add(new SensorInfo(defaultSensor));

			Iterator<Document> resultIter = searchResult.iterator();
			while (resultIter.hasNext()) {
				allVisibleSensors.add(new SensorInfo(resultIter.next()));
			}
			for (int i = 0; i < allVisibleSensors.size(); i++) {
				message += "\n";
				message += "<option value=\"";
				message += allVisibleSensors.get(i).getSensorId();
				message += "\">";
				message += allVisibleSensors.get(i).getFriendlyName();
				message += "</option>";
			}

			return message;
		} catch (NullPointerException nullE) {
			System.out.println("NullPointer(RawDataBuilder): adding sumby code for sensorOptions");
			return "";
		}
	}

	/**
	 * Makes a connection to mongo db and get the info of a all sensors to display
	 * 
	 * @return A string of html to represent the options
	 */
	private String getSensorChart() {
		String message = "";
		ArrayList<SensorSet> allSensors = new ArrayList<SensorSet>();

		MongoCollection<Document> rawDataCollection = null;
		MongoCollection<Document> namesCollection = null;

		namesCollection = database.getCollection(ConfigurationReaderSingleton.getSensorNamesCollection());
		rawDataCollection = database.getCollection(ConfigurationReaderSingleton.getRawDataCollection());

		// Date Fitlers
		Bson startFilter = Filters.gte("date", cStart);
		Bson endFilter = Filters.lte("date", cEnd);
		Bson dateFilter = Filters.and(startFilter, endFilter);

		FindIterable<Document> searchResult;
		FindIterable<Document> valuesResult;
		if (!sensorId.equals("default")) {
			Bson sensorFilter = Filters.eq("sensorId", sensorId);
			searchResult = namesCollection.find(sensorFilter);
			valuesResult = rawDataCollection.find(Filters.and(sensorFilter, dateFilter));

		} else { // If the sensor was not specified it will get the default and all with the same
					// type
			try {

				Bson defaultFilter = Filters.eq("isDefault", true);

				Document defaultResult = namesCollection.find(defaultFilter).first();
				this.sensorId = defaultResult.getString("sensorId");

				Bson sensorFilter = Filters.eq("sensorId", sensorId);

				searchResult = namesCollection.find(sensorFilter);
				valuesResult = rawDataCollection.find(Filters.and(sensorFilter, dateFilter));
			} catch (NullPointerException nullE) {
				System.out.println("NullPointer(RawDataBuilder): warning user to add sensor data");

				// To warn user of no sensor data
				String warning = "<div class=\"row wrapper page-heading\">\r\n" + "	<div class=\"col-lg-12\">\r\n"
						+ "		<div class=\"alert alert-warning\">\r\n"
						+ "			<p class=\"alert-link text-center\">Hey!</p>\r\n"
						+ "			<p class=\"text-center\">You don't seem to have any data to show. You should add some.</p>\r\n"
						+ "			<p class=\"text-center\">Perhaps there is no default sensor set. An admin can fix that.</p>\r\n"
						+ "		</div>\r\n" + "	</div>\r\n" + "</div>";
				model.addAttribute("no-sensor-data-found-warning", warning);
				return "";
			}

			model.addAttribute("no-sensor-data-found-warning", ""); // only called if data was found in database - to
																	// make sure the value is not null

		}

		// Set the friendly name for display
		Document sensorInfoDoc = searchResult.first(); // Gets the document
		this.sensorName = sensorInfoDoc.getString("friendlyName"); // Gets the friendly name

		Iterator<Document> valuesIter = valuesResult.iterator();
		while (valuesIter.hasNext()) {
			Document valuesDoc = valuesIter.next();
			Iterator<Document> namesIter = searchResult.iterator(); // must be local to make new iterator each time.

			while (namesIter.hasNext()) {
				Document namesDoc = namesIter.next();
				// If the ID's match -> do work
				if (namesDoc.getString("sensorId").equals(valuesDoc.getString("sensorId"))) {
					SensorSet result = new SensorSet(namesDoc, valuesDoc);
					allSensors.add(result);
					break;
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
		this.numItems = allSensors.size();
		return message;

	}

}