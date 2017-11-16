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
	public boolean grantAccessTo(UserAuthority userAuthority) {
		boolean grantAccess = false;
		if (userAuthority.getAuthorityLevel() >= getAuthorityLevel()) {
			grantAccess = true;
		}

		return grantAccess;
	}
}
