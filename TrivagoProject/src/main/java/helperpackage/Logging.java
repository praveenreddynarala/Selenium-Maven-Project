package helperpackage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import baseclass.BaseTest;

public class Logging {
	
	private static Logger logger;
	private static final String fileName = "Testlog";
	private static final String dateAndTimeFormat = "MM-dd-yyyy_hh.mm.ss";

	/**
     * This is the public block which appends the log file name with the
     * timestamp to make it unique
     */
	public void loggingFileAppender() {
	    
	    try {
	    	SimpleDateFormat dateFormat = new SimpleDateFormat(dateAndTimeFormat);
	    	Date now = new Date();
	        String dateTime = dateFormat.format(now);
	        String FileName = fileName + "-" + dateTime + ".log";
	        File file = new File("./logs/" + FileName);

	        if (file.createNewFile()) {
	        	BaseTest.properties = new ReadPropertiesFile("src/main/resources/log4j.properties");
	        	BaseTest.properties.setPropertyValue("log4j.appender.theFileAppender.File", "./logs/" + FileName);
	            org.apache.log4j.LogManager.resetConfiguration();
	            BaseTest.properties.configureProperties();
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        System.out.print("IO Exception in static method of Logger Class. "
	                + ex.getMessage());
	        System.exit(-1);
	    }

	}
	
	/**
	 * This method creates instance of the Logger class coming from log4j jar by
	 * implementing a singelton
	 * 
	 * @return _logger - new instance if no instance exist else an existing
	 *         instance if the method is invoked previously
	 */
	public static Logger createLogger() {
	    if (logger == null) {
	        logger = Logger.getLogger("Sample");
	        return logger;
	    } else
	        return logger;
	}
}
