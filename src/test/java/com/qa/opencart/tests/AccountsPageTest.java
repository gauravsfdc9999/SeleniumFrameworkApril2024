package com.qa.opencart.tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.ApplicationConstants;


public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accountSetUp() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accountsPageTitleTest() {
		String actTitle = accountsPage.getAccountPageTitle();
		Assert.assertEquals(actTitle, ApplicationConstants.ACCOUNT_PAGE_TITLE);
	}
	
	@Test
	public void accountsPageUrlTest() {
		String actUrl = accountsPage.getAccountPageUrl();
		Assert.assertTrue(actUrl.contains(ApplicationConstants.ACCOUNT_PAGE_URL_FRACTION));
	}
	
	@Test
	public void isLogoutLinkExistsTest() {
		boolean logoutLink = accountsPage.isLogoutLinkExists();
		Assert.assertTrue(logoutLink);
	}
	
	@Test
	public void isAccountLinkExistsTest() {
		boolean accountLink = accountsPage.isAccountLinkExists();
		Assert.assertTrue(accountLink);
	}
	
	@Test
	public void headersTest() {
		List<String> actHeadersList = accountsPage.getAccountPagegHeadersList();
		System.out.println(actHeadersList);
		Assert.assertTrue(actHeadersList.contains(ApplicationConstants.ACCOUNT_PAGE_HEADER_FRACTION));
	}
	
	@Test (priority = 100)
	public void searchProductTest() {
		accountsPage.doSearch("mackbook");
	}

}
