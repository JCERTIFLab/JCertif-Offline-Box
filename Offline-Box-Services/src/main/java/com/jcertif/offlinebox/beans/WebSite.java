package com.jcertif.offlinebox.beans;

import lombok.Data;

/**
 * <p>WebSite Configuration</p>
 *
 * @author Firas GABSI
 * @since 1.0
 */

@Data
public class WebSite {
    
    private String url;
    
    public WebSite(){}

    public WebSite(String url) {
        this.url = url;
    }
 
}