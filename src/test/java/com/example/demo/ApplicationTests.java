package com.example.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.example.demo.utils.ConstantsUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTests {

    protected static WebDriver driver;
    @LocalServerPort
    private int port;

    private SignupPage signupPage;
    private LoginPage loginPage;

    @BeforeAll
    static void beforeAll(){
         WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach(){
        this.driver =new ChromeDriver();
    }

    @AfterEach
    public void afterEach(){
        if(this.driver != null){
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void userNotAuthorizedToAccess(){
        driver.get("http://localhost:" + this.port + "/home");
        assertFalse(driver.getTitle() == "Home");

        driver.get("http://localhost:"+this.port+"/home");
        assertEquals("Login",driver.getTitle() );
    }

    @Test
    @Order(2)
    public void testSignupSuccess() throws InterruptedException {
        driver.get("http://localhost:" + port + "/signup");
        signupPage = new SignupPage(driver);
        signupPage.setInputFirstName(FIRSTNAME);
        signupPage.setInputLastName(LASTNAME);
        signupPage.setInputUserName(USERNAME);
        signupPage.setInputPassword(PASSWORD);
        assertEquals(FIRSTNAME, signupPage.getInputFirstName());
        assertEquals(LASTNAME, signupPage.getInputLastName());
        assertEquals(USERNAME,signupPage.getInputUserName());
        assertEquals(PASSWORD,signupPage.getInputPassword());
        signupPage.clickSubmit();
    }

    @Test
    @Order(3)
    public void testLoginSuccess() throws InterruptedException {
        driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(driver);
        assertEquals("Click here to sign up",loginPage.checkLoginPageExists());
        Boolean exists = driver.findElements(By.id("login-link")).size() != 0;
        assertEquals(true, exists);
        loginPage.setInputUsername(USERNAME);
        loginPage.setInputPassword(PASSWORD);
        loginPage.clickLoginButton();
    }

    @Test
    @Order(4)
    public void userSignupAndLogin() throws InterruptedException {

        signup();
        login();
        assertEquals("Home", driver.getTitle());

        HomePage homePage = new HomePage(driver);
        Thread.sleep(500);
        homePage.logout();

        driver.get("http://localhost:" + this.port + "/home");
        assertFalse(driver.getTitle() == "Home");
        assertEquals("Login", driver.getTitle());

    }

    public void signup(){
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signUp(FIRSTNAME,LASTNAME,USERNAME,PASSWORD);
    }
    public void login(){
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME,PASSWORD);
    }
     public HomePage getHomePage(){
         signup();
         login();
         return new HomePage(driver);
     }

}
