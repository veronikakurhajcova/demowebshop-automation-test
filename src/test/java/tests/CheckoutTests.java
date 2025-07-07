package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import pages.BooksPage;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.LoginPage;
import utils.RetryAnalyzer;

public class CheckoutTests extends BaseTest {

	HeaderPage headerPage;
	LoginPage loginPage;
	DashboardPage dashboardPage;
	BooksPage booksPage;

	public CheckoutTests() {

		super();

	}

	@BeforeMethod
	public void setup() {

		initializeBrowser();

		headerPage = new HeaderPage();
		loginPage = new LoginPage();
		dashboardPage = new DashboardPage();
		booksPage = new BooksPage();

		log.info("Add product to cart");
		headerPage.clickLoginLink();
		loginPage.loginRegisteredUser(testDataReader.getProperty("valid.email"),
				testDataReader.getProperty("valid.password"));
		
		dashboardPage.clickOnBooksLink();
		booksPage.addBookWithId13ToCart();
		booksPage.verifySuccessBarNotificationAfterAddProductToCart(booksReader.getProperty("cart.add.success.bar.notification"));

	}
	
	@Test(description = "Checkout cart information", retryAnalyzer = RetryAnalyzer.class)
	public void checkoutCart() {
		
		headerPage.clickOnShoppingCartLink();
		
	}

}
