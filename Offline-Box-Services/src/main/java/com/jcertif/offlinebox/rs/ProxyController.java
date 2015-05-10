package com.jcertif.offlinebox.rs;

import com.jcertif.offlinebox.beans.Proxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyController {
	
    @RequestMapping("/config")
    public Proxy getProxyConfig() {	
        Proxy proxy = new Proxy(true);
	return proxy;
    }
    
    @RequestMapping("/config")
    public boolean setProxyStatut(@RequestParam(value = "newStatut", required = false) Boolean newStatut) {	
        try{
            Proxy proxy = new Proxy(newStatut);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
}