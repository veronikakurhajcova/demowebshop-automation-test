package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import demowebshop.base.BasePage;

public class CheckoutPage extends BasePage {

    // Locators
    private final By firstNameBy = By.id("BillingNewAddress_FirstName");
    private final By lastNameBy = By.id("BillingNewAddress_LastName");
    private final By emailBy = By.id("BillingNewAddress_Email");
    private final By countryBy = By.id("BillingNewAddress_CountryId");
    private final By cityBy = By.id("BillingNewAddress_City");
    private final By address1By = By.id("BillingNewAddress_Address1");
    private final By zipPostalCodeBy = By.id("BillingNewAddress_ZipPostalCode");
    private final By phoneNumberBy = By.id("BillingNewAddress_PhoneNumber");
    private final By prefilledBillingAddressSelectBy = By.id("billing-address-select");
    private final By continueButtonBy = By.xpath("//input[@type='button'][@title='Continue']");

    public CheckoutPage() {
        super();
    }

    // Actions

    public String getBillingFirstName() {
        WebElement firstName = waitForElementToBeVisible(firstNameBy);
        return firstName.getAttribute("value");
    }

    public String getBillingLastName() {
        WebElement lastName = waitForElementToBeVisible(lastNameBy);
        return lastName.getAttribute("value");
    }

    public String getBillingEmail() {
        WebElement email = waitForElementToBeVisible(emailBy);
        return email.getAttribute("value");
    }

    public void waitForBillingAddressSection() {
        waitForElementToBeVisible(firstNameBy);
    }

    public void selectBillingCountry(String countryName) {
        waitForBillingAddressSection();
        WebElement country = waitForElementToBeVisible(countryBy);
        Select select = new Select(country);
        select.selectByVisibleText(countryName);
    }

    public void enterBillingCity(String city) {
        WebElement cityElem = waitForElementToBeClickable(cityBy);
        sendKeys(cityElem, city);
    }

    public void enterBillingAddress1(String address) {
        WebElement addressElem = waitForElementToBeClickable(address1By);
        sendKeys(addressElem, address);
    }

    public void enterBillingZipPostalCode(String postalZipCode) {
        WebElement zipElem = waitForElementToBeClickable(zipPostalCodeBy);
        sendKeys(zipElem, postalZipCode);
    }

    public void enterBillingPhoneNumber(String phoneNumber) {
        WebElement phoneElem = waitForElementToBeClickable(phoneNumberBy);
        sendKeys(phoneElem, phoneNumber);
    }

    public void clickBillingContinueButton() {
        WebElement continueButton = waitForElementToBeClickable(continueButtonBy);
        click(continueButton);
        wait.until(ExpectedConditions.invisibilityOf(continueButton));
    }

    public String getSelectedBillingCountry() {
        WebElement country = waitForElementToBeVisible(countryBy);
        Select select = new Select(country);
        return select.getFirstSelectedOption().getText();
    }

    public String getBillingCity() {
        WebElement cityElem = waitForElementToBeVisible(cityBy);
        return cityElem.getAttribute("value");
    }

    public String getBillingAddress1() {
        WebElement addressElem = waitForElementToBeVisible(address1By);
        return addressElem.getAttribute("value");
    }

    public String getBillingZipPostalCode() {
        WebElement zipElem = waitForElementToBeVisible(zipPostalCodeBy);
        return zipElem.getAttribute("value");
    }

    public String getBillingPhoneNumber() {
        WebElement phoneElem = waitForElementToBeVisible(phoneNumberBy);
        return phoneElem.getAttribute("value");
    }

    public void selectPrefilledBillingAddress() {
        WebElement selectElem = waitForElementToBeVisible(prefilledBillingAddressSelectBy);
        Select select = new Select(selectElem);
        select.selectByIndex(0);
    }

    public String getSelectedBillingAddressText() {
        WebElement selectElem = waitForElementToBeVisible(prefilledBillingAddressSelectBy);
        Select select = new Select(selectElem);
        return select.getFirstSelectedOption().getText().trim();
    }

    public boolean isSelectedBillingAddressContaining(String expectedPartialText) {
        WebElement selectElem = waitForElementToBeVisible(prefilledBillingAddressSelectBy);
        Select select = new Select(selectElem);
        String selectedText = select.getFirstSelectedOption().getText();
        return selectedText.contains(expectedPartialText);
    }

    public void waitForContinueButton() {
        waitForElementToBeClickable(continueButtonBy);
    }

    public boolean isBillingCountrySelectDisplayed() {
        WebElement country = waitForElementToBeVisible(countryBy);
        return country.isDisplayed();
    }
}
