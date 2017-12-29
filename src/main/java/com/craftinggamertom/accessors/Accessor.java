/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.accessors;

import org.bson.Document;

import com.craftinggamertom.database.MongoDatabaseConnection;
import com.mongodb.client.MongoDatabase;

public class Accessor {

	/**
	 * The instance of the collection work will be done in
	 */
	protected String collection;
	/**
	 * The instance of the database that work will be done in
	 */
	protected MongoDatabase database;

	public Accessor() {
		makeConnectionToDatabase();
	}

	/**
	 * Takes an entity and stores it in the database
	 * 
	 * @return true if successful, false if unsuccessful
	 */
	public boolean storeInDatabase(Document document) {
		
		try {
			
		}catch(Exception e) {
			System.out.println("Error storing item in database. Printing Stacktrace.");
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Updates an entity in the database
	 * 
	 * @return true if successful, false if unsuccessful
	 */
	public boolean updateEntity() {
		return false;
	}

	/**
	 * Deletes an entity from the database
	 * 
	 * @return true if successful, false if unsuccessful
	 */
	public boolean deleteEntity() {
		return false;
	}

	/**
	 * Makes a conntection to the database
	 */
	private void makeConnectionToDatabase() {

		try {
			database = MongoDatabaseConnection.getInstance(); // Singleton
		} catch (Exception e) {
			System.out.println(" ***** ERROR CONNECTING TO MONGO DB ***** ");
			System.out.println("ERROR WHEN GETTING DATABASE. (Accessor.java) STACKTRACE:");
			e.printStackTrace();
			System.out.println(" ***** END ERROR ***** ");
		}
	}
}
