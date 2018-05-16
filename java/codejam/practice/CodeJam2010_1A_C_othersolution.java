package com.codejam;
import static java.lang.Math.*;
import static java.util.Arrays.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class CodeJam2010_1A_C_othersolution {
	boolean TIME = false;
	Scanner sc;
	PrintWriter out;
	
	void solve() {
		int A1 = sc.nextInt(), A2 = sc.nextInt();
		int B1 = sc.nextInt(), B2 = sc.nextInt();
		long res = 0;
		for (int b = B1; b <= B2; b++) {
			int min = (int)(ceil(1.0 / R * b));
			int max = (int)(floor(R * b));
			min = max(min, A1);
			max = min(max, A2);
			res += A2 - A1 + 1;
			if (min <= max) {
				res -= max - min + 1;
			}
		}
		out.println(res);
	}
	
	double R = (sqrt(5) + 1) / 2;
	
	void run() throws FileNotFoundException {
		long time = System.currentTimeMillis();
		sc = new Scanner(new File("/Users/teuskim/codejam/C-small-practice.in.txt"));
		out = new PrintWriter("/Users/teuskim/codejam/C-small-practice.out.txt");
		int on = sc.nextInt();
		for (int o = 1; o <= on; o++) {
			double t = (System.currentTimeMillis() - time) * 1e-3;
			if (TIME) System.err.printf("%03d/%03d %.3f/%.3f%n", o, on, t, t / (o - 1) * on);
			out.printf("Case #%d: ", o);
			solve();
			out.flush();
		}
	}
	
	void debug(Object...os) {
		System.err.println(deepToString(os));
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		new CodeJam2010_1A_C_othersolution().run();
	}
}
