package com.teuskim.solved;
import java.util.Scanner;

/**
 * 1권 259페이지. 비대칭 타일링
 * 5번만에 정답 처리되었다. 
 * 1. 홀수일때 가로막대2개가 중앙을 포함하는 경우를 누락했었다.
 * 2. 계산중에 int범위를 벗어나는 경우가 있었다.
 * 
 * 내가 사용한 방법은 중앙부의 형태를 구분하여 각각의 상황에 따라 경우의 수를 계산하도록 한 것이다.
 * 그 외에도 아래와 같이 구할 수 있다.
 * 1. 외곽의 형태를 구분하여 사이의 남은 부분에 대하여 재귀호출 및 getCnt()호출하여 비대칭수 직접 구하기
 * 2. (전체 경우의 수 - 대칭이 되는 경우의 수) 로 구하기
 */
public class ASYMTILING {
	
	private int n;
	private int[][] memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			n = sc.nextInt();
			memo = new int[n][n];
			
			String result = ""+getResult();
			System.out.println(result);
		}
	}
	
	/*
	private int getResult(){
		int result,cnt1,cnt2;
		cnt1 = getCnt(0, n/2);
		cnt2 = getCnt(0, n/2-1);
		result = (int)((cnt1 * (long)(cnt1-1)) % 1000000007);
		if(n%2 == 0){
			result += (int)((cnt2 * (long)(cnt2-1)) % 1000000007);
		}
		else{
			if(cnt2 < 1) cnt2 = 1;
			result += (int)((cnt1*(long)cnt2*2) % 1000000007);
			
			int a = (int)((cnt1*(long)cnt2*2) % 1000000007);
			int b = (int)(((long)cnt1*(long)cnt2*(long)2) % 1000000007);
			if(a != b) System.out.println("a:"+a+", b:"+b);
		}
		return result;
	}
	*/
	
	private int getResult(){
		long result,cnt1,cnt2;
		cnt1 = getCnt(0, n/2);
		cnt2 = getCnt(0, n/2-1);
		result = (cnt1 * (cnt1-1)) % 1000000007;
		if(n%2 == 0){
			result += (cnt2 * (cnt2-1)) % 1000000007;
		}
		else{
			if(cnt2 < 1) cnt2 = 1;
			result += (((cnt1*cnt2) % 1000000007)*2) % 1000000007;
		}
		return (int)(result % 1000000007);
	}
	
	private int getCnt(int idx, int size){
		if(size-idx < 1) return 0;
		if(size-idx == 1) return 1;
		if(size-idx == 2) return 2;
		if(memo[idx][size] > 0) return memo[idx][size];
		
		int result = (getCnt(idx+1, size) + getCnt(idx+2, size)) % 1000000007;
		memo[idx][size] = result;
		return result;
	}
	
	public static void main(String[] args) {
		new ASYMTILING().goodluck();
	}
}