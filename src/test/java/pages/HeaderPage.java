package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import demowebshop.base.BasePage;

public class HeaderPage extends BasePage {

	public HeaderPage() {

		super();

	}

	// Elements

	@FindBy(xpath = "//a[@class='ico-register']")
	private WebElement registerLink;

	@FindBy(xpath = "//a[@href='/login']")
	private WebElement loginLink;

	@FindBy(css = "span.cart-qty")
	private WebElement cartQuantity;
	
	@FindBy(css = "a.ico-cart")
	private WebElement shoppingCartLink;

	// Actions
	// Register
	public void clickRegisterLink() {

		click(registerLink);
	}

	// Login
	public void clickLoginLink() {

		click(loginLink);
	}

	// Cart
	public int getCartQuantity() {

		String quantityText = cartQuantity.getText();
		return Integer.parseInt(quantityText.replaceAll("[^0-9]", ""));
	}
	
	public void clickOnShoppingCartLink() {
		waitForElementToBeClickable(shoppingCartLink);
		clickOnShoppingCartLink();
	}

}
