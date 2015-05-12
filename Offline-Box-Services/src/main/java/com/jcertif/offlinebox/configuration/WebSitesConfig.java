package com.jcertif.offlinebox.configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcertif.offlinebox.beans.WebSite; 
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;  
import com.jcertif.offlinebox.beans.WebSiteWrapper;
import java.io.File;
/**
 *
 * @author Komi Serge Innocent <komi.innocent@gmail.com>
 */
public class WebSitesConfig {
    
    private static final String WEB_SITES_FILE_NAME = "webSites.json";
    private static final String CONFIG_DIRECTRORY_NAME = "Config";
    
    private static final WebSitesConfig INSTANCE = new WebSitesConfig();
    
    private final FilesManagement filesManagement;
    
    private List<WebSite> listWebSites;
    
    private WebSitesConfig(){
        filesManagement = new FilesManagement();
    }

    public static WebSitesConfig getInstance() {
        return INSTANCE;
    }
    
    public List<WebSite> getListeWebSites(){
        if(listWebSites == null){
            listWebSites = retieveWebSiteFromConfigFile();
        }
        return listWebSites;
    }
    
    public void setListWebSites(List<WebSite> listWebSites){
        this.listWebSites = listWebSites;
    }

    private List<WebSite> retieveWebSiteFromConfigFile() {
        
        List<WebSite> sites = null;
        
        try {
            InputStream in = WebSitesConfig.class.getResourceAsStream(CONFIG_DIRECTRORY_NAME+"\\"+WEB_SITES_FILE_NAME);
            final ObjectMapper objectMapper = new ObjectMapper();
            WebSiteWrapper webSiteWrapper = objectMapper.readValue(in, WebSiteWrapper.class);            
            sites = webSiteWrapper.getWebSites();
        } catch (JsonParseException e) {
            Logger.getLogger(WebSitesConfig.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(WebSitesConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sites;
    }
    
    public void saveListWebSites() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            filesManagement.makeDirectories(new File(CONFIG_DIRECTRORY_NAME+"\\"+WEB_SITES_FILE_NAME));
            File configFile = new File(CONFIG_DIRECTRORY_NAME, WEB_SITES_FILE_NAME);
            if (!configFile.exists()) {
                filesManagement.createFile(configFile);
            }
            mapper.writeValue(configFile, listWebSites);
        } catch (IOException ex) {
            Logger.getLogger(OfflineBoxConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
