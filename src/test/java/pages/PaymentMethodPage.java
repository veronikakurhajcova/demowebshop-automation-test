package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import demowebshop.base.BasePage;

public class PaymentMethodPage extends BasePage {
	
	public PaymentMethodPage() {
		
		super();
		
	}
	
	// Elements

    @FindBy(id = "checkout-step-payment-method")
    private WebElement paymentMethodSection;

    @FindBy(id = "checkout-payment-method-load")
    private WebElement paymentMethodContent;
    
    @FindBy(id="paymentmethod_0")
    private WebElement cashOnDeliveryPaymentMethod;

    @FindBy(xpath = "//input[@type='button' and contains(@class,'payment-method-next-step-button')]")
    private WebElement continueButton;

    // Actions

    public void waitForPaymentMethodSection() {
    	
        wait.until(ExpectedConditions.visibilityOf(paymentMethodSection));
        wait.until(ExpectedConditions.visibilityOf(paymentMethodContent));
    }

    public boolean isPaymentInfoDisplayed() {
    	
        waitForPaymentMethodSection();
        return paymentMethodContent.getText().contains("Payment is not required");
    }
    
    public void selectCashOnDeliveryPaymentMethod() {
    	
    	waitForElementToBeClickable(cashOnDeliveryPaymentMethod);
    	click(cashOnDeliveryPaymentMethod);
    	
    }
    
    public boolean isCasOnDeliveryPaymentMethodSelected() {
    	
        return cashOnDeliveryPaymentMethod.isSelected();
    }


    public void clickContinue() {
    	
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
        wait.until(ExpectedConditions.invisibilityOf(continueButton));
    }

}
