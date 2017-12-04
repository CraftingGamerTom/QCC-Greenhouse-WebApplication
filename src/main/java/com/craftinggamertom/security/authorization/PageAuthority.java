package com.craftinggamertom.security.authorization;

public class PageAuthority extends Authority{

	/**
	 * Sets the authority
	 * @param keyword
	 */
	public PageAuthority(String keyword) {
		setAuthorityLevel(keyword);
	}

	/**
	 * Checks if the Authority level is greater than or equal to the authority level of the page.
	 * 
	 * @param userAuthority
	 * @return true if the authority passed in is equal to or greater than the
	 *         authority of the page
	 */
	public boolean grantAccessGTE(UserAuthority userAuthority) {
		boolean grantAccess = false;
		if (userAuthority.getAuthorityLevel() >= getAuthorityLevel()) {
			grantAccess = true;
		}

		return grantAccess;
	}
	
	/**
	 * Checks if the Authority level is less than or equal to the authority level of the page.
	 * 
	 * 
	 * @param userAuthority
	 * @return true if the authority passed in is equal to or less than the
	 *         authority of the page
	 */
	public boolean grantAccessLTE(UserAuthority userAuthority) {
		boolean grantAccess = false;
		if (userAuthority.getAuthorityLevel() <= getAuthorityLevel()) {
			grantAccess = true;
		}

		return grantAccess;
	}
	
	/**
	 * Checks if the Authority level is equal to the authority level of the page.
	 * 
	 * 
	 * @param userAuthority
	 * @return true if the authority passed in is equal to or less than the
	 *         authority of the page
	 */
	public boolean grantAccessEqual(UserAuthority userAuthority) {
		boolean grantAccess = false;
		if (userAuthority.getAuthorityLevel() == getAuthorityLevel()) {
			grantAccess = true;
		}

		return grantAccess;
	}
}
