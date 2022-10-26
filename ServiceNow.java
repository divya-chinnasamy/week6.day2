package week6.day2;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sukgu.Shadow;

public class ServiceNow {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		//disable notifications
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		
//		WebDriverManager.chromedriver().setup();
//		ChromeDriver driver = new ChromeDriver();
//		driver.manage().window().maximize();
		
		//1.Launch ServiceNow application
		driver.get("https://dev137890.service-now.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		//2. Login with valid credentials username and password 
		driver.findElement(By.id("user_name")).sendKeys("admin");
		driver.findElement(By.id("user_password")).sendKeys("Testleaf@123");
		
		driver.findElement(By.id("sysverb_login")).click();
		
		//calling shadow class to enter into shadow root
		Shadow shadow = new Shadow(driver);
		shadow.setImplicitWait(10);
		//3. Click All
		shadow.findElementByXPath("//div[text()='All']").click();
		
		//4.Click  Incidents in Filter navigator
		shadow.findElementByXPath("//span[text()='Incidents']").click();
		
		//new button is inside the frame
		WebElement frame1 = shadow.findElementByXPath("//iframe[@id='gsft_main']");
		driver.switchTo().frame(frame1);
		
		//5. Click new 
		driver.findElement(By.xpath("//button[text()='New']")).click();
		String inumber = driver.findElement(By.xpath("//input[@id='incident.number']")).getAttribute("value");
		//fill-in the mandatory fields
		driver.findElement(By.xpath("//input[@id='incident.short_description']")).sendKeys("Testleaf Assignment");
		driver.findElement(By.xpath("//button[@id='sysverb_insert_bottom']")).click();
		driver.switchTo().defaultContent();
		System.out.println("Incident Created" + inumber);
		
		//verify the newly created incident from the incident list
		WebElement framee = shadow.findElementByXPath("//iframe[@id='gsft_main']");
		driver.switchTo().frame(framee);
		
		driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys(inumber,Keys.ENTER);
		String inumbersearch = driver.findElement(By.xpath("//tbody[@class='list2_body']//tr//td[3]/a")).getText();
		System.out.println(inumbersearch);
		driver.switchTo().defaultContent();
		
		//verify the incident number exists are not
		if(inumber.equals(inumbersearch)) {
			System.out.println("Incident exists - verified");
		}
		else {
			System.out.println("Incident not exists");
		}
			
		//driver.close();
	}

}
