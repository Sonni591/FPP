package de.oth.smplsp.error;

/**
 * Throws a custom exception; used when the imported CSV file has an incorrect
 * number of values
 * 
 * @exception CSVFileWrongNumberOfValuesInFileError
 */
public class CSVFileWrongNumberOfValuesInFileError extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Throws a custom exception; used when the imported CSV file has an
     * incorrect number of values
     * 
     * @exception CSVFileWrongNumberOfValuesInFileError
     */
    public CSVFileWrongNumberOfValuesInFileError(int i) {
	super("" + i);
    }
}
