/**
 * Copyright (c) 2017 Thomas Rokicki
 */

package com.craftinggamertom.accessors;

import com.craftinggamertom.database.ConfigurationReaderSingleton;
import com.craftinggamertom.entity.Zone;

public class ZoneAccessor extends Accessor {

	public ZoneAccessor() {
		super();
		collection = database.getCollection(ConfigurationReaderSingleton.getZoneCollection());
	}

	public boolean storeInDatabase(Zone theZone) {
		return super.storeInDatabase(theZone.getEntityAsDocument());
	}

	/**
	 * Updates a Zone by replacement
	 * 
	 * @param The
	 *            zone to be updated
	 * @param The
	 *            updated zone
	 * @return true if successful
	 */
	public boolean updateEntity(Zone oldZone, Zone newZone) {
		return super.updateEntity(oldZone.getEntityAsDocument(), newZone.getEntityAsDocument());
	}

	/**
	 * Deletes a zone object
	 * 
	 * @param The
	 *            zone to delete
	 * @return true if successful
	 */
	public boolean deleteEntity(Zone theZone) {
		return super.deleteEntity(theZone.getEntityAsDocument());
	}

}
