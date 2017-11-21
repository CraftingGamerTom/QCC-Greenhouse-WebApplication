package com.craftinggamertom.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReaderSingleton {

	private static ConfigurationReaderSingleton instance = null; // Singleton

	private ConfigurationReaderSingleton() {
	}

	private String databaseIP;
	private int databasePort;

	private String databaseName;
	private String sensorNamesCollection;
	private String liveDataCollection;
	private String rawDataCollection;
	private String hourlyDataCollection;
	private String dailyDataCollection;
	private String weeklyDataCollection;
	private String monthlyDataCollection;
	private String yearlyDataCollection;
	
	private String appUserCollection;

	private String domainName;

	private static ConfigurationReaderSingleton getInstance() {
		if (instance == null) {
			instance = new ConfigurationReaderSingleton();
			instance.read();
		}
		return instance;
	}

	/**
	 * readed the configuration file always done in singleton instance
	 */
	private void read() {
		
		// Config file name here
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("database.cfg");
		
		try {
			// Loads File then Loads the properties
			Properties properties = new Properties();
			properties.load(stream);

			// Sets the configurations - Add properties here
			databaseIP = properties.getProperty("databaseIP");
			databasePort = Integer.parseInt(properties.getProperty("databasePort"));

			databaseName = properties.getProperty("databaseName");
			sensorNamesCollection = properties.getProperty("sensorNamesCollection");
			liveDataCollection = properties.getProperty("liveDataCollection");
			rawDataCollection = properties.getProperty("rawDataCollection");
			hourlyDataCollection = properties.getProperty("hourlyDataCollection");
			dailyDataCollection = properties.getProperty("dailyDataCollection");
			weeklyDataCollection = properties.getProperty("weeklyDataCollection");
			monthlyDataCollection = properties.getProperty("monthlyDataCollection");
			yearlyDataCollection = properties.getProperty("yearlyDataCollection");
			
			appUserCollection = properties.getProperty("appUserCollection");

			domainName = properties.getProperty("domainName");

			System.out.println("ConfigurationReader: Configuration Read / Updated");
		} catch (FileNotFoundException e) {
			System.out.println("*** Could not find configuration file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("*** IOException while attempting to load configuration file");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("*** Error while attempting to load configuration file");
			e.printStackTrace();
		}
		
		// close stream
		try {
		stream.close();
		}catch(IOException e) {
			System.out.println("Could not close configuration stream. IOException: ");
			e.printStackTrace();
		}
	}

	/**
	 * Re-reads the configuration file (use sparingly - only for testing)
	 */
	public void reset() {
		System.out.println("*** RELOADING CONFIGURATION ***");
		getInstance().read();
		System.out.println("*** FINISHED RELOADING ***");
	}

	/**
	 * Verifies the stored data by printing out the data in the console. Developer
	 * MUST update tags when config tags are changed.
	 */
	public void verify() {
		System.out.println("** CONFIGURATION VISUAL VERIFICATION **\n*");

		System.out.println("* datebaseIP: " + getDatabaseIP());
		System.out.println("* databasePort: " + getDatabasePort());
		
		System.out.println("* databaseName: " + getDatabaseName());
		System.out.println("* sensorNamesCollection: " + getSensorNamesCollection());
		System.out.println("* liveDataCollection: " + getLiveDataCollection());
		System.out.println("* rawDataCollection: " + getRawDataCollection());
		System.out.println("* hourlyDataCollection: " + getHourlyDataCollection());
		System.out.println("* dailyDataCollection: " + getDailyDataCollection());
		System.out.println("* weeklyDataCollection: " + getWeeklyDataCollection());
		System.out.println("* monthlyDataCollection: " + getMonthlyDataCollection());
		System.out.println("* yearlyDataCollection: " + getYearlyDataCollection());
		
		System.out.println("* appUserCollection: " + getAppUserCollection());

		System.out.println("* domainName: " + getDomainName());

		System.out.println("*\n** END CONFIGURATION VISUAL VERIFICATION **");
	}

	// Getters

	public static String getDatabaseIP() {
		return getInstance().getPrivateDatabaseIP();
	}

	public static int getDatabasePort() {
		return getInstance().getPrivateDatabasePort();
	}

	public static String getDatabaseName() {
		return getInstance().getPrivateDatabaseName();
	}

	public static String getSensorNamesCollection() {
		return getInstance().getPrivateSensorNamesCollection();
	}

	public static String getLiveDataCollection() {
		return getInstance().getPrivateLiveDataCollection();
	}

	public static String getRawDataCollection() {
		return getInstance().getPrivateRawDataCollection();
	}

	public static String getHourlyDataCollection() {
		return getInstance().getPrivateHourlyDataCollection();
	}

	public static String getDailyDataCollection() {
		return getInstance().getPrivateDailyDataCollection();
	}

	public static String getWeeklyDataCollection() {
		return getInstance().getPrivateWeeklyDataCollection();
	}

	public static String getMonthlyDataCollection() {
		return getInstance().getPrivateMonthlyDataCollection();
	}

	public static String getYearlyDataCollection() {
		return getInstance().getPrivateYearlyDataCollection();
	}
	
	public static String getAppUserCollection() {
		return getInstance().getPrivateAppUserCollection();
	}

	public static String getDomainName() {
		return getInstance().getPrivateDomainName();
	}

	// Private Getters

	public String getPrivateDatabaseIP() {
		return this.databaseIP;
	}

	public int getPrivateDatabasePort() {
		return this.databasePort;
	}

	public String getPrivateDatabaseName() {
		return this.databaseName;
	}

	public String getPrivateSensorNamesCollection() {
		return this.sensorNamesCollection;
	}

	public String getPrivateLiveDataCollection() {
		return this.liveDataCollection;
	}

	public String getPrivateRawDataCollection() {
		return this.rawDataCollection;
	}

	public String getPrivateHourlyDataCollection() {
		return this.hourlyDataCollection;
	}

	public String getPrivateDailyDataCollection() {
		return this.dailyDataCollection;
	}

	public String getPrivateWeeklyDataCollection() {
		return this.weeklyDataCollection;
	}

	public String getPrivateMonthlyDataCollection() {
		return this.monthlyDataCollection;
	}

	public String getPrivateYearlyDataCollection() {
		return this.yearlyDataCollection;
	}
	
	private String getPrivateAppUserCollection() {
		return this.appUserCollection;
	}

	public String getPrivateDomainName() {
		return this.domainName;
	}
}