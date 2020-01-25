package test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BanknCashAccount extends BasePage {
	
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
	@Test
	public void addNewAccount() throws InterruptedException {

		driver.findElement(By.id("username")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("abc123");
		driver.findElement(By.name("login")).click();

		waitForElement(driver, 10, By.xpath("//h2[contains(text(),'Dashboard')]"));
		
		driver.findElement(By.xpath("//ul[@id='side-menu']/child::li[5]/a/span[1]")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//ul[@id='side-menu']/child::li[5]/ul/li[1]")).click();
		Thread.sleep(1000);
		
		waitForElement(driver,3,By.xpath("//h5[contains(text(), 'Add New Account')]"));
		
		Random rnd = new Random();
		int randomNumber = rnd.nextInt(9999);
		String accountTitle = "Travel " + randomNumber;
		String desc = "Personal Funds " + randomNumber;
		String amount = String.valueOf(randomNumber);
		
		driver.findElement(By.name("account")).sendKeys(accountTitle);
		driver.findElement(By.name("description")).sendKeys(desc);
		driver.findElement(By.name("balance")).sendKeys(amount);
		
		driver.findElement(By.xpath("//div[@class='ibox-content']/descendant::button/i")).click();
		
		waitForElement(driver,3,By.xpath("//div[@class='alert alert-success fade in']/i"));
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		
		//Validate new account showed up in the bottom of the table
		String displayNewAccount = driver.findElement(By.xpath("//table/tbody/tr[last()]/td[2]")).getText();
		System.out.println("Expected Result: "+desc);
		System.out.println("Actual Result: "+displayNewAccount);
		
		Assert.assertEquals(displayNewAccount, desc);
		
		//Delete that account
		driver.findElement(By.xpath("//table/tbody/tr[last()]/td[3]/a[2]/i")).click();
		
		waitForElement(driver,5,By.xpath("//div[@class='bootbox-body']"));
		
		driver.findElement(By.xpath("//button[@data-bb-handler='confirm']")).click();
		
		//Validate account deleted from the bottom of the table
		boolean accountDeletedDisplay = driver.findElement(By.xpath("//div[@class='alert alert-success fade in']/i")).isDisplayed();
		Assert.assertTrue(accountDeletedDisplay, "Account is not deleted!");
	}
	
	@AfterMethod
	public void closeBrowser() {
		driver.close();
		driver.quit();
	}


}
