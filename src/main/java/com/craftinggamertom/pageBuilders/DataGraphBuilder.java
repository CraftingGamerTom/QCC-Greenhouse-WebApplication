package com.craftinggamertom.pageBuilders;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.entity.Sensor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

/**
 * This class can be tough to look at with the large blocks of code. Here is a
 * few tips to understanding the contents.
 * 
 * To create the graph we want to view we must first format the start time
 * correctly since our UI does not do that for us currently. [THIS CAN BE FIXED
 * BY MAKING THE DATE CHOOSER CHANGE BASED ON TIMING OPTION] then we must take
 * the start time and calculate the end time. (setActualEndTime method)
 * 
 * Then we connect to a database based on the timing preference since our data
 * is maintained according the the timing options
 * 
 * Then we must create the xAxis based on the timing
 * 
 * Finally the contents are modeled and returned.
 * 
 * @author Thomas Rokicki
 *
 */
public class DataGraphBuilder extends PageBuilder {

	private ArrayList<Double> highValue = new ArrayList<Double>();
	private ArrayList<Double> lowValue = new ArrayList<Double>();
	private ArrayList<String> xAxis = new ArrayList<String>();
	// The Y-Axis not included because it's handled by the UI on the client side.
	private String friendlyName = "";
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private String timing;
	private String sensorID;

	private String cTiming;

	public DataGraphBuilder(String organization_url) {
		super(organization_url);

	}

	/**
	 * Handles everything that is required to return all the needed components of
	 * the view data graph
	 * 
	 * @param cSensor
	 * @param cTiming
	 * @param date
	 * @param model
	 * @return
	 */
	public Model buildPage(String cSensor, String cTiming, String date, Model model) {

		super.buildPage(model); // Adds the standard model attributes

		Sensor sensor = convertSensor(cSensor); // Gathers info on a sensor based on Id

		// Due to the use of a ZonedDateTime object we must convert this manually before
		// parsing
		date = date + "T00:00:00-04:00";
		this.startDate = ZonedDateTime.parse(date);
		this.cTiming = cTiming;
		this.sensorID = sensor.getSensorId();
		this.friendlyName = sensor.getFriendlyName();

		setStartDate(); // MUST BE FIRST - Critical errors otherwise
		setEndDate(); // MUST BE AFTER START DATE - Critical errors otherwise
		addDataGraphData();
		setXAxis();

		// setTestGraph();

		return addPageAttributes();

	}

	/**
	 * Based on the timing the end date is set then the appropriate data is
	 * collected and put into the ArrayList containing all the points on the graph
	 */
	private void addDataGraphData() {
		// System.out.println("Timing: " + cTiming);
		// System.out.println("Start: " + startDate);
		// System.out.println("End: " + endDate);
		// Filter Types
		// Sensor ID
		Bson sensorFilter = Filters.eq("sensorId", sensorID);
		// After Date
		Bson afterFilter = Filters.gte("date", startDate.toString());
		// Before Date
		Bson beforeFilter = Filters.lte("date", endDate.toString());

		MongoCollection<Document> collection = null;
		FindIterable<Document> searchResult = null;

		try {
			// Iterates through the list of persistent collections
			collection = database.getCollection(getCollectionName());

			searchResult = collection.find(Filters.and(sensorFilter, beforeFilter, afterFilter));

			Iterator<Document> iter = searchResult.iterator();

			// Prevents NoSuchElementException
			if (searchResult.first() == null) {
				return;
			}

			ZonedDateTime workDate = startDate; // Date to be iterated through to set null values
			Document doc = null; // Initialize
			ZonedDateTime docDate = null; // initialize
			boolean readyToIterate = true;

			do {
				boolean foundValue = true; // Set to false if the value should be set to null

				if (readyToIterate) {
					doc = (Document) iter.next();
					docDate = ZonedDateTime.parse(doc.get("date").toString());
				}

				// For Hourly Data
				if (cTiming.equals("h")) {

					if (workDate.getHour() == docDate.getHour()) {
						lowValue.add((Double) doc.get("lowValue"));
						highValue.add((Double) doc.get("highValue"));

						readyToIterate = true;
						// System.out.println("Hour: " + docDate.getHour());

					} else {
						foundValue = false;
					}
					workDate = workDate.plusHours(1);
				} else if (cTiming.equals("d")) {
					if (workDate.getDayOfYear() == docDate.getDayOfYear()) {
						lowValue.add((Double) doc.get("lowValue"));
						highValue.add((Double) doc.get("highValue"));

						readyToIterate = true;
						// System.out.println("Day: " + docDate.getDayOfYear());

					} else {
						foundValue = false;
					}
					workDate = workDate.plusDays(1);
				} else if (cTiming.equals("w")) {
					// System.out.println("HIT");
					ZonedDateTime lastWorkDate = workDate.plusWeeks(1);
					if (workDate.getDayOfYear() <= docDate.getDayOfYear()
							&& lastWorkDate.getDayOfYear() >= docDate.getDayOfYear()) {
						lowValue.add((Double) doc.get("lowValue"));
						highValue.add((Double) doc.get("highValue"));

						readyToIterate = true;
						// System.out.println("Day (weekly): " + docDate.getDayOfYear());

					} else {
						foundValue = false;
						// System.out.println("(weekly): no value");
					}
					workDate = workDate.plusWeeks(1);
				} else if (cTiming.equals("m")) {
					if (workDate.getMonthValue() == docDate.getMonthValue()) {
						lowValue.add((Double) doc.get("lowValue"));
						highValue.add((Double) doc.get("highValue"));

						readyToIterate = true;
						// System.out.println("Month: " + docDate.getMonthValue());

					} else {
						foundValue = false;
					}
					workDate = workDate.plusMonths(1);
				} else if (cTiming.equals("y")) {
					if (workDate.getYear() == docDate.getYear()) {
						lowValue.add((Double) doc.get("lowValue"));
						highValue.add((Double) doc.get("highValue"));

						readyToIterate = true;
						// System.out.println("Year: " + docDate.getYear());

					} else {
						foundValue = false;
					}
					workDate = workDate.plusYears(1);
				} else {
					System.out.println("(DataGraphBuilder) Error getting query type (daily, monthly, etc)");
					System.out.println("(DataGraphBuilder) Giving up!");
					break;
				}

				if (!foundValue) {// There is missing data
					lowValue.add(null);
					highValue.add(null);

					readyToIterate = false;
				}
				// System.out.println("Count Day: " + workDate.getDayOfYear());
				// System.out.println("Count Hour: " + workDate.getHour());
				// System.out.println(lowValue.toString());
			} while (iter.hasNext() || !readyToIterate);

		} catch (

		Exception e) {
			System.out.println("Error while gathering data to display: ");
			e.printStackTrace();
		}
	}

	/**
	 * Adds the attributes for the page to the model Essentially - passes the data
	 * back to the front end
	 * 
	 * @param model
	 * @return the model with the attributes
	 */
	private Model addPageAttributes() {
		model.addAttribute("x-axis", xAxis);
		// UI
		/*
		 * c-sensor decides the default value shown on the graph page
		 * 
		 * javascript on client side will set value based on this
		 */
		model.addAttribute("c-sensor", "\"" + sensorID + "\""); // Must use these quote insertions for the UI to
																// understand
		model.addAttribute("friendly-name", friendlyName);
		model.addAttribute("start-date", startDate);
		model.addAttribute("end-date", endDate);
		model.addAttribute("sensor-id", sensorID);
		model.addAttribute("current-time", currentTime);
		model.addAttribute("timing", timing);
		// data
		model.addAttribute("high-value", highValue.toString());
		model.addAttribute("low-value", lowValue.toString());
		model.addAttribute("sensor-options", getSensorOptions());

		return model;
	}

	/**
	 * Makes a connection to mongo db and get teh name and friendly name of a all
	 * visible sensors to display as an option.
	 * 
	 * @return A string of html to represent the options
	 */
	private String getSensorOptions() {
		String message = "";
		ArrayList<Sensor> allVisibleSensors = new ArrayList<Sensor>();

		Bson isVisibleFilter = Filters.eq("isVisible", true);

		MongoCollection<Document> collection = null;
		collection = database.getCollection(ConfigurationReaderSingleton.getSensorNameCollection());

		FindIterable<Document> searchResult = collection.find(isVisibleFilter);
		Iterator<Document> iter = searchResult.iterator();
		while (iter.hasNext()) {
			allVisibleSensors.add(new Sensor(iter.next()));
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
	}

	/**
	 * Sets the x-Axis for the graph when viewing data. If it is hourly this method
	 * simply returns a value for each hour. Otherwise the rest is as follows: Daily
	 * - a day value for an entire month after the date chosen by the user Weekly -
	 * a value that represents each week for one month chosen by user. Monthly - a
	 * month value for an entire year after the date chosen by the user Yearly - a
	 * year value for an entire century after the date chosen by the user
	 * 
	 * If something goes wrong the x-axis will display text to show there was an
	 * error.
	 */
	private void setXAxis() {
		if (cTiming.equals("h")) {
			for (int i = 0; i < 24; i++) {
				xAxis.add(Integer.toString(i));
			}
		} else {
			ZonedDateTime lastDay = startDate; // Will be set below
			if (cTiming.equals("d")) {
				lastDay = startDate.plusWeeks(1);
				for (ZonedDateTime date = startDate; date.isBefore(lastDay); date = date.plusDays(1)) {
					// Gets the day number to display
					int axisVal = date.getDayOfMonth();

					// Finds the month range and sets string
					String axisMonthVal = date.getMonth().toString();
					axisMonthVal = axisMonthVal.substring(0, 3); // Shortens the month
					xAxis.add("\"" + axisMonthVal + Integer.toString(axisVal) + "\"");
				}
			} else if (cTiming.equals("w")) {
				lastDay = startDate.plusMonths(1);

				for (ZonedDateTime startOfWeek = startDate; startOfWeek
						.isBefore(lastDay); startOfWeek = startOfWeek.plusWeeks(1)) {
					// Finds the date range
					int axisVal1 = startOfWeek.getDayOfMonth(); // Beginning of Date Range
					ZonedDateTime endOfWeek = startOfWeek.plusDays(6);
					int axisVal2 = endOfWeek.getDayOfMonth(); // End of Date Range
					// Finds the month range and sets string
					String axisMonthVal1 = startOfWeek.getMonth().toString();
					axisMonthVal1 = axisMonthVal1.substring(0, 3); // Shortens the month
					String axisMonthVal2 = endOfWeek.getMonth().toString();
					axisMonthVal2 = axisMonthVal2.substring(0, 3); // Shortens the month

					xAxis.add("\"" + axisMonthVal1 + "" + Integer.toString(axisVal1) + " - " + axisMonthVal2
							+ Integer.toString(axisVal2) + "\"");
				}
			} else if (cTiming.equals("m")) {
				lastDay = startDate.plusYears(1);
				for (ZonedDateTime date = startDate; date.isBefore(lastDay); date = date.plusMonths(1)) {
					xAxis.add("\"" + date.getMonth().toString() + "\"");
				}
			} else if (cTiming.equals("y")) {
				lastDay = startDate.plusYears(10);
				for (ZonedDateTime date = startDate; date.isBefore(lastDay); date = date.plusYears(1)) {
					int axisVal = date.getYear();
					xAxis.add("\"" + Integer.toString(axisVal) + "\"");
				}
			} else {
				System.out.println("Error on date format when setting X Axis");
				xAxis.add("Error on date format when setting X-Axis");
			}

		}
	}

	/**
	 * Due to rather odd way of receiving the date from the UI We must change the
	 * value of the Date to get the correct data for weekly, monthly, and yearly
	 * Weekly must return a selected month but the date of the month being 1 Monthly
	 * must also set the day of month to 1 but also the month to January Yearly but
	 * do the same as Monthly
	 */
	private void setStartDate() {
		ZonedDateTime firstDay = startDate;
		if (cTiming.equals("w")) {
			firstDay = startDate.minusDays((startDate.getDayOfMonth() - 1));
		} else if (cTiming.equals("m")) {
			firstDay = startDate.minusDays((startDate.getDayOfMonth() - 1));
			firstDay = firstDay.minusMonths(firstDay.getMonthValue() - 1);
		} else if (cTiming.equals("y")) {
			firstDay = startDate.minusDays((startDate.getDayOfMonth() - 1));
			firstDay = firstDay.minusMonths(firstDay.getMonthValue() - 1);
		}
		startDate = firstDay;
	}

	/**
	 * Based on the desired time and the startDate frame the endDate is set. It is
	 * very important the the startDate is formatted before the endDate or this will
	 * get messed up.
	 */
	private void setEndDate() {
		if (cTiming.equals("h")) {
			timing = "Hourly";
			endDate = startDate.plusHours(23); // Sets the proper hour range
			endDate = endDate.plusMinutes(59); // Sets the proper minute range
		} else if (cTiming.equals("d")) {
			timing = "Daily";
			endDate = startDate.plusDays(7);
		} else if (cTiming.equals("w")) {
			timing = "Weekly";
			endDate = startDate.plusMonths(1);
		} else if (cTiming.equals("m")) {
			timing = "Monthly";
			endDate = startDate.plusYears(1);
		} else if (cTiming.equals("y")) {
			timing = "Yearly";
			endDate = startDate.plusYears(10);
		} else {
			System.out.println("Couldnt set end date when getting data. \nProbably going to throw an error soon.");
		}
	}

	/**
	 * Gets the appropriate collection name based on the desired time frame.
	 * 
	 * @return the collection name
	 */
	private String getCollectionName() {
		if (cTiming.equals("h")) {
			return ConfigurationReaderSingleton.getHourlyDataCollection();
		} else if (cTiming.equals("d")) {
			return ConfigurationReaderSingleton.getDailyDataCollection();
		} else if (cTiming.equals("w")) {
			return ConfigurationReaderSingleton.getWeeklyDataCollection();
		} else if (cTiming.equals("m")) {
			return ConfigurationReaderSingleton.getMonthlyDataCollection();
		} else if (cTiming.equals("y")) {
			return ConfigurationReaderSingleton.getYearlyDataCollection();
		} else {
			System.out.println("Error when deciding on what collection to look in (DataGraphBuilder)");
			return null;
		}

	}
}
