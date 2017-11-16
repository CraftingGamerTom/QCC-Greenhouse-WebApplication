package com.craftinggamertom.security.authentication;

/**
 * A object that contains all of the information on a user.
 * 
 * @author Thomas Rokicki
 *
 */
public class AppUser {

	private UserInfo userInfo;

	private static String authorityKey;

	public AppUser(UserInfo userInfo) {
		// TODO: add other initializing variables
		this.userInfo = userInfo;
	}

	/*
	 * TODO: This AppUser class should have the following and is not limited to
	 * authority_key date_joined num_of_observations num_of_updates last_seen
	 * nickname email_address AND ALL USERINFO
	 * 
	 */

	public String getAuthorityKey() {
		// TODO Auto-generated method stub
		return null;
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
		return userInfo.getName();
	}

}
