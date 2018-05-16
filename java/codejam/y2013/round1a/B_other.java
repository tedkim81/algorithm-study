package gcj.y2013.round1a;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.deepToString;

import java.io.File;
import java.util.Scanner;

public class B_other {
	String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
	Scanner sc;
	
	long E, R;
	int N;
	long[] vs;
	
	void read() {
		E = sc.nextInt();
		R = sc.nextInt();
		N = sc.nextInt();
		R = min(R, E);
		vs = new long[N];
		for (int i = 0; i < N; i++) {
			vs[i] = sc.nextInt();
		}
	}
	
	void solve() {
		long[] dp1 = new long[N + 1], dp2 = new long[N + 1];
		for (int i = 0; i < N; i++) {
			dp2[i + 1] = max(dp2[i + 1], dp1[i] + E * vs[i]);
			int p = 0;
			for (int j = 0; i + j < N; j++) {
				if (vs[i + p] < vs[i + j]) p = j;
				if (R * (p + 1) > E) break;
				if (R * (j + 1) <= E) dp2[i + j + 1] = max(dp2[i + j + 1], dp2[i] + R * (j + 1) * vs[i + j]);
				if (R * (j + 2) >= E) {
					long q = R * (j + 2) - E;
					if (q <= R * (p + 1)) {
						dp1[i + j + 1] = max(dp1[i + j + 1], dp2[i] + q * vs[i + p]);
					}
				}
			}
			for (int j = 0; i + j < N && R * (j + 1) <= E; j++) {
				dp1[i + j + 1] = max(dp1[i + j + 1], dp1[i] + R * (j + 1) * vs[i]);
			}
		}
		System.out.println(dp2[N]);
	}
	
	void run() {
		try{
			sc = new Scanner(new File(path+"B-large-practice.in.txt"));
		}catch(Exception e){}
		
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
		new B_other().run();
	}
}
