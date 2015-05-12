package com.jcertif.offlinebox.rs;

import com.jcertif.offlinebox.beans.Config;
import com.jcertif.offlinebox.beans.Proxy;
import com.jcertif.offlinebox.configuration.OfflineBoxConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyController {
    
    private final OfflineBoxConfig offlineBoxConfig;
    
    public ProxyController(){
        offlineBoxConfig = OfflineBoxConfig.getInstance();
    }
	
    @RequestMapping("/getProxyConfig")
    public Proxy getProxyConfig() {	
        return offlineBoxConfig.getConfig().getProxy();
    }
    
    @RequestMapping("/setProxyStatut")
    public boolean setProxyStatut(@RequestParam(value = "newStatut", required = true) Boolean newStatut) {	
        try{
            offlineBoxConfig.getConfig().setProxy(new Proxy(newStatut));
            offlineBoxConfig.saveConfiguration();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    @RequestMapping("/startProxy")
    public boolean startProxy() {	
        
        return true; 
    }
    
    @RequestMapping("/stopProxy")
    public boolean stopProxy() {	
        
        return true; 
    }
}