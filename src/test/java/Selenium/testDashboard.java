package Selenium;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class testDashboard {
	private static WebDriver driver;
	private static final String SONAR_HOME_PAGE_URL = "localhost:9000";
	private static final String SONAR_HOME_PAGE_URL2 = "localhost:9090";
	private static final String SONAR_DASHBOARD_INDEX_1_CSS = "a[href='/dashboard/index/1']";
	private static final String SONAR_DASHBOARD_INDEX_1_STRING = "";
	private static final String SONAR_HOME_PAGE_TITLE = "Sonar";
	
	   // Waiting 15 seconds for an element to be present on the page, checking
	   // for its presence once every 1 second.
	   Wait<WebDriver> customWait = new FluentWait<WebDriver>(driver)
	       .withTimeout(15L, TimeUnit.SECONDS)
	       .pollingEvery(1, TimeUnit.SECONDS)
	       .ignoring(NoSuchElementException.class);
	
	@BeforeClass
	public static void setup() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	@Before
	public void beforetest() {
		driver.navigate().to(SONAR_HOME_PAGE_URL);
		
		// wait for the title tag to load in the page
		WebElement title = customWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("title")));
		
		// if the title is not Sonar, try the other url
		if (!title.getText().equals(SONAR_HOME_PAGE_TITLE)){
			driver.navigate().to(SONAR_HOME_PAGE_URL2);
		}
		
		driver.findElement(By.cssSelector(SONAR_DASHBOARD_INDEX_1_CSS)).click();
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

}
