package utils;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	private static ExtentReports extent;

	public synchronized static ExtentReports getExtentReports() {
		
		if (extent == null) {
			String reportDir = "test-output";
			File reportFolder = new File(reportDir);
			if (!reportFolder.exists()) {
				reportFolder.mkdir();
			}

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportDir + "/extent-spark.html");
			sparkReporter.config().setDocumentTitle("DemoWebShop Test Report");
			sparkReporter.config().setReportName("Regression Test Results");

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);

			extent.setSystemInfo("Tester", "Veronika");
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			
		}
		
		return extent;
		
	}

}
