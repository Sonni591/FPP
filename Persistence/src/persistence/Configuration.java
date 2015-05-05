package persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private static Configuration instance;
	private Properties props;

	private Configuration() {
		props = new Properties();
		loadProperties();
	}

	private void loadProperties() {
		try {
			props.load(new FileInputStream("config.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Properties getProperties() {
		// TODO alternativ für jede property eine eigene Rückgabe und
		// Speichermethode schreiben, oder beides.

		// TODO Problem bei dieser Methode, es wird ein Zeiger zurückgegeben. ->
		// Änderungen an dem Objekt werden auch in dieser Klasse geändert, aber
		// nicht in der Datei gespeichert. Evlt. hier nur die Values
		// zurückgeben, oder Alternative wie oben beschrieben verwenden.
		return props;
	}

	public void saveProperties(Properties properties) {
		try {
			props = properties;
			props.store(new FileOutputStream("config.txt"), null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}
}
