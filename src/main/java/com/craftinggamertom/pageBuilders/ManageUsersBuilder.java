/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.pageBuilders;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.security.authentication.AppUser;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

/**
 * @author Thomas Rokicki
 *
 */
public class ManageUsersBuilder extends PageBuilder {

	public ManageUsersBuilder() {
		super();
	}

	/**
	 * Gets the attributes for the page and returns the model.
	 * 
	 * @return the model for the page
	 */
	public Model buildPage(Model model) {

		super.buildPage(model);

		this.model.addAttribute("users-chart", getChartContent()); // Alert Container just below the navigation bar

		return model;
	}

	private String getChartContent() {

		PageAuthority adminUserAuthority = new PageAuthority("admin");

		String chart = "";

		try {
			ArrayList<AppUser> allUsers = new ArrayList<AppUser>();

			MongoCollection<Document> collection = null;
			collection = database.getCollection(ConfigurationReaderSingleton.getAppUserCollection());

			chart = "<div class=\"row\">\r\n" + "                <div class=\"col-lg-12\">\r\n"
					+ "                    <div class=\"ibox\">\r\n"
					+ "                        <div class=\"ibox-content\">\r\n" + "\r\n"
					+ "                            <table class=\"footable table table-stripped toggle-arrow-tiny\" data-page-size=\"15\">\r\n"
					+ "                                <thead>\r\n" + "                                <tr>\r\n"
					+ "\r\n" + "                                    <th data-toggle=\"true\">Name</th>\r\n"
					+ "                                    <th data-hide=\"phone,tablet\">Authority</th>\r\n"
					+ "                                    <th data-hide=\"phone,tablet\">Join Date</th>\r\n"
					+ "                                    <th data-hide=\"phone,tablet\">Last Seen</th>\r\n"
					+ "                                    <th data-hide=\"all\"># of Observations</th>\r\n"
					+ "                                    <th data-hide=\"all\"># of Updates</th>\r\n"
					+ "                                    <th data-hide=\"all\">Cell Phone</th>\r\n"
					+ "                                    <th data-hide=\"all\">Email</th>\r\n"
					+ "                                    <th data-hide=\"all\">Id#</th>\r\n"
					+ "                                    <th data-hide=\"all\">Google Id#</th>\r\n"
					+ "                                    <th data-hide=\"all\">Photo</th>\r\n";

			if (adminUserAuthority.grantAccessGTE(userAuthority)) { // Adds column for admin actions
				chart += "                                    <th class=\"text-right\" data-sort-ignore=\"true\">Action</th>\r\n"
						+ "\r\n" + "                                </tr>\r\n";
			}

			chart += "                                </thead>\r\n" + "                                <tbody>";

			FindIterable<Document> userDocuments = collection.find();

			Iterator<Document> docIter = userDocuments.iterator();
			while (docIter.hasNext()) {
				allUsers.add(new AppUser(docIter.next()));
			}
			for (int i = 0; i < allUsers.size(); i++) {
				AppUser theUser = allUsers.get(i);

				chart += "                                <tr>\r\n" + "                                    <td>\r\n"
						+ "                                        " + theUser.getName()
						+ "                                    </td>\r\n"
						+ "                                    <td>\r\n" + "                                        "
						+ theUser.getAuthority_key() + "                                    </td>\r\n"
						+ "                                    <td>\r\n" + "                                        "
						+ theUser.getJoin_date() + "                                    </td>\r\n"
						+ "                                    <td>\r\n" + "                                        "
						+ theUser.getLast_seen() + "                                    </td>\r\n"
						+ "                                    <td>\r\n" + "                                        "
						+ theUser.getNum_of_observations() + "                                    </td>\r\n"
						+ "                                    <td>\r\n" + "                                        "
						+ theUser.getNum_of_updates() + "                                    </td>\r\n"
						+ "                                    <td>\r\n" + "                                        "
						+ theUser.getCell_phone() + "                                    </td>\r\n"
						+ "                                    <td>\r\n" + "                                        "
						+ theUser.getEmail_address() + "                                    </td>\r\n"
						+ "                                    <td>\r\n" + "                                        "
						+ theUser.getDatabaseId() + "                                    </td>\r\n"
						+ "                                    <td>\r\n" + "                                        "
						+ theUser.getId() + "                                    </td>\r\n"
						+ "                                    <td>\r\n" + "                                        "
						+ "<img alt=\"image\" class=\"img-sm\" style=\"width:18px;height:18px;\" src=\""
						+ theUser.getPicture() + "\" />\r\n" + "                                    </td>\r\n";

				if (adminUserAuthority.grantAccessGTE(userAuthority)) { // Adds admin actions content
					chart += "                                    <td class=\"text-right\">\r\n"
							+ "                                        <div class=\"btn-group\">\r\n"
							+ "                                            	    <button onclick=\"window.location.href='/view/profile/user?"
							+ theUser.getDatabaseId() + "'\" class=\"btn-success btn btn-xs\">Profile</button>\r\n"
							+ "                                            	    <button onclick=\"window.location.href='/admin/manage/users/user?"
							+ theUser.getDatabaseId() + "'\" class=\"btn-warning btn btn-xs\">Edit</button>\r\n"
							+ "                                                 <button class=\"btn-danger btn btn-xs press-delete\">Delete</button>\r\n"
							+ "                                        </div>\r\n"
							+ "                                    </td>\r\n" + "                                </tr>";
				}
			}

			// Closes chart
			chart += "\r\n" + "                                </tbody>\r\n"
					+ "                                <tfoot>\r\n" + "                                <tr>\r\n"
					+ "                                    <td colspan=\"6\">\r\n"
					+ "                                        <ul class=\"pagination pull-right\"></ul>\r\n"
					+ "                                    </td>\r\n" + "                                </tr>\r\n"
					+ "                                </tfoot>\r\n" + "                            </table>\r\n"
					+ "\r\n" + "                        </div>\r\n" + "                    </div>\r\n"
					+ "                </div>\r\n" + "            </div>";
		} catch (NullPointerException nullE) {
			chart = "It's lonely here. There are no users or database is offline."; // A container explaining there are
																					// no users or the database is
																					// offline
		}

		return chart;

	}

}
