package com.craftinggamertom.security.authorization;

/**
 * This object handles authority levels for authorization. This is implemented
 * so that if a new level of authorization is needed it is easy to add.
 * 
 * @author Thomas Rokicki
 *
 */
public class Authority {

	private int authorityLevel;

	/**
	 * Constructor
	 */
	public Authority() {
	}

	/**
	 * sets the authority level for it to be compared
	 * 
	 * @param keyword
	 */
	protected void setAuthorityLevel(String keyword) {
		if (keyword.equals("anonymous")) {
			this.authorityLevel = 0;
		} else if (keyword.equals("unverified")) {
			this.authorityLevel = 1;
		} else if (keyword.equals("user")) {
			this.authorityLevel = 2;
		} else if (keyword.equals("manager")) {
			this.authorityLevel = 3;
		} else if (keyword.equals("admin")) {
			this.authorityLevel = 4;
		} else if (keyword.equals("developer")) {
			this.authorityLevel = 5;
		} else { // SHOULD NEVER HAPPEN (would be caused by typo)
			System.out.println("INVALID AUTHORITY LEVEL KEYWORD!");
			this.authorityLevel = -1;
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

}
