package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import demowebshop.base.BasePage;

public class LoginPage extends BasePage {
	
	public LoginPage() {
		super();
	}
	
	// Elements
	
	@FindBy(id="Email")
	private WebElement emailInput;
	
	@FindBy(id="Password")
	private WebElement passwordInput;
	
	@FindBy(css = "input.button-1.login-button")
	private WebElement loginButton;
	
	@FindBy(css = "div.validation-summary-errors")
	private WebElement validationSummaryErrors;
	
	@FindBy(css ="div.validation-summary-errors>ul>li")
	private List<WebElement> credentialsErrorMessages;
	
	@FindBy(css = "span[data-valmsg-for='Email']")
	private WebElement errorMessageForValidEmail;

	// Actions
	
	public void loginUser(String email, String password) {
		
		sendKeys(emailInput, email);
		sendKeys(passwordInput, password);
		click(loginButton);		
		
	}
	
	public void enterEmailInvalidLogin(String email) {
	    sendKeys(emailInput, email);
	}

	public void enterPasswordInvalidLogin(String password) {
	    sendKeys(passwordInput, password);
	}

	public void clickLoginButton() {
	    click(loginButton);
	}

	
	public boolean isValidationSummaryErrorsDisplayed() {
		
		return validationSummaryErrors.isDisplayed();
		
	}
	
	public String getValidationSummaryErrorsText() {
		
		return validationSummaryErrors.getText();
		
	}
	
	public List<String> getAllErrorMessagesText() {
	    List<String> errorMessages = new ArrayList<>();
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        wait.until(ExpectedConditions.visibilityOf(validationSummaryErrors));

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
		        return errorMessageForValidEmail.isDisplayed() && !errorMessageForValidEmail.getText().isEmpty();
		    } catch (NoSuchElementException e) {
		        return false;
		    }
		
	}
	
	public String getErrorMessageForValidEmailText() {
		
		return errorMessageForValidEmail.getText();
		
	}


	public void focusPasswordField() {
		
		passwordInput.click(); 
	    
	}
	
}
