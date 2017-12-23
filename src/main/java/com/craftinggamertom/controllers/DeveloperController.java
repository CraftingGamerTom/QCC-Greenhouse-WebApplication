/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.controllers;

import java.io.InputStream;
import java.util.Scanner;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev")
public class DeveloperController {

	@RequestMapping(value = "/version")
	public String versionPage() {
		String fileName = "version.txt";
		StringBuilder result = new StringBuilder("");

		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(fileName);

		Scanner scanner = new Scanner(stream);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			result.append(line).append("\n");
		}

		scanner.close();

		return result.toString();

	}

}