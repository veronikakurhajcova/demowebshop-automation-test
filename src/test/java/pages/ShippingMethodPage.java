package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import demowebshop.base.BasePage;

public class ShippingMethodPage extends BasePage {

    // Locators
    private final By continueButtonBy = By.xpath("//input[@type='button' and contains(@class,'shipping-method-next-step-button')]");
    private final By shippingMethodSectionBy = By.id("checkout-step-shipping-method");
    private final By shippingMethodContentBy = By.id("checkout-shipping-method-load");
    private final By shippingMethodGroundBy = By.id("shippingoption_0");

    public ShippingMethodPage() {
        super();
    }

    // Actions

    public void waitForShippingMethodSection() {
        waitForElementToBeVisible(shippingMethodSectionBy);
        waitForElementToBeVisible(shippingMethodContentBy);
    }

    public boolean isShippingMethodDisplayed() {
        waitForShippingMethodSection();
        WebElement content = waitForElementToBeVisible(shippingMethodContentBy);
        return content.isDisplayed() && !content.getText().isEmpty();
    }

    public void selectGroundShippingMethod() {
        WebElement groundOption = waitForElementToBeClickable(shippingMethodGroundBy);
        if (!groundOption.isSelected()) {
            groundOption.click();
            wait.until(driver -> groundOption.isSelected());
        }
    }

    public boolean isGroundShippingMethodSelected() {
        WebElement groundOption = waitForElementToBeVisible(shippingMethodGroundBy);
        return groundOption.isSelected();
    }

    public void clickContinue() {
        WebElement continueBtn = waitForElementToBeClickable(continueButtonBy);
        click(continueBtn);
        wait.until(driver -> {
            try {
                return !continueBtn.isDisplayed();
            } catch (Exception e) {
                return true; // if element disappears, considered invisible
            }
        });
    }
}
