package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.ApplicationConstants;
import com.qa.opencart.errors.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//@Listeners(ExtentReportListener.class)
//to generate allure report
@Epic("Epic: 100: Desing open cart login page")
@Story("UserStory 101 : Design login page features for open cart application")
@Feature("Feature 201: Adding login feature")
public class LoginPageTest extends BaseTest {
	@Description("Checking login page title....")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String actTitle  =loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, ApplicationConstants.LOGIN_PAGE_TITLE,AppError.TITLE_NOT_FOUND);
	}
	
	@Description("Checking login page url....")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageUrlTest() {
		String actUrl  =loginPage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains(ApplicationConstants.LOGIN_PAGE_URL_FRACTION),AppError.URL_NOT_FOUND);
	}
	
	@Description("Checking forgot page link on login page....")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotPasswordLinkExistTest() {
		boolean passwordFlag  =loginPage.isForgotPasswordLinkExists();
		Assert.assertTrue(passwordFlag);
	}
	
	
	@Description("Checking user able to login....")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 1)
	public void doLoginTest() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		String actTitle = accountsPage.getAccountPageTitle();
		Assert.assertEquals(actTitle, ApplicationConstants.ACCOUNT_PAGE_TITLE,AppError.TITLE_NOT_FOUND);
	}

}
