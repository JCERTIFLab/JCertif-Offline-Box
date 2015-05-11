/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.offlinebox.configuration;

import com.jcertif.offlinebox.beans.Config;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Firas GABSI
 */
public class OfflineBoxConfigTest {
    
    public OfflineBoxConfigTest() {
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
        OfflineBoxConfig boxConfig = OfflineBoxConfig.getInstance();
        Config config= boxConfig.getConfig();
        Assert.assertNotNull(config);
    }
    
}
