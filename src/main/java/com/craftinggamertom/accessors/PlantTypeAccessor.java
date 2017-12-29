/**
* Copyright (c) 2017 Thomas Rokicki
*/

package com.craftinggamertom.accessors;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.entity.PlantType;

public class PlantTypeAccessor extends Accessor{
	
	public PlantTypeAccessor() {
		super();
		this.collection = ConfigurationReaderSingleton.getPlantTypesCollection();
	}

	public boolean storeInDatabase(PlantType plantType) {
		return super.storeInDatabase(plantType.getEntityAsDocument());
	}

	public boolean updateEntity() {
		return false;
	}

	public boolean deleteEntity() {
		return false;
	}

}
