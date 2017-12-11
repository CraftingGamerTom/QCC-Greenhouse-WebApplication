/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

import java.io.FileReader;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev")
public class DeveloperController {

	@RequestMapping(value = "/version")
	public String versionPage() {
		try {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(new FileReader("pom.xml"));

			return model.getVersion();
			// System.out.println(model.getId());
			// System.out.println(model.getGroupId());
			// System.out.println(model.getArtifactId());
			// System.out.println(model.getVersion());

		} catch (Exception e) {
			System.out.println("Fatal Error whilst obtaining App Version. Printing Stacktrace.");
			e.printStackTrace();
		}

		return "version not set";

	}

}