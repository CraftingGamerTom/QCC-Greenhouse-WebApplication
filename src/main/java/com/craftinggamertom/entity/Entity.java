/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.entity;

import org.bson.Document;

public class Entity {

	protected Document document;

	public Entity(Document doc) {
		document = doc;
	}

	public Document getEntityAsDocument() {
		return document;
	}
}
