package com.example.demo;

import com.example.demo.mapper.CredentialMapper;
import com.example.demo.model.Credential;
import com.example.demo.service.CredentialService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.demo.utils.ConstantsUtils.*;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

public class CredentialTest extends ApplicationTests {

    @Autowired
    CredentialMapper credentialMapper;

    @Test
    public void createCredentialTest() throws InterruptedException {
        HomePage homePage = getHomePage();
        Thread.sleep(1000);
        createAndVerifyCredential(URL, USERNAME, PASSWORD, homePage);
        Thread.sleep(1000);
        homePage.deleteCredential();
        homePage.logout();
    }

    @Test
    public void readUpdateCredentialTest() throws InterruptedException {
        HomePage homePage = getHomePage();
        createAndVerifyCredential(URL, USERNAME, PASSWORD, homePage);
        Credential credential = homePage.getFirstCredential();
        String firstEncryptedPassword = credential.getPassword();
        homePage.editCredential();
        String newUrl = EDIT_URL;
        String newCredentialUsername = EDIT_USERNAME;
        String newPassword = EDIT_PASSWORD;
        setCredentialFields(newUrl, newCredentialUsername, newPassword, homePage);
        homePage.saveCredentialChanges();

        CredentialService credentialService = new CredentialService(credentialMapper);
        List<Credential> credentials = credentialService.getCredentials(1);
        boolean isEmpty = credentials.isEmpty();
        Assertions.assertEquals(false, isEmpty);

        homePage.navToCredentialsTab();
        Credential modifiedCredential = homePage.getFirstCredential();
        Assertions.assertEquals(newUrl, modifiedCredential.getUrl());
        Assertions.assertEquals(newCredentialUsername, modifiedCredential.getUsername());
        String modifiedCredentialPassword = modifiedCredential.getPassword();
        Assertions.assertNotEquals(newPassword, modifiedCredentialPassword);
        Assertions.assertNotEquals(firstEncryptedPassword, modifiedCredentialPassword);
        homePage.deleteCredential();
        homePage.logout();
    }

    @Test
    public void deletionCredentialTest() throws InterruptedException {
        HomePage homePage = getHomePage();
        createCredential(URL, USERNAME, PASSWORD, homePage);
        createCredential(EDIT_URL, EDIT_USERNAME, EDIT_PASSWORD, homePage);
        createCredential("http://www.gmail.com/", "teste3", "teste3", homePage);
        Assertions.assertFalse(homePage.noCredentials(driver));
        for (int i = 0; i<=2; i++){
            homePage.deleteCredential();
            homePage.navToCredentialsTab();
            Thread.sleep(500);
        }
        Assertions.assertTrue(homePage.noCredentials(driver));
        homePage.logout();

        CredentialService credentialService = new CredentialService(credentialMapper);
        List<Credential> credentials = credentialService.getCredentials(1);
        boolean isEmpty = credentials.isEmpty();
        Assertions.assertEquals(true, isEmpty);
    }

    private void createAndVerifyCredential(String url, String username, String password, HomePage homePage) throws InterruptedException {
        createCredential(url, username, password, homePage);
        homePage.navToCredentialsTab();
        Credential credential = homePage.getFirstCredential();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(username, credential.getUsername());
        Assertions.assertNotEquals(password, credential.getPassword());
    }

    private void createCredential(String url, String username, String password, HomePage homePage) throws InterruptedException {
        homePage.navToCredentialsTab();
        homePage.addNewCredential();
        setCredentialFields(url, username, password, homePage);
        Thread.sleep(500);
        homePage.saveCredentialChanges();
        Thread.sleep(500);
        homePage.navToCredentialsTab();
    }

    private void setCredentialFields(String url, String username, String password, HomePage homePage) {
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
    }
}
