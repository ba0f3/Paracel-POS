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
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import java.util.HashMap;

import com.bremskerl.de.interfaces.openerp.models.product_uom;

/**
 * OpenERP Unit conversions
 * 
 * @author Marco Dieckhoff, BREMSKERL-REIBBELAGWERKE Emmerling GmbH & Co. KG
 */
public class OpenERPUnits {

	// database connection
	OpenERP openerp;

	/**
	 * cache for product.uom, by id
	 */
	HashMap<Integer, product_uom> cache;

	/**
	 * cache for reference product.uom (base unit), by category_id
	 */
	HashMap<Integer, product_uom> baseunits;

	/**
	 * create a new instance
	 * 
	 * @param o
	 *            OpenERP database connection
	 */
	public OpenERPUnits(OpenERP o) {
		openerp = o;
		cache = new HashMap<Integer, product_uom>();
		baseunits = new HashMap<Integer, product_uom>();
	}

	/**
	 * return known UoM by id
	 * @param id
	 * @return
	 */
	public product_uom getProductUoM(Integer id) {
		if (cache.containsKey(id))
			return cache.get(id);
		product_uom p = new product_uom(openerp, id);
		cache.put(id, p);
		return p;
	}

	/**
	 * search for a named UoM
	 * 
	 * @param category
	 *            Category ID to search in
	 * @param name
	 *            Name to search for
	 * @return product_uom found (expecting max. one)
	 */
	public product_uom searchUoM(Integer category, String name) {
		OpenERPDomain domain = new OpenERPDomain();
		domain.add("category_id", category);
		domain.add("name", "=", name);

		Object[] ids = openerp.search("product.uom", domain);
		if (ids == null)
			return null;

		Integer id = null;
		if (ids.length > 0)
			id = (Integer) ids[0];

		if (id == null)
			return null;

		return getProductUoM(id);
	}

	/**
	 * gets the reference (base) UoM for a category
	 * 
	 * @param category
	 *            Category ID to search the reference UoM in
	 * @return Reference UoM for the given category
	 */
	public product_uom getBaseUoM_byCategory(Integer category) {
		// use caching
		if (baseunits.containsKey(category))
			return baseunits.get(category);

		OpenERPDomain domain = new OpenERPDomain();
		domain.add("category_id", category);
		domain.add("uom_type", "=", "reference");
		Object[] ids = openerp.search("product.uom", domain);

		if (ids == null)
			return null;

		Integer id = null;
		if (ids.length > 0)
			id = (Integer) ids[0];

		if (id == null)
			return null;

		product_uom p = new product_uom(openerp, id);
		baseunits.put(id, p);
		return p;
	}

	/**
	 * gets the reference (base) UoM for this units category
	 * 
	 * @param uom
	 *            Unit (product_uom) to get Base unit of
	 * @return Base unit of uom
	 */
	public product_uom getBaseUoM(product_uom uom) {
		return getBaseUoM_byCategory(uom.getCategory_id());
	}

	/**
	 * gets the reference (base) UoM for this units category
	 * 
	 * @param uom
	 *            Unit (Integer) to get Base unit of
	 * @return Base unit of uom
	 */
	public product_uom getBaseUoM(Integer uomid) {
		product_uom uom = new product_uom(openerp, uomid);
		return getBaseUoM(uom);
	}

	/**
	 * convert between two known UoMs
	 * 
	 * @param from
	 *            source UoM
	 * @param value
	 *            value to convert
	 * @param to
	 *            target UoM
	 * @return converted value
	 * @throws Exception
	 *             if UoM categories are incompatible
	 */
	public Double convert(product_uom from, Double value, product_uom to) throws Exception {
		if ((from == null) || (to == null))
			return null;
		return from.convert(value, to);
	}
	
	/**
	 * convert between two known UoMs
	 * 
	 * @param uom_from
	 *            source UoM id
	 * @param value
	 *            value to convert
	 * @param uom_to
	 *            target UoM id
	 * @return converted value
	 * @throws Exception
	 *             if UoM categories are incompatible
	 */
	public Double convert(Integer uom_from, Double value, Integer uom_to) throws Exception {
		product_uom from = getProductUoM(uom_from);
		product_uom to = getProductUoM(uom_to);
		return convert(from, value, to);
	}

	/**
	 * convert between two known UoMs
	 * 
	 * @param uom_from
	 *            source UoM id
	 * @param value
	 *            value to convert
	 * @param uom_to
	 *            target UoM
	 * @return converted value
	 * @throws Exception
	 *             if UoM categories are incompatible
	 */
	public Double convert(Integer uom_from, Double value, product_uom to) throws Exception {
		product_uom from = getProductUoM(uom_from);
		return convert(from, value, to);
	}

	/**
	 * convert between two known UoMs
	 * 
	 * @param uom_from
	 *            source UoM
	 * @param value
	 *            value to convert
	 * @param uom_to
	 *            target UoM id
	 * @return converted value
	 * @throws Exception
	 *             if UoM categories are incompatible
	 */
	public Double convert(product_uom from, Double value, Integer uom_to) throws Exception {
		product_uom to = getProductUoM(uom_to);
		return convert(from, value, to);
	}


	/**
	 * convert between a known and a named UoMs
	 * 
	 * @param uom_from
	 *            source UoM ID
	 * @param value
	 *            value to convert
	 * @param uom_to
	 *            target UoM Name
	 * @return converted value
	 * @throws Exception
	 *             if UoM categories are incompatible
	 */
	public Double convert(product_uom from, Double value, String uom_to) {
		if (from == null)
			return null;

		product_uom p = searchUoM(from.getCategory_id(), uom_to);
		if (p == null)
			return null;

		// can't happen to have an incompatible conversion, as I'm searching in
		// the same category!
		try {
			return convert(from, value, p.getId());
		} catch (Exception e) {
			// should never happen
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * convert between a known and a named UoMs
	 * 
	 * @param uom_from
	 *            source UoM ID
	 * @param value
	 *            value to convert
	 * @param uom_to
	 *            target UoM Name
	 * @return converted value
	 * @throws Exception
	 *             if UoM categories are incompatible
	 */
	public Double convert(Integer uom_from, Double value, String uom_to) {
		product_uom from = getProductUoM(uom_from);
		return convert(from, value, uom_to);
	}
	

	/**
	 * convert to reference / base unit
	 * 
	 * @param from
	 *            Source UoM
	 * @param value
	 *            Value to convert
	 * @return converted value
	 */
	public Double convertToBase(product_uom from, Double value) {
		if (from == null)
			return null;

		product_uom to = getBaseUoM(from);
		if (to == null)
			return null;

		// can't happen to have an incompatible conversion, as I'm searching in
		// the same category!
		try {
			return from.convert(value, to);
		} catch (Exception e) {
			// should never happen
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * convert to reference / base unit
	 * 
	 * @param uom_from
	 *            Source UoM id
	 * @param value
	 *            Value to convert
	 * @return converted value
	 */
	public Double convertToBase(Integer uom_from, Double value) {
		product_uom from = getProductUoM(uom_from);
		return convertToBase(from, value);
	}

}
