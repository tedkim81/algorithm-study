package com.teuskim.solved;
import java.util.Scanner;
import java.util.Stack;

/**
 * 1장 196페이지 울타리 잘라내기 문제를 2장 628페이지 스택을 이용한 해법 적용
 */
public class FENCE {
	
	private int N;
	private int[] h;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			N = sc.nextInt();
			h = new int[N+1];
			for(int i=0; i<N; i++) h[i] = sc.nextInt();
			
			String result = ""+solve();
			System.out.println(result);
		}
	}
	
	private int solve(){
		int result = 0;
		Stack<Integer> remain = new Stack<Integer>();
		for(int i=0; i<=N; i++){
			while(remain.isEmpty()==false && h[remain.peek()]>=h[i]){
				int j = remain.pop();
				if(remain.isEmpty()){
					result = Math.max(result, h[j]*i);
				}
				else{
					result = Math.max(result, h[j]*(i-remain.peek()-1));
				}
			}
			remain.push(i);
		}
		return result;
	}
	
	public static void main(String[] args) {
		new FENCE().goodluck();
	}
}