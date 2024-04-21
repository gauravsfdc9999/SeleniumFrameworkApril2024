package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.ApplicationConstants;
import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//Page class gives the behaviour of the page and no testng code is written here
	//1.Private By locator
	//2.public page class contructor..
	//3.Public page action methods
	
	private final By emailId = By.id("input-email");
	private final By passwordId = By.id("input-password");
	private final By loginButton = By.xpath("//input[@value='Login']");
	private final By forgottenPwd = By.linkText("Forgotten Password");
	private final By registrationLink = By.linkText("Register");
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		this.eleUtil = new ElementUtil(driver);
	}
	
	@Step("getting login page title...")
	public String getLoginPageTitle() {
//		String pageTitle = driver.getTitle();
		String pageTitle = eleUtil.waitForTitleIs(ApplicationConstants.LOGIN_PAGE_TITLE, 10);
		System.out.println("Login page title "+pageTitle);
		Log.info("Login page title "+pageTitle);
		return pageTitle;
	}
	
	@Step("wating for login url")
	public String getLoginPageUrl() {
//		String pageUrl = driver.getCurrentUrl();
		String pageUrl = eleUtil.waitForUrlContains(ApplicationConstants.LOGIN_PAGE_URL_FRACTION, 5);
		System.out.println("Login page title "+pageUrl);
		Log.info("Login page title "+pageUrl);
		return pageUrl;
	}
	
	@Step("checking for forgot link displayed...")
	public boolean isForgotPasswordLinkExists() {
//		return driver.findElement(forgottenPwd).isDisplayed();
		return eleUtil.isElementDisplayed(forgottenPwd);
	}
	
	@Step("logging with username-> {0} and password --> {1}")
	public AccountsPage doLogin(String userName, String password) {
		System.out.println("user cred: "+userName+" & password: "+password);
		Log.info("user cred: "+userName+" & password: "+password);
//		driver.findElement(emailId).sendKeys(userName);
//		driver.findElement(passwordId).sendKeys(password);
//		driver.findElement(loginButton).click();
		eleUtil.waitForElementVisible(emailId, 10).sendKeys(userName);
		eleUtil.doSendKeys(passwordId, password);
		eleUtil.doClick(loginButton);
//		return eleUtil.waitForTitleIs("My Account", 5);
		return new AccountsPage(driver);
	}
	
	@Step("navigating to the registration page....")
	public RegistrationPage navigateToRegistrationPage() {
		System.out.println("Navingating to Registration Page from Login Page --");
		eleUtil.waitForElementVisible(registrationLink, 10).click();
		return new RegistrationPage(driver);
		
	}

}
