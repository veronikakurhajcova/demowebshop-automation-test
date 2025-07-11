package tests.negativeTests;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import demowebshop.base.BaseTest;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.IndexPage;
import pages.LoginPage;

public class LoginInvalidTests extends BaseTest {

    IndexPage indexPage;
    HeaderPage headerPage;
    LoginPage loginPage;
    DashboardPage dashboardPage;
    SoftAssert softAssert;

    public LoginInvalidTests() {
        super();
    }

    @BeforeMethod
    public void setup() {
        initializeBrowser();
        indexPage = new IndexPage();
        headerPage = new HeaderPage();
        dashboardPage = new DashboardPage();
        loginPage = new LoginPage();
        softAssert = new SoftAssert();
    }

    @Test(dataProvider = "invalidLoginData", 
    	      dataProviderClass = providers.DataProviders.class, 
    	      description = "Login with invalid or empty credentials")
    	public void loginWithInvalidCredentialsTest(String email, String password, String description,
    	                                            String[] expectedErrorMessages) {

    	    List<String> expectedErrorsList = Arrays.asList(expectedErrorMessages);

    	    log.info("Starting user login test: " + description);
    	    headerPage.clickLoginLink();

    	    // Enter email
    	    loginPage.enterEmailInvalidLogin(email);
    	    loginPage.focusPasswordField();

    	    String firstExpectedError = expectedErrorsList.get(0);
    	    if ("Please enter a valid email address.".equals(firstExpectedError)) {
    	        softAssert.assertTrue(loginPage.isErrorMessageForValidEmailDisplayed(), "Email validation message missing");
    	        softAssert.assertEquals(loginPage.getErrorMessageForValidEmailText(), firstExpectedError);
    	    } else {

    	        log.info("First expected error is not email validation, je to: " + firstExpectedError);
    	    }

    	    if (!password.isEmpty()) {
    	        loginPage.enterPasswordInvalidLogin(password);
    	    }

    	    loginPage.clickLoginButton();

    	    List<String> actualErrors = loginPage.getAllErrorMessagesText();

    	    List<String> expectedErrorsWithoutFirst = expectedErrorsList.subList(1, expectedErrorsList.size());

    	    softAssert.assertTrue(actualErrors.size() >= expectedErrorsWithoutFirst.size(),
    	            "Number of validation messages is less than expected");

    	    for (String expected : expectedErrorsWithoutFirst) {
    	        softAssert.assertTrue(actualErrors.contains(expected),
    	                "Expected error message not found: " + expected);
    	    }

    	    softAssert.assertAll();
    	}


    @AfterMethod
    public void tearDown() {
        log.info("Closing the browser after invalid login");
        quitDriver();
    }
}
