package com.jcertif.offlinebox.beans;

import lombok.Data;

@Data
public class Storage {
    
    private double maxUsageLimit;
    
    private String path;
    
    public Storage(double maxUsageLimit, String path){
        this.maxUsageLimit = maxUsageLimit;
        this.path = path;
    }
    
}
