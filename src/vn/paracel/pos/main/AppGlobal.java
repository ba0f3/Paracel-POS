/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.main;

import com.bremskerl.de.interfaces.openerp.OpenERP;
import java.net.MalformedURLException;
import java.util.Locale;
import org.apache.xmlrpc.XmlRpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

/**
 *
 * @author rgv151
 */
public class AppGlobal {
    public static Logger logger = LoggerFactory.getLogger(AppGlobal.class);
    public static I18n i18n = null;
    public static Locale locale = null;
    
    private static String backendHost = "";
    private static String backendDatabase = "";
    private static String backendUsername = "";
    private static String backendPassword = "";
    
    public static final String APP_NAME = "Paracel POS";
    public static final String APP_ID = "paracelpos";
    public static final String APP_VERSION = "0.12.3";
        
    public static Locale getLocale() {
        if(locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }
    
    public static void setLocale(Locale l) {
        logger.debug("Set application language to: " + l.getDisplayName());
        locale = l;
    }
    
    public static I18n getI18n(Class c) {
        if(i18n == null) {
            i18n = I18nFactory.getI18n(c, "vn.paracel.pos.i18n.Messages", AppGlobal.getLocale());
        }
        return i18n;
    }
    
    public static OpenERP getOpenERPClient() {
        OpenERP openerp = null; 
        try {
            openerp = new OpenERP(getBackendHost(), getBackendDatabase(), getBackendUsername(), getBackendPassword());
        } catch (MalformedURLException ex) {
            logger.info(ex.getMessage());
        } catch (XmlRpcException ex) {
            logger.error(ex.getMessage());
        } catch (ClassCastException ex) {
            logger.error("Unable to connect to the backend!");
            System.exit(1);
        }
        return openerp;        
    }
    
    public static Logger getLogger(Class c) {
        return LoggerFactory.getLogger(c);
    }

    public static String getBackendDatabase() {
        return backendDatabase;
    }

    public static void setBackendDatabase(String backendDatabase) {
        AppGlobal.backendDatabase = backendDatabase;
    }

    public static String getBackendHost() {
        return backendHost;
    }

    public static void setBackendHost(String backendHost) {
        AppGlobal.backendHost = backendHost;
    }

    public static String getBackendPassword() {
        return backendPassword;
    }

    public static void setBackendPassword(String backendPassword) {
        AppGlobal.backendPassword = backendPassword;
    }

    public static String getBackendUsername() {
        return backendUsername;
    }

    public static void setBackendUsername(String backendUsername) {
        AppGlobal.backendUsername = backendUsername;
    }
    
    
}
