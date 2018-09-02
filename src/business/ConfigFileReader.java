package business;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigFileReader {
	
	private InputStream inputStream;
	private Properties properties;

	public ConfigFileReader(String fileName){
		loadFile(fileName);
	}
	
	public void loadFile(String fileName){
		this.properties = new Properties();
		
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
			
			if (inputStream != null) {
				properties.load(inputStream);
			}
			else {
				throw new FileNotFoundException("config file: "+fileName+" not found.");
			}
		}
		catch(Exception e) {
			System.out.println("Exception: "+e);
		}
		finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getProperty(String propertyName){
		String propertyValue = "";
		try {
			if (!properties.isEmpty() && properties != null) {
				propertyValue = properties.getProperty(propertyName);
			}
			else {
				throw new NullPointerException("properties is empty or null");
			}
		}
		catch(Exception e) {
			System.out.println("Exception: "+e);
		}
		finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return propertyValue;
	}
}
