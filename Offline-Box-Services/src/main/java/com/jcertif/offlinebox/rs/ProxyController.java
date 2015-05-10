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
	
    @RequestMapping("/getConfig")
    public Proxy getProxyConfig() {	
        return offlineBoxConfig.getConfig().getProxy();
    }
    
    @RequestMapping("/setConfig")
    public boolean setProxyStatut(@RequestParam(value = "newStatut", required = false) Boolean newStatut) {	
        try{
            Config newConfiguration = offlineBoxConfig.getConfig();
            newConfiguration.setProxy(new Proxy(newStatut));
            offlineBoxConfig.saveConfiguration(newConfiguration, null);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
}