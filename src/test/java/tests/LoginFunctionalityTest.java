package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.IndexPage;
import pages.LoginPage;
import utils.RetryAnalyzer;

public class LoginFunctionalityTest extends BaseTest {

	IndexPage indexPage;
	HeaderPage headerPage;
	LoginPage loginPage;
	DashboardPage dashboardPage;

	public LoginFunctionalityTest() {

		super();

	}

	@BeforeMethod
	public void setup() {

		initializeBrowser();
		indexPage = new IndexPage();
		headerPage = new HeaderPage();
		dashboardPage = new DashboardPage();
		loginPage = new LoginPage();

	}

	@Test(description = "Valid user login with correct data", retryAnalyzer = RetryAnalyzer.class)
	public void testValidLogin() {

		log.info("Starting valid user login test");
		headerPage.clickLoginLink();

		log.info("Filling login form with valid data");
		loginPage.loginRegisteredUser(testDataReader.getProperty("valid.email"),
				testDataReader.getProperty("valid.password"));
		
		Assert.assertEquals(dashboardPage.getCurrentUrl(), configReader.getProperty("url"),
				"Current URL does not match dashboard URL after login");

		Assert.assertEquals(dashboardPage.getLoggedCustomerInfo(), testDataReader.getProperty("valid.email"),
				"Displayed customer info does match with valid logged user info");

	}

	@AfterMethod
	public void logoutLoggedUser() {
		// logout user after login

		try {
			dashboardPage.clickLogoutButton();
			log.info("User logged out successfully.");

			Assert.assertEquals(indexPage.getCurrentUrl(), dashboardReader.getProperty("dashboard.url"),
					"URL after logout does not match the base URL.");
		} catch (Exception e) {
			log.warn("Logout was not possible"
					+ e.getMessage());
		}
	}

	@AfterMethod
	public void tearDown() {

		log.info("Closing the browser after logout completion.");
		quitDriver();
		
	}

}
