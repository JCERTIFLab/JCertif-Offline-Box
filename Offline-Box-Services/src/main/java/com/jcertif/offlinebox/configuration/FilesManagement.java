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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import lombok.NoArgsConstructor;

/**
 *
 * @author Komi Serge Innocent <komi.innocent@gmail.com>
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
            throw new IllegalArgumentException("Le fichier" + path + "à écrire n'existe pas");
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
}
