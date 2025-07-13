package tests.negativeTests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import pages.HeaderPage;
import pages.IndexPage;
import pages.RegisterPage;
import utils.RetryAnalyzer;
import utils.Utils;

public class RegisterInvalidTests extends BaseTest {

	IndexPage indexPage;
	HeaderPage headerPage;
	RegisterPage registerPage;
	String randomEmail;

	public RegisterInvalidTests() {

		super();

	}

	@BeforeMethod
	public void setup() {

		initializeBrowser();

		indexPage = new IndexPage();
		headerPage = new HeaderPage();
		registerPage = new RegisterPage();

		log.info("Starting valid user registration test.");
		headerPage.clickRegisterLink();
		randomEmail = Utils.generateRandomEmail();

	}

	@Test(description = "Test with empty credentials", retryAnalyzer = RetryAnalyzer.class)
	public void registerWithEmptyCredentials() {

		registerPage.clickRegisterButton();

		log.info("Send registration with emtpy data");
		Assert.assertTrue(registerPage.isFirstNameRequiredWarningMessageDisplayed(),
				"Firstname warning message is not displayed");
		Assert.assertEquals(registerPage.getFirstNameWarningMessageText(),
				messageReader.getProperty("register.firstname.required"),
				"Firstname warning message in registration does not match");

		Assert.assertTrue(registerPage.isLastNameRequiredWarningMessageDisplayed(),
				"LastName warning message is not displayed");
		Assert.assertEquals(registerPage.getLastNameWarningMessageText(),
				messageReader.getProperty("register.lastname.required"),
				"Lastname warning message  in registration does not match");

		Assert.assertTrue(registerPage.isEmailRequiredWarningMessageDisplayed(),
				"Email warning message is not displayed");
		Assert.assertEquals(registerPage.getEmailWarningMessageText(),
				messageReader.getProperty("register.email.required"),
				"Email warning message in registration does not match");

		Assert.assertTrue(registerPage.isPasswordRequiredWarningMessageDisplayed(),
				"Password warning message is not displayed");
		Assert.assertEquals(registerPage.getPasswordWarningMessageText(),
				messageReader.getProperty("register.password.required"),
				"Password warning message in registration does not match");

		Assert.assertTrue(registerPage.isConfirmPasswordWarningMessageDisplayed(),
				"Confirm Password warning message is not displayed");
		Assert.assertEquals(registerPage.getConfirmPasswordWarningMessageText(),
				messageReader.getProperty("register.confirmpassword.required"),
				"Confirm password warning message in registration does not match");

	}

	@Test(description = "Registration with empty firstname field", retryAnalyzer = RetryAnalyzer.class)
	public void registerWithEmptyFirstNameField() {

		registerPage.registerUser("", testDataReader.getProperty("valid.lastname"), randomEmail,
				testDataReader.getProperty("valid.password"), testDataReader.getProperty("valid.confirmpassword"));

		 Assert.assertTrue(registerPage.isErrorMessageDisplayed(registerPage.firstNameIsRequiredMessage),
		            "Firstname warning message is not displayed");
		    Assert.assertEquals(registerPage.getFirstNameWarningMessageText(),
		            messageReader.getProperty("register.firstname.required"),
		            "Firstname warning message in registration does not match");

		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.lastNameIsRequiredMessage),
		            "Lastname warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.emailRequiredMessage),
		            "Email warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.passwordRequiredMessage),
		            "Password warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.confirmPasswordRequiredMessage),
		            "Confirm password warning should not be displayed");

	}

	@Test(description = "Registration with empty lastname field", retryAnalyzer = RetryAnalyzer.class)
	public void registerWithEmptyLastNameField() {

		registerPage.registerUser(testDataReader.getProperty("valid.firstname"), "", randomEmail,
				testDataReader.getProperty("valid.password"), testDataReader.getProperty("valid.confirmpassword"));

		 Assert.assertTrue(registerPage.isErrorMessageDisplayed(registerPage.lastNameIsRequiredMessage),
		            "Lastname warning message is not displayed");
		    Assert.assertEquals(registerPage.getLastNameWarningMessageText(),
		            messageReader.getProperty("register.lastname.required"),
		            "Lastname warning message in registration does not match");

		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.firstNameIsRequiredMessage),
		            "Lastname warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.emailRequiredMessage),
		            "Email warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.passwordRequiredMessage),
		            "Password warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.confirmPasswordRequiredMessage),
		            "Confirm password warning should not be displayed");


	}

	@Test(description = "Registration with empty email field", retryAnalyzer = RetryAnalyzer.class)
	public void registerWithEmptyEmailField() {

		registerPage.registerUser(testDataReader.getProperty("valid.firstname"),
				testDataReader.getProperty("valid.lastname"), "", testDataReader.getProperty("valid.password"),
				testDataReader.getProperty("valid.confirmpassword"));

		 Assert.assertTrue(registerPage.isErrorMessageDisplayed(registerPage.emailRequiredMessage),
		            "Lastname warning message is not displayed");
		    Assert.assertEquals(registerPage.getEmailWarningMessageText(),
		            messageReader.getProperty("register.email.required"),
		            "Email warning message in registration does not match");

		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.firstNameIsRequiredMessage),
		            "Lastname warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.lastNameIsRequiredMessage),
		            "Email warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.passwordRequiredMessage),
		            "Password warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.confirmPasswordRequiredMessage),
		            "Confirm password warning should not be displayed");

	}

	@Test(description = "Registration with empty password field", retryAnalyzer = RetryAnalyzer.class)
	public void registerWithEmptyPasswordField() {

		registerPage.registerUser(testDataReader.getProperty("valid.firstname"),
				testDataReader.getProperty("valid.lastname"), randomEmail, "",
				testDataReader.getProperty("valid.confirmpassword"));

		 Assert.assertTrue(registerPage.isErrorMessageDisplayed(registerPage.passwordRequiredMessage),
		            "Password warning message is not displayed");
		    Assert.assertEquals(registerPage.getPasswordWarningMessageText(),
		            messageReader.getProperty("register.password.required"),
		            "Password warning message in registration does not match");

		    Assert.assertTrue(registerPage.isErrorMessageDisplayed(registerPage.confirmPasswordRequiredMessage),
					"Confirm password warning should be displayed");
			Assert.assertEquals(registerPage.getConfirmPasswordWarningMessageText(),
					messageReader.getProperty("register.confirmpassword.required"),
					"Confirm password warning message in registration does not match");
			
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.firstNameIsRequiredMessage),
		            "Firstname warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.lastNameIsRequiredMessage),
		    		"Lastname warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.emailRequiredMessage),
		            "Email warning should not be displayed");

	}

	@Test(description = "Registration with empty confirmpassword field", retryAnalyzer = RetryAnalyzer.class)
	public void registerWithEmptyConfirmPasswordField() {

		registerPage.registerUser(testDataReader.getProperty("valid.firstname"),
				testDataReader.getProperty("valid.lastname"), randomEmail, testDataReader.getProperty("valid.password"),
				"");

		 Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.confirmPasswordRequiredMessage),
		            "Confirm password warning message is not displayed");
		    Assert.assertEquals(registerPage.getConfirmPasswordWarningMessageText(),
		            messageReader.getProperty("register.confirmpassword.required"),
		            "Confirm password warning message in registration does not match");


		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.firstNameIsRequiredMessage),
		            "Firstname warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.lastNameIsRequiredMessage),
		    		"Lastname warning should not be displayed");
		    Assert.assertFalse(registerPage.isErrorMessageDisplayed(registerPage.emailRequiredMessage),
		            "Email warning should not be displayed");
		    
		    log.info("Confirm password warning not checked because password is missing.");

	}

    

}
