package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
	
	// Actions
	
	public void loginRegisteredUser(String email, String password) {
		
		sendKeys(emailInput, email);
		sendKeys(passwordInput, password);
		click(loginButton);		
		
	}

	

}
