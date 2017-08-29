package com.craftinggamertom.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads in the configurations on Boot.
 * 
 * @author Thomas Rokicki
 *
 */
public class ConfigurationReader {

	// Config file name here
	private File configLocation = new File("src/main/webapp/WEB-INF/config/database.cfg");

	// Public tags
	public static String databaseIP;
	public static int databasePort;
	public static String databaseName; 
	

	/**
	 * Loads the configuration file when created.
	 */
	public ConfigurationReader() {

	}

	/**
	 * Reads in the configuration file.
	 */
	public void read() {
		try {
			// Loads File then Loads the properties
			FileReader reader = new FileReader(configLocation);
			Properties properties = new Properties();
			properties.load(reader);

			// Sets the configurations - Add properties here
			databaseIP = properties.getProperty("databaseIP");
			databasePort = Integer.parseInt(properties.getProperty("databasePort"));
			databaseName = properties.getProperty("databaseName");
			
			System.out.println("ConfigurationReader: Configuration Read / Updated");
		} catch (FileNotFoundException e) {
			System.out.println("*** Error while attempting to load configuration file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("*** Error while attempting to load configuration file");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("*** CAUGHT ERROR while loading configuration file");
			e.printStackTrace();
		}

	}

	/**
	 * Refreshes the data stored on boot when called
	 */
	public void refresh() {
		read();
		System.out.println("Refreshed Configuration");
	}

	/**
	 * Verifies the stored data by printing out the data in the console.
	 * Developer MUST update tags when config tags are changed.
	 */
	public void verify() {
		System.out.println("** CONFIGURATION VISUAL VERIFICATION **\n*");
		
		System.out.print("* Database IP: ");
		System.out.println(databaseIP);
		
		System.out.print("* Database Port: ");
		System.out.println(databasePort);
		
		System.out.print("* Database Name: ");
		System.out.println(databaseName);
		
		System.out.println("*\n** END CONFIGURATION VISUAL VERIFICATION **");
	}
}