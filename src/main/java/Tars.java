/*
	Class: Software Engineering 2
	Author:	Daniel Keller
	Date:	11/22/2019
*/
import java.io.IOException;
import java.io.File;

public class Tars {
	private static final char SEP = (char) 255;

	//includes the file denoted by file name into the archive file out.
	private static void tarer(BinaryOut out, String file_name) throws IOException {
		File in = new File(file_name);
		if (!in.exists() || !in.isFile())
			return;

		// file-name-length
		out.write(file_name.length());
		out.write(SEP);

		// file-name
		out.write(file_name);
		out.write(SEP);

		// file-size
		out.write(in.length());
		out.write(SEP);

		//file
		BinaryIn bin = new BinaryIn(file_name);
		while (!bin.isEmpty())
			out.write(bin.readChar());
		bin.close();
	}

	public static void main(String[] args) throws IOException {
		BinaryOut out = null;
		try {
			//first argument is the archive file
			out = new BinaryOut(args[0]);

			//every other file is to be included in the tar file
			for (int i = 1; i < args.length; i++)
				tarer(out, args[i]);
		}
		finally {
			if (out != null)
				out.close();
		}
	}
}