/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.accessors;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.entity.PlantType;

public class PlantTypeAccessor extends Accessor {

	public PlantTypeAccessor() {
		super();
		collection = database.getCollection(ConfigurationReaderSingleton.getPlantTypeCollection());
	}

	public boolean storeInDatabase(PlantType plantType) {
		return super.storeInDatabase(plantType.getEntityAsDocument());
	}

	/**
	 * Updates the entity by replacement
	 * 
	 * @param the
	 *            plant type to be updated
	 * @param the
	 *            updated plant type
	 * @return true if successful
	 */
	public boolean updateEntity(PlantType oldPlantType, PlantType newPlantType) {
		return super.updateEntity(oldPlantType.getEntityAsDocument(), newPlantType.getEntityAsDocument());
	}

	/**
	 * Deletes the plant type
	 * This is very dangerous currently
	 * 
	 * @param plantType
	 *            to be deleted
	 * @return true if successful
	 */
	public boolean deleteEntity(PlantType plantType) {
		return super.deleteEntity(plantType.getEntityAsDocument());
	}

}
