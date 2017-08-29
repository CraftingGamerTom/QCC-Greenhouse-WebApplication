package com.craftinggamertom.pageBuilders;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.ui.Model;

import com.mongodb.client.MongoCollection;

/**
 * This class can be tough to look at with the large blocks of code.
 * Here is a few tips to understanding the contents.
 * 
 * To create the graph we want to view we must first format the start time
 * correctly since our UI does not do that for us currently.
 * [THIS CAN BE FIXED BY MAKING THE DATE CHOOSER CHANGE BASED ON TIMING OPTION]
 * then we must take the start time and calculate the end time. (setActualEndTime method)
 * 
 * Then we connect to a database based on the timing preference since our data is
 * maintained according the the timing options
 * 
 * Then we can collect the data and put it into an array list to be shown on a graph
 * [CURRENTLY THE SEARCHING IS DONE ON THE APP - THIS IS VERY INEFFICIENT]
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
	private String location = "";
	private String type = "";
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private String timing;
	private String sensorID;

	private List<Document> documents;
	private String cTiming;
	private Model model;

	public DataGraphBuilder() {
		super();

	}

	/**
	 * Handles everything that is required to return all the needed
	 * components of the view data graph
	 * @param cSensor
	 * @param cTiming
	 * @param date
	 * @param model
	 * @return
	 */
	public Model buildPage(String cSensor, String cTiming, String date, Model model) {
		// Due to the use of a ZonedDateTime object we must convert this manually before
		// parsing
		date = date + "T00:00:00-05:00";
		this.startDate = ZonedDateTime.parse(date);
		this.cTiming = cTiming;
		this.model = model;
		this.sensorID = convertSensor(cSensor).get(0);
		this.location = convertSensor(cSensor).get(1);
		this.type = convertSensor(cSensor).get(2);

		setActualStartDate(); // MUST BE FIRST - Critical errors otherwise
		connectToDocument();
		addDataGraphData();
		setXAxis();

		setTestGraph();

		return addPageAttributes();

	}

	/**
	 * Based on the timing the end date is set then the appropriate data is
	 * collected and put into the ArrayList containing all the points on the graph
	 */
	private void addDataGraphData() {
		boolean workable = false;
		if (cTiming.equals("h")) {
			timing = "Hourly";
			endDate = startDate.plusHours(23); // Sets the proper hour range
			endDate = endDate.plusMinutes(59); // Sets the proper minute range
			workable = true;
		}
		if (cTiming.equals("d")) {
			timing = "Daily";
			endDate = startDate.plusDays(7);
			workable = true;
		} else if (cTiming.equals("w")) {
			timing = "Weekly";
			endDate = startDate.plusMonths(1);
			workable = true;
		} else if (cTiming.equals("m")) {
			timing = "Monthly";
			endDate = startDate.plusYears(1);
			workable = true;
		} else if (cTiming.equals("y")) {
			timing = "Yearly";
			endDate = startDate.plusYears(10);
			workable = true;
		}
		if (workable) {
			for (Document document : documents) {
				String dbDate = document.getString("date");
				ZonedDateTime dbZDT = ZonedDateTime.parse(dbDate);
				if (dbZDT.isAfter(startDate) && dbZDT.isBefore(endDate)) {
					if (document.containsValue(sensorID)) {
						highValue.add(document.getDouble("highValue"));
						lowValue.add(document.getDouble("lowValue"));

					}
				}
			}
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
		model.addAttribute("location", location);
		model.addAttribute("type", type);
		model.addAttribute("start-date", startDate);
		model.addAttribute("end-date", endDate);
		model.addAttribute("sensor-id", sensorID);
		model.addAttribute("current-time", currentTime);
		model.addAttribute("timing", timing);
		// data
		model.addAttribute("high-value", highValue.toString());
		model.addAttribute("low-value", lowValue.toString());

		return model;
	}

	private void connectToDocument() {
		MongoCollection<Document> collection = database.getCollection(cTiming);
		// Collects all documents
		documents = (List<Document>) collection.find().into(new ArrayList<Document>());

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
	private void setActualStartDate() {
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

	private void setTestGraph() {
		for (double i = 0; i < xAxis.size(); i++) {
			if (i > 12) {
				this.highValue.add(-(i * i));
			} else {
				this.highValue.add(i * i);
			}

		}
		for (double i = 0; i < xAxis.size(); i++) {
			if (i > 12) {
				this.lowValue.add((-(i * i)) / 2);
			} else {
				this.lowValue.add((i * i) / 2);
			}
		}
	}
}
