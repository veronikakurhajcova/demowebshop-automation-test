package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import demowebshop.base.BasePage;

public class DashboardPage extends BasePage {

    // Locators
    private final By logoutButtonBy = By.xpath("//a[@href='/logout']");
    private final By customerInfoLinkBy = By.xpath("(//a[@href='/customer/info'])[1]");
    private final By booksLinkBy = By.cssSelector("a[href='/books']");

    // Constructor
    public DashboardPage() {
        super();
    }

    // Actions

    public String getDashboardUrl() {
        return getCurrentUrl();
    }

    public String getLoggedCustomerInfo() {
        WebElement customerInfoLink = waitForElementToBeVisible(customerInfoLinkBy);
        return customerInfoLink.getText().trim();
    }

    // Shopping flow
    public void clickOnBooksLink() {
        WebElement booksLink = waitForElementToBeClickable(booksLinkBy);
        click(booksLink);
    }

    public void clickLogoutButton() {
        WebElement logoutButton = waitForElementToBeClickable(logoutButtonBy);
        click(logoutButton);
    }
}
