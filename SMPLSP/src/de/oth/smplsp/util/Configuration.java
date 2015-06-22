package de.oth.smplsp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The configuration class represents all settings of the application. It is
 * realized as Singleton. The settings are saved in the SMPLSP-Config.txt file.
 * 
 * @author Tobias Eichinger
 *
 */
public class Configuration {

    // singleton instance of this class
    private static Configuration instance;
    // settings of the application as a Properties object
    private Properties props;
    // set this flag to true, if the settings were changed
    private boolean hasChanged = false;

    private String CONFIGPATH = ""; //$NON-NLS-1$
    private static String DEFAULTDECIMALPLACES = "5"; //$NON-NLS-1$
    private static String DECIMALPLACES = "DecimalPlaces"; //$NON-NLS-1$
    private static String DEFAULTBLACKANDWHITEMODE = "false"; //$NON-NLS-1$
    private static String BLACKANDWHITEMODE = "BlackAndWhite"; //$NON-NLS-1$

    private Configuration() {
	CONFIGPATH = System.getProperty("user.dir") + "/SMPLSP-Config.txt";

	hasChanged = false;
	props = new Properties();
	loadSettings();
    }

    /**
     * Load settings from the configuration file.
     */
    public void loadSettings() {
	try {
	    hasChanged = false;
	    File configFile = new File(CONFIGPATH);

	    if (configFile.createNewFile()) {
		setBlackAndWhiteMode(getBlackAndWhiteMode());
		setDecimalPlaces(getDecimalPlaces());
		saveSettingsToConfigFile();
	    }
	    props.load(new FileInputStream(configFile));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
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
		DEFAULTDECIMALPLACES));
    }

    /**
     * Sets the number of decimal places, shown in the GUI.
     * 
     * @param decimalPlaces
     */
    public void setDecimalPlaces(int decimalPlaces) {
	hasChanged = true;
	props.setProperty(DECIMALPLACES, Integer.toString(decimalPlaces));
    }

    /**
     * Returns true, if the GUI is only black and white
     * 
     * @return
     */
    public boolean getBlackAndWhiteMode() {
	boolean blackAndWhite = false;
	if (props.getProperty(BLACKANDWHITEMODE, DEFAULTBLACKANDWHITEMODE)
		.compareTo("true") == 0) {
	    blackAndWhite = true;
	}

	return blackAndWhite;
    }

    public void setBlackAndWhiteMode(boolean blackAndWhiteMode) {
	hasChanged = true;
	props.setProperty(BLACKANDWHITEMODE,
		Boolean.toString(blackAndWhiteMode));
    }

    /**
     * Saves the settings to the configuration file.
     */
    public void saveSettingsToConfigFile() {
	try {
	    hasChanged = false;
	    props.store(new FileOutputStream(CONFIGPATH), null); //$NON-NLS-1$
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
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
