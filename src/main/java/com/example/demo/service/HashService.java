package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Component
public class HashService {
    private Logger logger= LoggerFactory.getLogger(HashService.class);

    public String getHashedValue(String data, String salt){
        byte[] hashedValue = null;
        final Integer iterationCount = 5000;
        final Integer keyLength = 128;

        KeySpec spec=new PBEKeySpec(data.toCharArray(),salt.getBytes(),iterationCount,keyLength);
        try{
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedValue = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(e.getMessage());
        }
        return Base64.getEncoder().encodeToString(hashedValue);
    }
}
