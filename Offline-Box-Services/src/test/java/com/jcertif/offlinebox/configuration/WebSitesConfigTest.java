/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.offlinebox.configuration;

import com.jcertif.offlinebox.beans.WebSite;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Komi Serge Innocent <komi.innocent@gmail.com>
 */
public class WebSitesConfigTest {
    
    public WebSitesConfigTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of retieveFromConfigFile method, of class WebSitesConfig.
     */
    @Test
    public void testRetieveFromConfigFile() {
        System.out.println("retieveFromConfigFile");
        WebSitesConfig instance = WebSitesConfig.getInstance(); 
        List<WebSite> result = instance.getListeWebSites();
        assertNotNull(result);        
        assertEquals(3, result.size()); 
    }
    
}
