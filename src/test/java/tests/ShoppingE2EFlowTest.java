package tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import pages.BooksPage;
import pages.CheckoutPage;
import pages.ConfirmOrderPage;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.LoginPage;
import pages.PaymentInformationPage;
import pages.PaymentMethodPage;
import pages.ShippingMethodPage;
import pages.ShippingPage;
import pages.ShoppingCartPage;
import utils.RetryAnalyzer;

public class ShoppingE2EFlowTest extends BaseTest {

	HeaderPage headerPage;
	LoginPage loginPage;
	DashboardPage dashboardPage;
	BooksPage booksPage;
	ShoppingCartPage shoppingCartPage;
	CheckoutPage checkoutPage;
	ShippingPage shippingPage;
	ShippingMethodPage shippingMethodPage;
	PaymentMethodPage paymentMethodPage;
	PaymentInformationPage paymentInformationPage;
	ConfirmOrderPage confirmOrderPage;

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
		shippingPage = new ShippingPage();
		shippingMethodPage = new ShippingMethodPage();
		paymentMethodPage = new PaymentMethodPage();
		paymentInformationPage = new PaymentInformationPage();
		confirmOrderPage = new ConfirmOrderPage();
		
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
		
		// Check shopping cart information 
		
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
		
		// Enter billing informations
		
		checkoutPage.clickBillingContinueButton();
		
		// Check shipping section
		
		log.info("Loading shipping section");

		shippingPage.clickContinue();
		
		
		// Shipping method section
		
		log.info("Load shipping method section");
		shippingMethodPage.waitForShippingMethodSection();

		log.info("Verify shipping method is displayed");
		Assert.assertTrue(shippingMethodPage.isShippingMethodDisplayed(), "Shipping method section is empty or not visible");

		log.info("Click Continue in shipping method section");
		shippingMethodPage.clickContinue();
		
		// Payment method section

		log.info("Click continue on payment method step");
		paymentMethodPage.clickContinue();
		
		// Payment information section

		log.info("Click continue on payment information step");
		paymentInformationPage.clickContinue();
		
		// Confirm order section
		
		log.info("Wait for confirm order section");
		confirmOrderPage.waitForConfirmOrderSection();

		String billingInfo = confirmOrderPage.getBillingInfoText();
		Assert.assertTrue(billingInfo.contains(cartReader.getProperty("valid.firstname")), "Billing info - first name mismatch");
		Assert.assertTrue(billingInfo.contains(cartReader.getProperty("valid.lastname")), "Billing info - last name mismatch");
		Assert.assertTrue(billingInfo.contains(cartReader.getProperty("valid.billing.city")), "Billing info - city mismatch");
		Assert.assertTrue(billingInfo.contains(cartReader.getProperty("valid.billing.address1")), "Billing info - address mismatch");
		Assert.assertTrue(billingInfo.contains(cartReader.getProperty("valid.billing.zippostalcode")), "Billing info - zip mismatch");

		String shippingInfo = confirmOrderPage.getShippingInfoText();
		Assert.assertTrue(shippingInfo.contains(cartReader.getProperty("valid.firstname")), "Shipping info - first name mismatch");
		Assert.assertTrue(shippingInfo.contains(cartReader.getProperty("valid.lastname")), "Shipping info - last name mismatch");
		Assert.assertTrue(shippingInfo.contains(cartReader.getProperty("valid.billing.city")), "Shipping info - city mismatch");
		Assert.assertTrue(shippingInfo.contains(cartReader.getProperty("valid.billing.address1")), "Shipping info - address mismatch");
		Assert.assertTrue(shippingInfo.contains(cartReader.getProperty("valid.billing.zippostalcode")), "Shipping info - zip mismatch");

		log.info("Click confirm to place the order");
		confirmOrderPage.clickConfirm();
		
		Assert.assertTrue(confirmOrderPage.getOrderSuccessMessage().contains("Your order has been successfully processed"));

	}

	@AfterTest
	public void tearDown() {

		log.info("Closing the browser after logout completion.");
		quitDriver();

	}
}
