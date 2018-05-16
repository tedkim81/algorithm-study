package com.codejam;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class CodeJam2010_1B_C_othersolution {
    long[][] memo;
    long[][] c;
    final long MOD = 100003;

    long calc(int n, int k) {
        if (memo[n][k] != -1) {
            return memo[n][k];
        }
        if (k == 1) {
            return memo[n][k] = 1;
        }

        memo[n][k] = 0;
        for (int j = 1; j < k; j++) {
            if (n - k >= k - j) {
                memo[n][k] = (memo[n][k] + calc(k, j) * c[n - k - 1][k - j - 1]) % MOD;
            }
        }

        return memo[n][k];
    }

    public void solve() throws FileNotFoundException {
        Scanner in = new Scanner(new File("/Users/teuskim/codejam/test.in.txt"));
        PrintWriter out = new PrintWriter("/Users/teuskim/codejam/test.out.txt");

        c = new long[501][501];  // 조합을 구하기 위한 맵
        c[0][0] = 1;
        for (int i = 1; i <= 500; i++) {
            c[i][0] = 1;
            for (int j = 1; j <= 500; j++) {
                c[i][j] = (c[i - 1][j - 1] + c[i - 1][j]) % MOD;
            }
        }

        int testN = in.nextInt();
        for (int test = 1; test <= testN; test++) {
            out.print("Case #" + test + ": ");

            int n = in.nextInt();
            memo = new long[n + 1][n];
            for (long[] z : memo) {
                Arrays.fill(z, -1);
            }

            long res = 0;
            for (int i = 1; i < n; i++) {
                res = (res + calc(n, i)) % MOD;
            }
            out.println(res);
            
            // 테스트 출력
            for(int i=0; i<n+1; i++){
            	for(int j=0; j<n; j++){
            		System.out.print(String.format("%4d", memo[i][j]));
            	}
            	System.out.print("\n");
            }
            System.out.print("\n\n");
        }

        in.close();
        out.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        new CodeJam2010_1B_C_othersolution().solve();
    }
}