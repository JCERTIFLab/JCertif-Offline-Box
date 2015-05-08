package com.jcertif.offlinebox.beans;

import java.util.Arrays;
import java.util.Collection;
import lombok.Data;

@Data
public class Crowing {
    
    private boolean activate;
    
    private Collection<WebSite> listWebSites;
    
    public Crowing(boolean activate, WebSite...listWebSites){
        this.activate = activate;
        this.listWebSites = Arrays.asList(listWebSites);
    }
    
}
