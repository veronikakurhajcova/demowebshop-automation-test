package demowebshop.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage extends BaseTest {
	protected WebDriver driver;
	protected WebDriverWait wait;

	// Initialize common actions for all pages
	public BasePage() {
		
		this.driver = BaseTest.getDriver();
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	}

	// Actions
	public void navigateTo(String url) {
		
		driver.get(url);
	}

	public String getPageTitle() {
		
		return driver.getTitle();
	}

	public String getCurrentUrl() {
		
		return driver.getCurrentUrl();
	}

	public void click(WebElement element) {
		
	    try {
	        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	    } catch (ElementClickInterceptedException e) {
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	    }
	}

	public void sendKeys(WebElement element, String text) {
		
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.clear();
		element.sendKeys(text);
	}

	public void waitForElementToBeVisible(WebElement element) {
		
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementToBeVisibleByLocator(By locator) {
		
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public boolean isElementPresent(WebElement element) {
		
	    try {
	        wait.until(ExpectedConditions.visibilityOf(element));
	        return true; 
	    } catch (TimeoutException e) {
	        return false; 
	    }
	}

	public void waitForElementToBeClickable(WebElement element) {
		
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void scrollToElement(WebElement element) {
		
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

}
