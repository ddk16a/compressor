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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FilenameUtils;

public class SchubsArcTest {
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

	@Test
	public void arc() {
		String[] args = new String[] {FOLDER+"arc",FOLDER+"empty.txt",FOLDER+"pork.cat",FOLDER+"reeves.txt",FOLDER+"shells.sea"};

		//compress the files
		SchubsArc.main(args);

		//decompress them
		Deschubs.main(new String[] { args[0].concat(".zl") });

		//compare each file to the file produces by deschubing the .zz file
		for (int i = 1; i < args.length; i++)
			compareFiles(args[i].replace("files","originalFiles"), args[i]);
	}
}