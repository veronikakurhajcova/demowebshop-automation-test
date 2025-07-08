package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import demowebshop.base.BasePage;

public class ShippingPage extends BasePage {

	public ShippingPage() {

		super();

	}

	// Elements

	@FindBy(id = "shipping-address-select")
	private WebElement shippingAddressSelect;

	@FindBy(id = "shipping-buttons-container")
	private WebElement shippingButtonsContainer;

	@FindBy(xpath = "//input[@onclick='Shipping.save()']")
	private WebElement continueButton;

	// Actions

	public void waitForShippingSection() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-shipping")));
	}

	public String getSelectedShippingAddressText() {

		Select select = new Select(shippingAddressSelect);
		return select.getFirstSelectedOption().getText().trim();

	}

	public boolean isSelectedShippingAddressContaining(String expectedPartialText) {

		Select select = new Select(shippingAddressSelect);
		String selectedText = select.getFirstSelectedOption().getText();
		return selectedText.contains(expectedPartialText);

	}

	public void clickContinue() {

		wait.until(ExpectedConditions.visibilityOf(continueButton));
		scrollToElement(continueButton);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", continueButton);
		wait.until(ExpectedConditions.invisibilityOf(continueButton));
	}
}
