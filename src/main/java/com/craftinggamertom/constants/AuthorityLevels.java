/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Uniform place to story all AuthorityLevels for easy modification
 * 
 * @author Thomas Rokicki
 *
 */
public class AuthorityLevels {

	private static Map<String, Integer> authLevelsMap = new TreeMap<String, Integer>(); // Singleton

	public static final String ANONYMOUS = "anonymous";
	public static final String UNVERIFIED = "unverified";
	public static final String USER = "user";
	public static final String MANAGER = "manager";
	public static final String ADMIN = "admin";
	public static final String DEVELOPER = "developer";

	/**
	 * Singleton method that returns a Map of the authority levels for checking
	 * against the users authorization
	 * 
	 * @return
	 */
	public static Map<String, Integer> getAuthorityLevelsAsMap() {

		if (authLevelsMap.isEmpty()) {
			authLevelsMap.put("anonymous", 1);
			authLevelsMap.put("unverified", 2);
			authLevelsMap.put("user", 3);
			authLevelsMap.put("manager", 4);
			authLevelsMap.put("admin", 5);
			authLevelsMap.put("developer", 6);
		}
		return authLevelsMap;
	}
}
