/*
	Class: Software Engineering 2
	Author: Daniel Keller
	Date: 12/4/2019
*/

public class SchubsH {
	public static void main(String[] args) {
		//compresses each and adds the '.hh' extension to the end (blee.txt.hh)
		for (int i = 0; i < args.length; i++)
			HuffmanSE2.compress(new BinaryIn(args[i]), new BinaryOut(args[i].concat(".hh")));
	}
}