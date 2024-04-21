package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.ElementUtil;

public class ProductDetailsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private Map<String,String> productMetaMap = new HashMap<>();
	
	//Page class gives the behaviour of the page and no testng code is written here
	//1.Private By locator
	//2.public page class contructor..
	//3.Public page action methods
	

	private final By productHeader = By.tagName("h1");
	private final By productImages = By.cssSelector("ul.thumbnails img");
	//div#content>div>div ul.list-unstyled>li or xpath - (//div[@id = 'content']//ul[@class='list-unstyled'])[1]/li
	private final By productMetaData = By.xpath("(//div[@id = 'content']//ul[@class='list-unstyled'])[1]/li");
	private final By productPriceData = By.xpath("(//div[@id = 'content']//ul[@class='list-unstyled'])[2]/li");
	private final By productQuantity = By.cssSelector("input#input-quantity");
	private final By addToCart = By.cssSelector("button#button-cart");
	private final By successMessages = By.cssSelector("div.alert.alert-success.alert-dismissible");
	private final By shoppingcart = By.xpath("//div[@class='alert alert-success alert-dismissible']/a[text()='shopping cart']");
	
	public ProductDetailsPage(WebDriver driver) {
		this.driver=driver;
		this.eleUtil = new ElementUtil(driver);
	}
	
	public String getProductHeader() {
//		String header = eleUtil.doGetElementText(productHeader);
		String header = eleUtil.waitForElementPresence(productHeader, 5).getText();
		System.out.println(header);
		Log.info("Header value: "+header);
		return header;
	}
	
	public int totalImagesCount() {
		int totalImages= eleUtil.waitForElementsVisible(productImages, 10).size();
		System.out.println("Images count for "+getProductHeader()+" is: "+totalImages);
		Log.info("Images count for "+getProductHeader()+" is: "+totalImages);
		return totalImages;
	}
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock
	private void getproductMetaData() {
		List<WebElement> metaList = eleUtil.waitForElementsPresence(productMetaData, 5);
		
		for(WebElement ele : metaList) {
			String text = ele.getText();
			String metaKey = text.split(":")[0].trim();
			String metaValue = text.split(":")[1].trim();
			productMetaMap.put(metaKey, metaValue);
		}
	}
	
//	$2,000.00
//	Ex Tax: $2,000.00

	private void getproductPriceData() {
		List<WebElement> priceList = eleUtil.waitForElementsPresence(productPriceData, 5);
		String price = priceList.get(0).getText();
		String extaxPrice= priceList.get(1).getText().split(":")[1].trim();
		productMetaMap.put("ProductPrice", price);
		productMetaMap.put("extaxPrice", extaxPrice);
		
	}
	
	public Map<String,String> getProductDetailsMap() {
		productMetaMap.put("headers", getProductHeader());
		productMetaMap.put("images", String.valueOf(totalImagesCount()));
		getproductMetaData();
		getproductPriceData();
		return productMetaMap;
	}
	
	public void addProductQuantity(int quantity) {
		eleUtil.doSendKeys(productQuantity, String.valueOf(quantity), 3);
	}
	
	public String addProductToCart(int quantity) {
		eleUtil.doSendKeys(productQuantity, String.valueOf(quantity), 3);
		eleUtil.doClick(addToCart, 5);
		String successText = eleUtil.waitForElementVisible(successMessages, 10).getText();
		return successText;
	}
	
	public ShoppingCart addShoppingCart(int quantity) {
		String successText = addProductToCart(quantity);
//		if(successText.contains("success")) {
//			eleUtil.clickWhenReady(shoppingcart, 5);
//			return new ShoppingCart(driver);
//		}
//		
//		return null;
		eleUtil.clickWhenReady(shoppingcart, 5);
		return new ShoppingCart(driver);
	}
}
