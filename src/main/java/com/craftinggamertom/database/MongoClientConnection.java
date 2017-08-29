package com.craftinggamertom.database;

import com.mongodb.MongoClient;

public class MongoClientConnection {
	
	private static MongoClient client = null;
	
	protected MongoClientConnection() {
		
	}
	
	public static MongoClient getInstance() {
		if(client == null) {
			ConfigurationReader reader = new ConfigurationReader();
			reader.read();
			
			client = new MongoClient( ConfigurationReader.databaseIP , ConfigurationReader.databasePort );
		}
		return client;
	}

}
