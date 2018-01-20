/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.pageBuilders;

import java.util.List;

import org.springframework.ui.Model;

/**
 * 
 * @author Thomas Rokicki
 *
 */
public class DashboardGalleryBuilder extends DashboardBuilder {

	public DashboardGalleryBuilder() {
		super();
	}

	@Override
	public Model buildPage(Model model, String organization_url) {

		super.buildPage(model, organization_url);

		model.addAttribute("carousel", getCarouselHTML());

		return model;
	}

	/**
	 * Builds the html for the carousel
	 * 
	 * @return html a string for the gallery carousel
	 */
	private String getCarouselHTML() {
		String html = "";
		boolean authorized = true; // To implement security for private organizations later
		if (authorized) {
			List<String> photos = org.getGallery_photos();
			
			if (photos.isEmpty()) {
				html = "<p>Nothing to Show</p>\r\n";
			} else {
				
				html += "<div class=\"carousel slide\" id=\"carousel2\">\r\n";
				String indicatorsHTML = "<ol class=\"carousel-indicators\">\r\n";
				String imagesHTML = "<div class=\"carousel-inner\">\r\n";

				// Add first - sets the active picture
				indicatorsHTML += "<li data-slide-to=\"" + "0" + "\" data-target=\"#carousel2\" class=\"active\"></li>";
				imagesHTML += "<div class=\"item active\">\r\n" + "									<img alt=\"feed" + "0"
						+ "\" class=\"img-responsive\"\r\n" + "										src=\""
						+ photos.get(0) + "\">\r\n"
						+ "									<div class=\"carousel-caption\"></div>\r\n"
						+ "								</div>";
				
				// Add the rest
				for (int i = 1; i < photos.size(); i++) {
					indicatorsHTML += "<li data-slide-to=\"" + i + "\" data-target=\"#carousel2\" class=\"\"></li>";
					imagesHTML += "<div class=\"item\">\r\n" + "									<img alt=\"feed" + i
							+ "\" class=\"img-responsive\"\r\n" + "										src=\""
							+ photos.get(i) + "\">\r\n"
							+ "									<div class=\"carousel-caption\"></div>\r\n"
							+ "								</div>";
				}

				imagesHTML += "</div>\r\n";
				indicatorsHTML += "</ol>\r\n";

				html += indicatorsHTML;
				html += imagesHTML;

				html += "						<a data-slide=\"prev\" href=\"#carousel2\"\r\n"
						+ "								class=\"left carousel-control\"> <span class=\"icon-prev\"></span>\r\n"
						+ "							</a> <a data-slide=\"next\" href=\"#carousel2\"\r\n"
						+ "								class=\"right carousel-control\"> <span class=\"icon-next\"></span>\r\n"
						+ "							</a>\r\n" + "						</div>\r\n";
			}
		} else {
			html = "<p>Nothing to Show</p>\r\n";
		}

		return html;

	}

}
