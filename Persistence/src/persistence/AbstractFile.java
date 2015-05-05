package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class AbstractFile {

	File file = null;

	public AbstractFile(String pathname) throws FileNotFoundException {
		loadFile(pathname);
	}

	public AbstractFile(File csvFile) throws FileNotFoundException {
		loadFile(csvFile);
	}

	public void loadFile(File file) throws FileNotFoundException {
		if (file.exists()) {
			this.file = file;
		} else {
			throw new FileNotFoundException();
		}
	}

	public void loadFile(String pathname) throws FileNotFoundException {
		loadFile(new File(pathname));
	}

	public abstract String[][] loadValues() throws IOException;

	public abstract void saveValues(String[][] values) throws IOException;

}