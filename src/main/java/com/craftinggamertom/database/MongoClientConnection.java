package com.craftinggamertom.database;

import com.mongodb.MongoClient;

public class MongoClientConnection {
	
	private static MongoClient client = null;
	
	protected MongoClientConnection() {
		
	}
	
	public static MongoClient getInstance() {
		if(client == null) {
			client = new MongoClient( ConfigurationReaderSingleton.getDatabaseIP() , ConfigurationReaderSingleton.getDatabasePort());
		}
		return client;
	}

}
