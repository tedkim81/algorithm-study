package com.teuskim.solved;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1권 254페이지. 삼각형 위의 최대 경로 개수 세기
 * 시간은 2시간 가까이 걸렸으나 난관에 봉착했던 상태에서 지속적인 생각의 전환을 통해 문제를 스스로 풀어냈다는 점에서 의미가 있었다.
 * 처음에 getPathCnt(int row, int col, int sum) 으로 구현을 하고 worst case test를 해보니 시간안에 결과를 구할 수 없었다.
 * 그리고 이런 저런 방법들을 고민해 보면서 시간이 많이 지연되었다. 생각한 방법을 구체화하고 가능여부를 따져보는 시간을 최소화해야 한다.
 * 생각을 더 이어가지 못하는 생각의 데드락 상태가 걸리지 않도록 주의하자.
 * 그리고 이어지는 중요한 깨달음 하나, 문제가 분명 많은 중복을 일으키는 형태이므로 동적계획법을 적용하는 문제라고 생각할 수 있다.
 * 그렇다면 중복되는 문제가 발생할 수 있도록 부분문제로 나누어서 동적계획법을 적용하도록 해보자고 생각했다.
 * 그렇다면 getPathCnt(int row, int col) 의 형태로 만들 수 있어야 한다.
 * 그리고 나서, (row+1,col)과 (row+1,col+1) 에서의 경로합이 같은지를 비교하는 방식을 생각해 낼 수 있었다.
 */
public class TRIPATHCNT {
	
	private int n;
	private int[][] tri, memo, memo2;
	
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
			memo2 = new int[n][n];
			for(int i=0; i<n; i++) Arrays.fill(memo2[i], -1);
			
			String result = ""+getPathCnt(0,0);
			System.out.println(result);
		}
	}
	
	private int getPathCnt(int row, int col){
		if(row == n-1) return 1;
		if(memo2[row][col] >= 0) return memo2[row][col];
		
		int left = getPathSize(row+1, col);
		int right = getPathSize(row+1, col+1);
		int result;
		if(left > right){
			result = getPathCnt(row+1, col);
		}
		else if(left < right){
			result = getPathCnt(row+1, col+1);
		}
		else{  // left == right
			result = getPathCnt(row+1, col) + getPathCnt(row+1, col+1);
		}
		memo2[row][col] = result;
		return result;
	}
	
	private int getPathSize(int row, int col){
		if(row == n-1) return tri[row][col];
		if(memo[row][col] > 0) return memo[row][col];
		
		int result = tri[row][col] + Math.max(getPathSize(row+1,col), getPathSize(row+1,col+1));
		memo[row][col] = result;
		return result;
	}
	
	public static void main(String[] args) {
		new TRIPATHCNT().goodluck();
//		new Main().test();
	}
	
	private void test(){
		n = 100;
		tri = new int[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<=i; j++){
				tri[i][j] = 1;
			}
		}
		memo = new int[n][n];
		memo2 = new int[n][n];
		for(int i=0; i<n; i++) Arrays.fill(memo2[i], -1);
		
		int result = getPathCnt(0,0);
		System.out.println("result:"+result);
	}
}