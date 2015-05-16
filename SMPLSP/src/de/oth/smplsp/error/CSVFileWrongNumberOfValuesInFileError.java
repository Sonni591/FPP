package de.oth.smplsp.error;


public class CSVFileWrongNumberOfValuesInFileError extends Exception {
    /**
     * 
     */
    public CSVFileWrongNumberOfValuesInFileError(int i) {
	// super(Messages.CSVFile_WrongNumberOfValuesInFile + i);
	super("" + i);
    }
}
