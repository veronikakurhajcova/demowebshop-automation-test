package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import demowebshop.utils.PropertiesReader;
import helpers.TestFlowHelper;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.IndexPage;
import pages.RegisterPage;
import utils.RetryAnalyzer;
import utils.Utils;


public class RegisterValidTest extends BaseTest {

	HeaderPage headerPage;
	IndexPage indexPage;
	RegisterPage registerPage;
	DashboardPage dashboardPage;
	
	String randomEmail;

	public RegisterValidTest() {
		
		super();
		
		try {
			testDataReader = new PropertiesReader("src/test/resources/testdata/validUser.properties");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("validUser.properties file not found or could not be loaded", e);
		}
	}

	@BeforeMethod
	public void setup() {
		
		// Initialize the browser and navigate to the URL
		
		log.info("Initializing browser and navigating to the URL.");
		initializeBrowser();
		headerPage = new HeaderPage();
		indexPage = new IndexPage();
		registerPage = new RegisterPage();
		dashboardPage = new DashboardPage();
		
		randomEmail = Utils.generateRandomEmail();
	}

	@Test(description = "Valid user registration with correct data", retryAnalyzer = RetryAnalyzer.class)
	public void testValidRegistration() {

		log.info("Starting valid user registration test.");
		headerPage.clickRegisterLink();

		log.info("Filling in registration form with valid data.");
		registerPage.registerUser(testDataReader.getProperty("valid.firstname"),
				testDataReader.getProperty("valid.lastname"), randomEmail,
				testDataReader.getProperty("valid.password"));

		log.info("Verifying registration result message and URL.");
		String actualMessage = registerPage.getResultMessage();
		Assert.assertEquals(actualMessage, "Your registration completed",
				"Registration message does not match expected.");
		
		Assert.assertTrue(getDriver().getCurrentUrl().contains("registerresult"),
				"URL does not contain 'registerresult' after registration.");
		
		Assert.assertTrue(registerPage.isRegisteredCustomerInfoDisplayed(),
				"Registered customer info is not displayed.");
		
		Assert.assertEquals(registerPage.getRegisteredCustomerInfo(), randomEmail,
				"Registered customer info does not match expected.");
		
		log.info("Registration completed successfully with expected data.");
		
		log.info("Clicking continue button to finish registration.");
		registerPage.clickContinue();
		
		Assert.assertEquals(dashboardPage.getDashboardUrl(), dashboardReader.getProperty("dashboard.url"),
				"Dashboard url after registration does not match expected.");
	}

	@AfterMethod
	public void logout() {
		
		// logout user after registration
		
		 TestFlowHelper.logout(dashboardPage, indexPage, configReader);
		 
		 log.info("Closing the browser after test completion.");
		 quitDriver();
	}

}
