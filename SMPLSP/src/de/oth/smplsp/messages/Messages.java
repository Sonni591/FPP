/**
 * 
 */
package de.oth.smplsp.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author Tobias Eichinger
 *
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "de.oth.smplsp.messages.messages"; //$NON-NLS-1$
    public static final String BUNDLE_NAME_FXML = "messages/messagesFXML.properties";
    public static String ExceptionDialog_Header;
    public static String ExceptionDialog_LabelOfTextArea;
    public static String ExceptionDialog_Title;
    public static String RootLayoutController_AboutDialog_License;
    public static String RootLayoutController_AboutDialog_NameAndVersion;
    public static String RootLayoutController_AboutDialog_Title;
    public static String SettingsDialog_Title;
    public static String SettingsDialogController_CloseButton;
    public static String SettingsDialogController_CloseDialog_Content;
    public static String SettingsDialogController_CloseDialog_Header;
    public static String SettingsDialogController_CloseDialog_Title;
    public static String SettingsDialogController_DialogNoNumber_Content;
    public static String SettingsDialogController_DialogNoNumber_Header;
    public static String SettingsDialogController_DialogNoNumber_Title;
    public static String SettingsDialogController_SaveButton;
    static {
	// initialize resource bundle
	NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
