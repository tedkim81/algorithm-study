package com.teuskim.solved;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1권 269페이지. 두니발 박사의 탈옥
 */
public class NUMB3RS {
	
	private int n,d,p,t;
	private int[][] A;
	private int[] q,adjacentCnt;
	private double[][] memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			n = sc.nextInt();
			d = sc.nextInt();
			p = sc.nextInt();
			A = new int[n][n];
			for(int i=0; i<n; i++) for(int j=0; j<n; j++) A[i][j] = sc.nextInt();
			t = sc.nextInt();
			q = new int[t];
			for(int i=0; i<t; i++) q[i] = sc.nextInt();
			adjacentCnt = new int[n];
			for(int i=0; i<n; i++){
				int cnt = 0;
				for(int j=0; j<n; j++){
					if(A[i][j] == 1) cnt++;
				}
				adjacentCnt[i] = cnt;
			}
			memo = new double[101][51];
			for(int i=0; i<101; i++) Arrays.fill(memo[i], -1);
			
			String result = "";
			for(int i=0; i<t; i++){
				result += String.format("%.8f", getResult(d, q[i]))+" ";
			}
			if(result.length() > 0) result = result.substring(0, result.length()-1);
			System.out.println(result);
		}
	}
	
	private double getResult(int days, int town){
		if(days == 0) return (town==p) ? 1 : 0;
		if(memo[days][town] >= 0) return memo[days][town];
		
		double result = 0;
		for(int i=0; i<n; i++){
			if(A[i][town] == 0) continue;
			result += getResult(days-1, i) / adjacentCnt[i];
		}
		memo[days][town] = result;
		return result;
	}
	
	public static void main(String[] args) {
		new NUMB3RS().goodluck();
	}
}