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

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * OpenERP Interfaces for Java
 * 
 * @author Marco Dieckhoff, BREMSKERL-REIBBELAGWERKE Emmerling GmbH & Co. KG
 */
public class OpenERP {

	// connection
	private XmlRpcClient xmlrpc;

	// userid, derived from first login
	private int openERPuserid;

	// internally stored
	private String openERPHost;
	private int openERPPort;
	private static int default_openERPPort = 8069;
	private String openERPDB;
	private String openERPUser;
	private String openERPPassword; // esp. the password is needed for every
									// request

	// cache variable - we'll only request it once from OpenERP server
	private String cacheServerTimeZone;
	private HashMap<String, String[]> cacheGetFieldNames;

	/**
	 * Initialize connection to OpenERP server
	 * 
	 * @param host
	 *            Hostname to connect to. Either DNS-resolvable or IP
	 * @param port
	 *            Port to connect to. OpenERP XML-RPC standard would be 8069.
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */
	public OpenERP(String host, int port) {
		openERPHost = host;
		openERPPort = port;
		cacheServerTimeZone = null;
		cacheGetFieldNames = null;
	}

	/**
	 * Initialize connection to OpenERP server
	 * 
	 * @param host
	 *            Hostname to connect to. Either DNS-resolvable or IP
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */
	public OpenERP(String host) {
		openERPHost = host;
		openERPPort = default_openERPPort;
		cacheServerTimeZone = null;
		cacheGetFieldNames = null;

	}

	/**
	 * Initialize connection to OpenERP server and log in
	 * 
	 * @param host
	 *            Hostname to connect to. Either DNS-resolvable or IP
	 * @param port
	 *            Port to connect to. OpenERP XML-RPC standard would be 8869.
	 * @param db
	 *            Database name
	 * @param user
	 *            User name
	 * @param pass
	 *            User password
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */
	public OpenERP(String host, int port, String db, String user, String pass) throws MalformedURLException, XmlRpcException {
		openERPHost = host;
		openERPPort = port;
		openERPDB = db;
		openERPUser = user;
		openERPPassword = pass;
		cacheServerTimeZone = null;
		cacheGetFieldNames = null;

		loginOpenERP();
		initializeXMLRPC();
	}

	/**
	 * Initialize connection to OpenERP server and log in
	 * 
	 * @param host
	 *            Hostname to connect to. Either DNS-resolvable or IP
	 * @param db
	 *            Database name
	 * @param user
	 *            User name
	 * @param pass
	 *            User password
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */
	public OpenERP(String host, String db, String user, String pass) throws MalformedURLException, XmlRpcException {
		openERPHost = host;
		openERPPort = default_openERPPort;
		openERPDB = db;
		openERPUser = user;
		openERPPassword = pass;
		cacheServerTimeZone = null;
		cacheGetFieldNames = null;

		loginOpenERP();
		initializeXMLRPC();
	}

	/**
	 * List databases from OpenERP Host No login needed, just servername on
	 * class initialisation
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */
	public String[] list() throws MalformedURLException, XmlRpcException {
		XmlRpcClient xmlrpcLogin = new XmlRpcClient();

		XmlRpcClientConfigImpl xmlrpcConfigLogin = new XmlRpcClientConfigImpl();
		xmlrpcConfigLogin.setEnabledForExtensions(true);

		URL serverURL = null;
		// processing is done with /db, unlike login (/common) or queries
		// (/object)
		serverURL = new URL("http", openERPHost, openERPPort, "/xmlrpc/db");
		xmlrpcConfigLogin.setServerURL(serverURL);

		xmlrpcLogin.setConfig(xmlrpcConfigLogin);

		// Connect
		Object[] params = new Object[] {};
		Object id = xmlrpcLogin.execute("list", params);
		Object[] ids = (Object[]) id;

		String[] strs = new String[ids.length];
		for (int i = 0; i < ids.length; i++) {
			Object idsi = ids[i];
			strs[i] = (String) idsi;
		}
		return strs;
	}

	/**
	 * Login to OpenERP with the givven credentials. May be used to change user
	 * in mid-session.
	 * 
	 * @param username
	 * @param password
	 * @return true if login was successful
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */
	public boolean loginOpenERP(String username, String password) throws MalformedURLException, XmlRpcException {
		openERPUser = username;
		openERPPassword = password;
		return loginOpenERP();
	}

	/**
	 * Login to OpenERP with the preset credentials.
	 * 
	 * @return true if login was successful
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */
	public boolean loginOpenERP() throws MalformedURLException, XmlRpcException {
		XmlRpcClient xmlrpcLogin = new XmlRpcClient();

		XmlRpcClientConfigImpl xmlrpcConfigLogin = new XmlRpcClientConfigImpl();
		xmlrpcConfigLogin.setEnabledForExtensions(true);

		URL serverURL = null;
		// processing is done with /common, unlike queries (/object)
		serverURL = new URL("http", openERPHost, openERPPort, "/xmlrpc/common");
		xmlrpcConfigLogin.setServerURL(serverURL);

		xmlrpcLogin.setConfig(xmlrpcConfigLogin);
		// Connect
		Object[] params = new Object[] { openERPDB, openERPUser, openERPPassword };
		Object id = xmlrpcLogin.execute("login", params);
		if (id instanceof Integer)
			openERPuserid = ((Integer) id).intValue();

		// return "true" if user id exists
		return (openERPuserid > 0);
	}

	/**
	 * Login to OpenERP with the preset credentials.
	 * 
	 * @return true if login was successful
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */
	public String getServerTimeZone() {
		if (cacheServerTimeZone != null)
			return cacheServerTimeZone;

		XmlRpcClient xmlrpcTimezone = new XmlRpcClient();

		XmlRpcClientConfigImpl xmlrpcConfigTimezone = new XmlRpcClientConfigImpl();
		xmlrpcConfigTimezone.setEnabledForExtensions(true);

		URL serverURL = null;
		// processing is done with /common, unlike queries (/object)
		try {
			serverURL = new URL("http", openERPHost, openERPPort, "/xmlrpc/common");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		xmlrpcConfigTimezone.setServerURL(serverURL);

		xmlrpcTimezone.setConfig(xmlrpcConfigTimezone);
		// Connect
		Object[] params = new Object[] { openERPDB, openERPuserid, openERPPassword };
		Object timezone = null;
		try {
			timezone = xmlrpcTimezone.execute("timezone_get", params);
		} catch (XmlRpcException e) {
			e.printStackTrace();
			return null;
		}
		if (timezone instanceof String)
			cacheServerTimeZone = (String) timezone;

		// return "true" if user id exists
		return cacheServerTimeZone;
	}

	/**
	 * initialize XML RPC request object
	 * 
	 * @throws MalformedURLException
	 */
	private void initializeXMLRPC() throws MalformedURLException {
		xmlrpc = new XmlRpcClient();

		XmlRpcClientConfigImpl xmlrpcConfig = new XmlRpcClientConfigImpl();
		xmlrpcConfig.setEnabledForExtensions(true);

		URL serverURL = null;
		serverURL = new URL("http", openERPHost, openERPPort, "/xmlrpc/object");
		xmlrpcConfig.setServerURL(serverURL);

		xmlrpc.setConfig(xmlrpcConfig);
	}

	/**
	 * tests if the last login to OpenERP was successful.
	 * 
	 * @return true if last login was successful
	 */
	public boolean isConnected() {
		// return "true" if user id exists
		return (openERPuserid > 0);
	}

	/**
	 * tests if a module exists or is installed
	 * 
	 * @param modulename
	 *            Name of the module to test for
	 * @param isInstalled
	 *            Do test if the module is installed in addition to mere
	 *            existence
	 * @return true if the module exists (resp. is installed)
	 */
	public boolean hasModule(String modulename, boolean isInstalled) {
		OpenERPDomain domain = new OpenERPDomain();
		domain.add("name", "=", modulename);
		if (isInstalled) {
			domain.add("state", "=", "installed");
		}
		// use a simple search on the module model/table
		Object[] is = search("ir.module.module", domain);
		return ((is != null) && (is.length > 0));
	}

	/**
	 * tests if a module is installed
	 * 
	 * @param modulename
	 *            Name of the module to test for
	 * @return true if the module is installed
	 */
	public boolean hasModule(String modulename) {
		return hasModule(modulename, true);
	}

	/**
	 * Gets a list of all field names for the named model.
	 * 
	 * @param model
	 *            model name to lookup
	 * @param useCache
	 *            mark whether to use the built-in cache or request from
	 *            database every time
	 * @return list of all field names belonging to the model
	 * 
	 */
	public String[] getFieldNames(String model, boolean useCache) {
		if (model == null)
			return null;

		if (cacheGetFieldNames == null)
			cacheGetFieldNames = new HashMap<String, String[]>();

		if ((useCache) && (cacheGetFieldNames.containsKey(model))) {
			return cacheGetFieldNames.get(model);
		}

		OpenERPDomain domain = new OpenERPDomain();
		domain.add("model", "=", model);

		// use a simple search on the module model/table
		Object[] is = search("ir.model.fields", domain);
		OpenERPRecordSet rs = readRecords("ir.model.fields", is, new String[] { "name" });

		Vector<String> v = new Vector<String>();

		Iterator<OpenERPRecord> ir = rs.iterator();
		while (ir.hasNext()) {
			OpenERPRecord r = ir.next();
			v.add(r.getString("name"));
		}
		String[] ret = (String[]) v.toArray(new String[v.size()]);
		cacheGetFieldNames.put(model, ret);
		return ret;
	}

	/**
	 * Gets a list of all field names for the named model.
	 * 
	 * @param model
	 *            model name to lookup
	 * @return list of all field names belonging to the model
	 * 
	 */
	public String[] getFieldNames(String model) {
		// use cache = true
		return getFieldNames(model, true);
	}

	/**
	 * create params for use with XMLRPC call
	 * 
	 * @param model
	 *            modelname to act on
	 * @param action
	 *            action to perform
	 * @return parametrized Vector
	 */
	private Vector<Object> createParams(String model, String action) {
		Vector<Object> params = new Vector<Object>();
		params.add(openERPDB);
		params.add(openERPuserid);
		params.add(openERPPassword);
		params.add(model);
		params.add(action);
		return params;
	}

	/**
	 * performs a search (kind of SELECT WHERE) on an OpenERP model (table)
	 * 
	 * @param model
	 *            model to search in
	 * @param domain
	 *            search domain
	 * @return null if no result, or Object[] castable to Integer[], list of IDs
	 * @throws nothing
	 *             will print stack trace and return null
	 */
	public Object[] search(String model, OpenERPDomain domain) {
		return search(model, domain.get());
	}

	/**
	 * performs a search (kind of SELECT WHERE) on an OpenERP model (table)
	 * 
	 * @param model
	 *            model to search in
	 * @param domain
	 *            search domain, list of (key, operator, value) lists.
	 * @return null if no result, or Object[] castable to Integer[], list of IDs
	 * @throws nothing
	 *             will print stack trace and return null
	 */
	public Object[] search(String model, Object[] domain) {
		Vector<Object> params = createParams(model, "search");

		domain = handleDateTimes(domain);

		params.add(domain);
		params.add(0); // offset
		params.add(0); // set the max number of result items
		params.add(0); // another 0. is this offset to the results size? but
						// what would the first 0 be then?
						// if you know what this is, please tell me :)

		Object[] resultArray = null;
		try {
			Object result = xmlrpc.execute("execute", params);
			resultArray = (Object[]) result;
		} catch (XmlRpcException e) {
			e.printStackTrace();
			return null;
		}

		return resultArray;
	}

	/**
	 * Performs a search (kind of SELECT WHERE) on an OpenERP model (table).
	 * Special case: do not search anything, returns all ids.
	 * 
	 * @param model
	 *            model to search in
	 * @return null if no result, or Object[] castable to Integer[], list of IDs
	 */
	public Object[] search(String model) {
		// hm, can't figure out how to do "no" request. just all ids. So id>0
		// looks ok :)
		return search(model, new Object[] { new Object[] { "id", ">", new Integer(0) } });
	}

	/**
	 * Performs a search (kind of SELECT WHERE) on an OpenERP model (table).
	 * Simplification for searching only one parameter.
	 * 
	 * @param model
	 *            model to search in
	 * @param fieldname
	 *            fieldname to search for
	 * @param operator
	 *            operator to use in comparism
	 * @param compare
	 *            object to compare to
	 * @return null if no result, or Object[] castable to Integer[], list of IDs
	 */
	public Object[] search(String model, String fieldname, String operator, Object compare) {
		OpenERPDomain domain = new OpenERPDomain();
		domain.add(fieldname, operator, compare);
		return search(model, domain);
	}

	/**
	 * Read (SELECT) content from model
	 * 
	 * @param model
	 *            model name to read from
	 * @param readlist
	 *            list of IDs to read (is Object[] here, but really Integer[] is
	 *            expected)
	 * @param fieldlist
	 *            list of field names to return
	 * @return null if empty, castable to HashMap(String,Object) otherwise
	 * @throws nothing
	 *             will print stack trace and return null
	 */
	public Object[] read(String model, Object[] readlist, Object[] fieldlist) {
		Vector<Object> params = createParams(model, "read");
		params.add(readlist);
		params.add(fieldlist);

		Object[] resultArray = null;
		try {
			Object result = xmlrpc.execute("execute", params);
			resultArray = (Object[]) result;
		} catch (XmlRpcException e) {
			e.printStackTrace();
			return null;
		}

		return resultArray;
	}

	/**
	 * Read (SELECT) content from model
	 * 
	 * @param model
	 *            model name to read from
	 * @param readlist
	 *            list of IDs to read (is Object[] here, but really Integer[] is
	 *            expected)
	 * @param fieldlist
	 *            list of field names to return
	 * @return null if empty, castable to HashMap(String,Object) otherwise
	 * @throws nothing
	 *             will print stack trace and return null
	 */
	public Object[] read(String model, Object[] readlist, String[] fieldlist) {
		Vector<Object> params = createParams(model, "read");
		params.add(readlist);
		params.add(fieldlist);

		Object[] resultArray = null;
		try {
			Object result = xmlrpc.execute("execute", params);
			resultArray = (Object[]) result;
		} catch (XmlRpcException e) {
			e.printStackTrace();
			return null;
		}

		return resultArray;
	}

	/**
	 * Read (SELECT) content from model
	 * 
	 * @param model
	 *            model name to read from
	 * @param readlist
	 *            list of IDs to read (is Object[] here, but really Integer[] is
	 *            expected)
	 * @param fieldlist
	 *            list of field names to return
	 * @return null if empty, castable to HashMap(String,Object) otherwise
	 * @throws nothing
	 *             will print stack trace and return null
	 */
	public OpenERPRecordSet readRecords(String model, Object[] readlist, Object[] fieldlist) {
		return new OpenERPRecordSet(this.read(model, readlist, fieldlist));
	}

	/**
	 * Read (SELECT) content from model
	 * 
	 * @param model
	 *            model name to read from
	 * @param readlist
	 *            list of IDs to read (is Object[] here, but really Integer[] is
	 *            expected)
	 * @param fieldlist
	 *            list of field names to return
	 * @return null if empty, castable to HashMap(String,Object) otherwise
	 * @throws nothing
	 *             will print stack trace and return null
	 */
	public OpenERPRecordSet readRecords(String model, Vector<Object> readlist, Object[] fieldlist) {
		return new OpenERPRecordSet(this.read(model, readlist.toArray(), fieldlist));
	}

	/**
	 * Read (SELECT) content from model
	 * 
	 * @param model
	 *            model name to read from
	 * @param readlist
	 *            list of IDs to read (is Object[] here, but really Integer[] is
	 *            expected)
	 * @param fieldlist
	 *            list of field names to return
	 * @return null if empty, castable to HashMap(String,Object) otherwise
	 * @throws nothing
	 *             will print stack trace and return null
	 */
	public OpenERPRecordSet readRecords(String model, Vector<Object> readlist, String[] fieldlist) {
		return new OpenERPRecordSet(this.read(model, readlist.toArray(), fieldlist));
	}

	/**
	 * Read (SELECT) content from model
	 * 
	 * @param model
	 *            model name to read from
	 * @param readlist
	 *            list of IDs to read (is Object[] here, but really Integer[] is
	 *            expected)
	 * @param fieldlist
	 *            list of field names to return
	 * @return null if empty, castable to HashMap(String,Object) otherwise
	 * @throws nothing
	 *             will print stack trace and return null
	 */
	public OpenERPRecordSet readRecords(String model, Object[] readlist, String[] fieldlist) {
		return new OpenERPRecordSet(this.read(model, readlist, fieldlist));
	}

	/**
	 * creates (INSERT) a new data record
	 * 
	 * @param model
	 *            model to create in
	 * @param data
	 *            key,value pairs to store
	 * @return id (primary key) of the data record created, or null if errors
	 *         occured
	 * @throws nothing
	 *             will print stack trace and return null
	 */
	public Integer create(String model, HashMap<String, Object> data) {
		Vector<Object> params = createParams(model, "create");

		data = handleDateTimes(data);
		params.add(data);

		Integer resultID = null;
		try {
			Object result = xmlrpc.execute("execute", params);
                        if(result instanceof Integer) {
                            resultID = (Integer) result;    
                        }
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
		return resultID;
	}

	/**
	 * Writes (UPDATE) to a single id
	 * 
	 * @param model
	 *            model to write to
	 * @param id
	 *            id to write/update
	 * @param values
	 *            map of key:value pairs. Data will be updated (overwritten).
	 *            You don't need to fill all fields available in the model.
	 * @return
	 */
	public Boolean write(String model, Integer id, HashMap<String, Object> values) {
		return write(model, new Integer[] { id }, values);
	}

	/**
	 * Writes (UPDATE) to multiple data records at once
	 * 
	 * @param model
	 *            model to write to
	 * @param id
	 *            ids to write/update
	 * @param values
	 *            map of key:value pairs. Data will be updated (overwritten).
	 *            You don't need to fill all fields available in the model.
	 * @return
	 */
	public Boolean write(String model, Integer[] ids, HashMap<String, Object> values) {
		Vector<Object> params = createParams(model, "write");
		params.add(ids);

		values = handleDateTimes(values);
		params.add(values);

		Boolean resultID = null;
		try {
			Object result = xmlrpc.execute("execute", params);
			resultID = (Boolean) result;
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}

		return resultID;

	}

	/**
	 * Deletes (unlink) to a single id
	 * 
	 * @param model
	 *            model to delete from
	 * @param id
	 *            id to unlink/delete
	 * @return
	 */
	public Boolean delete(String model, Integer id) {
		return unlink(model, new Integer[] { id });
	}

	/***
	 * Unlinks (DELETE) to multiple data records at once
	 * 
	 * @param model
	 *            model to delete from
	 * @param ids
	 *            ids to unlink/delete
	 * @return
	 */
	public Boolean unlink(String model, Integer[] ids) {
		Vector<Object> params = createParams(model, "unlink");
		params.add(ids);

		Boolean resultID = null;
		try {
			Object result = xmlrpc.execute("execute", params);
			resultID = (Boolean) result;
		} catch (XmlRpcException e) {
			e.printStackTrace();
			return null;
		}

		return resultID;

	}

	/**
	 * Calls a method
	 * 
	 * @param model
	 *            model that provides the method
	 * @param method
	 *            method to call
	 * @param methodParams
	 *            parameters for the method
	 * @return Object any return the method provides
	 */
	public Object methodCall(String model, String method, List<Object> methodParams) {
		Vector<Object> params = createParams(model, method);
		params.addAll(methodParams);

		Object result = null;
		try {
			result = xmlrpc.execute("execute", params);
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Calls a method
	 * 
	 * @param model
	 *            model that provides the method
	 * @param method
	 *            method to call
	 * @param methodParams
	 *            parameters for the method
	 * @return Object any return the method provides
	 */
	public Object methodCall(String model, String method, Object[] methodParams) {
		return methodCall(model, method, Arrays.asList(methodParams));
	}

	/**
	 * Get Time if object is {@link java.util.Date} or {@link java.sql.Date}
	 * 
	 * @see https://bugs.launchpad.net/openobject-server/+bug/397294
	 * @see https://bugs.launchpad.net/openobject-server/+bug/742439
	 * @param values
	 *            any {@link Object} to parse
	 * @return {@link Long} containing Date.getTime() or null
	 */
	private Long handleDateTime_getTime(Object value) {
		// get time
		Long d = null;
		if (value instanceof java.util.Date) {
			d = ((java.util.Date) value).getTime();
		}
		if (value instanceof java.sql.Date) {
			d = ((java.sql.Date) value).getTime();
		}
		return d;
	}

	private String handleDateTime_toString(Long d) {
		/*
		 * - For the formats, there are constants in the code, e.g. in
		 * server/tools
		 * 
		 * server/tools/misc.py (6.0.1)
		 * 
		 * DEFAULT_SERVER_DATE_FORMAT = "%Y-%m-%d"
		 * 
		 * DEFAULT_SERVER_TIME_FORMAT = "%H:%M:%S"
		 * 
		 * DEFAULT_SERVER_DATETIME_FORMAT = "%s %s" %
		 * (DEFAULT_SERVER_DATE_FORMAT, DEFAULT_SERVER_TIME_FORMAT)
		 */

		/*
		 * - For the timezone, keep in mind that all timestamps sent to the
		 * server must be expressed in the _server_'s timezone, not the
		 * timezone of the client. You can ask the server for its timezone
		 * by calling the common/timezone_get RPC method
		 */
		String timezone = getServerTimeZone();
		TimeZone tz = TimeZone.getTimeZone(timezone);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sdf.format(new java.util.Date(d + tz.getRawOffset()));
	}

	/**
	 * Converts DateTime to String
	 * 
	 * @see https://bugs.launchpad.net/openobject-server/+bug/397294
	 * @see https://bugs.launchpad.net/openobject-server/+bug/742439
	 * @param values
	 *            HashMap to parse
	 */
	private String handleDateTime(Object value) {
		Long d = handleDateTime_getTime(value);
		if (d == null)
			return null;
		return handleDateTime_toString(d);
	}

	/**
	 * Handles DateTime as Strings
	 * 
	 * @see https://bugs.launchpad.net/openobject-server/+bug/397294
	 * @see https://bugs.launchpad.net/openobject-server/+bug/742439
	 * @param values
	 *            Object array to parse
	 * @return Object array with handled dates.
	 */
	private Object[] handleDateTimes(Object[] values) {
		if (values == null)
			return null;

		Vector<Object> newValues = new Vector<Object>();

		for (int i = 0; i < values.length; i++) {
			Object value = values[i];

			String formattedDate = handleDateTime(value);
			if (formattedDate == null) {
				// no date
				newValues.add(value);
			} else {
				// was a date, converted to String
				newValues.add(formattedDate);
			}

		}

		return newValues.toArray();
	}

	/**
	 * Handles DateTime as Strings
	 * 
	 * @see https://bugs.launchpad.net/openobject-server/+bug/397294
	 * @see https://bugs.launchpad.net/openobject-server/+bug/742439
	 * @param values
	 *            HashMap to parse
	 * @return HashMap with handled dates.
	 */
	private HashMap<String, Object> handleDateTimes(HashMap<String, Object> values) {
		if (values == null)
			return null;
		@SuppressWarnings("unchecked")
		HashMap<String, Object> newValues = (HashMap<String, Object>) values.clone();
		Iterator<String> iKey = values.keySet().iterator();
		while (iKey.hasNext()) {
			String key = iKey.next();
			Object value = values.get(key);

			String formattedDate = handleDateTime(value);
			if (formattedDate == null) {
				newValues.put(key, value);
			} else {
				newValues.put(key, formattedDate);
			}
		}
		return newValues;
	}

	public int getOpenERPuserid() {
		return openERPuserid;
	}
}
