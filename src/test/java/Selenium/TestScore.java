package Selenium;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestScore {
	private static WebDriver driver;
	private static final String SONAR_HOME_PAGE_URL = "localhost:9000";
	private static final String SONAR_DASHBOARD_INDEX_1_CSS = "a[href='/dashboard/index/1']";
	private static final String SONAR_DASHBOARD_INDEX_1_STRING = "";
	
	@BeforeClass
	public static void setup() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	@Before
	public void beforetest() {
		driver.navigate().to(SONAR_HOME_PAGE_URL);
		driver.findElement(By.cssSelector(SONAR_DASHBOARD_INDEX_1_CSS)).click();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
