package com.craftinggamertom.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDatabaseConnection {

	private static MongoDatabase database = null;
	
	protected MongoDatabaseConnection() {

	}

	/**
	 * Singleton for the database
	 * @return
	 */
	public static MongoDatabase getInstance() {
		if (database == null) {
			MongoClient client = MongoClientConnection.getInstance();
			database = client.getDatabase(ConfigurationReaderSingleton.getDatabaseName());
		}
		return database;
	}
}
