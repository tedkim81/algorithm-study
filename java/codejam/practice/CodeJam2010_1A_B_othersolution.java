package com.codejam;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import java.util.*;

public class CodeJam2010_1A_B_othersolution {
	boolean TIME = false;
	Scanner sc;
	
	int D, I, M, N;
	int[] a;
	
	void solve() {
		D = sc.nextInt();	// 삭제 비용
		I = sc.nextInt();	// 삽입 비용
		M = sc.nextInt();	// 허용되는 최대 차이값
		N = sc.nextInt();	// 항목수
		a = new int[N];		// 항목 배열
		for (int i = 0; i < N; i++) a[i] = sc.nextInt();
		long[][] dp = new long[N + 1][256];
		for (int i = 0; i < N; i++) {		// 모든 항목에 대하여 순차적으로
			for (int j = 0; j < 256; j++) {	// 대입 가능한 모든 값에 대하여
				long tmp = dp[i][j] + D;	// 다음 항목을 삭제할 경우 현재까지의 비용 계산 결과
				if (M == 0) {	
					tmp = min(tmp, dp[i][j] + abs(a[i] - j));	// M이 0이면 모두 같은 값이어야 하므로 항목 삽입은 고려대상에서 제외된다.
				} else {
					for (int k = 0; k < 256; k++) {	// 대입 가능한 모든 값에 대하여
						long diff = abs(k - j);
						long need = (diff - 1) / M;	// 삽입해야 하는 항목 수
						if (need < 0) need = 0;
						long cost = need * I + abs(a[i] - j);
						if (tmp > dp[i][k] + cost) {
							tmp = dp[i][k] + cost;
						}
					}
				}
				dp[i + 1][j] = tmp;
			}
		}
		long res = Long.MAX_VALUE;
		for (int i = 0; i < 256; i++) res = min(res, dp[N][i]);
		System.out.println(res);
	}
	
	void run() {
		long time = System.currentTimeMillis();
		sc = new Scanner(System.in);
		int on = sc.nextInt();
		for (int o = 1; o <= on; o++) {
			double t = (System.currentTimeMillis() - time) * 1e-3;
			if (TIME) System.err.printf("%03d/%03d %.3f/%.3f%n", o, on, t, t / (o - 1) * on);
			System.out.printf("Case #%d: ", o);
			solve();
			System.out.flush();
		}
	}
	
	void debug(Object...os) {
		System.err.println(deepToString(os));
	}
	
	public static void main(String[] args) {
		new CodeJam2010_1A_B_othersolution().run();
	}
}
