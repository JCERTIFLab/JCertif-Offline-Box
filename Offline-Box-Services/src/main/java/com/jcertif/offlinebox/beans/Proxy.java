package com.jcertif.offlinebox.beans;

import lombok.Data;

@Data
public class Proxy {
    
    private boolean activate;
    
    public Proxy(boolean activate){
        this.activate = activate;
    }
    
}
