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
import org.bson.conversions.Bson;
import org.springframework.ui.Model;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.database.SensorSet;
import com.craftinggamertom.entity.Plant;
import com.craftinggamertom.entity.Sensor;
import com.craftinggamertom.security.authentication.AppUser;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

/**
 * 
 * @author Thomas Rokicki
 *
 */
public class DashboardInfoBuilder extends DashboardBuilder {

	Map<String, Sensor> sensorMap;
	Map<String, Double> liveDataMap;
	List<String> sensorIds;

	Map<String, Plant> plantMap;
	List<String> plantIds;

	public DashboardInfoBuilder() {
		super();
	}

	@Override
	public Model buildPage(Model model, String organization_url) {

		super.buildPage(model, organization_url);

		setSensorMap();
		setPlantMap();
		model.addAttribute("plant-list", getPlantListHTML());
		model.addAttribute("sensors-list", getSensorListHTML());

		return model;
	}

	/**
	 * Gets the sensors that are set to be visible and puts them in a map to be
	 * listed
	 */
	private void setSensorMap() {
		sensorMap = new HashMap<String, Sensor>();
		liveDataMap = new HashMap<String, Double>();
		sensorIds = new ArrayList<String>();

		MongoCollection<Document> liveDataCollection = null;
		MongoCollection<Document> namesCollection = null;

		namesCollection = database.getCollection(ConfigurationReaderSingleton.getSensorNameCollection());
		liveDataCollection = database.getCollection(ConfigurationReaderSingleton.getLiveDataCollection());

		try {

			FindIterable<Document> searchResult;
			FindIterable<Document> valuesResult;

			Bson isVisibleFilter = Filters.eq("isVisible", true);
			searchResult = namesCollection.find(isVisibleFilter);
			valuesResult = liveDataCollection.find();

			// Gather All Sensors
			Iterator<Document> namesIter = searchResult.iterator();
			while (namesIter.hasNext()) {
				Document sensorDoc = namesIter.next();
				Sensor sensor = new Sensor(sensorDoc);
				sensorMap.put(sensor.getSensorId(), sensor);
				sensorIds.add(sensor.getSensorId());
			}

			// Gather all Live Data
			Iterator<Document> valuesIter = valuesResult.iterator();
			while (valuesIter.hasNext()) {
				Document valuesDoc = valuesIter.next();
				liveDataMap.put(valuesDoc.getString("sensorId").toString(), valuesDoc.getDouble("value"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the plants that are labeled as alive and puts them in a map to be listed
	 * in html
	 */
	private void setPlantMap() {
		plantMap = new HashMap<String, Plant>();
		plantIds = new ArrayList<String>();

		MongoCollection<Document> plantCollection = null;

		plantCollection = database.getCollection(ConfigurationReaderSingleton.getPlantCollection());

		try {

			FindIterable<Document> plantDocResults;

			Bson isAliveFilter = Filters.eq("is_alive", true);
			plantDocResults = plantCollection.find(isAliveFilter);

			// Gather All Sensors
			Iterator<Document> plantIter = plantDocResults.iterator();
			while (plantIter.hasNext()) {
				Document plantDoc = plantIter.next();
				Plant plant = new Plant(plantDoc);
				plantMap.put(plantDoc.getObjectId("_id").toString(), plant);
				plantIds.add(plantDoc.getObjectId("_id").toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getPlantListHTML() {
		String html = "";

		if (plantMap.isEmpty()) {
			html = "<p>Nothing to Show</p>";
		} else {

			// top of table html
			html = "<table class=\"footable table table-stripped toggle-arrow-tiny\"\r\n"
					+ "						data-page-size=\"15\">\r\n" + "						<thead>\r\n"
					+ "							<tr>\r\n"
					+ "								<th data-sort-ignore=\"true\"></th>\r\n"
					+ "								<th data-sort-ignore=\"true\"></th>\r\n"
					+ "								<th data-hide=\"phone\" data-sort-ignore=\"true\"></th>\r\n"
					+ "							</tr>\r\n" + "						</thead>\r\n"
					+ "						<tbody>\r\n";

			for (int i = 0; i < plantIds.size(); i++) {
				html += "														<tr>\r\n"
						+ "								<td><a\r\n"
						+ "									href=\"/plants/plant?dbid="
						+ plantMap.get(plantIds.get(i)).getDatabaseId() + "\">"
						+ plantMap.get(plantIds.get(i)).getName() + "</a></td>\r\n"
						+ "								<td>" + plantMap.get(plantIds.get(i)).getPlant_type()
						+ "</td>\r\n" + "							</tr>" + "								<td>"
						+ plantMap.get(plantIds.get(i)).getDate_created() + "</td>\r\n"
						+ "							</tr>";
			}

			// Bottom of table html
			html += "						</tbody>\r\n" + "						<tfoot>\r\n"
					+ "							<tr>\r\n" + "								<td colspan=\"6\">\r\n"
					+ "									<ul class=\"pagination pull-right\"></ul>\r\n"
					+ "								</td>\r\n" + "							</tr>\r\n"
					+ "						</tfoot>\r\n" + "					</table>\r\n";
		}
		return html;
	}

	private String getSensorListHTML() {
		String html = "";

		if (sensorMap.isEmpty()) {
			html = "<p>Nothing to Show</p>";
		} else {
			String widgetColor;
			String widgetIcon;

			for (int i = 0; i < sensorIds.size(); i++) {

				// TODO Implement range
				// // Range is default to 1000. User cannot set the range outside of -999 and
				// 999
				// if (sensorMap.get(sensorIds.get(i)).getRangeMin() == 1000
				// || sensorMap.get(sensorIds.get(i)).getRangeMax() == 1000) {
				// widgetColor = "warning";
				// widgetIcon = "fa-warning";
				// } else if (liveDataMap.get(sensorIds.get(i)) >
				// sensorMap.get(sensorIds.get(i)).getRangeMin()
				// && liveDataMap.get(sensorIds.get(i)) <
				// sensorMap.get(sensorIds.get(i)).getRangeMax()) {
				// widgetColor = "navy";
				// widgetIcon = "fa-smile-o";
				// } else {
				widgetColor = "yellow";
				widgetIcon = "fa-exclamation-circle";
				// }

				html += "                  <div class=\"widget style1 " + widgetColor + "-bg\">\r\n"
						+ "						<div class=\"row\">\r\n"
						+ "							<div class=\"col-xs-2\">\r\n"
						+ "								<i class=\"fa " + widgetIcon + " fa-2x \"></i>\r\n"
						+ "							</div>\r\n"
						+ "							<div class=\"col-xs-6 text-left\">\r\n"
						+ "								<h3><strong>" + sensorMap.get(sensorIds.get(i)).getFriendlyName()
						+ "</strong></h3>\r\n" + "							</div>\r\n"
						+ "							<div class=\"col-xs-4 text-right\">\r\n"
						+ "								<h3>\r\n" + "									<strong>"
						+ liveDataMap.get(sensorIds.get(i)) + "</strong>\r\n"
						+ "								</h3>\r\n" + "							</div>\r\n"
						+ "						</div>\r\n" + "					</div>\r\n";
			}
		}

		return html;
	}

}
