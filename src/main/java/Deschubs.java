/*
	Class: Software Engineering 2
	Author: Daniel Keller
	Date: 12/4/2019

	Compilation: javac Deschubes.java
	Execution: java Deschubes <file_name>
	Dependencies: HuffmanSE2.java, LZW.java, Untar.java

	Note: identifes the extension of the file ".zl, .hh, .ll" and uncompressed accordingly.
		Any unsopported types result in a "this filetype not supported" message

	Example java Deschubs arc.zl
*/

import org.apache.commons.io.FilenameUtils;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Deschubs {
	public static void main(String[] args) {
		//if it was compressed be Huffman
		String extension = FilenameUtils.getExtension(args[0]);
		if (extension.equals("hh"))
			HuffmanSE2.expand(new BinaryIn(args[0]), new BinaryOut(FilenameUtils.removeExtension(args[0])));
		//if it was compressed by LZW
		else if (extension.equals("ll"))
			LZW.expand(new BinaryIn(args[0]), new BinaryOut(FilenameUtils.removeExtension(args[0])));
		//if it is a archive, that it was compressed by LZW, so uncompress it, and then untar it
		else if (extension.equals("zl")) {
			try {
				//temproarily keep the uncompressed tar file
				String filename = FilenameUtils.removeExtension(args[0]);
				LZW.expand(new BinaryIn(args[0]), new BinaryOut(filename));
				//untar the tar file and then delete the tar file
				Untar.main(new String[] {filename});
				Files.delete(Paths.get(filename));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("This filetype not supported");
	}
}
