package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;


public class ElementUtil {

	private WebDriver driver;
	private static final String DEFAULT_ELEMENT_TIME_OUT_MESSAGE = "=====Element not found in timeout....====";
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver=driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	//common null check method
	private void nullBlankCheck(String value) {
		if(value==null || value.length()==0) {
			throw new MyElementException(value +" --- value cannot be null");
		}
	}
	
	private void checkHighLight(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highLight)) {
			jsUtil.flashElement(element);
		}
	}
	
	@Step("Getting web element using locator {0}...")
	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			element = driver.findElement(locator);
//				if(Boolean.parseBoolean(DriverFactory.highLight)) {
//					jsUtil.flashElement(element);
//				}
			checkHighLight(element);
			}
		catch (NoSuchElementException e) {
			System.out.println("Element is not present on the page");
			e.printStackTrace();
			return null;
		}
		return element;
		
	}
	
	@Step("Entering values : {1} in element: {0}")
	public void doSendKeys(By locator,String value) {
		nullBlankCheck(value);
		getElement(locator).sendKeys(value);
	}
	
	//8th approach locators are string
	public By getBy(String locatortype, String locatorValue) {
		By locator=null;
		switch (locatortype.toLowerCase().trim()) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		case "css":
			locator = By.cssSelector(locatorValue);
			break;
		case "linktext":
			locator = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			locator = By.partialLinkText(locatorValue);
			break;
		case "tagname":
			locator = By.tagName(locatorValue);
			break;
		default:
			break;
		}
		return locator;
	}
	
	//Overload
	public WebElement getElement(String locatorType, String locatorValue) {
		return driver.findElement(getBy(locatorType, locatorValue));
	}
	
	//overload
	public void doSendKeys(String locatorType,String locatorValue, String value) {
		getElement(getBy(locatorType, locatorValue)).sendKeys(value);
	}
	
	//with vissibility 
	public void doSendKeys(By locator,String value, int timeOut) {
		nullBlankCheck(value);
		//getElement(locator).sendKeys(value);
		getElement(locator).clear();
		waitForElementVisible(locator, timeOut).sendKeys(value);
	}
	
	@Step("clicking on element using locator :{0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	//overload
	public void doClick(By locator,int timeOut) {
		//getElement(locator).click();
		waitForElementVisible(locator, timeOut).click();
	}
	
	public String doGetElementText(By locator) {
		return getElement(locator).getText();
	}
	
	public String doElementGetAttribute(By locator, String attributeValue) {
		return getElement(locator).getAttribute(attributeValue);
	}
	
	//Elements related utility
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}
	
	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> textList = new ArrayList<String>();//pc=0
		for(WebElement e: eleList) {
			String text = e.getText();
			if(text.length()!=0) {
				textList.add(text);
			}
			
		}
		return textList;
	}
	
	public ArrayList<String> getElementAttributeList(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		ArrayList<String> eleAttList = new ArrayList<String>();
		for(WebElement e: eleList) {
			String attrValue = e.getAttribute(attrName);
			if(attrValue.length()!=0) {
				eleAttList.add(attrValue);
			}
			
		}
		return eleAttList;
	}
	
	/**
	 * New method for Element is displayed
	 */
	
	@Step("checking for element ...{0} is displayed or not")
	public boolean isElementDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	//unique element is present
	public boolean isElementExists(By locator) {
		if(getElements(locator).size()==1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean multipleElementsExist(By locator) {
		if(getElements(locator).size()>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Overloading
	public boolean multipleElementsExist(By locator, int elementCount) {
		if(getElements(locator).size()==elementCount) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//****************Select based drop down utils******************
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}
	
	public void doSelectByVisibleText(By locator, String visibleText) {
//		if(visibleText!=null || visibleText.length()!=0) {
//			Select select = new Select(getElement(locator));
//			select.selectByVisibleText(visibleText);
//		}
//		else {
//			throw new MyElementException("Text cannot be null");
//		}
		
		nullBlankCheck(visibleText);
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}
	
	public void doSelectByValue(By locator, String attrValue) {
//		if(attrValue==null || attrValue.length()==0) {
//			throw new MyElementException("Attribute value cannot be null");
//		}
		nullBlankCheck(attrValue);
		Select select = new Select(getElement(locator));
		select.selectByValue(attrValue);
	}
	
	//****Select options*****
	
	public List<String> getDropDownOptionsTextList(By locator) {
		List<WebElement> optionsList = getDropDownOptionsList(locator);
		List<String> optionTextList = new ArrayList<String>();//pc=0
		for(WebElement e:optionsList ) {
			String optionText = e.getText();
			optionTextList.add(optionText);
		}
		
		return optionTextList;
		
	}
	public List<WebElement> getDropDownOptionsList(By locator) {
		//WebElement element = driver.findElement(locator);
		//WebElement element = getElement(locator);
		Select select = new Select(getElement(locator));
		return select.getOptions();
	}
	public int getDropDownValuesCount(By locator) {
//		WebElement element = driver.findElement(locator);
//		Select select = new Select(element);
		
		return getDropDownOptionsList(locator).size();
	}
	
	public void doSelectDropDownValue(By locator, String textValue) {
//		WebElement element = driver.findElement(locator);
//		Select select = new Select(element);
//		List<WebElement> optionList = select.getOptions();
		nullBlankCheck(textValue);
		List<WebElement> optionList = getDropDownOptionsList(locator);
		
		for(WebElement e: optionList) {
			String text = e.getText();
			System.out.println(text);
			if(text.equals(textValue)) {
				e.click();
				break;
			}
		}
	}
	
	public void printSelectDropDownValue(By locator) {
		List<WebElement> optionList = getDropDownOptionsList(locator);
		for(WebElement e: optionList) {
			String text = e.getText();
			System.out.println(text);
		}
	}
	
	//selecting drop down value without using select class
	public void doSelectValueFromDropDown(By locator, String textValue) {
		List<WebElement> optionsList = getElements(locator);
		System.out.println("Elements in list: "+optionsList.size());
		nullBlankCheck(textValue);
		for(WebElement e: optionsList) {
			String text = e.getText();
			if(text.equals(textValue)) {
				e.click();
				break;
			}
			
		}
	}
		
		public void doSelectValueFromDropDownIndex(By locator, int index) {
			List<WebElement> optionsList = getElements(locator);
			int counter=0;
			for(WebElement e: optionsList) {
				String text = e.getText();
				if(text!=null) {
					counter++;
				}
				if(counter==index) {
					e.click();
					break;
				}
				
			}
		}
		
		
	//***Generic search utility
		public void doSearch(By searchLocator,By searchSuggestion, String searchKey, String value) throws InterruptedException {
			//driver.findElement(searchLocator).sendKeys(searchKey);
			doSendKeys(searchLocator, searchKey);
			Thread.sleep(3000);
//			List<WebElement> suggestionList = driver.findElements(searchSuggestion);
			List<WebElement> suggestionList = getElements(searchSuggestion);
			for(WebElement e: suggestionList) {
				System.out.println(e.getText());
				String text = e.getText();
				if(text.contains(value)) {
					e.click();
					break;
				}
			}
		}
		
	/**
	 * Methods to handle submenu items using action class - Action utils
	 * To handle Menu and Sub menu of level 2
	 */
		
		public void handleMenuSubMenuLevel2(By parentLocator, By SubMenuLocator) throws InterruptedException {
			Actions act = new Actions(driver);
			act.moveToElement(getElement(parentLocator)).build().perform();
			Thread.sleep(5000);
			getElement(SubMenuLocator).click();
		}
		
		public void handleMenuSubMenuLevel4(By shopByCategory, By level1Menu, By level2Menu, By level3Menu) throws InterruptedException {
//			getElement(shopByCategory).click();
			doClick(shopByCategory);
			Thread.sleep(2000);
			
			Actions act = new Actions(driver);
			act.moveToElement(getElement(level1Menu)).build().perform();
			Thread.sleep(5000);
			act.moveToElement(getElement(level2Menu)).build().perform();
			Thread.sleep(5000);
			//getElement(level3Menu).click();
			doClick(level3Menu);
		}
		
	/**
	 * Click and Send keys using actions class
	 */
		public void doActionsClick(By locator) {
			Actions act = new Actions(driver);
			act.click(getElement(locator)).perform();
		}
		
		public void doActionsSendKeys(By locator, String value) {
			Actions act = new Actions(driver);
			act.sendKeys(getElement(locator), value).perform();
		}
		
	//****Wait utils******//
		/**
		 * An expectation for checking an element is visible and enabled such that you can click it.
		 * @param locator
		 * @param timeOut
		 */
		public void clickWhenReady(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}
		
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page. 
		 * This does not necessarily mean that the element is visible.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public WebElement waitForElementPresence(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		/**
		 * An expectation for checking that an element is present on the DOM of a page and visible.Visibility means that 
		 * the element is not only displayed but also has a height and width that isgreater than 0.
		 * @param locator
		 * @param timeOut
		 * default interval time =500ms
		 * @return
		 */
		@Step("Waiting for element using locator {0} within timeout of {1} ")
		public WebElement waitForElementVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut),Duration.ofSeconds(intervalTime));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		/**
		 * An expectation for checking that there is at least one element present on a web page.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		
		/**
		 * Wait for element using Fluent Wait
		 */
		public List<WebElement> waitForElementsPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
										.withTimeout(Duration.ofSeconds(timeOut))
										.pollingEvery(Duration.ofSeconds(pollingTime))
										.ignoring(NoSuchElementException.class)
										.withMessage(DEFAULT_ELEMENT_TIME_OUT_MESSAGE);
			
			//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		
		
		/**
		 * An expectation for checking that all elements present on the web page that match the locatorare visible. 
		 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		/**
		 * Explicitly wait for Title value contains
		 * @param titleFraction
		 * @param timeOut in seconds
		 */
		public String waitForTitleContains(String titleFraction, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
				if(wait.until(ExpectedConditions.titleContains(titleFraction))) {
					return driver.getTitle();
				}
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("title not found within: "+timeOut);
			}	
			return null;
		}
		
		/**
		 * Explicitly wait for Title value equals
		 * @param title
		 * @param timeOut in seconds
		 */
		@Step("wating for expected title")
		public String waitForTitleIs(String title, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
				if(wait.until(ExpectedConditions.titleIs(title))) {
					return driver.getTitle();
				}
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("title not found within: "+timeOut);
			}	
			return null;
		}
		/**
		 * Explicitly wait for URL value contains
		 * @param urlFraction
		 * @param timeOut in seconds
		 */
		@Step("wating for expected url")
		public String waitForUrlContains(String urlFraction, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
				if(wait.until(ExpectedConditions.urlContains(urlFraction))) {
					return driver.getCurrentUrl();
				}
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("Url not found within: "+timeOut);
			}	
			return null;
		}
		/**
		 * Explicitly wait for URL value ToBe
		 * @param Url
		 * @param timeOut in seconds
		 */
		
		public String waitForUrlIs(String url, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
				if(wait.until(ExpectedConditions.urlToBe(url))) {
					return driver.getCurrentUrl();
				}
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("Url not found within: "+timeOut);
			}	
			return null;
		}
		
		/**
		 * Alert utility
		 * Wait for alert using explicitly wait and switching the driver to alert window
		 * @param int timeOut
		 */
		public Alert waitForJSAlert(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut)); //wait for alert and switch to alert
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
		public String getAlertText(int timeOut) {
			return waitForJSAlert(timeOut).getText();
		}
		
		public void acceptAlert(int timeOut) {
			waitForJSAlert(timeOut).accept();;
		}
		public void dismissAlert(int timeOut) {
			waitForJSAlert(timeOut).dismiss();
		}
		public void alertSendKeys(int timeOut, String value) {
			waitForJSAlert(timeOut).sendKeys(value);
		}
		
		/**
		 * Wait for windowhandles
		 */
		public boolean waitForWindowHandles(int totalNumberOfWindowsToBe, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.numberOfWindowsToBe(totalNumberOfWindowsToBe));
		}
		/**
		 * An expectation for checking whether the given frame is available to switch to. 
		 * If the frame is available it switches the given driver to the specified frame.
		 */
		public void waitForFrameAndSwitchToIt(By frameLocator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator)); //returns driver
		}
		public void waitForFrameAndSwitchToIt(int frameIndex, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex)); //returns driver
		}
		public void waitForFrameAndSwitchToIt(WebElement frameElement, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement)); //returns driver
		}
		
		/**
		 * Custom wait based on number of attempts
		 */
		public WebElement retryingElement(By locator, int numberOfAttempts) {
			WebElement element= null;
			int attempts = 0;
			
			while(attempts < numberOfAttempts) {
				try {
					element = getElement(locator);
					System.out.println("element found in... "+locator+" in attempts "+attempts);
					break;
				}catch(NoSuchElementException ex) {
					System.out.println("Element not found..."+" in attempts "+attempts);
					TimeUtil.defaultTime();
				}
				attempts++;
			}
			if(element==null) {
				System.out.println("element not found.. tried for "+numberOfAttempts+" times with default interval time 500 milliseconds");
				throw new MyElementException("element not found.. tried with default interval time 500 milliseconds");
			}
			return element;	
		}
		
		public WebElement retryingElement(By locator, int numberOfAttempts, int intervalTime) {
			WebElement element= null;
			int attempts = 0;
			
			while(attempts < numberOfAttempts) {
				try {
					element = getElement(locator);
					System.out.println("element found in... "+locator+" in attempts "+attempts);
					break;
				}catch(NoSuchElementException ex) {
					System.out.println("Element not found..."+" in attempts "+attempts);
					TimeUtil.applyWait(intervalTime);
				}
				attempts++;
			}
			if(element==null) {
				System.out.println("element not found.. tried for "+numberOfAttempts+" times with interval time " + intervalTime+" seconds");
				throw new MyElementException("element not found.. tried with default given interval time");
			}
			return element;	
		}



}
