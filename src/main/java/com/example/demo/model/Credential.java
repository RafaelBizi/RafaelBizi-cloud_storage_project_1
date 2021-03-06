package com.example.demo.model;

import lombok.Data;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Data
public class Credential {
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userId;

    public Credential(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


}
