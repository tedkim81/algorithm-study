package com.teuskim.solved;
import java.util.Scanner;

/**
 * 1권 159페이지. 게임판 덮기
 * 실수로 정답을 구하는데 시간이 꽤 걸렸다. 주의하자.
 * 1. dy,dx를 시작점 제외한 두점의 좌표차이값으로 정의했는데, 아예 시작점 포함하도록 하고 board[][] 변경할때 for loop로 일괄적으로 하는게 나을 듯 하다.
 * 2. cover() 에서 2중 for loop 하는 이유가 시작점을 찾기 위한 것이기 때문에 시작점 찾고 재귀호출했다면 return 을 해야하는데 그 부분이 빠져 있어서 문제가 있었다.
 */
public class BOARDCOVER {
	
	private int H,W;
	private char[][] board;
	private int[][] dy = {{1,1},{1,1},{0,1},{1,0}};
	private int[][] dx = {{0,-1},{0,1},{1,1},{0,1}};
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			H = sc.nextInt();
			W = sc.nextInt();
			board = new char[H][W];
			int ptCnt = 0;
			for(int i=0; i<H; i++){
				String str = sc.next();
				for(int j=0; j<W; j++){
					board[i][j] = str.charAt(j);
					if(board[i][j] == '.') ptCnt++;
				}
			}
			
			String result = ""+cover(ptCnt);
			System.out.println(result);
		}
	}
	
	private int cover(int remainCnt){
		if(remainCnt == 0) return 1;
		if(remainCnt < 3) return 0;
		
		for(int i=0; i<H; i++){
			for(int j=0; j<W; j++){
				if(board[i][j] == '.'){
					int success = 0;
					for(int k=0; k<4; k++){
						if(i+dy[k][0] >= 0 && i+dy[k][0] < H && j+dx[k][0] >= 0 && j+dx[k][0] < W 
								&& board[i+dy[k][0]][j+dx[k][0]] == '.'
								&& i+dy[k][1] >= 0 && i+dy[k][1] < H && j+dx[k][1] >= 0 && j+dx[k][1] < W
								&& board[i+dy[k][1]][j+dx[k][1]] == '.'){
							board[i][j] = '#';  board[i+dy[k][0]][j+dx[k][0]] = '#';  board[i+dy[k][1]][j+dx[k][1]] = '#';
							success += cover(remainCnt-3);
							board[i][j] = '.';  board[i+dy[k][0]][j+dx[k][0]] = '.';  board[i+dy[k][1]][j+dx[k][1]] = '.';
						}
					}
					return success;
				}
			}
		}
		return 0;
	}
	
	public static void main(String[] args) {
		new BOARDCOVER().goodluck();
	}
}