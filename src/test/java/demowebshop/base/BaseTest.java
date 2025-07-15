package demowebshop.base;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import demowebshop.utils.PropertiesReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected PropertiesReader configReader;
    protected PropertiesReader testDataReader;
    protected PropertiesReader dashboardReader;
    protected PropertiesReader messageReader;
    protected PropertiesReader cartReader;

	
	 public BaseTest() {
		 
	        try {
	            configReader = new PropertiesReader("src/test/resources/config/config.properties");
	            testDataReader = new PropertiesReader("src/test/resources/testdata/validUser.properties");
	            dashboardReader = new PropertiesReader("src/test/resources/testdata/dashboard.properties");
	            cartReader = new PropertiesReader("src/test/resources/testdata/cart.properties");
	            messageReader = new PropertiesReader("src/test/resources/testdata/message.properties");
	            log.info("Properties files loaded successfully.");
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Config.properties file not found or could not be loaded", e);
	        }
	    }

	public static WebDriver getDriver() {
		
		return driver.get();
	}

	// Initialize the WebDriver instance
	public WebDriver initializeBrowser() {
		
		String browser = configReader.getProperty("browser");
		String url = configReader.getProperty("url");
		
			//Set up ChromeDriver 
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-search-engine-choice-screen");
			options.addArguments("start-maximized");
			options.addArguments("--headless");
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