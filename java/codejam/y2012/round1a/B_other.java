package gcj.y2012.round1a;
import static java.util.Arrays.deepToString;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Scanner;

public class B_other {
	Scanner sc = new Scanner(System.in);
	
	int N;
	int[] a, b;
	
	void read() {
		N = sc.nextInt();
		a = new int[N];
		b = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = sc.nextInt();
			b[i] = sc.nextInt();
		}
	}
	
	void solve() {
		int[] star = new int[N];
		int total = 0, res = 0;
		while (total < N * 2) {
			boolean ok = false;
			for (int i = 0; i < N; i++) if (star[i] < 2 && b[i] <= total) {
				total += 2 - star[i];
				star[i] = 2;
				res++;
				ok = true;
			}
			if (ok) continue;
			int p = -1;
			for (int i = 0; i < N; i++) if (star[i] == 0 && a[i] <= total && (p < 0 || b[p] < b[i])) p = i;
			if (p < 0) {
				System.out.println("Too Bad");
				return;
			}
			total++;
			star[p] = 1;
			res++;
		}
		System.out.println(res);
	}
	
	void run() {
		int caseN = sc.nextInt();
		for (int caseID = 1; caseID <= caseN; caseID++) {
			read();
			System.out.printf("Case #%d: ", caseID);
			solve();
			System.out.flush();
		}
	}
	
	void debug(Object...os) {
		System.err.println(deepToString(os));
	}
	
	public static void main(String[] args) {
		try {
			System.setIn(new BufferedInputStream(new FileInputStream("/Users/teuskim/Documents/workspace/android-src/CodeJam/src/B-small-practice.in.txt")));
		} catch (Exception e) {
		}
		new B_other().run();
	}
}
