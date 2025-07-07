package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import demowebshop.base.BasePage;

public class ShippingPage extends BasePage  {
	
	public ShippingPage() {
		
		super();
		
	}
	
	// Elements
	
	@FindBy(id = "shipping-address-select")
    private WebElement shippingAddressSelect;

    @FindBy(id = "shipping-buttons-container")
    private WebElement shippingButtonsContainer;

    @FindBy(xpath = "//input[@onclick='Shipping.save()']")
    private WebElement continueButton;
	
	// Actions

    public void waitForShippingSection() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-shipping")));
    }

    public String getSelectedShippingAddress() {
        waitForShippingSection();
        wait.until(ExpectedConditions.visibilityOf(shippingAddressSelect));
        Select select = new Select(shippingAddressSelect);
        return select.getFirstSelectedOption().getText();
    }

    public void clickContinue() {
    	 wait.until(ExpectedConditions.visibilityOf(continueButton));
    	    scrollToElement(continueButton);
    	    JavascriptExecutor js = (JavascriptExecutor) driver;
    	    js.executeScript("arguments[0].click();", continueButton);
    	    wait.until(ExpectedConditions.invisibilityOf(continueButton));
    }
}
