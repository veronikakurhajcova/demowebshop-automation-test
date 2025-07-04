package demowebshop.base;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import demowebshop.utils.PropertiesReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private PropertiesReader reader;

	
	 public Base() {
	        try {
	            reader = new PropertiesReader("src/test/resources/config.properties");
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Config.properties file not found or could not be loaded", e);
	        }
	    }

	public WebDriver getDriver() {
		return driver.get();
	}

	// Initialize the WebDriver instance
	public WebDriver initializeBrowser() {
		String browser = reader.getProperty("browser");
		String url = reader.getProperty("url");
		
			//Set up ChromeDriver 
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-search-engine-choice-screen");
			options.addArguments("start-maximized");
			driver.set(new ChromeDriver(options));
		} 
		else if (browser.equalsIgnoreCase("firefox")) {
			// Set up FirefoxDriver
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
		} 
		else if (browser.equalsIgnoreCase("edge")) {
			// Set up EdgeDriver
			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver());
		} 
		else {
			throw new IllegalArgumentException("Browser " + browser + " not supported");
		}
		
		WebDriver wdr = driver.get();
		wdr.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		wdr.manage().window().maximize();
		wdr.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wdr.get(url); 
		
		return wdr;
		
	}

	public static void quitDriver() {
	    WebDriver wdr = driver.get();
	    if (wdr != null) {
	        wdr.quit();
	        driver.remove();
	    }
	}

}
