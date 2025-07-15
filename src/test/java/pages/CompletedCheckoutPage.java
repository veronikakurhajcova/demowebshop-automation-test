package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import demowebshop.base.BasePage;

public class CompletedCheckoutPage extends BasePage {


    // Locators
    private final By orderSuccessMessageBy = By.cssSelector(".section.order-completed>.title>strong");
    private final By sectionOrderCompletedOrderNumberTextBy = By.cssSelector(".section.order-completed>.details>li");
    private final By sectionOrderCompletedDetailsLinkBy = By.cssSelector(".section.order-completed>.details>li>a");
    private final By sectionOrderCompletedContinueButtonBy = By.cssSelector(".buttons>.order-completed-continue-button");

    // Constructor
    public CompletedCheckoutPage() {
    	super();
    }
    
    // Actions

    public boolean isOrderSuccessMessageDisplayed() {
        WebElement orderSuccessMessage = waitForElementToBeVisible(orderSuccessMessageBy);
        return orderSuccessMessage.isDisplayed();
    }

    public String getSectionOrderCompletedTitleText() {
        WebElement orderSuccessMessage = waitForElementToBeVisible(orderSuccessMessageBy);
        return orderSuccessMessage.getText();
    }

    public boolean isSectionOrderCompletedOrderNumberTextDisplayed() {
        WebElement orderNumberText = waitForElementToBeVisible(sectionOrderCompletedOrderNumberTextBy);
        return orderNumberText.isDisplayed();
    }

    public boolean isSectionOrderCompletedDetailsLinkDisplayed() {
        WebElement detailsLink = waitForElementToBeVisible(sectionOrderCompletedDetailsLinkBy);
        return detailsLink.isDisplayed();
    }

    public void clickContinue() {
        WebElement continueButton = waitForElementToBeClickable(sectionOrderCompletedContinueButtonBy);
        continueButton.click();
    }

    public String extractOrderNumber() {
        WebElement orderNumberText = waitForElementToBeVisible(sectionOrderCompletedOrderNumberTextBy);
        String fullText = orderNumberText.getText().trim();
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d+");
        java.util.regex.Matcher matcher = pattern.matcher(fullText);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }
}
