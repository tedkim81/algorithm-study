package gcj.y2013.round1b;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Problem B. Falling Diamonds
 * 
 * 문제를 몇가지 방식으로 풀어봤으나 모두 오답처리된다.
 * TODO: analysis가 나오면 확인해보고, solution도 같이 확인해본후 문제점을 찾아보자.
 */
public class B_todo {
	
	private int N,X,Y;
	private int XGAP = 20;
	private int[][] comb;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		makeComb();
		
		// get file
		Scanner sc = new Scanner(new File(path+"test.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"test.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			X = sc.nextInt();
			Y = sc.nextInt();
			
//			String result = String.format("%.7f", solve(0, new boolean[N*2][N*2+XGAP]));
//			String result = String.format("%.7f", solve());
			String result = String.format("%.7f", solve2());
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	/**
	 * solve()의 컨셉과 같이 커버관련 정보를 구하고, 그 커버에 대해서만 완전탐색으로 확률을 재귀적으로 계산하는 방식
	 */
	private double solve2(){
		int coveridx = (Math.abs(X)+Math.abs(Y)) / 2;
		int covercnt = 1;
		int tricnt = 1;
		for(int i=0; i<coveridx; i++){
			covercnt += 4;
			tricnt += covercnt;
		}
		if(N >= tricnt) return 1;
		if(N < tricnt-covercnt+Y+1) return 0;
		
		return solve2(N-(tricnt-covercnt), -1, -1, covercnt/2);
	}
	
	private double solve2(int diacnt, int left, int right, int sidemax){
		if((X<=0 && Y==left) || (X>0 && Y==right)) return 1;
		if(diacnt == 0) return 0;
		if(left == sidemax && right == sidemax) return 1;
		
		double result = 0;
		if(left < sidemax && right < sidemax){
			result += 0.5 * solve2(diacnt-1, left+1, right, sidemax);
			result += 0.5 * solve2(diacnt-1, left, right+1, sidemax);
		}
		else if(left == sidemax){
			result += solve2(diacnt-1, left, right+1, sidemax);
		}
		else if(right == sidemax){
			result += solve2(diacnt-1, left+1, right, sidemax);
		}
		return result;
	}
	
	private void makeComb(){
		comb = new int[2000][2000];
		comb[0][0] = 1;
		for(int i=1; i<2000; i++){
			comb[i][0] = 1;
			for(int j=1; j<=i; j++){
				comb[i][j] = comb[i-1][j-1] + comb[i-1][j];
			}
		}
	}
	
	/**
	 * 1개, 6개, 15개, 28개,... 의 삼각형을 완성하면서 진행된다는 점을 이용하여, 삼각형 커버가 몇번째인지와 커버 구성수, 삼각형 구성수를 이용하여
	 * 목표지점이 포함된 커버에서 확률을 바로 계산하는 방식. 그러나 아래와 같이 바로 계산하면 경우의 수가 누락 또는 중복되는 문제가 발생한다.
	 */
	private double solve(){
		int coveridx = (Math.abs(X)+Math.abs(Y)) / 2;
		int covercnt = 1;
		int tricnt = 1;
		for(int i=0; i<coveridx; i++){
			covercnt += 4;
			tricnt += covercnt;
		}
		if(N >= tricnt && X == 0) return 1;
		if((Y+1)*(Y+2)/2 > N) return 0;
		
		double result = 0;
		for(int i=0; i<=coveridx; i++){
			if(tricnt-covercnt+i+Y+1 <= N){
				result += Math.pow(0.5, Y+1) * Math.pow(0.5, i) * comb[Math.max(i, Y)+1][Math.min(i, Y)];
			}
		}
		if(tricnt-covercnt+coveridx+1+Y+1 <= N) result += Math.pow(0.5, coveridx+1);
		return result;
	}
	
	/**
	 * 문제 그대로 시뮬레이션 하는 방식
	 * small input을 해결할 수 있을것 같은데, 오답 처리 된다. 원인은 잘 모르겠다.
	 */
	private double solve(int idx, boolean[][] dias){
		if(idx == N) return 0;
		
		int x = 0;
		int y = 0;
		while(dias[y][x+XGAP]) y += 2;
		
		boolean leftblock = (y==0 || dias[y-1][x-1+XGAP]);
		boolean rightblock = (y==0 || dias[y-1][x+1+XGAP]);
		if(leftblock && rightblock){
			if(x==X && y==Y) return 1;
			else{
				dias[y][x+XGAP] = true;
				return solve(idx+1, dias);
			}
		}
		
		double result = 0;
		if(leftblock == false){
			int xx = x;
			int yy = y;
			while(yy>0 && dias[yy-1][xx-1+XGAP]==false){
				xx -= 1;
				yy -= 1;
			}
			double multp = 1;
			if(rightblock == false) multp = 0.5;
			
			if(xx==X && yy==Y) result += multp;
			else{
				dias[yy][xx+XGAP] = true;
				result += multp*solve(idx+1, dias);
				dias[yy][xx+XGAP] = false;
			}
		}
		if(rightblock == false){
			int xx = x;
			int yy = y;
			while(yy>0 && dias[yy-1][xx+1+XGAP]==false){
				xx += 1;
				yy -= 1;
			}
			double multp = 1;
			if(leftblock == false) multp = 0.5;
			
			if(xx==X && yy==Y) result += multp;
			else{
				dias[yy][xx+XGAP] = true;
				result += multp*solve(idx+1, dias);
				dias[yy][xx+XGAP] = false;
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new B_todo().goodluck();
			
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
