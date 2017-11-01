package com.craftinggamertom.security.authentication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads in the configurations on Boot.
 * 
 * The security.properties file is not included onguthub for security reasons.
 * It is formated like:
 * 
 * google.oauth2.clientId=<CLIENT_ID> google.oauth2.clientSecret=<SECRET>
 * 
 * @author Thomas Rokicki
 *
 */
public class SecurityConfigReader {

	// Config file name here
	private InputStream stream = this.getClass().getClassLoader().getResourceAsStream("security.properties");

	// Public tags
	private String clientId;
	private String clientSecret;

	/**
	 * Loads the configuration file when created.
	 */
	public SecurityConfigReader() {
		read();
	}

	/**
	 * Reads in the configuration file.
	 */
	public void read() {
		try {
			// Loads File then Loads the properties
			Properties properties = new Properties();
			properties.load(stream);

			// Sets the configurations - Add properties here
			setClientId(properties.getProperty("google.oauth2.clientId"));
			setClientSecret(properties.getProperty("google.oauth2.clientSecret"));

			System.out.println("SecurityConfigReader: Configuration Read / Updated");
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

	}

	/**
	 * Refreshes the data stored on boot when called
	 */
	public void refresh() {
		read();
		System.out.println("Refreshed Security Configuration");
	}

	/**
	 * Verifies the stored data by printing out the data in the console. Developer
	 * MUST update tags when config tags are changed.
	 */
	protected void verify() {
		System.out.println("** CONFIGURATION VISUAL VERIFICATION **\n*");

		System.out.println("* clientId: " + clientId);
		System.out.println("* clientSecret: " + clientSecret);

		System.out.println("*\n** END CONFIGURATION VISUAL VERIFICATION **");
	}

	public String getClientId() {
		return clientId;
	}

	// Private since nothing else should be setting this
	private void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	// Private since nothing else should be setting this
	private void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

}