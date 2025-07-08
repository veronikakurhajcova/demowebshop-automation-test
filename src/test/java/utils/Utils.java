package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;



public class Utils {
	
	public static String takeScreenshot(WebDriver driver, String testName) {
	    TakesScreenshot ts = (TakesScreenshot) driver;
	    File source = ts.getScreenshotAs(OutputType.FILE);

	    String dir = "test-output/screenshots"; 
	    File screenshotsDir = new File(dir);
	    if (!screenshotsDir.exists()) {
	        screenshotsDir.mkdirs(); 
	    }

	    String fileName = testName + "_" + System.currentTimeMillis() + ".png";
	    File destination = new File(screenshotsDir, fileName);

	    try {
	        FileUtils.copyFile(source, destination);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return "screenshots/" + fileName; 
	}
}
