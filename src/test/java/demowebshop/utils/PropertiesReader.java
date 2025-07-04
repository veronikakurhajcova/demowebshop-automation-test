package demowebshop.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

	private Properties properties;

	public PropertiesReader(String filePath) throws IOException {
		properties = new Properties();

		try {
			FileInputStream input = new FileInputStream(filePath);
			properties.load(input);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IOException("Properties file not found: " + filePath, e);
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
