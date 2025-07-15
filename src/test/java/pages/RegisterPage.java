package pages;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import demowebshop.base.BasePage;

public class RegisterPage extends BasePage {

    // Locators
    private final By femaleRadioButtonBy = By.id("gender-female");
    private final By firstNameInputBy = By.id("FirstName");
    private final By lastNameInputBy = By.id("LastName");
    private final By emailInputBy = By.id("Email");
    private final By passwordInputBy = By.id("Password");
    private final By confirmPasswordInputBy = By.id("ConfirmPassword");
    private final By registerButtonBy = By.id("register-button");
    private final By registrationResultMessageBy = By.xpath("//div[@class='result']");
    private final By registeredCustomerInfoBy = By.xpath("(//a[@href='/customer/info'])[1]");
    private final By continueButtonBy = By.cssSelector("input[value='Continue']");

    private final By firstNameIsRequiredMessageBy = By.cssSelector("span[data-valmsg-for='FirstName'] >span");
    private final By lastNameIsRequiredMessageBy = By.cssSelector("span[data-valmsg-for='LastName'] >span");
    private final By emailRequiredMessageBy = By.cssSelector("span[data-valmsg-for='Email'] > span");
    private final By passwordRequiredMessageBy = By.cssSelector("span[data-valmsg-for='Password']");
    private final By confirmPasswordRequiredMessageBy = By.cssSelector("span[data-valmsg-for='ConfirmPassword'] > span");

    public RegisterPage() {
        super();
    }

    // Actions

    public void registerUser(String firstName, String lastName, String email, String password, String confirmPassword) {
        WebElement femaleRadioButton = waitForElementToBeClickable(femaleRadioButtonBy);
        click(femaleRadioButton);

        sendKeys(waitForElementToBeVisible(firstNameInputBy), firstName);
        sendKeys(waitForElementToBeVisible(lastNameInputBy), lastName);
        sendKeys(waitForElementToBeVisible(emailInputBy), email);
        sendKeys(waitForElementToBeVisible(passwordInputBy), password);
        sendKeys(waitForElementToBeVisible(confirmPasswordInputBy), confirmPassword);

        WebElement registerBtn = waitForElementToBeClickable(registerButtonBy);
        click(registerBtn);
        
    }

    public String getResultMessage() {
        return waitForElementToBeVisible(registrationResultMessageBy).getText();
    }

    public void clickContinue() {
        WebElement continueBtn = waitForElementToBeClickable(continueButtonBy);
        click(continueBtn);
    }

    public void clickRegisterButton() {
        WebElement registerBtn = waitForElementToBeClickable(registerButtonBy);
        click(registerBtn);
    }

    public boolean isRegisteredCustomerInfoDisplayed() {
        try {
            return waitForElementToBeVisible(registeredCustomerInfoBy).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getRegisteredCustomerInfo() {
        return waitForElementToBeVisible(registeredCustomerInfoBy).getText().trim();
    }

    // Warning messages presence

    public boolean isFirstNameRequiredWarningMessageDisplayed() {
        return isErrorMessageDisplayed(firstNameIsRequiredMessageBy);
    }

    public boolean isLastNameRequiredWarningMessageDisplayed() {
        return isErrorMessageDisplayed(lastNameIsRequiredMessageBy);
    }

    public boolean isEmailRequiredWarningMessageDisplayed() {
        return isErrorMessageDisplayed(emailRequiredMessageBy);
    }

    public boolean isPasswordRequiredWarningMessageDisplayed() {
        return isErrorMessageDisplayed(passwordRequiredMessageBy);
    }

    public boolean isConfirmPasswordWarningMessageDisplayed() {
        return isErrorMessageDisplayed(confirmPasswordRequiredMessageBy);
    }

    // Warning messages text

    public String getFirstNameWarningMessageText() {
        return waitForElementToBeVisible(firstNameIsRequiredMessageBy).getText();
    }

    public String getLastNameWarningMessageText() {
        return waitForElementToBeVisible(lastNameIsRequiredMessageBy).getText();
    }

    public String getEmailWarningMessageText() {
        return waitForElementToBeVisible(emailRequiredMessageBy).getText();
    }

    public String getPasswordWarningMessageText() {
        return waitForElementToBeVisible(passwordRequiredMessageBy).getText();
    }

    public String getConfirmPasswordWarningMessageText() {
        return waitForElementToBeVisible(confirmPasswordRequiredMessageBy).getText();
    }

    // Helper method to check if error message is displayed and non-empty
    private boolean isErrorMessageDisplayed(By locator) {
        try {
        	   WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
               String text = element.getText().trim();
               return !text.isEmpty();
           } catch (Exception e) {
               return false;
           }
    }

}
