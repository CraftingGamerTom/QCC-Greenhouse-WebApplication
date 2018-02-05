/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.accessors;

import org.bson.Document;

import com.craftinggamertom.database.MongoDatabaseConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Accessor {

	/**
	 * The instance of the collection work will be done in
	 */
	protected MongoCollection<Document> collection;
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
			collection.insertOne(document);
			return true;
		} catch (Exception e) {
			System.out.println("Error storing item in database. Printing Stacktrace.");
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Updates an entity in the database
	 * 
	 * @param the
	 *            original document
	 * @param the
	 *            document to update by replacement
	 * 
	 * @return true if successful, false if unsuccessful
	 */
	public boolean updateEntity(Document oldDoc, Document newDoc) {

		try {
			collection.replaceOne(oldDoc, newDoc);
			return true;
		} catch (Exception e) {
			System.out.println("Error storing item in database. Printing Stacktrace.");
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Deletes an entity from the database
	 * This is dangerous to the data
	 * 
	 * @param the
	 *            document to be deleted
	 * 
	 * @return true if successful, false if unsuccessful
	 */
	public boolean deleteEntity(Document document) {

		try {
			collection.deleteOne(document);
			return true;
		} catch (Exception e) {
			System.out.println("Error storing item in database. Printing Stacktrace.");
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Makes a connection to the database
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
