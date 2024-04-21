package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchResultsPageTest extends BaseTest{
	
	@BeforeClass
	public void searchResultSetUp() {
		accountsPage= loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	//paramterization - using Object[][]
	@DataProvider
	public Object[][] getProductCountData() {
		return new Object[][] {
			{"macbook",3},
			{"imac",1},
			{"samsung",2}
		};
	}
	
	
	@Test(dataProvider = "getProductCountData")
	public void searchResultsTest(String searchKey, int productCount) {
		//searchResultsPage=accountsPage.doSearch("macbook");
		searchResultsPage=accountsPage.doSearch(searchKey);
		Assert.assertEquals(searchResultsPage.getSearchProductsCount(), productCount);
	}

}
