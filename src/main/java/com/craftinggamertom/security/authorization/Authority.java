/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.security.authorization;

import java.util.Map;
import java.util.TreeMap;

import com.craftinggamertom.constants.AuthorityLevels;

/**
 * 
 * This object handles authority levels for authorization. This is implemented
 * so that if a new level of authorization is needed it is easy to add.
 * 
 * @author Thomas Rokicki
 *
 */
public class Authority {

	private Map<String, Integer> authLevels = new TreeMap<String, Integer>();
	private int authorityLevel;

	/**
	 * Constructs the TreeMap of authorityLevels. Puts all the authority levels into
	 * a Map
	 */
	public Authority() {
		authLevels.putAll(AuthorityLevels.getAuthorityLevelsAsMap());
	}

	/**
	 * Sets the authority level for it to be compared
	 * 
	 * @param keyword
	 */
	protected void setAuthorityLevel(String keyword) {
		try {
			if (authLevels.containsKey(keyword)) {
				authorityLevel = authLevels.get(keyword);
			} else { // SHOULD NEVER HAPPEN (would be caused by typo)
				System.out.println("INVALID AUTHORITY LEVEL KEYWORD!");
				this.authorityLevel = -1;
			}
		} catch (NullPointerException nullE) {
			System.out.println("(Authority) NullPointerException: No keyword was found. Is the database running?");
			setAuthorityLevel("anonymous"); // calls self to set the authority level
		}
	}

	/**
	 * Gets the authority level. This is private because the authority levels are
	 * meant to be scalable so the number represented in this object should never be
	 * directly referenced for authorization.
	 * 
	 * @return the authorityLevel
	 */
	protected int getAuthorityLevel() {
		return authorityLevel;
	}

	/**
	 * The map contains all the authority levels and their corresponding integer
	 * levels.
	 * 
	 * @return A Map with key value pair <"AuthLevel", level>
	 */
	public Map<String, Integer> getLevelsList() {
		return authLevels;
	}

}
