package testcases;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import baseclass.BaseTest;
import commonpageactions.CommonPageActions;

/**
 * This class contains all typical trivago test cases
 * @author praveenrn
 *
 */
public class Trivago_TestCases {

	private CommonPageActions CPA = null;
	
	/**
	 * Constructor for objects initialization
	 */
	public Trivago_TestCases(){
	
		CPA = new CommonPageActions();
	}
	
	/**
	 * Launch targeted test browser and navigate to URL (Trivago)
	 * @param method
	 */
	@BeforeMethod
	public void launchBrowser(Method method){
	
		BaseTest.testExcClsName = this.getClass().getSimpleName();
		BaseTest.testName = method.getName();
		CPA.startDriver();
	}
	
	@AfterMethod
	public void quitBrowser(){
		
		CPA.closeDriver();
	}
	
	@Test
	public void TC_ViewDeal(){
		
		//Navigate to 'trivago.com' and select city 'Hamburg' from result list
		CPA.Navigate_To_Website_And_Search_City(BaseTest.sURL, "Hamburg");
		//Close Check-in calendar 
		CPA.Close_Calendar_After_Search_Was_Executed();
		//Select second hotel from result list
		CPA.Chose_Second_Hotel_In_Result();
		//Choose third deal
		CPA.Choose_ThirdPrice();
		//Verify selected hotel name on partners window
		CPA.Verify_HotelName_In_Partners_window();
	}
}
