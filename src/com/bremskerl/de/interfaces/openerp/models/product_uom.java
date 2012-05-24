package com.bremskerl.de.interfaces.openerp.models;

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

import com.bremskerl.de.interfaces.openerp.OpenERP;
import com.bremskerl.de.interfaces.openerp.OpenERPRecord;
import com.bremskerl.de.interfaces.openerp.OpenERPRecordSet;

/**
 * OpenERP Product Units of Measurement
 * 
 * is a simple representation of the OpenERP class product.uom, used for unit
 * conversions in the Bremskerl OpenERP Java Interface
 * 
 * @author Marco Dieckhoff, BREMSKERL-REIBBELAGWERKE Emmerling GmbH & Co. KG
 */
public class product_uom {

	// variable storage
	private Integer id;
	private String name;
	private Integer category_id;
	private String category_id_NAME;
	private Double factor;
	private Double factor_inv;
	private String uom_type;

	/**
	 * Create UoM dataset with given values
	 * 
	 * @param i
	 *            ID
	 * @param n
	 *            Name
	 * @param c
	 *            Category ID
	 * @param cN
	 *            Category Name
	 * @param f
	 *            Factor
	 * @param fi
	 *            Inverse Factor
	 * @param t
	 *            UoM Factor Type
	 */
	public product_uom(Integer i, String n, Integer c, String cN, Double f, Double fi, String t) {
		id = i;
		name = n;
		category_id = c;
		category_id_NAME = cN;
		factor = f;
		factor_inv = fi;
		uom_type = t;
	}

	/**
	 * Create UoM dataset by loading an ID from OpenERP
	 * 
	 * @param openerp
	 *            OpenERP connection
	 * @param uom_id
	 *            ID to load
	 */
	public product_uom(OpenERP openerp, Integer uom_id) {
		OpenERPRecordSet rs = openerp.readRecords("product.uom", new Object[] { uom_id }, new String[] { "id", "name", "category_id", "factor", "factor_inv",
				"uom_type" });
		OpenERPRecord r = rs.get(uom_id);
		setProductUOM(r);
	}

	/**
	 * Create UoM dataset by record from OpenERP
	 * 
	 * @param r
	 *            OpenERPRecord to insert
	 */
	public product_uom(OpenERPRecord r) {
		setProductUOM(r);
	}

	/**
	 * set data from OpenERP record
	 * 
	 * @param r
	 *            OpenERPRecord to extract data from
	 */
	public void setProductUOM(OpenERPRecord r) {
		id = (Integer) r.get("id");
		name = (String) r.get("name");
		category_id = (Integer) r.get("category_id");
		category_id_NAME = (String) r.get("category_id_NAME");
		factor = (Double) r.get("factor");
		factor_inv = (Double) r.get("factor_inv");
		uom_type = (String) r.get("uom_type");
	}

	/**
	 * convert to another UoM
	 * 
	 * @param value
	 *            value to convert
	 * @param uom_to
	 *            target UoM
	 * @return converted value
	 * @throws Exception
	 *             if UoM categories are incompatible
	 */
	public Double convert(Double value, product_uom uom_to) throws Exception {
		if (value == null)
			return null;
		if (uom_to == null)
			return null;

		// no conversion needed
		if (id.equals(uom_to.getId()))
			return value;

		if (!category_id.equals(uom_to.category_id))
			throw new Exception("Categories " + category_id_NAME + " and " + uom_to.getCategory_id_NAME() + " don't match");

		return (value / factor) * uom_to.getFactor();
	}

	/**
	 * Determines whether two UoMs are equal
	 * 
	 * @param compare
	 *            product_uom to compare
	 * @return true if IDs match (exact equal)
	 */
	public boolean equals(product_uom compare) {
		if (compare == null)
			return false;
		return compare.getId().equals(this.id);
	}

	/**
	 * Determines whether two UoMs are compatible, that means they are in the
	 * same category and can be converted into each other.
	 * 
	 * @param compare
	 *            product_uom to compare
	 * @return true if categories match
	 */
	public boolean compatible(product_uom compare) {
		if (compare == null)
			return false;
		return compare.getCategory_id().equals(this.category_id);
	}

	/**
	 * Determines whether this is a reference / "base" unit in it's category.
	 * 
	 * @return true if it's the reference unit
	 */
	public boolean isBase() {
		return (uom_type.equals("reference"));
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public String getCategory_id_NAME() {
		return category_id_NAME;
	}

	public Double getFactor() {
		return factor;
	}

	public Double getFactor_inv() {
		return factor_inv;
	}

	public String getUom_type() {
		return uom_type;
	}

	public String toString() {
		return name + " [" + category_id_NAME + "]";
	}

}
