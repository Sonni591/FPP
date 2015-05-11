package de.oth.smplsp.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import de.oth.smplsp.model.Product;

/**
 * An abstract representation of a file to load/save a Product object.
 * 
 * @author Tobias Eichinger
 *
 */
public abstract class AbstractFile {

    File file = null;

    /**
     * Creating a new instance with the given pathname.
     * 
     * @param pathname
     * @throws FileNotFoundException
     */
    public AbstractFile(String pathname) throws FileNotFoundException {
	setFile(pathname);
    }

    /**
     * Creating a new instance with the given File object.
     * 
     * @param file
     * @throws FileNotFoundException
     */
    public AbstractFile(File file) throws FileNotFoundException {
	setFile(file);
    }

    /**
     * Sets the file, which further method should work with.
     * 
     * @param file
     * @throws FileNotFoundException
     */
    public void setFile(File file) throws FileNotFoundException {
	if (file.exists()) {
	    this.file = file;
	} else {
	    throw new FileNotFoundException();
	}
    }

    /**
     * Sets the file, by pathname, which further method should work with.
     * 
     * @param pathname
     * @throws FileNotFoundException
     */
    public void setFile(String pathname) throws FileNotFoundException {
	setFile(new File(pathname));
    }

    /**
     * Parses the set file and returns the values.
     * 
     * @return the values of the loaded product
     * @throws IOException
     */
    public abstract String[][] loadValues() throws IOException;

    /**
     * Saves the given values to the set file.
     * 
     * @param values
     * @throws IOException
     */
    public abstract void saveValues(String[][] values) throws IOException;

    /**
     * Parses the set file and returns the product.
     * 
     * @return the loaded Product
     * @throws IOException
     * @throws Exception
     */
    public abstract List<Product> loadValuesAsProduct() throws IOException,
	    Exception;

    /**
     * Saves the product to the set file.
     * 
     * @param products
     * @throws IOException
     */
    public abstract void saveValuesFromProduct(List<Product> products)
	    throws IOException;

}