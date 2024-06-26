package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.qa.opencart.logger.Log;

public class OptionsManager {
	private Properties prop;
	
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	public OptionsManager(Properties prop) {
		this.prop=prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless").trim())) {
//			System.out.println("running chrome in headless mode");
			Log.info("running chrome in headless mode");
			co.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
//			System.out.println("running chrome in incognito mode");
			Log.info("running chrome in incognito mode");
			co.addArguments("--incognito");
		}
		return co;
		
	}
	
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("running firefox in headless mode");
			Log.info("running firefox in headless mode");
			fo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			System.out.println("running firefox in incognito mode");
			Log.info("running firefox in incognito mode");
			fo.addArguments("--incognito");
		}
		return fo;
		
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("running edge in headless mode");
			Log.info("running edge in headless mode");
			eo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			System.out.println("running chrome in edge mode");
			Log.info("running chrome in edge mode");
			eo.addArguments("--inprivate");
		}
		return eo;
		
	}

}
