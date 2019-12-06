/*
	Class: Software Engineering 2
	Author: Daniel Keller
	Date: 12/4/2019

	Compilation: javac SchubsH.java
	Execution: java SchubsH <file_name>
	Dependencies: HuffmanSE2.java

	Note: compresses the file denoted by <file_name> using Huffman.

	Example java SchubsH pork.cat
*/

public class SchubsH {
	public static void main(String[] args) {
		//compresses each and adds the '.hh' extension to the end (blee.txt.hh)
		for (int i = 0; i < args.length; i++)
			HuffmanSE2.compress(new BinaryIn(args[i]), new BinaryOut(args[i].concat(".hh")));
	}
}
