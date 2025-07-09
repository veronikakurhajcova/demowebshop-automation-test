package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import helpers.TestFlowHelper;
import pages.BooksPage;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.IndexPage;
import pages.LoginPage;
import pages.ShoppingCartPage;
import utils.RetryAnalyzer;

public class AddProductToCartAfterLoginTest extends BaseTest {
	
	IndexPage indexPage;
	HeaderPage headerPage;
	LoginPage loginPage;
	DashboardPage dashboardPage;
	BooksPage booksPage;
	ShoppingCartPage shoppingCartPage;
	
	public AddProductToCartAfterLoginTest() {
		
		super();
	}
	
	
	@BeforeMethod
	public void setup() {
		
		initializeBrowser();
		
		indexPage = new IndexPage();
		headerPage = new HeaderPage();
		loginPage = new LoginPage();
		dashboardPage = new DashboardPage();
		booksPage = new BooksPage();
		shoppingCartPage = new ShoppingCartPage();
		
		headerPage.clickLoginLink();
		loginPage.loginRegisteredUser(testDataReader.getProperty("valid.email"), testDataReader.getProperty("valid.password"));
		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.clearShoppingCart();
	}
	
	@Test(description = "Add one valid product with id 13 to cart after login", retryAnalyzer = RetryAnalyzer.class)
	public void addOneProductToCart_ValidProduct() {
		
		log.info("Verify match books url");
		dashboardPage.clickOnBooksLink();
		booksPage.verifyOnBooksPage();
		
		log.info("Add book to cart");
		booksPage.addBookWithId13ToCart();
		booksPage.verifySuccessBarNotificationAfterAddProductToCart(messageReader.getProperty("cart.add.success.bar.notification"));
	
		log.info("Verify quantity in cart");
		int quantity = headerPage.getCartQuantity();
		Assert.assertEquals(quantity, 1, "In cart is not expected number of products");
		
	}
	
	@AfterMethod
	public void tearDown() {

		 TestFlowHelper.logout(dashboardPage, indexPage, configReader);
		 log.info("Closing the browser after test completion.");
		 quitDriver();
		
	}


}
