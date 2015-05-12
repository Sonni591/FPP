package de.oth.smplsp.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "messages.messages"; //$NON-NLS-1$
    public static String CSVFile_WrongNumberOfValuesInFile;
    static {
	// initialize resource bundle
	NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
