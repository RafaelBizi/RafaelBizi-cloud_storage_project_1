package com.example.demo;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

public class LoginPage {
    private final JavascriptExecutor js;
    @FindBy(css="#inputUsername")
    private WebElement usernameField;

    @FindBy(css="#inputPassword")
    private WebElement passwordField;

    @FindBy(css="#submit-button")
    private WebElement submitButton;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    public String getInputUsername(){
        return this.usernameField.getAttribute("value");
    }

    public void setInputUsername(String inputUsername){
        this.usernameField.sendKeys(inputUsername);
    }

    public void setInputPassword(String inputPassword){
        this.passwordField.sendKeys(inputPassword);
    }

    public String checkLoginPageExists(){
        return this.loginLink.getText();
    }

    public void clickLoginButton(){
        this.submitButton.click();
    }

    public LoginPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        js = (JavascriptExecutor) webDriver;
    }

    public void login(String userName, String password) {
        js.executeScript("arguments[0].value='"+ userName +"';", usernameField);
        js.executeScript("arguments[0].value='"+ password +"';", passwordField);
        js.executeScript("arguments[0].click();", submitButton);
    }
}
