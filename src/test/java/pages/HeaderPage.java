package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import demowebshop.base.BasePage;

public class HeaderPage extends BasePage {

    // Locators
    private final By registerLinkBy = By.xpath("//a[@class='ico-register']");
    private final By loginLinkBy = By.xpath("//a[@href='/login']");
    private final By cartQuantityBy = By.cssSelector("span.cart-qty");
    private final By shoppingCartLinkBy = By.cssSelector("a.ico-cart");

    public HeaderPage() {
        super();
    }

    // Actions

    // Register
    public void clickRegisterLink() {
        WebElement registerLink = waitForElementToBeClickable(registerLinkBy);
        click(registerLink);
    }

    // Login
    public void clickLoginLink() {
        WebElement loginLink = waitForElementToBeClickable(loginLinkBy);
        click(loginLink);
    }

    // Cart quantity
    public int getCartQuantity() {
        WebElement cartQuantity = waitForElementToBeVisible(cartQuantityBy);
        String quantityText = cartQuantity.getText();
        return Integer.parseInt(quantityText.replaceAll("[^0-9]", ""));
    }

    // Shopping cart link
    public void clickOnShoppingCartLink() {
        WebElement shoppingCartLink = waitForElementToBeVisible(shoppingCartLinkBy);
        waitForElementToBeClickable(shoppingCartLinkBy);
        click(shoppingCartLink);
    }
}
