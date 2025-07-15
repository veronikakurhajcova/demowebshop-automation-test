package pages;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import demowebshop.base.BasePage;

public class LoginPage extends BasePage {

    // Locators
    private final By emailInputBy = By.id("Email");
    private final By passwordInputBy = By.id("Password");
    private final By loginButtonBy = By.cssSelector("input.button-1.login-button");
    private final By validationSummaryErrorsBy = By.cssSelector("div.validation-summary-errors");
    private final By validationSummaryErrorItemsBy = By.cssSelector("div.validation-summary-errors > ul > li");
    private final By errorMessageForValidEmailBy = By.cssSelector("span[data-valmsg-for='Email']");

    public LoginPage() {
        super();
    }

    // Actions
    public void loginUser(String email, String password) {
        WebElement emailInput = waitForElementToBeVisible(emailInputBy);
        sendKeys(emailInput, email);
        WebElement passwordInput = waitForElementToBeVisible(passwordInputBy);
        sendKeys(passwordInput, password);
        WebElement loginButton = waitForElementToBeClickable(loginButtonBy);
        click(loginButton);
    }

    public void enterEmailInvalidLogin(String email) {
        WebElement emailInput = waitForElementToBeVisible(emailInputBy);
        sendKeys(emailInput, email);
    }

    public void enterPasswordInvalidLogin(String password) {
        WebElement passwordInput = waitForElementToBeVisible(passwordInputBy);
        sendKeys(passwordInput, password);
    }

    public void clickLoginButton() {
        WebElement loginButton = waitForElementToBeClickable(loginButtonBy);
        click(loginButton);
    }

    public boolean isValidationSummaryErrorsDisplayed() {
        try {
            WebElement validationSummaryErrors = waitForElementToBeVisible(validationSummaryErrorsBy);
            return validationSummaryErrors.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getValidationSummaryErrorsText() {
        WebElement validationSummaryErrors = waitForElementToBeVisible(validationSummaryErrorsBy);
        return validationSummaryErrors.getText();
    }

    public List<String> getAllErrorMessagesText() {
        List<String> errorMessages = new ArrayList<>();
        try {
            WebElement validationSummaryErrors = waitForElementToBeVisible(validationSummaryErrorsBy);
            List<WebElement> errorItems = validationSummaryErrors.findElements(By.tagName("li"));
            for (WebElement item : errorItems) {
                String text = item.getText().trim();
                if (!text.isEmpty()) {
                    errorMessages.add(text);
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            log.info("No validation summary errors found or visible.");
        }
        return errorMessages;
    }

    public boolean isErrorMessageForValidEmailDisplayed() {
        try {
            WebElement errorMessage = waitForElementToBeVisible(errorMessageForValidEmailBy);
            return errorMessage.isDisplayed() && !errorMessage.getText().isEmpty();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public String getErrorMessageForValidEmailText() {
        WebElement errorMessage = waitForElementToBeVisible(errorMessageForValidEmailBy);
        return errorMessage.getText();
    }

    public void focusPasswordField() {
        WebElement passwordInput = waitForElementToBeVisible(passwordInputBy);
        passwordInput.click();
    }
}
