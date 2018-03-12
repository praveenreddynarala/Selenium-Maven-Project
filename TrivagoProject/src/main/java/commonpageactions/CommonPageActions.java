package commonpageactions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import baseclass.BaseTest;
import baseclass.CommonActions;
import baseclass.CommonActions.ActionsType;
import baseclass.CommonActions.AssertionType;
import baseclass.CommonActions.LocatorType;
import baseclass.CommonActions.WaitType;
import objectrepository.Trivago_Home_PgObjs;
import objectrepository.Trivago_Result_PgObjs;
import objectrepository.Trivago_Result_PgObjs.Trivago_Accorhotels_Partners_PgObjs;

public class CommonPageActions extends BaseTest {

	private BaseTest basetestObj = null;
	private CommonActions commonObj = null;
	
	public CommonPageActions(){
	
		basetestObj = new BaseTest();
		commonObj = new CommonActions();
	}
	
	/**
	 * Navigate to http://www.trivago.com
	 * Search for German City 'Hamburg' and select from result list
	 * @param sURL
	 * @param sCityName
	 * @throws InterruptedException 
	 */
	public void Navigate_To_Website_And_Search_City(String sURL, String sCityName){
	
		commonObj.userActions(ActionsType.NavigateToUrl, "Open Trivago application", sURL);
		//Wait for Deal Seartch Textbox on UI for 60 seconds
		commonObj.waitFunctionality(WaitType.WaitForElement, LocatorType.id, Trivago_Home_PgObjs.searchTxt_ID, "Search Textbox", 60);
		//Click on Deal Seartch Textbox
		commonObj.userActions(ActionsType.Click, LocatorType.id, Trivago_Home_PgObjs.searchTxt_ID, "Search Textbox");
		//Type deal name to view deal list box
		commonObj.userActions(ActionsType.TypeText, LocatorType.id, Trivago_Home_PgObjs.searchTxt_ID, "Search Textbox", sCityName);
		//Wait for deal-form-suggest list box
		commonObj.waitFunctionality(WaitType.WaitForElement, LocatorType.id, Trivago_Home_PgObjs.dealformSuggest_Container_ID, "deal-form-suggetion", 30);
		//Wait and return deal list from list box
		commonObj.waitFunctionality(WaitType.WaitAndGetElements, LocatorType.xpath, Trivago_Home_PgObjs.dealformSuggest_List_XPATH, "deal-form-suggetion List", 30);
		//Select "Hamburg City - Hamburg, Germany (518 Hotels)" from the search list
		commonObj.userActions(ActionsType.SelectValueFromListByAttribute, LocatorType.xpath, Trivago_Home_PgObjs.dealformSuggest_List_XPATH, "title", "Hamburg City - Hamburg, Germany (518 Hotels)", 30, true);
	}
	
	/**
	 * Close Check-In Calendar after city search was executed
	 */
	public void Close_Calendar_After_Search_Was_Executed(){
		
		//Wait for Page load
		commonObj.waitFunctionality(WaitType.WaitForPageLoad, "Hotel Result Page", 60);
		//Wait for Check-in button on Hotel result screen
		commonObj.waitFunctionality(WaitType.WaitForElement, LocatorType.xpath, Trivago_Result_PgObjs.sCheckinBtn_XPATH, "Check-in button", 60);
		//Close Check-in Calendar
		commonObj.userActions(ActionsType.Click, LocatorType.xpath, Trivago_Result_PgObjs.sCloseCheckInCalender_XPATH, "Check-in Calendar");
	}
	
	/**
	 * Choose second hotel from the list
	 */
	public void Chose_Second_Hotel_In_Result(){
		
		//Wait for Page load
		commonObj.waitFunctionality(WaitType.WaitForPageLoad, "Hotel Result Page", 60);
		//Wait for Hotel list
		commonObj.waitFunctionality(WaitType.WaitAndGetElement, LocatorType.xpath, Trivago_Result_PgObjs.sHotelResultList_XPATH, "Hotel Result List", 60);
		sGetText = webelement.getAttribute("title");
		//Wait for View All Deals button for second hotel
		commonObj.waitFunctionality(WaitType.WaitAndGetElement, LocatorType.xpath, Trivago_Result_PgObjs.sViewAllDealsBtn_XPATH, "View All Deals", 60);
		//Click on View All Deals to see deals
		commonObj.userActions(ActionsType.Click, LocatorType.xpath, Trivago_Result_PgObjs.sViewAllDealsBtn_XPATH, "View All Deals", "View All Deals", 60, true);
	}
	
	/**
	 * Choose third price deal
	 */
	public void Choose_ThirdPrice(){
		
		//Stop script execution for some time
		commonObj.userActions(ActionsType.ScriptSleep, "Stop script execution", "Stop script execution for some time");
		//Wait for Hotel list
		commonObj.waitFunctionality(WaitType.WaitForElement, LocatorType.xpath, Trivago_Result_PgObjs.sThirdPrice_XPATH, "Third Price", 60);
		//Stop script execution for some time
		commonObj.userActions(ActionsType.ScriptSleep, "Stop script execution", "Stop script execution for some time");
		//Select third deal
		commonObj.userActions(ActionsType.Click,LocatorType.xpath, Trivago_Result_PgObjs.sThirdPrice_XPATH, "Third Price Deal", "Third Price", 30, true);
	}
	
	/**
	 * Verify Selected hotel name on partners window
	 */
	public void Verify_HotelName_In_Partners_window(){
		
		//Switch to Hotel popup window
		commonObj.userActions(ActionsType.SwitchToWindow, "Switch to Child Window - "+windowTitle, windowTitle);
		//Wait for popup window to close
		commonObj.waitFunctionality(WaitType.WaitForWindowToClose, "trivago popup window", 20);
		//Switch back to parent screen
		commonObj.userActions(ActionsType.SwitchToDefaultWindow, "Switch to Trivago Hotel Result Screen", "");
		//Switch to Hotel popup window
		commonObj.userActions(ActionsType.SwitchToWindow, "Switch to Child Window "+ windowTitle, windowTitle);
		//Assert Page Title
		commonObj.assertFunctionality(AssertionType.PageTitle, "Page Title should be "+ windowTitle, windowTitle);
		//Assert Hotel name in Partners site/window
		commonObj.assertFunctionality(AssertionType.TextExistsOnPageSource, "Page", "Mercure Hotel Hamburg Mitte");
	}
	
}
