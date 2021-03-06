package com.example.demo.mapper;

import com.example.demo.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM files WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Select("SELECT * FROM files WHERE fileId = #{fileId}")
    File getFileById(Integer id);

    @Insert("INSERT INTO files ( filename,userid,contenttype,filesize,filedata) VALUES(#{fileName}, #{owner},#{contentType},#{fileSize},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Select("SELECT * FROM files WHERE userid = #{owner}")
    List<File> getAllFiles(int userId);

    @Delete("DELETE FROM files WHERE fileId = #{fileId}")
    Integer deleteFile(int id);
}
