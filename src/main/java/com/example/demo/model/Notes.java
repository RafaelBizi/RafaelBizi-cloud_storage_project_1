package com.example.demo.model;

import lombok.Data;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Data
public class Notes {

    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public Notes(String title, String description) {
        this.noteTitle = title;
        this.noteDescription = description;
    }
}
