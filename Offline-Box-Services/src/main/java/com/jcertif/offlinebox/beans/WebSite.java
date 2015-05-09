package com.jcertif.offlinebox.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


public class WebSite {
    
    private String url;

    public WebSite() {
    }
        
    public WebSite(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
}
