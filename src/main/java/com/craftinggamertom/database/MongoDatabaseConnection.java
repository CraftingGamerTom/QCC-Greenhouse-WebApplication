package com.craftinggamertom.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDatabaseConnection {

	private static MongoDatabase database = null;
	
	protected MongoDatabaseConnection() {

	}

	public static MongoDatabase getInstance() {
		if (database == null) {
			ConfigurationReader reader = new ConfigurationReader();
			reader.read();
			
			MongoClient client = MongoClientConnection.getInstance();
			database = client.getDatabase(ConfigurationReader.databaseName);
		}
		return database;
	}
}
