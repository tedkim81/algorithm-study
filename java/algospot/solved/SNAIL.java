package com.teuskim.solved;
import java.util.Arrays;
import java.util.Scanner;

public class SNAIL {
	
	private int n,m;
	private double[][] memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			n = sc.nextInt();
			m = sc.nextInt();
			memo = new double[n+1][m*2];
			for(int i=0; i<n+1; i++) Arrays.fill(memo[i], -1);
			
			String result = String.format("%.10f", getResult(0,0));
			System.out.println(result);
		}
	}
	
	private double getResult(int days, int dist){
		if(days == m){
			if(dist >= n) return 1;
			else return 0;
		}
		if(memo[days][dist] >= 0) return memo[days][dist];
		
		double result = (getResult(days+1, dist+1) + getResult(days+1, dist+2)*3) / 4;
		memo[days][dist] = result;
		return result;
	}
	
	public static void main(String[] args) {
		new SNAIL().goodluck();
	}
}