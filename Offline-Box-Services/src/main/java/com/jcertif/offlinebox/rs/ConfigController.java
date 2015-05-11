package com.jcertif.offlinebox.rs;

import com.jcertif.offlinebox.beans.Config;
import com.jcertif.offlinebox.beans.WebSite;
import com.jcertif.offlinebox.configuration.OfflineBoxConfig;
import com.jcertif.offlinebox.configuration.WebSitesConfig;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {
    
    private final OfflineBoxConfig offlineBoxConfig;
    
    private final WebSitesConfig webSitesConfig;
    
    public ConfigController(){
        offlineBoxConfig = OfflineBoxConfig.getInstance();
        webSitesConfig = WebSitesConfig.getInstance();
    }
	
    @RequestMapping("/getOfflineBoxConfig")
    public Config getOfflineBoxConfig() {	
	return offlineBoxConfig.getConfig();
    }
    
    @RequestMapping("/getListWebSites")
    public List<WebSite> getListWebSites() {	
	return webSitesConfig.getListeWebSites();
    }
    
    @RequestMapping("/addWebSite")
    public boolean addWebSite(@RequestParam(value = "webSite", required = true) WebSite webSite) {	
	try{
            webSitesConfig.getListeWebSites().add(webSite);
            webSitesConfig.saveConfiguration(webSitesConfig.getListeWebSites());
            return true;
        }catch(Exception exception){
            return false;
        }
    }
    
}