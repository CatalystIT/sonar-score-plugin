package Selenium;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Score {
	private static WebDriver driver;
	private static final String SONAR_HOME_PAGE_URL = "localhost:9000";
	
	@BeforeClass
	public static void setup() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	@Before
	public void beforetest() {
		driver.navigate().to(SONAR_HOME_PAGE_URL);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
