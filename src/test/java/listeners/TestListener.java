package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import demowebshop.base.BaseTest;
import utils.Utils;

public class TestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		
		System.out.println("Test started: " + result.getName());
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		System.out.println("Test passed: " + result.getName());
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		System.out.println("Test failed: " + result.getName());
		System.out.println("Error: " + result.getThrowable());
		
		WebDriver driver = BaseTest.getDriver();
	    String screenshotPath = Utils.takeScreenshot(driver, result.getName());
	    System.out.println("Screenshot saved at: " + screenshotPath);
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		System.out.println("Test skipped: " + result.getName());
		
	}

}
