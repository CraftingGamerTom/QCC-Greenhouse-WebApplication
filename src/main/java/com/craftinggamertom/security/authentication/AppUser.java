/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.security.authentication;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

/**
 * A object that contains all of the information on a user.
 * 
 * @author Thomas Rokicki
 *
 */
public class AppUser {

	/*
	 * UserInfo contains: id name givenName familyName gender picture link token
	 * //intitialized as null - may be used later
	 */
	private UserInfo userInfo;

	private String database_id; // _id in database
	private String authority_key; // Keyword for authority level
	private String join_date; // Date the user was added to the database
	private String num_of_observations; // Number of observations the user has created
	private String num_of_updates; // Number of updates the user has created
	private String last_seen; // Last time the user was known to be online
	private String nickname; // Nickname for the user
	private String email_address; // Users Email address
	private String cell_phone; // Users cell phone number to text - in case of emergency

	// The items below are meant to be implemented later
	// When they get implemented the entire User collection in the database must be
	// updated to have these values as they wont exist

	// private Map<String, String> notification_subscriptions; // A Map of
	// the notifications a user is subscribed to
	// ([{n1:true},{n2:false}, etc])
	// private Map<String, String> recieved_notifications; // A Map of the
	// notifications a user recieved (track type(phone, email,
	// web), wether it has been opened or not on web, and the notification
	// object(not sure how that is set up yet but is should have the
	// date-time, a title, hidden list of recipients and a description)
	// private Map<String, String> conversations; // Conversations the user
	// has had (track: messages(time, recipient, sender), hasBeenReadBoolean, and
	// more?)
	// private String num_of_messages; // For display on navigation bar

	/**
	 * Creates the AppUser from a map. this is the easiest way to create a user
	 * based on the information in the database
	 * 
	 * Ideally this should only be used for users that are not signed in. Use
	 * AppUser(Document) constructor otherwise
	 * 
	 * @param userMap
	 * @param userInfo
	 */
	public AppUser(UserInfo userInfo, HashMap<String, String> userMap) {
		// Sets the UserInfo
		setUserInfo(userInfo);

		// Sets the other information
		// The defaults are used for a user that is not signed in
		setDatabaseId(userMap.getOrDefault("_id", "anonymous_db_id"));
		setAuthority_key(userMap.getOrDefault("authority_key", "anonymous"));
		setJoin_date(userMap.getOrDefault("join_date", "2017/01/25 00:00:00"));
		setNum_of_observations(userMap.getOrDefault("num_of_observations", "-1"));
		setNum_of_updates(userMap.getOrDefault("num_of_updates", "-1"));
		setLast_seen(userMap.getOrDefault("last_seen", "2017/01/25 00:00:00"));
		setNickname(userMap.getOrDefault("nickname", "anon user"));
		setEmail_address(userMap.getOrDefault("email_address", "anonymousUser_Email"));
		setCell_phone(userMap.getOrDefault("cell_phone", "anon_phone"));
	}

	public AppUser(Document userDoc) {

		// Sets the UserInfo
		setUserInfo(new UserInfo(userDoc.getString("id"), userDoc.getString("name"), userDoc.getString("given_name"),
				userDoc.getString("family_name"), userDoc.getString("gender"), userDoc.getString("picture"),
				userDoc.getString("link")));

		// Sets the other information
		// The defaults are used for a user that is not signed in
		setDatabaseId(userDoc.getObjectId("_id").toString());
		setAuthority_key(userDoc.getString("authority_key"));
		setJoin_date(userDoc.getString("join_date"));
		setNum_of_observations(userDoc.getString("num_of_observations"));
		setNum_of_updates(userDoc.getString("num_of_updates"));
		setLast_seen(userDoc.getString("last_seen"));
		setNickname(userDoc.getString("nickname"));
		setEmail_address(userDoc.getString("email_address"));
		setCell_phone(userDoc.getString("cell_phone"));
	}

	private void setDatabaseId(String database_id) {
		this.database_id = database_id;
	}

	private void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	private void setAuthority_key(String authority_key) {
		this.authority_key = authority_key;
	}

	private void setJoin_date(String join_date) {
		this.join_date = join_date;
	}

	private void setNum_of_observations(String num_of_observations) {
		this.num_of_observations = num_of_observations;
	}

	private void setNum_of_updates(String num_of_updates) {
		this.num_of_updates = num_of_updates;
	}

	private void setLast_seen(String last_seen) {
		this.last_seen = last_seen;
	}

	private void setNickname(String nickname) {
		this.nickname = nickname;
	}

	private void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	private void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}

	public String getDatabaseId() {
		return database_id;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public String getAuthority_key() {
		return authority_key;
	}

	public String getJoin_date() {
		return join_date;
	}

	public String getNum_of_observations() {
		return num_of_observations;
	}

	public String getNum_of_updates() {
		return num_of_updates;
	}

	public String getLast_seen() {
		return last_seen;
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail_address() {
		return email_address;
	}

	public String getCell_phone() {
		return cell_phone;
	}

	public String getName() {
		return userInfo.getName();
	}

	public String getFamilyName() {
		return userInfo.getFamilyName();
	}

	public String getGender() {
		return userInfo.getGender();
	}

	public String getGivenName() {
		return userInfo.getGivenName();
	}

	public String getId() {
		return userInfo.getId();
	}

	public String getLink() {
		return userInfo.getLink();
	}

	public String getPicture() {
		return userInfo.getPicture();
	}

	public String getToken() {
		return userInfo.getToken();
	}

	/**
	 * Gets the information about the AppUser in the form of a map.
	 * 
	 * This method includes the database id for the user ("_id")
	 * 
	 * IMPORTANT: Do NOT use this to create a new user. The _id must be defined by
	 * the database. If you use this method to create a new AppUser in the database
	 * their _id will be wrong.
	 * 
	 * @return A Map that contains all the AppUser's information
	 */
	public Map<String, String> getAllInformation() {
		HashMap<String, String> infoMap = new HashMap<String, String>();

		// UserInfo
		infoMap.put("id", getId());
		infoMap.put("name", getName());
		infoMap.put("given_name", getGivenName());
		infoMap.put("family_name", getFamilyName());
		infoMap.put("gender", getGender());
		infoMap.put("picture", getPicture());
		infoMap.put("link", getLink());

		// Other
		infoMap.put("_id", getDatabaseId());
		infoMap.put("authority_key", getAuthority_key());
		infoMap.put("join_date", getJoin_date());
		infoMap.put("num_of_observations", getNum_of_observations());
		infoMap.put("num_of_updates", getNum_of_updates());
		infoMap.put("last_seen", getLast_seen());
		infoMap.put("nickname", getNickname());
		infoMap.put("email_address", getEmail_address());
		infoMap.put("cell_phone", getCell_phone());

		return infoMap;
	}

	/**
	 * This method gets the information to create a new user in the database.
	 * 
	 * It does not include the users database id ("_id") so that the database can
	 * create a unique id for the user
	 * 
	 * @return information needed for the database to create a new user
	 */
	public Map<String, String> getInformationToPutInDatabase() {
		HashMap<String, String> infoMap = new HashMap<String, String>();

		// UserInfo
		infoMap.put("id", getId());
		infoMap.put("name", getName());
		infoMap.put("given_name", getGivenName());
		infoMap.put("family_name", getFamilyName());
		infoMap.put("gender", getGender());
		infoMap.put("picture", getPicture());
		infoMap.put("link", getLink());

		// Other (Does not include _id on purpose)
		infoMap.put("authority_key", getAuthority_key());
		infoMap.put("join_date", getJoin_date());
		infoMap.put("num_of_observations", getNum_of_observations());
		infoMap.put("num_of_updates", getNum_of_updates());
		infoMap.put("last_seen", getLast_seen());
		infoMap.put("nickname", getNickname());
		infoMap.put("email_address", getEmail_address());
		infoMap.put("cell_phone", getCell_phone());

		return infoMap;
	}

}
