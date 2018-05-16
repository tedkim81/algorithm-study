package com.teuskim.solved;
import java.util.Scanner;

/**
 * 1권 252페이지. 타일링 방법의 수 세기
 * 문제를 잘못 이해하여 시간이 다소 걸렸다.
 * 경우의 수를 세야 하는데 타일의 수를 세는 바람에 결과가 입력과 동일하게 나왔었다..
 */
public class TILING2 {
	
	private int n;
	private int[] memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			n = sc.nextInt();
			memo = new int[n];
			
			String result = ""+getResult(0);
			System.out.println(result);
		}
	}
	
	private int getResult(int idx){
		if(idx == n-1) return 1;
		if(idx == n-2) return 2;
		
		if(memo[idx] > 0) return memo[idx];
		
		int result = getResult(idx+1) + getResult(idx+2);
		result = result % 1000000007;
		memo[idx] = result;
		return result;
	}
	
	public static void main(String[] args) {
		new TILING2().goodluck();
	}
}