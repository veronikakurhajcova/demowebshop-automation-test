package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import demowebshop.base.BasePage;

public class ShippingMethodPage extends BasePage {
	
	public ShippingMethodPage() {
		
		super();
		
	}
	
	// Elements

    @FindBy(xpath = "//input[@type='button' and contains(@class,'shipping-method-next-step-button')]")
    private WebElement continueButton;

    @FindBy(id = "checkout-step-shipping-method")
    private WebElement shippingMethodSection;

    @FindBy(id = "checkout-shipping-method-load")
    private WebElement shippingMethodContent;
    
    @FindBy(id="shippingoption_0")
    private WebElement shippingMethodGround;

    // Actions

    public void waitForShippingMethodSection() {
    	
        wait.until(ExpectedConditions.visibilityOf(shippingMethodSection));
        wait.until(ExpectedConditions.visibilityOf(shippingMethodContent));
    }

    public boolean isShippingMethodDisplayed() {
    	
        waitForShippingMethodSection();
        return shippingMethodContent.isDisplayed() && !shippingMethodContent.getText().isEmpty();
    }
 
    
    public void selectGroundShippingMethod() {
    	
    	 if (!shippingMethodGround.isSelected()) {
    	        shippingMethodGround.click();
    	        wait.until(ExpectedConditions.elementToBeSelected(shippingMethodGround));
    	    }
    }
    
    public boolean isGroundShippingMethodSelected() {
    	
    	return shippingMethodGround.isSelected();
    }


    public void clickContinue() {
    	
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
        wait.until(ExpectedConditions.invisibilityOf(continueButton));
    }
	
	

}
