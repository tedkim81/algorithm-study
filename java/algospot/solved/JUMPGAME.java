package com.teuskim.solved;
import java.util.Scanner;

public class JUMPGAME {
	
	private int n;
	private int[][] map;
	private int[][] memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			n = sc.nextInt();
			map = new int[n][n];
			memo = new int[n][n];
			for(int i=0; i<n; i++){
				for(int j=0; j<n; j++){
					map[i][j] = sc.nextInt();
				}
			}
			
			String result = ""+(getResult(0,0) ? "YES" : "NO");
			System.out.println(result);
		}
	}
	
	private boolean getResult(int row, int col){
		if(row == n-1 && col == n-1) return true;
		if(row >= n || col >= n) return false;
		
		if(memo[row][col] != 0){
			return memo[row][col]==1 ? true : false;
		}
		
		int num = map[row][col];
		boolean result = getResult(row+num,col) || getResult(row,col+num);
		memo[row][col] = result ? 1 : -1;
		return result;
	}
	
	public static void main(String[] args) {
		new JUMPGAME().goodluck();
	}
}