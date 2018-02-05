/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.pageBuilders;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.craftinggamertom.constants.AuthorityLevels;
import com.craftinggamertom.entity.Organization;
import com.craftinggamertom.security.authorization.PageAuthority;

/**
 * 
 * @author Thomas Rokicki
 *
 */
public class DashboardBuilder extends PageBuilder {

	protected Organization org;

	public DashboardBuilder(String organization_url) {
		super(organization_url);
	}

	public Model buildPage(Model model) {

		super.buildPage(model);

		setOrganization(new Organization(organization_url)); // Get the Organization
		model.addAllAttributes(getDashboardAttributes());

		return model;
	}

	private Map<String, String> getDashboardAttributes() {
		Map<String, String> map = new HashMap<String, String>();

		// Organization Card
		map.put("org-name", org.getName());
		map.put("org-header", org.getHeader());
		map.put("org-profile-picture", org.getProfilePicture());
		map.put("org-member-count", org.getMember_count());
		map.put("org-sensor-count", org.getSensor_count());
		map.put("org-plant-count", org.getPlant_count());
		map.put("org-date-created", org.getDate_created());

		// Organization Information
		map.put("org-address", org.getAddress());
		map.put("org-phone-number", org.getPhone_number());
		map.put("org-website", org.getWebsite());

		return map;

	}

	/**
	 * Alert content is show at the top of the feed page
	 * 
	 * @return String of content
	 */
	@Override
	protected String getAlertContent() {

		String content = super.getAlertContent(); // Gets the super to add onto its alerts

		if (pageAuthority.grantAccessEqual(userAuthority, AuthorityLevels.UNVERIFIED)) {
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

	protected Organization getOrganization() {
		return org;
	}

	private void setOrganization(Organization orgObject) {
		this.org = orgObject;
	}
}
