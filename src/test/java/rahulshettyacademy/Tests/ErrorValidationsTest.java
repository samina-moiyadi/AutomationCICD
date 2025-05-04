package rahulshettyacademy.Tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.Retry;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;


public class ErrorValidationsTest extends BaseTest {
	
	@Test(groups={"ErrorHandlingTests"},retryAnalyzer=Retry.class)
	public void loginErrorValidation() throws IOException
	{
				
		//open url and login
		landingPage.loginApplication("Deepa@gmail.com", "Idiot");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		
	}
	
	@Test(retryAnalyzer=Retry.class)
	public void productErrorValidation() throws IOException
	{

		//declaring variables
		String productName = "ZARA COAT 3";
						
		//open url and login
		ProductCatalogue productCatalogue = landingPage.loginApplication("ShikhaD@gmail.com", "Idiot_123");
		
		//collecting all the product webelements into a list
		productCatalogue.getProductList();
		
		//search for ZARA Coat3 product in the list and add it to the cart
		productCatalogue.addProductToCart(productName);
		
		//click on cart button using child class
		CartPage cartPage = productCatalogue.goToCartPage();
		
		//get a list of products added to cart and check that ZARA COAT 3 is in the cart
		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 3");
		Assert.assertTrue(match);
		
	}


}
