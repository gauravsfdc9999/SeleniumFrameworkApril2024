package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//Page class gives the behaviour of the page and no testng code is written here
	//1.Private By locator
	//2.public page class contructor..
	//3.Public page action methods
	

	private final By searchProducts = By.cssSelector("div.product-thumb");
	
	public SearchResultsPage(WebDriver driver) {
		this.driver=driver;
		this.eleUtil = new ElementUtil(driver);
	}
	
	public int getSearchProductsCount() {
		return eleUtil.waitForElementsVisible(searchProducts, 10).size();
	}
	
	public ProductDetailsPage selectProduct(String productName) {
		System.out.println("selected product: "+productName);
		Log.info("selected product: "+productName);
		eleUtil.waitForElementVisible(By.linkText(productName), 10).click();
		return new ProductDetailsPage(driver);
	}
}
