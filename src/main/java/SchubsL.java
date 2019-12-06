/*
	Class: Software Engineering
	Author: Daniel Keller
	Date: 12/4/2019

	Compilation: javac SchubsL.java
	Execution: java SchubsL <file_name>
	Dependencies: LZW.java

	Note: compresses the file denoted by <file_name> using LZW.

	Example: java SchubsL pork.cat  produces pork.cat.ll
	-pork.cat.ll-
	0490 2007 4068 0690 6e06 b020 0700 6f07
	2063 0750 7010 5065 0730 2006 1072 0651
	0811 4074 0740 7911 206d 0610 7a10 5067
	1000 
*/

public class SchubsL {
	public static void main(String[] args) {
		//compresses each and adds the '.ll' extension to the file (blee.txt.ll)
		for (int i = 0; i < args.length; i++)
			LZW.compress(new BinaryIn(args[i]), new BinaryOut(args[i].concat(".ll")));
	}
}
