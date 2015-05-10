package com.jcertif.offlinebox.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcertif.offlinebox.beans.Config;
import com.jcertif.offlinebox.beans.Crowing;
import com.jcertif.offlinebox.beans.Proxy;
import com.jcertif.offlinebox.beans.Storage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger; 
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class OfflineBoxConfig {

    private static final OfflineBoxConfig INSTANCE = new OfflineBoxConfig();
    
    private static final String CONFIG_FILE_NAME = "offline-box-config.json";
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

    private Config getConfiguration() {
        
        JSONParser parser = new JSONParser();
        
        Config configObject = new Config();
        Crowing crowing = new Crowing();
        Storage storeg = new Storage();
        Proxy proxy = new Proxy();
 
        try {
 
            Object obj = parser.parse(new FileReader(CONFIG_FILE_NAME));
 
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
 
        } catch (IOException | ParseException e) {
        }
 
        return configObject;
    }

    public void saveConfiguration(Config configuration, File configDir) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            filesManagement.makeDirectories(configDir);
            File configFile = new File(configDir, CONFIG_FILE_NAME);
            if (!configFile.exists()) {
                filesManagement.createFile(configFile);
            }
            mapper.writeValue(configFile, configuration);
        } catch (IOException ex) {
            Logger.getLogger(OfflineBoxConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
