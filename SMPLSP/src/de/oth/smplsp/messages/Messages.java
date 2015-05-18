package de.oth.smplsp.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "messages.messages"; //$NON-NLS-1$
    public static String CSVFile_WrongNumberOfValuesInFile;
    public static String RootLayoutController_AboutDialogLicense;
    public static String RootLayoutController_AboutDialogNameAndVerison;
    public static String RootLayoutController_AboutDialogTitle;
    public static String SettingsDialog_Title;
    public static String SettingsDialogController_6DialogNoNumber_Header;
    public static String SettingsDialogController_CloseButton;
    public static String SettingsDialogController_CloseDialog_Content;
    public static String SettingsDialogController_CloseDialog_Header;
    public static String SettingsDialogController_CloseDialog_Title;
    public static String SettingsDialogController_DialogNoNumber_Content;
    public static String SettingsDialogController_DialogNoNumber_Title;
    public static String SettingsDialogController_SaveButton;
    static {
	// initialize resource bundle
	NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
