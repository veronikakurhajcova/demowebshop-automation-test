package tests.positiveTests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import helpers.TestFlowHelper;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.IndexPage;
import pages.LoginPage;
import utils.RetryAnalyzer;

public class LoginPositiveTest extends BaseTest {

	IndexPage indexPage;
	HeaderPage headerPage;
	LoginPage loginPage;
	DashboardPage dashboardPage;

	public LoginPositiveTest() {

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
	public void loginWithValidCredentialsShouldSucceed() {

		log.info("Starting valid user login test");
		headerPage.clickLoginLink();

		log.info("Filling login form with valid data");
		loginPage.loginUser(testDataReader.getProperty("valid.email"),
				testDataReader.getProperty("valid.password"));

		Assert.assertEquals(dashboardPage.getCurrentUrl(), configReader.getProperty("url"),
				"Current URL does not match dashboard URL after login");

		Assert.assertEquals(dashboardPage.getLoggedCustomerInfo(), testDataReader.getProperty("valid.email"),
				"Displayed customer info does match with valid logged user info");

	}

	@AfterMethod
	public void logoutLoggedUser() {

		TestFlowHelper.logout(dashboardPage, indexPage, configReader);

		log.info("Closing the browser after logout completion.");
		quitDriver();

	}

}
