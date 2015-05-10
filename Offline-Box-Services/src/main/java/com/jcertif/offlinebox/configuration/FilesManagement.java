package com.jcertif.offlinebox.configuration;

import io.netty.buffer.ByteBuf;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.NoArgsConstructor;

/**
 * <p>
 * CRUD Service for retrieving and storing configuration.</p>
 *
 * @author Martial SOMDA
 * @author Komi Serge Innocent <komi.innocent@gmail.com>
 * @since 1.0
 */

@NoArgsConstructor
public class FilesManagement {

    /**
     * This method save the stream passed as argument to the desired file
     * @param jsonStream the json stream to save
     * @param path the path of the file the stream should be saved to
     * @throws IOException 
     */
    public void saveJsonStream(String jsonStream, String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(jsonStream);
        } else {
            throw new IllegalArgumentException("Le fichier" + path + " n'existe pas");
        }
    }
 
    public void makeDirectories(File file) {
        File folder = new File(file.getParent());
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    
    /**
     * @param fileName
     * @param filePath
     * @return true if the file is created
     * @throws IOException 
     */
    public boolean createFile(String fileName, String filePath) throws IOException{
        File file=new File(filePath+File.pathSeparator+fileName);
        return file.createNewFile();
    }
    
    void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(OfflineBoxConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File openFile(String filePath) {
        File file = new File(filePath);
        makeDirectories(file);
        if (!file.exists()) {
            createFile(file);
        }
        return file;
    }

    public void closeFile(FileOutputStream outputStream) {
        try {
            outputStream.getChannel().close();
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(OfflineBoxConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveContent(ByteBuf buff, FileOutputStream outputStream) {
        if (buff != null) {
            try {
                FileChannel fileChannel = outputStream.getChannel();
                ByteBuffer byteBuffer = buff.nioBuffer();
                fileChannel.write(byteBuffer);
                fileChannel.force(true);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(OfflineBoxConfig.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(OfflineBoxConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void saveContent(InputStream in, String filePath) {
        if (in != null) {
            File file = new File(filePath);
            makeDirectories(file);
            try (OutputStream outputStream = new FileOutputStream(file)) {
                int read;
                byte[] bytes = new byte[1024];

                while ((read = in.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

            } catch (IOException ex) {
                Logger.getLogger(OfflineBoxConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void saveContent(String content, String filePath) {
        if (content != null) {
            File file = new File(filePath);
            makeDirectories(file);
            try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
                output.write(content);
            } catch (IOException ex) {
                Logger.getLogger(OfflineBoxConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
