package com.example.demo.config;
import org.springframework.context.annotation.Configuration;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Configuration
public class FileStoreConfig {

    private String pathUpload = "./uploads/";

    public String getUploadDir() {
        return pathUpload;
    }

    public void setUploadDir(String pathUpload) {
        this.pathUpload = pathUpload;
    }
}

