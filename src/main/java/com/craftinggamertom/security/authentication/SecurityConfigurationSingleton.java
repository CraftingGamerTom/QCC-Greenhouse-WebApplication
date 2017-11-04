package com.craftinggamertom.security.authentication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SecurityConfigurationSingleton {

	private static SecurityConfigurationSingleton instance = null; // Singleton
	
	private String clientId;
	private String clientSecret;

	private static SecurityConfigurationSingleton getInstance() {
		if (instance == null) {
			instance = new SecurityConfigurationSingleton();
			instance.read();
		}
		return instance;
	}

	/**
	 * readed the configuration file always done in singleton instance
	 */
	private void read() {
		
		// Config file name here
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("security.properties");
		
		try {
			// Loads File then Loads the properties
			Properties properties = new Properties();
			properties.load(stream);

			// Sets the configurations - Add properties here
			clientId = properties.getProperty("clientId");
			clientSecret = properties.getProperty("clientSecret");

			System.out.println("SecurityReader: Security Settings Read / Updated");
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
			System.out.println("Could not close security configuration stream. IOException: ");
			e.printStackTrace();
		}
	}

	/**
	 * Re-reads the configuration file (use sparingly - only for testing)
	 */
	public void reset() {
		System.out.println("*** RELOADING SECURITY CONFIGURATION ***");
		getInstance().read();
		System.out.println("*** FINISHED RELOADING ***");
	}

	/**
	 * Verifies the stored data by printing out the data in the console. Developer
	 * MUST update tags when config tags are changed.
	 */
	public void verify() {
		System.out.println("** CONFIGURATION VISUAL VERIFICATION **\n*");

		System.out.println("* clientId: " + getClientId());
		System.out.println("* clientSecret: " + getClientSecret());

		System.out.println("*\n** END CONFIGURATION VISUAL VERIFICATION **");
	}
	
	// Getters

	public static String getClientId() {
		return getInstance().getPrivateClientId();
	}

	public static String getClientSecret() {
		return getInstance().getPrivateClientSecret();
	}

	
	// Private Getters

	public String getPrivateClientId() {
		return this.clientId;
	}

	public String getPrivateClientSecret() {
		return this.clientSecret;
	}
}