package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.ApplicationConstants;
import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class AccountsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//Page class gives the behaviour of the page and no testng code is written here
	//1.Private By locator
	//2.public page class contructor..
	//3.Public page action methods
	
	private final By logoutLink = By.linkText("Logout");
	private final By myAccountLink = By.linkText("My Account");
	private final By headers = By.cssSelector("div#content h2");
	private final By searchField = By.name("search");
	private final By searchIcon = By.cssSelector("div#search button");
	
	public AccountsPage(WebDriver driver) {
		this.driver=driver;
		this.eleUtil = new ElementUtil(driver);
	}
	
	public String getAccountPageTitle() {
		String pageTitle = eleUtil.waitForTitleIs(ApplicationConstants.ACCOUNT_PAGE_TITLE, TimeUtil.DEFAULT_LONG_TIME);
		System.out.println("Account page title "+pageTitle);
		Log.info("Account page title "+pageTitle);
		return pageTitle;
	}
	
	public String getAccountPageUrl() {
		String pageUrl = eleUtil.waitForUrlContains(ApplicationConstants.ACCOUNT_PAGE_URL_FRACTION, TimeUtil.DEFAULT_MEDIUM_TIME);
		System.out.println("Account page title "+pageUrl);
		Log.info("Account page title "+pageUrl);
		return pageUrl;
	}
	
	public boolean isLogoutLinkExists() {
		return eleUtil.waitForElementVisible(logoutLink, TimeUtil.DEFAULT_LONG_TIME).isDisplayed();
	}
	
	public boolean isAccountLinkExists() {
		return eleUtil.waitForElementVisible(myAccountLink, TimeUtil.DEFAULT_LONG_TIME).isDisplayed();
	}
	
	public List<String> getAccountPagegHeadersList() {
		List<WebElement> headersEleList = eleUtil.getElements(headers);
		List<String> headersList = new ArrayList<>();
		for(WebElement e: headersEleList) {
			String header = e.getText();
			headersList.add(header);
		}
		if(headersList.size()>0) {
			return headersList;
		}
		return null;
	}
	
	public SearchResultsPage doSearch(String productName) {
		System.out.println("searching for product: "+productName);
		Log.info("searching for product: "+productName);
		eleUtil.doSendKeys(searchField, productName, TimeUtil.DEFAULT_MEDIUM_TIME);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}

}
