package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import demowebshop.base.BasePage;

public class PaymentInformationPage extends BasePage {
	
	public PaymentInformationPage() {
		
		super(); 
		
	}
	
	// Elements

    @FindBy(id = "checkout-step-payment-info")
    private WebElement paymentInfoSection;

    @FindBy(id = "checkout-payment-info-load")
    private WebElement paymentInfoContent;
    
    @FindBy(xpath="//div[@id='checkout-payment-info-load']//div[contains(@class,'payment-info')]//p")
    private WebElement paymentInfoTable;

    @FindBy(xpath = "//input[contains(@class,'payment-info-next-step-button')]")
    private WebElement continueButton;

    // Actions

    public void waitForPaymentInfoSection() {
    	
        wait.until(ExpectedConditions.visibilityOf(paymentInfoSection));
        wait.until(ExpectedConditions.visibilityOf(paymentInfoContent));
    }

    public boolean isPaymentInfoDisplayed() {
    	
    	waitForElementToBeVisible(paymentInfoTable);
        return paymentInfoTable.isDisplayed();
        
    }

    public String getPaymentInfoText() {
    	
    	waitForElementToBeVisible(paymentInfoTable);
    	return paymentInfoTable.getText();
    	
    }
    
    public void clickContinue() {
    	
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
        wait.until(ExpectedConditions.invisibilityOf(continueButton));
    }

}
