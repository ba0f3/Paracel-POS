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
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * OpenERP Read result records (set of OpenERPRecord)
 * 
 * @author Marco Dieckhoff, BREMSKERL-REIBBELAGWERKE Emmerling GmbH & Co. KG
 */
public class OpenERPRecordSet {

	// use java logging
	private static Logger jLog = Logger.getLogger(OpenERPRecordSet.class.getName());

	private Vector<OpenERPRecord> records;

	/**
	 * create new OpenERP result record set
	 * 
	 * @param readresult
	 *            data set derived from OpenERP.read
	 */
	public OpenERPRecordSet(Object[] readresult) {
		records = new Vector<OpenERPRecord>();
		if (readresult != null) {
			for (int i = 0; i < readresult.length; i++) {
				records.add(new OpenERPRecord(readresult[i]));
			}

		}
	}

	/***
	 * create new empty OpenERPRecordSet
	 */
	public OpenERPRecordSet() {
		records = new Vector<OpenERPRecord>();
	}

	/***
	 * adds an OpenERPRecord to this RecordSet
	 * 
	 * @param record
	 */
	public void add(OpenERPRecord record) {
		if (record != null) {
			records.add(record);
		}
	}

	/**
	 * return count of records available
	 * 
	 * @return count of records available
	 */
	public int size() {
		return records.size();
	}

	/**
	 * gets the first record (not necessarily deterministic!)
	 * 
	 * @param id
	 *            database id (primary key)
	 * @return identified Record, null if none found
	 */
	public OpenERPRecord get() {
		if (records == null)
			return null;
		if (records.size() == 0)
			return null;
		return records.elementAt(0);
	}

	/**
	 * get a single record with the specified id
	 * 
	 * @param id
	 *            database id (primary key)
	 * @return identified Record, null if none found
	 */
	public OpenERPRecord get(int id) {
		Iterator<OpenERPRecord> i = records.iterator();
		while (i.hasNext()) {
			OpenERPRecord n = i.next();
			Integer rID = (Integer) n.get("id"); // no class cast exception
													// expected here...
			if (rID == id)
				return n;
		}
		return null;
	}

	/**
	 * get a single record by searching for a key / value reference
	 * 
	 * @param key
	 *            key to search for in results
	 * @param value
	 *            value to match in results
	 * @return first encountered OpenERPRecord matching key and value
	 */
	public OpenERPRecord get(String key, Object value) {
		Iterator<OpenERPRecord> i = records.iterator();
		while (i.hasNext()) {
			OpenERPRecord n = i.next();
			if (!n.containsKey(key))
				continue;
			if ((n.get(key) == null) && (value == null))
				return n;
			if (n.get(key).equals(value))
				return n;
		}
		return null;
	}

	/**
	 * get iterator for the OpenERPRecord storage
	 * 
	 * @return iterator for the OpenERPRecord storage
	 */
	public Iterator<OpenERPRecord> iterator() {
		return records.iterator();
	}

	/**
	 * get string representation
	 */
	public String toString() {
		String EOL = System.getProperty("line.separator");

		String r = "[" + EOL;
		Iterator<OpenERPRecord> i = records.iterator();
		while (i.hasNext()) {
			OpenERPRecord n = i.next();
			r += "    " + n.toString() + EOL;
		}
		r += "]" + EOL;
		return r;
	}

	/**
	 * adds all OpenERPRecords as substrings relation.keynames to the records
	 * 
	 * @param string
	 *            relation name to use. Must be fieldname of a many2one field in
	 *            these OpenERPRecords.
	 * @param insert
	 *            records to add, must be set of records belonging to the
	 *            related object (content of field named "relation" is id of
	 *            these records)
	 * 
	 * 
	 * @example Object[] result_ids = openerp.search("product.product");
	 *          OpenERPRecordSet results =
	 *          openerp.readRecords("product.product", result_ids, new
	 *          String[]{"name","categ_id"});
	 * 
	 *          Object[] category_ids = results.getFieldContents("categ_id");
	 *          OpenERPRecordSet categories =
	 *          openerp.readRecords("product.category", category_ids, new
	 *          String[]{"name","sequence","type"});
	 * 
	 *          results.relate("categ_id", categories);
	 * 
	 */
	public void relate(String relation, OpenERPRecordSet insert) {
		// don't relate if there is nothing to relate
		if (relation == null || insert == null)
			return;

		// do this with all records
		Iterator<OpenERPRecord> i = records.iterator();
		while (i.hasNext()) {
			OpenERPRecord r = i.next();

			// if there is a relation
			if (r.containsKey(relation)) {
				Object relation_id_obj = r.get(relation);
				// don't work on empty objects
				if (!(relation_id_obj == null)) {
					// get the id

					// many2many / one2many handling:
					boolean isList = false;
					try {
						Object[] oList = (Object[]) r.get(relation);
						OpenERPRecordSet subset = insert.getIDSubset(oList);
						r.relate(relation, subset);
						isList = true;

					} catch (ClassCastException e) {
						// do nothing. So it is no object[], meaning no 2many,
						// just go on.
					}

					// single item handling:
					if (!isList) {
						Integer relation_id = (Integer) r.get(relation);
						// this should be the id used in the RecordSet to be
						// inserted
						OpenERPRecord related_object = insert.get(relation_id);
						// relate this specific object
						r.relate(relation, related_object);
					}
				}
			}
		}
	}

	/**
	 * Get a list of field content. Especially useful for getting a list of
	 * related ids from many2one fields.
	 * 
	 * @param fieldname
	 *            fieldname to get the list of
	 * @param ignoreNull
	 *            does not include null values if set
	 * 
	 * @return List of field contents. Entries can occur multiple times.
	 */
	public Vector<Object> getFieldContents(String fieldname, boolean ignoreNull) {
		// do nothing if nothing is requested
		if (fieldname == null)
			return null;

		Vector<Object> results = new Vector<Object>();

		// do this with all records
		Iterator<OpenERPRecord> i = records.iterator();
		while (i.hasNext()) {
			OpenERPRecord r = i.next();

			// if there is a fieldname
			if (r.containsKey(fieldname)) {
				Object value = r.get(fieldname);
				if ((value != null) || (!(ignoreNull)))
					results.add(value);
			}
		}

		return results;
	}

	/**
	 * Get a list of field content. Especially useful for getting a list of
	 * related ids from many2one fields.
	 * 
	 * @param fieldname
	 *            fieldname to get the list of
	 * 
	 * @return List of field contents. Entries can occur multiple times.
	 */
	public Vector<Object> getFieldContents(String fieldname) {
		return getFieldContents(fieldname, false);
	}

	/**
	 * Get a list of field content as a specific class. Especially useful for
	 * getting a list of
	 * related ids from many2one fields.
	 * If the cast to the desired class can not be made, the value in question
	 * is not returned at all.
	 * 
	 * @param <E>
	 *            Object class to cast into
	 * @param fieldname
	 *            fieldname to get the list of
	 * @return Vector of desired class
	 */
	@SuppressWarnings("unchecked")
	// catched ClassCastException
	public <E> Vector<E> getFieldContentsCasted(String fieldname) {
		Vector<Object> v = getFieldContents(fieldname, false);
		if (v == null)
			return null;

		Vector<E> ret = new Vector<E>();

		Iterator<Object> i = v.iterator();
		while (i.hasNext()) {
			Object o = i.next();
			try {
				ret.add((E) o);
			} catch (ClassCastException e) {
				// do nothing, we ignore uncastables
			}
		}
		return ret;
	}

	/**
	 * flattens an array of arrays down to a flat array.
	 * 
	 * @param o
	 *            array of arrays
	 * @return array
	 */
	public Object[] flattenArray(Object[] o) {
		if (o == null)
			return null;

		// simpler results handling
		Vector<Object> results = new Vector<Object>();

		for (int i = 0; i < o.length; i++) {

			try {
				Object[] subList = (Object[]) o[i];
				// ignore empty content
				if (subList == null)
					continue;

				// flatten into result
				for (int j = 0; j < subList.length; j++) {
					results.add(subList[j]);
				}
			} catch (ClassCastException e) {
				// it's no Object[]. So just add the default
			}

		}

		return results.toArray();
	}

	/**
	 * gets a subset of OpenERPRecordSet
	 * 
	 * @param lst
	 *            List of ids (field "id" of OpenERPRecord) to return
	 * @return OpenERPRecordSet containing only the records with the given ids.
	 */
	public OpenERPRecordSet getIDSubset(Object[] lst) {
		// bail out
		if (lst == null)
			return null;
		if (lst.length <= 0)
			return null;

		// get list of IDs as Vector, for .contains
		Vector<Integer> idList = new Vector<Integer>();
		for (int i = 0; i < lst.length; i++) {
			// while moving to vector, sort out everything that's not integer
			try {
				idList.add((Integer) lst[i]);
			} catch (ClassCastException cce) {
				jLog.warning("ClassCastException while converting '" + lst[i] + "' to Integer");
			}
		}

		// create record set to return
		OpenERPRecordSet result = new OpenERPRecordSet(null);

		// look into all records
		Iterator<OpenERPRecord> i = records.iterator();

		while (i.hasNext()) {
			OpenERPRecord r = i.next();

			// only for records that have a key
			if (!r.containsKey("id"))
				continue;

			// if the key is requested
			Integer id = (Integer) r.get("id");
			if (idList.contains(id)) {
				// add it to list
				try {
					result.add((OpenERPRecord) r.clone());
				} catch (CloneNotSupportedException e) {
					// should not happen anyway.
					e.printStackTrace();
				}
			}

		}

		// return subset
		return result;
	}

	/**
	 * get a subset of records by searching for a key / value reference
	 * 
	 * @param key
	 *            key to search for in results
	 * @param value
	 *            value to match in results
	 * @return subset of records matching key and value
	 */
	public OpenERPRecordSet subset(String key, Object value) {
		OpenERPRecordSet r = new OpenERPRecordSet();

		Iterator<OpenERPRecord> i = records.iterator();
		while (i.hasNext()) {
			OpenERPRecord n = i.next();
			if (!n.containsKey(key))
				continue;
			if ((n.get(key) == null) && (value == null))
				r.add(n);
			if (n.get(key).equals(value))
				r.add(n);
		}
		return r;
	}

	/**
	 * get a subset of records by searching for multiple key / value references
	 * 
	 * @param keyvalues
	 *            HashMap of key and values to compare
	 * @return subset of records matching keys and respective values
	 */
	public OpenERPRecordSet subset(HashMap<String, Object> keyvalues) {
		OpenERPRecordSet r = new OpenERPRecordSet();

		Iterator<OpenERPRecord> i = records.iterator();
		while (i.hasNext()) {
			OpenERPRecord n = i.next();

			boolean acceptedRecord = true;

			Iterator<String> keys = keyvalues.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				Object value = keyvalues.get(key);

				if (!n.containsKey(key)) {
					acceptedRecord = false;
					continue;
				}
				if ((n.get(key) == null) && (value != null)) {
					acceptedRecord = false;
					continue;
				}
				if (!(n.get(key).equals(value))) {
					acceptedRecord = false;
					continue;
				}
			}

			if (acceptedRecord)
				r.add(n);
		}
		return r;
	}

}
