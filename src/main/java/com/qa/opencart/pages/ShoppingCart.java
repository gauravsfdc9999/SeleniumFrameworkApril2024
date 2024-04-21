package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

public class ShoppingCart {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	//Page class gives the behaviour of the page and no testng code is written here
	//1.Private By locator
	//2.public page class contructor..
	//3.Public page action methods
	

	private final By shoppingCartHeader = By.tagName("h1");
	private final By checkOutLink = By.linkText("Checkout");
	private final By shoppingCartItems = By.xpath("//div[@id='content']//div[@class='table-responsive']/table/tbody//td/a");
	

	
	public ShoppingCart(WebDriver driver) {
		this.driver=driver;
		this.eleUtil = new ElementUtil(driver);
	}
	
	public String getShoppingCartHeader() {
		String header= eleUtil.doGetElementText(shoppingCartHeader);
		return header;
	}
	
	public void shoppingCartProductName() {
//		eleUtil
	}

}
