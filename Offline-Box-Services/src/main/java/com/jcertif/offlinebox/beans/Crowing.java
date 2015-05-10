package com.jcertif.offlinebox.beans;

import java.util.Arrays;
import java.util.List;
import lombok.Data;

/**
 * <p>Crowing Configuration</p>
 *
 * @author Firas GABSI
 * @since 1.0
 */

@Data
public class Crowing {
    
    private boolean activate;
    
    private List<WebSite> listWebSites;
    
    public Crowing() {
        
    }
    
    public Crowing(boolean activate, WebSite...listWebSites){
        this.activate = activate;
        this.listWebSites = Arrays.asList(listWebSites);
    }

}
