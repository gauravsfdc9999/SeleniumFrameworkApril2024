package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductDetailsPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultsPage;
import com.qa.opencart.pages.ShoppingCart;

import io.qameta.allure.Step;

public class BaseTest {
	private WebDriver driver;
	private DriverFactory df;
	
	
	protected Properties prop;
	protected SoftAssert softAssert;
	
	protected LoginPage loginPage;
	protected AccountsPage accountsPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductDetailsPage productDetailsPage;
	protected ShoppingCart shoppingCart;
	protected RegistrationPage registrationPage;
	
	@Step("SetUp: Launching {0} browser & initializing the properties")
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(String browserName) {
		df= new DriverFactory();

		prop = df.initProp();
		if(browserName!=null) {
			prop.setProperty("browser", browserName);
		}
		driver=df.initDriver(prop);
		//driver = df.initDriver(initProp(browserName));
		softAssert = new SoftAssert();
		loginPage = new LoginPage(driver);
	}
	
	@Step("TearDown: closing the browser")
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
	/*@BeforeSuite
	public Properties initProp() {
		prop = new Properties();

		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop;
	}
	*/

}
