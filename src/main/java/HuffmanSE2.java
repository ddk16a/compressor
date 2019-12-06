//Midified version of Sedwicks Huffman code
//modfied by Daniel Keller
//12/4/2019


/*************************************************************************
 *  Compilation:  javac Huffman.java
 *  Execution:    java Huffman - < input.txt   (compress)
 *  Execution:    java Huffman + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   http://algs4.cs.princeton.edu/55compression/abra.txt
 *                http://algs4.cs.princeton.edu/55compression/tinytinyTale.txt
 *  modified:     change logging to true to enable debugging info to StdErr
 *
 *
 *  Compress or expand a binary input stream using the Huffman algorithm.
 *
 *  % java Huffman - < abra.txt | java BinaryDump 60
 *  010100000100101000100010010000110100001101010100101010000100
 *  000000000000000000000000000110001111100101101000111110010100
 *  120 bits
 *
 *  % java Huffman - < abra.txt | java Huffman +
 *  ABRACADABRA!
 *
 *************************************************************************/

public class HuffmanSE2 {
	private static final int R= 256;
	public static boolean logging = true;

	private static class Node implements Comparable<Node> {
		private final char ch;
		private final int freq;
		private final Node left, right;

		Node(char ch, int freq, Node left, Node right) {
			this.ch = ch;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		private boolean isLeaf() {
			assert (left == null) == (right == null); //cannot have only one child
			return (left == null && right == null);
		}

		public int compareTo(Node that) {
			return this.freq - that.freq;
		}
	}

	public static void compress(BinaryIn in, BinaryOut out) {
        if (in.isEmpty()) {
        	in.close();
        	out.close();
        	return;
        }
		String s = in.readString();
		char[] input = s.toCharArray();

		int[] freq = new int[R];
		for (int i = 0; i < input.length; i++)
			freq[input[i]]++;

		Node root = buildTrie(freq);

		String[] st = new String[R];
		buildCode(st, root, "");

		writeTrie(root, out);

		out.write(input.length);

		for (int i = 0; i < input.length; i++) {
			String code = st[input[i]];
			for (int g = 0; g < code.length(); g++)
				out.write(code.charAt(g) == '1');
		}

		in.close();
		out.close();
	}

	private static Node buildTrie(int[] freq) {
		MinPQ<Node> pq = new MinPQ<Node>();
		for (char i = 0; i < R; i++)
			if (freq[i] > 0)
				pq.insert(new Node(i, freq[i], null, null));

		while (pq.size() > 1) {
			Node left = pq.delMin();
			Node right = pq.delMin();
			Node parent = new Node('\0', left.freq + right.freq, left, right);
			pq.insert(parent);
		}
		return pq.delMin();
	}

	private static void writeTrie(Node x, BinaryOut out) {
		if (x.isLeaf()) {
			out.write(true);
			out.write(x.ch);
			return;
		}
		out.write(false);
		writeTrie(x.left, out);
		writeTrie(x.right, out);
	}

	private static void buildCode(String[] st, Node x, String s) {
		if (x.isLeaf()) {
			st[x.ch] = s;
			return;
		}

		buildCode(st, x.left, s + '0');
		buildCode(st, x.right, s + '1');
	}

	public static void expand(BinaryIn in, BinaryOut out) {
        if (in.isEmpty()) {
        	in.close();
        	out.close();
        	return;
        }
		Node root = readTrie(in);

		int length = in.readInt();

		for (int i = 0; i < length; i++) {
			Node x = root;
			while (!x.isLeaf()) {
				boolean bit = in.readBoolean();
				if (bit) x = x.right;
				else     x = x.left;
			}
			out.write(x.ch);
		}
		in.close();
		out.close();
	}

	private static Node readTrie(BinaryIn in) {
		boolean isLeaf = in.readBoolean();
		if (isLeaf) {
		char x = in.readChar();
			return new Node(x, -1, null, null);
		}
		else {
			return new Node('\0', -1, readTrie(in), readTrie(in));
		}
	}

	//java HuffmanSE2  <uncompressed-file-name>  <compressed-file-name>
	public static void main(String[] args) {
		compress(new BinaryIn(args[0]), new BinaryOut(args[1]));
	}
}
