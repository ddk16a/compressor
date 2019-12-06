/*
	Class: Software Engineering 2
	Author: Daniel Keller
	Date: 12/4/2019

	Compilation: javac SchubsH.java
	Execution: java SchubsH <file_name>
	Dependencies: HuffmanSE2.java

	Note: compresses the file denoted by <file_name> using Huffman.

	Example java SchubsH pork.cat produces pork.cat.hh
	-pork.cat.hh-
	169b 08b6 d9d7 3b79 63b5 aeb7 9240 b95d
	0b2d c16e 5a14 9bd0 0000 012f d2fc 39b3
	574c 768e c543 5936 b2ed f0a0 fe39 20
*/

public class SchubsH {
	public static void main(String[] args) {
		//compresses each and adds the '.hh' extension to the end (blee.txt.hh)
		for (int i = 0; i < args.length; i++)
			HuffmanSE2.compress(new BinaryIn(args[i]), new BinaryOut(args[i].concat(".hh")));
	}
}
