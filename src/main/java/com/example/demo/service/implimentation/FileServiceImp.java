package com.example.demo.service.implimentation;

import com.example.demo.config.FileStoreConfig;
import com.example.demo.exception.FileStorageException;
import com.example.demo.exception.FileNotFoundException;
import com.example.demo.mapper.FileMapper;
import com.example.demo.model.File;
import com.example.demo.model.User;
import com.example.demo.service.FileService;
import com.example.demo.utils.ConstantsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Service
public class FileServiceImp implements FileService {
    private Logger logger =  LoggerFactory.getLogger(FileServiceImp.class);
    private final Path fileLocation;

    @Autowired
    private FileMapper fileMapper;

    public FileServiceImp(FileStoreConfig fileLocation, FileMapper fileMapper) {
        this.fileMapper = fileMapper;
        this.fileLocation = Paths.get(fileLocation.getUploadDir()).toAbsolutePath().normalize();
        logger.info("Location is: "+this.fileLocation);
        try{
            Files.createDirectories(this.fileLocation);
            logger.info("Location of the file is: ", this.fileLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not create a folder to store files", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file, User user) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        logger.info("----------- File information ----------",
                "Size = {} ", file.getSize(), 
                "ContentType = {} ", file.getContentType(),
                "Name = {} ", file.getName(),
                "Content (bytes) = {}", file.getBytes());
        try {
            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            fileMapper.insertFile(new File(null,fileName,file.getContentType(),Long.toString(file.getSize()),user.getUserId(),file.getBytes()));
        } catch (IOException e){
            throw new FileStorageException(String.format(ConstantsUtils.FILE_STORAGE_EXCEPTION, fileName), e);
        }

        return fileName;
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException(ConstantsUtils.FILE_NOT_FOUND + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException(ConstantsUtils.FILE_NOT_FOUND + fileName, ex);
        }
    }

    @Override
    public File findFile(String fileName) {
        return fileMapper.getFile(fileName);
    }

    @Override
    public File getFileById(Integer idFile){
        return fileMapper.getFileById(idFile);
    }

    @Override
    public List<File> getAllFiles(int userId){
        return fileMapper.getAllFiles(userId);
    }

    @Override
    public Integer deleteFile(int id) {

        return fileMapper.deleteFile(id);
    }
}
