/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.accessors;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.entity.PestType;

public class PestTypeAccessor extends Accessor {

	public PestTypeAccessor() {
		super();
		collection = database.getCollection(ConfigurationReaderSingleton.getPestTypeCollection());
	}

	public boolean storeInDatabase(PestType pestType) {
		return super.storeInDatabase(pestType.getEntityAsDocument());
	}

	/**
	 * Updates a pest type by replacement
	 * 
	 * @param pest
	 *            type to be updated
	 * @param updated
	 *            pest type
	 * @return returns true if successful
	 */
	public boolean updateEntity(PestType oldPestType, PestType newPestType) {
		return super.updateEntity(oldPestType.getEntityAsDocument(), newPestType.getEntityAsDocument());
	}

	/**
	 * Deletes a pest type
	 * 
	 * @param pestType
	 *            to be deleted
	 * @return true if successful
	 */
	public boolean deleteEntity(PestType pestType) {
		return super.deleteEntity(pestType.getEntityAsDocument());
	}

}
