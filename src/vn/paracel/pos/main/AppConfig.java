/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.main;

import java.io.*;
import java.util.Properties;
import org.slf4j.Logger;

/**
 *
 * @author rgv151
 */
public class AppConfig {
    private static Logger logger = AppGlobal.getLogger(AppConfig.class);
    
    private Properties prop;
    private File configfile;

    public AppConfig() {
        init(getDefaultConfig());        
    }
    
    public AppConfig(File configfile) {
        init(configfile);
    }
    
    private void init(File configfile) {
        this.configfile = configfile;
        prop = new Properties();

        logger.info("Reading configuration file: " + configfile.getAbsolutePath());
    }
    
    private File getDefaultConfig() {
        //return new File(new File(System.getProperty("user.home")), AppGlobal.APP_ID + ".properties");
        return new File(AppGlobal.APP_ID + ".conf");
    }
    
    public String getProperty(String sKey) {
        return prop.getProperty(sKey);
    }
    
    public File getConfigFile() {
        return configfile;
    }
    
    public void setProperty(String sKey, String sValue) {
        if (sValue == null) {
            prop.remove(sKey);
        } else {
            prop.setProperty(sKey, sValue);
        }
    }
       
    public boolean delete() {
        loadDefault();
        return configfile.delete();
    }
    
    public void load() {

        loadDefault();

        try {
            InputStream in = new FileInputStream(configfile);
            if (in != null) {
                prop.load(in);
                in.close();
            }
        } catch (IOException e){
            loadDefault();
        }
    
    }
    
    public void save() throws IOException {
        
        OutputStream out = new FileOutputStream(configfile);
        if (out != null) {
            prop.store(out, AppGlobal.APP_NAME + ". Configuration file.");
            out.close();
        }
    }
    
    private void loadDefault() {
        
        prop = new Properties();

        prop.setProperty("backend.host", "localhost");
        prop.setProperty("backend.port", "8069");
        prop.setProperty("backend.database", "paracelpos");
        prop.setProperty("backend.user", "admin");
        prop.setProperty("backend.password", "1");

        prop.setProperty("app.language", "vi");
        prop.setProperty("app.country", "VN");
        prop.setProperty("app.variant", "");     
        
        prop.setProperty("app.laf", System.getProperty("swing.defaultlaf", "com.seaglasslookandfeel.SeaGlassLookAndFeel"));
    }    
}