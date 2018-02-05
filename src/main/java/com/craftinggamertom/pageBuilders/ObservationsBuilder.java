/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.pageBuilders;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.ui.Model;

import com.craftinggamertom.constants.AuthorityLevels;
import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.entity.Observation;
import com.craftinggamertom.security.authorization.PageAuthority;
import com.github.rjeschke.txtmark.Processor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class ObservationsBuilder extends PageBuilder {

	private ZonedDateTime startDate;
	private ZonedDateTime endDate;

	public ObservationsBuilder(String organization_url) {
		super(organization_url);
	}

	public Model buildPage(Model model, String startDateString, String endDateString) {

		super.buildPage(model);

		// Convert dates to ZonedDateTime
		if (startDateString.equals("default")) {
			this.startDate = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).minusDays(7);
		} else {
			this.startDate = ZonedDateTime
					.parse(startDateString + "T00:00:00.000" + userAuthority.getUser().getTime_zone())
					.withZoneSameInstant(ZoneOffset.UTC);
		}
		if (endDateString.equals("default")) {
			this.endDate = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
		} else {
			this.endDate = ZonedDateTime.parse(endDateString + "T23:59:59.999" + userAuthority.getUser().getTime_zone())
					.withZoneSameInstant(ZoneOffset.UTC);
		}

		// Set all the page attributes
		model.addAttribute("observations", getObservations());
		model.addAttribute("post-observation-button", getNewObservationButton());

		// Converts back to the users time zone and cuts down the ZonedDateTime just to
		// the date portion for the range picker to be populated
		model.addAttribute("start-date-value", startDate
				.withZoneSameInstant(
						ZoneId.ofOffset("", ZoneOffset.of(userAuthority.getUser().getTime_zone().substring(0, 6))))
				.toString().substring(0, 10));
		model.addAttribute("end-date-value",
				endDate.withZoneSameInstant(
						ZoneId.ofOffset("", ZoneOffset.of(userAuthority.getUser().getTime_zone().substring(0, 6))))
						.toString().substring(0, 10));

		return model;
	}

	private String getNewObservationButton() {
		String html = "";

		if (pageAuthority.grantAccessGTE(userAuthority, AuthorityLevels.USER)) {
			html = "<button class=\"btn btn-block btn-primary pull-right\"\r\n"
					+ "					data-toggle=\"modal\" data-target=\"#newObservation\">Post New\r\n"
					+ "					Observation</button>\r\n";
		}
		return html;
	}

	private String getObservations() {
		String html = "<div class=\"well text-center\">\r\n" + "					<p>Nothing to Show</p>\r\n"
				+ "				</div>\r\n";
		try {
			MongoCollection<Document> organizationCollection = null;
			organizationCollection = database.getCollection(ConfigurationReaderSingleton.getObservationCollection());

			FindIterable<Document> observationResults;
			Map<String, List<Observation>> observationMap = new TreeMap<String, List<Observation>>(
					Collections.reverseOrder());

			Bson orgFilter = Filters.eq("organization_url", organization_url);
			Bson startFilter = Filters.gte("observation_date", startDate.toString());
			Bson endFilter = Filters.lte("observation_date", endDate.toString());
			;
			Bson dateFilter = Filters.and(startFilter, endFilter);

			Bson searchFilter = Filters.and(orgFilter, dateFilter);
			observationResults = organizationCollection.find(searchFilter);

			// Gather All Sensors
			Iterator<Document> observationIter = observationResults.iterator();
			while (observationIter.hasNext()) {
				Document observationDoc = observationIter.next();
				Observation observation = new Observation(observationDoc);

				List<Observation> obList = observationMap.get(observation.getObservation_date());
				if (obList == null) {
					List<Observation> listOfObservations = new ArrayList<Observation>();

					listOfObservations.add(observation);
					observationMap.put(observation.getObservation_date(), listOfObservations);
				} else {
					obList.add(observation);
					observationMap.put(observation.getObservation_date(), obList);
				}

			}

			if (!observationMap.isEmpty()) {
				html = "<div class=\"clients-list\" style=\"margin-bottom: 30px\">\r\n"
						+ "				<div class=\"tab-content\">\r\n"
						+ "					<div id=\"tab-1\" class=\"tab-pane active\">\r\n"
						+ "						<div class=\"full-height-scroll\">\r\n"
						+ "							<ul class=\"agile-list\" id=\"observation-list-1\"\r\n"
						+ "								style=\"margin-right: 10px\">\r\n";

				Collection<List<Observation>> collectionOfObservations = observationMap.values();
				Iterator<List<Observation>> i = collectionOfObservations.iterator();

				while (i.hasNext()) {
					List<Observation> oList = i.next();

					for (Observation o : oList) {

						// Set the observation time String
						ZonedDateTime observationDate = ZonedDateTime.parse(o.getObservation_date())
								.withZoneSameInstant(ZoneId.of(userAuthority.getUser().getTime_zone().substring(0, 6)));
						String oDate = DateTimeFormatter.ofPattern("MMM d").format(observationDate);
						String oYear = DateTimeFormatter.ofPattern("yy").format(observationDate);
						String oTime = DateTimeFormatter.ofPattern("hh:mm a").format(observationDate);

						// Set the posted time String
						ZonedDateTime postDate = ZonedDateTime.parse(o.getPost_date())
								.withZoneSameInstant(ZoneId.of(userAuthority.getUser().getTime_zone().substring(0, 6)));
						String pDate = DateTimeFormatter.ofPattern("MMM d").format(postDate);
						String pYear = DateTimeFormatter.ofPattern("yy").format(postDate);
						String pTime = DateTimeFormatter.ofPattern("hh:mm a").format(postDate);

						// Set the priority alert color
						String alertColor = "info";
						if (o.getPriority().equals("high")) {
							alertColor = "danger";
						} else if (o.getPriority().equals("medium")) {
							alertColor = "warning";
						} else if (o.getPriority().equals("low")) {
							alertColor = "success";
						}

						// Set the observed date
						String observedDate = "observed " + oDate + " '" + oYear + " at " + oTime;
						String postedDate = "posted " + pDate + " '" + pYear + " at " + pTime;

						html += "<input type=\"hidden\" id=\"" + o.getDatabase_id() + "-priority\" value=\""
								+ o.getPriority() + "\">\r\n" + "<input type=\"hidden\" id=\"" + o.getDatabase_id()
								+ "-color\" value=\"" + alertColor + "\">\r\n" + "<input type=\"hidden\" id=\""
								+ o.getDatabase_id() + "-title\" value=\"" + o.getTitle() + "\">\r\n"
								+ "<input type=\"hidden\" id=\"" + o.getDatabase_id() + "-odate\" value=\""
								+ observedDate + "\">\r\n" + "<input type=\"hidden\" id=\"" + o.getDatabase_id()
								+ "-pdate\" value=\"" + postedDate + "\">\r\n" + "<input type=\"hidden\" id=\""
								+ o.getDatabase_id() + "-body\" value=\"" + Processor.process(o.getBody()) + "\">\r\n";

						html += "                               <li class=\"" + alertColor
								+ "-element\" onclick=\"openViewObservation(\'" + o.getDatabase_id() + "\')\">\r\n"
								+ "									<p>\r\n"
								+ "										<strong>" + o.getTitle() + "</strong>\r\n"
								+ "									</p>\r\n"
								+ "									<div class=\"agile-detail\">\r\n"
								+ "										<a href=\"/user/profile?dbid="
								+ o.getPosted_by_id() + "\" class=\"pull-right btn btn-xs btn-white\">"
								+ o.getPosted_by() + "</a>\r\n" + observedDate + "\r\n"
								+ "									</div>\r\n"
								+ "								</li>\r\n";

					}

				}
				html += "							</ul>\r\n" + "						</div>\r\n"
						+ "					</div>\r\n" + "				</div>\r\n" + "			</div>\r\n";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}

}
