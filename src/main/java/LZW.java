//modified version of Sedwicks LZW
//midified by Daniel Keller
//12/4/2019

/******************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   https://algs4.cs.princeton.edu/55compression/abraLZW.txt
 *                https://algs4.cs.princeton.edu/55compression/ababLZW.txt
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 ******************************************************************************/

/**
 *  The {@code LZW} class provides static methods for compressing
 *  and expanding a binary input using LZW compression over the 8-bit extended
 *  ASCII alphabet with 12-bit codewords.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/55compression">Section 5.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick  
 *  @author Kevin Wayne
 */
public class LZW {
	private static final int R = 256;        // number of input chars
	private static final int L = 4096;       // number of codewords = 2^W
	private static final int W = 12;         // codeword width

	// Do not instantiate.
	private LZW() { }

	/**
	 * Reads a sequence of 8-bit bytes from standard input; compresses
	 * them using LZW compression with 12-bit codewords; and writes the results
	 * to standard output.
	 */
	public static void compress(BinaryIn in, BinaryOut out) {
		if (in.isEmpty()) {
			in.close();
			out.close();
			return;
		}
		String input = in.readString();
		TST<Integer> st = new TST<Integer>();
		for (int i = 0; i < R; i++)
			st.put("" + (char) i, i);
		int code = R+1;  // R is codeword for EOF

		while (input.length() > 0) {
			String s = st.longestPrefixOf(input);  // Find max prefix match s.
			out.write(st.get(s), W);      // Print s's encoding.
			int t = s.length();
			if (t < input.length() && code < L)    // Add s to symbol table.
				st.put(input.substring(0, t + 1), code++);
			input = input.substring(t);            // Scan past s in input.
		}
		out.write(R, W);
		out.close();
		in.close();
	} 

	/**
	 * Reads a sequence of bit encoded using LZW compression with
	 * 12-bit codewords from standard input; expands them; and writes
	 * the results to standard output.
	 */
	public static void expand(BinaryIn in, BinaryOut out) {
		if (in.isEmpty()) {
			in.close();
			out.close();
			return;
		}
		String[] st = new String[L];
		int i; // next available codeword value

		// initialize symbol table with all 1-character strings
		for (i = 0; i < R; i++)
			st[i] = "" + (char) i;
		st[i++] = "";                        // (unused) lookahead for EOF

		int codeword = in.readInt(W);
		if (codeword == R) return;           // expanded message is empty string
		String val = st[codeword];

		while (true) {
			out.write(val);
			codeword = in.readInt(W);
			if (codeword == R) break;
			String s = st[codeword];
			if (i == codeword) s = val + val.charAt(0);   // special case hack
			if (i < L) st[i++] = val + s.charAt(0);
			val = s;
		}
		out.close();
		in.close();
	}
}