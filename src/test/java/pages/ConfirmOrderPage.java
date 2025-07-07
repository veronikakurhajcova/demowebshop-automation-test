package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import demowebshop.base.BasePage;

public class ConfirmOrderPage extends BasePage {
	
	 public ConfirmOrderPage() {
	        super();
	    }

	    // Elements

	    @FindBy(id = "checkout-step-confirm-order")
	    private WebElement confirmOrderSection;

	    @FindBy(id = "checkout-confirm-order-load")
	    private WebElement confirmOrderContent;

	    @FindBy(css = ".billing-info")
	    private WebElement billingInfoBox;

	    @FindBy(css = ".shipping-info")
	    private WebElement shippingInfoBox;
	    
	    @FindBy(xpath = "//input[contains(@class,'confirm-order-next-step-button')]")
	    private WebElement confirmButton;

	    // Actions

	    public void waitForConfirmOrderSection() {
	        wait.until(ExpectedConditions.visibilityOf(confirmOrderSection));
	        wait.until(ExpectedConditions.visibilityOf(confirmOrderContent));
	    }
	    
	    public String getBillingInfoText() {
	        wait.until(ExpectedConditions.visibilityOf(billingInfoBox));
	        return billingInfoBox.getText();
	    }

	    public String getShippingInfoText() {
	        wait.until(ExpectedConditions.visibilityOf(shippingInfoBox));
	        return shippingInfoBox.getText();
	    }

	    public boolean isConfirmButtonDisplayed() {
	        return confirmButton.isDisplayed();
	    }

	    public void clickConfirm() {
	        wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
	        confirmButton.click();
	        wait.until(ExpectedConditions.invisibilityOf(confirmButton));
	    }

}
