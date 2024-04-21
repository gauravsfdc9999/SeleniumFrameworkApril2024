package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.ApplicationConstants;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;

public class ProductDetailsPageTest extends BaseTest {
	//AAA ---> test pattern Arrange, Act, and Assert.
	//TC -- only one hard assertion or can have multiple soft assertions

	@BeforeClass
	public void productDetailsPageSetUp() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	@DataProvider
	public Object[][] getProductSearchData() {
		return new Object[][] {
			{"macbook","MacBook Pro"},
			{"imac","iMac"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"samsung","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Test(dataProvider = "getProductSearchData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accountsPage.doSearch(searchKey);
		productDetailsPage = searchResultsPage.selectProduct(productName);
		String actHeader = productDetailsPage.getProductHeader();
		Assert.assertEquals(actHeader, productName);
	}
	
	@DataProvider
	public Object[][] getProductImagesData() {
		return new Object[][] {
			{"macbook","MacBook Pro",4},
			{"imac","iMac",3},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"samsung","Samsung Galaxy Tab 10.1",7}
		};
	}
	
	@DataProvider
	public Object[][] getProductImagesDataFromExcel() {
		return ExcelUtil.getTestData(ApplicationConstants.PRODUCTS_SHEET_NAME);
	}
	
	@DataProvider(name="somemeaningfullName")
	public Object[][] getProductImagesDataFromCSV() {
		return CSVUtil.csvData(ApplicationConstants.PRODUCTS_CSV_NAME);
	}
	
	@Test(dataProvider = "somemeaningfullName") //or we can use dataprovider method name
	public void productImagesCountTest(String searchKey,String productName, String imagesCount) {
		searchResultsPage = accountsPage.doSearch(searchKey);
		productDetailsPage = searchResultsPage.selectProduct(productName);
		int imagesCounts = productDetailsPage.totalImagesCount();
		Assert.assertEquals(imagesCounts,Integer.parseInt(imagesCount) );
	}
	
	@Test
	public void productPriceTest() {
		searchResultsPage = accountsPage.doSearch("imac");
		productDetailsPage = searchResultsPage.selectProduct("iMac");
		Map<String,String> productActDetailsMap = productDetailsPage.getProductDetailsMap();
		String productBrand = productActDetailsMap.get("Brand");
		//hard assertions
//		Assert.assertEquals(productBrand, "Apple");
//		Assert.assertEquals(productActDetailsMap.get("Product Code"), "Product 14");
//		Assert.assertEquals(productActDetailsMap.get("Availability"), "Out Of Stock");
//		Assert.assertEquals(productActDetailsMap.get("ProductPrice"), "$122.00");
//		Assert.assertEquals(productActDetailsMap.get("extaxPrice"), "$100.00");
		
		//soft assert - above is hard assert problem is if anyone hard assert failed then subsequent assert will fail
		//soft assert - 2 methods doAssert and assertAll we have create object of softAssert
		softAssert.assertEquals(productBrand, "Apple");
		softAssert.assertEquals(productActDetailsMap.get("Product Code"), "Product 14");
		softAssert.assertEquals(productActDetailsMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productActDetailsMap.get("ProductPrice"), "$122.00");
		softAssert.assertEquals(productActDetailsMap.get("extaxPrice"), "$100.00");
		softAssert.assertAll();		
	}
	
	@Test
	public void addToCartTest() {
		searchResultsPage = accountsPage.doSearch("imac");
		productDetailsPage = searchResultsPage.selectProduct("iMac");
		String actSuccessText = productDetailsPage.addProductToCart(1);
		Assert.assertTrue(actSuccessText.contains("Success"));
	}
}
