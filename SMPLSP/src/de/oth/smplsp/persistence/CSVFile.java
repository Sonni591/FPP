package de.oth.smplsp.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.Ostermiller.util.CSVPrint;
import com.Ostermiller.util.ExcelCSVParser;
import com.Ostermiller.util.ExcelCSVPrinter;

import de.oth.smplsp.error.CSVFileWrongNumberOfValuesInFileError;
import de.oth.smplsp.model.Product;

/**
 * A implementation of the abstract class AbstractFile. It represents a CSV -
 * File.
 * 
 * @author Tobias Eichinger
 *
 */
public class CSVFile extends AbstractFile {

    private static final long serialVersionUID = 1L;

    // set the delimiter as a semicolon (for the German world of *.csv files)
    char delimiter = ';';

    /**
     * Creating a new instance with the given pathname.
     * 
     * @param pathname
     * @throws FileNotFoundException
     */
    public CSVFile(String pathname) throws FileNotFoundException {
	super(pathname);
    }

    /**
     * Creating a new instance with the given file.
     * 
     * @param csvFile
     * @throws FileNotFoundException
     */
    public CSVFile(File csvFile) throws FileNotFoundException {
	super(csvFile);
    }

    /**
     * @see de.oth.smplsp.persistence.AbstractFile#loadValues()
     */
    @Override
    public String[][] loadValues() throws IOException {
	ExcelCSVParser parser = new ExcelCSVParser(new FileInputStream(
		this.file), delimiter);
	String[][] values = parser.getAllValues();
	parser.close();
	return values;
    }

    /**
     * @see de.oth.smplsp.persistence.AbstractFile#saveValues(java.lang.String[][])
     */
    @Override
    public void saveValues(String[][] values) throws IOException {
	CSVPrint printer = new ExcelCSVPrinter(new FileOutputStream(this.file));
	printer.changeDelimiter(delimiter);
	printer.println(values);
	printer.close();
    }

    /**
     * @see de.oth.smplsp.persistence.AbstractFile#loadValuesAsProduct()
     */
    @Override
    public ObservableList<Product> loadValuesAsProduct() throws IOException,
	    CSVFileWrongNumberOfValuesInFileError {
	ObservableList<Product> products = FXCollections.observableArrayList();
	String[][] values = loadValues();

	int i = 1;
	for (String[] strings : values) {
	    if (strings.length == 6) {
		products.add(new Product(strings));
		i++;
	    } else {
		throw new CSVFileWrongNumberOfValuesInFileError(i);
	    }
	}

	return products;
    }

    /**
     * @see de.oth.smplsp.persistence.AbstractFile#saveValuesFromProduct(javafx.collections.ObservableList)
     */
    @Override
    public void saveValuesFromProduct(ObservableList<Product> products)
	    throws IOException {
	String[][] values = new String[products.size()][];

	for (int i = 0; i < products.size(); i++) {
	    values[i] = products.get(i).getAllSaveParametersAsString();
	}
	saveValues(values);
    }
}
