package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import pages.BooksPage;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.LoginPage;
import pages.ShoppingCartPage;
import utils.RetryAnalyzer;

public class CheckoutTests extends BaseTest {

	HeaderPage headerPage;
	LoginPage loginPage;
	DashboardPage dashboardPage;
	BooksPage booksPage;
	ShoppingCartPage shoppingCartPage;
	
	String expectedName = cartReader.getProperty("cart.product.name");
	double expectedPrice = Double.parseDouble(cartReader.getProperty("cart.product.price"));
	double expectedQuantity = Double.parseDouble(cartReader.getProperty("cart.product.quantity"));
	double expectedTotal = Double.parseDouble(cartReader.getProperty("cart.product.total"));
	double expectedShipping = Double.parseDouble(cartReader.getProperty("cart.product.shipping.price"));


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
		shoppingCartPage = new ShoppingCartPage();

		log.info("Add product to cart");
		headerPage.clickLoginLink();
		loginPage.loginRegisteredUser(testDataReader.getProperty("valid.email"),
				testDataReader.getProperty("valid.password"));

		dashboardPage.clickOnBooksLink();
		booksPage.addBookWithId13ToCart();
		booksPage.verifySuccessBarNotificationAfterAddProductToCart(
				booksReader.getProperty("cart.add.success.bar.notification"));

	}

	@Test(description = "Checkout cart information", retryAnalyzer = RetryAnalyzer.class)
	public void checkoutShoppingCart() {

		log.info("Click on shopping cart link");
		headerPage.clickOnShoppingCartLink();

		log.info("Check shopping cart informations");
		Assert.assertTrue(shoppingCartPage.isProductImageDisplayed(), "Product image is not displayed");
		Assert.assertEquals(shoppingCartPage.getNameOfProductInShoppingCart(), expectedName, "Product name does not match");
		Assert.assertEquals(shoppingCartPage.getPriceInShoppingCart(), expectedPrice, 0.01, "Product price does not match");
		Assert.assertEquals(shoppingCartPage.getQuantityInShoppingCart(), expectedQuantity, 0.01, "Product quantity does not match");
		Assert.assertEquals(shoppingCartPage.getProductSubtotalInShoppingCartInformation(), expectedTotal, 0.01, "Product subtotal does not match");

	}
	
	@Test(description = "Enter estimate shipping", retryAnalyzer = RetryAnalyzer.class)
	public void shouldDisplayShippingEstimatesForSelectedCountry() {
		
		log.info("Select country");
		shoppingCartPage.selectCountry(cartReader.getProperty("cart.country"));
		Assert.assertTrue(shoppingCartPage.selectedCountryIsDisplayed(cartReader.getProperty("cart.country")), "Selected country is incorrect");
		
		log.info("Click estimate shipping button");
		shoppingCartPage.clickEstimateShippingButton();
		
		log.info("Verify shipping results summary");
		Assert.assertTrue(shoppingCartPage.shippingResultsSummaryIsDisplayed(),"Shipping results summary does not displayed");
		
	}
	
	@Test(description = "Verify total info in shopping cart summary")
	public void verifyTotalInfoInShoppingCartSummary() {
		
		log.info("Check total information in shopping cart summary");
		Assert.assertTrue(shoppingCartPage.totalInfoSummaryInShoppingCartIsDisplayed(), "Total summary table is not displayed");
		
		Assert.assertEquals(shoppingCartPage.getSubtotalPriceInShoppingCartSummary(), expectedPrice, 0.01, "Product price does not match");
		Assert.assertEquals(shoppingCartPage.getShippingPriceInShoppingCartSummary(), expectedShipping, 0.01, "Product shipping does not match");
		Assert.assertEquals(shoppingCartPage.getTaxInShoppingCartSummary(), expectedQuantity, 0.01, "Product tax does not match");
		Assert.assertEquals(shoppingCartPage.getTotalPriceInShoppingCartSummary(), expectedTotal, 0.01, "Product subtotal does not match");
			
	}
	
	@Test(description="Submit order", retryAnalyzer=RetryAnalyzer.class)
	public void submitOrderWithProduct() {
		
		log.info("Submit order with checkout button");
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();
		
		
		Assert.assertEquals(shoppingCartPage.getCheckoutCurrentUrl(), cartReader.getProperty("cart.expected.url"), "Checkout URL does not match");	}

}
