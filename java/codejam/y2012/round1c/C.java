package gcj.y2012.round1c;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Problem C. Box Factory
 * 
 * 타입이 일치하는 경우 포장을 하고 재귀호출, 그렇지 않은 경우 A타입을 버리거나, B타입을 버리고 재귀호출하여 
 * 결과들 중 최대값을 리턴하는 방식으로 구현했는데 오답처리되었다. 
 * 문제는 메모이제이션에 있었다. a, b 가 전역변수이면서 변경되고 있기 때문에 관련하여 적용해야 하는데 이것이 꽤 어려웠다.
 * 메모이제이션을 빼고, 타입이 일치하는 경우는 무조건 포장을 한다고 가정을 하니 small input은 풀렸다.
 * 다시 말해서, 타입이 일치하는데 박스나 토이를 그냥 버리는 경우에는 최대값이 나올 수 없다는 것이다.
 * 
 * 이제 large input을 해결하기 위해서는 메모이제이션이 필요하다. analysis를 확인해보자.
 * analysis 누가 썼는지.. 참.. 별로다. 어찌됐건 Longest Common Subsequence 문제의 변형이라는 정도(?)는 알았다.
 * 내가 사용한 알고리즘과 크게 다르지 않다. 그렇다면 다시.. 내 알고리즘 안에서 문제를 해결해보자.
 * 현재 solve()의 문제는 memo[ab][change][aidx][bidx] 에서 서로 다른 경우인데 4개의 값이 모두 같은 경우가 존재한다는 것이다.
 * 타입이 일치하지 않을때 change값을 다음으로 넘기기 때문에 change값이 같지만 실제로 a[aidx]( 또는 b[bidx] )가 다른 경우가 있을 수 있다.
 * solution 을 확인해보자.
 * 
 * analysis와 solution을 여러차례 하루종일 들여다보면서 간신히 해법을 이해할 수 있었다.
 * 역시.. 그림을 그려서 간단히 시뮬레이션을 해보는게 굉장히 중요하다는 것을 새삼 깨달았다.
 * 두 라인의 타입이 같은 경우 단순히 다음으로 넘길 수 없다는 것을 깨달아야 한다. 
 * 다행히 두 라인의 물건 개수가 같다면 다음으로 바로 넘길 수 있겠지만 그렇지 않은 경우들을 모두 포괄하는 일반적인 알고리즘을 만들어야 한다.
 * 두 라인 물건의 개수가 다르다면 적은 개수만큼 완성하고 남은 개수를 버릴지 다른 라인에서 같은 타입이 나올때까지 기다려야 할지에 따라 다양한 경우의 수가 발생한다.
 * A[aidx]==B[bidx]==T 라고 한다면, aidx<aidx2, bidx<bidx2 인 모든 aidx2,bidx2에 대하여 aidx~aidx2, bidx~bidx2구간에서 T타입인 완성품수와
 * 그 다음 구간에 대한 부분문제 호출결과의 합의 최대값을 구하면 되는 것이다.
 * 여기서 이해하기 어려웠던 부분은 왜 모든 구간에 대해 T타입인 완성품수만 구하느냐는 것이었다. 이것은 귀류법으로 설명이 가능하다.
 * T타입이 아닌 U타입에 대해서도 완성품수를 계산하도록 하는 경우는, U타입으로 시작하는 부분문제와 그 이전에 대해서는 T타입의 완성품수를 합하는 경우와 같다.
 * 따라서 T타입에 대해서만 완성품수를 구하도록 하고 다음 부분문제를 호출하도록 하면 모든 경우를 일반화 할 수 있는 것이다.
 * 
 * TODO: 아직 반복적 동적계획법이 익숙하지가 않다. 이 문제의 solution들도 모두 반복적 동적계획법을 사용했다. 
 *       반복적 동적계획법으로 풀어볼 필요가 있으나 일단 이 문제에 너무 많은 시간을 소비했고 지쳤기 때문에 다음으로 미룬다.
 */
public class C {
	
	private int N,M;
	private long[] a,b;
	private int[] A,B;
	private long[][][][] memo;
	private long[][] memo2;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"C-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"C-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			M = sc.nextInt();
			a = new long[N];  A = new int[N];
			for(int i=0; i<N; i++){
				a[i] = sc.nextLong();
				A[i] = sc.nextInt();
			}
			b = new long[M];  B = new int[M];
			for(int i=0; i<M; i++){
				b[i] = sc.nextLong();
				B[i] = sc.nextInt();
			}
//			memo = new long[3][100][N][M];
//			for(int k=0; k<3; k++) for(int j=0; j<100; j++) for(int i=0; i<N; i++) Arrays.fill(memo[k][j][i], -1);
//			String result = ""+solve(0,0,0,0);
			
			memo2 = new long[N][M];
			for(int i=0; i<N; i++) Arrays.fill(memo2[i], -1);
			String result = ""+solve2(0,0);
			
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private long solve2(int aidx, int bidx){
		if(aidx==N || bidx==M) return 0;
		if(memo2[aidx][bidx] >= 0) return memo2[aidx][bidx];
		
		long result = 0;
		if(A[aidx] == B[bidx]){
			long asum = a[aidx];
			long bsum = b[bidx];
			for(int i=aidx+1; i<=N; i++){
				for(int j=bidx+1; j<=M; j++){
					result = Math.max(result, solve2(i,j) + Math.min(asum, bsum));
					if(j<M && B[j] == B[bidx]) bsum += b[j];
				}
				if(i<N && A[i] == A[aidx]) asum += a[i];
				bsum = b[bidx];
			}
		}
		else{
			result = Math.max(solve2(aidx+1, bidx), solve2(aidx, bidx+1));
		}
		memo2[aidx][bidx] = result;
		return result;
	}
	
	private long solve(int ab, int change, int aidx, int bidx){
		if(aidx==N || bidx==M) return 0;
		if(memo[ab][change][aidx][bidx] >= 0) return memo[ab][change][aidx][bidx];
		
		long result = 0;
		if(A[aidx] == B[bidx]){
			if(a[aidx] > b[bidx]){
				a[aidx] -= b[bidx];
				result = b[bidx] + solve(1, (ab==1 ? change+1 : 1), aidx, bidx+1);
				a[aidx] += b[bidx];
			}
			else if(a[aidx] < b[bidx]){
				b[bidx] -= a[aidx];
				result = a[aidx] + solve(2, (ab==2 ? change+1 : 1), aidx+1, bidx);
				b[bidx] += a[aidx];
			}
			else{
				result = a[aidx] + solve(0, 0, aidx+1, bidx+1);
			}
		}
		else{
			result = Math.max(solve((ab==2 ? ab : 0), (ab==2 ? change : 0), aidx+1, bidx)
							, solve((ab==1 ? ab : 0), (ab==1 ? change : 0), aidx, bidx+1));
		}
//		result = Math.max(result, solve(0, bchange, aidx+1, bidx));
//		result = Math.max(result, solve(achange, 0, aidx, bidx+1));  // 여기가 문제였다. 타입이 일치하면 무조건 포장을 하는게 상책이다.
		
		memo[ab][change][aidx][bidx] = result;
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new C().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		print("end!");
	}
	
	
	/**********************
	 * code for debugging *
	 **********************/
	
	public void check(boolean isRight, String log){
		if(isRight == false){
			print("exit: "+log);
			System.exit(0);
		}
	}
	
	public static void print(String str){
		System.out.println(str);
	}
	
	public static void print(int[] arr){
		if(arr == null) print("null");
		else{
			String str = "[";
			if(arr.length > 0){
				for(int i=0; i<arr.length; i++) str += arr[i]+",";
				str = str.substring(0, str.length()-1);
			}
			str += "]";
			print(str);
		}
	}
}
