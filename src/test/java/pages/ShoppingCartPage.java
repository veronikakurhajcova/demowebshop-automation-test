package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import demowebshop.base.BasePage;

public class ShoppingCartPage extends BasePage {
    
    DashboardPage dashboardPage = new DashboardPage();
    JavascriptExecutor js;

    // Locators
    private final By imageOfProductBy = By.xpath("//td[@class='product-picture']");
    private final By nameOfProductInShoppingCartBy = By.xpath("//td[@class='product']/a[@class='product-name']");
    private final By productUnitPriceInShoppingCartInformationBy = By.xpath("//td[@class='unit-price nobr']//span[@class='product-unit-price']");
    private final By quantityInputInShoppingCartInformationBy = By.xpath("//td[@class='qty nobr']//input[@class='qty-input']");
    private final By productSubtotalInShoppingCartInformationBy = By.xpath("//td[@class='subtotal nobr end']//span[@class='product-subtotal']");

    private final By countrySelectBy = By.id("CountryId");
    private final By stateProvinceSelectBy = By.id("StateProvinceId");
    private final By zipPostalCodeSelectBy = By.id("ZipPostalCode");
    private final By estimateShippingButtonBy = By.xpath("//input[@type='submit'][@name='estimateshipping']");
    private final By shippingResultsSummaryBy = By.xpath("//ul[@class='shipping-results']");

    private final By totalInfoTableInShoppingCartSummaryBy = By.xpath("//table[@class='cart-total']");
    private final By productSubtotalInShoppingCartSummaryBy = By.xpath("//tr[td/span[contains(text(),'Sub-Total:')]]/td[@class='cart-total-right']//span[@class='product-price']");
    private final By shippingInShoppingCartSummaryBy = By.xpath("//tr[td/span[contains(text(),'Shipping:')]]/td[@class='cart-total-right']//span[@class='product-price']");
    private final By taxInShoppingCartSummaryBy = By.xpath("//tr[td/span[contains(text(),'Tax:')]]/td[@class='cart-total-right']//span[@class='product-price']");
    private final By totalPriceInShoppingCartSummaryBy = By.xpath("//tr[td/span[contains(text(),'Total:')]]/td[@class='cart-total-right']//span[contains(@class,'product-price')]//strong");

    private final By termOfServiceCheckboxBy = By.id("termsofservice");
    private final By checkoutButtonBy = By.id("checkout");

    private final By removeProductFromCartCheckboxBy = By.xpath("//input[@name='removefromcart']");
    private final By updateShoppingCartButtonBy = By.xpath("//input[@name='updatecart']");

    public ShoppingCartPage() {
        super();
        this.js = (JavascriptExecutor) driver;
    }

    // Helper method to parse price string to double
    public double parsePrice(String element) {
        return Double.parseDouble(element.replaceAll("[^\\d.]", ""));
    }

    // Shopping cart information methods
    public boolean isProductImageDisplayed() {
        return waitForElementToBeVisible(imageOfProductBy).isDisplayed();
    }

    public String getNameOfProductInShoppingCart() {
        return waitForElementToBeVisible(nameOfProductInShoppingCartBy).getText();
    }

    public double getPriceInShoppingCart() {
        return parsePrice(waitForElementToBeVisible(productUnitPriceInShoppingCartInformationBy).getText());
    }

    public double getQuantityInShoppingCart() {
        return parsePrice(waitForElementToBeVisible(quantityInputInShoppingCartInformationBy).getAttribute("value"));
    }

    public double getProductSubtotalInShoppingCartInformation() {
        return parsePrice(waitForElementToBeVisible(productSubtotalInShoppingCartInformationBy).getText());
    }

    // Estimate shipping methods
    public void selectCountry(String nameOfCountry) {
        WebElement countrySelect = waitForElementToBeVisible(countrySelectBy);
        Select select = new Select(countrySelect);
        select.selectByVisibleText(nameOfCountry);
    }

    public boolean selectedCountryIsDisplayed(String expectedCountry) {
        Select select = new Select(waitForElementToBeVisible(countrySelectBy));
        return select.getFirstSelectedOption().getText().equals(expectedCountry);
    }

    public void selectStateOrProvince(String nameOfStateOrProvince) {
        Select select = new Select(waitForElementToBeVisible(stateProvinceSelectBy));
        select.selectByVisibleText(nameOfStateOrProvince);
    }

    public void clickEstimateShippingButton() {
        click(waitForElementToBeClickable(estimateShippingButtonBy));
    }

    public boolean shippingResultsSummaryIsDisplayed() {
        return waitForElementToBeVisible(shippingResultsSummaryBy).isDisplayed();
    }

    // Totals summary methods
    public boolean totalInfoSummaryInShoppingCartIsDisplayed() {
        return waitForElementToBeVisible(totalInfoTableInShoppingCartSummaryBy).isDisplayed();
    }

    public double getSubtotalPriceInShoppingCartSummary() {
        return parsePrice(waitForElementToBeVisible(productSubtotalInShoppingCartSummaryBy).getText());
    }

    public double getShippingPriceInShoppingCartSummary() {
        return parsePrice(waitForElementToBeVisible(shippingInShoppingCartSummaryBy).getText());
    }

    public double getTaxInShoppingCartSummary() {
        return parsePrice(waitForElementToBeVisible(taxInShoppingCartSummaryBy).getText());
    }

    public double getTotalPriceInShoppingCartSummary() {
        return parsePrice(waitForElementToBeVisible(totalPriceInShoppingCartSummaryBy).getText());
    }

    public void clickTermsOfService() {
        click(waitForElementToBeClickable(termOfServiceCheckboxBy));
    }

    public void submitOrder() {
        click(waitForElementToBeClickable(checkoutButtonBy));
    }

    public String getCheckoutCurrentUrl() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return super.getCurrentUrl();
    }

    // Clean cart method
    public void clearShoppingCart() {
        if (isElementPresent(removeProductFromCartCheckboxBy)) {
            WebElement removeCheckbox = waitForElementToBeClickable(removeProductFromCartCheckboxBy);
            if (!removeCheckbox.isSelected()) {
                click(removeCheckbox);
                click(waitForElementToBeClickable(updateShoppingCartButtonBy));
            } else {
                click(waitForElementToBeClickable(updateShoppingCartButtonBy));
            }
        }
        navigateTo(dashboardPage.getDashboardUrl());
    }
}
