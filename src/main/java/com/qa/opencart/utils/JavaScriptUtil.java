package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

private WebDriver driver;
private JavascriptExecutor js;

	public JavaScriptUtil(WebDriver driver) {
		this.driver=driver;
		this.js=(JavascriptExecutor)driver;
	}
	
	public String getTitleByJS() {
		//JavascriptExecutor js = (JavascriptExecutor)driver; //Interface to interface casting
		return js.executeScript("return document.title;").toString();
	}
	
	public String getUrlByJS() {
		return js.executeScript("return document.URL;").toString();
	}
	
	/**
	 * JS method to forward from current page
	 */
	public void goForwardByJS() {
		js.executeScript("return history.go(1);");
	}
	/**
	 * JS method to backward from current page
	 */
	public void goBackwardByJS() {
		js.executeScript("return history.go(-1);");
	}
	/**
	 * JS method to refresh from current page
	 */
	public void refreshBrowserByJS() {
		js.executeScript("return history.go(0);");
	}
	
	/**
	 * Generating custom alert. Pass string message for alert
	 */
	public void getAlertMessageByJS(String message) {
		js.executeScript("return alert('"+message+"');");
	}
	/**
	 * Generating custom confirm pop up. Pass string message for confirmation
	 */
	public void generateConfirmPopUp(String message) {
		js.executeScript("confirm('"+message+"');");
	}
	/**
	 * Method to get the complete inner text of page
	 */
	public String getPageInnertext() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}
	
	/**
	 * Method to scroll from top to bottom of page
	 */
	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
	}
	
	/**
	 * Method to scroll from bottom to top of page
	 */
	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight,0);");
	}
	/**
	 * Method to scroll from top to middle of page
	 */
	public void scrollPageDownMiddlePage() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight/2);");
	}
	/**
	 * Method to scroll from top to some height in pixel
	 */
	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0,'"+height+"');");
	}
	
	/**
	 * Method to scroll to find element on page
	 */
	public void scrollIntoViewElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);",element);
	}
	
	/**
	 * Chrome method to zoom, using "document.body.style.zoom = '400%'"
	 */
	public void zoomChromeEdge(String zoomPercentage) {
		String zoom="document.body.style.zoom = '"+zoomPercentage+"%'";
		js.executeScript(zoom);
	}
	/**
	 * FireFox method to zoom, using "document.body.style.MozTransform = 'scale(0.5)'"
	 */
	public void zoomFirefox(String zoomPercentage) {
		String zoom="document.body.style.MozTransform = 'scale("+zoomPercentage+")'";
		js.executeScript(zoom);
	}
	
	//arguments[0].style.border='4px solid red'"
	/**
	 * method to draw border around element in red color, 
	 */
	public void drawBorder(WebElement element) {
		String border="arguments[0].style.border='4px solid red'";
		js.executeScript(border, element);
	}
	/**
	 * method to flash element in color, 
	 */
	public void flashElement(WebElement element) {
		String bgColor = element.getCssValue("backgroundColor");//red
		for(int i=0;i<3;i++) {
			changeColor("rgb(48,213,200)", element);//turquoise
			changeColor(bgColor, element);//original
		}
	}
	
	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor='"+color+"'", element);
		try {
			Thread.sleep(200);
		}catch (InterruptedException e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Click element using JS. Indirect way of clicking use it cautiously
	 * @param WebElement 
	 */
	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}
	
	/**
	 * sendkeys using JS. To be avoided
	 * @param Id and Value
	 */
	public void sendElementByJS(String Id, String value) {
		js.executeScript("document.getElementById('"+Id+"').value ='"+value+"';");
	}
	
}
