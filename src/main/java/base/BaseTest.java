package base;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.json.JSONArray;

public class BaseTest {
    	public RemoteWebDriver driver = null;
    	public WebDriverWait wait;
    	public JSONArray scrapedData = new JSONArray();

    	@BeforeTest
    	public void setup() {
    		WebDriverManager.chromedriver().setup();
    		ChromeOptions options = new ChromeOptions();
    		//options.addArguments("--headless");
    		driver = new ChromeDriver(options);
    		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	}

    	@AfterTest
    	public void tearDown() {
    		driver.quit();
    	}
}