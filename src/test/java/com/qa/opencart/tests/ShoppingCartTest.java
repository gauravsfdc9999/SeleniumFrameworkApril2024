package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ShoppingCartTest extends BaseTest {
	
	@BeforeClass
	public void ShoppingCartSetUp() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void shoppingCartHeaderTest() {
		searchResultsPage= accountsPage.doSearch("macbook");
		productDetailsPage = searchResultsPage.selectProduct("MacBook Pro");
		shoppingCart= productDetailsPage.addShoppingCart(1);
		Assert.assertTrue(shoppingCart.getShoppingCartHeader().contains("Shopping Cart"));
	}

}
