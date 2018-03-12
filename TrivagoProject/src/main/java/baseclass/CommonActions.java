package baseclass;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.common.base.Function;
import com.sun.media.sound.InvalidDataException;

import baseclass.CommonActions.AssertionType;
import baseclass.CommonActions.LocatorType;
import objectrepository.Trivago_Result_PgObjs;

public class CommonActions extends BaseTest {

	/**
	 * Global variables
	 */
	//private boolean isWait = false;
	//private boolean isDisplayed = true;
	
	/**
	 * Enum Element Locator Types 
	 * @author praveenrn
	 *
	 */
	public enum LocatorType	{
		id, xpath, className, name, tagName, linkText, partialLinkText, cssSelector, empty
	}
	
		
	/**
	 * Enum Element Wait Types 
	 * @author praveenrn
	 *
	 */
	public enum WaitType {
		TimeWait, WaitForScriptTimeOut, WaitForElement, WaitForSpecificElement, WaitForPageLoad, WaitAndGetElement, WaitAndGetElements,
		WaitUntilElementIsClickable, WaitForScreenLoad, WaitForAjaxCallOut, WaitForWindowToClose
	}
	
	/**
	 * Enum Action Types
	 * @author praveenrn
	 *
	 */
	public enum ActionsType {
		Click, TypeText, NavigateToUrl, GetText, GetAttributeText, SwitchToDefaultContent, ListView, CloseWindow, SelectValueFromListByAttribute, ScriptSleep,
		SwitchToFrameByName, SwitchToFrameByIndex, SwitchToFrameByWebElement, AcceptAlert, DismissAlert, GetAlertText, SwitchToWindow, SwitchToDefaultWindow
	}
	
	/**
	 * Enum Assertion Types
	 * @author praveenrn
	 *
	 */
	public enum AssertionType {
		TextExists, TextExistsOnPageSource, TextContains, CheckBoxChecked, RadioButtonChecked, PageTitle, CurrentWindowURL, AlertPresent, AlertText,
		CheckDropdownByText, CheckDropdownContainsText, ControlExists
	}
	
	/**
	 * This method return By value
	 * @return By
	 * 
	 * @author praveenrn
	 */
	private By locateElement()
	{
		switch(locatortype){
		case id:
			return By.id(elementLocation);
		case xpath:
			return By.xpath(elementLocation);
		case className:
			return By.className(elementLocation);
		case name:
			return By.name(elementLocation);
		case tagName:
			return By.tagName(elementLocation);
		case linkText:
			return By.linkText(elementLocation);
		case partialLinkText:
			return By.partialLinkText(elementLocation);
		case cssSelector:
			return By.cssSelector(elementLocation);
		default:
			return null;
		}
	}
	
	/**
	 * Find one element using By class
	 * @param by
	 * @return {@link WebElement}
	 * 
	 * @author praveenrn
	 */
	private WebElement getElementBy(By by) {
		
		return driver.findElement(by);
	}
	
	/**
	 * Find all elements using By class 
	 * @param by
	 * @return
	 * 
	 * @author praveenrn
	 */
	private List<WebElement> getElementsBy(By by) {
		
		return driver.findElements(by);
	}
	
	/**
	 * Gets a single web element and assigns it to the public "element" variable for use.
	 * @param eLocatorType
	 * @param time
	 * @param waitBeforeGetElement
	 * 
	 * @author praveenrn
	 */
    private void getElement(LocatorType eLocatorType, int time, boolean waitBeforeGetElement) throws InvalidDataException {
    	
        String sLocatorType = eLocatorType.toString();
        if (sLocatorType == "" || sLocatorType == null || elementLocation == "" || elementLocation == null)
            throw new InvalidDataException("The UI Locator Type is Empty, Please Specify a UI Locator");
        else if (waitBeforeGetElement)
            waitAndGetElement(eLocatorType);
		else
			webelement = (RemoteWebElement)getElementBy(locateElement());
    }
	
    /**
     * Gets a list of web elements and assigns it to the public "elements" variable for use.
     * @param eLocatorType : Element locator type
     * @param waitBeforeGetElement : Wait before getting element
     * @throws Exception
     * 
     * @author praveenrn
     */
    private void getMultipleElements(LocatorType eLocatorType, boolean waitBeforeGetElement) throws Exception {
    	
        String sLocatorType = eLocatorType.toString();

            if (sLocatorType == "" || sLocatorType == null || elementLocation == "" || elementLocation == null)
                throw new InvalidDataException("The UI Locator Type is Empty, Please Specify a UI Locator");
            else if (waitBeforeGetElement)
                waitAndGetElements(eLocatorType);
            else
            	webelements = getElementsBy(locateElement());
    }
    
    /**
     * Selects the specific Web Element based on the locator and stores it in the "element" variable in BaseScript for future use.
     * @param eLocatorType : String that contains the locator type associated with locator
     * @param time : Wait time
     * @param waitBeforeGetElement : True/False
     * 
     * @author praveenrn
     * @throws InvalidDataException 
     */
	private void getUIElement(LocatorType eLocatorType, int time, boolean waitBeforeGetElement) throws InvalidDataException {
		
		getElement(eLocatorType, time, waitBeforeGetElement);
	}
	
	/**
	 * Selects the multiple web elements based on the locator and stores it in the "element" variable in BaseScript for future use.
	 * @param eLocatorType : String that contains the locator type associated with locator
	 * @param time : Element wait duration
	 * @param waitBeforeGetElement : Wait before get elements
	 * @throws Exception
	 * 
	 * @author praveenrn
	 */
	private void getUIElements(LocatorType eLocatorType, int time, boolean waitBeforeGetElement) throws Exception {
		
        getMultipleElements(eLocatorType, waitBeforeGetElement);
    }
	
	/**
	 * All actions performed by users on the application will go through this method.
	 * This includes any mouse clicks and keyboard strokes.
	 * @param eAction: Action to perform on the UI.
	 * @param eLocatorType: Keyword identification type
	 * @param locator: locator that is used to locate the UI Map and the Test Data.
	 * @param locatorName: Name of the Locator
	 * @param text: Text to search/enter
	 * @param waitDuraion: Wait for element duration
	 * @param waitBeforeGetElement: Wait duration before getting element
	 * @return: true/false
	 * 
	 * @author praveenrn
	 */
	public boolean userActions(ActionsType eAction, LocatorType eLocatorType, String locator, String locatorName, String text, int waitDuraion, int index, boolean waitBeforeGetElement) {
		
		actionType = ActionsType.valueOf(eAction.toString());
		locatortype = LocatorType.valueOf(eLocatorType.toString());
		elementLocation = locator;
		elementName = locatorName;
		inputText = text;
		waitTime = waitDuraion;
		boolean bStatus = false;
		
		try {
			if (!(actionType.equals(ActionsType.NavigateToUrl) || actionType.equals(ActionsType.GetAlertText) || actionType.equals(ActionsType.DismissAlert) || actionType.equals(ActionsType.AcceptAlert) 
					|| (actionType.equals(ActionsType.SwitchToDefaultContent)) || (actionType.equals(ActionsType.SwitchToDefaultWindow)) || (actionType.equals(ActionsType.SwitchToWindow)) || (actionType.equals(ActionsType.CloseWindow))
					|| (actionType.equals(ActionsType.ScriptSleep)))) {
               if(actionType.equals(ActionsType.SelectValueFromListByAttribute)) {
            	   getUIElements(eLocatorType, waitDuraion, waitBeforeGetElement);
               }else { getUIElement(eLocatorType, waitDuraion, waitBeforeGetElement); }
			}  
			
			if(text == "") {//Performs the standard action with the locator for this step
				ActionTypeChooser(text, index, waitBeforeGetElement);
			}
			else {
				ActionTypeChooser(text, index, waitBeforeGetElement);
			}
			bStatus = true;
		}
		catch(Exception ex) {
			CaptureDeviceScreenshot(driver);
			log.error("Failed to perform " + actionType + " action on " + locatorName + " due to some error");
			log.error(ex.getMessage());
			BaseTest.executionStatus = "FAILED";
			bStatus = false;
		}
		locatortype = null;
		elementLocation = null;
		elementName = null;
		webelement = null;
		waitDuraion = 0;
		isDisplayed = false;
		return bStatus;
	}

	/**
	 * All actions performed by users without wait for an element on the application will go through this method.
	 * This includes any mouse clicks and keyboard strokes.
	 * @param eAction: Action to perform on the UI.
	 * @param locatorName: Name of the Locator
	 * @param text: Text to search/enter
	 * 
	 * @author praveenrn
	 */
	public void userActions(ActionsType eAction, String locatorName, String text) {
		
		userActions(eAction, LocatorType.empty, "", locatorName, text, 0, -1, false);
	}
	
	/**
	 * All actions performed by users post wait for an element on the application will go through this method.
	 * This includes any mouse clicks and keyboard strokes.
	 * @param eAction: Action to perform on the UI.
	 * @param eLocatorType: Keyword identification type
	 * @param locator: locator that is used to locate the UI Map and the Test Data.
	 * @param locatorName: Name of the Locator
	 * @param text: Text to search/enter
	 * @param waitDuraion: Wait for element duration
	 * @param waitBeforeGetElement: Wait duation before getting element
	 * @return: true/false
	 * 
	 * @author praveenrn
	 */
	public void userActions(ActionsType eAction, LocatorType eLocatorType, String locator, String locatorName, String text, int waitDuraion, boolean waitBeforeGetElement) {
		
		userActions(eAction, eLocatorType, locator, locatorName, text, waitDuraion, -1, waitBeforeGetElement);
	}
	
	/**
	 * All actions performed by users post wait for an element on the application will go through this method.
	 * This includes any mouse clicks and keyboard strokes.
	 * @param eAction: Action to perform on the UI.
	 * @param eLocatorType: Keyword identification type
	 * @param locator: locator that is used to locate the UI Map and the Test Data.
	 * @param locatorName: Name of the Locator
	 * @param text: Text to search/enter
	 * @return: true/false
	 * 
	 * @author praveenrn
	 */
	public void userActions(ActionsType eAction, LocatorType eLocatorType, String locator, String locatorName, String text) {
		
		userActions(eAction, eLocatorType, locator, locatorName, text, 0, -1, false);
	}
	
	/**
	 * Perform Click and all Get type actions
	 * @param eAction: Action to perform on the UI.
	 * @param eLocatorType: Keyword identification type
	 * @param locator: locator that is used to locate the UI Map and the Test Data.
	 * @param locatorName: Name of the Locator
	 * 
	 * @author praveenrn
	 */
	public void userActions(ActionsType eAction, LocatorType eLocatorType, String locator, String locatorName) {
		
		userActions(eAction, eLocatorType, locator, locatorName, "", -1, -1, false);
	}
	
	
	/**
	 * This will perform all actions needed by a test case.
	 * @param text
	 * @param waitBeforeGetElement
	 * @throws Exception 
	 * 
	 * @author praveenrn
	 */
	private void ActionTypeChooser(String text, int index, boolean waitBeforeGetElement) throws Exception {
		
		if(actionType != null) {
			switch(actionType) {
			case Click: click(); break;
			case TypeText: typeText(text); break;
			case GetText: getText(); break;
			case GetAttributeText: getAttributeText(inputText); break;
			case SwitchToDefaultContent: switchToDefaultContent(); break;
			case SwitchToFrameByIndex: switchToFrameByIndex(index); break;
			case SwitchToFrameByName: switchToFrameByName(text); break;
			case SwitchToFrameByWebElement: switchToFrameByWebElement(); break;
			case SwitchToWindow: switchToWindow(); break;
			case SwitchToDefaultWindow: switchToDefaultWindow(); break;
			case AcceptAlert: acceptAlert(); break;
			case DismissAlert: dismissAlert(); break;
			case GetAlertText: getAlertText(); break;
			case NavigateToUrl: navigateToUrl(text); break;
			case CloseWindow: closeWindow(); break;
			case SelectValueFromListByAttribute: selectValueFromListByAttribute(text); break;
			case ScriptSleep: scriptSleep(); break;
			default: 
				log.info("Not a valid Action enum value.");
			}
		}
	}
	
	
	/**
	 * Perform Click operation
	 * @author praveenrn
	 */
	private void click() {
		
		webelement.click();
		log.info("Successfully clicked on " + elementName);
	}
	
	/**
	 * Enter Text in the Text Area
	 * @param text : Text to enter in the Text Area
	 * @author praveenrn
	 */
	private void typeText(String text) {
		
        webelement.clear();
        webelement.sendKeys(text);
        log.info("Successfully entered text in "+ elementName);
    }
	
	/**
	 * Close Browser Window
	 * @author praveenrn
	 */
	private void closeWindow() {
		
		driver.close();
		driver.switchTo().window(currentWindowHandle);
		log.info("Successfully closed "+ driver.getTitle() +" window");
     }
	
	/**
	 *  Navigates to a url
	 * @param url : URL to navigate
	 * @return
	 * @author praveenrn
	 */
	private boolean navigateToUrl(String url) {
		
        driver.navigate().to(url);
        log.info("Successfully navigated to " + url);
        if (url.contains(driver.getCurrentUrl()))
            return true;
        return false;

    }
	
	/**
	 * Script sleep
	 */
	public void scriptSleep(){
		
		try {
			Thread.sleep(5000);
			log.info("Script waited for 5000 milliseconds");
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * Return text from control
	 * @param locatorName : Name of the Locator/Element
	 * @author praveenrn
	 */
	private void getText() {
		
		elementText = webelement.getText();
        log.info("Successfully returned text# "+ elementText +" from " + elementName);
    }
	
	/**
	 * Return text from control by using its attribute value
	 * @param attributeValue : Element Attribute 'value/name'
	 * @return Element attribute value
	 * @author praveenrn
	 */
	private void getAttributeText(String attributeValue) {
		
		elementText = webelement.getAttribute(attributeValue);
	    log.info("Successfully returned text from " + elementName);
	}
	
	/**
	 * Switch back to default content/page
	 * @author praveenrn
	 */
	private void switchToDefaultContent() {
		
        driver.switchTo().defaultContent();
        log.info("Successfully switched back to default content");
    }
	
	/**
	 *  Switching between frames by using frame name
	 * @param frameName : Frame Name
	 * @author praveenrn
	 * 
	 */
	private void switchToFrameByName(String frameName) {
		
        driver.switchTo().frame(frameName);
        log.info("Successfully switched to frame "+ frameName);
    }
	
	/**
	 * Switching between frames by using frame index
	 * @param frameIndex : iframe index
	 * 
	 * @author praveenrn
	 */
	private void switchToFrameByIndex(int frameIndex) {
		
        driver.switchTo().frame(frameIndex);
        log.info("Successfully switched to frame " + frameIndex);
    }
	
	/**
	 * Switching between frames by using WebElement
	 * 
	 * @author praveenrn
	 */
	private void switchToFrameByWebElement() {
		
        driver.switchTo().frame(webelement);
        log.info("Successfully switched to frame by WebElement");
    }
	
	/**
	 * Accepts alerts
	 * 
	 * @author praveenrn
	 */
	private void acceptAlert() {
		
        driver.switchTo().alert().accept();
        log.info("Successfully accepted allert messsages");
    }
	
	/**
	 * Dismisses Alerts
	 * 
	 * @author praveenrn
	 */
	private void dismissAlert() {
		
        driver.switchTo().alert().dismiss();
        log.info("Successfully dismissed allert messsages");
    }
	
	/**
	 * Return Alert text
	 * @return Alert Text
	 * 
	 * @author praveenrn
	 */
	private String getAlertText() {
		
        String alertText = "";
        alertText = driver.switchTo().alert().getText();
        log.info("Successfully returned allert text " + alertText);
        return alertText;
    }
	
	/**
	 * Get all available Window Handles
	 * @return All available Window Handles
	 * 
	 * @author praveenrn
	 */
	private Set<String> getAllHandles() {
		
		windowHandles = driver.getWindowHandles();
        return windowHandles;
    }
	
	/**
	 * Return Current Window Handle
	 * @return Window Handle
	 * 
	 * @author praveenrn
	 */
	private String getCurrentHanlde() {
		
		currentWindowHandle = driver.getWindowHandle();
		return currentWindowHandle;
	}
	
	/**
	 * Switching between windows
	 * 
	 * @author praveenrn
	 */
	private void switchToWindow() {
		
		String newWindow;
        Iterator<String> iterator;
        
        getCurrentHanlde();
        getAllHandles();
        Set<String> newSet = windowHandles;
        while (newSet.size() == windowHandles.size())
        {   
        	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}// Wait .40 seconds
        	// Wait for the new window to be displayed
            newSet = getAllHandles();
            break;// See if the new window is up
        }   // while

        iterator = newSet.iterator();
        do
        {   // Find which one is the new window
            newWindow = iterator.next();
        } while (windowHandles.contains(newWindow) && iterator.hasNext());

        driver.switchTo().window(newWindow);
        driver.manage().window().maximize();
        windowTitle = driver.getTitle();
        log.info("Successfully switched to "+ windowTitle);
		
    }
	
	/**
	 * Switch to default/current context
	 * @author praveenrn
	 */
	private void switchToDefaultWindow() {
		
		getCurrentHanlde();
        driver.switchTo().window(currentWindowHandle);
        log.info("Successfully switched to Current/Default " + driver.getTitle());
    }
	
	/**
	 * Wait functionality
	 * @param eWait : Wait type
	 * @param eLocatorType : Locator Indentification Type
	 * @param Locator : Element Location
	 * @param locatorName : Element Name
	 * @param time : Wait duration 
	 * @param text : Element Text
	 * @param elementIndex : Element Index
	 * @return If it wait returns true else returns false
	 * 
	 * @author praveenrn 
	 */
	public boolean waitFunctionality(WaitType eWait, LocatorType eLocatorType, String Locator, String locatorName, int time, String text, int elementIndex) {
		
		waitType = WaitType.valueOf(eWait.toString());
		elementLocation = Locator;
		locatortype = LocatorType.valueOf(eLocatorType.toString());
		elementName = locatorName;
		waitTime = time;
		//boolean bWait = false;
		try {
			if (waitTypeChooser(elementLocation, text, elementIndex)) {
                return bWait = true;
            }
            bWait = true;
		}
		catch(Exception ex) {
			log.error("Failed to perform " + waitType + "action on " + elementName + " due to some error");
            log.error(ex.getMessage());
            BaseTest.executionStatus = "FAILED";
			bWait = false;
		}
		locatortype = null;
		elementLocation = null;
		elementName = null;
		waitTime = 0;
		return bWait;
	}
	
	/**
	 * Wait functionality
	 * @param eWait : Wait type
	 * @param locatorName : Element Name
	 * @param time : Wait duration
	 * 
	 * @author praveenrn  
	 */
	public void waitFunctionality(WaitType eWait,String locatorName, int time) {
	
		waitFunctionality(eWait, LocatorType.empty, "", locatorName, time, "", -1);
	}
	
	/**
	 * Wait functionality
	 * @param eWait : Wait type
	 * @param eLocatorType : Locator Indentification Type
	 * @param Locator : Element Location
	 * @param locatorName : Element Name
	 * @param time : Wait duration 
	 * @return If it wait returns true else returns false
	 * 
	 * @author praveenrn 
	 */
	public void waitFunctionality(WaitType eWait, LocatorType eLocatorType, String Locator, String locatorName, int time) {
	
		waitFunctionality(eWait, eLocatorType, Locator, locatorName, time, "", -1);
	}
	
	/**
	 * Function designed to call different "Wait Functionalities" functions based on the enum type passed in.
	 * @param Locator : Element Location
	 * @param text : Element Text
	 * @param elementIndex : Element Index
	 * @return
	 * @throws Exception
	 * 
	 * @author praveenrn
	 */
	private boolean waitTypeChooser(String Locator, String text, int elementIndex) throws Exception {
		
		switch(waitType) {
		case TimeWait:
			return timeWait();
		case WaitForSpecificElement:
			return waitForSpecificElement(locatortype, elementIndex);
		case WaitForScriptTimeOut:
			return waitForScriptTimeOut();
		case WaitForElement:
			return waitForElement();
		case WaitForPageLoad:
			return waitForPageLoad();
		case WaitAndGetElement:
			return waitAndGetElement(locatortype);
		case WaitAndGetElements:
			return waitAndGetElements(locatortype);
		case WaitUntilElementIsClickable:
			return waitUntilElementIsClickable(locatortype);
		case WaitForAjaxCallOut:
			return waitforAjaxCallOut(); 
		case WaitForWindowToClose:
			return waitForWindowToClose();
		default:
			throw new Exception("Not a valid Wait enum value.");
		}
	}
	
	/**
	 * Implicit Wait for specific duration in Seconds.
	 * @return Returns true if operation is successfully done otherwise false
	 * 
	 * @author praveenrn
	 */
	private boolean timeWait() {
		
		return implicitWait(waitTime);
	}
	
   /**
	* Set Script Time out.
    * @param time Timeout duration in Seconds.
	* @return Returns true if operation is successfully done otherwise false
	* 
	* @author praveenrn
	*/
	private boolean waitForScriptTimeOut() {
		
		 return setScriptTimeOut(waitTime);
	 }
	
	/**
	 * Method to ensure a specific control is available for Action.
	 * @return Returns true : if element is found otherwise false
	 * 
	 * @author praveenrn
	 */
	private boolean waitForElement() {
		
		 if(explicitWait(waitTime)) {
			 log.info("Successfully performed WaitForElement on "+ elementName +" for "+ waitTime +" seconds");
			 return true;
		 }
		 return false;
	 }
	
	/**
	 * Wait for Ajax Call Out
	 * @param iTime : Wait duration
	 * @return 
	 * @return : true/false
	 */
	public Boolean waitforAjaxCallOut(){
		
		return (new WebDriverWait(driver, 120).until(new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver driver) {
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            return (Boolean) js.executeScript("return jQuery.active == 0");
	        }
	    }));
	}
	
	private boolean waitForWindowToClose(){
		
		int timeCount = 1;

		do
		{
		   driver.getWindowHandles();
		   try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		   timeCount++;
		   if ( timeCount > 50 ) 
		   {
			   bAssert = true;
		       break;
		   }
		}
		while ( driver.getWindowHandles().size() != 1 );
		return bAssert;
	}
	
	/**
	 * Wait for popup window to close
	 * @return
	 */
	private boolean waitIfPopupWindowClosed() {
		
		currentWindowHandle = driver.getWindowHandle();
		WebDriverWait wait = new WebDriverWait(driver, 20);
	    wait.until(new ExpectedCondition<Boolean>() {
	        @Override
	        public Boolean apply(WebDriver d) {
	            return d.getWindowHandles().size() != 1;
	        }
	    });
		  
	    for (String activeHandle : driver.getWindowHandles()) {
	        if (!activeHandle.equals(currentWindowHandle)) {
	            driver.switchTo().window(activeHandle);
	            if(driver.getTitle().equals("trivago")){
	            	
	            	
	            }
	        }
	    }
	    driver.switchTo().window(currentWindowHandle);
		return bAssert; 
	}
	
	/**
	 * Method to ensure that specific element with index is available for action, when we search for multiple elements.
	 * @param eLocatorType : Locator Indentification
	 * @param elementIndex : Index of element.
	 * @return Returns true if element is found otherwise false
	 * 
	 * @author praveenrn
	 */
	private boolean waitForSpecificElement(LocatorType eLocatorType, int elementIndex) {
		
		 if(waitForMultipleElements(eLocatorType, elementIndex)) {
			 log.info("Successfully performed WaitForSpecificElement on " + elementName + " seconds");
			 return true;
		 }
		 return false;
	 }
	 
	/**
	 * Implicit Wait for specific duration in Seconds.
	 * @param time
	 * @return
	 * 
	 * @author praveenrn
	 */
	private boolean implicitWait(int time) {
		
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		log.info("Successfully ImplicitWaited for " + time + " seconds");
		return true;
	}
	
	/**
	 * Method to ensure a specific control is available for Action and assign it to element.
	 * This method will wait for a maximun of provided time in seconds, if control is available before that, it will return the desired result.
	 * @param time
	 * @return
	 * 
	 * @author praveenrn
	 */
	private boolean explicitWait(int time) {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)  
						             .withTimeout(time, TimeUnit.SECONDS)  
						             .pollingEvery(2, TimeUnit.SECONDS)  
						             .ignoring(NoSuchElementException.class, StaleElementReferenceException.class); 

		webelement	= (RemoteWebElement) wait.until(new Function<WebDriver, WebElement>() {  
	           public WebElement apply(WebDriver driverObj) { 
	        	   try {
	        		   
	        		   isDisplayed = getElementBy(locateElement()).isDisplayed();
        			   
        			   if(isDisplayed == true) {
        				   webelement = (RemoteWebElement)getElementBy(locateElement());
        			   }
	        		   
	        	   } catch(ElementNotFoundException e) {
	        		   log.error( "ElementNotFound : An element# "+ elementName +" could not be located with the given searched parameter location# "+ elementLocation +" on the page.");
		       	   } catch(StaleElementReferenceException e) {
		       			log.error( "Stale element : An element# "+ elementName +" could not be located with the given searched parameter location# "+ elementLocation +" on the page.");
		       	   } catch(Exception e) {
		       			log.error( "Exception : An element# "+ elementName +" could not be located with the given searched parameter location# "+ elementLocation +" on the page.");
	        	   }
	        	   return webelement;
	            } 
	      });  
	  return isDisplayed;  
	}
	
	/**
	 * Wait for the Element to exists before assignment.
	 * @param eLocatorType
	 * 
	 * @author praveenrn
	 */
	private boolean waitAndGetElement(LocatorType eLocatorType) throws InvalidDataException {
		String sLocatorType = eLocatorType.toString();
		if (sLocatorType == "" || sLocatorType == null )
            throw new InvalidDataException("The UI Locator Type is Empty, Please Specify a UI Locator");
		else
            return waitAndReturnElement(eLocatorType);
	}
	
	/**
	 *  Wait for element and return once available.
	 * @param eLocatorType
	 * @param elementLoc
	 * @return
	 * 
	 * 
	 * @author praveenrn
	 */
	private boolean waitAndReturnElement(final LocatorType eLocatorType) {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)  
	             .withTimeout(120, TimeUnit.SECONDS)  
	             .pollingEvery(2, TimeUnit.SECONDS)  
	             .ignoring(NoSuchElementException.class); 

	     webelement	= (RemoteWebElement) wait.until(new Function<WebDriver, WebElement>()
	     {  
	           public WebElement apply(WebDriver driverObj) {
	        	   
	        	   try {
	        		   webelement = (RemoteWebElement)getElementBy(locateElement());
	        		   isDisplayed = webelement.isDisplayed();
	        		   
	        		   if(isDisplayed == false) {
	        			 throw new ElementNotVisibleException("An element# "+ elementName +" could not be located with the given searched parameter location# "+ elementLocation +" on the page.");   
	        		   }
	        		   log.info("Successfully waited for element# "+ elementName +" on Location# "+ elementLocation +" and returned element");
	        		   return webelement;
	        	   }
	        	   catch(Exception ex) {
	        		  log.error("Exception : An element# "+ elementName +" could not be located with the given searched parameter location# "+ elementLocation +" on the page.");
	        	   }
	        	   return webelement;
	            }  
	      });  
	     
	  return isDisplayed;  
    }

	/**
	 * Wait for multiple elements and returns true If all elements are found otherwise false
	 * This method will wait for a maximun of 20 seconds, if element is available before that, it will return the desired result.
	 * @param eLocatorType
	 * @return
	 * @throws Exception
	 * 
	 * 
	 * @author praveenrn
	 */
	private boolean waitAndGetElements(LocatorType eLocatorType) throws Exception {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)  
	             .withTimeout(120, TimeUnit.SECONDS)  
	             .pollingEvery(2, TimeUnit.SECONDS)  
	             .ignoring(NoSuchElementException.class); 

	     webelement	= (RemoteWebElement) wait.until(new Function<WebDriver, WebElement>()
	     {  
	           public WebElement apply(WebDriver driverObj) {
	        	   
	        	   try {
	        		   webelements = getElementsBy(locateElement());
	        		   for(WebElement element : webelements){
	        			
	        			   isDisplayed = element.isDisplayed();
	        			   webelement = (RemoteWebElement) element;
	        		   }
	        		   
	        		   if(isDisplayed == false) {
	        			 throw new ElementNotVisibleException("An element# "+ elementName +" could not be located with the given searched parameter location# "+ elementLocation +" on the page.");   
	        		   }
	        		   log.info("Successfully waited for element# "+ elementName +" on Location# "+ elementLocation +" and returned element");
	        		   return webelement;
	        	   }
	        	   catch(Exception ex) {
	        		  log.error("Exception : An element# "+ elementName +" could not be located with the given searched parameter location# "+ elementLocation +" on the page.");
	        	   }
	        	   return webelement;
	            }  
	      });
	     log.info("Successfully waited for WebElement "+ webelement.toString());
	     return isDisplayed;
	}

	/**
	 * Wait for specific control with index.
	 * This method will wait for a maximun of 10 seconds, if control is available before that, it will return the desired result. 
	 * @param eLocatorType : Locator Identification
	 * @param elementIndex : Provide the index of element.
	 * @return Returns True if elements are found otherwise false
	 * 
	 * @author praveenrn
	 */
	private boolean waitForMultipleElements(LocatorType eLocatorType, int elementIndex) {
		
		boolean bGetMultipleElements = false;
		driverWait = new WebDriverWait(driver, waitTime);
		try {
			webelements = getElementsBy(locateElement());
        	if(webelements.size() > 0) {
        		driverWait.until(ExpectedConditions.elementSelectionStateToBe(webelements.get(elementIndex), true));
        		driverWait.until(ExpectedConditions.elementToBeSelected(webelements.get(elementIndex)));
        		bGetMultipleElements = webelements.get(elementIndex).isDisplayed();
        	}
		}
		catch(NoSuchElementException ex) {
			log.error("NoSuchElement : An element# "+ elementName +" could not be located with the given searched parameter location# "+ elementLocation +" on the page.");
		}
		catch(Exception e) {
			log.error("Exception : An element# "+ elementName +" could not be located with the given searched parameter location# "+ elementLocation +" on the page.");
		}
		return bGetMultipleElements;
	}
	
	/**
	 * Set Script Time out.
	 * @param time
	 * @return Always returns true
	 * 
	 * @author praveenrn
	 */
	private boolean setScriptTimeOut(int time) {
		
		driver.manage().timeouts().setScriptTimeout(time, TimeUnit.SECONDS);
		log.info("Successfully Script waited for "+ time +" seconds");
		return true;
	}

	/**
	 * Wait for Page Load
	 * @return return page is loaded or not
	 * 
	 * @author praveenrn
	 */
	private boolean waitForPageLoad() {
		
		
		    ExpectedCondition<Boolean> pageLoadCondition = new
		        ExpectedCondition<Boolean>() {
		            public Boolean apply(WebDriver driver) {
		                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
		            }
		        };
		    WebDriverWait wait = new WebDriverWait(driver, 30);
		    return wait.until(pageLoadCondition);
		
		
		/*boolean isPageLoded = false;
		driverWait = new WebDriverWait(driver, waitTime);
		try {
			if(driverWait.until(ExpectedConditions.elementSelectionStateToBe(getElementBy(locateElement()), true))) {
				isPageLoded = true;
			}
		}
		catch(NoSuchElementException ex) {
			log.error("Unable to wait for Page Load for given time "+ waitTime);
		} catch (Exception e) {
			log.error("Unable to wait for Page Load for given time "+ waitTime);
		}
		log.info("Successfully performed WaitForPageLoad");
		return isPageLoded;*/
	}

	/**
	 * Wait for element to be clickable
	 * @param eLocatorType
	 * @return element is clickable or not
	 * 
	 * @author praveenrn
	 */
	private boolean waitUntilElementIsClickable(LocatorType eLocatorType) {
		
		boolean isClickable = false;
		driverWait = new WebDriverWait(driver, waitTime);
		try {
			driverWait.until(ExpectedConditions.elementToBeClickable(getElementBy(locateElement(/*eLocatorType*/))));
			log.info("Successfully waited for element "+ elementName +" to be clickable");
			isClickable = true;
		}
		catch(Exception e) {
			log.error("Element "+ elementName +" is not clickable at this location");
			isClickable = false;
		}
		
		return isClickable;
	}
	
	/**
	 * Assertion functionality
	 * @param eAssert: Type of Assert
	 * @param eLocatorType: Type of Element Locator 
	 * @param Locator: Element location
	 * @param LocatorName: Element Name/Type
	 * @param attributeType: WebElement Attribute value/name
	 * @param expectedResult: Expected result
	 * @param dropDownIndex: Drop Down Index
	 * @param waitDuraion: Wait Duration
	 * @param waitBeforeGetElement: Wait before get element
	 * @return
	 * 
	 * @author praveenrn
	 */
	public boolean assertFunctionality(AssertionType eAssert, LocatorType eLocatorType, String Locator, String locatorName, String attributeType, String expectedResult, int dropDownIndex,
			int waitDuraion, boolean waitBeforeGetElement) {
		
		assertTye = AssertionType.valueOf(eAssert.toString());
		elementLocation = Locator;
		locatortype = LocatorType.valueOf(eLocatorType.toString());
		elementName = locatorName;
		attType = attributeType;
		inputText = expectedResult;
		
		try {
			if(locatortype.toString() != "empty"){
				getUIElement(eLocatorType, waitDuraion, waitBeforeGetElement);
			}
			if (assertTypeChooser(expectedResult, dropDownIndex)) {
                return bAssert = true;
            }
            bAssert = true;
		}
		catch(Exception ex) {
			log.error("Assertion "+ assertTye +": Failed to perform " + assertTye + " Assertion on " + elementName + " due to some error");
            log.error(ex.getMessage());
            BaseTest.executionStatus = "FAILED";
			Assert.fail("Assertion "+ assertTye +": Failed to perform " + assertTye + " Assertion on " + elementName + " due to some error");			
			bAssert = false;
		}
		locatortype = null;
		elementLocation = null;
		elementName = null;
		attType = null;
		inputText = null;
		return bAssert;
	}
	
	/**
	 * Assertion functionality
	 * @param eAssert: Type of Assert
	 * @param expectedResult: Expected result
	 * @return
	 * 
	 * @author praveenrn
	 */
	public void assertFunctionality(AssertionType eAssert, String locatorName, String expectedResult) {
		
		assertFunctionality(eAssert, LocatorType.empty, "", locatorName, null, expectedResult, 0, -1, false);
	}
	
	/**
	 * Assertion functionality
	 * @param eAssert: Type of Assert
	 * @param eLocatorType: Type of Element Locator 
	 * @param Locator: Element location
	 * @param LocatorName: Element Name/Type
	 * @param attributeType: WebElement Attribute value/name
	 * @param expectedResult: Expected result
	 * @return
	 * 
	 * @author praveenrn
	 */
	public void assertFunctionality(AssertionType eAssert, LocatorType eLocatorType, String Locator, String locatorName, String attributeType, String expectedResult) {
	
		assertFunctionality(eAssert, eLocatorType, Locator, locatorName, attributeType, expectedResult, -1, 0, false);
	}
	
	/**
	 * Assertion functionality
	 * @param eAssert: Type of Assert
	 * @param eLocatorType: Type of Element Locator 
	 * @param Locator: Element location
	 * @param LocatorName: Element Name/Type
	 * @param attributeType: WebElement Attribute value/name
	 * @return
	 * 
	 * @author praveenrn
	 */
	public void assertFunctionality(AssertionType eAssert, LocatorType eLocatorType, String Locator, String locatorName, int waitDuraion, boolean waitBeforeGetElement ) {
	
		assertFunctionality(eAssert, eLocatorType, Locator, locatorName, null, "", -1, waitDuraion, waitBeforeGetElement);
	}
	
	/**
	 * Assertion functionality
	 * @param eAssert: Type of Assert
	 * @param eLocatorType: Type of Element Locator 
	 * @param Locator: Element location
	 * @param LocatorName: Element Name/Type
	 * @param expectedResult: Expected result
	 * @param waitDuraion: Wait Duration
	 * @param waitBeforeGetElement: Wait before get element
	 * 
	 * @author praveenrn
	 */
	public void assertFunctionality(AssertionType eAssert, LocatorType eLocatorType, String Locator, String locatorName, String expectedResult, int waitDuraion, 
			boolean waitBeforeGetElement) {
	
		assertFunctionality(eAssert, eLocatorType, Locator, locatorName, null, expectedResult, -1, waitDuraion, waitBeforeGetElement);
	}
	
	/**
	 * Assertion functionality
	 * @param eAssert: Type of Assert
	 * @param eLocatorType: Type of Element Locator 
	 * @param Locator: Element location
	 * @param LocatorName: Element Name/Type
	 * 
	 * @author praveenrn
	 */
	public void assertFunctionality(AssertionType eAssert, LocatorType eLocatorType, String Locator, String locatorName) {
	
		assertFunctionality(eAssert, eLocatorType, Locator, locatorName, null, null, -1, 0, false);
	}
	
	/**
	 * Assertion functionality
	 * @param eAssert: Type of Assert
	 * @param eLocatorType: Type of Element Locator 
	 * @param Locator: Element location
	 * @param LocatorName: Element Name/Type
	 * @param expectedResult: Expected result
	 * @param dropDownIndex: Drop Down Index
	 * @param waitDuraion: Wait Duration
	 * @param waitBeforeGetElement: Wait before get element
	 * 
	 * @author praveenrn
	 */
	public void assertFunctionality(AssertionType eAssert, LocatorType eLocatorType, String Locator, String locatorName, String expectedResult, int dropDownIndex,
			int waitDuraion, boolean waitBeforeGetElement) {
		
		assertFunctionality(eAssert, eLocatorType, Locator, locatorName, null, expectedResult, dropDownIndex, waitDuraion, waitBeforeGetElement);
	}
	
	/**
	 * Function designed to call different "Wait Functionalities" functions based on the enum type passed in.
	 * @param expectedResult: Expected result
	 * @param dropDownIndex: Drop Down Index
	 * @return: True/False
	 * @throws Exception 
	 * 
	 * @author praveenrn
	 */
	private boolean assertTypeChooser(String expectedResult, int dropDownIndex) throws Exception {
		
		switch(assertTye) {
		case TextExists: verifyTextOnElement(expectedResult); break;
		case TextExistsOnPageSource: verifyTextContainsOnPage(expectedResult); break;	
		case TextContains: verifyTextContainsOnElement(expectedResult); break;
		case CheckBoxChecked: verifyCheckBoxChecked(); break;
		case RadioButtonChecked: verifyRadioButtonSelected(); break;
		case PageTitle: verifyPageTitle(expectedResult); break;
		case CurrentWindowURL: verifyPageURL(expectedResult); break;
		case AlertPresent: verifyAlertPresent(); break;
		case AlertText: verifyAlertText(expectedResult); break;
		case CheckDropdownByText: checkDropdownByText(expectedResult); break;
		case CheckDropdownContainsText: checkDropdownContainsText(expectedResult); break;
		case ControlExists: verifyControlExists(); break;
		default:
			throw new Exception("Not a valid Wait enum value.");
		}
		return isDisplayed;
	}
	
	/**
	 * Assert Actual and Expected Texts are equal on WebElement
	 * @param expectedText: Expected Result/Text
	 * @return true/false
	 * 
	 * @author praveenrn
	 */
	private boolean isTextPresentOnElement(String expectedText) {
		
		if(attType != null) {
			actualTxt = webelement.getAttribute(attType);
			if(webelement.getAttribute(attType).trim().toLowerCase().equals(expectedText.trim().toLowerCase())) {
				log.info("Assrted text "+ expectedText +" is present on webelement");
				return true;
			}
		}else {
			actualTxt = webelement.getText();
			if(webelement.getText().trim().toLowerCase().equals(expectedText.trim().toLowerCase())) {
				log.info("Assrted text "+ expectedText +" is present on webelement");
				return true;
			} 
		}
		log.error("Asserted Expected Text:  "+ expectedText +" is not present on webelement. Actual Text is# "+actualTxt);
		return false;
	}
	
	/**
	 * Verify Text is present on WebElement
	 * @param expectedText: Expected Result/Text
	 * 
	 * @author praveenrn
	 */
	private void verifyTextOnElement(String expectedText) {
		
		Assert.assertTrue(isTextPresentOnElement(expectedText), "Expected Text: "+ expectedText +" is not present on webelement");
	}
	
	/**
	 * Verify Control/WebElement on page
	 * 
	 * @author praveenrn
	 */
	private void verifyControlExists() {
		
		Assert.assertTrue(explicitWait(waitTime), "Control "+ elementName +" does not exists on page/UI");
	}
	
	/**
	 * Assert Expected Text is present/contains on page
	 * @param expectedText: Expected Result/Text
	 * @return tru/false
	 * 
	 * @author praveenrn
	 */
	private boolean isTextContainsOnPage(String expectedText) {
		
		if(driver.getPageSource().contains(expectedText)) {
			log.info("Assrted text "+ expectedText +" is present on page");
			return true;
		}
		log.error("Assrted text "+ expectedText +" is not present on page");
		return false;
	}
	
	/**
	 * Verify Expected Text is contains on Page Source
	 * @param expectedText: Expected Result/Text
	 * 
	 * @author praveenrn
	 */
	private void verifyTextContainsOnPage(String expectedText) {
		
		Assert.assertTrue(isTextContainsOnPage(expectedText), "Expected Text: "+ expectedText +" is not present on page");
	}
	
	/**
	 * Assert Actual and Expected Texts are contains equal on WebElement
	 * @param expectedText: Expected Result/Text
	 * @return true/false
	 * 
	 * @author praveenrn
	 */
	private boolean isTextContainsOnElement(String expectedText) {
		
		if(attType != null) {
			actualTxt = webelement.getAttribute(attType);
			if(webelement.getAttribute(attType).trim().toLowerCase().contains(expectedText.trim().toLowerCase())) {
				log.info("Assrted text "+ expectedText +" is contains on webelement");
				return true;
			}
		}else {
			actualTxt = webelement.getText();
			if(webelement.getText().trim().toLowerCase().contains(expectedText.trim().toLowerCase())) {
				log.info("Assrted text "+ expectedText +" is contains on webelement");
				return true;
			}
		}
		log.error("Assrted text "+ expectedText +" is not contains on webelement. Actual Text is# "+actualTxt);
		return false;
	}
	
	/**
	 * Verify Expected Text contains on WebElement
	 * @param expectedText: Expected Result/Text
	 * 
	 * @author praveenrn
	 */
	private void verifyTextContainsOnElement(String expectedText) {
		
		Assert.assertTrue(isTextContainsOnElement(expectedText), ""+ expectedText +" is not contains on webelement");
	}
	
	
	private boolean isCheckBoxChecked() {
		
		if(webelement.isSelected()) {
			log.info("Assrted check box is checked");
			return true;
		}
		log.error("Assrted check box is not checked");
		return false;
	}
	
	/**
	 * Verify Checkbox is checked ot not
	 * 
	 * @author praveenrn
	 */
	private void verifyCheckBoxChecked() {
		
		Assert.assertTrue(isCheckBoxChecked(), "Check box is not checked");
	}
	
	private boolean isRadioButtonSelected() {
		
		if(webelement.isSelected()) {
			log.info("Assrted Radio Button is selected");
			return true;
		}
		log.error("Assrted Radio Button is not selected");
		return false;
	}
	
	/**
	 * Verify Checkbox is checked ot not
	 * 
	 * @author praveenrn
	 */
	private void verifyRadioButtonSelected() {
		
		Assert.assertTrue(isRadioButtonSelected(), "Radio Button is not elected");
	}
	
	private boolean isPageTitle(String expectedTitle) {
		
		windowTitle = driver.getTitle();
		if(driver.getTitle().trim().toLowerCase().equals(expectedTitle.trim().toLowerCase())) {
			log.info("Assterted page title "+ expectedTitle +" is contains on the page");
			return true;
		}
		log.error("Assterted page title "+ expectedTitle +" is not contains on the page");
		return false;
	}
	
	/**
	 * Verify Page Title
	 * @param expectedTitle: Expected Page Title
	 * 
	 * @author praveenrn
	 */
	private void verifyPageTitle(String expectedTitle) {
		
		Assert.assertTrue(isPageTitle(expectedTitle), "Expected Page Title: "+ expectedTitle +" is not contains on the page");
	}
	
	private boolean isPageURL(String expectedPageURL) {
		
		if(driver.getCurrentUrl().trim().toLowerCase().equals(expectedPageURL.trim().toLowerCase())) {
			log.info("Asserted Page Title "+ expectedPageURL +" is present on current page/window");
			return true;
		}
		log.error("Asserted Page Title "+ expectedPageURL +" is not present on current page/window");
		return false;
	}
	
	/**
	 * Assert Page URL
	 * @param expectedPageURL
	 * 
	 * @author praveenrn
	 */
	private void verifyPageURL(String expectedPageURL) {
		
		Assert.assertTrue(isPageURL(expectedPageURL), "Expected Page URL: "+ expectedPageURL +" is not exists");
	}
	
	private boolean isAlertPresent() {
		
		try {
			driver.switchTo().alert();
			return true;
		} catch(NoAlertPresentException na) {
			log.error("Alert is not present");
			return false;
		} catch(Exception ex) {
			log.info( "Exception: \n" + ex.getMessage() + "\n");
			return false;
		}
	}
	
	/**
	 * Verify Alert Presence
	 * 
	 * @author praveenrn
	 */
	private void verifyAlertPresent() {
		
		Assert.assertTrue(isAlertPresent(), "Alert is not present");
	}
	
	private boolean isAlertText(String expectedAlertText) {
		
		if(driver.switchTo().alert().getText().trim().toLowerCase().contains(expectedAlertText.trim().toLowerCase())) {
			log.info("Asserted alert text "+ expectedAlertText +" is contains on Alert window");
			return true;
		}
		log.error("Asserted alert text "+ expectedAlertText +" is not contains on Alert window");
		return false;
	}
	
	/**
	 * Verify Alert text
	 * @param expectedAlertText: Expected Alert Text
	 * 
	 * @author praveenrn
	 */
	private void verifyAlertText(String expectedAlertText) {
		
		Assert.assertTrue(isAlertText(expectedAlertText), "Expected Alert Text: "+ expectedAlertText +" is not contains on Alert window");
	}
	
	private boolean checkDropdownByText(String expectedText) {
		
		boolean bfound = false;
		try {
			
			Select select = new Select(webelement);
			List<WebElement> options = select.getOptions();
			for(WebElement option : options) {
				
				if(attType != null) {
					if(option.getAttribute("value").trim().toLowerCase().equals(expectedText.trim().toLowerCase())) {
						log.info("Assrted text "+ expectedText +" is present in dropdown");
						bfound = true;
						break;
					}
				}else {
					if(option.getText().trim().toLowerCase().equals(expectedText.trim().toLowerCase())) {
						log.info("Assrted text "+ expectedText +" is present in dropdown");
						bfound = true;
						break;
					}
					if(option.getTagName().trim().toLowerCase().equals(expectedText.trim().toLowerCase())) {
						log.info("Assrted text "+ expectedText +" is present in dropdown");
						bfound = true;
						break;
					}
				}
			}
		} catch(Exception ex) {
			log.error("Assrted text "+ expectedText +" does not present in dropdown :"+ex.getMessage());
		}
		return bfound;
	}
	
	private boolean checkDropdownContainsText(String expectedText) {
		
		boolean bfound = false;
		try {
			
			Select select = new Select(webelement);
			List<WebElement> options = select.getOptions();
			for(WebElement option : options) {
				
				if(attType != null) {
					if(option.getAttribute("value").trim().toLowerCase().contains(expectedText.trim().toLowerCase())) {
						log.info("Assrted text "+ expectedText +" is present in dropdown");
						bfound = true;
						break;
					}
				}else {
					if(option.getText().trim().toLowerCase().contains(expectedText.trim().toLowerCase())) {
						log.info("Assrted text "+ expectedText +" is present in dropdown");
						bfound = true;
						break;
					}
					if(option.getTagName().trim().toLowerCase().contains(expectedText.trim().toLowerCase())) {
						log.info("Assrted text "+ expectedText +" is present in dropdown");
						bfound = true;
						break;
					}
				}
			}
		} catch(Exception ex) {
			log.error("Assrted text "+ expectedText +" does not contain in dropdown :"+ex.getMessage());
		}
		return bfound;
	}
	
	/**
	 * Select an element from List control by its Attribute Value
	 * @param sAttributeType : Type of Attribute
	 * @param expectedText : Exptected Text/Result
	 * @return : True/False
	 */
	private boolean selectValueFromListByAttribute(String expectedText) {
		
			boolean bfound = false;
			try {
				
				//Iterate through list of cities
				for(WebElement city : webelements){
				
					//Get City details and compare with expected result. If matches
					if(city.getAttribute(elementName).trim().toLowerCase().equals(expectedText.trim().toLowerCase())){
						
						log.info("Selected "+ city.getAttribute("title") +" from suggetion list");
						bfound = true;
						city.click();
						break;
					} else{log.error("City doesnot exists in list");}
				}
				
			} catch(Exception ex) {
				log.error("Assrted text "+ expectedText +" does not present in dropdown :"+ex.getMessage());
			}
			
			return bfound;
		}
	}