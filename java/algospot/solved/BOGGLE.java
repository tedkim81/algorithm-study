package com.teuskim.solved;
import java.util.Scanner;

public class BOGGLE {
	
	private char[][] map;
	private int[][] way = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
	private int[][][] cache;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			map = new char[5][5];
			String str;
			for(int i=0; i<5; i++){
				str = sc.next();
				for(int j=0; j<5; j++){
					map[i][j] = str.charAt(j);
				}
			}
			int N = sc.nextInt();
			for(int i=0; i<N; i++){
				str = sc.next();
				if(findWord(str)){
					System.out.println(str+" YES");
				}
				else{
					System.out.println(str+" NO");
				}
			}
		}
	}
	
	private boolean findWord(String word){
		cache = new int[5][5][10];
		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){
				if(findWord(i,j,word,0)) return true;
			}
		}
		return false;
	}
	
	private boolean findWord(int row, int col, String word, int idx){
		if(row < 0 || row >= 5 || col < 0 || col >= 5) return false;
		if(map[row][col] != word.charAt(idx)) return false;
		if(idx == word.length()-1) return true;
		
		if(cache[row][col][idx] != 0){
			if(cache[row][col][idx] == 1) return true;
			else return false;
		}
		
		for(int i=0; i<way.length; i++){
			if(findWord(row+way[i][0], col+way[i][1], word, idx+1)){
				cache[row][col][idx] = 1;
				return true;
			}
		}
		cache[row][col][idx] = -1;
		return false;
	}
	
	public static void main(String[] args) {
		new BOGGLE().goodluck();
	}
}