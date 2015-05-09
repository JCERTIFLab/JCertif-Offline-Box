package com.jcertif.offlinebox.rs;

import com.jcertif.offlinebox.beans.Proxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyController {
	
    @RequestMapping("/config")
    public Proxy getProxyCnofig() {	
        Proxy proxy = new Proxy(true);
	return proxy;
    }
    
}