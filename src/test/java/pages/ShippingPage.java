package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import demowebshop.base.BasePage;

public class ShippingPage extends BasePage {

    // Locators
    private final By shippingAddressSelectBy = By.id("shipping-address-select");
    private final By continueButtonBy = By.xpath("//input[@onclick='Shipping.save()']");
    private final By shippingSectionBy = By.id("checkout-step-shipping");

    public ShippingPage() {
        super();
    }

    // Actions

    public void waitForShippingSection() {
        waitForElementToBeVisible(shippingSectionBy);
    }

    public String getSelectedShippingAddressText() {
        WebElement selectElement = waitForElementToBeVisible(shippingAddressSelectBy);
        Select select = new Select(selectElement);
        return select.getFirstSelectedOption().getText().trim();
    }

    public boolean isSelectedShippingAddressContaining(String expectedPartialText) {
        WebElement selectElement = waitForElementToBeVisible(shippingAddressSelectBy);
        Select select = new Select(selectElement);
        String selectedText = select.getFirstSelectedOption().getText();
        return selectedText.contains(expectedPartialText);
    }

    public void clickContinue() {
        WebElement continueBtn = waitForElementToBeVisible(continueButtonBy);
        scrollToElement(continueBtn);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", continueBtn);
        wait.until(ExpectedConditions.invisibilityOf(continueBtn));
    }
}
