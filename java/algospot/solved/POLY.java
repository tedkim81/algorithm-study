package com.teuskim.solved;
import java.util.Scanner;

/**
 * 1권 264페이지. 폴리오미노
 * getResult()의 기저사례를 size == 1 로만 해서 경우의 수 일부가 누락됐었다.
 * size == first 로 바꿔야한다는 것을 어렵지 않게 알아냈다.
 */
public class POLY {
	
	private int n;
	private int[][] memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			n = sc.nextInt();
			memo = new int[101][101];
			
			int result = 0;
			for(int i=1; i<=n; i++) result = (result + getResult(n, i)) % 10000000;
			System.out.println(result);
		}
	}
	
	private int getResult(int size, int first){
		if(size == first) return 1;
		if(memo[size][first] > 0) return memo[size][first];
		
		int result = 0;
		for(int i=1; i<=size-first; i++){
			result = (result + (getResult(size-first, i) * (first+i-1) % 10000000)) % 10000000;
		}
		memo[size][first] = result;
		return result;
	}
	
	public static void main(String[] args) {
		new POLY().goodluck();
	}
}