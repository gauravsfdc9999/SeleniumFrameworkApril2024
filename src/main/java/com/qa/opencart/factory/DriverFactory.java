package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameWorkException;
import com.qa.opencart.logger.Log;

public class DriverFactory {
	WebDriver driver;
	protected Properties prop;
	protected OptionsManager optionsManager;
	
	//implementing thread local driver for parallel execution and avoid deadlock
	protected static ThreadLocal<WebDriver> tlDriver= new ThreadLocal<WebDriver>();
	
	public static String highLight;
	
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		System.out.println("Browser name: "+browserName);
		Log.info("Browser name: "+browserName);
		
		highLight = prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);
		
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
//			driver=new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
//			driver=new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
//			driver=new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
//			driver=new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;

		default:
			System.out.println("plz pass right browser....."+browserName);
			Log.error("plz pass right browser....."+browserName);
			throw new BrowserException("No browser found.."+browserName);
			
		}
		
//		driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
		//thread local driver
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
//		driver.manage().timeouts()
		String applicationUrl = prop.getProperty("applicationUrl");
//		driver.get(applicationUrl);
		getDriver().get(applicationUrl);
		
		
//		return driver;
		return getDriver();
	}
	
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	public Properties initProp() {
		//envName=qa,stage,prod,uat,dev
		//mvn clean install -Denv="qa"
		
		FileInputStream ip = null;
		prop = new Properties();
		String envName = System.getProperty("env");
		System.out.println("Runnig test on =============:"+envName);
		Log.info("Runnig test on =============:"+envName);
		
		try {
		if(envName==null) {
			System.out.println("No environment is given.. hence running on QA env....");
			ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
		}
		else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
				break;
			case "uat":
				ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
				break;
			case "prod":
				ip = new FileInputStream("./src/test/resources/config/config.properties");
				break;

			default:
				System.out.println("please pass the right environment name...."+envName);
				Log.error("please pass the right environment name...."+envName);
				throw new FrameWorkException(AppError.ENV_NAME_NOT_FOUND +": "+envName);
			}
		}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
//		prop = new Properties();
		try {
//			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(ip);
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * Take screen shot
	 */
	
	public static String getScreenshot(String methodName) {
		File srcFile=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+ "/screenshot/"+methodName+"_"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		}catch (IOException e) {
			Log.error("File not found", e);
		}
		return path;
	}

}
