package com.jcertif.offlinebox.beans;

import lombok.Data;

/**
 * <p>Proxy Configuration</p>
 *
 * @author Firas GABSI
 * @since 1.0
 */

@Data
public class Proxy {
    
    private boolean activate;

    public Proxy() {
        
    }
    
    public Proxy(boolean activate){
        this.activate = activate;
    }
    
}
