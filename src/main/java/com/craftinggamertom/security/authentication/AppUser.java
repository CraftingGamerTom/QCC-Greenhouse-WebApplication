/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.security.authentication;

import java.util.HashMap;
import java.util.Map;

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

	private static String authority_key; // Keyword for authority level
	private static String join_date; // Date the user was added to the database
	private static String num_of_observations; // Number of observations the user has created
	private static String num_of_updates; // Number of updates the user has created
	private static String last_seen; // Last time the user was known to be online
	private static String nickname; // Nickname for the user
	private static String email_address; // Users Email address
	private static String cell_phone; // Users cell phone number to text - in case of emergency

	// The items below are meant to be implemented later
	// When they get implemented the entire User collection in the database must be
	// updated to have these values as they wont exist

	// private static Map<String, String> notification_subscriptions; // A Map of
	// the notifications a user is subscribed to
	// ([{n1:true},{n2:false}, etc])
	// private static Map<String, String> recieved_notifications; // A Map of the
	// notifications a user recieved (track type(phone, email,
	// web), wether it has been opened or not on web, and the notification
	// object(not sure how that is set up yet but is should have the
	// date-time, a title, hidden list of recipients and a description)
	// private static Map<String, String> conversations; // Conversations the user
	// has had (track: messages(time, recipient, sender), hasBeenReadBoolean, and
	// more?)
	// private static String num_of_messages; // For display on navigation bar

	/**
	 * Creates the AppUser from a map. this is the easiest way to create a user
	 * based on the information in the database
	 * 
	 * @param userMap
	 * @param userInfo
	 */
	public AppUser(UserInfo userInfo, HashMap<String, String> userMap) {
		// Sets the UserInfo
		setUserInfo(userInfo);

		// Sets the other information
		// The defaults are used for a user that is not signed in
		setAuthority_key(userMap.getOrDefault("authority_key", "anonymous"));
		setJoin_date(userMap.getOrDefault("join_date", "2017/01/25 00:00:00"));
		setNum_of_observations(userMap.getOrDefault("num_of_observations", "-1"));
		setNum_of_updates(userMap.getOrDefault("num_of_updates", "-1"));
		setLast_seen(userMap.getOrDefault("last_seen", "2017/01/25 00:00:00"));
		setNickname(userMap.getOrDefault("nickname", "anon user"));
		setEmail_address(userMap.getOrDefault("email_address", "anonymousUser_Email"));
		setCell_phone(userMap.getOrDefault("cell_phone", "anon_phone"));
	}

	private void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	private static void setAuthority_key(String authority_key) {
		AppUser.authority_key = authority_key;
	}

	private static void setJoin_date(String join_date) {
		AppUser.join_date = join_date;
	}

	private static void setNum_of_observations(String num_of_observations) {
		AppUser.num_of_observations = num_of_observations;
	}

	private static void setNum_of_updates(String num_of_updates) {
		AppUser.num_of_updates = num_of_updates;
	}

	private static void setLast_seen(String last_seen) {
		AppUser.last_seen = last_seen;
	}

	private static void setNickname(String nickname) {
		AppUser.nickname = nickname;
	}

	private static void setEmail_address(String email_address) {
		AppUser.email_address = email_address;
	}

	private static void setCell_phone(String cell_phone) {
		AppUser.cell_phone = cell_phone;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public String getAuthority_key() {
		return authority_key;
	}

	public static String getJoin_date() {
		return join_date;
	}

	public static String getNum_of_observations() {
		return num_of_observations;
	}

	public static String getNum_of_updates() {
		return num_of_updates;
	}

	public static String getLast_seen() {
		return last_seen;
	}

	public static String getNickname() {
		return nickname;
	}

	public static String getEmail_address() {
		return email_address;
	}

	public static String getCell_phone() {
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
