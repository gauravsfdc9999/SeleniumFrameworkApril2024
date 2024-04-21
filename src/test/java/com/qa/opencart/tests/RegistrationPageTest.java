package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.ApplicationConstants;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

public class RegistrationPageTest extends BaseTest{
	
	@BeforeClass
	public void regSetup() {
		registrationPage = loginPage.navigateToRegistrationPage();
	}
	
	@DataProvider
	public Object[][] getUserRegTestData() {
		return new Object[][] {
			{"gaurav","gupta","8798765674","gg@123","yes"}
		};
	}
	
	@DataProvider
	public Object[][] getUserRegTestDataFromExcel() {
		return ExcelUtil.getTestData(ApplicationConstants.REGISTER_SHEET_NAME);
	}
	
	@Test(dataProvider = "getUserRegTestDataFromExcel")
	public void userRegistrationTest(String firstName, String lastName, String telephone, String password, String subscribe) {
//		boolean regFlag = registrationPage.userRegistration("Gaurav", "Gupta", "gg@gmail.com", "8798765674", "gg@123", "yes");
		//with random email ID
		boolean regFlag = registrationPage.userRegistration(firstName, lastName, StringUtils.getRandomEmailId(), telephone, password, subscribe);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(regFlag);
	}

}
