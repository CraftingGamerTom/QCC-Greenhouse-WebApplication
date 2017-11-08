package com.craftinggamertom.pageBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.database.MongoDatabaseConnection;
import com.craftinggamertom.database.SensorInfo;
import com.craftinggamertom.security.authentication.UserInfo;
import com.craftinggamertom.security.authorization.AppAuthorizer;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class PageBuilder {

	protected MongoDatabase database;
	protected String currentTime = "";

	protected Model model;

	public PageBuilder() {

		// Collects and sets the current Time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		currentTime = dtf.format(now); // 2016/11/16 12:08:43

		try {
		database = MongoDatabaseConnection.getInstance(); // Singleton
		}catch(Exception e) {
			System.out.println(" ***** ERROR CONNECTING TO MONGO DB ***** ");
			System.out.println("ERROR WHEN GETTING DATABASE. (PageBuilder.java) STACKTRACE:");
			e.printStackTrace();
			System.out.println(" ***** END ERROR ***** ");
		}

	}

	/**
	 * The only part of the page built here is the nvaigation bar
	 * 
	 * It is typical for a child class to call this to insure the nvaigation bar
	 * loads properly.
	 * 
	 * @return Model containing all the variables
	 */
	public Model buildPage(Model model) {
		this.model = model;

		this.model.addAllAttributes(getNavigationBarAttributes());
		this.model.addAllAttributes(getFooterAttributes());

		return this.model;

	}

	/**
	 * Adds the attributes for the navigation. This is needed for every single page
	 */
	protected Map<String, String> getNavigationBarAttributes() {
		// Create Map
		Map<String, String> map = new HashMap<String, String>();

		// add domain name
		map.put("domain-name", ConfigurationReaderSingleton.getDomainName());

		// layout for signed in user or anonymousUser
		String theHTML = "";
		UserInfo userInfo;
		
		userInfo = AppAuthorizer.authorizeUser();

		if (userInfo.getId().equals("anonymousUser_Id")) {
			/*
			String signIn = "<button class=\"btn btn-sm btn-primary\" type=\"submit\" href=\"/register\"><strong>Register</strong></button>\r\n";
			String signUp = "<button class=\"btn btn-sm btn-white\" type=\"submit\" href=\"/login\">Log in</button>\r\n";
			*/
			String signIn = "<li><a href=\"/login\">Sign in</a></li>\r\n";
			String signUp = "<li><a href=\"/register\">Register</a></li>\r\n";
			
			
			theHTML = signIn + signUp;
		} else {

			String username = "	                <li>\r\n"
					+ "	                    <span class=\"m-r-sm text-muted welcome-message\">" + userInfo.getName()
					+ "</span>\r\n" + "	                </li>\r\n";
			String messages = ""; // for messages html if implemented later
			String notifications = ""; // for notifications html if implemented later

			String buttons = "<li>\r\n" + "                        <a href=\"/logout\">\r\n"
					+ "                            <i class=\"fa fa-sign-out\"></i> Log out\r\n"
					+ "                        </a>\r\n" + "                    </li>";
			buttons += "\n <logout/>";

			theHTML = username + messages + notifications + buttons;
		}

		map.put("sign-in-section", theHTML);

		return map;

	}
	
	/**
	 * Adds the needed attributes to all the footers.
	 * @return
	 */
	private Map<String, String> getFooterAttributes() {
		Map<String, String> map = new HashMap<String, String>();
		
		// Add date
		Calendar now = Calendar.getInstance();   // Gets the current date and time
		String year = Integer.toString(now.get(Calendar.YEAR));       // The current year converted to String
		map.put("copyright-year", year);
		// end add date
		
		return map;
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
		collection = database.getCollection(ConfigurationReaderSingleton.getSensorNamesCollection());

		Document searchResult = collection.find(sensorFilter).first();

		SensorInfo sensor = new SensorInfo(searchResult);
		return sensor;
	}
}
