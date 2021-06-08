package com.example.demo;

import com.example.demo.config.FileStoreConfig;
import com.example.demo.mapper.FileMapper;
import com.example.demo.model.File;
import com.example.demo.service.implimentation.FileServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author RafaelBizi
 * @created 07/06/2021 - 08:15
 * @project cloud-storage-project-1
 */

public class FileTests extends ApplicationTests {

    @Autowired
    FileMapper fileMapper;

    @Test
    public void creatingAndViewNewFile() throws IOException, InterruptedException {
        HomePage homePage = getHomePage();
        FileStoreConfig fileStoreConfig = new FileStoreConfig();
        FileServiceImp fileServiceImp = new FileServiceImp(fileStoreConfig, fileMapper);
        Resource resource = fileServiceImp.loadFile("testing.txt");
        String url = resource.getURL().getPath();
        homePage.chooseFile(url);
        homePage.uploadFile();
        List<File> allFiles = fileServiceImp.getAllFiles(1);
        boolean isEmpty = allFiles.isEmpty();
        Assertions.assertEquals(false, isEmpty);
    }

    @Test
    public void viewFile() throws InterruptedException {
        HomePage homePage = getHomePage();
        Thread.sleep(1000);
        homePage.viewFile();
        Set<String> windowHandles = driver.getWindowHandles();
        assertEquals(2, windowHandles.size());
    }

    @Test
    public void deleteFile() throws InterruptedException {
        HomePage homePage = getHomePage();
        Thread.sleep(1000);
        homePage.deleteFile();
        FileServiceImp fileServiceImp = new FileServiceImp(new FileStoreConfig(), fileMapper);
        List<File> allFiles = fileServiceImp.getAllFiles(1);
        Assertions.assertEquals(0, allFiles.size());
    }

}
