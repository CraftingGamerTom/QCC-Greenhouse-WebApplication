package com.craftinggamertom.security.authentication;

/**
 * A object that contains all of the information on a user.
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
	 * authority_key
	 * date_joined
	 * num_of_observations
	 * num_of_updates
	 * last_seen
	 * nickname
	 * email_address
	 * AND ALL USERINFO
	 * 
	 */
	
	public String getAuthorityKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		userInfo.getName();
		return null;
	}

}
