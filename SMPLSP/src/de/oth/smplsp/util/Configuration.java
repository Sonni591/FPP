package de.oth.smplsp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The configuration class represents all settings of the application. It is
 * realized as Singleton. The settings are saved in the config.txt file.
 * 
 * @author Tobias Eichinger
 *
 */
public class Configuration {

    private static Configuration instance;

    private Properties props;
    private boolean hasChanged;

    private static String CONFIGPATH = "config.txt"; //$NON-NLS-1$
    private static String DEFULTDECIMALPLACES = "5"; //$NON-NLS-1$
    private static String DECIMALPLACES = "DecimalPlaces"; //$NON-NLS-1$

    private Configuration() {
	props = new Properties();
	loadSettings();
    }

    /**
     * Load settings from the configuration file.
     */
    public void loadSettings() {
	try {
	    props.load(new FileInputStream(CONFIGPATH));
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * Returns the number of decimal places, shown in the GUI.
     * 
     * @return
     */
    public int getDecimalPlaces() {
	return Integer.parseInt(props.getProperty(DECIMALPLACES,
		DEFULTDECIMALPLACES));
    }

    /**
     * Sets the number of decimal places, shown in the GUI.
     * 
     * @param decimalPlaces
     */
    public void setDecimalPlaces(int decimalPlaces) {
	props.setProperty(DECIMALPLACES, Integer.toString(decimalPlaces));
    }

    /**
     * Saves the settings to the configuration file.
     */
    public void saveSettingsToConfigFile() {
	try {
	    props.store(new FileOutputStream(CONFIGPATH), null); //$NON-NLS-1$
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * Returns true, if there were any changes on the settings since the last
     * save and false else.
     * 
     * @return
     */
    public boolean hasChanged() {
	return hasChanged;
    }

    /**
     * Returns the configuration object associated with the current Java
     * application.
     * 
     * @return
     */
    public static Configuration getInstance() {
	if (instance == null) {
	    instance = new Configuration();
	}
	return instance;
    }
}
