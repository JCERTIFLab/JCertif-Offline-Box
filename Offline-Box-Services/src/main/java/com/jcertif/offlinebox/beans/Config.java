package com.jcertif.offlinebox.beans;
 
import lombok.Data;

/**
 * <p>Configuration Aggregation</p>
 *
 * @author Firas GABSI
 * @since 1.0
 */

@Data
public class Config {
    
    private Proxy proxy;
    
    private Crowing crowing;
    
    private Storage storage;

    public Config() {
        
    }

    public Config(Proxy proxy, Crowing crowing, Storage storage){
        this.proxy = proxy;
        this.crowing = crowing;
        this.storage = storage;
    }
    
}
