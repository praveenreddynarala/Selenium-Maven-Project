package objectrepository;

/**
 * It has selected city result page objects
 * @author praveenrn
 *
 */
public class Trivago_Result_PgObjs {

	//Checking button object
	public static String sCheckinBtn_XPATH = "//form[@id='js_dealform_querycenter']/div[2]/div[1]/button";
	//Check-out button object
	public static String sCheckoutBtn_XPATH = "//form[@id='js_dealform_querycenter']/div[2]/div[2]/button";
	//Double Room button object
	public static String sDoubleRoomBtn_XPATH = "//form[@id='js_dealform_querycenter']/div[2]/div[3]/button";
	//Close Check-In Calender object
	public static String sCloseCheckInCalender_XPATH = "//span[text()='Check-in']/../span[contains(@class, 'df_overlay_close_wrap')]";
	//Hotel Result list object
	public static String sHotelResultList_XPATH = "//li[@id='js_item_52934']//h3";//"//div[@id='content']//li//h3";
	//Second deal
	public static String sViewAllDealsBtn_XPATH = "//li[@id='js_item_52934']//ul[2]/li[2]";
	//Third Price selection object
	public static String sThirdPrice_XPATH = "//div[contains(@class, 'deal_wrp js_deal_wrp')][3]/div";
	
	/**
	 * We are forwarding you to the booking site deal. Page Objects
	 * @author praveenrn
	 *
	 */
	public static class trivago_Bookingsite_Window_PgObjs{
		
		//We are forwarding you to the booking site deal. object
		public static String sForwardBookingSite_XPATH = "//div[@id='js_headline1']/h1[text()='We are forwarding you to the booking site deal.']";
	}
	
	/**
	 * Accorhotels Partners Page Objects
	 * @author praveenrn
	 *
	 */
	public static class Trivago_Accorhotels_Partners_PgObjs{
		
		//Hotel Name
		public static String sHotelNama_XPATH = "//div[@id='hotelTemplate']//h3";
	}
}
