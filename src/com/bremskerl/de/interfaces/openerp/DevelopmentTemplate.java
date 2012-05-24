package com.bremskerl.de.interfaces.openerp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import com.bremskerl.de.interfaces.openerp.OpenERP;

public class DevelopmentTemplate {

	// use java logging
	private static Logger jLog = Logger.getLogger(DevelopmentTemplate.class.getName());
	
	// OpenERP connection
	OpenERP openerp;
	
	// fallback connections
	private static String DEFAULT_OPENERP_HOST = "127.0.0.1";
	private static String DEFAULT_OPENERP_DB = "test1";
	private static String DEFAULT_OPENERP_USER = "admin";
	private static String DEFAULT_OPENERP_PASSWORD = "admin";
	
	public static void main(String[] args) {
		// use defaults
		String host = DEFAULT_OPENERP_HOST;
		String db = DEFAULT_OPENERP_DB;
		String user = DEFAULT_OPENERP_USER;
		String password = DEFAULT_OPENERP_PASSWORD;
			
		// use config file if present (overwrites previous values)
		Properties configFile = new Properties();
		try {
			InputStream propFile = DevelopmentTemplate.class.getClassLoader().getResourceAsStream("/openerp.properties");
			if (propFile != null) {
				configFile.load(propFile);
			}
			if (configFile.getProperty("host") != null) host = configFile.getProperty("host");
			if (configFile.getProperty("db") != null) host = configFile.getProperty("db");
			if (configFile.getProperty("user") != null) host = configFile.getProperty("user");
			if (configFile.getProperty("password") != null) host = configFile.getProperty("password");
		} catch (IOException e1) {
			// could not read file - most likely, there is none, so I'll ignore this.
			jLog.config("Could not find or open openerp.properties config file. Ignoring.");
		}
		
		// use arguments if present (overwrites previous values)
		if (args != null) {
			if (args.length >= 1) host = args[1];
			if (args.length >= 2) db = args[2];
			if (args.length >= 3) user = args[3];
			if (args.length >= 4) password = args[4];
		}
		
		
		String conString = user+":"+password+"@"+host+"/"+db;
		
		jLog.config("Connecting OpenERP: " + conString);
		OpenERP o = null;
		try {
			o = new OpenERP(host, db, user, password);
		} catch (Exception e) {
			jLog.severe("Could not connect to OpenERP using " + conString);
			e.printStackTrace();
			System.exit(-1);
		}
		
		DevelopmentTemplate obj = new DevelopmentTemplate(o);
		obj.run();
	}
	
	public DevelopmentTemplate(OpenERP o) {
		openerp = o;
	}
	
	public void run() {
		// do something
	}
	
}
