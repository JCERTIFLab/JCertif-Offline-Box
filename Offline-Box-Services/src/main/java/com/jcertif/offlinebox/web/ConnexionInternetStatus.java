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
package com.jcertif.offlinebox.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Komi Serge Innocent <komi.innocent@gmail.com>
 */
public class ConnexionInternetStatus {

    /**
     * Check the availability of internet connection
     * @return true if connection is available
     */
    public boolean isConnectionAvailable() {
        boolean status = false;
        try {
            URL url = new URL("https://www.google.fr");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            System.out.println("response code "+con.getResponseCode()+" message "+con.getResponseMessage());
            if (con.getResponseCode() == 200) {
                status = true;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConnexionInternetStatus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnexionInternetStatus.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

}
