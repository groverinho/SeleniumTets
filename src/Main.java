package src;

import org.openqa.selenium.By;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;

public class Main {

    private WebDriver webDriver;
    String chromePath = System.getProperty("user.dir") + "/driver/chromedriver";
    String baseURL = "https://codility-frontend-prod.s3.amazonaws.com/media/task_static/qa_login_page/9a83bda125cd7398f9f482a3d6d45ea4/static/attachments/reference_page.html";

    @Before
    public void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", chromePath);
        webDriver = new ChromeDriver();
        System.out.println("Open website");
        webDriver.manage().window().maximize();
        webDriver.get(baseURL);
    }

    String validCredentials = "login@codility.com";
    String invalidCredentials = "unknown@codility.com";
    String invalidEmail = "invalidEmail.com";
    String password = "password";
    String emptyCredentials = "";

    /**
     * Method to verify if element is displaying on the page
     *
     * @param element the element
     * @return true, if the element is displayed
     */
    public boolean isDisplayed(WebElement element) {
        return element != null && element.isDisplayed();
    }

    /**
     * Method to verify the email input
     *
     * @return the element
     */
    public WebElement verifyEmailInput() {
        WebElement element = webDriver.findElement(By.id("email-input"));
        Assert.assertTrue("The email input is not displayed", isDisplayed(element));
        return element;
    }

    /**
     * Method to verify the password input
     *
     * @return the element
     */
    public WebElement verifyPasswordInput() {
        WebElement element = webDriver.findElement(By.id("password-input"));
        Assert.assertTrue("The password input is not displayed", isDisplayed(element));
        return element;
    }

    /**
     * Method to verify the login button
     *
     * @return the element
     */
    public WebElement verifyLoginButton() {
        WebElement element = webDriver.findElement(By.id("login-button"));
        Assert.assertTrue("The login button is not displayed", isDisplayed(element));
        return element;
    }

    /**
     * Method to evaluate the login process
     *
     * @param userName the userName
     * @param password the password
     */
    public void loginProcess(String userName, String password) {
        verifyEmailInput().sendKeys(userName);
        verifyPasswordInput().sendKeys(password);
        verifyLoginButton().click();
    }

    /**
     * 1. Check if the email and password fields are on the main screen of the application
     */
    @Test
    public void testLoginFormPresent() {

        verifyEmailInput();
        verifyPasswordInput();
        verifyLoginButton();

    }

    /**
     * 2. Check if the given valid credentials work:
     */
    @Test
    public void testGiveValidCredentials() {

        loginProcess(validCredentials, password);

        WebElement element = webDriver.findElement(By.cssSelector("div[class='message success']"));
        Assert.assertTrue("The element is not displayed", isDisplayed(element));

        boolean isDisplayed = element.getText().equals("Welcome to Codility");
        Assert.assertTrue("The message is not visible", isDisplayed);

    }

    /**
     * 3. Check if the given wrong credentials work:
     */
    @Test
    public void testWrongCredentials() {

        loginProcess(invalidCredentials, password);

        WebElement element = webDriver.findElement(By.cssSelector("div[class='message error']"));
        Assert.assertTrue("The element is not displayed", isDisplayed(element));

        boolean isDisplayed = element.getText().equals("You shall not pass! Arr!");
        Assert.assertTrue("The message is not visible", isDisplayed);

    }

    /**
     * 4. Check if the email validation is working:
     */
    @Test
    public void testEmailValidation() {

        loginProcess(invalidEmail, password);

        WebElement element = webDriver.findElement(By.cssSelector("div[class='validation error']"));
        Assert.assertTrue("The element is not displayed", isDisplayed(element));

        boolean isDisplayed = element.getText().equals("Enter a valid email");
        Assert.assertTrue("The message is not visible", isDisplayed);

    }

    /**
     * 5. Check for empty credentials:
     */
    @Test
    public void testCheckEmptyCredentials() {

        loginProcess(emptyCredentials, emptyCredentials);

        WebElement element = webDriver.findElement(By.xpath("//*[@id=\"messages\"]/div[1]"));
        Assert.assertTrue("The element is not displayed", isDisplayed(element));

        boolean isDisplayed = element.getText().equals("Email is required");
        Assert.assertTrue("The 'Email is required' message is not visible", isDisplayed);

        element = webDriver.findElement(By.xpath("//*[@id=\"messages\"]/div[2]"));
        Assert.assertTrue("The element is not displayed", isDisplayed(element));

        isDisplayed = element.getText().equals("Password is required");
        Assert.assertTrue("The 'Password is required' message is not visible", isDisplayed);

    }

    @After
    public void tearDown() {
        webDriver.close();
    }
}
