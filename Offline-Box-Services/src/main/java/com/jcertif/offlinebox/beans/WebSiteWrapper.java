package com.jcertif.offlinebox.beans;

import java.util.List;
import lombok.Data;

/**
 * A wrapper class that contains list of {@link WebSite}, the purpose of this class is to 
 * unmarshall the websites value from the config file
 * @author Komi Serge Innocent <komi.innocent@gmail.com>
 */
 
@Data
public class WebSiteWrapper {

    private List<WebSite> webSites;

    public WebSiteWrapper() {
    
    }
}
