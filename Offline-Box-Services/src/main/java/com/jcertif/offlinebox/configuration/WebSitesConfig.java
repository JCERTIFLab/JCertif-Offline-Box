/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jcertif.offlinebox.configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcertif.offlinebox.beans.WebSite; 
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;  


/**
 *
 * @author Komi Serge Innocent <komi.innocent@gmail.com>
 */
public class WebSitesConfig {

    /**
     * 
     * @return 
     */
    public List<WebSite> retieveWebSiteFromConfigFile() {
        
        List<WebSite> sites = null;
        
        try {
            
            InputStream in = WebSitesConfig.class.getResourceAsStream("webSites.json");
            final ObjectMapper objectMapper = new ObjectMapper();
            WebSiteWrapper webSiteWrapper = objectMapper.readValue(in, WebSiteWrapper.class);            
            sites = webSiteWrapper.webSites;
            
        } catch (JsonParseException e) {
            Logger.getLogger(WebSitesConfig.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(WebSitesConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sites;
    }
}
