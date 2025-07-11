package tests.positiveTests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import demowebshop.base.BaseTest;
import demowebshop.utils.PropertiesReader;
import helpers.TestFlowHelper;
import pages.BooksPage;
import pages.CheckoutPage;
import pages.CompletedCheckoutPage;
import pages.ConfirmOrderPage;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.IndexPage;
import pages.PaymentInformationPage;
import pages.PaymentMethodPage;
import pages.RegisterPage;
import pages.ShippingMethodPage;
import pages.ShippingPage;
import pages.ShoppingCartPage;
import utils.RetryAnalyzer;

public class EndToEndShoppingFlowAfterRegistrationTest extends BaseTest {

	HeaderPage headerPage;
	IndexPage indexPage;
	RegisterPage registerPage;
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

	public EndToEndShoppingFlowAfterRegistrationTest() {

		super();
		try {
			testDataReader = new PropertiesReader("src/test/resources/testdata/validUser.properties");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("validUser.properties file not found or could not be loaded", e);
		}

	}

	@BeforeMethod
	public void setup() {

		initializeBrowser();

		headerPage = new HeaderPage();
		indexPage = new IndexPage();
		registerPage = new RegisterPage();
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

		TestFlowHelper.registerUserAndAddBookToCart(headerPage, registerPage, dashboardPage, booksPage, testDataReader,
				messageReader);

	}

	@Test(description = "Check shopping cart details after user registration", retryAnalyzer = RetryAnalyzer.class)
	public void shouldDisplayCorrectProductDetailsAfterRegistration() {

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

	@Test(description = "Estimate shipping and check totals after user registration", retryAnalyzer = RetryAnalyzer.class)
	public void shouldEstimateShippingAndDisplayCorrectTotalsAfterRegistration() {

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

		log.info("Submit order with checkout button");
		shoppingCartPage.clickTermsOfService();
		shoppingCartPage.submitOrder();

		Assert.assertEquals(shoppingCartPage.getCheckoutCurrentUrl(), cartReader.getProperty("cart.expected.url"),
				"Checkout URL does not match");

	}

	@Test(description = "Billing information after user registration", retryAnalyzer = RetryAnalyzer.class)
	public void shouldVerifyPrefilledBillingFieldsAndEnterRemainingInformationAfterRegistration() {
		
		// Prepare and submit cart
		TestFlowHelper.proceedToShoppingCartAndEstimateShipping(headerPage, shoppingCartPage, cartReader);
		
		//  Verify prefilled billing fields
		
		log.info("Check prefilled information in billing address");
		Assert.assertEquals(checkoutPage.getBillingFirstName(), cartReader.getProperty("valid.firstname"),
				"Pre-filled FirstName does not match with registered user firstname");

		Assert.assertEquals(checkoutPage.getBillingLastName(), cartReader.getProperty("valid.lastname"),
				"Pre-filled lastname does not match with registered user lastname");

		Assert.assertEquals(checkoutPage.getBillingEmail(), TestFlowHelper.registeredUserEmail,
				"Pre-filled email does not match with registered user email");
		
		// Fill in rest of billing info
		
		checkoutPage.selectBillingCountry(cartReader.getProperty("valid.billing.country"));
		Assert.assertEquals(checkoutPage.getSelectedBillingCountry(), cartReader.getProperty("valid.billing.country"),
			    "Selected billing country does not match");

		checkoutPage.enterBillingCity(cartReader.getProperty("valid.billing.city"));
		checkoutPage.enterBillingAddress1(cartReader.getProperty("valid.billing.address1"));
		checkoutPage.enterBillingZipPostalCode(cartReader.getProperty("valid.billing.zippostalcode"));
		checkoutPage.enterBillingPhoneNumber(cartReader.getProperty("valid.billing.phonenumber"));
		
		checkoutPage.clickBillingContinueButton();
		
	}
	
	@Test(description = "Shipping information after user registration", retryAnalyzer = RetryAnalyzer.class)
	public void verifyPrefilledShippingInformationAfterRegistration() {

		// Prepare and submit cart

		TestFlowHelper.proceedToShoppingCartAndEstimateShipping(headerPage, shoppingCartPage, cartReader);
		TestFlowHelper.fillBillingInformation(checkoutPage, cartReader);
				
		String actualShippingAddressText = shippingPage.getSelectedShippingAddressText();
		Assert.assertTrue(shippingPage.isSelectedShippingAddressContaining(cartReader.getProperty("valid.lastname")),
				"Shipping address mismatch. Actual: " + actualShippingAddressText);

		shippingPage.clickContinue();

	}
	
	@Test(description = "User selects shipping method", retryAnalyzer = RetryAnalyzer.class)
	public void selectShippingMethodAfterRegistration() {

		TestFlowHelper.proceedToShoppingCartAndEstimateShipping(headerPage, shoppingCartPage, cartReader);
		TestFlowHelper.fillBillingInformation(checkoutPage, cartReader);
		TestFlowHelper.proceedToShippingMethod(shippingPage);

		shippingMethodPage.waitForShippingMethodSection();
		Assert.assertTrue(shippingMethodPage.isShippingMethodDisplayed(), "Shipping method not displayed");

		shippingMethodPage.selectGroundShippingMethod();
		Assert.assertTrue(shippingMethodPage.isGroundShippingMethodSelected(), "Ground shipping method not selected");

		shippingMethodPage.clickContinue();

	}
	
	@Test(description = "Payment method selection after user registration", retryAnalyzer = RetryAnalyzer.class)
	public void selectPaymentMethodAfterRegistration() {

		TestFlowHelper.proceedToShoppingCartAndEstimateShipping(headerPage, shoppingCartPage, cartReader);
		TestFlowHelper.fillBillingInformation(checkoutPage, cartReader);
		TestFlowHelper.proceedToShippingMethod(shippingPage);
		TestFlowHelper.proceedToPaymentMethod(shippingMethodPage);
		
		paymentMethodPage.selectCashOnDeliveryPaymentMethod();
		Assert.assertTrue(paymentMethodPage.isCasOnDeliveryPaymentMethodSelected(), "COD payment method not selected");

		paymentMethodPage.clickContinue();

	}
	
	@Test(description = "Verify payment info content", retryAnalyzer = RetryAnalyzer.class)
	public void verifyPaymentInformationAfterRegistration() {

		TestFlowHelper.proceedToShoppingCartAndEstimateShipping(headerPage, shoppingCartPage, cartReader);
		TestFlowHelper.fillBillingInformation(checkoutPage, cartReader);
		TestFlowHelper.proceedToShippingMethod(shippingPage);
		TestFlowHelper.proceedToPaymentMethod(shippingMethodPage);
		TestFlowHelper.proceedToPaymentInformation(paymentMethodPage);


		Assert.assertTrue(paymentInformationPage.isPaymentInfoDisplayed(), "Payment info not displayed");
		Assert.assertEquals(paymentInformationPage.getPaymentInfoText(),
				messageReader.getProperty("payment.information.cod"));

		paymentInformationPage.clickContinue();

	}
	
	@Test(description = "Confirm order details content", retryAnalyzer = RetryAnalyzer.class)
	public void checkConfirmOrderDetailsAfterRegistration() {

		TestFlowHelper.proceedToShoppingCartAndEstimateShipping(headerPage, shoppingCartPage, cartReader);
		TestFlowHelper.fillBillingInformation(checkoutPage, cartReader);
		TestFlowHelper.proceedToShippingMethod(shippingPage);
		TestFlowHelper.proceedToPaymentMethod(shippingMethodPage);
		TestFlowHelper.proceedToPaymentInformation(paymentMethodPage);
		TestFlowHelper.proceedToConfirmOrder(paymentInformationPage);

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
	
	@Test(description = "Order completion after user registration", retryAnalyzer = RetryAnalyzer.class)
	public void verifyOrderCompletionAfterRegistration() {

		TestFlowHelper.proceedToShoppingCartAndEstimateShipping(headerPage, shoppingCartPage, cartReader);
		TestFlowHelper.fillBillingInformation(checkoutPage, cartReader);
		TestFlowHelper.proceedToShippingMethod(shippingPage);
		TestFlowHelper.proceedToPaymentMethod(shippingMethodPage);
		TestFlowHelper.proceedToPaymentInformation(paymentMethodPage);
		TestFlowHelper.proceedToConfirmOrder(paymentInformationPage);
		confirmOrderPage.waitForConfirmOrderSection();
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
		
		 TestFlowHelper.logout(dashboardPage, indexPage, configReader);
		 
		 log.info("Closing the browser after test completion.");
		 quitDriver();

	}
	
	

}
