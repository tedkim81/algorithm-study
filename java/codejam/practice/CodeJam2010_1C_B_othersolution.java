package com.codejam;

import java.io.*;
import java.util.*;

public class CodeJam2010_1C_B_othersolution {
//	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "");
//	private static String inputFileName = fileName + ".in";
//	private static String outputFileName = fileName + ".out";
	private static String inputFileName = "/Users/teuskim/codejam/B-large-practice.in.txt";
	private static String outputFileName = "/Users/teuskim/codejam/B-large-practice.out.txt";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		long lo = in.nextLong();
		long hi = in.nextLong();
		int c = in.nextInt();
		int n = 0;
		while (lo < hi) {
			lo *= c;
			n++;
		}
		out.println(32 - Integer.numberOfLeadingZeros(n - 1));
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new FileReader(inputFileName));
		out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		for (int t = 1; t <= tests; t++) {
			out.print("Case #" + t + ": ");
			new CodeJam2010_1C_B_othersolution().solve();
		}
		in.close();
		out.close();
	}
}
