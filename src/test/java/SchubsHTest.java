/*
	Class: Software Engineering 2
	Author: Daniel Keller
	Date: 12/4/2019
*/

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FilenameUtils;
import java.io.IOException;

public class SchubsHTest {
	private static final String FOLDER = "src"+File.separator+"files"+File.separator;

	@BeforeClass
	public static void saveOriginals() {
		String[] originals = new String[] {FOLDER+"empty.txt", FOLDER+"pork.txt", FOLDER+"reeves.txt", FOLDER+"shells.txt"};
		for (int i = 0; i < originals.length; i++)
			try {
				Files.copy(Paths.get(originals[i]), Paths.get(originals[i].replace("files", "originalFiles")), StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}

	@AfterClass
	public static void restoreOriginals() {
		String[] originals = new String[] {FOLDER+"empty.txt", FOLDER+"pork.txt", FOLDER+"reeves.txt", FOLDER+"shells.txt"};
		for (int i = 0; i < originals.length; i++)
			try {
				Files.copy(Paths.get(originals[i].replace("files", "originalFiles")), Paths.get(originals[i]), StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}

	void compareFiles(String expected, String actual) {
		// same name and same extention
		assertEquals(FilenameUtils.getExtension(expected), FilenameUtils.getExtension(actual));
		assertEquals(FilenameUtils.getBaseName(expected), FilenameUtils.getBaseName(actual));

		//same content
		BinaryIn ein = new BinaryIn(expected);
		BinaryIn ain = new BinaryIn(actual);
		while (!ein.isEmpty() && !ain.isEmpty())
			assertEquals(ein.readChar(), ain.readChar());

		//same file size
		assertTrue(ein.isEmpty() && ain.isEmpty());
		ein.close();
		ain.close();
	}

	void testSingleFile(String filename) throws IOException {
		//compress it
		schubsH.main(new String[] { filename });

		//decompress the file
		deschubs.main(new String[] { filename.concat(".hh") });

		//compare the files
		compareFiles(filename.replace("files", "originalFiles"), filename);
	}

	void testMultipleFiles(String[] filenames) throws IOException {
		//compress it
		schubsH.main(filenames);

		//for each file
		for (int i = 0; i < filenames.length; i++) {
			//decompress the file
			deschubs.main(new String[] { filenames[i].concat(".hh") });

			//compare the files
			compareFiles(filenames[i].replace("files", "originalFiles"), filenames[i]);
		}
	}

	@Test
	public void empty() throws IOException {
		testSingleFile(FOLDER+"empty.txt");
	}

	@Test
	public void pork() throws IOException {
		testSingleFile(FOLDER+"pork.txt");
	}

	@Test
	public void reeves() throws IOException {
		testSingleFile(FOLDER+"reeves.txt");
	}

	@Test
	public void shells() throws IOException {
		testSingleFile(FOLDER+"shells.txt");
	}

	@Test
	public void glob() throws IOException {
		testMultipleFiles(new String[] {FOLDER+"empty.txt", FOLDER+"pork.txt", FOLDER+"reeves.txt", FOLDER+"shells.txt"});
	}
}