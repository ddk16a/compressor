/*
	Class: Software Engineering 2
	Author: Daniel Keller
	Date: 12/4/2019
*/

import org.apache.commons.io.FilenameUtils;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Deschubs {
	public static void main(String[] args) {
		//if it was compressed be Huffman
		if (FilenameUtils.getExtension(args[0]).equals("hh"))
			HuffmanSE2.expand(new BinaryIn(args[0]), new BinaryOut(FilenameUtils.removeExtension(args[0])));
		//if it was compressed by LZW
		else if (FilenameUtils.getExtension(args[0]).equals("ll"))
			LZW.expand(new BinaryIn(args[0]), new BinaryOut(FilenameUtils.removeExtension(args[0])));
		//if it is a archive, that it was compressed by LZW, so uncompress it, and then untar it
		else if (FilenameUtils.getExtension(args[0]).equals("zl")) {
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