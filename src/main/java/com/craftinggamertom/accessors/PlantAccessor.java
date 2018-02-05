/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.accessors;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.entity.Plant;

public class PlantAccessor extends Accessor {

	public PlantAccessor() {
		super();
		collection = database.getCollection(ConfigurationReaderSingleton.getPlantCollection());
	}

	public boolean storeInDatabase(Plant thePlant) {
		return super.storeInDatabase(thePlant.getEntityAsDocument());
	}

	/**
	 * Updates the entity by replacement
	 * 
	 * @param the
	 *            plant to be updated
	 * @param the
	 *            updated plant
	 * @return true if successful
	 */
	public boolean updateEntity(Plant oldPlant, Plant newPlant) {
		return super.updateEntity(oldPlant.getEntityAsDocument(), newPlant.getEntityAsDocument());
	}

	/**
	 * Deletes the plant
	 * This is very dangerous currently
	 * 
	 * @param thePlant
	 *            to be deleted
	 * @return true if successful
	 */
	public boolean deleteEntity(Plant thePlant) {
		return super.deleteEntity(thePlant.getEntityAsDocument());
	}

}
