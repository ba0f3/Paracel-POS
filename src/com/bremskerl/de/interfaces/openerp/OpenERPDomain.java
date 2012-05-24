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

import java.util.Iterator;
import java.util.Vector;

/**
 * OpenERP Search Domain
 * 
 * @author Marco Dieckhoff, BREMSKERL-REIBBELAGWERKE Emmerling GmbH & Co. KG
 */
public class OpenERPDomain {

	/**
	 * stored list of key,(operator),value sets
	 */
	private Vector<Object> domain;

	/**
	 * OpenERP Search Domain
	 */
	public OpenERPDomain() {
		domain = new Vector<Object>();
	}

	/**
	 * OpenERP Search Domain
	 * 
	 * @param param
	 *            set of key,(operator),value to add as (first) search parameter
	 */
	public OpenERPDomain(Object[] param) {
		domain = new Vector<Object>();
		domain.add(param);
	}

	/**
	 * remove all search constraints
	 */
	public void clean() {
		domain = new Vector<Object>();
	}

	/**
	 * add a search constraint
	 * 
	 * @param param
	 *            set of key,(operator),value
	 */
	public void add(Object[] param) {
		domain.add(param);
	}

	/**
	 * add a search constraint
	 * 
	 * @param field
	 * @param value
	 *            compare to this value. Defaults to LIKE comparism
	 */
	public void add(String field, String value) {
		this.add(field, "like", value);
	}

	/**
	 * add a search constraint
	 * 
	 * @param field
	 * @param value
	 *            compare to this value
	 */
	public void add(String field, Integer value) {
		this.add(field, "=", value);
	}

	/**
	 * add a search constraint for boolean
	 * 
	 * @param field
	 * @param value
	 *            compare to this value
	 */
	public void add(String field, Boolean value) {
		this.add(field, "=", value);
	}

	/**
	 * add a search constraint
	 * 
	 * @param field
	 * @param operator
	 * @param value
	 */
	public void add(String field, String operator, Object value) {
		// null is represented by Boolean false in XMLRPC
		if (value == null) {
			this.add(field, "=", new Boolean(false));
		} else {
			domain.add(new Object[] { field, operator, value });
		}
	}

	/**
	 * add AND connector (remember: Polish Notation)
	 */
	public void and() {
		domain.add("&");
	}

	/**
	 * adds multiple AND connectors (for Polish Notation)
	 * @param countParameters count of parameters following this addition
	 */
	public void and(int countParameters) {
		// add one less than parameters expected
		for (int i = 1; i < countParameters; i++) {
			domain.add("&");	
		}
	}
	
	/**
	 * add OR connector (remember to use Polish Notation)
	 */
	public void or() {
		domain.add("|");
	}


	/**
	 * adds multiple OR connectors (for Polish Notation)
	 * @param countParameters count of parameters following this addition
	 */
	public void or(int countParameters) {
		// add one less than parameters expected
		for (int i = 1; i < countParameters; i++) {
			domain.add("|");	
		}
	}

	
	/**
	 * converts domain to array (to be used in XMLRPC call)
	 * 
	 * @return Object[] array
	 */
	public Object[] toArray() {
		return domain.toArray();
	}

	/**
	 * converts domain to array (to be used in XMLRPC call)
	 * 
	 * @return Object[] array
	 */
	public Object[] get() {
		return this.toArray();
	}

	/**
	 * joins multiple strings together by a token
	 * 
	 * @param token
	 * @param strings
	 * @return joined String
	 */
	private static String join(String token, Object[] strings) {
		StringBuffer sb = new StringBuffer();

		for (int x = 0; x < (strings.length - 1); x++) {
			sb.append(strings[x].toString());
			sb.append(token);
		}
		sb.append(strings[strings.length - 1]);

		return sb.toString();
	}

	/**
	 * @return Object[] array
	 */
	public String toString() {
		Vector<String> domains = new Vector<String>();
		Iterator<Object> i = domain.iterator();
		while (i.hasNext()) {
			Object n = i.next();
			domains.add(n.toString());
		}
		return join(" , ", domains.toArray());
	}

}
