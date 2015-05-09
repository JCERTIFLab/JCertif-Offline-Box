/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.offlinebox.web;

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
public class ConnexionInternetStatusTest {
    
    public ConnexionInternetStatusTest() {
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
     * Test of isConnectionAvailable method, of class ConnexionInternetStatus.
     */
    @Test
    public void testIsConnectionAvailable() {
        System.out.println("isConnectionAvailable");
        ConnexionInternetStatus instance = new ConnexionInternetStatus();
        boolean expResult = true;
        boolean result = instance.isConnectionAvailable();
        assertEquals(expResult, result); 
    }
    
}
