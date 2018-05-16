package gcj.y2012.round1c;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class C_other2 {

    static void preprocess() {
    }

    void solve(Scanner sc, PrintWriter pw) {
        int N = sc.nextInt();
        int M = sc.nextInt();
        long[] a = new long[N];
        int[] A = new int[N];
        long[] b = new long[M];
        int[] B = new int[M];
        for (int i=0; i<N; i++) {
            a[i] = sc.nextLong();
            A[i] = sc.nextInt();
        }
        for (int i=0; i<M; i++) {
            b[i] = sc.nextLong();
            B[i] = sc.nextInt();
        }

        long[][] cumA = new long[N+1][101];
        long[][] cumB = new long[M+1][101];

        for (int typ=1; typ<=100; typ++) {
            cumA[0][typ] = 0;
            for (int i=0; i<N; i++) {
                cumA[i+1][typ] = cumA[i][typ];
                if (A[i] == typ)
                    cumA[i+1][typ] += a[i];
            }
            cumB[0][typ] = 0;
            for (int i=0; i<M; i++) {
                cumB[i+1][typ] = cumB[i][typ];
                if (B[i] == typ)
                    cumB[i+1][typ] += b[i];
            }
        }

        long[][] dp = new long[N+1][M+1];
        dp[0][0] = 0;
        for (int i=0; i<N; i++)
            for (int j=0; j<=M; j++) {
                for (int ni=i+1; ni<=N; ni++)
                    for (int nj=j; nj<=M; nj++) {
                        int typ = A[i];
                        long countA = cumA[ni][typ] - cumA[i][typ];
                        long countB = cumB[nj][typ] - cumB[j][typ];
                        dp[ni][nj] = Math.max(dp[ni][nj], dp[i][j] + Math.min(countA, countB));
                    }
            }

        pw.println(dp[N][M]);
    }

    public static void main(String[] args) throws Exception {
        preprocess();

        Scanner sc = new Scanner(new FileReader("/Users/teuskim/Documents/workspace/android-src/CodeJam/src/C-small-practice.in.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("/Users/teuskim/Documents/workspace/android-src/CodeJam/src/C-small-practice.out.txt"));
        int caseCnt = sc.nextInt();
        for (int caseNum=0; caseNum<caseCnt; caseNum++) {
            System.out.println("Processing test case " + (caseNum + 1));
            pw.print("Case #" + (caseNum+1) + ": ");
            new C_other2().solve(sc, pw);
        }
        pw.flush();
        pw.close();
        sc.close();
    }
}
