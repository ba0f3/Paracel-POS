package com.bremskerl.de.interfaces.openerp;

/**
 * OpenERP Interfaces for Java
 *  
 * Copyright (c) 2010+ BREMSKERL-REIBBELAGWERKE Emmerling GmbH & Co. KG
 * Author: Marco Dieckhoff, marco.dieckhoff@bremskerl.de
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import java.util.HashMap;

/**
 * Simple HashMap extension to not use null values
 * @author Marco Dieckhoff, BREMSKERL-REIBBELAGWERKE Emmerling GmbH & Co. KG
 * @param <K> Key object type
 * @param <V> Value object type
 */
public class OpenERPData<K,V> extends HashMap<K,V> {

	/**
	 * random generated number (eclipse told me to have one) 
	 */
	private static final long serialVersionUID = 2616516862446565830L;
	
	/**
	 * Associates the specified value with the specified key in this map. If the map previously contained a mapping for the key, the old value is replaced.
	 * Null values will not be stored
	 * @param key - key with which the specified value is to be associated
	 * @param value - value to be associated with the specified key
	 * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
	 */
	public V put(K key, V value) {
		V ret = super.containsKey(key) ? super.get(key) : null;
		if (value != null) super.put(key, value);
		return ret;
	}
	
	
}
