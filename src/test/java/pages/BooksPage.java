package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import demowebshop.base.BasePage;

public class BooksPage extends BasePage {

	public BooksPage() {

		super();
	}

	// Elements
	@FindBy(xpath = "//div[@data-productid='13']//input[@value='Add to cart']")
	private WebElement addToCartButtonForProduct13;

	@FindBy(id = "bar-notification")
	private WebElement successBarNotification;


	// Actions
	public boolean isOnBooksPage() {

		String currentUrl = getCurrentUrl();
		return currentUrl.contains("/books");
		
	}

	public void verifyOnBooksPage() {

		Assert.assertTrue(isOnBooksPage(), "Url does not match book url");
		
	}

	public void addBookWithId13ToCart() {

		waitForElementToBeClickable(addToCartButtonForProduct13);
		click(addToCartButtonForProduct13);
		
	}

	public String getSuccessBarNotificationAfterAddProductToCartText() {
		
		return successBarNotification.getText().trim();
	}
	
	public void verifySuccessBarNotificationAfterAddProductToCart(String expectedMessage) {
		
	    waitForElementToBeVisible(successBarNotification);
	    String actualMessage = getSuccessBarNotificationAfterAddProductToCartText();
	    Assert.assertTrue(actualMessage.contains(expectedMessage), "Text of success notification bar does not match!");
	    
	}
	
	


}
