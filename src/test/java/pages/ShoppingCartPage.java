package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import demowebshop.base.BasePage;

public class ShoppingCartPage extends BasePage {

	public ShoppingCartPage() {

		super();
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

	// Actions
	// Shopping cart informations methods

	public double parsePrice(String element) {

		return Double.parseDouble(element.replaceAll("[^\\d.]", ""));

	}

	public boolean getImageOfProductInShoppingCart() {

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

	public boolean expectedShoppingCartSummary(String expectedName, double expectedPrice, double expectedQuantity,
			double expectedTotalPriceForProduct) {

		return getImageOfProductInShoppingCart() && getNameOfProductInShoppingCart().equals(expectedName)
				&& getPriceInShoppingCart() == expectedPrice && getQuantityInShoppingCart() == expectedQuantity
				&& getProductSubtotalInShoppingCartInformation() == expectedTotalPriceForProduct;

	}

	public void selectCountry(String nameOfCountry) {

		Select select = new Select(countrySelect);
		select.selectByVisibleText(nameOfCountry);

	}

	public void selectStateOrProvince(String nameOfStateOrProvince) {

		Select select = new Select(stateProvinceSelect);
		select.selectByVisibleText(nameOfStateOrProvince);

	}

	public void fillAndSubmitShippingEstimateForm(String country) {

		waitForElementToBeClickable(countrySelect);
		selectCountry(country);
		click(estimateShippingButton);
		Assert.assertTrue(shippingResultsSummary.isDisplayed(), "Shipping results summary does not displayed");

	}

	// Totals summary methods

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

	public boolean checkInformationInShoppingCartTotalSummary(double expectedSubtotalPrice,
			double expectedShippingPrice, double expectedTax, double expectedTotal) {

		return getSubtotalPriceInShoppingCartSummary() == expectedSubtotalPrice
				&& getShippingPriceInShoppingCartSummary() == expectedShippingPrice
				&& getTaxInShoppingCartSummary() == expectedTax
				&& getTotalPriceInShoppingCartSummary() == expectedTotal;

	}
	
	public void submitOrder() {
		
		click(termOfServiceCheckbox);
		click(checkoutButton);
		
	}

}
