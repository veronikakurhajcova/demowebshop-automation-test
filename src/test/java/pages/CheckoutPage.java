package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import demowebshop.base.BasePage;

public class CheckoutPage extends BasePage {
	
	public CheckoutPage() {
		
		super();
		
	}
	
	// Elements
	@FindBy(id="BillingNewAddress_FirstName")
	private WebElement firstNameInBillingAddress;
	
	@FindBy(id="BillingNewAddress_LastName")
	private WebElement lastNameInBillingAddress;
	
	@FindBy(id="BillingNewAddress_Email")
	private WebElement emailInBillingAddress;
	
	@FindBy(id="BillingNewAddress_CountryId")
	private WebElement countryInBillingAddress;
	
	@FindBy(id="BillingNewAddress_City")
	private WebElement cityInBillingAddress;
	
	@FindBy(id="BillingNewAddress_Address1")
	private WebElement addressInBillingAddress;
	
	@FindBy(id="BillingNewAddress_ZipPostalCode")
	private WebElement zipPostalCodeBillingAddress;
	
	@FindBy(id="BillingNewAddress_PhoneNumber")
	private WebElement phoneNumberInBillingAddress;
	
	@FindBy(xpath="//input[@type='button'][@title='Continue']")
	private WebElement continueButtonInBillingAddress;
	
	
	
	
	
	
	
	//Actions

}
