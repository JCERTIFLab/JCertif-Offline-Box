package com.jcertif.offlinebox.configuration;

import com.jcertif.offlinebox.beans.Config;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Firas GABSI
 */
public class OfflineBoxConfigTest {
    
    private final OfflineBoxConfig offlineBoxConfig;
    
    public OfflineBoxConfigTest() {
        offlineBoxConfig = OfflineBoxConfig.getInstance();
    }

    @Test
    public void testLoadConfig() {
        Config config= offlineBoxConfig.getConfig();
        Assert.assertNotNull(config);
    }
    
    @Test
    public void testSaveConfig() {
        offlineBoxConfig.getConfig().getProxy().setActivate(true);
        offlineBoxConfig.getConfig().getCrowing().setActivate(true);
        offlineBoxConfig.getConfig().getStorage().setMaxUsageLimit(99.99);
        offlineBoxConfig.saveConfiguration();
    }
    
    @Test
    public void testRestConfig(){
        offlineBoxConfig.resetConfig();
    }
    
}
