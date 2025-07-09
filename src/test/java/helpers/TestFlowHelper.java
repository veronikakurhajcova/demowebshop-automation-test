package helpers;

import org.testng.Assert;

import demowebshop.utils.PropertiesReader;
import pages.BooksPage;
import pages.CheckoutPage;
import pages.DashboardPage;
import pages.HeaderPage;
import pages.LoginPage;
import pages.PaymentInformationPage;
import pages.PaymentMethodPage;
import pages.RegisterPage;
import pages.ShippingMethodPage;
import pages.ShippingPage;
import pages.ShoppingCartPage;
import utils.Utils;

public class TestFlowHelper {

	public static String registeredUserEmail;

	public static void registerUserAndAddBookToCart(HeaderPage headerPage, RegisterPage registerPage,
			DashboardPage dashboardPage, BooksPage booksPage, PropertiesReader testDataReader,
			PropertiesReader messageReader) {
		
		String randomEmail = Utils.generateRandomEmail();
		registeredUserEmail = randomEmail;

		headerPage.clickRegisterLink();
		registerPage.registerUser(testDataReader.getProperty("valid.firstname"),
				testDataReader.getProperty("valid.lastname"), randomEmail,
				testDataReader.getProperty("valid.password"));
		registerPage.clickContinue();

		dashboardPage.clickOnBooksLink();
		booksPage.addBookWithId13ToCart();
		booksPage.verifySuccessBarNotificationAfterAddProductToCart(
				messageReader.getProperty("cart.add.success.bar.notification"));
	}

	// Register user shopping flow

	public static void proceedToShoppingCartAndEstimateShipping(HeaderPage header, ShoppingCartPage cart,
			PropertiesReader cartReader) {
		
		header.clickOnShoppingCartLink();
		cart.selectCountry(cartReader.getProperty("cart.country"));
		cart.clickEstimateShippingButton();
		cart.clickTermsOfService();
		
		cart.submitOrder();
	}

	public static void fillBillingInformation(CheckoutPage checkout, PropertiesReader cartReader) {
		
		checkout.selectBillingCountry(cartReader.getProperty("valid.billing.country"));
		checkout.enterBillingCity(cartReader.getProperty("valid.billing.city"));
		checkout.enterBillingAddress1(cartReader.getProperty("valid.billing.address1"));
		checkout.enterBillingZipPostalCode(cartReader.getProperty("valid.billing.zippostalcode"));
		checkout.enterBillingPhoneNumber(cartReader.getProperty("valid.billing.phonenumber"));
		
		checkout.clickBillingContinueButton();
	}

	public static void proceedToShippingMethod(ShippingPage shippingPage) {
		shippingPage.clickContinue();
	}

	public static void proceedToPaymentMethod(ShippingMethodPage shippingMethodPage) {
		
		shippingMethodPage.clickContinue();
	}

	public static void proceedToPaymentInformation(PaymentMethodPage paymentMethodPage) {
		
		paymentMethodPage.clickContinue();
	}

	public static void proceedToConfirmOrder(PaymentInformationPage paymentInformationPage) {
		
		paymentInformationPage.clickContinue();
	}

	// Login user shopping flow

	public static void loginAndPrepareCart(HeaderPage headerPage, LoginPage loginPage, ShoppingCartPage shoppingCartPage, DashboardPage dashboardPage,
			BooksPage booksPage, PropertiesReader cartReader, PropertiesReader messageReader) {
		
		headerPage.clickLoginLink();
		loginPage.loginRegisteredUser(cartReader.getProperty("valid.email"), cartReader.getProperty("valid.password"));
		headerPage.clickOnShoppingCartLink();
		shoppingCartPage.clearShoppingCart();
		dashboardPage.clickOnBooksLink();
		booksPage.addBookWithId13ToCart();
		booksPage.verifySuccessBarNotificationAfterAddProductToCart(
				messageReader.getProperty("cart.add.success.bar.notification"));
	}
	
	public static void verifyAndUsePrefilledBillingInformation(CheckoutPage checkoutPage, PropertiesReader cartReader) {

        String billingAddressText = checkoutPage.getSelectedBillingAddressText();
        Assert.assertTrue(billingAddressText.contains(cartReader.getProperty("valid.lastname")), "Billing address mismatch");
        checkoutPage.clickBillingContinueButton();
    }
	
	 public static void verifyAndUsePrefilledShippingInformation(ShippingPage shippingPage, PropertiesReader cartReader) {
	        String shippingAddressText = shippingPage.getSelectedShippingAddressText();
	        Assert.assertTrue(shippingAddressText.contains(cartReader.getProperty("valid.lastname")), "Shipping address mismatch");
	        shippingPage.clickContinue();
	    }

	    public static void selectShippingMethodAndContinue(ShippingMethodPage shippingMethodPage) {
	        shippingMethodPage.waitForShippingMethodSection();
	        Assert.assertTrue(shippingMethodPage.isShippingMethodDisplayed(), "Shipping method not displayed");
	        shippingMethodPage.selectGroundShippingMethod();
	        Assert.assertTrue(shippingMethodPage.isGroundShippingMethodSelected(), "Ground shipping method not selected");
	        shippingMethodPage.clickContinue();
	    }

	    public static void selectPaymentMethodAndContinue(PaymentMethodPage paymentMethodPage) {
	        paymentMethodPage.selectCashOnDeliveryPaymentMethod();
	        Assert.assertTrue(paymentMethodPage.isCasOnDeliveryPaymentMethodSelected(), "COD payment method not selected");
	        paymentMethodPage.clickContinue();
	    }

	    public static void verifyPaymentInformationAndContinue(PaymentInformationPage paymentInformationPage, PropertiesReader messageReader) {
	        Assert.assertTrue(paymentInformationPage.isPaymentInfoDisplayed(), "Payment info not displayed");
	        Assert.assertEquals(paymentInformationPage.getPaymentInfoText(), messageReader.getProperty("payment.information.cod"));
	        paymentInformationPage.clickContinue();
	    }


}
