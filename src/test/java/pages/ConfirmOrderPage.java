package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import demowebshop.base.BasePage;

public class ConfirmOrderPage extends BasePage {

    // Locators
    private final By confirmOrderSectionBy = By.id("checkout-step-confirm-order");
    private final By confirmOrderContentBy = By.id("checkout-confirm-order-load");
    private final By billingInfoBoxBy = By.cssSelector(".billing-info");
    private final By shippingInfoBoxBy = By.cssSelector(".shipping-info");
    private final By confirmButtonBy = By.xpath("//input[contains(@class,'confirm-order-next-step-button')]");
    private final By orderSuccessMessageBy = By.cssSelector("div.section.order-completed div.title strong");

    // Constructor
    public ConfirmOrderPage() {
        super();
    }

    // Actions
    public void waitForConfirmOrderSection() {
        waitForElementToBeVisible(confirmOrderSectionBy);
        waitForElementToBeVisible(confirmOrderContentBy);
    }

    public String getBillingInfoText() {
        WebElement billingInfoBox = waitForElementToBeVisible(billingInfoBoxBy);
        return billingInfoBox.getText();
    }

    public String getShippingInfoText() {
        WebElement shippingInfoBox = waitForElementToBeVisible(shippingInfoBoxBy);
        return shippingInfoBox.getText();
    }

    public boolean isConfirmButtonDisplayed() {
        WebElement confirmButton = waitForElementToBeVisible(confirmButtonBy);
        return confirmButton.isDisplayed();
    }

    public void clickConfirm() {
        WebElement confirmButton = waitForElementToBeClickable(confirmButtonBy);
        click(confirmButton);  // BasePage click s JS fallbackom
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf(confirmButton));
    }

    public String getOrderSuccessMessage() {
        WebElement successMessage = waitForElementToBeVisible(orderSuccessMessageBy);
        return successMessage.getText();
    }
}
