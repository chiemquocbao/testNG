package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BasePage {
	WebDriver driver;

	@BeforeMethod
	public void startBrowser() {
		// setting Chromedriver properties
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("http://techfios.com/test/billing/?ng=login/");
	}

	@Test(enabled = false)
	public void validUserLogin() throws InterruptedException {

		driver.findElement(By.id("username")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("abc123");
		driver.findElement(By.name("login")).click();

		waitForElement(driver, 10, By.xpath("//h2[contains(text(),'Dashboard')]"));
	}

	@Test(enabled = false)
	public void invalidUserLogin() throws InterruptedException {

		// enter wrong credentials
		driver.findElement(By.id("username")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("xoxoxo");
		driver.findElement(By.name("login")).click();

		waitForElement(driver, 10, By.xpath("//div[@class='alert alert-danger fade in']"));

	}

	@AfterMethod
	public void close() {
		driver.close();
		driver.quit();
	}
}
