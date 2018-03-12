package helperpackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * Author: Praveenreddy Narala
 * This class contain different methods. Which are used to retrieve the properties data from different
 * config.properties files 
 */
public class ReadPropertiesFile {

	Properties propertyFile;
	String propertyFileName;
	
	public ReadPropertiesFile(String propertyfileName) {
		
		propertyFile = new Properties();
		
		try{		propertyFile.load(new FileInputStream(propertyfileName));
		}
		catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * Return Property Value againist Key
	 * @param key: The value to be searched
	 * @return: Value
	 */
	public String getPropertyValue(String key) {
		
		String var = propertyFile.getProperty(key);
		return var;
	}
	
	/**
	 * Set new value againist key in properties
	 * @param key: Value to be searched
	 * @param value: Sets new value
	 */
	public void setPropertyValue(String key, String value) {
		propertyFile.setProperty(key, value);
	}
	
	/**
	 * Configure new Config Property value
	 */
	public void configureProperties() {
		PropertyConfigurator.configure(propertyFile);
	}
}
