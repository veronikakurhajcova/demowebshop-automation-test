package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import demowebshop.base.BasePage;

public class CompletedCheckoutPage extends BasePage {

	public CompletedCheckoutPage() {

		super();

	}

	// Elements

	@FindBy(css = ".section.order-completed>.title>strong")
	private WebElement orderSuccessMessage;

	@FindBy(css = ".section.order-completed>.details>li")
	private WebElement sectionOrderCompletedOrderNumberText;

	@FindBy(css = ".section.order-completed>.details>li>a")
	private WebElement sectionOrderCompletedDetailsLink;

	@FindBy(css = ".buttons>.order-completed-continue-button")
	private WebElement sectionOrderCompletedContinueButton;

	// Actions

	public boolean isOrderSuccessMessageDisplayed() {

		waitForElementToBeVisible(orderSuccessMessage);
		return orderSuccessMessage.isDisplayed();

	}

	public String getSectionOrderCompletedTitleText() {

		return orderSuccessMessage.getText();
	}

	public boolean isSectionOrderCompletedOrderNumberTextDisplayed() {

		waitForElementToBeVisible(sectionOrderCompletedOrderNumberText);
		return sectionOrderCompletedOrderNumberText.isDisplayed();

	}
	
	public boolean isSectionOrderCompletedDetailsLinkDisplayed() {
		
		waitForElementToBeVisible(sectionOrderCompletedDetailsLink);
		return sectionOrderCompletedDetailsLink.isDisplayed();
		
	}
	
	public void clickContinue() {
		
		waitForElementToBeClickable(sectionOrderCompletedContinueButton);
		click(sectionOrderCompletedContinueButton);
		
	}
	
	public String extractOrderNumber() {
		
	    waitForElementToBeVisible(sectionOrderCompletedOrderNumberText);
	    String fullText = sectionOrderCompletedOrderNumberText.getText().trim();
	    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d+");
	    java.util.regex.Matcher matcher = pattern.matcher(fullText);
	    if (matcher.find()) {
	        return matcher.group();
	    } else {
	        return "";
	    }
	}


}
