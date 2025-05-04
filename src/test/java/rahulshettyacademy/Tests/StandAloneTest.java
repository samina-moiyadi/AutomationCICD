package rahulshettyacademy.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new comments are added
		//comments added again
		//Webdriver manager dependency can be added in pom.xml and then there would be no need to set up chrome driver property
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		//declaring variables
		String productName = "ZARA COAT 3";
		
		//implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		//maximize the window
		driver.manage().window().maximize();
		
		//open url
		driver.get("https://rahulshettyacademy.com/client");
		
		//login
		driver.findElement(By.id("userEmail")).sendKeys("ShikhaD@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Idiot_123");
		driver.findElement(By.id("login")).click();
		
		//explicit wait for the products to load
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		//collecting all the product webelements into a list
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		//search for ZARA Coat3 product in the list
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.xpath("//following-sibling::button[2]")).click();

		//explicit wait for the browser
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("ngx-spinner-overlay"))));
		
		//click on cart button
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//get a list of products added to cart and check that ZARA COAT 3 is in the cart
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		System.out.println(match);
		Assert.assertTrue(match);
		
		//click on check out button
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//select country using Actions class
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("input[placeholder*='Country']")),"india").build().perform();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("//section/button[2]")).click();
		
		//click on place order
		driver.findElement(By.cssSelector("[class*='submit']")).click();
		
		//get the order placed successfully text
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		//close the browser
		driver.close();
	}

}
