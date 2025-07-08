package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import pages.BooksPage;
import pages.CheckoutPage;
import pages.CompletedCheckoutPage;
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
	CompletedCheckoutPage completedCheckoutPage;

	String expectedName;
	double expectedPrice;
	double expectedQuantity;
	double expectedTotal;
	double expectedShipping;
	double expectedTax;

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
		completedCheckoutPage = new CompletedCheckoutPage();

		expectedName = cartReader.getProperty("cart.product.name");
		expectedPrice = Double.parseDouble(cartReader.getProperty("cart.product.price"));
		expectedQuantity = Double.parseDouble(cartReader.getProperty("cart.product.quantity"));
		expectedTotal = Double.parseDouble(cartReader.getProperty("cart.product.total"));
		expectedShipping = Double.parseDouble(cartReader.getProperty("cart.product.shipping.price"));
		expectedTax = Double.parseDouble(cartReader.getProperty("cart.product.tax"));

		log.info("Login and prepare cart");
		headerPage.clickLoginLink();
		loginPage.loginRegisteredUser(testDataReader.getProperty("valid.email"),
				testDataReader.getProperty("valid.password"));
		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.clearShoppingCart();
		dashboardPage.clickOnBooksLink();
		booksPage.addBookWithId13ToCart();
		booksPage.verifySuccessBarNotificationAfterAddProductToCart(
				messageReader.getProperty("cart.add.success.bar.notification"));

	}

	@Test(description = "Check shopping cart details", retryAnalyzer = RetryAnalyzer.class)
	public void testShoppingCartDetails() {

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

	}

	@Test(description = "Estimate shipping and check totals", retryAnalyzer = RetryAnalyzer.class)
	public void testShippingAndTotals() {

		headerPage.clickOnShoppingCartLink();
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
	}

	@Test(description = "Submit order and check checkout URL", retryAnalyzer = RetryAnalyzer.class)
	public void testSubmitOrderAndCheckoutUrl() {

		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.selectCountry(cartReader.getProperty("cart.country"));
		shoppingCartPage.clickEstimateShippingButton();

		log.info("Submit order with checkout button");
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();

		Assert.assertEquals(shoppingCartPage.getCheckoutCurrentUrl(), cartReader.getProperty("cart.expected.url"),
				"Checkout URL does not match");

	}

	@Test(description = "Billing information", retryAnalyzer = RetryAnalyzer.class)
	public void testBillingInformation() {

		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.selectCountry(cartReader.getProperty("cart.country"));
		shoppingCartPage.clickEstimateShippingButton();
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();

		checkoutPage.selectPrefilledBillingAddress();
		String actualBillingAddressText = checkoutPage.getSelectedBillingAddressText();
		Assert.assertTrue(checkoutPage.isSelectedBillingAddressContaining(cartReader.getProperty("valid.lastname")),
				"Billing address mismatch. Actual: " + actualBillingAddressText);

		checkoutPage.clickBillingContinueButton();

	}

	@Test(description = "Shipping information", retryAnalyzer = RetryAnalyzer.class)
	public void testShippingInformation() {

		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.selectCountry(cartReader.getProperty("cart.country"));
		shoppingCartPage.clickEstimateShippingButton();
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();
		checkoutPage.clickBillingContinueButton();

		String actualShippingAddressText = shippingPage.getSelectedShippingAddressText();
		Assert.assertTrue(shippingPage.isSelectedShippingAddressContaining(cartReader.getProperty("valid.lastname")),
				"Shipping address mismatch. Actual: " + actualShippingAddressText);

		shippingPage.clickContinue();

	}

	@Test(description = "Shipping method selection", retryAnalyzer = RetryAnalyzer.class)
	public void testShippingMethodSelection() {

		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.selectCountry(cartReader.getProperty("cart.country"));
		shoppingCartPage.clickEstimateShippingButton();
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();
		checkoutPage.clickBillingContinueButton();
		shippingPage.clickContinue();

		shippingMethodPage.waitForShippingMethodSection();
		Assert.assertTrue(shippingMethodPage.isShippingMethodDisplayed(), "Shipping method not displayed");

		shippingMethodPage.selectGroundShippingMethod();
		Assert.assertTrue(shippingMethodPage.isGroundShippingMethodSelected(), "Ground shipping method not selected");

		shippingMethodPage.clickContinue();

	}

	@Test(description = "Payment method selection", retryAnalyzer = RetryAnalyzer.class)
	public void testPaymentMethodSelection() {

		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.selectCountry(cartReader.getProperty("cart.country"));
		shoppingCartPage.clickEstimateShippingButton();
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();
		checkoutPage.clickBillingContinueButton();
		shippingPage.clickContinue();
		shippingMethodPage.clickContinue();

		paymentMethodPage.selectCashOnDeliveryPaymentMethod();
		Assert.assertTrue(paymentMethodPage.isCasOnDeliveryPaymentMethodSelected(), "COD payment method not selected");

		paymentMethodPage.clickContinue();

	}

	@Test(description = "Payment information verification", retryAnalyzer = RetryAnalyzer.class)
	public void testPaymentInformation() {

		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.selectCountry(cartReader.getProperty("cart.country"));
		shoppingCartPage.clickEstimateShippingButton();
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();
		checkoutPage.clickBillingContinueButton();
		shippingPage.clickContinue();
		shippingMethodPage.clickContinue();
		paymentMethodPage.clickContinue();

		Assert.assertTrue(paymentInformationPage.isPaymentInfoDisplayed(), "Payment info not displayed");
		Assert.assertEquals(paymentInformationPage.getPaymentInfoText(),
				messageReader.getProperty("payment.information.cod"));

		paymentInformationPage.clickContinue();

	}

	@Test(description = "Confirm order details", retryAnalyzer = RetryAnalyzer.class)
	public void testConfirmOrderDetails() {

		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.selectCountry(cartReader.getProperty("cart.country"));
		shoppingCartPage.clickEstimateShippingButton();
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();
		checkoutPage.clickBillingContinueButton();
		shippingPage.clickContinue();
		shippingMethodPage.clickContinue();
		paymentMethodPage.clickContinue();
		paymentInformationPage.clickContinue();

		confirmOrderPage.waitForConfirmOrderSection();

		String billingInfo = confirmOrderPage.getBillingInfoText();
		Assert.assertTrue(billingInfo.contains(cartReader.getProperty("valid.firstname")),
				"Billing first name mismatch");
		Assert.assertTrue(billingInfo.contains(cartReader.getProperty("valid.lastname")), "Billing last name mismatch");
		Assert.assertTrue(billingInfo.contains(cartReader.getProperty("valid.billing.city")), "Billing city mismatch");
		Assert.assertTrue(billingInfo.contains(cartReader.getProperty("valid.billing.address1")),
				"Billing address mismatch");
		Assert.assertTrue(billingInfo.contains(cartReader.getProperty("valid.billing.zippostalcode")),
				"Billing zip mismatch");

		String shippingInfo = confirmOrderPage.getShippingInfoText();
		Assert.assertTrue(shippingInfo.contains(cartReader.getProperty("valid.firstname")),
				"Shipping first name mismatch");
		Assert.assertTrue(shippingInfo.contains(cartReader.getProperty("valid.lastname")),
				"Shipping last name mismatch");
		Assert.assertTrue(shippingInfo.contains(cartReader.getProperty("valid.billing.city")),
				"Shipping city mismatch");
		Assert.assertTrue(shippingInfo.contains(cartReader.getProperty("valid.billing.address1")),
				"Shipping address mismatch");
		Assert.assertTrue(shippingInfo.contains(cartReader.getProperty("valid.billing.zippostalcode")),
				"Shipping zip mismatch");

		confirmOrderPage.clickConfirm();

	}

	@Test(description = "Order completion", retryAnalyzer = RetryAnalyzer.class)
	public void testOrderCompletion() {

		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.selectCountry(cartReader.getProperty("cart.country"));
		shoppingCartPage.clickEstimateShippingButton();
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();
		checkoutPage.clickBillingContinueButton();
		shippingPage.clickContinue();
		shippingMethodPage.clickContinue();
		paymentMethodPage.clickContinue();
		paymentInformationPage.clickContinue();
		confirmOrderPage.clickConfirm();

		Assert.assertTrue(completedCheckoutPage.isOrderSuccessMessageDisplayed(),
				"Order success message not displayed");
		Assert.assertEquals(completedCheckoutPage.getSectionOrderCompletedTitleText(),
				messageReader.getProperty("order.completed.successfully.message"));
		Assert.assertTrue(completedCheckoutPage.isSectionOrderCompletedOrderNumberTextDisplayed(),
				"Order number not displayed");

		String orderNumber = completedCheckoutPage.extractOrderNumber();
		Assert.assertFalse(orderNumber.isEmpty(), "Order number extraction failed");
		Assert.assertTrue(completedCheckoutPage.isSectionOrderCompletedDetailsLinkDisplayed(),
				"Order details link not displayed");
	}

	@AfterMethod
	public void tearDown() {

		log.info("Closing the browser after logout completion.");
		quitDriver();

	}
}
