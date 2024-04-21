package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.ApplicationConstants;
import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.ElementUtil;

public class RegistrationPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private final By firstName = By.id("input-firstname");
	private final By lastName = By.id("input-lastname");
	private final By email = By.id("input-email");
	private final By telephone = By.id("input-telephone");
	private final By password = By.id("input-password");
	private final By confirmPassword = By.id("input-confirm");
	private final By subscribeYes = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value='1']");
	private final By subscribeNo = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value='0']");
	private final By agreeCheckBox = By.name("agree");
	private final By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");
	private final By successMsg = By.cssSelector("div#content h1");
	private final By logoutLink = By.linkText("Logout");
	private final By registerLink = By.linkText("Register");


	public RegistrationPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
	
	}
	
	public boolean userRegistration(String firstName, String lastName, String emailId, String telephone, String password,
			String subscribe) {
		
		eleUtil.waitForElementVisible(this.firstName, 10).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, emailId);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(this.subscribeYes);
		}
		else {
			eleUtil.doClick(this.subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueBtn);
		
		String regisSuccessMsg = eleUtil.waitForElementVisible(this.successMsg, 10).getText();
		System.out.println(regisSuccessMsg);
		Log.info("registration success message: "+regisSuccessMsg);
		
		if(regisSuccessMsg.equals(ApplicationConstants.USER_REG_SUCCESS_MESSAGE)) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			regLogout();
			regRegister();
			return true;
		}
		return false;
		
	}
	
	
	private void regLogout() {
		eleUtil.waitForElementVisible(logoutLink, 5).click();
	}
	
	private void regRegister() {
		eleUtil.waitForElementVisible(registerLink, 5).click();
	}

}
