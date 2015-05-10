package com.jcertif.offlinebox.beans;

import lombok.Data;

/**
 * <p>Storage Configuration</p>
 *
 * @author Firas GABSI
 * @since 1.0
 */

@Data
public class Storage {
    
    private double maxUsageLimit;
    
    private String path;
    
    public Storage() {
        
    }
    
    public Storage(double maxUsageLimit, String path){
        this.maxUsageLimit = maxUsageLimit;
        this.path = path;
    }
    
}
