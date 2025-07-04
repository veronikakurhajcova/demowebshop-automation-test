package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import pages.IndexPage;
import pages.RegisterPage;

public class RegisterValidTest extends BaseTest {
	
	private static final Logger log = LogManager.getLogger(RegisterValidTest.class);

	IndexPage indexPage;
	RegisterPage registerPage;

	public RegisterValidTest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		// Initialize the browser and navigate to the URL
		log.info("Initializing browser and navigating to the URL.");
		initializeBrowser();
		indexPage = new IndexPage();
		registerPage = new RegisterPage();
	}

	@Test(description = "Valid user registration with correct data")
	public void testValidRegistration() {
		
		log.info("Starting valid user registration test.");
		indexPage.clickRegisterLink();
		
		log.info("Filling in registration form with valid data.");
		registerPage.registerUser(
			testDataReader.getProperty("valid.firstname"),
			testDataReader.getProperty("valid.lastname"),
			testDataReader.getProperty("valid.email"),
			testDataReader.getProperty("valid.password")
		);
		
		log.info("Verifying registration result message and URL.");
		String actualMessage = registerPage.getResultMessage();
		Assert.assertEquals(actualMessage, "Your registration completed", "Registration message does not match expected.");
		Assert.assertTrue(getDriver().getCurrentUrl().contains("registerresult"), "URL does not contain 'registerresult' after registration.");
		
		log.info("Clicking continue button to finish registration.");
		registerPage.clickContinue();
	}
	
	@AfterMethod
	public void tearDown() {
		log.info("Closing the browser after test completion.");
	    quitDriver();
	}


}
