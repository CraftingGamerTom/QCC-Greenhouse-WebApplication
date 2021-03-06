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
	// entity
	public static final String observations = "pages/entity/observations";
	// user
	public static final String userProfile = "pages/user/profile";
	public static final String editUserProfile = "pages/user/edit-profile";
	// manager
	public static final String manageUsers = "pages/manager/manage/users";
	public static final String manageUsersEditUser = "pages/manager/manage/user-edit";
	// anonymous
	public static final String organizationDashboard = "pages/anonymous/organization-dashboard";
	public static final String organizationDashboardGallery = "pages/anonymous/organization-gallery";
	public static final String organizationDashboardMembers = "pages/anonymous/organization-members";
	public static final String register = "pages/anonymous/register";
	public static final String terms = "pages/anonymous/terms";
	public static final String policy = "pages/anonymous/policy";
	// admin
	public static final String manageSensorsFriendlyNames = "pages/admin/manage/sensors/friendly-names";

}
