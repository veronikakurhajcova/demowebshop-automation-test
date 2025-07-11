package pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import demowebshop.base.BasePage;

public class RegisterPage extends BasePage {

	public RegisterPage() {
		super();
	}

	// Elements
	@FindBy(id = "gender-female")
	private WebElement femaleRadioButton;

	@FindBy(id = "FirstName")
	private WebElement firstNameInput;

	@FindBy(id = "LastName")
	private WebElement lastNameInput;

	@FindBy(id = "Email")
	private WebElement emailInput;

	@FindBy(id = "Password")
	private WebElement passwordInput;

	@FindBy(id = "ConfirmPassword")
	private WebElement confirmPasswordInput;

	@FindBy(id = "register-button")
	private WebElement registerButton;

	@FindBy(xpath = "//div[@class='result']")
	private WebElement registrationResultMessage;

	@FindBy(xpath = "(//a[@href='/customer/info'])[1]")
	private WebElement registeredCustomerInfo;

	@FindBy(css = "input[value='Continue']")
	public WebElement continueButton;

	// Elements for register error messages
	@FindBy(css = "span[data-valmsg-for='FirstName'] >span")
	public WebElement firstNameIsRequiredMessage;

	@FindBy(css = "span[data-valmsg-for='LastName'] >span")
	public WebElement lastNameIsRequiredMessage;

	@FindBy(css = "span[data-valmsg-for='Email'] > span")
	public WebElement emailRequiredMessage;

	@FindBy(css = "span[data-valmsg-for='Password'] > span")
	public WebElement passwordRequiredMessage;

	@FindBy(css = "span[data-valmsg-for='ConfirmPassword'] > span")
	public WebElement confirmPasswordRequiredMessage;

	// Actions registration user

	public void registerUser(String firstName, String lastName, String email, String password, String confirmPassword) {

		waitForElementToBeVisible(firstNameInput);
		waitForElementToBeClickable(femaleRadioButton);
		click(femaleRadioButton);
		sendKeys(firstNameInput, firstName);
		sendKeys(lastNameInput, lastName);
		sendKeys(emailInput, email);
		sendKeys(passwordInput, password);
		sendKeys(confirmPasswordInput, password);
		click(registerButton);

	}

	public String getResultMessage() {

		waitForElementToBeVisible(registrationResultMessage);
		return registrationResultMessage.getText();

	}

	public void clickContinue() {

		waitForElementToBeClickable(continueButton);
		click(continueButton);

	}

	public void clickRegisterButton() {

		waitForElementToBeClickable(registerButton);
		click(registerButton);

	}

	public boolean isRegisteredCustomerInfoDisplayed() {

		try {
			waitForElementToBeVisible(registeredCustomerInfo);
			return registeredCustomerInfo.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getRegisteredCustomerInfo() {

		waitForElementToBeVisible(registeredCustomerInfo);
		String actualUsername = registeredCustomerInfo.getText().trim();
		return actualUsername;

	}

	// Register Warning Messages methods

	public boolean isFirstNameRequiredWarningMessageDisplayed() {

		waitForElementToBeVisible(firstNameIsRequiredMessage);
		return firstNameIsRequiredMessage.isDisplayed();

	}

	public boolean isLastNameRequiredWarningMessageDisplayed() {

		waitForElementToBeVisible(lastNameIsRequiredMessage);
		return lastNameIsRequiredMessage.isDisplayed();

	}

	public boolean isEmailRequiredWarningMessageDisplayed() {

		waitForElementToBeVisible(emailRequiredMessage);
		return emailRequiredMessage.isDisplayed();

	}

	public boolean isPasswordRequiredWarningMessageDisplayed() {

		waitForElementToBeVisible(passwordRequiredMessage);
		return passwordRequiredMessage.isDisplayed();

	}

	public boolean isConfirmPasswordWarningMessageDisplayed() {

		waitForElementToBeVisible(confirmPasswordRequiredMessage);
		return confirmPasswordRequiredMessage.isDisplayed();

	}

	public String getFirstNameWarningMessageText() {

		waitForElementToBeVisible(firstNameIsRequiredMessage);
		return firstNameIsRequiredMessage.getText();

	}

	public String getLastNameWarningMessageText() {

		waitForElementToBeVisible(lastNameIsRequiredMessage);
		return lastNameIsRequiredMessage.getText();

	}

	public String getEmailWarningMessageText() {

		waitForElementToBeVisible(emailRequiredMessage);
		return emailRequiredMessage.getText();

	}

	public String getPasswordWarningMessageText() {

		waitForElementToBeVisible(passwordRequiredMessage);
		return passwordRequiredMessage.getText();

	}

	public String getConfirmPasswordWarningMessageText() {

		waitForElementToBeVisible(confirmPasswordRequiredMessage);
		return confirmPasswordRequiredMessage.getText();
	}

	public boolean isErrorMessageDisplayed(WebElement element) {
	    try {
	        
	            wait.until(d -> !element.getText().isEmpty());
	        return element.isDisplayed();
	    } catch (TimeoutException e) {
	        return false;
	    }
	}



}
