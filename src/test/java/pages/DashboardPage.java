package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import demowebshop.base.BasePage;

public class DashboardPage extends BasePage {
	
	public DashboardPage() {
		
		super();
	}

	// Elements
	@FindBy(xpath = "//a[@href=\"/logout\" ]")
	private WebElement logoutButton;
	
	@FindBy(xpath = "(//a[@href='/customer/info'])[1]")
	private WebElement customerInfoLink;
	
	//Actions
	
	public String getDashboardUrl() {
		
		return getCurrentUrl();
	}
	
	public String getLoggedCustomerInfo() {
		
		return customerInfoLink.getText().trim();
		
	}
	
	public void clickLogoutButton() {
		
		waitForElementToBeClickable(logoutButton);
		click(logoutButton);
	}

}
