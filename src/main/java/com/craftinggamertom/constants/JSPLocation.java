/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.constants;

/**
 * Maintains the locations of all the files.
 * 
 * @author TomWR
 *
 */
public class JSPLocation {

	// common
	public static final String errorPage = "pages/common/errorPage";
	public static final String unauthorized = "pages/common/unauthorized";
	// view
	public static final String oldLiveData = "pages/view/old-live-data"; // Not implemented?
	public static final String liveData = "pages/view/live-data";
	public static final String rawData = "pages/view/raw-data";
	public static final String sensorData = "pages/view/sensor-data";
	public static final String observations = "pages/view/observations";
	// user
	public static final String userProfile = "pages/user/profile";
	// manager
	public static final String manageUsers = "pages/manager/manage/users";
	// anonymous
	public static final String organizationFeed = "pages/anonymous/feed";
	public static final String register = "pages/anonymous/register";
	// admin
	public static final String manageSensorsFriendlyNames = "pages/admin/manage/sensors/friendly-names";
	public static final String manageUsersEditUser = "pages/admin/manage/users/user-edit";
}
