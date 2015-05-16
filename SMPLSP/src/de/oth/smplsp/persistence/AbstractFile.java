package de.oth.smplsp.persistence;

import java.io.File;
import java.io.IOException;

import javafx.collections.ObservableList;
import de.oth.smplsp.model.Product;

/**
 * An abstract representation of a File to load/save a Product object.
 * 
 * @author Tobias Eichinger
 *
 */
public abstract class AbstractFile extends File {

    private static final long serialVersionUID = 1L;

    File file = null;

    /**
     * Creates a new File instance from a parent abstract pathname and a child
     * pathname string. If parent is null then the new File instance is created
     * as if by invoking the single-argument File constructor on the given child
     * pathname string.
     * 
     * @param parent
     * @param child
     */
    public AbstractFile(File parent, String child) {
	super(parent, child);
    }

    /**
     * Creating a new instance with the given pathname.
     *
     * @param pathname
     */
    public AbstractFile(String pathname) {
	super(pathname);
    }

    /**
     * Creating a new instance with the given File object.
     *
     * @param file
     */
    public AbstractFile(File file) {
	super(file, "");
	this.file = file;
    }

    // /**
    // * Sets the file, which further method should work with.
    // *
    // * @param file
    // * @throws FileNotFoundException
    // */
    // public void setFile(File file) throws FileNotFoundException {
    // if (file.exists()) {
    // this.file = file;
    // } else {
    // throw new FileNotFoundException();
    // }
    // }
    //
    // /**
    // * Sets the file, by pathname, which further method should work with.
    // *
    // * @param pathname
    // * @throws FileNotFoundException
    // */
    // public void setFile(String pathname) throws FileNotFoundException {
    // setFile(new File(pathname));
    // }

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
    public abstract ObservableList<Product> loadValuesAsProduct()
	    throws IOException, Exception;

    /**
     * Saves the product to the set file.
     * 
     * @param products
     * @throws IOException
     */
    public abstract void saveValuesFromProduct(ObservableList<Product> products)
	    throws IOException;

}