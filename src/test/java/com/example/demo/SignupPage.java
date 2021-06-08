package com.example.demo;

import lombok.Data;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

public class SignupPage {

    private final JavascriptExecutor js;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUserName;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    public JavascriptExecutor getJs() {
        return js;
    }

    public String getInputFirstName() {
        return this.inputFirstName.getAttribute("value");
    }

    public void setInputFirstName(String inputFirstName) {
        this.inputFirstName.sendKeys(inputFirstName);
    }

    public String getInputLastName() {
        return this.inputLastName.getAttribute("value");
    }

    public void setInputLastName(String inputLastName) {
        this.inputLastName.sendKeys(inputLastName);
    }

    public String getInputUserName() {
        return this.inputUserName.getAttribute("value");
    }

    public void setInputUserName(String inputUserName) {
        this.inputUserName.sendKeys(inputUserName);
    }

    public String getInputPassword() {
        return this.inputPassword.getAttribute("value");
    }

    public void setInputPassword(String inputPassword) {
        this.inputPassword.sendKeys(inputPassword);
    }

    public void clickSubmit(){
        submitButton.click();
    }

    public void clickLoginLink(){
        loginLink.click();
    }



    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public void signUp(String firstName,String lastName,String userName,String password) {
        js.executeScript("arguments[0].value='"+ firstName +"';", inputFirstName);
        js.executeScript("arguments[0].value='"+ lastName +"';", inputLastName);
        js.executeScript("arguments[0].value='"+ userName +"';", inputUserName);
        js.executeScript("arguments[0].value='"+ password +"';", inputPassword);
        js.executeScript("arguments[0].click();", submitButton);
    }

}

