package tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import pages.BooksPage;
import pages.CheckoutPage;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.LoginPage;
import pages.ShoppingCartPage;
import utils.RetryAnalyzer;

public class ShoppingE2EFlowTest extends BaseTest {

	HeaderPage headerPage;
	LoginPage loginPage;
	DashboardPage dashboardPage;
	BooksPage booksPage;
	ShoppingCartPage shoppingCartPage;
	CheckoutPage checkoutPage;

	String expectedName = cartReader.getProperty("cart.product.name");
	double expectedPrice = Double.parseDouble(cartReader.getProperty("cart.product.price"));
	double expectedQuantity = Double.parseDouble(cartReader.getProperty("cart.product.quantity"));
	double expectedTotal = Double.parseDouble(cartReader.getProperty("cart.product.total"));
	double expectedShipping = Double.parseDouble(cartReader.getProperty("cart.product.shipping.price"));
	double expectedTax = Double.parseDouble(cartReader.getProperty("cart.product.tax"));


	public ShoppingE2EFlowTest() {

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
		checkoutPage = new CheckoutPage();

		log.info("Add product to cart");
		headerPage.clickLoginLink();
		loginPage.loginRegisteredUser(testDataReader.getProperty("valid.email"),
				testDataReader.getProperty("valid.password"));
		
		log.info("Clear shopping cart");
		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.clearShoppingCart();

		dashboardPage.clickOnBooksLink();
		booksPage.addBookWithId13ToCart();
		booksPage.verifySuccessBarNotificationAfterAddProductToCart(
		booksReader.getProperty("cart.add.success.bar.notification"));

	}

	@Test(description = "Shopping e2e flow", retryAnalyzer = RetryAnalyzer.class)
	public void shoppingE2EFlow() {
		
		log.info("Click on shopping cart link");
		headerPage.clickOnShoppingCartLink();

		log.info("Check shopping cart informations");
		Assert.assertTrue(shoppingCartPage.isProductImageDisplayed(), "Product image is not displayed");
		Assert.assertEquals(shoppingCartPage.getNameOfProductInShoppingCart(), expectedName,
				"Product name does not match");
		Assert.assertEquals(shoppingCartPage.getPriceInShoppingCart(), expectedPrice, 0.01,
				"Product price does not match");
		Assert.assertEquals(shoppingCartPage.getQuantityInShoppingCart(), expectedQuantity, 0.01,
				"Product quantity does not match");
		Assert.assertEquals(shoppingCartPage.getProductSubtotalInShoppingCartInformation(), expectedTotal, 0.01,
				"Product subtotal does not match");
		
		log.info("Select country");
		shoppingCartPage.selectCountry(cartReader.getProperty("cart.country"));
		Assert.assertTrue(shoppingCartPage.selectedCountryIsDisplayed(cartReader.getProperty("cart.country")),
				"Selected country is incorrect");

		log.info("Click estimate shipping button");
		shoppingCartPage.clickEstimateShippingButton();

		log.info("Verify shipping results summary");
		Assert.assertTrue(shoppingCartPage.shippingResultsSummaryIsDisplayed(),
				"Shipping results summary does not displayed");

		log.info("Check total information in shopping cart summary");
		Assert.assertTrue(shoppingCartPage.totalInfoSummaryInShoppingCartIsDisplayed(),
				"Total summary table is not displayed");

		Assert.assertEquals(shoppingCartPage.getSubtotalPriceInShoppingCartSummary(), expectedPrice, 0.01,
				"Product price does not match");
		Assert.assertEquals(shoppingCartPage.getShippingPriceInShoppingCartSummary(), expectedShipping, 0.01,
				"Product shipping does not match");
		Assert.assertEquals(shoppingCartPage.getTaxInShoppingCartSummary(), expectedTax, 0.01,
				"Product tax does not match");
		Assert.assertEquals(shoppingCartPage.getTotalPriceInShoppingCartSummary(), expectedTotal, 0.01,
				"Product subtotal does not match");

		log.info("Submit order with checkout button");
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();

		Assert.assertEquals(shoppingCartPage.getCheckoutCurrentUrl(), cartReader.getProperty("cart.expected.url"),
				"Checkout URL does not match");
		
		//enter billing informations
		log.info("Verify pre-filled billing information");
		Assert.assertEquals(checkoutPage.getBillingFirstName(), cartReader.getProperty("valid.firstname"), "First name mismatch");
		Assert.assertEquals(checkoutPage.getBillingLastName(), cartReader.getProperty("valid.lastname"), "Last name mismatch");
		Assert.assertEquals(checkoutPage.getBillingEmail(), cartReader.getProperty("valid.email"), "Email mismatch");
		
		log.info("Fill remaining billing address fields");
		checkoutPage.selectBillingCountry(cartReader.getProperty("valid.billing.country"));
		Assert.assertEquals(checkoutPage.getSelectedBillingCountry(), cartReader.getProperty("valid.billing.country"), "Billing country was not selected correctly");

		checkoutPage.enterBillingCity(cartReader.getProperty("valid.billing.city"));
		Assert.assertEquals(checkoutPage.getBillingCity(), cartReader.getProperty("valid.billing.city"), "Billing city mismatch");

		checkoutPage.enterBillingAddress1(cartReader.getProperty("valid.billing.address1"));
		Assert.assertEquals(checkoutPage.getBillingAddress1(), cartReader.getProperty("valid.billing.address1"), "Billing address mismatch");

		checkoutPage.enterBillingZipPostalCode(cartReader.getProperty("valid.billing.zippostalcode"));
		Assert.assertEquals(checkoutPage.getBillingZipPostalCode(), cartReader.getProperty("valid.billing.zippostalcode"), "Billing postal code mismatch");

		checkoutPage.enterBillingPhoneNumber(cartReader.getProperty("valid.billing.phonenumber"));
		Assert.assertEquals(checkoutPage.getBillingPhoneNumber(), cartReader.getProperty("valid.billing.phonenumber"), "Billing phone number mismatch");
		
		log.info("Proceed to next step from billing address");
		checkoutPage.clickBillingContinueButton();
		
		
	}

	@AfterTest
	public void tearDown() {

		log.info("Closing the browser after logout completion.");
		quitDriver();

	}
}
