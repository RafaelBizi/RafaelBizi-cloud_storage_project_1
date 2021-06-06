package com.example.demo.service;

import com.example.demo.mapper.CredentialMapper;
import com.example.demo.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getCredentials(int userId) {
        return credentialMapper.getCredentials(userId);
    }

    public int createCredential(Credential credential) {
        return credentialMapper.insertCredential(credential);
    }

    public void updateCredential(Credential credential) {
        credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(int noteId) {
        credentialMapper.deleteCredential(noteId);
    }
}
