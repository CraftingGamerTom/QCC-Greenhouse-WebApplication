package com.craftinggamertom.pageBuilders;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReader;
import com.craftinggamertom.database.SensorInfo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class ManageFriendlyNamesBuilder extends PageBuilder {

	private Model model;
	private String sensorType;
	
	
	/** Default Constructor} */
	public ManageFriendlyNamesBuilder() {
		super();
	}

	/**
	 * Does all the work to create the admin manage friendly names page. It
	 * automatically adds the elements to the given model then returns the model to
	 * the caller for display.
	 * 
	 * @param type as selected by the admin to display certain types
	 * @param model the model for the page to be modelled by
	 * @return Model with all elements for display in it
	 */
	public Model buildPage(String sensorType, Model model) {
		
		this.sensorType = sensorType;
		this.model = model;
		
		return addPageAttributes();

	}

	private Model addPageAttributes() {
		model.addAttribute("shown-type", sensorType); // for "telling" user what is queried
		model.addAttribute("displayed-sensors", getSensorOptions());
		model.addAttribute("type-options", getTypeOptions());
		
		return model;
	}
	
	private String getTypeOptions() {
		String message = "";
		ArrayList<SensorInfo> allSensors = new ArrayList<SensorInfo>();
		ArrayList<String> allTypes = new ArrayList<String>();
		
		MongoCollection<Document> collection = null;
		collection = database.getCollection(ConfigurationReader.sensorNamesCollection);
		
		FindIterable<Document> searchResult = collection.find();

		Iterator<Document> iter = searchResult.iterator();
		while(iter.hasNext()) {
			allSensors.add(new SensorInfo(iter.next()));
		}
		
		allTypes.add("all"); // adds all option for default value
		
		// Gets all the types into an arraylist
		for(int i = 0; i < allSensors.size(); i++) {
			
			String type = allSensors.get(i).getType();
			
			if(isNotDuplicate(allTypes, type)) {
				allTypes.add(type);
			}
		}
		// Adds all types as options on the UI
		for(int i = 0; i < allTypes.size(); i++) {
			message +="\n";
			message += "<option value=\"";
			message += allTypes.get(i);
			message += "\">";
			message += allTypes.get(i);
			message += "</option>";
		}
		
		return message;
	}

	/**
	 * Checks if the type has already been added to the array since we do not want duplicate type options for the query on the UI. 
	 * 
	 * The currently method is really inefficient for a large number of sensors
	 * 
	 * @param allSensors The array of all the sensors in the database
	 * @param type the type to check for duplicates
	 * @return true if it is a new type
	 */
	private boolean isNotDuplicate(ArrayList<String> allSensors, String type) {
		for(int i = 0; i < allSensors.size(); i++) {
			if(allSensors.get(i).equals(type)) {
				return false;
			}
			
		}
		return true;
	}

	/**
	 * based on a given boolean value the function will return a string to generate the needed html to display either a checked or unchecked checkbox
	 * The String is put into the html string below
	 * @return a html string
	 */
	private String getIsCheckedString(boolean isChecked) {
		if(isChecked) {
			return " checked=\"\" ";
		}
		return " "; // returns a space for formating reasons if the checkbox is not checked
	}
	/**
	 * Makes a connection to mongo db and get the info of a all
	 * sensors to display
	 * 
	 * @return A string of html to represent the options
	 */
	private String getSensorOptions() {
		String message = "";
		ArrayList<SensorInfo> allSensors = new ArrayList<SensorInfo>();
		
		MongoCollection<Document> collection = null;
		collection = database.getCollection(ConfigurationReader.sensorNamesCollection);

		FindIterable<Document> searchResult;
		if(!sensorType.equals("all")) {
			Bson typeFilter = Filters.eq("type", sensorType);
			searchResult = collection.find(typeFilter);
		} else {
			searchResult = collection.find();
		}
		Iterator<Document> iter = searchResult.iterator();
		while(iter.hasNext()) {
			allSensors.add(new SensorInfo(iter.next()));
		}
		for(int i = 0; i < allSensors.size(); i++) {
			
			String index = Integer.toString(i);
			String sensorID = allSensors.get(i).getSensorId();
			String type = allSensors.get(i).getType();
			String date = allSensors.get(i).getDate();
			String description = allSensors.get(i).getDescription();
			String friendlyName = allSensors.get(i).getFriendlyName();
			boolean isVisible = allSensors.get(i).isVisible();
			boolean isDefault = allSensors.get(i).isDefault();

			
			message +="                                <form role=\"form\" class=\"form-inline\" action=\"/admin/manage/sensors/friendly-names/update\">\r\n" + 
					"                                <tr>\r\n" + 
					"                                    <td>" + index +"</td>\r\n" + 
					"                                    <td><input name=\"sensor-id\" id=\"sensor-id\" type=\"hidden\" class=\"form-control\" value=\"" + sensorID + "\">" + sensorID + "</td>\r\n" + 
					"                                    <td>" + type + "</td>\r\n" + 
					"                                    <td>" + date + "</td>\r\n" + 
					"                                    <td><input name=\"description\" id=\"description\" type=\"text\" class=\"form-control\" value=\"" + description + "\"></td>\r\n" + 
					"                                    <td><input name=\"friendly-name\" id=\"friendly-name\" type=\"text\" class=\"form-control\" value=\"" + friendlyName + "\"></td>\r\n" + 
					"                                    <td>\r\n" + 
					"                                        <input type=\"checkbox\" id=\"is-visible\" name=\"is-visible\" value=\"1\"" + getIsCheckedString(isVisible) + "/>\n" +
					"                                    </td>\r\n" + 
					"                                    <td>\r\n" +
					"                                        <input type=\"checkbox\" id=\"is-default\" name=\"is-default\" value=\"1\"" + getIsCheckedString(isDefault) + "/>\n" +
					"                                    </td>\r\n" + 
					"                                    <td><input id=\"submit-button\" class=\"btn btn-xs btn-primary\" type=\"submit\" value=\"Save\"></td>\r\n" + 
					"                                </tr>\r\n" + 
					"	                            </form>";

		}
		
		return message;
	}
	
}
