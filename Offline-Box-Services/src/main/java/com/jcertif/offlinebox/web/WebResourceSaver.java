package com.jcertif.offlinebox.web;

import java.io.InputStream;
import java.util.concurrent.RecursiveAction;
import lombok.Getter;

/**
 *
 * @author Martial SOMDA
 * @since 1.0
 */
@Getter
public class WebResourceSaver extends RecursiveAction implements Runnable{

    private String content;
    private InputStream streamContent;
    private final String fileName;

    public WebResourceSaver(String content, String fileName) {
        this.content = content;
        this.fileName = fileName;
    }
    
    public WebResourceSaver(InputStream streamContent, String fileName) {
        this.streamContent = streamContent;
        this.fileName = fileName;
    }
    
    @Override
    public void run() {
        saveContent();
    }
    @Override
    protected void compute() {
        saveContent();
    }

    private void saveContent() {
        if(streamContent != null){
//            OfflineBoxStorage.getInstance().saveContent(streamContent, fileName);
        }
        else if(content != null && content.length() > 0){
//            OfflineBoxStorage.getInstance().saveContent(content, fileName);
        }
        
    }

}
