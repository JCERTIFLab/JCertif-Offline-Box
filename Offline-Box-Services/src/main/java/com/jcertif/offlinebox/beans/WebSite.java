package com.jcertif.offlinebox.beans;

import lombok.Data;

@Data
public class WebSite {
    
    private String url;
    
    public WebSite(String url){
        this.url = url;
    }
    
}
