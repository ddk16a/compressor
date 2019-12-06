/*
	Class: Software Engineering 2
	Author:	Daniel Keller
	Date:	11/22/2019

	Compilation: javac Untars.java
	Execution: java Untars <archive_name>
	Dependencies: BinaryIn.java, BinaryOut.java

	Note: recreats all the files contained within the file <archive_name>

	Example java Untars blee.tar
*/

import java.io.IOException;

class Untar {
	public static void main(String[] args) throws IOException {
		BinaryIn in = new BinaryIn(args[0]);
		BinaryOut out = null;

		while (!in.isEmpty()) {
			try {
				//file name size
				int file_name_size = in.readInt();
				in.readChar(); //seperator

				//file name
				String file_name = "";
				for (int i = 0; i < file_name_size; i++)
					file_name += in.readChar();
				in.readChar(); //seperator

				//file size
				long file_size = in.readLong();
				in.readChar(); //seperator

				//file
				out = new BinaryOut(file_name);
				for (long i = 0; i < file_size; i++)
					out.write(in.readChar());

				out.close();
			}
			finally {
				if (out != null)
					out.close();
			}
		}
		in.close();
	}
}