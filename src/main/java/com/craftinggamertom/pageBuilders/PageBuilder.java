/**
 * Copyright (c) 2017 Thomas Rokicki
 */

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
import com.craftinggamertom.entity.Sensor;
import com.craftinggamertom.security.authentication.AppUser;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.craftinggamertom.security.authorization.UserAuthority;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * @author Thomas Rokicki
 *
 */
public class PageBuilder {

	protected MongoDatabase database;
	protected String currentTime = "";

	protected Model model;

	protected UserAuthority userAuthority;

	public PageBuilder() {

		// Collects and sets the current Time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		currentTime = dtf.format(now); // 2016/11/16 12:08:43

		userAuthority = new UserAuthority(); // Gets the user to check against

		try {
			database = MongoDatabaseConnection.getInstance(); // Singleton
		} catch (Exception e) {
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

		this.model.addAllAttributes(getNavigationLinks());
		this.model.addAllAttributes(getNavigationBarAttributes());
		this.model.addAllAttributes(getFooterAttributes());

		this.model.addAttribute("alert-content", getAlertContent()); // Alert Container just below the navigation bar

		return this.model;
	}

	/**
	 * Authorizes the user and only adds what they can see to the page
	 */
	private Map<String, String> getNavigationLinks() {
		// Create Map
		Map<String, String> map = new HashMap<String, String>();

		String theHTML = ""; // This string returned;

		String anonLinks = "";
		String appUserLinks = "";
		String managerLinks = "";
		String adminLinks = "";
		String devLinks = "";

		PageAuthority appUserAuthority = new PageAuthority("user");
		PageAuthority managerAuthority = new PageAuthority("manager");
		PageAuthority adminAuthority = new PageAuthority("admin");
		PageAuthority developerAuthority = new PageAuthority("developer");

		if (developerAuthority.grantAccessGTE(userAuthority)) {
			devLinks = "";
		}
		if (adminAuthority.grantAccessGTE(userAuthority)) {
			adminLinks = "\r\n" + "                    <li class=\"dropdown\">\r\n"
					+ "                        <a aria-expanded=\"false\" role=\"button\" href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">Admin<span class=\"caret\"></span></a>\r\n"
					+ "                        <ul role=\"menu\" class=\"dropdown-menu\">\r\n"
					+ "                            <li><a href=\"/admin/manage/sensors/friendly-names\">Friendly Names</a></li>\r\n"
					// + " <li><a
					// href=\"/admin/manage/modules/emergency-alerts\">Alerts</a></li>\r\n"
					// + " <li><a href=\"/admin/manage/modules/meetings\">Meetings</a></li>\r\n"
					+ "                        </ul>\r\n" + "                    </li>" + "\r\n";

		}
		if (managerAuthority.grantAccessGTE(userAuthority)) {
			managerLinks = "\r\n" + "                    <li class=\"dropdown\">\r\n"
					+ "                        <a aria-expanded=\"false\" role=\"button\" href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">Manager<span class=\"caret\"></span></a>\r\n"
					+ "                        <ul role=\"menu\" class=\"dropdown-menu\">\r\n"
					+ "                            <li><a href=\"/manager/manage/users\">Users</a></li>\r\n"
					+ "                        </ul>\r\n" + "                    </li>" + "\r\n";

		}
		if (appUserAuthority.grantAccessGTE(userAuthority)) {
			appUserLinks = "";

		}
		// anonymous links (always get put in)
		anonLinks = "\r\n" + "                    <li>\r\n"
				+ "                        <a aria-expanded=\"false\" role=\"button\" href=\"/dashboard\">Dashboard</a>\r\n"
				+ "                    </li>" + "                    <li class=\"dropdown\">\r\n"
				+ "                        <a aria-expanded=\"false\" role=\"button\" href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">Data<span class=\"caret\"></span></a>\r\n"
				+ "                        <ul role=\"menu\" class=\"dropdown-menu\">\r\n"
				+ "                            <li><a href=\"/view/live-data\">Live Data</a></li>\r\n"
				+ "                            <li><a href=\"/view/sensor-data\">Sensor Data</a></li>\r\n"
				+ "                            <li><a href=\"/view/raw-data\">Raw Data</a></li>\r\n" +
				// " <li><a href=\"/view/compare-data\">Compare Data</a></li>\r\n" + // Removed
				// until implemented
				"                        </ul>\r\n" + "                    </li>" + "                    <li>\r\n"
				+ "                        <a aria-expanded=\"false\" role=\"button\" href=\"view/observation-notes\">Observation Notes</a>\r\n"
				+ "                    </li>\r\n" + "\r\n";

		theHTML = anonLinks + appUserLinks + managerLinks + adminLinks + devLinks; // Orders the HTML
		map.put("nav-link-section", theHTML);

		return map;
	}

	/**
	 * Adds the attributes for the navigation. This is needed for every single page
	 */
	protected Map<String, String> getNavigationBarAttributes() {
		// Create Map
		Map<String, String> map = new HashMap<String, String>();

		// add domain name
		// REMOVED BECAUSE THE THE LINK IS NOW SET UP STATICLY. JUST COMMENTED OUT FOR
		// FUTURE USE
		// map.put("domain-name", ConfigurationReaderSingleton.getDomainName());

		// layout for signed in user or anonymousUser
		String theHTML = "";

		PageAuthority pageAuthority = new PageAuthority("unverified"); // Sets the credentials needed
		AppUser appUser = userAuthority.getUser(); // Gets the user for referencing

		if (pageAuthority.grantAccessGTE(userAuthority)) { // If the user is >= "unverified" credentials do work

			// String username = " <li>\r\n"
			// + " <span class=\"m-r-sm text-muted welcome-message\">" + appUser.getName()
			// + "</span>\r\n" + " </li>\r\n";

			String username = "\r\n" + "             <ul class=\"nav navbar-nav navbar-right\">\r\n"
					+ "                    <li class=\"dropdown\">\r\n"
					+ "                        <a aria-expanded=\"false\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">"
					+ "<img alt=\"image\" class=\"img-sm\" style=\"width:18px;height:18px;\" src=\""
					+ appUser.getPicture() + "\" />\r\n" + appUser.getName() + "<span class=\"caret\"></span></a>\r\n"
					+ "            				<ul role=\"menu\" class=\"dropdown-menu\">\r\n"
					+ "                           	<li><a href=\"/user/profile\">Profile</a></li>\r\n"
					// + " <li><a href=\"/user/organizations\">Organizations</a></li>\r\n"
					// + " <li><a href=\"/user/settings">Settings</a></li>\r\n"
					+ "                        		<li>\r\n"
					+ "                        			<a href=\"/logout\">\r\n"
					+ "                            		<i class=\"fa fa-sign-out\"></i> Log out\r\n"
					+ "                        			</a>\r\n" + "                    			</li>"
					+ "                    			<logout/>" + "                 		</ul>\r\n"
					+ "           			</li>\r\n" + "            	</ul>\r\n" + "\r\n";
			String messages = ""; // for messages html if implemented later
			String notifications = ""; // for notifications html if implemented later

			theHTML = messages + notifications + username;

		} else { // If the user is not signed in - do work

			// String signIn = "<button class=\"btn btn-sm btn-white\" href=\"/login\">Log
			// in</button>\r\n";
			// String signUp = "<button class=\"btn btn-sm btn-primary\"
			// href=\"/register\"><strong>Register</strong></button>\r\n";
			String navStart = "             <ul class=\"nav navbar-top-links navbar-right\">\r\n";
			String signIn = "                   <li><a href=\"/login\">Sign in</a></li>\r\n";
			String signUp = "                   <li><a href=\"/register\">Register</a></li>\r\n";
			String navEnd = "            	</ul>\r\n";

			theHTML = navStart + signIn + signUp + navEnd;
		}

		map.put("nav-sign-in-section", theHTML);

		return map;

	}

	/**
	 * Adds the needed attributes to all the footers.
	 * 
	 * @return
	 */
	private Map<String, String> getFooterAttributes() {
		Map<String, String> map = new HashMap<String, String>();

		// Add date
		Calendar now = Calendar.getInstance(); // Gets the current date and time
		String year = Integer.toString(now.get(Calendar.YEAR)); // The current year converted to String
		map.put("copyright-year", year);
		// end add date

		return map;
	}

	/**
	 * If a new sensor is added it MUST be added here and on the front-end of the
	 * UI. This method converts the value on the front end to appropriate sensorID.
	 * 
	 * @param cSensor
	 * @return SensorInfo object
	 */
	protected Sensor convertSensor(String cSensor) {

		Bson sensorFilter = Filters.eq("isDefault", true);

		// if not default
		if (!cSensor.equals("default")) {
			sensorFilter = Filters.eq("sensorId", cSensor);
		}

		MongoCollection<Document> collection = null;
		collection = database.getCollection(ConfigurationReaderSingleton.getSensorNameCollection());

		Document searchResult = collection.find(sensorFilter).first();

		Sensor sensor;
		try {
			sensor = new Sensor(searchResult);

		} catch (NullPointerException nullE) {
			System.out.println("NullPointer(PageBuilder): Adding a dumby sensor with placemarkers and warning user");
			sensor = new Sensor(); // Uses placemarker metadata

			// To warn user of no sensor data
			String warning = "<div class=\"row wrapper page-heading\">\r\n" + "	<div class=\"col-lg-12\">\r\n"
					+ "		<div class=\"alert alert-warning\">\r\n"
					+ "			<p class=\"alert-link text-center\">Hey!</p>\r\n"
					+ "			<p class=\"text-center\">You don't seem to have any data to show. You should add some.</p>\r\n"
					+ "			<p class=\"text-center\">Perhaps there is no default sensor set. An admin can fix that.</p>\r\n"
					+ "		</div>\r\n" + "	</div>\r\n" + "</div>";
			this.model.addAttribute("no-sensor-data-found-warning", warning);
			return sensor;

		} catch (Exception e) {
			System.out.println("Error converting sensor (PageBuilder): Stacktrace: ");
			e.printStackTrace();
			return null;
		}
		model.addAttribute("no-sensor-data-found-warning", ""); // only called if data was found in database - to
																// make sure the value is not null
		return sensor;
	}

	/**
	 * For page alerts. Even if the attibute is added the page MUST include the tag
	 * below
	 * 
	 * <%=request.getAttribute("alert-content")%>
	 * 
	 * @return the string that makes up the alert
	 */
	protected String getAlertContent() {
		String alert = "";

		AppUser appUser = userAuthority.getUser(); // Gets the user for referencing

		if (!appUser.getId().equals("anonymousUser_Id") && appUser.getDatabaseId().equals("anonymous_db_id")) { // to
																												// check
																												// if
																												// the
																												// database
																												// is
																												// online
			alert = "<div class=\"row wrapper page-heading\">\r\n" + "	<div class=\"col-lg-12\">\r\n"
					+ "		<div class=\"alert alert-warning\">\r\n"
					+ "			<p class=\"alert-link text-center\">Umm..</p>\r\n"
					+ "			<p class=\"text-center\">You wouldn't happen to have seen a database run by here, have you?</p>\r\n"
					+ "			<p class=\"text-center\">Contact a server admin to fix this mess.</p>\r\n"
					+ "		</div>\r\n" + "	</div>\r\n" + "</div>";
		}
		return alert;
	}

	/**
	 * Gets the UserAuthority. This should be called in the Controller Classes to
	 * avoid duplicating the UserAuthority causing slowing down and duplicating the
	 * last seen update.
	 * 
	 * @return UserAuthority the UserAuthority for the signed in user.
	 */
	public UserAuthority getUserAuthority() {
		return userAuthority;
	}

}
