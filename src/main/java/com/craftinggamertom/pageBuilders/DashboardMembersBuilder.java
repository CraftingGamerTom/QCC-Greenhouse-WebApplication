/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.pageBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.security.authentication.AppUser;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author Thomas Rokicki
 *
 */
public class DashboardMembersBuilder extends DashboardBuilder {

	private List<AppUser> userList;

	public DashboardMembersBuilder() {
		super();
	}

	public Model buildPage(Model model, String organization_url) {

		super.buildPage(model, organization_url);

		setUserList();
		model.addAllAttributes(addMemberAttributes());

		return model;
	}

	private void setUserList() {
		// Will need to be updated to sort based on organization
		userList = new ArrayList<AppUser>();
		MongoCollection<Document> collection = null;
		collection = database.getCollection(ConfigurationReaderSingleton.getAppUserCollection());
		FindIterable<Document> userDocuments = collection.find();

		Iterator<Document> docIter = userDocuments.iterator();
		while (docIter.hasNext()) {
			userList.add(new AppUser(docIter.next()));
		}

	}

	private Map<String, String> addMemberAttributes() {
		String memberHTML = "";
		String unverifiedHTML = "";
		boolean hasUnverified = false;
		Map<String, String> map = new HashMap<String, String>();

		if (userList.isEmpty()) {
			memberHTML += "<p>Nothing to Show</p>";
		} else {
			// top of table html
			memberHTML += "<table class=\"footable table table-stripped toggle-arrow-tiny\"\r\n"
					+ "						data-page-size=\"15\">\r\n" + "						<thead>\r\n"
					+ "							<tr>\r\n"
					+ "								<th data-sort-ignore=\"true\"></th>\r\n"
					+ "								<th data-sort-ignore=\"true\"></th>\r\n"
					+ "								<th data-hide=\"phone\" data-sort-ignore=\"true\"></th>\r\n"
					+ "							</tr>\r\n" + "						</thead>\r\n"
					+ "						<tbody>\r\n";
			// top of table html
			unverifiedHTML += "<table class=\"footable table table-stripped toggle-arrow-tiny\"\r\n"
					+ "						data-page-size=\"15\">\r\n" + "						<thead>\r\n"
					+ "							<tr>\r\n"
					+ "								<th data-sort-ignore=\"true\"></th>\r\n"
					+ "								<th data-sort-ignore=\"true\"></th>\r\n"
					+ "							</tr>\r\n" + "						</thead>\r\n"
					+ "						<tbody>\r\n";

			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getAuthority_key().equals("unverified")) {
					hasUnverified = true;
					unverifiedHTML += "														<tr>\r\n"
							+ "								<td><img alt=\"image\" class=\"img-sm\"\r\n"
							+ "									style=\"width: 18px; height: 18px;\"\r\n"
							+ "									src=\"" + userList.get(i).getPicture() + "\" />\r\n"
							+ "								</td>\r\n" + "								<td><a\r\n"
							+ "									href=\"/user/profile?dbid="
							+ userList.get(i).getDatabaseId() + "\">" + userList.get(i).getName() + "</a></td>\r\n"
							+ "							</tr>";
				} else {
					memberHTML += "                           </tr>\r\n"
							+ "														<tr>\r\n"
							+ "								<td><img alt=\"image\" class=\"img-sm\"\r\n"
							+ "									style=\"width: 18px; height: 18px;\"\r\n"
							+ "									src=\"" + userList.get(i).getPicture() + "\" />\r\n"
							+ "								</td>\r\n" + "								<td><a\r\n"
							+ "									href=\"/user/profile?dbid="
							+ userList.get(i).getDatabaseId() + "\">" + userList.get(i).getName() + "</a></td>\r\n"
							+ "								<td>" + userList.get(i).getAuthority_key() + "</td>\r\n"
							+ "							</tr>";
				}
			}

			// If there are no unverified members - adds a string
			if (!hasUnverified) {
				// overwrites the table HTML that was previously added as well
				unverifiedHTML = "<p>Nothing to Show</p>";
			}
			// Bottom of table html
			memberHTML += "						</tbody>\r\n" + "						<tfoot>\r\n"
					+ "							<tr>\r\n" + "								<td colspan=\"6\">\r\n"
					+ "									<ul class=\"pagination pull-right\"></ul>\r\n"
					+ "								</td>\r\n" + "							</tr>\r\n"
					+ "						</tfoot>\r\n" + "					</table>\r\n";

			// Bottom of table html
			unverifiedHTML += "						</tbody>\r\n" + "						<tfoot>\r\n"
					+ "							<tr>\r\n" + "								<td colspan=\"6\">\r\n"
					+ "									<ul class=\"pagination pull-right\"></ul>\r\n"
					+ "								</td>\r\n" + "							</tr>\r\n"
					+ "						</tfoot>\r\n" + "					</table>\r\n";

		}

		map.put("member-list", memberHTML);
		map.put("unverified-member-list", unverifiedHTML);
		return map;
	}

}
