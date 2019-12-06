/*
	Class: Software Engineering 2
	Author: Daniel Keller
	Date: 12/4/2019
*/

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FilenameUtils;

public class SchubsLTest {
	//the folder in whic all the test files are located, platform independant
	private static final String FOLDER = "src"+File.separator+"files"+File.separator;
	
	@BeforeClass
	public static void saveOriginals() {
		String[] originals = new String[] {FOLDER+"empty.txt", FOLDER+"pork.cat", FOLDER+"reeves.txt", FOLDER+"shells.sea"};
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
		String[] originals = new String[] {FOLDER+"empty.txt", FOLDER+"pork.cat", FOLDER+"reeves.txt", FOLDER+"shells.sea"};
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
		SchubsL.main(new String[] { filename });

		//decompress the file
		Deschubs.main(new String[] { filename.concat(".ll") });

		//compare the files
		compareFiles(filename.replace("files","originalFiles"), filename);
	}

	void testMultipleFiles(String[] filenames) throws IOException {
		//compress it
		SchubsL.main(filenames);

		//for each file
		for (int i = 0; i < filenames.length; i++) {
			//decompress the file
			Deschubs.main(new String[] { filenames[i].concat(".ll") });

			//compare the files
			compareFiles(filenames[i].replace("files","originalFiles"), filenames[i] );
		}
	}

	@Test
	public void empty() throws IOException {
		testSingleFile(FOLDER+"empty.txt");
	}

	@Test
	public void pork() throws IOException {
		testSingleFile(FOLDER+"pork.cat");
	}

	@Test
	public void reeves() throws IOException {
		testSingleFile(FOLDER+"reeves.txt");
	}

	@Test
	public void shells() throws IOException {
		testSingleFile(FOLDER+"shells.sea");
	}

	@Test
	public void glob() throws IOException {
		testMultipleFiles(new String[] {FOLDER+"empty.txt", FOLDER+"pork.cat", FOLDER+"reeves.txt", FOLDER+"shells.sea"});
	}
}