package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.CSVPrint;
import com.Ostermiller.util.CSVPrinter;

public class CSVFile extends AbstractFile {

	File file = null;

	public CSVFile(String pathname) throws FileNotFoundException {
		super(pathname);
	}

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
}
