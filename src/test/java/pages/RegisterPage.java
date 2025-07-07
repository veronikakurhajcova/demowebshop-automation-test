package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import demowebshop.base.BasePage;

public class RegisterPage extends BasePage {
	
	
	public RegisterPage() {
		super();
	}
	
	// Elements
	@FindBy(id="gender-female")
	private WebElement femaleRadioButton;
	
	@FindBy(id="FirstName")
	private WebElement firstNameInput;
	
	@FindBy(id="LastName")
	private WebElement lastNameInput;
	
	@FindBy(id="Email")
	private WebElement emailInput;
	
	@FindBy(id="Password")
	private WebElement passwordInput;
	
	@FindBy(id="ConfirmPassword")
	private WebElement confirmPasswordInput;
	
	@FindBy(id="register-button")
	private WebElement registerButton;
	
	@FindBy(xpath="//div[@class='result']")
	private WebElement registrationResultMessage;
	
	@FindBy(xpath="(//a[@href='/customer/info'])[1]")
	private WebElement registeredCustomerInfo;
	
	@FindBy(css="input[value='Continue']")
	public WebElement continueButton;
	
	
	// Actions
	public void registerUser(String firstName, String lastName, String email, String password) {
		
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
	
}
