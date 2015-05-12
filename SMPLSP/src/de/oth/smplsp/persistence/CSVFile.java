package de.oth.smplsp.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.CSVPrint;
import com.Ostermiller.util.CSVPrinter;

import de.oth.smplsp.messages.Messages;
import de.oth.smplsp.model.Product;

/**
 * A implementation of the abstract class AbstractFile. It represents a CSV -
 * File.
 * 
 * @author Tobias Eichinger
 *
 */
public class CSVFile extends AbstractFile {

    File file = null;

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

    @Override
    public String[][] loadValues() throws IOException {
	CSVParse parser = new CSVParser(new FileInputStream(file));
	String[][] values = parser.getAllValues();
	parser.close();
	return values;
    }

    @Override
    public void saveValues(String[][] values) throws IOException {
	CSVPrint printer = new CSVPrinter(new FileOutputStream(file));
	printer.println(values);
	printer.close();
    }

    @Override
    public List<Product> loadValuesAsProduct() throws IOException, Exception {
	List<Product> products = new ArrayList<Product>();
	String[][] values = loadValues();

	int i = 1;
	for (String[] strings : values) {
	    if (strings.length == 6) {
		products.add(new Product(strings));
		i++;
	    } else {
		throw new Exception(Messages.CSVFile_WrongNumberOfValuesInFile
			+ i);
	    }
	}

	return products;
    }

    @Override
    public void saveValuesFromProduct(List<Product> products)
	    throws IOException {
	String[][] values = new String[products.size()][];

	for (int i = 0; i < products.size(); i++) {
	    values[i] = products.get(i).getAllSaveParametersAsString();
	}
	saveValues(values);
    }
}
