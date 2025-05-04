		package rahulshettyacademy.Tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;


public class SubmitOrderTest extends BaseTest {
	
	public String productName;
		
	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder (HashMap<String,String> input) throws IOException
	{
		
		//open url and login
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		
		//collecting all the product webelements into a list
		productCatalogue.getProductList();
		
		//search for ZARA Coat3 product in the list and add it to the cart
		productCatalogue.addProductToCart(input.get("productName"));
		
		//click on cart button using child class
		CartPage cartPage = productCatalogue.goToCartPage();
		
		//get a list of products added to cart and check that ZARA COAT 3 is in the cart
		Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);
		
		//click on check out button
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		
		//select country using Actions class
		checkoutPage.selectCountry("india");
		
		//click on place order
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		
		//get the order placed successfully text
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
	}
	
	@Test(dependsOnMethods={"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("ShikhaD@gmail.com", "Idiot_123");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.verifyOrderDisplay("ZARA COAT 3"));
	}


	@DataProvider
	public Object [][] getData() throws IOException{
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
}

/*//Method 1 of sending data -array
@DataProvider
public Object [][] getData(){
	 return new Object[][] {{"ShikhaD@gmail.com","Idiot_123","ZARA COAT 3"},{"PurvaG@gmail.com","Idiot_321","ADIDAS ORIGINAL"}};
}

//Method 2 of sending data -hash map
@DataProvider
public Object [][] getData(){
HashMap<String,String> map = new HashMap<String,String>();
map.put("email","ShikhaD@gmail.com");
map.put("password","Idiot_123");
map.put("productName", "ZARA COAT 3");

map1.put("email","PurvaG@gmail.com");
map1.put("password","Idiot_321");
map1.put("productName", "ADIDAS ORIGINAL");

return new Object[][] {{map},{map1}};
}
*/
