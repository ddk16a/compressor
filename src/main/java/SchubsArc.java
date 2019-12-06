/*
	Class: Software Engineering 2
	Author: Daniel Keller
	Date: 12/4/2019

	Compilation: javac SchubesArc.java
	Execution: java SchubesArc <archive_name> <file_name>...
	Dependencies: Tars.java, LZW.java

	Note: a tar file will be created with the files denoted by <file_name>.. and that tar file will be compressed with
		LZW. The extension zl will be added to <archive_name> and that will be the result file

	Example java SchubesSrc arc pork.txt
*/

import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.FilenameUtils;

public class SchubsArc {
	public static void main(String[] args) {
		try {
			//first tar the files
			Tars.main(args);
			String temp = args[0].concat(".temp");
			Files.move(Paths.get(args[0]), Paths.get(temp));

			//compress it and add the '.zl' back
			LZW.compress(new BinaryIn(temp), new BinaryOut(args[0].concat(".zl")));

			//delete the temporary file
			Files.delete(Paths.get(temp));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
