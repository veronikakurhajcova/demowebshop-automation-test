package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

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
	
	@FindBy(id = "billing-address-select")
	private WebElement prefilledBillingAddressSelect;
	
	@FindBy(xpath="//input[@type='button'][@title='Continue']")
	private WebElement continueButtonInBillingAddress;

	//Actions
	public String getBillingFirstName() {
	    return firstNameInBillingAddress.getAttribute("value");
	}
	
	public String getBillingLastName() {
		return lastNameInBillingAddress.getAttribute("value");
	}
	
	public String getBillingEmail() {
		return emailInBillingAddress.getAttribute("value");
	}
	
	public void waitForBillingAddressSection() {
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("BillingNewAddress_FirstName")));
	}
	
	public void selectBillingCountry(String countryName) {
		waitForBillingAddressSection(); 
	    wait.until(ExpectedConditions.visibilityOf(countryInBillingAddress));
	    Select select = new Select(countryInBillingAddress);
	    select.selectByVisibleText(countryName);
	}
	
	public void enterBillingCity(String city) {
		waitForElementToBeClickable(cityInBillingAddress);
		sendKeys(cityInBillingAddress, city);
	}
	
	public void enterBillingAddress1(String address) {
		sendKeys(addressInBillingAddress, address);
	}
	
	public void enterBillingZipPostalCode(String postalZipCode) {
		waitForElementToBeClickable(zipPostalCodeBillingAddress);
		sendKeys(zipPostalCodeBillingAddress, postalZipCode);
	}
	
	public void enterBillingPhoneNumber(String phoneNumber) {
		waitForElementToBeClickable(phoneNumberInBillingAddress);
		sendKeys(phoneNumberInBillingAddress, phoneNumber);
	}
	
	public void clickBillingContinueButton() {
		waitForElementToBeClickable(continueButtonInBillingAddress);
	    click(continueButtonInBillingAddress);
	    wait.until(ExpectedConditions.invisibilityOf(continueButtonInBillingAddress));
	}
	
	public String getSelectedBillingCountry() {
	    Select select = new Select(countryInBillingAddress);
	    return select.getFirstSelectedOption().getText();
	}

	public String getBillingCity() {
	    return cityInBillingAddress.getAttribute("value");
	}

	public String getBillingAddress1() {
	    return addressInBillingAddress.getAttribute("value");
	}

	public String getBillingZipPostalCode() {
	    return zipPostalCodeBillingAddress.getAttribute("value");
	}

	public String getBillingPhoneNumber() {
	    return phoneNumberInBillingAddress.getAttribute("value");
	}
	
	public void selectPrefilledBillingAddress() {
		
		Select select = new Select(prefilledBillingAddressSelect);
		select.selectByIndex(0);
		
	}
	
	public String getSelectedBillingAddressText() {
		
	    Select select = new Select(prefilledBillingAddressSelect);
	    return select.getFirstSelectedOption().getText().trim();
	}

	public boolean isSelectedBillingAddressContaining(String expectedPartialText) {
	    Select select = new Select(prefilledBillingAddressSelect);
	    String selectedText = select.getFirstSelectedOption().getText();
	    return selectedText.contains(expectedPartialText);
	}

	
	public void waitForContinueButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(continueButtonInBillingAddress));
	}

}
