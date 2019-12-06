/*
	Class: Software Engineering
	Author: Daniel Keller
	Date: 12/4/2019
*/

public class schubsL {
	public static void main(String[] args) {
		//compresses each and adds the '.ll' extension to the file (blee.txt.ll)
		for (int i = 0; i < args.length; i++)
			LZW.compress(new BinaryIn(args[i]), new BinaryOut(args[i].concat(".ll")));
	}
}