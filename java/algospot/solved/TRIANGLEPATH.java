package com.teuskim.solved;
import java.util.Scanner;

public class TRIANGLEPATH {
	
	private int n;
	private int[][] tri, memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			n = sc.nextInt();
			tri = new int[n][n];
			for(int i=0; i<n; i++){
				for(int j=0; j<=i; j++){
					tri[i][j] = sc.nextInt();
				}
			}
			memo = new int[n][n];
			
			String result = ""+getResult(0, 0);
			System.out.println(result);
		}
	}
	
	private int getResult(int row, int col){
		if(row == n-1) return tri[row][col];
		if(memo[row][col] > 0) return memo[row][col];
		
		int result = tri[row][col]+Math.max(getResult(row+1,col), getResult(row+1,col+1));
		memo[row][col] = result;
		return result;
	}
	
	public static void main(String[] args) {
		new TRIANGLEPATH().goodluck();
	}
}