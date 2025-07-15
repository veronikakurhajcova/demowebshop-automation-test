package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import demowebshop.base.BasePage;

public class PaymentInformationPage extends BasePage {

    // Locators
    private final By paymentInfoSectionBy = By.id("checkout-step-payment-info");
    private final By paymentInfoContentBy = By.id("checkout-payment-info-load");
    private final By paymentInfoTableBy = By.xpath("//div[@id='checkout-payment-info-load']//div[contains(@class,'payment-info')]//p");
    private final By continueButtonBy = By.xpath("//input[contains(@class,'payment-info-next-step-button')]");

    public PaymentInformationPage() {
        super();
    }

    // Actions
    public void waitForPaymentInfoSection() {
        waitForElementToBeVisible(paymentInfoSectionBy);
        waitForElementToBeVisible(paymentInfoContentBy);
    }

    public boolean isPaymentInfoDisplayed() {
        WebElement paymentInfoTable = waitForElementToBeVisible(paymentInfoTableBy);
        return paymentInfoTable.isDisplayed();
    }

    public String getPaymentInfoText() {
        WebElement paymentInfoTable = waitForElementToBeVisible(paymentInfoTableBy);
        return paymentInfoTable.getText();
    }

    public void clickContinue() {
        WebElement continueButton = waitForElementToBeClickable(continueButtonBy);
        click(continueButton);
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(continueButtonBy));
    }
}
