/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.pageBuilders;

import org.springframework.ui.Model;

import com.craftinggamertom.security.authorization.PageAuthority;

/**
 * 
 * @author Thomas Rokicki
 *
 */
public class FeedBuilder extends PageBuilder {

	public FeedBuilder() {
		super();
	}

	public Model buildPage(Model model) {

		super.buildPage(model);

		return model;
	}

	/**
	 * Alert content is show at the top of the feed page
	 * 
	 * @return String of content
	 */
	@Override
	protected String getAlertContent() {

		String content = super.getAlertContent(); // Gets the super to add onto its alerts
		PageAuthority unverifiedUserAuthority = new PageAuthority("unverified");

		if (unverifiedUserAuthority.grantAccessEqual(userAuthority)) {
			content += "\r\n" + "	<div class=\"col-lg-12\">\r\n"
					+ "		<div class=\"alert alert-warning alert-dismissable\">\r\n"
					+ "			<button aria-hidden=\"true\" data-dismiss=\"alert\" class=\"close\"\r\n"
					+ "				type=\"button\">×</button>\r\n"
					+ "			<p class=\"alert-link text-center\">Your Account is Unverified!</p>\r\n"
					+ "			<p class=\"text-center\">You must wait until a manager approves access to contribute.\r\n"
					+ "				Please contact your team leader so they can manage your account settings.</p>\r\n"
					+ "		</div>\r\n" + "	</div>\r\n" + "\r\n";
		}

		return content;
	}
}
