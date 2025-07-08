package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import demowebshop.base.BasePage;

public class ShoppingCartPage extends BasePage {
	
	DashboardPage dashboardPage = new DashboardPage();
	JavascriptExecutor js;

	public ShoppingCartPage() {

		super();
		this.js = (JavascriptExecutor) driver;
	}

	// Elements
	// Shopping cart information elements

	@FindBy(xpath = "//td[@class='product-picture']")
	private WebElement imageOfProduct;

	@FindBy(xpath = "//td[@class='product']/a[@class='product-name']")
	private WebElement nameOfProductInShoppingCart;

	@FindBy(xpath = "//td[@class='unit-price nobr']//span[@class='product-unit-price']")
	private WebElement productUnitPriceInShoppingCartInformation;

	@FindBy(xpath = "//td[@class='qty nobr']//input[@class='qty-input']")
	private WebElement quantityInputInShoppingCartInformation;

	@FindBy(xpath = "//td[@class='subtotal nobr end']//span[@class='product-subtotal']")
	private WebElement productSubtotalInShoppingCartInformation;

	// Estimate shipping elements

	@FindBy(id = "CountryId")
	private WebElement countrySelect;

	@FindBy(id = "StateProvinceId")
	private WebElement stateProvinceSelect;

	@FindBy(id = "ZipPostalCode")
	private WebElement zipPostalCodeSelect;

	@FindBy(xpath = "//input[@type='submit'][@name='estimateshipping']")
	private WebElement estimateShippingButton;

	@FindBy(xpath = "//ul[@class='shipping-results']")
	private WebElement shippingResultsSummary;

	// Total Shopping cart summary elements
	
	@FindBy(xpath="//table[@class='cart-total']")
	private WebElement totalInfoTableInShoppingCartSummary;

	@FindBy(xpath = "//tr[td/span[contains(text(),'Sub-Total:')]]/td[@class='cart-total-right']//span[@class='product-price']")
	private WebElement productSubtotalInShoppingCartSummary;

	@FindBy(xpath = "//tr[td/span[contains(text(),'Shipping:')]]/td[@class='cart-total-right']//span[@class='product-price']")
	private WebElement shippingInShoppingCartSummary;

	@FindBy(xpath = "//tr[td/span[contains(text(),'Tax:')]]/td[@class='cart-total-right']//span[@class='product-price']")
	private WebElement taxInShoppingCartSummary;

	@FindBy(xpath = "//tr[td/span[contains(text(),'Total:')]]/td[@class='cart-total-right']//span[contains(@class,'product-price')]//strong")
	private WebElement totalPriceInShoppingCartSummary;

	@FindBy(id = "termsofservice")
	private WebElement termOfServiceCheckbox;

	@FindBy(id = "checkout")
	private WebElement checkoutButton;
	
	// Clean cart
	@FindBy(xpath="//input[@name='removefromcart']")
	private WebElement removeProductFromCartCheckbox;
	
	@FindBy(xpath="//input[@name='updatecart']")
	private WebElement updateShoppingCartButton;

	// Actions
	// Shopping cart informations methods

	public double parsePrice(String element) {

		return Double.parseDouble(element.replaceAll("[^\\d.]", ""));

	}

	public boolean isProductImageDisplayed() {

		return imageOfProduct.isDisplayed();

	}

	public String getNameOfProductInShoppingCart() {

		return nameOfProductInShoppingCart.getText();

	}

	public double getPriceInShoppingCart() {

		return parsePrice(productUnitPriceInShoppingCartInformation.getText());

	}

	public double getQuantityInShoppingCart() {

		return parsePrice(quantityInputInShoppingCartInformation.getAttribute("value"));

	}

	public double getProductSubtotalInShoppingCartInformation() {

		return parsePrice(productSubtotalInShoppingCartInformation.getText());

	}

	// Estimate shipping methods

	public void selectCountry(String nameOfCountry) {
	    waitForElementToBeVisibleByLocator(By.id("CountryId"));
	    WebElement countrySelect = driver.findElement(By.id("CountryId"));
	    Select select = new Select(countrySelect);
	    select.selectByVisibleText(nameOfCountry);
	   

	}
	
	public WebElement getCountrySelect() {
	    return countrySelect;
	}


	
	public boolean selectedCountryIsDisplayed(String expectedCountry) {
	    Select select = new Select(countrySelect);
	    return select.getFirstSelectedOption().getText().equals(expectedCountry);
	}


	public void selectStateOrProvince(String nameOfStateOrProvince) {

		Select select = new Select(stateProvinceSelect);
		select.selectByVisibleText(nameOfStateOrProvince);

	}
	
	public void clickEstimateShippingButton() {
		
		click(estimateShippingButton);
		
	}
	
	public boolean shippingResultsSummaryIsDisplayed() {
		
		return shippingResultsSummary.isDisplayed();
		
	}

	// Totals summary methods
	
	public boolean totalInfoSummaryInShoppingCartIsDisplayed() {
		
		return totalInfoTableInShoppingCartSummary.isDisplayed();
	}

	public double getSubtotalPriceInShoppingCartSummary() {

		return parsePrice(productSubtotalInShoppingCartSummary.getText());

	}

	public double getShippingPriceInShoppingCartSummary() {

		return parsePrice(shippingInShoppingCartSummary.getText());

	}

	public double getTaxInShoppingCartSummary() {

		return parsePrice(taxInShoppingCartSummary.getText());

	}

	public double getTotalPriceInShoppingCartSummary() {

		return parsePrice(totalPriceInShoppingCartSummary.getText());

	}
	
	public void clickTermsOfService() {
		
		waitForElementToBeClickable(termOfServiceCheckbox);
		click(termOfServiceCheckbox);
		
	}


	public void submitOrder() {

		click(checkoutButton);

	}
	
	public String getCheckoutCurrentUrl() {

		return super.getCurrentUrl();
		
	}
	
	// Clean cart method
	public void clearShoppingCart() {
		
	    if (isElementPresent(removeProductFromCartCheckbox)) {
	        if (!removeProductFromCartCheckbox.isSelected()) {
	            waitForElementToBeClickable(removeProductFromCartCheckbox);
	            click(removeProductFromCartCheckbox);
	            waitForElementToBeClickable(updateShoppingCartButton);
	            click(updateShoppingCartButton);
	        } else {
	            waitForElementToBeClickable(updateShoppingCartButton);
	            click(updateShoppingCartButton);
	        }
	    }
	    
	    navigateTo(dashboardPage.getDashboardUrl());
	}


}
