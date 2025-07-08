package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import demowebshop.base.BaseTest;
import utils.ExtentManager;
import utils.Utils;

public class TestListener implements ITestListener {
	
	private static ExtentReports extent = ExtentManager.getExtentReports();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
		test.get().log(Status.INFO, "Test started");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		test.get().log(Status.PASS, "Test passed");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.get().log(Status.FAIL, result.getThrowable());
		
		WebDriver driver = BaseTest.getDriver();
		String screenshotPath = Utils.takeScreenshot(driver, result.getName());
		test.get().addScreenCaptureFromPath(screenshotPath);
		test.get().log(Status.FAIL, "Screenshot added");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		test.get().log(Status.SKIP, "Test skipped");
		
	}
	
	@Override
	public void onFinish(ITestContext context) {
		
		extent.flush();
		
	}

}
