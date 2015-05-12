package com.jcertif.offlinebox.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcertif.offlinebox.beans.Config;
import com.jcertif.offlinebox.beans.Crowing;
import com.jcertif.offlinebox.beans.Proxy;
import com.jcertif.offlinebox.beans.Storage;
import com.jcertif.offlinebox.beans.WebSite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger; 
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class OfflineBoxConfig {

    private static final OfflineBoxConfig INSTANCE = new OfflineBoxConfig();
    
    private static final String CONFIG_FILE_NAME = "offline-box-config.json";
    private static final String CONFIG_DIRECTRORY_NAME = "Config";
    
    private static final String PROXY = "proxy";
    private static final String CROWING = "crowing";
    private static final String STORAGE = "storage";
    private static final String ACTIVATED = "activated";
    private static final String MAX_USAGE_LIMIT = "maxUsageLimit";
    private static final String PATH = "path";
    
    private final FilesManagement filesManagement;
    
    private Config config = null;
    
    private OfflineBoxConfig(){
        filesManagement = new FilesManagement();
    }

    public static OfflineBoxConfig getInstance() {
        return INSTANCE;
    }
    
    public Config getConfig(){
        if(config == null){
            config = getConfiguration();
        }
        return config;
    }
    
    public void setConfig(Config config){
        this.config = config;
    }

    private Config getConfiguration() {
        
        JSONParser parser = new JSONParser();
        
        Config configObject = new Config();
        Crowing crowing = new Crowing();
        Storage storeg = new Storage();
        Proxy proxy = new Proxy();
 
        try {
 
            Object obj = parser.parse(new FileReader(CONFIG_DIRECTRORY_NAME+"\\"+CONFIG_FILE_NAME));
 
            JSONObject jsonObject = (JSONObject) obj;
 
            JSONObject proxyConfg = (JSONObject) jsonObject.get(PROXY);
            proxy.setActivate((Boolean) proxyConfg.get(ACTIVATED));
            
            JSONObject crowingConfg = (JSONObject) jsonObject.get(CROWING);
            crowing.setActivate((Boolean) crowingConfg.get(ACTIVATED));
            
            JSONObject storageConfg = (JSONObject) jsonObject.get(STORAGE);
            storeg.setMaxUsageLimit((Double) storageConfg.get(MAX_USAGE_LIMIT));
            storeg.setPath((String) storageConfg.get(PATH));
            
            configObject.setProxy(proxy);
            configObject.setCrowing(crowing);
            configObject.setStorage(storeg);
 
        } catch (IOException | ParseException ex) {
            Logger.getLogger(OfflineBoxConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return configObject;
    }

    public void saveConfiguration() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            filesManagement.makeDirectories(new File(CONFIG_DIRECTRORY_NAME+"\\"+CONFIG_FILE_NAME));
            File configFile = new File(CONFIG_DIRECTRORY_NAME, CONFIG_FILE_NAME);
            if (!configFile.exists()) {
                filesManagement.createFile(configFile);
            }
            mapper.writeValue(configFile, config);
        } catch (IOException ex) {
            Logger.getLogger(OfflineBoxConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void resetConfig(){
        this.config = getInitialConfig();
        saveConfiguration();
    }
    
    private Config getInitialConfig(){
        Config initConfig = new Config();
        
        Proxy proxy = new Proxy();
        proxy.setActivate(false);
        initConfig.setProxy(proxy);
        
        Crowing crowing = new Crowing();
        crowing.setActivate(false);
        crowing.setListWebSites(new ArrayList<WebSite>());
        initConfig.setCrowing(crowing);
        
        Storage storeg = new Storage();
        storeg.setPath("c://Crowing//");
        storeg.setMaxUsageLimit(100);
        initConfig.setStorage(storeg);
        
        return initConfig;
    }

}
