package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import demowebshop.base.BasePage;

public class BooksPage extends BasePage {


    // Locators
    private final By addToCartButtonForProduct13By = By.xpath("//div[@data-productid='13']//input[@value='Add to cart']");
    private final By successBarNotificationBy = By.id("bar-notification");

    // Constructor
    public BooksPage() {
    	super();
    }
    
    // Actions
    public boolean isOnBooksPage() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("/books");
    }

    public void verifyOnBooksPage() {
        Assert.assertTrue(isOnBooksPage(), "Url does not match book url");
    }

    public void addBookWithId13ToCart() {
        WebElement addToCartBtn = waitForElementToBeClickable(addToCartButtonForProduct13By);
        addToCartBtn.click();
    }

    public String getSuccessBarNotificationAfterAddProductToCartText() {
        WebElement successBar = waitForElementToBeVisible(successBarNotificationBy);
        return successBar.getText().trim();
    }

    public void verifySuccessBarNotificationAfterAddProductToCart(String expectedMessage) {
        String actualMessage = getSuccessBarNotificationAfterAddProductToCartText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Text of success notification bar does not match!");
    }
}
